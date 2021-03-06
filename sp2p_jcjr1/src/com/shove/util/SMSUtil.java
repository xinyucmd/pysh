package com.shove.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.sp2p.constants.IConstants;

/**
 * 短信接口，对短信接口地址进行拼接，提供公用
 * 
 * @author Administrator
 * 
 */
public class SMSUtil {
	
	public static final String uri = "http://api.sms1086.com/api/Send.aspx?username=";
	/**   
	 * @MethodName: sendSMS  
	 * @Param: SMSUtil  
	 * @Author: gang.lv
	 * @Date: 2013-5-30 下午04:04:13
	 * @Return:    
	 * @Descb: 发送短信
	 * @Throws:
	*/
	public static String sendMSM(String userName, String password, String content,
			String phone,String randomCode) {
		try {
			if(randomCode != null){
				content += randomCode;
			}
			content = "【微信贷】" + content;
			System.out.println("content=="+content);
			if(IConstants.ISDEMO.equals("2")){
				int i = sendMSM1086(userName, password, content, phone);
				if(i == 0){
					return "Sucess";
				}else{
					return "Fail";
				}
			}else{
				return "Fail";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Fail";
		} 
	}
	/**
	 * 还款短信
	* @Description:
	* @author liugang
	* @date 2016年8月29日 下午1:38:06
	 */
	public static int sendMSM(String userName, String password, String content, String phone) throws Exception{	
		URL url = null;
		try
		{
		  String strUrl = uri+URLEncoder.encode("weixindaihk","GB2312")+"&password="+java.net.URLEncoder.encode("weixindai123","GB2312")+"&mobiles="+java.net.URLEncoder.encode(phone,"GB2312")+"&content="+java.net.URLEncoder.encode(content,"GB2312");
		  url = new URL(strUrl);
		  URLConnection UConn = url.openConnection();  
		  BufferedReader breader = new BufferedReader(new InputStreamReader(UConn.getInputStream()));
		  String str=breader.readLine(); 
		  while(str != null){   
		   str = URLDecoder.decode(str,"GB2312");
		   String[] strs=str.split("&");
		   if(strs[0].replace("result=","").trim().equals("0")){
		   	str = "恭喜，短信发送成功。";
		   	return 0;
		   }
		   else {
		   str = "短信发送失败。失败原因："+strs[1].replace("description=","");
		   return 1;
		   }
		  }
	
		}catch(Exception e){
			e.printStackTrace();
		}
		 return 0;
	}
	/**
	 * 验证码短信
	* @Description:
	* @author liugang
	* @date 2016年8月29日 下午1:38:23
	 */
	public static int sendMSM1086(String userName, String password, String content, String phone){
		URL url = null;
		try
		{
		  String strUrl =uri+URLEncoder.encode("weixindai","GB2312")+"&password="+java.net.URLEncoder.encode("weixindai123","GB2312")+"&mobiles="+java.net.URLEncoder.encode(phone,"GB2312")+"&content="+java.net.URLEncoder.encode(content,"GB2312");
		  url = new URL(strUrl);
		  URLConnection UConn = url.openConnection();  
		  BufferedReader breader = new BufferedReader(new InputStreamReader(UConn.getInputStream()));
		  String str=breader.readLine(); 
		  while(str != null){   
		   str = URLDecoder.decode(str,"GB2312");
		   String[] strs=str.split("&");
		   if(strs[0].replace("result=","").trim().equals("0")){
		   	str = "恭喜，短信发送成功。";
		   	return 0;
		   }
		   else {
		   str = "短信发送失败。失败原因："+strs[1].replace("description=","");
		   return 1;
		   }
		  }
	
		}catch(Exception e){
			e.printStackTrace();
		}
		 return 0;
	}
	public static void main(String[] args) {
//		sendSMS("6SDK-EMY-6688-KFSRM", "JCJRBANKING", "你好", "15110120180", "0792");
		sendMSM("hanhan姐", "123456", "你好", "15110120180", "0792");
	}
}
