package com.quancheng.achilles.service.grpc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;

//import com.google.gson.Gson;
//import com.quancheng.achilles.dao.odps.model.OdpsFlyCheck;
//import com.quancheng.achilles.model.achilles.OdpsSytRequest;
//import com.quancheng.achilles.model.achilles.OdpsSytResponse;
//import com.quancheng.achilles.service.AchillesService;
import com.quancheng.achilles.service.odps.ODPSQueryService;
import com.quancheng.achilles.service.redis.RedisComponent;
//import com.quancheng.saluki.boot.SalukiService;

//@SalukiService
//public class OdpsSytFlyCheckService implements AchillesService {
//    Logger logger = LogManager.getLogger();
//    @Resource
//    ODPSQueryService odpsService;
//
//    @Autowired
//    RedisComponent redisService;
//    public OdpsSytResponse flyCheckStatisticMonth(OdpsSytRequest odpssytrequest) {
//        OdpsSytResponse osr = new OdpsSytResponse();
//        try {
//            org.springframework.data.redis.core.StringRedisTemplate srt = redisService.getStringRedisTemplate();
//            String redisKey = this.getClass().getName()+":flyCheckStatisticMonth"+":"+odpssytrequest.getClientId()+":"+odpssytrequest.getHappenDate();
//            Long size = srt.boundListOps( redisKey).size();
//            ArrayList<com.quancheng.achilles.model.achilles.OdpsFlyCheck> resultList =null;
////            Gson parser = new Gson();
////            if(size == null || size == 0 ){
////                List<OdpsFlyCheck> ofcList = odpsService.queryUserCostByClient(odpssytrequest.getClientId(),  odpssytrequest.getHappenDate());
////                BoundListOperations<String, String> redisListOperation = srt.boundListOps( redisKey);
////                if (ofcList != null && !ofcList.isEmpty()) {
////                    resultList = new ArrayList<>( ofcList.size());
////                    com.quancheng.achilles.model.achilles.OdpsFlyCheck ofc = null;
////                    for (OdpsFlyCheck odpsFlyCheck : ofcList) {
////                        ofc = new com.quancheng.achilles.model.achilles.OdpsFlyCheck();
////                        BeanUtils.copyProperties(odpsFlyCheck, ofc);
////                        resultList.add(ofc);
////                        redisListOperation.leftPush(parser.toJson(ofc));
////                    }
////                }
////            }else{
////                List<String> list = srt.boundListOps( redisKey).range(0, size);
////                resultList = new ArrayList<com.quancheng.achilles.model.achilles.OdpsFlyCheck>(size.intValue());
////                for (String json : list) {
////                    resultList.add(parser.fromJson(json, com.quancheng.achilles.model.achilles.OdpsFlyCheck.class));
////                }
////            } 
//            osr.setOdpsFlyCheck(resultList);
//            osr.setStatus("success");
//        } catch (Exception e) {
//            osr.setStatus("failed");
//            osr.setMessage(e.getCause() == null ? e.getMessage() : e.getMessage() + "; " + e.getCause().getMessage());
//            logger.error("{}",e);
//        }
//        return osr;
//    }
//}
