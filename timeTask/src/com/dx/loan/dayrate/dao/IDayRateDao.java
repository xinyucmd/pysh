/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IDayRateDao.java
 * 包名： com.dx.loan.dayrate.dao
 * 说明：
 * @author 乾之轩
 * @date 2012-6-11 下午05:11:47
 * @version V1.0
 */ 
package com.dx.loan.dayrate.dao;

import java.util.List;
import java.util.Map;

import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.payment.bean.RatedayBBean;

/**
 * 类名： IDayRateDao
 * 描述：
 * @author 乾之轩
 * @date 2012-6-11 下午05:11:47
 *
 *
 */
public interface IDayRateDao {
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
	public    RatedayBean getRateDay(RatedayBean rateDayBean);

	/**
	 * 
	 * 方法描述： 更新日终结果
	 * @param ratedayBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-16 下午04:27:18
	 */
	public void updateRateDay(RatedayBean ratedayBean);
	
	/**
	 * 
	 * 方法描述： 获得已经逾期的还款 
	 * @param ratedayBean
	 * @return
	 * List<RatedayBean>
	 * @author 乾之轩
	 * @date 2012-6-20 上午10:47:43
	 */
	public List<RatedayBean> getOverPlan(RatedayBean ratedayBean);
	/**
	 *  * 方法描述：获得期号小于当前期且还没有还完款的日终列表
	 * @param ratedayBean
	 * @return
	 */
	public List<RatedayBean> getRateDayBeanList(RatedayBean ratedayBean);
	
	public List<RepayBean> getOverduePeriods(String deuno);
	public List<PlanBean> getOverdueplanBeans(PlanBean planparmbean);
	
	
	/**
	 * 功能:更新借据的逾期天数  
	 * @param parmMap
	 */
	
	public void  upLn(Map<String,String> parmMap);  
	
	/**
	 * 功能:更新借据的逾期天数  
	 * @param parmMap
	 */
	
	public void  upFive(Map<String,String> parmMap);

	public void saveRateDayBean(RatedayBBean ratedayBBean);
  	
	
	
	
	
	
	

}
