package com.quancheng.achilles.service.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.dao.modelwrite.OssFileInfo;
import com.quancheng.achilles.service.constants.InnConstantsJob;
import com.quancheng.achilles.service.services.CacheLogService;
import com.quancheng.achilles.service.services.OssFileInfoService;
import com.quancheng.achilles.service.services.impl.DorisTableServiceImpl;

@Controller
public class HomeController {
//	 private Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private CacheLogService cacheLogService;
    @Autowired
    private OssFileInfoService ossFileInfoService;
    @Autowired
    DorisTableServiceImpl dorisTableServiceImpl;
    
    @RequestMapping(value = "/ops", method = { RequestMethod.GET }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
                                                                                 MediaType.TEXT_HTML_VALUE })
    public ModelAndView list(HttpSession session, ModelAndView mv) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mv.addObject("tables", dorisTableServiceImpl.query());
        String username = auth.getName();
        List<OssFileInfo> ossFileInfoList = ossFileInfoService.listLatest10OssFileInfoOfUser(username);
        session.setAttribute("ossFileInfoList", ossFileInfoList);
        for (Integer id : InnConstantsJob.IDS) {
            mv.addObject("refresh_time_"+id,cacheLogService.getRefreshTimeById(id));
        }
        return mv;
    }
}
