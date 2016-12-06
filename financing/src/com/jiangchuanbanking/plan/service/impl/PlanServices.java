package com.jiangchuanbanking.plan.service.impl;

/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PlanServices.java
 * 包名： com.dx.loan.plan.services.impl
 * @version V1.0
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jiangchuanbanking.plan.dao.IPlanDao;
import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.util.BigNumberUtil;
import com.jiangchuanbanking.util.DateUtil;
import com.jiangchuanbanking.util.Interest;
import com.jiangchuanbanking.util.StringUtil;
import com.jiangchuanbanking.util.SystemParm;

/**
 * 类名： PlanServices 描述：
 * 
 * @author 乾之轩
 * @date 2012-5-26 上午10:20:15
 * 
 * 
 */
public class PlanServices {
	private IPlanDao planDao;

	public void setPlanDao(IPlanDao planDao) {
		this.planDao = planDao;
	}

	private Double balance = 0.0;

	/**
	 * 
	 * 方法描述： 根据借据信息生成还款计划
	 * 
	 * @param dueBean
	 * @param begDate
	 *            (生成还款计划的日期,默认为借据的开始日期)
	 * @author 乾之轩
	 * @date 2012-5-24 上午09:23:52
	 */
	public List<PlanBean> genePlan(PactInfo pactInfo) {
		List<PlanBean> planBeans = null;
		String returnMethod = pactInfo.getPayment_type();
		
		// 利随本清还款计划
		if (StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR,
				returnMethod)) {
			planBeans = genePlan_1(pactInfo);
		}

		// 先息后本
		if (StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, returnMethod)) {
			planBeans = genePlan_3(pactInfo);
		}

		// 等额本金还款计划
		if (StringUtil.equals(SystemParm.RETURNMETHOD_PRINCIPAL, returnMethod)) {
			planBeans = genePlan_5(pactInfo);
		}

		// 等额本息还款计划
		if (StringUtil.equals(SystemParm.RETURNMETHOD_INTEREST, returnMethod)) {
			planBeans = genePlan_6(pactInfo);
		}
		return planBeans;
	}

	/************************** 生成还款计划部分开始 ***********************/
	/**
	 * 
	 * 方法描述： 利随本清还款计划 void
	 * 
	 * @author 乾之轩
	 * @date 2012-5-24 上午09:48:46
	 */
	private List<PlanBean> genePlan_1(PactInfo pactInfo) {
		balance = pactInfo.getPact_amt();
		List<PlanBean> planList = new ArrayList<PlanBean>();
		Interest interest = new Interest();
		PlanBean planBean = new PlanBean();
		planBean.setBeg_date(pactInfo.getBeginDate());
		planBean.setDue_bal(pactInfo.getDue_bal());
		// 使用闭区间
		if (SystemParm.PLAN_END == 1) {
			String planEndDate = DateUtil.subtDays(pactInfo.getEndDate(), "1");
			planBean.setEnd_date(planEndDate);
		}
		// 使用半开半闭区间
		if (SystemParm.PLAN_END == 0) {
			planBean.setEnd_date(pactInfo.getEndDate());
		}
		planBean.setPact_no(pactInfo.getPact_no());
		planBean.setCapital(String.valueOf(pactInfo.getPact_amt()));
		planBean.setIf_alarm("0");
		planBean.setIf_settle("0");
		planBean.setTerm("1");
		planBean.setInterest(interest.getInterest(planBean, pactInfo));
		planBean.setShould_amt(BigNumberUtil.add2String(
				String.valueOf(pactInfo.getPact_amt()),
				interest.getInterest(planBean, pactInfo)));
		
		planList.add(planBean);
		return planList;
	}

	/**
	 * 
	 * 方法描述： 先息后本
	 * 
	 * @param dueBean
	 * @param planParmBean
	 *            void
	 * @author 乾之轩
	 * @date 2012-5-24 下午06:43:37
	 */
	private List<PlanBean> genePlan_3(PactInfo pactInfo) {
		Interest interest = new Interest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// 总期数
		int terms = Integer.parseInt(defaultTerm(pactInfo));
		// 还款计划第一期开始日期
		String planBegDate = pactInfo.getBeginDate();
		int termNo = 0;
		int returnday = DateUtil.getDay(planBegDate);
		for (int i = 0; i < terms; ++i) {
			PlanBean planBean = new PlanBean();
			// 处理二月情况
			if (StringUtil.contains(planBegDate, "-02-29")
					|| StringUtil.contains(planBegDate, "-02-30")
					|| StringUtil.contains(planBegDate, "-02-31")) {
				if (DateUtil.isLeapYear(planBegDate.substring(0, 8) + "01")
						&& returnday > 29) {
					String tempDate = planBegDate.substring(0, 8);
					planBean.setBeg_date(tempDate + "29");
					planBegDate = tempDate + "29";
				} else {
					String tempDate = planBegDate.substring(0, 8);
					planBean.setBeg_date(tempDate + "28");
					planBegDate = tempDate + "28";
				}
			} else {
				planBean.setBeg_date(planBegDate);
			}

			String planEndDate = DateUtil.addByMonDay(planBegDate, 1, 0,
					DateUtil.DATE_FORMAT_);

			if ((returnday > 28 || returnday == 28)
					&& DateUtil.getMonth(planEndDate) != 2) {
				int month = DateUtil.getMonth(planEndDate);
				if ((month == 4 || month == 6 || month == 9 || month == 11)
						&& returnday == 31) {
					planEndDate = planEndDate.substring(0, 8) + "30";
				} else {
					planEndDate = planEndDate.substring(0, 8) + returnday;
				}
			}

			// 还款计划为闭区间(如2012-05-25--2012-05-24)
			if (SystemParm.PLAN_END == 1) {
				planBegDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
			if (SystemParm.PLAN_END == 0) {
				planBegDate = planEndDate;
			}
			planBean.setTerm(String.valueOf(termNo + 1));
			if (i == terms - 1) {
				planBean.setCapital(String.valueOf(pactInfo.getPact_amt()));
				// 最后一期使用闭区间
				if (SystemParm.LAST_TERM_OPEN == 0) {
					// planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(),
					// "1"));
					planBean.setEnd_date(pactInfo.getEndDate());
				}
				// 最后一期使用开区间
				if (SystemParm.LAST_TERM_OPEN == 1) {
					// planBean.setEndDate(acLnMstBean.getDueEndDate());
					planBean.setEnd_date(pactInfo.getEndDate());
				}
				planBean.setEnd_date(pactInfo.getEndDate());
			} else {
				planBean.setCapital("0.00");
				planBean.setEnd_date(planEndDate);
			}
			planBean.setPact_no(pactInfo.getPact_no());
			planBean.setIf_settle("0");
			planBean.setIf_alarm("0");
			planBean.setInterest(interest.getInterest(planBean, pactInfo));
			planBean.setShould_amt(BigNumberUtil.Add(planBean.getCapital(),
					planBean.getInterest()));
			termNo++;
			planBeans.add(planBean);
		}

		// 将列表按期号排序
		Collections.sort(planBeans);
		// 修改期号
		if (StringUtil.equals("0", planBeans.get(0).getTerm())) {
			int k = 1;
			for (PlanBean plan : planBeans) {
				plan.setTerm(String.valueOf(k));
				k = k + 1;
			}
		}

		return planBeans;
	}

	/**
	 * 
	 * 方法描述： 一次偿还本金按季结息
	 * 
	 * @param dueBean
	 * @param planParmBean
	 * @return List<PlanBean>
	 * @author 乾之轩
	 * @date 2012-5-25 上午09:11:32
	 */
	// private List<PlanBean> genePlan_4(AcLnMstBean acLnMstBean,PlanParmBean
	// planParmBean){
	// Interest interest = new Interest();
	// List<PlanBean> planBeans = new ArrayList<PlanBean>();
	// // 固定还款日时预先截取出来的(首期)的结束日期时在本月还是下一个月 0 本月 1 下一月
	// int advanceEndType = SystemParm.ADVANCE_END_TYPE;
	// // 固定还款日截取出来的一期的处理方式 0 在第一期收取 1 当做一期有本金 2 当做一期无本金
	// int dealAdvanceType = SystemParm.DEAL_ADVANCE_TYPE;
	// // 延期部分是否收取利息 0 不收取 1收取
	// int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
	// // 标示最后一期的利息是否需要从新计算
	// boolean rComLastTerm = false;
	// // 固定还款日时预先截取出来的(首期)的结束日期
	// String firEndDate="";
	// // 是否延期 只有选择固定还款日后该设置才能起作用 0 不延期 1 延期
	// int isDelay = SystemParm.IS_DELAY;
	// // 截取出来的一期的利息
	// String advaceInterest="0.00";
	// // 固定还款日
	// String fixDate = planParmBean.getFixDate();
	// // 延期后的结束日期
	// String delayDate="";
	// // 处理固定日还款
	// if(StringUtil.isNotEmpty(fixDate)){
	// int tempFixDate = Integer.parseInt(fixDate);
	// int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
	// // 结束日期在放款日所在月(当做一期并且没有本金不允许在下一个月)
	// if(advanceEndType==0 || dealAdvanceType==2){
	// if(putoutDay< tempFixDate){
	// String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0,
	// DateUtil.DATE_FORMAT_);
	// if(tempFixDate<10){
	// firEndDate = date.substring(0, date.length()-2)+"0"+fixDate;
	// }else{
	// firEndDate = date.substring(0, date.length()-2)+fixDate;
	// }
	// }else{
	// String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0,
	// DateUtil.DATE_FORMAT_);
	// if(tempFixDate<10){
	// firEndDate = date.substring(0, date.length()-2)+"0"+fixDate;
	// }else{
	// firEndDate = date.substring(0, date.length()-2)+fixDate;
	// }
	// }
	// }
	//
	// // 结束日期在放款日所在月的下一月
	// if(advanceEndType==1){
	// // 放款日期加一个月
	// String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0,
	// DateUtil.DATE_FORMAT);
	// if(tempFixDate<10){
	// firEndDate = date.substring(0, date.length()-2)+"0"+fixDate;
	// }else{
	// firEndDate = date.substring(0, date.length()-2)+fixDate;
	// }
	// }
	//
	//
	//
	// // 截取出来的一期的利息在第一期收取
	// if(dealAdvanceType==0){
	// PlanBean planBean = new PlanBean();
	// planBean.setBegDate(acLnMstBean.getDueBegDate());
	// planBean.setEndDate(firEndDate);
	// planBean.setDueBal(acLnMstBean.getDueBal());
	// advaceInterest= interest.getInterest(planBean,acLnMstBean);
	//
	// }
	//
	// // 截取出一期单独作为一期且有本金(一次偿还本金比较特殊他和当做一期无本金相同)
	// if(dealAdvanceType==1){
	// /**
	// PlanBean planBean = new PlanBean();
	// int firstDay = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(),
	// firEndDate);
	// // 获得借据期限天
	// int days = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(),
	// acLnMstBean.getDueEndDate());
	// String everyDayMoney = BigNumberUtil.Divide(acLnMstBean.getDueBal(),
	// String.valueOf(days), 2, "1");
	// String firstCapital = BigNumberUtil.Multiply(everyDayMoney,
	// String.valueOf(firstDay));
	// planBean.setBegDate(acLnMstBean.getDueBegDate());
	// planBean.setCifNo(acLnMstBean.getCifNo());
	// planBean.setDueAmt(acLnMstBean.getDueBal());
	// planBean.setDueBal(acLnMstBean.getDueBal());
	// planBean.setDueNo(acLnMstBean.getDueNo());
	// planBean.setEndDate(firEndDate);
	// planBean.setPactNo(acLnMstBean.getPactNo());
	// planBean.setReturnCapital(firstCapital);
	// planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
	// planBean.setState("0");
	// planBean.setTermNo("0");
	// planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
	// planBeans.add(planBean);
	// // 从新设置借据的余额
	// acLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(),
	// firstCapital));
	// **/
	//
	// PlanBean planBean = new PlanBean();
	// planBean.setBegDate(acLnMstBean.getDueBegDate());
	// planBean.setCifNo(acLnMstBean.getCifNo());
	// planBean.setDueAmt(acLnMstBean.getDueBal());
	// planBean.setDueBal(acLnMstBean.getDueBal());
	// planBean.setDueNo(acLnMstBean.getDueNo());
	// planBean.setEndDate(firEndDate);
	// planBean.setPactNo(acLnMstBean.getPactNo());
	// planBean.setReturnCapital("0.00");
	// planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
	// planBean.setState("0");
	// planBean.setTermNo("0");
	// planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
	// planBeans.add(planBean);
	//
	// }
	// // 单独作为一期并且没有本金
	// if(dealAdvanceType==2){
	// PlanBean planBean = new PlanBean();
	// planBean.setBegDate(acLnMstBean.getDueBegDate());
	// planBean.setCifNo(acLnMstBean.getCifNo());
	// planBean.setDueAmt(acLnMstBean.getDueBal());
	// planBean.setDueBal(acLnMstBean.getDueBal());
	// planBean.setDueNo(acLnMstBean.getDueNo());
	// planBean.setEndDate(firEndDate);
	// planBean.setPactNo(acLnMstBean.getPactNo());
	// planBean.setReturnCapital("0.00");
	// planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
	// planBean.setState("0");
	// planBean.setTermNo("0");
	// planBeans.add(planBean);
	// }
	//
	// // 如果延期设置延期后的结束日期
	// if(isDelay==1){
	// // 获得借据的结束日
	// int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
	// // 延期后的结束日期还在当前月
	// if(endDay<tempFixDate){
	// String date =
	// acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
	// if(tempFixDate<10){
	// delayDate = date+"0"+tempFixDate;
	// }else{
	// delayDate = date+"0"+tempFixDate;
	// }
	// }else{
	// // 延期后的结束日期做在下一个月
	// String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0,
	// DateUtil.DATE_FORMAT_);
	// date = date.substring(0,date.length()-2);
	// if(tempFixDate<10){
	// delayDate = date+"0"+tempFixDate;
	// }else{
	// delayDate = date+tempFixDate;
	// }
	// }
	// //
	// if (gatherDelayRate==0) {
	// rComLastTerm = true;
	// }
	// }
	// // 将截取出来的一期的结束日期作为借据的的开始日期
	// acLnMstBean.setDueBegDate(firEndDate);
	// }
	//
	// // 还款计划第一期开始日期
	// String planBegDate = acLnMstBean.getDueBegDate();
	// // 默认期数
	// int terms = Integer.parseInt(defaultTerm(acLnMstBean));
	// for(int i=0;i<terms;++i){
	// PlanBean planBean = new PlanBean();
	// planBean.setBegDate(planBegDate);
	// String planEndDate = DateUtil.addByMonDay(planBegDate, 3, 0,
	// DateUtil.DATE_FORMAT_);
	// // 还款计划为闭区间(如2012-05-25--2012-05-24)
	// if(SystemParm.PLAN_END==1){
	// planBegDate = planEndDate;
	// planEndDate = DateUtil.subtDays(planEndDate, "1");
	// }
	// // 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
	// if(SystemParm.PLAN_END==0){
	// planBegDate = planEndDate;
	// }
	// planBean.setTermNo(String.valueOf(i+1));
	// if(i==terms-1){
	// planBean.setReturnCapital(acLnMstBean.getDueBal());
	// // 最后一期使用闭区间
	// if(SystemParm.LAST_TERM_OPEN==0){
	// //planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(),
	// "1"));
	// planBean.setEndDate(acLnMstBean.getDueEndDate());
	// }
	// // 最后一期使用开区间
	// if(SystemParm.LAST_TERM_OPEN==1){
	// //planBean.setEndDate(acLnMstBean.getDueEndDate());
	// planBean.setEndDate(acLnMstBean.getDueEndDate());
	// }
	// // 延期后设置最后一期
	// if(StringUtil.isNotEmpty(delayDate)){
	// //planBean.setEndDate(delayDate);
	// planBean.setEndDate(acLnMstBean.getDueEndDate());
	// }
	// }else {
	// planBean.setReturnCapital("0.00");
	// planBean.setEndDate(planEndDate);
	// }
	// planBean.setCifNo(acLnMstBean.getCifNo());
	// planBean.setDueAmt(acLnMstBean.getDueAmt());
	// planBean.setDueBal(acLnMstBean.getDueBal());
	// planBean.setDueNo(acLnMstBean.getDueNo());
	// planBean.setPactNo(acLnMstBean.getPactNo());
	// planBean.setState("0");
	// planBean.setReturnInterest(interest.getInterest(planBean,acLnMstBean));
	// planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
	// // 贷后管理费
	// planBean.setAccFee(getFee(acLnMstBean));
	// if(i<13){
	// planBean.setPerfAmount(getPerfAmount(acLnMstBean));
	// }
	// planBeans.add(planBean);
	//
	// }
	//
	// // 将列表按期号排序
	// Collections.sort(planBeans);
	// // 截取出来的利息在第一期收取时使用
	// if(Double.parseDouble(advaceInterest)>0){
	// planBeans.get(0).setReturnInterest(BigNumberUtil.Add(planBeans.get(0).getReturnInterest(),advaceInterest));
	// }
	// // 修改期号
	// if(StringUtil.equals("0", planBeans.get(0).getTermNo())){
	// int i=1;
	// for(PlanBean planBean:planBeans){
	// planBean.setTermNo(String.valueOf(i));
	// i=i+1;
	// }
	// }
	//
	// if(rComLastTerm){
	// int planBeanSize = planBeans.size();
	// PlanBean planBean = planBeans.get(planBeanSize-1);
	// String endDate = planBean.getEndDate();
	// String interestMoney = interest.getInterest(planBean, acLnMstBean);
	// planBean.setReturnInterest(interestMoney);
	// planBean.setEndDate(endDate);
	// planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
	// }
	//
	// return planBeans;
	// }

	/**
	 * 
	 * 方法描述： 等额本金还款计划 每月还本付息金额=（本金/还款月数）+（本金－累计已还本金）×月利率 每月本金=总本金/还款月数
	 * 每月利息=（本金-累计已还本金）×月利率 计算原则：每月归还的本金额始终不变，利息随剩余本金的减少而减少
	 * 
	 * @param dueBean
	 * @param planParmBean
	 *            void
	 * @author 乾之轩
	 * @date 2012-5-25 上午10:39:38
	 */
	private List<PlanBean> genePlan_5(PactInfo pactInfo) {
		Interest interest = new Interest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// 默认期数
		int terms = Integer.parseInt(defaultTerm(pactInfo));
		// 每月偿还本金
		String capital = BigNumberUtil.Divide(
				String.valueOf(pactInfo.getDue_bal()), String.valueOf(terms),
				2, "1");
		// 模拟剩余本金(计算利息使用)
		String remaCapital = "0.00";
		String planBenDate = pactInfo.getBeginDate();
		balance = pactInfo.getPact_amt();
		for (int i = 0; i < terms; ++i) {
			PlanBean planBean = new PlanBean();
			planBean.setTerm(String.valueOf(i + 1));
			planBean.setBeg_date(planBenDate);
			String planEndDate = DateUtil.addByMonDay(planBenDate, 1, 0,
					DateUtil.DATE_FORMAT_);
			// 还款计划为闭区间(如2012-05-25--2012-05-24)
			if (SystemParm.PLAN_END == 1) {
				planBenDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
			if (SystemParm.PLAN_END == 0) {
				planBenDate = planEndDate;
			}

			// 结束日期
			if (i == terms - 1) {
				// 设置最后一期的结束日期
				// 最后一期使用闭区间
				if (SystemParm.LAST_TERM_OPEN == 0) {
					planBean.setEnd_date(pactInfo.getEndDate());
				}
				// 最后一期使用开区间
				if (SystemParm.LAST_TERM_OPEN == 1) {
					// planBean.setEndDate(acLnMstBean.getDueEndDate());
					planBean.setEnd_date(pactInfo.getEndDate());
				}
			} else {
				// 非最后期的结束日期
				planBean.setEnd_date(planEndDate);
			}
			remaCapital = BigNumberUtil.add2String(remaCapital, capital);
			planBean.setPact_no(pactInfo.getPact_no());
			planBean.setIf_settle("0");
			planBean.setIf_alarm("0");
			planBean.setCapital(capital);
			planBean.setDue_bal(BigNumberUtil.Subtract(
					String.valueOf(pactInfo.getPact_amt()), remaCapital));
			String termInterest = interest.getInterest(planBean, pactInfo);
			planBean.setInterest(termInterest);
			planBean.setShould_amt(BigNumberUtil.Add(planBean.getCapital(),
					planBean.getInterest()));
			balance = Double.parseDouble(BigNumberUtil.Subtract(
					String.valueOf(balance), planBean.getCapital()));
			planBeans.add(planBean);
		}

		// 将列表按期号排序
		Collections.sort(planBeans);
		// 修改期号
		if (StringUtil.equals("0", planBeans.get(0).getTerm())) {
			int i = 1;
			for (PlanBean planBean : planBeans) {
				planBean.setTerm(String.valueOf(i));
				i = i + 1;
			}
		}

		return planBeans;
	}

	/**
	 * 
	 * 方法描述： 等额本息还款计划
	 * 
	 * @param dueBean
	 * @param planParmBean
	 *            void
	 * @author 乾之轩
	 * @date 2012-5-25 下午06:44:52
	 */
	private List<PlanBean> genePlan_6(PactInfo pactInfo) {
		// String tempTotalMoney = "0.0";
		String lp = "";
		Interest interest = new Interest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// 截取出来的一期的结束日期
		String firEndDate = "";
		// 默认期数
		int terms =  Integer.parseInt(defaultTerm(pactInfo));
		// 默认借据开始日期
		String dueBegDate = pactInfo.getBeginDate();
		// 固定还款日
		String fixDate = "";

		// 第一期的开始日期
		String planBenDate = pactInfo.getBeginDate();
		// 模拟剩余本金,用于利息计算
		String remaMoney = "0.00";
		int termNo = 0;
		int returnday = DateUtil.getDay(planBenDate);
//		if (StringUtil.isNotEmpty(fixDate)) {
//			returnday = Integer.parseInt(fixDate);
//		}
		// 前n-1期的本金合计;
		String tempCapt_ = "0";
		balance = pactInfo.getPact_amt();
		// 循环计算每一期
		for (int i = 0; i < terms; ++i) {
			PlanBean planBean = new PlanBean();
			// 期数
			planBean.setTerm(String.valueOf(termNo + 1));
			// 开始日期
			// 处理二月情况
			if (StringUtil.contains(planBenDate, "-02-29")
					|| StringUtil.contains(planBenDate, "-02-30")
					|| StringUtil.contains(planBenDate, "-02-31")) {
				if (DateUtil.isLeapYear(planBenDate.substring(0, 8) + "01")
						&& returnday > 29) {
					String tempDate = planBenDate.substring(0, 8);
					planBean.setBeg_date(tempDate + "29");
					planBenDate = tempDate + "29";
				} else {
					String tempDate = planBenDate.substring(0, 8);
					planBean.setBeg_date(tempDate + "28");
					planBenDate = tempDate + "28";
				}
			} else {
				planBean.setBeg_date(planBenDate);
			}
			// 计算结束日期
			String planEndDate = "";
			if (i == 0 && StringUtil.isNotEmpty(firEndDate)) {
				planEndDate = firEndDate;
				if ((returnday > 28 || returnday == 28)
						&& DateUtil.getMonth(planEndDate) != 2) {
					planEndDate = planEndDate.substring(0, 8) + returnday;
				}
			} else {
				planEndDate = DateUtil.addByMonDay(planBenDate, 1, 0,
						DateUtil.DATE_FORMAT_);

				if ((returnday > 28 || returnday == 28)
						&& DateUtil.getMonth(planEndDate) != 2) {
					if (DateUtil.getMonth(planEndDate) == 1
							|| DateUtil.getMonth(planEndDate) == 3
							|| DateUtil.getMonth(planEndDate) == 5
							|| DateUtil.getMonth(planEndDate) == 7
							|| DateUtil.getMonth(planEndDate) == 8
							|| DateUtil.getMonth(planEndDate) == 10
							|| DateUtil.getMonth(planEndDate) == 12) {
						planEndDate = planEndDate.substring(0, 8) + returnday;
					} else {
						if (returnday == 31) {
							planEndDate = planEndDate.substring(0, 8) + "30";
						} else {
							planEndDate = planEndDate.substring(0, 8)
									+ returnday;
						}
					}
				}
			}

			if (SystemParm.PLAN_END == 1) {// 还款计划为闭区间(如2012-05-25--2012-05-24)
				planBenDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			} else if (SystemParm.PLAN_END == 0) {// 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
				planBenDate = planEndDate;
			}
			if (i == terms - 1) {// 最后期的结束日期
				planBean.setEnd_date(pactInfo.getEndDate());
			} else {
				// 非最后期的结束日期
				planBean.setEnd_date(planEndDate);
			}

			/**
			 * --------------------------------------财务计算开始--------------------
			 * ---------------------
			 */
			// 本息合计
			String TotalMoney = interest.getInterest(planBean, terms,
					BigNumberUtil.Subtract(pactInfo.getDue_bal(), remaMoney),
					pactInfo);
			// 当期利息
			// System.out.println(acLnMstBean.getDueBal()+"-"+remaMoney+"="+BigNumberUtil.Subtract(acLnMstBean.getDueBal(),remaMoney));
			String interestMoney = interest.getInterestByMonth(planBean,
					BigNumberUtil.Subtract(pactInfo.getDue_bal(), remaMoney),
					pactInfo);
			// 应还利息
			String tempReturnInterest = interestMoney;

			planBean.setInterest(BigNumberUtil.Divide2(tempReturnInterest, "1"));
			if (StringUtil.isNotEmpty(fixDate)) {
				if (i == 0) {
					int n = DateUtil.getBetweenDays(dueBegDate, planEndDate);
					planBean.setInterest(BigNumberUtil.Divide2(
							BigNumberUtil.Multiply(interestMoney,
									String.valueOf(n)), "30"));
				}
				if (i == terms - 1) {
					String capital = BigNumberUtil.Subtract(TotalMoney,
							interestMoney);
					capital = BigNumberUtil.Divide(capital, "1", 2, "1");
					String s = interest.getInterestByDay(planBean, capital,
							pactInfo);
					planBean.setInterest(s);
				}
			}

			// 合同号
			planBean.setPact_no(pactInfo.getPact_no());
			// 还款计划状态
			planBean.setIf_settle("0");
			planBean.setIf_alarm("0");
			// 本息合计
			if (i == 0) {
				// 第一期的期供
				String total = BigNumberUtil.Add(TotalMoney,
						planBean.getInterest());
				total = BigNumberUtil.Subtract(total, interestMoney);
				// 第一期的本金
				String returnCapital = BigNumberUtil.Subtract(total,
						planBean.getInterest());
				total = BigNumberUtil.Divide2(total, "1");
				returnCapital = BigNumberUtil.Divide2(returnCapital, "1");
				planBean.setCapital(returnCapital);
				tempCapt_ = BigNumberUtil.Add(returnCapital, tempCapt_);
				planBean.setShould_amt(total);
				remaMoney = BigNumberUtil.Add(remaMoney, returnCapital);
			} else if (i == terms - 1) {
				String returnCapital = BigNumberUtil.Subtract(
						pactInfo.getDue_bal(), tempCapt_);
				returnCapital = BigNumberUtil.Divide2(returnCapital, "1");

				planBean.setCapital(returnCapital);
				String day = pactInfo.getEndDate().substring(8, 10);

				String endDate = planBean.getEnd_date().substring(0, 8) + day;

				int[] monthAndDay = DateUtil.getMonthsAndDays(
						planBean.getBeg_date(), endDate);

				int n = monthAndDay[0] * 30 + monthAndDay[1];

				String lx = BigNumberUtil.Multiply(returnCapital,
						pactInfo.getRate());
				lx = BigNumberUtil.Multiply(lx, String.valueOf(n));
				lx = BigNumberUtil.Divide2(lx, String.valueOf(30000));
				planBean.setInterest(lx);
				String total = BigNumberUtil.Add(returnCapital, lx);
				total = BigNumberUtil.Divide2(total, "1");
				planBean.setShould_amt(total);
				remaMoney = BigNumberUtil.Add(remaMoney, returnCapital);
			} else if (0 < i && i < terms - 1) {
				String capital_ = BigNumberUtil.Divide(TotalMoney, "1", 2, "1");
				planBean.setShould_amt(BigNumberUtil.Divide(TotalMoney, "1", 2,
						"1"));
				// 应还本金
				lp = lp
						+ "+"
						+ BigNumberUtil
								.Divide(BigNumberUtil.Subtract(capital_,
										interestMoney), "1", 2, "1");

				String returnCapital = BigNumberUtil.Divide2(BigNumberUtil
						.Subtract(capital_, planBean.getInterest()), "1");
				planBean.setCapital(returnCapital);
				tempCapt_ = BigNumberUtil.Add(returnCapital, tempCapt_);
				remaMoney = BigNumberUtil.Add(remaMoney, returnCapital);
			}

			balance = Double.parseDouble(BigNumberUtil.Subtract(
					String.valueOf(balance), planBean.getCapital()));
			termNo++;
			planBeans.add(planBean);

		}

		Collections.sort(planBeans);

		Double s = 0d;
		if (s > 0) {
			String tempCapt = planBeans.get(planBeans.size() - 1).getCapital();
			String tempTotal = planBeans.get(planBeans.size() - 1)
					.getShould_amt();

			planBeans.get(planBeans.size() - 1).setCapital(
					BigNumberUtil.Subtract(tempCapt, s.toString()));
			planBeans.get(planBeans.size() - 1).setShould_amt(
					BigNumberUtil.Subtract(tempTotal, s.toString()));

		} else {
			String tempCapt = planBeans.get(planBeans.size() - 1).getCapital();
			String tempTotal = planBeans.get(planBeans.size() - 1)
					.getShould_amt();

			s = s * (-1);
			planBeans.get(planBeans.size() - 1).setCapital(
					BigNumberUtil.Add(tempCapt, s.toString()));

			planBeans.get(planBeans.size() - 1).setShould_amt(
					BigNumberUtil.Add(tempTotal, s.toString()));
		}

		System.out.println("lp=" + lp);
		return planBeans;
	}

	/**
	 * 
	 * 方法描述： 获得默认期数
	 * 
	 * @return String
	 * @author 乾之轩
	 * @date 2012-5-24 下午12:02:57
	 */
	private String defaultTerm(PactInfo pactInfo) {
		// 还款计划第一期的开始日期(默认和合同的开始日期相同)
		// 合同的开始日期
		String begDate = pactInfo.getBeginDate();
		String endDate = pactInfo.getEndDate();
		int[] monthAndDays = DateUtil.getMonthsAndDays(begDate, endDate);
		if (monthAndDays[1] > 0) {
			return String.valueOf(monthAndDays[0] + 1);
		} else {
			return String.valueOf(monthAndDays[0]);
		}
	}
	private String PMT(Double rate, Double term, Double financeAmount) {
		Double v = (1 + rate);
		Double t = (-(term / 12) * 12);
		Double result = (financeAmount * rate) / (1 - Math.pow(v, t));
		return result.toString();
	}

	public IPlanDao getPlanDao() {
		return planDao;
	}


}
