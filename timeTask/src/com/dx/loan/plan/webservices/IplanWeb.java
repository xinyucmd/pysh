/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� IplanWeb.java
 * ������ com.dx.loan.plan.webservices
 * ˵����
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
 * ������ IplanWeb
 * ������
 * @author songshengkai
 * @date Jun 18, 2012 9:50:08 AM
 *
 *
 */
@WebService
public interface IplanWeb {
	/**
	 * 
	 * ���������� ���ݽ�ݺźͻ���ƻ�����ʵ�����ɻ���ƻ�
	 * @param dueNo
	 * @param planParmBean
	 * @return
	 * PlanBeans
	 * @author songshengkai
	 * @date 2012-6-5 ����07:42:39
	 */
	@WebResult
	public PlanBeans createPlan(String dueNo,PlanParmBean planParmBean);
}
