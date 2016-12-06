/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� NormRateDaoImpl.java
 * ������ com.dx.loan.normrate.dao
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-17 ����04:48:16
 * @version V1.0
 */ 
package com.dx.loan.normrate.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.loan.normrate.bean.NormRateBean;
import com.dx.loan.normrate.bean.RateAdjustBean;
import com.dx.loan.normrate.dao.INormRateDao;

/**
 * ������ NormRateDaoImpl
 * ������
 * @author Ǭ֮��
 * @date 2012-5-17 ����04:48:16
 *
 *
 */
public class NormRateDaoImpl extends SqlMapClientDaoSupport   implements INormRateDao{

	/**
	 * 
	 * ���������� �������ʱ�Ż������ʵ��
	 * @param rateNo
	 * @return NormrateBean
	 * NormrateBean
	 * @author sll
	 * @date May 15, 2012 3:34:35 PM
	 */
	public NormRateBean getNormrateByNo(String rateNo) {
		NormRateBean normrateBean = (NormRateBean) this.getSqlMapClientTemplate().queryForObject("getNormrateByNo", rateNo);
		return normrateBean;
	}
	/**
	 * 
	 * ���������� �������ʱ�Ż�����ʵ����б�
	 * @param rateNo
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����04:34:18
	 */
	@SuppressWarnings("unchecked")
	public List<RateAdjustBean>  getRateAdjustList(String  rateNo){
		List<RateAdjustBean>  rateAdjustBeansList = (List<RateAdjustBean>) this.getSqlMapClientTemplate().queryForList("getRateAdjustList", rateNo);
		return rateAdjustBeansList;
	}
	
	

}
