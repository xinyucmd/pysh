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
	 * ���������� 
	 * @param dueno
	 * @return
	 * RatedayBean
	 * @author Ǭ֮��
	 * @date 2012-5-16 ����02:06:21
	 */
	public RepayBean getRepayBean(RepayBean repayBean);
	/**
	 * 
	 * ���������� ������������б�
	 * @param pageBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-16 ����02:06:24
	 */
	public   String getRepayList(PageBean pageBean); 
	
	
	/**
	 * 
	 * �������������ĳ�ڻ���ƻ�ʣ��ı���   
	 * @param dueNo
	 * @param termNo
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-23 ����06:01:19
	 */
	 public  String  remaCapital(PlanBean planBean);
	/**
	 * 
	 * ���������� ����������ļ�
	 * @param acLnMstBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-30 ����07:01:28
	 */
	 public void saveAcLnMstBean(AcLnMstBean acLnMstBean);
	 /**
	  * 
	  * ���������� 
	  * @param acLnMstBean
	  * void
	  * @author Ǭ֮��
	  * @date 2012-6-19 ����04:56:17
	  */
	 public AcLnMstBean getAcLnMstBean(AcLnMstBean acLnMstBean);
	 
	 
	 public List<AcLnMstBean> getAcLnMstBeans(AcLnMstBean acLnMstBean);
	 
	 /**
	  * 
	  * ���������� �����Ϣ��(������Ϣ,������Ϣ,������Ϣ) 
	  * void
	  * @author Ǭ֮��
	  * @date 2012-6-13 ����04:30:07
	  */
	 public String getTotalInterest(RatedayBean ratedayBean);
	 
	/**
	 * 
	 * ���������� ���ݽ�ݺź��ںŻ�û�����ʷ
	 * @param dueNo
	 * @param termNo
	 * @return
	 * List<RepayBean>
	 * @author Ǭ֮��
	 * @date 2012-6-15 ����11:10:07
	 */
	 public List<RepayBean> getRepayBeans(RepayBean repayBean);
	 public List<RepayBean> getRepayBeanss(RepayBean repayBean);
	 /**
	  * 
	  * ���������� ���滹�� 
	  * @param repayBean
	  * void
	  * @author Ǭ֮��
	  * @date 2012-6-15 ����02:58:44
	  */
	 public void  saveRepay(RepayBean repayBean);
	 
	 /**
	  * 
	  * ���������� ��ǰ����ҵ���� 
	  * void
	  * @author Ǭ֮��
	  * @date 2012-6-18 ����11:03:33
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
	  * ����: ���½�ݱ�
	  * @param repayBean
	  */
	 public void upLnDue(Map<String,String> parmMap);
	 public List<AcLnMstBean> getAcLnMstBeansByReportPolicy(AcLnMstBean parmAcLnMstBean);
}
