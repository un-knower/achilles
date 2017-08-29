package com.quancheng.achilles.service.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.service.model.DataItemKV;
import com.quancheng.achilles.service.model.ParamterConfig;

/**
 * Created by XZW on 2016/9/10 0010.
 */
@Controller
@RequestMapping(path = "/ops/template")
public class TemplateController {
    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
                                                             MediaType.TEXT_HTML_VALUE })
    public ModelAndView list(
                            Map<String,String[]> param,
                             ModelAndView mv) {
        param.put("aaa", new String[]{"321"});
        mv.addObject("paramaterValues", param);
        List<DataItemKV> items = Arrays.asList(new DataItemKV[]{new DataItemKV("321","上海")});
        mv.addObject("paramaterConfigs", new ParamterConfig[]{new ParamterConfig("input","likeKey","模糊搜索",null),new ParamterConfig("multiple","aaa","城市",items)});
        mv.setViewName("template");
        return mv;
    }
}
