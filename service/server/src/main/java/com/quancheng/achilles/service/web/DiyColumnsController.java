package com.quancheng.achilles.service.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplate;
import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.modelwrite.AchillesTemplateCondfig;
import com.quancheng.achilles.service.constants.InnConstantPage;
import com.quancheng.achilles.service.services.AchillesDiyColumnsService;
import io.swagger.annotations.ApiParam;

@RequestMapping("/template")
@RestController
public class DiyColumnsController {

    @Autowired
    AchillesDiyColumnsService achillesDiyColumnsServiceImpl;

    @RequestMapping(path = "/list", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    @ResponseBody
    public ModelAndView list(String tableName, ModelAndView mv,
            @ApiParam(value = "页码") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
            @ApiParam(value = "每页记录数") @RequestParam(value = "pageSize", required = false, defaultValue = InnConstantPage.PAGE_SIZE_STRING) int pageSize) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Page<AchillesDiyTemplate> page = achillesDiyColumnsServiceImpl.getOwnTemplate(tableName, null,
                new PageRequest(pageNum, pageSize));
        Map<String,AchillesTemplateCondfig> maps = achillesDiyColumnsServiceImpl.getTemplateConfig();
        mv.addObject("page", page);
        mv.addObject("urlconfig", maps);
        mv.addObject("username", username);
        mv.setViewName("template/template_list");
        return mv;
    }

    @RequestMapping(path = "/edit", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    @ResponseBody
    public ModelAndView edit(ModelAndView mv, String tableName,@RequestParam(required=false) Long templateId) throws Exception {
        
        List<String> cols = new ArrayList<>();
//        Map<String,String> colTitle= new HashMap<>();
        if(templateId != null && templateId  != 0){
            List<AchillesDiyTemplateColumns> selected= achillesDiyColumnsServiceImpl.getTemplateColsByTemplate(templateId);
            for (AchillesDiyTemplateColumns achillesDiyTemplateColumns : selected) {
                cols.add(achillesDiyTemplateColumns.getTableCol());
//                colTitle.put(achillesDiyTemplateColumns.getTableCol(), achillesDiyTemplateColumns.getColTitile());
            }
        }
        mv.addObject("selectedColumns", cols);
//        mv.addObject("colTitles", colTitle);
        mv.addObject("templateInfo", templateId == null|| templateId  == 0?new AchillesDiyTemplate():achillesDiyColumnsServiceImpl.getTemplate(templateId));
        mv.addObject("allColumns", achillesDiyColumnsServiceImpl.getTargetTableInfos(tableName));
        mv.addObject("tableName", tableName);
        mv.setViewName("template/template_modify");
        return mv;
    }

    @RequestMapping(path = "/save", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.TEXT_HTML_VALUE })
    @ResponseBody
    public ModelAndView save(ModelAndView mv, String tableName,Long templateId,String[] columnName,String templateName ) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        achillesDiyColumnsServiceImpl.save(tableName, username, templateId, columnName, templateName);
        mv.setViewName("redirect:/template/list");
        return mv;
    }
}
