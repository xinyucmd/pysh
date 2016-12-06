/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� IPlanServices.java
 * ������ com.dx.loan.plan.services
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-31 ����10:05:43
 * @version V1.0
 */ 
package com.dx.loan.plan.services;

import java.util.List;

import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanParmBean;

/**
 * ������ IPlanServices
 * ������
 * @author Ǭ֮��
 * @date 2012-5-31 ����10:05:43
 *
 *
 */
public interface IPlanServices {
	/**
	 * 
	 * ���������� ���ݽ�ݺźͻ���ƻ�����ʵ�����ɻ���ƻ�
	 * @param dueNo
	 * @param planParmBean
	 * @return
	 * List<PlanBean>
	 * @author Ǭ֮��
	 * @date 2012-6-5 ����07:42:39
	 */
	public List<PlanBean> createPlan(String dueNo,PlanParmBean planParmBean);
	/**
	 * �༭����ƻ�ʱ,�������л���ƻ��������ɻ���ƻ�
	 * @param planList
	 * @return
	 */
	public List<PlanBean> createPlan(List<PlanBean> planList,PlanParmBean planParmBean);
	
	
	/**
	 * 
	 * ���������� ���滹��ƻ� 
	 * @param planBeans
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-5 ����07:43:41
	 */
	public void savePlanList(List<PlanBean> planBeans);
	

}
