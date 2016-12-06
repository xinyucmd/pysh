package com.jiangchuanbanking.prod.dao;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.domain.ProdChargePolicy;

/**
 * Call service Interface
 */
public interface IProdChargePolicyDao extends IBaseDao<ProdChargePolicy> {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled calls
	 */
	public List<ProdChargePolicy> findScheduleProdBaseEntitys() throws Exception;

	public List<ProdChargePolicy> findPolicy(ProdChargePolicy prodChargePolicy);

}
