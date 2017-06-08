package com.quancheng.achilles.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quancheng.achilles.dao.model.QuanlityCheckRecord;
import com.quancheng.achilles.service.constants.ErrorKeys;
import com.quancheng.achilles.service.constants.FlyCheckErrorReason;

public class CheckReasonUtil {
    public static String convertReasonContent(String item){
        ObjectMapper om = new ObjectMapper();
        JsonNode jn = null;
        try {
            jn = om.readTree(item);
        } catch ( Exception e) {
        }
         if(jn  == null  ){
             return null;
        }
         if(jn.get("abnormal_content") != null){
            return jn.get("abnormal_content").asText();
         }
         String text = "";
         for (FlyCheckErrorReason fer : FlyCheckErrorReason.values()) {
             JsonNode obj = jn.get(fer.getKey());
             if(obj != null && fer.getErrorValue().equals(obj.asText())){
//                 text+=" "+fer.getText()+":";
                 for (ErrorKeys ek : fer.getErrorKeys()) {
                     if(jn.get(ek.getKey()) != null){
                         if(ek.getText() == null  ){
                             text+=" "+jn.get(ek.getKey()).asText();
                         }else if ( "1".equals(jn.get(ek.getKey()).asText())){
                             text+=" "+ek.getText();
                         }
                     }
                 }
             }
         }
         return text;
    }
    public static String getReasonType(String item){
        ObjectMapper om = new ObjectMapper();
        JsonNode jn = null;
        try {
            jn = om.readTree(item);
        } catch ( Exception e) {
        }
        if(jn == null ){
            return null;
        }
        if(jn.get("abnormal_type") != null){
            return( jn.get("abnormal_type").asText());
        }  
        String text = "";
        for (FlyCheckErrorReason fer : FlyCheckErrorReason.values()) {
            JsonNode obj = jn.get(fer.getKey());
            if(obj != null && fer.getErrorValue().equals(obj.asText())){
                text+=" "+fer.getText();
            }
        }
        return text;
    }
}
