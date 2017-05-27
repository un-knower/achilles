package com.quancheng.achilles.service.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import com.quancheng.achilles.service.constants.InnConstantRestStateEnum;
import com.quancheng.achilles.service.constants.InnConstantsTrain;
import com.quancheng.achilles.service.services.RestaurantTrainLogService;
import com.quancheng.achilles.service.services.RestaurantVisitLogService;
import com.quancheng.achilles.dao.model.BaseResponse;
import com.quancheng.achilles.dao.model.RestaurantTrainLog;
import com.quancheng.achilles.dao.model.RestaurantVisitLog;
import com.quancheng.achilles.service.utils.DateUtils;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;

import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(path = "/ops/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantTrainLogService restaurantTrainLogService;

    @Autowired
    RestaurantVisitLogService restaurantVisitLogService;
    //@Value("${achilles.restaurant.sources}")
    //String restaurantSources;

    /**
     * 餐厅培训记录
     */
    @RequestMapping(path = "/trainlog", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
            MediaType.TEXT_HTML_VALUE })
    public ModelAndView listRestaurantTrainLog(
            @ApiParam(value = "培训日期(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "培训日期(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false, defaultValue = "-1") String company,
            @ApiParam(value = "餐厅名") @RequestParam(value = "restaurantName", required = false, defaultValue = "") String restaurantName,
            @ApiParam(value = "城市") @RequestParam(value = "city[]", required = false ) String[] city,
            @ApiParam(value = "维护销售") @RequestParam(value = "salesName", required = false, defaultValue = "-1") String salesName,
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
        }
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "trainDate");
        Page<RestaurantTrainLog> page = new PageImpl<RestaurantTrainLog>(new ArrayList<RestaurantTrainLog>());
        page = restaurantTrainLogService.findAll(startDate, endDate, company, city, restaurantName, salesName,
                pageable);
        
        mv.addObject("start", start);
        mv.addObject("end", end);
        mv.addObject("company", company);
        mv.addObject("restaurantName", restaurantName);
        mv.addObject("city", city==null?new String[]{}:city);
        mv.addObject("salesName", salesName);
        mv.addObject("cityList", restaurantTrainLogService.listAllCityName());
        mv.addObject("salesNameList", restaurantTrainLogService.listAllSalesName());
        mv.addObject("page", page);
        convert(mv);
        mv.setViewName("restaurant/trainlog");
        return mv;
    }
    private void convert(ModelAndView mv){
        Map<Object,Object> params1 = new HashMap<>();
        params1.put( InnConstantsTrain.INN_TRAIN_TYPE_FIRST.value,InnConstantsTrain.INN_TRAIN_TYPE_FIRST.name);
        params1.put( InnConstantsTrain.INN_TRAIN_TYPE_SECOND.value,InnConstantsTrain.INN_TRAIN_TYPE_SECOND.name);
        mv.addObject("trainType", params1);
        
        Map<Object,Object> params2 = new HashMap<>();
        params2.put( InnConstantsTrain.INN_TRAIN_CP_TYPE_FIRST.value,InnConstantsTrain.INN_TRAIN_CP_TYPE_FIRST.name);
        params2.put( InnConstantsTrain.INN_TRAIN_CP_TYPE_SECOND.value,InnConstantsTrain.INN_TRAIN_CP_TYPE_SECOND.name);
        params2.put( InnConstantsTrain.INN_TRAIN_CP_TYPE_THRID.value,InnConstantsTrain.INN_TRAIN_CP_TYPE_THRID.name);
        mv.addObject("cpType", params2);
        
        Map<Object,Object> params3 = new HashMap<>();
        params3.put( InnConstantsTrain.INN_TRAIN_TC_TYPE_FIRST.value,InnConstantsTrain.INN_TRAIN_TC_TYPE_FIRST.name);
        params3.put( InnConstantsTrain.INN_TRAIN_TC_TYPE_SECOND.value,InnConstantsTrain.INN_TRAIN_TC_TYPE_SECOND.name);
        params3.put( InnConstantsTrain.INN_TRAIN_TC_TYPE_THRID.value,InnConstantsTrain.INN_TRAIN_TC_TYPE_THRID.name);
        mv.addObject("tcType", params3);
        
        Map<Object,Object> params4 = new HashMap<>();
        params4.put( InnConstantsTrain.INN_TRAIN_TT_TYPE_FIRST.value,InnConstantsTrain.INN_TRAIN_TT_TYPE_FIRST.name);
        params4.put( InnConstantsTrain.INN_TRAIN_TT_TYPE_SECOND.value,InnConstantsTrain.INN_TRAIN_TT_TYPE_SECOND.name);
        params4.put( InnConstantsTrain.INN_TRAIN_TT_TYPE_THRID.value,InnConstantsTrain.INN_TRAIN_TT_TYPE_THRID.name);
        mv.addObject("ttType", params4);
        
        Map<Object,Object> params5 = new HashMap<>();
        params5.put( InnConstantsTrain.INN_TRAIN_BQ_TYPE_FIRST.value,InnConstantsTrain.INN_TRAIN_BQ_TYPE_FIRST.name);
        params5.put( InnConstantsTrain.INN_TRAIN_BQ_TYPE_SECOND.value,InnConstantsTrain.INN_TRAIN_BQ_TYPE_SECOND.name);
        mv.addObject("bqType", params5);
    }
    /**
     * 餐厅培训记录
     * 
     * @throws IOException
     */
    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(path = "/trainlog/export", method = RequestMethod.GET)
    public BaseResponse listRestaurantTrainLogExport(
            @ApiParam(value = "培训日期(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "培训日期(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false, defaultValue = "-1") String company,
            @ApiParam(value = "餐厅名") @RequestParam(value = "restaurantName", required = false, defaultValue = "") String restaurantName,
            @ApiParam(value = "城市") @RequestParam(value = "city[]", required = false ) String[] city,
            @ApiParam(value = "维护销售") @RequestParam(value = "salesName", required = false, defaultValue = "-1") String salesName,
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
                mv = listRestaurantTrainLog(start, end, company, restaurantName, city, salesName, 5000, 0, mv);
                Page<RestaurantTrainLog> page = (Page<RestaurantTrainLog>) mv.getModel().get("page");
                Map<String,Map<Object,Object>> params = new HashMap<>();
                params.put("trainType", (Map<Object, Object>) mv.getModel().get("trainType"));
                params.put("cpType", (Map<Object, Object>) mv.getModel().get("cpType"));
                params.put("tcType", (Map<Object, Object>) mv.getModel().get("tcType"));
                params.put("ttType", (Map<Object, Object>) mv.getModel().get("ttType"));
                params.put("bqType", (Map<Object, Object>) mv.getModel().get("bqType"));
                DownloadBuilder<RestaurantTrainLog> eb = new DownloadBuilder<>(RestaurantTrainLog.class,params);
                eb.append(page.getContent());
                Pageable pageables = null;
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    mv = listRestaurantTrainLog(start, end, company, restaurantName, city, salesName,
                            pageables.getPageSize(), pageables.getPageNumber(), mv);
                    page = (Page<RestaurantTrainLog>) mv.getModel().get("page");
                    eb.append(page.getContent());
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "培训记录", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }

    @Autowired
    OssServiceDBUtil ossServiceDBUtil;
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    /**
     * 餐厅拜访记录
     */
    @RequestMapping(path = "/visitlog", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
            MediaType.TEXT_HTML_VALUE })
    public ModelAndView listRestaurantVisitLog(
            @ApiParam(value = "拜访日期(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "拜访日期(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "餐厅状态") @RequestParam(value = "restStatus", required = false) String restStatus,
            @ApiParam(value = "餐厅来源") @RequestParam(value = "type", required = false, defaultValue = "-1") String type,
            @ApiParam(value = "餐厅名") @RequestParam(value = "restaurantName", required = false, defaultValue = "") String restaurantName,
            @ApiParam(value = "城市") @RequestParam(value = "city[]", required = false ) String[] city,
            @ApiParam(value = "维护销售") @RequestParam(value = "salesName", required = false, defaultValue = "-1") String salesName,
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
        }
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "startTime");
        Page<RestaurantVisitLog> page = new PageImpl<RestaurantVisitLog>(new ArrayList<RestaurantVisitLog>());
        page = restaurantVisitLogService.findAll(startDate, endDate, restStatus,type, city, restaurantName, salesName, pageable);
        mv.addObject("start", start);
        mv.addObject("end", end);
        mv.addObject("type", type);
        mv.addObject("restStatus", restStatus);
        mv.addObject("restState",InnConstantRestStateEnum.parseSources());
        mv.addObject("restaurantName", restaurantName);
        mv.addObject("city", city==null?new String[]{}:city);
        mv.addObject("salesName", salesName);
//        mv.addObject("sourceMap", InnConstantsRecommandRs.parseSources());
        mv.addObject("cityList", restaurantVisitLogService.listAllCityName());
        mv.addObject("salesNameList", restaurantVisitLogService.listAllSalesName());
        mv.addObject("page", page);
        mv.setViewName("restaurant/visitlog");
        return mv;
    }

    /**
     * 餐厅拜访记录
     * 
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(path = "/visitlog/export", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse listRestaurantVisitLogExport(
            @ApiParam(value = "拜访日期(yyyy-MM-dd)") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "拜访日期(yyyy-MM-dd)") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "餐厅状态") @RequestParam(value = "restStatus", required = false) String restStatus,
            @ApiParam(value = "餐厅来源") @RequestParam(value = "type", required = false, defaultValue = "-1") String type,
            @ApiParam(value = "餐厅名") @RequestParam(value = "restaurantName", required = false, defaultValue = "") String restaurantName,
            @ApiParam(value = "城市") @RequestParam(value = "city[]", required = false ) String[] city,
            @ApiParam(value = "维护销售") @RequestParam(value = "salesName", required = false, defaultValue = "-1") String salesName,
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
                mv = listRestaurantVisitLog(start, end, restStatus,type, restaurantName, city, salesName, 5000, 0, mv);
                Page<RestaurantVisitLog> page = (Page<RestaurantVisitLog>) mv.getModel().get("page");
                DownloadBuilder<RestaurantVisitLog> eb = new DownloadBuilder<>(RestaurantVisitLog.class);
                eb.append(page.getContent());
                Pageable pageables = null;
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    mv = listRestaurantVisitLog(start, end,restStatus, type, restaurantName, city, salesName,
                            pageables.getPageSize(), pageables.getPageNumber(), mv);
                    page = (Page<RestaurantVisitLog>) mv.getModel().get("page");
                    eb.append(page.getContent());
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "拜访记录", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }
}
