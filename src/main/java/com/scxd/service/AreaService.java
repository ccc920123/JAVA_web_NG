package com.scxd.service;

import com.scxd.beans.Response;

import java.util.Map;

/**
 * 区域管理业务逻辑接口
 */
public interface AreaService {

    //页面根据所选区域得到对应的下级区域名
    Response getArea(String qyid)throws Exception;

    Response getAreaDetails(String qyid)throws Exception;

    //分页获取区域列表信息
    Response getAreas(Map map)throws Exception;

    //新增区域信息
    Response addAreas(Map map)throws Exception;

    //删除区域列表信息
    Response deleteAreas(String qyid)throws Exception;

    //修改区域信息
    Response alterAreasInfo(Map map)throws Exception;

    //根据行政级别和父级区域ID获取区域名称
    Response getQynameByXzjbAndFjqyid(Map map)throws Exception;

    //根据qyid获取上级区域名称和对应的ID
    Response getSjQynameByQyid(String qyid)throws Exception;

    //获取下级区域
    Response getXjqyById(String id)throws Exception;

    //根据区域所传的条件ID等于数据库中的QYID或SJQYID获取区域名和区域ID
    Response getAreaName(String id)throws Exception;

    //获取区域简介通过区域ID
    Response getAreaIntro(String qyid)throws Exception;

}
