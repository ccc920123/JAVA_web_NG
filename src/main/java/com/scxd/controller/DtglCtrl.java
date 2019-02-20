package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.beans.extendbeans.Authority;
import com.scxd.service.DtglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 动态管理控制层
 */
@RestController
@RequestMapping("dtgl")
public class DtglCtrl {

    @Autowired
    private DtglService dtglService;
    @Autowired
    private HttpSession session;

    //分页获取动态信息，标题名、首拼对应键名condition
    @RequestMapping(value = "/getDtgl", method = RequestMethod.POST)
    public Response getDtglInfo(@RequestBody Map map) {
        try {
            return dtglService.getDtglInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //新增动态信息,UUID做主键
    @RequestMapping(value = "/addDtgl", method = RequestMethod.POST)
    public Response addDtglInfo(@RequestBody Map map) {
        try {
            //新增动态信息只能在当前登录用户所在的区域
            Authority authority = (Authority) session.getAttribute("authority");
            String qyid = authority.getUser().getQyid();
            String cjr=authority.getUser().getAccount();
            String fbqyid = (String) map.get("fbqyid");
            map.put("cjr",cjr);
            if (qyid.equals(fbqyid)) {
                return dtglService.addDtglInfo(map);
            } else {
                return new Response().failure("当前用户不能在所选区域发布信息");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //修改动态信息，根据主键ID
    @RequestMapping(value = "/alterDtgl", method = RequestMethod.POST)
    public Response alterDtglInfo(@RequestBody Map map) {
        try {
            //取出角色层级查看是否有权限修改
            Authority authority = (Authority) session.getAttribute("authority");
            int level = authority.getRole().getRolelevel();
            //取出当前动态信息所在的层级,和审核状态
            int tszt = Integer.valueOf(String.valueOf(map.get("tszt")));
            if (tszt == 1 && level != 1) return new Response().failure("审核通过的信息不能再修改");
            map.remove("shzt");
            map.remove("tszt");
            int tjcj = Integer.valueOf(String.valueOf(map.get("tjcj")));
//            if (tjcj != 1){
//                tjcj += 1;
//            }
            if (level > tjcj) return new Response().failure("当前用户无权修改该区域的动态信息");
            map.remove("tjcj");
            return dtglService.alterDtglInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除动态信息，根据ID
    @RequestMapping(value = "/deleteDtgl", method = RequestMethod.POST)
    public Response deleteDtglInfo(@RequestBody String id) {
        try {
            Authority authority = (Authority) session.getAttribute("authority");
            String cjr=authority.getUser().getAccount();
            return dtglService.deleteDtglInfo(id,cjr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //分页查询需要审核的动态信息,区域名、首拼areacon/标题名、首拼btcon/审核状态shzt
    @RequestMapping(value = "/getDtAudit", method = RequestMethod.POST)
    public Response getDtAudit(@RequestBody Map map) {
        try {
            return dtglService.getDtAudit(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //审核动态信息，审核操作即通过或未通过，参数审核结果shzt/主键ID
    @RequestMapping(value = "/dtAudit", method = RequestMethod.POST)
    public Response dtAudit(@RequestBody Map map) {
        try {
            return dtglService.dtAudit(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().failure();
        }
    }

    //统计（新闻采纳/动态信息）排名,约束条件开始、结束时间/市级行政区域/区县级行政区域
    @RequestMapping(value = "/getStatistic", method = RequestMethod.POST)
    public Response statisticsDtInfo(@RequestBody Map map) {
        try {
            return dtglService.statisticsDtInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().failure();
        }
    }

    //根据ID获取动态信息
    @RequestMapping(value = "/getInfoById", method = RequestMethod.POST)
    public Response getDtInfoById(@RequestBody Map map) {
        try {
            return dtglService.getDtInfoById(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据ID获取动态预览
    @RequestMapping(value = "/getDtyl", method = RequestMethod.POST)
    public Response getDtyl(@RequestBody Map map) {
        try {
            return dtglService.getDtyl(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取新闻信息列表,参数发布位置FBWZ,分页pageNo
    @RequestMapping(value = "/getNews", method = RequestMethod.POST)
    public Response getNewsList(@RequestBody Map map) {
        try {
            return dtglService.getNewsList(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取新闻信息列表,参数发布位置FBWZ
    @RequestMapping(value = "/getNewsByFBWZ", method = RequestMethod.POST)
    public Response getNewsByFBWZ(@RequestBody Map map) {
        try {
            return dtglService.getNewsByFBWZ(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据动态ID获取新闻详情
    @RequestMapping(value = "/getNewsInfo", method = RequestMethod.POST)
    public Response getNewsInfoById(@RequestBody String id) {
        try {
            return dtglService.getNewsInfoById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据ID存储动态内容
    @RequestMapping(value = "/updateDtnr", method = RequestMethod.POST)
    public Response updateDtnr(@RequestBody Map map) {
        try {
            return dtglService.updateDtnr(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //存储动态预览内容
    @RequestMapping(value = "/savedtyl", method = RequestMethod.POST)
    public Response saveDtyl(@RequestBody Map map) {
        try {
            return dtglService.saveDtyl(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //动态信息提交到下一层级，根据动态ID
    @RequestMapping(value = "/submitInfo", method = RequestMethod.POST)
    public Response submitInfoById(@RequestBody Map map) {
        try {
            return dtglService.submitDtInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //首页获取新闻信息排名
    @RequestMapping(value = "/newsRank", method = RequestMethod.POST)
    public Response getNewsRank() {
        try {
            return dtglService.getNewsRank();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //首页获取新闻信息排名MORE
    @RequestMapping(value = "/newsRankMore", method = RequestMethod.POST)
    public Response getNewsRankMore() {
        try {
            return dtglService.getNewsRankMore();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取图片新闻
    @RequestMapping(value = "/pictureNews", method = RequestMethod.POST)
    public Response getPictureNews() {
        try {
            return dtglService.getPictureNews();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取图片新闻
    @RequestMapping(value = "/getpicBycontent", method = RequestMethod.POST)
    public Response getPictureNews(@RequestBody Map map) {
        String content= (String) map.get("content");
        List<String> imgs = new ArrayList<>();
        if (content != null && content != "") {
            Pattern p_img = Pattern.compile("(<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>)");
            Matcher m_img = p_img.matcher(content);
            String img = null;
            while (m_img.find()) {
                img = m_img.group(2); //m_img.group(1) 为获得整个img标签  m_img.group(2) 为获得src的值
                if (img != null) {
                    imgs.add(img);
                }
            }

            return new Response().success(imgs);
        } else {
            return new Response().failure("无图片信息");
        }
    }
}
