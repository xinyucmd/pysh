/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PlanBeans.java
 * 包名： com.dx.loan.plan.bean
 * 说明：
 * @author songshengkai
 * @date Jun 18, 2012 9:33:32 AM
 * @version V1.0
 */ 
package com.dx.loan.plan.bean;

import java.util.HashMap;
import java.util.List;
/**
 * 类名： PlanBeans
 * 描述：
 * @author songshengkai
 * @date Jun 18, 2012 9:33:32 AM
 *
 *
 */
public class PlanBeans {
	private List<PlanBean> planUsers;
    private PlanBean[] planUserArr;
    private HashMap<String, PlanBean> planMaps;
	public List<PlanBean> getPlanUsers() {
		return planUsers;
	}
	public void setPlanUsers(List<PlanBean> planUsers) {
		this.planUsers = planUsers;
	}
	public PlanBean[] getPlanUserArr() {
		return planUserArr;
	}
	public void setPlanUserArr(PlanBean[] planUserArr) {
		this.planUserArr = planUserArr;
	}
	public HashMap<String, PlanBean> getPlanMaps() {
		return planMaps;
	}
	public void setPlanMaps(HashMap<String, PlanBean> planMaps) {
		this.planMaps = planMaps;
	}
}
