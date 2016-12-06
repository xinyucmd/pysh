package com.jiangchuanbanking.redeem.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.jiangchuanbanking.base.domain.BaseEntity;


public  class RedeemEntity extends BaseEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 8250950813769457555L;
	private Integer id ;
	 private String pact_no;
	    private Integer cif_no;
	    private String cif_name;
	    private String sts;
	    private String redem_type;
	    private String redem_amount;
	    private String pay_amt;
	    private String create_date;
	    private String create_op;
	    private String cmt;
	    private String redem_Date;
	    private Integer open_id;
	    private String redem_capital;
	    private String redem_interest;
	    public RedeemEntity()
	    {
	    	this.id=0;
	        this.pact_no="";
	        this.cif_no=0;
	        this.cif_name="";
	        this.sts="";
	        this.redem_type="";
	        this.redem_amount="";
	        this.pay_amt="";
	        this.create_date="";
	        this.create_op="";
	        this.cmt="";
	        this.redem_Date="";
	        this.redem_capital="";
	        this.redem_interest="";
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
	     * 返回cif_name
	     * @return
	     */
	    public String getCif_name()
	    {
	        return this.cif_name;
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
	     * 返回sts
	     * @return
	     */
	    public String getSts()
	    {
	        return this.sts;
	    }
	    /**
	     * 设置sts
	     * @param sts
	     */
	    public void setSts(String sts)
	    {
	        this.sts = sts;
	    }
	    /**
	     * 返回redem_type
	     * @return
	     */
	    public String getRedem_type()
	    {
	        return this.redem_type;
	    }
	    /**
	     * 设置redem_type
	     * @param redem_type
	     */
	    public void setRedem_type(String redem_type)
	    {
	        this.redem_type = redem_type;
	    }
	    /**
	     * 返回redem_amount
	     * @return
	     */
	    public String getRedem_amount()
	    {
	        return this.redem_amount;
	    }
	    /**
	     * 设置redem_amount
	     * @param redem_amount
	     */
	    public void setRedem_amount(String redem_amount)
	    {
	        this.redem_amount = redem_amount;
	    }
	    /**
	     * 返回pay_amt
	     * @return
	     */
	    public String getPay_amt()
	    {
	        return this.pay_amt;
	    }
	    /**
	     * 设置pay_amt
	     * @param pay_amt
	     */
	    public void setPay_amt(String pay_amt)
	    {
	        this.pay_amt = pay_amt;
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
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}
		public Integer getCif_no() {
			return cif_no;
		}
		public void setCif_no(Integer cif_no) {
			this.cif_no = cif_no;
		}
		public String getRedem_Date() {
			return redem_Date;
		}
		public void setRedem_Date(String redem_Date) {
			this.redem_Date = redem_Date;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getOpen_id() {
			return open_id;
		}
		public void setOpen_id(Integer open_id) {
			this.open_id = open_id;
		}
		public String getRedem_capital() {
			return redem_capital;
		}
		public void setRedem_capital(String redem_capital) {
			this.redem_capital = redem_capital;
		}
		public String getRedem_interest() {
			return redem_interest;
		}
		public void setRedem_interest(String redem_interest) {
			this.redem_interest = redem_interest;
		}


}
