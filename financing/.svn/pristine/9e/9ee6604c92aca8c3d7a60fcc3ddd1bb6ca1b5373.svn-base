package com.jiangchuanbanking.flowdetails.service;

import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.subscription.domain.PactInfo;

/**
 * Call service Interface
 */
public interface IFinancingDetailsService extends IBaseService<FinancingDetails> {
	/**
	 * 获取想要的
	 */
	public List<FinancingDetails> findScheduleEntitys(FinancingDetails detals) throws Exception;
	
	public void creatDetail(SubAccount sub,String type,String amt,FinancingDetails fd);
	
	public void creatDetail(SubAccount sub,String type,FinancingDetails fd);
	
	public void creatDetails(SubAccount sub);
	
	public FinancingDetails getCashDetail(PactInfo pactInfo);
	
	public void deleteDetail(PactInfo pactInfo);
}
