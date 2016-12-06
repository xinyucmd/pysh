package com.dx.payment.bean;

public class AccountRepayRel {

	private String id;
	private String sub_no;
	private String seq_no;
	private String pact_no;
	private String due_no;
	private String occure_date;
	private String account_no;
	private String bz;
	private String realityDate;
	// 用于参数传递
	private String occDate2;
	private String returnAmt;
	private String overPay;
	private String csf;
	private String overInterest;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSub_no() {
		return sub_no;
	}

	public void setSub_no(String sub_no) {
		this.sub_no = sub_no;
	}

	public String getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
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

	public String getOccure_date() {
		return occure_date;
	}

	public void setOccure_date(String occure_date) {
		this.occure_date = occure_date;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getOccDate2() {
		return occDate2;
	}

	public void setOccDate2(String occDate2) {
		this.occDate2 = occDate2;
	}

	public String getReturnAmt() {
		return returnAmt;
	}

	public void setReturnAmt(String returnAmt) {
		this.returnAmt = returnAmt;
	}

	public String getOverPay() {
		return overPay;
	}

	public void setOverPay(String overPay) {
		this.overPay = overPay;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getRealityDate() {
		return realityDate;
	}

	public void setRealityDate(String realityDate) {
		this.realityDate = realityDate;
	}

	public String getCsf() {
		return csf;
	}

	public void setCsf(String csf) {
		this.csf = csf;
	}

	public String getOverInterest() {
		return overInterest;
	}

	public void setOverInterest(String overInterest) {
		this.overInterest = overInterest;
	}

}
