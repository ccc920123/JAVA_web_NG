package com.scxd.service;

import com.scxd.beans.Response;

import java.util.Map;

/**
 * 动态管理业务层接口
 */
public interface DtglService {

    //分页获取动态信息，标题名、首拼对应键名condition
    Response getDtglInfo(Map map)throws Exception;

    //新增动态信息,UUID做主键
    Response addDtglInfo(Map map)throws Exception;

    //修改动态信息，根据主键ID
    Response alterDtglInfo(Map map)throws Exception;

    //删除动态信息，根据ID
    Response deleteDtglInfo(String id, String cjr)throws Exception;

    //分页查询需要审核的动态信息,区域名、首拼areacon/标题名、首拼btcon/审核状态shzt
    Response getDtAudit(Map map)throws Exception;

    //审核动态信息，审核操作即通过或未通过，参数审核结果shjg/主键ID
    Response dtAudit(Map map)throws Exception;

    //统计（新闻采纳/动态信息）排名,约束条件开始、结束时间/市级行政区域/区县级行政区域
    Response statisticsDtInfo(Map map)throws Exception;

    //根据ID获取动态信息
    Response getDtInfoById(Map map)throws Exception;

    Response getDtyl(Map map)throws Exception;

    //根据ID上传动态内容
    Response updateDtnr(Map map)throws Exception;

    //动态预览
    Response saveDtyl(Map map)throws Exception;

    //获取新闻信息列表,参数发布位置FBWZ
    Response getNewsList(Map map)throws Exception;

    //根据动态ID获取新闻详情
    Response getNewsInfoById(String id)throws Exception;

    //动态信息提交到下一层级，根据动态ID
    Response submitDtInfo(Map map)throws Exception;

    //获取新闻信息列表,参数发布位置FBWZ
    Response getNewsByFBWZ(Map map)throws Exception;

    //首页获取新闻信息排名
    Response getNewsRank()throws Exception;

    //首页获取新闻信息排名MORE
    Response getNewsRankMore()throws Exception;

    //首页获取图片新闻
    Response getPictureNews()throws Exception;

}
