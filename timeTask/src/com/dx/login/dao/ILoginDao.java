/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� ILoginDao.java
 * @���� com.dx.login.dao
 * @˵�� TODO(�����ļ���ʲô)
 * @����   Ǭ֮��
 * @ʱ�� 2012-5-2 ����03:13:58
 * @�汾 V1.0
 */ 
package com.dx.login.dao;

import java.util.ArrayList;

import com.dx.common.bean.PageBean;
import com.dx.login.bean.LoginBean;
import com.dx.login.bean.TlrctBean;

/**
 * @���� ILoginDao
 * @���� TODO(��;˵��)
 * @���� luanhaowei
 * @���� 2012-5-2 ����03:13:58
 * ========�޸���־=======
 *
 */
public interface ILoginDao {
	public LoginBean getLoginBean(String tlrno);
	public ArrayList<TlrctBean> getTlrctlList(PageBean pageBean);
	public String getTlrctListSize();
}
