package com.dx.collect.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.collect.bean.CollectBr;

public class CollectBrDAOImpl extends SqlMapClientDaoSupport implements
		CollectBrDAO {

	public CollectBr getById(String id) {
		CollectBr collectBr = (CollectBr) getSqlMapClientTemplate()
				.queryForObject("collect_br.getById", id);
		return collectBr;
	}

	public List<CollectBr> getall(CollectBr collectBr) {
		List<CollectBr> list = getSqlMapClientTemplate().queryForList(
				"collectbrlist.findByPage", collectBr);
		return list;
	}

	public void del(String id) {
		getSqlMapClientTemplate().delete("CollectBr.del", id);
	}

	public void insert(CollectBr collectBr) {
		getSqlMapClientTemplate().insert("CollectBr.insert", collectBr);
	}

	public void update(CollectBr collectBr) {
		getSqlMapClientTemplate().update("CollectBr.update", collectBr);
	}

	public Integer getCount(CollectBr collectBr) {
		int count = (Integer) getSqlMapClientTemplate().queryForObject(
				"CollectBr.findPage.count", collectBr);
		return count;
	}

	public CollectBr getMng(CollectBr collectBr) {
		
		CollectBr cb = new CollectBr();
		try {
			cb = (CollectBr) getSqlMapClientTemplate()
					.queryForObject("collect_br.getMng", collectBr);
		} catch (DataAccessException e) {
			System.out.println(collectBr.getBr_no()+"=====*********=====");
			e.printStackTrace();
		}
		return cb;
	}

	public List<CollectBr> getTelMng(CollectBr collectBr) {
		List<CollectBr> list = getSqlMapClientTemplate().queryForList(
				"collect_br.getTelMng", collectBr);
		return list;
	}

}
