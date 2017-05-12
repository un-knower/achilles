package com.quancheng.achilles.service.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quancheng.achilles.dao.odps.model.OdpsFlyCheck;
import com.quancheng.achilles.service.odps.ODPSQueryService;
import com.quancheng.achilles.service.rest.BaseResponse;
import com.quancheng.achilles.service.rest.OdpsRestRequest;

@Controller
@RequestMapping(path="/odps")
public class OdpsSytController {
    Logger logger =  LogManager.getLogger();
    @Resource
    ODPSQueryService odpsService;
    
    @RequestMapping(path="/fly/check/statistic/month", method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public  BaseResponse<OdpsFlyCheck> getFlyCheckMonth(@RequestBody OdpsRestRequest orr){
        try {
            return new BaseResponse<OdpsFlyCheck>(odpsService.queryUserCostByClient(orr.getClientId(), orr.getHappenDate()));
        } catch ( Exception e) {
            return new BaseResponse<OdpsFlyCheck>(e.getCause()==null?e.getMessage():e.getMessage()+"; "+e.getCause().getMessage());
        }
    }
}
