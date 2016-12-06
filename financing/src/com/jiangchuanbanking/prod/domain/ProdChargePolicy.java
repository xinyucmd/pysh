package com.jiangchuanbanking.prod.domain;

import java.io.Serializable;
import java.util.Date;

import com.jiangchuanbanking.base.domain.BaseEntity;
import com.jiangchuanbanking.system.domain.User;

public  class ProdChargePolicy extends BaseEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 8250950813769457555L;

	private Integer id;
	private String prdtName;
	private String prdtNo;
	private String type;//A.产品定价	B.赎回定价
	private String paymentType;
	private String returnRate;
	private String stageMin;
	private String stageMax;
	private String rate;
	private String ifRedeem;
	private String delFlg;
	private String createOp;
	private String createDateString;
	private String cmt;
	private String stageMax1;
	private ProdBaseEntity prodBaseEntity;
	public ProdChargePolicy() {
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getReturnRate() {
		return returnRate;
	}
	public void setReturnRate(String returnRate) {
		this.returnRate = returnRate;
	}
	public String getStageMin() {
		return stageMin;
	}
	public void setStageMin(String stageMin) {
		this.stageMin = stageMin;
	}
	public String getStageMax() {
		return stageMax;
	}
	public void setStageMax(String stageMax) {
		this.stageMax = stageMax;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getIfRedeem() {
		return ifRedeem;
	}
	public void setIfRedeem(String ifRedeem) {
		this.ifRedeem = ifRedeem;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getCreateOp() {
		return createOp;
	}
	public void setCreateOp(String createOp) {
		this.createOp = createOp;
	}
	public String getCreateDateString() {
		return createDateString;
	}
	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ProdBaseEntity getProdBaseEntity() {
		return prodBaseEntity;
	}
	public void setProdBaseEntity(ProdBaseEntity prodBaseEntity) {
		this.prodBaseEntity = prodBaseEntity;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getStageMax1() {
		return stageMax1;
	}
	public void setStageMax1(String stageMax1) {
		this.stageMax1 = stageMax1;
	}


}
