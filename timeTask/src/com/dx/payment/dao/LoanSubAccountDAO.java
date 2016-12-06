package com.dx.payment.dao;

import java.util.List;
import java.util.Map;

import com.dx.payment.bean.LoanSubAccount;


public interface LoanSubAccountDAO {

	public LoanSubAccount getById(String id) ;

	public List<LoanSubAccount> findByPage(Map<String, Object> map)
			;

	public void del(String id) ;

	public void insert(LoanSubAccount account) ;

	public void update(LoanSubAccount account) ;

	public Integer getCount(LoanSubAccount account);

	public List getList(LoanSubAccount loanSubAccount);

}
