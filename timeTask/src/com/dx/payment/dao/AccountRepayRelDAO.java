package com.dx.payment.dao;

import java.util.List;
import java.util.Map;

import com.dx.payment.bean.AccountRepayRel;


public interface AccountRepayRelDAO {

	public AccountRepayRel getById(String id) ;

	public List<AccountRepayRel> findByPage(Map<String, Object> map)
			;

	public void del(String id) ;

	public void insert(AccountRepayRel rel) ;

	public void update(AccountRepayRel rel) ;

	public Integer getCount(AccountRepayRel rel);

	public List<AccountRepayRel> viewRel(AccountRepayRel accountRepayRel);

	public String getSeqNo();

}
