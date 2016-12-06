package com.jiangchuanbanking.investor.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.base.domain.BaseEntity;
import com.jiangchuanbanking.financing.domain.BankAccount;

public class Customer extends BaseEntity implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String cif_no;
	private String cif_name;
	private String sex;
	private String birth;
	private String cif_type;
	private String id_type;
	private String id_no;
	private String contact;
	private String contact_tel;
	private String contact_phone;
	private String mail;
	private String other_contact;
	private String addr;
	private String postcode;
	private String open_date;
	private String open_op;
	private Integer open_id; 
	private String cmt;
	private Set<BankAccount> bankAccounts = new HashSet<BankAccount>();
	
	public Set<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(Set<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCif_no() {
		return cif_no;
	}

	public void setCif_no(String cif_no) {
		this.cif_no = cif_no;
	}

	public String getCif_name() {
		return cif_name;
	}

	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getCif_type() {
		return cif_type;
	}

	public void setCif_type(String cif_type) {
		this.cif_type = cif_type;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContact_tel() {
		return contact_tel;
	}

	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getOther_contact() {
		return other_contact;
	}

	public void setOther_contact(String other_contact) {
		this.other_contact = other_contact;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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

	public Integer getOpen_id() {
		return open_id;
	}

	public void setOpen_id(Integer open_id) {
		this.open_id = open_id;
	}

	@Override
	public Customer clone() throws CloneNotSupportedException {
		Customer o = null;
		try {
			o = (Customer) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.cif_name;
	}

}
