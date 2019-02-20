package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.service.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 10:55 2018/11/19
 * @Modified By:
 */
@RestController
@RequestMapping("assess")
public class AssessController {

    @Autowired
    private AssessService assessService;
    @Autowired
    private HttpSession session;

    //考核类型
    @RequestMapping(value = "/getkhlx",method = RequestMethod.POST)
    public Response getArea(){
        try{
            return assessService.getKhlx();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //考核查询列表
    @RequestMapping(value = "/getAssessList",method = RequestMethod.POST)
    public Response getAssessList(@RequestBody Map map){
        try{
            return assessService.getAssessList(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //查看排名项目详情，内容是各个区域的打分情况，所以返回的是LIST
    //map中参数 id 为对应的排名项目ID，pageNo页码
    @RequestMapping(value = "/getAssessComplexMessage",method = RequestMethod.GET)
    public Response getAssessComplexMessage(String id){
        try{
            return assessService.getAssessComplexMessage(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //查看排名项目详情，内容是各个区域的打分情况，所以返回的是LIST
    //map中参数 id 为对应的排名项目ID，pageNo页码
    @RequestMapping(value = "/getAssessDetail",method = RequestMethod.POST)
    public Response getDetail(@RequestBody Map map){
        try{
            return assessService.getAssessDetails(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "/getAssessInfo",method = RequestMethod.POST)
    public Response getAssessInfo(@RequestBody Map map){
        try{
            return assessService.getAssessInfo(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/getAssessConfig",method = RequestMethod.POST)
    public Response getAssessConfig(){
        try{
            return assessService.getAssessConfig();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/getAssessSave",method = RequestMethod.POST)
    public Response getAssessSave(@RequestBody Map map){
        try{
            return assessService.getAssessSave(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //查看排名项目详情，内容是各个区域的打分情况，所以返回的是LIST
    //map中参数 id 为对应的排名项目ID，pageNo页码
    @RequestMapping(value = "/justIsExist",method = RequestMethod.GET)
    public Response justIsExist( String khkssj,
                                 String khjssj,
                                 String khlx){
        try{
            return assessService.justIsExist(khkssj,khjssj,khlx);
        }catch (Exception e){
            e.printStackTrace();
            return new Response().failure("异常");
        }
    }
    //综合考核
    @RequestMapping(value = "/getComplexList",method = RequestMethod.POST)
    public Response getComplexList(@RequestBody Map map){
        try{
            return assessService.getComplexList(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //启用或停止,参数是ID和状态state
    @RequestMapping(value = "/state",method = RequestMethod.POST)
    public Response startOrStopState(@RequestBody Map job){
        try{
            return assessService.startOrStopState(job);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value = "/getAssessComplexDetail",method = RequestMethod.POST)
    public Response getAssessComplexDetail(@RequestBody Map map){
        try{
            return assessService.getAssessComplexDetail(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value = "/getAssessdeleted",method = RequestMethod.POST)
    public Response getAssessdeleted(@RequestBody String id){
        try{
            return assessService.getAssessdeleted(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/getAssessComplexDeleted",method = RequestMethod.POST)
    public Response getAssessComplexDeleted(@RequestBody String id){
        try{
            return assessService.getAssessComplexDeleted(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //首页获取综合排名，默认显示最新时间的排名项目
    @RequestMapping(value = "/assessrank",method = RequestMethod.POST)
    public Response getAssessRankByNewTime(){
        try{
            return assessService.getAssessRankByNewTime();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
