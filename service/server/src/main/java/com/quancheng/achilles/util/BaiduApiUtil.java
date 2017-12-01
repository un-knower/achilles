package com.quancheng.achilles.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quancheng.achilles.util.model.PoiInfo;

public class BaiduApiUtil {
    private static  Logger logger = LogManager.getLogger(BaiduApiUtil.class);
    private static final String BAIDU_API_AK = "so17LnMwpaTC06kXfpHLmIGb";
    private static String baiduPoi="http://api.map.baidu.com/geocoder/v2/?location=%s&output=json&pois=1&ak=%s";
    private static String baiduLngLat="http://api.map.baidu.com/geocoder/v2/?ak=%s&output=json&address=%s&city=%s";
    public static JSONObject getPoiInfoFromBaidu(String addressOrName, String city, String careKeys) {
        String url = String.format(baiduLngLat,
                BAIDU_API_AK, city!=null && addressOrName!=null && addressOrName.indexOf(city) == -1 ? city + addressOrName : addressOrName, city);
        RestTemplate rest = new RestTemplate();
        JSONObject location = null;
        String str =null;
        try {
            String resp = rest.getForObject(url, String.class);
            if (resp == null || careKeys == null) {
                return null;
            }
            // IFNULL=result.location
            JSONObject json = JSON.parseObject(resp);
            String[] jsonKeys = careKeys.trim().split("\\.");
            Object obj =null;
            for (int i = 0; i < jsonKeys.length; i++) {
                obj = get(json, jsonKeys[i]);
                if (i == jsonKeys.length-1 && obj!=null) {
                    location= (JSONObject)obj ;
                } else if (i < jsonKeys.length) {
                    if (obj instanceof JSONObject) {
                        json = (JSONObject) obj;
                    } else if (obj instanceof JSONArray) {
                        json = ((JSONArray) obj).getJSONObject(0);
                    }
                }
            }
            if(location== null  ){
                return null;
            }
            url = String.format(baiduPoi, location.getDouble("lat")+","+location.getDouble("lng"), BAIDU_API_AK);
            str = rest.getForObject(url, String.class);
            JSONObject result = JSON.parseObject(str).getJSONObject(jsonKeys[0]);
            if(result==null || result.getJSONObject(jsonKeys[0])== null ){
                result = result==null?new JSONObject():result;
                result.put(jsonKeys[1], location);
            } 
            return result;
        } catch (RestClientException e) {
            return null;
        }
    }

    static Object get(JSONObject json, String key) {
        Object obj = json.get(key);
        return obj;
    }
    //
    public static PoiInfo getHospitalPoiInfoFromBaiduToBean(String addressOrName,String keyWord, String city, String careKeys){
        PoiInfo pi = new PoiInfo();
        JSONObject json = null;
        try {
            json = getPoiInfoFromBaidu(addressOrName, city, careKeys);
        } catch (Exception e) {
            logger.error(e );
            return null;
        }
        if(json == null ){
            return null;
        }
        JSONArray pois = json.getJSONArray("pois");
        pi.setAddress(json.getString("formatted_address"));
        JSONObject jsonObject=null;
        for (int i=0 ;i<pois.size();i++) {
            jsonObject =  pois.getJSONObject(i);
            if(keyWord.equals(jsonObject.getString("name")) 
                  || keyWord.contains(jsonObject.getString("name"))
                  || jsonObject.getString("name").contains(keyWord)){
                jsonObject =  pois.getJSONObject(0);
                
                pi.setName(jsonObject.getString("name"));
                if( json.getJSONObject("addressComponent") != null ){
                    pi.setArea(json.getJSONObject("addressComponent").getString("district"));
                    pi.setCity(json.getJSONObject("addressComponent").getString("city").replace("市", ""));
                    pi.setProvince(json.getJSONObject("addressComponent").getString("province").replace("省", "").replace("市", ""));
                }
            }
            if( json.getJSONObject("location") != null ){
                JSONObject locations = json.getJSONObject("location") ;
                pi.setLng(locations.getDouble("lng")+"");
                pi.setLat(locations.getDouble("lat")+"");
            }
        }
        return pi;
    }
}
