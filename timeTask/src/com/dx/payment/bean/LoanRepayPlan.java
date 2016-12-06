package com.dx.payment.bean;

import java.io.Serializable;

import com.dx.loan.plan.bean.PlanBean;

public class LoanRepayPlan implements Serializable, Comparable<LoanRepayPlan>{
	
	private String account_no;
	private String cif_no;
	private String pact_no;
	private String due_no;
	private String term;
	private String return_capital;
	private String capital;
	private String return_interest;
	private String interest;
	private String other;
	private String return_other;
	private String beg_date;
	private String end_date;
	private String if_settle;
	private String settle_date;
	private String sms_send;
	private String cmd;
	private String create_date;
	private String update_date;
	private String perf_amount;
	private String return_perf;
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public String getCif_no() {
		return cif_no;
	}
	public void setCif_no(String cif_no) {
		this.cif_no = cif_no;
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
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getReturn_capital() {
		return return_capital;
	}
	public void setReturn_capital(String return_capital) {
		this.return_capital = return_capital;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public String getReturn_interest() {
		return return_interest;
	}
	public void setReturn_interest(String return_interest) {
		this.return_interest = return_interest;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getReturn_other() {
		return return_other;
	}
	public void setReturn_other(String return_other) {
		this.return_other = return_other;
	}
	public String getBeg_date() {
		return beg_date;
	}
	public void setBeg_date(String beg_date) {
		this.beg_date = beg_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getIf_settle() {
		return if_settle;
	}
	public void setIf_settle(String if_settle) {
		this.if_settle = if_settle;
	}
	public String getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}
	public String getSms_send() {
		return sms_send;
	}
	public void setSms_send(String sms_send) {
		this.sms_send = sms_send;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public int compareTo(LoanRepayPlan plan) {
		int temoNo1 = Integer.parseInt(this.getTerm());
		int temoNo2 = Integer.parseInt(plan.getTerm());
		if (temoNo1 > temoNo2) {
			return 1;
		} else {
			return 0;
		}
	}
	public String getPerf_amount() {
		return perf_amount;
	}
	public void setPerf_amount(String perf_amount) {
		this.perf_amount = perf_amount;
	}
	public String getReturn_perf() {
		return return_perf;
	}
	public void setReturn_perf(String return_perf) {
		this.return_perf = return_perf;
	}
}
