package com.jiangchuanbanking.alert.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.alert.service.IAlertItemService;
import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.dict.domain.Currency;
import com.jiangchuanbanking.financing.domain.Account;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.service.IProdService;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;


@SuppressWarnings("serial")
public class ListAlertAction extends BaseListAction {

    private IBaseService<AlertItem> baseService;
    private IAlertItemService alertService;
    private AlertItem alertItem;
    private static final String CLAZZ = AlertItem.class.getSimpleName();
	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
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
