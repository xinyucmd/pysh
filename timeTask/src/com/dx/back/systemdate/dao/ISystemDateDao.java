/**
 * Copyright (C) DXHM 版权所有
 * 文件名： ISystemDateDao.java
 * 包名： com.dx.back.systemdate.dao
 * 说明：
 * @author 乾之轩
 * @date 2012-6-12 上午10:13:39
 * @version V1.0
 */ 
package com.dx.back.systemdate.dao;

import com.dx.back.systemdate.bean.SystemDateBean;

/**
 * 类名： ISystemDateDao
 * 描述：
 * @author 乾之轩
 * @date 2012-6-12 上午10:13:39
 *
 *
 */
public interface ISystemDateDao {
	
	/**
	 * 
	 * 方法描述：获得当前的系统日期 
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-6-12 上午10:10:38
	 */
	public  SystemDateBean getSytstemDate();
	
	
	/**
	 * 
	 * 方法描述： 更新系统日期 
	 * @param date
	 * void
	 * @author 乾之轩
	 * @date 2012-6-12 上午10:11:33
	 */
	public  void  updateSystemDate(SystemDateBean systemDateBean);

}
