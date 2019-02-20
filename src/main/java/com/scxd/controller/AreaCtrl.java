package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.service.AreaService;
import com.scxd.toolkit.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 区域管理控制器
 */
@RestController
@RequestMapping("area")
public class AreaCtrl {

    @Autowired
    private AreaService areaService;
    @Autowired
    private HttpSession session;

    //页面根据所选区域得到对应的下级区域名和上级区域名
    @RequestMapping(value = "/getArea",method = RequestMethod.POST)
    public Response getArea(@RequestBody String qyid){
        try{
            return areaService.getArea(qyid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/getAreaDetails",method = RequestMethod.POST)
    public Response getAreaDetails(@RequestBody String qyid){
        try{
            return areaService.getAreaDetails(qyid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //分页获取区域列表信息
    //根据区域名称或者首拼分页查询区域列表信息，相比后者多了一个条件condition
    @RequestMapping(value = "/getAreas",method = RequestMethod.POST)
    public Response getAreas(@RequestBody Map map){
        try{
            return areaService.getAreas(map);
        }catch (Exception e){
           e.printStackTrace();
           return null;
        }
    }

    //新增区域信息
    @RequestMapping(value = "/addAreas",method = RequestMethod.POST)
    public Response addPmxm(@RequestBody Map map){
        try{
            return areaService.addAreas(map);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //删除区域列表信息
    //根据区域id，删除区域信息
    @RequestMapping(value = "/deleteAreas",method = RequestMethod.POST)
    public Response deletedAreas(@RequestBody String id){
        try{
            return areaService.deleteAreas(id);
        }catch (Exception e){
           e.printStackTrace();
           return null;
        }
    }

    //根据区域ID修改区域信息
    @RequestMapping(value = "/alterArea",method = RequestMethod.POST)
    public Response alterAreaInfo(@RequestBody Map map){
        try{
            return areaService.alterAreasInfo(map);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //根据行政级别和父级区域ID获取区域名称
    @RequestMapping(value = "/getQyname",method = RequestMethod.POST)
    public Response getQynameByXzjbAndFjqyid(@RequestBody Map map){
        try{
           return areaService.getQynameByXzjbAndFjqyid(map);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //根据qyid获取上级区域名称和对应的ID
    @RequestMapping(value = "/getSjQyname",method = RequestMethod.POST)
    public Response getSjQynameByQyid(@RequestBody String qyid){
        try{
            return areaService.getSjQynameByQyid(qyid);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //根据区域ID获取下级区域
    @RequestMapping(value = "/getXjqy",method = RequestMethod.POST)
    public Response getXjqyById(@RequestBody String qyid){
        try{
            return areaService.getXjqyById(qyid);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //根据区域所传的条件ID等于数据库中的QYID或SJQYID获取区域名和区域ID
    @RequestMapping(value = "/getregion",method = RequestMethod.POST)
    public Response getAreaName(@RequestBody String id){
        try{
            return areaService.getAreaName(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //获取存入session的区域ID
    @RequestMapping(value = "/getAreaID",method = RequestMethod.POST)
    public Response getAreaId(HttpSession session){
        try{
            String qyid = (String)session.getAttribute("AreaId");
            return (UtilClass.strIsEmpty(qyid))?new Response().failure("51"):new Response().success(qyid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //判断用户是否有区域修改权限用
    @RequestMapping(value = "/alterAuthority",method = RequestMethod.POST)
    public Response getAlterAuthority(@RequestBody String qyid){
        try{
            //查看当前用户是否有修改该区域信息的权限,一当前用户所在的区域为该区域
             String userqyid = new UtilClass().getUserQyid(session);
             String substr = qyid.substring(0,userqyid.length());
            if (userqyid.equals(substr)){
                return  new Response().success();
            }else{
                return new Response().failure("当前用户无修改该区域权限");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Response().failure("获取权限错误");
        }
    }

    //获取区域简介
    @RequestMapping(value = "/areaIntro",method = RequestMethod.POST)
    public Response getAreaIntro( @RequestBody String qyid){
        try{
             return areaService.getAreaIntro(qyid);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
