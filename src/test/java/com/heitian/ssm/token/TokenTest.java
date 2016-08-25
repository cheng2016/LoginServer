package com.heitian.ssm.token;

import com.heitian.ssm.factory.TokenFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mitnick.cheng on 2016/8/19.
 */
public class TokenTest {
    Logger log = Logger.getLogger(TokenTest.class);

    @Test
    public void testToken() {
        String jwt = TokenFactory.generatorToken(12+"", TokenFactory.ACCESS);
        log.info("jwt：" + jwt);
        jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJzdGFydCI6MTQ3MTg2MDQwNywiaXNzIjoiaHR0cHM6Ly93d3cuZnR4LmNvbS9hcGkvIiwidHlwZSI6ImFjY2VzcyIsImVuZCI6MTQ3NTQ2MDQwN30.oUEC56DubljoWtjq9cMu-RSewsPn4gTeDtSPbJOgak8";
        System.out.println(TokenFactory.verifyToken(jwt));
    }

    @Test
    public void testFireToken() {
        //1小时=60分钟=3600秒
        long effectiveTime = 1 * 60 * 60;
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("uid", "1");
        payload.put("phone", "13880385808");

    }

    @Test
    public void testDate (){
        long effectiveTime = 1 * 60 * 60 * 1000;//有效时间为一个小时
        long time =System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(time);
        System.out.println(date.toString());
        System.out.println(sdf.format(date));
        System.out.println(sdf.format(new Date(System.currentTimeMillis() + effectiveTime)));

        long temp = 1471859832;
        Date date_temp = new Date(temp);
        System.out.println("-------"+date_temp.toString()+"----------"+sdf.format(date_temp));

    }
}
