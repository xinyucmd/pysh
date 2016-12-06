/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� AuthenticationInterceptor.java
 * @���� com.dx.common.aoplog
 * @˵�� TODO(�����ļ���ʲô)
 * @����   Ǭ֮��
 * @ʱ�� 2012-5-5 ����01:55:40
 * @�汾 V1.0
 */ 
package com.dx.common.interceptor;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.dx.login.bean.LoginBean;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @���� AuthenticationInterceptor
 * @���� ��¼������
 * @���� Ǭ֮��
 * @���� 2012-5-5 ����01:55:40
 * ========�޸���־=======
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
		 * @���� sessionIsValid
		 * @���� ��֤session�Ƿ�ʱ
		 * @���� @param ai
		 * @���� @return
		 * @����ֵ boolean
		 * @���� Ǭ֮��
		 * @ʱ�� 2012-5-5 ����02:12:17
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
