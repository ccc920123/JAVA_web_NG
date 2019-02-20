package com.scxd.dao;

import com.scxd.beans.database.SysRole;
import com.scxd.beans.database.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {

    //通过用户名和登录密码获取用户信息
    SysUser selectUserInfo(Map map) throws Exception;

    //获取用户角色信息
    SysRole selectRoleInfo(String roleid) throws Exception;

    //用户密码修改
    int updatePassword(@Param("newPassword") String newPassword, @Param("account")String account) throws Exception;

    //删除用户
    int deleteUser(String account) throws Exception;

    //用户角色授权
    int updateRoleid(Map map) throws Exception;

    //新增用户
    int insertUser(@Param("user") Map user) throws Exception;

    //修改用户
    int updateUser(@Param("user") Map user,@Param("account") String account) throws Exception;

    //查看身份证是否重复
    int selectSfzhm(String sfzhm) throws Exception;

    //查看账号是否重复
    int selectAccount(String account) throws Exception;

    //有条件和无条件的分页查询用户信息总数
    int selectTotalUserList(Map map) throws Exception;

    //有条件和无条件的分页查询用户信息
    List<Map> selectUserList(Map map) throws Exception;

    //根据账户获取用户信息
    SysUser selectUserInfoByAccount(String account) throws Exception;

    //通过账号和密码查询用户是否存在
    int selectUserByAccountAndPassqord(Map map) throws Exception;

    List<String> selectMenuListByRole(@Param("roleId") String id);

    int getAccountXzjb(@Param("account") String account);
}
