/**
 * Copyright (C) DXHM 版权所有
 * 文件名： SystemInit.java
 * 包名： com.dx.init
 * 说明：
 * @author 乾之轩
 * @date 2012-6-12 上午11:28:34
 * @version V1.0
 */ 
package com.dx.init;

import com.dx.back.systemdate.services.impl.SystemDateServices;
import com.dx.common.SystemParm;



/**
 * 类名： SystemInit
 * 描述：系统参数初始化
 * @author 乾之轩
 * @date 2012-6-12 上午11:28:34
 *
 *
 */
public class SystemInit   {
	private SystemDateServices systemDateServices;

	public void setSystemDateServices(SystemDateServices systemDateServices) {
		this.systemDateServices = systemDateServices;
	}
	public void init(){
		// 设置系统的当前日期
		SystemParm.SystemDate = systemDateServices.getSytstemDate().getNowDate();
	}
}
