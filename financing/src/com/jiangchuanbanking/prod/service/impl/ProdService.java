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
import com.jiangchuanbanking.prod.dao.IProdDao;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.service.IProdService;

/**
 * Call service
 */
public class ProdService extends BaseService<ProdBaseEntity> implements IProdService {

	private IProdDao prodDao;
	private IBaseDao<ProdBaseEntity> baseDao;

	public List<ProdBaseEntity> findScheduleProdBaseEntitys()
			throws Exception {
		return  prodDao.findScheduleProdBaseEntitys();
	}
	public void deleteEntity(String prdtNo) {
		prodDao.deleteEntity(prdtNo);
	}

	public IProdDao getProdDao() {
		return prodDao;
	}


	public void setProdDao(IProdDao prodDao) {
		this.prodDao = prodDao;
	}


	public IBaseDao<ProdBaseEntity> getBaseDao() {
		return baseDao;
	}


	public void setBaseDao(IBaseDao<ProdBaseEntity> baseDao) {
		this.baseDao = baseDao;
	}
	public ProdBaseEntity getProdByNo(String prdtNo) {
		return prodDao.getProdByNo(prdtNo);
	}


	



}
