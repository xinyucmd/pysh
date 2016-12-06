package com.jiangchuanbanking.process.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.jiangchuanbanking.base.domain.BaseEntity;


public class WealthFlow  extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pact_no;
	
	private Integer cif_no;
	
	private String cif_name;
	
	private String claims_pact_no;
	
	private String prdt_no;
	
	private String prdt_name;
	 
	private String workflow_type;
	
	private String status;
	
	private Integer op_no;
	
	private String op_name;
	
	private Date create_time;
	
	private Date update_time;
	
	private String cmt;
	
	
	private Set<FlowIdea> flowIdeaSet;
	
	

	




	public String getPact_no() {
		return pact_no;
	}








	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}








	public Integer getCif_no() {
		return cif_no;
	}








	public void setCif_no(Integer cif_no) {
		this.cif_no = cif_no;
	}








	public String getCif_name() {
		return cif_name;
	}








	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}








	public String getClaims_pact_no() {
		return claims_pact_no;
	}








	public void setClaims_pact_no(String claims_pact_no) {
		this.claims_pact_no = claims_pact_no;
	}







   







	public String getPrdt_no() {
		return prdt_no;
	}








	public void setPrdt_no(String prdt_no) {
		this.prdt_no = prdt_no;
	}








	public String getPrdt_name() {
		return prdt_name;
	}








	public void setPrdt_name(String prdt_name) {
		this.prdt_name = prdt_name;
	}








	public String getWorkflow_type() {
		return workflow_type;
	}








	public void setWorkflow_type(String workflow_type) {
		this.workflow_type = workflow_type;
	}








	public String getStatus() {
		return status;
	}








	public void setStatus(String status) {
		this.status = status;
	}








	public Integer getOp_no() {
		return op_no;
	}








	public void setOp_no(Integer op_no) {
		this.op_no = op_no;
	}








	public String getOp_name() {
		return op_name;
	}








	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}




   








	







	public Date getCreate_time() {
		return create_time;
	}








	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}








	public Date getUpdate_time() {
		return update_time;
	}








	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}








	public String getCmt() {
		return cmt;
	}








	public void setCmt(String cmt) {
		this.cmt = cmt;
	}








	public Set<FlowIdea> getFlowIdeaSet() {
		return flowIdeaSet;
	}








	public void setFlowIdeaSet(Set<FlowIdea> flowIdeaSet) {
		this.flowIdeaSet = flowIdeaSet;
	}








	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}






	

	

}
