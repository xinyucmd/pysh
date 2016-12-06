/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ��� RepayBean.java
 * ���� com.dx.loan.repay.bean
 * ˵��
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
 * ������ RepayBean
 * ������ ��������
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
	//��ݺ�
	private String dueNo;
	//�ͻ�����
	private String cifName; 
	// �ͻ���
	private String cifNo; 
	//��ݽ��
	private String dueAmt; 
	//Ӧ������
	private String returnCapital; 
	//Ӧ����Ϣ
	private String returnInterest; 
	//�������÷�(�������ʵ��ҵ�����)
	private String otherFee;
	// ������Ϣ
	private String overInterest ; 
	// ������Ϣ
	private String cmpdInterest;
	// �Żݽ��
	private String privilege;
	//����ƻ���ʼ����
	private String begDate; 
	//�����������
	private String endDate;
	// ��������
	private String occDate;
	//��������(�ֽ� ����װ��)
    private String payType;
    //���˱�ʶ
    private String accFlag;
    //�������
    private String counts;
    //���д���
    private String bankNo;
    //����
    private String termNo;
    // ��ǰ�����
    private String advaceAmt;
    // ��Լ��֤���
    private String perfAmount;
    // �û�����Ļ�����(������������,��Ҫ���ڲ�������)
    private String returnAmt;     
    //����
    private String repayId;
    
    // ��ǰ����ΥԼ��  
    private  String   adviceDedit;
    //  �ϴν���
    private  String   lastBalance;
	// ���ν���
    private  String   balance;
    
    //�����ۼƽ���
    private String  bjbalance;
    //��Ϣ�ۼƽ���
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
     * ����������  ������ʷ���ݻ������ڰ���������
     * @param repayHisBean
     * @return
     * @author Ǭ֮��
     * @date 2012-5-22 ����03:05:41
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
	 * ���������� ���ʵ���json��ʽ 
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-6-15 ����09:16:15
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
