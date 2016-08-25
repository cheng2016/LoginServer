package com.heitian.ssm.service.impl;

import com.heitian.ssm.dao.UserDao;
import com.heitian.ssm.model.User;
import com.heitian.ssm.model.LoginRequest;
import com.heitian.ssm.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    public int update(LoginRequest loginRequest) {
        return userDao.update(loginRequest);
    }

    public int regist(LoginRequest loginRequest) {
        return userDao.regist(loginRequest);
    }
}
