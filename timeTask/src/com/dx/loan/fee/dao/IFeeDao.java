/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� IFeeDao.java
 * ������ com.dx.loan.fee.dao
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-19 ����08:45:25
 * @version V1.0
 */ 
package com.dx.loan.fee.dao;

import com.dx.loan.fee.bean.FeeTypeBean;

/**
 * ������ IFeeDao
 * ������ ���ͼ���ӿ�
 * @author Ǭ֮��
 * @date 2012-6-19 ����08:45:25
 *
 *
 */
public interface IFeeDao {
	/**
	 * 
	 * ������������ѯ���ü���ʵ�� (��������ҵ�����) 
	 * @param feeTypeBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-19 ����08:49:29
	 */
	public FeeTypeBean  getFeeTypeBean(FeeTypeBean feeTypeBean); 
}
