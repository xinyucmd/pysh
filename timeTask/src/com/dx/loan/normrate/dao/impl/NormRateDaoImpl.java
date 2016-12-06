/**
 * Copyright (C) DXHM 版权所有
 * 文件名： NormRateDaoImpl.java
 * 包名： com.dx.loan.normrate.dao
 * 说明：
 * @author 乾之轩
 * @date 2012-5-17 下午04:48:16
 * @version V1.0
 */ 
package com.dx.loan.normrate.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.loan.normrate.bean.NormRateBean;
import com.dx.loan.normrate.bean.RateAdjustBean;
import com.dx.loan.normrate.dao.INormRateDao;

/**
 * 类名： NormRateDaoImpl
 * 描述：
 * @author 乾之轩
 * @date 2012-5-17 下午04:48:16
 *
 *
 */
public class NormRateDaoImpl extends SqlMapClientDaoSupport   implements INormRateDao{

	/**
	 * 
	 * 方法描述： 根据利率编号获得利率实体
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
	 * 方法描述： 根据利率编号获得利率调整列表
	 * @param rateNo
	 * void
	 * @author 乾之轩
	 * @date 2012-5-19 下午04:34:18
	 */
	@SuppressWarnings("unchecked")
	public List<RateAdjustBean>  getRateAdjustList(String  rateNo){
		List<RateAdjustBean>  rateAdjustBeansList = (List<RateAdjustBean>) this.getSqlMapClientTemplate().queryForList("getRateAdjustList", rateNo);
		return rateAdjustBeansList;
	}
	
	

}
