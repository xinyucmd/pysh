package com.jiangchuanbanking.alert.domain;

import java.io.Serializable;
import java.util.Date;

public class SmsData  implements Serializable, Cloneable {
	private static final long serialVersionUID = 8250950813769457555L;
	private Integer id;
    private String alert_method;
    private String alert_type;
    private String mobile;
    private String alert_content;
    private String send_sts;
    private String create_op;
    private String create_date;
    private String cmt;
    private String cif_no ;
    private String cif_name ;
    private String pact_no ;
    
    private String rate;
    private String pact_amt;
    private String payment_type;
    private Date beg_date;
    private Date end_date;
    private String term;
    public SmsData()
    {
        this.id=0;
        this.alert_method="";
        this.alert_type="";
        this.mobile="";
        this.alert_content="";
        this.send_sts="";
        this.create_op="";
        this.create_date="";
        this.cmt="";
        this.cif_name="";
        this.cif_no="";
        this.pact_no="";
    }
   
    /**
     * 返回alert_method
     * @return
     */
    public String getAlert_method()
    {
        return this.alert_method;
    }
    /**
     * 设置alert_method
     * @param alert_method
     */
    public void setAlert_method(String alert_method)
    {
        this.alert_method = alert_method;
    }
    /**
     * 返回alert_type
     * @return
     */
    public String getAlert_type()
    {
        return this.alert_type;
    }
    /**
     * 设置alert_type
     * @param alert_type
     */
    public void setAlert_type(String alert_type)
    {
        this.alert_type = alert_type;
    }
    /**
     * 返回mobile
     * @return
     */
    public String getMobile()
    {
        return this.mobile;
    }
    /**
     * 设置mobile
     * @param mobile
     */
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    /**
     * 返回alert_content
     * @return
     */
    public String getAlert_content()
    {
        return this.alert_content;
    }
    /**
     * 设置alert_content
     * @param alert_content
     */
    public void setAlert_content(String alert_content)
    {
        this.alert_content = alert_content;
    }
    /**
     * 返回send_sts
     * @return
     */
    public String getSend_sts()
    {
        return this.send_sts;
    }
    /**
     * 设置send_sts
     * @param send_sts
     */
    public void setSend_sts(String send_sts)
    {
        this.send_sts = send_sts;
    }
    /**
     * 返回create_op
     * @return
     */
    public String getCreate_op()
    {
        return this.create_op;
    }
    /**
     * 设置create_op
     * @param create_op
     */
    public void setCreate_op(String create_op)
    {
        this.create_op = create_op;
    }
    /**
     * 返回create_date
     * @return
     */
    public String getCreate_date()
    {
        return this.create_date;
    }
    /**
     * 设置create_date
     * @param create_date
     */
    public void setCreate_date(String create_date)
    {
        this.create_date = create_date;
    }
    /**
     * 返回cmt
     * @return
     */
    public String getCmt()
    {
        return this.cmt;
    }
    /**
     * 设置cmt
     * @param cmt
     */
    public void setCmt(String cmt)
    {
        this.cmt = cmt;
    }
	public String getCif_no() {
		return cif_no;
	}
	public void setCif_no(String cif_no) {
		this.cif_no = cif_no;
	}
	public String getCif_name() {
		return cif_name;
	}
	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}
	public String getPact_no() {
		return pact_no;
	}
	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPact_amt() {
		return pact_amt;
	}
	public void setPact_amt(String pact_amt) {
		this.pact_amt = pact_amt;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Date getBeg_date() {
		return beg_date;
	}
	public void setBeg_date(Date beg_date) {
		this.beg_date = beg_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
