package com.jiangchuanbanking.financing.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.financing.dao.ICallDao;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.financing.service.ICallService;

/**
 * Call service
 */
public class CallService extends BaseService<Call> implements ICallService {

	private ICallDao callDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.service.ICallService#findScheduleCalls(java.util.Date)
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Call> findScheduleCalls(Date startDate) throws Exception {
		return callDao.findScheduleCalls(startDate);
	}

	/**
	 * @return the callDao
	 */
	public ICallDao getCallDao() {
		return callDao;
	}

	/**
	 * @param callDao
	 *            the callDao to set
	 */
	public void setCallDao(ICallDao callDao) {
		this.callDao = callDao;
	}
}
