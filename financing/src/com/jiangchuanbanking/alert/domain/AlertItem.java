package com.jiangchuanbanking.alert.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.jiangchuanbanking.financing.domain.TargetList;
import com.jiangchuanbanking.system.domain.User;

public  class AlertItem implements Serializable, Cloneable {

	private static final long serialVersionUID = 8250950813769457555L;


    private Integer id;
    private String alert_method;
    private String alert_type;
    private String adv_days;
    private String alert_content;
    private String del_flg;
    private String create_op;
    private String create_date;
    private String cmt;
    private String type;
    public AlertItem()
    {
        this.id=0;
        this.alert_method="";
        this.alert_type="";
        this.adv_days="";
        this.alert_content="";
        this.del_flg="";
        this.create_op="";
        this.create_date="";
        this.cmt="";
        this.type="";
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
     * 返回adv_days
     * @return
     */
    public String getAdv_days()
    {
        return this.adv_days;
    }
    /**
     * 设置adv_days
     * @param adv_days
     */
    public void setAdv_days(String adv_days)
    {
        this.adv_days = adv_days;
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
     * 返回del_flg
     * @return
     */
    public String getDel_flg()
    {
        return this.del_flg;
    }
    /**
     * 设置del_flg
     * @param del_flg
     */
    public void setDel_flg(String del_flg)
    {
        this.del_flg = del_flg;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
