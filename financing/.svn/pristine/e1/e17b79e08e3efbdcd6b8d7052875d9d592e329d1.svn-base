package com.jiangchuanbanking.prod.service;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;

/**
 * Call service Interface
 */
public interface IProdService extends IBaseService<ProdBaseEntity> {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled calls
	 */
	public List<ProdBaseEntity> findScheduleProdBaseEntitys() throws Exception;

	public void deleteEntity(String prdtNo);

	public ProdBaseEntity getProdByNo(String prdtNo);

}
