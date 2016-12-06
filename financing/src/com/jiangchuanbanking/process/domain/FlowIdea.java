package com.jiangchuanbanking.process.domain;

import java.io.Serializable;
import java.util.Date;

import com.jiangchuanbanking.base.domain.BaseEntity;

public class FlowIdea  extends BaseEntity implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String workflow_no;
	
	private String flow_type;
	
	private String app_desc;
	
	private String app_idea;
	
	private String sts;
	
	private Integer op_no;
	
	private Date create_date;
	
	private String cmt;
	
	private WealthFlow wealthFlow;
	
	

	




	public Integer getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public String getWorkflow_no() {
		return workflow_no;
	}





	public void setWorkflow_no(String workflow_no) {
		this.workflow_no = workflow_no;
	}





	public String getFlow_type() {
		return flow_type;
	}





	public void setFlow_type(String flow_type) {
		this.flow_type = flow_type;
	}





	public String getApp_desc() {
		return app_desc;
	}





	public void setApp_desc(String app_desc) {
		this.app_desc = app_desc;
	}





	public String getApp_idea() {
		return app_idea;
	}





	public void setApp_idea(String app_idea) {
		this.app_idea = app_idea;
	}





	public String getSts() {
		return sts;
	}





	public void setSts(String sts) {
		this.sts = sts;
	}



    




	public Integer getOp_no() {
		return op_no;
	}





	public void setOp_no(Integer op_no) {
		this.op_no = op_no;
	}











	public Date getCreate_date() {
		return create_date;
	}





	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}





	public String getCmt() {
		return cmt;
	}





	public void setCmt(String cmt) {
		this.cmt = cmt;
	}





	public WealthFlow getWealthFlow() {
		return wealthFlow;
	}





	public void setWealthFlow(WealthFlow wealthFlow) {
		this.wealthFlow = wealthFlow;
	}





	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	



}
