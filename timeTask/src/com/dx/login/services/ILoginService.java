/**
 * Copyright (C) DXHM 版权所有
 * @文件名 ILoginService.java
 * @包名 com.dx.login.services
 * @说明 TODO(描述文件做什么)
 * @作者   乾之轩
 * @时间 2012-5-2 下午03:13:17
 * @版本 V1.0
 */ 
package com.dx.login.services;


import com.dx.common.bean.PageBean;
import com.dx.login.bean.LoginBean;


/**
 * @类名 ILoginService
 * @描述 TODO(用途说明)
 * @作者 luanhaowei
 * @日期 2012-5-2 下午03:13:17
 * ========修改日志=======
 *
 */
public interface ILoginService {
	
	public  LoginBean getLoginBean(String tlrno);
	public String getTlrctListSize();
	
	public String  getTlrctlList(PageBean pageBean);

}
