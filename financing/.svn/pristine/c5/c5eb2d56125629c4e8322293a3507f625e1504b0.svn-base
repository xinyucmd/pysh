package com.jiangchuanbanking.financing.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;

import com.jiangchuanbanking.financing.domain.BankAccount;
import com.jiangchuanbanking.util.CommonUtil;
import com.opensymphony.xwork2.Preparable;

public class EditBankAccountAction extends BaseEditAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IBaseService<BankAccount> baseService;
	private Integer cif_id;
	private String cif_name;
	private BankAccount bankAccount;
	
	public String get() throws Exception {
		String pageSpase=null;
		if (this.getId() != null) {
			bankAccount = baseService.getEntityById(BankAccount.class,
					this.getId());
			this.cif_id = bankAccount.getCustomer().getId();
			this.cif_name = bankAccount.getCustomer().getCif_name();
			pageSpase = "editBankAccountPage";
		}else{
			pageSpase = "editUpataBankAccountPage";
		}
		return pageSpase;
	}

	public String save() throws Exception {
		Date date = new Date();
		String sysTime = new SimpleDateFormat("yy/MM/dd").format(date).toString();
		BankAccount originalBankAccount = null;
		if (bankAccount.getBank_account_id() == null) {
			String userName  =	CommonUtil.getLoginUserName();
			Integer userId   =  CommonUtil.getLoginUserId();
			getBaseService().executeSQL("update WEALTH_BANK_ACCOUNT set sts='1' where sts='0' and id = "+bankAccount.getCustomer().getId()+"", null);
			bankAccount.setUpdata_time(sysTime);
			bankAccount.setOp_no(userName);
			bankAccount.setOpen_id(userId);
			bankAccount = getBaseService().makePersistent(bankAccount);
		}else {
			
			//getBaseService().executeSQL("update WEALTH_BANK_ACCOUNT set sts='2' where sts='1'", null);
			originalBankAccount = baseService.getEntityById(BankAccount.class,
					bankAccount.getBank_account_id());
			bankAccount.setCustomer(originalBankAccount.getCustomer());
			bankAccount.setUpdata_time(sysTime);
			bankAccount = getBaseService().makePersistent(bankAccount);
		}
		return SUCCESS;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public IBaseService<BankAccount> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<BankAccount> baseService) {
		this.baseService = baseService;
	}
	public String getCif_name() {
		return cif_name;
	}

	public void setCif_name(String cif_name) {
		this.cif_name = cif_name;
	}

	@Override
	public void prepare() throws Exception {

	}

	public Integer getCif_id() {
		return cif_id;
	}

	public void setCif_id(Integer cif_id) {
		this.cif_id = cif_id;
	}
	
	

}
