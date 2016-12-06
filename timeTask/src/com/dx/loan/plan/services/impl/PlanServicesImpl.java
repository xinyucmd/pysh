/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PlanServicesImpl.java
 * 包名： com.dx.loan.plan.services.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-5-31 上午10:07:12
 * @version V1.0
 */ 
package com.dx.loan.plan.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ctc.wstx.util.DataUtil;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.StringUtil;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanParmBean;
import com.dx.loan.plan.dao.IPlanDao;
import com.dx.loan.plan.services.IPlanServices;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.services.impl.RepayService;

/**
 * 类名： PlanServicesImpl
 * 描述： 还款计划服务类
 * @author 乾之轩
 * @date 2012-5-31 上午10:07:12
 *
 *
 */
public class PlanServicesImpl implements IPlanServices {
	private IPlanDao planDao; 
	private RepayService repayService; 
	public void setPlanDao(IPlanDao planDao) {
		this.planDao = planDao;
	}
	
	public void setRepayService(RepayService repayService) {
		this.repayService = repayService;
	}
	/**
	 * 
	 * 方法描述： 生成还款计划 
	 * @param dueNo
	 * @return
	 * @author 乾之轩
	 * @date 2012-5-31 上午10:07:34
	 */
	public List<PlanBean> createPlan(String dueNo,PlanParmBean planParmBean) {
		List<PlanBean> planBeans = null;
		// 正常还款计划
		if(StringUtil.equals("0.00", planParmBean.getAdvaceAmt())){
			AcLnMstBean acLnMstBean = repayService.getAcLnMstBeanByDueNo(dueNo);
			PlanServices planServices = new PlanServices();
			planBeans = planServices.genePlan(acLnMstBean, planParmBean);
		}else{
		// 提前还款从新生成还款计划
			AcLnMstBean acLnMstBean = repayService.getAcLnMstBeanByDueNo(dueNo);
			acLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planParmBean.getAdvaceAmt()));
			planBeans = advacePlan(dueNo,planParmBean,acLnMstBean);
		}
		return planBeans;
	}
	/**
	 * 编辑还款计划
	 */
	public List<PlanBean> createPlan(List<PlanBean> planList,PlanParmBean planParmBean) {
		String dueNo = planList.get(0).getDueNo();
		AcLnMstBean acLnMstBean = repayService.getAcLnMstBeanByDueNo(dueNo);
		int size = planList.size();
		// 期数小于或等于被调整还款计划期数的还款计划的本金和
		String dueBal = "0.00";
		// 保存调整期数之前的还款计划
		List<PlanBean> oldPlanList = new ArrayList<PlanBean>();
		// 判断还款计划的调整方式 0 调整本金 1 调整还款计划的结束日期
		String adjustType = planParmBean.getAdjustType();
		// 从第几期开始调整
		Integer adjustTerm =  Integer.parseInt(planParmBean.getTermNo());
		// 调整期还款计划
		PlanBean adjustPlanBean = planList.get(adjustTerm-1); 
		for(int i=0;i<adjustTerm;++i){
			PlanBean planBean  = planList.get(i);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
			oldPlanList.add(planBean);
			dueBal = BigNumberUtil.Add(dueBal,planBean.getReturnCapital());
		}
		// 从新设置还款计划的生成日期
		planParmBean.setBeginDate(adjustPlanBean.getEndDate());
		// 设置从新生成还款计划的开始期数
		planParmBean.setBeginTerm(String.valueOf(adjustTerm+1));
		// 设置还需要生成多少期还款计划
		int terms = planList.size()-adjustTerm;
		planParmBean.setTerms(String.valueOf(terms));
		// 剩余本金
		String  remaDueBal = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), dueBal);
		// 设置每期的本金
		String returnCapital =  BigNumberUtil.Divide(remaDueBal, String.valueOf(terms), 2, "1");
		
		for(int i=adjustTerm+1;i<=size;++i){
			PlanBean planBean = planList.get(i-1);
			int[] monthAndDays = DateUtil.getMonthsAndDays(planBean.getBegDate(),planBean.getEndDate());
			// 不是整月,用日利率计算(适用于江川金融)
			if(monthAndDays[1]>0){
				// 还款技计划期限
				int days = monthAndDays[0]*30+monthAndDays[1];
				// 将月利率转化为日利率
				String dayRate = BigNumberUtil.Divide(acLnMstBean.getLnRate(), "30", 10, "1");
				dayRate = BigNumberUtil.Divide(dayRate, "1000", 10, "1");
				// 剩余本金乘以期限天
				String interest = BigNumberUtil.Multiply(remaDueBal, String.valueOf(days));
				// 剩余本金乘以期限天乘以日利率
				interest = BigNumberUtil.Multiply(interest, dayRate);
				interest = BigNumberUtil.Divide(interest, "1", 2, "1");
				planBean.setReturnCapital(returnCapital);
				planBean.setReturnInterest(interest);
				planBean.setTermNo(String.valueOf(i));
				planBean.setTotal(BigNumberUtil.Add(interest, returnCapital));
				oldPlanList.add(planBean);	
				
				remaDueBal = BigNumberUtil.Subtract(remaDueBal, returnCapital);
			}else{
				// 整月使用月利率	
				// 剩余本金乘以期限月
				String interest = BigNumberUtil.Multiply(remaDueBal, String.valueOf(monthAndDays[0]));
				// 剩余本金乘以期限月乘以月利率
				interest = BigNumberUtil.Multiply(interest,acLnMstBean.getLnRate());
				interest  = BigNumberUtil.Divide(interest , "1000",2, "1");
				planBean.setReturnCapital(returnCapital);
				planBean.setReturnInterest(interest);
				planBean.setTermNo(String.valueOf(i));
				planBean.setTotal(BigNumberUtil.Add(interest, returnCapital));
				oldPlanList.add(planBean);	
				remaDueBal = BigNumberUtil.Subtract(remaDueBal, returnCapital);
			}
		}
		Collections.sort(oldPlanList);
		return oldPlanList;
	}
	
	/**
	 * 根据日期进行还款计划的调整
	 * @param planList
	 * @param planParmBean
	 * @return
	 */
	public List<PlanBean> createPlan1(List<PlanBean> planList,PlanParmBean planParmBean) {
		String dueNo = planList.get(0).getDueNo();
		AcLnMstBean acLnMstBean = repayService.getAcLnMstBeanByDueNo(dueNo);
		int size = planList.size();
		// 期数小于或等于被调整还款计划期数的还款计划的本金和
		String dueBal = "0.00";
		// 保存调整期数之前的还款计划
		List<PlanBean> oldPlanList = new ArrayList<PlanBean>();
		// 判断还款计划的调整方式 0 调整本金 1 调整还款计划的结束日期
		String adjustType = planParmBean.getAdjustType();
		// 从第几期开始调整
		Integer adjustTerm =  Integer.parseInt(planParmBean.getTermNo());
		// 调整期还款计划
		PlanBean adjustPlanBean = planList.get(adjustTerm-1); 
		
		// 被调整期的结束日期 
		String adjustEndDate = "";
		String tempDueBal = acLnMstBean.getDueBal();
		for(int i=0;i<adjustTerm;++i){
			PlanBean planBean  = planList.get(i);
			int[] monthAndDays = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
			if(monthAndDays[1]>0){
				// 不是整月使用日利率计算
				int days = monthAndDays[1]*30+monthAndDays[1];
				// 将月利率转化为日利率
				String dayRate = BigNumberUtil.Divide(acLnMstBean.getLnRate(), "30", 10, "1");
				dayRate = BigNumberUtil.Divide(dayRate, "1000", 10, "1");
				// 剩余本金乘以期限天
				String interest = BigNumberUtil.Multiply(tempDueBal, String.valueOf(days));
				// 剩余本金乘以期限天乘以日利率
				interest = BigNumberUtil.Multiply(interest, dayRate);
				interest = BigNumberUtil.Divide(interest, "1", 2, "1");
				planBean.setReturnInterest(interest);
				planBean.setTermNo(String.valueOf(i));
				planBean.setTotal(BigNumberUtil.Add(interest, planBean.getReturnCapital()));
				oldPlanList.add(planBean);	
				tempDueBal = BigNumberUtil.Subtract(tempDueBal, planBean.getReturnCapital());
				adjustEndDate = planBean.getEndDate();
				dueBal = BigNumberUtil.Add(dueBal,planBean.getReturnCapital());
			}else{
				// 整月使用月利率计算
				// 剩余本金乘以期限月
				String interest = BigNumberUtil.Multiply(tempDueBal, String.valueOf(monthAndDays[0]));
				// 剩余本金乘以期限月乘以月利率
				interest = BigNumberUtil.Multiply(interest,acLnMstBean.getLnRate());
				interest  = BigNumberUtil.Divide(interest , "1000",2, "1");
				planBean.setReturnInterest(interest);
				planBean.setTermNo(String.valueOf(i));
				planBean.setTotal(BigNumberUtil.Add(interest, planBean.getReturnCapital()));
				oldPlanList.add(planBean);	
				tempDueBal = BigNumberUtil.Subtract(tempDueBal, planBean.getReturnCapital());
				adjustEndDate = planBean.getEndDate();
				dueBal = BigNumberUtil.Add(dueBal,planBean.getReturnCapital());
			}
		}
		// 从调整期的结束日期到借据结束日期还剩多少期还款计划
		int[] monthAndDays = DateUtil.getMonthsAndDays(adjustEndDate, acLnMstBean.getDueEndDate());
		// 剩余的期数
		int terms = monthAndDays[0];
		if(monthAndDays[1]>0){
			terms = terms+1;
		}
		// 剩余本金
		String  remaDueBal = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), dueBal);
		int termNo = adjustTerm+1;
		for(int i=0;i<terms;++i){
			PlanBean planBean = planList.get(termNo);
			planBean.setTermNo(String.valueOf(termNo));
			planBean.setBegDate(adjustEndDate);
			String endDate = DateUtil.addByMonDay(adjustEndDate,1, 0, DateUtil.DATE_FORMAT_);
			planBean.setEndDate(endDate);
			String interest =  BigNumberUtil.Multiply(remaDueBal, acLnMstBean.getLnRate());
			interest =  BigNumberUtil.Divide(interest , "1000", 2, "1");
			planBean.setTotal(BigNumberUtil.Add(interest,planBean.getReturnCapital())); 
			oldPlanList.add(planBean);
			adjustEndDate = endDate;
			termNo = termNo+1;
		}
		
		return oldPlanList;
	}
	
	
	/**
	 * 
	 * 方法描述： 保存还款计划 
	 * @param planBeans
	 * @author 乾之轩
	 * @date 2012-6-5 下午07:49:57
	 */
	public void savePlanList(List<PlanBean> planBeans) {
		planDao.savePlanList(planBeans);
	}
	
	/**
	 * 
	 * 方法描述： 提前还款从新生成还款计划
	 * @param dueNo
	 * @param planParmBean
	 * @param acLnMstBean
	 * @return
	 * List<PlanBean>
	 * @author 乾之轩
	 * @date 2012-6-18 上午10:39:43
	 */
	private List<PlanBean> advacePlan(String dueNo,PlanParmBean planParmBean,AcLnMstBean acLnMstBean){
		// 删除未还款的还款计划
		PlanBean planBean = new PlanBean();
		planBean.setDueNo(dueNo);
		planBean.setState("0");
		// 从新生新的还款计划
		planDao.delPlan(planBean);
		// 从新生成新的还款计划
		PlanServices planServices = new PlanServices();
		return planServices.genePlan(acLnMstBean, planParmBean);
	}
}
