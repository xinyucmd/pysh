/**
 * Copyright (C) DXHM 版权所有
 * 文件名： FeeOptionBean.java
 * 包名： com.dx.loan.fee.bean
 * 说明：
 * @author 乾之轩
 * @date 2012-6-18 下午07:32:45
 * @version V1.0
 */ 
package com.dx.loan.fee.bean;
/**
 * 类名： FeeOptionBean
 * 描述： 费用计算项
 * @author 乾之轩
 * @date 2012-6-18 下午07:32:45
 *
 *
 */
public class FeeOptionBean {
	// 费用计算项id
	private String feeOptionId;
//	费用计算项名称
	private String feeOptionName;
    public String getFeeOptionId() {
		return feeOptionId;
	}
	public void setFeeOptionId(String feeOptionId) {
		this.feeOptionId = feeOptionId;
	}
	public String getFeeOptionName() {
		return feeOptionName;
	}
	public void setFeeOptionName(String feeOptionName) {
		this.feeOptionName = feeOptionName;
	}
	
}
