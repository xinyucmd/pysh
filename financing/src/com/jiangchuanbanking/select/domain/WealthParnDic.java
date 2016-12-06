package com.jiangchuanbanking.select.domain;

import java.io.Serializable;


public class WealthParnDic implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String parentId;
    private String keyName;
    private String keyCode;
    private String opCode;
    private String opCnName;
    private String opEnName;
    private String sts;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public String getOpCnName() {
		return opCnName;
	}
	public void setOpCnName(String opCnName) {
		this.opCnName = opCnName;
	}
	public String getOpEnName() {
		return opEnName;
	}
	public void setOpEnName(String opEnName) {
		this.opEnName = opEnName;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
    
}
