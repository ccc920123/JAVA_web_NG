package com.scxd.dao;

import com.scxd.beans.database.SysJobRank;
import com.scxd.beans.database.SysJobScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 工作排名数据操作接口
 */
@Repository
public interface JobRankDao {

    //分页查询获取工作排名信息总数
    int selectTotalJobrank(Map map)throws Exception;

    //分页查询获取工作排名信息
    List<SysJobRank> selectJobrank(Map map)throws Exception;

    //工作排名项目启用或停止,参数是ID和状态state
    int updateState(Map map)throws  Exception;

    //删除排名项目,根据ID
    int deleteJobrank(String id)throws Exception;

    //删除打分信息，根据pmxm
    int deleteJobScore(String pmxm)throws Exception;

    //新增排名项目
    int insertJobrank(@Param("job") Map job)throws Exception;

    //修改排名项目
    int updateJobrank(@Param("job")Map job)throws Exception;

    //排名详情信息条数
    int selectTotalDetail(String id)throws Exception;

    //排名详情分页查询
    List<SysJobScore> selectDetailList(Map map)throws Exception;

    //打分操作,即更新打分的数据,根据评分表的评分区域ID
    int updateJobScore(@Param("score")Map score,@Param("arg1") String kpqyid,@Param("arg2")String pmxm)throws Exception;

    //获取打分信息根据考评区域id和排名项目id
    SysJobScore selectScore(String id,String kpqyid)throws Exception;

    //首页获取工作排名，默认显示最新时间的排名项目
    List<Map> selectJobRankOrderByPMSJ(String qyid)throws Exception;

    //根据排名项目ID即打分表中的pmxm获取单位、得分、扣分
    List<Map> selectJobRankScoreByPmxm(String pmxm)throws Exception;

    //批量新增工作排名打分表
    int insertJobScoreByList(@Param("areaGrade") List<Map> areaGrade)throws Exception;

    SysJobRank getJobrankByPMID(@Param("pmid") String pmid);
}
