package com.scxd.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 15:51 2018/10/22
 * @Modified By:
 */
@Repository
public interface BaseDataDao {
    List<Map> getDrvStatisData(@Param("qyid") String dwCode,@Param("dwlb") int dwLb,@Param("qyname") String qyname);

    List<Map> getVehStatisData(@Param("qyid") String dwCode,@Param("dwlb") int dwLb,@Param("qyname") String qyname);

    List<Map> getRoadStatisData(@Param("qyid") String dwCode,@Param("dwlb") int dwLb,@Param("qyname") String qyname);

    List<Map> getTableLZStatisData(@Param("qyid")String qyid,@Param("dwlb") String  i);

    List<Map> getTableLYStatisData(@Param("qyid")String qyid,@Param("dwlb") String dw_lb);

    String getDwCode(@Param("qyid")String qyid);
}
