package com.heitian.ssm.service;

import com.heitian.ssm.model.User;
import com.heitian.ssm.model.LoginRequest;

import java.util.List;

/**
 * Created by Zhangxq on 2016/7/15.
 */
public interface UserService {

    List<User> selectAllUser();

    User selectUserById(Long userId);

    User selectUserByPhoneOrEmail(String emailOrPhone);

    User login(String phone,String password);

    User login(LoginRequest loginRequest);

    int update(LoginRequest loginRequest);

    int regist(LoginRequest loginRequest);
}
