package com.quancheng.achilles.service.grpc;

//import java.io.IOException;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.BoundListOperations;
//import org.springframework.data.redis.core.BoundValueOperations;
//
//import com.aliyun.odps.OdpsException;
//import com.google.gson.Gson;
//import com.quancheng.achilles.dao.odps.model.OdpsCompanyRestaurant;
//import com.quancheng.achilles.dao.odps.model.OdpsFlyCheck;
//import com.quancheng.achilles.model.achilles.AllNumResponse;
//import com.quancheng.achilles.model.achilles.FlyCheck;
//import com.quancheng.achilles.model.achilles.RestaurantsNumResponse;
//import com.quancheng.achilles.model.achilles.SytRequest;
//import com.quancheng.achilles.model.achilles.SytResponse;
//import com.quancheng.achilles.model.basemodel.BaseResponse;
//import com.quancheng.achilles.service.AchillesService;
//import com.quancheng.achilles.service.odps.ODPSQueryService;
//import com.quancheng.achilles.service.redis.RedisComponent;
//import com.quancheng.achilles.util.JsonUtil;
//import com.quancheng.achilles.util.ResponseUtil;
//import com.quancheng.achilles.util.TimeUtil;
//import com.quancheng.saluki.boot.SalukiService;
//
//@SalukiService
//public class AchillesServiceImpl implements AchillesService {
//
//    Logger           log = LogManager.getLogger(AchillesServiceImpl.class);
//    @Resource
//    ODPSQueryService odpsService;
//
//    @Autowired
//    RedisComponent   redisService;
//
//    @Override
//    public SytResponse flyCheckStatisticMonth(SytRequest odpssytrequest) {
//        SytResponse osr = new SytResponse();
//        BaseResponse baseResponse = ResponseUtil.setBaseResponse(false, "fail", null);
//        try {
//            org.springframework.data.redis.core.StringRedisTemplate srt = redisService.getStringRedisTemplate();
//            String redisKey = this.getClass().getName() + ":flyCheckStatisticMonth" + ":" + odpssytrequest.getClientId()
//                              + ":" + odpssytrequest.getHappenDate();
//            Long size = srt.boundListOps(redisKey).size();
//            ArrayList<FlyCheck> resultList = null;
//            Gson parser = new Gson();
//            if (size == null || size == 0) {
//                List<OdpsFlyCheck> ofcList = odpsService.queryUserCostByClient(odpssytrequest.getClientId(),
//                                                                               odpssytrequest.getHappenDate());
//                BoundListOperations<String, String> redisListOperation = srt.boundListOps(redisKey);
//                if (ofcList != null && !ofcList.isEmpty()) {
//                    resultList = new ArrayList<>(ofcList.size());
//                    FlyCheck ofc = null;
//                    for (OdpsFlyCheck odpsFlyCheck : ofcList) {
//                        ofc = new FlyCheck();
//                        BeanUtils.copyProperties(odpsFlyCheck, ofc);
//                        resultList.add(ofc);
//                        redisListOperation.leftPush(parser.toJson(ofc));
//                    }
//                }
//            } else {
//                List<String> list = srt.boundListOps(redisKey).range(0, size);
//                resultList = new ArrayList<FlyCheck>(size.intValue());
//                for (String json : list) {
//                    resultList.add(parser.fromJson(json, FlyCheck.class));
//                }
//            }
//            osr.setFlyCheck(resultList);
//            baseResponse = ResponseUtil.setBaseResponse(true, "success", null);
//        } catch (Exception e) {
//            baseResponse = ResponseUtil.setBaseResponse(false,
//                                                        e.getCause() == null ? e.getMessage() : e.getMessage() + "; "
//                                                                                                + e.getCause().getMessage(),
//                                                        null);
//            log.error("{}", e);
//        }
//        osr.setBaseResponse(baseResponse);
//        return osr;
//    }
//
//    private RestaurantsNumResponse queryCompanyRestaurantsStatisticsByTime(String time, String companyId) {
//        RestaurantsNumResponse response = new RestaurantsNumResponse();
//        BaseResponse baseResponse = ResponseUtil.setBaseResponse(false, "fail", null);
//        Date date;
//        OdpsCompanyRestaurant companyStatistics;
//        OdpsCompanyRestaurant previousCompanyStatistics;
//        try {
//            companyStatistics = odpsService.queryCompanyRestaurantsStatistics(time, companyId);
//            date = TimeUtil.parseDate("yyyyMM", time);
//            Date afterMonth = TimeUtil.addMonth(date, -1);
//            String afterMonthStr = TimeUtil.formatDate("yyyyMM", afterMonth);
//            previousCompanyStatistics = odpsService.queryCompanyRestaurantsStatistics(afterMonthStr, companyId);
//            if (companyStatistics != null) {
//                response.setTime(companyStatistics.getTime());
//                response.setCompanyId(companyStatistics.getCompanyId());
//                response.setCompanyName(companyStatistics.getCompanyName());
//                response.setCompanyId(companyStatistics.getCompanyId());
//                response.setAllRestaurantsNum(companyStatistics.getAllRestaurantsNum());
//
//                if (previousCompanyStatistics != null) {
//                    response.setPreviousAllRestaurantsNum(previousCompanyStatistics.getAllRestaurantsNum());
//                    response.setAddRestaurantsNum(companyStatistics.getAllRestaurantsNum()
//                                                  - previousCompanyStatistics.getAllRestaurantsNum());
//
//                } else {
//                    response.setPreviousAllRestaurantsNum(0);
//                    response.setAddRestaurantsNum(companyStatistics.getAllRestaurantsNum());
//                }
//                baseResponse = ResponseUtil.setBaseResponse(true, "success", null);
//
//            }
//        } catch (ParseException | OdpsException | IOException e) {
//            baseResponse = ResponseUtil.setBaseResponse(false,
//                                                        e.getCause() == null ? e.getMessage() : e.getMessage() + "; "
//                                                                                                + e.getCause().getMessage(),
//                                                        null);
//            log.error("queryCompanyStatisticsByTime rest have a error {}", e);
//        }
//        response.setBaseResponse(baseResponse);
//        return response;
//    }
//
//    @Override
//    public RestaurantsNumResponse queryCompanyRestaurantsStatistics(SytRequest odpssytrequest) {
//        RestaurantsNumResponse resp = new RestaurantsNumResponse();
//        BaseResponse baseResponse = ResponseUtil.setBaseResponse(false, "fail", null);
//        resp.setBaseResponse(baseResponse);
//        try {
//            org.springframework.data.redis.core.StringRedisTemplate srt = redisService.getStringRedisTemplate();
//            String redisKey = this.getClass().getName() + ":queryCompanyRestaurantsStatistics" + ":"
//                              + odpssytrequest.getClientId() + ":" + odpssytrequest.getHappenDate();
//            Long size = srt.boundValueOps(redisKey).size();
//            if (size == null || size == 0) {
//                resp = queryCompanyRestaurantsStatisticsByTime(odpssytrequest.getHappenDate(),
//                                                               odpssytrequest.getClientId());
//                if (resp.getBaseResponse().getSuccess()) {
//                    BoundValueOperations<String, String> boundValueOps = srt.boundValueOps(redisKey);
//                    String json = JsonUtil.objectToJson(resp);
//                    boundValueOps.set(json);
//                }
//            } else {
//                String json = srt.boundValueOps(redisKey).get();
//                resp = JsonUtil.jsonToObject(json, RestaurantsNumResponse.class);
//                if (!resp.getBaseResponse().getSuccess()) {
//                    resp = queryCompanyRestaurantsStatisticsByTime(odpssytrequest.getHappenDate(),
//                                                                   odpssytrequest.getClientId());
//                    if (resp.getBaseResponse().getSuccess()) {
//                        BoundValueOperations<String, String> boundValueOps = srt.boundValueOps(redisKey);
//                        boundValueOps.set(JsonUtil.objectToJson(resp));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            baseResponse = ResponseUtil.setBaseResponse(false,
//                                                        e.getCause() == null ? e.getMessage() : e.getMessage() + "; "
//                                                                                                + e.getCause().getMessage(),
//                                                        null);
//            resp.setBaseResponse(baseResponse);
//            log.error("{}", e);
//        }
//        return resp;
//    }
//
//    @Override
//    public AllNumResponse queryCompanyAllShelfNum(SytRequest arg0) {
//        AllNumResponse resp = new AllNumResponse();
//        BaseResponse baseResponse = ResponseUtil.setBaseResponse(false, "fail", null);
//        resp.setBaseResponse(baseResponse);
//        try {
//            org.springframework.data.redis.core.StringRedisTemplate srt = redisService.getStringRedisTemplate();
//            String redisKey = this.getClass().getName() + ":queryCompanyAllShelfNum" + ":" + arg0.getClientId() + ":"
//                              + arg0.getHappenDate();
//            Long size = srt.boundValueOps(redisKey).size();
//            if (size == null || size == 0) {
//                Integer num = odpsService.queryCompanyAllShelfNumByTime(arg0.getHappenDate(), arg0.getClientId());
//                if (num != null) {
//                    BoundValueOperations<String, String> boundValueOps = srt.boundValueOps(redisKey);
//                    boundValueOps.set(num + "");
//                    resp.setAllNum(num);
//                    resp.setBaseResponse(ResponseUtil.setBaseResponse(true, "success", null));
//                }
//
//            } else {
//                String json = srt.boundValueOps(redisKey).get();
//                int allNum = Integer.valueOf(json);
//                resp.setAllNum(allNum);
//                resp.setBaseResponse(ResponseUtil.setBaseResponse(true, "success", null));
//            }
//        } catch (Exception e) {
//            baseResponse = ResponseUtil.setBaseResponse(false,
//                                                        e.getCause() == null ? e.getMessage() : e.getMessage() + "; "
//                                                                                                + e.getCause().getMessage(),
//                                                        null);
//            resp.setBaseResponse(baseResponse);
//            log.error("{}", e);
//        }
//        return resp;
//    }
//
//    @Override
//    public AllNumResponse queryCompanyAllSourceNum(SytRequest arg0) {
//        AllNumResponse resp = new AllNumResponse();
//        BaseResponse baseResponse = ResponseUtil.setBaseResponse(false, "fail", null);
//        resp.setBaseResponse(baseResponse);
//        try {
//            org.springframework.data.redis.core.StringRedisTemplate srt = redisService.getStringRedisTemplate();
//            String redisKey = this.getClass().getName() + ":queryCompanyAllSourceNum" + ":" + arg0.getClientId() + ":"
//                              + arg0.getHappenDate();
//            Long size = srt.boundValueOps(redisKey).size();
//            if (size == null || size == 0) {
//                Integer num = odpsService.queryCompanyAllSourceNumByTime(arg0.getHappenDate(), arg0.getClientId());
//                if (num != null) {
//                    BoundValueOperations<String, String> boundValueOps = srt.boundValueOps(redisKey);
//                    boundValueOps.set(num + "");
//                    resp.setAllNum(num);
//                    resp.setBaseResponse(ResponseUtil.setBaseResponse(true, "success", null));
//                }
//            } else {
//                String json = srt.boundValueOps(redisKey).get();
//                int allNum = Integer.valueOf(json);
//                resp.setAllNum(allNum);
//                resp.setBaseResponse(ResponseUtil.setBaseResponse(true, "success", null));
//            }
//        } catch (Exception e) {
//            baseResponse = ResponseUtil.setBaseResponse(false,
//                                                        e.getCause() == null ? e.getMessage() : e.getMessage() + "; "
//                                                                                                + e.getCause().getMessage(),
//                                                        null);
//            resp.setBaseResponse(baseResponse);
//            log.error("{}", e);
//        }
//        return resp;
//    }
//
//}
