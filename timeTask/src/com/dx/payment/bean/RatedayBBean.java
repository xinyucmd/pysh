package com.dx.payment.bean;

import hirondelle.date4j.DateTime;

import java.io.Serializable;

public class RatedayBBean  implements Serializable ,Comparable<RatedayBBean> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1754046107074698349L;
	//主键
    private String id;
    //借据号
    private String dueNo;
    //期号
    private String termNo;
    //利息产生日期
    private String occureDate;
    //利息金额
    private String interest;
    //本金
    private String capital;
    //逾期利息
    private String overInterest;
    //复利利息
    private String cmpdInterest;
    //状态
    private String state;
    //合同号
    private String pactNo;
    // 其他费用
    private String accFee;
    // 计算罚息基数 逾期利息和
    private String baseInterest;
	// 设置计算罚息的基数 贷后管理费和
    private String  baseAccFee;
    
    private String cifName;
    // 催收费
    private String csf;

	public String getCifName() {
		return cifName;
	}

	public void setCifName(String cifName) {
		this.cifName = cifName;
	}

	public String getBaseInterest() {
		return baseInterest;
	}

	public void setBaseInterest(String baseInterest) {
		this.baseInterest = baseInterest;
	}

	public String getBaseAccFee() {
		return baseAccFee;
	}

	public void setBaseAccFee(String baseAccFee) {
		this.baseAccFee = baseAccFee;
	}

	public String getAccFee() {
		return accFee;
	}

	public void setAccFee(String accFee) {
		this.accFee = accFee;
	}

	public RatedayBBean(){};
    
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

    public String getTermNo() {
        return termNo;
    }

    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    public String getOccureDate() {
        return occureDate;
    }

    public void setOccureDate(String occureDate) {
        this.occureDate = occureDate;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getOverInterest() {
        return overInterest;
    }

    public void setOverInterest(String overInterest) {
        this.overInterest = overInterest;
    }

    public String getCmpdInterest() {
        return cmpdInterest;
    }

    public void setCmpdInterest(String cmpdInterest) {
        this.cmpdInterest = cmpdInterest;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPactNo() {
        return pactNo;
    }

    public void setPactNo(String pactNo) {
        this.pactNo = pactNo;
    }

	public String getCsf() {
		return csf;
	}

	public void setCsf(String csf) {
		this.csf = csf;
	}

	public int compareTo(RatedayBBean repayBean) {
		DateTime dateTime1 = new DateTime(this.getOccureDate());
		DateTime dateTime2 = new DateTime(repayBean.getOccureDate());
		if(dateTime1.gt(dateTime2)){
			return 1;
		}else{
			return 0;
		}
	}
}