/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PactAdditiBean.java
 * 包名： com.dx.loan.putout.bean
 * 说明：
 * @author 乾之轩
 * @date 2012-5-28 下午05:09:34
 * @version V1.0
 */ 
package com.dx.loan.putout.bean;
/**
 * 类名： PactAdditiBean
 * 描述： 合同列表实体类
 * @author 乾之轩
 * @date 2012-5-28 下午05:09:34
 *
 *
 */
public class PactAdditiBean {
	// 合同号
	private String pactNo;
	// 客户号
	private String cifNo;
	// 客户名称
	private String cifName;
	// 证件名称
	private String cardNo;
	// 合同金额
	private String pactAmt;
	// 业务种类名称
	private String kindName;
	// 当前状态
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
