package com.dx.payment.services;

import java.util.List;

import com.dx.payment.bean.LoanRepayPlan;

import app.base.ServiceException;

public interface LoanRepayPlanBO {

	public LoanRepayPlan getById(String id) throws ServiceException;

	public void del(String id) throws ServiceException;

	public void update(LoanRepayPlan plan) throws ServiceException;

	public void insert(LoanRepayPlan plan) throws ServiceException;

	/*public Ipage findByPage(Ipage ipg, LoanRepayPlan plan)
			throws ServiceException;*/

	public List<LoanRepayPlan> viewPlan(LoanRepayPlan loanRepayPlan);

	public LoanRepayPlan getPlanBean(LoanRepayPlan parmPlanBean);

}
