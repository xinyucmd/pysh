package com.dx.loan.plan.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.dx.common.util.StringUtil;
import com.google.gson.Gson;


/**
 * 
 * 类名： PlanBean
 * 描述： 还款计划类
 * @author sll
 * @date May 14, 2012 9:26:53 AM
 * 
 *
 */
public class PlanBean  implements Serializable ,Comparable<PlanBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 577542143851866200L;
	//合同号
    private String pactNo;
    //借据号
    private String dueNo;
    //客户号
    private String cifNo;
    //借据金额
    private String dueAmt;
    //期数
    private String termNo;
    //应还本金
    private String returnCapital;
    //应还利息
    private String returnInterest;
    //开始日期
    private String begDate;
    //结束日期
    private String endDate;
    //该期还款计划状态(0 正常 1 逾期 2 还完)
    private String state;
    //借据余额(借据金额-累计还款计划本金)
    private String dueBal;
    // 本息合计
    private String total;
    // 账户管理费
    private String  accFee;
    // 是否有欠款 0没有 1有
    private String  isDebt;
    // 履约保证金
    private  String perfAmount;
    
    
    
      
    
    public String getPerfAmount() {
		return perfAmount;
	}

	public void setPerfAmount(String perfAmount) {
		this.perfAmount = perfAmount;
	}

	public String getIsDebt() {
		return isDebt;
	}

	public void setIsDebt(String isDebt) {
		this.isDebt = isDebt;
	}

	public String getAccFee() {
		return accFee;
	}

	public void setAccFee(String accFee) {
		this.accFee = accFee;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public PlanBean(){};
    
    public String getPactNo() {
        return pactNo;
    }

    public void setPactNo(String pactNo) {
        this.pactNo = pactNo;
    }

    public String getDueNo() {
		return dueNo;
	}

	public void setDueNo(String dueNo) {
		this.dueNo = dueNo;
	}

	public String getCifNo() {
		return cifNo;
	}

	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}

	public String getDueAmt() {
		return dueAmt;
	}

	public void setDueAmt(String dueAmt) {
		this.dueAmt = dueAmt;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getReturnCapital() {
        return returnCapital;
    }

    public void setReturnCapital(String returnCapital) {
        this.returnCapital = returnCapital;
    }

    public String getReturnInterest() {
        return returnInterest;
    }

    public void setReturnInterest(String returnInterest) {
        this.returnInterest = returnInterest;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	public String getDueBal() {
		return dueBal;
	}

	public void setDueBal(String dueBal) {
		this.dueBal = dueBal;
	}
	
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
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
	public int compareTo(PlanBean planBean) {
		int temoNo1 = Integer.parseInt(this.getTermNo());
		int temoNo2 = Integer.parseInt(planBean.getTermNo());
		if(temoNo1>temoNo2){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 
	 * 方法描述： 克隆当前的实体
	 * @return
	 * @author 乾之轩
	 * @date 2012-6-9 上午11:01:18
	 */
	public PlanBean clone(){
		PlanBean p = new PlanBean();
		p.setBegDate(this.begDate);
		p.setCifNo(this.cifNo);
		p.setDueAmt(this.dueAmt);
		p.setDueBal(this.dueBal);
		p.setDueNo(this.dueNo);
		p.setEndDate(this.endDate);
		p.setPactNo(this.pactNo);
		p.setReturnCapital(this.returnCapital);
		p.setReturnInterest(this.returnInterest);
		p.setState(this.state);
		p.setTermNo(this.termNo);
		p.setTotal(this.total);
		return p;
	}
	
	
	public static void main(String[] args) {
		List<PlanBean> planList = new ArrayList<PlanBean>();
		PlanBean  planBean1 = new PlanBean();
		planBean1.setAccFee("100");
		planBean1.setBegDate("2012-09-10");
		
		PlanBean  planBean2 = new PlanBean(); 
		planBean2.setAccFee("200");
		planBean2.setBegDate("2012-12-10");
		
		planList.add(planBean1);
		planList.add(planBean2);
		Gson gson = new Gson();
	System.out.print(gson.toJson(planList).toString())	;
	System.out.print(gson.toJson(planBean1).toString())	;
		
	}
	               
	
}