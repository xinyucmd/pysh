package com.dx.collect.bean;

/**
 * ��������������������ʵ���� ������AFT_CHK
 * 
 * @author liuxiao mailto:liuxiao.
 * @date 2010-11-17
 * @see
 * @�޸���־��
 * 
 */
public class AftChk implements java.io.Serializable {

	public AftChk(){
		
	}
	
	// ��������
	private String chkId; // ������id
	private String pactNo; // ��ͬ��
	private String aftType; // �����鱨������
	private String cifNo; // �ͻ���
	private String cifName; // �ͻ�����
	private String chkDate; // ���ʱ��
	private String prdtNo; // �����Ʒ
	private String lnUse; // ������;
	private String lnUse1; // ������;(����)
	private String begDate; // ��ʼ����
	private String endDate; // ��������
	private String expEndDate; // չ�ڵ�������
	private int termMon; // ������
	private int termDay; // ������
	private String vouType; // ��������
	private String ifUse; // �Ƿ���;ʹ�� 0.�� 1.�� ��dic_if_use
	private String ifCarry; // �Ƿ�Լ���� 0.�� 1.�� ��dic_if_carry
	private String attitude; // ��ϼ��̬�� 1.��� 2.һ�� 3.����� ��dic_attitude
	private String chkDesc; // ���˵��������
	private double pactAmt; // ��ͬ���
	private double bal; // �������
	private double intst; // ��Ƿ��Ϣ
	private String fivSts; // �弶����״̬
	private String tel; // ��ϵ�绰
	private String aftChkType; // ��鷽ʽ
	private String cifdesc1; // ������������˾ҵ�����
	private String cifdesc2; // ��������䶯
	private String cifdesc3; // ��������䶯
	private String cifdesc4; // ΥԼ��¼
	private String cifdesc5; // ��������
	private String voudesc1; // ��֤�ˣ��֡���Ѻ��������
	private String voudesc2; // �����ˣ������䶯
	private String voudesc3; // ���������仯���
	private String voudesc4; // �����仯���
	private String means; // ��ȡ��ʩ������
	private String mangNo; // �����
	private String mangName; // ���������
	private String manager; // �г��������������Σ�
	private String managerName; // ��������
	private String brNo; // �Ǽǵ�λ
	private String opNo; // ����Ա
	private String txDate; // �Ǽ�����
	private String upDate; // ��������
	private String filler; // �Ǽǵ�λ
	private String ifChkEnd; // �Ƿ���ֹ���
	private String chkEndDesc; // ��ֹ���ԭ��
	private String chk_list; // Ԥ��ָ����ϸ
	private String aft_risk_lev; // Ԥ���źż���
	private String chk_result; // ����״̬
	private String flag_;//��־λ���ڴ����������е��״μ�鱣��ɹ���ķ��ر�־λ
	
	
	
	private String aft_classify;//�������������
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