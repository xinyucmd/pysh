package com.dx.loan.repay.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import app.batch.entity.CustRepayDetails;

import com.dx.common.SystemParm;
import com.dx.common.bean.PageBean;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.CifBean;
import com.dx.loan.repay.bean.DueBean;
import com.dx.loan.repay.bean.LnDue;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.dao.IRepayDao;

public class RepayDaoImpl extends SqlMapClientDaoSupport implements IRepayDao {

	/**
	 * ���ܣ�������������б� ���ߣ�sll ����ֵ�� ����ʵ���б� ���ڣ�2012-05-09
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<RepayBean> getRepayList(PageBean pageBean) {
		pageBean.setParm1(SystemParm.SystemDate);
		List<RepayBean> repayBeans = (List<RepayBean>) this
				.getSqlMapClientTemplate().queryForList("getRepayList",
						pageBean);
		pageBean.setTotalSize(getCount(pageBean));
		return repayBeans;
	}

	private String getCount(PageBean pageBean) {
		String size = (String) this.getSqlMapClientTemplate().queryForObject(
				"getRepayListSize", pageBean);
		return size;
	}

	/**
	 * 
	 * ���������� ���ݿͻ���Ż�ÿͻ�ʵ��
	 * 
	 * @param cifNo
	 * @return CifBean
	 * @author sll
	 * @date May 15, 2012 11:19:04 AM
	 */
	public CifBean getCifBeanByNO(String cifNo) {
		CifBean cifBean = (CifBean) this.getSqlMapClientTemplate()
				.queryForObject("getCifByNo", cifNo);
		return cifBean;
	}

	/**
	 * 
	 * ���������� ���ݽ�ݱ�Ż������ʵ��
	 * 
	 * @param dueNo
	 * @return AcLnMstBean
	 * @author sll
	 * @date May 15, 2012 3:34:35 PM
	 */
	public AcLnMstBean getAcLnMstBeanByDueNo(String dueNo) {
		AcLnMstBean acLnMstBean = (AcLnMstBean) this.getSqlMapClientTemplate()
				.queryForObject("getAcLnMstBeanByDueNo", dueNo);
		return acLnMstBean;
	}

	/**
	 * 
	 * ���������� ���ݽ�ݺź��ںŲ�ѯ����ʵ��
	 * 
	 * @param dueNo
	 * @param termNo
	 * @return RatedayBean
	 * @author Ǭ֮��
	 * @date 2012-5-16 ����03:03:50
	 */
	public RatedayBean getRateDay(String dueNo, String termNo) {
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("dueNo", dueNo);
		parmMap.put("termNo", termNo);
		return (RatedayBean) this.getSqlMapClientTemplate().queryForObject(
				"getRateDay", parmMap);
	}

	/**
	 * 
	 * ���������� ����������ļ�
	 * 
	 * @param acLnMstBean
	 * @author Ǭ֮��
	 * @date 2012-5-30 ����06:51:20
	 */
	public void saveAcLnMstBean(AcLnMstBean acLnMstBean) {
		this.getSqlMapClientTemplate().insert("saveAcLnMstBean", acLnMstBean);

	}

	/**
	 * 
	 * ���������� ��û�����ʷ
	 * 
	 * @param repayBean
	 *            ��������ҵ���Լ��趨
	 * @return
	 * @author Ǭ֮��
	 * @date 2012-6-15 ����11:20:57
	 */
	@SuppressWarnings("unchecked")
	public List<RepayBean> getRepayBeans(RepayBean repayBean) {
		return this.getSqlMapClientTemplate().queryForList("getRepayBeans",
				repayBean);
	}

	public List<RepayBean> getRepayBeanss(RepayBean repayBean) {
		return this.getSqlMapClientTemplate().queryForList("getRepayBeanss",
				repayBean);
	}

	/**
	 * 
	 * ���������� ���滹��
	 * 
	 * @param repayBean
	 * @author Ǭ֮��
	 * @date 2012-6-15 ����02:53:54
	 */
	public void saveRepay(RepayBean repayBean) {
		Object object = this.getSqlMapClientTemplate().insert("saveRepay",
				repayBean);
		// System.out.println(object.toString());
		System.out.println("ok");
		System.out.println("object=" + object);
	}

	/**
	 * 
	 * �������������´������ļ�(����������ø���ҵ���������)
	 * 
	 * @param acLnMstBean
	 * @author Ǭ֮��
	 * @date 2012-6-19 ����04:49:13
	 */
	public void updateAcLnMstBean(AcLnMstBean acLnMstBean) {
		this.getSqlMapClientTemplate().update("updateAcLnMstBean", acLnMstBean);
	}

	public AcLnMstBean getAcLnMstBean(AcLnMstBean acLnMstBean) {
		return (AcLnMstBean) this.getSqlMapClientTemplate().queryForObject(
				"getAcLnMstBean", acLnMstBean);
	}

	/**
	 * 
	 * ���������� ���滹��
	 * 
	 * @param repayBeans
	 * @author Ǭ֮��
	 * @date 2012-6-21 ����12:02:40
	 */
	public void saveRepay(List<RepayBean> repayBeans) {
		try {
			this.getSqlMapClient().startBatch();
			int i = 0;
			for (RepayBean repayBean : repayBeans) {
				// ÿ500������Ϊһ��
				i++;
				if (i == 500) {
					this.getSqlMapClient().executeBatch();
					this.getSqlMapClient().startBatch();
					i = 0;
				}
				this.getSqlMapClient().insert("saveRepayList", repayBean);
			}
			this.getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public RepayBean getRepayBean(RepayBean repayBean) {
		return (RepayBean) this.getSqlMapClientTemplate().queryForObject(
				"getRepayBean", repayBean);
	}

	public List<AcLnMstBean> getAcLnMstList(AcLnMstBean parmAcLnMstBean) {
		return this.getSqlMapClientTemplate().queryForList("getAcLnMstBean",
				parmAcLnMstBean);
	}

	public List<RepayBean> getDistinctRepayBeans(RepayBean repayBean) {
		return this.getSqlMapClientTemplate().queryForList(
				"getDistinctRepayBeans", repayBean);
	}

	public LnDue getDueBean(LnDue dueBean) {
		return (LnDue) this.getSqlMapClientTemplate().queryForObject(
				"getDueBean", dueBean);
	}

	public List<PlanBean> getPlanBeans(String currDate) {
		return this.getSqlMapClientTemplate().queryForList("getPlanBeans",
				currDate);
	}

	public List<RepayBean> getRepayBeansOnSchedule(String dueNo) {
		return this.getSqlMapClientTemplate().queryForList(
				"getRepayBeansOnSchedule", dueNo);
	}

	public List<PlanBean> getPlanBeanList(PlanBean planBean) {
		return this.getSqlMapClientTemplate().queryForList("getPlanBeanList",
				planBean);
	}

	public void updateREpayDetails(CustRepayDetails custRepayDetails) {
		getSqlMapClientTemplate().update("custRepayDetails.update",
				custRepayDetails);
	}

	public void insertREpayDetails(CustRepayDetails custRepayDetails) {
		getSqlMapClientTemplate().insert("insertCustRepayDetails",
				custRepayDetails);

	}

	public List<CustRepayDetails> findCustRepayDetailsList(
			CustRepayDetails custRepayDetails) {
		return this.getSqlMapClientTemplate().queryForList(
				"custRepayDetailsList", custRepayDetails);
	}

	public List<RepayBean> getLastRepayList() {
		return this.getSqlMapClientTemplate().queryForList("getLastRepayList");
	}

	public void upLnDue(Map<String, String> parmMap) {
		try {
			this.getSqlMapClientTemplate().update("upLnDue", parmMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<LnDue> getDueList(LnDue dueBean) {
		return (List<LnDue>) this.getSqlMapClientTemplate().queryForList(
				"lnduelist.getallforcollect", dueBean);
	}

	public void setcollect(LnDue due) {
		try {
			this.getSqlMapClientTemplate().update("upLnDue1", due);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<AcLnMstBean> getAcLnMstBeansByReportPolicy(AcLnMstBean parmAcLnMstBean) {
		return this.getSqlMapClientTemplate().queryForList("getAcLnMstBeansByReportPolicy", parmAcLnMstBean);
	}
}
