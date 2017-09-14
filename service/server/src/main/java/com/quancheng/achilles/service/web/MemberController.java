package com.quancheng.achilles.service.web;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.quancheng.achilles.service.constants.InnConstantPage;
//import com.quancheng.achilles.service.services.MemberService;
//import com.quancheng.achilles.service.services.OssFileInfoService;
//import com.quancheng.achilles.dao.model.BaseResponse;
//import com.quancheng.achilles.dao.modelwrite.Member;
//import com.quancheng.achilles.service.utils.DownloadBuilder;
//import com.quancheng.achilles.service.utils.OssServiceDBUtil;
//
//import io.swagger.annotations.ApiParam;
//
///**
// * Created by XZW on 2016/9/10 0010.
// */
//@Controller
//@RequestMapping(path = "/ops/member")
//public class MemberController {
//
//    private Logger              logger = LoggerFactory.getLogger(MemberController.class);
//    private final static String TITLE  = "用户信息";
//
//    @Autowired
//    MemberService               memberService;
//
//    @Autowired
//    OssFileInfoService ossFileUploadService;
//
//    @Autowired
//    OssServiceDBUtil ossServiceDBUtil;
//
//    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
//
//    @RequestMapping(path = "/download", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResponse download(@ApiParam(value = "开始时间") @RequestParam(value = "date", required = false) String startDate,
//                         @ApiParam(value = "结束时间") @RequestParam(value = "date", required = false) String endDate,
//                         @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false) String company,
//                         @ApiParam(value = "城市") @RequestParam(value = "city[]", required = false, defaultValue="-1") String[] city,
//                         @ApiParam(value = "搜索类型") @RequestParam(value = "type", required = false) String type,
//                         @ApiParam(value = "关键字") @RequestParam(value = "name", required = false) String name,
//                         @ApiParam(value = "用户状态") @RequestParam(value = "userStatus[]", required = false, defaultValue="-999") String[] userStatus,
//                         @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
//                         @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
//                         ModelAndView mv, HttpServletResponse response, HttpServletRequest request) throws Exception {
//
//        class AsyncUploadToOSS implements Runnable {
//            private ModelAndView mv;
//            private String username;
//
//            public AsyncUploadToOSS(ModelAndView mv, String username) {
//                this.mv = mv;
//                this.username = username;
//            }
//            @Override
//            public void run() {
//                DownloadBuilder<Member> downloadBuilder = new DownloadBuilder<>(Member.class);
//
//                mv = list(startDate, endDate, company, city, type, name, userStatus, 1000, 0, mv);
//                Page<Member> page = (Page<Member>) mv.getModel().get("page");
//                try {
//					downloadBuilder.append(page.getContent());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//                while (page.hasNext()) {
//	                	try {
//		                    Pageable pageable = page.nextPageable();
//		                    mv = list(startDate, endDate, company, city, type, name, userStatus, pageable.getPageSize(),
//		                            pageable.getPageNumber(), mv);
//		
//		                    page = (Page<Member>) mv.getModel().get("page");
//		                    downloadBuilder.append(page.getContent());
//		            	} catch (Exception e) {
//						e.printStackTrace();
//					}
//                }
//                String filePath = downloadBuilder.saveOnServer();
//                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, TITLE, username);
//            }
//        }
//                
//        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
//       
//        return new BaseResponse();
//    }
//
//    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
//                                                             MediaType.TEXT_HTML_VALUE })
//    public ModelAndView list(@ApiParam(value = "开始时间") @RequestParam(value = "date", required = false) String startDate,
//                             @ApiParam(value = "结束时间") @RequestParam(value = "date", required = false) String endDate,
//                             @ApiParam(value = "所属企业") @RequestParam(value = "company", required = false) String company,
//                             @ApiParam(value = "城市") @RequestParam(value = "city[]", required = false, defaultValue="-1") String[] cityArray,
//                             @ApiParam(value = "搜索类型") @RequestParam(value = "type", required = false) String type,
//                             @ApiParam(value = "关键字") @RequestParam(value = "keyword", required = false) String keyword,
//                             @ApiParam(value = "用户状态") @RequestParam(value = "userStatus", required = false, defaultValue="-999") String[] userStatusArray,
//                             @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
//                             @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
//                             ModelAndView mv) {
//        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "id");
//        Page<Member> page = memberService.findAll(startDate, endDate, company, cityArray, type, keyword, userStatusArray, pageable);
//        Map<Object,String> map =build();
//        for(Member me :page.getContent()){
//        		if(me!=null){
//	        		me.setStatusText( me.getStatus()==null?null:map.get(me.getStatus()));
//	        	}
//        }
//        mv.addObject("startDate", startDate);
//        mv.addObject("endDate", endDate);
//        mv.addObject("page", page);
//        mv.addObject("type", type);
//        mv.addObject("company", company);
//        mv.addObject("companyList", memberService.listAllCompany());
//        mv.addObject("userStatus", userStatusArray);
//        mv.addObject("userStatusList", memberService.listUserStatus());
//        mv.addObject("city", cityArray);
//        mv.addObject("keyword", keyword);
//        mv.addObject("cityList", memberService.listCity());
//        mv.setViewName("member/index");
//        return mv;
//    }
//    private         Map<Object,String> build(){
//    	 Map<Object,String>	stateMap = new HashMap<>();
//        stateMap.put(Member.STATUS_DISABLE, "禁用");
//        stateMap.put(Member.STATUS_ENABLE, "正常");
//        stateMap.put(Member.STATUS_UNACTIVATED, "未激活");
//        stateMap.put(Member.STATUS_UNAUTHENTICATION, "未认证");
//        stateMap.put(Member.STATUS_DELETED, "删除");
//        return stateMap;
//    } 
//}
