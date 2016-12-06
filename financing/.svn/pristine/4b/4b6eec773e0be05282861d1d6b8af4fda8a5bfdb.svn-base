package com.jiangchuanbanking.account.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.struts2.ServletActionContext;
import org.springframework.core.task.TaskExecutor;

import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.account.service.IMainAccountService;
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
import com.jiangchuanbanking.financing.domain.BankAccount;
import com.jiangchuanbanking.financing.domain.Campaign;
import com.jiangchuanbanking.financing.domain.Document;
import com.jiangchuanbanking.financing.domain.TargetList;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.BeanUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;
import com.jiangchuanbanking.util.DateTimeUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * 编辑投资人
 * 
 */
public class EditMainAccountAction extends BaseEditAction implements Preparable {

	private static final long serialVersionUID = -2404576552417042445L;
	private IBaseService baseService;
	private IMainAccountService mainAccountService;
	private MainAccount mainAccount;
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public MainAccount getMainAccount() {
		return mainAccount;
	}
	public void setMainAccount(MainAccount mainAccount) {
		this.mainAccount = mainAccount;
	}
	public IMainAccountService getMainAccountService() {
		return mainAccountService;
	}
	public void setMainAccountService(IMainAccountService mainAccountService) {
		this.mainAccountService = mainAccountService;
	}
	public IBaseService getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

}
