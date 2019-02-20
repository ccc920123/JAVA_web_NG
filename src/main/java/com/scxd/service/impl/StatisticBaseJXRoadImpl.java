package com.scxd.service.impl;

import com.scxd.beans.database.AssessConfigBean;
import com.scxd.beans.database.AssessLogBean;
import com.scxd.beans.extendbeans.StatisticQdzBean;
import com.scxd.dao.StatisticBaseJXRoadDao;
import com.scxd.service.StatisticAssessCommonService;
import com.scxd.service.StatisticBaseJXRoad;
import com.scxd.toolkit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 15:37 2018/11/28
 * @Modified By:
 */
@Service
public class StatisticBaseJXRoadImpl implements StatisticBaseJXRoad
{
    @Autowired
    StatisticBaseJXRoadDao statisticBase;
    @Autowired
    StatisticAssessCommonService assessCommonService;
    @Override
    public void statisticBase(String qyid, String qycj, AssessConfigBean assessConfigBean, Date khkssj, Date khjssj, int khlx, String uuid) {
        statisticBaseKH(qyid, qycj, assessConfigBean, khkssj,
                khjssj,khlx,uuid);
    }

    /**
     *  @param qyid 区域ID
     * @param qycj 区域层级
     * @param assessConfigBean
     * @param kssj
     * @param jssj
     * @param khlx
     */
    private void statisticBaseKH(String qyid, String qycj, AssessConfigBean assessConfigBean, Date kssj, Date jssj,
                                 int khlx, String uuid) {

        String log_id = UUID.randomUUID().toString();
        AssessLogBean logBean = new AssessLogBean();
        logBean.setId(log_id);
        logBean.setKhdx(assessConfigBean.getKhqy());
        logBean.setKhlx(khlx);
        logBean.setKhqy(assessConfigBean.getKhqy());
        try {
            List<StatisticQdzBean> list = statisticBase.getStatisticBase(qyid, qycj, kssj, jssj);
            if (list != null && list.size() > 0) {
                assessCommonService.statisticAssessInsertDB_ZL(list, assessConfigBean, kssj, jssj, khlx, uuid);
            }
            logBean.setScjg(1);
        } catch (Exception e) {
            logBean.setScjg(0);
            logBean.setYcxx(e.getMessage());
        }
        assessCommonService.insertLog(logBean);
    }
}
