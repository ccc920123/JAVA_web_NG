package com.scxd.service.impl;

import com.scxd.beans.ListTotal;
import com.scxd.beans.Response;
import com.scxd.beans.database.SysRole;
import com.scxd.beans.database.SysRoleMenu;
import com.scxd.dao.RoleManageDao;
import com.scxd.service.RoleManageService;
import com.scxd.toolkit.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 9:48 2018/10/25
 * @Modified By:
 */
@Service
public class RoleManageServiceImpl implements RoleManageService {

    @Autowired
    RoleManageDao roleManageDao;

    @Autowired
    private HttpSession session;

    @Override
    public Response getRoleListData(String rolecon, int level, int pageNo, int pageSize) {
        int total = roleManageDao.getRoleListDataTotal(rolecon,level);
        List<Map> list = null;
        if (total > 0) {
            list = roleManageDao.getRoleListData(rolecon, level,pageNo, pageSize);
        }
        ListTotal listTotal = new ListTotal(list, total);
        return new Response().success(listTotal);
    }

    @Override
    public Response upDataRoleData(SysRole role) {
        int i = roleManageDao.upDataRoleData(role);
        if (i > 0) {
            return new Response().success("修改成功");
        } else {
            return new Response().success("修改失败");
        }

    }

    @Override
    public Response setRoleMenus(Map map) {
        String roleId = (String) map.get("roleId");
        List<Integer> menus = (List<Integer>) map.get("menuIds");
        roleManageDao.deleteRoleMenus(roleId);
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setrId(roleId);
            sysRoleMenu.setmId(menus.get(i).longValue());
            sysRoleMenus.add(sysRoleMenu);
        }
        int i = roleManageDao.setRoleMenus(sysRoleMenus);
        if (i > 0) {
            return new Response().success("授权成功");
        } else {
            return new Response().success("授权失败");
        }
    }

    @Override
    public Response deleteRole(Map map) {
        String roleId = (String) map.get("roleId");
        int count = roleManageDao.getUserRolecount(roleId).size();
        if (count > 0) {
            return new Response().failure("有用户属于当前角色");
        } else {
            roleManageDao.deleteRoleMenus(roleId);
            roleManageDao.deleteRole(roleId);
            return new Response().success();
        }

    }

    @Override
    public Response getRoleDetail(String id) {
        SysRole sysRole = roleManageDao.getRoleDetailById(id);
        if (sysRole == null) {
            return new Response().failure("未查询到当前角色信息");
        } else {
            return new Response().success(sysRole);
        }

    }

    @Override
    public Response addRoleData(SysRole role) {
        int countName = roleManageDao.getRoleNameCount(role.getName());
        if (countName>0){
            return new Response().failure("已存在该角色");
        }
//        int countPmsp = roleManageDao.getRolePmspCount(role.getPmsp());
//        if (countPmsp>0){
//            return new Response().failure("已存在该角色");
//        }
        int i= roleManageDao.addRoleData(role);
        if (i>0){
            return new Response().success();
        }else {
            return new Response().failure("新增失败");
        }

    }

    @Override
    public Response getRoleMenusTree(String roleId) {
        List<Map> list=   roleManageDao.getRoleMenusTree(roleId);
        return new Response().success(list);
    }

    @Override
    public Response getLevel(int level) {
        List<Map> list=  roleManageDao.getLevel(level);
        return new Response().success(list);
    }


}
