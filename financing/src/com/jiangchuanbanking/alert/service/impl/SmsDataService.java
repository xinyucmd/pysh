package com.jiangchuanbanking.alert.service.impl;

import com.jiangchuanbanking.alert.dao.ISmsDataDao;
import com.jiangchuanbanking.alert.domain.SmsData;
import com.jiangchuanbanking.alert.service.ISmsDataService;
import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.impl.BaseService;

/**
 * Call service
 */
public class SmsDataService extends BaseService<SmsData> implements
		ISmsDataService {
	private IBaseDao<SmsData> baseDao;
	private ISmsDataDao smsDataDao;
	
	public IBaseDao<SmsData> getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(IBaseDao<SmsData> baseDao) {
		this.baseDao = baseDao;
	}
	public ISmsDataDao getSmsDataDao() {
		return smsDataDao;
	}
	public void setSmsDataDao(ISmsDataDao smsDataDao) {
		this.smsDataDao = smsDataDao;
	}
	public void insert(SmsData smsDataBean) {
		baseDao.makePersistent(smsDataBean);
	}
}
