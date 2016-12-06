package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import com.dx.collect.bean.Collection;

public interface CollectionDAO {

	public Collection getById(String id);

	public List<Collection> getall();

	public void del(String id);

	public void insert(Collection collection);

	public void update(Collection collection);

	public Integer getCount(Collection collection);

	public void update1(Collection collection);

	public void changestage(Collection collection);

	public void changeop(Collection collection);
}
