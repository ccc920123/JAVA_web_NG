package com.scxd.toolkit;

import com.scxd.beans.extendbeans.Authority;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * 常用的工具类
 */
public class UtilClass {

    /**
     * 判断字符串是否为空，为空返回true
     */
    public static boolean strIsEmpty(String str)throws Exception{
        return (str == null || str.equals("") || str.length() == 0)? true:false;
    }

    /**
     * 分页查询map组装每页十条
     */
    public static Map getPaging(Map map)throws Exception{
        int page = (int)map.get("pageNo");
        int start = (page - 1) * 10;
        int end = page * 10;
        map.put("start",start);
        map.put("end",end);
        return map;
    }

    /**
     * 分页组装每页15条，用于前端显示
     */
    public static Map getPagingfifteen(Map map)throws Exception{
        int page = (int)map.get("pageNo");
        int start = (page - 1) * 15;
        int end = page * 15;
        map.put("start",start);
        map.put("end",end);
        return map;
    }

    /**
     * 获取当前用户区域ID
     */
    public String getUserQyid(HttpSession session){
        Authority authority = (Authority)session.getAttribute("authority");
        return  authority.getUser().getQyid();
    }

    /**
     * 获取当前用户所在的层级
     */
    public int getUserlevel(HttpSession session){
        Authority authority = (Authority)session.getAttribute("authority");
        int level = authority.getRole().getRolelevel();
        return level;
    }

    /**
     * 获取当前用户所在的行政级别
     */
    public int getUserXzjb(HttpSession session){
        Authority authority = (Authority)session.getAttribute("authority");
        int xzjb = authority.getUser().getXzjb();
        return xzjb;
    }

    /**
     * 获取当前用户名
     */
    public String getUsername(HttpSession session){
        Authority authority = (Authority)session.getAttribute("authority");
        String relname = authority.getUser().getRelname();
        return relname;
    }

    /**
     * 获取单位层级
     * @param session
     * @return
     */
    public String getDwLBBySession(HttpSession session) {
        Authority authority = (Authority)session.getAttribute("authority");
        int level = authority.getRole().getRolelevel();
        String dw_lb="01";
        if (level==1){
            dw_lb="01";
        }else  if (level==2){
            dw_lb="05";
        }else if (level==3){
            dw_lb="11";
        }else if (level==4){
            dw_lb="15";
        }
        return dw_lb;
    }
}
