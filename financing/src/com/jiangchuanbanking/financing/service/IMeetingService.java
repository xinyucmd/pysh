package com.jiangchuanbanking.financing.service;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Meeting;

/**
 * Meeting service Interface
 */
public interface IMeetingService extends IBaseService<Meeting> {
	/**
	 * Finds scheduled meetings
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled meetings
	 */
	public List<Meeting> findScheduleMeetings(Date startDate) throws Exception;

}
