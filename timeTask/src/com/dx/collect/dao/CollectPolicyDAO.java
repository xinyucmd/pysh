package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import com.dx.collect.bean.CollectPolicy;

public interface CollectPolicyDAO {

	public CollectPolicy getById(String id);

	public List<CollectPolicy> getall();

	public void del(String id);

	public void insert(CollectPolicy collectPolicy);

	public void update(CollectPolicy collectPolicy);

	public Integer getCount(CollectPolicy collectPolicy);
}
