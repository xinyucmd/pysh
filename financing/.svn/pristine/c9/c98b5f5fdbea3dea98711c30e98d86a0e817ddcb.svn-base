package com.jiangchuanbanking.account.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.jiangchuanbanking.account.dao.ISubAccountDao;
import com.jiangchuanbanking.account.domain.MainAccount;
import com.jiangchuanbanking.account.domain.MergerInfo;
import com.jiangchuanbanking.account.domain.SubAccount;

import com.jiangchuanbanking.account.service.ISubAccountService;
import com.jiangchuanbanking.base.dao.IBaseDao;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.AuthenticationSuccessListener;
import com.jiangchuanbanking.util.BigNumberUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.DateTimeUtil;
import com.opensymphony.xwork2.ActionContext;


/**
 * Call service
 */
public class SubAccountService extends BaseService implements ISubAccountService {

	private ISubAccountDao subAccountDao;
	private IBaseService baseService;
	private IBaseDao baseDao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	public ISubAccountDao getSubAccountDao() {
		return subAccountDao;
	}

	public void setSubAccountDao(ISubAccountDao subAccountDao) {
		this.subAccountDao = subAccountDao;
	}

    

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public SubAccount creatSubAccount(PactInfo pi) throws Exception {
	
		String hql="from MainAccount where CIF_NO = "+pi.getCustomer().getId();
		List<MainAccount> list=baseService.findByHQL(hql);
		MainAccount ma=list.get(0);
		
		SubAccount sa=new SubAccount();
		
		sa.setSub_no(ma.getAccount_no()+String.format("%03d", Integer.parseInt(subAccountDao.getSubNo())));
		
		sa.setMainAccount(ma);
		sa.setPact_no(pi.getPact_no());
		sa.setCif_no(pi.getCustomer().getId());
		sa.setCif_name(pi.getCustomer().getCif_name());
		//sa.setCash_amt(pi.getCash_amt().toString());
		sa.setRate(pi.getRate());
		sa.setPrdt_no(pi.getPrdt_no());
		sa.setPrdt_name(pi.getPrdt_name());
		sa.setTerm(pi.getTerm_range());
		sa.setPayment_type(pi.getPayment_type());
		sa.setIf_wxd(pi.getIf_wxd());
		sa.setOpen_date(sdf.format(new Date()));
		sa.setOpen_op(this.getLoginUser().getName());
		sa.setOpen_id(CommonUtil.getLoginUserId());
		sa.setSts("11");
		return (SubAccount) baseDao.makePersistent(sa);

	}


	protected User getLoginUser() {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		User loginUser = (User) session
				.get(AuthenticationSuccessListener.LOGIN_USER);
		return loginUser;
	}

	@Override
	public void findSubAccount(PactInfo pi) throws Exception {
		
		String hql="from MainAccount";
		List<MainAccount> list=baseService.findByHQL(hql);
		
		
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	

	@Override
	public SubAccount updateSubAccount(PactInfo pi,String sts) throws Exception {
		
		SubAccount sa=(SubAccount) baseDao.findEntityByHql("from SubAccount where PACT_NO='"+pi.getPact_no()+"'");
		if (sa==null) {
			return null;
		}
		sa.setSts(sts);
		if (pi.getStart_date()!=null) {
			sa.setStart_date(DateTimeUtil.getDateString(pi.getStart_date()));
		}
		if (pi.getCash_amt()!=null) {
			sa.setCash_amt(pi.getCash_amt().toString());
		}
		if (pi.getIncome_amt()!=null) {
			sa.setRenew_amt(pi.getIncome_amt().toString());
		}
		if (sa.getIncome_amt()==null) {
			String interestTall = BigNumberUtil.Multiply(BigNumberUtil.add2String(sa.getCash_amt(), sa.getRenew_amt()), BigNumberUtil.Divide2(sa.getRate(), "100"));
			//12个月除以实际期数
			String n=BigNumberUtil.Divide2("12",sa.getTerm());
			//实际收益
			String interest=BigNumberUtil.Divide2(interestTall, n);
		
			sa.setIncome_amt(interest);
		}
		sa.setRedeem_amt("0");
		
		
		
		if ("22".equals(sts)) {
			sa.setEnd_date(DateTimeUtil.getNowDateString());
			sa.setClose_date(DateTimeUtil.getNowDateString());
			sa.setClose_op(CommonUtil.getLoginUserName());
		}
		return (SubAccount) baseService.makePersistent(sa);
	}

	@Override
	public List<SubAccount> closeSubAccounts(String pactNos) throws Exception {
		List<SubAccount> sa=baseService.findByHQL("from SubAccount where PACT_NO in (pactNos)");
		List<SubAccount> sa2=new ArrayList<SubAccount>();
		for (SubAccount subAccount : sa) {
			subAccount.setClose_date(DateTimeUtil.getNowDateString());
			subAccount.setClose_op(CommonUtil.getLoginUserName());
			subAccount.setSts("21");
			sa2.add(subAccount);
		}
		baseService.batchUpdate(sa2);	
		return sa2;
	}

	@Override
	public Double getRenoAmt(String pactNos) throws Exception {
		List<SubAccount> sa=baseService.findByHQL("from SubAccount where PACT_NO in (pactNos)");
		Double renoAmt=0d;
		for (SubAccount subAccount : sa) {
			Double bj=CommonUtil.strToDouble(subAccount.getCash_amt())+CommonUtil.strToDouble(subAccount.getRenew_amt());
			Double ll=(CommonUtil.strToDouble(subAccount.getTerm())/12)*(CommonUtil.strToDouble(subAccount.getRate())/100);
			renoAmt+=bj*(1+ll)-CommonUtil.strToDouble(subAccount.getRedeem_amt());
		}
		return renoAmt;
	}

	@Override
	public SubAccount updateSubRedeem(PactInfo pi, String sts,
			FinancingDetails fd) throws Exception {
		SubAccount sa=(SubAccount) baseDao.findEntityByHql("from SubAccount where PACT_NO='"+pi.getPact_no()+"'");
		if (sa!=null) {
			String redeemAmt=BigNumberUtil.add2String(sa.getRedeem_amt(), fd.getFlow_amt());
			sa.setRedeem_amt(redeemAmt);
			List<PlanBean> pb=baseDao.findByHQL("from PlanBean where PACT_NO='"+pi.getPact_no()+"' and IF_SETTLE='0'");
			if (pb.size()==0||pb==null) {
				sa.setSts(sts);
				sa.setClose_date(DateTimeUtil.getNowDateString());
				sa.setClose_op(CommonUtil.getLoginUserName());
			}
			sa=(SubAccount) baseDao.makePersistent(sa);
		}
		return sa;
		
	}



	




}
