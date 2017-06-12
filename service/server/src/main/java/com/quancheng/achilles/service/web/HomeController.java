package com.quancheng.achilles.service.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.service.constants.InnConstantsJob;
import com.quancheng.achilles.service.services.CacheLogService;
import com.quancheng.achilles.service.services.OssFileInfoService;
import com.quancheng.achilles.dao.modelwrite.OssFileInfo;

@Controller
public class HomeController {
	 private Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private CacheLogService cacheLogService;
    @Autowired
    private OssFileInfoService ossFileInfoService;

    @RequestMapping(value = "/ops", method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
                                                                                 MediaType.TEXT_HTML_VALUE })
    public ModelAndView list(HttpSession session, ModelAndView mv) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<OssFileInfo> ossFileInfoList = ossFileInfoService.listLatest10OssFileInfoOfUser(username);
        session.setAttribute("ossFileInfoList", ossFileInfoList);
        for (Integer id : InnConstantsJob.IDS) {
            mv.addObject("refresh_time_"+id,cacheLogService.getRefreshTimeById(id));
        }
        return mv;
    }

    @RequestMapping(value = "/ops/refresh")
    public @ResponseBody Map<String, String> doRefresh(@RequestParam("ids") Integer[] ids) {
        Map<String, String> mv = new HashMap<String, String>();
        // Random r = new Random();
        // String refreshResult = r.nextLong() + "";
        logger.info("fresh ids :"+Arrays.toString(ids));
        for (Integer integer : ids) {
            String refreshResult;
            try {
                logger.info("begin fresh :"+integer);
                refreshResult = cacheLogService.doRefresh(integer);
                mv.put("date", refreshResult);
            } catch (Exception e) {
            		logger.error("update tmp table error:{}",e);
                mv.put("date", "update error , call managers");
            }
        }
        return mv;
    }

}
