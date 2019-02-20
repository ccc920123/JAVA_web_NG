package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysLink;
import com.scxd.beans.extendbeans.Authority;
import com.scxd.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("link")
public class LinkCtrl {
    @Autowired
    private LinkService linkService;
    @Autowired
    private HttpSession session;

   @RequestMapping(value = "/getLink",method = RequestMethod.GET)
    public Response getLink(boolean isWeb,int pageNo, int pageSize)
    {
        //在session 中得到区域id
        String qyid="";
        if(isWeb)
        {
            qyid = (String) session.getAttribute("AreaId");
        }else {
            Authority authorityId = (Authority) session.getAttribute("authority");
            qyid = authorityId.getUser().getQyid();
        }
        try{
            return linkService.getLink(qyid,pageNo,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/updateLink",method = RequestMethod.POST)
    public Response updateLink(@RequestBody SysLink link)
    {
        try{
            //在session 中得到区域id
            Authority authorityId = (Authority)session.getAttribute("authority");
            link.setQyid(authorityId.getUser().getQyid());
            return linkService.updateLinkInfo(link);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value = "/deleteLink",method = RequestMethod.POST)
    public Response deleteLink(@RequestBody SysLink link)
    {
        try{
            return linkService.deleteLink(link.getId());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value = "/insertLink",method = RequestMethod.POST)
    public Response insertLink(@RequestBody SysLink link)
    {
        try{


            //在session 中得到区域id
            Authority authorityId = (Authority)session.getAttribute("authority");
            //添加新增项目主键
            String id = UUID.randomUUID().toString();

            link.setId(id);
            link.setQyid(authorityId.getUser().getQyid());
            return linkService.insertLink(link);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value = "/IdLink",method = RequestMethod.GET)
    public Response getIdLink(String id)
    {
        try{
            return linkService.getIdLink(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
