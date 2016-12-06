/**
 * Copyright (C) DXHM 版权所有
 * 文件名： ConsBean.java
 * 包名： com.dx.loan.consumer.bean
 * 说明：
 * @author 乾之轩
 * @date 2012-5-28 下午04:49:44
 * @version V1.0
 */ 
package com.dx.loan.consumer.bean;
/**
 * 类名： ConsBean
 * 描述： 客户实体类
 * @author 乾之轩
 * @date 2012-5-28 下午04:49:44
 *
 *
 */
public class ConsBean {
	// 客户号
	private String cifNo;
	// 客户名称
	private String cifName;
	// 证件类型
	private String cardType;
	// 证件类型
	private String cardNo;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
}
