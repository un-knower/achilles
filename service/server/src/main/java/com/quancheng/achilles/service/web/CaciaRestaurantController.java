package com.quancheng.achilles.service.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/cacia")
@RestController
public class CaciaRestaurantController {

    private static volatile String status = "free";

    @RequestMapping(path = "/import/page", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
            MediaType.TEXT_HTML_VALUE })
    public ModelAndView importPage(ModelAndView mv) {
        mv.setViewName("import_data/import_page");
        return mv;
    }

    @RequestMapping(path = "/import/status", produces = {
            MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.GET)
    public String request() {
        return status;
    }

    @RequestMapping(path = "/import/proccessing", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
            MediaType.TEXT_HTML_VALUE }, method = { RequestMethod.POST })
    public String importData(ModelAndView mv, Long clientId, Long dorisTableId, String targetTable, String apiType) {
        if ("free".equals(status)) {
            status = "busy";
            new Thread(new Runnable() {
                public void run() {
                    try {
                    } catch (Throwable e) {
                        e.printStackTrace();
                    } finally {
                        status = "free";
                    }
                }
            }).start();
            return "success";
        } else {
            return "failed";
        }
    }
}
