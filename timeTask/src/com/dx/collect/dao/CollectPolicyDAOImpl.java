package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.collect.bean.CollectPolicy;

public class CollectPolicyDAOImpl extends SqlMapClientDaoSupport implements
		CollectPolicyDAO {

	public CollectPolicy getById(String id) {
		CollectPolicy collectPolicy = (CollectPolicy) getSqlMapClientTemplate()
				.queryForObject("collect_policy.getById", id);
		return collectPolicy;
	}

	public List<CollectPolicy> getall() {
		List<CollectPolicy> list = getSqlMapClientTemplate().queryForList(
				"collect_policy.getall");
		return list;
	}

	public void del(String id) {
		getSqlMapClientTemplate().delete("collect_policy.del", id);
	}

	public void insert(CollectPolicy collectPolicy) {
		getSqlMapClientTemplate()
				.insert("collect_policy.insert", collectPolicy);
	}

	public void update(CollectPolicy collectPolicy) {
		getSqlMapClientTemplate()
				.update("collect_policy.update", collectPolicy);
	}

	public Integer getCount(CollectPolicy collectPolicy) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject(
				"collect_policy.findPage.count", collectPolicy);
		return count;
	}

}
