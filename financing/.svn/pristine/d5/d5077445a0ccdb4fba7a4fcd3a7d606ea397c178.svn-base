package com.jiangchuanbanking.account.service;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;

/**
 * Call service Interface
 */
public interface IMainAccountService extends IBaseService<MainAccount> {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled calls
	 */
	public List<MainAccount> findScheduleEntitys() throws Exception;
	
	public MainAccount creatMainAcc(Customer customer);

}
