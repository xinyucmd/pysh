package com.jiangchuanbanking.redeem.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.financing.dao.ICallDao;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.financing.service.ICallService;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.prod.dao.IProdDao;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.service.IProdService;
import com.jiangchuanbanking.redeem.dao.IRedeemDao;
import com.jiangchuanbanking.redeem.domain.RedeemEntity;
import com.jiangchuanbanking.redeem.service.IRedeemService;
import com.jiangchuanbanking.util.DateTimeUtil;

/**
 * Call service
 */
public class RedeemService extends BaseService<RedeemEntity> implements IRedeemService {

	private IRedeemDao redeemDao;
	private IBaseDao baseDao;
	
	
	
	
	
	public IRedeemDao getRedeemDao() {
		return redeemDao;
	}
	public void setRedeemDao(IRedeemDao redeemDao) {
		this.redeemDao = redeemDao;
	}
	public IBaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public RedeemEntity updateRedeem(String sts, String pactNo) {
		RedeemEntity re=(RedeemEntity) baseDao.findEntityByHql("from RedeemEntity where PACT_NO='"+pactNo+"' and STS='21'");
		if (re!=null) {
			re.setSts(sts);
			return (RedeemEntity) baseDao.makePersistent(re);
		}
		return null;
	}
	@Override
	public PlanBean updatePlan(String pactNo, Integer planBeanId,FinancingDetails fd) throws ParseException {
		PlanBean pb = new PlanBean();
		pb=(PlanBean) baseDao.getById(PlanBean.class, planBeanId);
		if (pb==null) {
			pb=(PlanBean) baseDao.findByHQL("from PlanBean where pact_no='"+pactNo+"' and if_settle='0' order by END_DATE desc").get(0);
		}			
		if (pb!=null) {
			pb.setIf_settle("1");
			pb.setReturn_amt(fd.getFlow_amt());		
			pb.setSettle_date(DateTimeUtil.getJDateToODate(fd.getFlow_date()));
			return (PlanBean) baseDao.makePersistent(pb);			
		}else{
			return null;
		}
	}
	@Override
	public RedeemEntity getRedeemByPactno(String pact_no) {
		RedeemEntity re=(RedeemEntity) baseDao.findEntityByHql("from RedeemEntity where PACT_NO='"+pact_no+"'");
		if (re!=null) {
			return (RedeemEntity) baseDao.makePersistent(re);
		}
		return re;
	}
	@Override
	public RedeemEntity updateRedeem1(String sts, String pactNo) {
		RedeemEntity re=(RedeemEntity) baseDao.findEntityByHql("from RedeemEntity where PACT_NO='"+pactNo+"'");
		if (re!=null) {
			re.setSts(sts);
			return (RedeemEntity) baseDao.makePersistent(re);
		}
		return null;
	}



}
