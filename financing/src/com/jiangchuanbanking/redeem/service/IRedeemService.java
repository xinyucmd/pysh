package com.jiangchuanbanking.redeem.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.redeem.domain.RedeemEntity;

/**
 * Call service Interface
 */
public interface IRedeemService extends IBaseService<RedeemEntity> {
	public RedeemEntity getRedeemByPactno(String pact_no);
	
	public RedeemEntity updateRedeem(String sts,String pactNo);
	
	public RedeemEntity updateRedeem1(String sts,String pactNo);

	
	public PlanBean updatePlan(String pactNo,Integer planBeanId,FinancingDetails fd) throws ParseException;
	
}
