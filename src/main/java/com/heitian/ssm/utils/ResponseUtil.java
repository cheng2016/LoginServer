package com.heitian.ssm.utils;

/**
 * Created by mitnick.cheng on 2016/8/18.
 */
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
public class ResponseUtil {
    public static void write(HttpServletResponse response,Object object)throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.println(object);
        out.flush();
        out.close();
    }
}
