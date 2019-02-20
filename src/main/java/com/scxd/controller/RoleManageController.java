package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysRole;
import com.scxd.beans.extendbeans.Authority;
import com.scxd.service.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 9:16 2018/10/25
 * @Modified By:
 */
@RestController
@RequestMapping("RoleManage")
public class RoleManageController {
    @Autowired
    RoleManageService roleManageService;

    @RequestMapping(value = "/getRoleListData", method = RequestMethod.GET)
    public Response getRoleListData(String rolecon, int pageNo, int pageSize, HttpSession session) {
        Authority authority = (Authority) session.getAttribute("authority");
        if (authority == null) {
            return new Response().failure("登录失效");
        }
        int level = authority.getRole().getRolelevel();
        return roleManageService.getRoleListData(rolecon,level, pageNo, pageSize);

    }

    @RequestMapping(value = "/upDataRoleData", method = RequestMethod.POST)
    public Response upDataRoleData(@RequestBody SysRole role, HttpSession session) {
        return roleManageService.upDataRoleData(role);

    }

    @RequestMapping(value = "/addRoleData", method = RequestMethod.POST)
    public Response addRoleData(@RequestBody SysRole role, HttpSession session) {
        return roleManageService.addRoleData(role);

    }

    @RequestMapping(value = "/getRoleDetail", method = RequestMethod.GET)
    public Response getRoleDetail(String id, HttpSession session) {
        return roleManageService.getRoleDetail(id);

    }

    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public Response deleteRole(@RequestBody Map map, HttpSession session) {
        return roleManageService.deleteRole(map);

    }

    @RequestMapping(value = "/setRoleMenus", method = RequestMethod.POST)
    public Response setRoleMenus(@RequestBody Map map, HttpSession session) {
        return roleManageService.setRoleMenus(map);

    }

    @RequestMapping(value = "/getRoleMenusTree", method = RequestMethod.GET)
    public Response getRoleMenusTree(String roleId, HttpSession session) {
        return roleManageService.getRoleMenusTree(roleId);

    }

    @RequestMapping(value = "/getLevel", method = RequestMethod.GET)
    public Response getLevel(HttpSession session) {
        Authority authority = (Authority) session.getAttribute("authority");
        if (authority == null) {
            return new Response().failure("登录失效");
        }
        int level = authority.getRole().getRolelevel();
        return roleManageService.getLevel(level);

    }
}
