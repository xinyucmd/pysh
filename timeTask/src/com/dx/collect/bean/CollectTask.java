package com.dx.collect.bean;

public class CollectTask {
	private String id;
	private String pact_no;
	private String br_no;
	private String cif_no;
	private String plan_time;
	private String collect_time;
	private String status;//1有效，2已完成，3超时未做
	private String term_no;
	private String create_time;
	private String update_time;
	private String aft_type;
	private String cif_name;
	private String mang_no;
	
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
	public String getBr_no() {
		return br_no;
	}
	public void setBr_no(String br_no) {
		this.br_no = br_no;
	}
	public String getCif_no() {
		return cif_no;
	}
	public void setCif_no(String cif_no) {
		this.cif_no = cif_no;
	}
	public String getPlan_time() {
		return plan_time;
	}
	public void setPlan_time(String plan_time) {
		this.plan_time = plan_time;
	}
	public String getCollect_time() {
		return collect_time;
	}
	public void setCollect_time(String collect_time) {
		this.collect_time = collect_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getTerm_no() {
		return term_no;
	}
	public void setTerm_no(String term_no) {
		this.term_no = term_no;
	}
	public String getAft_type() {
		return aft_type;
	}
	public void setAft_type(String aft_type) {
		this.aft_type = aft_type;
	}
	public String getMang_no() {
		return mang_no;
	}
	public void setMang_no(String mang_no) {
		this.mang_no = mang_no;
	}
	public String getCif_name() {
		return cif_name;
	}
	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}
}
