package com.jiangchuanbanking.subscription.service;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.financing.domain.BankAccount;
import com.jiangchuanbanking.subscription.domain.ListSubscrip;
import com.jiangchuanbanking.subscription.domain.PactInfo;

public interface ISubscripService {

	 public SearchResult<ListSubscrip> getSubscripObjects(String clazz, String columns,
			SearchCondition searchCondition);
	 
	

	public String getPactNo(String prdt_no);

	public List getIncomeAmt(String pact_no);
	
	public BankAccount createBankAcc(PactInfo pactInfo);
	
	public PactInfo updatePact1(PactInfo pactInfo,String sts,String arrivaldate,String cmt) throws Exception;
	
	public PactInfo updatePact(PactInfo pactInfo,String sts,String arrivaldate,String pactNo) throws Exception;
	
	public String getLastDay(Date date) throws Exception;
	
	public List<PactInfo> closePacts(String pactNos);
	
	public void createMergerSub(String pactNos, SubAccount subAccount);
	
	public void updatePacts(String pactNos);
	
	public void updateContinuePacts(String pactNo);
	
}
