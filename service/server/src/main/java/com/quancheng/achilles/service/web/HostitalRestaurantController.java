package com.quancheng.achilles.service.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aliyun.odps.OdpsException;
import com.github.pagehelper.PageInfo;
import com.quancheng.achilles.dao.constant.InnConstantODPSTables;
import com.quancheng.achilles.dao.model.BaseResponse;
import com.quancheng.achilles.dao.odps.model.HospitalInfo;
import com.quancheng.achilles.dao.odps.model.OutHospitalRestaurantDistance;
import com.quancheng.achilles.dao.odps.model.RestaurantInfo;
import com.quancheng.achilles.service.services.HospitalRestaurantDistanceService;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;

import io.swagger.annotations.ApiParam;

@Controller
public class HostitalRestaurantController {

    Logger                               logger           = LogManager.getLogger(HostitalRestaurantController.class);

    @Autowired
    HospitalRestaurantDistanceService    distanceService;
    @Autowired
    OssServiceDBUtil                     ossServiceDBUtil;
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
    private static boolean               isUsed           = false;

    @RequestMapping(value = "/api/ops/hospitalrestaurant/getStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean getStatus() {
        return isUsed;

    }

    /**
     * 上传和计算
     */
    @RequestMapping(value = "/ops/hospitalrestaurant/upload", method = RequestMethod.POST)
    @ResponseBody
    public void uploadCalculation(@ApiParam(value = "导入Excel的文件") @RequestParam(value = "file", required = false) MultipartFile file,
                                  @ApiParam(value = "是否导入Excel") @RequestParam(value = "isUpload", required = false) Boolean isUpload,
                                  @ApiParam(value = "导入Excel类型") @RequestParam(value = "excelType", required = false) String excelType,
                                  @ApiParam(value = "导入Excel所属公司") @RequestParam(value = "excelCompanyId", required = false) String excelCompanyId,
                                  @ApiParam(value = "距离") @RequestParam(value = "distances", required = false) Double distances,
                                  @ApiParam(value = "是否插入额外数据") @RequestParam(value = "isInsertDatas", required = false) Boolean isInsertDatas,
                                  @ApiParam(value = "任务类型") @RequestParam(value = "taskType", required = false) String taskType,
                                  @ApiParam(value = "导出外卖类型") @RequestParam(value = "waimai", required = false) String waimai,
                                  @ApiParam(value = "外卖是否在配送范围") @RequestParam(value = "isWaimaiOk", required = false) Boolean isWaimaiOk,
                                  @ApiParam(value = "导出预定类型") @RequestParam(value = "reserve", required = false) String reserve,
                                  @ApiParam(value = "是否进行公司对应") @RequestParam(value = "compareCompany", required = false) Boolean compareCompany,
                                  @ApiParam(value = "选择插入公司列表") @RequestParam(value = "companyIds", required = false) String[] companyIds,
                                  @ApiParam(value = "选择插入城市列表") @RequestParam(value = "cityIds", required = false) String[] cityIds,
                                  HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
        isUsed = true;
        mv.setViewName("hospital_restaurant/hospitalrestaurant_view");
        BaseResponse export = new BaseResponse("-1", "fail");
        Boolean saveExcelToDB = false;
        Map<String, String> param = new HashMap<>();
        if (!ArrayUtils.isEmpty(companyIds)) {
            param.put("companyIdList", StringUtils.join(companyIds, ","));
        }
        if (!ArrayUtils.isEmpty(cityIds)) {
            param.put("cityIdList", StringUtils.join(cityIds, ","));
        }

        Object otype = null;
        try {
            boolean clearTable = false;
            if (!StringUtils.isEmpty(excelType)) {
                if ("restaurant".equals(excelType)) {
                    otype = new RestaurantInfo();
                    if (isInsertDatas != null && isInsertDatas) {
                        if (!param.isEmpty()) {
                            clearTable = true;
                            distanceService.queryAndSaveRestaurantInfoToDB(param, clearTable);
                        }
                    }
                } else {
                    otype = new HospitalInfo();
                    if (isInsertDatas != null && isInsertDatas) {
                        if (!param.isEmpty()) {
                            clearTable = true;
                            distanceService.queryAndSaveHospitalInfoToDB(param, clearTable);
                        }
                    }
                }
            }
            String excelName = "医院餐厅距离计算详情";
            if (isUpload != null && isUpload) {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    if (!StringUtils.isEmpty(originalFilename)) {
                        excelName = excelName + "(" + originalFilename.split("\\.")[0] + ")";
                    }
                    saveExcelToDB = distanceService.saveExcelToDB(file, otype, excelCompanyId, !clearTable);
                    if (!saveExcelToDB) {
                        export.setMsg("上传失败，没保存到数据库");
                    }
                } else {
                    export.setMsg("上传失败，因为文件是空的.");
                }
            }
            EXECUTOR_SERVICE.submit(new Handel(taskType, excelType, compareCompany, otype, request.getRemoteUser(),
                                               excelName, distances, isWaimaiOk, waimai, reserve));
            // isUsed = !submit.get(30, TimeUnit.MINUTES);

        } catch (Exception e) {
            export.setStatus("-1");
            export.setMsg("error");
            logger.error("upload have a error {}", e);
            isUsed = false;
        }
        // setModelAndView(mv);
        // return mv.addObject("status", export);
        try {
            response.sendRedirect("/ops/hospitalrestaurant/view");
        } catch (IOException e) {
            logger.error("upload sendRedirect have a error {}", e);
        }
    }

    class Handel implements Callable<Boolean> {

        private String  taskType;
        private String  excelType;
        private Boolean compareCompany;
        private Object  otype;
        private String  username;
        private Double  distances;
        private Boolean isWaimaiOk;
        private String  waimai;
        private String  reserve;
        private String  excelName;

        public Handel(String taskType, String excelType, Boolean compareCompany, Object otype, String username,
                      String excelName, Double distances, Boolean isWaimaiOk, String waimai, String reserve){
            this.taskType = taskType;
            this.excelType = excelType;
            this.compareCompany = compareCompany;
            this.otype = otype;
            this.username = username;
            this.distances = distances;
            this.isWaimaiOk = isWaimaiOk;
            this.waimai = waimai;
            this.reserve = reserve;
            this.excelName = excelName;
        }

        @Override
        public Boolean call() {
            try {
                if ("restaurant".equals(excelType)) {
                    distanceService.syncDBToODPS("restaurant_info", "tmp_restaurant_info", true);
                } else {
                    distanceService.syncDBToODPS("hospital_info", "tmp_hospital_info", true);
                }

                InnConstantODPSTables.TaskHospitalRestaurantDistance taskTypeO;
                if ("RestaurantHospital".equalsIgnoreCase(taskType)) {
                    taskTypeO = InnConstantODPSTables.TaskHospitalRestaurantDistance.RestaurantHospital;
                } else {
                    taskTypeO = InnConstantODPSTables.TaskHospitalRestaurantDistance.HospitalRestaurant;
                }
                distanceService.invokeODPSTask(otype, taskTypeO, compareCompany, distances, isWaimaiOk,
                                               getSqlParam("r.", waimai, reserve));
                distanceService.queryFromODPSAndSaveToDB();

                export(distances, isWaimaiOk, waimai, reserve, username, excelName);
            } catch (IOException | OdpsException | ParseException | TimeoutException | ExecutionException e) {
                HostitalRestaurantController.isUsed = false;
                logger.error("upload have a error {}", e);
            }
            return true;
        }

    }

    public String getSqlParam(String prefix, String waimai, String reserve) {
        return getSqlParam(prefix, null, null, waimai, reserve);
    }

    public String getSqlParam(Boolean isWaimaiOk, String waimai, String reserve) {
        return getSqlParam("", null, isWaimaiOk, waimai, reserve);
    }

    public String getSqlParam(Double distances, Boolean isWaimaiOk, String waimai, String reserve) {
        return getSqlParam("", distances, isWaimaiOk, waimai, reserve);
    }

    public String getSqlParam(String prefix, Double distances, Boolean isWaimaiOk, String waimai, String reserve) {
        String queryParam = "";
        if (distances != null) {
            queryParam = prefix + "distance <= " + distances;
        }
        if (isWaimaiOk != null && isWaimaiOk) {
            queryParam = queryParam + prefix + "is_within=1 ";
        }

        String re = "and";
        String bian = "";
        String bian1 = "";
        if (!StringUtils.isEmpty(waimai) && !StringUtils.isEmpty(reserve)) {
            re = "or";
            bian = "(";
            bian1 = ")";
        }
        if (!StringUtils.isEmpty(waimai)) {
            if (waimai.contains("only")) {
                queryParam = queryParam + " and (" + bian + prefix + "support_waimai=1 and " + prefix
                             + "support_reserve=0)";
            } else {
                queryParam = queryParam + " and " + bian + prefix + "support_waimai=1";
            }
        }

        if (!StringUtils.isEmpty(reserve)) {
            if (reserve.contains("only")) {
                queryParam = queryParam + " " + re + " (" + prefix + "support_waimai=0 and " + prefix
                             + "support_reserve=1)" + bian1;
            } else {
                queryParam = queryParam + " " + re + " " + prefix + "support_reserve=1" + bian1;
            }
        }
        return queryParam;
    }

    public BaseResponse export(Double distances, Boolean isWaimaiOk, String waimai, String reserve, String username,
                               String excelName) {
        Map<String, Object> exportParam = new HashMap<>();
        exportParam.put("param", getSqlParam(distances, isWaimaiOk, waimai, reserve));
        class AsyncUploadToOSS implements Runnable {

            private String              username;
            private Map<String, Object> param;
            private String              excelName;

            public AsyncUploadToOSS(Map<String, Object> param, String username, String excelName){
                this.param = param;
                this.username = username;
                this.excelName = excelName;
            }

            @Override
            public void run() {

                DownloadBuilder<OutHospitalRestaurantDistance> eb = new DownloadBuilder<>(OutHospitalRestaurantDistance.class);
                Integer pageSize = 10000;
                PageInfo<OutHospitalRestaurantDistance> outInfo = distanceService.queryOutHospitalRestaurantDistanceFromDB(param,
                                                                                                                           1,
                                                                                                                           pageSize);
                eb.append(outInfo.getList());
                if (outInfo.getPages() > 1) {
                    for (int i = 2; i <= outInfo.getPages(); i++) {
                        outInfo = distanceService.queryOutHospitalRestaurantDistanceFromDB(param, i, pageSize);
                        eb.append(outInfo.getList());
                    }
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, excelName, username);
                HostitalRestaurantController.isUsed = false;
            }
        }
        EXECUTOR_SERVICE.execute(new AsyncUploadToOSS(exportParam, username, excelName));
        return new BaseResponse();
    }

    /**
     *  
     */
    @RequestMapping(path = "/ops/hospitalrestaurant/view", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
                                                                                                    MediaType.TEXT_HTML_VALUE })
    public ModelAndView listRestaurantVisitLog(ModelAndView mv) {
        setModelAndView(mv);
        mv.setViewName("hospital_restaurant/hospitalrestaurant_view");
        return mv;
    }

    public void setModelAndView(ModelAndView mv) {
        mv.addObject("companyList", distanceService.queryCompanyInfo());
        mv.addObject("cityList", distanceService.queryCityInfo());
    }
}
