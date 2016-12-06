package com.dx.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.payment.bean.AccountRepayRel;
import com.dx.payment.dao.AccountRepayRelDAO;


public class AccountRepayRelDAOImpl extends SqlMapClientDaoSupport implements
		AccountRepayRelDAO {

	public AccountRepayRel getById(String id)  {
		AccountRepayRel AccountRepayRel = null;
			AccountRepayRel = (AccountRepayRel) getSqlMapClientTemplate()
					.queryForObject("accountRepayRel.getById", id);
		return AccountRepayRel;
	}

	public List<AccountRepayRel> findByPage(Map<String, Object> map)
			 {
		List<AccountRepayRel> list = null;

			list = getSqlMapClientTemplate().queryForList(
					"accountRepayRel.findByPage", map);
		return list;
	}

	public void del(String id)  {
			getSqlMapClientTemplate().delete("accountRepayRel.del", id);
	}

	public void insert(AccountRepayRel AccountRepayRel)  {
			getSqlMapClientTemplate().insert("accountRepayRel.insert",
					AccountRepayRel);
	}

	public void update(AccountRepayRel AccountRepayRel)  {
			getSqlMapClientTemplate().update("accountRepayRel.update",
					AccountRepayRel);
	}
	
	public Integer getCount(AccountRepayRel AccountRepayRel) {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"accountRepayRel.findPage.count", AccountRepayRel);
		return count;
	}

	public List<AccountRepayRel> viewRel(AccountRepayRel accountRepayRel) {
		List<AccountRepayRel> list = null;

			list = getSqlMapClientTemplate().queryForList(
					"accountRepayRel.accountRepayRel", accountRepayRel);
		return list;
	}

	public String getSeqNo() {
		String seq="";
			seq=(String) getSqlMapClientTemplate().queryForObject("accountRepayRel.getSeqNo"	);
		return seq;
	}
	
}
