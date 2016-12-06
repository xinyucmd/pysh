package com.dx.loan.repay.bean;

public class LnDue implements java.io.Serializable {
	// 变量定义
	private String if_auto;

	private String ac_no;
	private String cap_type;// 贷款账号
	private String acc_hrt; // 科目号
	private String hx_prdt_no; // 核心产品号
	private String deal_flag; // 日终处理标志
	private String vou_type; // 担保类型
	private String pay_ac_no; // 还款账号
	private String put_ac_no; // 入账账号
	private String pay_type; // 支付方式
	private Integer over_days; // 逾期天数
	private Integer tot_over_times; // 逾期次数
	private String five_sts; // 五级分类状态
	private String four_sts; // 四级分类状态
	private String grade; // 信用等级
	private String due_sts; // 借据状态
	private String mang_brno; // 管户机构
	private String mang_no; // 管户客户经理
	private String acc_br_no; // 核算机构
	private String br_no; // 登记单位
	private String op_no; // 操作员
	private String tx_date; // 登记日期
	private String up_date; // 更新日期
	private String filler; // 登记单位
	private String old_end_date; //
	private String pact_no; // 合同号
	private String due_no; // 借据号
	private String cif_no; // 客户号
	private String cif_name; // 客户名称
	private String app_no; // 申请号
	private String note_no; // 通知单编号
	private String occur_type; // 发生类型
	private String sub_prdt_no; // 子产品号
	private String prdt_no; // 产品号
	private String cur_no; // 币种
	private String time_type; // 时限类型
	private Integer term_mon; // 期限月
	private Integer term_day; // 期限日
	private String beg_date; // 发放日期
	private String end_date; // 到期日期
	private String base_rate_type; // 利率类型
	private Double base_rate; // 基准列率
	private Double ln_rate; // 执行利率
	private Double float_ratio; // 浮动比率
	private Double over_rate; // 逾期利率
	private Double fine_rate; // 罚息利率
	private Double cmpd_rate; // 复利利率
	private Integer exp_flag; // 展期标志
	private Double exp_rate; // 展期利率
	private Double inner_intst; // 表内欠息
	private Double outer_intst; // 表外欠息
	private Double due_amt; // 借据金额
	private Double bal; // 借据余额
	private String pact_type; // 合同类型
	private String ic_type; // 计息方式
	private String repay_type; // 还款方式
	private String rate_chg_type; // 利率调整方式
	private String repay_day_way; // 还款日方式
	private String repay_day; // 还款日
	private String repay_term_kind; // 还款周期类型
	private Integer repay_term; // 还款周期

	private String payee_name; // 收款人全称
	private String payee_ac_no; // 收款人账号
	private String payee_br_name; // 收款人开户银行全称

	private String put_name; // 入账账户户名

	private String rate_no; // 利率编号
	private String lv_fee; // 贷后管理保证金比例

	private String collected;

	// 非数据库字段，用于垫款申请客户查询

	public String getLv_fee() {
		return lv_fee;
	}

	public String getIf_auto() {
		return if_auto;
	}

	public void setIf_auto(String if_auto) {
		this.if_auto = if_auto;
	}

	public void setLv_fee(String lvFee) {
		lv_fee = lvFee;
	}

	private String ln_type;

	public String getRate_no() {
		return rate_no;
	}

	public void setRate_no(String rateNo) {
		rate_no = rateNo;
	}

	public String getPut_name() {
		return put_name;
	}

	public void setPut_name(String put_name) {
		this.put_name = put_name;
	}

	public String getRate_chg_type() {
		return rate_chg_type;
	}

	public void setRate_chg_type(String rateChgType) {
		rate_chg_type = rateChgType;
	}

	public String getRepay_day_way() {
		return repay_day_way;
	}

	public void setRepay_day_way(String repayDayWay) {
		repay_day_way = repayDayWay;
	}

	public String getRepay_day() {
		return repay_day;
	}

	public void setRepay_day(String repayDay) {
		repay_day = repayDay;
	}

	public String getRepay_term_kind() {
		return repay_term_kind;
	}

	public void setRepay_term_kind(String repayTermKind) {
		repay_term_kind = repayTermKind;
	}

	public Integer getRepay_term() {
		return repay_term;
	}

	public void setRepay_term(Integer repayTerm) {
		repay_term = repayTerm;
	}

	public String getRepay_type() {
		return repay_type;
	}

	public void setRepay_type(String repayType) {
		repay_type = repayType;
	}

	public String getIc_type() {
		return ic_type;
	}

	public void setIc_type(String icType) {
		ic_type = icType;
	}

	// set方法
	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}

	public void setAcc_hrt(String acc_hrt) {
		this.acc_hrt = acc_hrt;
	}

	public void setHx_prdt_no(String hx_prdt_no) {
		this.hx_prdt_no = hx_prdt_no;
	}

	public void setDeal_flag(String deal_flag) {
		this.deal_flag = deal_flag;
	}

	public void setVou_type(String vou_type) {
		this.vou_type = vou_type;
	}

	public void setPay_ac_no(String pay_ac_no) {
		this.pay_ac_no = pay_ac_no;
	}

	public void setPut_ac_no(String put_ac_no) {
		this.put_ac_no = put_ac_no;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public void setOver_days(Integer over_days) {
		this.over_days = over_days;
	}

	public void setFive_sts(String five_sts) {
		this.five_sts = five_sts;
	}

	public void setFour_sts(String four_sts) {
		this.four_sts = four_sts;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setDue_sts(String due_sts) {
		this.due_sts = due_sts;
	}

	public void setMang_brno(String mang_brno) {
		this.mang_brno = mang_brno;
	}

	public void setMang_no(String mang_no) {
		this.mang_no = mang_no;
	}

	public void setAcc_br_no(String acc_br_no) {
		this.acc_br_no = acc_br_no;
	}

	public void setBr_no(String br_no) {
		this.br_no = br_no;
	}

	public void setOp_no(String op_no) {
		this.op_no = op_no;
	}

	public void setTx_date(String tx_date) {
		this.tx_date = tx_date;
	}

	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public void setOld_end_date(String old_end_date) {
		this.old_end_date = old_end_date;
	}

	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}

	public void setDue_no(String due_no) {
		this.due_no = due_no;
	}

	public void setCif_no(String cif_no) {
		this.cif_no = cif_no;
	}

	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}

	public void setApp_no(String app_no) {
		this.app_no = app_no;
	}

	public void setNote_no(String note_no) {
		this.note_no = note_no;
	}

	public void setOccur_type(String occur_type) {
		this.occur_type = occur_type;
	}

	public void setSub_prdt_no(String sub_prdt_no) {
		this.sub_prdt_no = sub_prdt_no;
	}

	public void setPrdt_no(String prdt_no) {
		this.prdt_no = prdt_no;
	}

	public void setCur_no(String cur_no) {
		this.cur_no = cur_no;
	}

	public void setTime_type(String time_type) {
		this.time_type = time_type;
	}

	public void setTerm_mon(Integer term_mon) {
		this.term_mon = term_mon;
	}

	public void setTerm_day(Integer term_day) {
		this.term_day = term_day;
	}

	public void setBeg_date(String beg_date) {
		this.beg_date = beg_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public void setBase_rate_type(String base_rate_type) {
		this.base_rate_type = base_rate_type;
	}

	public void setBase_rate(Double base_rate) {
		this.base_rate = base_rate;
	}

	public void setLn_rate(Double ln_rate) {
		this.ln_rate = ln_rate;
	}

	public void setFloat_ratio(Double float_ratio) {
		this.float_ratio = float_ratio;
	}

	public void setOver_rate(Double over_rate) {
		this.over_rate = over_rate;
	}

	public void setFine_rate(Double fine_rate) {
		this.fine_rate = fine_rate;
	}

	public void setCmpd_rate(Double cmpd_rate) {
		this.cmpd_rate = cmpd_rate;
	}

	public void setExp_flag(Integer exp_flag) {
		this.exp_flag = exp_flag;
	}

	public void setExp_rate(Double exp_rate) {
		this.exp_rate = exp_rate;
	}

	public void setInner_intst(Double inner_intst) {
		this.inner_intst = inner_intst;
	}

	public void setOuter_intst(Double outer_intst) {
		this.outer_intst = outer_intst;
	}

	public void setDue_amt(Double due_amt) {
		this.due_amt = due_amt;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	// get方法
	public String getAc_no() {
		return ac_no;
	}

	public String getAcc_hrt() {
		return acc_hrt;
	}

	public String getHx_prdt_no() {
		return hx_prdt_no;
	}

	public String getDeal_flag() {
		return deal_flag;
	}

	public String getVou_type() {
		return vou_type;
	}

	public String getPay_ac_no() {
		return pay_ac_no;
	}

	public String getPut_ac_no() {
		return put_ac_no;
	}

	public String getPay_type() {
		return pay_type;
	}

	public Integer getOver_days() {
		return over_days;
	}

	public String getFive_sts() {
		return five_sts;
	}

	public String getFour_sts() {
		return four_sts;
	}

	public String getGrade() {
		return grade;
	}

	public String getDue_sts() {
		return due_sts;
	}

	public String getMang_brno() {
		return mang_brno;
	}

	public String getMang_no() {
		return mang_no;
	}

	public String getAcc_br_no() {
		return acc_br_no;
	}

	public String getBr_no() {
		return br_no;
	}

	public String getOp_no() {
		return op_no;
	}

	public String getTx_date() {
		return tx_date;
	}

	public String getUp_date() {
		return up_date;
	}

	public String getFiller() {
		return filler;
	}

	public String getOld_end_date() {
		return old_end_date;
	}

	public String getPact_no() {
		return pact_no;
	}

	public String getDue_no() {
		return due_no;
	}

	public String getCif_no() {
		return cif_no;
	}

	public String getCif_name() {
		return cif_name;
	}

	public String getApp_no() {
		return app_no;
	}

	public String getNote_no() {
		return note_no;
	}

	public String getOccur_type() {
		return occur_type;
	}

	public String getSub_prdt_no() {
		return sub_prdt_no;
	}

	public String getPrdt_no() {
		return prdt_no;
	}

	public String getCur_no() {
		return cur_no;
	}

	public String getTime_type() {
		return time_type;
	}

	public Integer getTerm_mon() {
		return term_mon;
	}

	public Integer getTerm_day() {
		return term_day;
	}

	public String getBeg_date() {
		return beg_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public String getBase_rate_type() {
		return base_rate_type;
	}

	public Double getBase_rate() {
		return base_rate;
	}

	public Double getLn_rate() {
		return ln_rate;
	}

	public Double getFloat_ratio() {
		return float_ratio;
	}

	public Double getOver_rate() {
		return over_rate;
	}

	public Double getFine_rate() {
		return fine_rate;
	}

	public Double getCmpd_rate() {
		return cmpd_rate;
	}

	public Integer getExp_flag() {
		return exp_flag;
	}

	public Double getExp_rate() {
		return exp_rate;
	}

	public Double getInner_intst() {
		return inner_intst;
	}

	public Double getOuter_intst() {
		return outer_intst;
	}

	public Double getDue_amt() {
		return due_amt;
	}

	public Double getBal() {
		return bal;
	}

	public String getPact_type() {
		return pact_type;
	}

	public void setPact_type(String pact_type) {
		this.pact_type = pact_type;
	}

	public String getPayee_name() {
		return payee_name;
	}

	public void setPayee_name(String payee_name) {
		this.payee_name = payee_name;
	}

	public String getPayee_ac_no() {
		return payee_ac_no;
	}

	public void setPayee_ac_no(String payee_ac_no) {
		this.payee_ac_no = payee_ac_no;
	}

	public String getPayee_br_name() {
		return payee_br_name;
	}

	public void setPayee_br_name(String payee_br_name) {
		this.payee_br_name = payee_br_name;
	}

	public Integer getTot_over_times() {
		return tot_over_times;
	}

	public void setTot_over_times(Integer tot_over_times) {
		this.tot_over_times = tot_over_times;
	}

	public String getLn_type() {
		return ln_type;
	}

	public void setLn_type(String lnType) {
		ln_type = lnType;
	}

	public String getCap_type() {
		return cap_type;
	}

	public void setCap_type(String cap_type) {
		this.cap_type = cap_type;
	}

	public String getCollected() {
		return collected;
	}

	public void setCollected(String collected) {
		this.collected = collected;
	}

}