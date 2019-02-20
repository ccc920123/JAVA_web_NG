package com.scxd.dao;

import com.scxd.beans.database.SysOpLogBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 16:28 2018/12/26
 * @Modified By:
 */
@Repository
public interface OptLogServiceDao {

    public void saveLog(SysOpLogBean optLog);

    int getOperationTotal(@Param("kssj") String kssj,@Param("jssj") String jssj, @Param("content")String content);

    List<SysOpLogBean> getOperationList(@Param("kssj")String kssj, @Param("jssj")String jssj, @Param("pageNo") int pageNo, @Param("content")String content);

    SysOpLogBean queryOperationDetail(@Param("id")String id);
}
