package com.jiangchuanbanking.account.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.core.task.TaskExecutor;

import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.account.service.ISubAccountService;
import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.changelog.domain.ChangeLog;
import com.jiangchuanbanking.dict.domain.AccountLevel;
import com.jiangchuanbanking.dict.domain.AccountNature;
import com.jiangchuanbanking.dict.domain.AccountType;
import com.jiangchuanbanking.dict.domain.AnnualRevenue;
import com.jiangchuanbanking.dict.domain.Capital;
import com.jiangchuanbanking.dict.domain.CompanySize;
import com.jiangchuanbanking.dict.domain.Currency;
import com.jiangchuanbanking.dict.domain.Industry;
import com.jiangchuanbanking.dict.service.IOptionService;
import com.jiangchuanbanking.financing.domain.Account;
import com.jiangchuanbanking.financing.domain.Campaign;
import com.jiangchuanbanking.financing.domain.Document;
import com.jiangchuanbanking.financing.domain.TargetList;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.BeanUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * 编辑投资人
 * 
 */
public class EditSubAccountAction extends BaseEditAction implements Preparable {

	private static final long serialVersionUID = -2404576552417042445L;
	private IBaseService<SubAccount> baseService;
	private ISubAccountService subAccountService;
	private SubAccount subAccount;
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public IBaseService<SubAccount> getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService<SubAccount> baseService) {
		this.baseService = baseService;
	}
	public SubAccount getSubAccount() {
		return subAccount;
	}
	public void setSubAccount(SubAccount subAccount) {
		this.subAccount = subAccount;
	}
	public ISubAccountService getSubAccountService() {
		return subAccountService;
	}
	public void setSubAccountService(ISubAccountService subAccountService) {
		this.subAccountService = subAccountService;
	}

}
