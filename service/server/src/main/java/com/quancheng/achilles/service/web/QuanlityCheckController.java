package com.quancheng.achilles.service.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.dao.model.BaseResponse;
import com.quancheng.achilles.dao.model.QuanlityCheckRecord;
import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.constants.InnConstantsQualityState;
import com.quancheng.achilles.service.services.QuanlityCheckService;
import com.quancheng.achilles.service.utils.DateUtils;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;
import com.quancheng.achilles.util.CheckReasonUtil;

import io.swagger.annotations.ApiParam;

/**
 * @author pcuser 2016年9月21日 上午10:50:50
 * @version $Id: QuanlityCheckController.java, v 0.0.1 2016年9月21日 上午10:50:50
 *          jianglijun Exp $
 */
@Controller
@RequestMapping(path = "/ops/quanlity")
public class QuanlityCheckController {

    @Autowired
    QuanlityCheckService quanlityCheckService;

    @ResponseBody
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public BaseResponse download(
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "检查原因") @RequestParam(value = "checkreason", required = false ) String checkreason,
            @ApiParam(value = "是否异常") @RequestParam(value = "state", required = false ) String[] state,
            @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false, defaultValue = "-1") String company,
            @ApiParam(value = "检查类型") @RequestParam(value = "checkType", required = false, defaultValue = "-1") String checkType,
            @ApiParam(value = "检查方式") @RequestParam(value = "checkMode", required = false, defaultValue = "-1") String checkMode,
            @ApiParam(value = "城市") @RequestParam(value = "cityName", required = false, defaultValue = "-1") String[] cityName,
            @ApiParam(value = "餐厅名") @RequestParam(value = "shangpuName", required = false, defaultValue = "") String shangpuName,
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

                DownloadBuilder<QuanlityCheckRecord> downloadBuilder = new DownloadBuilder<>(QuanlityCheckRecord.class);
                mv = list(start, end,state,checkreason, company, checkType, checkMode, cityName, shangpuName, 1000, 0, mv);
                Page<QuanlityCheckRecord> page = (Page<QuanlityCheckRecord>) mv.getModel().get("page");
                downloadBuilder.append(page.getContent());
                while (page.hasNext()) {
                    Pageable pageable = page.nextPageable();
                    mv = list(start, end,state,checkreason,company, checkType, checkMode, cityName, shangpuName, pageable.getPageSize(),
                            pageable.getPageNumber(), mv);

                    page = (Page<QuanlityCheckRecord>) mv.getModel().get("page");
                    downloadBuilder.append(page.getContent());
                }
                String filePath = downloadBuilder.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "飞检记录", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }

    @Autowired
    OssServiceDBUtil ossServiceDBUtil;
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
            MediaType.TEXT_HTML_VALUE })
    public ModelAndView list(
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "是否异常") @RequestParam(value = "state", required = false ) String[] state,
            @ApiParam(value = "检查原因") @RequestParam(value = "checkreason", required = false ) String checkreason,
            @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false, defaultValue = "-1") String company,
            @ApiParam(value = "检查类型") @RequestParam(value = "checkType", required = false, defaultValue = "-1") String checkType,
            @ApiParam(value = "检查方式") @RequestParam(value = "checkMode", required = false, defaultValue = "-1") String checkMode,
            @ApiParam(value = "城市") @RequestParam(value = "cityName", required = false, defaultValue = "-1") String[] cityName,
            @ApiParam(value = "餐厅名") @RequestParam(value = "shangpuName", required = false, defaultValue = "") String shangpuName,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            ModelAndView mv) {

        Date startDate =null ;
        Date endDate = null;
        if(start != null && !start.isEmpty()){
            startDate =DateUtils.toDate(start, DateUtils.SDF_DATE);
        }
        if(end != null && !end.isEmpty()){
            endDate =DateUtils.toDate(end, DateUtils.SDF_DATE);
            Calendar cl = Calendar.getInstance();
            cl.setTime(endDate);
            cl.add(Calendar.DAY_OF_YEAR, 1);
            endDate=cl.getTime();
        }
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "checkDate");
        Page<QuanlityCheckRecord> page = new PageImpl<QuanlityCheckRecord>(new ArrayList<QuanlityCheckRecord>());
        page = quanlityCheckService.findAll(  state,checkreason, startDate, endDate, company, checkType, checkMode, cityName, shangpuName,
                pageable);
        Map<String,String> map = new HashMap<>();
        for( InnConstantsQualityState  states: InnConstantsQualityState.values()) {
            map.put(states .value, states .name);
        }
        for (QuanlityCheckRecord flyCheckRecord : page) {
            String vl = map.get(flyCheckRecord.getStatus());
            flyCheckRecord.setStatus(vl == null ?"未定义交易状态":vl);
            flyCheckRecord.setAbnormalType(CheckReasonUtil.getReasonType(flyCheckRecord.getCheckItem()) );
            flyCheckRecord.setCheckItem(CheckReasonUtil.convertReasonContent(flyCheckRecord.getCheckItem()) );
        }
        mv.addObject("start", start);
        mv.addObject("end", end);
        mv.addObject("statemap", map);
        mv.addObject("company", company);
        mv.addObject("checkType", checkType);
        mv.addObject("checkMode", checkMode);
        mv.addObject("cityName", cityName);
        mv.addObject("state",state == null ? new String[0]:state);
        mv.addObject("shangpuName", shangpuName);
        mv.addObject("companyList", quanlityCheckService.listAllCompany());
        mv.addObject("checkTypeList", quanlityCheckService.listAllCheckType());
        mv.addObject("checkModeList", quanlityCheckService.listAllCheckMode());
        mv.addObject("cityNameList", quanlityCheckService.listAllCityName());
        mv.addObject("page", page);
        mv.setViewName("flyrecord/flycheck");
        return mv;
    }
    private final static Logger LOGGER = LoggerFactory.getLogger(QuanlityCheckController.class);
}
