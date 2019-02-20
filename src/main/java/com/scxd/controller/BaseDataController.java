package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.service.BaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 11:35 2018/10/22
 * @Modified By:
 */
@RestController
@RequestMapping("baseData")
public class BaseDataController {
    @Autowired
    BaseDataService baseDataService;

    /**
     * 获取驾驶人统计数据
     * @param
     * @return
     */
    @RequestMapping(value = "/getDrvStatisData",method = RequestMethod.GET)
    public Response getDrvStatisData(HttpSession session){
        try{
            String qyid= (String) session.getAttribute("AreaId");
            return baseDataService.getDrvStatisData( qyid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取机动车统计数据
     * @param
     * @return
     */
    @RequestMapping(value = "/getVehStatisData",method = RequestMethod.GET)
    public Response getVehStatisData(HttpSession session){
        try{
            String qyid= (String) session.getAttribute("AreaId");
            return baseDataService.getVehStatisData( qyid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取道路统计数据
     * @param
     * @return
     */
    @RequestMapping(value = "/getRoadStatisData",method = RequestMethod.GET)
    public Response getRoadStatisData(HttpSession session){
        try{
            String qyid= (String) session.getAttribute("AreaId");
            return baseDataService.getRoadStatisData( qyid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取两站统计数据
     * @param
     * @return
     */
    @RequestMapping(value = "/getTableLZStatisData",method = RequestMethod.GET)
    public Response getTableLZStatisData(HttpSession session){
        try{
            String qyid= (String) session.getAttribute("AreaId");
            return baseDataService.getTableLZStatisData( qyid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取两员统计数据
     * @param
     * @return
     */
    @RequestMapping(value = "/getTableLYStatisData",method = RequestMethod.GET)
    public Response getTableLYStatisData(HttpSession session){
        try{
            String qyid= (String) session.getAttribute("AreaId");
            return baseDataService.getTableLYStatisData(qyid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
