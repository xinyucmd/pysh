package com.dx.common.bean;

import com.dx.common.util.StringUtil;
import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.repay.bean.DueBean;
/**
 * 
 * 类名： LoanAcLnMst
 * 描述： 贷款主文件
 * @author sll
 * @date May 15, 2012 10:53:03 AM
 * 
 *
 */
public class AcLnMstBean {
	//贷款主文件Id
    private String id;
    //借据号
    private String dueNo;
    //合同号
    private String pactNo;
    //借据金额
    private String dueAmt;
    //开始日期
    private String dueBegDate;
    //结束日期
    private String dueEndDate;
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
    private String dueBal;
	//账户管理费/期(上报)
    private String accFee;
    //账户管理费/汇总(上报)
    private String accTolFee;
    //表内欠息(上报)
    private String innerIntst;
    //表外欠息(上报)
    private String outerIntst;
    //五级分类(上报)
    private String fiveClass;
    //四级分类(上报)
    private String fourClass;
    //合同金额
    private String pactAmt;
    //合同开始日期
    private String pactBegDate;
    //合同结束日期
    private String pactEndDate;
    //合同期限月
    private String termMon;
    //合同期限日
    private String termDay;
    //合同利率
    private String lnRate;
    //合同利率浮动比率
    private String rateFloat;

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
    //用途说明
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
    //证件类型
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
    private String pactBal;
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
    // 上次还款日期
    private String lastReturnDate;
    // 个人利率调整标志(0没有调整过 1调整过)
    private String  PerRateChe;
    // 是否强制计划
    private String isForce;
    //是否固定日还款
    private String isFix;
	//固定日还款日
    private String  fixDate;
    // 是否自动延期 0 不延期 1 延期
    private String isDelay;
    // 预先支付方式 
    private String isAdvance;
    // 是否固定日还款 0 不是固定还款日  1 固定日还款  
    private String isFixDate;
    // 履约保证金额
    private String perfAmount;
    // 履约保证金额比例
    private String perfAmountPerc;
    
    // 提前还款金额
    private String  advaceAmt;
    
    private String planflag;
    // 历史补录使用已经还款本金
    private String hisAmt;
    //本金结余
    private String bjbalance;
    
    
    //利息结余
    
    private String ratecomebalance;
    
    
    
    
    
    

    
    
    
    public String getHisAmt() {
		return hisAmt;
	}


	public void setHisAmt(String hisAmt) {
		this.hisAmt = hisAmt;
	}


	public String getPlanflag() {
		return planflag;
	}


	public void setPlanflag(String planflag) {
		this.planflag = planflag;
	}


	public String getPerfAmountPerc() {
		return perfAmountPerc;
	}


	public void setPerfAmountPerc(String perfAmountPerc) {
		this.perfAmountPerc = perfAmountPerc;
	}


	public String getPerfAmount() {
		return StringUtil.KillNull(perfAmount, "0.00");
	}


	public void setPerfAmount(String perfAmount) {
		this.perfAmount = perfAmount;
	}


	public String getIsFixDate() {
		return isFixDate;
	}


	public void setIsFixDate(String isFixDate) {
		this.isFixDate = isFixDate;
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


	public String getIsForce() {
		return isForce;
	}


	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}


	public String getPerRateChe() {
		return PerRateChe;
	}


	public void setPerRateChe(String perRateChe) {
		PerRateChe = perRateChe;
	}


	public String getLastReturnDate() {
		return lastReturnDate;
	}


	public void setLastReturnDate(String lastReturnDate) {
		this.lastReturnDate = lastReturnDate;
	}


	public AcLnMstBean(){};
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

   
    public String getDueBal() {
		return dueBal;
	}


	public void setDueBal(String dueBal) {
		this.dueBal = dueBal;
	}


    public String getAccFee() {
        return accFee;
    }

    public void setAccFee(String accFee) {
        this.accFee = accFee;
    }

    public String getAccTolFee() {
        return accTolFee;
    }

    public void setAccTolFee(String accTolFee) {
        this.accTolFee = accTolFee;
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

    public String getPactAmt() {
        return pactAmt;
    }

    public void setPactAmt(String pactAmt) {
        this.pactAmt = pactAmt;
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

    public String getPactBal() {
		return pactBal;
	}


	public void setPactBal(String pactBal) {
		this.pactBal = pactBal;
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


	public String getDueBegDate() {
		return dueBegDate;
	}


	public void setDueBegDate(String dueBegDate) {
		this.dueBegDate = dueBegDate;
	}


	public String getDueEndDate() {
		return dueEndDate;
	}


	public void setDueEndDate(String dueEndDate) {
		this.dueEndDate = dueEndDate;
	}



	public String getPactBegDate() {
		return pactBegDate;
	}


	public void setPactBegDate(String pactBegDate) {
		this.pactBegDate = pactBegDate;
	}


	public String getPactEndDate() {
		return pactEndDate;
	}


	public void setPactEndDate(String pactEndDate) {
		this.pactEndDate = pactEndDate;
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
		/**
		 * 
		 * 方法描述： 将合同信息复制到贷款主文件实体 
		 * @param pactBean
		 * void
		 * @author 乾之轩
		 * @date 2012-5-30 下午05:04:01
		 */
		public void copyVal(PactBean pactBean){
			//合同号
		    this.pactNo = pactBean.getPactNo();
		    //客户号
		    this.cifNo = pactBean.getCifNo();
		    //合同金额
		    this.pactAmt = pactBean.getPactAmt();
		    //合同开始日期
		    this.pactBegDate = pactBean.getBegDate();
		    //合同结束日期
		    this.pactEndDate = pactBean.getEndDate();
		    //合同期限月
		    this.termMon = pactBean.getTermMon();
		    //合同期限日
		    this.termDay = pactBean.getTermDay();
		    //合同利率
		    this.lnRate = pactBean.getLnRate();
		    //合同利率浮动比率
		    this.rateFloat = pactBean.getRateFloat();
		   
		    //展期利率
		    this.expRate = pactBean.getExpRate();
		    //展期浮动比率
		    this.expRateFloat = pactBean.getExpRateFloat();
		    //复利浮动比率
		    this.cmpdRateFloat = pactBean.getCmpdRateFloat();
		    //复利利率
		    this.cmpdRate = pactBean.getCmpdRate();
		    //逾期浮动比率
		    this.overRateFloat = pactBean.getOverRateFloat();
		    //逾期利率
		    this.overRate = pactBean.getOverRate();
			//计息方式
		    this.rateWay = pactBean.getRateWay();
		    //还款方式
		    this.returnMethod = pactBean.getReturnMethod();
		    //业务品种编号
		    this.pactKind = pactBean.getPactKind();
		    //发生类型
		    this.occurType = pactBean.getOccurType();
		    //固定利息金额
		    this.fixrateInterest = pactBean.getFixrateInterest();
		    //固定利息是否提前收取
		    this.ispreFixrate = pactBean.getIspreFixrate();
		    //咨询费
		    this.consultAmt = pactBean.getConsultAmt();
		    //主担保方式
		    this.mainGuaran = pactBean.getMainGuaran();
		    //担保类型
		    this.guaranType = pactBean.getGuaranType();
		    //合同性质
		    this.pactNature = pactBean.getPactNature();
		    //申请用途
		    this.pactUse = pactBean.getPactUse();
		    //用途说明
		    this.pactUseDesc = pactBean.getPactUseDesc();
		    //申请人开户银行名称
		    this.applyerBankName = pactBean.getApplyerBankName();
		    //申请人账号
		    this.applyerBankNo = pactBean.getApplyerBankNo();
		    //收款人开户银行名称
		    this.receiverBankName = pactBean.getReceiverBankName();
		    //收款人账号
		    this.receiverBankNo = pactBean.getReceiverBankNo();
		    //客户经理
		    this.telNo = pactBean.getTelNo();
		    //证件类型
		    this.idType = pactBean.getIdType();
		    //授信协议号码
		    this.authId = pactBean.getAuthId();
		    //银团标志
		    this.ifYt = pactBean.getIfYt();
		    //担保标志
		    this.vouType = pactBean.getVouType();
		    //合同有效状态
		    this.pactSts = pactBean.getPactSts();
		    //合同签订日期
		    this.signDate = pactBean.getSignDate();
		    //贷款投向
		    this.lnTradeNo = pactBean.getLnTradeNo();
		    //合同余额
		    this.pactBal = pactBean.getBal();
		    //贷款业务种类
		    this.loanKind = pactBean.getLoanKind();
		    //业务发生日期(YYYYMMDD HH:MM:SS)
		    this.occDate = pactBean.getOccDate();
		    //贷款种类
		    this.lnType = pactBean.getLnType();
		    //币种
		    this.curNo = pactBean.getCurNo();
		    //贷款性质
		    this.loanType = pactBean.getLoanType();
		    //利率编号
		    this.rateNo = pactBean.getRateNo();
		}
		
		public void  copyVal(DueBean dueBean){
			
			//借据号
		  this.dueNo=dueBean.getDueNo();
		  
		    //借据金额
		  this.dueAmt=dueBean.getDueAmt();
		    //开始日期
		  this.dueBegDate=dueBean.getBegDate();
		    //结束日期
		  this.dueEndDate=dueBean.getEndDate();
		    //宽限天数
		  this.extDays=dueBean.getExtDays();
		    //扣除咨询费
		  this.consFee=dueBean.getConsFee();
		    //咨询费扣除方式
		  this.consFeeType=dueBean.getConsFeeType();
		    //借据状态
		  this.dueState=dueBean.getDueState();
		    //展期标志
		  this.expFlag=dueBean.getExpFlag();
		    //借据余额
		  this.dueBal=dueBean.getBal();
		    //表内欠息(上报)
		  this.innerIntst=dueBean.getInnerIntst();
		    //表外欠息(上报)
		  this.outerIntst=dueBean.getOuterIntst();
		    //五级分类(上报)
		  this.fiveClass=dueBean.getFiveClass();
		    //四级分类(上报)
		  this.fourClass=dueBean.getFourClass();
		    //是否固定日还款
		  this.isFix=dueBean.getIsFix();
		    //固定日还款日
		  this.fixDate=dueBean.getFixDate();
		    // 是否强制计划
		  this.isForce=dueBean.getIsForce();
		    
			
		}
		
		
		
		
		public static void main(String[] args) {
			AcLnMstBean acLnMstBean = new AcLnMstBean();
			PactBean pactBean = new PactBean();
			pactBean.setApplyerBankName("12121");
			acLnMstBean.copyVal(pactBean);
			System.out.println(acLnMstBean.getApplyerBankName());
		}


		public String getAdvaceAmt() {
			return advaceAmt;
		}


		public void setAdvaceAmt(String advaceAmt) {
			this.advaceAmt = advaceAmt;
		}


		public String getBjbalance() {
			return bjbalance;
		}


		public void setBjbalance(String bjbalance) {
			this.bjbalance = bjbalance;
		}


		public String getRatecomebalance() {
			return ratecomebalance;
		}


		public void setRatecomebalance(String ratecomebalance) {
			this.ratecomebalance = ratecomebalance;
		}
		

    
}