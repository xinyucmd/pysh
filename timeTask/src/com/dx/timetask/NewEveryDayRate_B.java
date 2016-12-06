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
import com.dx.payment.bean.AccountRepayRel;
import com.dx.payment.bean.LoanMainAccount;
import com.dx.payment.bean.LoanRepayHis;
import com.dx.payment.bean.LoanRepayPlan;
import com.dx.payment.bean.LoanSubAccount;
import com.dx.payment.bean.RatedayBBean;
import com.dx.payment.services.AccountRepayRelBO;
import com.dx.payment.services.LoanMainAccountBO;
import com.dx.payment.services.LoanRepayHisBO;
import com.dx.payment.services.LoanRepayPlanBO;
import com.dx.payment.services.LoanSubAccountBO;

/**
 * 类名： DayRate 描述：利息日终计算
 * 
 * @author 乾之轩
 * @date 2012-6-11 下午04:37:13
 */
public class NewEveryDayRate_B extends QuartzJobBean {
	private Log log = LogFactory.getLog(getClass());

	/**
	 * 最新批量
	 * 
	 * @param arg0
	 * @throws JobExecutionException
	 */
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
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

		// 新结算相关服务类
		// 还款计划
		LoanRepayPlanBO loanRepayPlanBO = SpringFactory.getBean("loanRepayPlanBO");
		// 还款历史
		LoanRepayHisBO loanRepayHisBO = SpringFactory.getBean("loanRepayHisBO");
		// 流水
		AccountRepayRelBO accountRepayRelBO = SpringFactory.getBean("accountRepayRelBO");
		// 主账户
		LoanMainAccountBO loanMainAccountBO = SpringFactory.getBean("loanMainAccountBO");
		// 分账户
		LoanSubAccountBO loanSubAccountBO = SpringFactory.getBean("loanSubAccountBO");

		// 还款计划列表
		List<LoanRepayPlan> planBeans = null;
		// 还款流水
		List<AccountRepayRel> repayRels = null;
		// 还款历史
		List<LoanRepayHis> repayHisList = null;
		// 分账户
		List<LoanSubAccount> subAccountList = null;
		
		// 计算罚息的开始日期
		String beginDate = null;

		// 是否计算罚息的标志
		boolean sqfx = false;

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
		parmAcLnMstBean.setDueNo("JC1033703012015030316816001");
		// 查询出有那些借据需要进行批量处理
		List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
		if (acLnMstBeans == null || acLnMstBeans.size() == 0) {
			return;
		}

		for (AcLnMstBean acLnMstBean : acLnMstBeans) {
			// 借据号
			String dueNo = acLnMstBean.getDueNo();
			// 主账户
			LoanMainAccount mainAccount = new LoanMainAccount();
			mainAccount.setDue_no(dueNo);
			mainAccount = loanMainAccountBO.viewMainAccount(mainAccount);
			// 分账户
			LoanSubAccount sa = new LoanSubAccount();
			sa.setAccount_no(mainAccount.getAccount_no());
			sa.setPay_type("B");
			subAccountList = loanSubAccountBO.getList(sa);
			String dueBal = "";
			for (LoanSubAccount subBean : subAccountList) {
				if ("21".equals(subBean.getAccount_type())) {
					dueBal = BigNumberUtil.Subtract(subBean.getShould_pay(), subBean.getPay());
				}
			}

			if (StringUtil.isEmpty(dueNo)) {
				log.error("借据号不能为空!");
				continue;
			}
			// 获取所有还款计划list
			LoanRepayPlan tempPlan = new LoanRepayPlan();
			tempPlan.setDue_no(dueNo);
			planBeans = loanRepayPlanBO.viewPlan(tempPlan);

			// 获取所有的还款流水
			AccountRepayRel tempRel = new AccountRepayRel();
			tempRel.setDue_no(acLnMstBean.getDueNo());
			repayRels = accountRepayRelBO.viewRel(tempRel);

			// 获取所有的还款历史
			LoanRepayHis tempHis = new LoanRepayHis();
			tempHis.setSeq_no(repayRels.get(0).getSeq_no());
			repayHisList = loanRepayHisBO.viewHis(tempHis);

			RatedayBBean ratedayBBean = new RatedayBBean();
			ratedayBBean.setDueNo(acLnMstBean.getDueNo());
			// 判断所还金额已冲动到第几期
			String lastFinishTerm = "0";
			for (LoanRepayPlan plan : planBeans) {
				if ("3".equals(plan.getIf_settle())) {// 已还清
					if (Integer.parseInt(lastFinishTerm) <= Integer.parseInt(plan.getTerm())) {
						lastFinishTerm = plan.getTerm();
					}
				}
			}
			// 2.获取最后还清那期的下一期还款计划
			tempPlan = new LoanRepayPlan();
			tempPlan.setDue_no(dueNo);
			tempPlan.setTerm(BigNumberUtil.Add(lastFinishTerm, "1"));
			LoanRepayPlan nextPlan = loanRepayPlanBO.viewPlan(tempPlan).get(0);
			String nextTermDate = nextPlan.getBeg_date();

			// 3.判断2、systemDate的先后顺序，得出结论是否需要计算罚息
			boolean flg = DateUtil.gt(systemDate, nextTermDate);
			if (flg) {
				// nextTermDate>systemDate==>nextTermDate+1
				sqfx = false;
			} else {
				sqfx = true;
			}

			// 当剩余本金为0时不再进行批量计算
			if (Double.parseDouble(dueBal) == 0 || Double.parseDouble(dueBal) < 0) {
				ratedayBBean.setOverInterest("0.00");
			} else {
				// 判断是否计算罚息.(sqfx为true 代表要收取罚息)
				if (sqfx) {
					// 从定价模型中得到罚息利率进行计算，取代原先的合同利率
					double overrate = Double.valueOf(acLnMstBean.getOverRate());
					// 最后一期逾期实行22.4
					overrate = Double.valueOf(acLnMstBean.getLastRate());
					String overInterest = getPenalty_1_15(overrate, dayRateServices, planServices, systemDate, acLnMstBean, planBeans, loanRepayPlanBO, repayServiceImpl,dueBal);
					ratedayBBean.setOverInterest(overInterest);
					String csf = getPenalty_2(overrate, dayRateServices, planServices, collectFeeService, systemDate, acLnMstBean, loanRepayPlanBO, planBeans,dueBal);
					ratedayBBean.setCsf(csf);
				} else {
					ratedayBBean.setOverInterest("0.00");
					ratedayBBean.setCsf("0.00");
					// 将逾期天数更新为0
					Map<String, String> parmMap1 = new HashMap<String, String>();
					parmMap1.put("dueNo", acLnMstBean.getDueNo());
					parmMap1.put("overDays", "0");
					repayServiceImpl.upLnDue(parmMap1);
				}
			}
			// 获取当前要操作的还款计划
			tempPlan = new LoanRepayPlan();
			tempPlan.setDue_no(dueNo);
			if (DateUtil.gt(acLnMstBean.getDueEndDate(), currDate)) {
				tempPlan.setBeg_date(acLnMstBean.getDueEndDate());
				tempPlan.setEnd_date(acLnMstBean.getDueEndDate());
			} else {
				tempPlan.setBeg_date(currDate);
				tempPlan.setEnd_date(currDate);
			}

			// 处理只有一期的还款
			LoanRepayPlan planBean = loanRepayPlanBO.getPlanBean(tempPlan);
			// 设置日终的本金
			if (planBean == null && planBeans.size() == 1) {
				ratedayBBean.setCapital(planBeans.get(0).getCapital());
				// 设置日终利息
				ratedayBBean.setInterest(planBeans.get(0).getInterest());
				// 设置管理费
				ratedayBBean.setAccFee(planBeans.get(0).getOther());
				// 设置日终期号
				ratedayBBean.setTermNo("1");
			} else {
				ratedayBBean.setCapital(planBean.getCapital());
				// 设置日终利息
				ratedayBBean.setInterest(planBean.getInterest());
				// 设置管理费
				ratedayBBean.setAccFee(planBean.getOther());
				// 设置日终期号
				ratedayBBean.setTermNo(planBean.getTerm());
			}
			// 设置发生日期
			ratedayBBean.setOccureDate(currDate);

			// 获取该结局的上一个日终信息
			RatedayBean parmRatedayBean = new RatedayBean();
			parmRatedayBean.setDueNo(dueNo);
			String lastDate = DateUtil.addByDay(currDate, -1, DateUtil.DATE_FORMAT_);
			parmRatedayBean.setOccureDate(lastDate);
			RatedayBean lastRatedayBean = dayRateServices.getRateDay(parmRatedayBean);
			// 获取上一个日终结束
			RatedayBean ratedayBean_0 = new RatedayBean();
			ratedayBean_0.setDueNo(dueNo);
			List<RatedayBean> ratedayBeans = dayRateServices.getRateDayBeanList(ratedayBean_0);

			if (planBeans.size() > 1 && ratedayBeans.size() > 0 && lastRatedayBean == null) {
				continue;
			}
			// 上一个日终的期号
			String lastTermNo = null;
			if (lastRatedayBean != null) {
				lastTermNo = lastRatedayBean.getTermNo();
			}

			// 判断当前日终的期号和上一个日终的期号是否一致.如果不一致,并且需要将上期的本金和利息添加到当期日终的本金和利息上.
			StringBuffer sb = new StringBuffer();
			if (planBeans.size() > 1 && !StringUtil.equals(lastTermNo, planBean.getTerm())) {
				// if(StringUtil.equals(currDate, planBean.getEndDate())){

				// 从新设置本金开始
				String capital = ratedayBBean.getCapital();
				sb.append("**************11111****************");
				sb.append("当前日终本金(if)=" + capital);
				sb.append("\r\n");
				if (lastRatedayBean != null) {
					String lastCapital = lastRatedayBean.getCapital();
					sb.append("当前日终+上期日终的和=" + capital + "+" + lastCapital);
					capital = BigNumberUtil.Add(capital, lastCapital);
					sb.append("=" + capital);
				}
				ratedayBBean.setCapital(capital);
				// 从新设置本金结束

				// 从新设置本金开始
				String interest = ratedayBBean.getInterest();
				if (lastRatedayBean != null) {
					String lastInterest = lastRatedayBean.getInterest();
					interest = BigNumberUtil.Add(interest, lastInterest);
				}

				ratedayBBean.setInterest(interest);
				// 从新设置本金结束

				String accFee = ratedayBBean.getAccFee();
				if (lastRatedayBean != null) {
					String lastAccFee = lastRatedayBean.getAccFee();
					accFee = BigNumberUtil.Add(accFee, lastAccFee);
				}
				ratedayBBean.setAccFee(accFee);
			} else {
				if (lastRatedayBean != null) {
					sb.append("当前日终本金(else)=" + lastRatedayBean.getCapital());
					ratedayBBean.setCapital(lastRatedayBean.getCapital());
					ratedayBBean.setInterest(lastRatedayBean.getInterest());
					ratedayBBean.setAccFee(lastRatedayBean.getAccFee());
					// ratedayBean.setTermNo(lastRatedayBean.getTermNo());
				}
			}
			sb.append("**************11111****************");

			// 设置借据号
			ratedayBBean.setDueNo(dueNo);
			// 设置合同号
			ratedayBBean.setPactNo(acLnMstBean.getPactNo());
			ratedayBBean.setId("000000");
			try {
				systemDateServices.updateSystemDate(systemDateBean);
				dayRateServices.saveRateDaybBean(ratedayBBean);
				// log.error("保存成功#"+SystemParm.SystemDate+"#"+ratedayBean.getDueNo());
			} catch (Exception e) {
				log.error("保存失败#" + ratedayBBean.getDueNo());
				// break;
			}
		}
	}


	public String getPenalty_1_15(double lnRate, DayRateServices dayRateServices, PlanServices planServices, String systemDate, AcLnMstBean acLnMstBean, List<LoanRepayPlan> planBeans, LoanRepayPlanBO loanRepayPlanBO, IRepayService repayServiceImpl, String dueBal) {
		String dueno = acLnMstBean.getDueNo();
		String lastReturnDate = "";
		// 判断所还金额已冲动到第几期
		String lastFinishTerm = "0";
		for (LoanRepayPlan plan : planBeans) {
			if ("3".equals(plan.getIf_settle())) {// 已还清
				if (Integer.parseInt(lastFinishTerm) <= Integer.parseInt(plan.getTerm())) {
					lastFinishTerm = plan.getTerm();
				}
			}
		}
		// 获取最后还清那期的下一期还款计划
		LoanRepayPlan tempPlan = new LoanRepayPlan();
		tempPlan.setDue_no(dueno);
		tempPlan.setTerm(BigNumberUtil.Add(lastFinishTerm, "1"));
		LoanRepayPlan nextPlan = loanRepayPlanBO.viewPlan(tempPlan).get(0);
		String nextTermDate = nextPlan.getBeg_date();

		// 3.判断2、systemDate的先后顺序，得出结论是否需要计算罚息
		// nextTermDate>systemDate==>nextTermDate+1
		lastReturnDate = DateUtil.addByDay(nextTermDate, 1);

		StringBuffer fx = new StringBuffer();

		int day = DateUtil.getBetweenDays(lastReturnDate, systemDate);

		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("dueNo", dueno);
		if (day < 0) {
			day = 0;
		}
		parmMap.put("days", String.valueOf(day));

		dayRateServices.upFive(parmMap);
		dayRateServices.upLn(parmMap);

		if (day < 0) {
			return "0";
		}

		String baseMoney = acLnMstBean.getDueAmt();
		// 计算费用收取基数
		if ("2".equals(acLnMstBean.getBaseFee())) {
			baseMoney = dueBal;// 余额
		}

		// 逾期利息 = 合同本金*利率/30*逾期天数
		fx.append("借据号(" + dueno + ")#发生日期" + systemDate + "#" + systemDate + "-" + lastReturnDate + "=" + day + "天#");
		fx.append(baseMoney);
		fx.append("×");
		fx.append(day);
		fx.append("×");
		fx.append(lnRate);
		fx.append("÷");
		fx.append("30000");
		fx.append("=");

		// 将逾期天数更新到借据表中
		Map<String, String> parmMap1 = new HashMap<String, String>();
		parmMap1.put("dueNo", dueno);
		parmMap1.put("overDays", String.valueOf(day));
		repayServiceImpl.upLnDue(parmMap1);

		String overins = BigNumberUtil.Multiply(baseMoney, String.valueOf(lnRate));// 22.4%
		overins = BigNumberUtil.Divide2(overins, "30000");
		LoanRepayPlan pb = new LoanRepayPlan();
		if (planBeans != null && planBeans.size() > 0) {
			Collections.sort(planBeans);

			for (LoanRepayPlan x : planBeans) {
				if (DateUtil.gteq(x.getBeg_date(), systemDate) && DateUtil.gteq(systemDate, x.getEnd_date())) {
					pb = x;
					break;
				}
			}
		}
		String interest = BigNumberUtil.Divide2(pb.getInterest(), "30");
		if (DateUtil.gteq(systemDate, planBeans.get(planBeans.size() - 1).getEnd_date())) {// 如果最后一期逾期之后将不再减去当日普通利息
			overins = BigNumberUtil.Subtract(overins, interest); // 每一天应交fx
																	// =当日本金余额*22.4/360
																	// - 当日普通利息
		}

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

	public String getPenalty_2(double lnRate, DayRateServices dayRateServices, 
			PlanServices planServices, CollectFeeService collectFeeService, String systemDate, 
			AcLnMstBean acLnMstBean, LoanRepayPlanBO loanRepayPlanBO, List<LoanRepayPlan> planBeans,String dueBal) {
		String csf = "0.00";
		String lastReturnDate = "";
		String dueno = acLnMstBean.getDueNo();
		// 判断所还金额已冲动到第几期
		String lastFinishTerm = "0";
		for (LoanRepayPlan plan : planBeans) {
			if ("3".equals(plan.getIf_settle())) {// 已还清
				if (Integer.parseInt(lastFinishTerm) <= Integer.parseInt(plan.getTerm())) {
					lastFinishTerm = plan.getTerm();
				}
			}
		}
		// 获取最后还清那期的下一期还款计划
		LoanRepayPlan tempPlan = new LoanRepayPlan();
		tempPlan.setDue_no(dueno);
		tempPlan.setTerm(BigNumberUtil.Add(lastFinishTerm, "1"));
		LoanRepayPlan nextPlan = loanRepayPlanBO.viewPlan(tempPlan).get(0);
		String nextTermDate = nextPlan.getBeg_date();

		// 3.判断2、systemDate的先后顺序，得出结论是否需要计算罚息
		// nextTermDate>systemDate==>nextTermDate+1
		lastReturnDate = DateUtil.addByDay(nextTermDate, 1);

		int day = DateUtil.getBetweenDays(lastReturnDate, systemDate);

		if (day > 60) {
			RatedayBean ratedayBean = new RatedayBean();
			ratedayBean.setDueNo(acLnMstBean.getDueNo());
			String lastDate = DateUtil.addByDay(systemDate, -1, DateUtil.DATE_FORMAT_);
			ratedayBean.setOccureDate(lastDate);
			ratedayBean = dayRateServices.getRateDay(ratedayBean);
			csf = ratedayBean.getCsf();// 前一天催收费

			CollectFeePolicy p = collectFeeService.getById(acLnMstBean.getCollectfeeId());
			String fee = "0.00";
			// 到期前每月2%
			if (!DateUtil.gt(acLnMstBean.getDueEndDate(), systemDate)) {
				fee = BigNumberUtil.Multiply(dueBal, p.getBaseRate());
			} else {
				// 到期后首月3%, 之后每月递增1%
				int overlastdays = DateUtil.getBetweenDays(acLnMstBean.getDueEndDate(), systemDate);
				int unit = overlastdays % 30;
				String addRate = BigNumberUtil.Multiply(p.getAddRate(), unit + "");
				String rate = BigNumberUtil.Add(p.getOverRate(), addRate);
				fee = BigNumberUtil.Multiply(dueBal, rate);
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
	 * 
	 * @return String
	 * @author 乾之轩
	 * @date 2012-5-24 下午12:02:57
	 */
	private int defaultTerm(AcLnMstBean acLnMstBean) {
		// 还款计划第一期的开始日期(默认和借据的开始日期相同)
		String begDate = acLnMstBean.getDueBegDate();
		// 借据的开始日期
		String endDate = acLnMstBean.getDueEndDate();
		int[] monthAndDays = DateUtil.getMonthsAndDays(begDate, endDate);
		boolean isdate = false;
		if (StringUtil.isNotEmpty(acLnMstBean.getAuthId())) {
			int putoutDay = DateUtil.getDay(endDate);
			if (putoutDay != Integer.parseInt(acLnMstBean.getAuthId())) {
				isdate = true;
			}
		}
		if (isdate) {
			return monthAndDays[0];
		} else if (monthAndDays[1] > 0) {
			return monthAndDays[0] + 1;
		} else {
			return monthAndDays[0];
		}
	}

	/**
	 * 
	 * 方法描述：算出逾期的天数和期数
	 * 
	 * @param dueno
	 *            借据号
	 * @param termno
	 *            借据第几期
	 * @return int[]
	 * @author songshengkai
	 * @date Jan 26, 2013 11:16:59 AM
	 */
	public int[] getOverduePerioddays(String dueno, String termno, DayRateServices dayRateServices, String systemDate) {
		PlanBean planbean = new PlanBean();
		planbean.setDueNo(dueno);
		List<PlanBean> planbeanList = dayRateServices.getOverdueplanBeans(planbean);// 获取一共逾期一共几期，还过款和没有还过款的都包括
		int countdays = 0;// 逾期天数
		int j = 0;// 逾期期数
		for (PlanBean planBean : planbeanList) {
			if (Integer.valueOf(termno) >= Integer.valueOf(planBean.getTermNo())) {
				if (planBean.getIsDebt() != null && Integer.valueOf(planBean.getIsDebt()) > 0) {
					countdays = countdays + Integer.valueOf(planBean.getIsDebt());
					j++;
				} else if (planBean.getState().equals("1") || (planBean.getState().equals("0") && DateUtil.getBetweenDays(planBean.getEndDate(), systemDate) > 0)) {
					countdays = countdays + DateUtil.getBetweenDays(planBean.getEndDate(), systemDate);
					j++;
				}
			}
		}
		int[] da = { countdays, j };
		return da;
	}



	/**
	 * 获取计算罚息的时开始日期
	 */
	private String getOverLastTerm(List<RepayBean> repayBeans, List<PlanBean> PlanBeans) {
		String termNo = "1";
		if (PlanBeans == null || PlanBeans.size() == 0) {
			return termNo;
		}
		if (repayBeans == null || repayBeans.size() == 0) {
			return termNo;
		}
		Collections.sort(PlanBeans);
		PlanBean planBean = PlanBeans.get(Integer.parseInt(termNo) - 1);
		RepayBean tempRepayBean = repayBeans.get(0);

		termNo = tempRepayBean.getTermNo();
		// 统计最后一次还款的期供信息
		String sum = getReturnOfTerm(repayBeans, termNo).get(0);
		// 当期的期供信息
		String sum0 = BigNumberUtil.Add(planBean.getReturnCapital(), planBean.getReturnInterest());
		// 当期偿还的期供和期供的差值
		Double d = Double.parseDouble(BigNumberUtil.Subtract(sum, sum0));
		// 当期已经偿还完毕
		if (d > 0 || d == 0) {
			termNo = BigNumberUtil.add2String(tempRepayBean.getTermNo(), "1");
			return termNo;
		}

		String sumReturn = "0.00";
		for (RepayBean repayBean : repayBeans) {
			sumReturn = BigNumberUtil.Add(sumReturn, repayBean.getReturnCapital(), repayBean.getReturnInterest(), repayBean.getBjbalance(), repayBean.getRatecomebalance());
		}

		Double d2 = Double.parseDouble(sumReturn);

		for (PlanBean planBean_ : PlanBeans) {
			String termReturn = BigNumberUtil.Add(planBean_.getReturnCapital(), planBean_.getReturnInterest());
			Double d1 = Double.parseDouble(termReturn);
			d2 = d2 - d1;
			d2 = Double.parseDouble(BigNumberUtil.Divide2(String.valueOf(d2), "1"));
			if (d2 < 0) {
				return planBean_.getTermNo();
			}
		}

		return termNo;
	}

	/**
	 * 获取某一期的还款信息
	 */
	private List<String> getReturnOfTerm(List<RepayBean> repayBeans, String termNo) {
		List<String> returnInf = new ArrayList<String>();
		if (repayBeans == null || repayBeans.size() == 0) {
			returnInf.add("0.00");
			returnInf.add("0.00");
			return returnInf;
		}

		if (termNo == null) {
			returnInf.add("0.00");
			returnInf.add("0.00");
			return returnInf;
		}
		// 期供
		String repaytotal = "0.00";
		// 结余
		String repayBalance = "0.00";
		for (RepayBean repayBean : repayBeans) {
			if (StringUtil.equals(repayBean.getTermNo().trim(), termNo.trim())) {
				repaytotal = BigNumberUtil.Add(repaytotal, repayBean.getReturnCapital(), repayBean.getReturnInterest());
				repayBalance = BigNumberUtil.Add(repayBalance, repayBean.getBjbalance(), repayBean.getRatecomebalance());
			}
		}
		returnInf.add(repaytotal);
		returnInf.add(repayBalance);
		return returnInf;
	}

}
