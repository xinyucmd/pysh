/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� FeeTypeBean.java
 * ������ com.dx.loan.fee.bean
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-18 ����07:28:11
 * @version V1.0
 */ 
package com.dx.loan.fee.bean;
/**
 * ������ FeeTypeBean
 * ������ ��������ʵ��
 * @author Ǭ֮��
 * @date 2012-6-18 ����07:28:11
 *
 *
 */
public class FeeTypeBean {
	// ����id
	private String feeId;
	// ��������
	private String feeName;
	// ���ù̶�ֵ.��ֵ��û��ָ�����ü��㹫ʽ��ǰ����ʹ��,���й�ʽ�򰴹�ʽ�������ֵ
	private String feeValue;
	// ���ü��㹫ʽ
	private String feeExpression;
	public String getFeeId() {
		return feeId;
	}
	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public String getFeeValue() {
		return feeValue;
	}
	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}
	public String getFeeExpression() {
		return feeExpression;
	}
	public void setFeeExpression(String feeExpression) {
		this.feeExpression = feeExpression;
	}
	
	
}
