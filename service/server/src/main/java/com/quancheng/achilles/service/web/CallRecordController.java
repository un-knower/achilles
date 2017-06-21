package com.quancheng.achilles.service.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.services.CallRecordService;
import com.quancheng.achilles.dao.model.BaseResponse;
import com.quancheng.achilles.dao.model.CallRecord;
import com.quancheng.achilles.service.utils.DateUtils;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;

import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(path = "/ops/callrecord")
public class CallRecordController {
    @Autowired
    OssServiceDBUtil ossServiceDBUtil;
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
    @Autowired
    CallRecordService callRecordService;

    @ResponseBody
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public BaseResponse download(
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false, defaultValue = "-1") String company,
            @ApiParam(value = "通话类型") @RequestParam(value = "type", required = false, defaultValue = "-1") String type,
            @ApiParam(value = "受理客服") @RequestParam(value = "kefuName", required = false, defaultValue = "-1") String kefuName,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            HttpServletRequest request,
            ModelAndView mv, HttpServletResponse response) throws IOException {
        class AsyncUploadToOSS implements Runnable {
            private ModelAndView mv;
            private String username;
            public AsyncUploadToOSS(ModelAndView mv, String username) {
                this.mv = mv;
                this.username = username;
            }

            @Override
            public void run() {
                DownloadBuilder<CallRecord> downloadBuild = new DownloadBuilder<>(CallRecord.class);
                mv = list(start, end, company, type, kefuName, 1000, 0, mv);
                Page<CallRecord> page = (Page) mv.getModel().get("page");
                downloadBuild.append(page.getContent());
                while (page.hasNext()) {
                    Pageable nextPageable = page.nextPageable();
                    list(start, end, company, type, kefuName, nextPageable.getPageSize(), nextPageable.getPageNumber(),
                            mv);
                    page = (Page) mv.getModel().get("page");
                    downloadBuild.append(page.getContent());
                }
                String filePath = downloadBuild.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "通话记录", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
            MediaType.TEXT_HTML_VALUE })
    public ModelAndView list(
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "时间(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false, defaultValue = "-1") String company,
            @ApiParam(value = "通话类型") @RequestParam(value = "type", required = false, defaultValue = "-1") String type,
            @ApiParam(value = "受理客服") @RequestParam(value = "kefuName", required = false, defaultValue = "-1") String kefuName,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            ModelAndView mv) {
        Date startDate = null;
        if (start != null && !start.isEmpty()) {
             startDate = DateUtils.toDate(start, DateUtils.SDF_DATE);
        }
        Date endDate = null;
        if (end != null && !end.isEmpty()) {
              endDate = DateUtils.toDate(end, DateUtils.SDF_DATE);
        }
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "recordTime", "description");
        Page<CallRecord> page = new PageImpl<CallRecord>(new ArrayList<CallRecord>());
        page = callRecordService.findAll(startDate, endDate, company, type, kefuName, pageable);
        
        mv.addObject("start", start);
        mv.addObject("end", end);
        mv.addObject("company", company);
        mv.addObject("type", type);
        mv.addObject("kefuName", kefuName);
        mv.addObject("companyList", callRecordService.listAllCompany());
        mv.addObject("typeList", callRecordService.listAllType());
        mv.addObject("kefuNameList", callRecordService.listAllKefuName());
        mv.addObject("page", page);
        mv.setViewName("callrecord/index");
        return mv;
    }
}
