package com.dx.collect.bean;

/**
 * 功能描述：贷后检查主表实体类 表名：AFT_CHK
 * 
 * @author liuxiao mailto:liuxiao.
 * @date 2010-11-17
 * @see
 * @修改日志：
 * 
 */
public class AftChk implements java.io.Serializable {

	public AftChk(){
		
	}
	
	// 变量定义
	private String chkId; // 贷后检查id
	private String pactNo; // 合同号
	private String aftType; // 贷后检查报告类型
	private String cifNo; // 客户号
	private String cifName; // 客户名称
	private String chkDate; // 检查时间
	private String prdtNo; // 贷款产品
	private String lnUse; // 贷款用途
	private String lnUse1; // 贷款用途(申请)
	private String begDate; // 起始日期
	private String endDate; // 到期日期
	private String expEndDate; // 展期到期日期
	private int termMon; // 期限月
	private int termDay; // 期限日
	private String vouType; // 担保类型
	private String ifUse; // 是否按用途使用 0.否 1.是 见dic_if_use
	private String ifCarry; // 是否按约履行 0.否 1.是 见dic_if_carry
	private String attitude; // 配合检查态度 1.配合 2.一般 3.不配合 见dic_attitude
	private String chkDesc; // 检查说明及建议
	private double pactAmt; // 合同金额
	private double bal; // 贷款余额
	private double intst; // 拖欠利息
	private String fivSts; // 五级分类状态
	private String tel; // 联系电话
	private String aftChkType; // 检查方式
	private String cifdesc1; // 借款人情况（公司业务二）
	private String cifdesc2; // 基本情况变动
	private String cifdesc3; // 收入情况变动
	private String cifdesc4; // 违约记录
	private String cifdesc5; // 信用评价
	private String voudesc1; // 保证人（抵、质押物数量）
	private String voudesc2; // 担保人（物）情况变动
	private String voudesc3; // 担保能力变化情况
	private String voudesc4; // 其他变化情况
	private String means; // 采取措施及建议
	private String mangNo; // 检查人
	private String mangName; // 检查人姓名
	private String manager; // 市场部经理（网点主任）
	private String managerName; // 经理姓名
	private String brNo; // 登记单位
	private String opNo; // 操作员
	private String txDate; // 登记日期
	private String upDate; // 更新日期
	private String filler; // 登记单位
	private String ifChkEnd; // 是否终止检查
	private String chkEndDesc; // 终止检查原因
	private String chk_list; // 预警指标明细
	private String aft_risk_lev; // 预警信号级别
	private String chk_result; // 审批状态
	private String flag_;//标志位，在贷后检查任务中的首次检查保存成功后的返回标志位
	
	
	
	private String aft_classify;//贷后检查情况分类
	private String ifinto_collect;
	private String collect_method;
	private String collect_reason;
	private String term_no;
	
	
	private static final long serialVersionUID = 1L;
	public String getChkId() {
		return chkId;
	}
	public void setChkId(String chkId) {
		this.chkId = chkId;
	}
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	public String getAftType() {
		return aftType;
	}
	public void setAftType(String aftType) {
		this.aftType = aftType;
	}
	public String getCifNo() {
		return cifNo;
	}
	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}
	public String getChkDate() {
		return chkDate;
	}
	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}
	public String getPrdtNo() {
		return prdtNo;
	}
	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}
	public String getLnUse() {
		return lnUse;
	}
	public void setLnUse(String lnUse) {
		this.lnUse = lnUse;
	}
	public String getBegDate() {
		return begDate;
	}
	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getExpEndDate() {
		return expEndDate;
	}
	public void setExpEndDate(String expEndDate) {
		this.expEndDate = expEndDate;
	}
	public int getTermMon() {
		return termMon;
	}
	public void setTermMon(int termMon) {
		this.termMon = termMon;
	}
	public int getTermDay() {
		return termDay;
	}
	public void setTermDay(int termDay) {
		this.termDay = termDay;
	}
	public String getVouType() {
		return vouType;
	}
	public void setVouType(String vouType) {
		this.vouType = vouType;
	}
	public String getIfUse() {
		return ifUse;
	}
	public void setIfUse(String ifUse) {
		this.ifUse = ifUse;
	}
	public String getIfCarry() {
		return ifCarry;
	}
	public void setIfCarry(String ifCarry) {
		this.ifCarry = ifCarry;
	}
	public String getAttitude() {
		return attitude;
	}
	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
	public String getChkDesc() {
		return chkDesc;
	}
	public void setChkDesc(String chkDesc) {
		this.chkDesc = chkDesc;
	}
	public double getPactAmt() {
		return pactAmt;
	}
	public void setPactAmt(double pactAmt) {
		this.pactAmt = pactAmt;
	}
	public double getBal() {
		return bal;
	}
	public void setBal(double bal) {
		this.bal = bal;
	}
	public double getIntst() {
		return intst;
	}
	public void setIntst(double intst) {
		this.intst = intst;
	}
	public String getFivSts() {
		return fivSts;
	}
	public void setFivSts(String fivSts) {
		this.fivSts = fivSts;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAftChkType() {
		return aftChkType;
	}
	public void setAftChkType(String aftChkType) {
		this.aftChkType = aftChkType;
	}
	public String getCifdesc1() {
		return cifdesc1;
	}
	public void setCifdesc1(String cifdesc1) {
		this.cifdesc1 = cifdesc1;
	}
	public String getCifdesc2() {
		return cifdesc2;
	}
	public void setCifdesc2(String cifdesc2) {
		this.cifdesc2 = cifdesc2;
	}
	public String getCifdesc3() {
		return cifdesc3;
	}
	public void setCifdesc3(String cifdesc3) {
		this.cifdesc3 = cifdesc3;
	}
	public String getCifdesc4() {
		return cifdesc4;
	}
	public void setCifdesc4(String cifdesc4) {
		this.cifdesc4 = cifdesc4;
	}
	public String getCifdesc5() {
		return cifdesc5;
	}
	public void setCifdesc5(String cifdesc5) {
		this.cifdesc5 = cifdesc5;
	}
	public String getVoudesc1() {
		return voudesc1;
	}
	public void setVoudesc1(String voudesc1) {
		this.voudesc1 = voudesc1;
	}
	public String getVoudesc2() {
		return voudesc2;
	}
	public void setVoudesc2(String voudesc2) {
		this.voudesc2 = voudesc2;
	}
	public String getVoudesc3() {
		return voudesc3;
	}
	public void setVoudesc3(String voudesc3) {
		this.voudesc3 = voudesc3;
	}
	public String getVoudesc4() {
		return voudesc4;
	}
	public void setVoudesc4(String voudesc4) {
		this.voudesc4 = voudesc4;
	}
	public String getMeans() {
		return means;
	}
	public void setMeans(String means) {
		this.means = means;
	}
	public String getMangNo() {
		return mangNo;
	}
	public void setMangNo(String mangNo) {
		this.mangNo = mangNo;
	}
	public String getMangName() {
		return mangName;
	}
	public void setMangName(String mangName) {
		this.mangName = mangName;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getOpNo() {
		return opNo;
	}
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	public String getUpDate() {
		return upDate;
	}
	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}
	public String getFiller() {
		return filler;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}
	public String getIfChkEnd() {
		return ifChkEnd;
	}
	public void setIfChkEnd(String ifChkEnd) {
		this.ifChkEnd = ifChkEnd;
	}
	public String getChkEndDesc() {
		return chkEndDesc;
	}
	public void setChkEndDesc(String chkEndDesc) {
		this.chkEndDesc = chkEndDesc;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setCifName(String cifName) {
		this.cifName = cifName;
	}
	public String getCifName() {
		return cifName;
	}
	public String getChk_list() {
		return chk_list;
	}
	public void setChk_list(String chk_list) {
		this.chk_list = chk_list;
	}
	public String getAft_risk_lev() {
		return aft_risk_lev;
	}
	public void setAft_risk_lev(String aft_risk_lev) {
		this.aft_risk_lev = aft_risk_lev;
	}
	public String getChk_result() {
		return chk_result;
	}
	public void setChk_result(String chk_result) {
		this.chk_result = chk_result;
	}
	public String getFlag_() {
		return flag_;
	}
	public void setFlag_(String flag_) {
		this.flag_ = flag_;
	}
	public String getAft_classify() {
		return aft_classify;
	}
	public void setAft_classify(String aft_classify) {
		this.aft_classify = aft_classify;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLnUse1() {
		return lnUse1;
	}
	public void setLnUse1(String lnUse1) {
		this.lnUse1 = lnUse1;
	}
	public String getIfinto_collect() {
		return ifinto_collect;
	}
	public void setIfinto_collect(String ifinto_collect) {
		this.ifinto_collect = ifinto_collect;
	}
	public String getCollect_method() {
		return collect_method;
	}
	public void setCollect_method(String collect_method) {
		this.collect_method = collect_method;
	}
	public String getCollect_reason() {
		return collect_reason;
	}
	public void setCollect_reason(String collect_reason) {
		this.collect_reason = collect_reason;
	}
	public String getTerm_no() {
		return term_no;
	}
	public void setTerm_no(String term_no) {
		this.term_no = term_no;
	}

}