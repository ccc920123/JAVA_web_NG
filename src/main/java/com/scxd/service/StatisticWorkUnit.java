package com.scxd.service;

import com.scxd.beans.database.AssessConfigBean;

import java.util.Date;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 15:40 2018/11/28
 * @Modified By:
 */
public interface StatisticWorkUnit {
    void statisticWork(String qyid, String qycj, AssessConfigBean assessConfigBean, Date khkssj, Date khjssj, int khlx, String uuid);
}
