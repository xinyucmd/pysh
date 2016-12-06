/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� RateAdjustLog.java
 * ������ com.dx.loan.normrate.bean
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-18 ����03:43:41
 * @version V1.0
 */ 
package com.dx.loan.normrate.bean;

import hirondelle.date4j.DateTime;

import java.io.Serializable;

/**
 * ������ RateAdjustLog
 * ������
 * @author Ǭ֮��
 * @date 2012-5-18 ����03:43:41
 *
 *
 */
public class RateAdjustBean  implements Serializable ,Comparable<RateAdjustBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 340032339420884347L;
	// ���ʱ��
	private String rateNo;   
	// ��������
	private String rateType;                                 
	// ����ֵ
	private String rateValue;      
	// ���ʿ�ʼ����
	private String begDate;
	// ���ʽ�������
	private String endDate;
	// ���ʵ�������
	private String adjustDate;
	// ��С����
	private String minTerm;
	// �������
	private String maxTerm;
	// ������Сֵ
	private String minValue;
	// �������ֵ
	private String maxValue;
	// ���ʵ���������
	private String id;  
	
	public String getRateNo() {
		return rateNo;
	}
	public void setRateNo(String rateNo) {
		this.rateNo = rateNo;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getRateValue() {
		return rateValue;
	}
	public void setRateValue(String rateValue) {
		this.rateValue = rateValue;
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
	public String getAdjustDate() {
		return adjustDate;
	}
	public void setAdjustDate(String adjustDate) {
		this.adjustDate = adjustDate;
	}
	public String getMinTerm() {
		return minTerm;
	}
	public void setMinTerm(String minTerm) {
		this.minTerm = minTerm;
	}
	public String getMaxTerm() {
		return maxTerm;
	}
	public void setMaxTerm(String maxTerm) {
		this.maxTerm = maxTerm;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int compareTo(RateAdjustBean rateAdjustBean) {
		DateTime dateTime1 = new DateTime(this.getAdjustDate());
		DateTime dateTime2 = new DateTime(rateAdjustBean.getAdjustDate());
		if(dateTime1.gt(dateTime2)){
			return 1;
		}else{
			return 0;
		}
	}
	                   
	

}
