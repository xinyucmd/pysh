package com.jiangchuanbanking.flowdetails.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.account.domain.MergerInfo;
import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.alert.dao.IAlertItemDao;
import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.alert.service.IAlertItemService;
import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.financing.dao.ICallDao;
import com.jiangchuanbanking.financing.domain.Call;
import com.jiangchuanbanking.financing.service.ICallService;
import com.jiangchuanbanking.flowdetails.dao.IFinancingDetailsDao;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.flowdetails.service.IFinancingDetailsService;
import com.jiangchuanbanking.prod.dao.IProdDao;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.service.IProdService;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.AuthenticationSuccessListener;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.DateTimeUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * Call service
 */
public class FinancingDetailsService extends BaseService<FinancingDetails> implements IFinancingDetailsService {

	private IFinancingDetailsDao financingDetailsDao;
	private IBaseDao baseDao;

	public List<FinancingDetails> findScheduleEntitys(FinancingDetails details)
			throws Exception {
		return  financingDetailsDao.findScheduleEntitys(details);
	}

	public IFinancingDetailsDao getFinancingDetailsDao() {
		return financingDetailsDao;
	}

	public void setFinancingDetailsDao(IFinancingDetailsDao financingDetailsDao) {
		this.financingDetailsDao = financingDetailsDao;
	}

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void creatDetail(SubAccount sub,String type,String amt,FinancingDetails fd) {
		if (fd==null) {
			 fd=new FinancingDetails();
		}
		fd.setAccount_no(sub.getMainAccount().getAccount_no());
		fd.setSub_no(sub.getSub_no());
		fd.setFlow_type(type);
		fd.setFlow_amt(amt);
		if (fd.getFlow_date()==null) {
			fd.setFlow_date(DateTimeUtil.getDateString(new Date()));
		}
		fd.setOp_no(CommonUtil.getLoginUserName());
		fd.setOpen_id(CommonUtil.getLoginUserId());
		baseDao.makePersistent(fd);
	}

	protected User getLoginUser() {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		User loginUser = (User) session
				.get(AuthenticationSuccessListener.LOGIN_USER);
		return loginUser;
	}

	@Override
	public void creatDetails(SubAccount sub) {
		List<MergerInfo> list=baseDao.findByHQL("from MergerInfo where NEW_SUB_NO='"+sub.getSub_no()+"'");
		for (MergerInfo mergerInfo : list) {
			SubAccount sa=(SubAccount) baseDao.findEntityByHql("from SubAccount where SUB_NO='"+mergerInfo.getSub_no()+"'");
			Double cash_amt=CommonUtil.strToDouble(sa.getCash_amt());
			Double renew_amt=CommonUtil.strToDouble(sa.getRenew_amt());
			Double rate=CommonUtil.strToDouble(sa.getRate());
			Double redeem_amt=CommonUtil.strToDouble(sa.getRedeem_amt());
			Double term=CommonUtil.strToDouble(sa.getTerm());
			Double incomeAmt=(cash_amt+renew_amt)*(1+(rate/12*term)/100)-redeem_amt;
			
			FinancingDetails fd=new FinancingDetails();
			fd.setAccount_no(sa.getMainAccount().getAccount_no());
			fd.setSub_no(sa.getSub_no());
			fd.setFlow_type("12");
			fd.setFlow_amt(incomeAmt.toString());
			fd.setFlow_date(DateTimeUtil.getNowDateString());
			fd.setOp_no(CommonUtil.getLoginUserName());
			baseDao.makePersistent(fd);
		}
			
		
	}

	@Override
	public FinancingDetails getCashDetail(PactInfo pactInfo) {	
		SubAccount sa=(SubAccount) baseDao.findEntityByHql("from SubAccount where PACT_NO ='"+pactInfo.getPact_no()+"'");
		FinancingDetails fd=(FinancingDetails) baseDao.findEntityByHql("from FinancingDetails where SUB_NO='"+sa.getSub_no()+"' and FLOW_TYPE='21'");
		return fd;
	}

	@Override
	public void deleteDetail(PactInfo pactInfo) {
		SubAccount sa=(SubAccount) baseDao.findEntityByHql("from SubAccount where PACT_NO ='"+pactInfo.getPact_no()+"'");
		List<FinancingDetails> listFd= baseDao.findByHQL("from FinancingDetails where SUB_NO='"+sa.getSub_no()+"' and (FLOW_TYPE='21' or FLOW_TYPE='22')");
		if (listFd!=null&&listFd.size()>0) {
			for (FinancingDetails fd : listFd) {
				baseDao.deleteEntity(FinancingDetails.class, fd.getFlow_no());
			}		
		}	
	}

	@Override
	public void creatDetail(SubAccount sub, String type, FinancingDetails fd) {
		if (fd==null) {
			 fd=new FinancingDetails();
		}
		fd.setAccount_no(sub.getMainAccount().getAccount_no());
		fd.setSub_no(sub.getSub_no());
		fd.setFlow_type(type);
		fd.setFlow_amt(fd.getFlow_amt());
		if (fd.getFlow_date()==null) {
			fd.setFlow_date(DateTimeUtil.getDateString(new Date()));
		}
		fd.setOp_no(CommonUtil.getLoginUserName());
		fd.setOpen_id(CommonUtil.getLoginUserId());
		baseDao.makePersistent(fd);
	}




}
