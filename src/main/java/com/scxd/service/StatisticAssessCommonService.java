package com.scxd.service;

import com.scxd.beans.database.*;
import com.scxd.beans.extendbeans.StatisticQdzBean;
import com.scxd.dao.AssessStaticDao;
import com.scxd.toolkit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 13:54 2018/11/27
 * @Modified By:
 */
@Service
public class StatisticAssessCommonService {
    @Autowired
    AssessStaticDao assessStaticDao;

    /**
     * 将统计的数据分别插入到3个表中
     *
     * @param list
     * @param assessConfigBean
     * @param kssj
     * @param jssj
     * @param khlx
     * @param uuid
     */
    public void statisticAssessInsertDB(List<StatisticQdzBean> list, AssessConfigBean assessConfigBean, Date kssj, Date jssj, int khlx, String uuid) {
        long avg = assessConfigBean.getDbz();
        long xxfz = assessConfigBean.getXxfz();
//        String khsj = DateUtil.getKHSJ(kssj, jssj);
//        String khbt = "月度工作考核(" + DateUtil.getKHBTSJ(kssj) + ")";
//        if (khlx == 2) {
//            khbt = "季度工作考核(" + DateUtil.getKHBTJD(kssj) + ")";
//        } else if (khlx == 3) {
//            khbt = "年度工作考核(" + DateUtil.getKHBTND(kssj) + ")";
//        }
        int i = 0;
        for (StatisticQdzBean bean : list
                ) {
//            String uuid_info = UUID.randomUUID().toString();
//            AssessInfoBean assessInfoBean = new AssessInfoBean();
//            assessInfoBean.setId(uuid_info);
//            assessInfoBean.setKhqy(bean.getQyid());
//            assessInfoBean.setKhsj(khsj);
//            assessInfoBean.setBt(bean.getQyname()+khbt);
//            assessInfoBean.setKssj(kssj);
//            assessInfoBean.setJssj(jssj);
//            assessInfoBean.setKhlx(khlx);
            double df = xxfz;
            String mx = "平均" + ((double) Math.round(bean.getQdsvg() * 100)) / 100;
            if (bean.getQdsvg() < avg) {
                if (bean.getQdsvg() == 0) {
                    df = 0.0d;
                    mx = mx + "，低于达标线，得0分";
                } else {
                    i++;
                    if (i == 1) {
                        df = df * 0.95;
                    } else if (i == 2) {
                        df = df * 0.90;
                    } else if (i == 3) {
                        df = df * 0.85;
                    } else {
                        df = df * ((85 - 2 * (i - 3)) / 100d);//配置的分值*（85-2*（名次-3））%
                    }

                    mx = mx + "，低于达标线，排名第" + i + "位";
                }
            } else {
                mx = mx + "，超过达标线";
            }
//            String uuid_sorce = UUID.randomUUID().toString();
//            AssessInfoScoreBean assessInfoScoreBean = new AssessInfoScoreBean();
//            assessInfoScoreBean.setId(uuid_sorce);
//            assessInfoScoreBean.setKhdf(df);
//            assessInfoScoreBean.setKhdx(bean.getQyid());
//            assessInfoScoreBean.setKhid(uuid_info);
////（平均）or（同比增率）xxx，（超过达标线） or（低于达标线，排名第x位）；

            AssessInfoDetailBean infoDetailBean = new AssessInfoDetailBean();
            infoDetailBean.setKhid(uuid);
            infoDetailBean.setDfid(bean.getQyid());
            infoDetailBean.setDx(assessConfigBean.getDx());
            infoDetailBean.setXx(assessConfigBean.getXx());
            infoDetailBean.setDxdf(df);
            infoDetailBean.setMxjg(mx);
//            assessStaticDao.addAssessInfo(assessInfoBean);
//            assessStaticDao.addAssessInfoScore(assessInfoScoreBean);
            assessStaticDao.addAssessInfoDetail(infoDetailBean);

        }
    }


    /**
     * 将统计的数据分别插入到3个表中
     *
     * @param list
     * @param assessConfigBean
     * @param kssj
     * @param jssj
     * @param khlx
     * @param uuid
     */
    public void statisticAssessInsertDB_ZL(List<StatisticQdzBean> list, AssessConfigBean assessConfigBean, Date kssj, Date jssj, int khlx, String uuid) {
        long avg = assessConfigBean.getDbz();
        long xxfz = assessConfigBean.getXxfz();
        int i = 0;
        for (StatisticQdzBean bean : list
                ) {
            double df = xxfz;
            String mx = "";
            if (bean.getQdsvg() != 0) {
                mx = "增长量为" + bean.getQdsvg();
            } else {
                mx = "增量没有变化";
                df = 0;
            }
////（平均）or（同比增率）xxx，（超过达标线） or（低于达标线，排名第x位）；

            AssessInfoDetailBean infoDetailBean = new AssessInfoDetailBean();
            infoDetailBean.setKhid(uuid);
            infoDetailBean.setDfid(bean.getQyid());
            infoDetailBean.setDx(assessConfigBean.getDx());
            infoDetailBean.setXx(assessConfigBean.getXx());
            infoDetailBean.setDxdf(df);
            infoDetailBean.setMxjg(mx);
            assessStaticDao.addAssessInfoDetail(infoDetailBean);

        }
    }

    public void insertLog(AssessLogBean logBean) {
        if (logBean != null) {
            assessStaticDao.addAssessLog(logBean);
        }
    }
}
