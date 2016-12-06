package com.dx.payment.services.impl;

import java.util.HashMap;
import java.util.List;

import com.dx.payment.bean.LoanSubAccount;
import com.dx.payment.dao.LoanSubAccountDAO;
import com.dx.payment.services.LoanSubAccountBO;

import app.base.ServiceException;

public class LoanSubAccountBOImpl   implements
		LoanSubAccountBO {

	private LoanSubAccountDAO loanSubAccountDAO;

	public LoanSubAccount getById(String id) throws ServiceException {
		LoanSubAccount LoanSubAccount = null;
		try {
			LoanSubAccount = loanSubAccountDAO.getById(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return LoanSubAccount;
	}

	public void del(String id) throws ServiceException {
		try {
			loanSubAccountDAO.del(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void update(LoanSubAccount LoanSubAccount) throws ServiceException {
		try {
			loanSubAccountDAO.update(LoanSubAccount);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void insert(LoanSubAccount LoanSubAccount) throws ServiceException {
		try {
			loanSubAccountDAO.insert(LoanSubAccount);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}


	public LoanSubAccountDAO getLoanSubAccountDAO() {
		return loanSubAccountDAO;
	}

	public void setLoanSubAccountDAO(LoanSubAccountDAO loanSubAccountDAO) {
		this.loanSubAccountDAO = loanSubAccountDAO;
	}

	public List<LoanSubAccount> getList(LoanSubAccount loanSubAccount) {
		List list=null;
		try {
			list=loanSubAccountDAO.getList(loanSubAccount);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return list;
	}

}
