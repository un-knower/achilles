package com.quancheng.achilles.service.utils;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author zhouchaoqun
 * @Email zhouchaoqun@quancheng-ec.com
 * @Date 2016/12/15 10:24
 */
public class CoverageUtil {

    private final static String BAIDU_API_AK = "FDVkeLGlUts3xfH5jKY75TdQ1IN4NENo";
    private final static String GAODE_API_AK = "e34e8262e16d53b829f44b746b00370d";


    /**
     * 计算两组经纬度坐标 之间的距离， 默认单位为 米（m），保留两位小数
     * params ：lat1 纬度1； lng1 经度1； lat2 纬度2； lng2 经度2;
     * return m or km
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        return getDistance(lat1, lng1, lat2, lng2, 1, 2);
    }

    /**
     * 计算两组经纬度坐标 之间的距离
     * params ：lat1 纬度1； lng1 经度1； lat2 纬度2； lng2 经度2； lenType （1:m or 2:km); decimal 保留小数位数
     * return m or km
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2, int lenType, int decimal)
    {
        final double EARTH_RADIUS = 6378.137;   //地球半径
        double PI = 3.1415926;
        final double HALF_PI = PI / 180.0;

        double radLat1 = lat1 * HALF_PI;
        double radLat2 = lat2 * HALF_PI;

        double a = Math.sin((radLat1 - radLat2) * 0.5);
        a = a * a;
        double b = Math.sin((lng1 - lng2) * HALF_PI * 0.5);
        b = b * b;

        double s = 2 * Math.asin(Math.sqrt(a + Math.cos(radLat1) * Math.cos(radLat2) * b));
        s = s * EARTH_RADIUS * 1000.0;

        s = Math.round(s);

        if (lenType > 1) {
            s /= 1000;
        }

        BigDecimal bg = new BigDecimal(s).setScale(decimal, RoundingMode.UP);
        return bg.doubleValue();
    }


    /**
     * 将覆盖率数据写入excel。<strong>需要占用大量内存。</strong>
     * @param file 待写入文件名
     * @param coverageRatio [{distance,location1,location2}]
     * @throws IOException
     */
    public static void writeCoverageRatioToExcel(File file, List<Map<String, String>> coverageRatio) throws IOException {
        //创建工作文档对象
        Workbook wb = null;
        String fileType = file.getName().split("\\.")[1];
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook();
        }else if(fileType.equals("xlsx")){
            wb = new XSSFWorkbook();
        }else{
            System.out.println("您的文档格式不正确！");
        }
        //创建sheet对象
        Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
        //循环写入行数据
        for(int i=0; i<coverageRatio.size(); i++) {
            Map<String, String> locAndDistMap = coverageRatio.get(i);
            Row row = (Row) sheet1.createRow(i);
            if (i == 0) {
                //写入表头数据
                Cell cell0 = row.createCell(0);
                cell0.setCellValue("distance");
                Cell cell1 = row.createCell(1);
                cell1.setCellValue("location1");
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("location2");
            } else {
                // 写入表体数据
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(locAndDistMap.get("distance"));
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(locAndDistMap.get("location1"));
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(locAndDistMap.get("location2"));
            }
        }
        //创建文件流
        OutputStream stream = new FileOutputStream(file);
        //写入数据
        wb.write(stream);
        //关闭文件流
        stream.close();
    }


    /**
     * 将覆盖率数据写入txt。<br>
     * 若文件已存在，数据将<strong> 追加 </strong>写入到文件
     * @param file 待写入文件名
     * @param coverageRatio [{distance,location1,location2}]
     * @throws IOException
     */
    public static void writeCoverageRatioToTxt(File file, List<Map<String, String>> coverageRatio) throws IOException {
        String[] strs = file.getName().split("\\.");
        assert ("txt".equals(strs[strs.length - 1]));
        FileWriter fileWritter = new FileWriter(file, true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        int size = coverageRatio.size();
        for(int i=0; i<size; i++) {
            Map<String, String> coverageMap = coverageRatio.get(i);
            String lineStr = coverageMap.get("distance") + "\t" + coverageMap.get("location1") + "\t" + coverageMap.get("location2") + "\n";
            bufferWritter.write(lineStr);
        }
        bufferWritter.close();
        fileWritter.close();
    }

    /**
     * Excel数据转 Map列表
     * @param file
     * @return
     * @throws IOException
     */
    public static List<Map<String,String>> getAttributeMapsFromExcel(File file) throws IOException
    {
        List<Map<String,String>> hospitalList = new ArrayList<>();
        InputStream stream = new FileInputStream(file);
        Workbook wb = null;
        String fileType = file.getName().split("\\.")[1];
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        }else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        }else {
            System.out.println("您输入的excel格式不正确");
        }
        Sheet sheet1 = wb.getSheetAt(0);

        List<String> keys = new ArrayList<>();

        int length = sheet1.getRow(0).getPhysicalNumberOfCells();
        for (Row row : sheet1) {
            // 从表头首行获取 json 的 key
            if(row.getRowNum() == 0) {
                for(int col=0; col<length; col++){
                    Cell cell = row.getCell(col);
                    keys.add(col, getCellValue(cell));
                }
                continue;
            }
            // 获取数据，生成 key-value
            Map<String, String> map = new HashMap<>();
            for(int col=0; col<length; col++) {
                String key = keys.get(col);
                String value = getCellValue(row.getCell(col));
                map.put(key, value);
            }
            /*// 转成 json
            String hospitalJson = JSON.toJSONString(map);*/
            hospitalList.add(map);
        }

        wb.close();
        stream.close();
        return hospitalList;
    }

    /**
     * Excel数据转 Json 列表。从Excel首行获取 json 的 key，后面的行都是对应的 value。
     * @param file
     * @return
     * @throws IOException
     */
    public static List<String> getAttributeJsonsFromExcel(File file) throws IOException
    {
        List<String> hospitalList = new ArrayList<>();
        InputStream stream = new FileInputStream(file);
        Workbook wb = null;
        String fileType = file.getName().split("\\.")[1];
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        }else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        }else {
            System.out.println("您输入的excel格式不正确");
        }
        Sheet sheet1 = wb.getSheetAt(0);

        List<String> keys = new ArrayList<>();

        int length = sheet1.getRow(0).getPhysicalNumberOfCells();
        for (Row row : sheet1) {
            // 从表头首行获取 json 的 key
            if(row.getRowNum() == 0) {
                for(int col=0; col<length; col++){
                    Cell cell = row.getCell(col);
                    keys.add(col, getCellValue(cell));
                }
                continue;
            }
            // 获取数据，生成 key-value
            Map<String, String> map = new HashMap<>();
            for(int col=0; col<length; col++) {
                String key = keys.get(col);
                String value = getCellValue(row.getCell(col));
                map.put(key, value);
            }
            // 转成 json
            String hospitalJson = JSON.toJSONString(map);
            hospitalList.add(hospitalJson);
        }

        wb.close();
        stream.close();
        return hospitalList;
    }

    private static String getCellValue(Cell cell) {
        if(cell == null) {
            return "";
        }
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            double value = cell.getNumericCellValue();
            if(value - (int)value <= 0.00001) {
                return (int)cell.getNumericCellValue() + "";
            }
            return cell.getNumericCellValue() + "";
        }else {
            return cell.getStringCellValue();
        }
    }

    /**
     * 根据地址或者地名获取经纬度,当具体地址获取不到经纬度时，尝试通过地名获取。
     * @param map ：包括详细地址(address)、地名(name)和城市(city)
     * @return Map<String, BigDecimal> 形如 {lng=119.3947439664622, lat=26.00910836345558}
     */
    public static Map<String, Double> getLatAndLngByAddress(Map<String, String> map){
        Map<String, Double> location = null;
        String address = map.get("address");
        if(address != null && !"".equals(address)) {
            location = CoverageUtil.getLatAndLngByAddressFromGaode(map.get("address"), map.get("city"));
        }
        if(location == null) {
            String name = map.get("name");
            if(name != null && !"".equals(name)) {
                location = CoverageUtil.getLatAndLngByAddressFromGaode(name, map.get("city"));
            }
        }
        return location;
    }


    /**
     * 从百度API获取经纬度
     * @param addrOrName 地址或者地名
     * @param city 城市名
     * @return Map<String, Double> 形如 {lng=119.3947439664622, lat=26.00910836345558}
     */
    public static Map<String, Double> getLatAndLngByAddressFromBaidu(String addrOrName, String city){
        String lat = "";
        String lng = "";
        try {
            addrOrName = java.net.URLEncoder.encode(addrOrName,"UTF-8");
            city = java.net.URLEncoder.encode(city,"UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
                +"ak=%s&output=json&address=%s&city=%s",BAIDU_API_AK,addrOrName,city);
        URL myURL = null;
        URLConnection httpsConn = null;
        //进行转码
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {

        }
        try {
            httpsConn = myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    String status = data.substring(10, 11);
                    if(!"0".equals(status)) {
                        return null;
                    }
                    lat = data.substring(data.indexOf("\"lat\":")
                            + ("\"lat\":").length(), data.indexOf("},\"precise\""));
                    lng = data.substring(data.indexOf("\"lng\":")
                            + ("\"lng\":").length(), data.indexOf(",\"lat\""));
                }
                insr.close();
            }
        } catch (IOException e) {
        }
        Map<String, Double> map = new HashMap<>();
        //System.out.println(lat+", "+lng);
        if(lat.isEmpty() || lng.isEmpty()){
            return null;
        }
        map.put("lat", Double.parseDouble(lat));
        map.put("lng", Double.parseDouble(lng));
        return map;
    }

    /**
     * 从高德API获取经纬度
     * @param addrOrName 地址或者地名
     * @param city 城市名
     * @return Map<String, Double> 形如 {lng=119.3947439664622, lat=26.00910836345558}
     */
    public static Map<String, Double> getLatAndLngByAddressFromGaode(String addrOrName, String city){
        String lat = "";
        String lng = "";
        try {
            addrOrName = java.net.URLEncoder.encode(addrOrName,"UTF-8");
            city = java.net.URLEncoder.encode(city,"UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = String.format("http://restapi.amap.com/v3/geocode/geo?key=%s&output=json&address=%s&city=%s",GAODE_API_AK,addrOrName,city);
        URL myURL = null;
        URLConnection httpsConn = null;
        //进行转码
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {

        }
        try {
            httpsConn = myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    String regx = "\"location\":\"(.*[0-9])\"";
                    Pattern pattern = Pattern.compile(regx);
                    Matcher matcher = pattern.matcher(data);
                    if (matcher.find()) {
                        String loc = matcher.group(1);
                        String[] latAndLng = loc.split(",");
                        lng = latAndLng[0];
                        lat = latAndLng[1];
                    } else {
                        return null;
                    }
                }
                insr.close();
            }
        } catch (IOException e) {
        }
        Map<String, Double> map = new HashMap<>();
        //System.out.println(lat+", "+lng);
        if(lat.isEmpty() || lng.isEmpty()){
            return null;
        }
        map.put("lat", Double.parseDouble(lat));
        map.put("lng", Double.parseDouble(lng));
        return map;
    }

    /**
     * 计算覆盖率。获取两地相距小于【餐厅的配送范围】的医院与餐厅的映射， 如果餐厅未指定配送范围，<strong>默认值3公里</strong>
     * @param hospitalFile excel文件，记录的是<strong>医院</strong>地名与地址等信息
     * @param restaurantFile excel文件，记录的是<strong>餐厅</strong>地名与地址等信息
     * @return 相距小于指定限制距离的两个位置组成的映射列表, <br>
     *     每一个 map 的格式为:<br>{location1={"id":"5","name":"...","city":"...","address":"...","lng":"...","level":"...","lat":"..."}, location2={同location1...}, distance=0.0}
     * @throws IOException
     */
    public static List<Map<String, String>> getCoverageRatio(File hospitalFile, File restaurantFile) throws IOException {
        return getCoverageRatio(hospitalFile, restaurantFile, 3000);
    }

    /**
     * 计算覆盖率，获取两地相距小于指定距离的地方
     * @param file1 excel文件，记录的是地名与地址等信息
     * @param file2 excel文件，记录的是地名与地址等信息
     * @param limitDistance 指定的限制距离，单位:米(m)
     * @return 相距小于指定限制距离的两个位置组成的映射列表,<br>
     *     每一个 map 的格式为:<br>{location1={"id":"5","name":"...","city":"...","address":"...","lng":"...","level":"...","lat":"..."}, location2={同location1...}, distance=0.0}
     * @throws IOException
     */
    public static List<Map<String, String>> getCoverageRatio(File file1, File file2, int limitDistance) throws IOException {

        List<Map<String,String>> locations1 = CoverageUtil.getAttributeMapsFromExcel(file1);
        List<Map<String,String>> locations2 = CoverageUtil.getAttributeMapsFromExcel(file2);

        // 获取 locations1 所有地址对应的经纬度
        locations1 = appendLatAndLng(locations1);
        // 获取 locations2 所有地址对应的经纬度
        locations2 = appendLatAndLng(locations2);

        List<Map<String, String>> result = new ArrayList<>();
        long sum = 0;
        for(int i = 0; i< locations1.size(); i++) {
            if(locations1.get(i) == null) {
                continue;
            }
            List<Map<String, String>> ratio = getCoverageRatioOneToMany(limitDistance, locations1.get(i), locations2);
            if (ratio.size() == 0) {
                // 如果当前医院没有覆盖到任何餐厅，添加一个【医院：空】的映射
                Map<String, String> map = new HashMap<>();
                String location1 = JSON.toJSONString(locations1.get(i));
                map.put("location1",location1);
                map.put("location2","");
                map.put("distance","");
                sum++;
                result.add(map);
            } else {
                //System.out.println("i = "+i +", size = "+ ratio.size());
                sum += ratio.size();
                result.addAll(ratio);
            }
            locations1.set(i, null);
        }
        return result;
    }

    /**
     * 追加经纬度信息到医院或餐厅信息中。
     * @param locations
     * @return
     */
    public static List<Map<String, String>> appendLatAndLng(List<Map<String, String>> locations) {
        for(int i = 0; i< locations.size(); i++) {
            Map<String, String> locationMap = locations.get(i);
            if(locationMap == null) {
                continue;
            }
            String lat = locationMap.get("lat");
            String lng = locationMap.get("lng");
            if(lat == null || "".equals(lat) || lng == null || "".equals(lng) ) {
                Map<String, Double> latAndLng = CoverageUtil.getLatAndLngByAddress(locationMap);
                if(latAndLng == null) {
                    locations.set(i, null);
                    continue;
                }
                // 将经纬度信息追加到 Map
                locationMap.put("lat", latAndLng.get("lat")+"");
                locationMap.put("lng", latAndLng.get("lng")+"");
            }
        }
        return locations;
    }

    /**
     * 计算一家医院的餐厅覆盖信息。
     * @param limitDistance
     * @param hospitalInfo
     * @param restaurantList
     * @return
     */
    private static List<Map<String, String>> getCoverageRatioOneToMany(int limitDistance, Map<String, String> hospitalInfo, List<Map<String, String>> restaurantList) {
        List<Map<String, String>> result = new ArrayList<>();

        double lat1 = Double.parseDouble(hospitalInfo.get("lat"));
        double lng1 = Double.parseDouble(hospitalInfo.get("lng"));
        String location1 = JSON.toJSONString(hospitalInfo);
        int size = restaurantList.size();
        for(int j = 0; j < size; j++) {
            if(restaurantList.get(j) == null) {
                continue;
            }
            double lat2 = Double.parseDouble(restaurantList.get(j).get("lat"));
            double lng2 = Double.parseDouble(restaurantList.get(j).get("lng"));
            if (hospitalInfo.get("city").equals(restaurantList.get(j).get("city"))) {
                double distance = CoverageUtil.getDistance(lat1,lng1,lat2,lng2);
                if(restaurantList.get(j).get("shipping_dis") != null && !"".equals(restaurantList.get(j).get("shipping_dis"))) {
                    limitDistance = (int)Double.parseDouble(restaurantList.get(j).get("shipping_dis"));
                }
                if(distance < limitDistance) {
                    Map<String, String> map = new HashMap<>();
                    String location2 = JSON.toJSONString(restaurantList.get(j));
                    map.put("location1",location1);
                    map.put("location2",location2);
                    map.put("distance",distance+"");
                    result.add(map);
                }
            }
        }
        return result;
    }

    /**
     * 同时计算所有医院对餐厅的覆盖。
     * @param limitDistance
     * @param locations1
     * @param locations2
     * @return
     * @deprecated 太耗内存，不再使用
     */
    private static List<Map<String, String>> getCoverageRatioManyToMany(int limitDistance, List<Map<String, String>> locations1, List<Map<String, String>> locations2) {
        List<Map<String, String>> result = new ArrayList<>();

        for(int i = 0; i< locations1.size(); i++) {
            double lat1 = Double.parseDouble(locations1.get(i).get("lat"));
            double lng1 = Double.parseDouble(locations1.get(i).get("lng"));
            for(int j = 0; j< locations2.size(); j++) {
                double lat2 = Double.parseDouble(locations2.get(j).get("lat"));
                double lng2 = Double.parseDouble(locations2.get(j).get("lng"));
                if (locations1.get(i).get("city").equals(locations2.get(j).get("city"))) {
                    double distance = CoverageUtil.getDistance(lat1,lng1,lat2,lng2);
                    if(distance < limitDistance) {
                        Map<String, String> map = new HashMap<>();
                        String location1 = JSON.toJSONString(locations1.get(i));
                        String location2 = JSON.toJSONString(locations2.get(j));
                        map.put("location1",location1);
                        map.put("location2",location2);
                        map.put("distance",distance+"");
                        System.out.println("i = "+ i +", j = "+ j);
                        result.add(map);
                    }
                }
            }
        }
        return result;
    }
}

