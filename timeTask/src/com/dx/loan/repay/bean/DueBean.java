package com.dx.loan.repay.bean;

public class DueBean {
	//借据号
    private String dueNo;
    //合同号
    private String pactNo;
    //借据金额
    private String dueAmt;
    //开始日期
    private String begDate;
    //结束日期
    private String endDate;
    //客户号
    private String cifNo;
    //宽限天数
    private String extDays;
    //扣除咨询费
    private String consFee;
    //咨询费扣除方式
    private String consFeeType;
    //借据状态
    private String dueState;
    //展期标志
    private String expFlag;
    //借据余额
    private String bal;
    //表内欠息(上报)
    private String innerIntst;
    //表外欠息(上报)
    private String outerIntst;
    //五级分类(上报)
    private String fiveClass;
    //四级分类(上报)
    private String fourClass;
    //是否固定日还款
    private String isFix;
    //固定日还款日
    private String fixDate;
    // 是否强制计划
    private String isForce;
    
    
    public String getIsForce() {
		return isForce;
	}
	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}
	public DueBean(){};
    public String getDueNo() {
        return dueNo;
    }

    public void setDueNo(String dueNo) {
        this.dueNo = dueNo;
    }

    public String getPactNo() {
        return pactNo;
    }

    public void setPactNo(String pactNo) {
        this.pactNo = pactNo;
    }

    public String getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(String dueAmt) {
        this.dueAmt = dueAmt;
    }

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getExtDays() {
        return extDays;
    }

    public void setExtDays(String extDays) {
        this.extDays = extDays;
    }

    public String getConsFee() {
        return consFee;
    }

    public void setConsFee(String consFee) {
        this.consFee = consFee;
    }

    public String getConsFeeType() {
        return consFeeType;
    }

    public void setConsFeeType(String consFeeType) {
        this.consFeeType = consFeeType;
    }

    public String getDueState() {
        return dueState;
    }

    public void setDueState(String dueState) {
        this.dueState = dueState;
    }

    public String getExpFlag() {
        return expFlag;
    }

    public void setExpFlag(String expFlag) {
        this.expFlag = expFlag;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }

   

    public String getInnerIntst() {
        return innerIntst;
    }

    public void setInnerIntst(String innerIntst) {
        this.innerIntst = innerIntst;
    }

    public String getOuterIntst() {
        return outerIntst;
    }

    public void setOuterIntst(String outerIntst) {
        this.outerIntst = outerIntst;
    }

    public String getFiveClass() {
        return fiveClass;
    }

    public void setFiveClass(String fiveClass) {
        this.fiveClass = fiveClass;
    }

    public String getFourClass() {
        return fourClass;
    }

    public void setFourClass(String fourClass) {
        this.fourClass = fourClass;
    }

    public String getIsFix() {
        return isFix;
    }

    public void setIsFix(String isFix) {
        this.isFix = isFix;
    }

    public String getFixDate() {
        return fixDate;
    }

    public void setFixDate(String fixDate) {
        this.fixDate = fixDate;
    }
}