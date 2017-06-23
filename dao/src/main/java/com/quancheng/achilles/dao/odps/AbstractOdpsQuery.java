package com.quancheng.achilles.dao.odps;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.aliyun.odps.Column;
import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.TableSchema;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordWriter;
import com.aliyun.odps.task.SQLTask;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TableTunnel.UploadSession;
import com.aliyun.odps.tunnel.TunnelException;
import com.quancheng.achilles.dao.annotation.OdpsColumn;
import com.quancheng.achilles.util.TimeUtil;

import net.sf.json.JSONArray;

public abstract class AbstractOdpsQuery {

    private Logger logger   = LoggerFactory.getLogger(AbstractOdpsQuery.class);
    long           interval = 3000l;
    @Autowired
    Odps           config;

    protected JSONArray query(Map<String, Object> map, String table) throws OdpsException, IOException {
        return query(buildSql(map, table));
    }

    protected <T> List<T> query(Map<String, Object> map, String table, Class<T> cls) throws OdpsException, IOException {
        return query(buildSql(map, table), cls);
    }

    protected <T> List<T> query(String columnStr, Map<String, Object> map, String table,
                                Class<T> cls) throws OdpsException, IOException {
        return query(buildSql(columnStr, map, table), cls);
    }

    protected <T> List<T> query(Class<T> cls, String sql, Object... paramaters) throws OdpsException, IOException {
        return query(buildSql(sql, paramaters), cls);
    }

    protected JSONArray query(String sql, Object... paramaters) throws OdpsException, IOException {
        return query(String.format(sql, paramaters));
    }

    protected String buildSql(String sql, Object... paramaters) {
        return String.format(sql, paramaters) + ";";
    }

    protected String buildSql(String columnStr, Map<String, Object> map, String table) {
        String sql = "select " + columnStr + " from " + table + " where 1=1 ";
        if (map != null && !map.isEmpty()) {
            for (String col : map.keySet()) {
                sql += " and " + col + "='" + map.get(col) + "'";
            }
        }
        return sql + ";";
    }

    protected String buildSql(Map<String, Object> map, String table) {
        return buildSql("*", map, table);
    }

    protected Odps getConfig() {
        return config;
    }

    /**
     * 返回json
     * 
     * @param sql
     * @return
     * @throws OdpsException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> query(String sql, Class<T> cls) throws OdpsException, IOException {
        Instance instance = SQLTask.run(getConfig(), sql);
        instance.waitForSuccess(interval);
        Iterator<Record> records = SQLTask.getResultSet(instance);
        List<T> ja = new ArrayList<>();
        if (cls != Map.class) {
            Field[] fields = cls.getDeclaredFields();
            Map<String, Field> fieldMap = new HashMap<>(fields.length);

            if (!records.hasNext()) {
                return ja;
            }
            for (Field field : fields) {
                OdpsColumn oc = field.getAnnotation(OdpsColumn.class);
                if (oc != null) {
                    fieldMap.put(oc.value(), field);
                } else {
                    fieldMap.put(field.getName(), field);
                }
            }
            while (records.hasNext()) {
                Record record = records.next();
                ja.add(RecordToJsonUitl.json(record, cls, fieldMap));
            }
        } else {
            while (records.hasNext()) {
                Record record = records.next();
                Map<String, Object> map = new HashMap<>();
                for (Column col : record.getColumns()) {
                    map.put(col.getName(), record.get(col.getName()));
                }
                ja.add((T) map);
            }
        }
        return ja;
    }

    protected JSONArray query(String sql) throws OdpsException, IOException {
        Instance instance = SQLTask.run(getConfig(), sql);
        instance.waitForSuccess(interval);
        Iterator<Record> records = SQLTask.getResultSet(instance);
        JSONArray ja = new JSONArray();
        while (records.hasNext()) {
            Record record = records.next();
            ja.add(RecordToJsonUitl.json(record));
        }
        return ja;
    }

    private List<Record> listMapToListRecord(UploadSession uploadSession,
                                             List<Map<String, Object>> datas) throws ParseException {
        List<Record> list = new ArrayList<>();
        TableSchema schema = uploadSession.getSchema();
        Record record;
        for (Map<String, Object> m : datas) {
            record = uploadSession.newRecord();
            for (int i = 0; i < schema.getColumns().size(); i++) {
                Column column = schema.getColumn(i);
                String name = column.getName();
                Object valO = m.get(name);
                String val = valO.toString();
                if (StringUtils.isEmpty(val)) {
                    // record.setString(i, val);
                    continue;
                }
                switch (column.getTypeInfo().getOdpsType()) {
                    case BIGINT:
                        record.setBigint(i, Long.valueOf(val));
                        break;
                    case BOOLEAN:
                        record.setBoolean(i, Boolean.valueOf(val));
                        break;
                    case DATETIME:
                        Date time = null;
                        if (!val.contains("-") && val.trim().length() == 10) {
                            time = new Date(Long.valueOf(val));
                        } else if (val.trim().length() == 10) {
                            time = TimeUtil.parseDate("yyyy-MM-dd", val);
                        } else {
                            time = TimeUtil.parseDate("yyyy-MM-dd hh:mm:ss", val);
                        }
                        record.setDatetime(i, time);
                        break;
                    case DOUBLE:
                        record.setDouble(i, Double.valueOf(val));
                        break;
                    case STRING:
                        record.setString(i, val);
                        break;
                    default:
                        throw new RuntimeException("Unknown column type: " + column.getTypeInfo().getTypeName());
                }
            }
            list.add(record);
        }
        return list;
    }

    private int             threadNum = 5;
    private ExecutorService pool      = Executors.newFixedThreadPool(threadNum);

    protected boolean insert(String tableName, List<Map<String, Object>> datas) throws OdpsException, IOException,
                                                                                ParseException {
        boolean result = true;
        try {
            TableTunnel tunnel = new TableTunnel(getConfig());
            UploadSession uploadSession = tunnel.createUploadSession(getConfig().getDefaultProject(), tableName);
            System.out.println("Session Status is : " + uploadSession.getStatus().toString());

            ArrayList<Callable<Boolean>> callers = new ArrayList<Callable<Boolean>>();
            for (int i = 0; i < threadNum; i++) {
                RecordWriter recordWriter = uploadSession.openRecordWriter(i);
                callers.add(new UploadOdpsThread(i, threadNum, recordWriter,
                                                 listMapToListRecord(uploadSession, datas)));
            }
            pool.invokeAll(callers);
            pool.shutdown();
            Long[] blockList = new Long[threadNum];
            for (int i = 0; i < threadNum; i++)
                blockList[i] = Long.valueOf(i);
            uploadSession.commit(blockList);
            System.out.println("upload success!");
        } catch (TunnelException e) {
            result = false;
            logger.error("insert {} have a error.{} ", tableName, e);
        } catch (IOException e) {
            result = false;
            logger.error("insert {} have a error.{} ", tableName, e);
        } catch (InterruptedException e) {
            result = false;
            logger.error("insert {} have a error.{} ", tableName, e);
        }

        return result;
    }
}
