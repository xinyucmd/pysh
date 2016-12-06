package com.jiangchuanbanking.prod.domain;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import com.jiangchuanbanking.base.domain.BaseEntity;


public  class ProdBaseEntity extends BaseEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 8250950813769457555L;

	private String prdtName;
	private String prdtNo;
	private String standardAmt;
	private String advanceRedeem;
	private String delFlg;//1.停用0启用
	private String sts;//5新建 8补充 10待审核 15审核通过 20审核否决 
	private String startDate;
	private String endDate;
	private String createOp;
	private String createDate;
	private String cmt;
	private String approveOp;//审批人
	private String approveDate;//审批时间
	private String approveIdea;//审批意见
	
	private Set<ProdChargePolicy> chargeLists = new HashSet<ProdChargePolicy>();
	

	public String getPrdtName() {
		return prdtName;
	}

	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
	}

	public String getPrdtNo() {
		return prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public String getStandardAmt() {
		return standardAmt;
	}

	public void setStandardAmt(String standardAmt) {
		this.standardAmt = standardAmt;
	}

	public String getAdvanceRedeem() {
		return advanceRedeem;
	}

	public void setAdvanceRedeem(String advanceRedeem) {
		this.advanceRedeem = advanceRedeem;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCreateOp() {
		return createOp;
	}

	public void setCreateOp(String createOp) {
		this.createOp = createOp;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public Set<ProdChargePolicy> getChargeLists() {
		return chargeLists;
	}

	public void setChargeLists(Set<ProdChargePolicy> chargeLists) {
		this.chargeLists = chargeLists;
	}

	
	
	
	
	 public String getApproveOp() {
		return approveOp;
	}

	public void setApproveOp(String approveOp) {
		this.approveOp = approveOp;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getApproveIdea() {
		return approveIdea;
	}

	public void setApproveIdea(String approveIdea) {
		this.approveIdea = approveIdea;
	}

	@Override
	 public ProdBaseEntity clone() {
		 ProdBaseEntity o = null;
	     try {
	           o = (ProdBaseEntity) super.clone();
	     } catch (CloneNotSupportedException e) {
	           e.printStackTrace();
	      }
	     return o;
	  }
//	public Set<ProdChargePolicy> getChargeLists() {
//		return chargeLists;
//	}
//
//	public void setChargeLists(Set<ProdChargePolicy> chargeLists) {
//		this.chargeLists = chargeLists;
//	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.prdtName;
	}


}
