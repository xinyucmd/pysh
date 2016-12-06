/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PutOutServices.java
 * ������ com.dx.loan.putout.services.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-16 ����10:22:43
 * @version V1.0
 */ 
package com.dx.loan.putout.services.impl;

import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.putout.dao.IPutOutDao;


/**
 * ������ PutOutServices
 * ������ 
 * @author Ǭ֮��
 * @date 2012-5-16 ����10:22:43
 *
 *
 */
public class PutOutServices {
	
	private IPutOutDao putOutDao;

	public void setPutOutDao(IPutOutDao putOutDao) {
		this.putOutDao = putOutDao;
		
	}
	
	
	
	/**
	 * 
	 * ���������� ����ſ�ʵ��
	 * @param pactBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-28 ����03:10:12
	 */
	public void savePactBean(PactBean pactBean){
		putOutDao.savePactBean(pactBean);
	}
	
	
	

}
