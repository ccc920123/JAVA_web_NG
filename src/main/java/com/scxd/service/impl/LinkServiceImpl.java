package com.scxd.service.impl;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysLink;
import com.scxd.dao.AreaDao;
import com.scxd.dao.LinkDao;
import com.scxd.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Override
    public Response getLink(String qyid,int pageNo, int pageSize) throws Exception {
        if(qyid==null && qyid=="") return new Response().failure("Session为空");
        int total = linkDao.selectTotal(qyid);
        List<SysLink> list=null;
        if (total>0)
        {
            list= linkDao.selectPageList(qyid,pageNo,pageSize);
        }
        return new Response().success(list,total);
    }

    @Override
    public Response getIdLink(String id) throws Exception {
        SysLink list=null;
        list= linkDao.selectIdList(id);
        return new Response().success(list);
    }

    @Override
    public Response updateLinkInfo(SysLink link) throws Exception {
        if (link == null)return new Response().failure("友情链接内容为空");
        int row;

        row=linkDao.updateLink(link);
        return  (row != 0 )?new Response().success():new Response().failure("修改友情链接失败");
    }

    @Override
    public Response deleteLink(String id) throws Exception {
         int row;
         row=linkDao.deleteLink(id);
          return  (row != 0 )?new Response().success():new Response().failure("删除友情链接失败");
    }

    @Override
    public Response insertLink(SysLink link) throws Exception {
        if (link == null)return new Response().failure("添加友情链接内容为空");
        int row=0;
        boolean isData=selectIsData(link);
        if(isData)
        {
           return  new Response().failure("已经存在该友情链接");
        }else {
             row = linkDao.insertLink(link);
        }
        return (row != 0 )?new Response().success():new Response().failure("添加友情链接失败");
    }

    /**
     * 查询链接数据是否存在（判断的是链接）
     * @param link
     * @return
     * @throws Exception
     */
    private boolean selectIsData(SysLink link) throws  Exception
    {
     List<SysLink> linkData=linkDao.selectIsData(link);

        return (linkData.size() != 0)?true:false;




    }
}
