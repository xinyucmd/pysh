package com.dx.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.payment.bean.LoanRepayHis;
import com.dx.payment.dao.LoanRepayHisDAO;


public class LoanRepayHisDAOImpl extends SqlMapClientDaoSupport implements
		LoanRepayHisDAO {

	public LoanRepayHis getById(String id)  {
		LoanRepayHis LoanRepayHis = null;
			LoanRepayHis = (LoanRepayHis) getSqlMapClientTemplate()
					.queryForObject("loanRepayHis.getById", id);
		return LoanRepayHis;
	}

	public List<LoanRepayHis> findByPage(Map<String, Object> map)
			 {
		List<LoanRepayHis> list = null;

			list = getSqlMapClientTemplate().queryForList(
					"loanRepayHis.findByPage", map);
		return list;
	}

	public void del(String id)  {
			getSqlMapClientTemplate().delete("loanRepayHis.del", id);
	}

	public void insert(LoanRepayHis LoanRepayHis)  {
			getSqlMapClientTemplate().insert("loanRepayHis.insert",
					LoanRepayHis);
	}

	public void update(LoanRepayHis LoanRepayHis)  {
			getSqlMapClientTemplate().update("loanRepayHis.update",
					LoanRepayHis);
	}

	public Integer getCount(LoanRepayHis LoanRepayHis) {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"loanRepayHis.findPage.count", LoanRepayHis);
		return count;
	}

	public List<LoanRepayHis> viewHis(LoanRepayHis loanRepayHis) {
		List<LoanRepayHis> list = null;

			list = getSqlMapClientTemplate().queryForList(
					"loanRepayHis.viewHis", loanRepayHis);
		return list;
	}

}
