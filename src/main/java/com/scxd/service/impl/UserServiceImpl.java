package com.scxd.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.scxd.beans.Response;
import com.scxd.beans.database.SysMenu;
import com.scxd.beans.database.SysRole;
import com.scxd.beans.database.SysUser;
import com.scxd.beans.extendbeans.Authority;
import com.scxd.dao.RoleManageDao;
import com.scxd.dao.UserDao;
import com.scxd.service.UserService;
import com.scxd.toolkit.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 用户业务逻辑层实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userdao;

    @Autowired
    private RoleManageDao roleManageDao;
    @Autowired
    private HttpSession session;

    //用户登录,登录成功并获取用户权限信息，将当前用户存入session
    @Override
    public Response userLogin(Map map) throws Exception {

        if (map.size() == 0) return new Response().failure("用户名密码为空");
        int total = userdao.selectAccount((String) map.get("account"));
        if (total == 0) return new Response().failure("该账号不存在");
        total = userdao.selectUserByAccountAndPassqord(map);
        if (total == 0) return new Response().failure("登录密码错误");

        //获取用户信息，并存入session和权限类Authority
        SysUser userInfo = userdao.selectUserInfo(map);
        if (userInfo == null) {
            return new Response().failure("该账号被禁止使用");
        }
        Authority authority = new Authority();
        authority.setUser(userInfo);

        //获取用户的角色信息
        String roleId = userInfo.getRoleid();
        if (UtilClass.strIsEmpty(roleId)) return new Response().failure("该用户未授权角色");
        SysRole roleInfo = userdao.selectRoleInfo(roleId);
        if (roleInfo == null) {
            return new Response().failure("该账号未分配角色");
        }
        authority.setRole(roleInfo);
        //获取角色的菜单信息
        List<String> sysMenus = userdao.selectMenuListByRole(roleInfo.getId());
        authority.setSysMenuIds(sysMenus);

        //将权限信息存入session方便进入home.html时及时获取数据
        session.setAttribute("authority", authority);
        return new Response().success();
    }

    //用户密码修改
    @Override
    public Response changePassword(String oldPassword, String newPassword) throws Exception {

        //取出当前登录用户的信息,比较用户输入的密码是否正确，正确则允许修改
        Authority authority = (Authority) session.getAttribute("authority");
        String pwd = authority.getUser().getPassword();
        String account = authority.getUser().getAccount();
        if (!(pwd.equals(oldPassword))) return new Response().failure("输入的原始密码不正确");
        int result = userdao.updatePassword(newPassword, account);
        return (result != 0) ? new Response().success() : new Response().failure("修改密码失败");
    }

    //新增用户
    @Override
    public Response addUser(Map user) throws Exception {
        if (user == null || user.size() == 0) return new Response().failure("输入信息为空");
        int result = userdao.insertUser(user);
        return (result != 0) ? new Response().success() : new Response().failure("新增用户失败");
    }

    //删除用户
    @Override
    public Response deleteUser(String account) throws Exception {
        if (UtilClass.strIsEmpty(account)) return new Response().failure("输入账户信息为空");
        int result = userdao.deleteUser(account);
        return (result != 0) ? new Response().success() : new Response().failure("删除用户失败");
    }

    //修改用户
    @Override
    public Response updateUser(Map user) throws Exception {
        if (user == null || user.size() == 0) return new Response().failure("输入信息为空");
        String account = (String) user.get("account");
        user.remove("account");
        int result = userdao.updateUser(user, account);
        return (result != 0) ? new Response().success() : new Response().failure("修改用户失败");
    }

    //角色授权
    @Override
    public Response impowerRole(Map map) throws Exception {
        if (map.size() == 0 || map == null) return new Response().failure("输入账户或角色ID信息为空");
        String account= (String) map.get("account");
        String roleId= (String) map.get("roleId");
        if (StringUtils.isEmpty(account)||StringUtils.isEmpty(roleId)){
            return new Response().failure("授权信息不全");
        }
        int accountXzjb=userdao.getAccountXzjb(account);
       SysRole sysRole= roleManageDao.getRoleDetailById(roleId);
       if (sysRole==null){
           return new Response().failure("未发现当前角色信息");
       }
       String xzjbStr="未知";
       switch (accountXzjb){
           case 1:
               xzjbStr="省级";
               break;
           case 2:
               xzjbStr="市级";
               break;
           case 3:
               xzjbStr="区县级";
               break;
           case 4:
               xzjbStr="乡镇级";
               break;
       }
       if (sysRole.getRolelevel()!=accountXzjb){
           return new Response().failure("当前账户为"+xzjbStr+"，请授予对应级别的角色权限");
       }
        int result = userdao.updateRoleid(map);
        return (result != 0) ? new Response().success() : new Response().failure("用户授权失败");
    }

    //验证身份证号是否存在
    @Override
    public boolean chechSfzhm(String sfzhm) throws Exception {
        int result = userdao.selectSfzhm(sfzhm);
        return (result == 1) ? true : false;
    }

    //验证账号不能重复
    @Override
    public boolean chechAccount(String account) throws Exception {
        int result = userdao.selectAccount(account);
        return (result == 1) ? true : false;
    }

    //有条件和无条件的分页查询用户信息
    @Override
    public Response getUserList(Map map) throws Exception {
        if (map.size() == 0 || map == null) return new Response().failure("查询条件为空");
        //获取用户所在的区域ID
        String qyid = new UtilClass().getUserQyid(session);
        map.put("qyid", qyid);
        int total = userdao.selectTotalUserList(map);
        if (total == 0) return new Response().success(null, 0);

        //分页查询具体内容
        map = UtilClass.getPaging(map);
        List<Map> result = userdao.selectUserList(map);
        return (result.size() != 0 && result != null) ? new Response().success(result, total) : new Response().failure("获取信息失败");
    }

    //通过用户账号获取用户信息
    @Override
    public Response getUserInfoByAccount(String account) throws Exception {
        SysUser user = userdao.selectUserInfoByAccount(account);
        return (user != null) ? new Response().success(user) : new Response().failure();
    }

    //重置密码
    @Override
    public Response resetPwd(String account) throws Exception {
        //设置重置后的密码为888888
//        String password = "888888";
        String password = "21218cca77804d2ba1922c33e0151105";
        int row = userdao.updatePassword(password, account);
        return (row != 0) ? new Response().success() : new Response().failure("重置密码失败");
    }
}
