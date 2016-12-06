/**
 * Copyright (C) DXHM 版权所有
 * @文件名 ILoginDao.java
 * @包名 com.dx.login.dao
 * @说明 TODO(描述文件做什么)
 * @作者   乾之轩
 * @时间 2012-5-2 下午03:13:58
 * @版本 V1.0
 */ 
package com.dx.login.dao;

import java.util.ArrayList;

import com.dx.common.bean.PageBean;
import com.dx.login.bean.LoginBean;
import com.dx.login.bean.TlrctBean;

/**
 * @类名 ILoginDao
 * @描述 TODO(用途说明)
 * @作者 luanhaowei
 * @日期 2012-5-2 下午03:13:58
 * ========修改日志=======
 *
 */
public interface ILoginDao {
	public LoginBean getLoginBean(String tlrno);
	public ArrayList<TlrctBean> getTlrctlList(PageBean pageBean);
	public String getTlrctListSize();
}
