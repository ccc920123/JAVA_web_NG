package com.scxd.controller;

import com.scxd.beans.database.SysFile;
import com.scxd.beans.database.SysPhoto;
import com.scxd.service.PhotoService;
import com.scxd.toolkit.ImgTools;
import com.scxd.toolkit.UtilClass;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baidu.ueditor.ActionEnter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.UUID;

/**
 * 富文本UEditor控制器
 */
@RestController
@RequestMapping("static/plugs/ueditor")
@CrossOrigin
public class UeditorCtrl {

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "/jsp")
    public String getUEditorConfig(@RequestParam("action") String param, HttpServletRequest request) {
        String result = "";

        try {
            if (param != null && param.equals("config")) {
                result = config(request);
            } else if (param != null && param.equals("uploadimage")) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                // 获得文件：
                MultipartFile file = multipartRequest.getFile("upfile");
                result = uplodaImg(file, request);
            } else if (param != null && param.equals("uploadfile")) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                // 获得文件：
                MultipartFile file = multipartRequest.getFile("upfile");
                result = upfile(file, request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private String upfile(MultipartFile file, HttpServletRequest request) throws Exception {
        String systempath = System.getProperty("catalina.home");
        String realPath = systempath + "/upload/file";
        String id = UUID.randomUUID().toString();
        String returnUrl = request.getContextPath() + "/downloadFile?id=" + id;//存储路径

        String fileName = file.getOriginalFilename();
        boolean isSave = true;
        String path = null;
        File fileD = new File((realPath));
        if (fileD.exists()) {
            if (!fileD.isDirectory()) {
                fileD.delete();
                fileD.mkdirs();
            }
        } else {
            fileD.mkdirs();
        }
        if (file != null) {// 判断上传的文件是否为空
            path = realPath + System.getProperty("file.separator") + fileName;
            // 转存文件到指定的路径
            File mFile = new File(path);
            if (mFile.exists()) { //如果文件存在
                fileName=id+fileName;
                path = realPath + System.getProperty("file.separator") + fileName;
                // 转存文件到指定的路径
                 mFile = new File(path);
//                mFile.delete();
            }
            try {
                file.transferTo(mFile);
            } catch (IOException e) {
                isSave = false;
            }

        } else {
            isSave = false;
        }
        if (isSave) {
            SysFile sysFile = new SysFile();
            sysFile.setId(id);
            sysFile.setLocalpath(path);
            sysFile.setPath(returnUrl);
            boolean success = photoService.uploadFile(sysFile);
            if (success) {
                JSONObject jo = new JSONObject();
                jo.put("state", "SUCCESS");
                jo.put("original", file.getOriginalFilename());//原来的文件名
                jo.put("size", file.getSize());//文件大小
                jo.put("title", "");//随意，代表的是鼠标经过图片时显示的文字
                jo.put("type", file.getContentType());//文件后缀名
                jo.put("url", returnUrl);//上传成功后图片的路径
                return jo.toString();
            }
        }
        return "";
    }

    /**
     * 加载配置文件config.json
     */
    private String config(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("utf-8");
            String rootPath = request.getSession().getServletContext().getRealPath("/");
//            String exec = new ActionEnter(request, rootPath).exec();
            return configJson();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 图片上传
     */
    private String uplodaImg(MultipartFile file, HttpServletRequest request) {
        try {
            String imgPath = uploadPhoto(file, request);
            JSONObject jo = new JSONObject();
            if (UtilClass.strIsEmpty(imgPath)) {
                //上传失败
            } else {
                jo.put("state", "SUCCESS");
                jo.put("original", file.getOriginalFilename());//原来的文件名
                jo.put("size", file.getSize());//文件大小
                jo.put("title", "");//随意，代表的是鼠标经过图片时显示的文字
                jo.put("type", file.getContentType());//文件后缀名
                jo.put("url", imgPath);//上传成功后图片的路径
            }
            return jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 图片上传,返回图片路径
     */
    private String uploadPhoto(MultipartFile file, HttpServletRequest request) throws Exception {
        String basePath = request.getContextPath() + "/getPhoto?id=";
        SysPhoto photo = new SysPhoto();
        String id = UUID.randomUUID().toString();
        photo.setId(id);
        byte[] imgbyte = ImgTools.reduceImg(file.getInputStream(), 958, 639, 0.5f);
        photo.setImg(imgbyte);
        photo.setPath(basePath + id);
        boolean result = photoService.uploadImg(photo);
        return (result) ? basePath + id : null;
    }

    /**
     * 返回config.json数据
     */
    private String configJson() {
        return "\n" +
                "{\n" +
                "    \"imageActionName\": \"uploadimage\",\n" +
                "    \"imageFieldName\": \"upfile\",\n" +
                "    \"imageMaxSize\": 10240000, \n" +
                "    \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], \n" +
                "    \"imageCompressEnable\": true, \n" +
                "    \"imageCompressBorder\": 1000, \n" +
                "    \"imageInsertAlign\": \"none\", \n" +
                "    \"imageUrlPrefix\": \"\",\n" +
                // "    \"imagePathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", \n" +
                "    \"scrawlActionName\": \"uploadscrawl\", \n" +
                "    \"scrawlFieldName\": \"upfile\", \n" +
                "    \"scrawlPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", \n" +
                "    \"scrawlMaxSize\": 2048000, \n" +
                "    \"scrawlUrlPrefix\": \"\",\n" +
                "    \"scrawlInsertAlign\": \"none\",\n" +
                "    \"snapscreenActionName\": \"uploadimage\",\n" +
                "    \"snapscreenPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", \n" +
                "    \"snapscreenUrlPrefix\": \"\",\n" +
                "    \"snapscreenInsertAlign\": \"none\",\n" +
                "    \"catcherLocalDomain\": [\"127.0.0.1\", \"localhost\", \"img.baidu.com\"],\n" +
                "    \"catcherActionName\": \"catchimage\",\n" +
                "    \"catcherFieldName\": \"source\",\n" +
                "    \"catcherPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", \n" +
                "    \"catcherUrlPrefix\": \"\", \n" +
                "    \"catcherMaxSize\": 2048000,\n" +
                "    \"catcherAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"],    \n" +
                " \"videoActionName\": \"uploadvideo\", \n" +
                "    \"videoFieldName\": \"upfile\",\n" +
                "    \"videoPathFormat\": \"/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}\", \n" +
                "    \"videoUrlPrefix\": \"\",\n" +
                "    \"videoMaxSize\": 102400000, \n" +
                "    \"videoAllowFiles\": [\n" +
                "        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\n" +
                "        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\"],\n" +
                "       \"fileActionName\": \"uploadfile\", \n" +
                "    \"fileFieldName\": \"upfile\",\n" +
                "    \"filePathFormat\": \"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}\",\n" +
                "    \"fileUrlPrefix\": \"\",\n" +
                "    \"fileMaxSize\": 51200000,\n" +
                "    \"fileAllowFiles\": [\n" +
                "        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\n" +
                "        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\n" +
                "        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\n" +
                "        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\n" +
                "        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\n" +
                "    ], \n" +
                "    \"imageManagerActionName\": \"listimage\",\n" +
                "    \"imageManagerListPath\": \"/ueditor/jsp/upload/image/\", \n" +
                "    \"imageManagerListSize\": 20, \n" +
                "    \"imageManagerUrlPrefix\": \"\",\n" +
                "    \"imageManagerInsertAlign\": \"none\",\n" +
                "    \"imageManagerAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"],\n" +
                "    \"fileManagerActionName\": \"listfile\", \n" +
                "    \"fileManagerListPath\": \"/ueditor/jsp/upload/file/\",\n" +
                "    \"fileManagerUrlPrefix\": \"\",\n" +
                "    \"fileManagerListSize\": 20,\n" +
                "    \"fileManagerAllowFiles\": [\n" +
                "        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\n" +
                "        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\n" +
                "        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\n" +
                "        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\n" +
                "        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\n" +
                "    ] \n" +
                "}";
    }
}
