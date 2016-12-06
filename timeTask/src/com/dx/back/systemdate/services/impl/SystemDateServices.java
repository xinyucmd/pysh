/**
 * Copyright (C) DXHM 版权所有
 * 文件名： SystemDateServices.java
 * 包名： com.dx.back.systemdate.services.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-6-12 上午10:40:24
 * @version V1.0
 */ 
package com.dx.back.systemdate.services.impl;

import com.dx.back.systemdate.bean.SystemDateBean;
import com.dx.back.systemdate.dao.ISystemDateDao;

/**
 * 类名： SystemDateServices
 * 描述：
 * @author 乾之轩
 * @date 2012-6-12 上午10:40:24
 *
 *
 */
public class SystemDateServices {
	
	private ISystemDateDao systemDateDao;

	public void setSystemDateDao(ISystemDateDao systemDateDao) {
		this.systemDateDao = systemDateDao;
	}
	/**
	 * 
	 * 方法描述： 获得系统日期 
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-6-12 上午10:41:49
	 */
	public SystemDateBean  getSytstemDate(){
		return systemDateDao.getSytstemDate();
	}
	
	
	public void updateSystemDate(SystemDateBean systemDateBean){
		systemDateDao.updateSystemDate(systemDateBean);
	}
	
	

}
