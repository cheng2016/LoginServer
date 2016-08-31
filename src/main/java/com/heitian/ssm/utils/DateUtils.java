package com.heitian.ssm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mitnick.cheng on 2016/8/31.
 */
public class DateUtils {
    public static String getCurrentTime(){
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = sdf.format(new Date(System.currentTimeMillis()));
        return time;
    }

    public static Date getCurrentDate(){
        Date date = new Date(System.currentTimeMillis());
        return date;
    }
}
