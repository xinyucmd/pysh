package com.jiangchuanbanking.subscription.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.util.CommonUtil;
import com.opensymphony.xwork2.Preparable;

public class EditPactInfoAction  extends BaseEditAction implements Preparable{

	private static final long serialVersionUID = 1L;
	private IBaseService<PactInfo> baseService;
	private PactInfo pactInfo;
	private String start_date1="";
	private String end_date1="";
	private String if_wxd;
	private String payment_type;
	private String fund_sources	;
	private String if_continue;
	private String sts;
	private ISelectService selectService;
	
	
	public String get() throws Exception {
		if (this.getId()!=null) {
			pactInfo = baseService.getEntityById(PactInfo.class, this.getId());
			Date start_date = pactInfo.getStart_date();
			Date end_date  =  pactInfo.getEnd_date(); 
			
			 if_wxd = pactInfo.getIf_wxd();
			 if_wxd = selectService.getOpCnName("YES_NO", if_wxd);
			
			 payment_type=	CommonUtil.fromNullToEmpty(pactInfo.getPayment_type());
			 payment_type = selectService.getOpCnName("PAYMENT_TYPE", payment_type);
			 
			 fund_sources=	CommonUtil.fromNullToEmpty(pactInfo.getFund_sources());
			 fund_sources = selectService.getOpCnName("FUNDS_FROM", fund_sources);		
			 
			 if_continue=	CommonUtil.fromNullToEmpty(pactInfo.getIf_continue());
			 if_continue = selectService.getOpCnName("YES_NO", if_continue);
					
			 sts =	CommonUtil.fromNullToEmpty(pactInfo.getSts());
			 sts	= selectService.getOpCnName("PACT_STS", sts);			

			 if(start_date==null){
		    	 start_date1 = "";
		    }else{
		    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				 start_date1 = CommonUtil.fromNullToEmpty(formatter
						.format(start_date));
				 end_date1 = CommonUtil.fromNullToEmpty(formatter
						.format(end_date));
		    }
		}
		return SUCCESS;
	}
	public ISelectService getSelectService() {
		return selectService;
	}

	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}

	public String getStart_date1() {
		return start_date1;
	}

	public void setStart_date1(String start_date1) {
		this.start_date1 = start_date1;
	}

	public String getEnd_date1() {
		return end_date1;
	}

	public void setEnd_date1(String end_date1) {
		this.end_date1 = end_date1;
	}

	public PactInfo getPactInfo() {
		return pactInfo;
	}

	public void setPactInfo(PactInfo pactInfo) {
		this.pactInfo = pactInfo;
	}

	public IBaseService<PactInfo> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<PactInfo> baseService) {
		this.baseService = baseService;
	}

	public String getIf_wxd() {
		return if_wxd;
	}
	public void setIf_wxd(String if_wxd) {
		this.if_wxd = if_wxd;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getFund_sources() {
		return fund_sources;
	}
	public void setFund_sources(String fund_sources) {
		this.fund_sources = fund_sources;
	}
	public String getIf_continue() {
		return if_continue;
	}
	public void setIf_continue(String if_continue) {
		this.if_continue = if_continue;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	@Override
	public void prepare() throws Exception {
	
	}
	
	
}
