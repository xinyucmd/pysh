/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� TLRCTLObj.java
 * @���� dxt.loan.htgl.bctl.been
 * @˵�� TODO(�����ļ���ʲô)
 * @���� rjq
 * @ʱ�� Nov 2, 2010 9:37:23 AM
 * @�汾 V1.0
 */
package com.dx.login.bean;

/**
 * @���� TLRCTLObj
 * @���� TODO(��;˵��)
 * @���� rjq
 * @���� Nov 2, 2010 9:37:23 AM ========�޸���־=======
 * 
 */
public class TlrctBean {

	// ���캯��
	public TlrctBean() {

	}

	private String BRNO; // ���ű�ţ�
	private String TYPE; // ���֤�ţ�
	private String TLRNO; // ����Ա��
	private String PASSWD; // ����Ա����
	private String NAME; // ����Ա����
	private String ROLENO; // ��ɫ��
	private String ROLENAME; // ��ɫ����
	private String TRMNO; // ����Ա����
	private String TXDATE; // �ս��־ 1--���� ��0--����

	private String EDATE; // �ν�ɫ��
	private String AUTH; // ÿҳ��ʾ������
	private String EJFNO; // ���ղ���Ա������ˮ
	private String WRGNUM; // �������
	private String RIGHTFLAG; // �Ŵ�Ȩ��λ
	private String FASTMENU; //
	private String MODDATE; //
	private String CRKIND; // ��������
	private String INCUMBENCY; // �Ƿ���ְ��1:��ְ��0����ְ��
	private String manageCusType; // '1'����Թ�����2��������ˣ���3������ȫ����
	private String LOCKTIME; // ����Ա������ʱ��
	private String LOCKSTATE; // ����Ա������״̬ �ձ�������0����������������ʾʣ��ɳ����������
	private String datalimits; // ����Ա�ܿ�������Ȩ�� 1 ���� 2 ������ 3 ȫ��
	private String  brnoName; //�������� ;

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
	 * '0'������ְ����1��������ְ
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
