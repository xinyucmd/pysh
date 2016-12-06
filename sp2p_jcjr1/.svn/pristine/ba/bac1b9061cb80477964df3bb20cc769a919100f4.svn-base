package com.sp2p.system.interceptor;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.shove.security.Encrypt;
import com.shove.security.License;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.app.BaseAppAction;
import com.sp2p.constants.IConstants;

public class AppInterceptor implements Interceptor {
	private String deviceType = "-1";
	private final static String APP_KEY = "wDwdKd27d0Qj1wdEa536yiuPE96Ofds3L";

	public void destroy() {

	}

	public void init() {

	}

	protected String request(String key) {
		return request().getParameter(key);
	}

	protected Map<String, String> getAppAuthMap() {
		return getRequestMap("auth");

	}

	protected Map<String, String> getAppInfoMap() {
		return getRequestMap("info");
	}

	protected HttpServletRequest request() {
		return ServletActionContext.getRequest();
	}

	protected HttpSession session() {
		return ServletActionContext.getRequest().getSession();
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getRequestMap(String requestAttr) {
		HttpServletRequest request = request();
		Map<String, String> jsonMap = new HashMap<String, String>();
		System.out.println("request url==>" + request.getContextPath());
		Map<String, Object> paraMap = request.getParameterMap();
		System.out.println("session==>" + session());
		Set<String> keySet = paraMap.keySet();
		System.out.println("=============request value start===============");
		for (String key : keySet) {
			Object val = paraMap.get(key);
			if (val instanceof String[]) {
				System.out.println(key + "==>"
						+ Arrays.toString((String[]) val));
			} else {
				System.out.println(key + "==>" + val);
			}
		}
		System.out.println("=============request value end===============");
		String json = request(requestAttr);
		Map<String, String> map = (Map<String, String>) JSONObject.toBean(
				JSONObject.fromObject(json), HashMap.class);
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return map;

	}

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		// HttpServletRequest request = ServletActionContext.getRequest();
		// String auth = (String) request.getParameter("auth");
		// System.out.println("auth===========>" + auth);
		// String info = (String)request.getParameter("info");
		// System.out.println("info===========>" + info);
		// Map<String, String> jsonMap = new HashMap<String, String>();
		// if (StringUtils.isBlank(auth)) {
		// jsonMap.put("error", "-2");
		// jsonMap.put("msg", "验证签名不正确");
		// JSONUtils.printObject(jsonMap);
		// return null;
		// }
		//
		// Map<String, String> map = (Map<String, String>) JSONObject.toBean(
		// JSONObject.fromObject(auth), HashMap.class);
		// String crc = map.get("crc");
		// System.out.println("crc==>"+crc);
		// if (StringUtils.isBlank(crc)) {
		// jsonMap.put("error", "-2");
		// jsonMap.put("msg", "验证签名不正确");
		// JSONUtils.printObject(jsonMap);
		// return null;
		// }
		// if(StringUtils.isBlank(map.get("time_stamp"))){
		// jsonMap.put("error", "-2");
		// jsonMap.put("msg", "时间戳不能为空");
		// JSONUtils.printObject(jsonMap);
		// return null;
		// }
		//		
		// if(StringUtils.isBlank(map.get("imei"))){
		// jsonMap.put("error", "-2");
		// jsonMap.put("msg", "imei不能为空");
		// JSONUtils.printObject(jsonMap);
		// return null;
		// }
		// if(StringUtils.isBlank(map.get("uid"))){
		// jsonMap.put("error", "-2");
		// jsonMap.put("msg", "uid不能为空");
		// JSONUtils.printObject(jsonMap);
		// return null;
		// }
		// if(StringUtils.isBlank(map.get("uid"))){
		// jsonMap.put("error", "-2");
		// jsonMap.put("msg", "uid不能为空");
		// JSONUtils.printObject(jsonMap);
		// return null;
		// }
		// StringBuilder keys = new StringBuilder();
		// keys.append(map.get("time_stamp"));
		// keys.append(map.get("imei"));
		// keys.append(map.get("uid"));
		// keys.append(Encrypt.MD5(map.get("uid")+"").substring(9, 20));
		// keys.append(info);
		// keys.append(APP_KEY);
		// System.out.println("keys==>"+keys.toString());
		// String md5Crc = Encrypt.MD5(keys.toString());
		// System.out.println("MD5CRC==>"+md5Crc);
		// if(!crc.equals(md5Crc)){
		// jsonMap.put("error", "-2");
		// jsonMap.put("msg", "验证签名不正确");
		// JSONUtils.printObject(jsonMap);
		// return null;
		// }

		//验证签名
		Map<String, String> jsonMap = new HashMap<String, String>();
		String auth = request("auth");
		String info = request("info");
		String sign = request("sign");
		String tmp = Encrypt.MD5(auth + info + IConstants.PASS_KEY, "gb2312")
				.toUpperCase();
		System.out.println("------sign1 = "+sign);
		System.out.println("------tmp = "+tmp);
		//whb
//		if (!tmp.equals(sign)) {
//			jsonMap.put("error", "-2");
//			jsonMap.put("msg", "签名不正确");
//			JSONUtils.printObject(jsonMap);
//			return null;
//		}
		HttpServletRequest request =  ServletActionContext.getRequest();
//		License.update(request, IConstants.LICENSE);
		// 配置拦截器 注册码拦截
//		if (!License.getAndoridAllow(request)&&!License.getiOSAllow(request)) {
//			jsonMap.put("error", "1");
//			jsonMap.put("msg", "");
//			JSONUtils.printObject(jsonMap);
//			return null;
//		}
		/**
		 * 校验令牌是否正确
		 * 
		 * @param token
		 * @param userId
		 * @return
		 */
		Map<String, String> appAuthMap = getAppAuthMap();
		String userId = appAuthMap.get("uid");
		String token = appAuthMap.get("token");
		if(StringUtils.isBlank(token) || "(null)".equals(token)){
			jsonMap.put("error", "0");
			jsonMap.put("msg", "您没有登录");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		String des = token.substring(0, token.length()-8);
		String md5 = com.shove.security.Encrypt.MD5(des
				+ IConstants.PASS_KEY);
		if (token.substring(token.length()-8).equals(md5.substring(0, 8))) {
			String temp = com.shove.security.Encrypt.decrypt3DES(des, IConstants.PASS_KEY);
			String[] array = temp.split(",");
			if (array.length>1 && array[1].equals(userId)) {
				return invocation.invoke();
			}
		} else {
			jsonMap.put("error", "1");
			jsonMap.put("msg", "非法操作");
			JSONUtils.printObject(jsonMap);
			return null;
		}

		return null;
	}
}

// boolean flag = true;
// String domain = map.get("domain");
// deviceType = map.get("deviceType");
// try {
// String sesPWD = Encrypt.decryptSES(IConstants.sesPWd, IConstants.sesKey);
//	
// flag = sesPWD.contains(domain);
// sesPWD = sesPWD.replaceAll("'", "");
// String [] array = sesPWD.split(",");
// if(!deviceType.equals(array[0])&&!array[0].equals("0")){
// flag = false;
// }
// } catch (Exception e) {
// e.printStackTrace();
// }
// if(!flag){
// jsonMap.put("error", "1");
// jsonMap.put("msg", "非法操作");
// JSONUtils.printObject(jsonMap);
// return null;
// }else{
// return invocation.invoke();
// }

