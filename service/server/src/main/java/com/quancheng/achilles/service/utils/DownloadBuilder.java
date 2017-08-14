package com.quancheng.achilles.service.utils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import com.quancheng.achilles.service.config.SpringBeanUtil;
import com.quancheng.achilles.service.services.OssFileInfoService;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.modelwrite.OssFileInfo;
import io.swagger.annotations.ApiModelProperty;

public class DownloadBuilder<T> {
    SXSSFWorkbook wb;
    SXSSFSheet sheet;
    private String fileType = ".xlsx";
    private int rownum = 0;
    FileOutputStream out;
    File file = null;
    Class<T> cls;
    public static final String EXCEL_PATH = "./export-excel/";
    /** 转换参数 */
    Map<String, Map<Object, Object>> convert = new HashMap<>();

    List<AchillesDiyTemplateColumns> tempcols;
    boolean isInit = false ;
    /**
     * 
     * @param clazz
     * @param strings 文件前缀
     */
    public DownloadBuilder(Class<T> clazz  ) {
        this.cls = clazz;
        init();
    }
    
    private void init(){
        file = new File( EXCEL_PATH + DateUtils.getToday() + "_" + UUID.randomUUID() + fileType);
        if (Files.notExists(Paths.get(file.getParent()))) {
            try {
                Files.createDirectory(Paths.get(file.getParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out = new FileOutputStream(file, true);
            wb = new SXSSFWorkbook(100);
            sheet = wb.createSheet();
            this.createHeader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public DownloadBuilder(Class<T> clazz, Map<String, Map<Object, Object>> convert) {
        this(clazz);
        this.convert = convert;
    }
    
    public DownloadBuilder(Class<T> clazz, Map<String, Map<Object, Object>> convert, List<AchillesDiyTemplateColumns> tempcols ) {
        this.convert = convert;
        this.tempcols=tempcols;
        this.cls=clazz;
        init();        
    }
    /**
     * normal download <br>
     * if the response time out ,use {@link #saveOnServer()} instead
     * @param response
     * @throws IOException
     */
    public void startDownload(HttpServletResponse response) throws IOException {
        try {
            wb.write(out);
            wb.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.delete();
        }
    }
    /**
     * save excel on server
     * @return file path stored on web server
     */
    public String saveOnServer(){
        try {
            wb.write(out);
            wb.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }
    
    /**
     * return file path 
     * @return
     * @throws Exception 
     */
    public String ossUpload(String userName,EnumDownLoadModel model) throws Exception{
        try {
            wb.write(out);
            wb.close();
            out.close();
            String url = OssServiceUtil.getInstance().uploadFileAndGetUrl(file.getPath());
            SpringBeanUtil.getBean(OssFileInfoService.class).save(
                    new OssFileInfo(userName,
                                                url,
                                                model.MODEL_DISPLAY,
                                                OssFileInfo.OSS_STATUS_SUCCESS ));
            return url;
        } catch (Exception e) {
            String msg = e.getMessage();
            SpringBeanUtil.getBean(OssFileInfoService.class).save(
                    new OssFileInfo( userName,
                                                (msg!=null && msg.length()>500 ?msg.substring(0, 500):msg),
                                                OssFileInfo.OSS_STATUS_ERROR,
                                                model.MODEL_DISPLAY) );
            throw e;
        }
    }
    
    
    /**
     * bean field use <link>io.swagger.annotations.ApiModelProperty<link>
     */
    private List<Field> fieldList;

    /**
     * 构建表头
     */
    Map<String , AchillesDiyTemplateColumns> columns ;
    private void createHeader() {

        SXSSFRow headrow = sheet.createRow(getRow());
        SXSSFCell cell = null;
        int currColumn = 0;
        Field[] fields = cls.getDeclaredFields();
        String title = null;
        Field field = null;
        fieldList = new ArrayList<>(fields.length);
        if(tempcols != null && !tempcols.isEmpty() ){
            columns = new HashMap<>(tempcols.size());
            for (AchillesDiyTemplateColumns adtc : tempcols) {
                columns.put(adtc.getTableCol(), adtc);
            }
        } 
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            if(columns != null){
                if(columns.containsKey(field.getName())){
                    cell = headrow.createCell(currColumn);
                    title = columns.get(field.getName()).getColTitile();
                }
            }else {
                if (field.isAnnotationPresent(ApiModelProperty.class) ) {
                    cell = headrow.createCell(currColumn);
                    title = field.getAnnotation(ApiModelProperty.class).value();
                }
            }
            if(cell != null){
                cell.setCellValue(title);
                field.setAccessible(true);
                fieldList.add(field);
                currColumn++;
            }
            cell = null;
        }
    }

    private int getRow() {
        int row = rownum;
        rownum++;
        return row;
    }

    public void append(List<? extends Object> datas) {
        if (datas == null || datas.isEmpty()) {
            return;
        }
        SXSSFRow row = null;
        Field field = null;
        Object fieldValue = null;
        PropertyDescriptor pd = null;
        Method readMethod = null;
        for (Object object : datas) {
            row = sheet.createRow(getRow());
            if(object==null){
            		continue;
            }
            for (int i = 0; i < fieldList.size(); i++) {
                try {
                    field = fieldList.get(i);
                    pd = new PropertyDescriptor(field.getName(), object.getClass());
                    // get field value through getter method if it exists
                    readMethod = pd.getReadMethod();
                    if (readMethod != null) {
                        fieldValue = readMethod.invoke(object);
                    } else {
                        fieldValue = field.get(object);
                    }
                    /** 数据转换 */
                    if (convert != null&& convert.containsKey(field.getName()) && fieldValue != null) {
                    		Object convertobj=convert.get(field.getName()).get(fieldValue);
                    		if(convertobj !=null){
                    			setValue(row.createCell(i), convertobj);
                    		}else{
                    			setValue(row.createCell(i), fieldValue);
                    		}
                    } else {
                        setValue(row.createCell(i), fieldValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        datas = null;
    }

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void setValue(SXSSFCell cell, Object value) {
        String empty = null;
        if (value == null) {
            cell.setCellValue(empty);
        } else if (value instanceof Number) {
            cell.setCellValue(Double.parseDouble(value.toString()));
        } else if (value instanceof Date) {
            cell.setCellValue(df.format((Date) value));
        } else {
            cell.setCellValue(value.toString().isEmpty()?empty:value.toString());
        }
    }
}
