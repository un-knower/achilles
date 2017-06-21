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
import com.quancheng.achilles.dao.modelwrite.FranchiseRestApproveDetail;
import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.services.MemberService;
import com.quancheng.achilles.service.services.impl.FranchiseRestaurantServiceImpl;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;

import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(path = "/ops")
public class FranchiseRestApproveDetailController extends ControllerAbstract {
    @Autowired
    OssServiceDBUtil ossServiceDBUtil;

    @Autowired
    MemberService memberService;
    @Autowired
    FranchiseRestaurantServiceImpl franchiseRestaurantServiceImpl;

    @RequestMapping(value = "/franchise/list", method = { RequestMethod.GET, RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    public ModelAndView pageFranchise(@RequestParam(required = false) String applyUserName,
            @RequestParam(required = false) String gouldId, @RequestParam(required = false) String restaurantName,
            @RequestParam(required = false) String[] cityNames, @RequestParam(required = false) String[] applyTypes,
            @RequestParam(required = false) String timeType, @RequestParam(required = false) String begin,
            @RequestParam(required = false) String end, @RequestParam(required = false) String[] finalResults,
            @RequestParam(required = false) String[] refusedReasons,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "begin", required = false) String startTime,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String endTime,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) Integer pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            Long templateId, ModelAndView mv) {
        Page<FranchiseRestApproveDetail> page = franchiseRestaurantServiceImpl.getFranchiseRest(applyUserName, gouldId,
                restaurantName, cityNames, applyTypes, timeType, begin, end, finalResults, refusedReasons,
                new PageRequest(pageNum, pageSize));
        mv.addObject("page", page);
        configTemplate(templateId, mv);
        mv.addObject("begin", startTime);
        mv.addObject("end", endTime);
        mv.addObject("applyUserName", applyUserName);
        mv.addObject("gouldId", gouldId);
        mv.addObject("restaurantName", restaurantName);
        mv.addObject("cityNames", cityNames);
        mv.addObject("applyTypes", applyTypes);
        mv.addObject("timeType", timeType);
        mv.addObject("finalResults", finalResults);
        mv.addObject("refusedReasons", refusedReasons);
        mv.addObject("cityList", memberService.listCity());
        mv.addObject("applyTypeList", new String[] { "快速通道", "普通通道" });
        mv.addObject("refusedReasonList", franchiseRestaurantServiceImpl.getAllReason());
        mv.setViewName("franchise/franchise_list");
        return mv;
    }

    @RequestMapping(value = "/franchise/export", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public BaseResponse exportFranchise(@RequestParam(required = false) String applyUserName,
            @RequestParam(required = false) String gouldId, @RequestParam(required = false) String restaurantName,
            @RequestParam(required = false) String[] cityNames, @RequestParam(required = false) String[] applyTypes,
            @RequestParam(required = false) String timeType, @RequestParam(required = false) String begin,
            @RequestParam(required = false) String end, @RequestParam(required = false) String[] finalResults,
            @RequestParam(required = false) String[] refusedReasons,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "startTime", required = false) String startTime,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "endTime", required = false) String endTime,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) Integer pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
            Long templateId, ModelAndView mv) {
        class AsyncUploadToOSS implements Runnable {
            private ModelAndView mv;
            private String username;
            public AsyncUploadToOSS(ModelAndView mv, String username) {
                this.mv = mv;
                this.username = username;
            }
            @Override
            public void run() {
                mv = pageFranchise(applyUserName, gouldId, restaurantName, cityNames, applyTypes, timeType, begin, end,
                        finalResults, refusedReasons, startTime, endTime, pageSize, pageNum, templateId, mv);
                Page<FranchiseRestApproveDetail> page = (Page<FranchiseRestApproveDetail>) mv.getModel().get("page");
                final List<AchillesDiyTemplateColumns> tempcols = templateId == null ? null
                        : achillesDiyColumnsServiceImpl.getTemplateColsByTemplate(templateId);
                AchillesDiyTemplate adt = achillesDiyColumnsServiceImpl.getTemplate(templateId);
                DownloadBuilder<FranchiseRestApproveDetail> eb = new DownloadBuilder<>(FranchiseRestApproveDetail.class, null, tempcols);
                eb.append(page.getContent());
                mv.clear();
                Pageable pageables = null;
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    mv = pageFranchise(applyUserName, gouldId, restaurantName, cityNames, applyTypes, timeType, begin,
                            end, finalResults, refusedReasons, startTime, endTime, pageables.getPageSize(), pageables.getPageNumber(), templateId, mv);
                    page = (Page<FranchiseRestApproveDetail>) mv.getModel().get("page");
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
