package app.batch.entity;

public class Rptputstat {
	private String mon_beg_date;
	private String mon_end_date;
	private String br_no;
	private String br_lev;
	private String prdt_no;
	private String vou_type;
	private String term_type;
	private double amt;
	private double cnt;
	private String app_op_no;
	public String getMon_beg_date() {
		return mon_beg_date;
	}
	public void setMon_beg_date(String mon_beg_date) {
		this.mon_beg_date = mon_beg_date;
	}
	public String getMon_end_date() {
		return mon_end_date;
	}
	public void setMon_end_date(String mon_end_date) {
		this.mon_end_date = mon_end_date;
	}
	public String getBr_no() {
		return br_no;
	}
	public void setBr_no(String br_no) {
		this.br_no = br_no;
	}
	public String getBr_lev() {
		return br_lev;
	}
	public void setBr_lev(String br_lev) {
		this.br_lev = br_lev;
	}
	public String getPrdt_no() {
		return prdt_no;
	}
	public void setPrdt_no(String prdt_no) {
		this.prdt_no = prdt_no;
	}
	public String getVou_type() {
		return vou_type;
	}
	public void setVou_type(String vou_type) {
		this.vou_type = vou_type;
	}
	public String getTerm_type() {
		return term_type;
	}
	public void setTerm_type(String term_type) {
		this.term_type = term_type;
	}
	public String getApp_op_no() {
		return app_op_no;
	}
	public void setApp_op_no(String app_op_no) {
		this.app_op_no = app_op_no;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public double getCnt() {
		return cnt;
	}
	public void setCnt(double cnt) {
		this.cnt = cnt;
	}
	
}
