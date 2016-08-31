package com.heitian.ssm.token;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.Gson;
import com.heitian.ssm.factory.TimeFactory;
import com.heitian.ssm.factory.UidFactory;
import com.heitian.ssm.model.Account;
import com.heitian.ssm.model.request.LoginRequest;
import com.heitian.ssm.utils.DateUtils;
import com.heitian.ssm.utils.MD5Util;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

/**
 * Created by mitnick.cheng on 2016/8/25.
 */
public class UidTest {
    @Test
    public void testUid(){
        UUID UID  = UUID.randomUUID();
        Random random = new Random();
        System.out.println("FTX"+ UidFactory.generatorUid());
        System.out.println("FTX"+""+UID.toString().replace("-","").substring(0,8));
        System.out.println(TimeFactory.getNowTime());
    }

    @Test
    public void getTime(){
        System.out.println(DateUtils.getCurrentTime());
    }


    @Test
    public void requestTest(){
        LoginRequest loginRequest = new LoginRequest("18202745852","123456");
        System.out.println(new Gson().toJson(loginRequest));
        Account account = new Account(1,"ftx","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJzdGFydCI6MTQ3MjYxNTg2NDc3MiwiaXNzIjoiaHR0cHM6Ly93d3cuZnR4LmNvbS9hcGkvIiwidHlwZSI6ImFjY2VzcyIsImVuZCI6MTQ3MzIyMDY2NDc3Mn0.N7WbyZClg4WlMkncZC0uMm5tcaWy6HF6asWH29bFoXU");
        System.out.println(new Gson().toJson(account));
    }

    @Test
    public void md5Test(){
        System.out.println(MD5Util.md5("123456"));
    }
}
