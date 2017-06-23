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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.aliyun.odps.Column;
import com.aliyun.odps.Instance;
import com.aliyun.odps.Instance.TaskStatus;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.TableSchema;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordReader;
import com.aliyun.odps.data.RecordWriter;
import com.aliyun.odps.task.SQLTask;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TableTunnel.DownloadSession;
import com.aliyun.odps.tunnel.TableTunnel.UploadSession;
import com.aliyun.odps.tunnel.TunnelException;
import com.quancheng.achilles.dao.annotation.OdpsColumn;
import com.quancheng.achilles.util.JsonUtil;
import com.quancheng.achilles.util.TimeUtil;

import net.sf.json.JSONArray;

public abstract class AbstractOdpsQuery {

    private Logger logger   = LoggerFactory.getLogger(AbstractOdpsQuery.class);
    long           interval = 1000 * 60 * 10l;
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

    protected Boolean update(String sql, Object... paramaters) throws OdpsException, IOException {
        return update(buildSql(sql, paramaters));
    }

    protected JSONArray query(String sql, Object... paramaters) throws OdpsException, IOException {
        return query(String.format(sql, paramaters));
    }

    protected String buildSql(String sql, Object... paramaters) {
        String sqlStr = String.format(sql, paramaters) + ";";
        System.err.println(sqlStr);
        return sqlStr;
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

    protected Boolean update(String sql) throws OdpsException, IOException {
        boolean result = false;
        Instance instance = SQLTask.run(getConfig(), sql);
        Map<String, TaskStatus> taskStatus = instance.getTaskStatus();
        System.err.println(JsonUtil.objectToJson(taskStatus));
        instance.waitForSuccess();
        taskStatus = instance.getTaskStatus();
        System.err.println(JsonUtil.objectToJson(taskStatus));
        if (taskStatus != null && "SUCCESS".equalsIgnoreCase(taskStatus.get("AnonymousSQLTask").getStatus().name())) {
            result = true;
        }
        return result;
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
        instance.waitForSuccess();
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
                if (StringUtils.isEmpty(valO)) {
                    // record.setString(i, val);
                    continue;
                }
                String val = valO.toString();

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

    private int             threadNum   = 6;
    private ExecutorService pool        = Executors.newFixedThreadPool(threadNum + 1);
    static Long             downloadNum = 0L;

    public Boolean getAllAndSaveToDB(String odpsTableName, SaveToDB saveToDB) throws TimeoutException {
        boolean result = true;
        TableTunnel tunnel = new TableTunnel(getConfig());
        // PartitionSpec partitionSpec = new PartitionSpec(partition);
        DownloadSession downloadSession;
        try {
            downloadSession = tunnel.createDownloadSession(getConfig().getDefaultProject(), odpsTableName);
            System.out.println("Session Status is : " + downloadSession.getStatus().toString());
            long count = downloadSession.getRecordCount();
            System.out.println("RecordCount is: " + count);
            ExecutorService pool = Executors.newFixedThreadPool(threadNum);
            List<Future<List<Map<String, Object>>>> futureList;
            int burck = 40000;
            double page = Math.ceil((double) count / (double) burck);
            downloadNum = 0l;
            int idNum = 0;
            for (int p = 0; p < page - 1; p++) {
                futureList = new ArrayList<>();
                long step = burck / threadNum;
                for (int i = 0; i < threadNum - 1; i++) {
                    getDownloadList(pool, downloadSession, futureList, idNum, i, downloadNum, step);
                    idNum++;
                }
                getDownloadList(pool, downloadSession, futureList, idNum, threadNum - 1, downloadNum,
                                burck - ((threadNum - 1) * step));
                Boolean save = saveDownloadList(futureList, saveToDB);
                idNum++;
                if (!save) {
                    return save;
                }
            }
            futureList = new ArrayList<>();
            long step = (long) (count - burck * (page - 1));
            getDownloadList(pool, downloadSession, futureList, idNum, 0, downloadNum, step);
            Boolean save = saveDownloadList(futureList, saveToDB);
            if (!save) {
                return save;
            }
            System.out.println("Record Count is: " + downloadNum);
            // pool.shutdown();
        } catch (TunnelException | IOException | InterruptedException | ExecutionException e) {
            result = false;
            logger.error("getAllAndSaveToDB {} have a error.{} ", odpsTableName, e);
        }
        return result;
    }

    private Boolean saveDownloadList(List<Future<List<Map<String, Object>>>> futureList,
                                     SaveToDB saveToDB) throws InterruptedException, ExecutionException,
                                                        TimeoutException {
        for (Future<List<Map<String, Object>>> future : futureList) {
            List<Map<String, Object>> dataList = future.get(5, TimeUnit.MINUTES);
            Boolean save = saveToDB.save(dataList);
            if (!save) {
                return save;
            }
            downloadNum = downloadNum + dataList.size();
        }
        return true;
    }

    private void getDownloadList(ExecutorService pool, DownloadSession downloadSession,
                                 List<Future<List<Map<String, Object>>>> futureList, int idNum, int index,
                                 Long startIndex, long step) throws TunnelException, IOException {
        startIndex = startIndex + (index * step);
        System.err.println("getDownloadList Thread:" + idNum + "  startIndex: " + startIndex + " size:" + step);
        RecordReader recordReader = downloadSession.openRecordReader(startIndex, step);
        futureList.add(pool.submit(new DownloadOdpsThread(idNum, recordReader, downloadSession.getSchema())));
    }

    protected boolean insert(String tableName, List<Map<String, Object>> datas) throws OdpsException, IOException,
                                                                                ParseException, ExecutionException,
                                                                                TimeoutException {
        boolean result = true;
        try {
            TableTunnel tunnel = new TableTunnel(getConfig());
            UploadSession uploadSession = tunnel.createUploadSession(getConfig().getDefaultProject(), tableName);
            System.out.println("Session Status is : " + uploadSession.getStatus().toString());

            // ArrayList<Callable<Boolean>> callers = new ArrayList<Callable<Boolean>>();
            List<Future<Boolean>> futureList = new ArrayList<>();
            int num = 1;
            switch (datas.size() / 3000) {
                case 0:
                    num = 1;
                    break;
                case 1:
                    num = 2;
                    break;
                case 2:
                    num = 3;
                    break;
                case 3:
                    num = 4;
                    break;
                case 4:
                    num = 5;
                    break;
                default:
                    num = 5;
                    break;
            }

            for (int i = 0; i < num; i++) {
                RecordWriter recordWriter = uploadSession.openBufferedWriter();
                // callers.add(new UploadOdpsThread(i, threadNum, recordWriter,
                // listMapToListRecord(uploadSession, datas)));
                futureList.add(pool.submit(new UploadOdpsThread(i, num, recordWriter,
                                                                listMapToListRecord(uploadSession, datas))));
            }
            for (Future<Boolean> future : futureList) {
                future.get(5, TimeUnit.MINUTES);
            }
            // pool.invokeAll(callers);
            // pool.shutdown();
            // Long[] blockList = new Long[threadNum];
            // for (int i = 0; i < threadNum; i++)
            // blockList[i] = Long.valueOf(i);
            // uploadSession.commit(blockList);
            uploadSession.commit();
            System.out.println("upload success!");
        } catch (TunnelException | IOException | InterruptedException e) {
            result = false;
            logger.error("insert {} have a error.{} ", tableName, e);
        }

        return result;
    }
}
