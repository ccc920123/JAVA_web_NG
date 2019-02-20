package com.scxd.service;

import com.scxd.beans.Response;

import java.util.Map;

/**
 * 用户业务逻辑层接口
 */
public interface UserService {

    //用户登录
    Response userLogin(Map map)throws Exception;

    //用户密码修改
    Response changePassword(String oldPassword,String newPassword)throws Exception;

    //新增用户
    Response addUser(Map user)throws Exception;

    //删除用户
    Response deleteUser(String account)throws Exception;

    //修改用户
    Response updateUser(Map user)throws Exception;

    //角色授权
    Response impowerRole(Map map)throws Exception;

    //验证身份证号是否存在
    boolean chechSfzhm(String sfzhm)throws Exception;

    //验证账号不能重复
    boolean chechAccount(String account)throws Exception;

    //有条件和无条件的分页查询用户信息
    Response getUserList(Map map)throws Exception;

    //通过用户账号获取用户信息
    Response getUserInfoByAccount(String account)throws Exception;

    //重置密码
    Response resetPwd(String account)throws Exception;
}
