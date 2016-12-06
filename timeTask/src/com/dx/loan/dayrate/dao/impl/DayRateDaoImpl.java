/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� DayRateDaoImpl.java
 * ������ com.dx.loan.dayrate.dao.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-11 ����05:12:12
 * @version V1.0
 */ 
package com.dx.loan.dayrate.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.dayrate.dao.IDayRateDao;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.payment.bean.RatedayBBean;

/**
 * ������ DayRateDaoImpl
 * ������
 * @author Ǭ֮��
 * @date 2012-6-11 ����05:12:12
 *
 *
 */
public class DayRateDaoImpl extends SqlMapClientDaoSupport implements IDayRateDao {
/**
 * 
 * ���������� ���ݽ�ݺźͷ������ڻ������ʵ�� 
 * @param dueNo
 * @param occureDate
 * @return
 * @author Ǭ֮��
 * @date 2012-6-12 ����07:56:46
 */
	public RatedayBean getRateDay(RatedayBean ratedayBean) {
		/*System.out.println(ratedayBean);
		System.out.println(1);*/
		return (RatedayBean)this.getSqlMapClientTemplate().queryForObject("getRateDay", ratedayBean);
	}
/**
 * 
 * ���������� �������ռ����� 
 * @param rateDayBean
 * @author Ǭ֮��
 * @date 2012-6-11 ����05:13:35
 */
	public void saveRateDayBean(RatedayBean rateDayBean) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().insert("saveRateDayBean",rateDayBean);
	}

	public void updateRateDay(RatedayBean ratedayBean) {
		this.getSqlMapClientTemplate().update("updateRateDay", ratedayBean);
	}
	/**
	 * 
	 * ����������  ����Ѿ����ڵĻ���
	 * @param ratedayBean
	 * @return
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����10:48:21
	 */
	@SuppressWarnings("unchecked")
	public List<RatedayBean> getOverPlan(RatedayBean ratedayBean) {
		return this.getSqlMapClientTemplate().queryForList("getOverPlans", ratedayBean);
	}
	
	/**
	 *  * ��������������ں�С�ڵ�ǰ���һ�û�л����������б�
	 */
	@SuppressWarnings("unchecked")
	public List<RatedayBean> getRateDayBeanList(RatedayBean ratedayBean) {
		return this.getSqlMapClientTemplate().queryForList("getRateDayList", ratedayBean);
	}
	@SuppressWarnings("unchecked")
	public List<RepayBean> getOverduePeriods(String deuno){
		return this.getSqlMapClientTemplate().queryForList("getOverduePeriod", deuno);
	}
	@SuppressWarnings("unchecked")
	public List<PlanBean> getOverdueplanBeans(PlanBean planparmbean){
		return this.getSqlMapClientTemplate().queryForList("getOverduePlanList", planparmbean);
	}
	
	/**
	 * ����:���½�ݱ����������
	 */
	public void upLn(Map<String, String> parmMap){
		 this.getSqlMapClientTemplate().update("upLn", parmMap);
	}
	
	/**
	 * ����:�����弶������������� 
	 */
	public void upFive(Map<String, String> parmMap) {
		 this.getSqlMapClientTemplate().update("upFive", parmMap);
	}
	public void saveRateDayBean(RatedayBBean ratedayBBean) {
		this.getSqlMapClientTemplate().insert("saveRateDayBBean",ratedayBBean);
		
	}
}
