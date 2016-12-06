package com.dx.payment.services.impl;

import java.util.List;

import com.dx.payment.bean.LoanRepayHis;
import com.dx.payment.dao.LoanRepayHisDAO;
import com.dx.payment.services.LoanRepayHisBO;

import app.base.ServiceException;

@SuppressWarnings("serial")
public class LoanRepayHisBOImpl extends ServiceException implements LoanRepayHisBO {
	private LoanRepayHisDAO loanRepayHisDAO;

	public LoanRepayHisDAO getLoanRepayHisDAO() {
		return loanRepayHisDAO;
	}

	public void setLoanRepayHisDAO(LoanRepayHisDAO loanRepayHisDAO) {
		this.loanRepayHisDAO = loanRepayHisDAO;
	}

	public List<LoanRepayHis> viewHis(LoanRepayHis loanRepayHis) {
		return loanRepayHisDAO.viewHis(loanRepayHis);
	}

	public void insert(LoanRepayHis repayHis) {
		loanRepayHisDAO.insert(repayHis);
	}
}
