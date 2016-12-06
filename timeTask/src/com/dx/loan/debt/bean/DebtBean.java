/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� DebtBean.java
 * ������ com.dx.loan.debt.bean
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-20 ����02:32:36
 * @version V1.0
 */ 
package com.dx.loan.debt.bean;

import java.io.Serializable;


/**
 * ������ DebtBean
 * ������ Ƿ���ʵ��
 * @author Ǭ֮��
 * @date 2012-6-20 ����02:32:36
 *
 *
 */
public class DebtBean  implements Serializable ,Comparable<DebtBean>{
	
	private static final long serialVersionUID = 465788483854122574L;
	private String dueNo;
    private String termNo;
    private String debCapital;
    private String debIntst;
    private String debCmpdintst;
    private String debOverintst;
    private String debAccFee;
    // �ͻ���
    private String cifNo;
    // ���ڻ���ƻ���ʼ����
    private String begDate;
    // ���ڻ���ƻ���������
    private String endDate;
    
    
    public String getCifNo() {
		return cifNo;
	}
	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
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
	public String getDueNo() {
        return dueNo;
    }
    public void setDueNo(String dueNo) {
        this.dueNo = dueNo ;
    }
    public String getTermNo() {
        return termNo;
    }
    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }
    public String getDebCapital() {
        return debCapital;
    }

    public void setDebCapital(String debCapital) {
        this.debCapital = debCapital;
    }
    public String getDebIntst() {
        return debIntst;
    }
    public void setDebIntst(String debIntst) {
        this.debIntst = debIntst;
    }
    public String getDebCmpdintst() {
        return debCmpdintst;
    }
    public void setDebCmpdintst(String debCmpdintst) {
        this.debCmpdintst = debCmpdintst;
    }
    public String getDebOverintst() {
        return debOverintst;
    }

    public void setDebOverintst(String debOverintst) {
        this.debOverintst = debOverintst;
    }
    public String getDebAccFee() {
        return debAccFee;
    }
    public void setDebAccFee(String debAccFee) {
        this.debAccFee = debAccFee;
    }
    
    
    /**
     * 
     * ���������� �����ں�����
     * @param rateAdjustBean
     * @return
     * int
     * @author Ǭ֮��
     * @date 2012-6-5 ����04:11:32
     */
	public int compareTo(DebtBean debtBean) {
		int temoNo1 = Integer.parseInt(this.getTermNo());
		int temoNo2 = Integer.parseInt(debtBean.getTermNo());
		if(temoNo1>temoNo2){
			return 1;
		}else{
			return 0;
		}
	}
	

}
