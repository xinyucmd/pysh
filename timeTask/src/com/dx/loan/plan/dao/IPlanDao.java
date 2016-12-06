/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� IPlanDao.java
 * ������ com.dx.loan.plan.dao
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-26 ����03:21:41
 * @version V1.0
 */ 
package com.dx.loan.plan.dao;

import java.util.List;

import com.dx.loan.plan.bean.PlanBean;

/**
 * ������ IPlanDao
 * ������
 * @author Ǭ֮��
 * @date 2012-5-26 ����03:21:41
 *
 *
 */
public interface IPlanDao {
	/**
	 * 
	 * ���������� ��û�û�л����Ļ���ƻ�(0 ���� 1���� 2 �����)
	 * @return
	 * List<PlanBean>
	 * @author Ǭ֮��
	 * @date 2012-6-11 ����04:42:45
	 */
	public List<PlanBean> getNormalPlanList(String systemDate);

	public List<PlanBean> getOverPlanList();
	
	/**
	 * 
	 * ���������� ���ݽ�ݺź��ںŸ��»���ƻ�
	 * @param planBean
	 * void   int
	 * @author Ǭ֮��
	 * @date 2012-5-17 ����09:09:46
	 */
	public int updatePlan(PlanBean planBean);
	/**
	 * 
	 * ���������� ���滹��ƻ� 
	 * @param planBeans
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-5 ����07:53:48
	 */
	public void savePlanList(List<PlanBean> planBeans);
	
	/**
	 * 
	 * ���������� ɾ������ƻ� (�����������ʵ��ҵ�����) 
	 * @param planBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-18 ����10:12:32
	 */
	public void delPlan(PlanBean planBean);
	/**
	 * 
	 * ���������� �������»���ƻ���״̬��Ƿ���־ 
	 * @param state
	 * @param isDebt
	 * @param planBeans
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-22 ����09:43:37
	 */
	public void updatePlanList(String state,String isDebt,String dueNo,List<PlanBean> planBeans);  
	
	
	public PlanBean getPlanBean(PlanBean parmPlanBean);
	
	
	public List<PlanBean> getPlanBeanList(PlanBean parmPlanBean);
	
	
	

}
