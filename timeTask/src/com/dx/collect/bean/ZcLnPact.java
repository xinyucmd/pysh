package com.dx.collect.bean;
      

public class ZcLnPact implements java.io.Serializable {
/**
	 * long 
	 * desc :
	 */
	private static final long serialVersionUID = -6576439627061605717L;
	//��������
	private String pact_no;              //��ͬ��
private String app_no;               //
private String cif_no;               //�ͻ���
private String cif_name;             //�ͻ�����
private String cur_no;               //����
private Double pact_amt;             //��ͬ���
private Double bal;                  //��ͬ���
private String pact_type;            //��ͬ���� 1.��ͨ����ͬ 2.һ��߶��ͬ 3.ѭ���߶��ͬ
private String sub_prdt_no;          //�Ӳ�Ʒ��
private String prdt_no;              //��Ʒ��
private String occur_type;           //��������
private String cap_type;             //�ʽ����� 1.�����ʽ� 2.ר���ʽ� 3.ί���ʽ�
private String time_type;            //��������
private Integer term_mon;             //������
private Integer term_day;             //������
private String sign_date;            //��ͬǩ������
private String chn_date;             //��ͬ�������
private String beg_date;             //��ʼ����
private String end_date;             //��������
private Double base_rate;            //��׼����
private Double ln_rate;              //ִ������
private String float_type;           //���ʵ�����ʽ
private Double float_ratio;          //��������
private Double over_rate;            //��������
private Double fine_rate;            // ��Ϣ����
private Double cmpd_rate;            //��������
private String repay_type;           //���ʽ
private String ic_type;              //��Ϣ��ʽ
private String vou_type;             //������ʽ
private String ln_use;               //������;
private Integer exp_flag;             //չ�ڱ�־
private String fair_flag;            //�Ƿ���
private String fair_pact_no;         //�������
private String pay_type;             //֧����ʽ
private String pact_sts;             //1.δ��Ч 2.Э��ǩ�� 3.���ֳ��� 4.�ѳ��� 5.�ѻ��� 6.��ע��
private String app_brno;             //������������
private String app_op_no;            //����������
private String app_date;             //��������ʱ��
private String mang_brno;            //�ܻ�����
private String mang_no;              //�ͻ�����
private String acc_brno;             //�������
private String br_no;                //�Ǽǵ�λ
private String op_no;                //�Ǽ���
private String tx_date;              //�Ǽ�����
private String up_date;              //��������
private String filler;               //��ע
private String if_chk_end;           //�Ƿ���ֹ�����飨0 ����1���ǣ�
private Double put_out_amt;           //���Ŷ��
private String old_end_date;         
private String auth_id;         //
private String rate_chg_type;//���ʵ�����ʽ
private String repay_day_way;//�����շ�ʽ
private String repay_day;//������
private String repay_term;//��������
private String repay_term_kind;//��������
private String br_lev;
private String acc_no;
private String if_cross;
private String cross_city;
private String cross_section;
private String special_permit;
private String special_reason;

private String chargestr;
private String active;
private String active_name;
private String return_sts;//�µĺ�ͬ�������жϺ�ͬ�Ƿ��˻صı�ʶ���Դ˱�ʶ�Ƿ����ӹ�ͬ�����1.�������̸ս���3.���ͷ���λ�˻�4.���ſ�ȷ�ϸ��˻�9.����޸ģ�1��3��4���޸ģ�
public String getAcc_no() {
	return acc_no;
}
public void setAcc_no(String acc_no) {
	this.acc_no = acc_no;
}
public String getBr_lev() {
	return br_lev;
}
public void setBr_lev(String br_lev) {
	this.br_lev = br_lev;
}
public String getRate_chg_type() {
	return rate_chg_type;
}
public void setRate_chg_type(String rate_chg_type) {
	this.rate_chg_type = rate_chg_type;
}
public String getRepay_day_way() {
	return repay_day_way;
}
public void setRepay_day_way(String repay_day_way) {
	this.repay_day_way = repay_day_way;
}
public String getRepay_day() {
	return repay_day;
}
public void setRepay_day(String repay_day) {
	this.repay_day = repay_day;
}
public String getRepay_term() {
	return repay_term;
}
public void setRepay_term(String repay_term) {
	this.repay_term = repay_term;
}
public String getRepay_term_kind() {
	return repay_term_kind;
}
public void setRepay_term_kind(String repay_term_kind) {
	this.repay_term_kind = repay_term_kind;
}
//set����
public void setApp_no(String app_no){ this.app_no=app_no;} 
public void setCif_no(String cif_no){ this.cif_no=cif_no;} 
public void setCif_name(String cif_name){ this.cif_name=cif_name;} 
public void setPact_no(String pact_no){ this.pact_no=pact_no;} 
public void setCur_no(String cur_no){ this.cur_no=cur_no;} 
public void setPact_amt(Double pact_amt){ this.pact_amt=pact_amt;} 
public void setBal(Double bal){ this.bal=bal;} 
public void setPact_type(String pact_type){ this.pact_type=pact_type;} 
public void setSub_prdt_no(String sub_prdt_no){ this.sub_prdt_no=sub_prdt_no;} 
public void setPrdt_no(String prdt_no){ this.prdt_no=prdt_no;} 
public void setOccur_type(String occur_type){ this.occur_type=occur_type;} 
public void setCap_type(String cap_type){ this.cap_type=cap_type;} 
public void setTime_type(String time_type){ this.time_type=time_type;} 
public void setTerm_mon(Integer term_mon){ this.term_mon=term_mon;} 
public void setTerm_day(Integer term_day){ this.term_day=term_day;} 
public void setSign_date(String sign_date){ this.sign_date=sign_date;} 
public void setChn_date(String chn_date){ this.chn_date=chn_date;} 
public void setBeg_date(String beg_date){ this.beg_date=beg_date;} 
public void setEnd_date(String end_date){ this.end_date=end_date;} 
public void setBase_rate(Double base_rate){ this.base_rate=base_rate;} 
public void setLn_rate(Double ln_rate){ this.ln_rate=ln_rate;} 
public void setFloat_type(String float_type){ this.float_type=float_type;} 
public void setFloat_ratio(Double float_ratio){ this.float_ratio=float_ratio;} 
public void setOver_rate(Double over_rate){ this.over_rate=over_rate;} 
public void setFine_rate(Double fine_rate){ this.fine_rate=fine_rate;} 
public void setCmpd_rate(Double cmpd_rate){ this.cmpd_rate=cmpd_rate;} 
public void setRepay_type(String repay_type){ this.repay_type=repay_type;} 
public void setIc_type(String ic_type){ this.ic_type=ic_type;} 
public void setVou_type(String vou_type){ this.vou_type=vou_type;} 
public void setLn_use(String ln_use){ this.ln_use=ln_use;} 
public void setExp_flag(Integer exp_flag){ this.exp_flag=exp_flag;} 
public void setFair_flag(String fair_flag){ this.fair_flag=fair_flag;} 
public void setFair_pact_no(String fair_pact_no){ this.fair_pact_no=fair_pact_no;} 
public void setPay_type(String pay_type){ this.pay_type=pay_type;} 
public void setPact_sts(String pact_sts){ this.pact_sts=pact_sts;} 
public void setApp_brno(String app_brno){ this.app_brno=app_brno;} 
public void setApp_op_no(String app_op_no){ this.app_op_no=app_op_no;} 
public void setApp_date(String app_date){ this.app_date=app_date;} 
public void setMang_brno(String mang_brno){ this.mang_brno=mang_brno;} 
public void setMang_no(String mang_no){ this.mang_no=mang_no;} 
public void setAcc_brno(String acc_brno){ this.acc_brno=acc_brno;} 
public void setBr_no(String br_no){ this.br_no=br_no;} 
public void setOp_no(String op_no){ this.op_no=op_no;} 
public void setTx_date(String tx_date){ this.tx_date=tx_date;} 
public void setUp_date(String up_date){ this.up_date=up_date;} 
public void setFiller(String filler){ this.filler=filler;} 
public void setIf_chk_end(String if_chk_end){ this.if_chk_end=if_chk_end;} 
public void setOld_end_date(String old_end_date){ this.old_end_date=old_end_date;}
public void setAuth_id(String auth_id) {this.auth_id = auth_id;} 
//get����
public String getApp_no(){return app_no;} 
public String getCif_no(){return cif_no;} 
public String getCif_name(){return cif_name;} 
public String getPact_no(){return pact_no;} 
public String getCur_no(){return cur_no;} 
public Double getPact_amt(){return pact_amt;} 
public Double getBal(){return bal;} 
public String getPact_type(){return pact_type;} 
public String getSub_prdt_no(){return sub_prdt_no;} 
public String getPrdt_no(){return prdt_no;} 
public String getOccur_type(){return occur_type;} 
public String getCap_type(){return cap_type;} 
public String getTime_type(){return time_type;} 
public Integer getTerm_mon(){return term_mon;} 
public Integer getTerm_day(){return term_day;} 
public String getSign_date(){return sign_date;} 
public String getChn_date(){return chn_date;} 
public String getBeg_date(){return beg_date;} 
public String getEnd_date(){return end_date;} 
public Double getBase_rate(){return base_rate;} 
public Double getLn_rate(){return ln_rate;} 
public String getFloat_type(){return float_type;} 
public Double getFloat_ratio(){return float_ratio;} 
public Double getOver_rate(){return over_rate;} 
public Double getFine_rate(){return fine_rate;} 
public Double getCmpd_rate(){return cmpd_rate;} 
public String getRepay_type(){return repay_type;} 
public String getIc_type(){return ic_type;} 
public String getVou_type(){return vou_type;} 
public String getLn_use(){return ln_use;} 
public Integer getExp_flag(){return exp_flag;} 
public String getFair_flag(){return fair_flag;} 
public String getFair_pact_no(){return fair_pact_no;} 
public String getPay_type(){return pay_type;} 
public String getPact_sts(){return pact_sts;} 
public String getApp_brno(){return app_brno;} 
public String getApp_op_no(){return app_op_no;} 
public String getApp_date(){return app_date;} 
public String getMang_brno(){return mang_brno;} 
public String getMang_no(){return mang_no;} 
public String getAcc_brno(){return acc_brno;} 
public String getBr_no(){return br_no;} 
public String getOp_no(){return op_no;} 
public String getTx_date(){return tx_date;} 
public String getUp_date(){return up_date;} 
public String getFiller(){return filler;} 
public String getIf_chk_end(){return if_chk_end;} 
public String getOld_end_date(){return old_end_date;}
public String getAuth_id() {return auth_id;}
public Double getPut_out_amt() {
	return put_out_amt;
}
public void setPut_out_amt(Double put_out_amt) {
	this.put_out_amt = put_out_amt;
}
public String getIf_cross() {
	return if_cross;
}
public void setIf_cross(String if_cross) {
	this.if_cross = if_cross;
}
public String getCross_city() {
	return cross_city;
}
public void setCross_city(String cross_city) {
	this.cross_city = cross_city;
}
public String getCross_section() {
	return cross_section;
}
public void setCross_section(String cross_section) {
	this.cross_section = cross_section;
}
public String getSpecial_permit() {
	return special_permit;
}
public void setSpecial_permit(String special_permit) {
	this.special_permit = special_permit;
}
public String getSpecial_reason() {
	return special_reason;
}
public void setSpecial_reason(String special_reason) {
	this.special_reason = special_reason;
}
public String getChargestr() {
	return chargestr;
}
public void setChargestr(String chargestr) {
	this.chargestr = chargestr;
}
public String getActive() {
	return active;
}
public void setActive(String active) {
	this.active = active;
}
public String getActive_name() {
	return active_name;
}
public void setActive_name(String active_name) {
	this.active_name = active_name;
}
public String getReturn_sts() {
	return return_sts;
}
public void setReturn_sts(String return_sts) {
	this.return_sts = return_sts;
}
}