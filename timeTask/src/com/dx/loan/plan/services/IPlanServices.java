/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IPlanServices.java
 * 包名： com.dx.loan.plan.services
 * 说明：
 * @author 乾之轩
 * @date 2012-5-31 上午10:05:43
 * @version V1.0
 */ 
package com.dx.loan.plan.services;

import java.util.List;

import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanParmBean;

/**
 * 类名： IPlanServices
 * 描述：
 * @author 乾之轩
 * @date 2012-5-31 上午10:05:43
 *
 *
 */
public interface IPlanServices {
	/**
	 * 
	 * 方法描述： 根据借据号和还款计划参数实体生成还款计划
	 * @param dueNo
	 * @param planParmBean
	 * @return
	 * List<PlanBean>
	 * @author 乾之轩
	 * @date 2012-6-5 下午07:42:39
	 */
	public List<PlanBean> createPlan(String dueNo,PlanParmBean planParmBean);
	/**
	 * 编辑还款计划时,根据现有还款计划从新生成还款计划
	 * @param planList
	 * @return
	 */
	public List<PlanBean> createPlan(List<PlanBean> planList,PlanParmBean planParmBean);
	
	
	/**
	 * 
	 * 方法描述： 保存还款计划 
	 * @param planBeans
	 * void
	 * @author 乾之轩
	 * @date 2012-6-5 下午07:43:41
	 */
	public void savePlanList(List<PlanBean> planBeans);
	

}
