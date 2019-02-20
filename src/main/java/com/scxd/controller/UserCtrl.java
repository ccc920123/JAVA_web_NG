package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.beans.extendbeans.Authority;
import com.scxd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 *  用户操作控制器
 */
@RestController
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;

    //用户登录/map 中的数据是账号account和密码password
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Response userLogin(@RequestBody Map map){
        try{
          return userService.userLogin(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //进入首页时，获取用户名，显示在页面头部
    @RequestMapping(value="/getUsername",method = RequestMethod.POST)
    public Response getUserName(){
     try{
         Authority authority = (Authority) session.getAttribute("authority");
         return new Response().success(authority.getUser().getRelname());
     }catch (Exception e){
         e.printStackTrace();
         return null;
     }
    }

    //获取权限信息
    @RequestMapping(value = "/getAuth",method = RequestMethod.POST)
    public Response getAuthority(){
        try{
            return new Response().success(session.getAttribute("authority"));
        }catch (Exception e){
            e.printStackTrace();
            return new Response().failure();
        }
    }

    //获取当前用户所在的区域ID
    @RequestMapping(value = "/userAreaId",method = RequestMethod.POST)
    public Response getUserAreaId(){
        try{
            Authority authority = (Authority)session.getAttribute("authority");
            String userAreaId = authority.getUser().getQyid();
            return new Response().success(userAreaId);
        }catch (Exception e){
            e.printStackTrace();
            return new Response().failure();
        }
    }

    //用户密码修改
    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public Response changePassword(@RequestBody Map map){
        try{
            return userService.changePassword((String)map.get("oldPwd"),(String)map.get("newPwd"));
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //新增用户
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Response addUser(@RequestBody Map user){
        try{
            //判断身份证号码是否存在
            String IDcard = (String)user.get("sfzhm");
            if (IDcard.length() != 18)return new Response().failure("请输入十八位身份证号码");
            boolean isExist =  userService.chechSfzhm(IDcard);
            if (isExist)return new Response().failure("该身份证号码已被注册");
            //判断账号是否存在
            isExist = userService.chechAccount((String)user.get("account"));
            if (isExist)return new Response().failure("该账号已被使用");
            return userService.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //删除用户
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Response deleteUser(@RequestBody String account){
        try{
            return userService.deleteUser(account);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //用户授权
    @RequestMapping(value = "/impower",method = RequestMethod.POST)
    public Response impowerRole(@RequestBody Map map){
      try {
          return userService.impowerRole(map);
      }catch (Exception e){
          e.printStackTrace();
          return null;
      }
    }

    //修改用户
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Response updateUser(@RequestBody Map user){
        try{
            return userService.updateUser(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //有条件和无条件的分页查询用户信息,区域框的条件areacon,用户框的条件usercon
    @RequestMapping(value = "/getusers",method = RequestMethod.POST)
    public Response getUserList(@RequestBody Map map){
        try{
          return userService.getUserList(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //通过用户账号获取用户信息
    @RequestMapping(value = "/getUserInfoByAccount",method = RequestMethod.POST)
    public Response getUserInfoByAccount(@RequestBody String account){
        try{
            return userService.getUserInfoByAccount(account);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //重置密码
    @RequestMapping(value = "/resetPwd",method = RequestMethod.POST)
    public Response resetPwd(@RequestBody String account){
        try{
            return userService.resetPwd(account);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //用户退出,删除存储的权限信息
    @RequestMapping(value = "/exit",method = RequestMethod.POST)
    public Object userExit(){
        try{
            session.removeAttribute("authority");
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //判断只有是省级管理员才能显示法律法规（即省级管理员管理法律法规）
    @RequestMapping(value = "/manageLaws",method = RequestMethod.POST)
    public Response manageLaws(){
              try{
                  Authority authority = (Authority)session.getAttribute("authority");
                  String roleId = authority.getRole().getId();
                  if (roleId.equals("1")){//省级管理员的角色ID是1
                     return new Response().success();
                  }else return new Response().failure();
              }catch(Exception e){
                  e.printStackTrace();
                  return new Response().failure();
              }
    }
}
