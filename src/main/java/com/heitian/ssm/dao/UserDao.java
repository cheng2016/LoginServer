package com.heitian.ssm.dao;

import com.heitian.ssm.model.request.RegistRequest;
import com.heitian.ssm.model.request.UpdateRequest;
import com.heitian.ssm.model.response.User;
import com.heitian.ssm.model.request.LoginRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
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

    int update(@RequestBody UpdateRequest updateRequest);

    int regist(@RequestBody RegistRequest registRequest);

    User selectByUserName(@Param("name") String name);

    int insertByUserName(@Param("name") String name,@Param("createTime") Date createTime);
}
