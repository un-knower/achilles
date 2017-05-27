package com.quancheng.achilles.service.services;

import java.util.List;

import com.quancheng.achilles.dao.modelwrite.OssFileInfo;

public interface OssFileInfoService {
    /**
     * 保存已上传到OSS的文件信息
     * @param ossFileInfo
     */
    public void save(OssFileInfo ossFileInfo);

    /**
     * 查找用户所有的OSS有效文件信息（还未标记为已删除的）
     * @param username
     * @return
     */
    public List<OssFileInfo> listOssFileInfoOfUser(String username);

    /**
     * 查找用户最近申请下载的10个OSS文件
     * @param username
     * @return
     */
    public List<OssFileInfo> listLatest10OssFileInfoOfUser(String username);

}
