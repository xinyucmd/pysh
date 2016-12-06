/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IplanWeb.java
 * 包名： com.dx.loan.plan.webservices
 * 说明：
 * @author songshengkai
 * @date Jun 18, 2012 9:50:08 AM
 * @version V1.0
 */ 
package com.dx.loan.plan.webservices;

import javax.jws.WebResult;
import javax.jws.WebService;

import com.dx.loan.plan.bean.PlanBeans;
import com.dx.loan.plan.bean.PlanParmBean;
/**
 * 类名： IplanWeb
 * 描述：
 * @author songshengkai
 * @date Jun 18, 2012 9:50:08 AM
 *
 *
 */
@WebService
public interface IplanWeb {
	/**
	 * 
	 * 方法描述： 根据借据号和还款计划参数实体生成还款计划
	 * @param dueNo
	 * @param planParmBean
	 * @return
	 * PlanBeans
	 * @author songshengkai
	 * @date 2012-6-5 下午07:42:39
	 */
	@WebResult
	public PlanBeans createPlan(String dueNo,PlanParmBean planParmBean);
}
