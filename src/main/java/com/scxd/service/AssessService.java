package com.scxd.service;

import com.scxd.beans.Response;

import java.util.List;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 10:55 2018/11/19
 * @Modified By:
 */
public interface AssessService {

    // 考核类型
    public Response getKhlx() throws Exception;

    // 考核查询列表
    public Response getAssessList(Map map) throws Exception;

    public Response getAssessDetails(Map map) throws Exception;

    public Response getAssessInfo(Map map) throws Exception;

    public Response getAssessConfig() throws Exception;

    public Response getAssessSave(Map map) throws Exception;

    public Response getComplexList(Map map) throws Exception;

    public Response startOrStopState(Map map) throws Exception;

    public Response getAssessComplexDetail(Map map) throws Exception;

    public Response getAssessdeleted(String id) throws Exception;

    public Response getAssessComplexDeleted(String id) throws Exception;

    Response getAssessComplexMessage(String id);

    //首页获取工作排名，默认显示最新时间的排名项目
    Response getAssessRankByNewTime()throws Exception;

    Response justIsExist(String khkssj, String khjssj, String khlx);
}
