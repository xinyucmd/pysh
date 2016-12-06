/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� LoginDaoImpl.java
 * @���� com.dx.login.dao.impl
 * @˵�� TODO(�����ļ���ʲô)
 * @����   Ǭ֮��
 * @ʱ�� 2012-5-2 ����03:14:21
 * @�汾 V1.0
 */ 
package com.dx.login.dao.impl;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.common.bean.PageBean;
import com.dx.login.bean.LoginBean;
import com.dx.login.dao.ILoginDao;
import com.dx.login.bean.TlrctBean;

public class LoginDaoImpl extends SqlMapClientDaoSupport  implements ILoginDao {

	public LoginBean getLoginBean(String tlrno) {
		tlrno = "121000";
		LoginBean loginBean = (LoginBean)this.getSqlMapClientTemplate().queryForObject("getUserByTlrno", tlrno);
		System.out.println(loginBean.getUserName());
		return loginBean;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<TlrctBean> getTlrctlList(PageBean pageBean) {
		ArrayList<TlrctBean> tlrctlList = (ArrayList<TlrctBean>)this.getSqlMapClientTemplate().queryForList("getTlrctlList",pageBean);
		pageBean.setTotalSize(getTlrctListSize());
		return tlrctlList;
	}
	public String getTlrctListSize(){
		String size=(String)this.getSqlMapClientTemplate().queryForObject("getTlrctlListSize");
		return size;
	}
}
