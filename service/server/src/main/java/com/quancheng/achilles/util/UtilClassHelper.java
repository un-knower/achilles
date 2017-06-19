package com.quancheng.achilles.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by wangjun on 2017/6/1.
 */
public class UtilClassHelper {

    private static final String BAIDU_API_AK = "so17LnMwpaTC06kXfpHLmIGb";

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static Map JsonToMap(String json) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = new HashMap<String, Object>();

        // convert JSON string to Map
        map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });

        return map;

    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            double value = cell.getNumericCellValue();
            if (value - (int) value <= 0.00001) {
                return (int) cell.getNumericCellValue() + "";
            }
            return cell.getNumericCellValue() + "";
        } else {
            return cell.getStringCellValue();
        }
    }

    /**
     * Excel数据转 Map列表
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> getAttributeMapsFromExcel(MultipartFile file) throws IOException {
        return getAttributeMapsFromExcel(file.getOriginalFilename(), file.getInputStream());
    }

    /**
     * Excel数据转 Map列表
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static List<Map<String, String>> getAttributeMapsFromExcel(File file) throws IOException {
        InputStream stream = new FileInputStream(file);
        return getAttributeMapsFromExcel(file.getName(), stream);
    }

    /**
     * Excel数据转 Map列表
     */
    public static List<Map<String, String>> getAttributeMapsFromExcel(String fileName,
                                                                      InputStream stream) throws IOException {
        List<Map<String, String>> hospitalList = new ArrayList<>();
        Workbook wb = null;
        String fileType = fileName.split("\\.")[1];
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        } else {
            System.out.println("您输入的excel格式不正确");
        }
        Sheet sheet1 = wb.getSheetAt(0);

        List<String> keys = new ArrayList<>();

        int length = sheet1.getRow(0).getPhysicalNumberOfCells();
        for (Row row : sheet1) {
            // 从表头首行获取 json 的 key
            if (row.getRowNum() == 0) {
                for (int col = 0; col < length; col++) {
                    Cell cell = row.getCell(col);
                    keys.add(col, getCellValue(cell));
                }
                continue;
            }
            // 获取数据，生成 key-value
            Map<String, String> map = new HashMap<>();
            for (int col = 0; col < length; col++) {
                String key = keys.get(col);
                String value = getCellValue(row.getCell(col));
                map.put(key, value);
            }
            /*
             * // 转成 json String hospitalJson = JSON.toJSONString(map);
             */
            hospitalList.add(map);
        }

        wb.close();
        stream.close();
        return hospitalList;
    }

    /**
     * 根据地址或者地名获取经纬度,当具体地址获取不到经纬度时，尝试通过地名获取。
     * 
     * @param map ：包括详细地址(address)、地名(name)和城市(cityName)
     * @return Map<String, BigDecimal> 形如 {lng=119.3947439664622, lat=26.00910836345558}
     */
    public static Map<String, Double> getLatAndLngByAddressFromBaidu(Map<String, String> map) {
        Map<String, Double> location = null;
        String address = map.get("address");
        if (address != null && !"".equals(address)) {
            location = getLatAndLngByAddressFromBaidu(map.get("address"), map.get("cityName"));
        }
        if (location == null) {
            String name = map.get("name");
            if (name != null && !"".equals(name)) {
                location = getLatAndLngByAddressFromBaidu(name, map.get("cityName"));
            }
        }
        return location;
    }

    /**
     * 从百度API获取经纬度
     * 
     * @param addrOrName 地址或者地名
     * @param city 城市名
     * @return Map<String, Double> 形如 {lng=119.3947439664622, lat=26.00910836345558}
     */
    public static Map<String, Double> getLatAndLngByAddressFromBaidu(String addrOrName, String city) {
        String lat = "";
        String lng = "";
        try {
            addrOrName = java.net.URLEncoder.encode(addrOrName, "UTF-8");
            city = java.net.URLEncoder.encode(city, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?" + "ak=%s&output=json&address=%s&city=%s",
                                   BAIDU_API_AK, addrOrName, city);
        URL myURL = null;
        URLConnection httpsConn = null;
        // 进行转码
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {

        }
        try {
            httpsConn = myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    String status = data.substring(10, 11);
                    if (!"0".equals(status)) {
                        return new HashMap<>();
                    }
                    lat = data.substring(data.indexOf("\"lat\":") + ("\"lat\":").length(),
                                         data.indexOf("},\"precise\""));
                    lng = data.substring(data.indexOf("\"lng\":") + ("\"lng\":").length(), data.indexOf(",\"lat\""));
                }
                insr.close();
            }
        } catch (IOException e) {
        }
        Map<String, Double> map = new HashMap<>();
        // System.out.println(lat+", "+lng);
        if (lat.isEmpty() || lng.isEmpty()) {
            return null;
        }
        map.put("lat", Double.parseDouble(lat));
        map.put("lng", Double.parseDouble(lng));
        return map;
    }

    /**
     * 计算两组经纬度坐标 之间的距离 params ：lat1 纬度1； lng1 经度1； lat2 纬度2； lng2 经度2； lenType （1:m or 2:km); decimal 保留小数位数 return m or
     * km
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2, int lenType, int decimal) {
        final double EARTH_RADIUS = 6378.137; // 地球半径
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
}
