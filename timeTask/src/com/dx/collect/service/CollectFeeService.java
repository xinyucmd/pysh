package com.dx.collect.service;

import com.dx.collect.bean.CollectFeePolicy;
import com.dx.collect.dao.CollectFeePolicyDAO;

public class CollectFeeService {
	private CollectFeePolicyDAO collectFeePolicyDAO;

	public CollectFeePolicyDAO getCollectFeePolicyDAO() {
		return collectFeePolicyDAO;
	}

	public void setCollectFeePolicyDAO(CollectFeePolicyDAO collectFeePolicyDAO) {
		this.collectFeePolicyDAO = collectFeePolicyDAO;
	}
	
	public CollectFeePolicy getById(String id) {
		return collectFeePolicyDAO.getById(id);
	}
	
}
