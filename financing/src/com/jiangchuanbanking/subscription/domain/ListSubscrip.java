package com.jiangchuanbanking.subscription.domain;

import java.io.Serializable;
import java.util.Date;


public class ListSubscrip implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String prdt_name;
	
	private String cif_name;
	
	private String id_no;
	
	private String sex;
	
    private String sts;
	
	private String contact_phone;
	
	private Double pact_amt;
	
	private String term_range;
	
    private String rate;
    
    private String pact_no;
	 
	private Date end_date;
	
	private Date start_date;
	
	private String continueflg;
	
	private String continue_amt;
	
	
	public String getPrdt_name() {
		return prdt_name;
	}

	public void setPrdt_name(String prdt_name) {
		this.prdt_name = prdt_name;
	}

	public String getCif_name() {
		return cif_name;
	}

	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public boolean equals(Object object) {  
		  return true;  
	 }  
		  
	 public int hashCode() {  
		  return 1;  
	 }

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public Double getPact_amt() {
		return pact_amt;
	}

	public void setPact_amt(Double pact_amt) {
		this.pact_amt = pact_amt;
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

	public String getPact_no() {
		return pact_no;
	}

	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}


	

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public String getContinueflg() {
		return continueflg;
	}

	public void setContinueflg(String continueflg) {
		this.continueflg = continueflg;
	}

	public String getContinue_amt() {
		return continue_amt;
	}

	public void setContinue_amt(String continue_amt) {
		this.continue_amt = continue_amt;
	}

    
	
	
	 
}
