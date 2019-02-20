package com.scxd.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.scxd.beans.Response;
import com.scxd.beans.database.*;
import com.scxd.dao.AssessDao;
import com.scxd.dao.AssessStaticDao;
import com.scxd.dao.BaseDataDao;
import com.scxd.service.AssessService;
import com.scxd.toolkit.DateUtil;
import com.scxd.toolkit.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 10:55 2018/11/19
 * @Modified By:
 */
@Service
public class AssessServiceImpl implements AssessService {

    @Autowired
    private AssessDao assessDao;
    @Autowired
    AssessBuildServiceImpl  assessBuildService;
    @Autowired
    private HttpSession session;
    @Autowired
    AssessStaticDao assessStaticDao;
    @Autowired
    BaseDataDao baseDataDao;

    //考核类型
    @Override
    public Response getKhlx() throws Exception {
        List<Map> areaInfo = assessDao.selectAssessKhlx();
        return (areaInfo != null && areaInfo.size() != 0)?new Response().success(areaInfo):new Response().failure();
    }

    // 查询列表
    @Override
    public Response getAssessList(Map map) throws Exception {
        //条件判空
        if (map.size() == 0 || map == null)return new Response().failure("查询条件为空");

        //获取当前用户所在的区域ID
        map.put("khqy", new UtilClass().getUserQyid(session));

        //获取信息总数，总数为0则直接返回
        int total = assessDao.selectTotal(map);
        if (total == 0)return new Response().success(null,total);

        //信息不为空，分页查询数据
        map = UtilClass.getPaging(map);
        List<SysAssess> areaInfo = assessDao.selectSAssessList(map);
        return (areaInfo != null && areaInfo.size() != 0)?new Response().success(areaInfo,total):new Response().failure("获取数据失败");
    }

    @Override
    public Response getAssessDetails(Map map) throws Exception {
        int total = assessDao.selectTotalDetail((String)map.get("id"));
        if (total == 0)return new Response().success(null,total);
        //分页查询
        map = UtilClass.getPaging(map);
        map.put("end",100);
        List<SysAssessDetails> areaInfo = assessDao.selectAssessDetails(map);
        return (areaInfo != null && areaInfo.size() != 0)?new Response().success(areaInfo,total):new Response().failure("获取数据失败");
    }

    @Override
    public Response getAssessInfo(Map map) throws Exception {
        Map<String, Object> mapResult = new HashMap<>();

        List<SysAssessGy> areaInfoGy = assessDao.selectAssessInfoGy(map);
        mapResult.put("gyxx", areaInfoGy);

        List<SysAssessInfo> areaInfo = assessDao.selectAssessInfo(map);
        mapResult.put("fxxx", areaInfo);

        return (mapResult != null && mapResult.size() != 0)?new Response().success(mapResult):new Response().failure("获取数据失败");
    }

    @Override
    public Response getAssessConfig() throws Exception {

        //获取当前用户所在的区域ID
        String khqy = new UtilClass().getUserQyid(session);
        List<SysAssessConfig> areaInfo = assessDao.selectAssessConfig(khqy);

        return (areaInfo != null && areaInfo.size() != 0)?new Response().success(areaInfo):new Response().failure("获取数据失败");
    }

    @Override
    public Response getAssessSave(Map map) throws Exception {

        List<Map> list = (List<Map>) map.get("conditions");
        int row = 0;
        int khlx= Integer.valueOf((String) map.get("khlx"));
        Date khkssj= DateUtil.formmatDateYYYYMMDD((String) map.get("khkssj"));
        Date khjssj= DateUtil.formmatDateYYYYMMDD((String) map.get("khjssj"));

        for (int i = 0; i < list.size(); i++) {
            row = assessDao.selectAssessSave(list.get(i));
            if (row == 0){
                break;
            }
        }
        if (row != 0){
            try {

                return assessBuildService.buildCompreAssess(khlx, khkssj, khjssj);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return new Response().failure("保存配置失败");
    }

    @Override
    public Response getComplexList(Map map) throws Exception {
        //条件判空
        if (map.size() == 0 || map == null)return new Response().failure("查询条件为空");

        if (null == map.get("khqy")) {
            //获取当前用户所在的区域ID
            map.put("khqy", new UtilClass().getUserQyid(session));
        }

        //获取信息总数，总数为0则直接返回
        int total = assessDao.selectComplexTotal(map);
        if (total == 0)return new Response().success(null,total);

        //信息不为空，分页查询数据
        map = UtilClass.getPaging(map);
        List<SysComplex> areaInfo = assessDao.selectComplexList(map);
        return (areaInfo != null && areaInfo.size() != 0)?new Response().success(areaInfo,total):new Response().failure("获取数据失败");
    }

    @Override
    public Response startOrStopState(Map map) throws Exception {
        if (map.size() == 0 || map == null) return new Response().failure("查询条件为空");
        int num = assessDao.updateState(map);
        return (num != 0) ? new Response().success() : new Response().failure("更新失败");
    }

    @Override
    public Response getAssessComplexDetail(Map map) throws Exception {
        int total = assessDao.selectComplexTotalDetail((String) map.get("id"));
        if (total == 0)return new Response().success(null,total);
        //分页查询
        map = UtilClass.getPaging(map);
        map.put("end",100);
        List<SysComplexDetails> areaInfo = assessDao.selectAssessComplexDetails(map);
        return (areaInfo != null && areaInfo.size() != 0)?new Response().success(areaInfo,total):new Response().failure("获取数据失败");
    }

    @Override
    public Response getAssessdeleted(String id) throws Exception {
        int row = assessDao.deleteAssess(id);
        return (row != 0) ? new Response().success("删除信息成功") : new Response().failure("删除信息失败");
    }

    @Override
    public Response getAssessComplexMessage(String id) {
        if (!StringUtils.isEmpty(id)){
            AssessCompInfoBean assessCompInfoBean=  assessDao.getAssessComplexMessage(id);
            if (assessCompInfoBean!=null){
                return new Response().success(assessCompInfoBean);
            }else{
                return new Response().failure("未能查询到考核信息");
            }

        }else{
            return new Response().failure("查询信息为空");
        }
    }

    @Override
    public Response getAssessComplexDeleted(String id) throws Exception {
        int row = assessDao.getAssessComplexDeleted(id);
        if (row != 0){
            row = assessDao.getAssessComplexSorceDeleted(id);
        }
        return (row != 0) ? new Response().success("删除信息成功") : new Response().failure("删除信息失败");
    }

    //首页获取工作排名，默认显示最新时间的排名项目
    @Override
    public Response getAssessRankByNewTime() throws Exception {
        Map<String, Object> map = new HashMap<>();
        //获取由时间降序排列的排名项目ID,参数区域ID
        String qyid = (String) session.getAttribute("AreaId");
        map.put("khqy", qyid);
        map.put("pageNo", 1);
        map = UtilClass.getPaging(map);
        map.put("state", "1");
        List<SysComplex> areaInfo = assessDao.selectComplexList(map);
        if (areaInfo.size() == 0 || areaInfo == null) return new Response().failure("暂无排名项目");
        map.put("id", areaInfo.get(0).getId());
        List<SysComplexDetails> ranks = assessDao.selectAssessComplexDetails(map);
        map.put("pmmc", areaInfo.get(0).getBt());
        map.put("ranks", ranks);
        return (map.size() != 0) ? new Response().success(map) : new Response().failure("获取数据为空");
    }

    @Override
    public Response justIsExist(String khkssjs, String khjssjs, String khlxs) {
        int khlx= Integer.valueOf(khlxs);
        Date khkssj= DateUtil.formmatDateYYYYMMDD(khkssjs);
        Date khjssj= DateUtil.formmatDateYYYYMMDD(khjssjs);
        UtilClass utilClass = new UtilClass();
        String qyid = utilClass.getUserQyid(session);
        String dw_code = baseDataDao.getDwCode(qyid);
        if (dw_code==null){
            return new Response().failure("无当前区域的基础数据或当前区域为城区");
        }
        String khid = assessStaticDao.getInfoId(dw_code, khlx, khkssj, khjssj);
        Map<String,Object> map=new HashMap<>();
        if (khid != null && khid != "") {
            map.put("isExist",true);
        }else{
            map.put("isExist",false);
        }
        return new Response().success(map);
    }
}

