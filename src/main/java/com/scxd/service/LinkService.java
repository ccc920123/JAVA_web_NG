package com.scxd.service;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysLink;

import java.util.Map;

/**
 * 获取友情链接业务逻辑接口
 */
public interface LinkService {

    Response getLink(String qyid,int pageNo, int pageSize) throws  Exception;

    Response getIdLink(String id)throws Exception;


    //修改友情链接
    Response updateLinkInfo(SysLink link)throws Exception;

    /**
     * 删除友情链接
     * @param id
     * @return
     * @throws Exception
     */
    Response deleteLink(String id) throws  Exception;

    /**
     * 添加友情链接
     * @param link
     * @return
     * @throws Exception
     */
    Response insertLink(SysLink link) throws  Exception;


}
