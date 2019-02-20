package com.scxd.dao;

import com.scxd.beans.database.SysFile;
import com.scxd.beans.database.SysPhoto;
import org.springframework.stereotype.Repository;

/**
 * 图片管理，数据库交互接口
 */
@Repository
public interface PhotoDao {
    //存入图片信息
    int insertPhoto(SysPhoto photo) throws Exception;

    //通过ID获取图片
    SysPhoto selectPhotoById(String id) throws Exception;

    int insertFile(SysFile file) throws Exception;

    SysFile selectFileByPrimaryKey(String id);
}
