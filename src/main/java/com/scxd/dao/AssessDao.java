package com.scxd.dao;

import com.scxd.beans.database.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *  工作考核
 */
@Repository
public interface AssessDao {

    //考核类型
    List<Map> selectAssessKhlx()throws Exception;

    //信息总数
    int selectTotal(Map map)throws Exception;

    // 查询分页列表
    List<SysAssess> selectSAssessList(Map assess)throws Exception;

    List<SysAssessDetails> selectAssessDetails(Map assess)throws Exception;

    //排名详情信息条数
    int selectTotalDetail(String id)throws Exception;

    //查询详情
    List<SysAssessInfo> selectAssessInfo(Map assess) throws Exception;
    List<SysAssessGy> selectAssessInfoGy(Map assess) throws Exception;

    List<SysAssessConfig> selectAssessConfig(String khqy) throws Exception;

    int selectAssessSave(@Param("assess") Map map) throws Exception;


    //信息总数
    int selectComplexTotal(Map map)throws Exception;
    // 查询分页列表
    List<SysComplex> selectComplexList(Map assess)throws Exception;

    int updateState(Map assess)throws Exception;

    int selectComplexTotalDetail(String id)throws Exception;

    List<SysComplexDetails> selectAssessComplexDetails(Map assess)throws Exception;


    int deleteAssess(String id)throws Exception;

    AssessCompInfoBean getAssessComplexMessage(@Param("id")String id);

    int getAssessComplexDeleted(String id)throws Exception;

    int getAssessComplexSorceDeleted(String id)throws Exception;
}
