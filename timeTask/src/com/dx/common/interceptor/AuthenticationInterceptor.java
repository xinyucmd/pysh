/**
 * Copyright (C) DXHM 版权所有
 * @文件名 AuthenticationInterceptor.java
 * @包名 com.dx.common.aoplog
 * @说明 TODO(描述文件做什么)
 * @作者   乾之轩
 * @时间 2012-5-5 下午01:55:40
 * @版本 V1.0
 */ 
package com.dx.common.interceptor;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.dx.login.bean.LoginBean;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @类名 AuthenticationInterceptor
 * @描述 登录拦截器
 * @作者 乾之轩
 * @日期 2012-5-5 下午01:55:40
 * ========修改日志=======
 *
 */
public class AuthenticationInterceptor extends AbstractInterceptor {
	
		private static final long serialVersionUID = -5384062760655500223L;
	 	public void init(){
	    }
		public void destroy(){
		}
		public String intercept(ActionInvocation ai) throws Exception {
			if(sessionIsValid(ai)){
				return ai.invoke();
			}else{
				return "noLogin";
			}
		}
		/**
		 * 
		 * @名称 sessionIsValid
		 * @描述 验证session是否超时
		 * @参数 @param ai
		 * @参数 @return
		 * @返回值 boolean
		 * @作者 乾之轩
		 * @时间 2012-5-5 下午02:12:17
		 */
		private  boolean  sessionIsValid(ActionInvocation ai){
			Map<String, Object> sessionMap = ai.getInvocationContext().getSession();
			LoginBean loginBean = (LoginBean)sessionMap.get("user");
			if (loginBean==null || StringUtils.isEmpty(loginBean.getTlrno())){
				return false;
			}else{
				return true;
			}
		}
}
