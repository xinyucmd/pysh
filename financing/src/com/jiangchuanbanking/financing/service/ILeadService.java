package com.jiangchuanbanking.financing.service;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Lead;

/**
 * Lead service Interface
 */
public interface ILeadService extends IBaseService<Lead> {
	/**
	 * Converts lead
	 * 
	 * @param id
	 *            lead instance id
	 * @param accountCheck
	 *            the flag to convert into account
	 * @param contactCheck
	 *            the flag to convert into contact
	 * @param opportunityCheck
	 *            the flag to convert into opportunity
	 */
	public void convert(Integer id, boolean accountCheck, boolean contactCheck,
			boolean opportunityCheck) throws Exception;

}
