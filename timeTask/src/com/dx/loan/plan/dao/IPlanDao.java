/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IPlanDao.java
 * 包名： com.dx.loan.plan.dao
 * 说明：
 * @author 乾之轩
 * @date 2012-5-26 下午03:21:41
 * @version V1.0
 */ 
package com.dx.loan.plan.dao;

import java.util.List;

import com.dx.loan.plan.bean.PlanBean;

/**
 * 类名： IPlanDao
 * 描述：
 * @author 乾之轩
 * @date 2012-5-26 下午03:21:41
 *
 *
 */
public interface IPlanDao {
	/**
	 * 
	 * 方法描述： 获得还没有还完款的还款计划(0 正常 1逾期 2 还完款)
	 * @return
	 * List<PlanBean>
	 * @author 乾之轩
	 * @date 2012-6-11 下午04:42:45
	 */
	public List<PlanBean> getNormalPlanList(String systemDate);

	public List<PlanBean> getOverPlanList();
	
	/**
	 * 
	 * 方法描述： 根据借据号和期号更新还款计划
	 * @param planBean
	 * void   int
	 * @author 乾之轩
	 * @date 2012-5-17 上午09:09:46
	 */
	public int updatePlan(PlanBean planBean);
	/**
	 * 
	 * 方法描述： 保存还款计划 
	 * @param planBeans
	 * void
	 * @author 乾之轩
	 * @date 2012-6-5 下午07:53:48
	 */
	public void savePlanList(List<PlanBean> planBeans);
	
	/**
	 * 
	 * 方法描述： 删除还款计划 (具体参数根据实际业务而定) 
	 * @param planBean
	 * void
	 * @author 乾之轩
	 * @date 2012-6-18 上午10:12:32
	 */
	public void delPlan(PlanBean planBean);
	/**
	 * 
	 * 方法描述： 批量更新还款计划的状态和欠款标志 
	 * @param state
	 * @param isDebt
	 * @param planBeans
	 * void
	 * @author 乾之轩
	 * @date 2012-6-22 上午09:43:37
	 */
	public void updatePlanList(String state,String isDebt,String dueNo,List<PlanBean> planBeans);  
	
	
	public PlanBean getPlanBean(PlanBean parmPlanBean);
	
	
	public List<PlanBean> getPlanBeanList(PlanBean parmPlanBean);
	
	
	

}
