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
	 * 方法描述：根据分页实体获取正常还款集合
	 * 
	 * @param pageBean
	 * @return List<RepayBean>
	 * @author sll
	 * @date 2012-05-15
	 **/
	public List<RepayBean> getRepayList(PageBean pageBean);

	/**
	 * 
	 * 方法描述： 根据借据编号获得利率实体
	 * 
	 * @param dueNo
	 * @return AcLnMstBean
	 * @author sll
	 * @date May 15, 2012 3:34:35 PM
	 */
	public AcLnMstBean getAcLnMstBeanByDueNo(String dueNo);

	/**
	 * 
	 * 方法描述： 根据客户编号获得客户实体
	 * 
	 * @param cifNo
	 * @return CifBean
	 * @author sll
	 * @date May 15, 2012 11:19:04 AM
	 */
	public CifBean getCifBeanByNO(String cifNo);

	/**
	 * 
	 * 方法描述： 根据借据号和期号查询日终实体
	 * 
	 * @param dueNo
	 *            借据号
	 * @param termNo
	 *            期号
	 * @return RatedayBean
	 * @author 乾之轩
	 * @date 2012-5-16 下午03:04:43
	 */
	public RatedayBean getRateDay(String dueNo, String termNo);

	public List<AcLnMstBean> getAcLnMstList(AcLnMstBean parmAcLnMstBean);

	/**
	 * 
	 * 方法描述： 保存贷款主文件
	 * 
	 * @param acLnMstBean
	 *            void
	 * @author 乾之轩
	 * @date 2012-5-30 下午06:50:32
	 */
	public void saveAcLnMstBean(AcLnMstBean acLnMstBean);

	/**
	 * 
	 * 方法描述： 更新贷款主文件(参数根据业务需求而定)
	 * 
	 * @param acLnMstBean
	 *            void
	 * @author 乾之轩
	 * @date 2012-6-19 下午04:47:31
	 */
	public void updateAcLnMstBean(AcLnMstBean acLnMstBean);

	/**
	 * 
	 * 方法描述： 获得贷款主文件实体(参数根据业务需求而定)
	 * 
	 * @param acLnMstBean
	 *            void
	 * @author 乾之轩
	 * @date 2012-6-19 下午05:05:59
	 */
	public AcLnMstBean getAcLnMstBean(AcLnMstBean acLnMstBean);

	/**
	 * 
	 * 方法描述： 获得还款历史
	 * 
	 * @param repayBean
	 *            具体参数自己设定
	 * @return List<RepayBean>
	 * @author 乾之轩
	 * @date 2012-6-15 上午11:19:13
	 */
	public List<RepayBean> getRepayBeans(RepayBean repayBean);

	/**
	 * 
	 * 方法描述： 保存还款历史
	 * 
	 * @param repayBean
	 *            void
	 * @author 乾之轩
	 * @date 2012-6-15 下午02:53:24
	 */
	public void saveRepay(RepayBean repayBean);

	/**
	 * 
	 * 方法描述： 保存还款
	 * 
	 * @param repayBeans
	 *            void
	 * @author 乾之轩
	 * @date 2012-6-21 下午12:01:09
	 */
	public void saveRepay(List<RepayBean> repayBeans);

	/**
	 * 获得还款实体(根据业务设定参数)
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
