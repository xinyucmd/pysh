/**
 * Copyright (C) DXHM 版权所有
 * 文件名： DayRateServices.java
 * 包名： com.dx.loan.dayrate.services.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-6-11 下午05:09:27
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
 * 类名： DayRateServices
 * 描述：
 * @author 乾之轩
 * @date 2012-6-11 下午05:09:27
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
 * 方法描述： 根据借据号和发生日期获得日终实体
 * @param dueNo
 * @param occureDate
 * @return
 * RatedayBean
 * @author 乾之轩
 * @date 2012-6-12 下午08:00:31
 */
	public RatedayBean getRateDay(RatedayBean ratedayBean) {
		return dayRateDao.getRateDay(ratedayBean);
	}
/**
 * 
 * 方法描述： 保存日终计算 
 * @param rateDayBean
 * void
 * @author 乾之轩
 * @date 2012-6-20 上午10:45:16
 */
	public void saveRateDayBean(RatedayBean rateDayBean) {
		// TODO Auto-generated method stub
		//log.error("借据号:"+rateDayBean.getDueNo()+"#期号"+rateDayBean.getTermNo()+"#发生日期"+rateDayBean.getOccureDate());
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
	 * 方法描述： 获得逾期的列表(参数根据业务设定)
	 * @return
	 * List<RatedayBean>
	 * @author 乾之轩
	 * @date 2012-6-20 上午10:46:18
	 */
	public List<RatedayBean> getOverPlan(RatedayBean rateDayBean){
		return  dayRateDao.getOverPlan(rateDayBean);
	}
	
	/**
	 * 方法描述：获得期号小于当前期且还没有还完款的日终列表
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
	 * 功能:更新借据逾期天数
	 * @param parmMap
	 */
	public void upLn(Map<String,String> parmMap){
		dayRateDao.upLn(parmMap);
	}
	
	/**
	 * 功能:更新危机分类逾期天数
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
