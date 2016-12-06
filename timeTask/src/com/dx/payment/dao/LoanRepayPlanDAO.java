package com.dx.payment.dao;

import java.util.List;
import java.util.Map;

import com.dx.payment.bean.LoanRepayPlan;


public interface LoanRepayPlanDAO {
	public LoanRepayPlan getById(String id) ;

	public List<LoanRepayPlan> findByPage(Map<String, Object> map)
			;

	public void del(String id) ;

	public void insert(LoanRepayPlan plan) ;

	public void update(LoanRepayPlan plan) ;

	public Integer getCount(LoanRepayPlan plan);

	public List<LoanRepayPlan> viewPlan(LoanRepayPlan loanRepayPlan);

	public LoanRepayPlan getPlanBean(LoanRepayPlan parmPlanBean);
}
