/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� DayRateServices.java
 * ������ com.dx.loan.dayrate.services.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-11 ����05:09:27
 * @version V1.0
 */ 
package com.dx.loan.dayrate.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.dayrate.dao.IDayRateDao;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.payment.bean.RatedayBBean;

/**
 * ������ DayRateServices
 * ������
 * @author Ǭ֮��
 * @date 2012-6-11 ����05:09:27
 *
 *
 */
public class DayRateServices {
	private Log log = LogFactory.getLog(getClass());
	private IDayRateDao dayRateDao;

	public void setDayRateDao(IDayRateDao dayRateDao) {
		this.dayRateDao = dayRateDao;
	}
/**
 * 
 * ���������� ���ݽ�ݺźͷ������ڻ������ʵ��
 * @param dueNo
 * @param occureDate
 * @return
 * RatedayBean
 * @author Ǭ֮��
 * @date 2012-6-12 ����08:00:31
 */
	public RatedayBean getRateDay(RatedayBean ratedayBean) {
		return dayRateDao.getRateDay(ratedayBean);
	}
/**
 * 
 * ���������� �������ռ��� 
 * @param rateDayBean
 * void
 * @author Ǭ֮��
 * @date 2012-6-20 ����10:45:16
 */
	public void saveRateDayBean(RatedayBean rateDayBean) {
		// TODO Auto-generated method stub
		//log.error("��ݺ�:"+rateDayBean.getDueNo()+"#�ں�"+rateDayBean.getTermNo()+"#��������"+rateDayBean.getOccureDate());
		dayRateDao.saveRateDayBean(rateDayBean);
	}

	public void updateRateDay(RatedayBean ratedayBean) {
		try{
			dayRateDao.updateRateDay(ratedayBean);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * ���������� ������ڵ��б�(��������ҵ���趨)
	 * @return
	 * List<RatedayBean>
	 * @author Ǭ֮��
	 * @date 2012-6-20 ����10:46:18
	 */
	public List<RatedayBean> getOverPlan(RatedayBean rateDayBean){
		return  dayRateDao.getOverPlan(rateDayBean);
	}
	
	/**
	 * ��������������ں�С�ڵ�ǰ���һ�û�л����������б�
	 * @return
	 */
	public List<RatedayBean> getRateDayBeanList(RatedayBean ratedayBean){
		
		return   dayRateDao.getRateDayBeanList(ratedayBean);
		
	} 
	public List<RepayBean> getOverduePeriods(String deuno){
		return dayRateDao.getOverduePeriods(deuno);
	}
	public List<PlanBean> getOverdueplanBeans(PlanBean planparmbean){
		
		return dayRateDao.getOverdueplanBeans(planparmbean);
	}
	
	
	/**
	 * ����:���½����������
	 * @param parmMap
	 */
	public void upLn(Map<String,String> parmMap){
		dayRateDao.upLn(parmMap);
	}
	
	/**
	 * ����:����Σ��������������
	 * @param parmMap
	 */
	public void upFive(Map<String,String> parmMap){
		dayRateDao.upFive(parmMap);
	}
	
	
	public  void upDayRateData(RatedayBean ratedayBean){
		dayRateDao.updateRateDay(ratedayBean);
	}
	public void saveRateDaybBean(RatedayBBean ratedayBBean) {
		dayRateDao.saveRateDayBean(ratedayBBean);
	}
	
	
	

}
