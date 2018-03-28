package com.quancheng.achilles.util;

import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GouldApiUtil {
    private static final String GOULD_AK="5876f2203ba4b86a9d54f4bef6efd93c";
    /**
     * 从百度API获取经纬度
     * @param addrOrName 地址或者地名
     * @param city 城市名
     * @return Map<String, Double> 形如 {lng=119.3947439664622, lat=26.00910836345558}
     */
    public static JSONObject gouldByID(String gouldId){
        RestTemplate rt = new RestTemplate();
        String url = "http://restapi.amap.com/v3/place/detail?output=JSON&id="+gouldId+"&output=xml&key="+GOULD_AK;
        String resp = rt.getForObject(url, String.class);
        JSONObject json = JSONObject.parseObject(resp);
        if(json.getJSONArray("pois")!= null && !json.getJSONArray("pois").isEmpty()){
            return json.getJSONArray("pois").getJSONObject(0);
        }
        return null;
    }
    
    /**
     * 从百度API获取经纬度
     * @param addrOrName 地址或者地名
     * @param city 城市名
     * @return Map<String, Double> 形如 {lng=119.3947439664622, lat=26.00910836345558}
     */
    public static JSONObject gould(String addrOrName, String city){
        RestTemplate rt = new RestTemplate();
        String url = "http://restapi.amap.com/v3/place/text?keywords="
                + (addrOrName.contains(city)?addrOrName:city+addrOrName)
//                + "&types=餐饮,餐厅,酒店,住宿服务"
                + "&city="
                +(city==null?"":city)
                + "&children=1&offset=20&page=1&extensions=all&key="+GOULD_AK;
        String resp = rt.getForObject(url, String.class);
        JSONObject json = JSONObject.parseObject(resp);
        json = json.getJSONArray("pois")!=null&& !json.getJSONArray("pois").isEmpty()?json:JSONObject.parseObject(rt.getForObject( "http://restapi.amap.com/v3/place/text?keywords="
                + (addrOrName)
              + "&city="
              +(city==null?"":city)
              + "&children=1&offset=20&page=1&extensions=all&key="+GOULD_AK, String.class));
        if(json.getJSONArray("pois")!= null && !json.getJSONArray("pois").isEmpty()){
            JSONArray jsonArray = json.getJSONArray("pois");
            for (int i=0 ; i<jsonArray.size();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String gaoldName = jsonObject.getString("name");
                if(gaoldName.equals(addrOrName)||addrOrName.indexOf(gaoldName)!=-1||gaoldName.indexOf(addrOrName)!=-1){
                    return jsonObject;
                }
            }
            return jsonArray.getJSONObject(0);
        }
        return null;
    }
}
