
package com.dx.loan.repay.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import app.base.ServiceException;
import app.batch.entity.CustRepayDetails;

import com.dx.common.SystemParm;
import com.dx.common.bean.PageBean;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.debt.bean.DebtBean;
import com.dx.loan.debt.services.impl.DebtServices;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanParmBean;
import com.dx.loan.plan.services.impl.PlanServices;
import com.dx.loan.plan.services.impl.PlanServicesImpl;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.DueBean;
import com.dx.loan.repay.bean.LnDue;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.dao.IRepayDao;
import com.dx.loan.repay.services.IRepayService;
import com.google.gson.Gson;
/**
 * 
 * 类名： PayServiceImpl
 * 描述：
 * @author 乾之轩
 * @date 2012-5-17 下午07:13:23
 * 
 *
 */
@WebService(endpointInterface = "com.dx.loan.repay.services.IRepayService")
public class PayServiceImpl implements IRepayService  {
	public  PayServiceImpl(){}
	
	private  IRepayDao repayDao;
	
	
	public void setRepayDao(IRepayDao repayDao) {
		this.repayDao = repayDao;
	}

	
/***
 * 获得还款实体
 * @param ratedayBean
 * @return
 */
	public RepayBean getRepayBean(RepayBean  repayBean) {
	  return	repayDao.getRepayBean(repayBean);
	}
/**
 * 
 * 方法描述： 
 * @param pageBean
 * 
 * @return
 * @author 乾之轩
 * @date 2012-5-17 下午07:23:51
 */
	public  String getRepayList(PageBean pageBean) {
		return toJSON(repayDao.getRepayList(pageBean),pageBean);
	}
	
	/**
	 * 
	 * 方法描述： 保存贷款主文件
	 * @param acLnMstBean
	 * @author 乾之轩
	 * @date 2012-5-30 下午07:03:06
	 */
	public void saveAcLnMstBean(AcLnMstBean acLnMstBean) {
			repayDao.saveAcLnMstBean(acLnMstBean);
	}
		/**
		 * 
		 * 方法描述：  获得贷款主文件
		 * @param acLnMstBean
		 * @return
		 * @author 乾之轩
		 * @date 2012-6-19 下午05:10:32
		 */
	public AcLnMstBean getAcLnMstBean(AcLnMstBean acLnMstBean) {
		return repayDao.getAcLnMstBean(acLnMstBean);
	} 	
		
		
	@SuppressWarnings("unchecked")
	private String toJSON(List<RepayBean> list, PageBean pageBean) {
		Map map = new HashMap();
		map.put("page", pageBean.getCurPage());
		map.put("total", pageBean.getTotalSize());
		List mapList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map cellMap = new HashMap();
			cellMap.put("dueNo", list.get(i).getDueNo());
			cellMap.put("cell", new Object[] {
					list.get(i).getDueNo(),
					list.get(i).getCifName(),
					list.get(i).getDueAmt(),
					list.get(i).getReturnCapital(),
					list.get(i).getReturnInterest(),
					list.get(i).getBegDate(),
					list.get(i).getEndDate(),
					list.get(i).getCifNo(),
					list.get(i).getTermNo(),
					list.get(i).getOtherFee()
					});
			mapList.add(cellMap);
		}
		map.put("rows", mapList);
		Gson gs=new Gson();
		String ja =gs.toJson(map);
		System.out.println(ja);
		return ja.toString();
	}

	
	
	/**
	 * 
	 * 方法描述： 获得某期还款计划的剩余本金  
	 * @param dueNo
	 * @param termNo
	 * @return
	 * @author 乾之轩
	 * @date 2012-5-23 下午06:02:53
	 */
	 public  String  remaCapital(PlanBean planBean){
		 String dueNo = planBean.getDueNo();
		 String termNo = planBean.getTermNo();
		 String capital = planBean.getReturnCapital();
		 String tempCapital = "0.00";
		 RepayBean repayBean = new RepayBean();
		 repayBean.setDueNo(dueNo);
		 repayBean.setTermNo(termNo);
		 List<RepayBean> repayHisBeans = repayDao.getRepayBeans(repayBean);
		 for(RepayBean repayHisBean:repayHisBeans){
			 tempCapital = BigNumberUtil.Add(tempCapital,repayHisBean.getReturnCapital());
		 }
		 String remaCapital = BigNumberUtil.Subtract(capital, tempCapital);
		 return remaCapital;
	 }
	 /**
	  * 
	  * 方法描述： 获得利息和(正常利息,逾期利息,复利利息) 
	  * void
	  * @author 乾之轩
	  * @date 2012-6-13 下午04:30:07
	  */
	 public String getTotalInterest(RatedayBean ratedayBean){
		 return BigNumberUtil.Add(ratedayBean.getInterest(),ratedayBean.getOverInterest(),ratedayBean.getCmpdInterest());
	 }
	 
/**
 * 
 * 方法描述： 获得还款历史
 * @param repayBean
 * @return
 * List<RepayBean>
 * @author 乾之轩
 * @date 2012-6-15 上午11:16:12
 */
	 public List<RepayBean> getRepayBeans(RepayBean repayBean){
		return  repayDao.getRepayBeans(repayBean);
	 }
	 public List<RepayBean> getRepayBeanss(RepayBean repayBean){
		return  repayDao.getRepayBeanss(repayBean);
	 }
	 /**
	  * 
	  * 方法描述： 保存还款 
	  * @param repayBean
	  * void
	  * @author 乾之轩
	  * @date 2012-6-15 下午02:58:44
	  */
	 // 欠款列表
	 List<DebtBean> myDebtBeans = new ArrayList<DebtBean>();
	 
	 // 重置后的还款实体
	 RepayBean  reRepayBean = null; 
	 // 偿还欠款列表
	 List<RepayBean> repayDabts = null;
	 
	 // 用于保存已经还完欠款的还款计划
	 List<PlanBean> planBeans = new ArrayList<PlanBean>();
	 
	 
	 
	 
	 /**
	  * 保存还款
	  */
	 public void  saveRepay(RepayBean repayBean){
		 	myDebtBeans.clear();
			// 获得该期还款是第几次还款
			int counts = 0;
			int termNo = Integer.parseInt(repayBean.getTermNo());
			RepayBean  tempRepayBean = new RepayBean();
			tempRepayBean.setDueNo(repayBean.getDueNo());
			tempRepayBean.setTermNo(repayBean.getTermNo());
			List<RepayBean> repayBeans = getRepayBeans(repayBean);
			if(repayBeans!=null && repayBeans.size()>0){
				counts = repayBeans.size()+1;
			}else{
				counts = 1;
			}
			// 设置是第几次还款
			repayBean.setCounts(String.valueOf(counts));
			// 设置记账标志
			repayBean.setAccFlag("0");  
			
			if(StringUtil.equals("0", repayBean.getPayType())){
				repayBean.setBankNo("");
			}
			
			if(StringUtil.equals("1", repayBean.getPayType()) && StringUtil.isEmpty(repayBean.getBankNo())){
				throw new IllegalArgumentException("选择银行转账时,银行代码不能为空!");
			}
			
		
			// 通过该方法给临时变量赋值
			reSetRepay(repayBean);
			
			// 判断本期还款是否产生欠款(当期还款总额小于实际应还款总额时就产生欠款)
			DebtBean debtBean = getDebtBean(repayBean, reRepayBean);
			
			// 说明该期还款计划有欠款
			if(debtBean!=null){
				 // 欠款服务类
				 DebtServices debtServices = SpringFactory.getBean("debtServices");
				 // 将该期应还但未还的部分保存到欠款表中
				 debtServices.saveDebtBean(debtBean);
				 // 获得还款计划服务类
				 PlanServices planServices = SpringFactory.getBean("planServices");
				 PlanBean planBean3 =  new PlanBean();
				 planBean3.setDueNo(repayBean.getDueNo());
				 planBean3.setTermNo(repayBean.getTermNo());
				 planBean3.setIsDebt("1");
				 // 根据借据号和期号更新该期还款计划的欠款标志位欠款
				 planServices.updatePlanBean(planBean3);
			}else{
				// 更新该期还款计划的状态为 3 欠款状态为0
				 // 获得还款计划服务类
				 PlanServices planServices = SpringFactory.getBean("planServices");
				 PlanBean planBean3 =  new PlanBean();
				 planBean3.setDueNo(repayBean.getDueNo());
				 planBean3.setTermNo(repayBean.getTermNo());
				 planBean3.setIsDebt("0");
				 planBean3.setState("3");
				 // 根据借据号和期号更新该期还款计划的欠款标志位欠款和还款计划状态
				 
				 planServices.updatePlanBean(planBean3);
			}
			if(reRepayBean!=null){
				repayBean.setReturnCapital(reRepayBean.getReturnCapital());
				repayBean.setReturnInterest(reRepayBean.getReturnInterest());
				repayBean.setCmpdInterest(reRepayBean.getCmpdInterest());
				repayBean.setOverInterest(reRepayBean.getOverInterest());
				repayBean.setOtherFee(reRepayBean.getOtherFee());
				// 将履约保证金设置为0
				repayBean.setPerfAmount("0.00");
			}

			 // 更新有欠款且欠款还完的还款计划的状态和欠款标志
			if(planBeans!=null && planBeans.size()>0){
				 PlanServices planServices = SpringFactory.getBean("planServices");
				 planServices.updatePlanList("1", "0", repayBean.getDueNo(), planBeans);
			}
			// 更新欠款表
			if(myDebtBeans!=null  &&  myDebtBeans.size()>0){
				DebtServices debtServices = SpringFactory.getBean("debtServices");
				debtServices.updateLoanDebtList(myDebtBeans);
			}
			  
			
			if(repayDabts!=null && repayDabts.size()>0){
				// 保存偿还的欠款
				repayDao.saveRepay(repayDabts);
			}
			// 判断是不是在改期结束日之前进行还款交易.true 是false  表示在改期结束日之后进行还款
			boolean  returnflag = DateUtil.gt( repayBean.getOccDate(),repayBean.getEndDate());
			
			// 返还履约保证金额的条件为,在没有预期的前提下还完本期应还金额,退还上一期的履约保证金
			// 如果没有预期,将履约保证金存入贷款主文件,可以在下期充当还款金额
			if(returnflag && debtBean==null){
				AcLnMstBean acLnMstBean = repayDao.getAcLnMstBeanByDueNo(repayBean.getDueNo());
				
				// 计算履约保证金额
				AcLnMstBean tempAcLnMstBean = new AcLnMstBean();
				tempAcLnMstBean.setDueNo(repayBean.getDueNo());
				// 更新履约保证金额
				if(termNo<13){
					// 原有履约保证金额减去本次还款冲值的履约保证金额加上本次返还的履约保证金额
					String perfAmount = acLnMstBean.getPerfAmount();
					perfAmount = BigNumberUtil.Subtract(perfAmount, repayBean.getPerfAmount());
					perfAmount = BigNumberUtil.Add(perfAmount, getPerfAmount(acLnMstBean));
					tempAcLnMstBean.setPerfAmount(perfAmount);
				}
				// 更新借据余额
				tempAcLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(),repayBean.getReturnCapital()));
				repayDao.updateAcLnMstBean(tempAcLnMstBean);
			}else{
				AcLnMstBean acLnMstBean = repayDao.getAcLnMstBeanByDueNo(repayBean.getDueNo());
				String perfAmount =  getPerfAmount(acLnMstBean);
				// 计算履约保证金额
				AcLnMstBean tempAcLnMstBean = new AcLnMstBean();
				tempAcLnMstBean.setDueNo(repayBean.getDueNo());
				// 更新借据余额
				tempAcLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(),repayBean.getReturnCapital()));
				repayDao.updateAcLnMstBean(tempAcLnMstBean);
			}
			
			// 保存当期的还款
			repayDao.saveRepay(repayBean);
		
			
			/****北京业务开始****/
			/**
			ReayService repayService = new ReayServiceImpl();
			app.creditapp.lnp.advrepay.entity.RepayBean parmRepayBean = new app.creditapp.lnp.advrepay.entity.RepayBean();
			parmRepayBean.setBal(Double.parseDouble(debtBean.getDebCapital()));
			parmRepayBean.setCif_name(repayBean.getCifName());
			parmRepayBean.setComp_intst(Double.parseDouble(debtBean.getDebCmpdintst()));
			// 借据金额
			parmRepayBean.setDue_amt(Double.parseDouble(repayBean.getDueAmt()));
			parmRepayBean.setDue_no(repayBean.getDueNo());
			// 正常利息减去已还利息
			parmRepayBean.setInner_intst(Double.parseDouble(debtBean.getDebIntst()));
			// 表外欠息设置为预期利息
			parmRepayBean.setOuter_intst(0.00);
			// 预期利息
			parmRepayBean.setOver_intst(Double.parseDouble(debtBean.getDebOverintst()));
			// 交易日期不用更新这里只是设置了一个值
			parmRepayBean.setTx_date("20120612");
			repayService.rapayService(parmRepayBean);
			**/
			/****北京业务结束****/
			
			
			
			
			
	 }
	 
	 /**
	  * 
	  * 方法描述：  还款顺序  	逾期罚息  上期账户管理费  当期罚息  当期账户管理费  剩余本金
	  * @param repayBean
	  * void 
	  * @author 乾之轩
	  * @date 2012-6-20 上午10:02:14
	  */
	 
	 private  void reSetRepay(RepayBean repayBean){
		 List<RepayBean> repayBeans = new ArrayList<RepayBean>();
		 // 获得还款总额(还款金额+担保金额)
		 String returnTotal = BigNumberUtil.Add(repayBean.getPerfAmount(),repayBean.getReturnAmt());
		 String tempReturnTotal = returnTotal;
		 // 欠款服务类
		 DebtServices debtServices = SpringFactory.getBean("debtServices");
		 // 获得欠款实体
		 DebtBean tempDebtBean = new DebtBean();
		 tempDebtBean.setDueNo(repayBean.getDueNo());
		 
		 List<DebtBean> debtBeans = debtServices.getDebtBeans(tempDebtBean);
		 Collections.sort(debtBeans);
		
		 
		 /********************** 欠款偿还开始********************************/
		 // 有欠款
		 if(debtBeans!=null && debtBeans.size()>0){
			 for (Iterator iterator = debtBeans.iterator(); iterator.hasNext();) {
				 DebtBean debtBean = (DebtBean) iterator.next();
				 //   保存偿还欠款后的欠款的余额
				 DebtBean myDebtBean =  new DebtBean();
				 
				 
				 myDebtBean.setDueNo(debtBean.getDueNo());
				 myDebtBean.setTermNo(debtBean.getTermNo());
				 
				 RepayBean tempRepayBean = new RepayBean();
				 // 设置借据号
				 tempRepayBean.setDueNo(debtBean.getDueNo());
				 // 设置是第几期还款计划
				 tempRepayBean.setTermNo(debtBean.getTermNo());
				 List<RepayBean> tempRepayBeans = getRepayBeans(tempRepayBean);
				 if(tempRepayBeans==null ||tempRepayBeans.size()==0){
					// 设置是该期还款计划的第几次还款
					 tempRepayBean.setCounts("1");
				  }else{
					 // 设置是该期还款计划的第几次还款
					 tempRepayBean.setCounts(String.valueOf(tempRepayBeans.size()+1));
				  }
				 // 记账标志设置为未记账
				 tempRepayBean.setAccFlag("0");
				 // 提前还款本金设置为0
				 tempRepayBean.setAdvaceAmt("0.00");
				 // 设置转账银行代码
				 tempRepayBean.setBankNo(repayBean.getBankNo());
				 // 设置本期还款计划开始日期
				 tempRepayBean.setBegDate(debtBean.getBegDate());
				 // 设置客户名称
				 tempRepayBean.setCifName(repayBean.getCifName());
				 // 设置客户号
				 tempRepayBean.setCifNo(debtBean.getCifNo());
				 // 设置借据金额
				 tempRepayBean.setDueAmt(repayBean.getDueAmt());
				
				 // 设置该期还款计划的结束日期
				 tempRepayBean.setEndDate(debtBean.getEndDate());
				 // 设置还款日期
				 tempRepayBean.setOccDate(SystemParm.SystemDate);
				 // 设置还款类型 转账 现金
				 tempRepayBean.setPayType(repayBean.getPayType());
				
				// 欠款逾期利息
				String debtOverInterest = debtBean.getDebOverintst();
				// 还款总额减去欠款逾期利息
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, debtOverInterest);
				if(returnTotal.startsWith("-")){
					// 还款总额小于欠款逾期利息,这时还款总额全部用于偿还欠款逾期利息
					tempRepayBean.setOverInterest(tempReturnTotal);
					returnTotal = "0.00";
					repayBeans.add(tempRepayBean);
					// 重新设置欠款逾期利息的金额时
					myDebtBean.setDebOverintst(BigNumberUtil.Subtract(debtOverInterest, tempReturnTotal));
					myDebtBeans.add(myDebtBean);
				    break;
				}else{
					myDebtBean.setDebOverintst("0.00");
					// 还款总额大于欠款逾期利息
					tempRepayBean.setOverInterest(debtOverInterest);
					tempReturnTotal = returnTotal;
				}
				
				
				// 欠款复利利息
				String debCmpdintst = debtBean.getDebCmpdintst();
				// 还款总额减去欠款逾期利息 减去复利利息后的剩余金额
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, debCmpdintst);
				
				if (returnTotal.startsWith("-")) {
					tempRepayBean.setCmpdInterest(tempReturnTotal);
					returnTotal = "0.00";
					repayBeans.add(tempRepayBean);
					// 此时欠的复利金额大于剩余的还款总额
					myDebtBean.setDebCmpdintst(BigNumberUtil.Subtract(debCmpdintst, tempReturnTotal));
					myDebtBeans.add(myDebtBean);
					break;
				} else {
					// 偿还完欠的复利利息
					myDebtBean.setDebCmpdintst("0.00");
					tempRepayBean.setCmpdInterest(debCmpdintst);
					tempReturnTotal = returnTotal;
				}
				
				// 欠款账户管理费
				String debAccFee =  debtBean.getDebAccFee();
				// 还款总额减去欠款逾期利息 减去复利利息  减去该期账户管理费后的剩余金额
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, debAccFee);
				if (returnTotal.startsWith("-")) {
					tempRepayBean.setOtherFee(tempReturnTotal);
					returnTotal = "0.00";
					myDebtBean.setDebAccFee(BigNumberUtil.Subtract(debAccFee ,tempReturnTotal));
					myDebtBeans.add(myDebtBean);
					repayBeans.add(tempRepayBean);
					break;
				} else {
					myDebtBean.setDebAccFee("0.00");
					tempRepayBean.setOtherFee(debAccFee);
					tempReturnTotal = returnTotal;
				}
				
				// 欠款利息
				String debIntst = debtBean.getDebIntst();
				// 还款总额减去欠款逾期利息 减去复利利息  减去该期账户管理费  减去利息后的剩余金额
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, debIntst);
				if (returnTotal.startsWith("-")) {
					tempRepayBean.setReturnInterest(tempReturnTotal);
					returnTotal = "0.00";
					myDebtBean.setDebIntst(BigNumberUtil.Subtract(debIntst ,tempReturnTotal));
					myDebtBeans.add(myDebtBean);
					repayBeans.add(tempRepayBean);
					break;
				} else {
					myDebtBean.setDebIntst("0.00");
					tempRepayBean.setReturnInterest(debIntst);
					tempReturnTotal = returnTotal;
				}
				
				// 欠款本金
				String capital = debtBean.getDebCapital();
				
				// 还款总额减去欠款逾期利息 减去复利利息  减去该期账户管理费  减去利息  减去欠款本金后的剩余金额
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, capital);
				if (returnTotal.startsWith("-")) {
					tempRepayBean.setReturnCapital(tempReturnTotal);
					returnTotal = "0.00";
					myDebtBean.setDebCapital(BigNumberUtil.Subtract( capital,tempReturnTotal));
					myDebtBeans.add(myDebtBean);
					repayBeans.add(tempRepayBean);
					break;
				} else {
					myDebtBean.setDebCapital("0.00");
					tempRepayBean.setReturnCapital(tempReturnTotal);
					tempReturnTotal = returnTotal;
				}
				
				PlanBean planBean = new PlanBean();
				planBean.setDueNo(debtBean.getDueNo());
				planBean.setTermNo(debtBean.getTermNo());
				// 这里存放和欠款相对应的还款计划,并且该欠款已经偿还完毕.需要更新还款计划的状态为3 欠款标志需要更新为0
				planBeans.add(planBean);
				
				myDebtBeans.add(myDebtBean);
				repayBeans.add(tempRepayBean);
			}
				tempReturnTotal = returnTotal;
				
		 }
		 // 将偿还的欠款存入临时变量
		 repayDabts = repayBeans;
		 /********************** 欠款偿还结束********************************/
		 
		 /**********************偿还当期开始********************************/
		 // 
		 RepayBean repayBean2  = new RepayBean();  
		 // 当期的逾期利息
		 String overInterest =  repayBean.getOverInterest();
		 
		 // 还款总额在还完欠款的前提下,减去当期逾期利息后剩余的金额
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, overInterest);
		if(returnTotal.startsWith("-")){
			repayBean2.setOverInterest(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		}else{
			repayBean2.setOverInterest(overInterest);
			tempReturnTotal = returnTotal;
		}
		
		 // 当期复利利息
		String cmpdInterest =  repayBean.getCmpdInterest();
		 // 还款总额在还完欠款的前提下,减去当期逾期利息  减去复利利息后剩余的金额
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, cmpdInterest);
		if (returnTotal.startsWith("-")) {
			repayBean2.setOverInterest(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		} else {
			repayBean2.setOverInterest(cmpdInterest);
			tempReturnTotal = returnTotal;
		}  
		
		
		// 当期账户管理费
		String otherFee = repayBean.getOtherFee();
		// 还款总额在还完欠款的前提下,减去当期逾期利息  减去复利利息  减去账户管理费后剩余的金额
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, otherFee);
		if (returnTotal.startsWith("-")) {
			repayBean2.setOtherFee(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		} else {
			repayBean2.setOtherFee(otherFee);
			tempReturnTotal = returnTotal;
		}
		
		
		// 当期利息
		String returnInterest =  repayBean.getReturnInterest();
		// 还款总额在还完欠款的前提下,减去当期逾期利息  减去复利利息  减去账户管理费 减去当期利息后剩余的金额
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, returnInterest);
		if (returnTotal.startsWith("-")) {
			repayBean2.setReturnInterest(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		} else {
			repayBean2.setReturnInterest(returnInterest);
			tempReturnTotal = returnTotal;
		}
	
		// 当期本金
		String returnCapital = repayBean.getReturnCapital();
		// 还款总额在还完欠款的前提下,减去当期逾期利息  减去复利利息  减去账户管理费 减去当期利息  减去当期本金后剩余的金额
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, returnCapital);
		
		if (returnTotal.startsWith("-")) {
			repayBean2.setReturnCapital(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		} else {
			repayBean2.setReturnCapital(returnCapital);
			tempReturnTotal = returnTotal;
		}
		reRepayBean = repayBean2;
		
		 /**********************偿还当期结束********************************/
	 } 
	 
	 /**
	  * 
	  * 方法描述：  用原还款实体的本金减去重置后的还款实体大于0则有欠款(其他字段如利息一次推导)
	  * @param repayBean   原还款实体    
	  * @param repayBean2     重置后的还款实体
	  * @return
	  * DebtBean
	  * @author 乾之轩
	  * @date 2012-6-21 上午09:10:45
	  */
	 private DebtBean getDebtBean(RepayBean repayBean,RepayBean repayBean2){
		 DebtBean debtBean = null;
		 // 原还款实体逾期利息 减去重置后逾期利息
		 Double overInterest =  Double.parseDouble(BigNumberUtil.Subtract(repayBean.getOverInterest(), repayBean2.getOverInterest()));
		 if (overInterest>0) {
			 debtBean = new DebtBean();
			 debtBean.setDebOverintst(overInterest.toString());
		 }
		 // 原还款实体复利利息减去重置后的复利利息
		 Double cmpdInterest = Double.parseDouble(BigNumberUtil.Subtract(repayBean.getCmpdInterest(),repayBean2.getCmpdInterest()));
		 if(cmpdInterest>0){
			 if(debtBean==null){
				 debtBean = new DebtBean();
			 }
			 debtBean.setDebCmpdintst(cmpdInterest.toString());
		 }
		 // 原账户管理费减去重置后的账户管理费
		 Double otherFee = Double.parseDouble(BigNumberUtil.Subtract(repayBean.getOtherFee(), repayBean2.getOtherFee()));
		 if(otherFee>0){
			 if(debtBean==null){
				 debtBean = new DebtBean();
			 }
			 debtBean.setDebAccFee(otherFee.toString());
		 }
		 //  原还款实体利息减去重置后的利息
		 Double returnInterest = Double.parseDouble(BigNumberUtil.Subtract(repayBean.getReturnInterest(),repayBean2.getReturnInterest()));
		 if(returnInterest>0){
			 if(debtBean==null){
				 debtBean = new DebtBean();
			 }
			 debtBean.setDebIntst(returnInterest.toString()); 
		 }
		 
		 // 原还款实体本金减去重置后的本金
		 Double  returnCapital= Double.parseDouble(BigNumberUtil.Subtract(repayBean.getReturnCapital(),repayBean2.getReturnCapital()));
		 if(returnCapital>0){
			 if(debtBean==null){
				 debtBean = new DebtBean();
			 }
			 debtBean.setDebCapital(returnCapital.toString());
		 }
		// 如果有欠款 
		 if (debtBean!=null) {
			debtBean.setDueNo(repayBean.getDueNo());
			debtBean.setTermNo(repayBean.getTermNo());
		}
		 return debtBean;
	 }
	 
	 
	 
	 /**
	  * 
	  * 方法描述： 提前还款业务处理 
	  * void
	  * @author 乾之轩
	  * @date 2012-6-18 上午11:03:33
	  */
	 public void saveAdvaceRepay(RepayBean repayBean){
		 PlanServicesImpl planServicesImpl = SpringFactory.getBean("planServicesImpl");
		 AcLnMstBean acLnMstBean = repayDao.getAcLnMstBeanByDueNo(repayBean.getDueNo());
		 PlanParmBean planParmBean = new PlanParmBean();
		 // 设置提前还款本金
		 planParmBean.setAdvaceAmt(repayBean.getAdvaceAmt());
		 // 设置还款计划(第一期)的开始日期
		 planParmBean.setBeginDate(repayBean.getBegDate());
		 // 固定还款日期
		 planParmBean.setFixDate(acLnMstBean.getFixDate());
		 // 0 不预先支付 1 预先支付 3 当做一期
		 planParmBean.setIsAdvance(acLnMstBean.getIsAdvance());
		 // 是否延期  0 不延期   1延期
		 planParmBean.setIsDelay(acLnMstBean.getIsDelay());
		 // 是否强制计划
		 planParmBean.setIsForce(acLnMstBean.getIsForce());
		 // 是否固定日还款 0 不是 1是
		 planParmBean.setIsFixDate(acLnMstBean.getIsFixDate());
		 // 还款方式
		 planParmBean.setReturnMentod(acLnMstBean.getReturnMethod());
		 
		 planServicesImpl.createPlan(repayBean.getDueNo(), planParmBean);
		 
	 }
	 
	 /**
	  * 
	  * 方法描述：获得履约保证金额 
	  * @return
	  * String
	  * @author 乾之轩
	  * @date 2012-6-19 下午04:35:04
	  */
	 private String  getPerfAmount(AcLnMstBean acLnMstBean){
		 // 借据金额
		 String dueAmt = acLnMstBean.getDueAmt();
		 // 每期收取担保金额比例
		 String perfAmountPerc =  acLnMstBean.getPerfAmountPerc();
		 String perfAmount =  BigNumberUtil.Multiply(dueAmt, perfAmountPerc);
		 perfAmount = BigNumberUtil.Divide(perfAmount, "100", 2, "1");
		 return perfAmount;
	 }
	 
	
	 

		public List<AcLnMstBean> getAcLnMstBeans(AcLnMstBean parmAcLnMstBean) {
			return repayDao.getAcLnMstList(parmAcLnMstBean);
		}


		public List<RepayBean> getDistinctRepayBeans(RepayBean repayBean) {
			return  repayDao.getDistinctRepayBeans(repayBean);
		}


		public LnDue getDueBean(LnDue dueBean) {
			return  repayDao.getDueBean(dueBean);
		}


		public List<PlanBean> getPlanBeans(String currDate) {
			return  repayDao.getPlanBeans(currDate);
		}


		public List<RepayBean> getRepayBeansOnSchedule(String dueNo) {
			return  repayDao.getRepayBeansOnSchedule(dueNo);
		}


		public List<PlanBean> getPlanBeanList(PlanBean planBean) {
			return  repayDao.getPlanBeanList(planBean);
		}


		public void updateREpayDetails(CustRepayDetails custRepayDetails) throws ServiceException {
			try {
				  repayDao.updateREpayDetails(custRepayDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}


		public void insertREpayDetails(CustRepayDetails custRepayDetails) throws ServiceException {
			try {
				  repayDao.insertREpayDetails(custRepayDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}


		public List<CustRepayDetails> findCustRepayDetailsList(CustRepayDetails custRepayDetails) {
			return  repayDao.findCustRepayDetailsList(custRepayDetails);
			
		}
		 
		public List<RepayBean>  getLastRepayList(){
			return repayDao.getLastRepayList();
		}
	
		 
		
		public void upLnDue(Map<String,String> parmMap){
			repayDao.upLnDue(parmMap);
		}
	 

		public List<AcLnMstBean> getAcLnMstBeansByReportPolicy(AcLnMstBean parmAcLnMstBean) {
			return repayDao.getAcLnMstBeansByReportPolicy(parmAcLnMstBean);
		}

	
	 
}
