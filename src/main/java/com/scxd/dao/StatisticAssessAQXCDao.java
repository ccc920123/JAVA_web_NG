package com.scxd.dao;

import com.scxd.beans.extendbeans.StatisticQdzBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 14:06 2018/11/27
 * @Modified By:
 */
@Repository
public interface StatisticAssessAQXCDao {
    List<StatisticQdzBean> getStatisticAqxcSvgByQYID(@Param("qyid") String qyid,@Param("qycj") String qycj,@Param("kssj") Date kssj,@Param("jssj") Date jssj);
}