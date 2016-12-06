package com.jiangchuanbanking.contract.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.jiangchuanbanking.financing.domain.BankAccount;

public class TemplatePackage implements Serializable {

	private static final long serialVersionUID = 675685658637755403L;

	private Integer id;
	private String prdtNo;
//	private String location;
//	private String locationName;
	private String name;
	private String path;
	private String status;
	private String defaultFlg;
	private String createDate;
	private String updateDate;
	private Set<Template> templates = new HashSet<Template>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPrdtNo() {
		return prdtNo;
	}
	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDefaultFlg() {
		return defaultFlg;
	}
	public void setDefaultFlg(String defaultFlg) {
		this.defaultFlg = defaultFlg;
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
	public Set<Template> getTemplates() {
		return templates;
	}
	public void setTemplates(Set<Template> templates) {
		this.templates = templates;
	}



}
