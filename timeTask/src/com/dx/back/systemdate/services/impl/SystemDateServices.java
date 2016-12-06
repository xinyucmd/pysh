/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� SystemDateServices.java
 * ������ com.dx.back.systemdate.services.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-12 ����10:40:24
 * @version V1.0
 */ 
package com.dx.back.systemdate.services.impl;

import com.dx.back.systemdate.bean.SystemDateBean;
import com.dx.back.systemdate.dao.ISystemDateDao;

/**
 * ������ SystemDateServices
 * ������
 * @author Ǭ֮��
 * @date 2012-6-12 ����10:40:24
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
	 * ���������� ���ϵͳ���� 
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-6-12 ����10:41:49
	 */
	public SystemDateBean  getSytstemDate(){
		return systemDateDao.getSytstemDate();
	}
	
	
	public void updateSystemDate(SystemDateBean systemDateBean){
		systemDateDao.updateSystemDate(systemDateBean);
	}
	
	

}
