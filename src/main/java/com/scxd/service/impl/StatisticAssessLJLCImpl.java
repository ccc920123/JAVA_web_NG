package com.scxd.service.impl;

import com.scxd.beans.database.AssessConfigBean;
import com.scxd.beans.database.AssessLogBean;
import com.scxd.beans.extendbeans.StatisticQdzBean;
import com.scxd.dao.StatisticAssessLJLCDao;
import com.scxd.service.StatisticAssessCommonService;
import com.scxd.service.StatisticAssessLJLC;
import com.scxd.toolkit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther:陈攀
 * @Description: 路检路查
 * @Date:Created in 11:18 2018/11/27
 * @Modified By:
 */
@Service
public class StatisticAssessLJLCImpl implements StatisticAssessLJLC {
    @Autowired
    StatisticAssessLJLCDao statisticAssessLJLCDao;
    @Autowired
    StatisticAssessCommonService assessCommonService;

    @Override
    public void statisticAssessLJLC(String qyid, String qycj, AssessConfigBean assessConfigBean, Date khkssj, Date khjssj, int khlx, String uuid) {

        //月
        statisticAssessLjlcKH(qyid, qycj, assessConfigBean,khkssj, khjssj, khlx,uuid);

    }

    private void statisticAssessLjlcKH(String qyid, String qycj, AssessConfigBean assessConfigBean, Date kssj,
                                       Date jssj, int khlx,String uuid) {
        String log_id = UUID.randomUUID().toString();
        AssessLogBean logBean = new AssessLogBean();
        logBean.setId(log_id);
        logBean.setKhdx(assessConfigBean.getKhqy());
        logBean.setKhlx(khlx);
        logBean.setKhqy(assessConfigBean.getKhqy());
        try {
        List<StatisticQdzBean> list = statisticAssessLJLCDao.getStatisticLjlcSvgByQYID(qyid, qycj, kssj, jssj);
        if (list!=null&&list.size()>0){
            assessCommonService.statisticAssessInsertDB(list,assessConfigBean,kssj,jssj,khlx, uuid);
        }
            logBean.setScjg(1);
        } catch (Exception e) {
            logBean.setScjg(0);
            logBean.setYcxx(e.getMessage());
        }
        assessCommonService.insertLog(logBean);
    }
}
