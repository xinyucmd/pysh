package com.dx.payment.dao;

import java.util.List;
import java.util.Map;

import com.dx.payment.bean.LoanRepayHis;


public interface LoanRepayHisDAO {

	public LoanRepayHis getById(String id) ;

	public List<LoanRepayHis> findByPage(Map<String, Object> map)
			;

	public void del(String id) ;

	public void insert(LoanRepayHis his) ;

	public void update(LoanRepayHis his) ;

	public Integer getCount(LoanRepayHis his);

	public List<LoanRepayHis> viewHis(LoanRepayHis loanRepayHis);

}
