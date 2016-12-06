package com.dx.payment.dao;

import java.util.List;
import java.util.Map;

import com.dx.payment.bean.LoanMainAccount;


public interface LoanMainAccountDAO {

	public LoanMainAccount getById(String id) ;

	public List<LoanMainAccount> findByPage(Map<String, Object> map)
			;

	public void del(String id) ;

	public void insert(LoanMainAccount account) ;

	public void update(LoanMainAccount account) ;

	public Integer getCount(LoanMainAccount account);

	public LoanMainAccount viewMainAccount(LoanMainAccount mainAccount);

}
