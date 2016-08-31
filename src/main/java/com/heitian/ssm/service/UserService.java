package com.heitian.ssm.service;

import com.heitian.ssm.model.request.RegistRequest;
import com.heitian.ssm.model.request.UpdateRequest;
import com.heitian.ssm.model.response.User;
import com.heitian.ssm.model.request.LoginRequest;

import java.util.Date;
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

    int update(UpdateRequest updateRequest);

    int regist(RegistRequest registRequest);

    User selectByUserName(String name);

    int insertByUserName(String name, Date createTime);
}
