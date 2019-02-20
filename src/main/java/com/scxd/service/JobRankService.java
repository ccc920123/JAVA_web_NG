package com.scxd.service;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysJobRankConfigBean;
import com.scxd.beans.database.SysJobRankDetailBean;

import java.util.List;
import java.util.Map;

/**
 * 工作排名业务接口
 */
public interface JobRankService {

    //分页查询获取工作排名信息
    Response getJobRankList(Map map)throws Exception;

    //工作排名项目启用或停止,参数是ID和状态state
    Response startOrStopState(Map job)throws Exception;

    //新增排名项目
    Response addPmxm(Map map)throws Exception;

    //修改排名项目
    Response alterPmxm(Map map)throws Exception;

    //删除排名项目,根据ID
    Response deletePmxm(String id)throws  Exception;

    //查看排名项目详情，内容是各个考评区域的打分情况，所以返回的是LIST
    Response getDetail(Map map)throws Exception;

    //打分操作,即更新打分的数据,根据评分表的评分区域ID
    Response grade(Map map)throws Exception;

    //获取打分信息根据考评区域id和排名项目id
    Response getScore(Map map)throws Exception;

    //首页获取工作排名，默认显示最新时间的排名项目
    Response getJobRankByNewTime()throws Exception;

    //首页工作排名展示更多内容
    Response getMoreRankInfo(Map map)throws Exception;

    //首页工作排名展示更多内容
    Response getMoreRankList(Map map)throws Exception;

    Response getConfigByPMID(String pmid);

    Response addConfigByPMID(List<SysJobRankConfigBean> list) throws Exception;

    Response deleteConfigByID(String uuid);

    Response saveConfigBean(List<SysJobRankConfigBean> list);

    Response saveSorceTable(String uuid);

    Response getXXSorceTable(String pmid, String khqyid);
    //查看排名项目详情，内容是各个考评区域的打分情况，所以返回的是LIST
    Response getDetailSorce(Map map)throws Exception;

    Response gradeByList(String pmid, List<SysJobRankDetailBean> listDetail);

    Response getJobrankByPMID(String pmid);
}
