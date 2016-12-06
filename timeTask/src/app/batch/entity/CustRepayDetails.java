package app.batch.entity;

public class CustRepayDetails {
	private String id	;
	private String due_no	;
	private String over_days;
	private String term_no		;
	private String term_amt	;
	private String term_date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDue_no() {
		return due_no;
	}
	public void setDue_no(String due_no) {
		this.due_no = due_no;
	}
	public String getOver_days() {
		return over_days;
	}
	public void setOver_days(String over_days) {
		this.over_days = over_days;
	}
	public String getTerm_no() {
		return term_no;
	}
	public void setTerm_no(String term_no) {
		this.term_no = term_no;
	}
	public String getTerm_amt() {
		return term_amt;
	}
	public void setTerm_amt(String term_amt) {
		this.term_amt = term_amt;
	}
	public String getTerm_date() {
		return term_date;
	}
	public void setTerm_date(String term_date) {
		this.term_date = term_date;
	}
	 
}
