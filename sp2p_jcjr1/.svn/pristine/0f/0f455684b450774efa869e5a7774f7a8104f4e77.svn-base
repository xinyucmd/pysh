package com.sp2p.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class CheckRegFrom {
		 /**
		 * 判断User-Agent 是不是来自于手机
		 * @param ua
		 * @return
		 */
	// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),    
    // 字符串在编译时会被转码一次,所以是 "\\b"    
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)    
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"    
            +"|windows (phone|ce)|blackberry"    
            +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"    
            +"|laystation portable)|nokia|fennec|htc[-_]"    
            +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";    
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"    
            +"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";    
      
    //移动设备正则匹配：手机端、平板  
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);    
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);    
    public static boolean check(HttpServletRequest request){
			 try {
				 String userAgent = request.getHeader("USER-AGENT").toLowerCase();
				 if (null == userAgent) {
				 userAgent = "";
				 }
				 // 匹配
				 Matcher matcherPhone = phonePat.matcher(userAgent);
				 Matcher matcherTable = tablePat.matcher(userAgent);
				 if (matcherPhone.find() || matcherTable.find()) {
				 return true;
				 } else {
				 return false;
				 }
				 } catch (Exception e) {
				 e.printStackTrace();
				 }
				 return false;
				     }  
		 }
