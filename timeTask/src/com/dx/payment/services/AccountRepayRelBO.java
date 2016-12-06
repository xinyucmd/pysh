package com.dx.payment.services;

import java.util.List;

import com.dx.payment.bean.AccountRepayRel;


public interface AccountRepayRelBO {

	List<AccountRepayRel> viewRel(AccountRepayRel accountRepayRel);

	String findSeqNo();

	void inset(AccountRepayRel parmAccountRepayRel);

}
