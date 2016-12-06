package com.jiangchuanbanking.account.service.impl;


import java.util.List;



import com.jiangchuanbanking.account.dao.IMainAccountDao;
import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.account.service.IMainAccountService;
import com.jiangchuanbanking.base.dao.IBaseDao;

import com.jiangchuanbanking.base.service.impl.BaseService;


import com.jiangchuanbanking.investor.domain.Customer;

import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.DateTimeUtil;

/**
 * Call service
 */
public class MainAccountService extends BaseService<MainAccount> implements IMainAccountService {

	private IMainAccountDao mainAccountDao;
	private IBaseDao<MainAccount> baseDao;

	public List<MainAccount> findScheduleEntitys()
			throws Exception {
		return  mainAccountDao.findScheduleEntitys();
	}




	public IBaseDao<MainAccount> getBaseDao() {
		return baseDao;
	}


	public void setBaseDao(IBaseDao<MainAccount> baseDao) {
		this.baseDao = baseDao;
	}




	public IMainAccountDao getMainAccountDao() {
		return mainAccountDao;
	}




	public void setMainAccountDao(IMainAccountDao mainAccountDao) {
		this.mainAccountDao = mainAccountDao;
	}




	@Override
	public MainAccount creatMainAcc(Customer customer) {
		List<MainAccount> list=baseDao.findByHQL("from MainAccount where CIF_NO="+ customer.getId());
		if (list==null||list.size()==0) {
			MainAccount ma=new MainAccount();
			ma.setAccount_no(mainAccountDao.getAccNo());
			ma.setCif_no(customer.getId());
			ma.setCif_name(customer.getCif_name());
			ma.setAccount_type("1");
			ma.setStatus("1");
			ma.setOpen_date(DateTimeUtil.getNowDateString());
			ma.setOpen_op(CommonUtil.getLoginUserName());
			ma.setOpen_id(CommonUtil.getLoginUserId());
			ma=baseDao.makePersistent(ma);
			return ma;
		}		
		return list.get(0);
	}
    
	




}
