package com.quancheng.achilles.service.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.odps.OdpsException;
import com.quancheng.achilles.dao.constant.InnConstantODPSTables;
import com.quancheng.achilles.dao.odps.model.HospitalInfo;
import com.quancheng.achilles.dao.odps.model.OdpsFlyCheck;
import com.quancheng.achilles.dao.odps.model.RestaurantInfo;
import com.quancheng.achilles.service.model.OdpsBaseResponse;
import com.quancheng.achilles.service.model.OdpsRestRequest;
import com.quancheng.achilles.service.odps.ODPSQueryService;
import com.quancheng.achilles.service.services.HospitalRestaurantDistanceService;

@Controller
@RequestMapping(path = "/api/odps")
public class OdpsSytController {

    Logger           logger = LogManager.getLogger();
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

    @Autowired
    HospitalRestaurantDistanceService hospitalRestaurantDistanceService;

    /**
     * 文件上传具体实现方法（单文件上传）
     *
     * @param file
     * @return
     * @author 单红宇(CSDN CATOOP)
     * @create 2017年3月11日
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = true) MultipartFile file,
                         @RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "companyId", required = false) String companyId,
                         @RequestParam(value = "distances", required = false) Double distances,
                         @RequestParam(value = "compareCompany", required = false) Boolean compareCompany,
                         @RequestParam(value = "companyIds", required = false) String[] companyIds,
                         @RequestParam(value = "cityIds", required = false) String[] cityIds) {
        Boolean saveExcelToDB = false;
        Map<String, String> param = new HashMap<>();
        if (!ArrayUtils.isEmpty(companyIds)) {
            param.put("companyIdList", StringUtils.join(companyIds, ","));
        }
        if (!ArrayUtils.isEmpty(companyIds)) {
            param.put("cityIdList", StringUtils.join(cityIds, ","));
        }
        if (!file.isEmpty()) {
            Object otype;
            try {
                if ("restaurant".equals(type)) {
                    otype = new RestaurantInfo();
                    hospitalRestaurantDistanceService.queryAndSaveHospitalInfoToDB(param);
                } else {
                    otype = new HospitalInfo();
                    hospitalRestaurantDistanceService.queryAndSaveRestaurantInfoToDB(param);
                }
                saveExcelToDB = hospitalRestaurantDistanceService.saveExcelToDB(file, otype, companyId);

                // hospitalRestaurantDistanceService.syncDBToODPS("restaurant_info", "tmp_restaurant_info");
                hospitalRestaurantDistanceService.syncDBToODPS("hospital_info", "tmp_hospital_info");
                InnConstantODPSTables.TaskHospitalRestaurantDistance taskType = InnConstantODPSTables.TaskHospitalRestaurantDistance.HospitalRestaurant;
                hospitalRestaurantDistanceService.invokeODPSTask(taskType, distances, compareCompany);
                hospitalRestaurantDistanceService.queryFromODPSAndSaveToDB();
            } catch (IOException | OdpsException | ParseException | TimeoutException | ExecutionException e) {
                logger.error("upload have a error {}", e);
            }
            if (saveExcelToDB) {
                return "上传成功";
            } else {
                return "上传成功";
            }

        } else {
            return "上传失败，因为文件是空的.";
        }
    }

}
