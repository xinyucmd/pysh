/**
 * Copyright (C) DXHM 版权所有

 * 文件名： InterestCom.java
 * 包名： com.dx.loan.repay.interest
 * 说明：

 * @author 乾之轩

 * @date 2012-5-10 上午10:49:09
 * @version V1.0
 */
package com.jiangchuanbanking.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.subscription.domain.PactInfo;

/**
 * 类名： InterestCom
 * 描述： 内部利息利息计算
 * @author 乾之轩
 * @date 2012-5-10 上午10:49:09
 */
public class Interest {
	
	/****************************日终批量利息计算****************************/
	
	/**
	 * 
	 * 方法描述：利息计算 生成还款计划时使用
	 * void
	 * @author 乾之轩
	 * @date 2012-5-18 下午05:30:02
	 */
	public String  getInterest(PlanBean planBean,PactInfo pactInfo){
		   	String interest = "0.0";
//			/** 计算正常利息**/
			// 利随本清
			if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR, pactInfo.getPayment_type())){
				interest = getRateCome_1(planBean,pactInfo);
			}
			
			// 一次偿还本金按季结息
			if(StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, pactInfo.getPayment_type())){
				interest = getRateCome_3(planBean,pactInfo);
			}
			
			// 等额本金利息
			if(StringUtil.equals(SystemParm.RETURNMETHOD_PRINCIPAL, pactInfo.getPayment_type())){
				interest = getRateCome_4(planBean,pactInfo);
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
	public String getInterest(PlanBean planBean,int terms,String remaMoney,PactInfo pactInfo){
		String money = "0.00";
		// 利息计算时用的利率 0 日利率 1 月利率  2足月用月利率 ,不足部分用日利率
		//int useRateType = SystemParm.USE_RATE_TYPE;
//		int useRateType = 1;
		// 当用日利率计算时每月的天数 0 实际天数 1 足月按30天
//		int monthOfDays = SystemParm.MONTH_OF_DAYS;
//		if(useRateType==1){
		// 执行利率(月利率)
		String lnRate = pactInfo.getRate();
//			if(StringUtil.isNotBlank(lnRate)&&Integer.parseInt(acLnMstBean.getTermMon())<=6){
//				if(!StringUtil.equals("18.666667", lnRate) && Integer.parseInt(acLnMstBean.getTermMon())<7  ){
//					lnRate = "18.666667";
//				}
//			}
		// 本金乘以月利率
		String totalMoney = BigNumberUtil.Multiply(pactInfo.getDue_bal(), lnRate);
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
        money = BigNumberUtil.Divide(tempMoney, monthRate3,10, "1");
//		}
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
	public String getInterestByMonth(PlanBean planBean,String remaMoney,PactInfo pactInfo){
		String interest = "0.00";
		String lnRate = pactInfo.getRate();
//		if(StringUtil.isNotBlank(lnRate)&&Integer.parseInt(acLnMstBean.getTermMon())<=6){
//			if(!StringUtil.equals("18.666667", lnRate) && Integer.parseInt(acLnMstBean.getTermMon())<7){
//				lnRate = "18.666667";
//			}
//		}
		remaMoney = BigNumberUtil.Divide2(remaMoney, "1");
		interest = BigNumberUtil.Multiply(remaMoney, lnRate);
		interest = BigNumberUtil.Divide(interest, "1000",10, "1");
		return interest;
	}
	
	/**
	 * @功能: 计算等额本息每月偿还的本金
	 * @param capital 本金
	 * @param rate    月利率
	 * @param nowTerm 当前还款月数(第几个月还款)
	 * @param totalTerm    总期数
	 * @return
	 */
	public  String  getCapitalByMonth(String capital,String monthRate,String nowTerm,String totalTerm){
		// 1 加月利率
		String rateAddOne = BigNumberUtil.Add(monthRate,"1");
		// 当期期数减去1
		String nowTermSubOne  =BigNumberUtil.Subtract(nowTerm,"1");
		
		// 1加月利率的n-1次幂
		String tempValue =  Math.pow(Double.parseDouble(rateAddOne),Double.parseDouble(nowTermSubOne))+"";
		tempValue = BigNumberUtil.Multiply(tempValue, capital);
		tempValue = BigNumberUtil.Multiply(tempValue, monthRate);
		
		
		String tempValue1 =  Math.pow(Double.parseDouble(rateAddOne),Double.parseDouble(totalTerm))+"";
		tempValue1 = BigNumberUtil.Subtract(tempValue1, "1");
		
		String returnCapital = BigNumberUtil.Divide(tempValue, tempValue1, 2, "1");
		
		return returnCapital;
	}
	
	public   static   void   main(String ...s){
		Interest  Interest = new Interest();  
		Interest.getCapitalByMonth("200000", "0.02", "5", "6");
		
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
	public String getInterestByDay(PlanBean planBean,String remaMoney,PactInfo pactInfo){
		String interest = "0.00";
		int [] monthAndDay = DateUtil.getMonthsAndDays(pactInfo.getBeginDate(), planBean.getEnd_date());
		int days = monthAndDay[0]*30+monthAndDay[1];
		String lnRate = pactInfo.getRate();
//		if(StringUtil.isNotEmpty(lnRate)&&Integer.parseInt(acLnMstBean.getTermMon())<=6){
//			if(!StringUtil.equals("18.666667", lnRate) && Integer.parseInt(acLnMstBean.getTermMon())<7 ){
//				lnRate="18.666667";
//			}
//		}
		interest = BigNumberUtil.Multiply(remaMoney,lnRate);
		
		interest = BigNumberUtil.Multiply(interest,String.valueOf(days));
		interest = BigNumberUtil.Divide(interest, "30000",2, "1");
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
//	private String getRateComeByAdjust(String principal,String beginDate,String endDate,String lnRate ,List<PartParmBean> partParmBeans){
//			String interest = "0.0";
//			
//			
//			// 第一段使用老利率,第二段使用第一次调整后的利率,第三段使用第二次调整后的利率一次类推第n段使用第n-1次调整后的利息
//			// 判断是不是第一段
//			boolean isFirstPart  = true;
//			String executeRate = lnRate;
//			String begDate = beginDate;
//			for(PartParmBean  partParmBean:partParmBeans){
//			    if(isFirstPart){
//			    	String days = String.valueOf(DateUtil.getBetweenDays(begDate, partParmBean.getEffectDate()));
//			    	interest = BigNumberUtil.Multiply(principal, days);
//			    	String dayRate =  monthRate2DayRate(executeRate);
//			    	interest = BigNumberUtil.Multiply(interest, dayRate);
//			    	interest = BigNumberUtil.Divide(interest, "1000", 10, "1");
//			    	isFirstPart = false;
//			    	executeRate = partParmBean.getExecuteRate();
//			    	begDate = partParmBean.getEffectDate();
//			    }else{
//			    	String days = String.valueOf(DateUtil.getBetweenDays(begDate,partParmBean.getEffectDate()));
//			    	String tempInterest =  BigNumberUtil.Multiply(principal, days);
//			    	String dayRate =  monthRate2DayRate(executeRate);
//			    	tempInterest = BigNumberUtil.Multiply(tempInterest,dayRate);
//			    	tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 10, "1");
//			    	interest = BigNumberUtil.Add(interest,tempInterest);
//			    	executeRate = partParmBean.getExecuteRate(); 
//			    	begDate = partParmBean.getEffectDate();
//			    }	
//			}	
//			return interest;
//	}
	
	/**
	 * 
	 * 方法描述： 利随本清利息  
	 * void
	 * @author 乾之轩
	 * @date 2012-5-21 下午01:54:03
	 */
	private String  getRateCome_1(PlanBean planBean,PactInfo pactInfo){
			String interest = "0.00";
			// 计算利息时使用月利率还是日利率 0 日利率 1 月利率
		    int useRateType = 2;
		    //  当选月利率,并且不是足月的处理, 0  足月部分使用月利率计算,不足月部分使用日利率计算 1 使用日利率计算
		    int rateNotFull = SystemParm.RATE_NOT_FULL;
		    // 获得合同的期限月期限日
		    int monthAndDays[] = DateUtil.getMonthsAndDays(planBean.getBeg_date(), planBean.getEnd_date());
		    int limitMonth = monthAndDays[0];
		    int limitDays = monthAndDays[1];
		    // 借据本金
		    String principal = String.valueOf(pactInfo.getPact_amt()); 
		    // 合同利率
		    String executeRate = pactInfo.getRate();
		    
		    
		    
//		    if(StringUtil.isNotBlank(executeRate)&&Integer.parseInt(acLnMstBean.getTermMon())<=6){
//				if(!StringUtil.equals("18.666667", executeRate)){
//					executeRate = "18.666667";
//				}
//			}
		    //年利率 add by wangyuqi
		    if(useRateType==2){
				//12个月总收益
				String interestTall = BigNumberUtil.Multiply(principal, BigNumberUtil.Divide2(executeRate, "100"));
				//12个月除以实际期数
				String n=BigNumberUtil.Divide2("12",String.valueOf(limitMonth));
				//实际收益
				interest=BigNumberUtil.Divide2(interestTall, n);		
		    }
		    
		    
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
		    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBeg_date(), planBean.getEnd_date()));
		    			days = StringUtil.KillNull(days,"0");
		    		}
		    		// 满月部分按30天计算
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days= String.valueOf(limitMonth*30+limitDays);
		    		}
		    		// 本金乘以日利率乘以期限日
		    		String interestDay = BigNumberUtil.Multiply(principal, days);
		    		interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
		    		interest = interestDay;
		    	}
		    }
		    
		    // 使用日利率计算
		    if(useRateType==0){
		    	//贷款期限天
		    	String days = "0";
		    	// 按照实际天数计算期限
		    	if(SystemParm.MONTH_OF_DAYS==0){
		    		days = String.valueOf(DateUtil.getBetweenDays(planBean.getBeg_date(), planBean.getEnd_date()));
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
		    	
		    	interest = BigNumberUtil.Divide(interestDay, "1000", 2,"1");
		    }
		    
			return  interest; 
	}
	
//	/**
//	 * 
//	 * 方法描述： 按计划利息
//	 * 1 使用月利率进行计算,足月直接使用月利率.不是足月的足月部分使用月利率,非足月使用日利率.整期使用日利率.
//	 * 2 使用日利率进行计算
//	 * 3 使用日利率时需要转化足月的天数,是实际天数还是按照30天来计算
//	 * @param planBean
//	 * @param acLnMstBean
//	 * void
//	 * @author 乾之轩
//	 * @date 2012-6-4 上午10:19:05
//	 */
//	private String getRateCome_2(PlanBean planBean,AcLnMstBean acLnMstBean){
//		// 利息
//		String interest = "0.00";
//		// 计算利息时使用月利率还是日利率 0 日利率 1 月利率
//	    int useRateType = SystemParm.USE_RATE_TYPE;
//	    //  当选月利率,并且不是足月的处理, 0  足月部分使用月利率计算,不足月部分使用日利率计算 1 使用日利率计算
//	    int rateNotFull = SystemParm.RATE_NOT_FULL; 
//	  //  int rateNotFull = 2;
//	    // 本金 (生成还款计划时使用)
//	    String principal = planBean.getDueBal();
//	    // 合同利率
//	    String exxcuteRate = acLnMstBean.getLnRate();
////	    int termMonth = Integer.parseInt(acLnMstBean.getTermMon());
////	    if(StringUtil.isNotBlank(exxcuteRate)&&Integer.parseInt(acLnMstBean.getTermMon())<=6){
////			if(!StringUtil.equals("18.666667", exxcuteRate) &&  termMonth<7  ){
////				exxcuteRate = "18.666667";
////			}
////		}
//	    // 还款计划的期限
//	    int []monthAndDay = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
//	   
//	    if(useRateType==1){
//	    	// 判断是不是整月,monthAndDay[1]==0 说明是整月
//	    	if(monthAndDay[1]==0){
//	    		// 本金乘以月利率
//	    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
//	    		// 本金乘以月利率乘以期限
//	    		interestMonth = BigNumberUtil.Multiply(interestMonth, String.valueOf(monthAndDay[0]));
//	    		interest = BigNumberUtil.Divide(interestMonth, "1000", 2, "1");
//	    	}else{
//	    		// 不是整月
//	    		if(rateNotFull==0){
//	    			/** 足月部分使用月利率,不足月部分使用日利率**/
//	    			
//	    			// 本金乘以月利率
//		    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
//		    		// 本金乘以月利率乘以期限
//		    		
//		    		interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10, "1");//(interestMonth, "1000");
//		    		
//		    		interestMonth = BigNumberUtil.Divide(interestMonth, "30", 10, "1");
//		    		
//		    		interestMonth = BigNumberUtil.Multiply(interestMonth,monthAndDay[1]+"");
//		    		interestMonth = BigNumberUtil.Divide(interestMonth, "1", 2, "1");
//		    		interest = interestMonth;
//	    		}
//	    		
//	    		if(rateNotFull==1){
//	    			/**使用日利率**/
//	    			// 将月利率转化为日利率
//		    		String dayRate = monthRate2DayRate(exxcuteRate);
//		    		String days = "0";
//		    		// 满月部分为实际天数
//		    		if(SystemParm.MONTH_OF_DAYS==0){
//		    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBeg_date(), planBean.getEnd_date()));
//		    		}
//		    		// 满月部分按30天计算
//		    		if(SystemParm.MONTH_OF_DAYS==0){
//		    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
//		    		}
//		    		
//		    		// 本金乘以日利率乘以期限日
//		    		String interestDay = BigNumberUtil.Multiply(principal, days);
//		    		interestDay = BigNumberUtil.Multiply(principal, dayRate);
//		    		interestDay = BigNumberUtil.Divide(interestDay, "10000", 2, "1");
//		    		interest = interestDay;
//	    		}
//	    		
//	    		/*if(rateNotFull==2){
//	    			*//** 使用月利率**//*
//	    			// 本金乘以月利率
//		    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
//		    	
//		    		interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10, "1");//(interestMonth, "1000");
//		    		interestMonth = BigNumberUtil.Divide(interestMonth, "1", 2, "1");
//		    		interest = interestMonth;
//	    		}*/
//	    	}
//	    }else{
//	    	/**使用日利率**/
//			// 将月利率转化为日利率
//    		String dayRate = monthRate2DayRate(exxcuteRate);
//    		String days = "0";
//    		// 满月部分为实际天数
//    		if(SystemParm.MONTH_OF_DAYS==0){
//    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBeg_date(), planBean.getEnd_date()));
//    		}
//    		// 满月部分按30天计算
//    		if(SystemParm.MONTH_OF_DAYS==1){
//    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
//    		}
//    		
//    		// 本金乘以日利率乘以期限日
//    		String interestDay = BigNumberUtil.Multiply(principal, days);
//    		interestDay = BigNumberUtil.Multiply(interestDay, dayRate);
//    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
//    		interest = interestDay;
//	    }
//	    return interest;
//	}
	
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
	private String getRateCome_3(PlanBean planBean,PactInfo pactInfo){
		// 利息
		String interest = "0.00";
		// 计算利息时使用月利率还是日利率 0 日利率 1 月利率
	    int useRateType = SystemParm.USE_RATE_TYPE;
	    //  当选月利率,并且不是足月的处理, 0  足月部分使用月利率计算,不足月部分使用日利率计算 1 使用日利率计算
	    int rateNotFull = 2;
	    // 本金 (生成还款计划时使用)
	    //String principal = planBean.getDue_bal();
	    String principal=pactInfo.getPact_amt().toString();
	    if(StringUtil.equals("0",principal)){
	    	return "0.00";
	    }
	    // 合同利率
	    String exxcuteRate = pactInfo.getRate();
	    //add by wangyuqi 计算月利率
	    exxcuteRate=BigNumberUtil.Divide5(exxcuteRate, "12"); 
	    exxcuteRate=BigNumberUtil.Multiply(exxcuteRate, "10");
	    // 还款计划的期限
	    int []monthAndDay = DateUtil.getMonthsAndDays(planBean.getBeg_date(), planBean.getEnd_date());
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
		    		if(StringUtil.equals("0.00",interestMonth)){
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
		    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBeg_date() , planBean.getEnd_date()));
		    		}
		    		// 满月部分按30天计算
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
		    		}
		    		
		    		// 本金乘以日利率乘以期限日
		    		String interestDay = BigNumberUtil.Multiply(principal, days);
		    		interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		interestDay = BigNumberUtil.Divide(interestDay, "10000", 2, "1");
		    		interest = interestDay;
	    		}
	    		if(rateNotFull==2){
	    			/** 使用月利率**/
	    			// 本金乘以月利率
		    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
		    	
		    		interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10, "1");//(interestMonth, "1000");
		    		interestMonth = BigNumberUtil.Divide(interestMonth, "1", 2, "1");
		    		interest = interestMonth;
	    		}
	    	}
	    }else{
	    	/**使用日利率**/
			// 将月利率转化为日利率
    		String dayRate = monthRate2DayRate(exxcuteRate);
    		String days = "0";
    		// 满月部分为实际天数
    		if(SystemParm.MONTH_OF_DAYS==0){
    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBeg_date(), planBean.getEnd_date()));
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
	private String getRateCome_4(PlanBean planBean,PactInfo pactInfo){
		String interest= "0.00";
		int[] monthAndDay = DateUtil.getMonthsAndDays(planBean.getBeg_date(), planBean.getEnd_date());
		// 月利率
		if(SystemParm.USE_RATE_TYPE==1){
			// 是足月
			if (monthAndDay[1]==0) {
				String  interestMonth = "0.00";
				// 月利率
				String lnRate = pactInfo.getRate();
//				if(StringUtil.isNotBlank(lnRate)&&Integer.parseInt(acLnMstBean.getTermMon())<=6){
//					if(!StringUtil.equals("18.666667", lnRate)){
//						lnRate = "18.666667";
//					}
//				}
				// 剩余本金
				//String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
				String remaCapital = planBean.getDue_bal();
				
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
					String lnRate = pactInfo.getRate();
//					if(StringUtil.isNotBlank(lnRate)&&Integer.parseInt(acLnMstBean.getTermMon())<=6){
//						if(!StringUtil.equals("18.666667", lnRate)){
//							lnRate = "18.666667";
//						}
//					}
					// 剩余本金
					//String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
					String remaCapital = planBean.getDue_bal();
					
					/**足月部分利息**/
					// 剩余本金乘以月利率
					interestMonth = BigNumberUtil.Multiply(remaCapital,lnRate);
					// 剩余本金乘以月利率乘以期限月
					interestMonth = BigNumberUtil.Multiply(interestMonth,String.valueOf(monthAndDay[0]));
					interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10, "1");
					/**不足月部分利息**/
					
					String dayRate = monthRate2DayRate(lnRate);
					// 剩余本金乘以日利率
					interestDay = BigNumberUtil.Multiply(remaCapital,dayRate);
					// 剩余本金乘以月利率乘以期限日
					interestDay = BigNumberUtil.Multiply(interestDay,String.valueOf(monthAndDay[1]));
					interestDay = BigNumberUtil.Divide(interestDay, "10000", 10, "1");
					
					// 足月部分利息加不足月部分利息
					interest = BigNumberUtil.Add(interestDay,interestMonth);
					interest = BigNumberUtil.Divide(interest, "1", 2, "1");
					
				}
				// 使用日利率计算
				if (SystemParm.RATE_NOT_FULL==1) {
					String  interestDay = "0.00";
					// 剩余本金
					String remaCapital = BigNumberUtil.Subtract(pactInfo.getDue_bal(), planBean.getDue_bal());
					// 月利率
					String lnRate = pactInfo.getDue_bal();
//					if(StringUtil.isNotBlank(lnRate)&&Integer.parseInt(acLnMstBean.getTermMon())<=6){
//						if(!StringUtil.equals("18.666667", lnRate)){
//							lnRate = "18.666667";
//						}
//					}
					String dayRate = monthRate2DayRate(lnRate);
					int days = 0;
					// 一个月按30 天计算
					if (SystemParm.MONTH_OF_DAYS==1) {
						days = monthAndDay[1]*30;
					}
					// 取一个月的实际天数
					if (SystemParm.MONTH_OF_DAYS==0) {
						days = DateUtil.getBetweenDays(planBean.getBeg_date(), planBean.getEnd_date());
					}
					days = days +monthAndDay[1];
					
					// 剩余本金乘以日利率
					interestDay = BigNumberUtil.Multiply(remaCapital,dayRate);
					// 剩余本金乘以月利率乘以期限日
					interestDay = BigNumberUtil.Multiply(interestDay,String.valueOf(monthAndDay[1]));
					interest = BigNumberUtil.Divide(interestDay, "10000", 2, "1");
				}
			}
		}
		
		// 使用日利率计算
		if(SystemParm.USE_RATE_TYPE==0){
			String  interestDay = "0.00";
			// 剩余本金
			//String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
			String remaCapital =   planBean.getDue_bal();
			// 月利率
			String lnRate =pactInfo.getRate();
//			if(StringUtil.isNotBlank(lnRate)&&Integer.parseInt(acLnMstBean.getTermMon())<=6){
//				if(!StringUtil.equals("18.666667", lnRate)){
//					lnRate = "18.666667";
//				}
//			}
			String dayRate = monthRate2DayRate(lnRate);
			int days = 0;
			// 一个月按30 天计算
			if (SystemParm.MONTH_OF_DAYS==1) {
				days = monthAndDay[1]*30;
			}
			// 取一个月的实际天数
			if (SystemParm.MONTH_OF_DAYS==0) {
				if (monthAndDay[0]>0) {
					days = DateUtil.getDay(planBean.getBeg_date());
					days = DateUtil.getBetweenDays(planBean.getBeg_date(), planBean.getEnd_date());
				}
			}
			days = days +monthAndDay[1];
			
			// 剩余本金乘以日利率
			interestDay = BigNumberUtil.Multiply(remaCapital,dayRate);
			// 剩余本金乘以月利率乘以期限日
			interestDay = BigNumberUtil.Multiply(interestDay,String.valueOf(days));
			interest = BigNumberUtil.Divide(interestDay, "10000", 2, "1");
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
//	private String seasonEffectDate(String adjustDate){
//		String date = ""; 
//		int month = DateUtil.getMonth(adjustDate);
//		
//		// 1 2 3
//		if(month<4){
//			int year = DateUtil.getYear(adjustDate);
//			date = year+"-04-01"; 
//		}
//		
//		//4 5 6
//		if(3<month && month<7){
//			int year = DateUtil.getYear(adjustDate);
//			date = year+"-07-01"; 
//		}
//		
//		// 7 8 9
//		if(6<month && month<10){
//			int year = DateUtil.getYear(adjustDate);
//			date = year+"-10-01"; 
//		}
//		
//		//10 11 12
//		if(9<month && month<13){
//			int year = DateUtil.getYear(adjustDate);
//			date = year+1+"-01-01"; 
//		}
//		
//		return date;
//	}
	
	/**
	 * 
	 * 方法描述： 
	 * @param adjustDate
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-19 下午03:36:08
	 */
//	private String seasonAndDayEffectDate(String adjustDate){
//		
//		String date = ""; 
//		int month = DateUtil.getMonth(adjustDate);
//		int day = DateUtil.getMonth(adjustDate);
//		String s_day = String.valueOf(day);
//		if(day<10){
//			s_day = "0"+day;
//		}
//		
//		// 1 2 3
//		if(month<4){
//			int year = DateUtil.getYear(adjustDate);
//			date = year+"-04-"+s_day; 
//		}
//		
//		//4 5 6
//		if(3<month && month<7){
//			int year = DateUtil.getYear(adjustDate);
//			date = year+"-07-"+s_day; 
//		}
//		
//		// 7 8 9
//		if(6<month && month<10){
//			int year = DateUtil.getYear(adjustDate);
//			date = year+"-10-"+s_day; 
//		}
//		
//		//10 11 12
//		if(9<month && month<13){
//			int year = DateUtil.getYear(adjustDate);
//			date = year+1+"-01-"+s_day; 
//		}
//		
//		return date;
//	}
	
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
//private  String getOverBase(PlanBean planBean,AcLnMstBean acLnMstBean){
//	String overBase = "0.00";
//	//判断还款方式
//	String returnMethod = acLnMstBean.getReturnMethod();
//	// 利随本清
//	if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR, returnMethod)){
//		
//		//  获得剩余本金
//		String dueBal  = acLnMstBean.getDueBal();
//		//  从日终表获得利息 逾期利息  复利利息的和
//		
//		// 获得日终服务类
//		DayRateServices   dayRateServices = SpringFactory.getBean("dayRateServices");
//		RatedayBean  tempRatedayBean = new RatedayBean();
//		tempRatedayBean.setDueNo(acLnMstBean.getDueNo());
//		RatedayBean  ratedayBean = dayRateServices.getRateDay(tempRatedayBean);
//		// 获得欠款服务类
//		DebtServices debtServices = SpringFactory.getBean("debtServices");
//		DebtBean  debtBean =  new DebtBean();
//		debtBean.setDueNo(acLnMstBean.getDueNo());
//		debtBean.setTermNo("1");
//		debtServices.getDebtBean(debtBean);
//	    // 剩余本金 
//		overBase  = BigNumberUtil.add2String(acLnMstBean.getDueBal(), overBase);
//		// 剩余本金加利息
//		overBase  = BigNumberUtil.add2String(overBase, ratedayBean.getInterest());
//		// 剩余本金 加利息 加逾期利息
//		overBase  = BigNumberUtil.add2String(overBase, ratedayBean.getOverInterest());
//		// 剩余本金 加利息 加逾期利息 加复利利息
//		overBase  = BigNumberUtil.add2String(overBase, ratedayBean.getOverInterest());
//		// 剩余本金 加利息 加逾期利息 加复利利息 加欠款利息
//		overBase  = BigNumberUtil.add2String(overBase,debtBean.getDebIntst());
//		// 剩余本金 加利息 加逾期利息 加复利利息 加欠款利息 加欠款逾期利息
//		overBase  = BigNumberUtil.add2String(overBase,debtBean.getDebOverintst());
//		// 剩余本金 加利息 加逾期利息 加复利利息 加欠款利息 加欠款逾期利息 加复利利息
//		overBase  = BigNumberUtil.add2String(overBase,debtBean.getDebCmpdintst());
//	}else{
//		// 剩余本金 
//		overBase  = BigNumberUtil.add2String(acLnMstBean.getDueBal(), overBase);
//		// 获得是第几期还款计划
//		Integer termNo = Integer.parseInt(planBean.getTermNo());
//		// 根据借据号和期号(termNo)获得期号小于 termNo 并且还没有还完款的计划,取出和这些计划相对应得日终和欠款信息
//		// 获得日终服务类
//		DayRateServices   dayRateServices = SpringFactory.getBean("dayRateServices");
//		// 参数实体
//		RatedayBean   parmRateDayBean = new RatedayBean();
//		parmRateDayBean.setDueNo(planBean.getDueNo());
//		parmRateDayBean.setTermNo(planBean.getTermNo());
//		// 获得期号小雨当前
//		List<RatedayBean> ratedayBeans = dayRateServices.getRateDayBeanList(parmRateDayBean);
//		for(RatedayBean ratedayBean :ratedayBeans){
//			overBase = BigNumberUtil.Add(overBase,ratedayBean.getInterest(),ratedayBean.getOverInterest(),ratedayBean.getCmpdInterest(),ratedayBean.getAccFee());
//		}
//		// 获得欠款服务类
//		DebtServices debtServices = SpringFactory.getBean("debtServices");
//		DebtBean  parmDebtBean = new DebtBean();
//		parmDebtBean.setDueNo(planBean.getDueNo());
//		parmDebtBean.setTermNo(planBean.getTermNo());
//		
//		List<DebtBean> debtBeanList =  debtServices.getLoanDebtList(parmDebtBean);
//		
//		for(DebtBean debtBean:debtBeanList){
//			overBase = BigNumberUtil.Add(overBase,debtBean.getDebIntst(),debtBean.getDebOverintst(),debtBean.getDebCmpdintst(),debtBean.getDebAccFee());
//		}
//	}
//	return overBase;
//}

}
