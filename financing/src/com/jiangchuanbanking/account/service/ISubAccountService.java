package com.jiangchuanbanking.account.service;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.subscription.domain.PactInfo;

/**
 * Call service Interface
 */
public interface ISubAccountService {
	/**
	 * Finds scheduled calls
	 * 
	 * @param startDate
	 *            Schedule start date
	 * @return scheduled calls
	 */

	
	public SubAccount creatSubAccount(PactInfo pi) throws Exception;
	
	public void findSubAccount(PactInfo pi) throws Exception;
	
	public SubAccount updateSubRedeem(PactInfo pi,String sts,FinancingDetails fd) throws Exception;
	
	public SubAccount updateSubAccount(PactInfo pi,String sts) throws Exception;

	public List<SubAccount> closeSubAccounts(String pactNos) throws Exception;
     
	public Double getRenoAmt(String pactNos) throws Exception; 
}
