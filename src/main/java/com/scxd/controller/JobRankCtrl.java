package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysJobRankConfigBean;
import com.scxd.beans.database.SysJobRankDetailBean;
import com.scxd.beans.extendbeans.JobSorceXXTableBodyBean;
import com.scxd.service.JobRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 工作排名控制器
 */
@RestController
@RequestMapping("job")
public class JobRankCtrl {

    @Autowired
    private JobRankService jobRankService;

    //分页查询获取工作排名信息,条件为condition（排名名称、首拼）
    @RequestMapping(value = "/joblist",method = RequestMethod.POST)
    public Response getJobRankList(@RequestBody Map map){
        try{
            return jobRankService.getJobRankList(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    //工作排名项目启用或停止,参数是ID和状态state
    @RequestMapping(value = "/state",method = RequestMethod.POST)
    public Response startOrStopState(@RequestBody Map job){
      try{
          return jobRankService.startOrStopState(job);
      }catch(Exception e){
          e.printStackTrace();
          return null;
      }
    }

    //新增排名项目
    @RequestMapping(value = "/addxm",method = RequestMethod.POST)
    public Response addPmxm(@RequestBody Map map){
        try{
            return jobRankService.addPmxm(map);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //修改排名项目
    @RequestMapping(value = "/alterxm",method = RequestMethod.POST)
    public Response alterPmxm(@RequestBody Map map){
        try{
            return jobRankService.alterPmxm(map);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //删除排名项目,根据ID
    @RequestMapping(value = "/deletexm",method = RequestMethod.POST)
    public Response deletePmxm(@RequestBody String id){
        try{
            return jobRankService.deletePmxm(id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value = "/jobRankMessage",method = RequestMethod.GET)
    public Response jobRankMessage( String pmid){
        try{
            return jobRankService.getJobrankByPMID(pmid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //查看排名项目详情，内容是各个考评区域的打分情况，所以返回的是LIST
    //map中参数 id 为对应的排名项目ID，pageNo页码
    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    public Response getDetail(@RequestBody Map map){
        try{
//            return jobRankService.getDetail(map);
           return jobRankService.getDetailSorce(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //打分操作,即更新打分的数据,根据评分表的评分区域ID
    @RequestMapping(value = "/grade",method = RequestMethod.POST)
    public Response grade(@RequestBody JobSorceXXTableBodyBean jobSorceXXTableBodyBean){
        try{
//            return jobRankService.grade(map);
            return jobRankService.gradeByList(jobSorceXXTableBodyBean.getQyid(),jobSorceXXTableBodyBean.getSysJobRankDetailBeans());

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //获取打分信息根据考评区域id和排名项目id
    @RequestMapping(value = "/score",method = RequestMethod.POST)
    public Response getScore(@RequestBody Map map){
        try{
           return jobRankService.getScore(map);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //首页获取工作排名，默认显示最新时间的排名项目
    @RequestMapping(value = "/jobrank",method = RequestMethod.POST)
    public Response getJobRankByNewTime(){
        try{
            return jobRankService.getJobRankByNewTime();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //首页工作排名展示更多内容
    @RequestMapping(value = "/rankInfo",method = RequestMethod.POST)
    public Response getMoreRankInfo(@RequestBody Map map){
        try{
            return jobRankService.getMoreRankInfo(map);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //分页查询获取工作排名信息,条件为condition（排名名称、首拼）
    @RequestMapping(value = "/rankList",method = RequestMethod.POST)
    public Response getMoreRankList(@RequestBody Map map){
        try{
            return jobRankService.getMoreRankList(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
/**
* 获取当前工作排名的小项
 * */
    @RequestMapping(value = "/getConfigByPMID",method = RequestMethod.GET)
    public Response getConfigByPMID( String pmid){
        try{
            return jobRankService.getConfigByPMID(pmid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 增加小项
     * */
    @RequestMapping(value = "/addConfigByPMID",method = RequestMethod.POST)
    public Response addConfigByPMID(@RequestBody  List<SysJobRankConfigBean> list){
        try{
            return jobRankService.addConfigByPMID(list);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 增加小项
     * */
    @RequestMapping(value = "/saveConfigBean",method = RequestMethod.POST)
    public Response saveConfigBean(@RequestBody  List<SysJobRankConfigBean> list){
        try{
            return jobRankService.saveConfigBean(list);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 删除小项
     * */
    @RequestMapping(value = "/DeleteConfigByID",method = RequestMethod.POST)
    public Response deleteConfigByID(@RequestBody  Map map){
        try{
            return jobRankService.deleteConfigByID((String)map.get("xxid"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 重新生成得分表
     * */
    @RequestMapping(value = "/saveSorceTable",method = RequestMethod.POST)
    public Response saveSorceTable(@RequestBody  Map map){
        try{
            return jobRankService.saveSorceTable((String)map.get("pmid"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 重新生成得分表
     * */
    @RequestMapping(value = "/getXXSorceTable",method = RequestMethod.GET)
    public Response getXXSorceTable( String  pmid,String khqyid){
        try{
            return jobRankService.getXXSorceTable(pmid,khqyid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
