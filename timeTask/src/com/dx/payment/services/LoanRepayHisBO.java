package com.dx.payment.services;

import java.util.List;

import com.dx.payment.bean.LoanRepayHis;

import app.base.ServiceException;

public interface LoanRepayHisBO {

	List<LoanRepayHis> viewHis(LoanRepayHis loanRepayHis);

	void insert(LoanRepayHis repayHis);

}
