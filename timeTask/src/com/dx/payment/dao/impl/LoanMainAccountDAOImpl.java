package com.dx.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.payment.bean.LoanMainAccount;
import com.dx.payment.dao.LoanMainAccountDAO;


public class LoanMainAccountDAOImpl extends SqlMapClientDaoSupport implements
		LoanMainAccountDAO {

	public LoanMainAccount getById(String id) {
		LoanMainAccount LoanMainAccount = null;
			LoanMainAccount = (LoanMainAccount) getSqlMapClientTemplate()
					.queryForObject("loanMainAccount.getById", id);
		return LoanMainAccount;
	}

	public List<LoanMainAccount> findByPage(Map<String, Object> map)
			{
		List<LoanMainAccount> list = null;

			list = getSqlMapClientTemplate().queryForList(
					"loanMainAccount.findByPage", map);
		return list;
	}

	public void del(String id) {
			getSqlMapClientTemplate().delete("loanMainAccount.del", id);
	}

	public void insert(LoanMainAccount LoanMainAccount) {
			getSqlMapClientTemplate().insert("loanMainAccount.insert",
					LoanMainAccount);
	}

	public void update(LoanMainAccount LoanMainAccount) {
			getSqlMapClientTemplate().update("loanMainAccount.update",
					LoanMainAccount);
	}
	
	public Integer getCount(LoanMainAccount LoanMainAccount) {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"loanMainAccount.findPage.count", LoanMainAccount);
		return count;
	}

	public LoanMainAccount viewMainAccount(LoanMainAccount mainAccount) {
		return (LoanMainAccount) getSqlMapClientTemplate().queryForObject("viewMainAccount", mainAccount);
	}
	
}
