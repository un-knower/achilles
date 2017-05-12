package com.quancheng.achilles.dao.odps;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
    protected JSONArray query(Map<String,Object> map,String table) throws OdpsException, IOException{
        return query(buildSql(map,table));
    }
    protected <T>List<T> query(Map<String,Object> map,String table,Class<T> cls) throws OdpsException, IOException{
        return query(buildSql(map,table),cls);
    }
    protected JSONArray query(String sql,Object ... paramaters) throws OdpsException, IOException{
        return query(String.format(sql, paramaters));
    }
    protected String buildSql(Map<String,Object> map,String table){
        String sql="select * from "+table +" where 1=1 ";
        if (map != null && !map.isEmpty()) {
            for (String col : map.keySet()) {
                sql += " and " + col + "='" + map.get(col) + "'";
            } 
        }
        return  sql+";";
    }
    protected Odps getConfig() {
        return config;
    }
    /**
     * 返回json
     * @param sql
     * @return
     * @throws OdpsException
     * @throws IOException
     */
    protected <T>List<T> query( String sql,Class<T> cls ) throws OdpsException, IOException{
        Instance instance = SQLTask.run(getConfig(), sql);
        instance.waitForSuccess(interval);
        Iterator<Record> records = SQLTask.getResultSet(instance);
        Field[] fields = cls.getDeclaredFields();
        Map<String,Field> fieldMap= new HashMap<>(fields.length);
        List<T> ja = new ArrayList<>();
        if(!records.hasNext()){
            return ja;
        }
        for (Field field : fields) {
            OdpsColumn oc = field.getAnnotation(OdpsColumn.class);
            if( oc !=null ){
                fieldMap.put(oc.value(), field);
            }else{
                fieldMap.put(field.getName(), field);
            }
        }
        while(records.hasNext()){
            Record record = records.next();
            ja.add(RecordToJsonUitl.json(record,cls,fieldMap));
        }
        return ja ;
    }
    protected JSONArray query( String sql  ) throws OdpsException, IOException{
        Instance instance = SQLTask.run(getConfig(), sql);
        instance.waitForSuccess(interval);
        Iterator<Record> records = SQLTask.getResultSet(instance);
        JSONArray ja = new JSONArray();
        while(records.hasNext()){
            Record record = records.next();
            ja.add(RecordToJsonUitl.json(record));
        }
        return ja ;
    }
}
