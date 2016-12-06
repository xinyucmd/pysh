/**
 * Copyright (C) DXHM 版权所有
 * 文件名： DayRate.java
 * 包名： timetask
 * 说明：
 * @author 乾之轩
 * @date 2012-6-11 下午04:37:13
 * @version V1.0
 */ 
package com.dx.timetask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dx.back.systemdate.bean.SystemDateBean;
import com.dx.back.systemdate.services.impl.SystemDateServices;
import com.dx.collect.bean.CollectFeePolicy;
import com.dx.collect.service.CollectFeeService;
import com.dx.common.SystemParm;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.dayrate.services.impl.DayRateServices;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.services.impl.PlanServices;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.services.IRepayService;
import com.dx.loan.repay.services.impl.RepayService;

/**
 * 类名： DayRate
 * 描述：利息日终计算
 * @author 乾之轩
 * @date 2012-6-11 下午04:37:13
 */
public class EveryDayRate extends QuartzJobBean {
	private Log log = LogFactory.getLog(getClass());
	
	/**
	 * 最新批量
	 * @param arg0
	 * @throws JobExecutionException
	 */
	protected void executeInternal(JobExecutionContext arg0)	throws JobExecutionException {
		// 系统日期格式化
		 String currDate = null;
		 IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		 // 还款计划服务类
		 PlanServices planServices = SpringFactory.getBean("planServices");
		 // 日终处理服务类
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 // 系统日期服务类
		 SystemDateServices systemDateServices = SpringFactory.getBean("systemDateServices");
		 // 催收费规则
		 CollectFeeService collectFeeService = SpringFactory.getBean("collectFeeService");
		 // 还款计划列表
		 List<PlanBean> planBeans = null;
		 // 计算罚息的开始日期
		 String beginDate = null;
		 
		 //是否计算罚息的标志
		 boolean sqfx  = false;
		 // 标识上次是还的那一期还款计划
		 String termNo = "1";
		
		// 更新系统日期
		SystemDateBean systemDateBean = new SystemDateBean();
		// 北京日期格式
		systemDateBean.setLastDate(SystemParm.SystemDate.replace("-", ""));
		String systemDate = DateUtil.addByDay(SystemParm.SystemDate.trim(), 1, DateUtil.DATE_FORMAT_);
		
		currDate = systemDate;
		systemDateBean.setNowDate(systemDate.replace("-", ""));
		
		SystemParm.SystemDate = systemDate;
		
		AcLnMstBean parmAcLnMstBean = new AcLnMstBean();
		parmAcLnMstBean.setDueState("1");
		//parmAcLnMstBean.setDueNo("JC2003703022014122516581001");
		// 查询出有那些借据需要进行批量处理
		List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
		if(acLnMstBeans==null  || acLnMstBeans.size()==0){
			return;
		}

		for(AcLnMstBean acLnMstBean :acLnMstBeans){
			 termNo="1";
			 RatedayBean ratedayBean = new  RatedayBean();
			 // 借据号
			 String dueNo = acLnMstBean.getDueNo();
			 String dueBal = "0.00";
			 dueBal = StringUtil.KillNull(acLnMstBean.getDueBal(),"0.00");
			 
			 if(StringUtil.isEmpty(dueNo)){
				// log.error("借据号不能为空!");
				 continue;
			 }
			 PlanBean    planBean1 = new PlanBean();
			 planBean1.setDueNo(dueNo);
			 planBeans = planServices.getPlanBeanList(planBean1);
			 
			 RepayBean parmRepayBean = new RepayBean();
			 parmRepayBean.setDueNo(dueNo);
			 List<RepayBean> repayBeans = repayServiceImpl.getRepayBeans(parmRepayBean);
			 
			 // 获取当前要操作的还款计划
			 PlanBean parmPlanBean = new PlanBean();
			 parmPlanBean.setDueNo(dueNo);
			 //如果超过了合同的结束日期并且还没有偿还完借款
			 
			 if(DateUtil.gt(acLnMstBean.getDueEndDate(), currDate)){
				 parmPlanBean.setBegDate(acLnMstBean.getDueEndDate());
				 parmPlanBean.setEndDate(acLnMstBean.getDueEndDate());
			 }else{
				 parmPlanBean.setBegDate(currDate);
				 parmPlanBean.setEndDate(currDate);
			 }
			      
			 // 处理只有一期的还款
			 PlanBean planBean= planServices.getPlanBean(parmPlanBean);
			 if(planBeans.size()>1){
				 if(planBean==null){
					 continue;
				 }	 
			 }
			if (planBeans.size()<1) {
				 continue;
			}
			 //设置计算罚息的开始日期
			 
			 // 如果上次还款日期不为空则将罚息计算的开始日期设置为上次的还款日期.
			 if(StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())){
				 beginDate =  acLnMstBean.getLastReturnDate();
			 }
			 
			 if(repayBeans!=null && repayBeans.size()>0){
				 beginDate = repayBeans.get(0).getOccDate();
				 termNo =  repayBeans.get(0).getTermNo();
				 termNo =  getOverLastTerm(repayBeans,planBeans);
			 }
			 
			 if(beginDate==null){
				 PlanBean parmPlanBean0 = new PlanBean();
				 parmPlanBean0.setDueNo(dueNo);
				 parmPlanBean0.setTermNo("1");
				// PlanBean planBean0= planServices.getPlanBean(parmPlanBean);
				 PlanBean planBean0= planServices.getPlanBean(parmPlanBean0);
				 System.out.println(dueNo);
				 if (planBean0!=null) {
					 beginDate = planBean0.getEndDate();
				}
				 
			 }
			 if(beginDate == null){
				 continue;
			 }
			 
			
			 //判断是否需要计算罚息(本金结余或利息结余为负数或者当期的还款计划的结束日期小于当前的系统日期)
			 
			 // 判断当前的日期和当期还款计划的结束日期的大小(当前日期大于还款计划结束日期返回true否则返回false)
			 PlanBean parmPlanBean0 = new PlanBean();
			 parmPlanBean0.setDueNo(dueNo);
			 
//			 if(!StringUtil.equals("1", termNo)){
//				 // 处理在n期把第n-1期的所有结余偿还完,当没有偿还或未还清第n期的情况
//				 if(!FinshCurTermLx(repayBeans,dueNo,termNo,planServices) && !FinshCurTermBj(repayBeans,dueNo,termNo,planServices)){
//					 
//				 }else{
//					 termNo =  Integer.parseInt(termNo)+1+"";
//					 if(Integer.parseInt(termNo)>planBeans.size()){
//						 termNo = 	 String.valueOf(planBeans.size());
//					 }
//				 }
//			 }
			 
			 parmPlanBean0.setTermNo(termNo);
			 PlanBean tempPlanBean = planServices.getPlanBean(parmPlanBean0);
			 boolean flag3 = true;
			 if(tempPlanBean==null){
				 if (Integer.valueOf(termNo) <= Integer.valueOf(planBeans.size())) {
					 termNo="1";
					 continue;
				 } else {
					 flag3 = false;
				 }
			 }
			
			 boolean flag = false;
			 if (flag3) {
				 flag = DateUtil.gt(tempPlanBean.getEndDate(), currDate);
			 }
			 // 本金结余为负数的标志flag1为true 说明为负数
			 boolean flag1 = false;
			// 利息结余为负数的标志flag2为true 说明为负数
			 boolean flag2 = false;
			 
			 String bjbalance = acLnMstBean.getBjbalance();
			 // 如果bjbalance为空设置为0
			 if(StringUtil.isEmpty(bjbalance)){
				 bjbalance  = "0.00";
			 }
			 String ratecomebalance = acLnMstBean.getRatecomebalance();
			// 如果ratecomebalance为空设置为0
			 if(StringUtil.isEmpty(ratecomebalance)){
				 ratecomebalance  = "0.00";
			 }
			 
			 if(Double.parseDouble(bjbalance)<0){
				 flag1 = true;
			 }
			 
			 if(Double.parseDouble(ratecomebalance)<0){
				 flag2 = true;
			 }
			 
			 if(flag || flag1 || flag2){
				 sqfx = true;
			 }else{
				 sqfx = false;
			 }
			 // 设置是否计算罚息标志结束
			// 当剩余本金为0时不再进行批量计算
			 if(Double.parseDouble(dueBal)==0  ||  Double.parseDouble(dueBal)<0){
				 ratedayBean.setOverInterest("0.00");
			 } else {
				 ratedayBean.setCsf("0.00");
				 // 判断是否计算罚息.(sqfx为true 代表要收取罚息)
				 if(sqfx){
					 // 从定价模型中得到罚息利率进行计算，取代原先的合同利率 
					// 结算模式
					String settleModel = acLnMstBean.getSettleModel();
					 double overrate = Double.valueOf(acLnMstBean.getOverRate());
					 String overInterest="";
					 // 最后一期逾期实行22.4
					 if ("B".equals(settleModel)) {//DateUtil.gt("2014-12-31", acLnMstBean.getDueBegDate())
//						 if (DateUtil.gt(planBeans.get(planBeans.size()-1).getEndDate(), systemDate)){
						 overrate = Double.valueOf(acLnMstBean.getLastRate());
//						 }
						 overInterest=getPenalty_1_15(termNo, overrate, acLnMstBean.getDueNo(),dayRateServices,planServices,systemDate, acLnMstBean);
						 ratedayBean.setOverInterest(overInterest);
					 } else if ("A".equals(settleModel)) {
						 overInterest=getPenalty_1(termNo, overrate, acLnMstBean.getDueNo(),dayRateServices,planServices,systemDate, acLnMstBean);
						 ratedayBean.setOverInterest(overInterest);
					 }else  if ("C".equals(settleModel)||"D".equals(settleModel)) {//3/10000
						 overInterest=getPenalty_1_15LX(termNo, overrate, acLnMstBean.getDueNo(),dayRateServices,planServices,systemDate, acLnMstBean);
						 ratedayBean.setOverInterest(overInterest);
					}
				 }else{
					 ratedayBean.setOverInterest("0.00");
					// 将逾期天数更新为0
					Map<String,String> parmMap1 = new HashMap<String,String>();
					parmMap1.put("dueNo", acLnMstBean.getDueNo());
					parmMap1.put("overDays", "0");
					repayServiceImpl.upLnDue(parmMap1);
				 }
			 }
			 // 设置日终的本金
			 if(planBean==null  &&  planBeans.size()==1){
				 ratedayBean.setCapital(planBeans.get(0).getReturnCapital());
				 // 设置日终利息
				 ratedayBean.setInterest(planBeans.get(0).getReturnInterest());
				 // 设置管理费
				 ratedayBean.setAccFee(planBeans.get(0).getAccFee());
				 // 设置日终期号		
				 ratedayBean.setTermNo("1");
			 }else{
				 System.out.println(dueNo+"================================");
				 ratedayBean.setCapital(planBean.getReturnCapital());
				 // 设置日终利息
				 ratedayBean.setInterest(planBean.getReturnInterest());
				 // 设置管理费
				 ratedayBean.setAccFee(planBean.getAccFee());
				 // 设置日终期号		
				 ratedayBean.setTermNo(planBean.getTermNo());
			 }
			 // 设置发生日期
			 ratedayBean.setOccureDate(currDate);
			 
			 //获取该结局的上一个日终信息
			 RatedayBean parmRatedayBean = new RatedayBean();
			 parmRatedayBean.setDueNo(dueNo);
			 String lastDate = DateUtil.addByDay(currDate, -1, DateUtil.DATE_FORMAT_);
			 parmRatedayBean.setOccureDate(lastDate);
			 RatedayBean lastRatedayBean = dayRateServices.getRateDay(parmRatedayBean);
			 // 获取上一个日终结束
			 RatedayBean  ratedayBean_0 =  new RatedayBean();
			 ratedayBean_0.setDueNo(dueNo);
			 List<RatedayBean> ratedayBeans = dayRateServices.getRateDayBeanList(ratedayBean_0);
			 
			 if(planBeans.size()>1 && ratedayBeans.size()>0 && lastRatedayBean==null){
				 continue ;
			 }
			 // 上一个日终的期号
			 String lastTermNo = null;
			 if(lastRatedayBean!=null){
				 lastTermNo = lastRatedayBean.getTermNo();
			 }
			 
			 // 判断当前日终的期号和上一个日终的期号是否一致.如果不一致,并且需要将上期的本金和利息添加到当期日终的本金和利息上.
			 StringBuffer sb = new StringBuffer();
			 if(planBeans.size()>1 && !StringUtil.equals(lastTermNo, planBean.getTermNo())){
			// if(StringUtil.equals(currDate, planBean.getEndDate())){
				 
				 // 从新设置本金开始
				 String capital = ratedayBean.getCapital();
				 sb.append("**************11111****************");
				 sb.append("当前日终本金(if)="+capital);
				 sb.append("\r\n");
				 if(lastRatedayBean!=null ){
					 String lastCapital = lastRatedayBean.getCapital();
					 sb.append("当前日终+上期日终的和="+capital+"+"+lastCapital);
					 capital =  BigNumberUtil.Add(capital,lastCapital);
					 sb.append("="+capital);
				 }
				  ratedayBean.setCapital(capital);
				// 从新设置本金结束
				
				 // 从新设置本金开始
				 String interest = ratedayBean.getInterest();
				 if(lastRatedayBean!=null ){
					 String lastInterest = lastRatedayBean.getInterest();
					 interest =  BigNumberUtil.Add(interest,lastInterest);
				 }
				
				 ratedayBean.setInterest(interest);
				 // 从新设置本金结束
				 
				 String accFee = ratedayBean.getAccFee();
				 if(lastRatedayBean!=null ){
					 String lastAccFee = lastRatedayBean.getAccFee();
					 accFee =  BigNumberUtil.Add(accFee,lastAccFee);
				 }
				 ratedayBean.setAccFee(accFee);
			 }else{
				 if(lastRatedayBean!=null ){
					 sb.append("当前日终本金(else)="+lastRatedayBean.getCapital());
					 ratedayBean.setCapital(lastRatedayBean.getCapital()); 
					 ratedayBean.setInterest( lastRatedayBean.getInterest());
					 ratedayBean.setAccFee(lastRatedayBean.getAccFee());
					// ratedayBean.setTermNo(lastRatedayBean.getTermNo());
				 }
			 }
			 sb.append("**************11111****************");

			 //设置借据号
			 ratedayBean.setDueNo(dueNo);
			 // 设置合同号
			 ratedayBean.setPactNo(acLnMstBean.getPactNo());
			 ratedayBean.setId("000000");
			 try{
				 systemDateServices.updateSystemDate(systemDateBean);
				 dayRateServices.saveRateDayBean(ratedayBean);
				 //log.error("保存成功#"+SystemParm.SystemDate+"#"+ratedayBean.getDueNo());
			 }catch(Exception e){
              log.error("保存失败#"+ratedayBean.getDueNo());
              //break;
			 }
		}
		System.out.println("批量执行完毕============");
	}
	
	private String getPenalty_1_15LX(String termNo, double lnRate,
			String dueno, DayRateServices dayRateServices,
			PlanServices planServices, String systemDate,
			AcLnMstBean acLnMstBean) {
		// 获取固定还款日期
		StringBuffer fx = new StringBuffer(); 
		String lastReturnDate="";
		//获取一共逾期几期，还没有还款的
		PlanBean planbean=new PlanBean();
		planbean.setDueNo(dueno);
		List<PlanBean> planbeanList=dayRateServices.getOverdueplanBeans(planbean);
		if(planbeanList!=null && planbeanList.size()>0){
			Collections.sort(planbeanList);
		}
		
		IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		RepayBean  repayBean = new RepayBean(); 
		repayBean.setDueNo(dueno);
		List<RepayBean> repayBeans =  repayServiceImpl.getRepayBeans(repayBean);
		// 获取最后一次还款信息
		if(repayBeans!=null && repayBeans.size()>0){
			// 获取最后一次还款的日期
			lastReturnDate = repayBeans.get(0).getOccDate();
			
			//获取最后一次偿还的本金
			String returnCapital=StringUtil.KillNull(repayBeans.get(0).getReturnCapital(),"0.00");
			//获取最后一次偿还的利息
			String returnInterest=StringUtil.KillNull(repayBeans.get(0).getReturnInterest(),"0.00");
			
			PlanBean parmPlanBean =  new PlanBean();
			parmPlanBean.setDueNo(dueno);
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
			// 获取最后一次还款时,是否还清了当期
			PlanBean tempPlanBean = planServices.getPlanBean(parmPlanBean);
			Double d =Double.parseDouble(BigNumberUtil.Subtract(returnCapital, tempPlanBean.getReturnCapital()));
			Double d1 =Double.parseDouble(BigNumberUtil.Subtract(returnInterest, tempPlanBean.getReturnInterest()));
			
			if(d<0 || d1<0){
				lastReturnDate=tempPlanBean.getEndDate();
			}
			
		}

		PlanBean parmPlanBean = new PlanBean();
		parmPlanBean.setDueNo(dueno);
		if(repayBeans==null  || repayBeans.size()==0 || repayBeans.get(0)==null ){
			parmPlanBean.setTermNo("-1");
		}else{
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
		}
		PlanBean  tempPlan = planServices.getPlanBean(parmPlanBean);
		if(tempPlan!=null && StringUtil.equals("3", tempPlan.getState())){
			parmPlanBean.setDueNo(dueno);
			AcLnMstBean parmAcLnMstBean = new AcLnMstBean();
			parmAcLnMstBean.setDueNo(dueno);
			List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
			if (StringUtil.equals(acLnMstBeans.get(0).getTermMon(), repayBeans.get(0).getTermNo())) {
				lastReturnDate = systemDate;
			}else{
			parmPlanBean.setTermNo((Integer.parseInt(repayBeans.get(0).getTermNo())+1)+"");
			tempPlan = planServices.getPlanBean(parmPlanBean);
			lastReturnDate = tempPlan.getEndDate();}
		} else {
			// 当期未还清
			parmPlanBean.setTermNo(termNo);
			tempPlan = planServices.getPlanBean(parmPlanBean);
			lastReturnDate = tempPlan.getEndDate();
		}
		
		int day =  DateUtil.getBetweenDays(lastReturnDate, systemDate);
		if(termNo==null){
			termNo = "1";
		}
		
		Map<String,String> parmMap = new HashMap<String,String>();
		parmMap.put("dueNo", dueno);
		if(day<0){
			day = 0;
		}
		parmMap.put("days", String.valueOf(day));
	
		dayRateServices.upFive(parmMap);
		dayRateServices.upLn(parmMap);
		
		if(day<0){
			return "0";
		}
		
		String baseMoney = planbeanList.get(0).getDueAmt();
		// 计算费用收取基数
		if ("2".equals(acLnMstBean.getBaseFee())) {
			baseMoney = acLnMstBean.getDueBal();// 余额
		} else if ("3".equals(acLnMstBean.getBaseFee())) {
			int noPayTerm = 0;
			for (PlanBean pb : planbeanList) {
				if ("0".equals(pb.getState())) {
					if (noPayTerm == 0) {
						noPayTerm = Integer.valueOf(pb.getTermNo());
					} else if (Integer.valueOf(pb.getTermNo()) < Integer.valueOf(noPayTerm)) {
						noPayTerm = Integer.valueOf(pb.getTermNo());
					}
				}
			}
			
			String pmt = PMT(Double.valueOf(BigNumberUtil.Divide5(acLnMstBean.getLnRate(), "1000")), Double.valueOf(defaultTerm(acLnMstBean)),
					Double.valueOf(acLnMstBean.getDueAmt()));// 期供
			baseMoney = "0";
			for (int i = noPayTerm; i<=Integer.valueOf(termNo); i++) {
				int m = Integer.valueOf(termNo) - i + 1;
				baseMoney = BigNumberUtil.Add(baseMoney, BigNumberUtil.Multiply(pmt, m+""));
			}
		}
		
		// 逾期利息 = 合同本金*利率/30*逾期天数
		fx.append("借据号("+dueno+")#发生日期"+ systemDate +"#"+ systemDate +"-"+lastReturnDate+"="+day+"天#");
		fx.append(baseMoney);
		fx.append("×");
		fx.append(day);
		fx.append("×");
		fx.append(lnRate);
		fx.append("÷");
		fx.append("30000");
		fx.append("=");
		
		// 将逾期天数更新到借据表中
		Map<String,String> parmMap1 = new HashMap<String,String>();
		parmMap1.put("dueNo", dueno);
		parmMap1.put("overDays", String.valueOf(day));
		repayServiceImpl.upLnDue(parmMap1);
		
		String overrate ="";
		//比较日期是否在期内,获取逾期利率
		if (DateUtil.gt(acLnMstBean.getDueEndDate(),systemDate)) {
			overrate = acLnMstBean.getLastRate();
		}else{
			overrate = acLnMstBean.getOverRate();
		}
		String overins = BigNumberUtil.Multiply(acLnMstBean.getDueBal(), overrate);
		overins = BigNumberUtil.Divide2(overins, "30000");
		
		// 加未还清的罚息
		RatedayBean ratedayBean = new RatedayBean();
		ratedayBean.setDueNo(acLnMstBean.getDueNo());
		String lastDate = DateUtil.addByDay(systemDate, -1, DateUtil.DATE_FORMAT_);
		ratedayBean.setOccureDate(lastDate);
		ratedayBean = dayRateServices.getRateDay(ratedayBean);
		overins = BigNumberUtil.Add(overins, ratedayBean.getOverInterest());// +前一天fx
		
		if ("3".equals(acLnMstBean.getBaseFee())) {
			overins = baseMoney;
		}
		fx.append(overins);
		log.error(fx.toString());
		System.out.println(fx.toString());
		return overins;
	}

	public String getPenalty_1(String termNo,double lnRate, String dueno,DayRateServices dayRateServices,
			PlanServices planServices,String systemDate,AcLnMstBean acLnMstBean){
		// 获取固定还款日期
		StringBuffer fx = new StringBuffer(); 
		String lastReturnDate="";
		//获取一共逾期几期，还没有还款的
		PlanBean planbean=new PlanBean();
		planbean.setDueNo(dueno);
		List<PlanBean> planbeanList=dayRateServices.getOverdueplanBeans(planbean);
		if(planbeanList!=null && planbeanList.size()>0){
			Collections.sort(planbeanList);
		}
		
		IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		RepayBean  repayBean = new RepayBean(); 
		repayBean.setDueNo(dueno);
		List<RepayBean> repayBeans =  repayServiceImpl.getRepayBeans(repayBean);
		// 获取最后一次还款信息
		if(repayBeans!=null && repayBeans.size()>0){
			// 获取最后一次还款的日期
			lastReturnDate = repayBeans.get(0).getOccDate();
			
			//获取最后一次偿还的本金
			String returnCapital=StringUtil.KillNull(repayBeans.get(0).getReturnCapital(),"0.00");
			//获取最后一次偿还的利息
			String returnInterest=StringUtil.KillNull(repayBeans.get(0).getReturnInterest(),"0.00");
			
			PlanBean parmPlanBean =  new PlanBean();
			parmPlanBean.setDueNo(dueno);
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
			// 获取最后一次还款时,是否还清了当期
			PlanBean tempPlanBean = planServices.getPlanBean(parmPlanBean);
			Double d =Double.parseDouble(BigNumberUtil.Subtract(returnCapital, tempPlanBean.getReturnCapital()));
			Double d1 =Double.parseDouble(BigNumberUtil.Subtract(returnInterest, tempPlanBean.getReturnInterest()));
			
			if(d<0 || d1<0){
				lastReturnDate=tempPlanBean.getEndDate();
			}
			
		}
		
//		// 没有逾期也没有偿还过贷款
//		if(StringUtil.isEmpty(lastReturnDate)){
//		//	log.error("借据号("+dueno+")"+"没有逾期也没还过贷款");
//			//return "0";
//		}
//		/**
//		 * 2014-02-08 修改
//		 * 解决正常还款后,又逾期后罚息计算为一个月的罚息的问题
//		 * */
		PlanBean parmPlanBean = new PlanBean();
//		PlanBean parmPlanBean1 = new PlanBean();

		parmPlanBean.setDueNo(dueno);
		if(repayBeans==null  || repayBeans.size()==0 || repayBeans.get(0)==null ){
			parmPlanBean.setTermNo("-1");
		}else{
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
		}
		PlanBean  tempPlan = planServices.getPlanBean(parmPlanBean);
		if(tempPlan!=null && StringUtil.equals("3", tempPlan.getState())){
			parmPlanBean.setDueNo(dueno);
			AcLnMstBean parmAcLnMstBean = new AcLnMstBean();
			parmAcLnMstBean.setDueNo(dueno);
			List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
			if (StringUtil.equals(acLnMstBeans.get(0).getTermMon(), repayBeans.get(0).getTermNo())) {
				lastReturnDate = systemDate;
			}else{
			parmPlanBean.setTermNo((Integer.parseInt(repayBeans.get(0).getTermNo())+1)+"");
			tempPlan = planServices.getPlanBean(parmPlanBean);
			lastReturnDate = tempPlan.getEndDate();}
		} else {
			// 当期未还清
			parmPlanBean.setTermNo(termNo);
			tempPlan = planServices.getPlanBean(parmPlanBean);
			lastReturnDate = tempPlan.getEndDate();
		}
		
		int day =  DateUtil.getBetweenDays(lastReturnDate, systemDate);
		if(termNo==null){
			termNo = "1";
		}
		
		Map<String,String> parmMap = new HashMap<String,String>();
		parmMap.put("dueNo", dueno);
		if(day<0){
			day = 0;
		}
		parmMap.put("days", String.valueOf(day));
	
		dayRateServices.upFive(parmMap);
		dayRateServices.upLn(parmMap);
		
		if(day<0){
		//	log.error("借据号("+dueno+")#发生日期"+SystemParm.SystemDate+"#"+SystemParm.SystemDate+"-"+lastReturnDate+"="+day+"天#");
			return "0";
		}
		
		String baseMoney = planbeanList.get(0).getDueAmt();
		// 计算费用收取基数
		if ("2".equals(acLnMstBean.getBaseFee())) {
			baseMoney = acLnMstBean.getDueBal();// 余额
		} else if ("3".equals(acLnMstBean.getBaseFee())) {
			int noPayTerm = 0;
			for (PlanBean pb : planbeanList) {
				if ("0".equals(pb.getState())) {
					if (noPayTerm == 0) {
						noPayTerm = Integer.valueOf(pb.getTermNo());
					} else if (Integer.valueOf(pb.getTermNo()) < Integer.valueOf(noPayTerm)) {
						noPayTerm = Integer.valueOf(pb.getTermNo());
					}
				}
			}
			
			String pmt = PMT(Double.valueOf(BigNumberUtil.Divide5(acLnMstBean.getLnRate(), "1000")), Double.valueOf(defaultTerm(acLnMstBean)),
					Double.valueOf(acLnMstBean.getDueAmt()));// 期供
			baseMoney = "0";
			for (int i = noPayTerm; i<=Integer.valueOf(termNo); i++) {
				int m = Integer.valueOf(termNo) - i + 1;
				baseMoney = BigNumberUtil.Add(baseMoney, BigNumberUtil.Multiply(pmt, m+""));
			}
		}
		
		// 逾期利息 = 合同本金*利率/30*逾期天数
		fx.append("借据号("+dueno+")#发生日期"+ systemDate +"#"+ systemDate +"-"+lastReturnDate+"="+day+"天#");
		fx.append(baseMoney);
		fx.append("×");
		fx.append(day);
		fx.append("×");
		fx.append(lnRate);
		fx.append("÷");
		fx.append("30000");
		fx.append("=");
		
		// 将逾期天数更新到借据表中
		Map<String,String> parmMap1 = new HashMap<String,String>();
		parmMap1.put("dueNo", dueno);
		parmMap1.put("overDays", String.valueOf(day));
		repayServiceImpl.upLnDue(parmMap1);
		
		String overins = BigNumberUtil.Multiply(baseMoney, String.valueOf(day));
		overins = BigNumberUtil.Multiply(overins, String.valueOf(lnRate));
		overins = BigNumberUtil.Divide2(overins, "30000");
		if ("3".equals(acLnMstBean.getBaseFee())) {
			overins = baseMoney;
		}
		fx.append(overins);
		log.error(fx.toString());
		System.out.println(fx.toString());
		return overins;
	}
	
	public String getPenalty_1_15(String termNo,double lnRate, String dueno,DayRateServices dayRateServices,
			PlanServices planServices,String systemDate,AcLnMstBean acLnMstBean){
		// 获取固定还款日期
		StringBuffer fx = new StringBuffer(); 
		String lastReturnDate="";
		//获取一共逾期几期，还没有还款的
		PlanBean planbean=new PlanBean();
		planbean.setDueNo(dueno);
		List<PlanBean> planbeanList=dayRateServices.getOverdueplanBeans(planbean);
		if(planbeanList!=null && planbeanList.size()>0){
			Collections.sort(planbeanList);
		}
		
		IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		RepayBean  repayBean = new RepayBean(); 
		repayBean.setDueNo(dueno);
		List<RepayBean> repayBeans =  repayServiceImpl.getRepayBeans(repayBean);
		// 获取最后一次还款信息
		if(repayBeans!=null && repayBeans.size()>0){
			// 获取最后一次还款的日期
			lastReturnDate = repayBeans.get(0).getOccDate();
			
			//获取最后一次偿还的本金
			String returnCapital=StringUtil.KillNull(repayBeans.get(0).getReturnCapital(),"0.00");
			//获取最后一次偿还的利息
			String returnInterest=StringUtil.KillNull(repayBeans.get(0).getReturnInterest(),"0.00");
			
			PlanBean parmPlanBean =  new PlanBean();
			parmPlanBean.setDueNo(dueno);
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
			// 获取最后一次还款时,是否还清了当期
			PlanBean tempPlanBean = planServices.getPlanBean(parmPlanBean);
			Double d =Double.parseDouble(BigNumberUtil.Subtract(returnCapital, tempPlanBean.getReturnCapital()));
			Double d1 =Double.parseDouble(BigNumberUtil.Subtract(returnInterest, tempPlanBean.getReturnInterest()));
			
			if(d<0 || d1<0){
				lastReturnDate=tempPlanBean.getEndDate();
			}
			
		}

		PlanBean parmPlanBean = new PlanBean();
		parmPlanBean.setDueNo(dueno);
		if(repayBeans==null  || repayBeans.size()==0 || repayBeans.get(0)==null ){
			parmPlanBean.setTermNo("-1");
		}else{
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
		}
		PlanBean  tempPlan = planServices.getPlanBean(parmPlanBean);
		if(tempPlan!=null && StringUtil.equals("3", tempPlan.getState())){
			parmPlanBean.setDueNo(dueno);
			AcLnMstBean parmAcLnMstBean = new AcLnMstBean();
			parmAcLnMstBean.setDueNo(dueno);
			List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
			if (StringUtil.equals(acLnMstBeans.get(0).getTermMon(), repayBeans.get(0).getTermNo())) {
				lastReturnDate = systemDate;
			}else{
			parmPlanBean.setTermNo((Integer.parseInt(repayBeans.get(0).getTermNo())+1)+"");
			tempPlan = planServices.getPlanBean(parmPlanBean);
			lastReturnDate = tempPlan.getEndDate();}
		} else {
			// 当期未还清
			parmPlanBean.setTermNo(termNo);
			tempPlan = planServices.getPlanBean(parmPlanBean);
			lastReturnDate = tempPlan.getEndDate();
		}
		
		int day =  DateUtil.getBetweenDays(lastReturnDate, systemDate);
		if(termNo==null){
			termNo = "1";
		}
		
		Map<String,String> parmMap = new HashMap<String,String>();
		parmMap.put("dueNo", dueno);
		if(day<0){
			day = 0;
		}
		parmMap.put("days", String.valueOf(day));
	
		dayRateServices.upFive(parmMap);
		dayRateServices.upLn(parmMap);
		
		if(day<0){
			return "0";
		}
		
		String baseMoney = planbeanList.get(0).getDueAmt();
		// 计算费用收取基数
		if ("2".equals(acLnMstBean.getBaseFee())) {
			baseMoney = acLnMstBean.getDueBal();// 余额
		} else if ("3".equals(acLnMstBean.getBaseFee())) {
			int noPayTerm = 0;
			for (PlanBean pb : planbeanList) {
				if ("0".equals(pb.getState())) {
					if (noPayTerm == 0) {
						noPayTerm = Integer.valueOf(pb.getTermNo());
					} else if (Integer.valueOf(pb.getTermNo()) < Integer.valueOf(noPayTerm)) {
						noPayTerm = Integer.valueOf(pb.getTermNo());
					}
				}
			}
			
			String pmt = PMT(Double.valueOf(BigNumberUtil.Divide5(acLnMstBean.getLnRate(), "1000")), Double.valueOf(defaultTerm(acLnMstBean)),
					Double.valueOf(acLnMstBean.getDueAmt()));// 期供
			baseMoney = "0";
			for (int i = noPayTerm; i<=Integer.valueOf(termNo); i++) {
				int m = Integer.valueOf(termNo) - i + 1;
				baseMoney = BigNumberUtil.Add(baseMoney, BigNumberUtil.Multiply(pmt, m+""));
			}
		}
		
		// 逾期利息 = 合同本金*利率/30*逾期天数
		fx.append("借据号("+dueno+")#发生日期"+ systemDate +"#"+ systemDate +"-"+lastReturnDate+"="+day+"天#");
		fx.append(baseMoney);
		fx.append("×");
		fx.append(day);
		fx.append("×");
		fx.append(lnRate);
		fx.append("÷");
		fx.append("30000");
		fx.append("=");
		
		// 将逾期天数更新到借据表中
		Map<String,String> parmMap1 = new HashMap<String,String>();
		parmMap1.put("dueNo", dueno);
		parmMap1.put("overDays", String.valueOf(day));
		repayServiceImpl.upLnDue(parmMap1);
		
		String overins = BigNumberUtil.Multiply(baseMoney, String.valueOf(lnRate));//22.4%
		overins = BigNumberUtil.Divide2(overins, "30000");
		PlanBean pb = new PlanBean();
		pb.setDueNo(dueno);
		List<PlanBean> pblist = planServices.getPlanBeanList(pb);
		if(pblist!=null && pblist.size()>0){
			Collections.sort(pblist);
			
			for (PlanBean x : pblist) {
				if (DateUtil.gteq(x.getBegDate(), systemDate) && DateUtil.gteq(systemDate, x.getEndDate())) {
					pb = x;
					break;
				}
			}
		}
		String interest =  BigNumberUtil.Divide2(pb.getReturnInterest(), "30");
		if (DateUtil.gteq(systemDate, pblist.get(pblist.size()-1).getEndDate())) {// 如果最后一期逾期之后将不再减去当日普通利息
			overins = BigNumberUtil.Subtract(overins, interest); // 每一天应交fx = 当日本金余额*22.4/360 - 当日普通利息
		}
		
		// 加未还清的罚息
		RatedayBean ratedayBean = new RatedayBean();
		ratedayBean.setDueNo(acLnMstBean.getDueNo());
		String lastDate = DateUtil.addByDay(systemDate, -1, DateUtil.DATE_FORMAT_);
		ratedayBean.setOccureDate(lastDate);
		ratedayBean = dayRateServices.getRateDay(ratedayBean);
		try {
			overins = BigNumberUtil.Add(overins, ratedayBean.getOverInterest());// +前一天fx
		} catch (Exception e) {
			System.out.println(acLnMstBean.getDueNo()+"出错啦！");
			e.printStackTrace();
		}
		
		if ("3".equals(acLnMstBean.getBaseFee())) {
			overins = baseMoney;
		}
		fx.append(overins);
		log.error(fx.toString());
		System.out.println(fx.toString());
		return overins;
	}
	
	public String getPenalty_2(String termNo,double lnRate, String dueno, DayRateServices dayRateServices,
			PlanServices planServices, CollectFeeService collectFeeService, String systemDate, AcLnMstBean acLnMstBean){
		String csf = "0.00";
		String lastReturnDate="";
		//获取一共逾期几期，还没有还款的
		PlanBean planbean=new PlanBean();
		planbean.setDueNo(dueno);
		List<PlanBean> planbeanList=dayRateServices.getOverdueplanBeans(planbean);
		if(planbeanList!=null && planbeanList.size()>0){
			Collections.sort(planbeanList);
		}
		
		IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		RepayBean  repayBean = new RepayBean(); 
		repayBean.setDueNo(dueno);
		List<RepayBean> repayBeans =  repayServiceImpl.getRepayBeans(repayBean);
		// 获取最后一次还款信息
		if(repayBeans!=null && repayBeans.size()>0){
			// 获取最后一次还款的日期
			lastReturnDate = repayBeans.get(0).getOccDate();
			
			//获取最后一次偿还的本金
			String returnCapital=StringUtil.KillNull(repayBeans.get(0).getReturnCapital(),"0.00");
			//获取最后一次偿还的利息
			String returnInterest=StringUtil.KillNull(repayBeans.get(0).getReturnInterest(),"0.00");
			
			PlanBean parmPlanBean =  new PlanBean();
			parmPlanBean.setDueNo(dueno);
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
			// 获取最后一次还款时,是否还清了当期
			PlanBean tempPlanBean = planServices.getPlanBean(parmPlanBean);
			Double d =Double.parseDouble(BigNumberUtil.Subtract(returnCapital, tempPlanBean.getReturnCapital()));
			Double d1 =Double.parseDouble(BigNumberUtil.Subtract(returnInterest, tempPlanBean.getReturnInterest()));
			
			if(d<0 || d1<0){
				lastReturnDate=tempPlanBean.getEndDate();
			}
			
		}
		
		PlanBean parmPlanBean = new PlanBean();

		parmPlanBean.setDueNo(dueno);
		if(repayBeans==null  || repayBeans.size()==0 || repayBeans.get(0)==null ){
			parmPlanBean.setTermNo("-1");
		}else{
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
		}
		PlanBean  tempPlan = planServices.getPlanBean(parmPlanBean);
		if(tempPlan!=null && StringUtil.equals("3", tempPlan.getState())){
			parmPlanBean.setDueNo(dueno);
			AcLnMstBean parmAcLnMstBean = new AcLnMstBean();
			parmAcLnMstBean.setDueNo(dueno);
			List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
			if (StringUtil.equals(acLnMstBeans.get(0).getTermMon(), repayBeans.get(0).getTermNo())) {
				lastReturnDate = systemDate;
			}else{
			parmPlanBean.setTermNo((Integer.parseInt(repayBeans.get(0).getTermNo())+1)+"");
			tempPlan = planServices.getPlanBean(parmPlanBean);
			lastReturnDate = tempPlan.getEndDate();}
		} else {
			// 当期未还清
			parmPlanBean.setTermNo(termNo);
			tempPlan = planServices.getPlanBean(parmPlanBean);
			lastReturnDate = tempPlan.getEndDate();
		}
		
		int day =  DateUtil.getBetweenDays(lastReturnDate, systemDate);
		
		if (day > 60) {
			RatedayBean ratedayBean = new RatedayBean();
			ratedayBean.setDueNo(acLnMstBean.getDueNo());
			String lastDate = DateUtil.addByDay(systemDate, -1, DateUtil.DATE_FORMAT_);
			ratedayBean.setOccureDate(lastDate);
			//System.out.println(ratedayBean.getOccureDate()+ratedayBean.getDueNo()+"****到了1111111111111111111111");
			ratedayBean = dayRateServices.getRateDay(ratedayBean);
			//System.out.println(ratedayBean+"****到了2222222222222222222222222"+ratedayBean.getCsf());
			csf = ratedayBean.getCsf();// 前一天催收费
			
			CollectFeePolicy p = collectFeeService.getById(acLnMstBean.getCollectfeeId());
			String fee = "0.00";
			// 到期前每月2%
			if (!DateUtil.gt(acLnMstBean.getDueEndDate(), systemDate)) {
				fee = BigNumberUtil.Multiply(acLnMstBean.getDueBal(), p.getBaseRate());
			} else {
				// 到期后首月3%, 之后每月递增1%
				int overlastdays = DateUtil.getBetweenDays(acLnMstBean.getDueEndDate(), systemDate);
				int unit = overlastdays % 30;
				String addRate = BigNumberUtil.Multiply(p.getAddRate(), unit+"");
				String rate = BigNumberUtil.Add(p.getOverRate(), addRate);
				fee = BigNumberUtil.Multiply(acLnMstBean.getDueBal(), rate);
			}
			if (day % 30 == 1) {
				fee = BigNumberUtil.Divide5(fee, "100");
				csf = BigNumberUtil.Add(csf, fee);
			}
		}
		
		return csf;
	}
	
	private String PMT(Double rate, Double term, Double financeAmount) {
		Double v = (1 + rate);
		Double t = (-(term / 12) * 12);
		Double result = (financeAmount * rate) / (1 - Math.pow(v, t));
		return result.toString();
	}
	
	/**
	 * 
	 * 方法描述： 获得默认期数 
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-24 下午12:02:57
	 */
	private int defaultTerm(AcLnMstBean acLnMstBean){
		// 还款计划第一期的开始日期(默认和借据的开始日期相同)
		String begDate = acLnMstBean.getDueBegDate();
		// 借据的开始日期
		String endDate = acLnMstBean.getDueEndDate();
		int[]  monthAndDays = DateUtil.getMonthsAndDays(begDate, endDate);
		boolean isdate=false;
		if (StringUtil.isNotEmpty(acLnMstBean.getAuthId())) {
			int putoutDay = DateUtil.getDay(endDate);
			if (putoutDay!=Integer.parseInt(acLnMstBean.getAuthId())) {
				isdate=true;
			}
		}
		if (isdate) {
			return monthAndDays[0];
		} else if(monthAndDays[1]>0){
			return monthAndDays[0]+1;
		}else{
			return monthAndDays[0];
		}
	} 
	
	/**
	 * 
	 * 方法描述：算出逾期的天数和期数 
	 * @param dueno 借据号
	 * @param termno 借据第几期
	 * @return
	 * int[]
	 * @author songshengkai
	 * @date Jan 26, 2013 11:16:59 AM
	 */
	public int[] getOverduePerioddays(String dueno,String termno,DayRateServices dayRateServices,String systemDate){
		PlanBean planbean=new PlanBean();
		planbean.setDueNo(dueno);
		List<PlanBean> planbeanList=dayRateServices.getOverdueplanBeans(planbean);//获取一共逾期一共几期，还过款和没有还过款的都包括
		int countdays=0;//逾期天数
		int j=0;//逾期期数
		for(PlanBean planBean:planbeanList){
			if (Integer.valueOf(termno)>=Integer.valueOf(planBean.getTermNo())) {
				if (planBean.getIsDebt()!=null&&Integer.valueOf(planBean.getIsDebt())>0) {
					countdays=countdays+Integer.valueOf(planBean.getIsDebt());
					j++;
				}else if(planBean.getState().equals("1")||(planBean.getState().equals("0")&&DateUtil.getBetweenDays(planBean.getEndDate(), systemDate )>0)){
					countdays=countdays+DateUtil.getBetweenDays(planBean.getEndDate(), systemDate);
					j++;
				}
			}
		}
		int[] da={countdays,j};
		return da;
	}
	
	public String getFineInterest(RatedayBean ratedayBean,AcLnMstBean acLnMstBean,String overInterest,boolean nextterm){
		StringBuffer sb = new StringBuffer();
		//  统计累计偿还的利息和账户管理费
		String baseData = "0.00";
		RepayService  repayService = SpringFactory.getBean("repayService");
		List<RepayBean>  repayBeans = repayService.getRepayBeans(ratedayBean.getDueNo());
		// 累计偿还的利息和账户管理费开始
		if(repayBeans!=null  && repayBeans.size()>0){
			for(RepayBean repayBean:repayBeans){
				baseData = BigNumberUtil.Add(repayBean.getReturnInterest(),baseData);
				baseData = BigNumberUtil.Add(repayBean.getOtherFee(),baseData);
			}
		}
		// 累计偿还的利息和账户管理费结束
		
		// 当期的利息 账户管理费  和剩余本金的和 
		String base = BigNumberUtil.Add(ratedayBean.getBaseInterest(),ratedayBean.getBaseAccFee(),acLnMstBean.getDueBal());
		sb.append("(");
		sb.append(ratedayBean.getBaseInterest());
		sb.append("+");
		sb.append(ratedayBean.getBaseAccFee());
		sb.append("+");
		sb.append(acLnMstBean.getDueBal());
		
		// 判断是是不是某一期的开始
		if(nextterm){
			sb.append("+");
			sb.append(overInterest);
			 base = BigNumberUtil.Add(base,overInterest);
		}else{
			sb.append("+");
			sb.append("0.00");
		}
		// 当期的利息 账户管理费  和剩余本金的和减去已还的利息和账户管理费
		base = BigNumberUtil.Subtract(base, baseData);
		sb.append("-");
		sb.append(baseData);
		sb.append(")");
		sb.append("x");
		sb.append(acLnMstBean.getOverRate()+"‰"+"÷30=");
		log.error(sb.toString());
		base=BigNumberUtil.Multiply(base, acLnMstBean.getOverRate());
		base=BigNumberUtil.Divide(base, "30000", 2, "1");
		base = BigNumberUtil.Add(base,overInterest);
		sb.append(base);
		return base;
	}
	
	/**
	 * 处理在n期把第n-1期的所有结余偿还完,当没有偿还或未还清第n期的情况
	 *			 
	 */
//	private boolean FinshCurTermBj(List<RepayBean> repayBeans,String dueNo,String termNo,PlanServices planServices){
//		// 统计当前期以偿还的本金
//		String tempBj="0.00";
//		PlanBean parmPlanBean = new PlanBean();
//		parmPlanBean.setDueNo(dueNo);
//		parmPlanBean.setTermNo(termNo);
//		PlanBean planBean = planServices.getPlanBean(parmPlanBean);
//		// 当期的本金是否偿还完的标识
//		boolean flag=false;
//		for(RepayBean repayBean:repayBeans){
//			if(StringUtil.equals(repayBean.getTermNo().trim(), termNo.trim())){
//				tempBj = BigNumberUtil.Add(tempBj,StringUtil.KillNull(repayBean.getReturnCapital(),"0.00"));
//			}
//		}
//		Double d= Double.parseDouble(BigNumberUtil.Subtract(tempBj, planBean.getReturnCapital()));
//	    if(d>0||d==0) {
//	    	flag=true;
//	    }
//	    return flag;
//	}
	
	// repayBeans,dueNo,termNo,planServices
//	private boolean FinshCurTermLx(List<RepayBean> repayBeans,String dueNo,String termNo,PlanServices planServices){
//		
//		// 统计当前期以偿还的本金
//		String tempLx="0.00";
//		
//		
//		PlanBean parmPlanBean = new PlanBean();
//		parmPlanBean.setDueNo(dueNo);
//		parmPlanBean.setTermNo(termNo);
//		PlanBean planBean = planServices.getPlanBean(parmPlanBean);
//		// 当期的本金是否偿还完的标识
//		boolean flag=false;
//		for(RepayBean repayBean:repayBeans){
//			if(StringUtil.equals(repayBean.getTermNo().trim(), termNo.trim())){
//				tempLx = BigNumberUtil.Add(tempLx,StringUtil.KillNull(repayBean.getReturnInterest(),"0.00"));
//			}
//		}
//		Double d = null;
//		try {
//			d = Double.parseDouble(BigNumberUtil.Subtract(tempLx, planBean.getReturnInterest()));
//		} catch (NumberFormatException e) {
//			System.out.println(dueNo+"出错啦啦啦~~~~~~~~~~~");
//			e.printStackTrace();
//		}
//	    if(d>0||d==0) {
//	    	flag=true;
//	    }
//	    return flag;
//	}
	
	/**
	 * 获取计算罚息的时开始日期
	 */
	private String getOverLastTerm(List<RepayBean>  repayBeans,List<PlanBean> PlanBeans){
		String termNo="1";
		if(PlanBeans==null  ||  PlanBeans.size()==0){
			return  termNo;
		}
		if(repayBeans==null  ||  repayBeans.size()==0){
			return  termNo;
		}
		Collections.sort(PlanBeans);
        
		RepayBean tempRepayBean = repayBeans.get(0);
		
		termNo = tempRepayBean.getTermNo();
		PlanBean  planBean =  PlanBeans.get(Integer.parseInt(termNo)-1);     
		// 统计最后一次还款的期供信息
		String sum  = getReturnOfTerm(repayBeans,termNo).get(0); 
		// 当期的期供信息
		String sum0 =  BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest());
		// 当期偿还的期供和期供的差值
		Double d = Double.parseDouble(BigNumberUtil.Subtract(sum,sum0));
		// 当期已经偿还完毕
		if(d>0 || d==0){
			termNo = BigNumberUtil.add2String(tempRepayBean.getTermNo(), "1");
			return  termNo;
		}
		
		String sumReturn = "0.00";
		for(RepayBean repayBean:repayBeans){
			sumReturn = BigNumberUtil.Add(sumReturn,repayBean.getReturnCapital(),repayBean.getReturnInterest(),repayBean.getBjbalance(),repayBean.getRatecomebalance());
		}
		
		Double d2 = Double.parseDouble(sumReturn);
		
		for(PlanBean planBean_:PlanBeans ){
			String termReturn = BigNumberUtil.Add(planBean_.getReturnCapital(),planBean_.getReturnInterest());
			Double d1 = Double.parseDouble(termReturn);
			d2 = d2-d1;
			d2=Double.parseDouble(BigNumberUtil.Divide2(String.valueOf(d2), "1"));
			if(d2<0){
				return planBean_.getTermNo();
			}
		}
		
		return termNo;
	}
	
	/**
	 * 获取某一期的还款信息
	 */
	private  List<String>  getReturnOfTerm(List<RepayBean> repayBeans,String termNo){
		List<String> returnInf = new ArrayList<String>();
		if(repayBeans==null  || repayBeans.size()==0){
			returnInf.add("0.00");
			returnInf.add("0.00");
			return returnInf;
		}
		
		if(termNo==null ){
			returnInf.add("0.00");
			returnInf.add("0.00");
			return returnInf;
		}
		// 期供
		String repaytotal = "0.00";
		// 结余
		String repayBalance = "0.00";
	    for(RepayBean  repayBean:repayBeans){
	    	if(StringUtil.equals(repayBean.getTermNo().trim(), termNo.trim())){
	    		repaytotal = BigNumberUtil.Add(repaytotal,repayBean.getReturnCapital(),repayBean.getReturnInterest());
	    		repayBalance = BigNumberUtil.Add(repayBalance,repayBean.getBjbalance(),repayBean.getRatecomebalance());
	    	}
	    }
	    returnInf.add(repaytotal);
	    returnInf.add(repayBalance);
		return returnInf;
	}
	
}
