/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� LoginBean.java
 * @���� com.dx.bean.login
 * @˵�� TODO(�����ļ���ʲô)
 * @����   Ǭ֮��
 * @ʱ�� 2012-5-2 ����02:59:40
 * @�汾 V1.0
 */ 
package com.dx.login.bean;

import java.io.Serializable;

/**
 * 
 * ������ LoginBean
 * ������ ϵͳ��¼ʵ��bean
 * @author Ǭ֮��
 * @date 2012-5-10 ����09:33:56
 * 
 *
 */
public class LoginBean implements Serializable {

	/**
	 * @���� LoginBean.java
	 * @���� TODO(��;˵��)
	 * @���� Ǭ֮��
	 * @���� 2012-5-2 ����03:16:33
	 * ========�޸���־=======
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private  String  userName; 
	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getTlrno() {
		return tlrno;
	}



	public void setTlrno(String tlrno) {
		this.tlrno = tlrno;
	}



	private String tlrno;
	
	

	public LoginBean(){};
	

}
