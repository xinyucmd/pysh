package com.jiangchuanbanking.account.dao;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;

/**
 * Call service Interface
 */
public interface IMainAccountDao extends IBaseDao<MainAccount> {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled calls
	 */
	public List<MainAccount> findScheduleEntitys() throws Exception;
	
	public String getAccNo();

}
