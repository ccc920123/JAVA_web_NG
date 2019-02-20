package com.scxd.service;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysRole;

import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 9:48 2018/10/25
 * @Modified By:
 */
public interface RoleManageService {
    Response getRoleListData(String rolecon, int level, int pageNo, int pageSize);

    Response upDataRoleData(SysRole role);

    Response setRoleMenus(Map map);

    Response deleteRole(Map map);

    Response getRoleDetail(String id);

    Response addRoleData(SysRole role);

    Response getRoleMenusTree(String roleId);

    Response getLevel(int level);
}
