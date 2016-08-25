package com.heitian.ssm.factory;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mitnick.cheng on 2016/8/19.
 */
public class TokenFactory {
    private final static String SECRET_KEY = "FtxLoginSdk";
    private final static String ISSUER =   "https://www.ftx.com/api/";

    public final static String  ACCESS = "access";
    public final static String  REFRESH = "refresh";
    public final static String  CODE = "code";

    private static Logger log = Logger.getLogger(TokenFactory.class);

    private static SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static String  generatorToken(String id,String tokenType){
        long effectiveTime = 7 * 24 * 60 * 60 * 1000;//有效时间为7天
        long start = System.currentTimeMillis(); //start time
        long end = start + effectiveTime; // expires claim. In this case the token expires in 600 seconds

//        log.info("generatorToken：" + ISSUER + "\t" + sdf.format(new Date(start)) + "\t" + sdf.format(new Date(end)) + "\t" + id+ "\t" + tokenType);

        final JWTSigner signer = new JWTSigner(SECRET_KEY);
        final HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("iss", ISSUER);
        claims.put("start", start);
        claims.put("end", end);
        claims.put("id", id);
        claims.put("type",tokenType);
        final String jwt = signer.sign(claims);
        return jwt;
    }

    public static String verifyToken(String token){
        JWTVerifier verifier = new JWTVerifier(SECRET_KEY);
        try {
            Map<String, Object> claims = verifier.verify(token);
            if(claims!=null ){
                String id = (String) claims.get("id");
                String iss = (String) claims.get("iss");
                long end = (Long)claims.get("end");

                if(end < System.currentTimeMillis()){
                    log.info("验证码已过期");
                    return "验证码已过期";
                }else if(!iss.equals(ISSUER)){
                    log.info("证书有误");
                    return "证书有误";
                }else {
                    return id;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String  generatorSmsToken(String phone,String code){
        long effectiveTime = 1 * 2 * 60 * 1000;//有效时间为二分钟
        long start = System.currentTimeMillis(); //start time
        long end = start + effectiveTime; // expires claim. In this case the token expires in 600 seconds

        log.info("generatorToken：" + ISSUER + "\t" + sdf.format(new Date(start)) + "\t" + end + "\t"+"-----"+phone+"------" + code);

        final JWTSigner signer = new JWTSigner(SECRET_KEY);
        final HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("iss", ISSUER);
        claims.put("start", start);
        claims.put("end", end);
        claims.put("phone", phone);
        claims.put("code", code);
        claims.put("type",CODE);
        final String jwt = signer.sign(claims);
        return jwt;
    }


    public static String verifySmsToken(String token,String phoneParam,String codeParam){
        JWTVerifier verifier = new JWTVerifier(SECRET_KEY);
        try {
            Map<String, Object> claims = verifier.verify(token);
            if(claims!=null){
                String iss = (String) claims.get("iss");
                long start = (Long)claims.get("start");
                long end = (Long)claims.get("end");
                String phone = (String) claims.get("phone");
                String code = (String) claims.get("code");
                String type = (String) claims.get("type");
                log.info("verifySmsToken："+iss+"-----"+start+"------"+sdf.format(new Date(end))+"-----"+phone+"-----"+code+"----"+type);

                if(end < System.currentTimeMillis()){
                    log.info("验证码已过期");
                    return "验证码已过期";
                }else if(!iss.equals(ISSUER)){
                    log.info("证书有误");
                    return "证书有误";
                }else if(!type.equals(CODE)){
                    log.info("类型有误");
                    return "类型有误";
                }else if(!phone.equals(phoneParam)){
                    log.info("手机号有误");
                    return "手机号有误";
                }else if(!code.equals(codeParam)){
                    log.info("验证码有误");
                    return "验证码有误";
                }else{
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }
}
