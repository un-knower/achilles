package com.quancheng.achilles.service.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.quancheng.achilles.dao.ds_qc.model.BaseResponse;
import com.quancheng.achilles.dao.odps.model.HospitalInfo;
import com.quancheng.achilles.dao.odps.model.OutHospitalRestaurantDistance;
import com.quancheng.achilles.dao.odps.model.RestaurantInfo;
import com.quancheng.achilles.service.services.HospitalRestaurantDistanceService;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;
import com.quancheng.starter.log.LogUtil;
import com.quancheng.starter.log.QcLog;
import com.quancheng.starter.log.QcLoggable;

import io.swagger.annotations.ApiParam;

@Controller
public class HostitalRestaurantController {

    private static final QcLog logger = LogUtil.getLogger(HostitalRestaurantController.class);

    @Autowired
    HospitalRestaurantDistanceService    distanceService;
    @Autowired
    OssServiceDBUtil                     ossServiceDBUtil;
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
    private static boolean               isUsed           = false;
    @QcLoggable(QcLoggable.Type.NONE)
    @RequestMapping(value = "/api/ops/hospitalrestaurant/getStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean getStatus() {
        return isUsed;

    }

    /**
     * 上传和计算
     */
    @QcLoggable(QcLoggable.Type.NONE)
    @RequestMapping(value = "/ops/hospitalrestaurant/upload", method = RequestMethod.POST)
    @ResponseBody
    public void uploadCalculation(@ApiParam(value = "导入Excel的文件") @RequestParam(value = "file", required = false) MultipartFile file,
                                  @ApiParam(value = "是否导入Excel") @RequestParam(value = "isUpload", required = false) Boolean isUpload,
                                  @ApiParam(value = "导入Excel类型") @RequestParam(value = "excelType", required = false) String excelType,
                                  @ApiParam(value = "导入Excel所属公司") @RequestParam(value = "excelCompanyId", required = false) String excelCompanyId,
                                  @ApiParam(value = "距离") @RequestParam(value = "distances", required = false) Double distances,
                                  @ApiParam(value = "是否插入额外数据") @RequestParam(value = "isInsertDatas", required = false) Boolean isInsertDatas,
                                  @ApiParam(value = "是否包含线上特许餐厅数据") @RequestParam(value = "isIncludeSpecial", required = false) Boolean isIncludeSpecial,
                                  @ApiParam(value = "任务类型") @RequestParam(value = "taskType", required = false) String taskType,
                                  @ApiParam(value = "导出外卖类型") @RequestParam(value = "waimai", required = false) String waimai,
                                  @ApiParam(value = "外卖是否在配送范围") @RequestParam(value = "isWaimaiOk", required = false) Boolean isWaimaiOk,
                                  @ApiParam(value = "导出预定类型") @RequestParam(value = "reserve", required = false) String reserve,
                                  @ApiParam(value = "是否进行公司对应") @RequestParam(value = "compareCompany", required = false) Boolean compareCompany,
                                  @ApiParam(value = "选择插入公司列表") @RequestParam(value = "companyIds", required = false) String[] companyIds,
                                  @ApiParam(value = "选择插入城市列表") @RequestParam(value = "cityIds", required = false) String[] cityIds,
                                  HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
        String remoteUser = request.getRemoteUser();
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
            EXECUTOR_SERVICE.execute(() -> {
                upload(nf, isUpload, excelType, excelCompanyId, distances, isInsertDatas, isIncludeSpecial, taskType,
                       waimai, isWaimaiOk, reserve, compareCompany, companyIds, cityIds, remoteUser);
            });
            response.sendRedirect("/ops/hospitalrestaurant/view");
        } catch (IOException e) {
            logger.error("upload sendRedirect have a error {}", e);
        }
    }

    public void upload(File file, Boolean isUpload, String excelType, String excelCompanyId, Double distances,
                       Boolean isInsertDatas, Boolean isIncludeSpecial, String taskType, String waimai,
                       Boolean isWaimaiOk, String reserve, Boolean compareCompany, String[] companyIds,
                       String[] cityIds, String remoteUser) {
        isUsed = true;
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
            if (isUpload != null && isUpload && file.exists()) {
                    String originalFilename = file.getName();
                    if (!StringUtils.isEmpty(originalFilename)) {
                        excelName = excelName + "(" + originalFilename.split("\\.")[0] + ")";
                    }
                    saveExcelToDB = distanceService.saveExcelToDB(file, otype, excelCompanyId, !clearTable);
                    if (!saveExcelToDB) {
                        export.setMsg("上传失败，没保存到数据库");
                    }else {
                        EXECUTOR_SERVICE.submit(new Handel(taskType, excelType, compareCompany, otype, remoteUser, excelName,
                                distances, isWaimaiOk, waimai, reserve, isIncludeSpecial));
                    }
            }else {
                EXECUTOR_SERVICE.submit(new Handel(taskType, excelType, compareCompany, otype, remoteUser, excelName,
                        distances, isWaimaiOk, waimai, reserve, isIncludeSpecial));
            }
        } catch (Exception e) {
            e.printStackTrace();
            export.setStatus("-1");
            export.setMsg("error");
            isUsed = false;
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

        private Boolean isIncludeSpecial;// 是否包含线上特许餐厅

        public Handel(String taskType, String excelType, Boolean compareCompany, Object otype, String username,
                      String excelName, Double distances, Boolean isWaimaiOk, String waimai, String reserve,
                      Boolean isIncludeSpecial){
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
            this.isIncludeSpecial = isIncludeSpecial;
        }

        @Override
        public Boolean call() {
            try {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
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
                distanceService.invokeODPSTask(uuid, otype, taskTypeO, compareCompany, distances, isWaimaiOk,
                                               getSqlParam("r.", waimai, reserve), isIncludeSpecial);
                distanceService.queryFromODPSAndSaveToDB(uuid);

                export(1, uuid, distances, isWaimaiOk, waimai, reserve, username, excelName);
            } catch (Exception e) {
//                logger.error("upload have a error {}", e);
            }finally{
                HostitalRestaurantController.isUsed = false;
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

    public BaseResponse export(int index, String uuid, Double distances, Boolean isWaimaiOk, String waimai,
                               String reserve, String username, String excelName) {
        Map<String, Object> exportParam = new HashMap<>();
        exportParam.put("param", getSqlParam(distances, isWaimaiOk, waimai, reserve));
        class AsyncUploadToOSS implements Runnable {

            private String              uuid;
            private String              username;
            private Map<String, Object> param;
            private String              excelName;

            public AsyncUploadToOSS(String uuid, Map<String, Object> param, String username, String excelName){
                this.uuid = uuid;
                this.param = param;
                this.username = username;
                this.excelName = excelName;
            }

            @Override
            public void run() {
                int maxSize = 900000;
                Integer pageSize = 8000;
                double pageNum = Math.ceil((double) maxSize / pageSize);
                int indexSize = (int) ((index - 1) * pageNum);

                DownloadBuilder<OutHospitalRestaurantDistance> eb = new DownloadBuilder<>(OutHospitalRestaurantDistance.class);

                PageInfo<OutHospitalRestaurantDistance> outInfo = distanceService.queryOutHospitalRestaurantDistanceFromDB(param,
                                                                                                                           indexSize
                                                                                                                                  + 1,
                                                                                                                           pageSize);
                double page = Math.ceil((double) (outInfo.getTotal() - (index - 1) * maxSize) / maxSize);
                int size = outInfo.getPages();
                if (page > 1) {
                    export(index + 1, uuid, distances, isWaimaiOk, waimai, reserve, username, excelName + "-" + index);
                    size = (int) (index * pageNum);
                }
                eb.append(outInfo.getList());
                if ((outInfo.getPages() - indexSize) > 1) {
                    for (int i = (indexSize + 2); i <= size; i++) {
                        outInfo = distanceService.queryOutHospitalRestaurantDistanceFromDB(param, i, pageSize);
                        eb.append(outInfo.getList());
                    }
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, excelName, username);
                HostitalRestaurantController.isUsed = false;
                String ODPSTableName = "out_hospital_restaurant_distance" + "_" + uuid;
                try {
                    distanceService.deleteODPSTable(ODPSTableName);
                } catch (OdpsException | IOException e) {
//                    logger.error("deleteODPSTable have a error {}", e);
                }
            }
        }
        EXECUTOR_SERVICE.execute(new AsyncUploadToOSS(uuid, exportParam, username, excelName));
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
