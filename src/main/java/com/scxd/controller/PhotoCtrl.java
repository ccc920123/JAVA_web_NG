package com.scxd.controller;

import com.alibaba.druid.util.StringUtils;
import com.scxd.beans.Response;
import com.scxd.beans.database.SysFile;
import com.scxd.beans.database.SysPhoto;
import com.scxd.service.PhotoService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 图片管理控制器
 */
@RestController
public class PhotoCtrl {

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value="/getPhoto")
    public void getPhoto(String id,HttpServletResponse response){
        try{
            if (id==null){
                return;
            }
            if (id.length()>50){
                //RDovU2NOY2RsanRhcVh4dy9QaG90by9HemR0LzIwMTgvMTEvMTkvYTg3OTQxM2YxNTMwNGE0ODgxYjhlY2U5OTg0ZTA4ODYuanBn
                byte[] byteid=Base64.decodeBase64(id);
                String baseD=new String(byteid);//D:/ScNcdljtaqXxw/Photo/Gzdt/2018/11/19/a879413f15304a4881b8ece9984e0886.jpg
                if (!StringUtils.isEmpty(baseD)){
                    String[] path=baseD.split("/");
                    if (path!=null&&path.length>0){
                        String imgname=path[path.length-1];
                        //a879413f15304a4881b8ece9984e0886.jpg
                        if (!StringUtils.isEmpty(imgname)){

                            id=imgname.substring(0,32);
                        }else{
                            return;
                        }
                    }else{
                        return;
                    }
                }else{
                    return;
                }



            }

            byte[] img = photoService.getPhoto(id);
            //设置Http响应
            response.setDateHeader("Expires",0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setContentType("image/jpeg");
            OutputStream outputStream = response.getOutputStream();
            BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(img));
            byte[] buff = new byte[1024];
            int i = inputStream.read(buff);
            while (i != -1){
                outputStream.write(buff,0,buff.length);
                outputStream.flush();
                i = inputStream.read(buff);
            }
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadAPK(String id, HttpServletResponse res, HttpServletRequest request) {
        String fileName = "";
        String filePath = "";

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        SysFile sysFile = photoService.selectFileByPrimaryKey(id);
        if (sysFile != null) {
            filePath = sysFile.getLocalpath();
        }
        File tempFile=null;
        //获取文件名
        if (filePath != null) {
            tempFile = new File(filePath.trim());
            fileName = tempFile.getName();
        }else {
        }
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        res.setContentLengthLong(tempFile.length());
        OutputStream os = null;
//        byte [] data=getBytes(filePath);
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(tempFile));
//            bis = new BufferedInputStream(new ByteArrayInputStream(data));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value="/savePhoto")
    public Response  savePhoto(String name, String img , HttpServletRequest request) throws Exception {
         SysPhoto photo = new SysPhoto();
        if (name==null||"".equals(name)){
            return new Response().failure("图片名字不能为空");
        }
        if (img==null||"".equals(img)){
            return new Response().failure("图片不能为空");
        }
        photo.setId(name);
        String base64=img.replaceAll("%2B","+");
        photo.setImg(Base64.decodeBase64(base64));
        String basePath = request.getContextPath() + "/getPhoto?id="+name;
        photo.setPath(basePath);
        if (photoService.uploadImg(photo)){
            return new Response().success();
        }else{
            return new Response().failure("保存图片失败");
        }

    }
}
