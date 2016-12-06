package com.dx.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.payment.bean.LoanSubAccount;
import com.dx.payment.dao.LoanSubAccountDAO;

public class LoanSubAccountDAOImpl extends SqlMapClientDaoSupport implements LoanSubAccountDAO {

	public LoanSubAccount getById(String id) {
		LoanSubAccount LoanSubAccount = null;
		LoanSubAccount = (LoanSubAccount) getSqlMapClientTemplate().queryForObject("loanSubAccount.getById", id);
		return LoanSubAccount;
	}

	public List<LoanSubAccount> findByPage(Map<String, Object> map) {
		List<LoanSubAccount> list = null;

		list = getSqlMapClientTemplate().queryForList("loanSubAccount.findByPage", map);
		return list;
	}

	public void del(String id) {
		getSqlMapClientTemplate().delete("loanSubAccount.del", id);
	}

	public void insert(LoanSubAccount LoanSubAccount) {
		getSqlMapClientTemplate().insert("loanSubAccount.insert", LoanSubAccount);
	}

	public void update(LoanSubAccount LoanSubAccount) {
		getSqlMapClientTemplate().update("loanSubAccount.update", LoanSubAccount);
	}

	public Integer getCount(LoanSubAccount LoanSubAccount) {
		int count = 0;
		count = (Integer) getSqlMapClientTemplate().queryForObject("loanSubAccount.findPage.count", LoanSubAccount);
		return count;
	}

	public List getList(LoanSubAccount loanSubAccount) {
		List<LoanSubAccount> list = null;

		list = getSqlMapClientTemplate().queryForList("loanSubAccount.getList", loanSubAccount);
		return list;
	}

}
