/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� SystemDateDaoImpl.java
 * ������ com.dx.back.systemdate.dao.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-12 ����10:12:49
 * @version V1.0
 */ 
package com.dx.back.systemdate.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.back.systemdate.bean.SystemDateBean;
import com.dx.back.systemdate.dao.ISystemDateDao;
import com.dx.common.util.DateUtil;

/**
 * ������ SystemDateDaoImpl
 * ������
 * @author Ǭ֮��
 * @date 2012-6-12 ����10:12:49
 *
 *
 */
public class SystemDateDaoImpl extends SqlMapClientDaoSupport implements ISystemDateDao {
/**
 * 
 * ���������� ���ϵͳ�ĵ�ǰ����
 * @return
 * @author Ǭ֮��
 * @date 2012-6-12 ����10:17:08
 */
	public SystemDateBean getSytstemDate() {
		return (SystemDateBean) this.getSqlMapClientTemplate().queryForObject("getSytstemDate");
	}
/**
 * 
 * ��������������ϵͳ���� 
 * @param date
 * @author Ǭ֮��
 * @date 2012-6-12 ����10:23:58
 */
	
	public void updateSystemDate(SystemDateBean systemDateBean) {
		try{
			this.getSqlMapClientTemplate().update("updateSystemDate", systemDateBean);
		}catch(Exception e){
			e.printStackTrace();	
		}
		
	}
	
	

	
	
}
