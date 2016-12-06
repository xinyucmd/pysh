package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.collect.bean.CheckReportPolicy;


public class CheckReportPolicyDAOImpl extends SqlMapClientDaoSupport implements		CheckReportPolicyDAO {

	public CheckReportPolicy getById(String id)  {
		CheckReportPolicy checkReportPolicy = null;
			checkReportPolicy = (CheckReportPolicy) getSqlMapClientTemplate()
					.queryForObject("CheckReportPolicy.getById", id);
		return checkReportPolicy;
	}

	public List<CheckReportPolicy> findByPage(Map<String, Object> map)
			 {
		List<CheckReportPolicy> list = null;

			list = getSqlMapClientTemplate().queryForList(
					"collectreportpolicylist.findByPage", map);
		return list;
	}

	public void del(String id)  {
			getSqlMapClientTemplate().delete("CheckReportPolicy.del", id);
	}

	public void insert(CheckReportPolicy checkReportPolicy)  {
			getSqlMapClientTemplate().insert("CheckReportPolicy.insert",
					checkReportPolicy);
	}

	public void update(CheckReportPolicy checkReportPolicy)  {
			getSqlMapClientTemplate().update("CheckReportPolicy.update",
					checkReportPolicy);
	}

	public Integer getCount(CheckReportPolicy checkReportPolicy) {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"CheckReportPolicy.findPage.count", checkReportPolicy);
		return count;
	}

	public List<CheckReportPolicy> getReportPolicy(CheckReportPolicy crp)  {
		List<CheckReportPolicy> list = null;

			list = getSqlMapClientTemplate().queryForList(	"collectreportpolicylist.getReportPolicy", crp);
		return list;
	}

}
