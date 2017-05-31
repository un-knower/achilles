package com.quancheng.achilles.service.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.quancheng.achilles.service.config.SpringBeanUtil;

/**
 * @ClassName: OssServiceUtil
 * @Description: 文件上传类
 * @author liujun
 * @date 2016年3月14日 上午9:50:21
 */
public class OssServiceUtil {
    private  final Logger log = LoggerFactory.getLogger(OssServiceUtil.class);
    private  String bucketName ;
    private  OSSClient client;
    private  String fileExpireTimeLong ;
    private  String fileExpireTimeUnit ;
    private  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private  String projectName = "inn-service";// 项目名
    /** 生成唯一码 */
    public  String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    public static OssServiceUtil getInstance(){
        return new OssServiceUtil();
    }
    
    private OssServiceUtil(){
        OssConfig cc = SpringBeanUtil.getBean(OssConfig.class);
        client = cc.createClient();
        bucketName=cc.bucketName;
        fileExpireTimeLong = cc.fileExpireTimeLong;
        fileExpireTimeUnit = cc.fileExpireTimeUnit;
    }
    /** 获取日期 */
    public String getTime() {
        return dateFormat.format(new Date());
    }

    /** 上传文件 */
    public  void uploadFile(String filePath) {
        try {
            File file = new File(filePath.trim());
            String fileName = file.getName();
            fileName = projectName + "/" + getTime() + "/" + getUUID() + "/" + fileName;
            client.putObject(new PutObjectRequest(bucketName, fileName, file));
        } catch (OSSException oe) {
            log.info("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason.erroeMessage is{}", oe);
            throw oe;
        } catch (ClientException ce) {
            log.info("Caught an ClientException, which means the client encountered a serious internal problem while trying to communicate with OSS,such as not being able to access the network.erroeMessage is{}", ce);
            throw ce;
        } finally {
            // client.shutdown();
        }
    }

    /**
     * 上传文件,返回url
     * 
     * @throws ParseException
     */
    public  String uploadFileAndGetUrl(String filePath) throws OSSException, ClientException, ParseException {
        File file = null;
        String fileName = null;
        PutObjectResult putObject = null;
        String url = null;
        try {
            file = new File(filePath.trim());
            fileName = file.getName();
            fileName = projectName + "/" + getTime() + "/" + getUUID() + "/" + fileName;
            putObject = client.putObject(new PutObjectRequest(bucketName, fileName, file));
        } catch (OSSException oe) {
            log.info("Caught an OSSException, which means your request made it to OSS, but was rejected with an error response for some reason.erroeMessage is{}", oe.getErrorCode());
            throw oe;
        } catch (ClientException ce) {
            log.info("Caught an ClientException, which means the client encountered a serious internal problem while trying to communicate with OSS,such as not being able to access the network.erroeMessage is{}", ce.getErrorCode());
            throw ce;
        } finally {
//            client.shutdown();
            file.delete();
        }
        if (putObject != null && putObject.getETag()!=null && !"".equals(putObject.getETag())) {
            url = getUrl(fileName);
        }
        return url;
    }

    /** 上传文件 */
    public  void uploadFile(String fileName, InputStream input) {
        try {
            fileName = projectName + "/" + getTime() + "/" + getUUID() + "/" + fileName;
            client.putObject(bucketName, fileName, input);
        } catch (OSSException oe) {
            log.info("CCaught an OSSException, which means your request made it to OSS,but was rejected with an error response for some reason. .erroeMessage is{}", oe);
            throw oe;
        } catch (ClientException ce) {
            log.info("Caught an ClientException, which means the client encountered a serious internal problem while trying to communicate with OSS,such as not being able to access the network.erroeMessage is{}", ce);
            throw ce;
        } finally {
            // client.shutdown();
        }
    }

    /**
     * 从oss中下载文件
     */
    public  byte[] downLoadFromOss(String key) throws IOException {
        byte[] data = null;
        try {
            OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
            // 得到输入流
            InputStream inputStream = object.getObjectContent();
            if (inputStream != null) {
                // 获取自己数组
                data = readInputStream(inputStream);
                inputStream.close();
            }
        } catch (IOException e) {
            log.info("downLoad from oss error. error is {}", e);
            throw e;
        }
        return data;
    }

    /**
     * 从网络Url中下载文件
     * 
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public  byte[] downLoadFromUrl(String urlStr) throws IOException {
        URL url = null;
        HttpURLConnection conn;
        byte[] data = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            // 获取自己数组
            data = readInputStream(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            log.info("downLoad from url error. error is {}", e);
            throw e;
        }
        return data;
    }

    /**
     * 从输入流中获取字节数组
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    public  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /** 删除文件 */
    public  void deleteFile(String key) {
        client.deleteObject(bucketName, key);
    }

    /** 获取所有文件列表 */
    public  Map<String, String[]> getAllFileList() {
        Map<String, String[]> map = new HashMap<String, String[]>();
        // 获取指定bucket下的所有Object信息
        ObjectListing listing = null;
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // List Objects
        listing = client.listObjects(listObjectsRequest);
        // 遍历所有Object
        String[] arr = new String[listing.getObjectSummaries().size()];
        int index = 0;
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            arr[index] = objectSummary.getKey();
            index++;
        }
        map.put("Objects", arr);
        index = 0;
        // 遍历所有CommonPrefix
        for (String commonPrefix : listing.getCommonPrefixes()) {
            arr[index] = commonPrefix;
            index++;
        }
        map.put("CommonPrefixs", arr);
        return map;
    }

    /**
     * 获取文件下载地址
     * 
     * @throws ParseException
     */
    public  String getUrl(String fileName) throws ParseException {
        if (fileExpireTimeLong == null) {
            fileExpireTimeLong = "1";
        }
        if (fileExpireTimeUnit == null) {
            fileExpireTimeUnit = "y";
        }
        int timeLong = Integer.parseInt(fileExpireTimeLong);
        Date expireTime = getTimeMove(new Date(), timeLong, fileExpireTimeUnit);
        return getUrl(fileName, expireTime);
    }

    /**
     * @Description: 计算时间 ，计算指定长度时间
     */
    public  Date getTimeMove(Date time, int timeLong, String timeUnit) {
        Date startTime = null;
        switch (timeUnit) {
            case "y":
                startTime = getTimeMove(time, timeLong, 0, 0, 0, 0, 0);
                break;
            case "M":
                startTime = getTimeMove(time, 0, timeLong, 0, 0, 0, 0);
                break;
            case "d":
                startTime = getTimeMove(time, 0, 0, timeLong, 0, 0, 0);
                break;
            case "h":
                startTime = getTimeMove(time, 0, 0, 0, timeLong, 0, 0);
                break;
            case "m":
                startTime = getTimeMove(time, 0, 0, 0, 0, timeLong, 0);
                break;
            case "s":
                startTime = getTimeMove(time, 0, 0, 0, 0, 0, timeLong);
                break;

            default:
                startTime = getTimeMove(time, 0, 0, timeLong, 0, 0, 0);
                break;
        }
        return startTime;
    }
    

    /**
     * @Description: 计算时间 时间推移计算
     */
    private  Date getTimeMove(Date time, int year, int month, int day, int hours, int minute, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, year);//
        calendar.add(Calendar.MONTH, month);//
        calendar.add(Calendar.DAY_OF_YEAR, day);//
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, seconds);
        Date date = calendar.getTime();
        return date;
    }
    /**
     * 获取文件下载地址
     */
    public  String getUrl(String key, Date expireTime) throws ParseException {
        // 服务器端生成url签名字串
        Date expiration = null;
        try {
            if (expireTime == null) {
                expiration = DateUtil.parseRfc822Date("Wed, 18 Mar 2116 14:20:00 GMT");
            } else {
                expiration = expireTime;
            }
        } catch (ParseException e) {
            throw e;
        }
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
        // 设置过期时间
        request.setExpiration(expiration);
        // 生成URL签名(HTTP GET请求)
        URL signedUrl = client.generatePresignedUrl(request);
        return signedUrl.toString();
    }

    /**
     * 解析url，获得文件名
     */
    public  String getNamefromUrl(String url) throws UnsupportedEncodingException {
        String pattern = ".com/.*?Expires";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(url);
        String source = null;
        while (m.find()) {
            source = m.group();
        }
        source = source.substring(5, source.length() - 8);
        try {
            source = URLDecoder.decode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.info("url decode error. error is{}", e);
            throw e;
        }
        return source;
    }

}