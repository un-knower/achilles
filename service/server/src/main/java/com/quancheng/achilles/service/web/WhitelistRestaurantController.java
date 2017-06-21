package com.quancheng.achilles.service.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.quancheng.achilles.dao.model.BaseResponse;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplate;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.modelwrite.WhiteListRestaurant;
import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.services.MemberService;
import com.quancheng.achilles.service.services.impl.WhiteListRestaurantServiceImpl;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(path = "/ops")
public class WhitelistRestaurantController extends ControllerAbstract {

    @Autowired
    OssServiceDBUtil ossServiceDBUtil;
    @Autowired
    WhiteListRestaurantServiceImpl whiteListRestaurantServiceImpl;
    @Autowired
    MemberService memberService;

    @RequestMapping(value = "/whitelist/restaurant", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView list(String applyUserName, String applyUserJobNum, String restaurantName, String[] cityIds,
            String timeType, String begin, String end, String[] applyCompanys, String[] approveTypes,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) Integer pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(required = false, defaultValue = "true") Boolean forPage, Long templateId, ModelAndView mv) {
        Page<WhiteListRestaurant> page = whiteListRestaurantServiceImpl.page(applyUserName, applyUserJobNum,
                restaurantName, cityIds, approveTypes, timeType, begin, end, applyCompanys,
                new PageRequest(pageNum, pageSize));
        mv.addObject("page", page);
        if (forPage) {
            mv.setViewName("whitelist_restaurant/whiterestaurant_list");
            configTemplate(templateId, mv);
            mv.addObject("applyUserName", applyUserName);
            mv.addObject("applyUserJobNum", applyUserJobNum);
            mv.addObject("approveTypes", approveTypes);
            mv.addObject("cityList", memberService.listCity());
            mv.addObject("companyList", whiteListRestaurantServiceImpl.getAllClients());
            mv.addObject("begin", begin);
            mv.addObject("end", end);
            mv.addObject("restaurantName", restaurantName);
            mv.addObject("cityIds", cityIds);
            mv.addObject("applyCompanys", applyCompanys);
            mv.addObject("timeType", timeType);
        }
        return mv;
    }

    @RequestMapping(value = "/whitelist/restaurant/export", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @SuppressWarnings("unchecked")
    public BaseResponse export(String applyUserName, String applyUserJobNum, String restaurantName, String[] cityIds,
            String timeType, String begin, String end, String[] applyCompanys, String[] approveTypes,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) Integer pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(required = false, defaultValue = "true") Boolean forPage, Long templateId, ModelAndView mv) {
        class AsyncUploadToOSS implements Runnable {
            private ModelAndView mv;
            private String username;
            public AsyncUploadToOSS(ModelAndView mv, String username) {
                this.mv = mv;
                this.username = username;
            }
            @Override
            public void run() {
                mv = list(applyUserName, applyUserJobNum, restaurantName, cityIds, timeType, begin, end, applyCompanys,
                        approveTypes, pageSize, pageNum, false, templateId, mv);
                Page<WhiteListRestaurant> page = (Page<WhiteListRestaurant>) mv.getModel().get("page");
                final List<AchillesDiyTemplateColumns> tempcols = templateId == null ? null
                        : achillesDiyColumnsServiceImpl.getTemplateColsByTemplate(templateId);
                AchillesDiyTemplate adt = achillesDiyColumnsServiceImpl.getTemplate(templateId);
                DownloadBuilder<WhiteListRestaurant> eb = new DownloadBuilder<>(WhiteListRestaurant.class, null, tempcols);
                eb.append(page.getContent());
                mv.clear();
                Pageable pageables = null;
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    mv = list(applyUserName, applyUserJobNum, restaurantName, cityIds, timeType, begin, end, applyCompanys,
                            approveTypes, pageables.getPageSize(), pageables.getPageNumber(), false, templateId, mv);
                    page = (Page<WhiteListRestaurant>) mv.getModel().get("page");
                    eb.append(page.getContent());
                    mv.clear();
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath,adt.getTemplateName()==null?adt.getTableName():adt.getTemplateName(), username);
                eb = null;
            }
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        new Thread(new AsyncUploadToOSS(mv, username)).start();
        return new BaseResponse();
    }
}
