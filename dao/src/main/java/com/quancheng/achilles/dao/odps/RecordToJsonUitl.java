package com.quancheng.achilles.dao.odps;

import com.aliyun.odps.Column;
import com.aliyun.odps.OdpsType;
import com.aliyun.odps.data.Record;
import com.quancheng.achilles.util.JsonUtil;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class RecordToJsonUitl {


    public static JSONObject json(Record record) {
        JSONObject json = new JSONObject();
        String value = null;
        for (Column col : record.getColumns()) {
            OdpsType ot = col.getTypeInfo().getOdpsType();
            if (OdpsType.DATETIME.equals(ot)) {
                value = record.get(col.getName()).toString();
            } else if (OdpsType.VOID.equals(ot)) {
                value = null;
            } else {
                value = record.get(col.getName()) == null ? null : record.get(col.getName()).toString();
            }
            json.put(col.getName(), value);
        }
        return json;
    }

    public static <T> T json(Record record, Class<T> cls, Map<String, Field> fieldMap) {
        Field field = null;
        Map<String, Object> map = new HashMap<>();
        for (Column col : record.getColumns()) {
            field = fieldMap.get(col.getName());
            map.put(field.getName(), record.get(col.getName()));
        }
        T mapToObject = null;
        try {
            mapToObject = JsonUtil.mapToObject(map, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapToObject;

    }
}
