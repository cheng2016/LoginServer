package com.heitian.ssm.sms;

import com.google.gson.Gson;
import com.heitian.ssm.controller.UserController;
import com.heitian.ssm.model.response.JsonResult;
import com.heitian.ssm.factory.TokenFactory;
import com.heitian.ssm.utils.URLUtil;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by mitnick.cheng on 2016/8/23.
 */
public class SmsService {
    private final static String URL="http://kltx.sms10000.com.cn/sdk/SMS";
    private final static Logger log = Logger.getLogger(UserController.class);

    private final static String mobileUid = "3216";
    private final static String mobilePwd = "dca3a8e80a573a58f37f98bb38444cb3";

    public static String sendMessage(String phone){
        String securityCode=new Random().nextInt(999999)+"";
        int ret=0;
        String msgId=System.currentTimeMillis()+"";
        String url=URL+"?cmd=send&uid="+mobileUid
                +"&psw="+mobilePwd
                +"&mobiles="+phone
                +"&msgid="+msgId
                +"&msg="+getSecurityContext(securityCode);

        log.info(url);

        String result= URLUtil.getRequestStr(url);
        ret=Integer.valueOf(result);
        //发送成功
        if(ret==100){
            return new Gson().toJson(new JsonResult(200, "getCode", TokenFactory.generatorSmsToken(phone,securityCode)));
        }
        return new Gson().toJson(new JsonResult(ret, "发送失败", null));
    }

    //后台验证码内容
    private static String getSecurityContext(String code){
        String context="您的验证码为 "+ code +" (任何人向您索取安全码,均为诈骗行为,请勿泄漏)【范特西】";
        return URLEncoder(context);
    }

    //gbk编码
    private static String URLEncoder(String param) {
        try {
            param = java.net.URLEncoder.encode(param, "gbk");
            log.info("URLDecoder abk 编码--------------"+java.net.URLDecoder.decode(param,"gbk"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        return param;
    }
}
