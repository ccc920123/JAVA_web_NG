package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.beans.database.AssessCompInfoBean;
import com.scxd.service.CompreAssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 10:57 2018/12/7
 * @Modified By:
 */
@RestController
@RequestMapping("compreAssess")
public class CompreAssessController {

    @Autowired
    CompreAssessService compreAssessService;


    @RequestMapping(value = "/saveCompreAssess",method = RequestMethod.POST)
    public Response saveCompreAssess(@RequestBody AssessCompInfoBean assessCompInfoBean){
        try{
            return compreAssessService.saveCompreAssess(assessCompInfoBean);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
