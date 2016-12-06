package com.dx.collect.dao;

import java.util.List;

import com.dx.collect.bean.CollectBr;

public interface CollectBrDAO {

	public CollectBr getById(String id);

	public List<CollectBr> getall(CollectBr collectBr);

	public void del(String id);

	public void insert(CollectBr collectBr);

	public void update(CollectBr collectBr);

	public Integer getCount(CollectBr collectBr);
	
	public CollectBr getMng(CollectBr collectBr);

	public List<CollectBr> getTelMng(CollectBr collectBr);
}
