package com.dx.payment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.payment.bean.LoanRepayPlan;
import com.dx.payment.dao.LoanRepayPlanDAO;


public class LoanRepayPlanDAOImpl extends SqlMapClientDaoSupport implements LoanRepayPlanDAO {

	public LoanRepayPlan getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LoanRepayPlan> findByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public void del(String id) {
		// TODO Auto-generated method stub

	}

	public void insert(LoanRepayPlan plan) {
			getSqlMapClientTemplate().insert("LoanRepayPlan.insert",
					plan);

	}

	public void update(LoanRepayPlan plan) {
			getSqlMapClientTemplate().update("LoanRepayPlan.update",
					plan);
	}

	public Integer getCount(LoanRepayPlan plan) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LoanRepayPlan> viewPlan(LoanRepayPlan loanRepayPlan) {
		List<LoanRepayPlan> list = null;
		list = getSqlMapClientTemplate().queryForList("LoanRepayPlan.viewPlan", loanRepayPlan);
		return list;
	}

	public LoanRepayPlan getPlanBean(LoanRepayPlan parmPlanBean) {
		return (LoanRepayPlan) getSqlMapClientTemplate().queryForObject("getLoanRepayPlan", parmPlanBean);
	}

}
