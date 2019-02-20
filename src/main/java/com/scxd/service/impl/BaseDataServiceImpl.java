package com.scxd.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.scxd.beans.Response;
import com.scxd.beans.database.SysArea;
import com.scxd.dao.AreaDao;
import com.scxd.dao.BaseDataDao;
import com.scxd.service.AreaService;
import com.scxd.service.BaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 11:37 2018/10/22
 * @Modified By:
 */
@Service
public class BaseDataServiceImpl implements BaseDataService {
    @Autowired
    BaseDataDao baseDataDao;
    @Autowired
    private AreaDao areadao;

    @Override
    public Response getDrvStatisData(String qyid) {
        if (qyid == null || "".equals(qyid)) {
            return new Response().failure("未选择地区");
        }
        SysArea sysArea = areadao.getSysAreaBy(qyid);
        int xzjb = 0;
        if (sysArea != null) {
            xzjb = sysArea.getXzjb();
        } else {
            return new Response().failure("未找到当前行政区域信息");
        }

        List<Map> list = baseDataDao.getDrvStatisData(qyid, xzjb+1,sysArea.getQyname());
        return new Response().success(list);
    }

    @Override
    public Response getVehStatisData(String qyid) {
        if (qyid == null || "".equals(qyid)) {
            return new Response().failure("未选择地区");
        }
        SysArea sysArea = areadao.getSysAreaBy(qyid);
        int xzjb = 0;
        if (sysArea != null) {
            xzjb = sysArea.getXzjb();
        } else {
            return new Response().failure("未找到当前行政区域信息");
        }
        List<Map> list = baseDataDao.getVehStatisData(qyid, xzjb+1,sysArea.getQyname());
        return new Response().success(list);

    }

    @Override
    public Response getRoadStatisData(String qyid) {
        if (qyid == null || "".equals(qyid)) {
            return new Response().failure("未选择地区");
        }
        SysArea sysArea = areadao.getSysAreaBy(qyid);
        int xzjb = 0;
        if (sysArea != null) {
            xzjb = sysArea.getXzjb();
        } else {
            return new Response().failure("未找到当前行政区域信息");
        }
        List<Map> list = baseDataDao.getRoadStatisData(qyid, xzjb+1,sysArea.getQyname());
        return new Response().success(list);
    }

    @Override
    public Response getTableLZStatisData(String qyid) {
        if (qyid == null || "".equals(qyid)) {
            return new Response().failure("未选择地区");
        }
        SysArea sysArea = areadao.getSysAreaBy(qyid);
        int xzjb = 0;
        if (sysArea != null) {
            xzjb = sysArea.getXzjb();
        } else {
            return new Response().failure("未找到当前行政区域信息");
        }
        String dw_lb="05";
        switch (xzjb){
            case 1://省
                dw_lb="01";
                break;
            case 2://市
                dw_lb="05";
                break;
            case 3://区县\
                dw_lb="11";
                break;
            case 4://乡镇
                dw_lb="15";
                break;
        }
     String dw_code=   baseDataDao.getDwCode(qyid);
        if (StringUtils.isEmpty(dw_code)){
            return new Response().failure();
        }
        List<Map> list = baseDataDao.getTableLZStatisData(dw_code, dw_lb);
        return new Response().success(list);
    }
    @Override
    public Response getTableLYStatisData(String qyid) {
        if (qyid == null || "".equals(qyid)) {
            return new Response().failure("未选择地区");
        }
        SysArea sysArea = areadao.getSysAreaBy(qyid);
        int xzjb = 0;
        if (sysArea != null) {
            xzjb = sysArea.getXzjb();
        } else {
            return new Response().failure("未找到当前行政区域信息");
        }
        String dw_lb="05";
        switch (xzjb){
            case 1://省
                dw_lb="01";
                break;
            case 2://市
                dw_lb="05";
                break;
            case 3://区县\
                dw_lb="11";
                break;
            case 4://乡镇
                dw_lb="15";
                break;
        }
        String dw_code=   baseDataDao.getDwCode(qyid);
        if (StringUtils.isEmpty(dw_code)){
            return new Response().failure();
        }
        List<Map> list = baseDataDao.getTableLYStatisData(dw_code, dw_lb);
        return new Response().success(list);
    }
}
