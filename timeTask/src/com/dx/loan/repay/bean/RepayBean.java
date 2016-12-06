/**
 * Copyright (C) DXHM 锟斤拷权锟斤拷锟斤拷
 * 锟侥硷拷锟斤拷 RepayBean.java
 * 锟斤拷锟斤拷 com.dx.loan.repay.bean
 * 说锟斤拷
 * @author sll
 * @date May 10, 2012 10:00:02 AM
 * @version V1.0
 */ 
package com.dx.loan.repay.bean;

import hirondelle.date4j.DateTime;

import java.io.Serializable;

import com.dx.common.util.StringUtil;
import com.google.gson.Gson;

/**
 * 类名： RepayBean
 * 描述： 正常还款
 * @author sll
 * @date May 10, 2012 10:00:02 AM
 *
 *
 */
public class RepayBean implements Serializable ,Comparable<RepayBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4204985846008567680L;
	//借据号
	private String dueNo;
	//客户名称
	private String cifName; 
	// 客户号
	private String cifNo; 
	//借据金额
	private String dueAmt; 
	//应还本金
	private String returnCapital; 
	//应还利息
	private String returnInterest; 
	//其他费用费(具体根据实际业务而定)
	private String otherFee;
	// 逾期利息
	private String overInterest ; 
	// 复利利息
	private String cmpdInterest;
	// 优惠金额
	private String privilege;
	//还款计划开始日期
	private String begDate; 
	//还款结束日期
	private String endDate;
	// 还款日期
	private String occDate;
	//还款类型(现金 银行装张)
    private String payType;
    //记账标识
    private String accFlag;
    //还款次数
    private String counts;
    //银行代码
    private String bankNo;
    //期数
    private String termNo;
    // 提前还款本金
    private String advaceAmt;
    // 履约保证金额
    private String perfAmount;
    // 用户输入的还款金额(不进行入库操作,主要用于参数传递)
    private String returnAmt;     
    //主键
    private String repayId;
    
    // 提前还款违约金  
    private  String   adviceDedit;
    //  上次结余
    private  String   lastBalance;
	// 本次结余
    private  String   balance;
    
    //本金累计结余
    private String  bjbalance;
    //利息累计结余
    private String  ratecomebalance ;
	
	
    
	public String getLastBalance() {
		return lastBalance;
	}
	public void setLastBalance(String lastBalance) {
		this.lastBalance = lastBalance;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAdviceDedit() {
		return adviceDedit;
	}
	public void setAdviceDedit(String adviceDedit) {
		this.adviceDedit = adviceDedit;
	}
	public String getRepayId() {
		return repayId;
	}
	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}
	public String getReturnAmt() {
		return returnAmt;
	}
	public void setReturnAmt(String returnAmt) {
		this.returnAmt = returnAmt;
	}
	public String getPerfAmount() {
		return StringUtil.KillNull(perfAmount, "0.00");
	}
	public void setPerfAmount(String perfAmount) {
		this.perfAmount = perfAmount;
	}
	public String getAdvaceAmt() {
		return StringUtil.KillNull(advaceAmt, "0.00");
	}
	public void setAdvaceAmt(String advaceAmt) {
		this.advaceAmt = advaceAmt;
	}
	public String getOccDate() {
		return occDate;
	}
	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getAccFlag() {
		return accFlag;
	}
	public void setAccFlag(String accFlag) {
		this.accFlag = accFlag;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	
	public String getBankNo() {
		return StringUtil.KillNull(bankNo, "");
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getPrivilege() {
		return StringUtil.KillNull(privilege, "0.00");
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	
	public String getOverInterest() {
		return overInterest;
	}
	public void setOverInterest(String overInterest) {
		this.overInterest = overInterest;
	}
	public String getCmpdInterest() {
		return cmpdInterest;
	}
	public void setCmpdInterest(String cmpdInterest) {
		this.cmpdInterest = cmpdInterest;
	}
	
	public String getDueNo() {
		return dueNo;
	}
	public void setDueNo(String dueNo) {
		this.dueNo = dueNo;
	}
	public String getCifName() {
		return cifName;
	}
	public void setCifName(String cifName) {
		this.cifName = cifName;
	}
	public String getDueAmt() {
		return dueAmt;
	}
	public void setDueAmt(String dueAmt) {
		this.dueAmt = dueAmt;
	}
	
	public String getReturnInterest() {
		return returnInterest;
	}
	public void setReturnInterest(String returnInterest) {
		this.returnInterest = returnInterest;
	}
	public String getBegDate() {
		return begDate;
	}
	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public RepayBean (){}
	public String getReturnCapital() {
		return returnCapital;
	}
	public void setReturnCapital(String returnCapital) {
		this.returnCapital = returnCapital;
	};
	public String getCifNo() {
		return cifNo;
	}
	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}
	public String getOtherFee() {
		return StringUtil.KillNull(otherFee, "0.00");
	}
	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}
	/**
     * 
     * 方法描述：  还款历史根据还款日期按升序排序
     * @param repayHisBean
     * @return
     * @author 乾之轩
     * @date 2012-5-22 下午03:05:41
     */
	public int compareTo(RepayBean repayBean) {
		DateTime dateTime1 = new DateTime(this.getOccDate());
		DateTime dateTime2 = new DateTime(repayBean.getOccDate());
		if(dateTime1.gt(dateTime2)){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 
	 * 方法描述： 获得实体的json格式 
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-6-15 上午09:16:15
	 */
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	public String getBjbalance() {
		return bjbalance;
	}
	public void setBjbalance(String bjbalance) {
		this.bjbalance = bjbalance;
	}
	public String getRatecomebalance() {
		return ratecomebalance;
	}
	public void setRatecomebalance(String ratecomebalance) {
		this.ratecomebalance = ratecomebalance;
	}
	
	
}
