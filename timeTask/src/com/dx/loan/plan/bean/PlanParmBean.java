/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PlanParmBean.java
 * 包名： com.dx.loan.putout.bean
 * 说明：
 * @author 乾之轩
 * @date 2012-5-24 上午11:04:45
 * @version V1.0
 */ 
package com.dx.loan.plan.bean;

import com.dx.common.util.StringUtil;

/**
 * 类名： PlanParmBean
 * 描述： 生成还款计划时的参数实体
 * @author 乾之轩
 * @date 2012-5-24 上午11:04:45
 *
 */
public class PlanParmBean {
	// 生成还款计划的开始日期
	private  String beginDate;
	// 是否是强制计划 0 不是 1 是
	private String isForce;
	// 是否预先支付利息  0 不预先支付 1 预先支付 3 当做一期
	private String  isAdvance;
	// 是否自动延期 0 不自动延期 1 自动延期
	private String isDelay;
	// 还款方式
	private String returnMentod;
	// 期数
	private String terms;
	// 是否固定还款日 0 不是 1是
	private String isFixDate;
	// 固定还款日期
	private String fixDate;
	// 提前还款本金
	private String advaceAmt;
	// 对哪一期还款计划进行了调整
	private String termNo;
	// 还款计划调整方式 0 调整本金  1 调整结束日期
	private  String adjustType;
	// 还款计划开始期数
	private String beginTerm;
	
	
	
	public String getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	public String getAdvaceAmt() {
		return StringUtil.KillNull(advaceAmt, "0.00");
	}

	public void setAdvaceAmt(String advaceAmt) {
		this.advaceAmt = advaceAmt;
	}

	public String getIsFixDate() {
		return isFixDate;
	}

	public void setIsFixDate(String isFixDate) {
		this.isFixDate = isFixDate;
	}

	public String getFixDate() {
		return fixDate;
	}

	public void setFixDate(String fixDate) {
		this.fixDate = fixDate;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getReturnMentod() {
		return returnMentod;
	}

	public void setReturnMentod(String returnMentod) {
		this.returnMentod = returnMentod;
	}

	public PlanParmBean(){};
	
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getIsForce() {
		return isForce;
	}
	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}
	public String getIsAdvance() {
		return isAdvance;
	}
	public void setIsAdvance(String isAdvance) {
		this.isAdvance = isAdvance;
	}
	public String getIsDelay() {
		return isDelay;
	}
	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getBeginTerm() {
		return beginTerm;
	}

	public void setBeginTerm(String beginTerm) {
		this.beginTerm = beginTerm;
	}

	

}
