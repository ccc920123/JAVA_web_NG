package com.scxd.dao;

import com.scxd.beans.database.SysDtgl;
import com.scxd.beans.database.SysDtyl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 动态管理数据处理层
 */
@Repository
public interface DtglDao {

    //分页获取动态信息，标题名、首拼对应键名condition
    int selectTotalDtglInfo(Map map)throws Exception;
    List<Map> selectDtglInfo(Map map)throws Exception;

    //新增动态信息,UUID做主键
    int insertDtglInfo(@Param("map")Map map)throws Exception;

    //修改动态信息，根据主键ID
    int updateDtglInfo(SysDtgl bean)throws Exception;

    //删除动态信息，根据ID
    int deleteDtInfo(@Param("id")String id, @Param("cjr")String cjr)throws Exception;

    //分页查询需要审核的动态信息,区域名、首拼areacon/标题名、首拼btcon/审核状态shzt
    int selectTotalAudit(Map map)throws Exception;
    List<Map> selectDtAudit(Map map)throws Exception;

    List<Map> selectDtAuditTszt(Map map)throws Exception;

    //审核动态信息，审核操作即通过或未通过，参数审核结果shjg/主键ID
    int updateAudit(Map map)throws Exception;

    //统计（新闻采纳/动态信息）排名,约束条件开始、结束时间/市级行政区域/区县级行政区域
    List<Map> selectStatisticsDtInfo (Map map)throws Exception;

    //根据ID获取动态信息
    SysDtgl selectDtInfoById(Map map)throws Exception;

    SysDtyl getDtyl(Map map)throws Exception;

    //根据ID存储动态内容
    int updateDtnrById(Map map)throws Exception;

    //动态预览
    int saveDtyl(@Param("map") Map map)throws Exception;

    //动态预览
    int deleteDtyl(@Param("map") Map map)throws Exception;

    //根据动态ID和提交层级查询是否是第一次提交
    int selectDtshByDtidAndTjcj(Map map)throws Exception;

    //新增动态审核状态
    int insertDtsh(@Param("map") Map map)throws Exception;

    //新增动态信息提交记录
    int insertDtglTjjl(@Param("map") Map map)throws Exception;

    //通过动态ID删除动态审核状态信息
    int deleteDtshByDtid(String dtid)throws Exception;

    int deleteDtshByDtidTjcj(Map map)throws Exception;

    //根据发布位置和所在区域分页查询动态信息
    int selectNewsTotal(Map map)throws Exception;
    List<Map> selectNewsList(Map map)throws Exception;

    //根据动态ID获取新闻详情
    SysDtgl selectNewsInfo(String id)throws Exception;

    //根据发布位置获取动态信息
    List<Map> selectNewsListByFBWZ(Map map)throws Exception;

    //首页获取新闻统计排名
    List<Map> selectNewsRank(Map map)throws Exception;

    //首页获取新闻统计排名More
    List<Map> selectNewsRankMore(Map map)throws Exception;

    //首页获取图片新闻
    List<Map> selectPictureNews(Map map)throws Exception;
//根据当前时间更新置顶状态
    void updateTopStatus(@Param("date") Date date);
}
