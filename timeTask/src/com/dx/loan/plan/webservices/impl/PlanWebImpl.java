/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PlanWeb.java
 * ������ com.dx.loan.plan.webservices.impl
 * ˵����
 * @author songshengkai
 * @date Jun 18, 2012 9:52:59 AM
 * @version V1.0
 */ 
package com.dx.loan.plan.webservices.impl;

import javax.jws.WebService;
import com.dx.loan.plan.bean.PlanBeans;
import com.dx.loan.plan.bean.PlanParmBean;
import com.dx.loan.plan.services.IPlanServices;
import com.dx.loan.plan.services.impl.PlanServicesImpl;
import com.dx.loan.plan.webservices.IplanWeb;
/**
 * ������ PlanWeb
 * ������
 * @author songshengkai
 * @date Jun 18, 2012 9:52:59 AM
 *
 *
 */
@WebService(endpointInterface = "com.dx.loan.plan.webservices.IplanWeb")//
public class PlanWebImpl implements IplanWeb {

	public PlanBeans createPlan(String dueNo, PlanParmBean planParmBean) {
		IPlanServices plan= new PlanServicesImpl();
		PlanBeans planBeans=new PlanBeans();
		planBeans.setPlanUsers(plan.createPlan(dueNo,planParmBean));
		return planBeans;
	}
}
