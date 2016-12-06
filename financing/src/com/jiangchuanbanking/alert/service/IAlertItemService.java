package com.jiangchuanbanking.alert.service;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;

/**
 * Call service Interface
 */
public interface IAlertItemService extends IBaseService<AlertItem> {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled calls
	 */
	public List<AlertItem> findScheduleEntitys() throws Exception;
	/**
	 * 获取想要的提醒模板list
	 */
	public List<AlertItem> findItem(AlertItem alert) throws Exception;
	/**
	 * 获取想要的提醒模板对象
	 */
	public AlertItem getItem(AlertItem alert) throws Exception;
	/**
	 * 提醒功能，参数待定
	 * @return 
	 */
	public void alarm(AlertItem alertItem,String mobile[])throws Exception;
}
