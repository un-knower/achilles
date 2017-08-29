package com.quancheng.achilles.service.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
public class DorisDataServiceImpl {
    
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    
    public String handleSqlParam(String sql, Map<String, Object[]> params) {
        String regex = "(?<=#\\{)[^\\}]+";
        sql = handelIf(sql, params);
        sql = replaceParams(sql, params);

        if (sql.contains("#{") | sql.contains("${")) {
            regex = "<if[^>]*>([^/]*)</if( )*>";
            sql = findAndReplace(regex, sql, "", "#");
            sql = findAndReplace(regex, sql, "", "$");
        }
        regex = "(<if[^>]*>|</if( )*>)";
        sql = findAndReplace(regex, sql, "", null);
        regex = "(?=#|\\$\\{)[^\\}]+\\}";
        sql = findAndReplace(regex, sql, "", null);
        return sql;
    }

    private String findAndReplace(String regex, String str, String replace, String mustContain) {
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            if (!StringUtils.isEmpty(mustContain)) {
                if (group.contains(mustContain) && replace != null) {
                    str = str.replace(group, replace);
                }
            } else {
                if (replace != null) {
                    str = str.replace(group, replace);
                }
            }
        }
        return str;
    }
    public static String replaceParams(String sql, Map<String, Object[]> params) {
        if (params != null) {
            for (Map.Entry<String, Object[]> m : params.entrySet()) {
                String key = m.getKey();
                String value = getFromMap(m.getValue(), true);
                if (!StringUtils.isEmpty(value)) {
                    sql = sql.replaceAll("#\\{" + key + "\\}", value);
                }
            }
            for (Map.Entry<String, Object[]> m : params.entrySet()) {
                String key = m.getKey();
                String value = getFromMap(m.getValue(), false);
                if (!StringUtils.isEmpty(value)) {
                    sql = sql.replaceAll("\\$\\{" + key + "\\}", value);
                }
            }
        }
        return sql;
    } 

    public static List<String> getAllTestKey(String testStr) {
        List<String> list = new ArrayList<>();
        Pattern compile = Pattern.compile("(\\d+.\\d+|\\w+)");
        Matcher matcher = compile.matcher(testStr);
        int i = 0;
        while (matcher.find()) {
            String group = matcher.group();
            if (i % 2 == 0) {
                list.add(group);
            }
            i++;
        }
        return list;
    }

    public   Boolean checkTestStr(String testStr, Map<String, Object[]> params) {
        Object result = false;
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            List<String> allTestKey = getAllTestKey(testStr);
            for (String k : allTestKey) {
                Object object = null;
                if (params != null) {
                    object = params.get(k);
                }
                engine.put(k, object);
            }
            result = engine.eval(testStr);
        } catch (Exception e) {
            LOG.debug("checkTestStr have a error ", e);
        }
        return (Boolean) result;
    }

    public   String handelIf(String sql, Map<String, Object[]> params) {
        Pattern compile = Pattern.compile("<if[^>]*>([^/]*)</if( )*>");
        Matcher matcher = compile.matcher(sql);
        while (matcher.find()) {
            String group = matcher.group();
            String ifStr = group.substring(0, group.indexOf(">") + 1);
            if (ifStr.contains("test")) {
                String testStr = ifStr.substring(ifStr.indexOf("'") + 1, ifStr.lastIndexOf("'"));
                testStr = testStr.replaceAll("( )*(and)( )*", "&&").replaceAll("( )*(or)( )*", "||");

                Boolean checkTestStr = checkTestStr(testStr, params);
                if (checkTestStr) {
                    String gro = replaceParams(group, params);
                    sql = sql.replace(group, gro);
                } else {
                    sql = sql.replace(group, "");
                }
            }
        }
        return sql;
    }
    public static String getFromMap(Object[] value, boolean isAdd) {
        if (value == null) {
            return null;
        }
        String resp = value.toString();
        if (isAdd && !StringUtils.isEmpty(resp)) {
            resp = "'" + resp + "'";
        }
        if ( value instanceof Object[]) {
            StringBuilder sb = new StringBuilder();
            for (Object s : value) {
                sb.append("'").append(s.toString()).append("',");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            resp = sb.toString();
        }
        return resp;
    }
}
