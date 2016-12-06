/**
 * Copyright (C) DXHM 版权所有
 * @文件名 LoginService.java
 * @包名 com.dx.login.services.impl
 * @说明 TODO(描述文件做什么)
 * @作者   乾之轩
 * @时间 2012-5-2 下午03:12:58
 * @版本 V1.0
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
 * @类名 LoginService
 * @描述 TODO(用途说明)
 * @作者 luanhaowei
 * @日期 2012-5-2 下午03:12:58
 * ========修改日志=======
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
