package com.scxd.dao;

import com.scxd.beans.database.SysLink;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 友情链接，数据库连接接口
 */
@Repository
public interface LinkDao {
    /**
     * 查询友情链接数据
     * @param qyid
     * @return
     * @throws Exception
     */
    List<SysLink> selectLinkInfo(String qyid)throws Exception;

    //新增链接
    int insertLink(SysLink link)throws Exception;

    //修改链接
    int updateLink(SysLink link)throws Exception;

    //删除链接根据ID
    int deleteLink(String id)throws Exception;
   //查询表中是否存在数据
    List<SysLink> selectIsData(SysLink link) throws Exception;

    /**
     * 根据区域id 查询总条数
     * @param qyid
     * @return
     * @throws Exception
     */
    int selectTotal(String qyid)throws Exception;

    /**
     *    分页查询数据
     * @param qyid
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<SysLink>selectPageList(@Param("qyid") String qyid,@Param("pageNo") int pageNo, @Param("pageSize") int pageSize)throws Exception;

    /**
     * 根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    SysLink selectIdList(String id)throws Exception;
}
