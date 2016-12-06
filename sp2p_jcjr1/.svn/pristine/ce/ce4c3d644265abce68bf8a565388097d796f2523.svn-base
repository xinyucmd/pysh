package com.sp2p.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sp2p.constants.IConstants;


public class ChanagePwdTest {

	public static void main(String[] args) throws ParseException {
		
		StringBuffer command = new StringBuffer();
		command.append("SELECT ");
		// # 原债权人补偿天数 
		command.append(" if((to_days(NOW())-to_days(date_sub(t_invest_repayment.repayDate, interval 1 month))) < 30,");
		command.append("(to_days(NOW())-to_days(date_sub(t_invest_repayment.repayDate, interval 1 month))),30) AS days, ");
		// # 债权购买人需要补偿的利息天数
		command.append("if((to_days(t_invest_repayment.repayDate)-to_days(NOW())) < 30,(to_days(t_invest_repayment.repayDate)-to_days(NOW())),30) AS recoupDays, ");
		// # 原始期限
		command.append("t_borrow.deadline as deadline, ");
		// # 原始利率
		command.append("t_borrow.annualRate as annualRate,");
		// # 还款方式
		command.append(" t_borrow.paymentMode as paymentMode ,");
		// # 还款到期日
		command.append(" date_sub(DATE_FORMAT(t_borrow.auditTime,'%Y-%m-%d'), interval -deadline MONTH) as lastRepayment,");
		// # 债权剩余天数
		command.append(" ("
				+ "TO_DAYS(date_sub(DATE_FORMAT(t_borrow.auditTime,'%Y-%m-%d'), interval -deadline MONTH))-TO_DAYS(DATE_FORMAT(NOW(),'%Y-%m-%d'))"
				+ ") as remainingDays");
		
		command.append(" from t_invest_repayment LEFT JOIN t_repayment ");
		command.append(" on t_invest_repayment.repayId = t_repayment.id  LEFT JOIN t_borrow ON t_repayment.borrowId = t_borrow.id ");
		
		command.append(" where t_invest_repayment.repayDate > date_sub(DATE_FORMAT(NOW(),'%Y-%m-%d'), interval 1 day) ");
		command.append(" and date_sub(t_invest_repayment.repayDate, interval 1 month) < DATE_FORMAT(NOW(),'%Y-%m-%d') ");
		
		command.append(" and date_sub(DATE_FORMAT(t_borrow.auditTime,'%Y-%m-%d'), interval -deadline MONTH) > DATE_FORMAT(NOW(),'%Y-%m-%d') ");
		
//		command.append(" and t_invest_repayment.borrow_id ="+borrowId);
//		command.append(" and t_invest_repayment.invest_id ="+investId);
		
		System.out.println(command);
	}
	
	
	public static int getUserAgent(){
		int result = -1;
		String userAgent = "User-Agent:Apache-HttpClient/UNAVAILABLE (java 1.4)";
		if(userAgent != null){
			if(userAgent.toLowerCase().indexOf("iphone")!=-1){// iphone
				result = 1;
			}else if(userAgent.indexOf("android")!=-1){// android
				result = 2;
			}
		}
		
		return result;
	}
	
}
