package com.dx.loan.putout.bean;

public class PactBean {
	//合同号
    private String pactNo;
    //客户号
    private String cifNo;
    //合同金额
    private String pactAmt;
    //合同开始日期
    private String begDate;
    //合同结束日期
    private String endDate;
    //合同期限年
    private String termYear;
    //合同期限月
    private String termMon;
    //合同期限日
    private String termDay;
    //合同利率
    private String lnRate;
    //合同利率浮动比率
    private String rateFloat;
    //罚息利率
    private String fineRate;
    //罚息浮动比率
    private String fineRateFloat;
    //展期利率
    private String expRate;
    //展期浮动比率
    private String expRateFloat;
    //复利浮动比率
    private String cmpdRateFloat;
    //复利利率
    private String cmpdRate;
    //逾期浮动比率
    private String overRateFloat;
    //逾期利率
    private String overRate;
	//计息方式
    private String rateWay;
    //还款方式
    private String returnMethod;
    //业务品种编号
    private String pactKind;
    //发生类型
    private String occurType;
    //固定利息金额
    private String fixrateInterest;
    //固定利息是否提前收取
    private String ispreFixrate;
    //咨询费
    private String consultAmt;
    //主担保方式
    private String mainGuaran;
    //担保类型
    private String guaranType;
    //合同性质
    private String pactNature;
    //申请用途
    private String pactUse;
    //用途说明˵��
    private String pactUseDesc;
    //申请人开户银行名称
    private String applyerBankName;
    //申请人账号
    private String applyerBankNo;
    //收款人开户银行名称
    private String receiverBankName;
    //收款人账号
    private String receiverBankNo;
    //客户经理
    private String telNo;
    //信息记录操作类型
    private String infoOpType;
    //֤证件类型
    private String idType;
    //授信协议号码
    private String authId;
    //银团标志
    private String ifYt;
    //担保标志
    private String vouType;
    //合同有效状态
    private String pactSts;
    //合同签订日期
    private String signDate;
    //贷款投向
    private String lnTradeNo;
    //合同余额
    private String bal;
    //贷款业务种类
    private String loanKind;
    //业务发生日期(YYYYMMDD HH:MM:SS)
    private String occDate;
    //贷款种类
    private String lnType;
    //币种
    private String curNo;
    //贷款性质
    private String loanType;
    //利率编号
    private String rateNo;
    
    public String getFineRateFloat() {
		return fineRateFloat;
	}

	public void setFineRateFloat(String fineRateFloat) {
		this.fineRateFloat = fineRateFloat;
	}

	public String getExpRateFloat() {
		return expRateFloat;
	}

	public void setExpRateFloat(String expRateFloat) {
		this.expRateFloat = expRateFloat;
	}

	public String getCmpdRate() {
		return cmpdRate;
	}

	public void setCmpdRate(String cmpdRate) {
		this.cmpdRate = cmpdRate;
	}

	public String getOverRate() {
		return overRate;
	}

	public void setOverRate(String overRate) {
		this.overRate = overRate;
	}

    public PactBean(){};
    
    public String getPactNo() {
        return pactNo;
    }

    public void setPactNo(String pactNo) {
        this.pactNo = pactNo;
    }

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getPactAmt() {
        return pactAmt;
    }

    public void setPactAmt(String pactAmt) {
        this.pactAmt = pactAmt;
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

    public String getTermYear() {
        return termYear;
    }

    public void setTermYear(String termYear) {
        this.termYear = termYear;
    }

    public String getTermMon() {
        return termMon;
    }

    public void setTermMon(String termMon) {
        this.termMon = termMon;
    }

    public String getTermDay() {
        return termDay;
    }

    public void setTermDay(String termDay) {
        this.termDay = termDay;
    }

    public String getLnRate() {
        return lnRate;
    }

    public void setLnRate(String lnRate) {
        this.lnRate = lnRate;
    }

    public String getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(String rateFloat) {
        this.rateFloat = rateFloat;
    }

    public String getFineRate() {
        return fineRate;
    }

    public void setFineRate(String fineRate) {
        this.fineRate = fineRate;
    }

    public String getExpRate() {
        return expRate;
    }

    public void setExpRate(String expRate) {
        this.expRate = expRate;
    }

    public String getCmpdRateFloat() {
        return cmpdRateFloat;
    }

    public void setCmpdRateFloat(String cmpdRateFloat) {
        this.cmpdRateFloat = cmpdRateFloat;
    }

    public String getOverRateFloat() {
        return overRateFloat;
    }

    public void setOverRateFloat(String overRateFloat) {
        this.overRateFloat = overRateFloat;
    }

    public String getRateWay() {
        return rateWay;
    }

    public void setRateWay(String rateWay) {
        this.rateWay = rateWay;
    }

    public String getReturnMethod() {
        return returnMethod;
    }

    public void setReturnMethod(String returnMethod) {
        this.returnMethod = returnMethod;
    }

    public String getPactKind() {
        return pactKind;
    }

    public void setPactKind(String pactKind) {
        this.pactKind = pactKind;
    }

    public String getOccurType() {
        return occurType;
    }

    public void setOccurType(String occurType) {
        this.occurType = occurType;
    }

    public String getFixrateInterest() {
        return fixrateInterest;
    }

    public void setFixrateInterest(String fixrateInterest) {
        this.fixrateInterest = fixrateInterest;
    }

    public String getIspreFixrate() {
        return ispreFixrate;
    }

    public void setIspreFixrate(String ispreFixrate) {
        this.ispreFixrate = ispreFixrate;
    }

    public String getConsultAmt() {
        return consultAmt;
    }

    public void setConsultAmt(String consultAmt) {
        this.consultAmt = consultAmt;
    }

    public String getMainGuaran() {
        return mainGuaran;
    }

    public void setMainGuaran(String mainGuaran) {
        this.mainGuaran = mainGuaran;
    }

    public String getGuaranType() {
        return guaranType;
    }

    public void setGuaranType(String guaranType) {
        this.guaranType = guaranType;
    }

    public String getPactNature() {
        return pactNature;
    }

    public void setPactNature(String pactNature) {
        this.pactNature = pactNature;
    }

    public String getPactUse() {
        return pactUse;
    }

    public void setPactUse(String pactUse) {
        this.pactUse = pactUse;
    }

    public String getPactUseDesc() {
        return pactUseDesc;
    }

    public void setPactUseDesc(String pactUseDesc) {
        this.pactUseDesc = pactUseDesc;
    }

    public String getApplyerBankName() {
        return applyerBankName;
    }

    public void setApplyerBankName(String applyerBankName) {
        this.applyerBankName = applyerBankName;
    }

    public String getApplyerBankNo() {
        return applyerBankNo;
    }

    public void setApplyerBankNo(String applyerBankNo) {
        this.applyerBankNo = applyerBankNo;
    }

    public String getReceiverBankName() {
        return receiverBankName;
    }

    public void setReceiverBankName(String receiverBankName) {
        this.receiverBankName = receiverBankName;
    }

    public String getReceiverBankNo() {
        return receiverBankNo;
    }

    public void setReceiverBankNo(String receiverBankNo) {
        this.receiverBankNo = receiverBankNo;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getInfoOpType() {
        return infoOpType;
    }

    public void setInfoOpType(String infoOpType) {
        this.infoOpType = infoOpType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getIfYt() {
        return ifYt;
    }

    public void setIfYt(String ifYt) {
        this.ifYt = ifYt;
    }

    public String getVouType() {
        return vouType;
    }

    public void setVouType(String vouType) {
        this.vouType = vouType;
    }

    public String getPactSts() {
        return pactSts;
    }

    public void setPactSts(String pactSts) {
        this.pactSts = pactSts;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getLnTradeNo() {
        return lnTradeNo;
    }

    public void setLnTradeNo(String lnTradeNo) {
        this.lnTradeNo = lnTradeNo;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }

    public String getLoanKind() {
        return loanKind;
    }

    public void setLoanKind(String loanKind) {
        this.loanKind = loanKind;
    }

    public String getOccDate() {
        return occDate;
    }

    public void setOccDate(String occDate) {
        this.occDate = occDate;
    }

    public String getLnType() {
        return lnType;
    }

    public void setLnType(String lnType) {
        this.lnType = lnType;
    }

    public String getCurNo() {
        return curNo;
    }

    public void setCurNo(String curNo) {
        this.curNo = curNo;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getRateNo() {
        return rateNo;
    }

    public void setRateNo(String rateNo) {
        this.rateNo = rateNo;
    }
}