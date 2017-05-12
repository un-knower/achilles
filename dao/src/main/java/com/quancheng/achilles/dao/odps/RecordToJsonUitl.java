package com.quancheng.achilles.dao.odps;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aliyun.odps.Column;
import com.aliyun.odps.OdpsType;
import com.aliyun.odps.data.Record;
import net.sf.json.JSONObject;

public class RecordToJsonUitl {
    final static Logger logger =  LogManager.getLogger();
    public static JSONObject json(Record record){
        JSONObject json = new JSONObject();
        String value = null;
        for(Column col:record.getColumns()){
            OdpsType ot = col.getTypeInfo().getOdpsType();
            if(OdpsType.DATETIME.equals(ot)){
                value = record.get(col.getName()).toString();
            }else if(OdpsType.VOID.equals(ot)){
                value = null;
            }else{
                value = record.get(col.getName()) == null?null:record.get(col.getName()).toString();
            }
            json.put(col.getName(), value);
        }
        return json;
    }
    
    
    public static <T>T json(Record record,Class<T> cls,Map<String,Field> fieldMap){
        T t = null;
        try {
            t =cls.newInstance();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        Field field = null;
        PropertyDescriptor pd = null;
        for(Column col:record.getColumns()){
            if(fieldMap.containsKey(col.getName()) && record.get(col.getName()) != null){
                field = fieldMap.get(col.getName());
                try {
                    pd = new PropertyDescriptor(field.getName(), cls);
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
                try {
                    pd.getWriteMethod().invoke(t, record.get(col.getName()));
                } catch ( Exception e) {
                    logger.error("error{},field :{},field type:{} , col:{}, value:{},value type:{}",
                            e.getMessage(),field.getName(),field.getType().getName(),col.getName(), 
                            record.get(col.getName()),record.get(col.getName())==null?null:record.get(col.getName()).getClass().getName());
                }
            }
        }     
        return t;
    }
}
