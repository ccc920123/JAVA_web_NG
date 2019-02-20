package com.scxd.dao;

import com.scxd.beans.database.SysDtgl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllShearchDao {

    /**
     * 搜搜数据
     * @param qyid
     * @param bt
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<SysDtgl> getAllShearch(@Param("qyid") String qyid,@Param("fbwz")String fbwz, @Param("bt")String bt, @Param("tjcj")String tjcj,@Param("pageNo")int pageNo, @Param("pageSize")int pageSize)throws Exception;



    /**
     * 根据区域id ,类型，标题，查询总条数
     * @param qyid
     * @return
     * @throws Exception
     */
    int selectTotal(@Param("qyid") String qyid, @Param("fbwz")String fbwz, @Param("bt")String bt,@Param("tjcj")String tjcj)throws Exception;
}
