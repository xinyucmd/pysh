package com.dx.payment.bean;

import hirondelle.date4j.DateTime;

import java.io.Serializable;

public class RatedayBBean  implements Serializable ,Comparable<RatedayBBean> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1754046107074698349L;
	//����
    private String id;
    //��ݺ�
    private String dueNo;
    //�ں�
    private String termNo;
    //��Ϣ��������
    private String occureDate;
    //��Ϣ���
    private String interest;
    //����
    private String capital;
    //������Ϣ
    private String overInterest;
    //������Ϣ
    private String cmpdInterest;
    //״̬
    private String state;
    //��ͬ��
    private String pactNo;
    // ��������
    private String accFee;
    // ���㷣Ϣ���� ������Ϣ��
    private String baseInterest;
	// ���ü��㷣Ϣ�Ļ��� �������Ѻ�
    private String  baseAccFee;
    
    private String cifName;
    // ���շ�
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