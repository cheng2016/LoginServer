package com.heitian.ssm.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by mitnick.cheng on 2016/8/24.
 */
public class Base64Utils {
    //编码
    public static String encode(String str){
        return Base64.encodeBase64String(str.getBytes());
    }

    //解码
    public static String decode(String str){
        String phoneDecode = new String(Base64.decodeBase64(str));
        return phoneDecode;
    }
}
