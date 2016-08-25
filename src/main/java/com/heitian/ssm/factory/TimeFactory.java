package com.heitian.ssm.factory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mitnick.cheng on 2016/8/25.
 */
public class TimeFactory {
    public static String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }
}
