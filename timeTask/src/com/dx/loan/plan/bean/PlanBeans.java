/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PlanBeans.java
 * ������ com.dx.loan.plan.bean
 * ˵����
 * @author songshengkai
 * @date Jun 18, 2012 9:33:32 AM
 * @version V1.0
 */ 
package com.dx.loan.plan.bean;

import java.util.HashMap;
import java.util.List;
/**
 * ������ PlanBeans
 * ������
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
