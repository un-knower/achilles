package com.quancheng.achilles.service.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.quancheng.achilles.dao.ds_st.model.OssFileInfo;
import com.quancheng.achilles.dao.ds_st.repository.OssFileInfoRepository;

@Service
public class OssFileInfoServiceImpl implements OssFileInfoService {
    public static final int ONE_DAY_IN_SECONDS = 24 * 60 * 60;
    private final Logger LOGGER = LoggerFactory.getLogger(OssFileInfoService.class);
    @Autowired
    OssFileInfoRepository ossFileInfoRepository;

    public void save(OssFileInfo ossFileInfo) {
        LOGGER.info(ossFileInfoRepository.save(ossFileInfo).toString());
    }

    public List<OssFileInfo> listOssFileInfoOfUser(String username) {
        List<OssFileInfo> list = ossFileInfoRepository.findAllByUserName(username);
        markOutDatedOssFile(list);
        return list ;
    }

    private void markOutDatedOssFile(List<OssFileInfo> list) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        for (int i = 0;list != null &&  i < list.size(); i++) {
            OssFileInfo ofi = list.get(i);
            try {
                long nowSeconds = (now.getTime()-df.parse(ofi.getCreatedAt()).getTime())/1000;
                if(OssFileInfo.OSS_STATUS_SUCCESS ==ofi.getOssStatus() && nowSeconds> ONE_DAY_IN_SECONDS){
                    ofi.setOssStatus(OssFileInfo.OSS_STATUS_OUTDATED);
                }
            } catch (ParseException e) {
                LOGGER.error("Failed to parse OSS file created date", e);
            }
        }
    }

    @Override
    public List<OssFileInfo> listLatest10OssFileInfoOfUser(String username) {
        Page<OssFileInfo> list = ossFileInfoRepository.findLatest10ByUserName(username, new PageRequest(0,10));
        markOutDatedOssFile(list.getContent());
        return list.getContent();
    }

}
