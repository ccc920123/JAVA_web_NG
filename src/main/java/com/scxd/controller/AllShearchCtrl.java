package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.service.AllShearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
@RequestMapping("allshearch")
public class AllShearchCtrl {
    @Autowired
    private AllShearchService service;
    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Response getAllData(String fbwz, String bt, int pageNo, int pageSize) {
        //在session 中得到区域id
        String currentAreaId = (String) session.getAttribute("AreaId");
        try {
            return service.getAllData(currentAreaId, fbwz, bt,getTheirLevel(currentAreaId), pageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    //根据区域ID判断所属层级,前端显示新闻信息只显示省、市、区县三级
    private String getTheirLevel(String qyid) {
        String submitLevel;
        switch (qyid.length()) {
            case 2:
                submitLevel = "1";
                break;
            case 4:
                submitLevel = "2";
                break;
            case 6:
                submitLevel = "3";
                break;
            default:
                submitLevel = "1";
                break;
        }
        return submitLevel;
    }
}
