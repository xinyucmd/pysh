/**
 * Copyright (C) DXHM 版权所有
 * 文件名： FeeServices.java
 * 包名： com.dx.loan.fee.services
 * 说明：
 * @author 乾之轩
 * @date 2012-6-19 上午09:00:04
 * @version V1.0
 */ 
package com.dx.loan.fee.services;

import com.dx.loan.fee.bean.FeeTypeBean;
import com.dx.loan.fee.dao.IFeeDao;

/**
 * 类名： FeeServices
 * 描述： 费用计算服务类
 * @author 乾之轩
 * @date 2012-6-19 上午09:00:04
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
	 * 方法描述： 获得费用计算实体 
	 * @param feeTypeBean
	 * void
	 * @author 乾之轩
	 * @date 2012-6-19 上午09:01:43
	 */
	public FeeTypeBean getFeeTypeBean(FeeTypeBean feeTypeBean){
		return feeDao.getFeeTypeBean(feeTypeBean);
	}

}
