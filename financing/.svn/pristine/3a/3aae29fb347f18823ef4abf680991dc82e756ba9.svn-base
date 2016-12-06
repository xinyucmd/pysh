package com.jiangchuanbanking.financing.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;

import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.financing.dao.ICallDao;
import com.jiangchuanbanking.financing.domain.Call;

/**
 * Call DAO
 */
public class CallDao extends BaseDao<Call> implements ICallDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.ICallDao#findScheduleCalls(java.util.Date)
	 */
	@Override
	public List<Call> findScheduleCalls(Date startDate) throws Exception {
		List<Call> calls = this.findByParam(
				"from Call where reminder_email = true and start_date > ? ",
				startDate);
		for (Call call : calls) {
			Hibernate.initialize(call.getContacts());
			Hibernate.initialize(call.getLeads());
			Hibernate.initialize(call.getUsers());
		}

		return calls;
	}

}
