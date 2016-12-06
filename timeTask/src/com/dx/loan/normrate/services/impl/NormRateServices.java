/**
 * Copyright (C) DXHM 版权所有
 * 文件名： NormRateServices.java
 * 包名： com.dx.loan.normrate.services.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-5-17 下午04:53:42
 * @version V1.0
 */ 
package com.dx.loan.normrate.services.impl;

import java.util.List;

import com.dx.loan.normrate.bean.NormRateBean;
import com.dx.loan.normrate.bean.RateAdjustBean;
import com.dx.loan.normrate.dao.INormRateDao;

/**
 * 类名： NormRateServices
 * 描述：
 * @author 乾之轩
 * @date 2012-5-17 下午04:53:42
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
	 * 方法描述： 根据借据号获得基准利率实体
	 * @param rateNo
	 * @return
	 * NormRateBean
	 * @author 乾之轩
	 * @date 2012-5-17 下午05:05:02
	 */
	public NormRateBean getNormRateBeanByNo(String rateNo){
		return normRateDao.getNormrateByNo(rateNo);
	} 
	/**
	 * 
	 * 方法描述：  根据利率编号获得利率调整列表
	 * @param rateNo
	 * @return
	 * List<RateAdjustBean>
	 * @author 乾之轩
	 * @date 2012-5-21 上午10:22:38
	 */
	public List<RateAdjustBean>  getRateAdjustList(String  rateNo){
		List<RateAdjustBean>  rateAdjustBeansList = normRateDao.getRateAdjustList(rateNo);
		return rateAdjustBeansList;
	}
	
	

	

}
