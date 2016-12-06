package com.jiangchuanbanking.system.domain;

import java.io.Serializable;
import com.jiangchuanbanking.base.domain.BaseEntity;

public class ProductConfiguration extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String productNo;
	private String productName;
	private Integer standardAMT;
	private String advanceRedeem;
	private String prymentType;
	private String stageDays;
	private String returnRate;
	private String redeemDays;
	private String isRedeemBefore;
	private String rates;
	public Integer getStandardAMT() {
		return standardAMT;
	}

	public void setStandardAMT(Integer standardAMT) {
		this.standardAMT = standardAMT;
	}

	public String getAdvanceRedeem() {
		return advanceRedeem;
	}

	public void setAdvanceRedeem(String advanceRedeem) {
		this.advanceRedeem = advanceRedeem;
	}

	public String getPrymentType() {
		return prymentType;
	}

	public void setPrymentType(String prymentType) {
		this.prymentType = prymentType;
	}

	public String getStageDays() {
		return stageDays;
	}

	public void setStageDays(String stageDays) {
		this.stageDays = stageDays;
	}

	public String getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(String returnRate) {
		this.returnRate = returnRate;
	}

	public String getRedeemDays() {
		return redeemDays;
	}

	public void setRedeemDays(String redeemDays) {
		this.redeemDays = redeemDays;
	}

	public String getIsRedeemBefore() {
		return isRedeemBefore;
	}

	public void setIsRedeemBefore(String isRedeemBefore) {
		this.isRedeemBefore = isRedeemBefore;
	}

	public String getRates() {
		return rates;
	}

	public void setRates(String rates) {
		this.rates = rates;
	}

	public ProductConfiguration() {
	};

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String getName() {
		return this.productName;
	}

	@Override
	public ProductConfiguration clone() throws CloneNotSupportedException {
		ProductConfiguration o = null;
		try {
			o = (ProductConfiguration) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

}
