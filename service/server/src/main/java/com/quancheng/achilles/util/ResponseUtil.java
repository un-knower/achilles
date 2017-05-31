package com.quancheng.achilles.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.quancheng.achilles.model.basemodel.BaseResponse;

public class ResponseUtil {

    public static ObjectMapper objectMapper;
    private static Logger      log = LoggerFactory.getLogger(ResponseUtil.class);

//    public static BaseResponse setBaseResponse(Boolean success, String message, Integer errorCode) {
//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setSuccess(success);
//        if (!StringUtils.isEmpty(message)) {
//            baseResponse.setMessage(message);
//        }
//        if (errorCode != null) {
//            baseResponse.setErrorCode(errorCode);
//        }
//        return baseResponse;
//    }

}
