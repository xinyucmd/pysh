/**
 * Copyright (C) DXHM 版权所有
 * @文件名 TLRCTLObj.java
 * @包名 dxt.loan.htgl.bctl.been
 * @说明 TODO(描述文件做什么)
 * @作者 rjq
 * @时间 Nov 2, 2010 9:37:23 AM
 * @版本 V1.0
 */
package com.dx.login.bean;

/**
 * @类名 TLRCTLObj
 * @描述 TODO(用途说明)
 * @作者 rjq
 * @日期 Nov 2, 2010 9:37:23 AM ========修改日志=======
 * 
 */
public class TlrctBean {

	// 构造函数
	public TlrctBean() {

	}

	private String BRNO; // 部门编号；
	private String TYPE; // 身份证号；
	private String TLRNO; // 操作员号
	private String PASSWD; // 操作员密码
	private String NAME; // 操作员姓名
	private String ROLENO; // 角色号
	private String ROLENAME; // 角色名称
	private String TRMNO; // 操作员级别
	private String TXDATE; // 终结标志 1--启用 ；0--冻结

	private String EDATE; // 次角色；
	private String AUTH; // 每页显示的行数
	private String EJFNO; // 当日操作员交易流水
	private String WRGNUM; // 错误次数
	private String RIGHTFLAG; // 信贷权限位
	private String FASTMENU; //
	private String MODDATE; //
	private String CRKIND; // 贷款种类
	private String INCUMBENCY; // 是否在职：1:在职；0：离职；
	private String manageCusType; // '1'代表对公，‘2’代表个人，‘3’代表全部；
	private String LOCKTIME; // 操作员号锁定时间
	private String LOCKSTATE; // 操作员号锁定状态 空表正常，0代表锁定，其他表示剩余可尝试输入次数
	private String datalimits; // 操作员能看到数据权限 1 本人 2 本部门 3 全部
	private String  brnoName; //部门名称 ;

	public String getBRNO() {
		return BRNO;
	}

	public void setBRNO(String BRNO) {
		this.BRNO = BRNO;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String TYPE) {
		this.TYPE = TYPE;
	}

	public String getTLRNO() {
		return TLRNO;
	}

	public void setTLRNO(String TLRNO) {
		this.TLRNO = TLRNO;
	}

	public String getPASSWD() {
		return PASSWD;
	}

	public void setPASSWD(String PASSWD) {
		this.PASSWD = PASSWD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getROLENO() {
		return ROLENO;
	}

	public void setROLENO(String ROLENO) {
		this.ROLENO = ROLENO;
	}

	public String getROLENAME() {
		return ROLENAME;
	}

	public void setROLENAME(String ROLENAME) {
		this.ROLENAME = ROLENAME;
	}

	public String getTRMNO() {
		return TRMNO;
	}

	public void setTRMNO(String TRMNO) {
		this.TRMNO = TRMNO;
	}

	public String getTXDATE() {
		return TXDATE;
	}

	public void setTXDATE(String TXDATE) {
		this.TXDATE = TXDATE;
	}

	public String getEDATE() {
		return EDATE;
	}

	public void setEDATE(String EDATE) {
		this.EDATE = EDATE;
	}

	public String getAUTH() {
		return AUTH;
	}

	public void setAUTH(String AUTH) {
		this.AUTH = AUTH;
	}

	public String getEJFNO() {
		return EJFNO;
	}

	public void setEJFNO(String EJFNO) {
		this.EJFNO = EJFNO;
	}

	public String getWRGNUM() {
		return WRGNUM;
	}

	public void setWRGNUM(String WRGNUM) {
		this.WRGNUM = WRGNUM;
	}

	public String getRIGHTFLAG() {
		return RIGHTFLAG;
	}

	public void setRIGHTFLAG(String RIGHTFLAG) {
		this.RIGHTFLAG = RIGHTFLAG;
	}

	public String getFASTMENU() {
		return FASTMENU;
	}

	public void setFASTMENU(String FASTMENU) {
		this.FASTMENU = FASTMENU;
	}

	public String getMODDATE() {
		return MODDATE;
	}

	public void setMODDATE(String MODDATE) {
		this.MODDATE = MODDATE;
	}

	public String getCRKIND() {
		return CRKIND;
	}

	public void setCRKIND(String CRKIND) {
		this.CRKIND = CRKIND;
	}

	/**
	 * '0'代表离职，‘1’代表在职
	 */
	public String getINCUMBENCY() {
		return INCUMBENCY;
	}

	public void setINCUMBENCY(String INCUMBENCY) {
		this.INCUMBENCY = INCUMBENCY;
	}

	public String getManageCusType() {
		return manageCusType;
	}

	public void setManageCusType(String manageCusType) {
		this.manageCusType = manageCusType;
	}

	public String getLOCKTIME() {
		if (LOCKTIME == null) {
			LOCKTIME = "";
		}
		return LOCKTIME;
	}

	public void setLOCKTIME(String locktime) {
		LOCKTIME = locktime;
	}

	public String getLOCKSTATE() {
		if (LOCKSTATE == null) {
			LOCKSTATE = "";
		}
		return LOCKSTATE;
	}

	public void setLOCKSTATE(String lockstate) {
		LOCKSTATE = lockstate;
	}

	public String getDatalimits() {
		return datalimits;
	}

	public void setDatalimits(String datalimits) {
		this.datalimits = datalimits;
	}

	public String getBrnoName() {
		return brnoName;
	}

	public void setBrnoName(String brnoName) {
		this.brnoName = brnoName;
	}

}
