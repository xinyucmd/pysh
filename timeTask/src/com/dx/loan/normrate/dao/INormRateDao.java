/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� INormRateDao.java
 * ������ com.dx.loan.normrate.dao
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-17 ����04:46:59
 * @version V1.0
 */ 
package com.dx.loan.normrate.dao;

import java.util.List;

import com.dx.loan.normrate.bean.NormRateBean;
import com.dx.loan.normrate.bean.RateAdjustBean;

/**
 * ������ INormRateDao
 * ������
 * @author Ǭ֮��
 * @date 2012-5-17 ����04:46:59
 *
 *
 */
public interface INormRateDao {
	/**
	 * 
	 * ���������� �������ʱ�Ż������ʵ��
	 * @param rateNo
	 * @return NormrateBean
	 * NormrateBean
	 * @author sll
	 * @date May 15, 2012 3:34:35 PM
	 */
	public NormRateBean getNormrateByNo(String rateNo);
	
	/**
	 * 
	 * �����������������ʱ�Ż�û�׼���ʵ����б� 
	 * @param rateNo
	 * @return
	 * List<RateAdjustBean>
	 * @author Ǭ֮��
	 * @date 2012-5-21 ����10:20:58
	 */
	public List<RateAdjustBean>  getRateAdjustList(String  rateNo);

}
