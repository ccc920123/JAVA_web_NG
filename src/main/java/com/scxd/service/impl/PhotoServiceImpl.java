package com.scxd.service.impl;

import com.scxd.beans.database.SysFile;
import com.scxd.beans.database.SysPhoto;
import com.scxd.dao.PhotoDao;
import com.scxd.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoDao photoDao;

    //图片上传
    @Override
    public boolean uploadImg(SysPhoto photo) throws Exception {
        if (photo == null)return false;
        int row = photoDao.insertPhoto(photo);
        return (row != 0)?true:false;
    }

    //获取图片
    @Override
    public byte[] getPhoto(String id) throws Exception {
        SysPhoto photo = photoDao.selectPhotoById(id);
        byte[] img = photo.getImg();
        return (img != null)? img:null;
    }

    @Override
    public boolean uploadFile(SysFile file) throws Exception {
        if (file == null)return false;
        int row = photoDao.insertFile(file);
        return (row != 0)?true:false;
    }

    @Override
    public SysFile selectFileByPrimaryKey(String id) {
        SysFile sysFile=photoDao.selectFileByPrimaryKey(id);
        return sysFile;
    }
}
