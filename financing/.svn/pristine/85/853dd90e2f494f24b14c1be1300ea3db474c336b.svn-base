package com.jiangchuanbanking.financing.dao;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.financing.domain.Meeting;

/**
 * Meeting DAO
 */
public interface IMeetingDao extends IBaseDao<Meeting> {
	/**
	 * Finds scheduled meetings
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled meetings
	 */
	public List<Meeting> findScheduleMeetings(Date startDate) throws Exception;
}
