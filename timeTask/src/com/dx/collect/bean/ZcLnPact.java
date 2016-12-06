package com.dx.collect.bean;
      

public class ZcLnPact implements java.io.Serializable {
/**
	 * long 
	 * desc :
	 */
	private static final long serialVersionUID = -6576439627061605717L;
	//变量定义
	private String pact_no;              //合同号
private String app_no;               //
private String cif_no;               //客户号
private String cif_name;             //客户名称
private String cur_no;               //币种
private Double pact_amt;             //合同金额
private Double bal;                  //合同余额
private String pact_type;            //合同类型 1.普通借款合同 2.一般高额合同 3.循环高额合同
private String sub_prdt_no;          //子产品号
private String prdt_no;              //产品号
private String occur_type;           //发生类型
private String cap_type;             //资金性质 1.自有资金 2.专项资金 3.委托资金
private String time_type;            //期限类型
private Integer term_mon;             //期限月
private Integer term_day;             //期限日
private String sign_date;            //合同签订日期
private String chn_date;             //合同变更日期
private String beg_date;             //起始日期
private String end_date;             //到期日期
private Double base_rate;            //基准利率
private Double ln_rate;              //执行利率
private String float_type;           //利率调整方式
private Double float_ratio;          //浮动比率
private Double over_rate;            //逾期利率
private Double fine_rate;            // 罚息利率
private Double cmpd_rate;            //复利利率
private String repay_type;           //还款方式
private String ic_type;              //计息方式
private String vou_type;             //担保方式
private String ln_use;               //贷款用途
private Integer exp_flag;             //展期标志
private String fair_flag;            //是否公正
private String fair_pact_no;         //公正编号
private String pay_type;             //支付方式
private String pact_sts;             //1.未生效 2.协议签订 3.部分出账 4.已出账 5.已还清 6.已注销
private String app_brno;             //最终审批机构
private String app_op_no;            //最终审批人
private String app_date;             //最终审批时间
private String mang_brno;            //管户机构
private String mang_no;              //客户经理
private String acc_brno;             //核算机构
private String br_no;                //登记单位
private String op_no;                //登记人
private String tx_date;              //登记日期
private String up_date;              //更新日期
private String filler;               //备注
private String if_chk_end;           //是否终止贷后检查（0 ：否，1：是）
private Double put_out_amt;           //发放额度
private String old_end_date;         
private String auth_id;         //
private String rate_chg_type;//利率调整方式
private String repay_day_way;//还款日方式
private String repay_day;//还款日
private String repay_term;//还款周期
private String repay_term_kind;//还款类型
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
private String return_sts;//新的合同流程中判断合同是否退回的标识，以此标识是否增加共同借款人1.审批流程刚结束3.被客服岗位退回4.被放款确认刚退回9.完成修改（1，3，4可修改）
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
//set方法
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
//get方法
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