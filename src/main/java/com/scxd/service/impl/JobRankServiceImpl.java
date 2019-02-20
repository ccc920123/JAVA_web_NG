package com.scxd.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.scxd.beans.Response;
import com.scxd.beans.database.SysJobRank;
import com.scxd.beans.database.SysJobRankConfigBean;
import com.scxd.beans.database.SysJobRankDetailBean;
import com.scxd.beans.database.SysJobScore;
import com.scxd.beans.extendbeans.JobSorceXXTableBodyBean;
import com.scxd.beans.extendbeans.JobSorceXXTableHeadBean;
import com.scxd.dao.AreaDao;
import com.scxd.dao.JobRankConfigDao;
import com.scxd.dao.JobRankDao;
import com.scxd.service.JobRankService;
import com.scxd.toolkit.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工作排名业务逻辑实现类
 */
@Service
public class JobRankServiceImpl implements JobRankService {

    @Autowired
    private JobRankDao jobRankDao;
    @Autowired
    private HttpSession session;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    JobRankConfigDao jobRankConfigDao;

    //分页查询获取工作排名信息
    @Override
    public Response getJobRankList(Map map) throws Exception {
        if (map.size() == 0 || map == null) return new Response().failure("查询条件为空");
        map = UtilClass.getPagingfifteen(map);
        //获取用户所在的区域ID
        String qyid = new UtilClass().getUserQyid(session);
        map.put("qyid", qyid);
        int total = jobRankDao.selectTotalJobrank(map);
        if (total == 0) return new Response().success(null, total);
        //分页查询
        map = UtilClass.getPaging(map);
        List<SysJobRank> result = jobRankDao.selectJobrank(map);
        for (SysJobRank bean : result) {
            if (null == bean.getKssj() || "null".equals(bean.getKssj())) {
                bean.setKssj("");
            }
            if (null == bean.getJssj() || "null".equals(bean.getJssj())) {
                bean.setJssj("");
            }
        }
        return (result != null && result.size() != 0) ? new Response().success(result, total) : new Response().failure("获取数据失败");
    }

    //工作排名项目启用或停止,参数是ID和状态state
    @Override
    public Response startOrStopState(Map job) throws Exception {
        if (job.size() == 0 || job == null) return new Response().failure("查询条件为空");
        int num = jobRankDao.updateState(job);
        return (num != 0) ? new Response().success() : new Response().failure("更新失败");
    }

    //新增排名项目
    @Override
    public Response addPmxm(Map map) throws Exception {
        if (map.size() == 0 || map == null) return new Response().failure("新增内容为空");
        //添加新增项目主键
        String id = UUID.randomUUID().toString();
        map.put("id", id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date kssj = dateFormat.parse((String) map.get("kssj"));
        map.put("kssj", kssj);
        map.put("jssj", dateFormat.parse((String) map.get("jssj")));
        map.put("cjsj", new Date());
        int row = jobRankDao.insertJobrank(map);
        boolean isSuccess = addJobScoreList(id, (String) map.get("pmqy"));
        /**
         * 默认添加5个大项在小项中
        * */
        List<SysJobRankConfigBean> list =new ArrayList<>();
        SysJobRankConfigBean sysJobRankConfigBean=new SysJobRankConfigBean();
        sysJobRankConfigBean.setDx("01");
        sysJobRankConfigBean.setPmid(id);
        sysJobRankConfigBean.setXxname("组织领导");
        list.add(sysJobRankConfigBean);
        SysJobRankConfigBean sysJobRankConfigBean02=new SysJobRankConfigBean();
        sysJobRankConfigBean02.setDx("02");
        sysJobRankConfigBean02.setPmid(id);
        sysJobRankConfigBean02.setXxname("贯彻落实");
        list.add(sysJobRankConfigBean02);
        SysJobRankConfigBean sysJobRankConfigBean03=new SysJobRankConfigBean();
        sysJobRankConfigBean03.setDx("03");
        sysJobRankConfigBean03.setPmid(id);
        sysJobRankConfigBean03.setXxname("督导检查");
        list.add(sysJobRankConfigBean03);
        SysJobRankConfigBean sysJobRankConfigBean04=new SysJobRankConfigBean();
        sysJobRankConfigBean04.setDx("04");
        sysJobRankConfigBean04.setPmid(id);
        sysJobRankConfigBean04.setXxname("工作成效");
        list.add(sysJobRankConfigBean04);
        SysJobRankConfigBean sysJobRankConfigBean05=new SysJobRankConfigBean();
        sysJobRankConfigBean05.setDx("05");
        sysJobRankConfigBean05.setPmid(id);
        sysJobRankConfigBean05.setXxname("扣分");
        list.add(sysJobRankConfigBean05);
        addConfigByPMID(list);
        return (row != 0 && isSuccess) ? new Response().success() : new Response().failure("新增失败");
    }

    //修改排名项目
    @Override
    public Response alterPmxm(Map map) throws Exception {
        if (map.size() == 0 || map == null) return new Response().failure("修改内容为空");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date kssj = dateFormat.parse((String) map.get("kssj"));
        map.put("kssj", kssj);
        map.put("jssj", dateFormat.parse((String) map.get("jssj")));
        int row = jobRankDao.updateJobrank(map);
        return (row != 0) ? new Response().success() : new Response().failure("修改失败");
    }

    //删除排名项目,根据ID
    @Override
    public Response deletePmxm(String id) throws Exception {
        int num = jobRankDao.deleteJobrank(id);
        if (num == 0) return new Response().failure("删除失败");
        //删除打分表中和pmxm相关的数据

        num = jobRankDao.deleteJobScore(id);
        jobRankConfigDao.deleteConfigByPmid(id);
        jobRankConfigDao.deleteDetailByPmid(id);
        return (num != 0) ? new Response().success() : new Response().failure("删除失败");
    }

    //查看排名项目详情，内容是各个考评区域的打分情况，所以返回的是LIST
    //map中参数 id 为对应的排名项目ID，pageNo页码
    @Override
    public Response getDetail(Map map) throws Exception {
        int total = jobRankDao.selectTotalDetail((String) map.get("id"));
        if (total == 0) return new Response().success(null, total);
        //分页查询
        map = UtilClass.getPaging(map);
        map.put("end", 100);
        List<SysJobScore> result = jobRankDao.selectDetailList(map);
        return (result != null && result.size() != 0) ? new Response().success(result, total) : new Response().failure("获取数据失败");
    }

    //打分操作,即更新打分的数据,根据评分表的评分区域ID
    @Override
    public Response grade(Map map) throws Exception {
        List<Map> list = (List<Map>) map.get("conditions");
        if (list.size() == 0 || list == null) return new Response().failure("修改内容为空");
        //计算总分
        float zzld = 0;
        float gcls = 0;
        float ddjc = 0;
        float gzcx = 0;
        float kf = 0;
        float zf = 0;
        int row = 0;
        for (int i = 0; i < list.size(); i++) {
            zzld = Float.parseFloat((String) list.get(i).get("zzld"));
            gcls = Float.parseFloat((String) list.get(i).get("gcls"));
            ddjc = Float.parseFloat((String) list.get(i).get("ddjc"));
            gzcx = Float.parseFloat((String) list.get(i).get("gzcx"));
            kf = Float.parseFloat((String) list.get(i).get("kf"));//这是扣分
            zf = zzld + gcls + ddjc + gzcx - kf;
            list.get(i).put("zf", zf);
            row = jobRankDao.updateJobScore(list.get(i), (String) list.get(i).get("kpqyid"), (String) list.get(i).get("pmxm"));
            if (row == 0) {
                break;
            }
        }
        return (row != 0) ? new Response().success() : new Response().failure("修改失败");
    }

    //获取打分信息根据考评区域id和排名项目id
    @Override
    public Response getScore(Map map) throws Exception {
        if (map.size() == 0 || map == null) return new Response().failure("查询条件为空");
        SysJobScore result = jobRankDao.selectScore((String) map.get("pmxm"), (String) map.get("kpqyid"));
        return (result != null) ? new Response().success(result) : new Response().failure("获取数据失败");
    }

    //首页获取工作排名，默认显示最新时间的排名项目
    @Override
    public Response getJobRankByNewTime() throws Exception {
        Map<String, Object> map = new HashMap<>();
        //获取由时间降序排列的排名项目ID,参数区域ID
        String qyid = (String) session.getAttribute("AreaId");
        List<Map> ids = jobRankDao.selectJobRankOrderByPMSJ(qyid);
        if (ids.size() == 0 || ids == null) return new Response().failure("暂无排名项目");
        List<Map> ranks = jobRankDao.selectJobRankScoreByPmxm((String) ids.get(0).get("ID"));
        map.put("pmmc", ids.get(0).get("PMMC"));
        map.put("ranks", ranks);
        return (map.size() != 0) ? new Response().success(map) : new Response().failure("获取数据为空");
    }

    //首页工作排名展示更多内容
    @Override
    public Response getMoreRankInfo(Map map) throws Exception {
//        String qyid = (String)session.getAttribute("AreaId");
//        List<Map> ids = jobRankDao.selectJobRankOrderByPMSJ((String) map.get("id"));
//        if (ids.size() == 0 || ids == null) return new Response().failure("暂无排名项目");
        map = UtilClass.getPagingfifteen(map);
        int total = jobRankDao.selectTotalDetail((String) map.get("id"));
        List<SysJobScore> ranks = jobRankDao.selectDetailList(map);
        if (ranks.size() == 0 || ranks == null) return new Response().failure("查询数据为空");
        map.put("ranks", ranks);
        return new Response().success(map, total);
    }

    //分页查询获取工作排名信息
    @Override
    public Response getMoreRankList(Map map) throws Exception {
        if (map.size() == 0 || map == null) return new Response().failure("查询条件为空");
        String qyid = (String) session.getAttribute("AreaId");
        map = UtilClass.getPagingfifteen(map);
        map.put("qyid", qyid);
        int total = jobRankDao.selectTotalJobrank(map);
        if (total == 0) return new Response().success(null, total);
        //分页查询
        map = UtilClass.getPaging(map);
        List<SysJobRank> result = jobRankDao.selectJobrank(map);
        List<SysJobRank> result2 = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getState() == 1) {
                if (null == result.get(i).getKssj() || "null".equals(result.get(i).getKssj())) {
                    result.get(i).setKssj("");
                }
                if (null == result.get(i).getJssj() || "null".equals(result.get(i).getJssj())) {
                    result.get(i).setJssj("");
                }
                result2.add(result.get(i));
            }
        }
        return (result2 != null && result2.size() != 0) ? new Response().success(result2, result2.size()) : new Response().failure("获取数据失败");
    }

    @Override
    public Response getConfigByPMID(String pmid) {
        if (pmid != null && !"".equals(pmid)) {
            List<SysJobRankConfigBean> maps = jobRankConfigDao.getConfigByPMID(pmid);
            return new Response().success(maps);
        } else {
            return new Response().failure("无当前工作排名");
        }

    }

    @Override
    public Response addConfigByPMID(List<SysJobRankConfigBean> list) throws Exception {
        if (list != null && list.size() > 0) {
            int i = insertConfigAndDetail(list);//jobRankConfigDao.insertConfig(list);
            if (i > 0) {
                return new Response().success("保存成功");
            } else {
                return new Response().failure("保存失败");
            }
        } else {
            return new Response().success();
        }

    }

    @Override
    public Response deleteConfigByID(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return new Response().failure("删除数据为空");
        } else {
            int i = jobRankConfigDao.deleteDetail(uuid);
            int j = jobRankConfigDao.deleteConfig(uuid);
            if (i > 0 && j > 0) {
                return new Response().success();
            }
        }

        return new Response().failure("删除失败");
    }

    @Override
    public Response saveConfigBean(List<SysJobRankConfigBean> list) {
        List<SysJobRankConfigBean> changelist = new ArrayList<>();
        List<SysJobRankConfigBean> addlist = new ArrayList<>();
        for (SysJobRankConfigBean sysbean : list
                ) {
            if (StringUtils.isEmpty(sysbean.getId())) {
                addlist.add(sysbean);
            } else {
                changelist.add(sysbean);
            }
        }
        int insert = 0;
        int change = 0;
        if (addlist != null && addlist.size() > 0) {
            insert = insertConfigAndDetail(addlist);//jobRankConfigDao.insertConfig(list);
        }
        if (insert >= addlist.size() && changelist.size() > 0) {
            change += changeConfigBeans(changelist);
        }
        if (insert >= addlist.size() && change == changelist.size()) {
            return new Response().success();
        } else {
            return new Response().failure("设置小项失败");
        }

    }

    /**
     * 计算小项总分，生成得分表
     * @param uuid
     * @return
     */
    @Override
    public Response saveSorceTable(String uuid) {
        if (uuid != null) {
            int i = jobRankConfigDao.saveSorceTable(uuid);
            if (i > 0) {
                jobRankConfigDao.getSorceTableZF(uuid);
                return new Response().success();
            }
        }
        return new Response().failure("得分表更新失败");
    }

    @Override
    public Response getXXSorceTable(String pmid, String khqyid) {
//        if (!StringUtils.isEmpty(pmid) && !StringUtils.isEmpty(khqyid)) {
//            Map mapConfigDetail = jobRankConfigDao.getJobDetailMessage(pmid, khqyid);
//            if (mapConfigDetail != null) {
//                Map<String, Object> map = new HashMap<>();
//                jobRankConfigDao.getXXSorceTable(pmid, khqyid);
//                map.put("detail", mapConfigDetail);
//
//            } else {
//                return new Response().failure("未能查询到当前排名信息");
//            }
//        }
        return new Response().failure("查询的数据为空");
    }

    /**
     * 修改小项
     *
     * @param changelist
     * @return
     */
    private int changeConfigBeans(List<SysJobRankConfigBean> changelist) {
        int change = 0;
        for (SysJobRankConfigBean sysConfig : changelist
                ) {
            change += jobRankConfigDao.changeConfigBean(sysConfig);
        }
        return change;
    }

    private int insertConfigAndDetail(List<SysJobRankConfigBean> list) {
        int ii = 0;
        String qyid = new UtilClass().getUserQyid(session);
        List<String> areas = areaDao.selectAreaIdAreaNameByUpAreaId(qyid);
        for (SysJobRankConfigBean sysConfig : list
                ) {
            sysConfig.setId(UUID.randomUUID().toString());
            ii += jobRankConfigDao.insertConfigBean(sysConfig);
        }
        if (areas.size() == 0) {//如果没有下级区域,直接插入config表

        } else {
            for (int i = 0; i < areas.size(); i++) {
                String area = areas.get(i);
                for (SysJobRankConfigBean sysConfig : list
                        ) {
                    String detailid = UUID.randomUUID().toString();
                    String xxid = sysConfig.getId();
                    SysJobRankDetailBean sysJobRankDetailBean = new SysJobRankDetailBean();
                    sysJobRankDetailBean.setId(detailid);
                    sysJobRankDetailBean.setPmid(sysConfig.getPmid());
                    sysJobRankDetailBean.setPmqy(area);
                    sysJobRankDetailBean.setDx(sysConfig.getDx());
                    sysJobRankDetailBean.setSorce(0f);
                    sysJobRankDetailBean.setXx(xxid);
                    ii += jobRankConfigDao.insertJobDetailBean(sysJobRankDetailBean);
                }
            }

        }
        return ii;
    }

    //新增排名项目之后需要添加该排名项目下的所有区域的初始打分信息
    //参数排名项目ID对应打分表中每个考评区域的排名项目pmxm
    //排名区域是所有考评区域的父级区域
    private boolean addJobScoreList(String pmxm, String pmqy) throws Exception {
        //根据排名区域获取下属区域并组装List<Map>
        List<String> areas = areaDao.selectAreaIdAreaNameByUpAreaId(pmqy);
        List<Map> areaGrade = new ArrayList<>();
        int row = 0;
        if (areas.size() == 0) {
            row = 0;
        } else {
            for (int i = 0; i < areas.size(); i++) {
                Map map = new HashMap();
                map.put("kpqyid", areas.get(i));
                map.put("pmxm", pmxm);
                map.put("zzld", 0);
                map.put("gcls", 0);
                map.put("ddjc", 0);
                map.put("gzcx", 0);
                map.put("kf", 0);
                map.put("zf", 0);
                areaGrade.add(map);
            }
            row = jobRankDao.insertJobScoreByList(areaGrade);
        }
        return (row != 0) ? true : false;
    }

    //查看排名项目详情，内容是各个考评区域的打分情况，所以返回的是LIST
    //map中参数 id 为对应的排名项目ID，pageNo页码
    @Override
    public Response getDetailSorce(Map map) throws Exception {
        String id = (String) map.get("id");
        int total = jobRankDao.selectTotalDetail(id);
        if (total == 0) return new Response().success(null, total);
        //分页查询
        map = UtilClass.getPaging(map);
        map.put("end", 100);
        Map<String, Object> resultMap = new HashMap();
        List<JobSorceXXTableHeadBean> sorceXXTableHeadBeanList = jobRankConfigDao.getXXForTableView(id);
        resultMap.put("head", sorceXXTableHeadBeanList);
        List<SysJobScore> sysJobScores = jobRankDao.selectDetailList(map);
        List<JobSorceXXTableBodyBean> result = null;
        if (sysJobScores.size() > 0) {
            result=new ArrayList<>();
            for (SysJobScore sysJobScore :
                    sysJobScores) {
                JobSorceXXTableBodyBean jobSorceXXTableBodyBean=new JobSorceXXTableBodyBean();
                jobSorceXXTableBodyBean.setQyid(sysJobScore.getKpqyid());
                jobSorceXXTableBodyBean.setQyname(sysJobScore.getKpqymc());
                jobSorceXXTableBodyBean.setZf(sysJobScore.getZf());
                jobSorceXXTableBodyBean.setPm(sysJobScore.getPm());
                List<SysJobRankDetailBean> listDetail=jobRankConfigDao.getJobDetailMessage(id,sysJobScore.getKpqyid());
                jobSorceXXTableBodyBean.setSysJobRankDetailBeans(listDetail);
                result.add(jobSorceXXTableBodyBean);
            }
        }
        resultMap.put("tbody", result);
        return (result != null && result.size() != 0) ? new Response().success(resultMap, total) : new Response().failure("获取数据失败");
    }

    @Override
    public Response gradeByList(String pmid, List<SysJobRankDetailBean> listDetail) {
        int i=0;
        if (listDetail!=null&&listDetail.size()>0){
            for (SysJobRankDetailBean sysJobRankDetailBean:
            listDetail) {
              i+=  jobRankConfigDao.updateDetailByBean(sysJobRankDetailBean);
            }
            if (i==listDetail.size()){
                saveSorceTable(pmid);
                return new Response().success();
            }else{
                return new Response().failure("请重试");
            }

        }else{
            return new Response().failure("传入的打分为空");
        }
    }

    @Override
    public Response getJobrankByPMID(String pmid) {
        if (!StringUtils.isEmpty(pmid)){
            SysJobRank sysJobRank=  jobRankDao.getJobrankByPMID(pmid);
            if (sysJobRank!=null){
                return new Response().success(sysJobRank);
            }else{
                return new Response().failure("未能查询到排名信息");
            }

        }else{
            return new Response().failure("查询信息为空");
        }
    }

}
