package com.scxd.dao;

import com.scxd.beans.database.SysRole;
import com.scxd.beans.database.SysRoleMenu;
import com.scxd.beans.database.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 9:53 2018/10/25
 * @Modified By:
 */
@Repository
public interface RoleManageDao {
    int getRoleListDataTotal(@Param("rolecon") String rolecon, @Param("level")int level);

    List<Map> getRoleListData(@Param("rolecon") String rolecon,@Param("level") int level, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    int upDataRoleData(SysRole role);

    void deleteRoleMenus(@Param("roleId")String roleId);

    int setRoleMenus(@Param("sysRoleMenus") List<SysRoleMenu> sysRoleMenus);

    int  deleteRole(@Param("roleId")String roleId);

    List<SysUser> getUserRolecount(@Param("roleId")String roleId);

    SysRole getRoleDetailById(@Param("roleId")String id);

    int addRoleData(SysRole role);

    List<Map> getRoleMenusTree(@Param("roleId") String roleId);

    int getRoleNameCount(@Param("name")String name);

    int getRolePmspCount(@Param("pmsp")String pmsp);

    List<Map> getLevel(@Param("level") int level);
}
