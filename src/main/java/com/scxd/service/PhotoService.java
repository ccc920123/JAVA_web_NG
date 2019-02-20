package com.scxd.service;

import com.scxd.beans.database.SysFile;
import com.scxd.beans.database.SysPhoto;

/**
 * 图片管理，业务接口
 */
public interface PhotoService {
    //上传图片
    boolean uploadImg(SysPhoto photo)throws Exception;
    //通过ID获取图片
    byte[] getPhoto(String id)throws Exception;
    //上传文件
    boolean uploadFile(SysFile file)throws Exception;

    SysFile selectFileByPrimaryKey(String id);
}
