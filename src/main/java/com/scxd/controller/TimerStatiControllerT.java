package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.beans.database.AssessCompInfoBean;
import com.scxd.beans.database.AssessConfigBean;
import com.scxd.beans.database.AssessInfoBean;
import com.scxd.beans.database.SysArea;
import com.scxd.dao.AreaDao;
import com.scxd.dao.AssessStaticDao;
import com.scxd.dao.BaseDataDao;
import com.scxd.service.*;
import com.scxd.toolkit.DateUtil;
import com.scxd.toolkit.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 16:16 2018/11/21
 * @Modified By: 这个类不用  用AssessBuildServiceImpl
 */
//@Component
//@EnableScheduling
//@Component
public class TimerStatiControllerT {
    //
//    @Autowired
//    AssessStaticDao assessStaticDao;
    @Autowired
    StatisticAssessQDZ statisticAssessQDZ;
    @Autowired
    StatisticAssessAQXC statisticAssessAQXC;
    @Autowired
    StatisticAssessQTXHD statisticAssessQTXHD;
    @Autowired
    StatisticAssessHYGZ statisticAssessHYGZ;
    @Autowired
    StatisticBaseCar statisticBaseCar;
    @Autowired
    StatisticBaseDriver statisticBaseDriver;
    @Autowired
    StatisticBaseFCar statisticBaseFCar;
    @Autowired
    StatisticBaseJXRoad statisticBaseJXRoad;
    @Autowired
    StatisticBaseRoad statisticBaseRoad;
    @Autowired
    StatisticWorkHidden statisticWorkHidden;
    @Autowired
    StatisticWorkUnit statisticWorkUnit;
    @Autowired
    AreaDao areaDao;
    @Autowired
    StatisticAssessLJLC statisticAssessLJLC;

    @Autowired
    AssessStaticDao assessStaticDao;
    @Autowired
    BaseDataDao baseDataDao;
    @Autowired
    private HttpSession session;

    //    @Scheduled(cron = "0 10 9 10 * ?")//每月1日上午3:00触发"
//    @Scheduled(cron = "0 0/2 * * * ?")//0 /1 * * ?  每隔1分钟执行一次
    public Response buildCompreAssess(int khlx,
                                      Date khkssj,
                                      Date khjssj) {
        UtilClass utilClass = new UtilClass();
        String qyid = utilClass.getUserQyid(session);
        String dw_lb = utilClass.getDwLBBySession(session);
        //省
        List<AssessConfigBean> assessConfigBeans = assessStaticDao.getAllAssessConfigByCode(qyid);
        if (assessConfigBeans != null && assessConfigBeans.size() > 0) {
           String dw_code= baseDataDao.getDwCode(qyid);
           if (dw_code!=null&&dw_code!=""){
               createInfoTableData(qyid, dw_lb, assessConfigBeans, khkssj, khjssj, khlx);
           }else{
               return  new Response().failure("未能找到当前单位");
           }

        } else {
            return new Response().failure("当前区域未找到考核配置");
        }
        return new Response().success();
    }

    /**
     * 生成主表信息
     *
     * @param qyid
     * @param qycj
     * @param assessConfigBeans
     * @param khkssj
     * @param khjssj
     * @param khlx
     */
    private void createInfoTableData(String qyid, String qycj, List<AssessConfigBean> assessConfigBeans,
                                     Date khkssj, Date khjssj, int khlx) {
        String uuid = insertInfoTable(qyid, khkssj, khjssj, khlx);
        createScoreDataAndDetail(qyid, qycj, assessConfigBeans, khkssj, khjssj, khlx, uuid);

        //月

    }

    /**
     * 生成得分表和详情表
     *
     * @param qyid
     * @param qycj
     * @param assessConfigBeans
     * @param khkssj
     * @param khjssj
     * @param khlx
     * @param uuid
     */
    private void createScoreDataAndDetail(String qyid, String qycj, List<AssessConfigBean> assessConfigBeans, Date khkssj, Date khjssj, int khlx, String uuid) {
/**
 * 生成详细表
 */
        justProjectAssessConfig(qyid, qycj, assessConfigBeans, khkssj, khjssj, khlx, uuid);
        /**
         * 生成得分表
         */
        assessStaticDao.addAssessInfoScores(uuid);

    }

    /**
     * @param qyid
     * @param kssj
     * @param jssj
     * @param khlx
     * @return uuid
     */
    private String insertInfoTable(String qyid, Date kssj, Date jssj, int khlx) {
        String qyname = assessStaticDao.getQYNAME(qyid);
        String khsj = DateUtil.getKHSJ(kssj, jssj);
        String khbt = "月度工作考核(" + DateUtil.getKHBTSJ(kssj) + ")";
        if (khlx == 2) {
            khbt = "季度工作考核(" + DateUtil.getKHBTJD(kssj) + ")";
        } else if (khlx == 3) {
            khbt = "年度工作考核(" + DateUtil.getKHBTND(kssj) + ")";
        }
        String uuid_info = UUID.randomUUID().toString();
        AssessInfoBean assessInfoBean = new AssessInfoBean();
        assessInfoBean.setId(uuid_info);
        assessInfoBean.setKhqy(qyid);
        assessInfoBean.setKhsj(khsj);
        assessInfoBean.setBt(qyname + khbt);
        assessInfoBean.setKssj(kssj);
        assessInfoBean.setJssj(jssj);
        assessInfoBean.setKhlx(khlx);
        assessStaticDao.addAssessInfo(assessInfoBean);
        return uuid_info;
    }

    /**
     * 判断项目考核
     *
     * @param qyid              传的是单位code
     * @param qycj              单位表中的 dw_lb  对应的是行政区划级别
     * @param assessConfigBeans
     * @param khkssj
     * @param khjssj
     * @param khlx
     * @param uuid              主表的uuid
     */
    private void justProjectAssessConfig(String qyid, String qycj, List<AssessConfigBean> assessConfigBeans,
                                         Date khkssj, Date khjssj, int khlx, String uuid) {
        for (int i = 0; i < assessConfigBeans.size(); i++) {
            switch (assessConfigBeans.get(i).getDx()) {
                case "01":
                    switch (assessConfigBeans.get(i).getXx()) {
                        case "01"://劝导站工作统计
                            statisticAssessQDZ.statisticAssessQDZ(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;
                        case "02"://路检路查
                            statisticAssessLJLC.statisticAssessLJLC(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;
                        case "03"://安全宣传
                            statisticAssessAQXC.statisticAssessAQXC(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;
                        case "04"://群体性活动
                            statisticAssessQTXHD.statisticAssessQTXHD(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;
                        case "05"://会议工作
                            statisticAssessHYGZ.statisticAssessHYGZ(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;

                    }
                    break;
                case "02":
                    switch (assessConfigBeans.get(i).getXx()) {
                        case "01":
                            statisticBaseCar.statisticBase(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;
                        case "02":
                            statisticBaseFCar.statisticBase(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;
                        case "03":
                            statisticBaseDriver.statisticBase(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;
                        case "04":
                            statisticBaseJXRoad.statisticBase(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;
                        case "05":
                            statisticBaseRoad.statisticBase(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;

                    }
                    break;
                case "03":
                    switch (assessConfigBeans.get(i).getXx()) {
                        case "01":
                            statisticWorkHidden.statisticWork(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;
                        case "02":
                            statisticWorkUnit.statisticWork(qyid, qycj, assessConfigBeans.get(i), khkssj, khjssj, khlx, uuid);
                            break;

                    }
                    break;
            }
        }
    }

}
