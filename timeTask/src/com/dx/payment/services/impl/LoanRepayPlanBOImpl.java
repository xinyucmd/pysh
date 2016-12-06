package com.dx.payment.services.impl;

import java.util.List;

import com.dx.payment.bean.LoanRepayPlan;
import com.dx.payment.dao.LoanRepayPlanDAO;
import com.dx.payment.services.LoanRepayPlanBO;

import app.base.ServiceException;

public class LoanRepayPlanBOImpl   implements LoanRepayPlanBO {

private LoanRepayPlanDAO loanRepayPlanDAO;
	public LoanRepayPlan getById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	public void del(String id) throws ServiceException {
		// TODO Auto-generated method stub

	}


	public void update(LoanRepayPlan plan) throws ServiceException {
		loanRepayPlanDAO.update(plan);
	}


	public void insert(LoanRepayPlan plan) throws ServiceException {
		loanRepayPlanDAO.insert(plan);
	}


	/*public Ipage findByPage(Ipage ipg, LoanRepayPlan plan) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}*/


	public LoanRepayPlanDAO getLoanRepayPlanDAO() {
		return loanRepayPlanDAO;
	}


	public void setLoanRepayPlanDAO(LoanRepayPlanDAO loanRepayPlanDAO) {
		this.loanRepayPlanDAO = loanRepayPlanDAO;
	}


	public List<LoanRepayPlan> viewPlan(LoanRepayPlan loanRepayPlan) {
		return loanRepayPlanDAO.viewPlan(loanRepayPlan);
	}


	public LoanRepayPlan getPlanBean(LoanRepayPlan parmPlanBean) {
		return loanRepayPlanDAO.getPlanBean(parmPlanBean);
	}

}
