/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� IDayRateServices.java
 * ������ com.dx.loan.dayrate.services
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-11 ����05:06:56
 * @version V1.0
 */ 
package com.dx.loan.dayrate.services;

import java.util.List;

import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.RepayBean;

/**
 * ������ IDayRateServices
 * ������
 * @author Ǭ֮��
 * @date 2012-6-11 ����05:06:56
 *
 *
 */
public interface IDayRateServices {
	/**
	 * 
	 * ����������  �������ռ�����
	 * @param rateDayBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-16 ����02:08:30
	 */
	public void  saveRateDayBean(RatedayBean rateDayBean );
	
	/**
	 * 
	 * ���������� ���ݽ�ݺź��ںŲ�ѯ����ʵ��
	 * @param dueNo ��ݺ�
	 * @param termNo �ں�
	 * @return
	 * RatedayBean
	 * @author Ǭ֮��
	 * @date 2012-5-16 ����03:04:43
	 */
	public    RatedayBean getRateDay(String dueNo,String termNo);

	/**
	 * 
	 * ���������� �������ս��
	 * @param ratedayBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-16 ����04:27:18
	 */
	public void updateRateDay(RatedayBean ratedayBean);
	public List<RepayBean> getOverduePeriods(String deuno);
	public List<PlanBean> getOverdueplanBeans(PlanBean planparmbean);
}
