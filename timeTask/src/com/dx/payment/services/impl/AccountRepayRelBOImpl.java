package com.dx.payment.services.impl;

import java.util.List;

import com.dx.payment.bean.AccountRepayRel;
import com.dx.payment.dao.AccountRepayRelDAO;
import com.dx.payment.services.AccountRepayRelBO;

import app.base.ServiceException;

@SuppressWarnings("serial")
public class AccountRepayRelBOImpl extends ServiceException implements AccountRepayRelBO {
private AccountRepayRelDAO accountRepayRelDAO;

public AccountRepayRelDAO getAccountRepayRelDAO() {
	return accountRepayRelDAO;
}

public void setAccountRepayRelDAO(AccountRepayRelDAO accountRepayRelDAO) {
	this.accountRepayRelDAO = accountRepayRelDAO;
}

public List<AccountRepayRel> viewRel(AccountRepayRel accountRepayRel) {
	return accountRepayRelDAO.viewRel(accountRepayRel);
}

public String findSeqNo() {
	return accountRepayRelDAO.getSeqNo();
}

public void inset(AccountRepayRel parmAccountRepayRel) {
	accountRepayRelDAO.insert(parmAccountRepayRel);	
}
}
