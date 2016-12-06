/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IFeeDao.java
 * 包名： com.dx.loan.fee.dao
 * 说明：
 * @author 乾之轩
 * @date 2012-6-19 上午08:45:25
 * @version V1.0
 */ 
package com.dx.loan.fee.dao;

import com.dx.loan.fee.bean.FeeTypeBean;

/**
 * 类名： IFeeDao
 * 描述： 费油计算接口
 * @author 乾之轩
 * @date 2012-6-19 上午08:45:25
 *
 *
 */
public interface IFeeDao {
	/**
	 * 
	 * 方法描述：查询费用计算实体 (参数根据业务而定) 
	 * @param feeTypeBean
	 * void
	 * @author 乾之轩
	 * @date 2012-6-19 上午08:49:29
	 */
	public FeeTypeBean  getFeeTypeBean(FeeTypeBean feeTypeBean); 
}
