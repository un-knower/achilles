package com.quancheng.achilles.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.service.services.impl.DataItemServiceImpl;
import com.quancheng.achilles.service.services.impl.DorisTableServiceImpl;
import com.quancheng.achilles.service.services.impl.ImportToOnlineDbServiceImpl;


@RequestMapping("/hospital")
@RestController
public class ImportController {
    @Autowired
    ImportToOnlineDbServiceImpl importToOnlineDbServiceImpl;
    @Autowired
    DataItemServiceImpl dataItemServiceImpl;
    @Autowired
    DorisTableServiceImpl dorisTableServiceImpl;
    
    private static volatile String status="free";
    
    @RequestMapping(path = "/import/page", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, 
            MediaType.TEXT_HTML_VALUE })
    public ModelAndView importPage(ModelAndView mv){
        mv.addObject("clientList", dataItemServiceImpl.getDataItemDetail("ALL_CLIENT_LIST"));
        mv.addObject("tableList", dorisTableServiceImpl.queryDataImport());
        mv.addObject("status", status);
        mv.setViewName("import_data/import_page");
        return mv;
    }
    
    @RequestMapping(path = "/import/status" ,produces = { MediaType.APPLICATION_JSON_UTF8_VALUE },method=RequestMethod.GET)
    public String request( ){
        return status;
    }
    
    @RequestMapping(path = "/import/proccessing", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, 
            MediaType.TEXT_HTML_VALUE },method={RequestMethod.POST})
    public String importData(ModelAndView mv, Long clientId, Long dorisTableId, String targetTable, String apiType){
            if("free".equals(status)){
                status="busy"; 
                importToOnlineDbServiceImpl.calculate(clientId, dorisTableId, targetTable, apiType);
                status="free";
                return "success";
            }else{
                return "failed";
            }
    }
}
