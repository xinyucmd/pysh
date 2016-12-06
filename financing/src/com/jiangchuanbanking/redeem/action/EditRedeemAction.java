package com.jiangchuanbanking.redeem.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.account.service.ISubAccountService;

import javassist.expr.NewArray;

import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.financing.domain.BankAccount;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.flowdetails.service.IFinancingDetailsService;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.redeem.domain.RedeemEntity;
import com.jiangchuanbanking.redeem.domain.RedeemPayView;
import com.jiangchuanbanking.redeem.service.IRedeemService;
import com.jiangchuanbanking.subscription.domain.Claims;
import com.jiangchuanbanking.subscription.domain.ListSubscrip;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.subscription.service.ISubscripService;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.BigNumberUtil;
import com.jiangchuanbanking.util.Constant;
import com.jiangchuanbanking.util.DateTimeUtil;
import com.opensymphony.xwork2.Preparable;

public class EditRedeemAction extends BaseEditAction implements Preparable {
	private IRedeemService redeemService;

	private ISubscripService subscripService;

	private IFinancingDetailsService financingDetailsService;

	private ISubAccountService subAccountService;
	private IBaseService baseService;
	private RedeemEntity redeemEntity;
	private PactInfo pactInfo;
	private Customer customer;
	private Integer cusID;
	private String cusName;
	private String prdtNo;
	private String prdtName;
	private Integer id;
	private String pact_no;
	private String account_name;
	private String account_no;
	private String account_bank;
	private String result;
	private List<Claims> claimsList;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private FinancingDetails financingDetails;

	
	private RedeemPayView redeemPayView;
	
	private PlanBean planBean;
	private String startDate;
	
	private String endDate;



	@Override
	public void prepare() throws Exception {

	}

	public String get() throws Exception {
		if (this.getId() != null) {
			redeemEntity = (RedeemEntity) baseService.getEntityById(
					RedeemEntity.class, getId());
			redeemEntity.setRedem_amount(BigNumberUtil.Divide2(
					redeemEntity.getRedem_amount(), "1"));
			redeemEntity.setPay_amt(BigNumberUtil.Divide2(
					redeemEntity.getPay_amt(), "1"));
			pactInfo = (PactInfo) baseService.findByHQL(
					" from PactInfo where pact_no='" + pact_no + "'").get(0);
			customer = pactInfo.getCustomer();
			this.setCusID(customer.getId());
			this.setCusName(customer.getCif_name());
			this.setPrdtNo(pactInfo.getPrdt_no());
			this.setPrdtName(pactInfo.getPrdt_name());
			account_bank = pactInfo.getAccount_bank();
			account_name = pactInfo.getAccount_name();
			account_no = pactInfo.getAccount_no();
		}

		this.initBaseInfo();
		return SUCCESS;
	}

	public String cancelAdvRedem() {
		if (this.getId() != null) {
			redeemEntity = (RedeemEntity) baseService.findByHQL(
					" from  RedeemEntity  where id=" + this.getId() + "")
					.get(0);
			redeemEntity.setSts("31");// 撤销
			baseService.makePersistent(redeemEntity);
			
			PactInfo pactInfo1 = (PactInfo) baseService.findByHQL(" from PactInfo where pact_no='" + pact_no + "'").get(0);
			pactInfo1.setSts("6");
			pactInfo1.setContinueflg("0");
			baseService.makePersistent(pactInfo1);
		}
		return SUCCESS;
	}

	public String getRedemPay() throws Exception {
		if (this.getId() != null) {
			redeemPayView = (RedeemPayView) baseService.getEntityById(
					RedeemPayView.class, getId());
			pactInfo = (PactInfo) baseService.findByHQL(
					" from PactInfo where pact_no='" + pact_no + "'").get(0);
			startDate=DateTimeUtil.getDateString(pactInfo.getStart_date());
			endDate=DateTimeUtil.getDateString(pactInfo.getEnd_date());
			customer = pactInfo.getCustomer();			
			//planBean=(PlanBean) baseService.getEntityById(PlanBean.class, this.getId());
			//if (planBean==null) {
				planBean=(PlanBean) baseService.findByHQL("from PlanBean where pact_no='"+pactInfo.getPact_no()+"' and if_settle='0' order by END_DATE desc").get(0);
			//}
		}

		this.initBaseInfo();
		return SUCCESS;
	}

	public String undoRedem() {
		redeemEntity = (RedeemEntity) baseService.findByHQL(
				" from RedeemEntity  where id ='" + id + "'").get(0);
		redeemEntity.setSts("31");// 申请撤销
		this.getBaseService().makePersistent(redeemEntity);
		pactInfo.setSts("6");
		this.getBaseService().makePersistent(pactInfo);

		return SUCCESS;
	}

	public String cancelFinancing() {
		if (this.getId() != null) {
			pactInfo = (PactInfo) baseService.findByHQL(
					" from PactInfo where id=" + pactInfo.getId() + "")
					.get(0);
			redeemEntity = (RedeemEntity) baseService.findByHQL(
					" from RedeemEntity where id=" + this.getId() + "")
					.get(0);
			if ("1".equals(result)) {// 同意撤销

				
				pactInfo.setSts("7");
				baseService.makePersistent(pactInfo);

				
				redeemEntity.setSts("21");
				baseService.makePersistent(redeemEntity);

			} else {
				pactInfo.setSts("6");
				baseService.makePersistent(pactInfo);

				
				redeemEntity.setSts("31");
				baseService.makePersistent(redeemEntity);

			}
		}
		return SUCCESS;
	}

	public String redemConfirm() {
		// 1.赎回更新
		redeemEntity = (RedeemEntity) baseService.findByHQL(
				" from RedeemEntity  where id ='" + id + "'").get(0);
		redeemEntity.setSts("21");// 确认完成
		this.getBaseService().makePersistent(redeemEntity);
		// 2。合同状态，账户信息更新
		if (redeemEntity.getRedem_type().equals("1")
				|| redeemEntity.getRedem_type().equals("2")) {
			pactInfo.setSts("84");
		} else {// 提前赎回
			pactInfo.setSts("82");
		}
		pactInfo.setAccount_bank(account_bank);
		pactInfo.setAccount_name(account_name);
		pactInfo.setAccount_no(account_no);
		this.getBaseService().makePersistent(pactInfo);
		// 3.银行账户信息更新
		// .....把其他的置成无效，新增一条有效记录
		getBaseService().executeSQL("update WEALTH_BANK_ACCOUNT set sts='1'  ",
				null);
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBank_account_addr(account_bank);
		bankAccount.setBank_account_name(account_name);
		bankAccount.setBank_account_no(account_bank);
		bankAccount.setUpdata_time(dateFormat.format(new Date()));
		bankAccount.setOp_no(this.getLoginUser().getName());
		bankAccount.setSts("0");
		Customer customer = pactInfo.getCustomer();
		bankAccount.setCustomer(customer);
		baseService.makePersistent(bankAccount);

		return SUCCESS;
	}
public String cancelSubcript(){
	//合同状态6
	
	//赎回表状态
	if (this.getId() != null) {
		redeemEntity = (RedeemEntity) baseService.findByHQL(
				" from  RedeemEntity  where id=" + this.getId() + "")
				.get(0);
		redeemEntity.setSts("31");// 撤销
		baseService.makePersistent(redeemEntity);
		
		PactInfo pactInfo1 = (PactInfo) baseService.findByHQL(" from PactInfo where pact_no='" + pact_no + "'").get(0);
		pactInfo1.setSts("6");
		pactInfo1.setContinueflg("0");
		baseService.makePersistent(pactInfo1);
	}
	return SUCCESS;
}

	
	public String redemPay() throws Exception{
		//更新回款计划表
		redeemService.updatePlan(pactInfo.getPact_no(),planBean.getId(),financingDetails);
		//1.更新赎回表
		redeemService.updateRedeem1("23", pactInfo.getPact_no());
		// 2.更新账户
		SubAccount sa = new SubAccount();
		PactInfo pi = (PactInfo) baseService.findByHQL(
				"from PactInfo where PACT_NO='" + pactInfo.getPact_no() + "'")
				.get(0);

		if (redeemPayView.getRedem_type().equals("3")
				|| redeemPayView.getRedem_type().equals("4")) {
			sa = subAccountService.updateSubRedeem(pi, "22", financingDetails);
		} else {// 提前赎回
			sa = subAccountService.updateSubRedeem(pi, "21",financingDetails);

		}
		financingDetailsService.creatDetail(sa, "11",financingDetails);	
		return SUCCESS;		
	}

	public String saveRedemConfirm() {
		return SUCCESS;
	}

	public IRedeemService getRedeemService() {
		return redeemService;
	}

	public void setRedeemService(IRedeemService redeemService) {
		this.redeemService = redeemService;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public RedeemEntity getRedeemEntity() {
		return redeemEntity;
	}

	public void setRedeemEntity(RedeemEntity redeemEntity) {
		this.redeemEntity = redeemEntity;
	}

	public PactInfo getPactInfo() {
		return pactInfo;
	}

	public void setPactInfo(PactInfo pactInfo) {
		this.pactInfo = pactInfo;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getCusID() {
		return cusID;
	}

	public void setCusID(Integer cusID) {
		this.cusID = cusID;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getPrdtNo() {
		return prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public String getPrdtName() {
		return prdtName;
	}

	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getAccount_bank() {
		return account_bank;
	}

	public void setAccount_bank(String account_bank) {
		this.account_bank = account_bank;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getPact_no() {
		return pact_no;
	}

	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}

	public ISubscripService getSubscripService() {
		return subscripService;
	}

	public void setSubscripService(ISubscripService subscripService) {
		this.subscripService = subscripService;
	}

	public IFinancingDetailsService getFinancingDetailsService() {
		return financingDetailsService;
	}

	public void setFinancingDetailsService(
			IFinancingDetailsService financingDetailsService) {
		this.financingDetailsService = financingDetailsService;
	}

	public ISubAccountService getSubAccountService() {
		return subAccountService;
	}

	public void setSubAccountService(ISubAccountService subAccountService) {
		this.subAccountService = subAccountService;
	}

	public FinancingDetails getFinancingDetails() {
		return financingDetails;
	}

	public void setFinancingDetails(FinancingDetails financingDetails) {
		this.financingDetails = financingDetails;
	}


	public RedeemPayView getRedeemPayView() {
		return redeemPayView;
	}

	public void setRedeemPayView(RedeemPayView redeemPayView) {
		this.redeemPayView = redeemPayView;
	}

	public PlanBean getPlanBean() {
		return planBean;
	}

	public void setPlanBean(PlanBean planBean) {
		this.planBean = planBean;
	}
	
	


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Claims> getClaimsList() {
		return claimsList;
	}

	public void setClaimsList(List<Claims> claimsList) {
		this.claimsList = claimsList;
	}

	

}
