/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PlanServices.java
 * 包名： com.dx.loan.plan.services.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-5-26 上午10:20:15
 * @version V1.0
 */ 
package com.dx.loan.plan.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dx.common.SystemParm;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.fee.bean.FeeTypeBean;
import com.dx.loan.fee.services.FeeServices;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanParmBean;
import com.dx.loan.plan.dao.IPlanDao;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.interest.BactInterest;

/**
 * 类名： PlanServices
 * 描述：
 * @author 乾之轩
 * @date 2012-5-26 上午10:20:15
 *
 *
 */
public class PlanServices {
	private IPlanDao  planDao ;
	public void setPlanDao(IPlanDao planDao) {
		this.planDao = planDao;
	}

	/**
	 * 
	 * 方法描述： 根据借据信息生成还款计划
	 * @param dueBean
	 * @param begDate(生成还款计划的日期,默认为借据的开始日期)
	 * @author 乾之轩
	 * @date 2012-5-24 上午09:23:52
	 */
	public List<PlanBean> genePlan(AcLnMstBean acLnMstBean,PlanParmBean planParmBean) {
		List<PlanBean> planBeans = null;
		String returnMethod = acLnMstBean.getReturnMethod();
		
		// 利随本清还款计划
		if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR, returnMethod)){
			planBeans = genePlan_1(acLnMstBean);
		}
		
		// 按计划的还款计划
		if(StringUtil.equals(SystemParm.RETURNMETHOD_PLAN, returnMethod)){
			planBeans = genePlan_2(acLnMstBean,planParmBean);
		}
		
		// 一次偿还本金按月结息
		if(StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, returnMethod)){
			planBeans = genePlan_3(acLnMstBean,planParmBean);
		}
		
		// 一次偿还本金按季结息
		if(StringUtil.equals(SystemParm.RETURNMETHOD_SEASON, returnMethod)){
			planBeans = genePlan_4(acLnMstBean,planParmBean);
		}
		
		// 等额本金还款计划
		if(StringUtil.equals(SystemParm.RETURNMETHOD_PRINCIPAL, returnMethod)){
			planBeans = genePlan_5(acLnMstBean,planParmBean);
		}
		
		// 等额本息还款计划
		if(StringUtil.equals(SystemParm.RETURNMETHOD_INTEREST, returnMethod)){
			planBeans = genePlan_6(acLnMstBean,planParmBean);
		}
		return planBeans;
	} 
	
	/**************************生成还款计划部分开始***********************/
	/**
	 * 
	 * 方法描述： 利随本清还款计划
	 * void
	 * @author 乾之轩
	 * @date 2012-5-24 上午09:48:46
	 */
	private List<PlanBean> genePlan_1(AcLnMstBean acLnMstBean){
		List<PlanBean> planList = new ArrayList<PlanBean>();
		BactInterest interest = new BactInterest();
		PlanBean planBean = new PlanBean();
		planBean.setBegDate(acLnMstBean.getDueBegDate());
		
		planBean.setCifNo(acLnMstBean.getCifNo());
		planBean.setDueAmt(acLnMstBean.getDueAmt());
		planBean.setDueBal(acLnMstBean.getDueBal());
		planBean.setDueNo(acLnMstBean.getDueNo());
		// 使用闭区间
		if(SystemParm.PLAN_END==1){
			String planEndDate = DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1");
			planBean.setEndDate(planEndDate);
		}
		// 使用半开半闭区间
		if(SystemParm.PLAN_END==0){
			planBean.setEndDate(acLnMstBean.getDueEndDate());
		}
		planBean.setPactNo(acLnMstBean.getPactNo());
		planBean.setReturnCapital(acLnMstBean.getDueAmt());
		planBean.setState("0");
		planBean.setTermNo("1");
		planBean.setReturnInterest(interest.getInterest(planBean,acLnMstBean));
		// 账户管理费
		planBean.setAccFee(getFee(acLnMstBean));
		// 履约保证金
		planBean.setPerfAmount(getPerfAmount(acLnMstBean));
		planList.add(planBean);
		return planList;
	}
	
	/**
	 * 
	 * 方法描述： 获得生成的还款计划(每期的天数计算方法)
	 * @param dueBean 
	 * @param begDate
	 * void   
	 * @author 乾之轩
	 * @date 2012-5-24 上午11:02:23
	 */
	private List<PlanBean> genePlan_2(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// 计算利息工具类
		BactInterest interest = new BactInterest();
		// 截取出来的利息
		String advaceInterest = "0.00";
		int plan_day_type = SystemParm.PLAN_DAY_TYPE;
		// 固定还款日
		String fixDate = planParmBean.getFixDate();
		// 截取出来的的一期的结束日是在本月还是在下一个月   0 本月 1 下一月 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// 截取出来的一期的处理方式 0 在第一期收取 1 单独当做一期有本金  2 单独做为一期无本金
		int dealAdvanceType =  SystemParm.DEAL_ADVANCE_TYPE;
		// 延期部分是否收取利息 0 不收取 1收取
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// 标示最后一期的利息是否需要从新计算
		boolean rComLastTerm = false;
		//是否延期 0 不延期 1 延期
		int isDelay = SystemParm.IS_DELAY;
		// 延期后的结束日期
		String  delayDate = "";
		// 截取出来的结束日期
		String firEndDate = "";
		// 处理固定日还款
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// 结束日期在放款日所在月(当做一期并且没有本金不允许在下一个月)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			// 结束日期在放款日所在月的下一月
			if(advanceEndType==1){
				// 放款日期加一个月
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			
			
			// 截取出来的一期的利息在第一期收取
			if(dealAdvanceType==0){
				 PlanBean planBean = new PlanBean();
				 planBean.setBegDate(acLnMstBean.getDueBegDate());
				 planBean.setEndDate(firEndDate);
				 planBean.setDueBal(acLnMstBean.getDueBal());
				 planBean.setAccFee(getFee(acLnMstBean));
				 planBean.setPerfAmount(getPerfAmount(acLnMstBean));
				 advaceInterest= interest.getInterest(planBean,acLnMstBean);
				 
			}
			
			// 截取出一期单独作为一期且有本金
			if(dealAdvanceType==1){
				PlanBean planBean = new PlanBean();
				int firstDay = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), firEndDate);
				// 获得借据期限天
				int days = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
				String everyDayMoney = BigNumberUtil.Divide(acLnMstBean.getDueBal(), String.valueOf(days), 2, "1");
				String firstCapital = BigNumberUtil.Multiply(everyDayMoney, String.valueOf(firstDay));
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital(firstCapital);
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				 planBean.setAccFee(getFee(acLnMstBean));
				 planBean.setPerfAmount(getPerfAmount(acLnMstBean));
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				// 从新设置借据的余额
				acLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(), firstCapital));
				
			}
			// 单独作为一期并且没有本金
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				 planBean.setAccFee(getFee(acLnMstBean));
				 planBean.setPerfAmount(getPerfAmount(acLnMstBean));
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
			}
			
			// 如果延期设置延期后的结束日期
			if(isDelay==1){
				// 获得借据的结束日
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// 延期后的结束日期还在当前月
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+"0"+tempFixDate;
					}
				}else{
					// 延期后的结束日期做在下一个月
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			// 将截取出来的一期的结束日期作为借据的的开始日期
			acLnMstBean.setDueBegDate(firEndDate);
		}
		
		
		
		
		
		
		// 获得借据期限(天数)
		int days = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
		// 默认期数
		int terms = Integer.parseInt(defaultTerm(acLnMstBean));
		// 期限除以期数取整
		int termDays = days/terms;
		// 期限除以期数求余
		int remaDays = days%terms;
		// 借据金额除以期数取整
		String termMoney = BigNumberUtil.Divide(acLnMstBean.getDueBal(),String.valueOf(terms) , 0, "3");
		// 借据金额除以期数后的余数
		String tempMoney = BigNumberUtil.Multiply(termMoney, String.valueOf(terms)); 
		String remaMoney = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), tempMoney);
		
		String planBegDate = acLnMstBean.getDueBegDate();
		if(plan_day_type==1){
			for(int i=0;i<terms;++i){
				PlanBean planBean = new PlanBean(); 
				planBean.setTermNo(String.valueOf(i+1));
				planBean.setBegDate(planBegDate);
				String planEndDate = DateUtil.addByDay(planBegDate, termDays, DateUtil.DATE_FORMAT_);
				
				// 还款计划为闭区间(如2012-05-25--2012-05-24)
				if(SystemParm.PLAN_END==1){
					planBegDate = planEndDate;
					planEndDate = DateUtil.subtDays(planEndDate, "1");
				}
				// 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
				if(SystemParm.PLAN_END==0){
					planBegDate = planEndDate;
				}
				
				// 结束日期
				if(i==terms-1){
					// 设置最后一期的结束日期
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}else{
					// 非最后期的结束日期
					planBean.setEndDate(planEndDate);
				}
				
			
				
	 			if(remaDays>0){
	 				planEndDate  =  DateUtil.addByDay(planEndDate, 1, DateUtil.DATE_FORMAT_);
	 				remaDays = remaDays-1;
	 			}
	 			
	 			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
	 			if(i<13){
	 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
	 			}
	 			planBeans.add(planBean);
	 			
	 			planBean = null;
			}
		}
		
		if(plan_day_type==0){
			String tempBal = "0.00";
			for(int i=0;i<terms;++i){
				PlanBean planBean = new PlanBean();
				planBean.setTermNo(String.valueOf(i+1));
				planBean.setBegDate(planBegDate);
				String planEndDate = DateUtil.addByMonDay(planBegDate, 1, 0, DateUtil.DATE_FORMAT_);
				planBean.setBegDate(planBegDate);
				// 还款计划为闭区间(如2012-05-25--2012-05-24)
				if(SystemParm.PLAN_END==1){
					planBegDate = planEndDate;
					planEndDate = DateUtil.subtDays(planEndDate, "1");
				}
				// 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
				if(SystemParm.PLAN_END==0){
					planBegDate = planEndDate;
				}
				
				
				if(i==terms-1){
					// 最后一期使用闭区间
					if(SystemParm.LAST_TERM_OPEN==0){
						planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
					}
					// 最后一期使用开区间
					if(SystemParm.LAST_TERM_OPEN==1){
						planBean.setEndDate(acLnMstBean.getDueEndDate());
					}
					planBean.setReturnCapital(BigNumberUtil.Add(termMoney,remaMoney));
					// 延期后设置最后一期
					if(StringUtil.isNotEmpty(delayDate)){
						planBean.setEndDate(delayDate);
					}
					
				}else{
					planBean.setReturnCapital(termMoney);
					planBean.setEndDate(planEndDate);
				}
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueAmt());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setPactNo(acLnMstBean.getPactNo());
				String planBal = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), tempBal);
				planBal = BigNumberUtil.Divide(planBal, "1",2,"1");
				planBean.setDueBal(planBal);
				
				tempBal = BigNumberUtil.Add(tempBal, planBean.getReturnCapital());
				planBean.setState("0");
				planBean.setReturnInterest(BigNumberUtil.Divide(interest.getInterest(planBean,acLnMstBean), "1", 2, "1"));
				
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				// 账户管理费
				planBean.setAccFee(getFee(acLnMstBean));
				// 履约保证金额
				if(i<13){
					planBean.setPerfAmount(getPerfAmount(acLnMstBean));
				}
				planBeans.add(planBean);
				planBean = null;
			}
		}
		// 将列表按期号排序
		Collections.sort(planBeans);
		// 截取出来的利息在第一期收取时使用
		if(Double.parseDouble(advaceInterest)>0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(planBeans.get(0).getReturnInterest(),advaceInterest));
		}
		// 修改期号
		if(StringUtil.equals("0", planBeans.get(0).getTermNo())){
			int i=1;
			for(PlanBean planBean:planBeans){
				planBean.setTermNo(String.valueOf(i));
				i=i+1;
			}
		}
		// 延期部分不计算利息时,从新计算最后一期的利息
		if(rComLastTerm){
			int planBeanSize = planBeans.size();
			PlanBean planBean = planBeans.get(planBeanSize-1);
			String endDate = planBean.getEndDate();
			planBean.setEndDate(acLnMstBean.getDueEndDate());
			String  interestMoney = interest.getInterest(planBean,acLnMstBean);
			planBean.setReturnInterest(interestMoney);
			planBean.setEndDate(endDate);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
		}
		
		return planBeans;
	}
	
	
	/**
	 * 
	 * 方法描述： 一次偿还本金按月结息
	 * @param dueBean
	 * @param planParmBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-24 下午06:43:37
	 */
	private List<PlanBean> genePlan_3(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		BactInterest interest = new BactInterest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// 固定还款日时预先截取出来的(首期)的结束日期时在本月还是下一个月 0 本月 1 下一月 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// 固定还款日截取出来的一期的处理方式 0 在第一期收取 1 当做一期有本金 2 当做一期无本金
		int dealAdvanceType = SystemParm.DEAL_ADVANCE_TYPE;
		// 延期部分是否收取利息 0 不收取 1收取
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// 标示最后一期的利息是否需要从新计算
		boolean rComLastTerm = false;
		// 固定还款日时预先截取出来的(首期)的结束日期
		String firEndDate="";
		// 是否延期  只有选择固定还款日后该设置才能起作用  0 不延期  1 延期
		int isDelay = SystemParm.IS_DELAY;
		// 截取出来的一期的利息
		String advaceInterest="0.00";
		// 固定还款日
		String fixDate = planParmBean.getFixDate();
		// 延期后的结束日期 
		String delayDate="";
		// 处理固定日还款
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// 结束日期在放款日所在月(当做一期并且没有本金不允许在下一个月)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			// 结束日期在放款日所在月的下一月
			if(advanceEndType==1){
				// 放款日期加一个月
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			
			
			// 截取出来的一期的利息在第一期收取
			if(dealAdvanceType==0){
				 PlanBean planBean = new PlanBean();
				 planBean.setBegDate(acLnMstBean.getDueBegDate());
				 planBean.setEndDate(firEndDate);
				 planBean.setDueBal(acLnMstBean.getDueBal());
				 advaceInterest= interest.getInterest(planBean,acLnMstBean);
				 
			}
			
			// 截取出一期单独作为一期且有本金(一次偿还本金比较特殊他和当做一期无本金相同)
			if(dealAdvanceType==1){
				/**
				PlanBean planBean = new PlanBean();
				int firstDay = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), firEndDate);
				// 获得借据期限天
				int days = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
				String everyDayMoney = BigNumberUtil.Divide(acLnMstBean.getDueBal(), String.valueOf(days), 2, "1");
				String firstCapital = BigNumberUtil.Multiply(everyDayMoney, String.valueOf(firstDay));
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital(firstCapital);
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				// 从新设置借据的余额
				acLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(), firstCapital));
				**/
				
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				
			}
			// 单独作为一期并且没有本金
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBeans.add(planBean);
			}
			
			// 如果延期设置延期后的结束日期
			if(isDelay==1){
				// 获得借据的结束日
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// 延期后的结束日期还在当前月
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+"0"+tempFixDate;
					}
				}else{
					// 延期后的结束日期做在下一个月
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			// 将截取出来的一期的结束日期作为借据的的开始日期
			acLnMstBean.setDueBegDate(firEndDate);
		}
		
		// 还款计划第一期开始日期
		String planBegDate = acLnMstBean.getDueBegDate();
		// 默认期数
		int terms = Integer.parseInt(defaultTerm(acLnMstBean));
		for(int i=0;i<terms;++i){
			PlanBean planBean = new PlanBean();
			planBean.setBegDate(planBegDate);
			String planEndDate = DateUtil.addByMonDay(planBegDate, 1, 0, DateUtil.DATE_FORMAT_);
			// 还款计划为闭区间(如2012-05-25--2012-05-24)
			if(SystemParm.PLAN_END==1){
				planBegDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
			if(SystemParm.PLAN_END==0){
				planBegDate = planEndDate;
			}
			planBean.setTermNo(String.valueOf(i+1));
			if(i==terms-1){
				planBean.setReturnCapital(acLnMstBean.getDueBal());
				// 最后一期使用闭区间
				if(SystemParm.LAST_TERM_OPEN==0){
					planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
				}
				// 最后一期使用开区间
				if(SystemParm.LAST_TERM_OPEN==1){
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}
				// 延期后设置最后一期
				if(StringUtil.isNotEmpty(delayDate)){
					planBean.setEndDate(delayDate);
				}
			}else {
				planBean.setReturnCapital("0.00");
				planBean.setEndDate(planEndDate);
			}
			planBean.setCifNo(acLnMstBean.getCifNo());
			planBean.setDueAmt(acLnMstBean.getDueAmt());
			planBean.setDueBal(acLnMstBean.getDueBal());
			planBean.setDueNo(acLnMstBean.getDueNo());
			planBean.setPactNo(acLnMstBean.getPactNo());
			planBean.setState("0");
			planBean.setReturnInterest(interest.getInterest(planBean,acLnMstBean));
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
			// 账户管理费
			planBean.setAccFee(getFee(acLnMstBean));
				if(i<13){
	 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
	 			}
			planBeans.add(planBean);
		
		}
		
		// 将列表按期号排序
		Collections.sort(planBeans);
		// 截取出来的利息在第一期收取时使用
		if(Double.parseDouble(advaceInterest)>0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(planBeans.get(0).getReturnInterest(),advaceInterest));
		}
		// 修改期号
		if(StringUtil.equals("0", planBeans.get(0).getTermNo())){
			int i=1;
			for(PlanBean planBean:planBeans){
				planBean.setTermNo(String.valueOf(i));
				i=i+1;
			}
		}
		if(rComLastTerm){
			int planBeanSize = planBeans.size();
			PlanBean planBean = planBeans.get(planBeanSize-1);
			String endDate = planBean.getEndDate();
			planBean.setEndDate(acLnMstBean.getDueEndDate());
			String interestMoney = interest.getInterest(planBean, acLnMstBean);
			planBean.setReturnInterest(interestMoney);
			planBean.setEndDate(endDate);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
		}
	
		return planBeans;
	}
	
	/**
	 * 
	 * 方法描述：  一次偿还本金按季结息
	 * @param dueBean
	 * @param planParmBean
	 * @return
	 * List<PlanBean>
	 * @author 乾之轩
	 * @date 2012-5-25 上午09:11:32
	 */
	private List<PlanBean> genePlan_4(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		BactInterest interest = new BactInterest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// 固定还款日时预先截取出来的(首期)的结束日期时在本月还是下一个月 0 本月 1 下一月 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// 固定还款日截取出来的一期的处理方式 0 在第一期收取 1 当做一期有本金 2 当做一期无本金
		int dealAdvanceType = SystemParm.DEAL_ADVANCE_TYPE;
		// 延期部分是否收取利息 0 不收取 1收取
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// 标示最后一期的利息是否需要从新计算
		boolean rComLastTerm = false;
		// 固定还款日时预先截取出来的(首期)的结束日期
		String firEndDate="";
		// 是否延期  只有选择固定还款日后该设置才能起作用  0 不延期  1 延期
		int isDelay = SystemParm.IS_DELAY;
		// 截取出来的一期的利息
		String advaceInterest="0.00";
		// 固定还款日
		String fixDate = planParmBean.getFixDate();
		// 延期后的结束日期 
		String delayDate="";
		// 处理固定日还款
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// 结束日期在放款日所在月(当做一期并且没有本金不允许在下一个月)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			// 结束日期在放款日所在月的下一月
			if(advanceEndType==1){
				// 放款日期加一个月
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			
			
			// 截取出来的一期的利息在第一期收取
			if(dealAdvanceType==0){
				 PlanBean planBean = new PlanBean();
				 planBean.setBegDate(acLnMstBean.getDueBegDate());
				 planBean.setEndDate(firEndDate);
				 planBean.setDueBal(acLnMstBean.getDueBal());
				 advaceInterest= interest.getInterest(planBean,acLnMstBean);
				 
			}
			
			// 截取出一期单独作为一期且有本金(一次偿还本金比较特殊他和当做一期无本金相同)
			if(dealAdvanceType==1){
				/**
				PlanBean planBean = new PlanBean();
				int firstDay = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), firEndDate);
				// 获得借据期限天
				int days = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
				String everyDayMoney = BigNumberUtil.Divide(acLnMstBean.getDueBal(), String.valueOf(days), 2, "1");
				String firstCapital = BigNumberUtil.Multiply(everyDayMoney, String.valueOf(firstDay));
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital(firstCapital);
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				// 从新设置借据的余额
				acLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(), firstCapital));
				**/
				
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				
			}
			// 单独作为一期并且没有本金
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBeans.add(planBean);
			}
			
			// 如果延期设置延期后的结束日期
			if(isDelay==1){
				// 获得借据的结束日
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// 延期后的结束日期还在当前月
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+"0"+tempFixDate;
					}
				}else{
					// 延期后的结束日期做在下一个月
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				// 
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			// 将截取出来的一期的结束日期作为借据的的开始日期
			acLnMstBean.setDueBegDate(firEndDate);
		}
		
		// 还款计划第一期开始日期
		String planBegDate = acLnMstBean.getDueBegDate();
		// 默认期数
		int terms = Integer.parseInt(defaultTerm(acLnMstBean));
		for(int i=0;i<terms;++i){
			PlanBean planBean = new PlanBean();
			planBean.setBegDate(planBegDate);
			String planEndDate = DateUtil.addByMonDay(planBegDate, 3, 0, DateUtil.DATE_FORMAT_);
			// 还款计划为闭区间(如2012-05-25--2012-05-24)
			if(SystemParm.PLAN_END==1){
				planBegDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
			if(SystemParm.PLAN_END==0){
				planBegDate = planEndDate;
			}
			planBean.setTermNo(String.valueOf(i+1));
			if(i==terms-1){
				planBean.setReturnCapital(acLnMstBean.getDueBal());
				// 最后一期使用闭区间
				if(SystemParm.LAST_TERM_OPEN==0){
					planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
				}
				// 最后一期使用开区间
				if(SystemParm.LAST_TERM_OPEN==1){
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}
				// 延期后设置最后一期
				if(StringUtil.isNotEmpty(delayDate)){
					planBean.setEndDate(delayDate);
				}
			}else {
				planBean.setReturnCapital("0.00");
				planBean.setEndDate(planEndDate);
			}
			planBean.setCifNo(acLnMstBean.getCifNo());
			planBean.setDueAmt(acLnMstBean.getDueAmt());
			planBean.setDueBal(acLnMstBean.getDueBal());
			planBean.setDueNo(acLnMstBean.getDueNo());
			planBean.setPactNo(acLnMstBean.getPactNo());
			planBean.setState("0");
			planBean.setReturnInterest(interest.getInterest(planBean,acLnMstBean));
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
			// 账户管理费
			planBean.setAccFee(getFee(acLnMstBean));
			if(i<13){
 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
 			}
			planBeans.add(planBean);
		
		}
		
		// 将列表按期号排序
		Collections.sort(planBeans);
		// 截取出来的利息在第一期收取时使用
		if(Double.parseDouble(advaceInterest)>0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(planBeans.get(0).getReturnInterest(),advaceInterest));
		}
		// 修改期号
		if(StringUtil.equals("0", planBeans.get(0).getTermNo())){
			int i=1;
			for(PlanBean planBean:planBeans){
				planBean.setTermNo(String.valueOf(i));
				i=i+1;
			}
		}
		
		if(rComLastTerm){
			int planBeanSize = planBeans.size();
			PlanBean planBean = planBeans.get(planBeanSize-1);
			String endDate = planBean.getEndDate();
			String interestMoney = interest.getInterest(planBean, acLnMstBean);
			planBean.setReturnInterest(interestMoney);
			planBean.setEndDate(endDate);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
		}
	
		return planBeans;
	}
	
	/**
	 * 
	 * 方法描述： 等额本金还款计划
	 * 每月还本付息金额=（本金/还款月数）+（本金－累计已还本金）×月利率 
	 * 每月本金=总本金/还款月数 
     * 每月利息=（本金-累计已还本金）×月利率 
     * 计算原则：每月归还的本金额始终不变，利息随剩余本金的减少而减少 
	 * @param dueBean
	 * @param planParmBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-25 上午10:39:38
	 */
	private List<PlanBean>  genePlan_5(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		BactInterest interest = new BactInterest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// 固定还款日时预先截取出来的(首期)的结束日期时在本月还是下一个月 0 本月 1 下一月 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// 固定还款日截取出来的一期的处理方式 0 在第一期收取 1 当做一期有本金 2 当做一期无本金
		int dealAdvanceType = SystemParm.DEAL_ADVANCE_TYPE;
		// 延期部分是否收取利息 0 不收取 1收取
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// 标示最后一期的利息是否需要从新计算
		boolean rComLastTerm = false;
		// 是否延期 0 不延期 1 延期
		int isDelay = SystemParm.IS_DELAY;
		// 延期后的结束日期
		String delayDate="";
		// 固定还款日截取出来的一期的利息
		String advaceInterest="0.00";
		// 固定还款日时预先截取出来的(首期)的结束日期
		String firEndDate="";
		// 固定还款日
		String fixDate = planParmBean.getFixDate();
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// 结束日期在放款日所在月(当做一期并且没有本金不允许在下一个月)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			// 结束日期在放款日所在月的下一月
			if(advanceEndType==1){
				// 放款日期加一个月
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			
			// 截取出来的一期的利息在第一期收取
			if(dealAdvanceType==0){
				 PlanBean planBean = new PlanBean();
				 planBean.setBegDate(acLnMstBean.getDueBegDate());
				 planBean.setEndDate(firEndDate);
				 planBean.setDueBal(acLnMstBean.getDueBal());
				 planBean.setTermNo("0");
				 advaceInterest= interest.getInterest(planBean,acLnMstBean);
			}
			
			// 截取出一期单独作为一期且有本金
			if(dealAdvanceType==1){
				PlanBean planBean = new PlanBean();
				int terms = Integer.parseInt(defaultTerm(acLnMstBean));
				terms =  terms+1;
				String  firstCapital = BigNumberUtil.Divide(acLnMstBean.getDueAmt(), String.valueOf(terms), 2, "1");
				String remaBal =  BigNumberUtil.Subtract(acLnMstBean.getDueBal(), firstCapital);
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(remaBal);
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital(firstCapital);
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
			
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				// 从新设置借据的余额
				acLnMstBean.setDueBal(remaBal);
			}
			
			// 单独作为一期并且没有本金
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setTermNo("0");
				planBean.setState("0");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
			}
			
			// 如果延期设置延期后的结束日期
			if(isDelay==1){
				// 获得借据的结束日
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// 延期后的结束日期还在当前月
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}else{
					// 延期后的结束日期做在下一个月
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				// 延期部分不收取利息
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			// 将截取出来的一期的结束日期作为借据的的开始日期
			acLnMstBean.setDueBegDate(firEndDate);
		}
		
		// 默认期数
		int terms = Integer.parseInt(defaultTerm(acLnMstBean));
		// 每月偿还本金
		String  capital = BigNumberUtil.Divide(acLnMstBean.getDueBal(), String.valueOf(terms), 2, "1");
        // 模拟剩余本金(计算利息使用)
        String remaCapital = "0.00";
        String planBenDate = acLnMstBean.getDueBegDate();
		for(int i=0;i<terms;++i){
        	PlanBean planBean = new PlanBean();
        	planBean.setTermNo(String.valueOf(i+1));
        	planBean.setBegDate(planBenDate);
        	String planEndDate = DateUtil.addByMonDay(planBenDate, 1, 0, DateUtil.DATE_FORMAT_);
    		// 还款计划为闭区间(如2012-05-25--2012-05-24)
			if(SystemParm.PLAN_END==1){
				planBenDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
			if(SystemParm.PLAN_END==0){
				planBenDate = planEndDate;
			}
			
			// 结束日期
			if(i==terms-1){
				// 设置最后一期的结束日期
				// 最后一期使用闭区间
				if(SystemParm.LAST_TERM_OPEN==0){
					planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
				}
				// 最后一期使用开区间
				if(SystemParm.LAST_TERM_OPEN==1){
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}
				// 延期后设置最后一期
				if(StringUtil.isNotEmpty(delayDate)){
					planBean.setEndDate(delayDate);
				}
			}else{
				// 非最后期的结束日期
				planBean.setEndDate(planEndDate);
			}
        	planBean.setCifNo(acLnMstBean.getCifNo());
        	planBean.setDueAmt(acLnMstBean.getDueAmt());
        	planBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueAmt(), remaCapital));
        	remaCapital =BigNumberUtil.add2String(remaCapital, capital);
        	planBean.setDueNo(acLnMstBean.getDueNo());
        	planBean.setPactNo(acLnMstBean.getPactNo());
        	planBean.setState("0");
        	planBean.setReturnCapital(capital);
        	String termInterest = interest.getInterest(planBean,acLnMstBean);
        	planBean.setReturnInterest(termInterest);
        	planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
        	// 账户管理费
			planBean.setAccFee(getFee(acLnMstBean));
			if(i<13){
 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
 			}
        	planBeans.add(planBean);
        }		
		
		// 将列表按期号排序
		Collections.sort(planBeans);
		// 截取出来的利息在第一期收取时使用
		if(Double.parseDouble(advaceInterest)>0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(planBeans.get(0).getReturnInterest(),advaceInterest));
		}
		// 修改期号
		if(StringUtil.equals("0", planBeans.get(0).getTermNo())){
			int i=1;
			for(PlanBean planBean:planBeans){
				planBean.setTermNo(String.valueOf(i));
				i=i+1;
			}
		}
		// 延期部分不收取利息
		if(rComLastTerm){
			int planBeanSize =  planBeans.size();
			PlanBean planBean = planBeans.get(planBeanSize-1);
			String endDate = planBean.getEndDate();
			planBean.setEndDate(acLnMstBean.getDueEndDate());
			String  interestMoney =  interest.getInterest(planBean,acLnMstBean);
			planBean.setReturnInterest(interestMoney);
			planBean.setEndDate(endDate);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
		}
		
		return planBeans;
	}
	
	
	/**
	 * 
	 * 方法描述：  等额本息还款计划
	 * @param dueBean
	 * @param planParmBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-25 下午06:44:52
	 */
	private List<PlanBean> genePlan_6(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		BactInterest interest = new BactInterest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// 固定还款日时预先截取出来的(首期)的结束日期时在本月还是下一个月 0 本月 1 下一月 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// 固定还款日截取出来的一期的处理方式 0 在第一期收取 1 当做一期有本金 2 当做一期无本金
		int dealAdvanceType = SystemParm.DEAL_ADVANCE_TYPE;
		// 延期部分是否收取利息 0 不收取 1收取
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// 标示最后一期的利息是否需要从新计算
		boolean rComLastTerm = false;
		 dealAdvanceType = 0;
		//判断是否延期 0 不延期 1 延期
		int isDelay = SystemParm.IS_DELAY;
		// 延期后的借据结束日
		String delayDate = "";
		// 截取出来来的一期的结束日期 
		String firEndDate = "";
		// 默认期数
		int terms = 0;
		// 预先截取出来的利息
		String advaceInterest="0.00";
		// 默认借据开始日期
		String dueBegDate = acLnMstBean.getDueBegDate();
		
		// 固定还款日
		String fixDate = planParmBean.getFixDate();
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// 结束日期在放款日所在月(当做一期并且没有本金不允许在下一个月)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			
			// 结束日期在放款日所在月的下一月
			if(advanceEndType==1){
				// 放款日期加一个月
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			// 截取出来的利息在第一期收取
			if(dealAdvanceType==0){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setEndDate(firEndDate);
				planBean.setDueBal(acLnMstBean.getDueBal());
				advaceInterest = interest.getInterestByDay(planBean, acLnMstBean.getDueBal(), acLnMstBean);
				acLnMstBean.setDueBegDate(firEndDate);
				terms = Integer.parseInt(defaultTerm(acLnMstBean));
			}
			
			
			
			// 当做一期有本金
			if(dealAdvanceType==1){
				// 将借据的开始日期设置为截取出来的一期的结束日期
			    acLnMstBean.setDueBegDate(firEndDate);
				// 计算从截取出来的一期的结束日期到借据结束日期的期数
				terms = Integer.parseInt(defaultTerm(acLnMstBean));
				//计算出来的期数不包括截取出来的那一期所以再加上1
				terms = terms+1;
				// 恢复借据的默认开始日期
				acLnMstBean.setDueBegDate(dueBegDate);
			}
			
			// 当做一期无本金
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setTermNo("0");
				planBean.setState("0");
				planBean.setReturnInterest(interest.getInterestByDay(planBean, acLnMstBean.getDueBal(), acLnMstBean));
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				
				// 将借据的开始日期设置为截取出来的一期的结束日期
			    acLnMstBean.setDueBegDate(firEndDate);
				// 计算从截取出来的一期的结束日期到借据结束日期的期数
				terms = Integer.parseInt(defaultTerm(acLnMstBean));
				//计算出来的期数不包括截取出来的那一期所以再加上1
			}
			// 如果延期设置延期后的结束日期
			if(isDelay==1){
				// 获得借据的结束日
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// 延期后的结束日期还在当前月
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+"0"+tempFixDate;
					}
				}else{
					// 延期后的结束日期做在下一个月
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				// 延期部分不收取利息
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			
			
		}else{
			terms = Integer.parseInt(defaultTerm(acLnMstBean));
		}
		
		// 第一期的开始日期
		String planBenDate = acLnMstBean.getDueBegDate();
		//模拟剩余本金,用于利息计算
		String remaMoney = "0.00";
		
		for(int i=0;i<terms;++i){
			PlanBean  planBean = new PlanBean(); 
			// 期数
			planBean.setTermNo(String.valueOf(i+1));
			// 开始日期
			planBean.setBegDate(planBenDate);
			String planEndDate ="";
			if(i==0 && StringUtil.isNotEmpty(firEndDate) ){
				 planEndDate = firEndDate;
				 planEndDate = DateUtil.addByMonDay(planBenDate, 1, 0, DateUtil.DATE_FORMAT_);
			}else{
				
				 planEndDate = DateUtil.addByMonDay(planBenDate, 1, 0, DateUtil.DATE_FORMAT_);
			}
			// 还款计划为闭区间(如2012-05-25--2012-05-24)
			if(SystemParm.PLAN_END==1){
				planBenDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// 还款计划为半开半闭区间(如2012-05-25--2012-05-25)
			if(SystemParm.PLAN_END==0){
				planBenDate = planEndDate;
			}
			
			// 结束日期
			if(i==terms-1){
				// 设置最后一期的结束日期
				// 最后一期使用闭区间
				if(SystemParm.LAST_TERM_OPEN==0){
					planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
				}
				// 最后一期使用开区间
				if(SystemParm.LAST_TERM_OPEN==1){
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}
				// 如果延期设置借据的最后一期的结束日期
				if(StringUtil.isNotEmpty(delayDate)){
					planBean.setEndDate(delayDate);
				}
			}else{
				// 非最后期的结束日期
				planBean.setEndDate(planEndDate);
			}
		
			// 客户号
			planBean.setCifNo(acLnMstBean.getCifNo());
			planBean.setDueNo(acLnMstBean.getDueNo());
			// 借据金额
			planBean.setDueAmt(acLnMstBean.getDueBal());
			// 本息合计
			String TotalMoney = interest.getInterest(planBean,terms, BigNumberUtil.Subtract(acLnMstBean.getDueBal(),remaMoney),acLnMstBean);
			// 当期利息
			String interestMoney =   interest.getInterestByMonth(planBean,BigNumberUtil.Subtract(acLnMstBean.getDueBal(),remaMoney),acLnMstBean);
			// 应还利息
			planBean.setReturnInterest(interestMoney);
			String capital = BigNumberUtil.Subtract(TotalMoney, interestMoney);
			capital = BigNumberUtil.Divide(capital, "1", 2, "1");
			// 应还本金
			planBean.setReturnCapital(capital);
			remaMoney = BigNumberUtil.Add(remaMoney,capital);
			// 借据余额
			planBean.setDueBal(remaMoney);
			// 合同号
			planBean.setPactNo(acLnMstBean.getPactNo());
			// 还款计划状态
			planBean.setState("0");
			// 本息合计
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
			// 账户管理费
			planBean.setAccFee(getFee(acLnMstBean));
			if(i<13){
 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
 			}
			
			planBeans.add(planBean);
		}
		Collections.sort(planBeans);
		// 单独处理第一期
		if(dealAdvanceType==0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(advaceInterest,planBeans.get(0).getReturnInterest()));
			planBeans.get(0).setTotal(BigNumberUtil.Add(planBeans.get(0).getReturnCapital(), planBeans.get(0).getReturnInterest()));
		}
		// 单独处理第一期的利息
		if(dealAdvanceType==1){
			String interestMoney = interest.getInterestByDay(planBeans.get(0), acLnMstBean.getDueBal(), acLnMstBean);
			planBeans.get(0).setReturnInterest(interestMoney);
			planBeans.get(0).setTotal(BigNumberUtil.Add(planBeans.get(0).getReturnCapital(), planBeans.get(0).getReturnInterest()));
		}
		// 单独处理第一期
		if(dealAdvanceType==2){
			String interestMoney = interest.getInterestByDay(planBeans.get(0), acLnMstBean.getDueBal(), acLnMstBean);
			planBeans.get(0).setReturnInterest(interestMoney);
			planBeans.get(0).setTotal(BigNumberUtil.Add(planBeans.get(0).getReturnCapital(), planBeans.get(0).getReturnInterest()));
		}
		// 延期部分不收取利息处理
		if(rComLastTerm){
			int planBeansSize = planBeans.size();
			PlanBean planBean = planBeans.get(planBeansSize-1);
			String endDate = planBean.getEndDate();
			planBean.setEndDate(acLnMstBean.getDueEndDate());
			// 从新计算最后一期的利息
			String interestMoney = interest.getInterestByDay(planBean, planBean.getDueBal(), acLnMstBean);
			planBeans.get(planBeansSize-1).setReturnInterest(interestMoney);
			planBeans.get(planBeansSize-1).setTotal(BigNumberUtil.Add(planBeans.get(planBeansSize-1).getReturnInterest(),planBeans.get(planBeansSize-1).getReturnCapital()));
			planBean.setEndDate(endDate);
		}
		
		
		return planBeans;
	}
	
	
	/**
	 * 
	 * 方法描述： 获得默认期数 
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-24 下午12:02:57
	 */
	private String defaultTerm(AcLnMstBean acLnMstBean){
		// 还款计划第一期的开始日期(默认和借据的开始日期相同)
		String begDate = acLnMstBean.getDueBegDate();
		// 借据的开始日期
		String endDate = acLnMstBean.getDueEndDate();
		int[]  monthAndDays = DateUtil.getMonthsAndDays(begDate, endDate);
		if(monthAndDays[1]>0){
			return String.valueOf(monthAndDays[0]+1);
		}else{
			return String.valueOf(monthAndDays[0]);
		}
	} 
	
	
	/**
	 * 
	 * 方法描述：  获得需要还款的列表
	 * @return
	 * List<PlanBean>
	 * @author 乾之轩
	 * @date 2012-5-26 上午10:52:35
	 */
	public List<PlanBean>   getNormalPlanList(){
		return planDao.getNormalPlanList(SystemParm.SystemDate);
	}
	
	
	
	
	/**
	 * 
	 * 方法描述： 根据费用计算公式来计算费用
	 * @param feeTypeBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-6-19 上午10:01:20
	 */
	private String getFee(AcLnMstBean acLnMstBean){
		String  feeValue = "0.00"; 
		
		feeValue = BigNumberUtil.Multiply(acLnMstBean.getDueAmt(), StringUtil.KillNull(acLnMstBean.getAccFee(), "0.00"));
		if(!StringUtil.equals(feeValue, "0.00")){
			feeValue = BigNumberUtil.Divide(feeValue, "100", 2, "1");
		}
		
		
		
		/**
		// 费用计算服务类
		FeeServices feeServices = SpringFactory.getBean("feeServices");
		// 根据计算实体id获得计算实体
		FeeTypeBean tempFeeTypeBean = new FeeTypeBean();
		tempFeeTypeBean.setFeeId(feeId);
		FeeTypeBean feeTypeBean = feeServices.getFeeTypeBean(tempFeeTypeBean);
		// 用公式计算
		if(StringUtil.isNotEmpty(feeTypeBean.getFeeExpression()) ){
			
		}else{
		// 固定值 
			feeValue = feeTypeBean.getFeeValue();
		}
		**/
		return feeValue;
	}
	/**
	 * 履约保证金额
	 */
   private String  getPerfAmount(AcLnMstBean acLnMstBean){
	   
	   String  perfAmount = "0.00"; 
		
	   perfAmount = BigNumberUtil.Multiply(acLnMstBean.getDueAmt(), StringUtil.KillNull(acLnMstBean.getPerfAmountPerc(), "0.00"));
		if(!StringUtil.equals(perfAmount, "0.00")){
			perfAmount = BigNumberUtil.Divide(perfAmount, "100", 2, "1");
		}
		
		return perfAmount;
	}
	
	
	/**
	 * 
	 * 方法描述： 批量更新还款计划的状态和欠款标志 
	 * @param state
	 * @param isDebt
	 * @param dueNo
	 * @param planBeans
	 * void
	 * @author 乾之轩
	 * @date 2012-6-22 上午09:55:37
	 */
	public void updatePlanList(String state, String isDebt,String dueNo, List<PlanBean> planBeans){
		planDao.updatePlanList(state, isDebt, dueNo, planBeans);
	}  
	
	/**
	 * 
	 * 方法描述： 更新还款计划 
	 * @param planBean
	 * void
	 * @author 乾之轩
	 * @date 2012-6-22 上午11:33:32
	 */
	public void updatePlanBean(PlanBean planBean){
		planDao.updatePlan(planBean);
	}
	
	
	public  PlanBean   getPlanBean(PlanBean parmPlanBean){
		return 	planDao.getPlanBean(parmPlanBean);
	}
	
	
	public List<PlanBean>  getPlanBeanList(PlanBean parmPlanBean) {
		return planDao.getPlanBeanList(parmPlanBean);
	}
	
	
	

}
