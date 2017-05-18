package com.quancheng.achilles.dao.odps;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.odps.Column;
import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.task.SQLTask;
import com.quancheng.achilles.dao.annotation.OdpsColumn;

import net.sf.json.JSONArray;

public abstract class AbstractOdpsQuery {

    long interval = 3000l;
    @Autowired
    Odps config;

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
}
