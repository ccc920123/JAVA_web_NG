package com.scxd.service;

import com.scxd.beans.database.AssessConfigBean;

import java.util.Date;

/**
 * @Auther:陈攀
 * @Description: 安全宣传
 * @Date:Created in 14:02 2018/11/27
 * @Modified By:
 */
public interface StatisticAssessAQXC {
    void statisticAssessAQXC(String qyid, String qycj, AssessConfigBean assessConfigBean, Date khkssj, Date khjssj, int khlx, String uuid);
}
