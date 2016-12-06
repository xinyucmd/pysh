/**
 * Copyright (C) DXHM 版权所有
 * 文件名： INormRateDao.java
 * 包名： com.dx.loan.normrate.dao
 * 说明：
 * @author 乾之轩
 * @date 2012-5-17 下午04:46:59
 * @version V1.0
 */ 
package com.dx.loan.normrate.dao;

import java.util.List;

import com.dx.loan.normrate.bean.NormRateBean;
import com.dx.loan.normrate.bean.RateAdjustBean;

/**
 * 类名： INormRateDao
 * 描述：
 * @author 乾之轩
 * @date 2012-5-17 下午04:46:59
 *
 *
 */
public interface INormRateDao {
	/**
	 * 
	 * 方法描述： 根据利率编号获得利率实体
	 * @param rateNo
	 * @return NormrateBean
	 * NormrateBean
	 * @author sll
	 * @date May 15, 2012 3:34:35 PM
	 */
	public NormRateBean getNormrateByNo(String rateNo);
	
	/**
	 * 
	 * 方法描述：根据利率编号获得基准利率调整列表 
	 * @param rateNo
	 * @return
	 * List<RateAdjustBean>
	 * @author 乾之轩
	 * @date 2012-5-21 上午10:20:58
	 */
	public List<RateAdjustBean>  getRateAdjustList(String  rateNo);

}
