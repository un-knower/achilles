package com.quancheng.achilles.service.web;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.quancheng.achilles.dao.modelwrite.AchillesDiyTemplateColumns;
import com.quancheng.achilles.service.services.AchillesDiyColumnsService;

public abstract class ControllerAbstract {
    @Autowired
    AchillesDiyColumnsService achillesDiyColumnsServiceImpl;
    public void configTemplate(Long templateId, ModelAndView mv){
        List<AchillesDiyTemplateColumns> cols = achillesDiyColumnsServiceImpl.getTemplateColsByTemplate(templateId);
        List<String> selectedCols = new ArrayList<>(cols.size());
        for (AchillesDiyTemplateColumns achillesDiyTemplateColumns : cols) {
            selectedCols.add(achillesDiyTemplateColumns.getTableCol());
        }
        mv.addObject("templateColumns",cols);
        mv.addObject("templateId",templateId);
        mv.addObject("templateColumnKeys",selectedCols);
    }
}
