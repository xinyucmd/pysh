package com.jiangchuanbanking.financing.dao;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.financing.domain.Call;

/**
 * Call DAO
 */
public interface ICallDao extends IBaseDao<Call> {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled calls
	 */
	public List<Call> findScheduleCalls(Date startDate) throws Exception;
}
