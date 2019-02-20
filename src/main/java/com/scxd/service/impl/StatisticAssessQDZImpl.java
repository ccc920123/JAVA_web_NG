package com.scxd.service.impl;

import com.scxd.beans.database.AssessConfigBean;
import com.scxd.beans.database.AssessLogBean;
import com.scxd.beans.extendbeans.StatisticQdzBean;
import com.scxd.dao.StatisticAssessQDZDao;
import com.scxd.service.StatisticAssessCommonService;
import com.scxd.service.StatisticAssessQDZ;
import com.scxd.toolkit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther:陈攀
 * @Description:劝导站工作
 * @Date:Created in 10:32 2018/11/26
 * @Modified By:
 */
@Service
public class StatisticAssessQDZImpl implements StatisticAssessQDZ {
    @Autowired
    StatisticAssessQDZDao statisticAssessQDZDao;
    @Autowired
    StatisticAssessCommonService assessCommonService;

    /**
     * 根据区域id 以及层级统计当前区域下的直属下级的劝导站情况
     * @param qyid
     * @param qycj
     * @param assessConfigBean
     * @param khkssj
     * @param khjssj
     * @param khlx
     * @param uuid
     */
    @Override

    public void statisticAssessQDZ(String qyid, String qycj, AssessConfigBean assessConfigBean, Date khkssj, Date khjssj, int khlx, String uuid) {

        //月
        statisticAssessQdzKH(qyid, qycj, assessConfigBean,khkssj,khjssj,khlx ,uuid);

    }

    /**
     * @param qyid
     * @param qycj
     * @param assessConfigBean
     * @param kssj
     * @param jssj
     * @param khlx
     */
    private void statisticAssessQdzKH(String qyid, String qycj, AssessConfigBean assessConfigBean, Date kssj, Date jssj, int khlx,String uuid) {
        String log_id = UUID.randomUUID().toString();
        AssessLogBean logBean = new AssessLogBean();
        logBean.setId(log_id);
        logBean.setKhdx(assessConfigBean.getKhqy());
        logBean.setKhlx(khlx);
        logBean.setKhqy(assessConfigBean.getKhqy());
        try {
            List<StatisticQdzBean> list = statisticAssessQDZDao.getStatisticQdSvgByQYID(qyid, qycj, kssj, jssj);
            if (list != null && list.size() > 0) {
                assessCommonService.statisticAssessInsertDB(list, assessConfigBean, kssj, jssj, khlx,uuid);
            }
            logBean.setScjg(1);
        } catch (Exception e) {
            logBean.setScjg(0);
            logBean.setYcxx(e.getMessage());
        }
        assessCommonService.insertLog(logBean);

    }
}
