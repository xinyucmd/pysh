package com.dx.loan.repay.services;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import app.batch.entity.CustRepayDetails;

import com.dx.common.bean.PageBean;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.LnDue;
import com.dx.loan.repay.bean.RepayBean;
@WebService
public interface IRepayService {
	
	/**
	 * 
	 * 方法描述： 
	 * @param dueno
	 * @return
	 * RatedayBean
	 * @author 乾之轩
	 * @date 2012-5-16 下午02:06:21
	 */
	public RepayBean getRepayBean(RepayBean repayBean);
	/**
	 * 
	 * 方法描述： 获得正常还款列表
	 * @param pageBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-16 下午02:06:24
	 */
	public   String getRepayList(PageBean pageBean); 
	
	
	/**
	 * 
	 * 方法描述：获得某期还款计划剩余的本金   
	 * @param dueNo
	 * @param termNo
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-23 下午06:01:19
	 */
	 public  String  remaCapital(PlanBean planBean);
	/**
	 * 
	 * 方法描述： 保存贷款主文件
	 * @param acLnMstBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-30 下午07:01:28
	 */
	 public void saveAcLnMstBean(AcLnMstBean acLnMstBean);
	 /**
	  * 
	  * 方法描述： 
	  * @param acLnMstBean
	  * void
	  * @author 乾之轩
	  * @date 2012-6-19 下午04:56:17
	  */
	 public AcLnMstBean getAcLnMstBean(AcLnMstBean acLnMstBean);
	 
	 
	 public List<AcLnMstBean> getAcLnMstBeans(AcLnMstBean acLnMstBean);
	 
	 /**
	  * 
	  * 方法描述： 获得利息和(正常利息,逾期利息,复利利息) 
	  * void
	  * @author 乾之轩
	  * @date 2012-6-13 下午04:30:07
	  */
	 public String getTotalInterest(RatedayBean ratedayBean);
	 
	/**
	 * 
	 * 方法描述： 根据借据号和期号获得还款历史
	 * @param dueNo
	 * @param termNo
	 * @return
	 * List<RepayBean>
	 * @author 乾之轩
	 * @date 2012-6-15 上午11:10:07
	 */
	 public List<RepayBean> getRepayBeans(RepayBean repayBean);
	 public List<RepayBean> getRepayBeanss(RepayBean repayBean);
	 /**
	  * 
	  * 方法描述： 保存还款 
	  * @param repayBean
	  * void
	  * @author 乾之轩
	  * @date 2012-6-15 下午02:58:44
	  */
	 public void  saveRepay(RepayBean repayBean);
	 
	 /**
	  * 
	  * 方法描述： 提前还款业务处理 
	  * void
	  * @author 乾之轩
	  * @date 2012-6-18 上午11:03:33
	  */
	 public void saveAdvaceRepay(RepayBean repayBean);
	 public List<RepayBean> getDistinctRepayBeans(RepayBean repayBean);
	public LnDue getDueBean(LnDue dueBean);
	public List<PlanBean> getPlanBeans(String currDate);
	public List<RepayBean> getRepayBeansOnSchedule(String dueNo);
	public List<PlanBean> getPlanBeanList(PlanBean planBean);
	public void updateREpayDetails(CustRepayDetails custRepayDetails);
	public void insertREpayDetails(CustRepayDetails custRepayDetails);
	public List<CustRepayDetails> findCustRepayDetailsList(CustRepayDetails custRepayDetails);
	 
	
		 /**
	  * 
	  * @return
	  */
	 public List<RepayBean> getLastRepayList();
	 /**
	  * 功能: 更新借据表
	  * @param repayBean
	  */
	 public void upLnDue(Map<String,String> parmMap);
	 public List<AcLnMstBean> getAcLnMstBeansByReportPolicy(AcLnMstBean parmAcLnMstBean);
}
