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

/**
 * ������ DayRate
 * ��������Ϣ���ռ���
 * @author Ǭ֮��
 * @date 2012-6-11 ����04:37:13
 */
public class EveryDayRate extends QuartzJobBean {
	private Log log = LogFactory.getLog(getClass());
	
	/**
	 * ��������
	 * @param arg0
	 * @throws JobExecutionException
	 */
	protected void executeInternal(JobExecutionContext arg0)	throws JobExecutionException {
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
		 // ����ƻ��б�
		 List<PlanBean> planBeans = null;
		 // ���㷣Ϣ�Ŀ�ʼ����
		 String beginDate = null;
		 
		 //�Ƿ���㷣Ϣ�ı�־
		 boolean sqfx  = false;
		 // ��ʶ�ϴ��ǻ�����һ�ڻ���ƻ�
		 String termNo = "1";
		
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
		//parmAcLnMstBean.setDueNo("JC2003703022014122516581001");
		// ��ѯ������Щ�����Ҫ������������
		List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
		if(acLnMstBeans==null  || acLnMstBeans.size()==0){
			return;
		}

		for(AcLnMstBean acLnMstBean :acLnMstBeans){
			 termNo="1";
			 RatedayBean ratedayBean = new  RatedayBean();
			 // ��ݺ�
			 String dueNo = acLnMstBean.getDueNo();
			 String dueBal = "0.00";
			 dueBal = StringUtil.KillNull(acLnMstBean.getDueBal(),"0.00");
			 
			 if(StringUtil.isEmpty(dueNo)){
				// log.error("��ݺŲ���Ϊ��!");
				 continue;
			 }
			 PlanBean    planBean1 = new PlanBean();
			 planBean1.setDueNo(dueNo);
			 planBeans = planServices.getPlanBeanList(planBean1);
			 
			 RepayBean parmRepayBean = new RepayBean();
			 parmRepayBean.setDueNo(dueNo);
			 List<RepayBean> repayBeans = repayServiceImpl.getRepayBeans(parmRepayBean);
			 
			 // ��ȡ��ǰҪ�����Ļ���ƻ�
			 PlanBean parmPlanBean = new PlanBean();
			 parmPlanBean.setDueNo(dueNo);
			 //��������˺�ͬ�Ľ������ڲ��һ�û�г�������
			 
			 if(DateUtil.gt(acLnMstBean.getDueEndDate(), currDate)){
				 parmPlanBean.setBegDate(acLnMstBean.getDueEndDate());
				 parmPlanBean.setEndDate(acLnMstBean.getDueEndDate());
			 }else{
				 parmPlanBean.setBegDate(currDate);
				 parmPlanBean.setEndDate(currDate);
			 }
			      
			 // ����ֻ��һ�ڵĻ���
			 PlanBean planBean= planServices.getPlanBean(parmPlanBean);
			 if(planBeans.size()>1){
				 if(planBean==null){
					 continue;
				 }	 
			 }
			if (planBeans.size()<1) {
				 continue;
			}
			 //���ü��㷣Ϣ�Ŀ�ʼ����
			 
			 // ����ϴλ������ڲ�Ϊ���򽫷�Ϣ����Ŀ�ʼ��������Ϊ�ϴεĻ�������.
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
			 
			
			 //�ж��Ƿ���Ҫ���㷣Ϣ(����������Ϣ����Ϊ�������ߵ��ڵĻ���ƻ��Ľ�������С�ڵ�ǰ��ϵͳ����)
			 
			 // �жϵ�ǰ�����ں͵��ڻ���ƻ��Ľ������ڵĴ�С(��ǰ���ڴ��ڻ���ƻ��������ڷ���true���򷵻�false)
			 PlanBean parmPlanBean0 = new PlanBean();
			 parmPlanBean0.setDueNo(dueNo);
			 
//			 if(!StringUtil.equals("1", termNo)){
//				 // ������n�ڰѵ�n-1�ڵ����н��೥����,��û�г�����δ�����n�ڵ����
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
			 // �������Ϊ�����ı�־flag1Ϊtrue ˵��Ϊ����
			 boolean flag1 = false;
			// ��Ϣ����Ϊ�����ı�־flag2Ϊtrue ˵��Ϊ����
			 boolean flag2 = false;
			 
			 String bjbalance = acLnMstBean.getBjbalance();
			 // ���bjbalanceΪ������Ϊ0
			 if(StringUtil.isEmpty(bjbalance)){
				 bjbalance  = "0.00";
			 }
			 String ratecomebalance = acLnMstBean.getRatecomebalance();
			// ���ratecomebalanceΪ������Ϊ0
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
			 // �����Ƿ���㷣Ϣ��־����
			// ��ʣ�౾��Ϊ0ʱ���ٽ�����������
			 if(Double.parseDouble(dueBal)==0  ||  Double.parseDouble(dueBal)<0){
				 ratedayBean.setOverInterest("0.00");
			 } else {
				 ratedayBean.setCsf("0.00");
				 // �ж��Ƿ���㷣Ϣ.(sqfxΪtrue ����Ҫ��ȡ��Ϣ)
				 if(sqfx){
					 // �Ӷ���ģ���еõ���Ϣ���ʽ��м��㣬ȡ��ԭ�ȵĺ�ͬ���� 
					// ����ģʽ
					String settleModel = acLnMstBean.getSettleModel();
					 double overrate = Double.valueOf(acLnMstBean.getOverRate());
					 String overInterest="";
					 // ���һ������ʵ��22.4
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
					// ��������������Ϊ0
					Map<String,String> parmMap1 = new HashMap<String,String>();
					parmMap1.put("dueNo", acLnMstBean.getDueNo());
					parmMap1.put("overDays", "0");
					repayServiceImpl.upLnDue(parmMap1);
				 }
			 }
			 // �������յı���
			 if(planBean==null  &&  planBeans.size()==1){
				 ratedayBean.setCapital(planBeans.get(0).getReturnCapital());
				 // ����������Ϣ
				 ratedayBean.setInterest(planBeans.get(0).getReturnInterest());
				 // ���ù����
				 ratedayBean.setAccFee(planBeans.get(0).getAccFee());
				 // ���������ں�		
				 ratedayBean.setTermNo("1");
			 }else{
				 System.out.println(dueNo+"================================");
				 ratedayBean.setCapital(planBean.getReturnCapital());
				 // ����������Ϣ
				 ratedayBean.setInterest(planBean.getReturnInterest());
				 // ���ù����
				 ratedayBean.setAccFee(planBean.getAccFee());
				 // ���������ں�		
				 ratedayBean.setTermNo(planBean.getTermNo());
			 }
			 // ���÷�������
			 ratedayBean.setOccureDate(currDate);
			 
			 //��ȡ�ý�ֵ���һ��������Ϣ
			 RatedayBean parmRatedayBean = new RatedayBean();
			 parmRatedayBean.setDueNo(dueNo);
			 String lastDate = DateUtil.addByDay(currDate, -1, DateUtil.DATE_FORMAT_);
			 parmRatedayBean.setOccureDate(lastDate);
			 RatedayBean lastRatedayBean = dayRateServices.getRateDay(parmRatedayBean);
			 // ��ȡ��һ�����ս���
			 RatedayBean  ratedayBean_0 =  new RatedayBean();
			 ratedayBean_0.setDueNo(dueNo);
			 List<RatedayBean> ratedayBeans = dayRateServices.getRateDayBeanList(ratedayBean_0);
			 
			 if(planBeans.size()>1 && ratedayBeans.size()>0 && lastRatedayBean==null){
				 continue ;
			 }
			 // ��һ�����յ��ں�
			 String lastTermNo = null;
			 if(lastRatedayBean!=null){
				 lastTermNo = lastRatedayBean.getTermNo();
			 }
			 
			 // �жϵ�ǰ���յ��ںź���һ�����յ��ں��Ƿ�һ��.�����һ��,������Ҫ�����ڵı������Ϣ��ӵ��������յı������Ϣ��.
			 StringBuffer sb = new StringBuffer();
			 if(planBeans.size()>1 && !StringUtil.equals(lastTermNo, planBean.getTermNo())){
			// if(StringUtil.equals(currDate, planBean.getEndDate())){
				 
				 // �������ñ���ʼ
				 String capital = ratedayBean.getCapital();
				 sb.append("**************11111****************");
				 sb.append("��ǰ���ձ���(if)="+capital);
				 sb.append("\r\n");
				 if(lastRatedayBean!=null ){
					 String lastCapital = lastRatedayBean.getCapital();
					 sb.append("��ǰ����+�������յĺ�="+capital+"+"+lastCapital);
					 capital =  BigNumberUtil.Add(capital,lastCapital);
					 sb.append("="+capital);
				 }
				  ratedayBean.setCapital(capital);
				// �������ñ������
				
				 // �������ñ���ʼ
				 String interest = ratedayBean.getInterest();
				 if(lastRatedayBean!=null ){
					 String lastInterest = lastRatedayBean.getInterest();
					 interest =  BigNumberUtil.Add(interest,lastInterest);
				 }
				
				 ratedayBean.setInterest(interest);
				 // �������ñ������
				 
				 String accFee = ratedayBean.getAccFee();
				 if(lastRatedayBean!=null ){
					 String lastAccFee = lastRatedayBean.getAccFee();
					 accFee =  BigNumberUtil.Add(accFee,lastAccFee);
				 }
				 ratedayBean.setAccFee(accFee);
			 }else{
				 if(lastRatedayBean!=null ){
					 sb.append("��ǰ���ձ���(else)="+lastRatedayBean.getCapital());
					 ratedayBean.setCapital(lastRatedayBean.getCapital()); 
					 ratedayBean.setInterest( lastRatedayBean.getInterest());
					 ratedayBean.setAccFee(lastRatedayBean.getAccFee());
					// ratedayBean.setTermNo(lastRatedayBean.getTermNo());
				 }
			 }
			 sb.append("**************11111****************");

			 //���ý�ݺ�
			 ratedayBean.setDueNo(dueNo);
			 // ���ú�ͬ��
			 ratedayBean.setPactNo(acLnMstBean.getPactNo());
			 ratedayBean.setId("000000");
			 try{
				 systemDateServices.updateSystemDate(systemDateBean);
				 dayRateServices.saveRateDayBean(ratedayBean);
				 //log.error("����ɹ�#"+SystemParm.SystemDate+"#"+ratedayBean.getDueNo());
			 }catch(Exception e){
              log.error("����ʧ��#"+ratedayBean.getDueNo());
              //break;
			 }
		}
		System.out.println("����ִ�����============");
	}
	
	private String getPenalty_1_15LX(String termNo, double lnRate,
			String dueno, DayRateServices dayRateServices,
			PlanServices planServices, String systemDate,
			AcLnMstBean acLnMstBean) {
		// ��ȡ�̶���������
		StringBuffer fx = new StringBuffer(); 
		String lastReturnDate="";
		//��ȡһ�����ڼ��ڣ���û�л����
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
		// ��ȡ���һ�λ�����Ϣ
		if(repayBeans!=null && repayBeans.size()>0){
			// ��ȡ���һ�λ��������
			lastReturnDate = repayBeans.get(0).getOccDate();
			
			//��ȡ���һ�γ����ı���
			String returnCapital=StringUtil.KillNull(repayBeans.get(0).getReturnCapital(),"0.00");
			//��ȡ���һ�γ�������Ϣ
			String returnInterest=StringUtil.KillNull(repayBeans.get(0).getReturnInterest(),"0.00");
			
			PlanBean parmPlanBean =  new PlanBean();
			parmPlanBean.setDueNo(dueno);
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
			// ��ȡ���һ�λ���ʱ,�Ƿ����˵���
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
			// ����δ����
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
		// ���������ȡ����
		if ("2".equals(acLnMstBean.getBaseFee())) {
			baseMoney = acLnMstBean.getDueBal();// ���
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
					Double.valueOf(acLnMstBean.getDueAmt()));// �ڹ�
			baseMoney = "0";
			for (int i = noPayTerm; i<=Integer.valueOf(termNo); i++) {
				int m = Integer.valueOf(termNo) - i + 1;
				baseMoney = BigNumberUtil.Add(baseMoney, BigNumberUtil.Multiply(pmt, m+""));
			}
		}
		
		// ������Ϣ = ��ͬ����*����/30*��������
		fx.append("��ݺ�("+dueno+")#��������"+ systemDate +"#"+ systemDate +"-"+lastReturnDate+"="+day+"��#");
		fx.append(baseMoney);
		fx.append("��");
		fx.append(day);
		fx.append("��");
		fx.append(lnRate);
		fx.append("��");
		fx.append("30000");
		fx.append("=");
		
		// �������������µ���ݱ���
		Map<String,String> parmMap1 = new HashMap<String,String>();
		parmMap1.put("dueNo", dueno);
		parmMap1.put("overDays", String.valueOf(day));
		repayServiceImpl.upLnDue(parmMap1);
		
		String overrate ="";
		//�Ƚ������Ƿ�������,��ȡ��������
		if (DateUtil.gt(acLnMstBean.getDueEndDate(),systemDate)) {
			overrate = acLnMstBean.getLastRate();
		}else{
			overrate = acLnMstBean.getOverRate();
		}
		String overins = BigNumberUtil.Multiply(acLnMstBean.getDueBal(), overrate);
		overins = BigNumberUtil.Divide2(overins, "30000");
		
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

	public String getPenalty_1(String termNo,double lnRate, String dueno,DayRateServices dayRateServices,
			PlanServices planServices,String systemDate,AcLnMstBean acLnMstBean){
		// ��ȡ�̶���������
		StringBuffer fx = new StringBuffer(); 
		String lastReturnDate="";
		//��ȡһ�����ڼ��ڣ���û�л����
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
		// ��ȡ���һ�λ�����Ϣ
		if(repayBeans!=null && repayBeans.size()>0){
			// ��ȡ���һ�λ��������
			lastReturnDate = repayBeans.get(0).getOccDate();
			
			//��ȡ���һ�γ����ı���
			String returnCapital=StringUtil.KillNull(repayBeans.get(0).getReturnCapital(),"0.00");
			//��ȡ���һ�γ�������Ϣ
			String returnInterest=StringUtil.KillNull(repayBeans.get(0).getReturnInterest(),"0.00");
			
			PlanBean parmPlanBean =  new PlanBean();
			parmPlanBean.setDueNo(dueno);
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
			// ��ȡ���һ�λ���ʱ,�Ƿ����˵���
			PlanBean tempPlanBean = planServices.getPlanBean(parmPlanBean);
			Double d =Double.parseDouble(BigNumberUtil.Subtract(returnCapital, tempPlanBean.getReturnCapital()));
			Double d1 =Double.parseDouble(BigNumberUtil.Subtract(returnInterest, tempPlanBean.getReturnInterest()));
			
			if(d<0 || d1<0){
				lastReturnDate=tempPlanBean.getEndDate();
			}
			
		}
		
//		// û������Ҳû�г���������
//		if(StringUtil.isEmpty(lastReturnDate)){
//		//	log.error("��ݺ�("+dueno+")"+"û������Ҳû��������");
//			//return "0";
//		}
//		/**
//		 * 2014-02-08 �޸�
//		 * ������������,�����ں�Ϣ����Ϊһ���µķ�Ϣ������
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
			// ����δ����
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
		//	log.error("��ݺ�("+dueno+")#��������"+SystemParm.SystemDate+"#"+SystemParm.SystemDate+"-"+lastReturnDate+"="+day+"��#");
			return "0";
		}
		
		String baseMoney = planbeanList.get(0).getDueAmt();
		// ���������ȡ����
		if ("2".equals(acLnMstBean.getBaseFee())) {
			baseMoney = acLnMstBean.getDueBal();// ���
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
					Double.valueOf(acLnMstBean.getDueAmt()));// �ڹ�
			baseMoney = "0";
			for (int i = noPayTerm; i<=Integer.valueOf(termNo); i++) {
				int m = Integer.valueOf(termNo) - i + 1;
				baseMoney = BigNumberUtil.Add(baseMoney, BigNumberUtil.Multiply(pmt, m+""));
			}
		}
		
		// ������Ϣ = ��ͬ����*����/30*��������
		fx.append("��ݺ�("+dueno+")#��������"+ systemDate +"#"+ systemDate +"-"+lastReturnDate+"="+day+"��#");
		fx.append(baseMoney);
		fx.append("��");
		fx.append(day);
		fx.append("��");
		fx.append(lnRate);
		fx.append("��");
		fx.append("30000");
		fx.append("=");
		
		// �������������µ���ݱ���
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
		// ��ȡ�̶���������
		StringBuffer fx = new StringBuffer(); 
		String lastReturnDate="";
		//��ȡһ�����ڼ��ڣ���û�л����
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
		// ��ȡ���һ�λ�����Ϣ
		if(repayBeans!=null && repayBeans.size()>0){
			// ��ȡ���һ�λ��������
			lastReturnDate = repayBeans.get(0).getOccDate();
			
			//��ȡ���һ�γ����ı���
			String returnCapital=StringUtil.KillNull(repayBeans.get(0).getReturnCapital(),"0.00");
			//��ȡ���һ�γ�������Ϣ
			String returnInterest=StringUtil.KillNull(repayBeans.get(0).getReturnInterest(),"0.00");
			
			PlanBean parmPlanBean =  new PlanBean();
			parmPlanBean.setDueNo(dueno);
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
			// ��ȡ���һ�λ���ʱ,�Ƿ����˵���
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
			// ����δ����
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
		// ���������ȡ����
		if ("2".equals(acLnMstBean.getBaseFee())) {
			baseMoney = acLnMstBean.getDueBal();// ���
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
					Double.valueOf(acLnMstBean.getDueAmt()));// �ڹ�
			baseMoney = "0";
			for (int i = noPayTerm; i<=Integer.valueOf(termNo); i++) {
				int m = Integer.valueOf(termNo) - i + 1;
				baseMoney = BigNumberUtil.Add(baseMoney, BigNumberUtil.Multiply(pmt, m+""));
			}
		}
		
		// ������Ϣ = ��ͬ����*����/30*��������
		fx.append("��ݺ�("+dueno+")#��������"+ systemDate +"#"+ systemDate +"-"+lastReturnDate+"="+day+"��#");
		fx.append(baseMoney);
		fx.append("��");
		fx.append(day);
		fx.append("��");
		fx.append(lnRate);
		fx.append("��");
		fx.append("30000");
		fx.append("=");
		
		// �������������µ���ݱ���
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
		if (DateUtil.gteq(systemDate, pblist.get(pblist.size()-1).getEndDate())) {// ������һ������֮�󽫲��ټ�ȥ������ͨ��Ϣ
			overins = BigNumberUtil.Subtract(overins, interest); // ÿһ��Ӧ��fx = ���ձ������*22.4/360 - ������ͨ��Ϣ
		}
		
		// ��δ����ķ�Ϣ
		RatedayBean ratedayBean = new RatedayBean();
		ratedayBean.setDueNo(acLnMstBean.getDueNo());
		String lastDate = DateUtil.addByDay(systemDate, -1, DateUtil.DATE_FORMAT_);
		ratedayBean.setOccureDate(lastDate);
		ratedayBean = dayRateServices.getRateDay(ratedayBean);
		try {
			overins = BigNumberUtil.Add(overins, ratedayBean.getOverInterest());// +ǰһ��fx
		} catch (Exception e) {
			System.out.println(acLnMstBean.getDueNo()+"��������");
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
		//��ȡһ�����ڼ��ڣ���û�л����
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
		// ��ȡ���һ�λ�����Ϣ
		if(repayBeans!=null && repayBeans.size()>0){
			// ��ȡ���һ�λ��������
			lastReturnDate = repayBeans.get(0).getOccDate();
			
			//��ȡ���һ�γ����ı���
			String returnCapital=StringUtil.KillNull(repayBeans.get(0).getReturnCapital(),"0.00");
			//��ȡ���һ�γ�������Ϣ
			String returnInterest=StringUtil.KillNull(repayBeans.get(0).getReturnInterest(),"0.00");
			
			PlanBean parmPlanBean =  new PlanBean();
			parmPlanBean.setDueNo(dueno);
			parmPlanBean.setTermNo(repayBeans.get(0).getTermNo());
			// ��ȡ���һ�λ���ʱ,�Ƿ����˵���
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
			// ����δ����
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
			//System.out.println(ratedayBean.getOccureDate()+ratedayBean.getDueNo()+"****����1111111111111111111111");
			ratedayBean = dayRateServices.getRateDay(ratedayBean);
			//System.out.println(ratedayBean+"****����2222222222222222222222222"+ratedayBean.getCsf());
			csf = ratedayBean.getCsf();// ǰһ����շ�
			
			CollectFeePolicy p = collectFeeService.getById(acLnMstBean.getCollectfeeId());
			String fee = "0.00";
			// ����ǰÿ��2%
			if (!DateUtil.gt(acLnMstBean.getDueEndDate(), systemDate)) {
				fee = BigNumberUtil.Multiply(acLnMstBean.getDueBal(), p.getBaseRate());
			} else {
				// ���ں�����3%, ֮��ÿ�µ���1%
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
	 * ���������� ���Ĭ������ 
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-24 ����12:02:57
	 */
	private int defaultTerm(AcLnMstBean acLnMstBean){
		// ����ƻ���һ�ڵĿ�ʼ����(Ĭ�Ϻͽ�ݵĿ�ʼ������ͬ)
		String begDate = acLnMstBean.getDueBegDate();
		// ��ݵĿ�ʼ����
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
	 * ����������������ڵ����������� 
	 * @param dueno ��ݺ�
	 * @param termno ��ݵڼ���
	 * @return
	 * int[]
	 * @author songshengkai
	 * @date Jan 26, 2013 11:16:59 AM
	 */
	public int[] getOverduePerioddays(String dueno,String termno,DayRateServices dayRateServices,String systemDate){
		PlanBean planbean=new PlanBean();
		planbean.setDueNo(dueno);
		List<PlanBean> planbeanList=dayRateServices.getOverdueplanBeans(planbean);//��ȡһ������һ�����ڣ��������û�л�����Ķ�����
		int countdays=0;//��������
		int j=0;//��������
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
		//  ͳ���ۼƳ�������Ϣ���˻������
		String baseData = "0.00";
		RepayService  repayService = SpringFactory.getBean("repayService");
		List<RepayBean>  repayBeans = repayService.getRepayBeans(ratedayBean.getDueNo());
		// �ۼƳ�������Ϣ���˻�����ѿ�ʼ
		if(repayBeans!=null  && repayBeans.size()>0){
			for(RepayBean repayBean:repayBeans){
				baseData = BigNumberUtil.Add(repayBean.getReturnInterest(),baseData);
				baseData = BigNumberUtil.Add(repayBean.getOtherFee(),baseData);
			}
		}
		// �ۼƳ�������Ϣ���˻�����ѽ���
		
		// ���ڵ���Ϣ �˻������  ��ʣ�౾��ĺ� 
		String base = BigNumberUtil.Add(ratedayBean.getBaseInterest(),ratedayBean.getBaseAccFee(),acLnMstBean.getDueBal());
		sb.append("(");
		sb.append(ratedayBean.getBaseInterest());
		sb.append("+");
		sb.append(ratedayBean.getBaseAccFee());
		sb.append("+");
		sb.append(acLnMstBean.getDueBal());
		
		// �ж����ǲ���ĳһ�ڵĿ�ʼ
		if(nextterm){
			sb.append("+");
			sb.append(overInterest);
			 base = BigNumberUtil.Add(base,overInterest);
		}else{
			sb.append("+");
			sb.append("0.00");
		}
		// ���ڵ���Ϣ �˻������  ��ʣ�౾��ĺͼ�ȥ�ѻ�����Ϣ���˻������
		base = BigNumberUtil.Subtract(base, baseData);
		sb.append("-");
		sb.append(baseData);
		sb.append(")");
		sb.append("x");
		sb.append(acLnMstBean.getOverRate()+"��"+"��30=");
		log.error(sb.toString());
		base=BigNumberUtil.Multiply(base, acLnMstBean.getOverRate());
		base=BigNumberUtil.Divide(base, "30000", 2, "1");
		base = BigNumberUtil.Add(base,overInterest);
		sb.append(base);
		return base;
	}
	
	/**
	 * ������n�ڰѵ�n-1�ڵ����н��೥����,��û�г�����δ�����n�ڵ����
	 *			 
	 */
//	private boolean FinshCurTermBj(List<RepayBean> repayBeans,String dueNo,String termNo,PlanServices planServices){
//		// ͳ�Ƶ�ǰ���Գ����ı���
//		String tempBj="0.00";
//		PlanBean parmPlanBean = new PlanBean();
//		parmPlanBean.setDueNo(dueNo);
//		parmPlanBean.setTermNo(termNo);
//		PlanBean planBean = planServices.getPlanBean(parmPlanBean);
//		// ���ڵı����Ƿ񳥻���ı�ʶ
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
//		// ͳ�Ƶ�ǰ���Գ����ı���
//		String tempLx="0.00";
//		
//		
//		PlanBean parmPlanBean = new PlanBean();
//		parmPlanBean.setDueNo(dueNo);
//		parmPlanBean.setTermNo(termNo);
//		PlanBean planBean = planServices.getPlanBean(parmPlanBean);
//		// ���ڵı����Ƿ񳥻���ı�ʶ
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
//			System.out.println(dueNo+"����������~~~~~~~~~~~");
//			e.printStackTrace();
//		}
//	    if(d>0||d==0) {
//	    	flag=true;
//	    }
//	    return flag;
//	}
	
	/**
	 * ��ȡ���㷣Ϣ��ʱ��ʼ����
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
		// ͳ�����һ�λ�����ڹ���Ϣ
		String sum  = getReturnOfTerm(repayBeans,termNo).get(0); 
		// ���ڵ��ڹ���Ϣ
		String sum0 =  BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest());
		// ���ڳ������ڹ����ڹ��Ĳ�ֵ
		Double d = Double.parseDouble(BigNumberUtil.Subtract(sum,sum0));
		// �����Ѿ��������
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
	 * ��ȡĳһ�ڵĻ�����Ϣ
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
		// �ڹ�
		String repaytotal = "0.00";
		// ����
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
