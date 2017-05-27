package com.quancheng.achilles.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quancheng.achilles.service.services.OssFileInfoService;
import com.quancheng.achilles.dao.modelwrite.OssFileInfo;

@Service
public class OssServiceDBUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(OssServiceDBUtil.class);

    @Autowired
    OssFileInfoService ossFileUploadService;


    /**
     * 保存本地文件到OSS，将OSS返回的URL存到数据库，保存OssFileInfo
     *
     * @param filePath
     * @param modelName
     * @param username
     */
    public void uploadToOSSAndStoreUrlToDB(String filePath, String modelName, String username) {
        OssServiceUtil ossServiceUtil = OssServiceUtil.getInstance();

        String ossFileUrl = null;
        int i = 0;
        while (ossFileUrl == null && i < 3) {
            i++;
            LOGGER.info("upload {} to oss, attempt {}", filePath, i);
            try {
                ossFileUrl = ossServiceUtil.uploadFileAndGetUrl(filePath);
            } catch (Exception e) {
                LOGGER.error("Failed to upload file to OSS", e);
            }
        }

        OssFileInfo ossFileInfo = new OssFileInfo(username, ossFileUrl, modelName, (ossFileUrl == null ? OssFileInfo.OSS_STATUS_ERROR : OssFileInfo.OSS_STATUS_SUCCESS));
        try {
            ossFileUploadService.save(ossFileInfo);
        } catch (Exception e) {
            LOGGER.error("Failed to save file to DB", e);
        }
    }
}