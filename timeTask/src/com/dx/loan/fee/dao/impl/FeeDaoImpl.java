/**
 * Copyright (C) DXHM 版权所有
 * 文件名： FeeDaoImpl.java
 * 包名： com.dx.loan.fee.dao.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-6-19 上午08:54:01
 * @version V1.0
 */ 
package com.dx.loan.fee.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.loan.fee.bean.FeeTypeBean;
import com.dx.loan.fee.dao.IFeeDao;

/**
 * 类名： FeeDaoImpl
 * 描述：
 * @author 乾之轩
 * @date 2012-6-19 上午08:54:01
 *
 *
 */
public class FeeDaoImpl extends SqlMapClientDaoSupport  implements IFeeDao {

	/**
	 * 
	 * 方法描述：获得费用计算实体 
	 * @param feeTypeBean
	 * @author 乾之轩
	 * @date 2012-6-19 上午08:55:41
	 */
	public FeeTypeBean getFeeTypeBean(FeeTypeBean feeTypeBean) {
		return (FeeTypeBean) this.getSqlMapClientTemplate().queryForObject("getFeeTypeBean", feeTypeBean);
	}

}
