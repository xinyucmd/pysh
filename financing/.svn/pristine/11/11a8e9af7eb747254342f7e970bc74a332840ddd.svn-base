package com.jiangchuanbanking.account.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.jiangchuanbanking.base.domain.BaseEntity;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.prod.domain.ProdChargePolicy;

/**
 * 数据库表WEALTH_MAIN_ACCOUNT所对应的实体类
 * @author 
 *
 */
public class MainAccount  extends BaseEntity implements Serializable
{
	private static final long serialVersionUID = 8250950813769457555L;
    private String account_no;
    private Integer cif_no;
    private String cif_name;
    private String account_type;
    private String status;
    private String open_date;
    private String open_op;
    private String close_date;
    private String close_op;
    private String cmt;
    private Integer open_id;
    private Set<SubAccount> subAccountLists = new HashSet<SubAccount>();
    private Customer customer;
    public MainAccount()
    {
        this.account_no="";
        this.cif_no=null;
        this.cif_name="";
        this.account_type="";
        this.status="";
        this.open_date="";
        this.open_op="";
        this.close_date="";
        this.close_op="";
        this.cmt="";
    }
	/**
     * 返回account_no
     * @return
     */
    public String getAccount_no()
    {
        return this.account_no;
    }
    /**
     * 设置account_no
     * @param account_no
     */
    public void setAccount_no(String account_no)
    {
        this.account_no = account_no;
    }
    /**
     * 返回cif_no
     * @return
     */
   
    /**
     * 返回cif_name
     * @return
     */
    public String getCif_name()
    {
        return this.cif_name;
    }
    public Integer getCif_no() {
		return cif_no;
	}
	public void setCif_no(Integer cif_no) {
		this.cif_no = cif_no;
	}
	/**
     * 设置cif_name
     * @param cif_name
     */
    public void setCif_name(String cif_name)
    {
        this.cif_name = cif_name;
    }
    /**
     * 返回account_type
     * @return
     */
    public String getAccount_type()
    {
        return this.account_type;
    }
    /**
     * 设置account_type
     * @param account_type
     */
    public void setAccount_type(String account_type)
    {
        this.account_type = account_type;
    }
    /**
     * 返回status
     * @return
     */
    public String getStatus()
    {
        return this.status;
    }
    /**
     * 设置status
     * @param status
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
    /**
     * 返回open_date
     * @return
     */
    public String getOpen_date()
    {
        return this.open_date;
    }
    /**
     * 设置open_date
     * @param open_date
     */
    public void setOpen_date(String open_date)
    {
        this.open_date = open_date;
    }
    /**
     * 返回open_op
     * @return
     */
    public String getOpen_op()
    {
        return this.open_op;
    }
    /**
     * 设置open_op
     * @param open_op
     */
    public void setOpen_op(String open_op)
    {
        this.open_op = open_op;
    }
    /**
     * 返回close_date
     * @return
     */
    public String getClose_date()
    {
        return this.close_date;
    }
    /**
     * 设置close_date
     * @param close_date
     */
    public void setClose_date(String close_date)
    {
        this.close_date = close_date;
    }
    /**
     * 返回close_op
     * @return
     */
    public String getClose_op()
    {
        return this.close_op;
    }
    /**
     * 设置close_op
     * @param close_op
     */
    public void setClose_op(String close_op)
    {
        this.close_op = close_op;
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
	public Set<SubAccount> getSubAccountLists() {
		return subAccountLists;
	}
	public void setSubAccountLists(Set<SubAccount> subAccountLists) {
		this.subAccountLists = subAccountLists;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Integer getOpen_id() {
		return open_id;
	}
	public void setOpen_id(Integer open_id) {
		this.open_id = open_id;
	}
	
}

