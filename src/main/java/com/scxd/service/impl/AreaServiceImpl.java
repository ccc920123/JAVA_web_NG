package com.scxd.service.impl;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysAddArea;
import com.scxd.beans.database.SysArea;
import com.scxd.dao.AreaDao;
import com.scxd.dao.DtglDao;
import com.scxd.service.AreaService;
import com.scxd.toolkit.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 区域管理业务逻辑实现类
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areadao;
    @Autowired
    private HttpSession session;
    @Autowired
    private DtglDao dtglDao;

    //页面根据所选区域得到对应的下级区域名和上级区域名
    @Override
    public Response getArea(String qyid) throws Exception {
        if (UtilClass.strIsEmpty(qyid))return new Response().failure("区域ID为空");
        List<SysArea> areaInfo = areadao.selectAreaInfo(qyid);
        return (areaInfo != null && areaInfo.size() != 0)?new Response().success(areaInfo):new Response().failure();
    }

    @Override
    public Response getAreaDetails(String qyid) throws Exception {
        if (UtilClass.strIsEmpty(qyid))return new Response().failure("区域ID为空");
        SysArea areaInfo = areadao.selectAreaDetails(qyid);
        return (areaInfo != null)?new Response().success(areaInfo):new Response().failure();
    }

    //分页获取区域列表信息
    @Override
    public Response getAreas(Map map) throws Exception {
        //条件判空
        if (map.size() == 0 || map == null)return new Response().failure("查询条件为空");

        //获取当前用户所在的区域ID
        map.put("qyid", new UtilClass().getUserQyid(session));
        //获取信息总数，总数为0则直接返回
        int total = areadao.selectTotal(map);
        if (total == 0)return new Response().success(null,0);

        //信息不为空，分页查询数据
        map = UtilClass.getPaging(map);
        List<Map> result = areadao.selectAreaList(map);
        return (result.size() != 0 && result != null)?new Response().success(result,total):new Response().failure("获取信息失败");
    }

    //新增区域
    @Override
    public Response addAreas(Map map) throws Exception {
        if (map.size() == 0 || map == null)return new Response().failure("新增内容为空");
        int num = areadao.selectQueryqyid((String) map.get("qyid"));
        if (num > 0) return new Response().failure("该区域已存在");
        SysAddArea bean = new SysAddArea((String) map.get("qyid"), (String) map.get("qyname"), (String) map.get("sjqyid"),
                new Date(), (String) map.get("cjr"),
                (int) map.get("xzjb"), (String) map.get("qysp"), (String) map.get("qyjj"));
        int row = areadao.insertAreas(bean);
        return (row != 0)?new Response().success():new Response().failure("新增区域失败");
    }

    //删除区域列表信息
    @Override
    public Response deleteAreas(String qyid) throws Exception {
        int num = areadao.deleteAreas(qyid);
        if (num == 0)return new Response().failure("删除失败");
        return (num != 0)?new Response().success():new Response().failure("删除失败");
    }

    //修改区域信息
    @Override
    public Response alterAreasInfo(Map map) throws Exception {
        if (map.size() == 0 || map == null)return new Response().failure("输入信息为空");
            int result = areadao.updateAreaInfo(map);
            return (result != 0)?new Response().success():new Response().failure("修改信息失败");
    }

    //根据行政级别和父级区域ID获取区域名称
    @Override
    public Response getQynameByXzjbAndFjqyid(Map map) throws Exception {
        if (map == null || map.size() == 0)return new Response().failure("查询条件为空");
        List<Map> maps = areadao.selectQynameAndQyidByFjqyidAndXzjb(map);
        return (maps == null || maps.size() == 0)? new Response().failure("查询数据为空"):new Response().success(maps);
    }

    //根据qyid获取上级区域名称和对应的ID
    @Override
    public Response getSjQynameByQyid(String qyid) throws Exception {
        List<Map> maps = areadao.selectSjqyByQyid(qyid);
        return (maps.size() != 0 && maps != null)?new Response().success(maps):new Response().failure("获取数据为空");
    }

    //获取下级区域
    @Override
    public Response getXjqyById(String id) throws Exception {
        List<Map> maps = areadao.selectXjqyByQyid(id);
        return (maps.size() != 0 && maps != null)?new Response().success(maps):new Response().failure("获取数据为空");
    }

    //根据区域所传的条件ID等于数据库中的QYID或SJQYID获取区域名和区域ID
    @Override
    public Response getAreaName(String id) throws Exception {
        //将当前区域ID存入session
        session.setAttribute("AreaId",id);
        List<Map> result = areadao.selectAreaName(id);
        return (result.size() != 0 && result != null)?new Response().success(result):new Response().failure("获取数据为空");
    }

    //通过区域ID获取区域简介
    @Override
    public Response getAreaIntro(String qyid) throws Exception {
        String areaIntro = areadao.selectQyjjByqyid(qyid);
        dtglDao.updateTopStatus(new Date());
        return (UtilClass.strIsEmpty(areaIntro))?new Response().failure():new Response().success(areaIntro);
    }

}
