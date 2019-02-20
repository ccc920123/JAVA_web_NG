package com.scxd.service.impl;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysDtgl;
import com.scxd.dao.AllShearchDao;
import com.scxd.service.AllShearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllShearchServiceImpl implements AllShearchService {
    @Autowired
    AllShearchDao shearchDao;

    @Override
    public Response getAllData(String qyid,String fbwz, String bt,String tjcj ,int pageNo, int pageSize) throws Exception {
        if(qyid==null && qyid=="") return new Response().failure("Session为空");
        //查询处总条数
       int total=0;
        total= shearchDao.selectTotal(qyid,fbwz,bt,tjcj);
        List<SysDtgl> listData=null;
        if (total>0) {

            listData=shearchDao.getAllShearch(qyid, fbwz, bt,tjcj, pageNo, pageSize);
        }

        return new Response().success(listData,total);
    }

}
