package com.jiangchuanbanking.prod.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.financing.dao.ICallDao;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.financing.service.ICallService;
import com.jiangchuanbanking.prod.dao.IProdChargePolicyDao;
import com.jiangchuanbanking.prod.dao.IProdDao;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.domain.ProdChargePolicy;
import com.jiangchuanbanking.prod.service.IProdChargePolicyService;
import com.jiangchuanbanking.prod.service.IProdService;

/**
 * Call service
 */
public class ProdChargePolicyService extends BaseService<ProdChargePolicy> implements IProdChargePolicyService {

	private IProdChargePolicyDao prodChargePolicyDao;
	private IBaseDao<ProdChargePolicy> baseDao;

	public List<ProdChargePolicy> findScheduleProdBaseEntitys()
			throws Exception {
		return  prodChargePolicyDao.findScheduleProdBaseEntitys();
	}


	public IBaseDao<ProdChargePolicy> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao<ProdChargePolicy> baseDao) {
		this.baseDao = baseDao;
	}


	public IProdChargePolicyDao getProdChargePolicyDao() {
		return prodChargePolicyDao;
	}


	public void setProdChargePolicyDao(IProdChargePolicyDao prodChargePolicyDao) {
		this.prodChargePolicyDao = prodChargePolicyDao;
	}


	public List<ProdChargePolicy> findPolicy(ProdChargePolicy prodChargePolicy) {
		return prodChargePolicyDao.findPolicy(prodChargePolicy);
	}






}
