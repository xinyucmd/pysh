package com.dx.collect.bean;

import org.apache.commons.lang.StringUtils;

/**
 * 登录柜员
 * 
 * @author leopard
 * @date 2010-11-01
 * @see 修改记录:
 */
public class TblOrgUser implements java.io.Serializable {

	private Integer userid; //
	private String last_mod_date; // 上次密码修改日期
	private String pwd_inval_date; // 密码失效日期
	private String id_no; // 身份证
	private String op_sts; // 状态
	private String extend; //
	private Integer seq; //
	private String username; //
	private String passwordhash; //
	private String diaplayname; //
	private String nick; //
	private String job; //
	private Integer sex; //
	private String birthday; //
	private String mobile; //
	private String businesstelephone; //
	private String businesstelephone0; //
	private String businesstelephone1; //
	private String businessfax; //
	private String hometelephone; //
	private String email; //
	private String email0; //
	private String email1; //
	private String web; //
	private String jabberid; //
	private String msn; //
	private String icq; //
	private String description; //
	private String br_no;

	// add by Leopard
	private String roleNo;
	private String brNo;
	private String brName;

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getBrNo() {
		return StringUtils.trimToNull(brNo);
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getLast_mod_date() {
		return last_mod_date;
	}

	public void setLast_mod_date(String last_mod_date) {
		this.last_mod_date = last_mod_date;
	}

	public String getPwd_inval_date() {
		return pwd_inval_date;
	}

	public void setPwd_inval_date(String pwd_inval_date) {
		this.pwd_inval_date = pwd_inval_date;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public String getOp_sts() {
		return op_sts;
	}

	public void setOp_sts(String op_sts) {
		this.op_sts = op_sts;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordhash() {
		return passwordhash;
	}

	public void setPasswordhash(String passwordhash) {
		this.passwordhash = passwordhash;
	}

	public String getDiaplayname() {
		return diaplayname;
	}

	public void setDiaplayname(String diaplayname) {
		this.diaplayname = diaplayname;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBusinesstelephone() {
		return businesstelephone;
	}

	public void setBusinesstelephone(String businesstelephone) {
		this.businesstelephone = businesstelephone;
	}

	public String getBusinesstelephone0() {
		return businesstelephone0;
	}

	public void setBusinesstelephone0(String businesstelephone0) {
		this.businesstelephone0 = businesstelephone0;
	}

	public String getBusinesstelephone1() {
		return businesstelephone1;
	}

	public void setBusinesstelephone1(String businesstelephone1) {
		this.businesstelephone1 = businesstelephone1;
	}

	public String getBusinessfax() {
		return businessfax;
	}

	public void setBusinessfax(String businessfax) {
		this.businessfax = businessfax;
	}

	public String getHometelephone() {
		return hometelephone;
	}

	public void setHometelephone(String hometelephone) {
		this.hometelephone = hometelephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail0() {
		return email0;
	}

	public void setEmail0(String email0) {
		this.email0 = email0;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getJabberid() {
		return jabberid;
	}

	public void setJabberid(String jabberid) {
		this.jabberid = jabberid;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getIcq() {
		return icq;
	}

	public void setIcq(String icq) {
		this.icq = icq;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

	public String getBr_no() {
		return br_no;
	}

	public void setBr_no(String br_no) {
		this.br_no = br_no;
	}

}
