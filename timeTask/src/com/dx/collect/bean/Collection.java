package com.dx.collect.bean;

import java.io.Serializable;

public class Collection implements Serializable {

	private static final long serialVersionUID = -4328685426946188672L;

	private String id;
	private String pact_no;
	private String due_no;
	private String cif_no;
	private String collector;
	private String share_no;
	private String share_name;
	private String stage;
	private String over_flg;
	private String prolonged;
	private String start_time;
	private String policy;
	private String cif_name;
	private String op_name;
	private String br;
	private String createDate;
	private String updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPact_no() {
		return pact_no;
	}

	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}

	public String getDue_no() {
		return due_no;
	}

	public void setDue_no(String due_no) {
		this.due_no = due_no;
	}

	public String getCif_no() {
		return cif_no;
	}

	public void setCif_no(String cif_no) {
		this.cif_no = cif_no;
	}

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	public String getShare_no() {
		return share_no;
	}

	public void setShare_no(String share_no) {
		this.share_no = share_no;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getOver_flg() {
		return over_flg;
	}

	public void setOver_flg(String over_flg) {
		this.over_flg = over_flg;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getProlonged() {
		return prolonged;
	}

	public void setProlonged(String prolonged) {
		this.prolonged = prolonged;
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

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getCif_name() {
		return cif_name;
	}

	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}

	public String getOp_name() {
		return op_name;
	}

	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

	public String getBr() {
		return br;
	}

	public void setBr(String br) {
		this.br = br;
	}

	public String getShare_name() {
		return share_name;
	}

	public void setShare_name(String share_name) {
		this.share_name = share_name;
	}

}
