package com.scxd.beans.extendbeans;

import com.scxd.beans.database.SysArea;
import com.scxd.beans.database.SysRole;
import com.scxd.beans.database.SysUser;

import java.util.List;

/**
 * 权限表，包含了用户信息，菜单，角色，所属区域
 */
public class Authority {

    private SysUser user;

    private SysRole role;

    private SysArea area;

    List<String> sysMenuIds;

    public List<String> getSysMenuIds() {
        return sysMenuIds;
    }

    public void setSysMenuIds(List<String> sysMenuIds) {
        this.sysMenuIds = sysMenuIds;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public SysArea getArea() {
        return area;
    }

    public void setArea(SysArea area) {
        this.area = area;
    }
}
