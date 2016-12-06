/**
 * Copyright (C) DXHM 版权所有
 * @文件名 LoginBean.java
 * @包名 com.dx.bean.login
 * @说明 TODO(描述文件做什么)
 * @作者   乾之轩
 * @时间 2012-5-2 下午02:59:40
 * @版本 V1.0
 */ 
package com.dx.login.bean;

import java.io.Serializable;

/**
 * 
 * 类名： LoginBean
 * 描述： 系统登录实体bean
 * @author 乾之轩
 * @date 2012-5-10 上午09:33:56
 * 
 *
 */
public class LoginBean implements Serializable {

	/**
	 * @类名 LoginBean.java
	 * @描述 TODO(用途说明)
	 * @作者 乾之轩
	 * @日期 2012-5-2 下午03:16:33
	 * ========修改日志=======
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
