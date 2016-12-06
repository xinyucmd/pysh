package com.jiangchuanbanking.subscription.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.account.domain.MergerInfo;
import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.financing.domain.BankAccount;
import com.jiangchuanbanking.subscription.dao.ISubscripDao;
import com.jiangchuanbanking.subscription.domain.ListSubscrip;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.subscription.service.ISubscripService;
import com.jiangchuanbanking.util.BigNumberUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.DateTimeUtil;

public class SubscripService extends BaseService implements ISubscripService{

	private ISubscripDao subscripDao;

	@Override
	public SearchResult<ListSubscrip> getSubscripObjects(String clazz,
			String columns, SearchCondition searchCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	

	


	public ISubscripDao getSubscripDao() {
		return subscripDao;
	}






	public void setSubscripDao(ISubscripDao subscripDao) {
		this.subscripDao = subscripDao;
	}






	public String getPactNo(String prodNo) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
		String dateStr = sdf.format(new Date());	
		return prodNo+dateStr+String.format("%03d", Integer.parseInt(subscripDao.getPactNo()));
	
	}












	@Override
	public BankAccount createBankAcc(PactInfo pactInfo) {
		BankAccount ba=(BankAccount) subscripDao.findEntityByHql("from BankAccount where BANK_ACCOUNT_NO='"+pactInfo.getAccount_no()+"'");
		if (ba==null) {
			//this.executeSQL("update WEALTH_BANK_ACCOUNT set STS='1' where STS='0' and id='"+pactInfo.getCustomer().getId()+"'", null);
			BankAccount bankAccount=new BankAccount();
			bankAccount.setBank_account_name(pactInfo.getAccount_name());
			bankAccount.setBank_account_no(pactInfo.getAccount_no());
			bankAccount.setBank_account_addr(pactInfo.getAccount_bank());
			bankAccount.setSts("0");
			bankAccount.setUpdata_time(DateTimeUtil.getNowDateString());
			bankAccount.setOp_no(CommonUtil.getLoginUserName());
			bankAccount.setOpen_id(CommonUtil.getLoginUserId());
			bankAccount.setCustomer(pactInfo.getCustomer());
			return (BankAccount) this.makePersistent(bankAccount);
			
		}
		
		return ba;
	}






	@Override
	public PactInfo updatePact1(PactInfo pactInfo, String sts,String arrivaldate,String cmt) throws Exception {
		PactInfo pi=(PactInfo) subscripDao.getEntityById(PactInfo.class, pactInfo.getId());
		//债权合同号
		if (pactInfo.getClaims_pact_no()!=null) {
			pi.setClaims_pact_no(pactInfo.getClaims_pact_no());
		}
		
		pi.setSts(sts);
		pi.setCmt(cmt);
		//开始时间、结束时间
		if (arrivaldate!=null&&!"".equals(arrivaldate)) {
	        pi.setStart_date(this.getNextDay(arrivaldate));
	        pi.setEnd_date(this.getEndDay(pi.getStart_date(), pactInfo.getTerm_range()));
		}
		
		return (PactInfo) this.makePersistent(pi);
	}

	//计算开始时间，到账日期的下一天
	public Date getNextDay(String arrivaldate) throws Exception{	
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");    
		Date date=sdf.parse(arrivaldate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        return c.getTime();
	}
	
	//计算结束时间，开始时间加期数
	public Date getEndDay(Date startDate,String range) throws Exception{
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.MONTH, Integer.parseInt(range));	
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		return c.getTime();	
	}
	
	public String getLastDay(Date date) throws Exception{	
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");    
		Calendar c = Calendar.getInstance();
		c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day -1);
        return sdf.format(c.getTime());
	}

    
	public Date getNextDay(String arrivaldate,String practNo) throws Exception{
		
		SubAccount sa=(SubAccount) this.findByHQL("from SubAccount where PACT_NO='"+practNo+"'").get(0);
		List<MergerInfo> listMe=this.findByHQL("from MergerInfo where NEW_SUB_NO ='"+sa.getSub_no()+"'");
		String pactNos="";
		for (MergerInfo m : listMe) {
			SubAccount sub=(SubAccount) this.findByHQL("from SubAccount where SUB_NO='"+m.getSub_no()+"'").get(0);
			pactNos=pactNos+"'"+sub.getPact_no()+"',";
		}
		pactNos=pactNos.substring(0, pactNos.length()-1);	
		
		List<PactInfo> list=this.findByHQL("from PactInfo where PACT_NO in("+pactNos+")");
		Date startDate=list.get(0).getEnd_date();
		for (PactInfo p : list) {
			if (p.getEnd_date().after(startDate)) {
				startDate=p.getEnd_date();
			}
		}
		if (arrivaldate!=null&&!"".equals(arrivaldate)) {
			SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");    
			Date date=sdf.parse(arrivaldate);
			if (date.after(startDate)) {
				startDate=date;
			}
		}		
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        return c.getTime();		
	}




	@Override
	public List<PactInfo> closePacts(String pactNos) {
		List<PactInfo> list=this.findVOByHQL("from PactInfo where PACT_NO in (pactNos)");
		List<PactInfo> list2=new ArrayList<PactInfo>();
		for (PactInfo pactInfo : list) {
			pactInfo.setSts("9");
			list2.add(pactInfo);
		}
		this.batchUpdate(list2);
		return list2;
	}



	@Override
	public void createMergerSub(String pactNos, SubAccount subAccount) {
		List<SubAccount> list=this.findByHQL("from SubAccount where PACT_NO in ("+pactNos+")");
		List<MergerInfo> list2=new ArrayList<MergerInfo>();
		for (SubAccount sa : list) {
			MergerInfo mi=new MergerInfo();
			mi.setSub_no(sa.getSub_no());
			mi.setNew_sub_no(subAccount.getSub_no());
			list2.add(mi);
		}
		this.batchUpdate(list2);
		
	}






	@Override
	public PactInfo updatePact(PactInfo pactInfo, String sts,
			String arrivaldate, String pactNo) throws Exception {
		PactInfo pi=(PactInfo) subscripDao.getEntityById(PactInfo.class, pactInfo.getId());
		//债权合同号
		if (pactInfo.getClaims_pact_no()!=null) {
			pi.setClaims_pact_no(pactInfo.getClaims_pact_no());
		}
		
		pi.setSts(sts);
		//开始时间、结束时间
		if (pactNo!=null&&!"".equals(pactNo)) {
	        pi.setStart_date(this.getNextDay(arrivaldate,pactNo));
	        pi.setEnd_date(this.getEndDay(pi.getStart_date(), pactInfo.getTerm_range()));
		}
		return (PactInfo) this.makePersistent(pi);
	}






	@Override
	public List getIncomeAmt(String pact_no) {
		List<String> list=new ArrayList<String>();
		SubAccount sa=(SubAccount) subscripDao.findEntityByHql("from SubAccount where pact_no='"+pact_no+"'");
		if (sa==null) {
			return list;
		}
		if (sa.getCash_amt()==null) {
			sa.setCash_amt("0");
		}
		if (sa.getRenew_amt()==null) {
			sa.setRenew_amt("0");
		}
		if (sa.getRedeem_amt()==null) {
			sa.setRedeem_amt("0");
		}
	
		list.add(sa.getIncome_amt());
		list.add(sa.getRedeem_amt());	
		return list;
	}






	@Override
	public void updatePacts(String pactNos) {
		subscripDao.executeSQL("update WEALTH_PACT_INFO set IF_ALREADY_CONTI='1' where PACT_NO in ("+pactNos+")", null);
	}

	@Override
	public void updateContinuePacts(String pactNo) {
		SubAccount sa=  (SubAccount) subscripDao.findEntityByHql("from SubAccount where PACT_NO='"+pactNo+"'");
		List<MergerInfo> listMerger=  subscripDao.findByHQL("from MergerInfo where NEW_SUB_NO='"+sa.getSub_no()+"'");
	    for (MergerInfo mergerInfo : listMerger) {
	    	SubAccount saa=  (SubAccount) subscripDao.findEntityByHql("from SubAccount where SUB_NO='"+mergerInfo.getSub_no()+"'");
	    	subscripDao.executeSQL("update WEALTH_PACT_INFO set IF_ALREADY_CONTI='2' where PACT_NO='"+saa.getPact_no()+"'", null);
		}
	}






    
    






	
	





	
	

	
	
}
