package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.UserDao;
import com.heitian.ssm.model.request.RegistRequest;
import com.heitian.ssm.model.request.UpdateRequest;
import com.heitian.ssm.model.response.User;
import com.heitian.ssm.model.request.LoginRequest;
import com.heitian.ssm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhangxq on 2016/7/15.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserDao userDao;

    public List<User> selectAllUser() {
        return userDao.selectAllUser();
    }

    public User selectUserById(Long userId) {
        return userDao.selectUserById(userId);
    }

    public User selectUserByPhoneOrEmail(String emailOrPhone) {
        return userDao.selectUserByPhoneOrEmail(emailOrPhone);
    }

    public User login(String phone, String password) {
        return userDao.login(phone,password);
    }

    public User login(LoginRequest loginRequest) {
        return userDao.login(loginRequest);
    }

    public int update(UpdateRequest updateRequest) {
        return userDao.update(updateRequest);
    }

    public int regist(RegistRequest registRequest) {
        return userDao.regist(registRequest);
    }

    public User selectByUserName(String name){
        return userDao.selectByUserName(name);
    }

    public int insertByUserName(String name, Date createTime){
        return userDao.insertByUserName(name,createTime);
    }
}
