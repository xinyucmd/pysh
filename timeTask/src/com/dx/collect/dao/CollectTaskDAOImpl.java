package com.dx.collect.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dx.collect.bean.*;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


public class CollectTaskDAOImpl extends SqlMapClientDaoSupport implements CollectTaskDAO {

	public void insert(CollectTask ct)  {
			getSqlMapClientTemplate().insert("CollectTask.insert",
					ct);
	}


	public List<CollectTask> findFirstReport(HashMap<String, Object> map)  {
		List<CollectTask> list = null;

			list = getSqlMapClientTemplate().queryForList("CollectTask.findFirstReport", map);
		return list;
	}


	public Integer getCount(CollectTask ct)  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"CollectTask.findPage.count", ct);
		return count;
	}


	public Integer getFollowCount(CollectTask ct)  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"CollectTask.findPage.getFollowCount", ct);
		return count;
	}


	public Object findFollowReport(HashMap<String, Object> map)  {
		List<CollectTask> list = null;

			list = getSqlMapClientTemplate().queryForList("CollectTask.findFollowReport", map);
		return list;
	}


	public void setStatus(CollectTask ct)  {
			getSqlMapClientTemplate().update("CollectTask.setStatus",
					ct);
	}


	public List<CollectTask> findAllReport(CollectTask ct)  {
		List<CollectTask> list = null;

			list = getSqlMapClientTemplate().queryForList("CollectTask.findAllReport", ct);
		return list;
	}


	public List<CollectTask> findByPact(CollectTask ctTemp)  {
		List<CollectTask> list = null;
			list = getSqlMapClientTemplate().queryForList("CollectTask.findByPact", ctTemp);
		return list;
	}


	public CollectTask getById(String id)  {
		CollectTask ct = null;
			ct = (CollectTask) getSqlMapClientTemplate().queryForObject("CollectTask.getById", id);
		return ct;
	}


	public Integer findCountByTermNo(CollectTask ct)  {
		int count = 0 ;
			count = (Integer) getSqlMapClientTemplate().queryForObject("CollectTask.findCountByTermNo", ct);
		return count;
	}


	public void updateTask(CollectTask ct)  {
			getSqlMapClientTemplate().update("CollectTask.updateTask", ct);
	}


	public void UpdateNotDone(CollectTask ct)  {
			getSqlMapClientTemplate().update("CollectTask.UpdateNotDone", ct);
	}
	
}