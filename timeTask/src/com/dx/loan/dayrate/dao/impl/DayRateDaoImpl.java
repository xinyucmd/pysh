/**
 * Copyright (C) DXHM 版权所有
 * 文件名： DayRateDaoImpl.java
 * 包名： com.dx.loan.dayrate.dao.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-6-11 下午05:12:12
 * @version V1.0
 */ 
package com.dx.loan.dayrate.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.dayrate.dao.IDayRateDao;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.payment.bean.RatedayBBean;

/**
 * 类名： DayRateDaoImpl
 * 描述：
 * @author 乾之轩
 * @date 2012-6-11 下午05:12:12
 *
 *
 */
public class DayRateDaoImpl extends SqlMapClientDaoSupport implements IDayRateDao {
/**
 * 
 * 方法描述： 根据借据号和发生日期获得日终实体 
 * @param dueNo
 * @param occureDate
 * @return
 * @author 乾之轩
 * @date 2012-6-12 下午07:56:46
 */
	public RatedayBean getRateDay(RatedayBean ratedayBean) {
		/*System.out.println(ratedayBean);
		System.out.println(1);*/
		return (RatedayBean)this.getSqlMapClientTemplate().queryForObject("getRateDay", ratedayBean);
	}
/**
 * 
 * 方法描述： 保存日终计算结果 
 * @param rateDayBean
 * @author 乾之轩
 * @date 2012-6-11 下午05:13:35
 */
	public void saveRateDayBean(RatedayBean rateDayBean) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().insert("saveRateDayBean",rateDayBean);
	}

	public void updateRateDay(RatedayBean ratedayBean) {
		this.getSqlMapClientTemplate().update("updateRateDay", ratedayBean);
	}
	/**
	 * 
	 * 方法描述：  获得已经逾期的还款
	 * @param ratedayBean
	 * @return
	 * @author 乾之轩
	 * @date 2012-6-20 上午10:48:21
	 */
	@SuppressWarnings("unchecked")
	public List<RatedayBean> getOverPlan(RatedayBean ratedayBean) {
		return this.getSqlMapClientTemplate().queryForList("getOverPlans", ratedayBean);
	}
	
	/**
	 *  * 方法描述：获得期号小于当前期且还没有还完款的日终列表
	 */
	@SuppressWarnings("unchecked")
	public List<RatedayBean> getRateDayBeanList(RatedayBean ratedayBean) {
		return this.getSqlMapClientTemplate().queryForList("getRateDayList", ratedayBean);
	}
	@SuppressWarnings("unchecked")
	public List<RepayBean> getOverduePeriods(String deuno){
		return this.getSqlMapClientTemplate().queryForList("getOverduePeriod", deuno);
	}
	@SuppressWarnings("unchecked")
	public List<PlanBean> getOverdueplanBeans(PlanBean planparmbean){
		return this.getSqlMapClientTemplate().queryForList("getOverduePlanList", planparmbean);
	}
	
	/**
	 * 功能:更新借据表的逾期天数
	 */
	public void upLn(Map<String, String> parmMap){
		 this.getSqlMapClientTemplate().update("upLn", parmMap);
	}
	
	/**
	 * 功能:更新五级分类表逾期天数 
	 */
	public void upFive(Map<String, String> parmMap) {
		 this.getSqlMapClientTemplate().update("upFive", parmMap);
	}
	public void saveRateDayBean(RatedayBBean ratedayBBean) {
		this.getSqlMapClientTemplate().insert("saveRateDayBBean",ratedayBBean);
		
	}
}
