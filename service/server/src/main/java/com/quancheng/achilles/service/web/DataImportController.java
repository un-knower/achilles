package com.quancheng.achilles.service.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.dao.ds_st.model.DorisTableColumns;
import com.quancheng.achilles.dao.ds_st.model.DorisTableInfo;
import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.services.DataImportService;
import com.quancheng.achilles.service.services.impl.DataItemServiceImpl;
import com.quancheng.achilles.service.services.impl.DorisTableServiceImpl;
//import com.quancheng.starter.log.QcLoggable;
import com.quancheng.starter.log.QcLoggable;

import io.swagger.annotations.ApiParam;
/***
 * 数据模版-
 *          a模版-
 *              a1-数据集
 *              a2-数据集
 *            b模版-
 *              b1-数据集
 *              b2-数据集   
 * 数据导入功能
 * @author zhuzhong
 */
@RequestMapping("/data")
@RestController
public class DataImportController {
    @Autowired
    DataImportService dataImportService;
    
    @Autowired
    DorisTableServiceImpl dorisTableServiceImpl;
    
    @Autowired
    DataItemServiceImpl dataItemServiceImpl;
    
    private static volatile String status="free";
    
    String dir = "upload/";
    /***
     * 模版 列表
     * @param mv
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(path = "/model/list", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, 
            MediaType.TEXT_HTML_VALUE })
    @ResponseBody
    public ModelAndView modelList(ModelAndView mv,
             @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
             @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) 
                int pageSize) {
        mv.setViewName("import_data__file/template_list");
        return mv;
    }
    /***
     * 模版编辑
     * @param mv
     * @return
     */
    @RequestMapping(path = "/model/edit", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, 
            MediaType.TEXT_HTML_VALUE })
    @ResponseBody
    public void modelEdit(HttpServletRequest request,ModelAndView mv,  Long dorisTableId) {
        Map<String,String[]> param = request.getParameterMap();
        dorisTableServiceImpl.modifyColumn(dorisTableId,param);
    }
    @RequestMapping(path = "/model/page", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE},  method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView importpage(ModelAndView mv ,String templateId) {
        mv.addObject("tableList", dorisTableServiceImpl.queryDataImport());
        mv.addObject("status", status);
        mv.addObject("clientList", dataItemServiceImpl.getDataItemDetail("ALL_CLIENT_LIST"));
        mv.setViewName("import_data__file/file_upload");
        return mv;
    }
    
    @RequestMapping(path = "/model/import",  method = RequestMethod.POST)
    @ResponseBody
    public void importOption(  @ApiParam(value = "导入Excel的文件") @RequestParam(value = "file", required = false) MultipartFile file, 
            @ApiParam(value = "模版id") @RequestParam(value = "dorisTableId", required = false) Long dorisTableId) {
        if("free".equals(status) && file!=null){
            status="busy";
            File nf;
            try {
                File dir = new File("upload/");
                if(!dir.exists()) {
                    dir.mkdir();
                }
                nf=file==null?null:new File(dir.getAbsolutePath()+"/"+file.getOriginalFilename());
                if(file!=null && file.getOriginalFilename()!=null && !file.getOriginalFilename().isEmpty()) {
                    file.transferTo(nf);
                    File oldfile =new File(file.getOriginalFilename());
                    if( oldfile.exists()) {
                        oldfile.delete();
                    }
                }
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            dataImportService.doImport(nf, dorisTableId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally{
                            status="free";
                        }
                    }
                }).start();   
            } catch (IllegalStateException | IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    @QcLoggable(QcLoggable.Type.NONE)
    @RequestMapping(path = "/model/status" ,produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },method=RequestMethod.GET)
    public String request( ){
        return status;
    }
    
    @RequestMapping(path = "/model/view",  method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView dataView(HttpServletRequest request,ModelAndView mv,  Long dorisTableId) {
        mv.setViewName("import_data__file/data_preview");
        if(dorisTableId==null){
            return mv;
        }
        Map<String,String[]> param = request.getParameterMap();
        mv.addObject("paramaterValues", param);
        List<DorisTableColumns> cols = dorisTableServiceImpl.getTableColumns(dorisTableId);
        DorisTableInfo table = dorisTableServiceImpl.dorisTableInfo(dorisTableId);
        mv.addObject("templateColumnKeys", cols);
        mv.addObject("page", dataImportService.dataView(param, cols,table));
        mv.addObject("itemList", dataItemServiceImpl.getDataItem(new Object[]{}));
        mv.addObject("dorisTableId", dorisTableId);
        return mv;
    }
}
