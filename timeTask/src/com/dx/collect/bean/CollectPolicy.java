package com.dx.collect.bean;

import java.io.Serializable;

public class CollectPolicy implements Serializable {

	private static final long serialVersionUID = -3006944720504312450L;

	private String id;
	private String prdt_no;
	private String overdays;
	private String teldays;
	private String prolong_days;
	private String perdays;
	private String def_flg;
	private String del_flg;
	private String createDate;
	private String updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrdt_no() {
		return prdt_no;
	}

	public void setPrdt_no(String prdt_no) {
		this.prdt_no = prdt_no;
	}

	public String getOverdays() {
		return overdays;
	}

	public void setOverdays(String overdays) {
		this.overdays = overdays;
	}

	public String getTeldays() {
		return teldays;
	}

	public void setTeldays(String teldays) {
		this.teldays = teldays;
	}

	public String getProlong_days() {
		return prolong_days;
	}

	public void setProlong_days(String prolong_days) {
		this.prolong_days = prolong_days;
	}

	public String getPerdays() {
		return perdays;
	}

	public void setPerdays(String perdays) {
		this.perdays = perdays;
	}

	public String getDef_flg() {
		return def_flg;
	}

	public void setDef_flg(String def_flg) {
		this.def_flg = def_flg;
	}

	public String getDel_flg() {
		return del_flg;
	}

	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
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

}
