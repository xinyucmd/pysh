/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� ILoginService.java
 * @���� com.dx.login.services
 * @˵�� TODO(�����ļ���ʲô)
 * @����   Ǭ֮��
 * @ʱ�� 2012-5-2 ����03:13:17
 * @�汾 V1.0
 */ 
package com.dx.login.services;


import com.dx.common.bean.PageBean;
import com.dx.login.bean.LoginBean;


/**
 * @���� ILoginService
 * @���� TODO(��;˵��)
 * @���� luanhaowei
 * @���� 2012-5-2 ����03:13:17
 * ========�޸���־=======
 *
 */
public interface ILoginService {
	
	public  LoginBean getLoginBean(String tlrno);
	public String getTlrctListSize();
	
	public String  getTlrctlList(PageBean pageBean);

}
