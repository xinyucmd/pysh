/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� FeeServices.java
 * ������ com.dx.loan.fee.services
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-19 ����09:00:04
 * @version V1.0
 */ 
package com.dx.loan.fee.services;

import com.dx.loan.fee.bean.FeeTypeBean;
import com.dx.loan.fee.dao.IFeeDao;

/**
 * ������ FeeServices
 * ������ ���ü��������
 * @author Ǭ֮��
 * @date 2012-6-19 ����09:00:04
 *
 *
 */
public class FeeServices {
	private IFeeDao feeDao;

	public void setFeeDao(IFeeDao feeDao) {
		this.feeDao = feeDao;
	}
	/**
	 * 
	 * ���������� ��÷��ü���ʵ�� 
	 * @param feeTypeBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-19 ����09:01:43
	 */
	public FeeTypeBean getFeeTypeBean(FeeTypeBean feeTypeBean){
		return feeDao.getFeeTypeBean(feeTypeBean);
	}

}
