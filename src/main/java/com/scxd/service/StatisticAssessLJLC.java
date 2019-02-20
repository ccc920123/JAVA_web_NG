package com.scxd.service;

import com.scxd.beans.database.AssessConfigBean;

import java.util.Date;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 11:17 2018/11/27
 * @Modified By:
 */
public interface StatisticAssessLJLC {


    void statisticAssessLJLC(String qyid, String qycj, AssessConfigBean assessConfigBean, Date khkssj, Date khjssj, int khlx, String uuid);
}
