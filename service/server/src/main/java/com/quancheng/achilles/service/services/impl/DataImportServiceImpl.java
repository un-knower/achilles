package com.quancheng.achilles.service.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.quancheng.achilles.dao.ds_st.mapper.DataImportTemplateMapper;
import com.quancheng.achilles.dao.ds_st.model.AchillesDiyTemplateColumns;
import com.quancheng.achilles.dao.ds_st.model.DataItemDetail;
import com.quancheng.achilles.dao.ds_st.model.DorisTableColumns;
import com.quancheng.achilles.dao.ds_st.model.DorisTableInfo;
import com.quancheng.achilles.dao.ds_st.repository.DorisTableColumnsRepository;
import com.quancheng.achilles.dao.ds_st.repository.DorisTableInfoRepository;
import com.quancheng.achilles.service.model.ChartDataResp;
import com.quancheng.achilles.service.model.PageInfo;
import com.quancheng.achilles.service.services.DataImportService;
import com.quancheng.achilles.util.UtilClassHelper;
/**
 * poi / csv 文件导入
 * @author zhuzhong
 */
@Service
public class DataImportServiceImpl implements DataImportService {
    public final static ExecutorService fileParser = Executors.newFixedThreadPool(1);
    @Autowired
    DorisTableColumnsRepository dorisTableColumnsRepository;
    @Autowired
    DataImportTemplateMapper dataImportTemplateMapper;
    @Autowired
    DorisTableInfoRepository dorisTableInfoRepository;
    @Autowired
    DorisTableServiceImpl dorisTableServiceImpl;
    @Autowired
    DataItemServiceImpl dataItemServiceImpl;
    String dateTypePrex="CONVERT_";
    String dateDefaultPrex = "DEFAULT_";
    public void doImport(File file, Long tableId) {
        try {
            DorisTableInfo table = dorisTableInfoRepository.findOne(tableId);
            List<DorisTableColumns> cols =  dorisTableColumnsRepository.findAll(new Specification<DorisTableColumns>() {
                public Predicate toPredicate(Root<DorisTableColumns> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.equal(root.get("tableId"),tableId);
                }
            });
            dataImportTemplateMapper.deleteAllData(table.getRemark());
            DorisTableColumns[] colsArr = cols.toArray(new DorisTableColumns[cols.size()]);
            Map<String,Map<String,String>> convertMapping = new HashMap<>();
            Map<String,String> defaultMapping = new HashMap<>();
            //数据转换
            for (DorisTableColumns dorisTableColumns : colsArr) {
                if(dorisTableColumns.getDataType()!=null && dorisTableColumns.getDataType().toUpperCase().startsWith(dateTypePrex)){
                    //数据转换
                    List<DataItemDetail> items = dataItemServiceImpl.getDataItemDetail(dorisTableColumns.getDataType().substring(dateTypePrex.length()));
                    Map<String,String> itemMapping = new HashMap<>(items.size());
                    for (int i = 0; i < items.size(); i++) {
                        if(!itemMapping.containsKey(items.get(i).getDetailText())){
                            itemMapping.put(items.get(i).getDetailText(), items.get(i).getDetailKey());
                        }
                    }
                    convertMapping.put(dorisTableColumns.getColName(), itemMapping);
                    //默认值
                }else if(dorisTableColumns.getDataType()!=null && dorisTableColumns.getDataType().toUpperCase().startsWith(dateDefaultPrex)){
                    defaultMapping.put(dorisTableColumns.getColName(), dorisTableColumns.getDataType().substring(dateTypePrex.length()));
                }
            }
            UtilClassHelper.processByGivenRowHandler(new FileInputStream(file)  ,file.getName(), 100, new Consumer<List<Map<String, String>>>(){
                public void accept(List<Map<String, String>> t) {
                    doInsert(colsArr,t,table,convertMapping,defaultMapping);
                }
            });
       } catch (IOException e) {
           e.printStackTrace();
       }finally{
           if(file.exists()) {
               file.delete();
           }
       }
    }
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public void doInsert(DorisTableColumns[] cols,List<Map<String, String>> t,DorisTableInfo table,Map<String,Map<String,String>> convertMapping,Map<String,String> defaultMapping){
        try {
            Map<String,Object> parameter = new HashMap<>(2);
            parameter.put("colName", cols);
            Object[] valueParam = new Object[t.size()];
            for (int j=0;j<t.size();j++) {
                Map<String, String> valueMap  =t.get(j);
                Object[] values = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    String vl = valueMap.get(cols[i].getColExcel());
                    if(convertMapping.containsKey(cols[i].getColName())){
                        //数据转换
                        vl = convertMapping.get(cols[i].getColName()).get(vl);
                    }else if(defaultMapping.containsKey(cols[i].getColName())){
                        //数据默认
                        vl = defaultMapping.get(cols[i].getColName());
                    }
                    //api 百度、高德api外部数据
                    values[i]=vl==null||vl.isEmpty()?("datetime".equals(cols[i].getDataType())?df.format(new Date()):null):vl;
                }
                valueParam[j]=values;
            }
            parameter.put("datas", valueParam);
            parameter.put("tableName", table.getRemark());
            dataImportTemplateMapper.doInsert(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public ChartDataResp dataView(Map<String,String[]> param, List<DorisTableColumns> cols,DorisTableInfo table) {
        if(table == null ){
            return new ChartDataResp();
        }
        String[] pageSize = param.get("pageSize");
        String[] pageNum = param.get("pageNum");
        Long ps = Long.parseLong(pageSize== null?"10":pageSize[0]);
        Long pn = Long.parseLong(pageNum== null?"0":pageNum[0]);
        try {
            List<AchillesDiyTemplateColumns> columns = new ArrayList<>();
            AchillesDiyTemplateColumns adtc = null;
            for (int i = 0; i < cols.size(); i++) {
                adtc = new AchillesDiyTemplateColumns();
                adtc.setTableCol(cols.get(i).getColName());
                columns.add(adtc);
            }
            ChartDataResp cdr = dorisTableServiceImpl.getData(null, columns, null, new PageInfo(pn,ps), table);
            return cdr;
        } catch (Exception e) {
        }
        return new ChartDataResp();
    }
    
}
