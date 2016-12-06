/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� NormRateServices.java
 * ������ com.dx.loan.normrate.services.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-17 ����04:53:42
 * @version V1.0
 */ 
package com.dx.loan.normrate.services.impl;

import java.util.List;

import com.dx.loan.normrate.bean.NormRateBean;
import com.dx.loan.normrate.bean.RateAdjustBean;
import com.dx.loan.normrate.dao.INormRateDao;

/**
 * ������ NormRateServices
 * ������
 * @author Ǭ֮��
 * @date 2012-5-17 ����04:53:42
 *
 *
 */
public class NormRateServices {
	
	private INormRateDao normRateDao;

	public void setNormRateDao(INormRateDao normRateDao) {
		this.normRateDao = normRateDao;
	}
	
	/**
	 * 
	 * ���������� ���ݽ�ݺŻ�û�׼����ʵ��
	 * @param rateNo
	 * @return
	 * NormRateBean
	 * @author Ǭ֮��
	 * @date 2012-5-17 ����05:05:02
	 */
	public NormRateBean getNormRateBeanByNo(String rateNo){
		return normRateDao.getNormrateByNo(rateNo);
	} 
	/**
	 * 
	 * ����������  �������ʱ�Ż�����ʵ����б�
	 * @param rateNo
	 * @return
	 * List<RateAdjustBean>
	 * @author Ǭ֮��
	 * @date 2012-5-21 ����10:22:38
	 */
	public List<RateAdjustBean>  getRateAdjustList(String  rateNo){
		List<RateAdjustBean>  rateAdjustBeansList = normRateDao.getRateAdjustList(rateNo);
		return rateAdjustBeansList;
	}
	
	

	

}
