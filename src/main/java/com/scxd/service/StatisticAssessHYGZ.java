package com.scxd.service;

import com.scxd.beans.database.AssessConfigBean;

import java.util.Date;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 14:04 2018/11/27
 * @Modified By:
 */
public interface StatisticAssessHYGZ {
    void statisticAssessHYGZ(String qyid, String qycj, AssessConfigBean assessConfigBean, Date khkssj, Date khjssj, int khlx, String uuid);
}
