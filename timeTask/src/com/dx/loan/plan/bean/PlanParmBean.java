/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PlanParmBean.java
 * ������ com.dx.loan.putout.bean
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-24 ����11:04:45
 * @version V1.0
 */ 
package com.dx.loan.plan.bean;

import com.dx.common.util.StringUtil;

/**
 * ������ PlanParmBean
 * ������ ���ɻ���ƻ�ʱ�Ĳ���ʵ��
 * @author Ǭ֮��
 * @date 2012-5-24 ����11:04:45
 *
 */
public class PlanParmBean {
	// ���ɻ���ƻ��Ŀ�ʼ����
	private  String beginDate;
	// �Ƿ���ǿ�Ƽƻ� 0 ���� 1 ��
	private String isForce;
	// �Ƿ�Ԥ��֧����Ϣ  0 ��Ԥ��֧�� 1 Ԥ��֧�� 3 ����һ��
	private String  isAdvance;
	// �Ƿ��Զ����� 0 ���Զ����� 1 �Զ�����
	private String isDelay;
	// ���ʽ
	private String returnMentod;
	// ����
	private String terms;
	// �Ƿ�̶������� 0 ���� 1��
	private String isFixDate;
	// �̶���������
	private String fixDate;
	// ��ǰ�����
	private String advaceAmt;
	// ����һ�ڻ���ƻ������˵���
	private String termNo;
	// ����ƻ�������ʽ 0 ��������  1 ������������
	private  String adjustType;
	// ����ƻ���ʼ����
	private String beginTerm;
	
	
	
	public String getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	public String getAdvaceAmt() {
		return StringUtil.KillNull(advaceAmt, "0.00");
	}

	public void setAdvaceAmt(String advaceAmt) {
		this.advaceAmt = advaceAmt;
	}

	public String getIsFixDate() {
		return isFixDate;
	}

	public void setIsFixDate(String isFixDate) {
		this.isFixDate = isFixDate;
	}

	public String getFixDate() {
		return fixDate;
	}

	public void setFixDate(String fixDate) {
		this.fixDate = fixDate;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getReturnMentod() {
		return returnMentod;
	}

	public void setReturnMentod(String returnMentod) {
		this.returnMentod = returnMentod;
	}

	public PlanParmBean(){};
	
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getIsForce() {
		return isForce;
	}
	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}
	public String getIsAdvance() {
		return isAdvance;
	}
	public void setIsAdvance(String isAdvance) {
		this.isAdvance = isAdvance;
	}
	public String getIsDelay() {
		return isDelay;
	}
	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getBeginTerm() {
		return beginTerm;
	}

	public void setBeginTerm(String beginTerm) {
		this.beginTerm = beginTerm;
	}

	

}
