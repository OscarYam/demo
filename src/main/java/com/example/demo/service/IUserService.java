package com.example.demo.service;

import com.example.demo.entity.UserEntity;

import java.util.List;

/**
 * 处理用户数据的业务层接口
 * */
public interface IUserService {
    /**
     * 获取所有用户数据
     * */
    List<UserEntity> getAllUsers();

    /**
     * 通过用户名查找用户数据
     * */
    List<UserEntity> getUserByName(String name);

    /**
     * 通过用户验证码查找用户数据
     * */
//    Object getUserByToken(String token);
    Object getUserByToken(UserEntity userEntity, String userTokenNew);

    /**
     * 通过用户ID查找用户
     *
     * @return*/
    Object getUserById(String Id);

    /**
     * 添加用户
     * */
    UserEntity addUser(UserEntity usr);

    /**
     * 删除用户
     * */
    String delUser(String usrId);

    /**
     * 通过用户名更新用户数据
     *
     * @return*/
    Object updateUser(UserEntity usr, String userTokenNew);

    /**
     * 用户登录
     * */
    Object userLogin(String usrID, String userPsw, String userTokenNew);

    /**
     * 获取验证码
     * */
    String getUserRam(int num);

}
