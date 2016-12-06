/**
 * Copyright (C) DXHM 版权所有
 * 文件名： FeeTypeBean.java
 * 包名： com.dx.loan.fee.bean
 * 说明：
 * @author 乾之轩
 * @date 2012-6-18 下午07:28:11
 * @version V1.0
 */ 
package com.dx.loan.fee.bean;
/**
 * 类名： FeeTypeBean
 * 描述： 费用类型实体
 * @author 乾之轩
 * @date 2012-6-18 下午07:28:11
 *
 *
 */
public class FeeTypeBean {
	// 费用id
	private String feeId;
	// 费用名称
	private String feeName;
	// 费用固定值.该值在没有指定费用计算公式的前提下使用,若有公式则按公式计算费用值
	private String feeValue;
	// 费用计算公式
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
