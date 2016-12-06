package com.dx.payment.services;

import com.dx.payment.bean.LoanMainAccount;

import app.base.ServiceException;

public interface LoanMainAccountBO {

	public LoanMainAccount getById(String id) throws ServiceException;

	public void del(String id) throws ServiceException;

	public void update(LoanMainAccount account) throws ServiceException;

	public void insert(LoanMainAccount account) throws ServiceException;

	public LoanMainAccount viewMainAccount(LoanMainAccount mainAccount);


}
