package com.quancheng.achilles.util;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static ObjectMapper objectMapper;
    private static Logger      log = LoggerFactory.getLogger(JsonUtil.class);

    public static String toString(Object obj) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        String writeValueAsString = null;
        try {
            writeValueAsString = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(" Object to json String have a error {}", e);
        }
        return writeValueAsString;
    }

    public static <T> T mapToObject(Map<?, ?> map, Class<T> cls) {
        return jsonToObject(toString(map), cls);
    }

    public static <T> T jsonToObject(String json, Class<T> cls) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        T readValue = null;
        try {
            readValue = objectMapper.readValue(json, cls);
        } catch (IOException e) {
            log.error(" json  to  Object  have a error {}", e);
        }
        return readValue;
    }

}
