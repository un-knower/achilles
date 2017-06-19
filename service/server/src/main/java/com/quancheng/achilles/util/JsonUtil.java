package com.quancheng.achilles.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.quancheng.achilles.dao.odps.model.HospitalInfo;

public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();
    private static Gson         gson   = new Gson();

    /** 对象转json */
    public static String objectToJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static <T> T jsonToObject(String json, Class<T> object) throws IOException {
        return mapper.readValue(json, object);
    }

    public static String objectToJsonByGson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T jsonToObjectByGson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T jsonToObjectBy(String json, Class<T> clazz) throws InstantiationException,
                                                                    IllegalAccessException, IllegalArgumentException,
                                                                    InvocationTargetException {
        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
        T t = clazz.newInstance();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("set")) {
                String substring = name.substring(3, 4).toLowerCase() + name.substring(4);
                String val = map.get(substring);

                if (!StringUtils.isEmpty(val)) {
                    Object value = null;
                    Class<?> parameterType = method.getParameterTypes()[0];
                    if (parameterType.isAssignableFrom(Double.class)) {
                        value = Double.parseDouble(val);
                    } else if (parameterType.isAssignableFrom(Integer.class)) {
                        value = Integer.parseInt(val);
                    } else if (parameterType.isAssignableFrom(Long.class)) {
                        value = Long.parseLong(val);
                    } else {
                        value = val;
                    }
                    method.invoke(t, value);
                }
            }
        }
        return t;
    }

    public static <T> List<T> jsonToListByGson(String json, Class<T[]> clazz) {
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = gson.fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
                                           IllegalArgumentException, InvocationTargetException,
                                           JsonProcessingException {
        String json = "{\"省份\": \"广东省\",\"address\": \"深圳市龙岗区布吉布澜路29号（联创科技园、李朗国际珠宝产业园旁）\",\"hospatalId\":\"8002705\",\"cityName\": \"深圳\",\"lng\": \"114.13560545153777\",\"hospatalName\": \"广东省深圳市东湖医院\",\"lat\":\"22.641950749266943\",\"区县\": \"罗湖区\"}";

        HospitalInfo jsonToObjectBy = JsonUtil.jsonToObjectBy(json, HospitalInfo.class);
        System.err.println(JsonUtil.objectToJson(jsonToObjectBy));
    }
}
