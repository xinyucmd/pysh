package com.dx.collect.bean;

import java.io.Serializable;

public class CollectFeePolicy implements Serializable {

	private static final long serialVersionUID = 5894774616520285302L;
	private String id;
	private String product;
	private String location;
	private String locationName;
	private String overdays;
	private String baseRate;
	private String overRate;
	private String addRate;
	private String startTime;
	private String endTime;
	private String createDate;
	private String updateDate;
	private String delFlg;
	private String defFlg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getOverdays() {
		return overdays;
	}

	public void setOverdays(String overdays) {
		this.overdays = overdays;
	}

	public String getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}

	public String getOverRate() {
		return overRate;
	}

	public void setOverRate(String overRate) {
		this.overRate = overRate;
	}

	public String getAddRate() {
		return addRate;
	}

	public void setAddRate(String addRate) {
		this.addRate = addRate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getDefFlg() {
		return defFlg;
	}

	public void setDefFlg(String defFlg) {
		this.defFlg = defFlg;
	}

}
