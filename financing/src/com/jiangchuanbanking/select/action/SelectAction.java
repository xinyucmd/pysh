package com.jiangchuanbanking.select.action;

import java.util.List;

import com.jiangchuanbanking.select.domain.WealthParnDic;
import com.jiangchuanbanking.select.service.ISelectService;
import com.opensymphony.xwork2.ActionSupport;


public class SelectAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ISelectService selectService;
	
	public String execute(){
		return null;
	}
	
	public List<WealthParnDic> queryWPD(String keyCode) throws Exception{
		List<WealthParnDic> list = selectService.queryWPD(keyCode);	
		return list;
	}
	

	public List<WealthParnDic> queryWPD() throws Exception{
		List<WealthParnDic> list = selectService.queryWPD();	
		return list;
	}


	public ISelectService getSelectService() {
		return selectService;
	}


	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}
	
}
