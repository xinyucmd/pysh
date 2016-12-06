package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.collect.bean.Collection;

public class CollectionDAOImpl extends SqlMapClientDaoSupport implements
		CollectionDAO {

	public Collection getById(String id) {
		Collection Collection = (Collection) getSqlMapClientTemplate()
				.queryForObject("t_collection.getById", id);
		return Collection;
	}

	public List<Collection> getall() {
		List<Collection> list = getSqlMapClientTemplate().queryForList(
				"t_collection.findByPage");
		return list;
	}

	public void del(String id) {
		getSqlMapClientTemplate().delete("t_collection.del", id);
	}

	public void insert(Collection Collection) {
		getSqlMapClientTemplate().insert("t_collection.insert", Collection);
	}

	public void update(Collection Collection) {
		getSqlMapClientTemplate().update("t_collection.update", Collection);
	}

	public void update1(Collection Collection) {
		getSqlMapClientTemplate().update("t_collection.update1", Collection);
	}

	public Integer getCount(Collection Collection) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject(
				"t_collection.findPage.count", Collection);
		return count;
	}

	public void changestage(Collection collection) {
		getSqlMapClientTemplate()
				.update("t_collection.changestage", collection);
	}

	public void changeop(Collection collection) {
		getSqlMapClientTemplate().update("t_collection.changeop", collection);
	}
}
