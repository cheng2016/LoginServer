package com.heitian.ssm.sms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.net.URL;
import java.net.URLConnection;


public class dealText {

	/**
	 * @param args
	 */
	//发送成功短信的保存输入器
	static PrintWriter succMsgWriter;
	//发送失败短信的保存输入器
	static PrintWriter failMsgWriter;
	private static List<String> msgAddList = new ArrayList<String>(); 
	private static List<String> msgSendedList = new ArrayList<String>(); 
	
	
	
	/**
	 * 初使化
	 * @param saveSuccMsgFile
	 */
	private static void initSaveSuccMsg(String saveSuccMsgFile){
		String MsgFile = "";
		if(MsgFile.indexOf(":")<0){//根据传入的路径中是否带":"确认传入的是相对路径还是绝对路径，相对路径则做路径补充，绝对路径直接使用
			MsgFile=new File("").getAbsolutePath()+File.separator+saveSuccMsgFile;
		}	
        if(succMsgWriter==null){
	        try {
	            succMsgWriter = new PrintWriter(new FileWriter(MsgFile, true), true);
	        }
	        catch (IOException e) {
	        	System.err.println("初使化Msg保存文件错误！");
	            succMsgWriter = new PrintWriter(System.err);
	        }
		}
	}
	/**
	 * 保存发送成功的Msg到指定的文件中
	 * @param Msg					当前保存的Msg
	 * @param saveSuccMsgFile		输出文件
	 */
	public static void saveSuccMsg(String Msg,String saveSuccMsgFile){
		if(succMsgWriter==null){
			initSaveSuccMsg(saveSuccMsgFile);
		}		
		succMsgWriter.write(Msg);
		succMsgWriter.println();
		succMsgWriter.flush();
	}
    /**
     * 收件人的数目
     * @return
     */
	public static int getToCounts(){
		return msgAddList.size();
	}
	/**
	 * 从指定的文件中获取发送列表，该文件每行保存一个地址
	 * @param pathOrFilename			可以是存放的绝对地址，也可以是当前路径的相对路径下的文件名
	 * @return
	 */
	public static List<String> getMsgToList(String pathOrFilename){
		FileReader  read = null;
		BufferedReader br = null;
		try{
			if(pathOrFilename.indexOf(":")<0){//根据传入的路径中是否带":"确认传入的是相对路径还是绝对路径，相对路径则做路径补充，绝对路径直接使用
				pathOrFilename=new File("").getAbsolutePath()+File.separator+pathOrFilename;
			}	
			read = new FileReader (pathOrFilename);
			br = new BufferedReader(read);				
			String info = null;
			while((info = br.readLine())!=null) {
				if(!info.trim().equals("")){
					msgAddList.add(info);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				br.close();
				read.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return msgAddList;
	}
	/**
	 * 获取要发送了的Mobile并保存于内存中，发送的时候比较是否已经发送过，发送过的就不要再次发送
	 * @param sendMsgFile		可以是存放的绝对地址，也可以是当前路径的相对路径下的文件名
	 * @throws Exception
	 */
	public static List<String> getSendedMsg(String sendMsgFile){
		FileReader  read = null;
		BufferedReader br = null;
		try{
			if(sendMsgFile.indexOf(":")<0){//根据传入的路径中是否带":"确认传入的是相对路径还是绝对路径，相对路径则做路径补充，绝对路径直接使用
				sendMsgFile=new File("").getAbsolutePath()+File.separator+sendMsgFile;
			}	
			read = new FileReader (sendMsgFile);
			br = new BufferedReader(read);				
			String info = null;
			while((info = br.readLine())!=null) {
				if(!info.trim().equals("")){
					msgSendedList.add(info);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				br.close();
				read.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return msgSendedList;
	}

	/**
	 * 发送短信
	 * 
	 * @param to
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private static boolean sendMsg(String to, String context)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		Date sendDate = new Date();
		System.out.println(new Date().toString() + ": 开始向" + to + "发送....");
		String urlString="http://192.168.12.119:8080/" +
				"api/mobile/send_custom_msg?" +
				"userMobile=" +to+
				"&msg="+URLEncoder.encode(URLEncoder.encode(context,"UTF-8"),"UTF-8");
		System.out.println(urlString);
		boolean flag = false;
		try {
			// 设置收件人
			URL url = new URL(urlString);// 生成url对象
			URLConnection urlConnection = url.openConnection();// 打开url连接
			BufferedReader br = new BufferedReader(new InputStreamReader(
			urlConnection.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
			}
			flag = true;
			System.out.println(sendDate.toString() + ":成功发送。"+sb.toString());
			br.close();
		} catch (Exception ex) {
			System.out.println("发送出现异常！");
			throw ex;
		}
		return flag;
	}

	private static boolean sendMsgPost(String to, String context, String reqtype)
	throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
		Date sendDate = new Date();
		System.out.println(new Date().toString() + ": 开始向" + to + "发送.."+ reqtype +".."+context);
		String rurl="http://192.168.12.119:8080/api/mobile/send_custom_msg";
		String param="type="+reqtype+
				"&userMobile=" +to+"" +				
				"&msg="+URLEncoder.encode(URLEncoder.encode(context,"UTF-8"),"UTF-8");
		boolean flag = false;
		try {
            URL realUrl = new URL(rurl);
         // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
			flag = true;
			System.out.println(sendDate.toString() + ":成功发送。"+result.toString());
		} catch (Exception ex) {
			System.out.println("发送 POST 请求出现异常！"+ex);
			throw ex;
		}
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
		return flag;
		}
	
    //生成随机数字和字母,  
    public static String getStringRandom(int length) {  
          
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
    
	public static void main(String[] args) throws Exception {		
		// 收件人地址list集合
		int failCount = 0;
		int successCount = 0;
		// 每封邮件发送的间隔时间
		int sendInterval = 200;
		// 读取已经发送的Mobile
		List<String> msgAddresslist = getMsgToList("mobile.txt");
		// 读取已经发送的Mobile
		List<String> sendedMsg = getSendedMsg("sended.txt");
		String customContext="范特西荣誉巨制《范特西足球经理2》，5月7日11点QQ空间首服震撼开启！（登录QQ空间搜索足球经理2即可）";
		String sendContext = "";
		String reqtype="post";
		System.out.println("正在初始化..."
				+"列表数量："+msgAddresslist.size()
				+"已发送数量："+sendedMsg.size()
				+"发送内容："+sendContext
		);
		String mobile = "";
		List<String> fialAddressList = new ArrayList<String>();
		try {
		for (int i = 0; i < msgAddresslist.size(); i++) {
			// 休息指定的时间再发
			sendInterval=100+(int)(Math.random()*500);
			System.out.println("线程休息."+sendInterval);
			Thread.sleep(sendInterval);
			boolean flag = false;
			try {
				mobile = msgAddresslist.get(i);
				if (mobile.length()<11) {
					System.out.println("mobile:" + mobile + " 无效");
					continue;
				}				
				// 未发送过的邮件才允许发送
				if (sendedMsg.contains(mobile)) {
					System.out.println("mobile " + mobile + "已经发送过了");
					continue;
				}			
//				sendContext = customContext+ " ("+getStringRandom(3)+")【范特西】";
				sendContext = customContext+" 退订回T【范特西】";
//				sendContext ="11223【验证码】";
				flag = sendMsgPost(mobile, sendContext,reqtype);
//				flag = sendMsg(mobile, sendContext);
			} catch (Exception e) {
				System.out.println("发送mobile:" + mobile + " 时发生异常:");
				e.printStackTrace();
			}
			if (flag) {
				saveSuccMsg(mobile, "sended.txt");
				System.out.println("短信" + mobile + "发送成功!" + "目前已发送：["
						+ (successCount + 1)+"] 条成功...");
				successCount++;
			} else {
				System.out.println("短信" + mobile + "发送失败!");
				fialAddressList.add(mobile);
				failCount++;
			}			
			if((successCount%2000)==0){
				System.out.println("===================");
			}
		}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("process Done~!");
	}

}
