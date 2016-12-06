package com.dx.collect.bean;

public class CheckReportPolicy {
	private String id;
	private String vou_type;
	private String report_type;//1 首次检查报告  2 跟踪检查报告 
	private String aft_classify;
	private String amt_min;
	private String amt_max;
	private String days_ahead;
	private String time_interval;    
	private String create_time;
	private String update_time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVou_type() {
		return vou_type;
	}
	public void setVou_type(String vou_type) {
		this.vou_type = vou_type;
	}
	public String getReport_type() {
		return report_type;
	}
	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}
	public String getAft_classify() {
		return aft_classify;
	}
	public void setAft_classify(String aft_classify) {
		this.aft_classify = aft_classify;
	}
	public String getAmt_min() {
		return amt_min;
	}
	public void setAmt_min(String amt_min) {
		this.amt_min = amt_min;
	}
	public String getAmt_max() {
		return amt_max;
	}
	public void setAmt_max(String amt_max) {
		this.amt_max = amt_max;
	}
	public String getDays_ahead() {
		return days_ahead;
	}
	public void setDays_ahead(String days_ahead) {
		this.days_ahead = days_ahead;
	}
	public String getTime_interval() {
		return time_interval;
	}
	public void setTime_interval(String time_interval) {
		this.time_interval = time_interval;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
