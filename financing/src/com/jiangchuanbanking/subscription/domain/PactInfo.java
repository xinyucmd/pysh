package com.jiangchuanbanking.subscription.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.jiangchuanbanking.base.domain.BaseEntity;
import com.jiangchuanbanking.investor.domain.Customer;



public class PactInfo extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String pact_no;
	
	private String claims_pact_no;
	
	private Integer sub_no;
	
	private Integer cif_no;
	
	private String cif_name;
	private Customer customer;
	private String old_pactno;
	
	public Integer getCif_no() {
		return cif_no;
	}

	public void setCif_no(Integer cif_no) {
		this.cif_no = cif_no;
	}

	public String getCif_name() {
		return cif_name;
	}

	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}

	private String account_name;
	
	private String account_no;
	
	private String account_bank;
	
	private String if_wxd;
	
	private String prdt_no;
	
	private String prdt_name;
	
	private String term_range;
	
	private String rate;
	;
	private Double pact_amt;
	
	private Double cash_amt;
	
	private Double income_amt;
	
	private String fund_sources;
	
	private String payment_type;
	
	private Date start_date;
	
	private Date end_date;
	
	private String if_continue;
	
	private String sts;
	
	private String pact_type;
	
	private String me_adult;
	
	private String open_date;
	
	private String open_op;
	
	private Integer open_id;
	
	private String cmt;
	
	private String continue_amt;
	
	private String if_already_conti;
		
	private Double continueAmt;//页面传参使用
	private String continueflg;
	
	private String beginDate;
	private String endDate;
	private String due_bal;
	
	private String if_export_ht;
	
	private String if_export_sj;
		
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPact_no() {
		return pact_no;
	}

	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}

	public String getClaims_pact_no() {
		return claims_pact_no;
	}

	public void setClaims_pact_no(String claims_pact_no) {
		this.claims_pact_no = claims_pact_no;
	}

	public Integer getSub_no() {
		return sub_no;
	}

	public void setSub_no(Integer sub_no) {
		this.sub_no = sub_no;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getAccount_bank() {
		return account_bank;
	}

	public void setAccount_bank(String account_bank) {
		this.account_bank = account_bank;
	}

	public String getIf_wxd() {
		return if_wxd;
	}

	public void setIf_wxd(String if_wxd) {
		this.if_wxd = if_wxd;
	}

	public String getPrdt_no() {
		return prdt_no;
	}

	public void setPrdt_no(String prdt_no) {
		this.prdt_no = prdt_no;
	}

	public String getPrdt_name() {
		return prdt_name;
	}

	public void setPrdt_name(String prdt_name) {
		this.prdt_name = prdt_name;
	}

	public String getTerm_range() {
		return term_range;
	}

	public void setTerm_range(String term_range) {
		this.term_range = term_range;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Double getPact_amt() {
		return pact_amt;
	}

	public void setPact_amt(Double pact_amt) {
		this.pact_amt = pact_amt;
	}

	public Double getCash_amt() {
		return cash_amt;
	}

	public void setCash_amt(Double cash_amt) {
		this.cash_amt = cash_amt;
	}

	public Double getIncome_amt() {
		return income_amt;
	}

	public void setIncome_amt(Double income_amt) {
		this.income_amt = income_amt;
	}

	public String getFund_sources() {
		return fund_sources;
	}

	public void setFund_sources(String fund_sources) {
		this.fund_sources = fund_sources;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getIf_continue() {
		return if_continue;
	}

	public void setIf_continue(String if_continue) {
		this.if_continue = if_continue;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getPact_type() {
		return pact_type;
	}

	public void setPact_type(String pact_type) {
		this.pact_type = pact_type;
	}

	public String getMe_adult() {
		return me_adult;
	}

	public void setMe_adult(String me_adult) {
		this.me_adult = me_adult;
	}

	public String getOpen_date() {
		return open_date;
	}

	public void setOpen_date(String open_date) {
		this.open_date = open_date;
	}

	public String getOpen_op() {
		return open_op;
	}

	public void setOpen_op(String open_op) {
		this.open_op = open_op;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getContinueAmt() {
		return continueAmt;
	}

	public void setContinueAmt(Double continueAmt) {
		this.continueAmt = continueAmt;
	}

	public String getContinueflg() {
		return continueflg;
	}

	public void setContinueflg(String continueflg) {
		this.continueflg = continueflg;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDue_bal() {
		return due_bal;
	}

	public void setDue_bal(String due_bal) {
		this.due_bal = due_bal;
	}

	public Integer getOpen_id() {
		return open_id;
	}

	public void setOpen_id(Integer open_id) {
		this.open_id = open_id;
	}

	public String getContinue_amt() {
		return continue_amt;
	}

	public void setContinue_amt(String continue_amt) {
		this.continue_amt = continue_amt;
	}

	public String getIf_already_conti() {
		return if_already_conti;
	}

	public void setIf_already_conti(String if_already_conti) {
		this.if_already_conti = if_already_conti;
	}

	public String getIf_export_ht() {
		return if_export_ht;
	}

	public void setIf_export_ht(String if_export_ht) {
		this.if_export_ht = if_export_ht;
	}

	public String getIf_export_sj() {
		return if_export_sj;
	}

	public void setIf_export_sj(String if_export_sj) {
		this.if_export_sj = if_export_sj;
	}

	public String getOld_pactno() {
		return old_pactno;
	}

	public void setOld_pactno(String old_pactno) {
		this.old_pactno = old_pactno;
	}

	
	

}
