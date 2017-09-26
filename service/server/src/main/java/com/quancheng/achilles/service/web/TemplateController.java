package com.quancheng.achilles.service.web;

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

import com.quancheng.achilles.dao.model.BaseResponse;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.modelwrite.DorisTableInfo;
import com.quancheng.achilles.dao.modelwrite.RestaurantGonghai;
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
        if(param.containsKey("templateId")){
            mv.setViewName("redirect:/ops");
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
        mv.addObject("paramaterConfigs",dorisTableServiceImpl.configParamater(dtt.getParams()) );
        cdr.setTemplateId(templateId);
        mv.addObject("templateColumnKeys", cols);
        mv.addObject("page", cdr);
        mv.addObject("template",dtt.getTemplate());
        mv.setViewName("template");
        return mv;
    }
    @Autowired
    OssServiceDBUtil ossServiceDBUtil;
    @RequestMapping(path="export",method = { RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public BaseResponse export(HttpServletRequest request)   {
        Map<String,String[]> param = request.getParameterMap();
       
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
                DorisTableInfo dti = dorisTableServiceImpl.dorisTableInfo(dtt.getTemplate().getTemplateConfigId());
                Long ps = 5000L;
                Long pn = 0L;
                try {
                    cdr = dorisTableServiceImpl.getData(param,cols, dtt.getTemplate(),new PageInfo(pn,ps),dti);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DownloadBuilder<RestaurantGonghai> eb = new DownloadBuilder<>(null,null,cols);
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
