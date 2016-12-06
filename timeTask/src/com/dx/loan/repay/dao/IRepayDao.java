package com.dx.loan.repay.dao;

import java.util.List;
import java.util.Map;

import app.batch.entity.CustRepayDetails;

import com.dx.common.bean.PageBean;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.CifBean;
import com.dx.loan.repay.bean.DueBean;
import com.dx.loan.repay.bean.LnDue;
import com.dx.loan.repay.bean.RepayBean;

public interface IRepayDao {
	/**
	 * �������������ݷ�ҳʵ���ȡ���������
	 * 
	 * @param pageBean
	 * @return List<RepayBean>
	 * @author sll
	 * @date 2012-05-15
	 **/
	public List<RepayBean> getRepayList(PageBean pageBean);

	/**
	 * 
	 * ���������� ���ݽ�ݱ�Ż������ʵ��
	 * 
	 * @param dueNo
	 * @return AcLnMstBean
	 * @author sll
	 * @date May 15, 2012 3:34:35 PM
	 */
	public AcLnMstBean getAcLnMstBeanByDueNo(String dueNo);

	/**
	 * 
	 * ���������� ���ݿͻ���Ż�ÿͻ�ʵ��
	 * 
	 * @param cifNo
	 * @return CifBean
	 * @author sll
	 * @date May 15, 2012 11:19:04 AM
	 */
	public CifBean getCifBeanByNO(String cifNo);

	/**
	 * 
	 * ���������� ���ݽ�ݺź��ںŲ�ѯ����ʵ��
	 * 
	 * @param dueNo
	 *            ��ݺ�
	 * @param termNo
	 *            �ں�
	 * @return RatedayBean
	 * @author Ǭ֮��
	 * @date 2012-5-16 ����03:04:43
	 */
	public RatedayBean getRateDay(String dueNo, String termNo);

	public List<AcLnMstBean> getAcLnMstList(AcLnMstBean parmAcLnMstBean);

	/**
	 * 
	 * ���������� ����������ļ�
	 * 
	 * @param acLnMstBean
	 *            void
	 * @author Ǭ֮��
	 * @date 2012-5-30 ����06:50:32
	 */
	public void saveAcLnMstBean(AcLnMstBean acLnMstBean);

	/**
	 * 
	 * ���������� ���´������ļ�(��������ҵ���������)
	 * 
	 * @param acLnMstBean
	 *            void
	 * @author Ǭ֮��
	 * @date 2012-6-19 ����04:47:31
	 */
	public void updateAcLnMstBean(AcLnMstBean acLnMstBean);

	/**
	 * 
	 * ���������� ��ô������ļ�ʵ��(��������ҵ���������)
	 * 
	 * @param acLnMstBean
	 *            void
	 * @author Ǭ֮��
	 * @date 2012-6-19 ����05:05:59
	 */
	public AcLnMstBean getAcLnMstBean(AcLnMstBean acLnMstBean);

	/**
	 * 
	 * ���������� ��û�����ʷ
	 * 
	 * @param repayBean
	 *            ��������Լ��趨
	 * @return List<RepayBean>
	 * @author Ǭ֮��
	 * @date 2012-6-15 ����11:19:13
	 */
	public List<RepayBean> getRepayBeans(RepayBean repayBean);

	/**
	 * 
	 * ���������� ���滹����ʷ
	 * 
	 * @param repayBean
	 *            void
	 * @author Ǭ֮��
	 * @date 2012-6-15 ����02:53:24
	 */
	public void saveRepay(RepayBean repayBean);

	/**
	 * 
	 * ���������� ���滹��
	 * 
	 * @param repayBeans
	 *            void
	 * @author Ǭ֮��
	 * @date 2012-6-21 ����12:01:09
	 */
	public void saveRepay(List<RepayBean> repayBeans);

	/**
	 * ��û���ʵ��(����ҵ���趨����)
	 * 
	 * @param ratedayBean
	 */
	public RepayBean getRepayBean(RepayBean repayBean);

	public List<RepayBean> getDistinctRepayBeans(RepayBean repayBean);

	public LnDue getDueBean(LnDue dueBean);
	
	public List<LnDue> getDueList(LnDue dueBean);

	public List<PlanBean> getPlanBeans(String currDate);

	public List<RepayBean> getRepayBeansOnSchedule(String dueNo);

	public List<PlanBean> getPlanBeanList(PlanBean planBean);

	public List<RepayBean> getRepayBeanss(RepayBean repayBean);

	public void updateREpayDetails(CustRepayDetails custRepayDetails);

	public void insertREpayDetails(CustRepayDetails custRepayDetails);

	public List<CustRepayDetails> findCustRepayDetailsList(
			CustRepayDetails custRepayDetails);

	public List<RepayBean> getLastRepayList();

	public void upLnDue(Map<String, String> parmMap);

	public void setcollect(LnDue due);

	public List<AcLnMstBean> getAcLnMstBeansByReportPolicy(AcLnMstBean parmAcLnMstBean);

}
