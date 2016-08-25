package com.heitian.ssm.dao;

import com.heitian.ssm.model.User;
import com.heitian.ssm.model.LoginRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by Zhangxq on 2016/7/15.
 */

@Repository
public interface UserDao {

    List<User> selectAllUser();

    User selectUserById(@Param("userId") Long userId);

    User selectUserByPhoneOrEmail(@Param("emailOrPhone") String emailOrPhone);

    User login(@Param("phone") String phone, @Param("password") String password);

    User login(@RequestBody LoginRequest loginRequest);

    int update(@RequestBody LoginRequest loginRequest);

    int regist(@RequestBody LoginRequest loginRequest);
}
