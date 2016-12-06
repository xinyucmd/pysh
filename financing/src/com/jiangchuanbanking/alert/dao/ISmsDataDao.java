package com.jiangchuanbanking.alert.dao;


import com.jiangchuanbanking.alert.domain.SmsData;
import com.jiangchuanbanking.base.dao.IBaseDao;

/**
 * Call service Interface
 */
public interface ISmsDataDao extends IBaseDao<SmsData> {

	void insert(SmsData smsDataBean);
	
}
