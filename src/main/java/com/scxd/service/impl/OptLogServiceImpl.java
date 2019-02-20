package com.scxd.service.impl;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysOpLogBean;
import com.scxd.dao.OptLogServiceDao;
import com.scxd.service.OptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 15:37 2018/12/26
 * @Modified By:
 */
@Service
public class OptLogServiceImpl implements OptLogService {
    @Autowired
    OptLogServiceDao optLogServiceDao;

    @Override
    public void saveLog(SysOpLogBean optLog) {
        optLogServiceDao.saveLog(optLog);
    }

    @Override
    public Response getOperationList(Map map) {
        String kssj= (String) map.get("kssj");
        String jssj= (String) map.get("jssj");
        int pageNo= (int) map.get("pageNo");
        String content= (String) map.get("content");
        int total=optLogServiceDao.getOperationTotal(kssj,jssj,content);
        if (total>0){
            List<SysOpLogBean> list= optLogServiceDao.getOperationList(kssj,jssj,pageNo,content);
            return new Response().success(list,total);
        }else{
            return new Response().failure("未能查询到数据");
        }

    }

    @Override
    public Response queryOperationDetail(String id) {
        SysOpLogBean sysOpLogBean=   optLogServiceDao.queryOperationDetail(id);
        if (sysOpLogBean==null){
            return new Response().failure("未查询到信息");
        }
        return new Response().success(sysOpLogBean);
    }
}
