package com.quancheng.achilles.service.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quancheng.achilles.dao.odps.model.OdpsFlyCheck;
import com.quancheng.achilles.service.model.OdpsBaseResponse;
import com.quancheng.achilles.service.model.OdpsRestRequest;
import com.quancheng.achilles.service.odps.ODPSQueryService;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;

@Controller
@RequestMapping(path = "/api/odps")
public class OdpsSytController {

    private static final QcLog logger = LogUtil.getLogger(OdpsSytController.class);
    @Resource
    ODPSQueryService odpsService;

    @RequestMapping(path = "/fly/check/statistic/month", method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public OdpsBaseResponse<OdpsFlyCheck> getFlyCheckMonth(@RequestBody OdpsRestRequest orr) {
        try {
            return new OdpsBaseResponse<OdpsFlyCheck>(odpsService.queryUserCostByClient(orr.getClientId(),
                                                                                        orr.getHappenDate()));
        } catch (Exception e) {
            return new OdpsBaseResponse<OdpsFlyCheck>(e.getCause() == null ? e.getMessage() : e.getMessage() + "; "
                                                                                              + e.getCause().getMessage());
        }
    }

    @RequestMapping(path = "/test", method = { RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public Integer test() {
        try {
            // return odpsService.test();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getStackTrace());
        }
        return null;
    }

}
