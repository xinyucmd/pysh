/**
 * Copyright (C) DXHM 版权所有
 * 文件名： FormatUtil.java
 * 包名： com.dx.common.util
 * 说明：
 * @author 乾之轩
 * @date 2012-5-30 上午11:20:43
 * @version V1.0
 */ 
package com.dx.common.util;
/**
 * 类名： FormatUtil
 * 描述：
 * @author 乾之轩
 * @date 2012-5-30 上午11:20:43
 *
 *
 */
public class FormatUtil {
	
	public void getString(String string){
		
		System.out.println(string.replace(",", ",\n"));
		
	}
	
	public  void  format(String s){
		//s="1213,666,null,444";
		String[] s1 = s.split(",");
		StringBuffer sb = new StringBuffer();
		for(String s2: s1){
			if(s2!=null && !StringUtil.equals("null", s2)){
				sb.append("'");
				sb.append(s2.trim());
				sb.append("'");
				sb.append(",\n");
			}else{
				sb.append(s2.trim());
				sb.append(",\n");
			}
		}
		String s3 = sb.toString();
		s3=s3.trim();
		System.out.println(s3.substring(0, s3.length()-1));
		
		
	} 
	
	public static void main(String[] args) {
		FormatUtil FormatUtil = new FormatUtil();
		String string=" ACC_TOL_FEE, INNER_INTST, OUTER_INTST, FIVE_CLASS, FOUR_CLASS, PACT_AMT, PACT_BEG_DATE,"+
			" PACT_END_DATE, TERM_Year, TERM_MON, TERM_DAY, LN_RATE, Rate_Float, FINE_RATE, EXP_RATE,"+
			" CMPD_Rate_Float, OVER_RATE_FLOAT, RATE_WAY, RETURN_METHOD, PACT_KIND, OCCUR_TYPE,"+
			" FIXRATE_INTEREST, ISPRE_FIXRATE, CONSULT_AMT, MAIN_GUARAN, GUARAN_TYPE, PACT_NATURE, PACT_USE,"+
			" PACT_USE_DESC, APPLYER_BANK_NAME, APPLYER_BANK_NO, RECEIVER_BANK_NAME, RECEIVER_BANK_NO,"+
			" TEL_NO, PACT_INFO_OP_TYPE, ID_TYPE, AUTH_ID, IF_YT, VOU_TYPE, PACT_STS, SIGN_DATE,"+
			" LN_TRADE_NO, PACT_BAL, LOAN_KIND, OCC_DATE, LN_TYPE, CUR_NO, LOAN_TYPE, RATE_NO, ISFORCE)"+
			" values (#id:VARCHAR#, #dueNo:VARCHAR#, #pactNo:VARCHAR#, #dueAmt:VARCHAR#, #dueBegDate:VARCHAR#,"+
			" #dueEndDate:VARCHAR#, #cifNo:VARCHAR#, #extDays:VARCHAR#, #consFee:VARCHAR#,"+
			" #consFeeType:VARCHAR#, #dueState:VARCHAR#, #expFlag:VARCHAR#, #dueInfoOpType:VARCHAR#,"+
			" #dueBal:VARCHAR#, #accFee:VARCHAR#, #accTolFee:VARCHAR#, #innerIntst:VARCHAR#,"+
			" #outerIntst:VARCHAR#, #fiveClass:VARCHAR#, #fourClass:VARCHAR#, #pactAmt:VARCHAR#,"+
			" #pactBegDate:VARCHAR#, #pactEndDate:VARCHAR#, #termYear:VARCHAR#, #termMon:VARCHAR#,"+
			" #termDay:VARCHAR#, #lnRate:VARCHAR#, #rateFloat:VARCHAR#, #fineRate:VARCHAR#,"+
			" #expRate:VARCHAR#, #cmpdRateFloat:VARCHAR#, #overRateFloat:VARCHAR#, #rateWay:VARCHAR#,"+
			" #returnMethod:VARCHAR#, #pactKind:VARCHAR#, #occurType:VARCHAR#, #fixrateInterest:VARCHAR#,"+
			" #ispreFixrate:VARCHAR#, #consultAmt:VARCHAR#, #mainGuaran:VARCHAR#, #guaranType:VARCHAR#,"+
			" #pactNature:VARCHAR#, #pactUse:VARCHAR#, #pactUseDesc:VARCHAR#, #applyerBankName:VARCHAR#,"+
			" #applyerBankNo:VARCHAR#, #receiverBankName:VARCHAR#, #receiverBankNo:VARCHAR#,"+
			" #telNo:VARCHAR#, #pactInfoOpType:VARCHAR#, #idType:VARCHAR#, #authId:VARCHAR#, #ifYt:VARCHAR#,"+
			" #vouType:VARCHAR#, #pactSts:VARCHAR#, #signDate:VARCHAR#, #lnTradeNo:VARCHAR#,"+
			" #pactBal:VARCHAR#, #loanKind:VARCHAR#, #occDate:VARCHAR#, #lnType:VARCHAR#, #curNo:VARCHAR#,"+
			" #loanType:VARCHAR#, #rateNo:VARCHAR#, #isforce:VARCHAR#)";
		string= string.replace(":VARCHAR", "");
		// FormatUtil.getString(string);
		String string2= "222222, 10000, 333333, 1, 24751.26, 667.00, 0.00, 0.00, 2012-06-28, 2012-05-28, 2012-06-15, 0, 11, 1, 1, null, 0.00, null";
		FormatUtil.format(string2);
	}
	
	
	
	

}
