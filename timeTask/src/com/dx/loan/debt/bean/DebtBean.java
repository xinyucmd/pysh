/**
 * Copyright (C) DXHM 版权所有
 * 文件名： DebtBean.java
 * 包名： com.dx.loan.debt.bean
 * 说明：
 * @author 乾之轩
 * @date 2012-6-20 下午02:32:36
 * @version V1.0
 */ 
package com.dx.loan.debt.bean;

import java.io.Serializable;


/**
 * 类名： DebtBean
 * 描述： 欠款表实体
 * @author 乾之轩
 * @date 2012-6-20 下午02:32:36
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
    // 客户号
    private String cifNo;
    // 本期还款计划开始日期
    private String begDate;
    // 本期还款计划结束日期
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
     * 方法描述： 按照期号排序
     * @param rateAdjustBean
     * @return
     * int
     * @author 乾之轩
     * @date 2012-6-5 下午04:11:32
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
