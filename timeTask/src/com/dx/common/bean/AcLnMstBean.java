package com.dx.common.bean;

import com.dx.common.util.StringUtil;
import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.repay.bean.DueBean;
/**
 * 
 * ������ LoanAcLnMst
 * ������ �������ļ�
 * @author sll
 * @date May 15, 2012 10:53:03 AM
 * 
 *
 */
public class AcLnMstBean {
	//�������ļ�Id
    private String id;
    //��ݺ�
    private String dueNo;
    //��ͬ��
    private String pactNo;
    //��ݽ��
    private String dueAmt;
    //��ʼ����
    private String dueBegDate;
    //��������
    private String dueEndDate;
    //�ͻ���
    private String cifNo;
    //��������
    private String extDays;
    //�۳���ѯ��
    private String consFee;
    //��ѯ�ѿ۳���ʽ
    private String consFeeType;
    //���״̬
    private String dueState;
    //չ�ڱ�־
    private String expFlag;
    //������
    private String dueBal;
	//�˻������/��(�ϱ�)
    private String accFee;
    //�˻������/����(�ϱ�)
    private String accTolFee;
    //����ǷϢ(�ϱ�)
    private String innerIntst;
    //����ǷϢ(�ϱ�)
    private String outerIntst;
    //�弶����(�ϱ�)
    private String fiveClass;
    //�ļ�����(�ϱ�)
    private String fourClass;
    //��ͬ���
    private String pactAmt;
    //��ͬ��ʼ����
    private String pactBegDate;
    //��ͬ��������
    private String pactEndDate;
    //��ͬ������
    private String termMon;
    //��ͬ������
    private String termDay;
    //��ͬ����
    private String lnRate;
    //��ͬ���ʸ�������
    private String rateFloat;

    //չ������
    private String expRate;
    //չ�ڸ�������
    private String expRateFloat;
    //������������
    private String cmpdRateFloat;
    //��������
    private String cmpdRate;
    //���ڸ�������
    private String overRateFloat;
    //��������
    private String overRate;
	//��Ϣ��ʽ
    private String rateWay;
    //���ʽ
    private String returnMethod;
    //ҵ��Ʒ�ֱ��
    private String pactKind;
    //��������
    private String occurType;
    //�̶���Ϣ���
    private String fixrateInterest;
    //�̶���Ϣ�Ƿ���ǰ��ȡ
    private String ispreFixrate;
    //��ѯ��
    private String consultAmt;
    //��������ʽ
    private String mainGuaran;
    //��������
    private String guaranType;
    //��ͬ����
    private String pactNature;
    //������;
    private String pactUse;
    //��;˵��
    private String pactUseDesc;
    //�����˿�����������
    private String applyerBankName;
    //�������˺�
    private String applyerBankNo;
    //�տ��˿�����������
    private String receiverBankName;
    //�տ����˺�
    private String receiverBankNo;
    //�ͻ�����
    private String telNo;
    //֤������
    private String idType;
    //����Э�����
    private String authId;
    //���ű�־
    private String ifYt;
    //������־
    private String vouType;
    //��ͬ��Ч״̬
    private String pactSts;
    //��ͬǩ������
    private String signDate;
    //����Ͷ��
    private String lnTradeNo;
    //��ͬ���
    private String pactBal;
	//����ҵ������
    private String loanKind;
    //ҵ��������(YYYYMMDD HH:MM:SS)
    private String occDate;
    //��������
    private String lnType;
    //����
    private String curNo;
    //��������
    private String loanType;
    //���ʱ��
    private String rateNo;
    // �ϴλ�������
    private String lastReturnDate;
    // �������ʵ�����־(0û�е����� 1������)
    private String  PerRateChe;
    // �Ƿ�ǿ�Ƽƻ�
    private String isForce;
    //�Ƿ�̶��ջ���
    private String isFix;
	//�̶��ջ�����
    private String  fixDate;
    // �Ƿ��Զ����� 0 ������ 1 ����
    private String isDelay;
    // Ԥ��֧����ʽ 
    private String isAdvance;
    // �Ƿ�̶��ջ��� 0 ���ǹ̶�������  1 �̶��ջ���  
    private String isFixDate;
    // ��Լ��֤���
    private String perfAmount;
    // ��Լ��֤������
    private String perfAmountPerc;
    
    // ��ǰ������
    private String  advaceAmt;
    
    private String planflag;
    // ��ʷ��¼ʹ���Ѿ������
    private String hisAmt;
    //�������
    private String bjbalance;
    
    
    //��Ϣ����
    
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
		 * ���������� ����ͬ��Ϣ���Ƶ��������ļ�ʵ�� 
		 * @param pactBean
		 * void
		 * @author Ǭ֮��
		 * @date 2012-5-30 ����05:04:01
		 */
		public void copyVal(PactBean pactBean){
			//��ͬ��
		    this.pactNo = pactBean.getPactNo();
		    //�ͻ���
		    this.cifNo = pactBean.getCifNo();
		    //��ͬ���
		    this.pactAmt = pactBean.getPactAmt();
		    //��ͬ��ʼ����
		    this.pactBegDate = pactBean.getBegDate();
		    //��ͬ��������
		    this.pactEndDate = pactBean.getEndDate();
		    //��ͬ������
		    this.termMon = pactBean.getTermMon();
		    //��ͬ������
		    this.termDay = pactBean.getTermDay();
		    //��ͬ����
		    this.lnRate = pactBean.getLnRate();
		    //��ͬ���ʸ�������
		    this.rateFloat = pactBean.getRateFloat();
		   
		    //չ������
		    this.expRate = pactBean.getExpRate();
		    //չ�ڸ�������
		    this.expRateFloat = pactBean.getExpRateFloat();
		    //������������
		    this.cmpdRateFloat = pactBean.getCmpdRateFloat();
		    //��������
		    this.cmpdRate = pactBean.getCmpdRate();
		    //���ڸ�������
		    this.overRateFloat = pactBean.getOverRateFloat();
		    //��������
		    this.overRate = pactBean.getOverRate();
			//��Ϣ��ʽ
		    this.rateWay = pactBean.getRateWay();
		    //���ʽ
		    this.returnMethod = pactBean.getReturnMethod();
		    //ҵ��Ʒ�ֱ��
		    this.pactKind = pactBean.getPactKind();
		    //��������
		    this.occurType = pactBean.getOccurType();
		    //�̶���Ϣ���
		    this.fixrateInterest = pactBean.getFixrateInterest();
		    //�̶���Ϣ�Ƿ���ǰ��ȡ
		    this.ispreFixrate = pactBean.getIspreFixrate();
		    //��ѯ��
		    this.consultAmt = pactBean.getConsultAmt();
		    //��������ʽ
		    this.mainGuaran = pactBean.getMainGuaran();
		    //��������
		    this.guaranType = pactBean.getGuaranType();
		    //��ͬ����
		    this.pactNature = pactBean.getPactNature();
		    //������;
		    this.pactUse = pactBean.getPactUse();
		    //��;˵��
		    this.pactUseDesc = pactBean.getPactUseDesc();
		    //�����˿�����������
		    this.applyerBankName = pactBean.getApplyerBankName();
		    //�������˺�
		    this.applyerBankNo = pactBean.getApplyerBankNo();
		    //�տ��˿�����������
		    this.receiverBankName = pactBean.getReceiverBankName();
		    //�տ����˺�
		    this.receiverBankNo = pactBean.getReceiverBankNo();
		    //�ͻ�����
		    this.telNo = pactBean.getTelNo();
		    //֤������
		    this.idType = pactBean.getIdType();
		    //����Э�����
		    this.authId = pactBean.getAuthId();
		    //���ű�־
		    this.ifYt = pactBean.getIfYt();
		    //������־
		    this.vouType = pactBean.getVouType();
		    //��ͬ��Ч״̬
		    this.pactSts = pactBean.getPactSts();
		    //��ͬǩ������
		    this.signDate = pactBean.getSignDate();
		    //����Ͷ��
		    this.lnTradeNo = pactBean.getLnTradeNo();
		    //��ͬ���
		    this.pactBal = pactBean.getBal();
		    //����ҵ������
		    this.loanKind = pactBean.getLoanKind();
		    //ҵ��������(YYYYMMDD HH:MM:SS)
		    this.occDate = pactBean.getOccDate();
		    //��������
		    this.lnType = pactBean.getLnType();
		    //����
		    this.curNo = pactBean.getCurNo();
		    //��������
		    this.loanType = pactBean.getLoanType();
		    //���ʱ��
		    this.rateNo = pactBean.getRateNo();
		}
		
		public void  copyVal(DueBean dueBean){
			
			//��ݺ�
		  this.dueNo=dueBean.getDueNo();
		  
		    //��ݽ��
		  this.dueAmt=dueBean.getDueAmt();
		    //��ʼ����
		  this.dueBegDate=dueBean.getBegDate();
		    //��������
		  this.dueEndDate=dueBean.getEndDate();
		    //��������
		  this.extDays=dueBean.getExtDays();
		    //�۳���ѯ��
		  this.consFee=dueBean.getConsFee();
		    //��ѯ�ѿ۳���ʽ
		  this.consFeeType=dueBean.getConsFeeType();
		    //���״̬
		  this.dueState=dueBean.getDueState();
		    //չ�ڱ�־
		  this.expFlag=dueBean.getExpFlag();
		    //������
		  this.dueBal=dueBean.getBal();
		    //����ǷϢ(�ϱ�)
		  this.innerIntst=dueBean.getInnerIntst();
		    //����ǷϢ(�ϱ�)
		  this.outerIntst=dueBean.getOuterIntst();
		    //�弶����(�ϱ�)
		  this.fiveClass=dueBean.getFiveClass();
		    //�ļ�����(�ϱ�)
		  this.fourClass=dueBean.getFourClass();
		    //�Ƿ�̶��ջ���
		  this.isFix=dueBean.getIsFix();
		    //�̶��ջ�����
		  this.fixDate=dueBean.getFixDate();
		    // �Ƿ�ǿ�Ƽƻ�
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