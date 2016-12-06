/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� IDayRateDao.java
 * ������ com.dx.loan.dayrate.dao
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-11 ����05:11:47
 * @version V1.0
 */ 
package com.dx.loan.dayrate.dao;

import java.util.List;
import java.util.Map;

import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.payment.bean.RatedayBBean;

/**
 * ������ IDayRateDao
 * ������
 * @author Ǭ֮��
 * @date 2012-6-11 ����05:11:47
 *
 *
 */
public interface IDayRateDao {
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
	public    RatedayBean getRateDay(RatedayBean rateDayBean);

	/**
	 * 
	 * ���������� �������ս��
	 * @param ratedayBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-16 ����04:27:18
	 */
	public void updateRateDay(RatedayBean ratedayBean);
	
	/**
	 * 
	 * ���������� ����Ѿ����ڵĻ��� 
	 * @param ratedayBean
	 * @return
	 * List<RatedayBean>
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����10:47:43
	 */
	public List<RatedayBean> getOverPlan(RatedayBean ratedayBean);
	/**
	 *  * ��������������ں�С�ڵ�ǰ���һ�û�л����������б�
	 * @param ratedayBean
	 * @return
	 */
	public List<RatedayBean> getRateDayBeanList(RatedayBean ratedayBean);
	
	public List<RepayBean> getOverduePeriods(String deuno);
	public List<PlanBean> getOverdueplanBeans(PlanBean planparmbean);
	
	
	/**
	 * ����:���½�ݵ���������  
	 * @param parmMap
	 */
	
	public void  upLn(Map<String,String> parmMap);  
	
	/**
	 * ����:���½�ݵ���������  
	 * @param parmMap
	 */
	
	public void  upFive(Map<String,String> parmMap);

	public void saveRateDayBean(RatedayBBean ratedayBBean);
  	
	
	
	
	
	
	

}
