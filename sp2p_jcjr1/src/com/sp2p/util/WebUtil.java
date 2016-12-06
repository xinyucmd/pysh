package com.sp2p.util;

import com.sp2p.constants.IConstants;

public class WebUtil {

	public static String getBasePath() {
		return getWebPath();

	}

	/**
	 * @MethodName: getWebPath
	 * @Param: WebUtil
	 * @Author: gang.lv
	 * @Date: 2013-5-12 下午10:57:47
	 * @Return:
	 * @Descb: 获取web路径
	 * @Throws:
	 */
	public static String getWebPath() {
		return IConstants.WEB_URL;
	}
	
	public static void main(String[] args) {
		StringBuilder command = new StringBuilder("");
		command.append("SELECT t_assignment_debt.*,t_borrow.paymentMode as paymentMode,");
		command.append("if((`t_borrow`.`borrowWay` = 4),concat('交易宝-',date_format(`t_assignment_debt`.`publishTime`,'%y%m'),`t_assignment_debt`.`debtIndexLable`),`t_borrow`.`borrowTitle`) ");
		command.append(" as debtBorrowTile from t_assignment_debt LEFT JOIN t_borrow on t_assignment_debt.borrowId = t_borrow.id");
		command.append(" WHERE t_assignment_debt.id =");
		
		System.out.println(command);
	}
	
}
