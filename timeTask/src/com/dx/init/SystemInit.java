/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� SystemInit.java
 * ������ com.dx.init
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-12 ����11:28:34
 * @version V1.0
 */ 
package com.dx.init;

import com.dx.back.systemdate.services.impl.SystemDateServices;
import com.dx.common.SystemParm;



/**
 * ������ SystemInit
 * ������ϵͳ������ʼ��
 * @author Ǭ֮��
 * @date 2012-6-12 ����11:28:34
 *
 *
 */
public class SystemInit   {
	private SystemDateServices systemDateServices;

	public void setSystemDateServices(SystemDateServices systemDateServices) {
		this.systemDateServices = systemDateServices;
	}
	public void init(){
		// ����ϵͳ�ĵ�ǰ����
		SystemParm.SystemDate = systemDateServices.getSytstemDate().getNowDate();
	}
}
