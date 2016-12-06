package com.jiangchuanbanking.plan.domain;

import java.io.Serializable;
import java.util.Date;


public class PlanBean  implements Serializable, Cloneable  , Comparable<PlanBean> {
	private static final long serialVersionUID = 8250950813769457555L;
    private String pact_no;
    private String term;
    private String beg_date;
    private String end_date;
    private String if_settle;
    private String settle_date;
    private String if_alarm;
    private String should_amt;
    private String return_amt;
    private Integer id;
    private String interest;
    private String capital;
    private String due_bal;
    public PlanBean()
    {
        this.pact_no="";
        this.term="";
        this.beg_date="";
        this.end_date="";
        this.if_settle="";
        this.settle_date="";
        this.if_alarm="";
        this.should_amt="";
        this.return_amt="";
        this.id=0;
        this.interest="";
        this.capital="";
    }
    /**
     * 返回pact_no
     * @return
     */
    public String getPact_no()
    {
        return this.pact_no;
    }
    /**
     * 设置pact_no
     * @param pact_no
     */
    public void setPact_no(String pact_no)
    {
        this.pact_no = pact_no;
    }
    /**
     * 返回term
     * @return
     */
    public String getTerm()
    {
        return this.term;
    }
    /**
     * 设置term
     * @param term
     */
    public void setTerm(String term)
    {
        this.term = term;
    }
    /**
     * 返回beg_date
     * @return
     */
    public String getBeg_date()
    {
        return this.beg_date;
    }
    /**
     * 设置beg_date
     * @param beg_date
     */
    public void setBeg_date(String beg_date)
    {
        this.beg_date = beg_date;
    }
    /**
     * 返回end_date
     * @return
     */
    public String getEnd_date()
    {
        return this.end_date;
    }
    /**
     * 设置end_date
     * @param end_date
     */
    public void setEnd_date(String end_date)
    {
        this.end_date = end_date;
    }
    /**
     * 返回if_settle
     * @return
     */
    public String getIf_settle()
    {
        return this.if_settle;
    }
    /**
     * 设置if_settle
     * @param if_settle
     */
    public void setIf_settle(String if_settle)
    {
        this.if_settle = if_settle;
    }
    /**
     * 返回settle_date
     * @return
     */
    public String getSettle_date()
    {
        return this.settle_date;
    }
    /**
     * 设置settle_date
     * @param settle_date
     */
    public void setSettle_date(String settle_date)
    {
        this.settle_date = settle_date;
    }
    /**
     * 返回if_alarm
     * @return
     */
    public String getIf_alarm()
    {
        return this.if_alarm;
    }
    /**
     * 设置if_alarm
     * @param if_alarm
     */
    public void setIf_alarm(String if_alarm)
    {
        this.if_alarm = if_alarm;
    }
    /**
     * 返回should_amt
     * @return
     */
    public String getShould_amt()
    {
        return this.should_amt;
    }
    /**
     * 设置should_amt
     * @param should_amt
     */
    public void setShould_amt(String should_amt)
    {
        this.should_amt = should_amt;
    }
    /**
     * 返回return_amt
     * @return
     */
    public String getReturn_amt()
    {
        return this.return_amt;
    }
    /**
     * 设置return_amt
     * @param return_amt
     */
    public void setReturn_amt(String return_amt)
    {
        this.return_amt = return_amt;
    }
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public int compareTo(PlanBean arg0) {
		int temoNo1 = Integer.parseInt(this.getTerm());
		int temoNo2 = Integer.parseInt(arg0.getTerm());
		if (temoNo1 > temoNo2) {
			return 1;
		} else {
			return 0;
		}
	}
	public String getDue_bal() {
		return due_bal;
	}
	public void setDue_bal(String due_bal) {
		this.due_bal = due_bal;
	}

}
