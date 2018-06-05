package com.quancheng.achilles.service.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.dao.ds_qc.model.BaseResponse;
import com.quancheng.achilles.dao.ds_st.model.OssFileInfo;
import com.quancheng.achilles.service.services.OssFileInfoService;
import com.quancheng.starter.log.QcLoggable;

/**
 * @author lijun jiang
 * @version 创建时间：2016年10月13日下午3:24:50
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DownLoadRecordController {

    @Autowired
    private OssFileInfoService ossFileInfoService;

    @RequestMapping(value = "/ops/lastest-10-files", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @QcLoggable(QcLoggable.Type.NONE)
    public BaseResponse getLatest10OssFiles(HttpSession session, ModelAndView mv) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<OssFileInfo> ossFileInfoList = ossFileInfoService.listLatest10OssFileInfoOfUser(username);
        session.setAttribute("ossFileInfoList", ossFileInfoList);

        return new BaseResponse(ossFileInfoList);
    }

}
