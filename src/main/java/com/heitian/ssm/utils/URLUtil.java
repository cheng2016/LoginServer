package com.heitian.ssm.utils;

/**
 * Created by mitnick.cheng on 2016/8/23.
 */

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * URL工具类
 * @author sky.du
 *
 */
public class URLUtil {

    private static Logger log = Logger.getLogger(URLUtil.class);

    /**
     * get方式请求
     * @param urlStr
     * @param params
     * @return
     */
    public static String getRequestStr(String urlStr,Map<String, String> params) {
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        urlStr +=param;
        return getRequestStr(urlStr);
    }

    /**
     * get方式请求
     * @param urlStr
     * @return
     */
    public static String getRequestStr(String urlStr) {
        String inputLine = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String str = null;
            while ((str = in.readLine()) != null) {
                inputLine += str;
            }
            in.close();
        } catch (Exception e) {
            log.error("[URLUtil] getRequestStr error." + e.getMessage(), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return inputLine;
    }

    /**
     * Post方式
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return
     */
    public static String getPostRequestStr(String urlStr, String param) {
        String inputLine = "";
        URL url = null;
        PrintWriter out = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            out = new PrintWriter(urlConnection.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String str = null;
            while ((str = in.readLine()) != null) {
                inputLine += str;
            }
            in.close();
        } catch (Exception e) {
            log.error("[URLUtil] getRequestStr error." + e.getMessage(), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return inputLine;
    }

    public static String postRequestStr(String urlStr, String param, String charSet) throws Exception {
        String inputLine = "";
        HttpURLConnection urlConnection = null;
        PrintWriter out;
        try {
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            out = new PrintWriter(urlConnection.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), charSet));
            String str = null;
            while ((str = in.readLine()) != null) {
                inputLine += str;
            }
            in.close();
        } catch (Exception e) {
            log.error("[URLUtil] getRequestStr error." + e.getMessage()+",urlStr="+urlStr, e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return inputLine;
    }

    public static String URLEncoder(String param) {
        try {
            param = java.net.URLEncoder.encode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("[URLUtil] URLEncoder error." + e.getMessage(), e);
            return "";
        }
        return param;
    }

    public static String uniDecode(String name) {
        char[] c = name.toCharArray();
        char[] out = new char[c.length];
        name = loadConvert(c, 0, c.length, out);
        return name;
    }

    private static String loadConvert(char[] in, int off, int len,
                                      char[] convtBuf) {
        if (convtBuf.length < len) {
            int newLen = len * 2;
            if (newLen < 0) {
                newLen = Integer.MAX_VALUE;
            }
            convtBuf = new char[newLen];
        }
        char aChar;
        char[] out = convtBuf;
        int outLen = 0;
        int end = off + len;

        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = in[off++];
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed \\uxxxx encoding.");
                        }
                    }
                    out[outLen++] = (char) value;
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = (char) aChar;
            }
        }
        return new String(out, 0, outLen);
    }

    public static List<String> URLPost(String strUrl,String map,String charset) throws IOException {

        String content = "";
        content = map;
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setAllowUserInteraction(false);
        con.setUseCaches(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset="+charset);
        BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
                getOutputStream()));
        bout.write(content);
        bout.flush();
        bout.close();
        BufferedReader bin = new BufferedReader(new InputStreamReader(con.
                getInputStream()));
        List<String> result = new ArrayList<String>();
        while (true) {
            String line = bin.readLine();
            if (line == null) {
                break;
            }
            else {
                result.add(line);
            }
        }
        return result;
    }
}