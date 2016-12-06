package com.jiangchuanbanking.alert.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.alert.service.IAlertItemService;
import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.changelog.domain.ChangeLog;
import com.jiangchuanbanking.dict.domain.AccountLevel;
import com.jiangchuanbanking.dict.domain.AccountNature;
import com.jiangchuanbanking.dict.domain.AccountType;
import com.jiangchuanbanking.dict.domain.AnnualRevenue;
import com.jiangchuanbanking.dict.domain.Capital;
import com.jiangchuanbanking.dict.domain.CompanySize;
import com.jiangchuanbanking.dict.domain.Currency;
import com.jiangchuanbanking.dict.domain.Industry;
import com.jiangchuanbanking.financing.domain.Account;
import com.jiangchuanbanking.financing.domain.Document;
import com.jiangchuanbanking.financing.domain.TargetList;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.domain.ProdChargePolicy;
import com.jiangchuanbanking.prod.service.IProdChargePolicyService;
import com.jiangchuanbanking.prod.service.IProdService;
import com.jiangchuanbanking.system.domain.ProductConfiguration;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.BeanUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class EditAlertAction extends BaseEditAction implements Preparable {

	private IBaseService<AlertItem> baseService;
	private IAlertItemService alertService;
	private AlertItem alertItem;

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	public IBaseService<AlertItem> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<AlertItem> baseService) {
		this.baseService = baseService;
	}


	public AlertItem getAlertItem() {
		return alertItem;
	}

	public void setAlertItem(AlertItem alertItem) {
		this.alertItem = alertItem;
	}

	public IAlertItemService getAlertService() {
		return alertService;
	}

	public void setAlertService(IAlertItemService alertService) {
		this.alertService = alertService;
	}

}
