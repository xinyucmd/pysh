/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� LoginService.java
 * @���� com.dx.login.services.impl
 * @˵�� TODO(�����ļ���ʲô)
 * @����   Ǭ֮��
 * @ʱ�� 2012-5-2 ����03:12:58
 * @�汾 V1.0
 */ 
package com.dx.login.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dx.common.bean.PageBean;
import com.dx.login.bean.LoginBean;
import com.dx.login.bean.TlrctBean;
import com.dx.login.dao.ILoginDao;
import com.dx.login.services.ILoginService;
import com.google.gson.Gson;

/**
 * @���� LoginService
 * @���� TODO(��;˵��)
 * @���� luanhaowei
 * @���� 2012-5-2 ����03:12:58
 * ========�޸���־=======
 *
 */
public class LoginService implements ILoginService {
	public LoginService(){};
	private ILoginDao loginDao;

	public ILoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}

	public LoginBean getLoginBean(String tlrno) {
		loginDao.getLoginBean(tlrno);
		return null;
	}
	
	public String getTlrctListSize(){
		return loginDao.getTlrctListSize();
	}
	
	
	public String  getTlrctlList(PageBean pageBean){
		return toJSON(loginDao.getTlrctlList(pageBean),pageBean);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private String toJSON(List<TlrctBean> list, PageBean pageBean) {
		Map map = new HashMap();
		map.put("page", pageBean.getCurPage());
		map.put("total", pageBean.getTotalSize());
		List mapList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map cellMap = new HashMap();
			cellMap.put("BRNO", list.get(i).getBRNO());
			cellMap.put("cell", new Object[] { 
					list.get(i).getBRNO(),
					list.get(i).getTLRNO(),
					list.get(i).getNAME(),
					list.get(i).getROLENAME(),
					list.get(i).getTXDATE()
					});
			mapList.add(cellMap);
		}
		map.put("rows", mapList);
		Gson gs=new Gson();
		String ja =gs.toJson(map);
		return ja.toString();
	}
	
}
