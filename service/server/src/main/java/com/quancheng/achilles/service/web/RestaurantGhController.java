package com.quancheng.achilles.service.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.constants.InnConstantRestStateEnum;
import com.quancheng.achilles.service.constants.InnConstantsRecommandRs;
import com.quancheng.achilles.service.services.RecommandResServiceImpl;
import com.quancheng.achilles.service.services.RestaurantServiceImpl;
import com.quancheng.achilles.dao.model.BaseResponse;
import com.quancheng.achilles.dao.model.RestaurantGonghai;
import com.quancheng.achilles.dao.model.RestaurantRecommender;
import com.quancheng.achilles.service.utils.DownloadBuilder;
import com.quancheng.achilles.service.utils.EnumDownLoadModel;
import com.quancheng.achilles.service.utils.OssServiceDBUtil;
import com.quancheng.achilles.service.utils.SynDownload;

import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-09-05T09:42:08.356Z")
@Controller
public class RestaurantGhController {

    @Autowired
    OssServiceDBUtil ossServiceDBUtil;
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    @Autowired
    RestaurantServiceImpl restService;
    @Autowired
    RecommandResServiceImpl recommandService;

    private final static int EXPORTLIMIT = 5000;
//    @Value("${achilles.restaurant.sources}")
//    String restaurantSources;

    @Value("${inn.restaurant.priority}")
    String restaurantPriority;

    @Value("${inn.restaurant.manage.type}")
    String restaurantManageType;

    @RequestMapping(value = "/ops/restaurant/gh_index", method = { RequestMethod.GET }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ModelAndView callRecordListGet(
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            ModelAndView mv) {
        mv.setViewName("restaurant/restaurant_gh");
        RestaurantGonghai rg = new RestaurantGonghai();
        Pageable pageable = new PageRequest(pageNum, pageSize);
        Page<RestaurantGonghai> list = restService.pageGhRest(rg, null, null, null, null, pageable);
        addCommonAttr(mv);
        mv.addObject("page", list);
        return mv;
    }

    /***
     * 导出demo
     * 
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/ops/restaurant/export", method = { RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public BaseResponse restaurantGhExport(
            @ApiParam(value = "餐厅名称") @RequestParam(value = "restName", required = false) String restName,
            @ApiParam(value = "城市") @RequestParam(value = "city", required = false) String city,
            @ApiParam(value = "所属企业") @RequestParam(value = "ownCompany", required = false) String ownCompany,
            @ApiParam(value = "餐厅状态") @RequestParam(value = "restStatus", required = false) String restStatus,
            @ApiParam(value = "餐厅来源") @RequestParam(value = "restResource", required = false) String restResource,
            @ApiParam(value = "公海任务名") @RequestParam(value = "ghTaskName", required = false) String ghTaskName,
            @ApiParam(value = "推荐人邮箱") @RequestParam(value = "recommendersEmail", required = false) String recommendersEmail,
            @ApiParam(value = "推荐企业") @RequestParam(value = "recommendersCompany", required = false) String recommendersCompany,
            @ApiParam(value = "项目名") @RequestParam(value = "projectName", required = false) String projectName,
            @ApiParam(value = "入库时间") @RequestParam(value = "inStoreDateStart", required = false) String inStoreDateStart,
            @ApiParam(value = "入库时间") @RequestParam(value = "inStoreDateEnd", required = false) String inStoreDateEnd,
            @ApiParam(value = "下线时间") @RequestParam(value = "shelfTimeStart", required = false) String shelfTimeStart,
            @ApiParam(value = "下线时间") @RequestParam(value = "shelfTimeEnd", required = false) String shelfTimeEnd,
            @ApiParam(value = "是否支持外卖") @RequestParam(value = "supportTakeout", required = false) String supportTakeout,
            @ApiParam(value = "是否支持订座") @RequestParam(value = "supportReverse", required = false) String supportReverse,
            @ApiParam(value = "维护销售") @RequestParam(value = "sales", required = false) String sales,
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
                mv = restaurantGh(EXPORTLIMIT, 0, restName, city, ownCompany, restStatus, restResource, ghTaskName,
                        recommendersEmail, recommendersCompany, projectName, inStoreDateStart, inStoreDateEnd,
                        shelfTimeStart, shelfTimeEnd, supportTakeout, supportReverse, sales, mv);
                Page<RestaurantGonghai> page = (Page<RestaurantGonghai>) mv.getModel().get("page");
                Map<String, Map<Object, Object>> convert = new HashMap<>();
//                convert.put("restaurantSources", parseSources());
                Map<Object, Object> restState = new HashMap<>();
                restState.put("0", "正常");
                restState.put("1", "禁用");
                convert.put("restaurantState", restState);
                convert.put("priority", parsePriority());
                Map<Object, Object> ifelse = new HashMap<>();
                ifelse.put("0", "是");
                ifelse.put("1", "否");
                convert.put("supportReserve", ifelse);
                convert.put("supportTakeoutOfFood", ifelse);
                convert.put("manageType", parseManageType());
                convert.put("gonghaiStatus", InnConstantRestStateEnum.parseSources());
//                convert.put("recommendsCompany", parseSources());
                DownloadBuilder<RestaurantGonghai> eb = new DownloadBuilder<>(RestaurantGonghai.class, convert);
                eb.append(page.getContent());
                mv.getModel().remove("page");
                Pageable pageables = null;
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    mv = restaurantGh(pageables.getPageSize(), pageables.getPageNumber(), restName, city, ownCompany,
                            restStatus, restResource, ghTaskName, recommendersEmail, recommendersCompany, projectName,
                            inStoreDateStart, inStoreDateEnd, shelfTimeStart, shelfTimeEnd, supportTakeout,
                            supportReverse, sales, mv);
                    page = (Page<RestaurantGonghai>) mv.getModel().get("page");
                    eb.append(page.getContent());
                    mv.getModel().remove("page");
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "公海餐厅", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }

    @RequestMapping(value = "/ops/restaurant/gh", method = { RequestMethod.POST }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ModelAndView restaurantGh(
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            @ApiParam(value = "餐厅名称") @RequestParam(value = "restName", required = false) String restName,
            @ApiParam(value = "城市") @RequestParam(value = "city", required = false) String city,
            @ApiParam(value = "所属企业") @RequestParam(value = "ownCompany", required = false) String ownCompany,
            @ApiParam(value = "餐厅状态") @RequestParam(value = "restStatus", required = false) String restStatus,
            @ApiParam(value = "餐厅来源") @RequestParam(value = "restResource", required = false) String restResource,
            @ApiParam(value = "公海任务名") @RequestParam(value = "ghTaskName", required = false) String ghTaskName,
            @ApiParam(value = "推荐人邮箱") @RequestParam(value = "recommendersEmail", required = false) String recommendersEmail,
            @ApiParam(value = "推荐企业") @RequestParam(value = "recommendersCompany", required = false) String recommendersCompany,
            @ApiParam(value = "项目名") @RequestParam(value = "projectName", required = false) String projectName,
            @ApiParam(value = "入库时间") @RequestParam(value = "inStoreDateStart", required = false) String inStoreDateStart,
            @ApiParam(value = "入库时间") @RequestParam(value = "inStoreDateEnd", required = false) String inStoreDateEnd,
            @ApiParam(value = "下线时间") @RequestParam(value = "shelfTimeStart", required = false) String shelfTimeStart,
            @ApiParam(value = "下线时间") @RequestParam(value = "shelfTimeEnd", required = false) String shelfTimeEnd,
            @ApiParam(value = "是否支持外卖") @RequestParam(value = "supportTakeout", required = false) String supportTakeout,
            @ApiParam(value = "是否支持订座") @RequestParam(value = "supportReverse", required = false) String supportReverse,
            @ApiParam(value = "维护销售") @RequestParam(value = "sales", required = false) String sales, ModelAndView mv) {
        RestaurantGonghai rg = new RestaurantGonghai();
        rg.setStoreName(restName);
        rg.setCity("-1".equals(city) ? null : city);
        rg.setOwnCompanysId("-1".equals(ownCompany) ? null : ownCompany);
        rg.setRestaurantState("-1".equals(restStatus) ? null : restStatus);
        rg.setRestaurantSources("-1".equals(restResource) ? null : restResource);
        rg.setGhTaskName(ghTaskName);
        rg.setRecommendsEmails(recommendersEmail);
        rg.setRecommendsCompany("-1".equals(recommendersCompany) ? null : recommendersCompany);
        rg.setProjectNames(projectName);
        rg.setSupportTakeoutOfFood("-1".equals(supportTakeout) ? null : supportTakeout);
        rg.setSupportReserve("-1".equals(supportReverse) ? null : supportReverse);
        rg.setSalesName("-1".equals(sales) ? null : sales);
        Page<RestaurantGonghai> page = restService.pageGhRest(rg, inStoreDateStart, inStoreDateEnd, shelfTimeStart,
                shelfTimeEnd, new PageRequest(pageNum, pageSize));
        mv.addObject("page", page);
        mv.addObject("restName", restName);
        mv.addObject("city", toSet(city));
        mv.addObject("ownCompany", ownCompany !=null?Long.parseLong(ownCompany):ownCompany);
        mv.addObject("restStatus", restStatus);
        mv.addObject("restResource", restResource);
        mv.addObject("ghTaskName", ghTaskName);
        mv.addObject("recommendersEmail", recommendersEmail);
        mv.addObject("recommendersCompany", recommendersCompany);
        mv.addObject("projectName", projectName);
        mv.addObject("inStoreDateStart", inStoreDateStart);
        mv.addObject("inStoreDateEnd", inStoreDateEnd);
        mv.addObject("shelfTimeStart", shelfTimeStart);
        mv.addObject("shelfTimeEnd", shelfTimeEnd);
        mv.addObject("supportTakeout", supportTakeout);
        mv.addObject("supportReverse", supportReverse);
        mv.addObject("sales", sales);
        addCommonAttr(mv);
        mv.setViewName("restaurant/restaurant_gh");
        return mv;
    }
	/**
	 * 上架
	 * @param pageSize
	 * @param pageNum
	 * @param columnTime
	 * @param ownCompany
	 * @param start
	 * @param end
	 * @param mv
	 * @return
	 */
    @RequestMapping(value = "/ops/restaurant/online", method = { RequestMethod.GET }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ModelAndView restOnline(
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            @ApiParam(value = "时间字段") @RequestParam(value = "columnTime", required = false) String columnTime,
            @ApiParam(value = "所属企业") @RequestParam(value = "ownCompany", required = false) String ownCompany,
            @ApiParam(value = "上线时间") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "上线时间") @RequestParam(value = "end", required = false) String end, ModelAndView mv) {
        RestaurantGonghai rg = new RestaurantGonghai();
        rg.setRestaurantState("0");
        rg.setOwnCompanysId("-1".equals(ownCompany) ? null : ownCompany);
        Page<RestaurantGonghai> list = restService.pageOnlineRest(rg, columnTime, start, end,
                new PageRequest(pageNum, pageSize));
        mv.addObject("page", list);
        mv.addObject("columnTime", columnTime);
        mv.addObject("start", start);
        mv.addObject("end", end);
        mv.addObject("ownCompany", ownCompany !=null?Long.parseLong(ownCompany):ownCompany);
        addCommonAttr(mv);
        mv.setViewName("restaurant/restaurant_online");
        return mv;
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ops/restaurant/online/export", method = { RequestMethod.GET })
    public BaseResponse restOnlineExport(
            @ApiParam(value = "时间字段") @RequestParam(value = "columnTime", required = false) String columnTime,
            @ApiParam(value = "所属企业") @RequestParam(value = "ownCompany", required = false) String ownCompany,
            @ApiParam(value = "上线时间") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "上线时间") @RequestParam(value = "end", required = false) String end, ModelAndView mv,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        class AsyncUploadToOSS implements Runnable {
            private ModelAndView mv;
            private String username;

            public AsyncUploadToOSS(ModelAndView mv, String username) {
                this.mv = mv;
                this.username = username;
            }

            @Override
            public void run() {

                mv = restOnline(EXPORTLIMIT, 0, columnTime,ownCompany, start, end, mv);
                Page<RestaurantGonghai> page = (Page<RestaurantGonghai>) mv.getModel().get("page");
                DownloadBuilder<RestaurantGonghai> eb = new DownloadBuilder<>(RestaurantGonghai.class);
                eb.append(page.getContent());
                Pageable pageables = null;
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    mv = restOnline(pageables.getPageSize(), pageables.getPageNumber(), columnTime, ownCompany, start, end, mv);
                    page = (Page<RestaurantGonghai>) mv.getModel().get("page");
                    eb.append(page.getContent());
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "上架餐厅", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }

    /**
     * 在线餐厅
     * @param pageSize
     * @param pageNum
     * @param city
     * @param ownCompany
     * @param supportTakeout
     * @param supportReverse
     * @param columnTime
     * @param start
     * @param end
     * @param mv
     * @return
     */
    @RequestMapping(value = "/ops/restaurant/ol", method = { RequestMethod.GET }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ModelAndView restOl(
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            @ApiParam(value = "城市") @RequestParam(value = "city", required = false) String city,
            @ApiParam(value = "所属企业") @RequestParam(value = "ownCompany", required = false) String ownCompany,
            @ApiParam(value = "是否支持外卖") @RequestParam(value = "supportTakeout", required = false) String supportTakeout,
            @ApiParam(value = "是否支持订座") @RequestParam(value = "supportReverse", required = false) String supportReverse,
            @ApiParam(value = "时间字段") @RequestParam(value = "columnTime", required = false) String columnTime,
            @ApiParam(value = "上线时间") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "上线时间") @RequestParam(value = "end", required = false) String end, ModelAndView mv) {
        RestaurantGonghai rg = new RestaurantGonghai();
        rg.setCity("-1".equals(city) ? null : city);
        rg.setSupportTakeoutOfFood("-1".equals(supportTakeout) ? null : supportTakeout);
        rg.setSupportReserve("-1".equals(supportReverse) ? null : supportReverse);
        rg.setOwnCompanysId("-1".equals(ownCompany) ? null : ownCompany);
        Page<RestaurantGonghai> list = restService.pageOlRest(rg, columnTime, start, end,
                new PageRequest(pageNum, pageSize));
        mv.addObject("page", list);
        mv.addObject("city", toSet(city));
        mv.addObject("ownCompany", ownCompany);
        mv.addObject("columnTime", columnTime);
        mv.addObject("start", start);
        mv.addObject("end", end);
        mv.addObject("supportTakeout", supportTakeout);
        mv.addObject("supportReverse", supportReverse);
        addCommonAttr(mv);
        mv.setViewName("restaurant/restaurant_ol");
        return mv;
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ops/restaurant/ol/export", method = { RequestMethod.GET })
    public BaseResponse restOlExport(
            @ApiParam(value = "城市") @RequestParam(value = "city", required = false) String city,
            @ApiParam(value = "所属企业") @RequestParam(value = "ownCompany", required = false) String ownCompany,
            @ApiParam(value = "是否支持外卖") @RequestParam(value = "supportTakeout", required = false) String supportTakeout,
            @ApiParam(value = "是否支持订座") @RequestParam(value = "supportReverse", required = false) String supportReverse,
            @ApiParam(value = "时间字段") @RequestParam(value = "columnTime", required = false) String columnTime,
            @ApiParam(value = "上线时间") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "上线时间") @RequestParam(value = "end", required = false) String end, ModelAndView mv,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        class AsyncUploadToOSS implements Runnable {
            private ModelAndView mv;
            private String username;

            public AsyncUploadToOSS(ModelAndView mv, String username) {
                this.mv = mv;
                this.username = username;
            }

            @Override
            public void run() {
                mv = restOl(EXPORTLIMIT, 0, city, ownCompany, supportTakeout,supportReverse,columnTime, start, end, mv);
                Page<RestaurantGonghai> page = (Page<RestaurantGonghai>) mv.getModel().get("page");
                Map<String, Map<Object, Object>> convert = new HashMap<>();
//                convert.put("restaurantSources", parseSources());
                DownloadBuilder<RestaurantGonghai> eb = new DownloadBuilder<>(RestaurantGonghai.class, convert);
                eb.append(page.getContent());
                Pageable pageables = null;
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    mv = restOl(pageables.getPageSize(), pageables.getPageNumber(), city, ownCompany,supportTakeout,supportReverse, columnTime, start,
                            end, mv);
                    page = (Page<RestaurantGonghai>) mv.getModel().get("page");
                    eb.append(page.getContent());
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "在线餐厅", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }
	/**
	 * 下架
	 * @param pageSize
	 * @param pageNum
	 * @param columnTime
	 * @param start
	 * @param end
	 * @param ownCompany
	 * @param supportTakeout
	 * @param supportReverse
	 * @param mv
	 * @return
	 */
    @RequestMapping(value = "/ops/restaurant/shelf", method = { RequestMethod.GET }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ModelAndView restShelf(
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            @ApiParam(value = "时间字段") @RequestParam(value = "columnTime", required = false) String columnTime,
            @ApiParam(value = "开始时间") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "结束时间") @RequestParam(value = "end", required = false) String end, 
            @ApiParam(value = "所属企业") @RequestParam(value = "ownCompany", required = false) String ownCompany,
            @ApiParam(value = "是否支持外卖") @RequestParam(value = "supportTakeout", required = false) String supportTakeout,
            @ApiParam(value = "是否支持订座") @RequestParam(value = "supportReverse", required = false) String supportReverse,
            ModelAndView mv) {
        RestaurantGonghai rg = new RestaurantGonghai();
        rg.setRestaurantState("1");
        rg.setOwnCompanysId("-1".equals(ownCompany) ? null : ownCompany);
        rg.setSupportTakeoutOfFood("-1".equals(supportTakeout) ? null : supportTakeout);
        rg.setSupportReserve("-1".equals(supportReverse) ? null : supportReverse);
        Page<RestaurantGonghai> list = restService.pageOlRest(rg, columnTime, start, end,
                new PageRequest(pageNum, pageSize));
        mv.addObject("page", list);
        mv.addObject("columnTime", columnTime);
        mv.addObject("start", start);
        mv.addObject("end", end);
        mv.addObject("supportTakeout", supportTakeout);
        mv.addObject("supportReverse", supportReverse);
        addCommonAttr(mv);
        mv.setViewName("restaurant/restaurant_shelf");
        return mv;
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/ops/restaurant/shelf/export", method = { RequestMethod.GET })
    public BaseResponse restShelfExport(
            @ApiParam(value = "时间字段") @RequestParam(value = "columnTime", required = false) String columnTime,
            @ApiParam(value = "上线时间") @RequestParam(value = "start", required = false) String start,
            @ApiParam(value = "上线时间") @RequestParam(value = "end", required = false) String end,
            @ApiParam(value = "所属企业") @RequestParam(value = "ownCompany", required = false) String ownCompany,
            @ApiParam(value = "是否支持外卖") @RequestParam(value = "supportTakeout", required = false) String supportTakeout,
            @ApiParam(value = "是否支持订座") @RequestParam(value = "supportReverse", required = false) String supportReverse,
            ModelAndView mv,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        class AsyncUploadToOSS implements Runnable {
            private ModelAndView mv;
            private String username;

            public AsyncUploadToOSS(ModelAndView mv, String username) {
                this.mv = mv;
                this.username = username;
            }

            @Override
            public void run() {
                mv = restShelf(EXPORTLIMIT, 0, columnTime, start, end, ownCompany,supportTakeout,supportReverse,mv);
                Page<RestaurantGonghai> page = (Page<RestaurantGonghai>) mv.getModel().get("page");
                DownloadBuilder<RestaurantGonghai> eb = new DownloadBuilder<>(RestaurantGonghai.class);
                eb.append(page.getContent());
                Pageable pageables = null;
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    mv = restShelf(pageables.getPageSize(), pageables.getPageNumber(), columnTime, start, end,ownCompany, supportTakeout,supportReverse,mv);
                    page = (Page<RestaurantGonghai>) mv.getModel().get("page");
                    eb.append(page.getContent());
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "下架餐厅", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }

    @RequestMapping(value = "/ops/restaurant/recommenders", method = { RequestMethod.GET }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ModelAndView recommenders(
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            @ApiParam(value = "城市") @RequestParam(value = "city[]", required = false) String[] city,
            @ApiParam(value = "餐厅来源") @RequestParam(value = "restResource", required = false) String restResource,
            @ApiParam(value = "餐厅名") @RequestParam(value = "restaurantName", required = false) String restaurantName,
            @ApiParam(value = "推荐方式") @RequestParam(value = "recommandMethd", required = false) String recommandMethd,
            @ApiParam(value = "推荐时间") @RequestParam(value = "start", required = false) String shelfTimeStart,
            @ApiParam(value = "推荐时间") @RequestParam(value = "end", required = false) String shelfTimeEnd,
            ModelAndView mv) {
        RestaurantRecommender rr = new RestaurantRecommender();
        rr.setRecommandMethod("-1".equals(recommandMethd) ? null : recommandMethd);
        rr.setRestaurantName(restaurantName);
        rr.setRecommendCompany("-1".equals(restResource) ? null : restResource);
        Page<RestaurantRecommender> page = recommandService.pageRRRest(rr,city, shelfTimeStart, shelfTimeEnd,
                new PageRequest(pageNum, pageSize));
        mv.addObject("page", page);
        mv.setViewName("restaurant/recommenders");
//        mv.addObject("restResourceList", parseSources());
        mv.addObject("city", city ==null? new String[]{}:city);
        mv.addObject("restaurantName", restaurantName);
        mv.addObject("recommandMethod", recommandMethd);
        mv.addObject("restResource", restResource);
        mv.addObject("start", shelfTimeStart);
        mv.addObject("end", shelfTimeEnd);
        mv.addObject("cityList", restService.queryAllCitys());
        return mv;
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ops/restaurant/recommenders/export", method = { RequestMethod.GET })
    public BaseResponse recommendersExport(@ApiParam(value = "城市") @RequestParam(value = "city[]", required = false) String[] city,
            @ApiParam(value = "餐厅来源") @RequestParam(value = "restResource", required = false) String restResource,
            @ApiParam(value = "推荐方式") @RequestParam(value = "recommandMethd", required = false) String recommandMethd,
            @ApiParam(value = "餐厅名") @RequestParam(value = "restaurantName", required = false) String restaurantName,
            @ApiParam(value = "推荐时间") @RequestParam(value = "start", required = false) String shelfTimeStart,
            @ApiParam(value = "推荐时间") @RequestParam(value = "end", required = false) String shelfTimeEnd,
            ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws IOException {
        class AsyncUploadToOSS implements Runnable {
            private ModelAndView mv;
            private String username;

            public AsyncUploadToOSS(ModelAndView mv, String username) {
                this.mv = mv;
                this.username = username;
            }

            @Override
            public void run() {
                mv = recommenders(EXPORTLIMIT, 0, city, restResource, restaurantName, recommandMethd, shelfTimeStart,
                        shelfTimeEnd, mv);
                Page<RestaurantRecommender> page = (Page<RestaurantRecommender>) mv.getModel().get("page");
                DownloadBuilder<RestaurantRecommender> eb = new DownloadBuilder<>(RestaurantRecommender.class);
                eb.append(page.getContent());
                Pageable pageables = null;
                while (page.hasNext()) {
                    pageables = page.nextPageable();
                    mv = recommenders(pageables.getPageSize(), pageables.getPageNumber(), city, restResource,
                            restaurantName, recommandMethd, shelfTimeStart, shelfTimeEnd, mv);
                    page = (Page<RestaurantRecommender>) mv.getModel().get("page");
                    eb.append(page.getContent());
                }
                String filePath = eb.saveOnServer();
                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "推荐餐厅", username);
            }
        }
        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
        return new BaseResponse();
    }

    // @RequestMapping(value = "/ops/restaurant/recommenders/export", method = {
    // RequestMethod.GET }, produces = {
    // MediaType.APPLICATION_JSON_VALUE })
    // @ResponseBody
    public BaseResponse exportTest(@ApiParam(value = "城市") @RequestParam(value = "city", required = false) String city,
            @ApiParam(value = "餐厅来源") @RequestParam(value = "restResource", required = false) String restResource,
            @ApiParam(value = "推荐方式") @RequestParam(value = "recommandMethd", required = false) String recommandMethd,
            @ApiParam(value = "餐厅名") @RequestParam(value = "restaurantName", required = false) String restaurantName,
            @ApiParam(value = "推荐时间") @RequestParam(value = "start", required = false) String shelfTimeStart,
            @ApiParam(value = "推荐时间") @RequestParam(value = "end", required = false) String shelfTimeEnd,
            ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        SynDownload.mkfile(new DownloadBuilder<>(RestaurantRecommender.class), this, EnumDownLoadModel.RECOMMANDER,
                new Object[] { EXPORTLIMIT, 0, city, restResource, restaurantName, recommandMethd, shelfTimeStart,
                        shelfTimeEnd, mv },
                new Class<?>[] { int.class, int.class, String.class, String.class, String.class, String.class,
                        String.class, String.class, ModelAndView.class },
                request);
        return new BaseResponse();
    }

    private void addCommonAttr(ModelAndView mv) {
        mv.addObject("restManageTypeList", parseManageType());
//        mv.addObject("restResourceList", parseSources());
        mv.addObject("restPriorityList", parsePriority());
        mv.addObject("cityList", restService.queryAllCitys());
        mv.addObject("salesList", restService.querySales());
        mv.addObject("companyList", restService.queryAllCompanys());
        Map<Object, Object> mapstate = InnConstantRestStateEnum.parseSources();
        mv.addObject("gonghaiStatusList", mapstate);
    }

//    private Map<Object, Object> parseSources() {
//        return InnConstantsRecommandRs.parseSources();
//    }

    private Map<Object, Object> parsePriority() {
        return parse(restaurantPriority);
    }

    private Map<Object, Object> parseManageType() {
        return parse(restaurantManageType);
    }

    private Set<String> toSet(String keys) {
        Set<String> set = new HashSet<>();
        if (keys != null) {
            for (String key : keys.split(",")) {
                set.add(key);
            }
        }
        return set;
    }

    private Map<Object, Object> parse(String str) {
        Map<Object, Object> result = new HashMap<>();
        for (String iterable_element : str.split(",")) {
            String[] entry = iterable_element.split(":");
            result.put(entry[0], entry[1]);
        }
        return result;
    }
}
