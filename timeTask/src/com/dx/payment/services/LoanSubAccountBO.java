package com.dx.payment.services;

import java.util.List;

import com.dx.payment.bean.LoanSubAccount;

import app.base.ServiceException;

public interface LoanSubAccountBO {

	public LoanSubAccount getById(String id) throws ServiceException;

	public void del(String id) throws ServiceException;

	public void update(LoanSubAccount account) throws ServiceException;

	public void insert(LoanSubAccount account) throws ServiceException;


	public List<LoanSubAccount> getList(LoanSubAccount loanSubAccount);

}
