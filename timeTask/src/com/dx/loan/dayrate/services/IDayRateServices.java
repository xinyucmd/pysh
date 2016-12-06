/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IDayRateServices.java
 * 包名： com.dx.loan.dayrate.services
 * 说明：
 * @author 乾之轩
 * @date 2012-6-11 下午05:06:56
 * @version V1.0
 */ 
package com.dx.loan.dayrate.services;

import java.util.List;

import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.RepayBean;

/**
 * 类名： IDayRateServices
 * 描述：
 * @author 乾之轩
 * @date 2012-6-11 下午05:06:56
 *
 *
 */
public interface IDayRateServices {
	/**
	 * 
	 * 方法描述：  保存日终计算结果
	 * @param rateDayBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-16 下午02:08:30
	 */
	public void  saveRateDayBean(RatedayBean rateDayBean );
	
	/**
	 * 
	 * 方法描述： 根据借据号和期号查询日终实体
	 * @param dueNo 借据号
	 * @param termNo 期号
	 * @return
	 * RatedayBean
	 * @author 乾之轩
	 * @date 2012-5-16 下午03:04:43
	 */
	public    RatedayBean getRateDay(String dueNo,String termNo);

	/**
	 * 
	 * 方法描述： 更新日终结果
	 * @param ratedayBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-16 下午04:27:18
	 */
	public void updateRateDay(RatedayBean ratedayBean);
	public List<RepayBean> getOverduePeriods(String deuno);
	public List<PlanBean> getOverdueplanBeans(PlanBean planparmbean);
}
