package com.heitian.ssm.token;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.heitian.ssm.factory.TimeFactory;
import com.heitian.ssm.factory.UidFactory;
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
}
