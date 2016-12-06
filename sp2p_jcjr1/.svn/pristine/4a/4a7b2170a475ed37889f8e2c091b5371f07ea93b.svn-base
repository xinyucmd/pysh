/**
 * 
 */
package com.sp2p.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;


/**
 * 债权转让工具类
 * @author Administrator
 *
 */
public class DebtUtil {
	
	public static Log log = LogFactory.getLog(DebtUtil.class);
	
	public static Map<String, Double> rateMap = new HashMap<String,Double>();
	static{
//		rateMap.put("1", 6.00);
//		rateMap.put("3", 8.00);
//		rateMap.put("6", 10.00);
//		rateMap.put("12", 11.00);
		
//		rateMap.put("1", 6.00);
//		rateMap.put("3", 7.00);
//		rateMap.put("6", 9.75);
//		rateMap.put("12", 11.00);
		
//		rateMap.put("1", 6.00);
//		rateMap.put("3", 7.00);
//		rateMap.put("6", 8.50);
//		rateMap.put("12", 10.00);
		
//		最新利率调整
		rateMap.put("1", 5.50);
		rateMap.put("3", 6.50);
		rateMap.put("6", 7.50);
		rateMap.put("12", 9.00);
	}
	
	/**
	 * 债权转让公式
	 * 
	 * @param：borrowId借款id;investId还款拥有者;annualRate年利率;transRatio转让系数;nextRepayDate下一还款日期 isAdd 0 默认，1忽略加息
	 * @author 黑暗珊瑚君
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	@SuppressWarnings("unused")
	public static Map<String,Object> debtFormula(Map<String,String> debtMap,String annualRateDebt) throws Exception{
		
//		log.info("=======================>"+debtMap);
			
		Map<String,Object> result = new HashMap<String, Object>();
					
		//保留位数
		int scale = 100;
		
		//年利率
		BigDecimal annualRate = new BigDecimal("0.00");
		
		//期数(原债权期数)
		BigDecimal deadline = new BigDecimal("0.00");
		
		//转让系数
//		BigDecimal trans = new BigDecimal("0.00");
		
		//转让利率
		BigDecimal tRate = new BigDecimal("0.00");
		
		//应计利息天数 (原债权人补偿天数)
		BigDecimal days = new BigDecimal("0.00");
		
		//利息补偿天数（债权购买人需要补偿的利息天数）
		BigDecimal recoupDays = new BigDecimal("0.00");
		
		// 债权剩余还款天数
		BigDecimal remainingDays = new BigDecimal("0.00");
		
		// 债权待尝期数(剩余的期数)
		BigDecimal periodsBanlanceCount = new BigDecimal("0.00");
		
		// 转让后的利率
		BigDecimal annualRateDebtBD = new BigDecimal("0.0");
		
		//提前还款期数
		BigDecimal k = new BigDecimal("0"); 
		
		// 剩余未还本金 
		BigDecimal principalBalance = new BigDecimal("0.00");
		
		// 剩余债转本金
		BigDecimal principalBalanceDebt = new BigDecimal("0.00");
		
		
		//债权价格（现值）
		BigDecimal debtValue = new BigDecimal("0.00");
		
		//债权价值(债权总价值)
		BigDecimal debtPrice = new BigDecimal("0.00");
		
		//保留两位小数
		DecimalFormat df = new DecimalFormat("#.00");
		
		// 初始化参数
		if(null != debtMap && debtMap.size() > 0){
			result.putAll(debtMap);// 将所有参数放入结果集中
			annualRate = new BigDecimal(debtMap.get("annualRate")).divide(new BigDecimal("100"),scale,BigDecimal.ROUND_HALF_DOWN);
			deadline = new BigDecimal(debtMap.get("deadline"));
			days = new BigDecimal(debtMap.get("days"));
			recoupDays = new BigDecimal(debtMap.get("recoupDays"));
			periodsBanlanceCount = new BigDecimal(debtMap.get("periodsBanlanceCount"));
			if(debtMap.get("principalBalance")!=null && debtMap.get("principalBalance").length()>0){
				principalBalance = new BigDecimal(debtMap.get("principalBalance"));
			}
			if(debtMap.get("remainingDays")!=null && debtMap.get("remainingDays").length()>0){
				remainingDays = new BigDecimal(debtMap.get("remainingDays"));
			}
			
			double annualRateDebtBDDouble = 0;
			
			if(debtMap.containsKey("annualRateDebtBDDouble") && debtMap.get("annualRateDebtBDDouble")!=null
					&& !debtMap.get("annualRateDebtBDDouble").equals("") 
					&& Convert.strToDouble(debtMap.get("annualRateDebtBDDouble"), -1)>0){
				annualRateDebtBDDouble = Double.parseDouble(debtMap.get("annualRateDebtBDDouble"));
			}
			
			double annualRateDebtDouble = 0f;
			// 转让利率
			if(annualRateDebt!=null && annualRateDebt.trim().length()>0 && Double.parseDouble(annualRateDebt)>0){
				annualRateDebtDouble = Double.parseDouble(annualRateDebt);
			}else if(annualRateDebtBDDouble>0){
				annualRateDebtDouble = annualRateDebtBDDouble;
			}else{// 自动计算转让利率
				if(annualRateDebtBD.intValue()<=0){
					
					double r0 =0f;
					double r1 = 0f;
					int d0 = 0;
					int d1 = 0;
					
					if(30>=remainingDays.intValue() && remainingDays.intValue()>0){
						annualRateDebtDouble = 0+((rateMap.get("1")-0)/(1-0))*(periodsBanlanceCount.intValue() -0);
						r0 =0.00f;
						r1 = rateMap.get("1");// 1个月 6;
						
						d0 = 0;
						d1 = 30;
					}
					if(90>=remainingDays.intValue() && remainingDays.intValue()>30){

						r0 =rateMap.get("1");
						r1 = rateMap.get("3");// 3个月 7;
						
						d0 = 30;
						d1 = 90;
					}
					if(180>=remainingDays.intValue() && remainingDays.intValue()>90){
						r0 =rateMap.get("3");
						r1 = rateMap.get("6");// 6个月 9;
						
						d0 = 90;
						d1 = 180;
					}
					if(360>=remainingDays.intValue() && remainingDays.intValue()>180){
						r0 =rateMap.get("6");
						r1 = rateMap.get("12");// 12个月10;
						
						d0 = 180;
						d1 = 360;
					}
					
					annualRateDebtDouble = ((r1-r0)/(d1-d0))*(remainingDays.intValue()-d0)+r0;
					
				}
			}
			
			// 感恩十一月丰收季- 加息 
			if(DateUtil.dateToStringYYMM(new Date()).equals("2015-11") && annualRateDebtDouble<12){
				
				if(remainingDays.intValue()>=90 && remainingDays.intValue()<=120){
					annualRateDebtDouble += 0.5;
				} else if(remainingDays.intValue()>=240 && remainingDays.intValue()<=330){
					annualRateDebtDouble += 0.8;
				}else if(remainingDays.intValue()==360){
					annualRateDebtDouble += 1.2;
				}
				
			}
			// 感恩十一月丰收季- 加息
			
			annualRateDebtBD = new BigDecimal(annualRateDebtDouble).divide(new BigDecimal("100"),4,BigDecimal.ROUND_DOWN);
			
			
			// 输出参数
//			log.info("annualRate,deadline,days,recoupDays,periodsBanlanceCount,principalBalance:"+
//					annualRate+","+deadline+","+days+","+recoupDays+","+periodsBanlanceCount+","+principalBalance);
//			log.info("转让利率："+annualRateDebtBD.doubleValue());
		}
		
		// 计算债转价值
		String paymentMode = debtMap.get("paymentMode");
		BigDecimal M = new BigDecimal(debtMap.get("investAmount"));
		
		// 原始月利率
		BigDecimal MR = annualRate.divide(new BigDecimal("12"),scale,BigDecimal.ROUND_HALF_DOWN);
		
		BigDecimal Mr = annualRateDebtBD.divide(new BigDecimal("12"),scale,BigDecimal.ROUND_HALF_DOWN);
		
		// 剩余待还期数
		int d = periodsBanlanceCount.intValue();
		
		if(paymentMode.equals("1")){// 等额本息
			BigDecimal FZ = M.multiply(MR).multiply((new BigDecimal("1").add(MR)).pow(deadline.intValue()));
			BigDecimal FM = (new BigDecimal("1").add(MR)).pow(deadline.intValue()).subtract(new BigDecimal("1"));
			
			BigDecimal FZ1 = (new BigDecimal("1").add(Mr)).pow(d).subtract(new BigDecimal("1"));
			BigDecimal FM1 = (new BigDecimal("1").add(Mr)).pow(d).multiply(Mr);
			
			principalBalanceDebt = FZ.divide(FM,scale,BigDecimal.ROUND_HALF_DOWN)
									.multiply(FZ1.divide(FM1,scale,BigDecimal.ROUND_HALF_DOWN));
					
		}else if(paymentMode.equals("2")){// 先息后本
			
				BigDecimal FZ = ((new BigDecimal("1").add(Mr)).pow(d).subtract(new BigDecimal("1")));
				BigDecimal FM = (new BigDecimal("1").add(Mr)).pow(d).multiply(Mr);
				
				principalBalanceDebt = M
						.multiply(MR)
						.multiply(FZ.divide(FM,scale,BigDecimal.ROUND_HALF_DOWN)
						).add(M.divide((new BigDecimal("1").add(Mr)).pow(d),scale,BigDecimal.ROUND_HALF_DOWN));
				
		}else if(paymentMode.equals("4")){// 利随本清
			
			BigDecimal FZ = M.multiply(
					(new BigDecimal("1").add(MR.multiply(deadline)))
				);
			
//			BigDecimal FM = (new BigDecimal("1").add(Mr)).pow(d);
			
			BigDecimal FM = (new BigDecimal("1").add(Mr.multiply(new BigDecimal(d))));
			principalBalanceDebt = FZ.divide(FM,scale,BigDecimal.ROUND_HALF_DOWN);
		}
		
		
//		interest = days.divide(new BigDecimal("30"),scale,BigDecimal.ROUND_HALF_DOWN).multiply(principalBalance).multiply(rate);
//		debtValue = (principalBalance.add(interest)).doubleValue();
		
		/*
		 * 统一计算债权现值
		 * P = A/(1+r%/12)k(k是幂值)
		 */
		debtValue = principalBalanceDebt.divide(new BigDecimal(String.valueOf(Math.pow(
				new BigDecimal("1").add(tRate.divide(new BigDecimal("12"),scale,BigDecimal.ROUND_HALF_DOWN)).doubleValue(),k.doubleValue())
				)), scale ,BigDecimal.ROUND_HALF_DOWN);
		
		// 原债权人应补利息
		BigDecimal BInterest = debtValue.multiply(annualRateDebtBD).multiply(days).divide(new BigDecimal("360"),scale,BigDecimal.ROUND_HALF_DOWN);
		
		// 债权总价值
		debtPrice = debtValue.add(BInterest);
		
		// 转让利率
		double annualRateDebtBDDouble = annualRateDebtBD.multiply(new BigDecimal("100")).doubleValue();
		
		result.put("term", periodsBanlanceCount);
		result.put("interest", df.format(BInterest.doubleValue()));
		result.put("principalBalance", df.format(principalBalanceDebt.doubleValue()));
		result.put("debtValue", df.format(debtValue.doubleValue())); 
		result.put("debtPrice", df.format(debtPrice.doubleValue()));
		result.put("remainPeriod", periodsBanlanceCount.doubleValue());
		result.put("remainingDays", remainingDays);
		result.put("annualRateDebtBDDouble", annualRateDebtBDDouble);
			
		return result;
	}
	
	
	/**
	 * 债权转让公式
	 * 
	 * @param：borrowId借款id;owner还款拥有者;annualRate年利率
	 * @author whb
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public static Map<String,Object> debtFormula_old(String annualRate,String transRatio,
			List<Map<String, Object>> oneList,
			List<Map<String, Object>> thisList,
			List<Map<String, Object>> countList,
			List<Map<String, Object>> nextList) throws Exception{
		
		Map<String,Object> result = new HashMap<String, Object>();
		
		//保留位数
		int scale = 10;
		//剩余未还本金
		BigDecimal principalBalance = new BigDecimal("0.00");
		//年利率
		BigDecimal rate = new BigDecimal(StringUtils.isNotBlank(annualRate)==true?annualRate:"1");
		rate = rate.divide(new BigDecimal("1200"),scale,BigDecimal.ROUND_HALF_DOWN);
		//应计利息天数
		BigDecimal days = new BigDecimal("0.00");
		//利息补偿天数
		BigDecimal recoupDays = new BigDecimal("0.00");
		//利息
		BigDecimal interest = new BigDecimal("0.00");
		//转让系数
		BigDecimal trans = new BigDecimal(StringUtils.isNotBlank(transRatio)==true?transRatio:"1");
		//债权价值
		double debtValue = 0.00;
		//债权价格
		double debtPrice = 0.00;
		//保留两位小数
		DecimalFormat df = new DecimalFormat("#.00");
		//期数判断
		int term = -1;
		if(null != countList && countList.size() > 0){
			term = Integer.parseInt(String.valueOf(countList.get(0).get("term")));
		}
		//一次性还款
		if(null != oneList && oneList.size() > 0){
			principalBalance = new BigDecimal(String.valueOf(oneList.get(0).get("investAmount")));
			days = new BigDecimal(String.valueOf(oneList.get(0).get("days")));
			interest = days.divide(new BigDecimal("30"),scale,BigDecimal.ROUND_HALF_DOWN).multiply(principalBalance).multiply(rate);
			debtValue = (principalBalance.add(interest)).doubleValue();
			debtPrice = (principalBalance.multiply(trans).add(interest)).doubleValue();
		}
		//当期还款未还     
		if(null != thisList && thisList.size() > 0){
			if(thisList.get(0).get("realRepayDate") == null || thisList.get(0).get("realRepayDate").equals("")){
				principalBalance = new BigDecimal(String.valueOf(thisList.get(0).get("principalBalance"))).add(new BigDecimal(String.valueOf(thisList.get(0).get("recivedPrincipal"))));
				days = new BigDecimal(String.valueOf(thisList.get(0).get("days")));
				interest = days.divide(new BigDecimal("30"),scale,BigDecimal.ROUND_HALF_DOWN).multiply(principalBalance).multiply(rate);
				debtValue = (principalBalance.add(interest)).doubleValue();
				term = -1;
				debtPrice = (principalBalance.multiply(trans).add(interest)).doubleValue();
			}
			else if(thisList.get(0).get("realRepayDate") != null && !thisList.get(0).get("realRepayDate").equals("")){
				//当期已还，下一期未还
				if(0 == term){
					principalBalance = new BigDecimal(String.valueOf(thisList.get(0).get("principalBalance")));
					recoupDays = new BigDecimal(String.valueOf(thisList.get(0).get("recoupDays")));
					interest = recoupDays.divide(new BigDecimal("30"),scale,BigDecimal.ROUND_HALF_DOWN).multiply(principalBalance).multiply(rate);
					debtValue = (principalBalance.subtract(interest)).doubleValue();
					debtPrice = (principalBalance.multiply(trans).subtract(interest)).doubleValue();
				}
				//当期已还，下n期已还
				if(term > 0){
					if(nextList != null && nextList.size() > 0 && null != nextList.get(0).get("principalBalance")){
						principalBalance = new BigDecimal(String.valueOf(nextList.get(0).get("principalBalance")));
					}
					//计算基数
					BigDecimal basis = new BigDecimal("0.00");
					basis = principalBalance.divide(new BigDecimal(String.valueOf(Math.pow((new BigDecimal("1").add(rate)).doubleValue(),
							Double.parseDouble(String.valueOf(term))))),scale,BigDecimal.ROUND_HALF_DOWN);
					recoupDays = new BigDecimal(String.valueOf(thisList.get(0).get("recoupDays")));
					interest = basis.multiply(recoupDays.divide(new BigDecimal("30"),scale,BigDecimal.ROUND_HALF_DOWN)).multiply(rate);
					debtValue = (basis.subtract(interest)).doubleValue();
					principalBalance = basis;
					debtPrice = (basis.multiply(trans).subtract(interest)).doubleValue();
				}
			}
		}
		result.put("term", term);
		result.put("interest", interest);
		result.put("principalBalance", principalBalance);
		result.put("debtValue", df.format(debtValue)); 
		result.put("debtPrice", df.format(debtPrice));
			
		return result;
	}
	
}