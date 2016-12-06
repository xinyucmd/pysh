/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� FeeDaoImpl.java
 * ������ com.dx.loan.fee.dao.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-19 ����08:54:01
 * @version V1.0
 */ 
package com.dx.loan.fee.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.loan.fee.bean.FeeTypeBean;
import com.dx.loan.fee.dao.IFeeDao;

/**
 * ������ FeeDaoImpl
 * ������
 * @author Ǭ֮��
 * @date 2012-6-19 ����08:54:01
 *
 *
 */
public class FeeDaoImpl extends SqlMapClientDaoSupport  implements IFeeDao {

	/**
	 * 
	 * ������������÷��ü���ʵ�� 
	 * @param feeTypeBean
	 * @author Ǭ֮��
	 * @date 2012-6-19 ����08:55:41
	 */
	public FeeTypeBean getFeeTypeBean(FeeTypeBean feeTypeBean) {
		return (FeeTypeBean) this.getSqlMapClientTemplate().queryForObject("getFeeTypeBean", feeTypeBean);
	}

}
