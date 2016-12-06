/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� ISystemDateDao.java
 * ������ com.dx.back.systemdate.dao
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-12 ����10:13:39
 * @version V1.0
 */ 
package com.dx.back.systemdate.dao;

import com.dx.back.systemdate.bean.SystemDateBean;

/**
 * ������ ISystemDateDao
 * ������
 * @author Ǭ֮��
 * @date 2012-6-12 ����10:13:39
 *
 *
 */
public interface ISystemDateDao {
	
	/**
	 * 
	 * ������������õ�ǰ��ϵͳ���� 
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-6-12 ����10:10:38
	 */
	public  SystemDateBean getSytstemDate();
	
	
	/**
	 * 
	 * ���������� ����ϵͳ���� 
	 * @param date
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-12 ����10:11:33
	 */
	public  void  updateSystemDate(SystemDateBean systemDateBean);

}
