package com.quancheng.achilles.service.web;

//import java.io.IOException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.quancheng.achilles.service.constants.InnConstantPage;
//import com.quancheng.achilles.service.services.MemberServiceImpl;
//import com.quancheng.achilles.service.services.QCClientServiceImpl;
//import com.quancheng.achilles.service.services.RestaurantServiceImpl;
//import com.quancheng.achilles.dao.model.BaseResponse;
//import com.quancheng.achilles.dao.modelwrite.QCClient;
//import com.quancheng.achilles.service.utils.DownloadBuilder;
//import com.quancheng.achilles.service.utils.OssServiceDBUtil;
//
//import io.swagger.annotations.ApiParam;
//
//@Controller
//public class ClientController {
//    @Autowired
//    QCClientServiceImpl qcClientServ;
//    @Autowired
//    RestaurantServiceImpl restaurantService;
//    @Autowired
//    MemberServiceImpl memberService;
//
//    @Autowired
//    OssServiceDBUtil ossServiceDBUtil;
//    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
//
//    @RequestMapping(path = "/ops/client/list", method = RequestMethod.GET)
//    public ModelAndView clientsList(
//            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
//            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize,
//            @ApiParam(value = "城市") @RequestParam(value = "citys[]", required = false,defaultValue="-1") String[] citys,
//            @ApiParam(value = "客户") @RequestParam(value = "client", required = false) String client, ModelAndView mv)
//            throws IOException {
//        QCClient qcclient = new QCClient();
//        qcclient.setClientName("-1".equals(client) ? null : client);
//        Page<QCClient> result = qcClientServ.pageQuery(qcclient,citys, new PageRequest(pageNum, pageSize));
//        mv.addObject("page", result);
//        mv.addObject("citys", citys);
//        mv.addObject("client", client);
//        mv.addObject("companylist", memberService.listAllCompany());
//        mv.addObject("citylist", restaurantService.queryAllCitys());
//        mv.setViewName("clients/qc_client_index");
//        return mv;
//    }
//
//    @ResponseBody
//    @SuppressWarnings("unchecked")
//    @RequestMapping(path = "/ops/client/export", method = RequestMethod.GET)
//    public BaseResponse clientsExport(
//            @ApiParam(value = "城市") @RequestParam(value = "citys[]", required = false) String[] citys,
//            @ApiParam(value = "客户") @RequestParam(value = "client", required = false) String client,
//            HttpServletRequest request, ModelAndView mv, HttpServletResponse response) throws IOException {
//        class AsyncUploadToOSS implements Runnable {
//            private ModelAndView mv;
//            private String username;
//
//            public AsyncUploadToOSS(ModelAndView mv, String username) {
//                this.mv = mv;
//                this.username = username;
//            }
//
//            @Override
//            public void run() {
//                try {
//                    mv = clientsList(0, 5000, citys, client, mv);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Page<QCClient> result = (Page<QCClient>) mv.getModel().get("page");
//                DownloadBuilder<QCClient> eb = new DownloadBuilder<>(QCClient.class);
//                eb.append(result.getContent());
//                Pageable pageables = null;
//                while (result.hasNext()) {
//                    pageables = result.nextPageable();
//                    try {
//                        mv = clientsList(pageables.getPageNumber(), pageables.getPageSize(), citys, client, mv);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    result = (Page<QCClient>) mv.getModel().get("page");
//                    eb.append(result.getContent());
//                }
//                String filePath = eb.saveOnServer();
//                ossServiceDBUtil.uploadToOSSAndStoreUrlToDB(filePath, "企业信息", username);
//            }
//        }
//        EXECUTOR_SERVICE.submit(new AsyncUploadToOSS(mv, request.getRemoteUser()));
//        return new BaseResponse();
//    }
//}
