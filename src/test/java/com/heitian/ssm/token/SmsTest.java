package com.heitian.ssm.token;

import com.heitian.ssm.factory.TokenFactory;
import com.heitian.ssm.utils.Base64Utils;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * Created by mitnick.cheng on 2016/8/23.
 */
public class SmsTest {
    String SECRET_KEY = "moozooherpderp";

    @Test
    public void testSmsToken(){
        String jwt = TokenFactory.generatorSmsToken("18202745852","336688");
        TokenFactory.verifySmsToken(jwt,"18202745852","336688");
    }

    @Test
    public void Base64Test(){
        String str = "18202745852";
        String strEncode = Base64.encodeBase64String(str.getBytes());
        System.out.println(strEncode);
        String phoneDecode = new String(Base64.decodeBase64(strEncode));
        System.out.println(phoneDecode);

        System.out.println(Base64Utils.encode(str));
        System.out.println(Base64Utils.decode(Base64Utils.encode(str)));
    }
}
