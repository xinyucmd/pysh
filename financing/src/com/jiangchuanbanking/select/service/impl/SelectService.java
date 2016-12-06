package com.jiangchuanbanking.select.service.impl;


import java.util.List;

import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.select.dao.ISelectDao;
import com.jiangchuanbanking.select.domain.WealthParnDic;
import com.jiangchuanbanking.select.service.ISelectService;

public class SelectService extends BaseService implements ISelectService { 
	
	private ISelectDao selectDao;

	@Override
	public List<WealthParnDic> queryWPD(String keyCode) throws Exception {
		
		String hql=" from WealthParnDic wpd where wpd.keyCode='"+keyCode+"'";	
		List<WealthParnDic> list=this.getBaseDao().findByHQL(hql);
	   
		return list;
	}

	@Override
	public List<WealthParnDic> queryWPD() {
		
		String hql=" from WealthParnDic ";	
	    List list=this.getBaseDao().findByHQL(hql);	
		return list;
	}

	@Override
	public String getSubParentId() {
		
		return selectDao.getSubParentId();
	}

	public ISelectDao getSelectDao() {
		return selectDao;
	}

	public void setSelectDao(ISelectDao selectDao) {
		this.selectDao = selectDao;
	}

	@Override
	public  String getOpCnName(String keyCode,String opCode) {	
		String hql=" from WealthParnDic wpd where wpd.keyCode='"+keyCode+"' and wpd.opCode='"+opCode+"'";	
		WealthParnDic wd=(WealthParnDic) this.getBaseDao().findEntityByHql(hql);
		if (wd==null) {
			return "";
		}
		return wd.getOpCnName();
	}

	
	
}
