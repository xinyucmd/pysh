package com.jiangchuanbanking.flowdetails.dao;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.record.formula.functions.FinanceFunction;

import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;

/**
 * Call service Interface
 */
public interface IFinancingDetailsDao extends IBaseDao<FinancingDetails> {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 */
	public List<FinancingDetails> findScheduleEntitys(FinancingDetails details) throws Exception;


}
