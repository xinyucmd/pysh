/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PactAdditiBean.java
 * ������ com.dx.loan.putout.bean
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-28 ����05:09:34
 * @version V1.0
 */ 
package com.dx.loan.putout.bean;
/**
 * ������ PactAdditiBean
 * ������ ��ͬ�б�ʵ����
 * @author Ǭ֮��
 * @date 2012-5-28 ����05:09:34
 *
 *
 */
public class PactAdditiBean {
	// ��ͬ��
	private String pactNo;
	// �ͻ���
	private String cifNo;
	// �ͻ�����
	private String cifName;
	// ֤������
	private String cardNo;
	// ��ͬ���
	private String pactAmt;
	// ҵ����������
	private String kindName;
	// ��ǰ״̬
	private String pactSts;
	
	
	public String getPactSts() {
		return pactSts;
	}
	public void setPactSts(String pactSts) {
		this.pactSts = pactSts;
	}
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	public String getCifNo() {
		return cifNo;
	}
	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}
	public String getCifName() {
		return cifName;
	}
	public void setCifName(String cifName) {
		this.cifName = cifName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPactAmt() {
		return pactAmt;
	}
	public void setPactAmt(String pactAmt) {
		this.pactAmt = pactAmt;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
}
