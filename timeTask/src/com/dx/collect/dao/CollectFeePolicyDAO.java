package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import com.dx.collect.bean.CollectFeePolicy;

public interface CollectFeePolicyDAO {

	public CollectFeePolicy getById(String id);

	public List<CollectFeePolicy> findByPage(Map<String, Object> map);

	public void del(String id);

	public void insert(CollectFeePolicy policy);

	public void update(CollectFeePolicy policy);

	public Integer getCount(CollectFeePolicy policy);
	
}
