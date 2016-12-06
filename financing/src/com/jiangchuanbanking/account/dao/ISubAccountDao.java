package com.jiangchuanbanking.account.dao;


import java.util.List;


import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.base.dao.IBaseDao;


/**
 * Call service Interface
 */
public interface ISubAccountDao extends IBaseDao<SubAccount> {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled calls
	 */
	public List<SubAccount> findScheduleEntitys() throws Exception;
	
	public String getSubNo();

}
