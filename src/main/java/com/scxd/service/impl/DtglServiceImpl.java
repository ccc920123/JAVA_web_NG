package com.scxd.service.impl;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysDtgl;
import com.scxd.beans.database.SysDtyl;
import com.scxd.beans.extendbeans.Authority;
import com.scxd.dao.AreaDao;
import com.scxd.dao.DtglDao;
import com.scxd.service.DtglService;
import com.scxd.toolkit.UtilClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 动态管理业务逻辑实现类
 */
@Service
public class DtglServiceImpl implements DtglService {

    @Autowired
    private DtglDao dtglDao;
    @Autowired
    private HttpSession session;
    @Autowired
    private AreaDao areaDao;

    //分页获取动态信息，标题名、首拼对应键名condition,
    //动态信息获取当前区域和下级区域，审核状态显示的是fbqy所在层级的审核状态
    @Override
    public Response getDtglInfo(Map map) throws Exception {
        if (map == null || map.size() == 0) return new Response().failure("查询条件为空");
        //获取当前用户所在的区域ID
        map.put("qyid", new UtilClass().getUserQyid(session));
        //查询动态信息条数
        int total = dtglDao.selectTotalDtglInfo(map);
        if (total == 0) return new Response().success(null, total);
        //分页查询
        map = UtilClass.getPaging(map);
        int tjcj = 0;
        int level = new UtilClass().getUserlevel(session);
        if (level != 1) {//用户所在层级不是是省级层级
            tjcj = level - 1;
        }
        map.put("tjcj", tjcj);
        List<Map> result = dtglDao.selectDtglInfo(map);
        if (result != null){
            List<Map> tszt = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                map.put("id", result.get(i).get("ID"));
                map.put("sjcj", tjcj);
                tszt = dtglDao.selectDtAuditTszt(map);
                if (null != tszt && tszt.size() > 0) {
                    result.get(i).put("TSZT", tszt.get(0).get("TSZT"));
                    result.get(i).put("BZ", tszt.get(0).get("BZ"));
                } else {
                    result.get(i).put("TSZT", null);
                    result.get(i).put("BZ", null);
                }
                if (level == 1){
                    result.get(i).put("TSZT", null);
                }
            }
        }
        return (result.size() != 0 && result != null) ? new Response().success(result, total) : new Response().failure("获取数据失败");
    }

    //新增动态信息,UUID做主键
    @Override
    public Response addDtglInfo(Map map) throws Exception {
        if (map == null || map.size() == 0) return new Response().failure("新增内容为空");
        //生成主键ID
        String dtid = UUID.randomUUID().toString();
        map.put("id", dtid);
        //发布时间
        map.put("fbsj", new Date());
        String topdate = (String) map.get("topdate");
        if (topdate != null && topdate != "") {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(topdate);
            map.put("topdate", date);
        }
        int row = dtglDao.insertDtglInfo(map);
        if (row != 0) {
            //新增的动态内容它的默认提交层级为当前所在的层级
            int level = new UtilClass().getUserlevel(session);
            Map dtsh = new HashMap();
            dtsh.put("id", UUID.randomUUID().toString());
            dtsh.put("dtid", dtid);
            dtsh.put("shzt", 0);
            dtsh.put("tjcj", level);
            row = dtglDao.insertDtsh(dtsh);
            //成功之后需要返回动态信息表中的ID，为动态内容存储提供条件
            return (row != 0) ? new Response().success(dtid) : new Response().failure("新增动态信息失败");
        } else return new Response().failure("新增动态信息失败");

    }

    //修改动态信息，根据主键ID
    @Override
    public Response alterDtglInfo(Map map) throws Exception {
        if (map == null || map.size() == 0) return new Response().failure("修改内容为空");
        map = judgeTitle(map);
        String topdate = (String) map.get("topdate");
        if (topdate != null && topdate != "") {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(topdate);
            map.put("topdate", date);
        }
        SysDtgl bean = new SysDtgl();
        bean.setId(map.get("id") != null ? (String) map.get("id") : "");
        bean.setBt(map.get("bt") != null ? (String) map.get("bt") : "");
        bean.setBtsp(map.get("btsp") != null ? (String) map.get("btsp") : "");
        bean.setDtlx(map.get("dtlx") != null ? (String) map.get("dtlx") : "");
        bean.setDtnr(map.get("dtnr") != null ? (String) map.get("dtnr") : "");
        bean.setFbqyid(map.get("fbqyid") != null ? (String) map.get("fbqyid") : "");
        bean.setFbsj(map.get("fbsj") != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) map.get("fbsj")) : new Date());
        bean.setFbwz(map.get("fbwz") != null ? (String) map.get("fbwz") : "");
        bean.setFirstpic(map.get("firstpic") != null ? (String) map.get("firstpic") : "");
        bean.setIstop(map.get("istop") != null ? Integer.valueOf((String) map.get("istop")) : 0);
        bean.setLyqyid(map.get("lyqyid") != null ? (String) map.get("lyqyid") : "");
        bean.setNgr(map.get("ngr") != null ? (String) map.get("ngr") : "");
        bean.setShzt(map.get("shzt") != null ? Long.valueOf((String) map.get("shzt")) : 0);
        bean.setTjcj(map.get("tjcj") != null ? Integer.valueOf((String) map.get("tjcj")) : 0);
        if (map.get("topdate") != null || map.get("topdate").equals("")){
            bean.setTopdate(new Date());
        } else {
            bean.setTopdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) map.get("topdate")));
        }
        bean.setTopsort(map.get("topsort") != null ? (String) map.get("topsort") : "");
        int row = dtglDao.updateDtglInfo(bean);
        return (row != 0) ? new Response().success() : new Response().failure("修改信息失败");
    }

    //删除动态信息，根据ID
    @Override
    public Response deleteDtglInfo(String id, String cjr) throws Exception {
        int row = dtglDao.deleteDtInfo(id,cjr);
        //删除动态信息则相应的要删除它的各级审核状态信息
        if (row == 0) return new Response().failure("删除信息失败");
        row = dtglDao.deleteDtshByDtid(id);
        return (row != 0) ? new Response().success() : new Response().failure("删除信息失败");
    }

    //分页查询需要审核的动态信息,区域名、首拼areacon/标题名、首拼btcon/审核状态shzt
    //该信息包含本区域和提交到本区域的信息以及被上级退回的信息
    @Override
    public Response getDtAudit(Map map) throws Exception {
        if (map == null || map.size() == 0) return new Response().failure("查询条件为空");
        //获取角色层级和用户所在区域
        int level = new UtilClass().getUserlevel(session);
        map.put("level", level);
        map.put("qyid", new UtilClass().getUserQyid(session));
        //获取上级的层级
        if (level != 1) {//也就是当前层级不是省级层级
            map.put("sjcj", level - 1);
        } else {
            map.put("sjcj", null);
        }
        int total = dtglDao.selectTotalAudit(map);
        if (total == 0) return new Response().success(null, total);
        //分页查询
        map = UtilClass.getPaging(map);
        List<Map> result = dtglDao.selectDtAudit(map);
        if (null != result && null != map.get("sjcj")){
            List<Map> tszt = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                map.put("id", result.get(i).get("ID"));
                tszt = dtglDao.selectDtAuditTszt(map);
                if (null != tszt && tszt.size() > 0) {
                    result.get(i).put("TSZT", tszt.get(0).get("TSZT"));
                    result.get(i).put("BZ", tszt.get(0).get("BZ"));
                } else {
                    result.get(i).put("TSZT", null);
                    result.get(i).put("BZ", null);
                }
            }
        }
        return (result.size() != 0 && result != null) ? new Response().success(result, total) : new Response().failure("获取数据失败");
    }

    //审核动态信息，审核操作即通过或未通过，参数审核结果shzt/动态dtid
    @Override
    public Response dtAudit(Map map) throws Exception {
        if (map == null || map.size() == 0) return new Response().failure("条件为空");
        int tjcj = new UtilClass().getUserlevel(session);//获取当前用户所在的层级
        map.put("tjcj", tjcj);
        map.put("shr", new UtilClass().getUsername(session));
        map.put("shsj", new Date());
        int row = dtglDao.updateAudit(map);
        return (row != 0) ? new Response().success() : new Response().failure("审核失败");
    }

    //提交动态信息,动态ID
    @Override
    public Response submitDtInfo(Map map) throws Exception {
        if (map == null || map.size() == 0) return new Response().failure("条件为空");
        //判断是否是第一次提交
        int tjcj = new UtilClass().getUserlevel(session);
        String sftj = String.valueOf(map.get("sftj"));
        if (tjcj != 1) {//用户所在层级不是是省级层级
            tjcj = tjcj - 1;
        } else {
            if (sftj.equals("1")) {
                return new Response().failure("当前层级不能继续提交");
            }
        }
        map.put("tjcj", tjcj);
        int xzjb = new UtilClass().getUserXzjb(session);
        int total = dtglDao.selectDtshByDtidAndTjcj(map);
        int level = new UtilClass().getUserlevel(session);
        if (sftj.equals("0") && xzjb != 4 && level != 1){
            if (total > 0){
                dtglDao.deleteDtshByDtidTjcj(map);
            }
            return (true) ? new Response().success() : new Response().failure();
        } else {
            map.remove("sftj");
            boolean isSuccess = true;
            if (total == 0) {//第一次提交
                isSuccess = insertDtsh(map);
            } else {//之前已经提交过
                if (level != 1) {
                    isSuccess = updateDtshzt(map);
                }
            }
            if (isSuccess == false) return new Response().failure("提交失败");
            //新增提交记录
            map.remove("shzt");
            map.remove("lyqyid");
            isSuccess = insertDtglTjjl(map);
            return (isSuccess) ? new Response().success("提交成功") : new Response().failure("提交失败");
        }
    }

    //获取新闻信息列表,参数发布位置FBWZ,在首页显示默认是显示8条
    @Override
    public Response getNewsByFBWZ(Map map) throws Exception {
        //从session中获取当前选中的区域ID
        System.out.println(map.get("fbwz"));
        String qyid = (String) session.getAttribute("AreaId");
        map.put("qyid", qyid);
        //根据区域ID判断所属层级,前端显示新闻信息只显示省、市、区县三级
        map.put("tjcj", getTheirLevel(qyid));
        List<Map> news = dtglDao.selectNewsListByFBWZ(map);
        return (news.size() != 0 && news != null) ? new Response().success(news) : new Response().failure("获取数据为空");
    }

    //首页获取新闻信息排名
    @Override
    public Response getNewsRank() throws Exception {
        //取出当前的区域ID
        String currentAreaId = (String) session.getAttribute("AreaId");
        Map map = new HashMap();
        map.put("sjqyid", currentAreaId);
        map.put("tjcj", getTheirLevel(currentAreaId));

        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date nowDay = new Date();
        String time = format.format(nowDay);
        map.put("kssj", time + "-01-01");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        map.put("jssj", formatter.format(nowDay));
        List<Map> news = dtglDao.selectNewsRank(map);
        return (news.size() != 0 && news != null) ? new Response().success(news) : new Response().failure("获取数据为空");
    }

    //首页获取新闻信息排名More
    @Override
    public Response getNewsRankMore() throws Exception {
        //取出当前的区域ID
        String currentAreaId = (String) session.getAttribute("AreaId");
        Map map = new HashMap();
        map.put("sjqyid", currentAreaId);
        map.put("tjcj", getTheirLevel(currentAreaId));

        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date nowDay = new Date();
        String time = format.format(nowDay);
        map.put("kssj", time + "-01-01");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        map.put("jssj", formatter.format(nowDay));
        List<Map> news = dtglDao.selectNewsRankMore(map);
        return (news.size() != 0 && news != null) ? new Response().success(news) : new Response().failure("获取数据为空");
    }

    //获取图片新闻
    @Override
    public Response getPictureNews() throws Exception {
        Map map = new HashMap();
        //从session中获取当前选中的区域ID
        String qyid = (String) session.getAttribute("AreaId");
        map.put("qyid", qyid);
        //根据区域ID判断所属层级,前端显示新闻信息只显示省、市、区县三级
        map.put("tjcj", getTheirLevel(qyid));
        List<Map> news = dtglDao.selectPictureNews(map);
        if (news.size() == 0 || news == null) return new Response().failure("获取数据为空");
        Map result = separationDynamicAndNews(news);
        return new Response().success(result);
    }

    //统计（新闻采纳/动态信息）排名,约束条件开始、结束时间/市级行政区域sjqy
    // 区县级行政区域qxqy
    @Override
    public Response statisticsDtInfo(Map map) throws Exception {
        if (map == null || map.size() == 0) return new Response().failure("条件为空");
        //获取排名的上级区域IDsjqyid
        if (map.containsKey("qxqy") && !map.get("qxqy").equals("")) {//条件中有区县区域
            map.put("sjqyid", map.get("qxqy"));
            map.put("tjcj", 3);
        } else if (map.containsKey("sjqy") && !map.get("sjqy").equals("")) {//没有区县区域，有市级区域
            map.put("sjqyid", map.get("sjqy"));
            map.put("tjcj", 2);
        } else {//市级和县级区域都为空,就设上级区域为四川省
            map.put("sjqyid", "51");
            map.put("tjcj", 1);
        }
//        Authority authority = (Authority) session.getAttribute("authority");
//        if (authority!=null){
//            map.put( "sjqyid",  authority.getUser().getQyid());
//            map.put( "tjcj",  authority.getUser().getXzjb());
//        }

        List<Map> result = dtglDao.selectStatisticsDtInfo(map);
        return (result != null && result.size() != 0) ? new Response().success(result) : new Response().failure("获取数据失败");
    }

    //根据ID获取动态信息
    @Override
    public Response getDtInfoById(Map map) throws Exception {
        //要获取当前所在的层级审核状态,取出登录用户所在的层级
        int level = new UtilClass().getUserlevel(session);
        int tscj = 1;
        if (level != 1){
            tscj = level - 1;
        }
        map.put("tjcj", level);
        map.put("tscj", tscj);
        SysDtgl result = dtglDao.selectDtInfoById(map);
        if (result != null){
            map.put("sjcj", tscj);
            map.put("id", map.get("dtid"));
            List<Map> tszt = dtglDao.selectDtAuditTszt(map);
            if (null != tszt && tszt.size() > 0) {
                result.setTszt(Long.valueOf(String.valueOf(tszt.get(0).get("TSZT"))));
                result.setBz((String) tszt.get(0).get("BZ"));
            } else {
                result.setTszt(null);
                result.setBz(null);
            }
        }
        return (result != null) ? new Response().success(result) : new Response().failure("获取数据失败");
    }

    @Override
    public Response getDtyl(Map map) throws Exception {
        SysDtyl result = dtglDao.getDtyl(map);
        return (result != null) ? new Response().success(result) : new Response().failure("获取数据失败");
    }

    //根据ID上传动态内容
    @Override
    public Response updateDtnr(Map map) throws Exception {
        int row = dtglDao.updateDtnrById(map);
        return (row != 0) ? new Response().success() : new Response().failure("动态内容上传失败");
    }

    //动态预览
    @Override
    public Response saveDtyl(Map map) throws Exception {
        int number = dtglDao.deleteDtyl(map);
        //生成主键ID
        String dtid = UUID.randomUUID().toString();
        map.put("id", dtid);
        int row = dtglDao.saveDtyl(map);
        //成功之后需要返回动态信息表中的ID，为动态内容存储提供条件
        return (row != 0) ? new Response().success(dtid) : new Response().failure("");
    }

    //获取新闻信息列表,参数发布位置FBWZ,分页pageNo//隐藏的限制条件，根据当前的所选区域
    @Override
    public Response getNewsList(Map map) throws Exception {
        //从session中获取当前选中的区域ID
        String qyid = (String) session.getAttribute("AreaId");
        map.put("qyid", qyid);
        //根据区域ID判断所属层级,前端显示新闻信息只显示省、市、区县三级
        map.put("tjcj", getTheirLevel(qyid));
        int total = dtglDao.selectNewsTotal(map);
        if (total == 0) return new Response().success(null, total);
        //分页查询
        map = UtilClass.getPagingfifteen(map);
        List<Map> result = dtglDao.selectNewsList(map);
        return (result.size() != 0 && result != null) ? new Response().success(result, total) : new Response().failure("获取数据失败");
    }

    //根据动态ID获取新闻详情
    @Override
    public Response getNewsInfoById(String id) throws Exception {
        SysDtgl result = dtglDao.selectNewsInfo(id);
        return (result != null) ? new Response().success(result) : new Response().failure("获取数据失败");
    }

    //新增动态审核状态
    private boolean insertDtsh(Map map) throws Exception {
        map.put("id", UUID.randomUUID().toString());
        map.put("shzt", 0);
        //提交的时候需要备注来源区域，也就是当前区域
        String qyid = new UtilClass().getUserQyid(session);
        map.put("lyqyid", qyid);
        int row = dtglDao.insertDtsh(map);
        return (row != 0) ? true : false;
    }

    //更新动态审核状态,也就是下一级向上一级提交的时候审核状态初始化为零
    private boolean updateDtshzt(Map map) throws Exception {
        map.put("shzt", 0);
        int row = dtglDao.updateAudit(map);
        return (row != 0) ? true : false;
    }

    //新增提交记录
    private boolean insertDtglTjjl(Map map) throws Exception {
        map.put("id", UUID.randomUUID().toString());
        map.put("tjsj", new Date());
        map.put("tjr", new UtilClass().getUsername(session));
        int row = dtglDao.insertDtglTjjl(map);
        return (row != 0) ? true : false;
    }

    //根据区域ID判断所属层级,前端显示新闻信息只显示省、市、区县三级
    private int getTheirLevel(String qyid) {
        int submitLevel;
        switch (qyid.length()) {
            case 2:
                submitLevel = 1;
                break;
            case 4:
                submitLevel = 2;
                break;
            case 6:
                submitLevel = 3;
                break;
            default:
                submitLevel = 0;
                break;
        }
        return submitLevel;
    }

    //循环取出动态内容
    private Map separationDynamicAndNews(List<Map> news) {
        //如果新闻信息大于五则ings数组长度为五，否则数组长度为news.size
        int length = (news.size() > 5) ? 5 : news.size();
        String[] imgs = new String[length];
        for (int i = 0; i < news.size(); i++) {
            if (i < 5) {//只取前五张图片
                String img = (String) news.get(i).get("FIRSTPIC");
                if (img != null) {
                    imgs[i] = img;
                }
            }
        }
        Map map = new HashMap();
        map.put("news", news);
        map.put("imgs", imgs);
        return map;
    }

    //取出标题进行前面字段的判定
    private Map judgeTitle(Map map) throws Exception {
        String submit = (String) map.get("submit");//取出提交判断
        map.remove("submit");//删除提交判断，干干净净的map去做更新循环才不会循环出多余的东西
        if (submit.equals("1")) {//需要向上提交，则标题要该变
            int level = new UtilClass().getUserlevel(session);
            if (level != 1) {//不等于省级，可以向上提交
                //根据当前层级区域ID获取当前层级区域名
                String areaId = new UtilClass().getUserQyid(session);
                List<String> areaNames = areaDao.selectAreaNameByAreaId(areaId);
                String title = (String) map.get("bt");//取出标题
                String areaName = areaNames.get(0);//取出本层级区域名

                String titleStr = title;
                if(title.indexOf(":") !=-1 ) {
                    titleStr = title.substring(title.indexOf(":"));
                    map.put("bt", areaName + ":" + titleStr.substring(1));
                } else {
                    map.put("bt", areaName + ":" + titleStr);
                }
            }
        }
        return map;
    }

    // 将字Clob转成String类型
    public String ClobToString(Clob sc) throws SQLException, IOException {
        String reString = "";
        Reader is = sc.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }
}
