package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.collect.bean.CollectFeePolicy;

public class CollectFeePolicyDAOImpl extends SqlMapClientDaoSupport implements
		CollectFeePolicyDAO {

	public CollectFeePolicy getById(String id) {
		return (CollectFeePolicy) getSqlMapClientTemplate().queryForObject(
				"collectFeePolicy.getById", id);
	}

	public List<CollectFeePolicy> findByPage(Map<String, Object> map) {
		return getSqlMapClientTemplate().queryForList(
				"collectFeePolicy.findByPage", map);
	}

	public void del(String id) {
		getSqlMapClientTemplate().delete("collectFeePolicy.del", id);
	}

	public void insert(CollectFeePolicy policy) {
		getSqlMapClientTemplate().insert("collectFeePolicy.insert", policy);
	}

	public void update(CollectFeePolicy policy) {
		getSqlMapClientTemplate().update("collectFeePolicy.update", policy);
	}

	public Integer getCount(CollectFeePolicy policy) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"collectFeePolicy.findPage.count", policy);
	}

}
