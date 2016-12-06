package com.dx.payment.services.impl;

import java.util.HashMap;

import com.dx.payment.bean.LoanMainAccount;
import com.dx.payment.dao.LoanMainAccountDAO;
import com.dx.payment.services.LoanMainAccountBO;

import app.base.ServiceException;

public class LoanMainAccountBOImpl   implements
		LoanMainAccountBO {

	private LoanMainAccountDAO loanMainAccountDAO;

	public LoanMainAccount getById(String id) throws ServiceException {
		LoanMainAccount LoanMainAccount = null;
		try {
			LoanMainAccount = loanMainAccountDAO.getById(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return LoanMainAccount;
	}

	public void del(String id) throws ServiceException {
		try {
			loanMainAccountDAO.del(id);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void update(LoanMainAccount LoanMainAccount) throws ServiceException {
		try {
			loanMainAccountDAO.update(LoanMainAccount);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void insert(LoanMainAccount LoanMainAccount) throws ServiceException {
		try {
			loanMainAccountDAO.insert(LoanMainAccount);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}


	public LoanMainAccountDAO getLoanMainAccountDAO() {
		return loanMainAccountDAO;
	}

	public void setLoanMainAccountDAO(LoanMainAccountDAO loanMainAccountDAO) {
		this.loanMainAccountDAO = loanMainAccountDAO;
	}

	public LoanMainAccount viewMainAccount(LoanMainAccount mainAccount) {
		return loanMainAccountDAO.viewMainAccount(mainAccount);
	}

}
