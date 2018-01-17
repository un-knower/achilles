package com.quancheng.achilles.service.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.quancheng.achilles.dao.ds_qc.model.BaseResponse;
import com.quancheng.achilles.dao.ds_st.model.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.ds_st.model.DorisTableInfo;
import com.quancheng.achilles.service.model.ChartDataResp;
import com.quancheng.achilles.service.model.DorisTableTO;
import com.quancheng.achilles.service.model.PageInfo;
import com.quancheng.achilles.service.services.impl.AchillesDiyColumnsServiceImpl;
import com.quancheng.achilles.service.services.impl.DorisTableServiceImpl;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;

@Controller
@RequestMapping(path = "/ops/template")
public class TemplateController {
    
    @Autowired
    AchillesDiyColumnsServiceImpl achillesDiyColumnsServiceImpl;
    @Autowired
    DorisTableServiceImpl dorisTableServiceImpl;
    
    @RequestMapping(path="index",method = {RequestMethod.POST,RequestMethod.GET} )
    public ModelAndView list(HttpServletRequest request, ModelAndView mv)   {
        Map<String,String[]> param = request.getParameterMap();
        mv.addObject("paramaterValues", param);
        if(!param.containsKey("templateId")){
            mv.setViewName("redirect:/ops");
            return mv;
        }
        Long templateId=Long.parseLong(param.get("templateId")[0].toString());
        DorisTableTO dtt = dorisTableServiceImpl.query(templateId);
        List<AchillesDiyTemplateColumns> cols = achillesDiyColumnsServiceImpl.getTemplateColsByTemplate(templateId);
        ChartDataResp cdr = null;
        String[] pageSize = param.get("pageSize");
        String[] pageNum = param.get("pageNum");
        Long ps = Long.parseLong(pageSize== null?"50":pageSize[0]);
        Long pn = Long.parseLong(pageNum== null?"0":pageNum[0]);
        DorisTableInfo dti = dorisTableServiceImpl.dorisTableInfo(dtt.getTemplate().getTemplateConfigId());
        try {
            cdr = dorisTableServiceImpl.getData(param,cols, dtt.getTemplate(),new PageInfo(pn,ps),dti);
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("message", e.getMessage());
            mv.setViewName("error/500");
            return mv;
        }
        mv.addObject("paramaterConfigs",dorisTableServiceImpl.configParamater(dtt.getParams()));
        cdr.setTemplateId(templateId);
        mv.addObject("templateColumnKeys", convert(cols));
        mv.addObject("page", cdr);
        mv.addObject("template",dtt.getTemplate());
        mv.setViewName("template");
        return mv;
    }
    
    private List<AchillesDiyTemplateColumns> convert(List<AchillesDiyTemplateColumns> cols){
        if(cols==null){
            return new ArrayList<>();
        }
        for (AchillesDiyTemplateColumns achillesDiyTemplateColumns : cols) {
            if(achillesDiyTemplateColumns.getTableCol().trim().matches("^[\u4e00-\u9fa5\\'\"a-z\\.A-Z_\\s]+$")){
                String[] arr = achillesDiyTemplateColumns.getTableCol().trim().split("\\s");
                achillesDiyTemplateColumns.setTableCol(arr[arr.length-1].indexOf(".")!=-1?arr[arr.length-1].substring(arr[arr.length-1].lastIndexOf(".")+1):arr[arr.length-1]);
            } 
        }
        return cols;
    }
    
    @Autowired
    OssServiceDBUtil ossServiceDBUtil;
    @RequestMapping(path="export",method = { RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public BaseResponse export(HttpServletRequest request)   {
        final Map<String,String[]> param = request.getParameterMap();
        class AsyncUploadToOSS implements Runnable {
            private String username;
            public AsyncUploadToOSS( String username) {
                this.username = username;
            }
            @Override
            public void run() {
                ChartDataResp cdr =null;
                Long templateId=Long.parseLong(param.get("templateId")[0].toString());
                DorisTableTO dtt = dorisTableServiceImpl.query(templateId);
                List<AchillesDiyTemplateColumns> cols = achillesDiyColumnsServiceImpl.getTemplateColsByTemplate(templateId);
                List<AchillesDiyTemplateColumns> colsC = achillesDiyColumnsServiceImpl.getTemplateColsByTemplate(templateId);
                DorisTableInfo dti = dorisTableServiceImpl.dorisTableInfo(dtt.getTemplate().getTemplateConfigId());
                Long ps = 5000L;
                Long pn = 0L;
                try {
                    cdr = dorisTableServiceImpl.getData(param,cols, dtt.getTemplate(),new PageInfo(pn,ps),dti);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DownloadBuilder<?> eb = new DownloadBuilder<>(null,null,convert(colsC));
                eb.appendData(cdr.getDataList());
                PageInfo pi = cdr.getPageInfo();
                while (pi != null && pi.hasNext()) {
                    try {
                        pi =cdr.getPageInfo().next() ;
                        cdr = dorisTableServiceImpl.getData(param,cols, dtt.getTemplate(),pi,dti);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    eb.appendData(cdr.getDataList());
                    cdr.getDataList().clear();
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, dtt.getTemplate().getTemplateName(), username);
                eb=null;
            }
        }
        new Thread(new AsyncUploadToOSS( request.getRemoteUser())).start();
        return new BaseResponse();
    }
}
