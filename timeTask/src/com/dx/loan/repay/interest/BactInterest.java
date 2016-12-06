/**
 * Copyright (C) DXHM 版权所有

 * 文件名： InterestCom.java
 * 包名： com.dx.loan.repay.interest
 * 说明：

 * @author 乾之轩

 * @date 2012-5-10 上午10:49:09
 * @version V1.0
 */
package com.dx.loan.repay.interest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dx.common.SystemParm;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.dayrate.services.impl.DayRateServices;
import com.dx.loan.debt.bean.DebtBean;
import com.dx.loan.debt.services.impl.DebtServices;
import com.dx.loan.normrate.bean.PartParmBean;
import com.dx.loan.normrate.bean.RateAdjustBean;
import com.dx.loan.normrate.services.impl.NormRateServices;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.dao.IRepayDao;
import com.dx.loan.repay.services.IRepayService;
import com.dx.loan.repay.services.impl.RepayService;



/**
 * 类名： InterestCom
 * 描述： 内部利息利息计算
 * @author 乾之轩

 * @date 2012-5-10 上午10:49:09
 *
 *
 */
public class BactInterest {

	
	
	public RatedayBean getDayInterest(PlanBean planBean){
		 String  occDate = SystemParm.SystemDate;
		 RatedayBean ratedayBean = new RatedayBean();
		 IRepayDao repayDao = SpringFactory.getBean("repayDao");
		 IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		 //根据借据号获得贷款主文件
		 AcLnMstBean acLnMstBean = repayDao.getAcLnMstBeanByDueNo(planBean.getDueNo());
		 // 获得计息方式
		 String returnMethod = acLnMstBean.getReturnMethod();
		 // 利随本清
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR,returnMethod)){
			 // 正常利息
			 String interest = profitsClearInterest(planBean,acLnMstBean,false);
			 // 逾期利息
			 String overInterest  = profitsClearOverInterest(acLnMstBean);
			 // 复利利息
			 String cmpdInterest  = profitsClearCmpdInterest(acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(acLnMstBean.getDueBal());
			 if(DateUtil.gt(planBean.getEndDate(), occDate)){
				 // 逾期
				 ratedayBean.setState("1");
			 }else{
				 // 没有 逾期
				 ratedayBean.setState("0");
			 }
		 }
		 
		 // 按计划
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_PLAN, returnMethod)){
			 String interest = planInterest(planBean,acLnMstBean,false);
			 // 逾期利息
			 String overInterest  = planOverInterest(planBean ,acLnMstBean);
			 // 复利利息
			 String cmpdInterest  = planCmpdInterest(planBean,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			 
			// if(DateUtil.gt(planBean.getEndDate(), occDate) && StringUtil.equals("1", acLnMstBean.getIsForce())){
			  if(DateUtil.gt(planBean.getEndDate(), occDate)){
				 // 逾期
				 ratedayBean.setState("1");
			 }else{
				 // 没有 逾期
				 ratedayBean.setState("0");
			 }
		 }
		 
		 //一次偿还本金按月结息
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, returnMethod)){
			 String interest = oneMonthInterest(planBean,acLnMstBean,false);
			 // 逾期利息
			 String overInterest  = oneMonthOverInterest(planBean ,acLnMstBean);
			 // 复利利息
			 String cmpdInterest  = oneMonthCmpdInterest(planBean ,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			// if(DateUtil.gt(planBean.getEndDate(), occDate) && StringUtil.equals("1", acLnMstBean.getIsForce())){
			  if(DateUtil.gt(planBean.getEndDate(), occDate) ){
				 // 逾期
				 ratedayBean.setState("1");
			 }else{
				 // 没有 逾期
				 ratedayBean.setState("0");
			 }
		 }
		 
		//一次偿还本金季月结息
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_SEASON, returnMethod)){
			 String interest = oneMonthInterest(planBean,acLnMstBean,false);
			 // 复利利息
			 String cmpdInterest  = oneMonthCmpdInterest(planBean ,acLnMstBean);
			 // 逾期利息
			 String overInterest  = oneMonthOverInterest(planBean ,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			 if(DateUtil.gt(planBean.getEndDate(), occDate) && StringUtil.equals("1", acLnMstBean.getIsForce())){
				 // 逾期
				 ratedayBean.setState("1");
			 }else{
				 // 没有 逾期
				 ratedayBean.setState("0");
			 }
		 }
		 
		 // 等额本金
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_PRINCIPAL, returnMethod)){
			 String interest = matchPrincipalInterest(planBean,acLnMstBean,false);
			// 逾期利息
			 String overInterest  = matchPrincipalOverInterest(planBean ,acLnMstBean);
			 // 复利利息
			 String cmpdInterest  = matchPrincipalCmpdInterest(planBean ,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			 if(DateUtil.gt(planBean.getEndDate(), occDate)){
				 // 逾期
				 ratedayBean.setState("1");
			 }else{
				 // 没有 逾期
				 ratedayBean.setState("0");
			 }
			 
		 }
		 
		 // 等额本息
         if(StringUtil.equals(SystemParm.RETURNMETHOD_INTEREST, returnMethod)){
        	 String interest = monthPrincipalInterest(planBean,acLnMstBean,false);
        	// 逾期利息
			 String overInterest  = monthPrincipalOverInterest(planBean ,acLnMstBean);
			 
			 // 复利利息
			 String cmpdInterest  = monthPrincipalCmpdInterest(planBean ,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			 
			 if(DateUtil.gt(planBean.getEndDate(), occDate)){
				 // 逾期
				 ratedayBean.setState("1");
			 }else{
				 // 没有 逾期
				 ratedayBean.setState("0");
			 }
		 } 		 
		 
         ratedayBean.setDueNo(planBean.getDueNo());
         
         ratedayBean.setId(StringUtil.getPK("R"));
         ratedayBean.setOccureDate(occDate);
         ratedayBean.setPactNo(planBean.getPactNo());
         ratedayBean.setTermNo(planBean.getTermNo());
        
         RepayService repayService = SpringFactory.getBean("repayService");        
         // list[0] 累计还款本金
         // list[1] 累计还款利息
         // list[2] 累计还款逾期利息
         // list[3] 累计还款复利利息
         // list[4] 累计还款账户管理费
         List<String> hasRepayList = repayService.getHasRepay(planBean.getDueNo(), planBean.getTermNo());
         // 该期实际本金减去已换本金
         ratedayBean.setCapital(BigNumberUtil.Subtract(ratedayBean.getCapital(),hasRepayList.get(0)));
         // 该期账户管理费减去已经偿还的账户管理费
         ratedayBean.setAccFee(BigNumberUtil.Subtract(StringUtil.KillNull(ratedayBean.getAccFee(), "0.00"), hasRepayList.get(4)));
         
		 return ratedayBean;
	}
	
	/****************************日终批量利息计算****************************/
	/**
	 * 
	 * 方法描述： 利随本清逾期利息 (欠本金*逾期天数*逾期利率)
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-22 下午07:27:49
	 */
	private String profitsClearOverInterest(AcLnMstBean acLnMstBean){
		String overInterest = "0.00";
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		String overRate = acLnMstBean.getOverRate();
		// 获得欠款本金
		String dueBal = acLnMstBean.getDueBal();
		String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
		String begDate = acLnMstBean.getDueEndDate();
		// 获得利率服务类
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		// 根据利率编号获得利率调整列表
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		// 根据利率调整列表和计息方式获得生效点列表
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		// 获得落在借据结束日期和当前日期之间的生效点
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
		// 获得距离借据结束日期最近的一次利率调整
		PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, begDate);
		if(latePartParmBean!=null){
			// 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间
			if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
				overRate = latePartParmBean.getOverRate();
			}
		}
		
		if(filterEffectDates!=null && filterEffectDates.size()>0){
			int size = filterEffectDates.size();
			
			for (int i = 0; i <size; i++) {
				if(i<size-1){
					int days =  0;
					int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
					if(monthAndDay[1]>0 && monthOfDays==1){
						days  =  monthAndDay[0]*30+monthAndDay[1];
					}else{
						days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
					}
					// 本金*天数
					String tempOverInterest = BigNumberUtil.Multiply(dueBal, String.valueOf(days));
					// 将逾期利率转化为日利率
					String dayOverRate = monthRate2DayRate(overRate);
					// 本金*天数*利率
					tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, dayOverRate);
					tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
					overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
					
					begDate = filterEffectDates.get(i).getEffectDate();
					overRate = latePartParmBean.getOverRate();
				}else{
					int days =  0;
					int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
					if(monthAndDay[1]>0 && monthOfDays==1){
						days  =  monthAndDay[0]*30+monthAndDay[1];
					}else{
						days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
					}
					// 本金*天数
					String tempOverInterest = BigNumberUtil.Multiply(dueBal, String.valueOf(days));
					// 将逾期利率转化为日利率
					String dayOverRate = monthRate2DayRate(overRate);
					// 本金*天数*利率
					tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, dayOverRate);
					tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
					overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
				}
 			}
		}else{
			int days =  0;
			int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
			if(monthAndDay[1]>0 && monthOfDays ==1){
				days  =  monthAndDay[0]*30+monthAndDay[1];
			}else{
				days = DateUtil.getBetweenDays(begDate, endDate);
			}
			// 本金*天数
			String tempOverInterest = BigNumberUtil.Multiply(dueBal,  String.valueOf(days));
			// 将逾期利率转化为日利率
			String dayOverRate = monthRate2DayRate(overRate);
			// 本金*天数*利率
			tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, dayOverRate);
			tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
			overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		}
		
		return  overInterest;
	}
	/**
	 * 
	 * 方法描述： 按计划的逾期利息 
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-23 上午11:03:39
	 */
	private  String planOverInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		     int monthOfDays = SystemParm.MONTH_OF_DAYS;
			 String overInterest = "0.00";
			 // 借据剩余金额
			 String dueBal  = acLnMstBean.getDueBal();
			 /**
			  * 获得期号小余当前期号且已经逾期的信息
			  */
			 // 计算逾期的基数
			 String baseOver="0.00";
			 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
			 RatedayBean ratedayBean = new RatedayBean(); 
			 ratedayBean.setDueNo(planBean.getDueNo());
			 ratedayBean.setTermNo(planBean.getTermNo());
			 // 状态为逾期
			 ratedayBean.setState("1");
			 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
			 // 按照发生日期排序
			 
			 Collections.sort(ratedayBeans);
			 int termno = Integer.parseInt(planBean.getTermNo());
			 
			 for(int i=1;i<termno;++i){
				 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
				if (myRatedayBean!=null) {
					baseOver = BigNumberUtil.Add(baseOver,myRatedayBean.getInterest(),myRatedayBean.getOverInterest(),myRatedayBean.getCmpdInterest(),myRatedayBean.getAccFee());
				}
			 }
			 
			 baseOver = BigNumberUtil.Add(baseOver,dueBal,planBean.getAccFee());
			 
			 
			 // 计算逾期利息的结束日期
			 String endDate = SystemParm.SystemDate;	
		     // 获得逾期利率
		     String overRate = acLnMstBean.getOverRate();
		 	 // 获得利率服务类
			 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
			 // 根据利率编号获得利率调整列表
			 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
			 // 根据利率调整列表和计息方式获得生效点列表
			// List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
			 // 江川不进行利率调整 
			 List<PartParmBean> effectDates = null;
		     
		     // 是强制计划
		     if(SystemParm.IS_FORCE==1){
		    	// 计算逾期利息的开始 (该期计划的结束日期)
	    		 String begDate = planBean.getEndDate(); 
		    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
				 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
				 // 获得距离借据结束日期最近的一次利率调整
				 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
				 if(latePartParmBean!=null){
					 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
					 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
						 overRate = latePartParmBean.getOverRate();
					 }
				 }
				 //当前的日期已经超过该期的结束日期
		    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
		    		   
		    		 // 如果有利率调整
		    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
		    			 int size = filterEffectDates.size();
			    		 for(int i=0;i<size;++i){
			    			 if(i<size-1){
					    		 int days =  0;
								 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
								 if(monthAndDay[1]>0 && monthOfDays==1){
									days  =  monthAndDay[0]*30+monthAndDay[1];
								 }else{
								 	days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
								 }
					    		 
					    		 
					    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
					    		 String overDayRate = monthRate2DayRate(overRate);
					    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
					    		 if(StringUtil.equals("0.00", tempOverInterest)){
					    			 tempOverInterest = "0.00";
					    		 }else{
					    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
					    		 }
					    		 
					    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
					    		 
					    		 begDate = filterEffectDates.get(i).getEffectDate();
					    		 overRate = filterEffectDates.get(i).getOverRate();
			    			 }else{
			    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
			    				 int days =  0;
								 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
								 if(monthAndDay[1]>0 && monthOfDays==1){
									days  =  monthAndDay[0]*30+monthAndDay[1];
								 }else{
								 	days = DateUtil.getBetweenDays(begDate, endDate);
								 }
			    				 
			    				 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
					    		 String overDayRate = monthRate2DayRate(overRate);
					    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
					    		 if(StringUtil.equals("0.00", tempOverInterest)){
					    			 tempOverInterest = "0.00";
					    		 }else{
					    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
					    		 }
					    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
			    			 }
			    		 }
		    		 }else{// 没有利率调整
		    			 int days =  0;
		    			 // 有过还款
		    			 if(StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())){
		    				 begDate = acLnMstBean.getLastReturnDate();
		    			 }
						 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
						 if(monthAndDay[1]>0 && monthOfDays==1){
							days  =  monthAndDay[0]*30+monthAndDay[1];
						 }else{
						 	days = DateUtil.getBetweenDays(begDate, endDate);
						 }
	    				 
		    			 
			    		 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempOverInterest)){
			    			 tempOverInterest = "0.00";
			    		 }else{
			    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
			    		 }
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    		 }
			     }
		     }else{// 不是强制计划
		    	 // 计算逾期利息的开始日期(借据结束日期)
		    	 String begDate = acLnMstBean.getDueEndDate();
		    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
				 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
				 // 获得距离借据结束日期最近的一次利率调整
				 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, planBean.getEndDate());
				 if(latePartParmBean!=null){
					 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
					 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
						 overRate = latePartParmBean.getOverRate();
					 }
				 }
				 
				 // 如果有利率调整
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
				    		 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days =  0;
							 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
							 if(monthAndDay[1]>0 && monthOfDays==1){
								days  =  monthAndDay[0]*30+monthAndDay[1];
							 }else{
							 	days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
							 }
				    		 
				    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 
				    		 if(StringUtil.equals("0.00", tempOverInterest)){
				    			 tempOverInterest = "0.00";
				    		 }else{
				    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
				    		 }
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 overRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
		    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
				    		
		    				 int days =  0;
							 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
							 if(monthAndDay[1]>0 && monthOfDays==1){
								days  =  monthAndDay[0]*30+monthAndDay[1];
							 }else{
							 	days = DateUtil.getBetweenDays(begDate, endDate);
							 }
		    				 
		    				 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempOverInterest)){
				    			 tempOverInterest = "0.00";
				    		 }else{
				    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
				    		 }
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    			 }
		    		 }
	    		 }else{// 没有利率调整
	    			 
	    			 int days =  0;
					 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
					 if(monthAndDay[1]>0 && monthOfDays==1){
						days  =  monthAndDay[0]*30+monthAndDay[1];
					 }else{
					 	days = DateUtil.getBetweenDays(begDate, endDate);
					 }
		    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(overRate);
		    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempOverInterest)){
		    			 tempOverInterest = "0.00";
		    		 }else{
		    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
		    		 }
		    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
	    		 }
		     }
		     return overInterest;
	}
	
/**
 * 
 * 方法描述： 等额本金逾期利息计算
 * @param planBean
 * @param acLnMstBean
 * @return
 * String
 * @author 乾之轩
 * @date 2012-5-23 下午03:18:24
 */
private String  matchPrincipalOverInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
	
	int monthOfDays = SystemParm.MONTH_OF_DAYS;
	 String overInterest = "0.00";
	 // 借据剩余金额
	 String dueBal  = acLnMstBean.getDueBal(); 
	 // 计算逾期利息的结束日期
	 // String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);	
	    String endDate = SystemParm.SystemDate;	
     // 获得逾期利率
     String overRate = acLnMstBean.getOverRate();
 	 // 获得利率服务类
	 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
	 // 根据利率编号获得利率调整列表
	 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
	 // 根据利率调整列表和计息方式获得生效点列表
	 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
     
    	//计算逾期利息的开始 (该期计划的结束日期)
		 String begDate = planBean.getEndDate(); 
    	 // 如果不是强制计划
		 if (SystemParm.IS_FORCE==0) {
    		 begDate = acLnMstBean.getDueEndDate();
		 }
		 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
		 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
		 // 获得距离借据结束日期最近的一次利率调整
		 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
		 if(latePartParmBean!=null){
			 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
			 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
				 overRate = latePartParmBean.getOverRate();
			 }
		 }
		 //当前的日期已经超过该期的结束日期
    	 if(DateUtil.gteq(begDate, endDate)){
    		   
    		 // 如果有利率调整
    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
    			 int size = filterEffectDates.size();
	    		 for(int i=0;i<size;++i){
	    			 if(i<size-1){
			    		 // String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
			    		 
			    		 int days =  0;
						 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
						 if(monthAndDay[1]>0 && monthOfDays==1){
							days  =  monthAndDay[0]*30+monthAndDay[1];
						 }else{
						 	days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
						 }
			    		 
			    		 
			    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
			    		 
			    		 
			    		 begDate = filterEffectDates.get(i).getEffectDate();
			    		 overRate = filterEffectDates.get(i).getOverRate();
	    			 }else{
	    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
			    		 
	    				 int days =  0;
						 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
						 if(monthAndDay[1]>0 && monthOfDays==1){
							days  =  monthAndDay[0]*30+monthAndDay[1];
						 }else{
						 	days = DateUtil.getBetweenDays(begDate, endDate);
						 }
	    				 
	    				 
	    				 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
	    			 }
	    		 }
    		 }else{// 没有利率调整
    			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
    			 int days =  0;
				 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
				 if(monthAndDay[1]>0 && monthOfDays==1){
					days  =  monthAndDay[0]*30+monthAndDay[1];
				 }else{
				 	days = DateUtil.getBetweenDays(begDate, endDate);
				 }
    			 
	    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
	    		 String overDayRate = monthRate2DayRate(overRate);
	    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
	    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
	    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
    		 }
	     }
    	 return overInterest;
	}

	/**
	 * 
	 * 方法描述： 等额本息逾期利息计算 
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-23 下午03:20:43
	 */
	private String monthPrincipalOverInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		int isForce = SystemParm.IS_FORCE;
		String overInterest = "0.00";
		 // 借据剩余金额
		 String dueBal  = acLnMstBean.getDueBal(); 
		 // 计算逾期的基数
		 String baseOver="0.00";
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 RatedayBean ratedayBean = new RatedayBean(); 
		 ratedayBean.setDueNo(planBean.getDueNo());
		 ratedayBean.setTermNo(planBean.getTermNo());
		 ratedayBean.setState("1");
		 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
		 
		 int termno = Integer.parseInt(planBean.getTermNo());
		 
		 for(int i=1;i<termno;++i){
			 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
			if (myRatedayBean!=null) {
				baseOver = BigNumberUtil.Add(baseOver,myRatedayBean.getInterest(),myRatedayBean.getOverInterest(),myRatedayBean.getCmpdInterest(),myRatedayBean.getAccFee());
			}
		 }
		 baseOver = BigNumberUtil.Add(baseOver,dueBal,planBean.getAccFee());
		 
		 
		 // 计算逾期利息的结束日期
		 String endDate = SystemParm.SystemDate;	
	     // 获得逾期利率
	     String overRate = acLnMstBean.getOverRate();
	 	 // 获得利率服务类
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // 根据利率编号获得利率调整列表
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // 根据利率调整列表和计息方式获得生效点列表
//		 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
	// 江川没有利率调整
		 List<PartParmBean> effectDates = null;
	     
	    	//计算逾期利息的开始 (该期计划的结束日期)
			 String begDate = planBean.getEndDate(); 
			 if (isForce==0) {
				 begDate =acLnMstBean.getDueEndDate();
			}
			 
	    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // 获得距离借据结束日期最近的一次利率调整
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
			 if(latePartParmBean!=null){
				 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 overRate = latePartParmBean.getOverRate();
				 }
			 }
			 //当前的日期已经超过该期的结束日期
	    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
	    		   
	    		 // 如果有利率调整
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
//				    		 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
		    				 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate()); 
				    		 if(monthOfDays==1){
				    			 days = monthAndDay[0]*30+monthAndDay[1];
				    		 }else{
				    			 days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 }
				    		 
				    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
				    		 
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 overRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
		    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
				    		 
		    				 int days = 0;
		    				 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate); 
				    		 if(monthOfDays==1){
				    			 days = monthAndDay[0]*30+monthAndDay[1];
				    		 }else{
				    			 days = DateUtil.getBetweenDays(begDate,endDate);
				    		 }
		    				 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    			 }
		    		 }
	    		 }else{// 没有利率调整
	    			 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    			 int days = 0;
	    			 if (StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())) {
							begDate = acLnMstBean.getLastReturnDate();
						}
    				 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate); 
		    		 if(monthOfDays==1){
		    			 days = monthAndDay[0]*30+monthAndDay[1];
		    		 }else{
		    			 days = DateUtil.getBetweenDays(begDate,endDate);
		    		 }
		    		 String tempOverInterest  = BigNumberUtil.Multiply(baseOver,String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(overRate);
		    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
		    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 10, "1");
		    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    		 overInterest = BigNumberUtil.Divide(overInterest, "1", 2, "1");
		    		 
	    		 }
		     }
	    	 return overInterest;
	}
	
	
	/*************日终复利************/
	/**
	 * 
	 * 方法描述： 利随本清复利利息 (欠本金*逾期天数*逾期利率)
	 * @param acLnMstBean
	 * interest 当期利息
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-22 下午07:27:49
	 */
	private String profitsClearCmpdInterest(AcLnMstBean acLnMstBean){
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		String cmpdInterest = "0.00";
		String cmpdRate = acLnMstBean.getCmpdRate();
		// 获得欠款本金
		String dueBal = acLnMstBean.getDueBal();
		//String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
		String endDate = SystemParm.SystemDate;
		String begDate = acLnMstBean.getDueEndDate();
		// 获得利率服务类
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		// 根据利率编号获得利率调整列表
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		// 根据利率调整列表和计息方式获得生效点列表
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		// 获得落在借据结束日期和当前日期之间的生效点
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
		// 获得距离借据结束日期最近的一次利率调整
		PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, begDate);
		if(latePartParmBean!=null){
			// 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间
			if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
				cmpdRate = latePartParmBean.getCmpRate();
			}
		}
		
		if(filterEffectDates!=null && filterEffectDates.size()>0){
			int size = filterEffectDates.size();
			for (int i = 0; i <size; i++) {
				if(i<size-1){
//					String days = String.valueOf(DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate()));
					int days =0; 
					int[] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
					
					if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
					}
					// 本金*天数
					String tempCmpdInterest = BigNumberUtil.Multiply(dueBal,String.valueOf(days));
					// 将逾期利率转化为日利率
					String dayOverRate = monthRate2DayRate(cmpdRate);
					// 本金*天数*利率
					tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, dayOverRate);
					tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
					cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
					
					begDate = filterEffectDates.get(i).getEffectDate();
					cmpdRate = latePartParmBean.getOverRate();
				}else{
					//String days = String.valueOf(DateUtil.getBetweenDays(begDate, endDate));
					
					int days =0; 
					int[] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
					
					if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate, endDate);
					}
					
					// 本金*天数
					String tempCmpdInterest = BigNumberUtil.Multiply(dueBal,String.valueOf(days));
					// 将逾期利率转化为日利率
					String dayOverRate = monthRate2DayRate(cmpdRate);
					// 本金*天数*利率
					tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, dayOverRate);
					tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
					cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
				}
 			}
		}else{
			// String days = String.valueOf(DateUtil.getBetweenDays(begDate, endDate));
			int days =0; 
			int[] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
			
			if (monthOfDays==1) {
				days = monthAndDay[0]*30+monthAndDay[1];
			} else {
				days = DateUtil.getBetweenDays(begDate, endDate);
			}
			
			// 本金*天数
			String tempCmpdInterest = BigNumberUtil.Multiply(dueBal,String.valueOf(days));
			// 将逾期利率转化为日利率
			String dayOverRate = monthRate2DayRate(cmpdRate);
			// 本金*天数*利率
			tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, dayOverRate);
			tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
			cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		}
		
		return  cmpdInterest;
	}
	/**
	 * 
	 * 方法描述： 按计划的逾期利息 
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-23 上午11:03:39
	 */
	private  String planCmpdInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
			int monthOfDays = SystemParm.MONTH_OF_DAYS;
			 String cmpdInterest = "0.00";
			 // 借据剩余金额
			 String dueBal  = acLnMstBean.getDueBal(); 
			 // 计算复利的基数
			 String baseCmpd = "0.00";
			 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
			 RatedayBean ratedayBean = new RatedayBean(); 
			 ratedayBean.setDueNo(planBean.getDueNo());
			 ratedayBean.setTermNo(planBean.getTermNo());
			 ratedayBean.setState("1");
			 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
			 
			 
			 int termno = Integer.parseInt(planBean.getTermNo());
			 
			 for(int i=1;i<termno;++i){
				 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
				if (myRatedayBean!=null) {
					baseCmpd = BigNumberUtil.Add(baseCmpd,myRatedayBean.getInterest());
				}
			 }
			 
			 
			 baseCmpd = BigNumberUtil.Add(baseCmpd,planBean.getReturnInterest());
			 
			 // 计算逾期利息的结束日期
			 String endDate = SystemParm.SystemDate;	
			 // 判断是不是强制计划 0 不是强制计划 1 是强制计划
		     int isForce = SystemParm.IS_FORCE;
		     // 获得逾期利率
		     String cmpdRate = acLnMstBean.getCmpdRate();
		 	 // 获得利率服务类
			 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
			 // 根据利率编号获得利率调整列表
			 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
			 // 根据利率调整列表和计息方式获得生效点列表
			 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		     
		     // 是强制计划
		     if(isForce==1){
		    	//计算逾期利息的开始 (该期计划的结束日期)
	    		 String begDate = planBean.getEndDate(); 
		    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
				 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
				 // 获得距离借据结束日期最近的一次利率调整
				 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
				 if(latePartParmBean!=null){
					 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
					 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
						 cmpdRate = latePartParmBean.getCmpRate();
					 }
				 }
				 //当前的日期已经超过该期的结束日期
		    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
		    		   
		    		 // 如果有利率调整
		    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
		    			 int size = filterEffectDates.size();
			    		 for(int i=0;i<size;++i){
			    			 if(i<size-1){
//					    		 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
					    		 int days = 0;
					    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
					    		 
					    		 if (monthOfDays==1) {
					    			 days = monthAndDay[0]*30+monthAndDay[1];
								} else {
									days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
								}
					    		 
					    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
					    		 String overDayRate = monthRate2DayRate(cmpdRate);
					    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
					    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
					    			 tempCmpdInterest = "0.00";
					    		 }else{
					    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
					    		 }
					    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
					    		 
					    		 
					    		 begDate = filterEffectDates.get(i).getEffectDate();
					    		 cmpdRate = filterEffectDates.get(i).getOverRate();
			    			 }else{
			    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
			    				 int days = 0;
					    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
					    		 
					    		 if (monthOfDays==1) {
					    			 days = monthAndDay[0]*30+monthAndDay[1];
								} else {
									days = DateUtil.getBetweenDays(begDate,endDate);
								}
					    		 
					    		 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd,String.valueOf(days)); 
					    		 String overDayRate = monthRate2DayRate(cmpdRate);
					    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
					    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
					    			 tempCmpdInterest = "0.00";
					    		 }else{
					    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
					    		 }
					    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
			    			 }
			    		 }
		    		 }else{// 没有利率调整
		    			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    			 int days = 0;
		    			 if(StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())){
		    				begDate = acLnMstBean.getLastReturnDate(); 
		    			 }
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
			    		 
			    		 if (monthOfDays==1) {
			    			 days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,endDate);
						}
			    		 
		    			 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
			    			 tempCmpdInterest = "0.00";
			    		 }else{
			    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
			    		 }
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		    		 }
			     }
		     }else{// 不是强制计划
		    	 // 计算逾期利息的开始日期(借据结束日期)
		    	 String begDate = acLnMstBean.getDueEndDate();
		    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
				 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
				 // 获得距离借据结束日期最近的一次利率调整
				 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, planBean.getEndDate());
				 if(latePartParmBean!=null){
					 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
					 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
						 cmpdRate = latePartParmBean.getCmpRate();
					 }
				 }
				 
				 // 如果有利率调整
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
				    		 // String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 
				    		 if (monthOfDays==1) {
				    			 days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
							}
				    		 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
				    			 tempCmpdInterest = "0.00";
				    		 }else{
				    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
				    		 }
				    		 
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 cmpdRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
		    				//  String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    				 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
				    		 
				    		 if (monthOfDays==1) {
				    			 days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,endDate);
							}
		    				 
		    				 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
				    			 tempCmpdInterest = "0.00";
				    		 }else{
				    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
				    		 }
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		    			 }
		    		 }
	    		 }else{// 没有利率调整
	    			 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    			 int days = 0;
		    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
		    		 
		    		 if (monthOfDays==1) {
		    			 days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate,endDate);
					}
	    			 
		    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(cmpdRate);
		    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
		    			 tempCmpdInterest = "0.00";
		    		 }else{
		    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
		    		 }
		    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    		 }
		     }
		     return cmpdInterest;
	}
	
/**
 * 
 * 方法描述： 等额本金逾期利息计算
 * @param planBean
 * @param acLnMstBean
 * @return
 * String
 * @author 乾之轩
 * @date 2012-5-23 下午03:18:24
 */
private String  matchPrincipalCmpdInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
	int isForce = SystemParm.IS_FORCE;
	int monthOfDays = SystemParm.MONTH_OF_DAYS;
	String cmpdInterest = "0.00";
	 // 借据剩余金额
	 String dueBal  = acLnMstBean.getDueBal(); 
	 // 计算逾期利息的结束日期
	 String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);	

     // 获得逾期利率
     String cmpdRate = acLnMstBean.getCmpdRate();
 	 // 获得利率服务类
	 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
	 // 根据利率编号获得利率调整列表
	 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
	 // 根据利率调整列表和计息方式获得生效点列表
	 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
     
    	//计算逾期利息的开始 (该期计划的结束日期)
		 String begDate = planBean.getEndDate(); 
    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
		 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
		 // 获得距离借据结束日期最近的一次利率调整
		 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
		 if(latePartParmBean!=null){
			 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
			 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
				 cmpdRate = latePartParmBean.getCmpRate();
			 }
		 }
		 //当前的日期已经超过该期的结束日期
    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
    		   
    		 // 如果有利率调整
    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
    			 int size = filterEffectDates.size();
	    		 for(int i=0;i<size;++i){
	    			 if(i<size-1){
			    		 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
			    		 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+ monthAndDay[1];
						} else {
							days =DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
						}
			    		 
			    		 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
			    		 
			    		 
			    		 begDate = filterEffectDates.get(i).getEffectDate();
			    		 cmpdRate = filterEffectDates.get(i).getOverRate();
	    			 }else{
	    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    				 
	    				 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+ monthAndDay[1];
						} else {
							days =DateUtil.getBetweenDays(begDate,endDate);
						}
	    				 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    			 }
	    		 }
    		 }else{// 没有利率调整
    			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
    			 
    			 int days = 0;
	    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
	    		 if (monthOfDays==1) {
					days = monthAndDay[0]*30+ monthAndDay[1];
				} else {
					days =DateUtil.getBetweenDays(begDate,endDate);
				}
	    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
	    		 String overDayRate = monthRate2DayRate(cmpdRate);
	    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
	    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
	    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
    		 }
	     }
    	 return cmpdInterest;
	}

	/**
	 * 
	 * 方法描述： 等额本息逾期利息计算 
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-23 下午03:20:43
	 */
	private String monthPrincipalCmpdInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		int isForce = SystemParm.IS_FORCE;
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		String cmpdInterest = "0.00";
		 // 借据剩余金额
		 String dueBal  = acLnMstBean.getDueBal(); 
		 String baseCmpd = "0.00";
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 RatedayBean ratedayBean = new RatedayBean(); 
		 ratedayBean.setDueNo(planBean.getDueNo());
		 ratedayBean.setTermNo(planBean.getTermNo());
		 ratedayBean.setState("1");
		 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
		 
		 int termno = Integer.parseInt(planBean.getTermNo());
		 
		 for(int i=1;i<termno;++i){
			 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
			if (myRatedayBean!=null) {
				baseCmpd = BigNumberUtil.Add(baseCmpd,myRatedayBean.getInterest());
			}
		 }
		 baseCmpd = BigNumberUtil.Add(baseCmpd,planBean.getReturnInterest());
		 // 计算逾期利息的结束日期
		 String endDate = SystemParm.SystemDate;	
	     // 获得逾期利率
	     String cmpdRate = acLnMstBean.getCmpdRate();
	 	 // 获得利率服务类
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // 根据利率编号获得利率调整列表
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // 根据利率调整列表和计息方式获得生效点列表
		// List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		 // 江川没有利率调整
		 List<PartParmBean> effectDates = null;
	     
	    	//计算逾期利息的开始 (该期计划的结束日期)
			 String begDate = planBean.getEndDate(); 
			 if (isForce==0) {
				begDate = acLnMstBean.getDueEndDate();
			}
	    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // 获得距离借据结束日期最近的一次利率调整
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
			 if(latePartParmBean!=null){
				 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 cmpdRate = latePartParmBean.getCmpRate();
				 }
			 }
			 //当前的日期已经超过该期的结束日期
	    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
	    		   
	    		 // 如果有利率调整
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
//				    		 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days =DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
							}
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
				    		 
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 cmpdRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
//		    				 String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    				 
		    				 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days =DateUtil.getBetweenDays(begDate,endDate);
							}
		    				 
		    				 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 10, "1");
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		    			 }
		    		 }
	    		 }else{// 没有利率调整
	    			 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    		
	    			 int days = 0;
	    			 if (StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())) {
							begDate = acLnMstBean.getLastReturnDate();
						}
		    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
		    		 if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days =DateUtil.getBetweenDays(begDate,endDate);
					}
	    			 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd,String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(cmpdRate);
		    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
		    			 tempCmpdInterest = "0.00";
		    		 }else{
		    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
		    		 }
		    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    		 }
		     }
	    	 return cmpdInterest;
	} 

	/**
	 * 
	 * 方法描述： 一次偿还本金按月/季结息
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-23 下午04:01:26
	 */
	private String oneMonthCmpdInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		int monthOfDays = SystemParm.MONTH_OF_DAYS; 
		String cmpdInterest = "0.00";
		 // 借据剩余金额
		 String dueBal  = acLnMstBean.getDueBal(); 
		 // 计算复利的基数
		 String baseCmpd = "0.00";
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 RatedayBean ratedayBean = new RatedayBean(); 
		 ratedayBean.setDueNo(planBean.getDueNo());
		 ratedayBean.setTermNo(planBean.getTermNo());
		 ratedayBean.setState("1");
		 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
		 
		 int termno = Integer.parseInt(planBean.getTermNo());
		 
		 for(int i=1;i<termno;++i){
			 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
			if (myRatedayBean!=null) {
				baseCmpd = BigNumberUtil.Add(baseCmpd,myRatedayBean.getInterest());
			}
		 }
		 baseCmpd = BigNumberUtil.Add(baseCmpd,planBean.getReturnInterest());
		 // 计算逾期利息的结束日期
		 String endDate = SystemParm.SystemDate;	
		 // 判断是不是强制计划 0 不是强制计划 1 是强制计划
	     int isForce = SystemParm.IS_FORCE;
	     // 获得逾期利率
	     String cmpdRate = acLnMstBean.getCmpdRate();
	 	 // 获得利率服务类
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // 根据利率编号获得利率调整列表
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // 根据利率调整列表和计息方式获得生效点列表
	//	 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		// 江川没有利率调整
		 	 List<PartParmBean> effectDates = null;
	     
	     // 是强制计划
	     if(isForce==1){
	    	//计算逾期利息的开始 (该期计划的结束日期)
	    	 String begDate = planBean.getEndDate(); 
	    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // 获得距离借据结束日期最近的一次利率调整
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
			 if(latePartParmBean!=null){
				 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 cmpdRate = latePartParmBean.getCmpRate();
				 }
			 }
			 //当前的日期已经超过该期的结束日期
	    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
	    		   
	    		 // 如果有利率调整
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
//				    		 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
							}
				    		 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
				    			 tempCmpdInterest ="0.00";
				    		 }else{
				    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
				    		 }
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
				    		 
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 cmpdRate = filterEffectDates.get(i).getCmpRate();
		    			 }else{
		    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    				 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
				    		 
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,endDate);
							}
		    				 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
				    			 tempCmpdInterest ="0.00";
				    		 }else{
				    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
				    		 }
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		    			 }
		    		 }
	    		 }else{// 没有利率调整
//	    			 String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    			 
	    			 int days = 0;
	    			 if (StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())) {
							begDate = acLnMstBean.getLastReturnDate();
						}
		    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
		    		 
		    		 if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate,endDate);
					}
	    			 
		    		 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd, String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(cmpdRate);
		    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
		    			 tempCmpdInterest = "0.00";
		    		 }else{
		    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
		    		 }
		    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    		 }
		     }
	     }else{// 不是强制计划
	    	 // 计算逾期利息的开始日期(借据结束日期)
	    	 String begDate = acLnMstBean.getDueEndDate();
	    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // 获得距离借据结束日期最近的一次利率调整
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, planBean.getEndDate());
			 if(latePartParmBean!=null){
				 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 cmpdRate = latePartParmBean.getCmpRate();
				 }
			 }
			 
			 // 如果有利率调整
   		 if(filterEffectDates!=null && filterEffectDates.size()>0){
   			 int size = filterEffectDates.size();
	    		 for(int i=0;i<size;++i){
	    			 if(i<size-1){
			    		 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
			    		 
			    		 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
			    		 
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
						}
			    		 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 
			    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
			    			 tempCmpdInterest = "0.00";
			    		 }else{
			    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
			    		 }
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
			    		 
			    		 begDate = filterEffectDates.get(i).getEffectDate();
			    		 cmpdRate = filterEffectDates.get(i).getCmpRate();
	    			 }else{
	    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    				 
	    				 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
			    		 
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,endDate);
						}
	    				 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
			    			 tempCmpdInterest = "0.00";
			    		 }else{
			    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
			    		 }
			    		 
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    			 }
	    		 }
   		 }else{// 没有利率调整
   			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
   			 int days = 0;
    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
    		 
    		 if (monthOfDays==1) {
				days = monthAndDay[0]*30+monthAndDay[1];
			} else {
				days = DateUtil.getBetweenDays(begDate,endDate);
			}
			 
    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
   			 
	    		 String overDayRate = monthRate2DayRate(cmpdRate);
	    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
	    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
	    			 tempCmpdInterest = "0.00";
	    		 }else{
	    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
	    		 }
	    		 
	    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
   		 }
	     }
	     return cmpdInterest;
	}
	
	
	/*************日终复利************/
	
	
	
	

	/**
	 * 
	 * 方法描述： 一次偿还本金按月/季结息
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-23 下午04:01:26
	 */
	private String oneMonthOverInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		int isForce = SystemParm.IS_FORCE;
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		String overInterest = "0.00";
		 // 借据剩余金额
		 String dueBal  = acLnMstBean.getDueBal(); 
		 // 计算逾期的基数
		 String baseOver="0.00";
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 RatedayBean ratedayBean = new RatedayBean(); 
		 ratedayBean.setDueNo(planBean.getDueNo());
		 ratedayBean.setTermNo(planBean.getTermNo());
		 ratedayBean.setState("1");
		 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
		 
		 
		 int termno = Integer.parseInt(planBean.getTermNo());
		 for(int i=1;i<termno;++i){
			 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
			if (myRatedayBean!=null) {
				baseOver = BigNumberUtil.Add(baseOver,myRatedayBean.getInterest(),myRatedayBean.getOverInterest(),myRatedayBean.getCmpdInterest(),myRatedayBean.getAccFee());
			}
		 }
		
		 baseOver = BigNumberUtil.Add(baseOver,dueBal,planBean.getAccFee());
		 
		 
		 // 计算逾期利息的结束日期
		 String endDate =SystemParm.SystemDate;	
		 // 判断是不是强制计划 0 不是强制计划 1 是强制计划
	     // 获得逾期利率
	     String overRate = acLnMstBean.getOverRate();
	 	 // 获得利率服务类
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // 根据利率编号获得利率调整列表
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // 根据利率调整列表和计息方式获得生效点列表
		 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
	     
	     // 是强制计划
	     if(isForce==1){
	    	//计算逾期利息的开始 (该期计划的结束日期)
   		     String begDate = planBean.getEndDate(); 
	    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // 获得距离借据结束日期最近的一次利率调整
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
			 if(latePartParmBean!=null){
				 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 overRate = latePartParmBean.getOverRate();
				 }
			 }
			 //当前的日期已经超过该期的结束日期
	    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
	    		   
	    		 // 如果有利率调整
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
				    		 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
							}
				    		 
				    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempOverInterest)){
				    			 tempOverInterest = "0.00";
				    		 }else{
				    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
				    		 }
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
				    		 
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 overRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
		    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    				 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
				    		 
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,endDate);
							}
		    				 
		    				 
		    				 
		    				 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempOverInterest)){
				    			 tempOverInterest = "0.00";
				    		 }else{
				    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
				    		 }
				    		 
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    			 }
		    		 }
	    		 }else{// 没有利率调整
	    			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    			 int days = 0;
	    			 if (StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())) {
						begDate = acLnMstBean.getLastReturnDate();
					}
		    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
		    		 
		    		 if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate,endDate);
					}
    				 
    				 
    				 
    				 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
	    			 
		    		// String tempOverInterest  = BigNumberUtil.Multiply(dueBal, days); 
		    		 String overDayRate = monthRate2DayRate(overRate);
		    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempOverInterest)){
		    			 tempOverInterest = "0.00";
		    		 }else{
		    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
		    		 }
		    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
	    		 }
		     }
	     }else{// 不是强制计划
	    	 // 计算逾期利息的开始日期(借据结束日期)
	    	 String begDate = acLnMstBean.getDueEndDate();
	    	 // 获得落该期还款计划开始日期和该期还款计划结束日期之间的生效点
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // 获得距离借据结束日期最近的一次利率调整
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, planBean.getEndDate());
			 if(latePartParmBean!=null){
				 // 判断最后一次调整的生效点是否落在借据的开始日期和借据的结束日期之间,否则是无效的.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 overRate = latePartParmBean.getOverRate();
				 }
			 }
			 
			 // 如果有利率调整
   		 if(filterEffectDates!=null && filterEffectDates.size()>0){
   			 int size = filterEffectDates.size();
	    		 for(int i=0;i<size;++i){
	    			 if(i<size-1){
			    		// String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
			    		 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
			    		 
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
						}
			    		 
			    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
			    		 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempOverInterest)){
			    			 tempOverInterest = "0.00";
			    		 }else{
			    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
			    		 }
			    		 
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
			    		 
			    		 begDate = filterEffectDates.get(i).getEffectDate();
			    		 overRate = filterEffectDates.get(i).getOverRate();
	    			 }else{
	    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    				 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
			    		 
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,endDate);
						}
	    				 
	    				 
			    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempOverInterest)){
			    			 tempOverInterest = "0.00";
			    		 }else{
			    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
			    		 }
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
	    			 }
	    		 }
   		 }else{// 没有利率调整
   			 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	   			 int days = 0;
	    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
	    		 
	    		 if (monthOfDays==1) {
					days = monthAndDay[0]*30+monthAndDay[1];
				} else {
					days = DateUtil.getBetweenDays(begDate,endDate);
				}
	    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
	    		 String overDayRate = monthRate2DayRate(overRate);
	    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
	    		 if(StringUtil.equals("0.00", tempOverInterest)){
	    			 tempOverInterest = "0.00";
	    		 }else{
	    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
	    		 }
	    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
   		 }
	     }
	     return overInterest;
	}
	
	
	
	
	/**
	 * 
	 * 方法描述： 利随本清日终利息计算 
	 * @param planBean
	 * @param acLnMstBean
	 * @param isFullTerm(是否计算整期利息)
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-22 下午05:06:23
	 */
	private String  profitsClearInterest(PlanBean planBean, AcLnMstBean acLnMstBean,boolean isFullTerm) {
		String interest = "0.00";
		// 截止到当前的日期
		String endDate = SystemParm.SystemDate;
		int tempDays = DateUtil.getBetweenDays(planBean.getBegDate(),endDate);
		if (tempDays < 1) {
			return interest;
		}

		// 执行利率
		String executeRate = acLnMstBean.getLnRate();
		// 判断是否有利率调整后的生效点落到该区间段
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		// 根据利率编号获得利率调整列表
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		// 根据利率调整列表和计息方式获得生效点列表
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		// 借据开始日期
		String begDate = acLnMstBean.getDueBegDate();
		
		/**获得最近一次还款日期开始**/
		IRepayDao repayDao = SpringFactory.getBean("repayDao");
		RepayBean repayBean = new RepayBean();
		repayBean.setDueNo(planBean.getDueNo());
		repayBean.setTermNo( planBean.getTermNo());
		List<RepayBean> repayHisBeans = repayDao.getRepayBeans(repayBean);
		if(repayHisBeans!=null && repayHisBeans.size()>0){
			Collections.sort(repayHisBeans);
			begDate = repayHisBeans.get(repayHisBeans.size()-1).getOccDate(); 
		}
		/**获得最近一次还款日期结束**/
		
		
		
		// 是否计算当期利息
		if(isFullTerm){
			endDate = planBean.getEndDate();
		}
		
		// 获得距离该期还款计划开始日期最近的一次调整
		 PartParmBean  LatelyPartParmBean =  getLatelyEffectDate(effectDates, begDate);
		if (LatelyPartParmBean!=null) {
			executeRate = LatelyPartParmBean.getExecuteRate();
		}
		
		// 过滤生效点获取落在开始日期和结束日期之间的生效点
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates,begDate, endDate);
		if (filterEffectDates != null && filterEffectDates.size() > 0) {
			int size = filterEffectDates.size();
			// 分段利息计算第一段
			for (int i = 0; i < size; ++i) {
				if (i < size - 1) {
					// 获得借据开始日期和第一个生效点之间的天数
					PlanBean tempPlanBean = new PlanBean();
					tempPlanBean.setBegDate(begDate);
					tempPlanBean.setEndDate(filterEffectDates.get(i).getEffectDate());
					acLnMstBean.setLnRate(executeRate);
					String tempInterest = getInterest(planBean,acLnMstBean);
					interest = BigNumberUtil.Add(interest,tempInterest);
					
					// 重新设置执行利率,开始日期和第一段标志
					executeRate = filterEffectDates.get(i).getExecuteRate();
					begDate = filterEffectDates.get(i).getEffectDate();
					tempPlanBean = null;
				} else {
					// 获得借据开始日期和第一个生效点之间的天数
					PlanBean tempPlanBean = new PlanBean();
					tempPlanBean.setBegDate(begDate);
					tempPlanBean.setEndDate(endDate);
					acLnMstBean.setLnRate(executeRate);
					String tempInterest = getInterest(planBean,acLnMstBean);
					interest = BigNumberUtil.Add(interest, tempInterest);
				}
			}
		} else {
			// 没有利率调整的情况(现在都是按日利率计算)
			
			PlanBean tempPlanBean = new PlanBean();
			tempPlanBean.setBegDate(begDate);
			tempPlanBean.setEndDate(endDate);
			String tempInterest = getInterest(planBean,acLnMstBean);
			interest = BigNumberUtil.Add(interest, tempInterest);
		}
		return interest;	
	}
	
	
	
	
	
	
	/**
	 * 
	 * 方法描述： 按计划日终利息计算
	 * @param planBean
	 * @param acLnMstBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-22 上午11:12:15
	 */
	private String planInterest(PlanBean planBean, AcLnMstBean acLnMstBean,boolean isFullTerm){
			 String interest = "0.00";
			 // 计算利息的开始日期
			 String begDate = planBean.getBegDate();
			
			
			 /**获得最近一次还款日期开始**/
				IRepayDao repayDao = SpringFactory.getBean("repayDao");
				RepayBean repayBean = new RepayBean();
				repayBean.setDueNo(planBean.getDueNo());
				repayBean.setTermNo( planBean.getTermNo());
				List<RepayBean> repayHisBeans = repayDao.getRepayBeans(repayBean);
				if(repayHisBeans!=null && repayHisBeans.size()>0){
					Collections.sort(repayHisBeans);
					begDate = repayHisBeans.get(repayHisBeans.size()-1).getOccDate(); 
				}
			 /**获得最近一次还款日期结束**/
			 
			 // 计算利息的结束日期 
			 String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
			 // 是否计算整期利息
			 if(isFullTerm){
					endDate = planBean.getEndDate();
			 }
			 // 执行利率 
			 String executeRate = acLnMstBean.getLnRate();
			 
			 // 获得利率服务类
			 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
			 // 根据利率编号获得利率调整列表
			 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
			 // 根据利率调整列表和计息方式获得生效点列表
			 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
			 //根据还款计划的开始日期结束日期过滤生效点,只取落在计划开始日期和截止到当前日期之间的生效点
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, planBean.getBegDate(), endDate);
			
			 /**将计算利息的开始日期更新到最近一次还款日期开始**/
			 PartParmBean  LatelyPartParmBean =  getLatelyEffectDate(effectDates, begDate);
			 if(LatelyPartParmBean!=null){
				 executeRate = LatelyPartParmBean.getExecuteRate();
			 }
			 /**将计算利息的开始日期更新到最近一次还款日期结束**/
			 
			 
			 
			 /**有利率调整分段计算开始**/
			 if(filterEffectDates!=null && filterEffectDates.size()>0){
				 int size = filterEffectDates.size();
				 for(int i=0;i<size;++i){
					 if(i<size-1){
						PlanBean tempPlanBean = planBean.clone();
						tempPlanBean.setBegDate(begDate);
						tempPlanBean.setEndDate(filterEffectDates.get(i).getEffectDate());
						acLnMstBean.setLnRate(executeRate);
						
						String tempInterest  = getInterest(tempPlanBean, acLnMstBean);
						interest = BigNumberUtil.Add(tempInterest,interest);
						 
//						 // 获得2个生效点之间间隔的天数
//						 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
//						 // 本金乘以天数
//						 String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
//						 // 月利率转化为日利率
//						 String dayRate = monthRate2DayRate(executeRate);
//						 // 本金乘以天数乘以日利率
//						 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
//						 tempInterest = BigNumberUtil.Divide(tempInterest, "10000", 10, "1");
//						 interest = BigNumberUtil.Add(tempInterest,interest);
						 //	重新设置数据
						 begDate = filterEffectDates.get(i).getEffectDate();
						 executeRate = filterEffectDates.get(i).getExecuteRate();
						 tempPlanBean = null;
					 }else{
						 // 单独处理最后一段的利息
						 // 获得间隔的天数
						 PlanBean tempPlanBean = planBean.clone();
						 tempPlanBean.setBegDate(begDate);
						 tempPlanBean.setEndDate(endDate);
						 acLnMstBean.setLnRate(executeRate);
						 
						 String tempInterest  = getInterest(tempPlanBean, acLnMstBean);
						 interest = BigNumberUtil.Add(tempInterest,interest);
						 
//						 String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
//						// 本金乘以天数
//						 String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
//						 // 月利率转化为日利率
//						 String dayRate = monthRate2DayRate(executeRate);
//						 // 本金乘以天数乘以日利率
//						 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
//						 tempInterest = BigNumberUtil.Divide(tempInterest, "10000", 10, "1");
//						 interest = BigNumberUtil.Add(tempInterest,interest);
						 tempPlanBean = null;
					 }
				 }
			 /**有利率调整分段计算结束**/
			 }else{
				 // 没有利率调整
				 
				 String tempInterest  = getInterest(planBean, acLnMstBean);
				 interest = BigNumberUtil.Add(tempInterest,interest);
				 
				 // 获得间隔的天数
				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
				// 本金乘以天数
				// String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
				 // 月利率转化为日利率
				// String dayRate = monthRate2DayRate(executeRate);
				 // 本金乘以天数乘以日利率
				 //tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
				// tempInterest = BigNumberUtil.Divide(tempInterest, "10000", 10, "1");
				// interest = BigNumberUtil.Add(tempInterest,interest);
			 }
			 return interest;
	}
	
	/**
	 * 
	 * 方法描述： 等额本金日终利息计算.(从还款计划的开始的日期开始计算如果没有利息调整每期的利息都是一个定值(该期还款计划的利息)
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-22 下午01:26:49
	 */
	private String matchPrincipalInterest(PlanBean planBean ,AcLnMstBean acLnMstBean,boolean isFullTerm){
		String begDate = planBean.getBegDate();
		
		String executeRate = acLnMstBean.getLnRate();
		// 获得该期还款计划的应还利息
		String shoudInterest = planBean.getReturnInterest();
		// 计算利息的结束日期 
		String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
		 // 获得利率服务类
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // 根据利率编号获得利率调整列表
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // 根据利率调整列表和计息方式获得生效点列表
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
		 /**将计算利息的开始日期更新到最近一次还款日期开始**/
		 PartParmBean  latelyPartParmBean =  getLatelyEffectDate(effectDates, planBean.getBegDate());
		 if (latelyPartParmBean!=null) {
			 	acLnMstBean.setLnRate(latelyPartParmBean.getExecuteRate());
		 }
		 //根据还款计划的开始日期结束日期过滤生效点,只取落在计划开始日期和截止到当前日期之间的生效点
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, planBean.getBegDate(), endDate);
		// 如果没有神效点落在当期直接返回还款计划中的应还利息
		if(filterEffectDates==null || filterEffectDates.size()==0){
			return shoudInterest;
		}else{
		// 如果有生效点落在当期的还款计划内需要从新计算利息(现在还没有实现)
			 /**有利率调整分段计算开始**/
				 String interest = "0.00";
				 int size = filterEffectDates.size();
				 for(int i=0;i<size;++i){
					 if(i<size-1){
						PlanBean tempPlanBean = planBean.clone();
						tempPlanBean.setBegDate(begDate);
						tempPlanBean.setEndDate(filterEffectDates.get(i).getEffectDate());
						acLnMstBean.setLnRate(executeRate);
						String tempInterest  = getInterest(tempPlanBean, acLnMstBean);
						interest = BigNumberUtil.Add(tempInterest,interest);
						begDate = filterEffectDates.get(i).getEffectDate();
						executeRate = filterEffectDates.get(i).getExecuteRate();
						tempPlanBean = null;
					 }else{
						 // 单独处理最后一段的利息
						 // 获得间隔的天数
						 PlanBean tempPlanBean = planBean.clone();
						 tempPlanBean.setBegDate(begDate);
						 tempPlanBean.setEndDate(endDate);
						 acLnMstBean.setLnRate(executeRate);
						 
						 String tempInterest  = getInterest(tempPlanBean, acLnMstBean);
						 interest = BigNumberUtil.Add(tempInterest,interest);

						 tempPlanBean = null;
					 }
				 }
			 /**有利率调整分段计算结束**/
				 shoudInterest  = interest;
		}
		return shoudInterest;
	} 
	
	/**
	 * 
	 * 方法描述： 等额本息日终利息计算.从还款计划的开始的日期开始计算如果没有利息调整每期的利息都是一个定值(该期还款计划的利息)
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-22 下午01:41:40
	 */
	private String monthPrincipalInterest(PlanBean planBean ,AcLnMstBean acLnMstBean,boolean isFullTerm){
		String begDate = planBean.getBegDate();
		String executeRate = acLnMstBean.getLnRate();
		// 获得该期还款计划的应还利息
		String shoudInterest = planBean.getReturnInterest();
		// 计算利息的结束日期 
		String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
		 // 获得利率服务类
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // 根据利率编号获得利率调整列表
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // 根据利率调整列表和计息方式获得生效点列表
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
		 //根据还款计划的开始日期结束日期过滤生效点,只取落在计划开始日期和截止到当前日期之间的生效点
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, planBean.getBegDate(), endDate);
		// 如果没有神效点落在当期直接返回还款计划中的应还利息
		
		PartParmBean  latelyPartParmBean =  getLatelyEffectDate(effectDates, planBean.getBegDate());
		 if (latelyPartParmBean!=null) {
			 executeRate = latelyPartParmBean.getExecuteRate();
		 }
		if(filterEffectDates==null || filterEffectDates.size()==0){
			return shoudInterest;
		}else{
		// 如果有生效点落在当期的还款计划内需要从新计算利息(现在还没有实现)
			 String interest = "0.00";
			 int size = filterEffectDates.size();
			 String tempEndDate = "";
			 for(int i=0;i<size;++i){
				 if(i<size-1){
					tempEndDate =   filterEffectDates.get(i).getEffectDate();
					int days = DateUtil.getBetweenDays(begDate, tempEndDate);
					// 本金乘以期限
					String tempInterest = BigNumberUtil.Multiply(planBean.getDueBal(), String.valueOf(days));
					String dayRate = monthRate2DayRate(executeRate);
					// 本金乘以期限乘以日利率
					tempInterest = BigNumberUtil.Multiply(tempInterest,dayRate);
					tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
					interest = BigNumberUtil.Add(interest,tempInterest);
					
					begDate = tempEndDate;
					executeRate =  filterEffectDates.get(i).getExecuteRate();
					
				 }else{
					 // 单独处理最后一段的利息
					 // 获得间隔的天数
					 int days = DateUtil.getBetweenDays(begDate, planBean.getEndDate());
					// 本金乘以期限
					String tempInterest = BigNumberUtil.Multiply(planBean.getDueBal(), String.valueOf(days));
					String dayRate = monthRate2DayRate(filterEffectDates.get(i).getExecuteRate());
					// 本金乘以期限乘以日利率
					tempInterest = BigNumberUtil.Multiply(tempInterest,dayRate);
					tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
					interest = BigNumberUtil.Add(interest,tempInterest);
				 }
			 }
			 shoudInterest = interest;
			return shoudInterest;
		}
	}

	
	/**
	 * 
	 * 方法描述：一次偿还本金按月结息的日终利息计算  
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-22 下午01:44:18
	 */
	private String oneMonthInterest(PlanBean planBean ,AcLnMstBean acLnMstBean,boolean isFullTerm){
		 String interest = "0.00";
		 // 获得借据剩余本金
		 String remaCapital = acLnMstBean.getDueBal();
		 // 计算利息的开始日期
		 String begDate = planBean.getBegDate();
		 // 计算利息的结束日期
		 String endDate = planBean.getEndDate();//DateUtil.addByDay(SystemParm.SystemDate,1, DateUtil.DATE_FORMAT_);
		 if(isFullTerm){
			 endDate = planBean.getEndDate();
		 }
		 
		 // 获得执行利率 
		 String executeRate = acLnMstBean.getLnRate(); 
		 // 获得利率服务类
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // 根据利率编号获得利率调整列表
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // 根据利率调整列表和计息方式获得生效点列表
		 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
		 // 根据还款计划的开始日期结束日期过滤生效点,只取落在计划开始日期和截止到当前日期之间的生效点
		 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, planBean.getBegDate(), endDate);
		 // 获得距离该期还款计划开始日期最近的一次利率调整
		 PartParmBean latelyPartParmBean = getLatelyEffectDate(effectDates, begDate);
		 
		 if(latelyPartParmBean!=null){
			// 判断距离该期还款计划开始日期最近的一次利率调整是否落在借据开始日期和该期还款计划开始范围内
			 boolean isBetween = DateUtil.isBetween(latelyPartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(),begDate);
			 if(isBetween){
				 executeRate = latelyPartParmBean.getEffectDate(); 
			 }
		 }
		 // 有利率调整
		 if(filterEffectDates!=null && filterEffectDates.size()>0){
			 int size = filterEffectDates.size();
			 for(int i=0;i<size;++i){
				 if(i<size-1){
					 // 获得2个生效点之间间隔的天数
					 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
					 // 本金乘以天数
					 String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
					 // 月利率转化为日利率
					 String dayRate = monthRate2DayRate(executeRate);
					 // 本金乘以天数乘以日利率
					 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
					 if(StringUtil.equals("0.00", tempInterest)){
						 tempInterest = "0.00";
					 }else{
						 tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
					 }
					 
					 interest = BigNumberUtil.Add(tempInterest,interest);
					 //	重新设置数据
					 begDate = filterEffectDates.get(i).getEffectDate();
					 executeRate = filterEffectDates.get(i).getExecuteRate();
				 }else{
					 // 获得2个生效点之间间隔的天数
					 String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
					 // 本金乘以天数
					 String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
					 // 月利率转化为日利率
					 String dayRate = monthRate2DayRate(executeRate);
					 // 本金乘以天数乘以日利率
					 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
					 if(StringUtil.equals("0.00", tempInterest)){
						 tempInterest = "0.00";
					 }else{
						 tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
					 }
					 interest = BigNumberUtil.Add(tempInterest,interest);
				 }
			 }
		 }else{
			 
			 int[] monthAndDays = DateUtil.getMonthsAndDays(begDate,endDate);
			 String tempMonthInterest = BigNumberUtil.Multiply(remaCapital, String.valueOf(monthAndDays[0]));
			 if(StringUtil.equals("0.00", tempMonthInterest)  || StringUtil.equals("0.0", tempMonthInterest)){
				 
			 }else{
				 tempMonthInterest = BigNumberUtil.Multiply(tempMonthInterest, executeRate);
				 tempMonthInterest = BigNumberUtil.Divide(tempMonthInterest, "1000", 2, "1");
			 }
			 
			 
			 
			 // 本金乘以天数
			 String tempInterest = BigNumberUtil.Multiply(remaCapital, String.valueOf(monthAndDays[1]));
			 // 月利率转化为日利率
			 String dayRate = monthRate2DayRate(executeRate);
			 // 本金乘以天数乘以日利率
			 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
			 if(StringUtil.equals("0.00", tempInterest)||StringUtil.equals("0.0", tempInterest)){
				 
			 }else{
				 tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
				 
			 }
			 interest = BigNumberUtil.Add(tempInterest,interest,tempMonthInterest);
		 }
		 return interest;
	}
	
	
	/****************************日终批量利息计算****************************/
	
	
	
	
	
	
	
	/**
	 * 
	 * 方法描述：利息计算 生成还款计划时使用
	 * void
	 * @author 乾之轩
	 * @date 2012-5-18 下午05:30:02
	 */
	public String  getInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		   	String interest = "0.0";
			// 获得利率 调整列表
			NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
			List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
			//根据利率调整列表获得生效点列表
			List<PartParmBean> effectDates =  getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
			// 过滤生效点获取落在开始日期和结束日期之间的生效点
			List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
			/** 计算正常利息**/
		
			
			// 利随本清
			if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR, acLnMstBean.getReturnMethod())){
					interest = getRateCome_1(planBean,acLnMstBean);
			}
			
			// 按计划
			if(StringUtil.equals(SystemParm.RETURNMETHOD_PLAN, acLnMstBean.getReturnMethod())){
				interest = getRateCome_2(planBean,acLnMstBean);
			}
			// 一次偿还本金按月结息
			if(StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, acLnMstBean.getReturnMethod())){
				interest = getRateCome_3(planBean,acLnMstBean);
			}
			
			// 一次偿还本金按季结息
			if(StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, acLnMstBean.getReturnMethod())){
				interest = getRateCome_3(planBean,acLnMstBean);
			}
			
			// 等额本金利息
			if(StringUtil.equals(SystemParm.RETURNMETHOD_PRINCIPAL, acLnMstBean.getReturnMethod())){
				interest = getRateCome_4(planBean,acLnMstBean);
			}
			
			return  interest;
	}
	
	
	/**
	 * 
	 * 方法描述：  等额本息每期的本息和
	 * void
	 * @author 乾之轩
	 * @date 2012-5-25 下午07:29:49
	 */
	public String getInterest(PlanBean planBean,int terms,String remaMoney,AcLnMstBean acLnMstBean){
		String money = "0.00";
		// 利息计算时用的利率 0 日利率 1 月利率  2足月用月利率 ,不足部分用日利率
		//int useRateType = SystemParm.USE_RATE_TYPE;
		int useRateType = 1;
		// 当用日利率计算时每月的天数 0 实际天数 1 足月按30天
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		if(useRateType==1){
			// 执行利率(月利率)
			String lnRate = acLnMstBean.getLnRate();
			// 本金乘以月利率
			String totalMoney = BigNumberUtil.Multiply(acLnMstBean.getDueBal(), lnRate);
			totalMoney = BigNumberUtil.Divide(totalMoney, "1000", 10, "1");
			// 月利率加1
			String monthRate = BigNumberUtil.Divide(lnRate, "1000", 10, "1");
			String monthRate1 = BigNumberUtil.Add(monthRate,"1");
			// (1加月利率)的期数次方
			String monthRate2 = String.valueOf(Math.pow(Double.valueOf(monthRate1), Double.valueOf(terms)));
			// 本金乘以月利率乘以月利率的期数次方
			String tempMoney = BigNumberUtil.Multiply(totalMoney, monthRate2);
			// (1加月利率)的期数减次方
	        String monthRate3 = BigNumberUtil.Subtract(monthRate2, "1");
	        // 没月应还本金和利息的和
	        money = BigNumberUtil.Divide(tempMoney, monthRate3, 2, "1");
		}
        return money;
	}
	
	/**
	 * 
	 * 方法描述：   等额本息每期的利息
	 * @param planBean
	 * @param remaMoney
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-26 上午08:59:33
	 */
	public String getInterestByMonth(PlanBean planBean,String remaMoney,AcLnMstBean acLnMstBean){
		String interest = "0.00";
		String lnRate = acLnMstBean.getLnRate();
		interest = BigNumberUtil.Multiply(remaMoney, lnRate);
		interest = BigNumberUtil.Divide(interest, "1000", 2, "1");
		return interest;
	}
	
	/**
	 * 
	 * 方法描述：   等额本息每期的利息
	 * @param planBean
	 * @param remaMoney
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-26 上午08:59:33
	 */
	public String getInterestByDay(PlanBean planBean,String remaMoney,AcLnMstBean acLnMstBean){
		String interest = "0.00";
		int [] monthAndDay = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
		String lnRate = acLnMstBean.getLnRate();
		String dayRate = monthRate2DayRate(lnRate);
		interest = BigNumberUtil.Multiply(remaMoney,dayRate);
		interest = BigNumberUtil.Multiply(interest,String.valueOf(monthAndDay[1]));
		interest = BigNumberUtil.Divide(interest, "10000", 2, "1");
		return interest;
	}
	
	
	
	
	
	
	/**
	 * 
	 * 方法描述：放款之前有利率调整分段进行利息计算(现在只要调整后就用日利率进行计算,可能不是很合适需要讨论) 
	 * @param principal
	 * @param beginDate(还款计划开始日期)
	 * @param endDate(还款计划结束日期)
	 * @param partParmBeans
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-21 下午03:25:41
	 */
	private String getRateComeByAdjust(String principal,String beginDate,String endDate,String lnRate ,List<PartParmBean> partParmBeans){
			String interest = "0.0";
			
			
			// 第一段使用老利率,第二段使用第一次调整后的利率,第三段使用第二次调整后的利率一次类推第n段使用第n-1次调整后的利息
			// 判断是不是第一段
			boolean isFirstPart  = true;
			String executeRate = lnRate;
			String begDate = beginDate;
			for(PartParmBean  partParmBean:partParmBeans){
			    if(isFirstPart){
			    	String days = String.valueOf(DateUtil.getBetweenDays(begDate, partParmBean.getEffectDate()));
			    	interest = BigNumberUtil.Multiply(principal, days);
			    	String dayRate =  monthRate2DayRate(executeRate);
			    	interest = BigNumberUtil.Multiply(interest, dayRate);
			    	interest = BigNumberUtil.Divide(interest, "1000", 10, "1");
			    	isFirstPart = false;
			    	executeRate = partParmBean.getExecuteRate();
			    	begDate = partParmBean.getEffectDate();
			    }else{
			    	String days = String.valueOf(DateUtil.getBetweenDays(begDate,partParmBean.getEffectDate()));
			    	String tempInterest =  BigNumberUtil.Multiply(principal, days);
			    	String dayRate =  monthRate2DayRate(executeRate);
			    	tempInterest = BigNumberUtil.Multiply(tempInterest,dayRate);
			    	tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 10, "1");
			    	interest = BigNumberUtil.Add(interest,tempInterest);
			    	executeRate = partParmBean.getExecuteRate(); 
			    	begDate = partParmBean.getEffectDate();
			    }	
			}	
			return interest;
	}
	
	

	
	
	/**
	 * 
	 * 方法描述： 利随本清利息  
	 * @param acLnMstBean  贷款主文件
	 * void
	 * @author 乾之轩
	 * @date 2012-5-21 下午01:54:03
	 */
	private String  getRateCome_1(PlanBean planBean,AcLnMstBean acLnMstBean){
			String interest = "0.00";
			// 计算利息时使用月利率还是日利率 0 日利率 1 月利率
		    int useRateType = SystemParm.USE_RATE_TYPE;
		    //  当选月利率,并且不是足月的处理, 0  足月部分使用月利率计算,不足月部分使用日利率计算 1 使用日利率计算
		    int rateNotFull = SystemParm.RATE_NOT_FULL;
		    // 获得合同的期限月期限日
		    int monthAndDays[] = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
		    int limitMonth = monthAndDays[0];
		    int limitDays = monthAndDays[1];
		    // 借据本金
		    String principal = acLnMstBean.getDueBal(); 
		    // 合同利率
		    String executeRate = acLnMstBean.getLnRate();
		    
		    // 月利率
		    if(useRateType==1){
		    	// 足月部分使用月利率不足月部分使用日利率
		    	if(rateNotFull==0){
		    		// 本金乘以期限月
					String  interestMonth = BigNumberUtil.Multiply(principal, String.valueOf(limitMonth));
					// 本金乘以期限日
					String  interestDay = BigNumberUtil.Multiply(principal, String.valueOf(limitDays));
					// 本金乘以期限月乘以月利率
					interestMonth = BigNumberUtil.Multiply(interestMonth, executeRate);
					interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10,"1");
					// 根据月利率获得日利率
					String dayRate = monthRate2DayRate(executeRate);
					// 本金乘以期限乘以日利率
					interestDay = BigNumberUtil.Multiply(interestDay, dayRate);
					interestDay = BigNumberUtil.Divide(interestDay, "10000", 10,"1");
					interest = BigNumberUtil.Add(interestMonth,interestDay);
					interest = BigNumberUtil.Divide(interest, "1", 2,"1");
		    	}
		    	
		    	if(rateNotFull==1){
		    		// 将月利率转化为日利率
		    		String dayRate = monthRate2DayRate(executeRate);
		    		String days = "0";
		    		// 满月部分为实际天数
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
		    			days = StringUtil.KillNull(days,"0");
		    		}
		    		// 满月部分按30天计算
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days= String.valueOf(limitMonth*30+limitDays);
		    		}
		    		// 本金乘以日利率乘以期限日
		    		String interestDay = BigNumberUtil.Multiply(principal, days);
		    		interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		interestDay = BigNumberUtil.Divide(interestDay, "10000", 2, "1");
		    		interest = interestDay;
		    	}
		    }
		    
		    // 使用日利率计算
		    if(useRateType==0){
		    	//贷款期限天
		    	String days = "0";
		    	// 按照实际天数计算期限
		    	if(SystemParm.MONTH_OF_DAYS==0){
		    		days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
		    		days = StringUtil.KillNull(days,"0");
		    	}
		    	// 一月按照30 天计算
		    	if(SystemParm.MONTH_OF_DAYS==1){
		    		days= String.valueOf(limitMonth*30+limitDays);
		    	}
		    	// 将月利率转化为日利率
		    	String dayRate = monthRate2DayRate(executeRate);
		    	// 本金乘以日利率
		    	String  interestDay = BigNumberUtil.Multiply(principal, dayRate) ;
		    	interestDay = BigNumberUtil.Multiply(interestDay, days) ;
		    	
		    	interest = BigNumberUtil.Divide(interestDay, "10000", 2,"1");
		    }
		    
			return  interest; 
	}
	
	
	/**
	 * 
	 * 方法描述： 按计划利息
	 * 1 使用月利率进行计算,足月直接使用月利率.不是足月的足月部分使用月利率,非足月使用日利率.整期使用日利率.
	 * 2 使用日利率进行计算
	 * 3 使用日利率时需要转化足月的天数,是实际天数还是按照30天来计算
	 * @param planBean
	 * @param acLnMstBean
	 * void
	 * @author 乾之轩
	 * @date 2012-6-4 上午10:19:05
	 */
	private String getRateCome_2(PlanBean planBean,AcLnMstBean acLnMstBean){
		// 利息
		String interest = "0.00";
		// 计算利息时使用月利率还是日利率 0 日利率 1 月利率
	    int useRateType = SystemParm.USE_RATE_TYPE;
	    //  当选月利率,并且不是足月的处理, 0  足月部分使用月利率计算,不足月部分使用日利率计算 1 使用日利率计算
	    int rateNotFull = SystemParm.RATE_NOT_FULL;
	    // 本金 (生成还款计划时使用)
	    String principal = planBean.getDueBal();
	    // 合同利率
	    String exxcuteRate = acLnMstBean.getLnRate();
	    // 还款计划的期限
	    int []monthAndDay = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
	   
	    if(useRateType==1){
	    	// 判断是不是整月,monthAndDay[1]==0 说明是整月
	    	if(monthAndDay[1]==0){
	    		// 本金乘以月利率
	    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
	    		// 本金乘以月利率乘以期限
	    		interestMonth = BigNumberUtil.Multiply(interestMonth, String.valueOf(monthAndDay[0]));
	    		interest = BigNumberUtil.Divide(interestMonth, "1000", 2, "1");
	    	}else{
	    		// 不是整月
	    		if(rateNotFull==0){
	    			/** 足月部分使用月利率,不足月部分使用日利率**/
	    			
	    			// 本金乘以月利率
		    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
		    		// 本金乘以月利率乘以期限
		    		interestMonth = BigNumberUtil.Multiply(interestMonth, String.valueOf(monthAndDay[0]));
		    		if(StringUtil.equals("0.00", interestMonth)){
		    			interestMonth = "0.00";
		    		}else{
		    			interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10, "1");	
		    		}
		    		
		    		
		    		// 将月利率转化为日利率
		    		String dayRate = monthRate2DayRate(exxcuteRate);
		    		// 本金乘以日利率
		    		String interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		// 本金乘以日利率乘以期限日
		    		interestDay = BigNumberUtil.Multiply(interestDay, String.valueOf(monthAndDay[1]));
		    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 10, "1");
		    		
		    		// 将整月部分利息和非整月部分的利息加在一起作为,该期利息
		    		interest = BigNumberUtil.Add(interestDay,interestMonth);
		    		interest  = BigNumberUtil.Divide(interest, "1", 2,"1");
	    		}
	    		
	    		if(rateNotFull==1){
	    			/**使用日利率**/
	    			// 将月利率转化为日利率
		    		String dayRate = monthRate2DayRate(exxcuteRate);
		    		String days = "0";
		    		// 满月部分为实际天数
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
		    		}
		    		// 满月部分按30天计算
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
		    		}
		    		
		    		// 本金乘以日利率乘以期限日
		    		String interestDay = BigNumberUtil.Multiply(principal, days);
		    		interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
		    		interest = interestDay;
	    		}
	    	}
	    }else{
	    	/**使用日利率**/
			// 将月利率转化为日利率
    		String dayRate = monthRate2DayRate(exxcuteRate);
    		String days = "0";
    		// 满月部分为实际天数
    		if(SystemParm.MONTH_OF_DAYS==0){
    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
    		}
    		// 满月部分按30天计算
    		if(SystemParm.MONTH_OF_DAYS==1){
    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
    		}
    		
    		// 本金乘以日利率乘以期限日
    		String interestDay = BigNumberUtil.Multiply(principal, days);
    		interestDay = BigNumberUtil.Multiply(interestDay, dayRate);
    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
    		interest = interestDay;
	    }
	    return interest;
	}
	
	
	/**
	 * 
	 * 方法描述： 一次偿还本金按月结息
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-6-4 上午11:14:26
	 */
	private String getRateCome_3(PlanBean planBean,AcLnMstBean acLnMstBean){
		// 利息
		String interest = "0.00";
		// 计算利息时使用月利率还是日利率 0 日利率 1 月利率
	    int useRateType = SystemParm.USE_RATE_TYPE;
	    //  当选月利率,并且不是足月的处理, 0  足月部分使用月利率计算,不足月部分使用日利率计算 1 使用日利率计算
	    int rateNotFull = SystemParm.RATE_NOT_FULL;
	    // 本金 (生成还款计划时使用)
	    String principal = planBean.getDueBal();
	    if(StringUtil.equals("0",principal)){
	    	return "0.00";
	    }
	    // 合同利率
	    String exxcuteRate = acLnMstBean.getLnRate();
	    // 还款计划的期限
	    int []monthAndDay = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
	    // 使用月利率计算
	    if(useRateType==1){
	    	// 判断是不是整月,monthAndDay[1]==0 说明是整月
	    	if(monthAndDay[1]==0){
	    		// 本金乘以月利率
	    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
	    		// 本金乘以月利率乘以期限
	    		interestMonth = BigNumberUtil.Multiply(interestMonth, String.valueOf(monthAndDay[0]));
	    		interest = BigNumberUtil.Divide(interestMonth, "1000", 2, "1");
	    	}else{
	    		// 不是整月
	    		if(rateNotFull==0){
	    			/** 足月部分使用月利率,不足月部分使用日利率**/
	    			
	    			// 本金乘以月利率
		    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
		    		// 本金乘以月利率乘以期限
		    		interestMonth = BigNumberUtil.Multiply(interestMonth, String.valueOf(monthAndDay[0]));
		    		interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10, "1");
		    		
		    		// 将月利率转化为日利率
		    		String dayRate = monthRate2DayRate(exxcuteRate);
		    		// 本金乘以日利率
		    		String interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		// 本金乘以日利率乘以期限日
		    		interestDay = BigNumberUtil.Multiply(interestDay, String.valueOf(monthAndDay[1]));
		    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
		    		
		    		// 将整月部分利息和非整月部分的利息加在一起作为,该期利息
		    		interest = BigNumberUtil.Add(interestDay,interestMonth);
		    		interest  = BigNumberUtil.Divide(interest, "1", 2,"1");
	    		}
	    		
	    		if(rateNotFull==1){
	    			/**使用日利率**/
	    			// 将月利率转化为日利率
		    		String dayRate = monthRate2DayRate(exxcuteRate);
		    		String days = "0";
		    		// 满月部分为实际天数
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
		    		}
		    		// 满月部分按30天计算
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
		    		}
		    		
		    		// 本金乘以日利率乘以期限日
		    		String interestDay = BigNumberUtil.Multiply(principal, days);
		    		interestDay = BigNumberUtil.Multiply(interestDay, dayRate);
		    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
		    		interest = interestDay;
	    		}
	    	}
	    }else{
	    	/**使用日利率**/
			// 将月利率转化为日利率
    		String dayRate = monthRate2DayRate(exxcuteRate);
    		String days = "0";
    		// 满月部分为实际天数
    		if(SystemParm.MONTH_OF_DAYS==0){
    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
    		}
    		// 满月部分按30天计算
    		if(SystemParm.MONTH_OF_DAYS==0){
    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
    		}
    		
    		// 本金乘以日利率乘以期限日
    		String interestDay = BigNumberUtil.Multiply(principal, days);
    		interestDay = BigNumberUtil.Multiply(interestDay, dayRate);
    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
    		interest = interestDay;
	    }
	    return interest;
		
	}
	/**
	 * 
	 * 方法描述： 等额本金利息计算
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-6-7 下午02:51:57
	 */
	private String getRateCome_4(PlanBean planBean,AcLnMstBean acLnMstBean){
		String interest= "0.00";
		int[] monthAndDay = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
		// 月利率
		if(SystemParm.USE_RATE_TYPE==1){
			// 是足月
			if (monthAndDay[1]==0) {
				String  interestMonth = "0.00";
				// 月利率
				String lnRate = acLnMstBean.getLnRate();
				// 剩余本金
				//String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
				String remaCapital =  planBean.getDueBal();
				
				/**足月部分利息**/
				// 剩余本金乘以月利率
				interestMonth = BigNumberUtil.Multiply(remaCapital,lnRate);
				// 剩余本金乘以月利率乘以期限月
				interestMonth = BigNumberUtil.Multiply(interestMonth,String.valueOf(monthAndDay[0]));
				interest = BigNumberUtil.Divide(interestMonth, "1000", 2, "1");
			}
			// 不是足月(非整月)
			if (monthAndDay[1]>0) {
				// 足月用月利率,不足月部分用日利率
				if (SystemParm.RATE_NOT_FULL==0) {
					String  interestMonth = "0.00";
					String  interestDay = "0.00";
					// 月利率
					String lnRate = acLnMstBean.getLnRate();
					// 剩余本金
					//String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
					String remaCapital = planBean.getDueBal();
					
					/**足月部分利息**/
					// 剩余本金乘以月利率
					interestMonth = BigNumberUtil.Multiply(remaCapital,lnRate);
					// 剩余本金乘以月利率乘以期限月
					interestMonth = BigNumberUtil.Multiply(interestMonth,String.valueOf(monthAndDay[0]));
					interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 2, "1");
					/**不足月部分利息**/
					
					String dayRate = monthRate2DayRate(lnRate);
					// 剩余本金乘以日利率
					interestDay = BigNumberUtil.Multiply(remaCapital,dayRate);
					// 剩余本金乘以月利率乘以期限日
					interestDay = BigNumberUtil.Multiply(interestDay,String.valueOf(monthAndDay[1]));
					interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
					
					// 足月部分利息加不足月部分利息
					interest = BigNumberUtil.Add(interestDay,interestMonth);
					interest = BigNumberUtil.Divide(interest, "1", 2, "1");
					
				}
				// 使用日利率计算
				if (SystemParm.RATE_NOT_FULL==1) {
					String  interestDay = "0.00";
					// 剩余本金
					String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
					// 月利率
					String lnRate = acLnMstBean.getLnRate();
					String dayRate = monthRate2DayRate(lnRate);
					int days = 0;
					// 一个月按30 天计算
					if (SystemParm.MONTH_OF_DAYS==1) {
						days = monthAndDay[1]*30;
					}
					// 取一个月的实际天数
					if (SystemParm.MONTH_OF_DAYS==0) {
						days = DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate());
					}
					days = days +monthAndDay[1];
					
					// 剩余本金乘以日利率
					interestDay = BigNumberUtil.Multiply(remaCapital,dayRate);
					// 剩余本金乘以月利率乘以期限日
					interestDay = BigNumberUtil.Multiply(interestDay,String.valueOf(monthAndDay[1]));
					interest = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
				}
			}
		}
		
		// 使用日利率计算
		if(SystemParm.USE_RATE_TYPE==0){
			String  interestDay = "0.00";
			// 剩余本金
			//String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
			String remaCapital =  planBean.getDueBal();
			// 月利率
			String lnRate = acLnMstBean.getLnRate();
			String dayRate = monthRate2DayRate(lnRate);
			int days = 0;
			// 一个月按30 天计算
			if (SystemParm.MONTH_OF_DAYS==1) {
				days = monthAndDay[1]*30;
			}
			// 取一个月的实际天数
			if (SystemParm.MONTH_OF_DAYS==0) {
				if (monthAndDay[0]>0) {
					days = DateUtil.getDay(planBean.getBegDate());
					days = DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate());
				}
			}
			days = days +monthAndDay[1];
			
			// 剩余本金乘以日利率
			interestDay = BigNumberUtil.Multiply(remaCapital,dayRate);
			// 剩余本金乘以月利率乘以期限日
			interestDay = BigNumberUtil.Multiply(interestDay,String.valueOf(days));
			interest = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
		}
		return interest;
	}
	
	/**
	 * 
	 * 方法描述： 将月利率装换为日利率 
	 * @param momthRate
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-21 下午02:28:07
	 */
	private  String monthRate2DayRate(String momthRate){
		return BigNumberUtil.Divide(momthRate, "30", 10, "1");
	} 
	
	
	/**
	 * 
	 * 方法描述： 根据开始日期和结束日期对生效日期进行过滤,去除不在开始日期和结束日期之间的生效点(取头不取尾)
	 * @param effectDates
	 * @param beginDate
	 * @param endDate
	 * @return
	 * List<String>
	 * @author 乾之轩
	 * @date 2012-5-21 上午11:33:59
	 */
	private List<PartParmBean> filterEffectDate(List<PartParmBean> effectDates , String beginDate,String endDate){
		if(effectDates==null || effectDates.size()==0){
			return null;
		}
		List<PartParmBean>  filterEffectDates = new ArrayList<PartParmBean>();
		for(PartParmBean  partParmBean: effectDates){
			if(DateUtil.isBetween(partParmBean.getEffectDate(), beginDate, endDate)){
				filterEffectDates.add(partParmBean);
			}
		}
		// 按照生效时间进行排序
		Collections.sort(filterEffectDates);
		return filterEffectDates;
	}
	

	
	
	/**
	 * 
	 * 方法描述：获得距离开始日期最近的一次利率调整
	 * @param effectDates
	 * @param beginDate
	 * @param endDate
	 * @return
	 * PartParmBean
	 * @author 乾之轩
	 * @date 2012-5-22 下午02:31:36
	 */
	private  PartParmBean getLatelyEffectDate(List<PartParmBean> effectDates,String beginDate){
		if (effectDates==null || effectDates.size()==0) {
			return null;
		}
		Collections.sort(effectDates);
		PartParmBean tempPartParmBean = null;
		for(PartParmBean partParmBean:effectDates){
			if(DateUtil.gteq(partParmBean.getEffectDate(),beginDate)){
				tempPartParmBean = partParmBean;
				continue;
			}else{
				break;
			}
		}
		return tempPartParmBean;
	}
	
	
	/**
	 * 
	 * 方法描述： 根据
	 * @param rateAdjustBeans
	 * @param rateWay
	 * @param lnRateFloat(执行利率浮动比率)
	 * @return
	 * List<String>
	 * @author 乾之轩
	 * @date 2012-5-21 上午11:03:00
	 */
	private List<PartParmBean> getEffectDateByAdjustDate(List<RateAdjustBean> rateAdjustBeans,AcLnMstBean acLnMstBean){
		if(rateAdjustBeans==null || rateAdjustBeans.size()==0){
			return null;
		}
		
		// String rateWay,String lnRateFloat
		    // 计息方式
			String rateWay = acLnMstBean.getRateWay();
			// 执行利率浮动比率
			String lnRateFloat = acLnMstBean.getRateFloat();
			// 逾期浮动比率
			String overRateFloat = acLnMstBean.getOverRateFloat();
			// 复利浮动
			String  cmpRateFloat = acLnMstBean.getCmpdRateFloat();
			
			
		    List<PartParmBean>  effectDates = new ArrayList<PartParmBean>();
		    for( RateAdjustBean  rateAdjustBean : rateAdjustBeans){
		    	PartParmBean  partParmBean = new PartParmBean(); 
		    	String effectDate = getEffectDate(rateAdjustBean.getAdjustDate(),rateWay);
		    	partParmBean.setEffectDate(effectDate);
		    	if(StringUtil.equals(SystemParm.RATE_MONTH, rateAdjustBean.getRateType())){
		    		String baseRate = rateAdjustBean.getRateValue();
		    		String executeRate = BigNumberUtil.Multiply(baseRate,lnRateFloat);
		    		executeRate = BigNumberUtil.Divide(executeRate, "100",10, "1");
		    		// 逾期利率
		    		String overRate = BigNumberUtil.Multiply(baseRate, overRateFloat);
		    		overRate = BigNumberUtil.Divide(overRate, "100", 10, "1");
		    		// 复利利率
		    		String cmpRate = BigNumberUtil.Multiply(baseRate, cmpRateFloat); 
		    		cmpRate = BigNumberUtil.Divide(cmpRate, "100",10, "1");	
		    		
		    	}
		    	
		    	if(StringUtil.equals(SystemParm.RATE_YEAR,rateAdjustBean.getRateType())){
		    		String baseRate = rateAdjustBean.getRateValue();
		    		String executeRate = BigNumberUtil.Multiply(baseRate, lnRateFloat);
		    		executeRate = BigNumberUtil.Divide(executeRate, "12", 10, "1");
		    		executeRate = BigNumberUtil.Divide(executeRate, "100", 10, "1");
		    		
		    		// 逾期利率
		    		String overRate = BigNumberUtil.Multiply(baseRate, overRateFloat);
		    		overRate = BigNumberUtil.Divide(overRate, "12", 10, "1");
		    		overRate = BigNumberUtil.Divide(overRate, "100", 10, "1");
		    		
		    		// 复利利率
		    		String  cmpRate = BigNumberUtil.Multiply(baseRate, cmpRateFloat);
		    		cmpRate = BigNumberUtil.Divide(cmpRate, "12", 10, "1");
		    		cmpRate = BigNumberUtil.Divide(cmpRate, "100", 10, "1");
		    		
		    	}
		    	
		    	if(StringUtil.equals(SystemParm.RATE_DAY,rateAdjustBean.getRateType())){
		    		String baseRate = rateAdjustBean.getRateValue();
		    		String executeRate = BigNumberUtil.Multiply(baseRate, "30");
		    		executeRate = BigNumberUtil.Multiply(executeRate,lnRateFloat);
		    		executeRate = BigNumberUtil.Divide(executeRate,"100",10,"1");
		    		
		    		// 逾期利率
		    		String overRate = BigNumberUtil.Multiply(baseRate, "30");
		    		overRate = BigNumberUtil.Multiply(overRate,overRateFloat);
		    		overRate = BigNumberUtil.Divide(overRate,"100",10,"1");
		    		
		    		// 复利利率
		    		String cmpRate = BigNumberUtil.Multiply(baseRate, "30");
		    		cmpRate = BigNumberUtil.Multiply(cmpRate,cmpRateFloat);
		    		cmpRate = BigNumberUtil.Divide(cmpRate,"100",10,"1");
		    		
		    	}
		    	effectDates.add(partParmBean);
		    	effectDate = null;
		    	partParmBean = null;
		    }
		    return effectDates;
	}  
	
	
	/**
	 * 
	 * 方法描述： 根据利率调整日期和计息方式获得生效日期
	 * @param adjust
	 * @param rateWay
	 * void
	 * @author 乾之轩
	 * @date 2012-5-19 上午11:06:28
	 */
	private String getEffectDate(String adjustDate,String rateWay){
		String effectDate =  "";
		// 立即调整生效日期
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_IMMEDIATELY)){
			effectDate = immediatelyEffectDate(adjustDate);
		}
		//按月调整
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_MONTH)){
			effectDate = monthEffectDate(adjustDate);
		}
		//按月对日调整
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_MONTH_DAY)){
			effectDate = monthAndDayEffectDate(adjustDate);
		}
		// 按季调整
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_SEASON)){
			effectDate =  monthAndDayEffectDate(adjustDate);
		}
		//按季对月对日调整
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_SEASON_DAY)){
			effectDate =  monthAndDayEffectDate(adjustDate);
		}
		// 按年调整
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_YEAR)){
			effectDate =  yearEffectDate(adjustDate);
		}
		// 按年对月对日调整
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_YEAR_MONTH_DAY)){
			effectDate =  yearAndDayEffectDate(adjustDate);
		}
		return effectDate ;
	}
	
	/**
	 * 
	 * 方法描述： 立即调整的生效日期 
	 * void
	 * @author 乾之轩
	 * @date 2012-5-19 上午11:09:35
	 */
	private String  immediatelyEffectDate(String adjustDate){
		// 次日生效
		if(SystemParm.EffectiveType==1){
			return DateUtil.addByDay(adjustDate, 1, DateUtil.DATE_FORMAT_);
		}else{// 当天生效
			return adjustDate;
		}
	}
	
	
	/**
	 * 
	 * 方法描述： 获得按月调整的生效点
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-19 上午11:25:31
	 */
	private String monthEffectDate(String adjustDate){
		String date =  DateUtil.addByMonDay(adjustDate,1, 0, DateUtil.DATE_FORMAT_);
		return date.substring(0, 8)+"01";
	}
	/**
	 * 
	 * 方法描述：   获得按月对日调整的生效点
	 * @param adjustDate
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-19 上午11:41:55
	 */
	private String monthAndDayEffectDate(String adjustDate){
		String date =  DateUtil.addByMonDay(adjustDate,1, 0, DateUtil.DATE_FORMAT_);
		return date;
	}
	
	/**
	 * 
	 * 方法描述：  获得按季调整的生效点
	 * @param adjustDate
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-19 下午03:17:52
	 */
	private String seasonEffectDate(String adjustDate){
		String date = ""; 
		int month = DateUtil.getMonth(adjustDate);
		
		// 1 2 3
		if(month<4){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-04-01"; 
		}
		
		//4 5 6
		if(3<month && month<7){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-07-01"; 
		}
		
		// 7 8 9
		if(6<month && month<10){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-10-01"; 
		}
		
		//10 11 12
		if(9<month && month<13){
			int year = DateUtil.getYear(adjustDate);
			date = year+1+"-01-01"; 
		}
		
		return date;
	}
	/**
	 * 
	 * 方法描述： 
	 * @param adjustDate
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-19 下午03:36:08
	 */
	private String seasonAndDayEffectDate(String adjustDate){
		
		String date = ""; 
		int month = DateUtil.getMonth(adjustDate);
		int day = DateUtil.getMonth(adjustDate);
		String s_day = String.valueOf(day);
		if(day<10){
			s_day = "0"+day;
		}
		
		// 1 2 3
		if(month<4){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-04-"+s_day; 
		}
		
		//4 5 6
		if(3<month && month<7){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-07-"+s_day; 
		}
		
		// 7 8 9
		if(6<month && month<10){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-10-"+s_day; 
		}
		
		//10 11 12
		if(9<month && month<13){
			int year = DateUtil.getYear(adjustDate);
			date = year+1+"-01-"+s_day; 
		}
		
		return date;
	}
	
	
	/**
	 * 
	 * 方法描述：  获得按年调整的生效日
	 * @param adjustDate
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-19 下午03:53:23
	 */
	private  String yearEffectDate(String adjustDate){
		int year = DateUtil.getYear(adjustDate);
		year =  year+1;
 		int month = DateUtil.getMonth(adjustDate);
		String s_month  = String.valueOf(month);
		if(month<10){
			s_month = "0"+month;
		}
		return year+"-"+s_month+"-01";
	}
	
	
	/**
	 * 
	 * 方法描述： 获得按年对月对日调整的生效日
	 * @param adjustDate
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-19 下午04:02:35
	 */
	private String yearAndDayEffectDate(String adjustDate){
		int year = DateUtil.getYear(adjustDate);
		year =  year+1;
 		int month = DateUtil.getMonth(adjustDate);
		String s_month  = String.valueOf(month);
		if(month<10){
			s_month = "0"+month;
		}
		int day = DateUtil.getDay(adjustDate);
		String s_day  = String.valueOf(day); 
		if(day<10){
			s_day = "0"+day;
		}
		return year+"-"+s_month+"-"+s_day;
	}
	
	
	
	
	/*************************************************新版结束******************************************************/
	
	/**
	 * 获得计算逾期利息时使用的基数
	 */
private  String getOverBase(PlanBean planBean,AcLnMstBean acLnMstBean){
	String overBase = "0.00";
	//判断还款方式
	String returnMethod = acLnMstBean.getReturnMethod();
	// 利随本清
	if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR, returnMethod)){
		
		//  获得剩余本金
		String dueBal  = acLnMstBean.getDueBal();
		//  从日终表获得利息 逾期利息  复利利息的和
		
		// 获得日终服务类
		DayRateServices   dayRateServices = SpringFactory.getBean("dayRateServices");
		RatedayBean  tempRatedayBean = new RatedayBean();
		tempRatedayBean.setDueNo(acLnMstBean.getDueNo());
		RatedayBean  ratedayBean = dayRateServices.getRateDay(tempRatedayBean);
		// 获得欠款服务类
		DebtServices debtServices = SpringFactory.getBean("debtServices");
		DebtBean  debtBean =  new DebtBean();
		debtBean.setDueNo(acLnMstBean.getDueNo());
		debtBean.setTermNo("1");
		debtServices.getDebtBean(debtBean);
	    // 剩余本金 
		overBase  = BigNumberUtil.add2String(acLnMstBean.getDueBal(), overBase);
		// 剩余本金加利息
		overBase  = BigNumberUtil.add2String(overBase, ratedayBean.getInterest());
		// 剩余本金 加利息 加逾期利息
		overBase  = BigNumberUtil.add2String(overBase, ratedayBean.getOverInterest());
		// 剩余本金 加利息 加逾期利息 加复利利息
		overBase  = BigNumberUtil.add2String(overBase, ratedayBean.getOverInterest());
		// 剩余本金 加利息 加逾期利息 加复利利息 加欠款利息
		overBase  = BigNumberUtil.add2String(overBase,debtBean.getDebIntst());
		// 剩余本金 加利息 加逾期利息 加复利利息 加欠款利息 加欠款逾期利息
		overBase  = BigNumberUtil.add2String(overBase,debtBean.getDebOverintst());
		// 剩余本金 加利息 加逾期利息 加复利利息 加欠款利息 加欠款逾期利息 加复利利息
		overBase  = BigNumberUtil.add2String(overBase,debtBean.getDebCmpdintst());
	}else{
		// 剩余本金 
		overBase  = BigNumberUtil.add2String(acLnMstBean.getDueBal(), overBase);
		// 获得是第几期还款计划
		Integer termNo = Integer.parseInt(planBean.getTermNo());
		// 根据借据号和期号(termNo)获得期号小于 termNo 并且还没有还完款的计划,取出和这些计划相对应得日终和欠款信息
		// 获得日终服务类
		DayRateServices   dayRateServices = SpringFactory.getBean("dayRateServices");
		// 参数实体
		RatedayBean   parmRateDayBean = new RatedayBean();
		parmRateDayBean.setDueNo(planBean.getDueNo());
		parmRateDayBean.setTermNo(planBean.getTermNo());
		// 获得期号小雨当前
		List<RatedayBean> ratedayBeans = dayRateServices.getRateDayBeanList(parmRateDayBean);
		for(RatedayBean ratedayBean :ratedayBeans){
			overBase = BigNumberUtil.Add(overBase,ratedayBean.getInterest(),ratedayBean.getOverInterest(),ratedayBean.getCmpdInterest(),ratedayBean.getAccFee());
		}
		// 获得欠款服务类
		DebtServices debtServices = SpringFactory.getBean("debtServices");
		DebtBean  parmDebtBean = new DebtBean();
		parmDebtBean.setDueNo(planBean.getDueNo());
		parmDebtBean.setTermNo(planBean.getTermNo());
		
		List<DebtBean> debtBeanList =  debtServices.getLoanDebtList(parmDebtBean);
		
		for(DebtBean debtBean:debtBeanList){
			overBase = BigNumberUtil.Add(overBase,debtBean.getDebIntst(),debtBean.getDebOverintst(),debtBean.getDebCmpdintst(),debtBean.getDebAccFee());
		}
	}
	return overBase;
}




private RatedayBean getLastRatedayBean(List<RatedayBean> ratedayBeans,String termNo){
	List<RatedayBean>  result = null;
	
	for (RatedayBean ratedayBean : ratedayBeans) {
		if(StringUtil.equals(termNo, ratedayBean.getTermNo())){
			if (result==null) {
				result = new ArrayList<RatedayBean>();
			}
			result.add(ratedayBean);
		}
	}
	
	if(result!=null){
		Collections.sort(result);
	}
	
	return result.get(result.size()-1);
}



}
