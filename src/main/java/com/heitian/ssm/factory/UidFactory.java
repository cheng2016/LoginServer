package com.heitian.ssm.factory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mitnick.cheng on 2016/8/25.
 */
public class UidFactory {
    public static String generatorUid(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmssSSSyyyy");
        long time = System.currentTimeMillis();
        String uid = "FTX"+sdf.format(new Date(time));
        return uid;
    }
}
