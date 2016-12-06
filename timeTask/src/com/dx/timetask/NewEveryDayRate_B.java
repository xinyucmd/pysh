/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� DayRate.java
 * ������ timetask
 * ˵����
 * @author Ǭ֮��
 * @date 2012-6-11 ����04:37:13
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
 * ������ DayRate ��������Ϣ���ռ���
 * 
 * @author Ǭ֮��
 * @date 2012-6-11 ����04:37:13
 */
public class NewEveryDayRate_B extends QuartzJobBean {
	private Log log = LogFactory.getLog(getClass());

	/**
	 * ��������
	 * 
	 * @param arg0
	 * @throws JobExecutionException
	 */
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// ϵͳ���ڸ�ʽ��
		String currDate = null;
		IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		// ����ƻ�������
		PlanServices planServices = SpringFactory.getBean("planServices");
		// ���մ��������
		DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		// ϵͳ���ڷ�����
		SystemDateServices systemDateServices = SpringFactory.getBean("systemDateServices");
		// ���շѹ���
		CollectFeeService collectFeeService = SpringFactory.getBean("collectFeeService");

		// �½�����ط�����
		// ����ƻ�
		LoanRepayPlanBO loanRepayPlanBO = SpringFactory.getBean("loanRepayPlanBO");
		// ������ʷ
		LoanRepayHisBO loanRepayHisBO = SpringFactory.getBean("loanRepayHisBO");
		// ��ˮ
		AccountRepayRelBO accountRepayRelBO = SpringFactory.getBean("accountRepayRelBO");
		// ���˻�
		LoanMainAccountBO loanMainAccountBO = SpringFactory.getBean("loanMainAccountBO");
		// ���˻�
		LoanSubAccountBO loanSubAccountBO = SpringFactory.getBean("loanSubAccountBO");

		// ����ƻ��б�
		List<LoanRepayPlan> planBeans = null;
		// ������ˮ
		List<AccountRepayRel> repayRels = null;
		// ������ʷ
		List<LoanRepayHis> repayHisList = null;
		// ���˻�
		List<LoanSubAccount> subAccountList = null;
		
		// ���㷣Ϣ�Ŀ�ʼ����
		String beginDate = null;

		// �Ƿ���㷣Ϣ�ı�־
		boolean sqfx = false;

		// ����ϵͳ����
		SystemDateBean systemDateBean = new SystemDateBean();
		// �������ڸ�ʽ
		systemDateBean.setLastDate(SystemParm.SystemDate.replace("-", ""));
		String systemDate = DateUtil.addByDay(SystemParm.SystemDate.trim(), 1, DateUtil.DATE_FORMAT_);

		currDate = systemDate;
		systemDateBean.setNowDate(systemDate.replace("-", ""));

		SystemParm.SystemDate = systemDate;

		AcLnMstBean parmAcLnMstBean = new AcLnMstBean();
		parmAcLnMstBean.setDueState("1");
		parmAcLnMstBean.setDueNo("JC1033703012015030316816001");
		// ��ѯ������Щ�����Ҫ������������
		List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
		if (acLnMstBeans == null || acLnMstBeans.size() == 0) {
			return;
		}

		for (AcLnMstBean acLnMstBean : acLnMstBeans) {
			// ��ݺ�
			String dueNo = acLnMstBean.getDueNo();
			// ���˻�
			LoanMainAccount mainAccount = new LoanMainAccount();
			mainAccount.setDue_no(dueNo);
			mainAccount = loanMainAccountBO.viewMainAccount(mainAccount);
			// ���˻�
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
				log.error("��ݺŲ���Ϊ��!");
				continue;
			}
			// ��ȡ���л���ƻ�list
			LoanRepayPlan tempPlan = new LoanRepayPlan();
			tempPlan.setDue_no(dueNo);
			planBeans = loanRepayPlanBO.viewPlan(tempPlan);

			// ��ȡ���еĻ�����ˮ
			AccountRepayRel tempRel = new AccountRepayRel();
			tempRel.setDue_no(acLnMstBean.getDueNo());
			repayRels = accountRepayRelBO.viewRel(tempRel);

			// ��ȡ���еĻ�����ʷ
			LoanRepayHis tempHis = new LoanRepayHis();
			tempHis.setSeq_no(repayRels.get(0).getSeq_no());
			repayHisList = loanRepayHisBO.viewHis(tempHis);

			RatedayBBean ratedayBBean = new RatedayBBean();
			ratedayBBean.setDueNo(acLnMstBean.getDueNo());
			// �ж���������ѳ嶯���ڼ���
			String lastFinishTerm = "0";
			for (LoanRepayPlan plan : planBeans) {
				if ("3".equals(plan.getIf_settle())) {// �ѻ���
					if (Integer.parseInt(lastFinishTerm) <= Integer.parseInt(plan.getTerm())) {
						lastFinishTerm = plan.getTerm();
					}
				}
			}
			// 2.��ȡ��������ڵ���һ�ڻ���ƻ�
			tempPlan = new LoanRepayPlan();
			tempPlan.setDue_no(dueNo);
			tempPlan.setTerm(BigNumberUtil.Add(lastFinishTerm, "1"));
			LoanRepayPlan nextPlan = loanRepayPlanBO.viewPlan(tempPlan).get(0);
			String nextTermDate = nextPlan.getBeg_date();

			// 3.�ж�2��systemDate���Ⱥ�˳�򣬵ó������Ƿ���Ҫ���㷣Ϣ
			boolean flg = DateUtil.gt(systemDate, nextTermDate);
			if (flg) {
				// nextTermDate>systemDate==>nextTermDate+1
				sqfx = false;
			} else {
				sqfx = true;
			}

			// ��ʣ�౾��Ϊ0ʱ���ٽ�����������
			if (Double.parseDouble(dueBal) == 0 || Double.parseDouble(dueBal) < 0) {
				ratedayBBean.setOverInterest("0.00");
			} else {
				// �ж��Ƿ���㷣Ϣ.(sqfxΪtrue ����Ҫ��ȡ��Ϣ)
				if (sqfx) {
					// �Ӷ���ģ���еõ���Ϣ���ʽ��м��㣬ȡ��ԭ�ȵĺ�ͬ����
					double overrate = Double.valueOf(acLnMstBean.getOverRate());
					// ���һ������ʵ��22.4
					overrate = Double.valueOf(acLnMstBean.getLastRate());
					String overInterest = getPenalty_1_15(overrate, dayRateServices, planServices, systemDate, acLnMstBean, planBeans, loanRepayPlanBO, repayServiceImpl,dueBal);
					ratedayBBean.setOverInterest(overInterest);
					String csf = getPenalty_2(overrate, dayRateServices, planServices, collectFeeService, systemDate, acLnMstBean, loanRepayPlanBO, planBeans,dueBal);
					ratedayBBean.setCsf(csf);
				} else {
					ratedayBBean.setOverInterest("0.00");
					ratedayBBean.setCsf("0.00");
					// ��������������Ϊ0
					Map<String, String> parmMap1 = new HashMap<String, String>();
					parmMap1.put("dueNo", acLnMstBean.getDueNo());
					parmMap1.put("overDays", "0");
					repayServiceImpl.upLnDue(parmMap1);
				}
			}
			// ��ȡ��ǰҪ�����Ļ���ƻ�
			tempPlan = new LoanRepayPlan();
			tempPlan.setDue_no(dueNo);
			if (DateUtil.gt(acLnMstBean.getDueEndDate(), currDate)) {
				tempPlan.setBeg_date(acLnMstBean.getDueEndDate());
				tempPlan.setEnd_date(acLnMstBean.getDueEndDate());
			} else {
				tempPlan.setBeg_date(currDate);
				tempPlan.setEnd_date(currDate);
			}

			// ����ֻ��һ�ڵĻ���
			LoanRepayPlan planBean = loanRepayPlanBO.getPlanBean(tempPlan);
			// �������յı���
			if (planBean == null && planBeans.size() == 1) {
				ratedayBBean.setCapital(planBeans.get(0).getCapital());
				// ����������Ϣ
				ratedayBBean.setInterest(planBeans.get(0).getInterest());
				// ���ù����
				ratedayBBean.setAccFee(planBeans.get(0).getOther());
				// ���������ں�
				ratedayBBean.setTermNo("1");
			} else {
				ratedayBBean.setCapital(planBean.getCapital());
				// ����������Ϣ
				ratedayBBean.setInterest(planBean.getInterest());
				// ���ù����
				ratedayBBean.setAccFee(planBean.getOther());
				// ���������ں�
				ratedayBBean.setTermNo(planBean.getTerm());
			}
			// ���÷�������
			ratedayBBean.setOccureDate(currDate);

			// ��ȡ�ý�ֵ���һ��������Ϣ
			RatedayBean parmRatedayBean = new RatedayBean();
			parmRatedayBean.setDueNo(dueNo);
			String lastDate = DateUtil.addByDay(currDate, -1, DateUtil.DATE_FORMAT_);
			parmRatedayBean.setOccureDate(lastDate);
			RatedayBean lastRatedayBean = dayRateServices.getRateDay(parmRatedayBean);
			// ��ȡ��һ�����ս���
			RatedayBean ratedayBean_0 = new RatedayBean();
			ratedayBean_0.setDueNo(dueNo);
			List<RatedayBean> ratedayBeans = dayRateServices.getRateDayBeanList(ratedayBean_0);

			if (planBeans.size() > 1 && ratedayBeans.size() > 0 && lastRatedayBean == null) {
				continue;
			}
			// ��һ�����յ��ں�
			String lastTermNo = null;
			if (lastRatedayBean != null) {
				lastTermNo = lastRatedayBean.getTermNo();
			}

			// �жϵ�ǰ���յ��ںź���һ�����յ��ں��Ƿ�һ��.�����һ��,������Ҫ�����ڵı������Ϣ��ӵ��������յı������Ϣ��.
			StringBuffer sb = new StringBuffer();
			if (planBeans.size() > 1 && !StringUtil.equals(lastTermNo, planBean.getTerm())) {
				// if(StringUtil.equals(currDate, planBean.getEndDate())){

				// �������ñ���ʼ
				String capital = ratedayBBean.getCapital();
				sb.append("**************11111****************");
				sb.append("��ǰ���ձ���(if)=" + capital);
				sb.append("\r\n");
				if (lastRatedayBean != null) {
					String lastCapital = lastRatedayBean.getCapital();
					sb.append("��ǰ����+�������յĺ�=" + capital + "+" + lastCapital);
					capital = BigNumberUtil.Add(capital, lastCapital);
					sb.append("=" + capital);
				}
				ratedayBBean.setCapital(capital);
				// �������ñ������

				// �������ñ���ʼ
				String interest = ratedayBBean.getInterest();
				if (lastRatedayBean != null) {
					String lastInterest = lastRatedayBean.getInterest();
					interest = BigNumberUtil.Add(interest, lastInterest);
				}

				ratedayBBean.setInterest(interest);
				// �������ñ������

				String accFee = ratedayBBean.getAccFee();
				if (lastRatedayBean != null) {
					String lastAccFee = lastRatedayBean.getAccFee();
					accFee = BigNumberUtil.Add(accFee, lastAccFee);
				}
				ratedayBBean.setAccFee(accFee);
			} else {
				if (lastRatedayBean != null) {
					sb.append("��ǰ���ձ���(else)=" + lastRatedayBean.getCapital());
					ratedayBBean.setCapital(lastRatedayBean.getCapital());
					ratedayBBean.setInterest(lastRatedayBean.getInterest());
					ratedayBBean.setAccFee(lastRatedayBean.getAccFee());
					// ratedayBean.setTermNo(lastRatedayBean.getTermNo());
				}
			}
			sb.append("**************11111****************");

			// ���ý�ݺ�
			ratedayBBean.setDueNo(dueNo);
			// ���ú�ͬ��
			ratedayBBean.setPactNo(acLnMstBean.getPactNo());
			ratedayBBean.setId("000000");
			try {
				systemDateServices.updateSystemDate(systemDateBean);
				dayRateServices.saveRateDaybBean(ratedayBBean);
				// log.error("����ɹ�#"+SystemParm.SystemDate+"#"+ratedayBean.getDueNo());
			} catch (Exception e) {
				log.error("����ʧ��#" + ratedayBBean.getDueNo());
				// break;
			}
		}
	}


	public String getPenalty_1_15(double lnRate, DayRateServices dayRateServices, PlanServices planServices, String systemDate, AcLnMstBean acLnMstBean, List<LoanRepayPlan> planBeans, LoanRepayPlanBO loanRepayPlanBO, IRepayService repayServiceImpl, String dueBal) {
		String dueno = acLnMstBean.getDueNo();
		String lastReturnDate = "";
		// �ж���������ѳ嶯���ڼ���
		String lastFinishTerm = "0";
		for (LoanRepayPlan plan : planBeans) {
			if ("3".equals(plan.getIf_settle())) {// �ѻ���
				if (Integer.parseInt(lastFinishTerm) <= Integer.parseInt(plan.getTerm())) {
					lastFinishTerm = plan.getTerm();
				}
			}
		}
		// ��ȡ��������ڵ���һ�ڻ���ƻ�
		LoanRepayPlan tempPlan = new LoanRepayPlan();
		tempPlan.setDue_no(dueno);
		tempPlan.setTerm(BigNumberUtil.Add(lastFinishTerm, "1"));
		LoanRepayPlan nextPlan = loanRepayPlanBO.viewPlan(tempPlan).get(0);
		String nextTermDate = nextPlan.getBeg_date();

		// 3.�ж�2��systemDate���Ⱥ�˳�򣬵ó������Ƿ���Ҫ���㷣Ϣ
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
		// ���������ȡ����
		if ("2".equals(acLnMstBean.getBaseFee())) {
			baseMoney = dueBal;// ���
		}

		// ������Ϣ = ��ͬ����*����/30*��������
		fx.append("��ݺ�(" + dueno + ")#��������" + systemDate + "#" + systemDate + "-" + lastReturnDate + "=" + day + "��#");
		fx.append(baseMoney);
		fx.append("��");
		fx.append(day);
		fx.append("��");
		fx.append(lnRate);
		fx.append("��");
		fx.append("30000");
		fx.append("=");

		// �������������µ���ݱ���
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
		if (DateUtil.gteq(systemDate, planBeans.get(planBeans.size() - 1).getEnd_date())) {// ������һ������֮�󽫲��ټ�ȥ������ͨ��Ϣ
			overins = BigNumberUtil.Subtract(overins, interest); // ÿһ��Ӧ��fx
																	// =���ձ������*22.4/360
																	// - ������ͨ��Ϣ
		}

		// ��δ����ķ�Ϣ
		RatedayBean ratedayBean = new RatedayBean();
		ratedayBean.setDueNo(acLnMstBean.getDueNo());
		String lastDate = DateUtil.addByDay(systemDate, -1, DateUtil.DATE_FORMAT_);
		ratedayBean.setOccureDate(lastDate);
		ratedayBean = dayRateServices.getRateDay(ratedayBean);
		overins = BigNumberUtil.Add(overins, ratedayBean.getOverInterest());// +ǰһ��fx

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
		// �ж���������ѳ嶯���ڼ���
		String lastFinishTerm = "0";
		for (LoanRepayPlan plan : planBeans) {
			if ("3".equals(plan.getIf_settle())) {// �ѻ���
				if (Integer.parseInt(lastFinishTerm) <= Integer.parseInt(plan.getTerm())) {
					lastFinishTerm = plan.getTerm();
				}
			}
		}
		// ��ȡ��������ڵ���һ�ڻ���ƻ�
		LoanRepayPlan tempPlan = new LoanRepayPlan();
		tempPlan.setDue_no(dueno);
		tempPlan.setTerm(BigNumberUtil.Add(lastFinishTerm, "1"));
		LoanRepayPlan nextPlan = loanRepayPlanBO.viewPlan(tempPlan).get(0);
		String nextTermDate = nextPlan.getBeg_date();

		// 3.�ж�2��systemDate���Ⱥ�˳�򣬵ó������Ƿ���Ҫ���㷣Ϣ
		// nextTermDate>systemDate==>nextTermDate+1
		lastReturnDate = DateUtil.addByDay(nextTermDate, 1);

		int day = DateUtil.getBetweenDays(lastReturnDate, systemDate);

		if (day > 60) {
			RatedayBean ratedayBean = new RatedayBean();
			ratedayBean.setDueNo(acLnMstBean.getDueNo());
			String lastDate = DateUtil.addByDay(systemDate, -1, DateUtil.DATE_FORMAT_);
			ratedayBean.setOccureDate(lastDate);
			ratedayBean = dayRateServices.getRateDay(ratedayBean);
			csf = ratedayBean.getCsf();// ǰһ����շ�

			CollectFeePolicy p = collectFeeService.getById(acLnMstBean.getCollectfeeId());
			String fee = "0.00";
			// ����ǰÿ��2%
			if (!DateUtil.gt(acLnMstBean.getDueEndDate(), systemDate)) {
				fee = BigNumberUtil.Multiply(dueBal, p.getBaseRate());
			} else {
				// ���ں�����3%, ֮��ÿ�µ���1%
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
	 * ���������� ���Ĭ������
	 * 
	 * @return String
	 * @author Ǭ֮��
	 * @date 2012-5-24 ����12:02:57
	 */
	private int defaultTerm(AcLnMstBean acLnMstBean) {
		// ����ƻ���һ�ڵĿ�ʼ����(Ĭ�Ϻͽ�ݵĿ�ʼ������ͬ)
		String begDate = acLnMstBean.getDueBegDate();
		// ��ݵĿ�ʼ����
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
	 * ����������������ڵ�����������
	 * 
	 * @param dueno
	 *            ��ݺ�
	 * @param termno
	 *            ��ݵڼ���
	 * @return int[]
	 * @author songshengkai
	 * @date Jan 26, 2013 11:16:59 AM
	 */
	public int[] getOverduePerioddays(String dueno, String termno, DayRateServices dayRateServices, String systemDate) {
		PlanBean planbean = new PlanBean();
		planbean.setDueNo(dueno);
		List<PlanBean> planbeanList = dayRateServices.getOverdueplanBeans(planbean);// ��ȡһ������һ�����ڣ��������û�л�����Ķ�����
		int countdays = 0;// ��������
		int j = 0;// ��������
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
	 * ��ȡ���㷣Ϣ��ʱ��ʼ����
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
		// ͳ�����һ�λ�����ڹ���Ϣ
		String sum = getReturnOfTerm(repayBeans, termNo).get(0);
		// ���ڵ��ڹ���Ϣ
		String sum0 = BigNumberUtil.Add(planBean.getReturnCapital(), planBean.getReturnInterest());
		// ���ڳ������ڹ����ڹ��Ĳ�ֵ
		Double d = Double.parseDouble(BigNumberUtil.Subtract(sum, sum0));
		// �����Ѿ��������
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
	 * ��ȡĳһ�ڵĻ�����Ϣ
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
		// �ڹ�
		String repaytotal = "0.00";
		// ����
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
