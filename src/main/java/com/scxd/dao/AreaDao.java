package com.scxd.dao;

import com.scxd.beans.database.SysAddArea;
import com.scxd.beans.database.SysArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 区域管理，数据库连接接口
 */
@Repository
public interface AreaDao {

    //获取所选区域的下级区域和上级区域
    List<SysArea> selectAreaInfo(String qyid)throws Exception;

    SysArea selectAreaDetails(String qyid)throws Exception;

    //无条件获取区域信息总数或根据区域名或首拼获取区域信息总数
    int selectTotal(Map map)throws Exception;

    //有条件或无条件的分页获取区域信息列表
    List<Map> selectAreaList(Map map)throws Exception;

    //新增区域信息列表
    int insertAreas(SysAddArea sysAddArea)throws Exception;

    //删除区域信息列表
    int deleteAreas(String qyid)throws Exception;

    //根据区域ID修改区域信息
    int updateAreaInfo(@Param("area") Map area)throws Exception;

    //根据行政级别和父级区域ID获取区域名称,这里用map是因为返回的数据只有两项：区域名称和ID
    List<Map> selectQynameAndQyidByFjqyidAndXzjb(Map map)throws Exception;

    //根据区域ID获取上级区域的名称、区域ID、行政级别
    List<Map> selectSjqyByQyid(String qyid)throws Exception;

    //获取下级区域
    List<Map> selectXjqyByQyid(String qyid)throws Exception;

    //根据区域所传的条件ID等于数据库中的QYID或SJQYID获取区域名和区域ID
    List<Map> selectAreaName(String id)throws Exception;

    //通过区域ID获取区域简介
    String selectQyjjByqyid(String qyid)throws Exception;

    //根据SJQYID获取区域名和区域区域ID
    List<String> selectAreaIdAreaNameByUpAreaId(String upAreaId);

    //根据区域ID获取区域名和下级区域名
    List<String> selectAreaNameByAreaId(String areaId)throws Exception;

    SysArea getSysAreaBy(@Param("qyid") String qyid);

    //根据区域id查询是否存在
    int selectQueryqyid(String qyid)throws Exception;
    List<SysArea> getAreasByQyAndLevel(@Param("qyid") String qyid,@Param("xzjb") int xzjb);
}
