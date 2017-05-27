package com.quancheng.achilles.service.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.services.CheckPeopleRestService;
import com.quancheng.achilles.dao.model.BaseResponse;
import com.quancheng.achilles.dao.model.CheckEmphasisPeople;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;

import io.swagger.annotations.ApiParam;

/**
 * <strong>描述：</strong>TODO<br>
 * <strong>功能：</strong><br>
 * <strong>使用场景：</strong><br>
 * <strong>注意事项：</strong>
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author pcuser 2016年9月12日 下午6:15:09
 * @version $Id: CheckPeopleRestController.java, v 0.0.1 2016年9月12日 下午6:15:09
 *          lijunjiang Exp $
 */
@Controller
@RequestMapping(path = "/ops/checkpeople")
public class CheckPeopleRestController {

    @Autowired
    CheckPeopleRestService checkPeopleRestService;
    @Autowired
    OssServiceDBUtil ossServiceDBUtil;
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    @ResponseBody
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public BaseResponse downLoad(
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            HttpServletRequest request, ModelAndView mv, HttpServletResponse response) throws IOException {
        class AsyncUploadToOSS implements Runnable {
            private ModelAndView mv;
            private String username;

            public AsyncUploadToOSS(ModelAndView mv, String username) {
                this.mv = mv;
                this.username = username;
            }

            @Override
            public void run() {
                DownloadBuilder<CheckEmphasisPeople> downLoadBuild = new DownloadBuilder(CheckEmphasisPeople.class);
                mv = list(1000, 0, mv);
                Page<CheckEmphasisPeople> page = (Page<CheckEmphasisPeople>) mv.getModel().get("page");
                downLoadBuild.append(page.getContent());
                while (page.hasNext()) {
                    Pageable nextPageable = page.nextPageable();
                    list(nextPageable.getPageSize(), nextPageable.getPageNumber(), mv);
                    page = (Page<CheckEmphasisPeople>) mv.getModel().get("page");
                    downLoadBuild.append(page.getContent());
                }
                String filePath = downLoadBuild.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "重点关注人员", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
            MediaType.TEXT_HTML_VALUE })
    public ModelAndView list(
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            ModelAndView mv) {

        Pageable pageable = new PageRequest(pageNum, pageSize);
        Page<CheckEmphasisPeople> page = new PageImpl<CheckEmphasisPeople>(new ArrayList<CheckEmphasisPeople>());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        page = checkPeopleRestService.findAll(pageable);
        for (CheckEmphasisPeople checkEmphasisPeople : page) {
            try {
                
                Long expire = df.parse(checkEmphasisPeople.getExpire()).getTime();
                Long today = new Date().getTime();
                if (expire > today) {
                    checkEmphasisPeople.setAttentionStatus("关注中");
                } else {
                    checkEmphasisPeople.setAttentionStatus("已结束");
                }
                
            } catch (ParseException e) {
            }
            convert(checkEmphasisPeople);
        }
        mv.addObject("page", page);
        mv.setViewName("flyrecord/people");
        return mv;
    }
    private final static Logger LOGGER = LoggerFactory.getLogger(CheckPeopleRestController.class);
    private void convert(CheckEmphasisPeople flyCheckRecord){
        if(flyCheckRecord.getCheckItem() == null){
            return ;
        }
        ObjectMapper om = new ObjectMapper();
        JsonNode jn = null;
        try {
            jn = om.readTree(flyCheckRecord.getCheckItem());
        } catch ( Exception e) {
            LOGGER.error("PARSE RATE ERROR:{}", flyCheckRecord.getCheckItem());
            LOGGER.error("PARSE RATE EXCEPTIONS:{}",e);
        }
        if(jn  != null && jn.get("abnormal_content") != null){
            flyCheckRecord.setCheckItem(jn.get("abnormal_content").asText());
        } else{
            flyCheckRecord.setCheckItem(null);
        }
    }
}
