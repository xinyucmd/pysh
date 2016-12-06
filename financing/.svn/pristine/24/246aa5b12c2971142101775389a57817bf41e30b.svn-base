package com.jiangchuanbanking.alert.dao;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;

/**
 * Call service Interface
 */
public interface IAlertItemDao extends IBaseDao<AlertItem> {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 */
	public List<AlertItem> findScheduleEntitys() throws Exception;

	public List<AlertItem> findItem(AlertItem alert);

	public AlertItem getItem(AlertItem alert);

}
