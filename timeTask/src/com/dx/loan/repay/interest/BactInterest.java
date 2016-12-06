/**
 * Copyright (C) DXHM ��Ȩ����

 * �ļ����� InterestCom.java
 * ������ com.dx.loan.repay.interest
 * ˵����

 * @author Ǭ֮��

 * @date 2012-5-10 ����10:49:09
 * @version V1.0
 */
package com.dx.loan.repay.interest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dx.common.SystemParm;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.dayrate.services.impl.DayRateServices;
import com.dx.loan.debt.bean.DebtBean;
import com.dx.loan.debt.services.impl.DebtServices;
import com.dx.loan.normrate.bean.PartParmBean;
import com.dx.loan.normrate.bean.RateAdjustBean;
import com.dx.loan.normrate.services.impl.NormRateServices;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.dao.IRepayDao;
import com.dx.loan.repay.services.IRepayService;
import com.dx.loan.repay.services.impl.RepayService;



/**
 * ������ InterestCom
 * ������ �ڲ���Ϣ��Ϣ����
 * @author Ǭ֮��

 * @date 2012-5-10 ����10:49:09
 *
 *
 */
public class BactInterest {

	
	
	public RatedayBean getDayInterest(PlanBean planBean){
		 String  occDate = SystemParm.SystemDate;
		 RatedayBean ratedayBean = new RatedayBean();
		 IRepayDao repayDao = SpringFactory.getBean("repayDao");
		 IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		 //���ݽ�ݺŻ�ô������ļ�
		 AcLnMstBean acLnMstBean = repayDao.getAcLnMstBeanByDueNo(planBean.getDueNo());
		 // ��ü�Ϣ��ʽ
		 String returnMethod = acLnMstBean.getReturnMethod();
		 // ���汾��
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR,returnMethod)){
			 // ������Ϣ
			 String interest = profitsClearInterest(planBean,acLnMstBean,false);
			 // ������Ϣ
			 String overInterest  = profitsClearOverInterest(acLnMstBean);
			 // ������Ϣ
			 String cmpdInterest  = profitsClearCmpdInterest(acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(acLnMstBean.getDueBal());
			 if(DateUtil.gt(planBean.getEndDate(), occDate)){
				 // ����
				 ratedayBean.setState("1");
			 }else{
				 // û�� ����
				 ratedayBean.setState("0");
			 }
		 }
		 
		 // ���ƻ�
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_PLAN, returnMethod)){
			 String interest = planInterest(planBean,acLnMstBean,false);
			 // ������Ϣ
			 String overInterest  = planOverInterest(planBean ,acLnMstBean);
			 // ������Ϣ
			 String cmpdInterest  = planCmpdInterest(planBean,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			 
			// if(DateUtil.gt(planBean.getEndDate(), occDate) && StringUtil.equals("1", acLnMstBean.getIsForce())){
			  if(DateUtil.gt(planBean.getEndDate(), occDate)){
				 // ����
				 ratedayBean.setState("1");
			 }else{
				 // û�� ����
				 ratedayBean.setState("0");
			 }
		 }
		 
		 //һ�γ��������½�Ϣ
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, returnMethod)){
			 String interest = oneMonthInterest(planBean,acLnMstBean,false);
			 // ������Ϣ
			 String overInterest  = oneMonthOverInterest(planBean ,acLnMstBean);
			 // ������Ϣ
			 String cmpdInterest  = oneMonthCmpdInterest(planBean ,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			// if(DateUtil.gt(planBean.getEndDate(), occDate) && StringUtil.equals("1", acLnMstBean.getIsForce())){
			  if(DateUtil.gt(planBean.getEndDate(), occDate) ){
				 // ����
				 ratedayBean.setState("1");
			 }else{
				 // û�� ����
				 ratedayBean.setState("0");
			 }
		 }
		 
		//һ�γ��������½�Ϣ
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_SEASON, returnMethod)){
			 String interest = oneMonthInterest(planBean,acLnMstBean,false);
			 // ������Ϣ
			 String cmpdInterest  = oneMonthCmpdInterest(planBean ,acLnMstBean);
			 // ������Ϣ
			 String overInterest  = oneMonthOverInterest(planBean ,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			 if(DateUtil.gt(planBean.getEndDate(), occDate) && StringUtil.equals("1", acLnMstBean.getIsForce())){
				 // ����
				 ratedayBean.setState("1");
			 }else{
				 // û�� ����
				 ratedayBean.setState("0");
			 }
		 }
		 
		 // �ȶ��
		 if(StringUtil.equals(SystemParm.RETURNMETHOD_PRINCIPAL, returnMethod)){
			 String interest = matchPrincipalInterest(planBean,acLnMstBean,false);
			// ������Ϣ
			 String overInterest  = matchPrincipalOverInterest(planBean ,acLnMstBean);
			 // ������Ϣ
			 String cmpdInterest  = matchPrincipalCmpdInterest(planBean ,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			 if(DateUtil.gt(planBean.getEndDate(), occDate)){
				 // ����
				 ratedayBean.setState("1");
			 }else{
				 // û�� ����
				 ratedayBean.setState("0");
			 }
			 
		 }
		 
		 // �ȶϢ
         if(StringUtil.equals(SystemParm.RETURNMETHOD_INTEREST, returnMethod)){
        	 String interest = monthPrincipalInterest(planBean,acLnMstBean,false);
        	// ������Ϣ
			 String overInterest  = monthPrincipalOverInterest(planBean ,acLnMstBean);
			 
			 // ������Ϣ
			 String cmpdInterest  = monthPrincipalCmpdInterest(planBean ,acLnMstBean);
			 ratedayBean.setInterest(interest);
			 ratedayBean.setCmpdInterest(cmpdInterest);
			 ratedayBean.setOverInterest(overInterest);
			 ratedayBean.setCapital(repayServiceImpl.remaCapital(planBean));
			 
			 if(DateUtil.gt(planBean.getEndDate(), occDate)){
				 // ����
				 ratedayBean.setState("1");
			 }else{
				 // û�� ����
				 ratedayBean.setState("0");
			 }
		 } 		 
		 
         ratedayBean.setDueNo(planBean.getDueNo());
         
         ratedayBean.setId(StringUtil.getPK("R"));
         ratedayBean.setOccureDate(occDate);
         ratedayBean.setPactNo(planBean.getPactNo());
         ratedayBean.setTermNo(planBean.getTermNo());
        
         RepayService repayService = SpringFactory.getBean("repayService");        
         // list[0] �ۼƻ����
         // list[1] �ۼƻ�����Ϣ
         // list[2] �ۼƻ���������Ϣ
         // list[3] �ۼƻ������Ϣ
         // list[4] �ۼƻ����˻������
         List<String> hasRepayList = repayService.getHasRepay(planBean.getDueNo(), planBean.getTermNo());
         // ����ʵ�ʱ����ȥ�ѻ�����
         ratedayBean.setCapital(BigNumberUtil.Subtract(ratedayBean.getCapital(),hasRepayList.get(0)));
         // �����˻�����Ѽ�ȥ�Ѿ��������˻������
         ratedayBean.setAccFee(BigNumberUtil.Subtract(StringUtil.KillNull(ratedayBean.getAccFee(), "0.00"), hasRepayList.get(4)));
         
		 return ratedayBean;
	}
	
	/****************************����������Ϣ����****************************/
	/**
	 * 
	 * ���������� ���汾��������Ϣ (Ƿ����*��������*��������)
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����07:27:49
	 */
	private String profitsClearOverInterest(AcLnMstBean acLnMstBean){
		String overInterest = "0.00";
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		String overRate = acLnMstBean.getOverRate();
		// ���Ƿ���
		String dueBal = acLnMstBean.getDueBal();
		String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
		String begDate = acLnMstBean.getDueEndDate();
		// ������ʷ�����
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		// �������ʱ�Ż�����ʵ����б�
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		// �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		// ������ڽ�ݽ������ں͵�ǰ����֮�����Ч��
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
		// ��þ����ݽ������������һ�����ʵ���
		PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, begDate);
		if(latePartParmBean!=null){
			// �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��
			if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
				overRate = latePartParmBean.getOverRate();
			}
		}
		
		if(filterEffectDates!=null && filterEffectDates.size()>0){
			int size = filterEffectDates.size();
			
			for (int i = 0; i <size; i++) {
				if(i<size-1){
					int days =  0;
					int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
					if(monthAndDay[1]>0 && monthOfDays==1){
						days  =  monthAndDay[0]*30+monthAndDay[1];
					}else{
						days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
					}
					// ����*����
					String tempOverInterest = BigNumberUtil.Multiply(dueBal, String.valueOf(days));
					// ����������ת��Ϊ������
					String dayOverRate = monthRate2DayRate(overRate);
					// ����*����*����
					tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, dayOverRate);
					tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
					overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
					
					begDate = filterEffectDates.get(i).getEffectDate();
					overRate = latePartParmBean.getOverRate();
				}else{
					int days =  0;
					int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
					if(monthAndDay[1]>0 && monthOfDays==1){
						days  =  monthAndDay[0]*30+monthAndDay[1];
					}else{
						days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
					}
					// ����*����
					String tempOverInterest = BigNumberUtil.Multiply(dueBal, String.valueOf(days));
					// ����������ת��Ϊ������
					String dayOverRate = monthRate2DayRate(overRate);
					// ����*����*����
					tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, dayOverRate);
					tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
					overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
				}
 			}
		}else{
			int days =  0;
			int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
			if(monthAndDay[1]>0 && monthOfDays ==1){
				days  =  monthAndDay[0]*30+monthAndDay[1];
			}else{
				days = DateUtil.getBetweenDays(begDate, endDate);
			}
			// ����*����
			String tempOverInterest = BigNumberUtil.Multiply(dueBal,  String.valueOf(days));
			// ����������ת��Ϊ������
			String dayOverRate = monthRate2DayRate(overRate);
			// ����*����*����
			tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, dayOverRate);
			tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
			overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		}
		
		return  overInterest;
	}
	/**
	 * 
	 * ���������� ���ƻ���������Ϣ 
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-23 ����11:03:39
	 */
	private  String planOverInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		     int monthOfDays = SystemParm.MONTH_OF_DAYS;
			 String overInterest = "0.00";
			 // ���ʣ����
			 String dueBal  = acLnMstBean.getDueBal();
			 /**
			  * ����ں�С�൱ǰ�ں����Ѿ����ڵ���Ϣ
			  */
			 // �������ڵĻ���
			 String baseOver="0.00";
			 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
			 RatedayBean ratedayBean = new RatedayBean(); 
			 ratedayBean.setDueNo(planBean.getDueNo());
			 ratedayBean.setTermNo(planBean.getTermNo());
			 // ״̬Ϊ����
			 ratedayBean.setState("1");
			 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
			 // ���շ�����������
			 
			 Collections.sort(ratedayBeans);
			 int termno = Integer.parseInt(planBean.getTermNo());
			 
			 for(int i=1;i<termno;++i){
				 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
				if (myRatedayBean!=null) {
					baseOver = BigNumberUtil.Add(baseOver,myRatedayBean.getInterest(),myRatedayBean.getOverInterest(),myRatedayBean.getCmpdInterest(),myRatedayBean.getAccFee());
				}
			 }
			 
			 baseOver = BigNumberUtil.Add(baseOver,dueBal,planBean.getAccFee());
			 
			 
			 // ����������Ϣ�Ľ�������
			 String endDate = SystemParm.SystemDate;	
		     // �����������
		     String overRate = acLnMstBean.getOverRate();
		 	 // ������ʷ�����
			 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
			 // �������ʱ�Ż�����ʵ����б�
			 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
			 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
			// List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
			 // �������������ʵ��� 
			 List<PartParmBean> effectDates = null;
		     
		     // ��ǿ�Ƽƻ�
		     if(SystemParm.IS_FORCE==1){
		    	// ����������Ϣ�Ŀ�ʼ (���ڼƻ��Ľ�������)
	    		 String begDate = planBean.getEndDate(); 
		    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
				 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
				 // ��þ����ݽ������������һ�����ʵ���
				 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
				 if(latePartParmBean!=null){
					 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
					 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
						 overRate = latePartParmBean.getOverRate();
					 }
				 }
				 //��ǰ�������Ѿ��������ڵĽ�������
		    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
		    		   
		    		 // ��������ʵ���
		    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
		    			 int size = filterEffectDates.size();
			    		 for(int i=0;i<size;++i){
			    			 if(i<size-1){
					    		 int days =  0;
								 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
								 if(monthAndDay[1]>0 && monthOfDays==1){
									days  =  monthAndDay[0]*30+monthAndDay[1];
								 }else{
								 	days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
								 }
					    		 
					    		 
					    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
					    		 String overDayRate = monthRate2DayRate(overRate);
					    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
					    		 if(StringUtil.equals("0.00", tempOverInterest)){
					    			 tempOverInterest = "0.00";
					    		 }else{
					    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
					    		 }
					    		 
					    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
					    		 
					    		 begDate = filterEffectDates.get(i).getEffectDate();
					    		 overRate = filterEffectDates.get(i).getOverRate();
			    			 }else{
			    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
			    				 int days =  0;
								 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
								 if(monthAndDay[1]>0 && monthOfDays==1){
									days  =  monthAndDay[0]*30+monthAndDay[1];
								 }else{
								 	days = DateUtil.getBetweenDays(begDate, endDate);
								 }
			    				 
			    				 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
					    		 String overDayRate = monthRate2DayRate(overRate);
					    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
					    		 if(StringUtil.equals("0.00", tempOverInterest)){
					    			 tempOverInterest = "0.00";
					    		 }else{
					    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
					    		 }
					    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
			    			 }
			    		 }
		    		 }else{// û�����ʵ���
		    			 int days =  0;
		    			 // �й�����
		    			 if(StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())){
		    				 begDate = acLnMstBean.getLastReturnDate();
		    			 }
						 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
						 if(monthAndDay[1]>0 && monthOfDays==1){
							days  =  monthAndDay[0]*30+monthAndDay[1];
						 }else{
						 	days = DateUtil.getBetweenDays(begDate, endDate);
						 }
	    				 
		    			 
			    		 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempOverInterest)){
			    			 tempOverInterest = "0.00";
			    		 }else{
			    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
			    		 }
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    		 }
			     }
		     }else{// ����ǿ�Ƽƻ�
		    	 // ����������Ϣ�Ŀ�ʼ����(��ݽ�������)
		    	 String begDate = acLnMstBean.getDueEndDate();
		    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
				 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
				 // ��þ����ݽ������������һ�����ʵ���
				 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, planBean.getEndDate());
				 if(latePartParmBean!=null){
					 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
					 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
						 overRate = latePartParmBean.getOverRate();
					 }
				 }
				 
				 // ��������ʵ���
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
				    		 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days =  0;
							 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
							 if(monthAndDay[1]>0 && monthOfDays==1){
								days  =  monthAndDay[0]*30+monthAndDay[1];
							 }else{
							 	days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
							 }
				    		 
				    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 
				    		 if(StringUtil.equals("0.00", tempOverInterest)){
				    			 tempOverInterest = "0.00";
				    		 }else{
				    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
				    		 }
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 overRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
		    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
				    		
		    				 int days =  0;
							 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
							 if(monthAndDay[1]>0 && monthOfDays==1){
								days  =  monthAndDay[0]*30+monthAndDay[1];
							 }else{
							 	days = DateUtil.getBetweenDays(begDate, endDate);
							 }
		    				 
		    				 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempOverInterest)){
				    			 tempOverInterest = "0.00";
				    		 }else{
				    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
				    		 }
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    			 }
		    		 }
	    		 }else{// û�����ʵ���
	    			 
	    			 int days =  0;
					 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
					 if(monthAndDay[1]>0 && monthOfDays==1){
						days  =  monthAndDay[0]*30+monthAndDay[1];
					 }else{
					 	days = DateUtil.getBetweenDays(begDate, endDate);
					 }
		    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(overRate);
		    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempOverInterest)){
		    			 tempOverInterest = "0.00";
		    		 }else{
		    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
		    		 }
		    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
	    		 }
		     }
		     return overInterest;
	}
	
/**
 * 
 * ���������� �ȶ��������Ϣ����
 * @param planBean
 * @param acLnMstBean
 * @return
 * String
 * @author Ǭ֮��
 * @date 2012-5-23 ����03:18:24
 */
private String  matchPrincipalOverInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
	
	int monthOfDays = SystemParm.MONTH_OF_DAYS;
	 String overInterest = "0.00";
	 // ���ʣ����
	 String dueBal  = acLnMstBean.getDueBal(); 
	 // ����������Ϣ�Ľ�������
	 // String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);	
	    String endDate = SystemParm.SystemDate;	
     // �����������
     String overRate = acLnMstBean.getOverRate();
 	 // ������ʷ�����
	 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
	 // �������ʱ�Ż�����ʵ����б�
	 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
	 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
	 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
     
    	//����������Ϣ�Ŀ�ʼ (���ڼƻ��Ľ�������)
		 String begDate = planBean.getEndDate(); 
    	 // �������ǿ�Ƽƻ�
		 if (SystemParm.IS_FORCE==0) {
    		 begDate = acLnMstBean.getDueEndDate();
		 }
		 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
		 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
		 // ��þ����ݽ������������һ�����ʵ���
		 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
		 if(latePartParmBean!=null){
			 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
			 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
				 overRate = latePartParmBean.getOverRate();
			 }
		 }
		 //��ǰ�������Ѿ��������ڵĽ�������
    	 if(DateUtil.gteq(begDate, endDate)){
    		   
    		 // ��������ʵ���
    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
    			 int size = filterEffectDates.size();
	    		 for(int i=0;i<size;++i){
	    			 if(i<size-1){
			    		 // String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
			    		 
			    		 int days =  0;
						 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
						 if(monthAndDay[1]>0 && monthOfDays==1){
							days  =  monthAndDay[0]*30+monthAndDay[1];
						 }else{
						 	days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
						 }
			    		 
			    		 
			    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
			    		 
			    		 
			    		 begDate = filterEffectDates.get(i).getEffectDate();
			    		 overRate = filterEffectDates.get(i).getOverRate();
	    			 }else{
	    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
			    		 
	    				 int days =  0;
						 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
						 if(monthAndDay[1]>0 && monthOfDays==1){
							days  =  monthAndDay[0]*30+monthAndDay[1];
						 }else{
						 	days = DateUtil.getBetweenDays(begDate, endDate);
						 }
	    				 
	    				 
	    				 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
	    			 }
	    		 }
    		 }else{// û�����ʵ���
    			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
    			 int days =  0;
				 int [] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
				 if(monthAndDay[1]>0 && monthOfDays==1){
					days  =  monthAndDay[0]*30+monthAndDay[1];
				 }else{
				 	days = DateUtil.getBetweenDays(begDate, endDate);
				 }
    			 
	    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
	    		 String overDayRate = monthRate2DayRate(overRate);
	    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
	    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
	    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
    		 }
	     }
    	 return overInterest;
	}

	/**
	 * 
	 * ���������� �ȶϢ������Ϣ���� 
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-23 ����03:20:43
	 */
	private String monthPrincipalOverInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		int isForce = SystemParm.IS_FORCE;
		String overInterest = "0.00";
		 // ���ʣ����
		 String dueBal  = acLnMstBean.getDueBal(); 
		 // �������ڵĻ���
		 String baseOver="0.00";
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 RatedayBean ratedayBean = new RatedayBean(); 
		 ratedayBean.setDueNo(planBean.getDueNo());
		 ratedayBean.setTermNo(planBean.getTermNo());
		 ratedayBean.setState("1");
		 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
		 
		 int termno = Integer.parseInt(planBean.getTermNo());
		 
		 for(int i=1;i<termno;++i){
			 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
			if (myRatedayBean!=null) {
				baseOver = BigNumberUtil.Add(baseOver,myRatedayBean.getInterest(),myRatedayBean.getOverInterest(),myRatedayBean.getCmpdInterest(),myRatedayBean.getAccFee());
			}
		 }
		 baseOver = BigNumberUtil.Add(baseOver,dueBal,planBean.getAccFee());
		 
		 
		 // ����������Ϣ�Ľ�������
		 String endDate = SystemParm.SystemDate;	
	     // �����������
	     String overRate = acLnMstBean.getOverRate();
	 	 // ������ʷ�����
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // �������ʱ�Ż�����ʵ����б�
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
//		 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
	// ����û�����ʵ���
		 List<PartParmBean> effectDates = null;
	     
	    	//����������Ϣ�Ŀ�ʼ (���ڼƻ��Ľ�������)
			 String begDate = planBean.getEndDate(); 
			 if (isForce==0) {
				 begDate =acLnMstBean.getDueEndDate();
			}
			 
	    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // ��þ����ݽ������������һ�����ʵ���
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
			 if(latePartParmBean!=null){
				 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 overRate = latePartParmBean.getOverRate();
				 }
			 }
			 //��ǰ�������Ѿ��������ڵĽ�������
	    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
	    		   
	    		 // ��������ʵ���
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
//				    		 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
		    				 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate()); 
				    		 if(monthOfDays==1){
				    			 days = monthAndDay[0]*30+monthAndDay[1];
				    		 }else{
				    			 days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 }
				    		 
				    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
				    		 
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 overRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
		    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
				    		 
		    				 int days = 0;
		    				 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate); 
				    		 if(monthOfDays==1){
				    			 days = monthAndDay[0]*30+monthAndDay[1];
				    		 }else{
				    			 days = DateUtil.getBetweenDays(begDate,endDate);
				    		 }
		    				 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "10000", 10, "1");
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    			 }
		    		 }
	    		 }else{// û�����ʵ���
	    			 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    			 int days = 0;
	    			 if (StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())) {
							begDate = acLnMstBean.getLastReturnDate();
						}
    				 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate); 
		    		 if(monthOfDays==1){
		    			 days = monthAndDay[0]*30+monthAndDay[1];
		    		 }else{
		    			 days = DateUtil.getBetweenDays(begDate,endDate);
		    		 }
		    		 String tempOverInterest  = BigNumberUtil.Multiply(baseOver,String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(overRate);
		    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
		    		 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 10, "1");
		    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    		 overInterest = BigNumberUtil.Divide(overInterest, "1", 2, "1");
		    		 
	    		 }
		     }
	    	 return overInterest;
	}
	
	
	/*************���ո���************/
	/**
	 * 
	 * ���������� ���汾�帴����Ϣ (Ƿ����*��������*��������)
	 * @param acLnMstBean
	 * interest ������Ϣ
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����07:27:49
	 */
	private String profitsClearCmpdInterest(AcLnMstBean acLnMstBean){
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		String cmpdInterest = "0.00";
		String cmpdRate = acLnMstBean.getCmpdRate();
		// ���Ƿ���
		String dueBal = acLnMstBean.getDueBal();
		//String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
		String endDate = SystemParm.SystemDate;
		String begDate = acLnMstBean.getDueEndDate();
		// ������ʷ�����
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		// �������ʱ�Ż�����ʵ����б�
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		// �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		// ������ڽ�ݽ������ں͵�ǰ����֮�����Ч��
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
		// ��þ����ݽ������������һ�����ʵ���
		PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, begDate);
		if(latePartParmBean!=null){
			// �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��
			if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
				cmpdRate = latePartParmBean.getCmpRate();
			}
		}
		
		if(filterEffectDates!=null && filterEffectDates.size()>0){
			int size = filterEffectDates.size();
			for (int i = 0; i <size; i++) {
				if(i<size-1){
//					String days = String.valueOf(DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate()));
					int days =0; 
					int[] monthAndDay = DateUtil.getMonthsAndDays(begDate, filterEffectDates.get(i).getEffectDate());
					
					if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate, filterEffectDates.get(i).getEffectDate());
					}
					// ����*����
					String tempCmpdInterest = BigNumberUtil.Multiply(dueBal,String.valueOf(days));
					// ����������ת��Ϊ������
					String dayOverRate = monthRate2DayRate(cmpdRate);
					// ����*����*����
					tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, dayOverRate);
					tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
					cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
					
					begDate = filterEffectDates.get(i).getEffectDate();
					cmpdRate = latePartParmBean.getOverRate();
				}else{
					//String days = String.valueOf(DateUtil.getBetweenDays(begDate, endDate));
					
					int days =0; 
					int[] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
					
					if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate, endDate);
					}
					
					// ����*����
					String tempCmpdInterest = BigNumberUtil.Multiply(dueBal,String.valueOf(days));
					// ����������ת��Ϊ������
					String dayOverRate = monthRate2DayRate(cmpdRate);
					// ����*����*����
					tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, dayOverRate);
					tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
					cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
				}
 			}
		}else{
			// String days = String.valueOf(DateUtil.getBetweenDays(begDate, endDate));
			int days =0; 
			int[] monthAndDay = DateUtil.getMonthsAndDays(begDate, endDate);
			
			if (monthOfDays==1) {
				days = monthAndDay[0]*30+monthAndDay[1];
			} else {
				days = DateUtil.getBetweenDays(begDate, endDate);
			}
			
			// ����*����
			String tempCmpdInterest = BigNumberUtil.Multiply(dueBal,String.valueOf(days));
			// ����������ת��Ϊ������
			String dayOverRate = monthRate2DayRate(cmpdRate);
			// ����*����*����
			tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, dayOverRate);
			tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
			cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		}
		
		return  cmpdInterest;
	}
	/**
	 * 
	 * ���������� ���ƻ���������Ϣ 
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-23 ����11:03:39
	 */
	private  String planCmpdInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
			int monthOfDays = SystemParm.MONTH_OF_DAYS;
			 String cmpdInterest = "0.00";
			 // ���ʣ����
			 String dueBal  = acLnMstBean.getDueBal(); 
			 // ���㸴���Ļ���
			 String baseCmpd = "0.00";
			 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
			 RatedayBean ratedayBean = new RatedayBean(); 
			 ratedayBean.setDueNo(planBean.getDueNo());
			 ratedayBean.setTermNo(planBean.getTermNo());
			 ratedayBean.setState("1");
			 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
			 
			 
			 int termno = Integer.parseInt(planBean.getTermNo());
			 
			 for(int i=1;i<termno;++i){
				 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
				if (myRatedayBean!=null) {
					baseCmpd = BigNumberUtil.Add(baseCmpd,myRatedayBean.getInterest());
				}
			 }
			 
			 
			 baseCmpd = BigNumberUtil.Add(baseCmpd,planBean.getReturnInterest());
			 
			 // ����������Ϣ�Ľ�������
			 String endDate = SystemParm.SystemDate;	
			 // �ж��ǲ���ǿ�Ƽƻ� 0 ����ǿ�Ƽƻ� 1 ��ǿ�Ƽƻ�
		     int isForce = SystemParm.IS_FORCE;
		     // �����������
		     String cmpdRate = acLnMstBean.getCmpdRate();
		 	 // ������ʷ�����
			 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
			 // �������ʱ�Ż�����ʵ����б�
			 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
			 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
			 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		     
		     // ��ǿ�Ƽƻ�
		     if(isForce==1){
		    	//����������Ϣ�Ŀ�ʼ (���ڼƻ��Ľ�������)
	    		 String begDate = planBean.getEndDate(); 
		    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
				 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
				 // ��þ����ݽ������������һ�����ʵ���
				 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
				 if(latePartParmBean!=null){
					 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
					 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
						 cmpdRate = latePartParmBean.getCmpRate();
					 }
				 }
				 //��ǰ�������Ѿ��������ڵĽ�������
		    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
		    		   
		    		 // ��������ʵ���
		    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
		    			 int size = filterEffectDates.size();
			    		 for(int i=0;i<size;++i){
			    			 if(i<size-1){
//					    		 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
					    		 int days = 0;
					    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
					    		 
					    		 if (monthOfDays==1) {
					    			 days = monthAndDay[0]*30+monthAndDay[1];
								} else {
									days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
								}
					    		 
					    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
					    		 String overDayRate = monthRate2DayRate(cmpdRate);
					    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
					    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
					    			 tempCmpdInterest = "0.00";
					    		 }else{
					    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
					    		 }
					    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
					    		 
					    		 
					    		 begDate = filterEffectDates.get(i).getEffectDate();
					    		 cmpdRate = filterEffectDates.get(i).getOverRate();
			    			 }else{
			    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
			    				 int days = 0;
					    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
					    		 
					    		 if (monthOfDays==1) {
					    			 days = monthAndDay[0]*30+monthAndDay[1];
								} else {
									days = DateUtil.getBetweenDays(begDate,endDate);
								}
					    		 
					    		 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd,String.valueOf(days)); 
					    		 String overDayRate = monthRate2DayRate(cmpdRate);
					    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
					    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
					    			 tempCmpdInterest = "0.00";
					    		 }else{
					    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
					    		 }
					    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
			    			 }
			    		 }
		    		 }else{// û�����ʵ���
		    			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    			 int days = 0;
		    			 if(StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())){
		    				begDate = acLnMstBean.getLastReturnDate(); 
		    			 }
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
			    		 
			    		 if (monthOfDays==1) {
			    			 days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,endDate);
						}
			    		 
		    			 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
			    			 tempCmpdInterest = "0.00";
			    		 }else{
			    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
			    		 }
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		    		 }
			     }
		     }else{// ����ǿ�Ƽƻ�
		    	 // ����������Ϣ�Ŀ�ʼ����(��ݽ�������)
		    	 String begDate = acLnMstBean.getDueEndDate();
		    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
				 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
				 // ��þ����ݽ������������һ�����ʵ���
				 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, planBean.getEndDate());
				 if(latePartParmBean!=null){
					 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
					 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
						 cmpdRate = latePartParmBean.getCmpRate();
					 }
				 }
				 
				 // ��������ʵ���
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
				    		 // String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 
				    		 if (monthOfDays==1) {
				    			 days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
							}
				    		 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
				    			 tempCmpdInterest = "0.00";
				    		 }else{
				    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
				    		 }
				    		 
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 cmpdRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
		    				//  String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    				 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
				    		 
				    		 if (monthOfDays==1) {
				    			 days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,endDate);
							}
		    				 
		    				 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
				    			 tempCmpdInterest = "0.00";
				    		 }else{
				    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
				    		 }
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		    			 }
		    		 }
	    		 }else{// û�����ʵ���
	    			 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    			 int days = 0;
		    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
		    		 
		    		 if (monthOfDays==1) {
		    			 days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate,endDate);
					}
	    			 
		    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(cmpdRate);
		    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
		    			 tempCmpdInterest = "0.00";
		    		 }else{
		    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
		    		 }
		    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    		 }
		     }
		     return cmpdInterest;
	}
	
/**
 * 
 * ���������� �ȶ��������Ϣ����
 * @param planBean
 * @param acLnMstBean
 * @return
 * String
 * @author Ǭ֮��
 * @date 2012-5-23 ����03:18:24
 */
private String  matchPrincipalCmpdInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
	int isForce = SystemParm.IS_FORCE;
	int monthOfDays = SystemParm.MONTH_OF_DAYS;
	String cmpdInterest = "0.00";
	 // ���ʣ����
	 String dueBal  = acLnMstBean.getDueBal(); 
	 // ����������Ϣ�Ľ�������
	 String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);	

     // �����������
     String cmpdRate = acLnMstBean.getCmpdRate();
 	 // ������ʷ�����
	 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
	 // �������ʱ�Ż�����ʵ����б�
	 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
	 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
	 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
     
    	//����������Ϣ�Ŀ�ʼ (���ڼƻ��Ľ�������)
		 String begDate = planBean.getEndDate(); 
    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
		 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
		 // ��þ����ݽ������������һ�����ʵ���
		 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
		 if(latePartParmBean!=null){
			 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
			 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
				 cmpdRate = latePartParmBean.getCmpRate();
			 }
		 }
		 //��ǰ�������Ѿ��������ڵĽ�������
    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
    		   
    		 // ��������ʵ���
    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
    			 int size = filterEffectDates.size();
	    		 for(int i=0;i<size;++i){
	    			 if(i<size-1){
			    		 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
			    		 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+ monthAndDay[1];
						} else {
							days =DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
						}
			    		 
			    		 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
			    		 
			    		 
			    		 begDate = filterEffectDates.get(i).getEffectDate();
			    		 cmpdRate = filterEffectDates.get(i).getOverRate();
	    			 }else{
	    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    				 
	    				 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+ monthAndDay[1];
						} else {
							days =DateUtil.getBetweenDays(begDate,endDate);
						}
	    				 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    			 }
	    		 }
    		 }else{// û�����ʵ���
    			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
    			 
    			 int days = 0;
	    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
	    		 if (monthOfDays==1) {
					days = monthAndDay[0]*30+ monthAndDay[1];
				} else {
					days =DateUtil.getBetweenDays(begDate,endDate);
				}
	    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
	    		 String overDayRate = monthRate2DayRate(cmpdRate);
	    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
	    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
	    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
    		 }
	     }
    	 return cmpdInterest;
	}

	/**
	 * 
	 * ���������� �ȶϢ������Ϣ���� 
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-23 ����03:20:43
	 */
	private String monthPrincipalCmpdInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		int isForce = SystemParm.IS_FORCE;
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		String cmpdInterest = "0.00";
		 // ���ʣ����
		 String dueBal  = acLnMstBean.getDueBal(); 
		 String baseCmpd = "0.00";
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 RatedayBean ratedayBean = new RatedayBean(); 
		 ratedayBean.setDueNo(planBean.getDueNo());
		 ratedayBean.setTermNo(planBean.getTermNo());
		 ratedayBean.setState("1");
		 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
		 
		 int termno = Integer.parseInt(planBean.getTermNo());
		 
		 for(int i=1;i<termno;++i){
			 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
			if (myRatedayBean!=null) {
				baseCmpd = BigNumberUtil.Add(baseCmpd,myRatedayBean.getInterest());
			}
		 }
		 baseCmpd = BigNumberUtil.Add(baseCmpd,planBean.getReturnInterest());
		 // ����������Ϣ�Ľ�������
		 String endDate = SystemParm.SystemDate;	
	     // �����������
	     String cmpdRate = acLnMstBean.getCmpdRate();
	 	 // ������ʷ�����
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // �������ʱ�Ż�����ʵ����б�
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
		// List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		 // ����û�����ʵ���
		 List<PartParmBean> effectDates = null;
	     
	    	//����������Ϣ�Ŀ�ʼ (���ڼƻ��Ľ�������)
			 String begDate = planBean.getEndDate(); 
			 if (isForce==0) {
				begDate = acLnMstBean.getDueEndDate();
			}
	    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // ��þ����ݽ������������һ�����ʵ���
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
			 if(latePartParmBean!=null){
				 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 cmpdRate = latePartParmBean.getCmpRate();
				 }
			 }
			 //��ǰ�������Ѿ��������ڵĽ�������
	    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
	    		   
	    		 // ��������ʵ���
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
//				    		 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days =DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
							}
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "10000", 10, "1");
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
				    		 
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 cmpdRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
//		    				 String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    				 
		    				 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days =DateUtil.getBetweenDays(begDate,endDate);
							}
		    				 
		    				 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 10, "1");
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		    			 }
		    		 }
	    		 }else{// û�����ʵ���
	    			 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    		
	    			 int days = 0;
	    			 if (StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())) {
							begDate = acLnMstBean.getLastReturnDate();
						}
		    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
		    		 if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days =DateUtil.getBetweenDays(begDate,endDate);
					}
	    			 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd,String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(cmpdRate);
		    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
		    			 tempCmpdInterest = "0.00";
		    		 }else{
		    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
		    		 }
		    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    		 }
		     }
	    	 return cmpdInterest;
	} 

	/**
	 * 
	 * ���������� һ�γ���������/����Ϣ
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-23 ����04:01:26
	 */
	private String oneMonthCmpdInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		int monthOfDays = SystemParm.MONTH_OF_DAYS; 
		String cmpdInterest = "0.00";
		 // ���ʣ����
		 String dueBal  = acLnMstBean.getDueBal(); 
		 // ���㸴���Ļ���
		 String baseCmpd = "0.00";
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 RatedayBean ratedayBean = new RatedayBean(); 
		 ratedayBean.setDueNo(planBean.getDueNo());
		 ratedayBean.setTermNo(planBean.getTermNo());
		 ratedayBean.setState("1");
		 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
		 
		 int termno = Integer.parseInt(planBean.getTermNo());
		 
		 for(int i=1;i<termno;++i){
			 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
			if (myRatedayBean!=null) {
				baseCmpd = BigNumberUtil.Add(baseCmpd,myRatedayBean.getInterest());
			}
		 }
		 baseCmpd = BigNumberUtil.Add(baseCmpd,planBean.getReturnInterest());
		 // ����������Ϣ�Ľ�������
		 String endDate = SystemParm.SystemDate;	
		 // �ж��ǲ���ǿ�Ƽƻ� 0 ����ǿ�Ƽƻ� 1 ��ǿ�Ƽƻ�
	     int isForce = SystemParm.IS_FORCE;
	     // �����������
	     String cmpdRate = acLnMstBean.getCmpdRate();
	 	 // ������ʷ�����
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // �������ʱ�Ż�����ʵ����б�
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
	//	 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		// ����û�����ʵ���
		 	 List<PartParmBean> effectDates = null;
	     
	     // ��ǿ�Ƽƻ�
	     if(isForce==1){
	    	//����������Ϣ�Ŀ�ʼ (���ڼƻ��Ľ�������)
	    	 String begDate = planBean.getEndDate(); 
	    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // ��þ����ݽ������������һ�����ʵ���
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
			 if(latePartParmBean!=null){
				 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 cmpdRate = latePartParmBean.getCmpRate();
				 }
			 }
			 //��ǰ�������Ѿ��������ڵĽ�������
	    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
	    		   
	    		 // ��������ʵ���
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
//				    		 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
							}
				    		 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
				    			 tempCmpdInterest ="0.00";
				    		 }else{
				    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
				    		 }
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
				    		 
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 cmpdRate = filterEffectDates.get(i).getCmpRate();
		    			 }else{
		    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    				 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
				    		 
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,endDate);
							}
		    				 
				    		 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(cmpdRate);
				    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
				    			 tempCmpdInterest ="0.00";
				    		 }else{
				    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
				    		 }
				    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
		    			 }
		    		 }
	    		 }else{// û�����ʵ���
//	    			 String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    			 
	    			 int days = 0;
	    			 if (StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())) {
							begDate = acLnMstBean.getLastReturnDate();
						}
		    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
		    		 
		    		 if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate,endDate);
					}
	    			 
		    		 String tempCmpdInterest  = BigNumberUtil.Multiply(baseCmpd, String.valueOf(days)); 
		    		 String overDayRate = monthRate2DayRate(cmpdRate);
		    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
		    			 tempCmpdInterest = "0.00";
		    		 }else{
		    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
		    		 }
		    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    		 }
		     }
	     }else{// ����ǿ�Ƽƻ�
	    	 // ����������Ϣ�Ŀ�ʼ����(��ݽ�������)
	    	 String begDate = acLnMstBean.getDueEndDate();
	    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // ��þ����ݽ������������һ�����ʵ���
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, planBean.getEndDate());
			 if(latePartParmBean!=null){
				 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 cmpdRate = latePartParmBean.getCmpRate();
				 }
			 }
			 
			 // ��������ʵ���
   		 if(filterEffectDates!=null && filterEffectDates.size()>0){
   			 int size = filterEffectDates.size();
	    		 for(int i=0;i<size;++i){
	    			 if(i<size-1){
			    		 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
			    		 
			    		 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
			    		 
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
						}
			    		 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 
			    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
			    			 tempCmpdInterest = "0.00";
			    		 }else{
			    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
			    		 }
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
			    		 
			    		 begDate = filterEffectDates.get(i).getEffectDate();
			    		 cmpdRate = filterEffectDates.get(i).getCmpRate();
	    			 }else{
	    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    				 
	    				 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
			    		 
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,endDate);
						}
	    				 
			    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(cmpdRate);
			    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
			    			 tempCmpdInterest = "0.00";
			    		 }else{
			    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
			    		 }
			    		 
			    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
	    			 }
	    		 }
   		 }else{// û�����ʵ���
   			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
   			 int days = 0;
    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
    		 
    		 if (monthOfDays==1) {
				days = monthAndDay[0]*30+monthAndDay[1];
			} else {
				days = DateUtil.getBetweenDays(begDate,endDate);
			}
			 
    		 String tempCmpdInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
   			 
	    		 String overDayRate = monthRate2DayRate(cmpdRate);
	    		 tempCmpdInterest = BigNumberUtil.Multiply(tempCmpdInterest, overDayRate);
	    		 if(StringUtil.equals("0.00", tempCmpdInterest)){
	    			 tempCmpdInterest = "0.00";
	    		 }else{
	    			 tempCmpdInterest = BigNumberUtil.Divide(tempCmpdInterest, "1000", 2, "1");
	    		 }
	    		 
	    		 cmpdInterest = BigNumberUtil.Add(tempCmpdInterest,cmpdInterest);
   		 }
	     }
	     return cmpdInterest;
	}
	
	
	/*************���ո���************/
	
	
	
	

	/**
	 * 
	 * ���������� һ�γ���������/����Ϣ
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-23 ����04:01:26
	 */
	private String oneMonthOverInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		int isForce = SystemParm.IS_FORCE;
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		String overInterest = "0.00";
		 // ���ʣ����
		 String dueBal  = acLnMstBean.getDueBal(); 
		 // �������ڵĻ���
		 String baseOver="0.00";
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 RatedayBean ratedayBean = new RatedayBean(); 
		 ratedayBean.setDueNo(planBean.getDueNo());
		 ratedayBean.setTermNo(planBean.getTermNo());
		 ratedayBean.setState("1");
		 List<RatedayBean> ratedayBeans = dayRateServices.getOverPlan(ratedayBean);
		 
		 
		 int termno = Integer.parseInt(planBean.getTermNo());
		 for(int i=1;i<termno;++i){
			 RatedayBean myRatedayBean=getLastRatedayBean(ratedayBeans, String.valueOf(i));
			if (myRatedayBean!=null) {
				baseOver = BigNumberUtil.Add(baseOver,myRatedayBean.getInterest(),myRatedayBean.getOverInterest(),myRatedayBean.getCmpdInterest(),myRatedayBean.getAccFee());
			}
		 }
		
		 baseOver = BigNumberUtil.Add(baseOver,dueBal,planBean.getAccFee());
		 
		 
		 // ����������Ϣ�Ľ�������
		 String endDate =SystemParm.SystemDate;	
		 // �ж��ǲ���ǿ�Ƽƻ� 0 ����ǿ�Ƽƻ� 1 ��ǿ�Ƽƻ�
	     // �����������
	     String overRate = acLnMstBean.getOverRate();
	 	 // ������ʷ�����
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // �������ʱ�Ż�����ʵ����б�
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
		 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
	     
	     // ��ǿ�Ƽƻ�
	     if(isForce==1){
	    	//����������Ϣ�Ŀ�ʼ (���ڼƻ��Ľ�������)
   		     String begDate = planBean.getEndDate(); 
	    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // ��þ����ݽ������������һ�����ʵ���
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates,begDate);
			 if(latePartParmBean!=null){
				 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 overRate = latePartParmBean.getOverRate();
				 }
			 }
			 //��ǰ�������Ѿ��������ڵĽ�������
	    	 if(DateUtil.gteq(planBean.getEndDate(), endDate)){
	    		   
	    		 // ��������ʵ���
	    		 if(filterEffectDates!=null && filterEffectDates.size()>0){
	    			 int size = filterEffectDates.size();
		    		 for(int i=0;i<size;++i){
		    			 if(i<size-1){
				    		 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
				    		 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
				    		 
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
							}
				    		 
				    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempOverInterest)){
				    			 tempOverInterest = "0.00";
				    		 }else{
				    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
				    		 }
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
				    		 
				    		 
				    		 begDate = filterEffectDates.get(i).getEffectDate();
				    		 overRate = filterEffectDates.get(i).getOverRate();
		    			 }else{
		    				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
		    				 int days = 0;
				    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
				    		 
				    		 if (monthOfDays==1) {
								days = monthAndDay[0]*30+monthAndDay[1];
							} else {
								days = DateUtil.getBetweenDays(begDate,endDate);
							}
		    				 
		    				 
		    				 
		    				 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
				    		 String overDayRate = monthRate2DayRate(overRate);
				    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
				    		 if(StringUtil.equals("0.00", tempOverInterest)){
				    			 tempOverInterest = "0.00";
				    		 }else{
				    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
				    		 }
				    		 
				    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
		    			 }
		    		 }
	    		 }else{// û�����ʵ���
	    			// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    			 int days = 0;
	    			 if (StringUtil.isNotEmpty(acLnMstBean.getLastReturnDate())) {
						begDate = acLnMstBean.getLastReturnDate();
					}
		    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
		    		 
		    		 if (monthOfDays==1) {
						days = monthAndDay[0]*30+monthAndDay[1];
					} else {
						days = DateUtil.getBetweenDays(begDate,endDate);
					}
    				 
    				 
    				 
    				 String tempOverInterest  = BigNumberUtil.Multiply(baseOver, String.valueOf(days)); 
	    			 
		    		// String tempOverInterest  = BigNumberUtil.Multiply(dueBal, days); 
		    		 String overDayRate = monthRate2DayRate(overRate);
		    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
		    		 if(StringUtil.equals("0.00", tempOverInterest)){
		    			 tempOverInterest = "0.00";
		    		 }else{
		    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
		    		 }
		    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
	    		 }
		     }
	     }else{// ����ǿ�Ƽƻ�
	    	 // ����������Ϣ�Ŀ�ʼ����(��ݽ�������)
	    	 String begDate = acLnMstBean.getDueEndDate();
	    	 // �������ڻ���ƻ���ʼ���ں͸��ڻ���ƻ���������֮�����Ч��
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, begDate, endDate);
			 // ��þ����ݽ������������һ�����ʵ���
			 PartParmBean latePartParmBean =  getLatelyEffectDate(effectDates, planBean.getEndDate());
			 if(latePartParmBean!=null){
				 // �ж����һ�ε�������Ч���Ƿ����ڽ�ݵĿ�ʼ���ںͽ�ݵĽ�������֮��,��������Ч��.
				 if(DateUtil.isBetween(latePartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate())){
					 overRate = latePartParmBean.getOverRate();
				 }
			 }
			 
			 // ��������ʵ���
   		 if(filterEffectDates!=null && filterEffectDates.size()>0){
   			 int size = filterEffectDates.size();
	    		 for(int i=0;i<size;++i){
	    			 if(i<size-1){
			    		// String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
			    		 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,filterEffectDates.get(i).getEffectDate());
			    		 
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate());
						}
			    		 
			    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
			    		 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempOverInterest)){
			    			 tempOverInterest = "0.00";
			    		 }else{
			    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
			    		 }
			    		 
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
			    		 
			    		 begDate = filterEffectDates.get(i).getEffectDate();
			    		 overRate = filterEffectDates.get(i).getOverRate();
	    			 }else{
	    				// String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	    				 int days = 0;
			    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
			    		 
			    		 if (monthOfDays==1) {
							days = monthAndDay[0]*30+monthAndDay[1];
						} else {
							days = DateUtil.getBetweenDays(begDate,endDate);
						}
	    				 
	    				 
			    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal,String.valueOf(days)); 
			    		 String overDayRate = monthRate2DayRate(overRate);
			    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
			    		 if(StringUtil.equals("0.00", tempOverInterest)){
			    			 tempOverInterest = "0.00";
			    		 }else{
			    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
			    		 }
			    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
	    			 }
	    		 }
   		 }else{// û�����ʵ���
   			 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
	   			 int days = 0;
	    		 int[] monthAndDay = DateUtil.getMonthsAndDays(begDate,endDate);
	    		 
	    		 if (monthOfDays==1) {
					days = monthAndDay[0]*30+monthAndDay[1];
				} else {
					days = DateUtil.getBetweenDays(begDate,endDate);
				}
	    		 String tempOverInterest  = BigNumberUtil.Multiply(dueBal, String.valueOf(days)); 
	    		 String overDayRate = monthRate2DayRate(overRate);
	    		 tempOverInterest = BigNumberUtil.Multiply(tempOverInterest, overDayRate);
	    		 if(StringUtil.equals("0.00", tempOverInterest)){
	    			 tempOverInterest = "0.00";
	    		 }else{
	    			 tempOverInterest = BigNumberUtil.Divide(tempOverInterest, "1000", 2, "1");
	    		 }
	    		 overInterest = BigNumberUtil.Add(tempOverInterest,overInterest);
   		 }
	     }
	     return overInterest;
	}
	
	
	
	
	/**
	 * 
	 * ���������� ���汾��������Ϣ���� 
	 * @param planBean
	 * @param acLnMstBean
	 * @param isFullTerm(�Ƿ����������Ϣ)
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����05:06:23
	 */
	private String  profitsClearInterest(PlanBean planBean, AcLnMstBean acLnMstBean,boolean isFullTerm) {
		String interest = "0.00";
		// ��ֹ����ǰ������
		String endDate = SystemParm.SystemDate;
		int tempDays = DateUtil.getBetweenDays(planBean.getBegDate(),endDate);
		if (tempDays < 1) {
			return interest;
		}

		// ִ������
		String executeRate = acLnMstBean.getLnRate();
		// �ж��Ƿ������ʵ��������Ч���䵽�������
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		// �������ʱ�Ż�����ʵ����б�
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		// �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,	acLnMstBean);
		// ��ݿ�ʼ����
		String begDate = acLnMstBean.getDueBegDate();
		
		/**������һ�λ������ڿ�ʼ**/
		IRepayDao repayDao = SpringFactory.getBean("repayDao");
		RepayBean repayBean = new RepayBean();
		repayBean.setDueNo(planBean.getDueNo());
		repayBean.setTermNo( planBean.getTermNo());
		List<RepayBean> repayHisBeans = repayDao.getRepayBeans(repayBean);
		if(repayHisBeans!=null && repayHisBeans.size()>0){
			Collections.sort(repayHisBeans);
			begDate = repayHisBeans.get(repayHisBeans.size()-1).getOccDate(); 
		}
		/**������һ�λ������ڽ���**/
		
		
		
		// �Ƿ���㵱����Ϣ
		if(isFullTerm){
			endDate = planBean.getEndDate();
		}
		
		// ��þ�����ڻ���ƻ���ʼ���������һ�ε���
		 PartParmBean  LatelyPartParmBean =  getLatelyEffectDate(effectDates, begDate);
		if (LatelyPartParmBean!=null) {
			executeRate = LatelyPartParmBean.getExecuteRate();
		}
		
		// ������Ч���ȡ���ڿ�ʼ���ںͽ�������֮�����Ч��
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates,begDate, endDate);
		if (filterEffectDates != null && filterEffectDates.size() > 0) {
			int size = filterEffectDates.size();
			// �ֶ���Ϣ�����һ��
			for (int i = 0; i < size; ++i) {
				if (i < size - 1) {
					// ��ý�ݿ�ʼ���ں͵�һ����Ч��֮�������
					PlanBean tempPlanBean = new PlanBean();
					tempPlanBean.setBegDate(begDate);
					tempPlanBean.setEndDate(filterEffectDates.get(i).getEffectDate());
					acLnMstBean.setLnRate(executeRate);
					String tempInterest = getInterest(planBean,acLnMstBean);
					interest = BigNumberUtil.Add(interest,tempInterest);
					
					// ��������ִ������,��ʼ���ں͵�һ�α�־
					executeRate = filterEffectDates.get(i).getExecuteRate();
					begDate = filterEffectDates.get(i).getEffectDate();
					tempPlanBean = null;
				} else {
					// ��ý�ݿ�ʼ���ں͵�һ����Ч��֮�������
					PlanBean tempPlanBean = new PlanBean();
					tempPlanBean.setBegDate(begDate);
					tempPlanBean.setEndDate(endDate);
					acLnMstBean.setLnRate(executeRate);
					String tempInterest = getInterest(planBean,acLnMstBean);
					interest = BigNumberUtil.Add(interest, tempInterest);
				}
			}
		} else {
			// û�����ʵ��������(���ڶ��ǰ������ʼ���)
			
			PlanBean tempPlanBean = new PlanBean();
			tempPlanBean.setBegDate(begDate);
			tempPlanBean.setEndDate(endDate);
			String tempInterest = getInterest(planBean,acLnMstBean);
			interest = BigNumberUtil.Add(interest, tempInterest);
		}
		return interest;	
	}
	
	
	
	
	
	
	/**
	 * 
	 * ���������� ���ƻ�������Ϣ����
	 * @param planBean
	 * @param acLnMstBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����11:12:15
	 */
	private String planInterest(PlanBean planBean, AcLnMstBean acLnMstBean,boolean isFullTerm){
			 String interest = "0.00";
			 // ������Ϣ�Ŀ�ʼ����
			 String begDate = planBean.getBegDate();
			
			
			 /**������һ�λ������ڿ�ʼ**/
				IRepayDao repayDao = SpringFactory.getBean("repayDao");
				RepayBean repayBean = new RepayBean();
				repayBean.setDueNo(planBean.getDueNo());
				repayBean.setTermNo( planBean.getTermNo());
				List<RepayBean> repayHisBeans = repayDao.getRepayBeans(repayBean);
				if(repayHisBeans!=null && repayHisBeans.size()>0){
					Collections.sort(repayHisBeans);
					begDate = repayHisBeans.get(repayHisBeans.size()-1).getOccDate(); 
				}
			 /**������һ�λ������ڽ���**/
			 
			 // ������Ϣ�Ľ������� 
			 String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
			 // �Ƿ����������Ϣ
			 if(isFullTerm){
					endDate = planBean.getEndDate();
			 }
			 // ִ������ 
			 String executeRate = acLnMstBean.getLnRate();
			 
			 // ������ʷ�����
			 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
			 // �������ʱ�Ż�����ʵ����б�
			 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
			 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
			 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
			 //���ݻ���ƻ��Ŀ�ʼ���ڽ������ڹ�����Ч��,ֻȡ���ڼƻ���ʼ���ںͽ�ֹ����ǰ����֮�����Ч��
			 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, planBean.getBegDate(), endDate);
			
			 /**��������Ϣ�Ŀ�ʼ���ڸ��µ����һ�λ������ڿ�ʼ**/
			 PartParmBean  LatelyPartParmBean =  getLatelyEffectDate(effectDates, begDate);
			 if(LatelyPartParmBean!=null){
				 executeRate = LatelyPartParmBean.getExecuteRate();
			 }
			 /**��������Ϣ�Ŀ�ʼ���ڸ��µ����һ�λ������ڽ���**/
			 
			 
			 
			 /**�����ʵ����ֶμ��㿪ʼ**/
			 if(filterEffectDates!=null && filterEffectDates.size()>0){
				 int size = filterEffectDates.size();
				 for(int i=0;i<size;++i){
					 if(i<size-1){
						PlanBean tempPlanBean = planBean.clone();
						tempPlanBean.setBegDate(begDate);
						tempPlanBean.setEndDate(filterEffectDates.get(i).getEffectDate());
						acLnMstBean.setLnRate(executeRate);
						
						String tempInterest  = getInterest(tempPlanBean, acLnMstBean);
						interest = BigNumberUtil.Add(tempInterest,interest);
						 
//						 // ���2����Ч��֮����������
//						 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
//						 // �����������
//						 String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
//						 // ������ת��Ϊ������
//						 String dayRate = monthRate2DayRate(executeRate);
//						 // ���������������������
//						 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
//						 tempInterest = BigNumberUtil.Divide(tempInterest, "10000", 10, "1");
//						 interest = BigNumberUtil.Add(tempInterest,interest);
						 //	������������
						 begDate = filterEffectDates.get(i).getEffectDate();
						 executeRate = filterEffectDates.get(i).getExecuteRate();
						 tempPlanBean = null;
					 }else{
						 // �����������һ�ε���Ϣ
						 // ��ü��������
						 PlanBean tempPlanBean = planBean.clone();
						 tempPlanBean.setBegDate(begDate);
						 tempPlanBean.setEndDate(endDate);
						 acLnMstBean.setLnRate(executeRate);
						 
						 String tempInterest  = getInterest(tempPlanBean, acLnMstBean);
						 interest = BigNumberUtil.Add(tempInterest,interest);
						 
//						 String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
//						// �����������
//						 String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
//						 // ������ת��Ϊ������
//						 String dayRate = monthRate2DayRate(executeRate);
//						 // ���������������������
//						 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
//						 tempInterest = BigNumberUtil.Divide(tempInterest, "10000", 10, "1");
//						 interest = BigNumberUtil.Add(tempInterest,interest);
						 tempPlanBean = null;
					 }
				 }
			 /**�����ʵ����ֶμ������**/
			 }else{
				 // û�����ʵ���
				 
				 String tempInterest  = getInterest(planBean, acLnMstBean);
				 interest = BigNumberUtil.Add(tempInterest,interest);
				 
				 // ��ü��������
				 //String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
				// �����������
				// String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
				 // ������ת��Ϊ������
				// String dayRate = monthRate2DayRate(executeRate);
				 // ���������������������
				 //tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
				// tempInterest = BigNumberUtil.Divide(tempInterest, "10000", 10, "1");
				// interest = BigNumberUtil.Add(tempInterest,interest);
			 }
			 return interest;
	}
	
	/**
	 * 
	 * ���������� �ȶ��������Ϣ����.(�ӻ���ƻ��Ŀ�ʼ�����ڿ�ʼ�������û����Ϣ����ÿ�ڵ���Ϣ����һ����ֵ(���ڻ���ƻ�����Ϣ)
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����01:26:49
	 */
	private String matchPrincipalInterest(PlanBean planBean ,AcLnMstBean acLnMstBean,boolean isFullTerm){
		String begDate = planBean.getBegDate();
		
		String executeRate = acLnMstBean.getLnRate();
		// ��ø��ڻ���ƻ���Ӧ����Ϣ
		String shoudInterest = planBean.getReturnInterest();
		// ������Ϣ�Ľ������� 
		String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
		 // ������ʷ�����
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // �������ʱ�Ż�����ʵ����б�
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
		 /**��������Ϣ�Ŀ�ʼ���ڸ��µ����һ�λ������ڿ�ʼ**/
		 PartParmBean  latelyPartParmBean =  getLatelyEffectDate(effectDates, planBean.getBegDate());
		 if (latelyPartParmBean!=null) {
			 	acLnMstBean.setLnRate(latelyPartParmBean.getExecuteRate());
		 }
		 //���ݻ���ƻ��Ŀ�ʼ���ڽ������ڹ�����Ч��,ֻȡ���ڼƻ���ʼ���ںͽ�ֹ����ǰ����֮�����Ч��
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, planBean.getBegDate(), endDate);
		// ���û����Ч�����ڵ���ֱ�ӷ��ػ���ƻ��е�Ӧ����Ϣ
		if(filterEffectDates==null || filterEffectDates.size()==0){
			return shoudInterest;
		}else{
		// �������Ч�����ڵ��ڵĻ���ƻ�����Ҫ���¼�����Ϣ(���ڻ�û��ʵ��)
			 /**�����ʵ����ֶμ��㿪ʼ**/
				 String interest = "0.00";
				 int size = filterEffectDates.size();
				 for(int i=0;i<size;++i){
					 if(i<size-1){
						PlanBean tempPlanBean = planBean.clone();
						tempPlanBean.setBegDate(begDate);
						tempPlanBean.setEndDate(filterEffectDates.get(i).getEffectDate());
						acLnMstBean.setLnRate(executeRate);
						String tempInterest  = getInterest(tempPlanBean, acLnMstBean);
						interest = BigNumberUtil.Add(tempInterest,interest);
						begDate = filterEffectDates.get(i).getEffectDate();
						executeRate = filterEffectDates.get(i).getExecuteRate();
						tempPlanBean = null;
					 }else{
						 // �����������һ�ε���Ϣ
						 // ��ü��������
						 PlanBean tempPlanBean = planBean.clone();
						 tempPlanBean.setBegDate(begDate);
						 tempPlanBean.setEndDate(endDate);
						 acLnMstBean.setLnRate(executeRate);
						 
						 String tempInterest  = getInterest(tempPlanBean, acLnMstBean);
						 interest = BigNumberUtil.Add(tempInterest,interest);

						 tempPlanBean = null;
					 }
				 }
			 /**�����ʵ����ֶμ������**/
				 shoudInterest  = interest;
		}
		return shoudInterest;
	} 
	
	/**
	 * 
	 * ���������� �ȶϢ������Ϣ����.�ӻ���ƻ��Ŀ�ʼ�����ڿ�ʼ�������û����Ϣ����ÿ�ڵ���Ϣ����һ����ֵ(���ڻ���ƻ�����Ϣ)
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����01:41:40
	 */
	private String monthPrincipalInterest(PlanBean planBean ,AcLnMstBean acLnMstBean,boolean isFullTerm){
		String begDate = planBean.getBegDate();
		String executeRate = acLnMstBean.getLnRate();
		// ��ø��ڻ���ƻ���Ӧ����Ϣ
		String shoudInterest = planBean.getReturnInterest();
		// ������Ϣ�Ľ������� 
		String endDate = DateUtil.addByDay(SystemParm.SystemDate, 1, DateUtil.DATE_FORMAT_);
		 // ������ʷ�����
		NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // �������ʱ�Ż�����ʵ����б�
		List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
		List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
		 //���ݻ���ƻ��Ŀ�ʼ���ڽ������ڹ�����Ч��,ֻȡ���ڼƻ���ʼ���ںͽ�ֹ����ǰ����֮�����Ч��
		List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, planBean.getBegDate(), endDate);
		// ���û����Ч�����ڵ���ֱ�ӷ��ػ���ƻ��е�Ӧ����Ϣ
		
		PartParmBean  latelyPartParmBean =  getLatelyEffectDate(effectDates, planBean.getBegDate());
		 if (latelyPartParmBean!=null) {
			 executeRate = latelyPartParmBean.getExecuteRate();
		 }
		if(filterEffectDates==null || filterEffectDates.size()==0){
			return shoudInterest;
		}else{
		// �������Ч�����ڵ��ڵĻ���ƻ�����Ҫ���¼�����Ϣ(���ڻ�û��ʵ��)
			 String interest = "0.00";
			 int size = filterEffectDates.size();
			 String tempEndDate = "";
			 for(int i=0;i<size;++i){
				 if(i<size-1){
					tempEndDate =   filterEffectDates.get(i).getEffectDate();
					int days = DateUtil.getBetweenDays(begDate, tempEndDate);
					// �����������
					String tempInterest = BigNumberUtil.Multiply(planBean.getDueBal(), String.valueOf(days));
					String dayRate = monthRate2DayRate(executeRate);
					// ����������޳���������
					tempInterest = BigNumberUtil.Multiply(tempInterest,dayRate);
					tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
					interest = BigNumberUtil.Add(interest,tempInterest);
					
					begDate = tempEndDate;
					executeRate =  filterEffectDates.get(i).getExecuteRate();
					
				 }else{
					 // �����������һ�ε���Ϣ
					 // ��ü��������
					 int days = DateUtil.getBetweenDays(begDate, planBean.getEndDate());
					// �����������
					String tempInterest = BigNumberUtil.Multiply(planBean.getDueBal(), String.valueOf(days));
					String dayRate = monthRate2DayRate(filterEffectDates.get(i).getExecuteRate());
					// ����������޳���������
					tempInterest = BigNumberUtil.Multiply(tempInterest,dayRate);
					tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
					interest = BigNumberUtil.Add(interest,tempInterest);
				 }
			 }
			 shoudInterest = interest;
			return shoudInterest;
		}
	}

	
	/**
	 * 
	 * ����������һ�γ��������½�Ϣ��������Ϣ����  
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����01:44:18
	 */
	private String oneMonthInterest(PlanBean planBean ,AcLnMstBean acLnMstBean,boolean isFullTerm){
		 String interest = "0.00";
		 // ��ý��ʣ�౾��
		 String remaCapital = acLnMstBean.getDueBal();
		 // ������Ϣ�Ŀ�ʼ����
		 String begDate = planBean.getBegDate();
		 // ������Ϣ�Ľ�������
		 String endDate = planBean.getEndDate();//DateUtil.addByDay(SystemParm.SystemDate,1, DateUtil.DATE_FORMAT_);
		 if(isFullTerm){
			 endDate = planBean.getEndDate();
		 }
		 
		 // ���ִ������ 
		 String executeRate = acLnMstBean.getLnRate(); 
		 // ������ʷ�����
		 NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
		 // �������ʱ�Ż�����ʵ����б�
		 List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
		 // �������ʵ����б�ͼ�Ϣ��ʽ�����Ч���б�
		 List<PartParmBean> effectDates = getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
		 // ���ݻ���ƻ��Ŀ�ʼ���ڽ������ڹ�����Ч��,ֻȡ���ڼƻ���ʼ���ںͽ�ֹ����ǰ����֮�����Ч��
		 List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, planBean.getBegDate(), endDate);
		 // ��þ�����ڻ���ƻ���ʼ���������һ�����ʵ���
		 PartParmBean latelyPartParmBean = getLatelyEffectDate(effectDates, begDate);
		 
		 if(latelyPartParmBean!=null){
			// �жϾ�����ڻ���ƻ���ʼ���������һ�����ʵ����Ƿ����ڽ�ݿ�ʼ���ں͸��ڻ���ƻ���ʼ��Χ��
			 boolean isBetween = DateUtil.isBetween(latelyPartParmBean.getEffectDate(), acLnMstBean.getDueBegDate(),begDate);
			 if(isBetween){
				 executeRate = latelyPartParmBean.getEffectDate(); 
			 }
		 }
		 // �����ʵ���
		 if(filterEffectDates!=null && filterEffectDates.size()>0){
			 int size = filterEffectDates.size();
			 for(int i=0;i<size;++i){
				 if(i<size-1){
					 // ���2����Ч��֮����������
					 String days = String.valueOf(DateUtil.getBetweenDays(begDate,filterEffectDates.get(i).getEffectDate()));
					 // �����������
					 String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
					 // ������ת��Ϊ������
					 String dayRate = monthRate2DayRate(executeRate);
					 // ���������������������
					 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
					 if(StringUtil.equals("0.00", tempInterest)){
						 tempInterest = "0.00";
					 }else{
						 tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
					 }
					 
					 interest = BigNumberUtil.Add(tempInterest,interest);
					 //	������������
					 begDate = filterEffectDates.get(i).getEffectDate();
					 executeRate = filterEffectDates.get(i).getExecuteRate();
				 }else{
					 // ���2����Ч��֮����������
					 String days = String.valueOf(DateUtil.getBetweenDays(begDate,endDate));
					 // �����������
					 String tempInterest = BigNumberUtil.Multiply(remaCapital, days);
					 // ������ת��Ϊ������
					 String dayRate = monthRate2DayRate(executeRate);
					 // ���������������������
					 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
					 if(StringUtil.equals("0.00", tempInterest)){
						 tempInterest = "0.00";
					 }else{
						 tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
					 }
					 interest = BigNumberUtil.Add(tempInterest,interest);
				 }
			 }
		 }else{
			 
			 int[] monthAndDays = DateUtil.getMonthsAndDays(begDate,endDate);
			 String tempMonthInterest = BigNumberUtil.Multiply(remaCapital, String.valueOf(monthAndDays[0]));
			 if(StringUtil.equals("0.00", tempMonthInterest)  || StringUtil.equals("0.0", tempMonthInterest)){
				 
			 }else{
				 tempMonthInterest = BigNumberUtil.Multiply(tempMonthInterest, executeRate);
				 tempMonthInterest = BigNumberUtil.Divide(tempMonthInterest, "1000", 2, "1");
			 }
			 
			 
			 
			 // �����������
			 String tempInterest = BigNumberUtil.Multiply(remaCapital, String.valueOf(monthAndDays[1]));
			 // ������ת��Ϊ������
			 String dayRate = monthRate2DayRate(executeRate);
			 // ���������������������
			 tempInterest = BigNumberUtil.Multiply(tempInterest, dayRate);
			 if(StringUtil.equals("0.00", tempInterest)||StringUtil.equals("0.0", tempInterest)){
				 
			 }else{
				 tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 2, "1");
				 
			 }
			 interest = BigNumberUtil.Add(tempInterest,interest,tempMonthInterest);
		 }
		 return interest;
	}
	
	
	/****************************����������Ϣ����****************************/
	
	
	
	
	
	
	
	/**
	 * 
	 * ������������Ϣ���� ���ɻ���ƻ�ʱʹ��
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-18 ����05:30:02
	 */
	public String  getInterest(PlanBean planBean,AcLnMstBean acLnMstBean){
		   	String interest = "0.0";
			// ������� �����б�
			NormRateServices normRateServices = SpringFactory.getBean("normRateServices");
			List<RateAdjustBean> adjustBeans = normRateServices.getRateAdjustList(acLnMstBean.getRateNo());
			//�������ʵ����б�����Ч���б�
			List<PartParmBean> effectDates =  getEffectDateByAdjustDate(adjustBeans,acLnMstBean);
			// ������Ч���ȡ���ڿ�ʼ���ںͽ�������֮�����Ч��
			List<PartParmBean> filterEffectDates = filterEffectDate(effectDates, acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
			/** ����������Ϣ**/
		
			
			// ���汾��
			if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR, acLnMstBean.getReturnMethod())){
					interest = getRateCome_1(planBean,acLnMstBean);
			}
			
			// ���ƻ�
			if(StringUtil.equals(SystemParm.RETURNMETHOD_PLAN, acLnMstBean.getReturnMethod())){
				interest = getRateCome_2(planBean,acLnMstBean);
			}
			// һ�γ��������½�Ϣ
			if(StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, acLnMstBean.getReturnMethod())){
				interest = getRateCome_3(planBean,acLnMstBean);
			}
			
			// һ�γ������𰴼���Ϣ
			if(StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, acLnMstBean.getReturnMethod())){
				interest = getRateCome_3(planBean,acLnMstBean);
			}
			
			// �ȶ����Ϣ
			if(StringUtil.equals(SystemParm.RETURNMETHOD_PRINCIPAL, acLnMstBean.getReturnMethod())){
				interest = getRateCome_4(planBean,acLnMstBean);
			}
			
			return  interest;
	}
	
	
	/**
	 * 
	 * ����������  �ȶϢÿ�ڵı�Ϣ��
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-25 ����07:29:49
	 */
	public String getInterest(PlanBean planBean,int terms,String remaMoney,AcLnMstBean acLnMstBean){
		String money = "0.00";
		// ��Ϣ����ʱ�õ����� 0 ������ 1 ������  2������������ ,���㲿����������
		//int useRateType = SystemParm.USE_RATE_TYPE;
		int useRateType = 1;
		// ���������ʼ���ʱÿ�µ����� 0 ʵ������ 1 ���°�30��
		int monthOfDays = SystemParm.MONTH_OF_DAYS;
		if(useRateType==1){
			// ִ������(������)
			String lnRate = acLnMstBean.getLnRate();
			// �������������
			String totalMoney = BigNumberUtil.Multiply(acLnMstBean.getDueBal(), lnRate);
			totalMoney = BigNumberUtil.Divide(totalMoney, "1000", 10, "1");
			// �����ʼ�1
			String monthRate = BigNumberUtil.Divide(lnRate, "1000", 10, "1");
			String monthRate1 = BigNumberUtil.Add(monthRate,"1");
			// (1��������)�������η�
			String monthRate2 = String.valueOf(Math.pow(Double.valueOf(monthRate1), Double.valueOf(terms)));
			// ������������ʳ��������ʵ������η�
			String tempMoney = BigNumberUtil.Multiply(totalMoney, monthRate2);
			// (1��������)���������η�
	        String monthRate3 = BigNumberUtil.Subtract(monthRate2, "1");
	        // û��Ӧ���������Ϣ�ĺ�
	        money = BigNumberUtil.Divide(tempMoney, monthRate3, 2, "1");
		}
        return money;
	}
	
	/**
	 * 
	 * ����������   �ȶϢÿ�ڵ���Ϣ
	 * @param planBean
	 * @param remaMoney
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-26 ����08:59:33
	 */
	public String getInterestByMonth(PlanBean planBean,String remaMoney,AcLnMstBean acLnMstBean){
		String interest = "0.00";
		String lnRate = acLnMstBean.getLnRate();
		interest = BigNumberUtil.Multiply(remaMoney, lnRate);
		interest = BigNumberUtil.Divide(interest, "1000", 2, "1");
		return interest;
	}
	
	/**
	 * 
	 * ����������   �ȶϢÿ�ڵ���Ϣ
	 * @param planBean
	 * @param remaMoney
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-26 ����08:59:33
	 */
	public String getInterestByDay(PlanBean planBean,String remaMoney,AcLnMstBean acLnMstBean){
		String interest = "0.00";
		int [] monthAndDay = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
		String lnRate = acLnMstBean.getLnRate();
		String dayRate = monthRate2DayRate(lnRate);
		interest = BigNumberUtil.Multiply(remaMoney,dayRate);
		interest = BigNumberUtil.Multiply(interest,String.valueOf(monthAndDay[1]));
		interest = BigNumberUtil.Divide(interest, "10000", 2, "1");
		return interest;
	}
	
	
	
	
	
	
	/**
	 * 
	 * �����������ſ�֮ǰ�����ʵ����ֶν�����Ϣ����(����ֻҪ��������������ʽ��м���,���ܲ��Ǻܺ�����Ҫ����) 
	 * @param principal
	 * @param beginDate(����ƻ���ʼ����)
	 * @param endDate(����ƻ���������)
	 * @param partParmBeans
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-21 ����03:25:41
	 */
	private String getRateComeByAdjust(String principal,String beginDate,String endDate,String lnRate ,List<PartParmBean> partParmBeans){
			String interest = "0.0";
			
			
			// ��һ��ʹ��������,�ڶ���ʹ�õ�һ�ε����������,������ʹ�õڶ��ε����������һ�����Ƶ�n��ʹ�õ�n-1�ε��������Ϣ
			// �ж��ǲ��ǵ�һ��
			boolean isFirstPart  = true;
			String executeRate = lnRate;
			String begDate = beginDate;
			for(PartParmBean  partParmBean:partParmBeans){
			    if(isFirstPart){
			    	String days = String.valueOf(DateUtil.getBetweenDays(begDate, partParmBean.getEffectDate()));
			    	interest = BigNumberUtil.Multiply(principal, days);
			    	String dayRate =  monthRate2DayRate(executeRate);
			    	interest = BigNumberUtil.Multiply(interest, dayRate);
			    	interest = BigNumberUtil.Divide(interest, "1000", 10, "1");
			    	isFirstPart = false;
			    	executeRate = partParmBean.getExecuteRate();
			    	begDate = partParmBean.getEffectDate();
			    }else{
			    	String days = String.valueOf(DateUtil.getBetweenDays(begDate,partParmBean.getEffectDate()));
			    	String tempInterest =  BigNumberUtil.Multiply(principal, days);
			    	String dayRate =  monthRate2DayRate(executeRate);
			    	tempInterest = BigNumberUtil.Multiply(tempInterest,dayRate);
			    	tempInterest = BigNumberUtil.Divide(tempInterest, "1000", 10, "1");
			    	interest = BigNumberUtil.Add(interest,tempInterest);
			    	executeRate = partParmBean.getExecuteRate(); 
			    	begDate = partParmBean.getEffectDate();
			    }	
			}	
			return interest;
	}
	
	

	
	
	/**
	 * 
	 * ���������� ���汾����Ϣ  
	 * @param acLnMstBean  �������ļ�
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-21 ����01:54:03
	 */
	private String  getRateCome_1(PlanBean planBean,AcLnMstBean acLnMstBean){
			String interest = "0.00";
			// ������Ϣʱʹ�������ʻ��������� 0 ������ 1 ������
		    int useRateType = SystemParm.USE_RATE_TYPE;
		    //  ��ѡ������,���Ҳ������µĴ���, 0  ���²���ʹ�������ʼ���,�����²���ʹ�������ʼ��� 1 ʹ�������ʼ���
		    int rateNotFull = SystemParm.RATE_NOT_FULL;
		    // ��ú�ͬ��������������
		    int monthAndDays[] = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
		    int limitMonth = monthAndDays[0];
		    int limitDays = monthAndDays[1];
		    // ��ݱ���
		    String principal = acLnMstBean.getDueBal(); 
		    // ��ͬ����
		    String executeRate = acLnMstBean.getLnRate();
		    
		    // ������
		    if(useRateType==1){
		    	// ���²���ʹ�������ʲ����²���ʹ��������
		    	if(rateNotFull==0){
		    		// �������������
					String  interestMonth = BigNumberUtil.Multiply(principal, String.valueOf(limitMonth));
					// �������������
					String  interestDay = BigNumberUtil.Multiply(principal, String.valueOf(limitDays));
					// ������������³���������
					interestMonth = BigNumberUtil.Multiply(interestMonth, executeRate);
					interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10,"1");
					// ���������ʻ��������
					String dayRate = monthRate2DayRate(executeRate);
					// ����������޳���������
					interestDay = BigNumberUtil.Multiply(interestDay, dayRate);
					interestDay = BigNumberUtil.Divide(interestDay, "10000", 10,"1");
					interest = BigNumberUtil.Add(interestMonth,interestDay);
					interest = BigNumberUtil.Divide(interest, "1", 2,"1");
		    	}
		    	
		    	if(rateNotFull==1){
		    		// ��������ת��Ϊ������
		    		String dayRate = monthRate2DayRate(executeRate);
		    		String days = "0";
		    		// ���²���Ϊʵ������
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
		    			days = StringUtil.KillNull(days,"0");
		    		}
		    		// ���²��ְ�30�����
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days= String.valueOf(limitMonth*30+limitDays);
		    		}
		    		// ������������ʳ���������
		    		String interestDay = BigNumberUtil.Multiply(principal, days);
		    		interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		interestDay = BigNumberUtil.Divide(interestDay, "10000", 2, "1");
		    		interest = interestDay;
		    	}
		    }
		    
		    // ʹ�������ʼ���
		    if(useRateType==0){
		    	//����������
		    	String days = "0";
		    	// ����ʵ��������������
		    	if(SystemParm.MONTH_OF_DAYS==0){
		    		days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
		    		days = StringUtil.KillNull(days,"0");
		    	}
		    	// һ�°���30 �����
		    	if(SystemParm.MONTH_OF_DAYS==1){
		    		days= String.valueOf(limitMonth*30+limitDays);
		    	}
		    	// ��������ת��Ϊ������
		    	String dayRate = monthRate2DayRate(executeRate);
		    	// �������������
		    	String  interestDay = BigNumberUtil.Multiply(principal, dayRate) ;
		    	interestDay = BigNumberUtil.Multiply(interestDay, days) ;
		    	
		    	interest = BigNumberUtil.Divide(interestDay, "10000", 2,"1");
		    }
		    
			return  interest; 
	}
	
	
	/**
	 * 
	 * ���������� ���ƻ���Ϣ
	 * 1 ʹ�������ʽ��м���,����ֱ��ʹ��������.�������µ����²���ʹ��������,������ʹ��������.����ʹ��������.
	 * 2 ʹ�������ʽ��м���
	 * 3 ʹ��������ʱ��Ҫת�����µ�����,��ʵ���������ǰ���30��������
	 * @param planBean
	 * @param acLnMstBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-4 ����10:19:05
	 */
	private String getRateCome_2(PlanBean planBean,AcLnMstBean acLnMstBean){
		// ��Ϣ
		String interest = "0.00";
		// ������Ϣʱʹ�������ʻ��������� 0 ������ 1 ������
	    int useRateType = SystemParm.USE_RATE_TYPE;
	    //  ��ѡ������,���Ҳ������µĴ���, 0  ���²���ʹ�������ʼ���,�����²���ʹ�������ʼ��� 1 ʹ�������ʼ���
	    int rateNotFull = SystemParm.RATE_NOT_FULL;
	    // ���� (���ɻ���ƻ�ʱʹ��)
	    String principal = planBean.getDueBal();
	    // ��ͬ����
	    String exxcuteRate = acLnMstBean.getLnRate();
	    // ����ƻ�������
	    int []monthAndDay = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
	   
	    if(useRateType==1){
	    	// �ж��ǲ�������,monthAndDay[1]==0 ˵��������
	    	if(monthAndDay[1]==0){
	    		// �������������
	    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
	    		// ������������ʳ�������
	    		interestMonth = BigNumberUtil.Multiply(interestMonth, String.valueOf(monthAndDay[0]));
	    		interest = BigNumberUtil.Divide(interestMonth, "1000", 2, "1");
	    	}else{
	    		// ��������
	    		if(rateNotFull==0){
	    			/** ���²���ʹ��������,�����²���ʹ��������**/
	    			
	    			// �������������
		    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
		    		// ������������ʳ�������
		    		interestMonth = BigNumberUtil.Multiply(interestMonth, String.valueOf(monthAndDay[0]));
		    		if(StringUtil.equals("0.00", interestMonth)){
		    			interestMonth = "0.00";
		    		}else{
		    			interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10, "1");	
		    		}
		    		
		    		
		    		// ��������ת��Ϊ������
		    		String dayRate = monthRate2DayRate(exxcuteRate);
		    		// �������������
		    		String interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		// ������������ʳ���������
		    		interestDay = BigNumberUtil.Multiply(interestDay, String.valueOf(monthAndDay[1]));
		    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 10, "1");
		    		
		    		// �����²�����Ϣ�ͷ����²��ֵ���Ϣ����һ����Ϊ,������Ϣ
		    		interest = BigNumberUtil.Add(interestDay,interestMonth);
		    		interest  = BigNumberUtil.Divide(interest, "1", 2,"1");
	    		}
	    		
	    		if(rateNotFull==1){
	    			/**ʹ��������**/
	    			// ��������ת��Ϊ������
		    		String dayRate = monthRate2DayRate(exxcuteRate);
		    		String days = "0";
		    		// ���²���Ϊʵ������
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
		    		}
		    		// ���²��ְ�30�����
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
		    		}
		    		
		    		// ������������ʳ���������
		    		String interestDay = BigNumberUtil.Multiply(principal, days);
		    		interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
		    		interest = interestDay;
	    		}
	    	}
	    }else{
	    	/**ʹ��������**/
			// ��������ת��Ϊ������
    		String dayRate = monthRate2DayRate(exxcuteRate);
    		String days = "0";
    		// ���²���Ϊʵ������
    		if(SystemParm.MONTH_OF_DAYS==0){
    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
    		}
    		// ���²��ְ�30�����
    		if(SystemParm.MONTH_OF_DAYS==1){
    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
    		}
    		
    		// ������������ʳ���������
    		String interestDay = BigNumberUtil.Multiply(principal, days);
    		interestDay = BigNumberUtil.Multiply(interestDay, dayRate);
    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
    		interest = interestDay;
	    }
	    return interest;
	}
	
	
	/**
	 * 
	 * ���������� һ�γ��������½�Ϣ
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-6-4 ����11:14:26
	 */
	private String getRateCome_3(PlanBean planBean,AcLnMstBean acLnMstBean){
		// ��Ϣ
		String interest = "0.00";
		// ������Ϣʱʹ�������ʻ��������� 0 ������ 1 ������
	    int useRateType = SystemParm.USE_RATE_TYPE;
	    //  ��ѡ������,���Ҳ������µĴ���, 0  ���²���ʹ�������ʼ���,�����²���ʹ�������ʼ��� 1 ʹ�������ʼ���
	    int rateNotFull = SystemParm.RATE_NOT_FULL;
	    // ���� (���ɻ���ƻ�ʱʹ��)
	    String principal = planBean.getDueBal();
	    if(StringUtil.equals("0",principal)){
	    	return "0.00";
	    }
	    // ��ͬ����
	    String exxcuteRate = acLnMstBean.getLnRate();
	    // ����ƻ�������
	    int []monthAndDay = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
	    // ʹ�������ʼ���
	    if(useRateType==1){
	    	// �ж��ǲ�������,monthAndDay[1]==0 ˵��������
	    	if(monthAndDay[1]==0){
	    		// �������������
	    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
	    		// ������������ʳ�������
	    		interestMonth = BigNumberUtil.Multiply(interestMonth, String.valueOf(monthAndDay[0]));
	    		interest = BigNumberUtil.Divide(interestMonth, "1000", 2, "1");
	    	}else{
	    		// ��������
	    		if(rateNotFull==0){
	    			/** ���²���ʹ��������,�����²���ʹ��������**/
	    			
	    			// �������������
		    		String interestMonth = BigNumberUtil.Multiply(principal, exxcuteRate);
		    		// ������������ʳ�������
		    		interestMonth = BigNumberUtil.Multiply(interestMonth, String.valueOf(monthAndDay[0]));
		    		interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 10, "1");
		    		
		    		// ��������ת��Ϊ������
		    		String dayRate = monthRate2DayRate(exxcuteRate);
		    		// �������������
		    		String interestDay = BigNumberUtil.Multiply(principal, dayRate);
		    		// ������������ʳ���������
		    		interestDay = BigNumberUtil.Multiply(interestDay, String.valueOf(monthAndDay[1]));
		    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
		    		
		    		// �����²�����Ϣ�ͷ����²��ֵ���Ϣ����һ����Ϊ,������Ϣ
		    		interest = BigNumberUtil.Add(interestDay,interestMonth);
		    		interest  = BigNumberUtil.Divide(interest, "1", 2,"1");
	    		}
	    		
	    		if(rateNotFull==1){
	    			/**ʹ��������**/
	    			// ��������ת��Ϊ������
		    		String dayRate = monthRate2DayRate(exxcuteRate);
		    		String days = "0";
		    		// ���²���Ϊʵ������
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
		    		}
		    		// ���²��ְ�30�����
		    		if(SystemParm.MONTH_OF_DAYS==0){
		    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
		    		}
		    		
		    		// ������������ʳ���������
		    		String interestDay = BigNumberUtil.Multiply(principal, days);
		    		interestDay = BigNumberUtil.Multiply(interestDay, dayRate);
		    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
		    		interest = interestDay;
	    		}
	    	}
	    }else{
	    	/**ʹ��������**/
			// ��������ת��Ϊ������
    		String dayRate = monthRate2DayRate(exxcuteRate);
    		String days = "0";
    		// ���²���Ϊʵ������
    		if(SystemParm.MONTH_OF_DAYS==0){
    			days = String.valueOf(DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate()));
    		}
    		// ���²��ְ�30�����
    		if(SystemParm.MONTH_OF_DAYS==0){
    			days= String.valueOf(monthAndDay[0]*30+monthAndDay[1]);
    		}
    		
    		// ������������ʳ���������
    		String interestDay = BigNumberUtil.Multiply(principal, days);
    		interestDay = BigNumberUtil.Multiply(interestDay, dayRate);
    		interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
    		interest = interestDay;
	    }
	    return interest;
		
	}
	/**
	 * 
	 * ���������� �ȶ����Ϣ����
	 * @param planBean
	 * @param acLnMstBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-6-7 ����02:51:57
	 */
	private String getRateCome_4(PlanBean planBean,AcLnMstBean acLnMstBean){
		String interest= "0.00";
		int[] monthAndDay = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
		// ������
		if(SystemParm.USE_RATE_TYPE==1){
			// ������
			if (monthAndDay[1]==0) {
				String  interestMonth = "0.00";
				// ������
				String lnRate = acLnMstBean.getLnRate();
				// ʣ�౾��
				//String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
				String remaCapital =  planBean.getDueBal();
				
				/**���²�����Ϣ**/
				// ʣ�౾�����������
				interestMonth = BigNumberUtil.Multiply(remaCapital,lnRate);
				// ʣ�౾����������ʳ���������
				interestMonth = BigNumberUtil.Multiply(interestMonth,String.valueOf(monthAndDay[0]));
				interest = BigNumberUtil.Divide(interestMonth, "1000", 2, "1");
			}
			// ��������(������)
			if (monthAndDay[1]>0) {
				// ������������,�����²�����������
				if (SystemParm.RATE_NOT_FULL==0) {
					String  interestMonth = "0.00";
					String  interestDay = "0.00";
					// ������
					String lnRate = acLnMstBean.getLnRate();
					// ʣ�౾��
					//String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
					String remaCapital = planBean.getDueBal();
					
					/**���²�����Ϣ**/
					// ʣ�౾�����������
					interestMonth = BigNumberUtil.Multiply(remaCapital,lnRate);
					// ʣ�౾����������ʳ���������
					interestMonth = BigNumberUtil.Multiply(interestMonth,String.valueOf(monthAndDay[0]));
					interestMonth = BigNumberUtil.Divide(interestMonth, "1000", 2, "1");
					/**�����²�����Ϣ**/
					
					String dayRate = monthRate2DayRate(lnRate);
					// ʣ�౾�����������
					interestDay = BigNumberUtil.Multiply(remaCapital,dayRate);
					// ʣ�౾����������ʳ���������
					interestDay = BigNumberUtil.Multiply(interestDay,String.valueOf(monthAndDay[1]));
					interestDay = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
					
					// ���²�����Ϣ�Ӳ����²�����Ϣ
					interest = BigNumberUtil.Add(interestDay,interestMonth);
					interest = BigNumberUtil.Divide(interest, "1", 2, "1");
					
				}
				// ʹ�������ʼ���
				if (SystemParm.RATE_NOT_FULL==1) {
					String  interestDay = "0.00";
					// ʣ�౾��
					String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
					// ������
					String lnRate = acLnMstBean.getLnRate();
					String dayRate = monthRate2DayRate(lnRate);
					int days = 0;
					// һ���°�30 �����
					if (SystemParm.MONTH_OF_DAYS==1) {
						days = monthAndDay[1]*30;
					}
					// ȡһ���µ�ʵ������
					if (SystemParm.MONTH_OF_DAYS==0) {
						days = DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate());
					}
					days = days +monthAndDay[1];
					
					// ʣ�౾�����������
					interestDay = BigNumberUtil.Multiply(remaCapital,dayRate);
					// ʣ�౾����������ʳ���������
					interestDay = BigNumberUtil.Multiply(interestDay,String.valueOf(monthAndDay[1]));
					interest = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
				}
			}
		}
		
		// ʹ�������ʼ���
		if(SystemParm.USE_RATE_TYPE==0){
			String  interestDay = "0.00";
			// ʣ�౾��
			//String remaCapital = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planBean.getDueBal());
			String remaCapital =  planBean.getDueBal();
			// ������
			String lnRate = acLnMstBean.getLnRate();
			String dayRate = monthRate2DayRate(lnRate);
			int days = 0;
			// һ���°�30 �����
			if (SystemParm.MONTH_OF_DAYS==1) {
				days = monthAndDay[1]*30;
			}
			// ȡһ���µ�ʵ������
			if (SystemParm.MONTH_OF_DAYS==0) {
				if (monthAndDay[0]>0) {
					days = DateUtil.getDay(planBean.getBegDate());
					days = DateUtil.getBetweenDays(planBean.getBegDate(), planBean.getEndDate());
				}
			}
			days = days +monthAndDay[1];
			
			// ʣ�౾�����������
			interestDay = BigNumberUtil.Multiply(remaCapital,dayRate);
			// ʣ�౾����������ʳ���������
			interestDay = BigNumberUtil.Multiply(interestDay,String.valueOf(days));
			interest = BigNumberUtil.Divide(interestDay, "1000", 2, "1");
		}
		return interest;
	}
	
	/**
	 * 
	 * ���������� ��������װ��Ϊ������ 
	 * @param momthRate
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-21 ����02:28:07
	 */
	private  String monthRate2DayRate(String momthRate){
		return BigNumberUtil.Divide(momthRate, "30", 10, "1");
	} 
	
	
	/**
	 * 
	 * ���������� ���ݿ�ʼ���ںͽ������ڶ���Ч���ڽ��й���,ȥ�����ڿ�ʼ���ںͽ�������֮�����Ч��(ȡͷ��ȡβ)
	 * @param effectDates
	 * @param beginDate
	 * @param endDate
	 * @return
	 * List<String>
	 * @author Ǭ֮��
	 * @date 2012-5-21 ����11:33:59
	 */
	private List<PartParmBean> filterEffectDate(List<PartParmBean> effectDates , String beginDate,String endDate){
		if(effectDates==null || effectDates.size()==0){
			return null;
		}
		List<PartParmBean>  filterEffectDates = new ArrayList<PartParmBean>();
		for(PartParmBean  partParmBean: effectDates){
			if(DateUtil.isBetween(partParmBean.getEffectDate(), beginDate, endDate)){
				filterEffectDates.add(partParmBean);
			}
		}
		// ������Чʱ���������
		Collections.sort(filterEffectDates);
		return filterEffectDates;
	}
	

	
	
	/**
	 * 
	 * ������������þ��뿪ʼ���������һ�����ʵ���
	 * @param effectDates
	 * @param beginDate
	 * @param endDate
	 * @return
	 * PartParmBean
	 * @author Ǭ֮��
	 * @date 2012-5-22 ����02:31:36
	 */
	private  PartParmBean getLatelyEffectDate(List<PartParmBean> effectDates,String beginDate){
		if (effectDates==null || effectDates.size()==0) {
			return null;
		}
		Collections.sort(effectDates);
		PartParmBean tempPartParmBean = null;
		for(PartParmBean partParmBean:effectDates){
			if(DateUtil.gteq(partParmBean.getEffectDate(),beginDate)){
				tempPartParmBean = partParmBean;
				continue;
			}else{
				break;
			}
		}
		return tempPartParmBean;
	}
	
	
	/**
	 * 
	 * ���������� ����
	 * @param rateAdjustBeans
	 * @param rateWay
	 * @param lnRateFloat(ִ�����ʸ�������)
	 * @return
	 * List<String>
	 * @author Ǭ֮��
	 * @date 2012-5-21 ����11:03:00
	 */
	private List<PartParmBean> getEffectDateByAdjustDate(List<RateAdjustBean> rateAdjustBeans,AcLnMstBean acLnMstBean){
		if(rateAdjustBeans==null || rateAdjustBeans.size()==0){
			return null;
		}
		
		// String rateWay,String lnRateFloat
		    // ��Ϣ��ʽ
			String rateWay = acLnMstBean.getRateWay();
			// ִ�����ʸ�������
			String lnRateFloat = acLnMstBean.getRateFloat();
			// ���ڸ�������
			String overRateFloat = acLnMstBean.getOverRateFloat();
			// ��������
			String  cmpRateFloat = acLnMstBean.getCmpdRateFloat();
			
			
		    List<PartParmBean>  effectDates = new ArrayList<PartParmBean>();
		    for( RateAdjustBean  rateAdjustBean : rateAdjustBeans){
		    	PartParmBean  partParmBean = new PartParmBean(); 
		    	String effectDate = getEffectDate(rateAdjustBean.getAdjustDate(),rateWay);
		    	partParmBean.setEffectDate(effectDate);
		    	if(StringUtil.equals(SystemParm.RATE_MONTH, rateAdjustBean.getRateType())){
		    		String baseRate = rateAdjustBean.getRateValue();
		    		String executeRate = BigNumberUtil.Multiply(baseRate,lnRateFloat);
		    		executeRate = BigNumberUtil.Divide(executeRate, "100",10, "1");
		    		// ��������
		    		String overRate = BigNumberUtil.Multiply(baseRate, overRateFloat);
		    		overRate = BigNumberUtil.Divide(overRate, "100", 10, "1");
		    		// ��������
		    		String cmpRate = BigNumberUtil.Multiply(baseRate, cmpRateFloat); 
		    		cmpRate = BigNumberUtil.Divide(cmpRate, "100",10, "1");	
		    		
		    	}
		    	
		    	if(StringUtil.equals(SystemParm.RATE_YEAR,rateAdjustBean.getRateType())){
		    		String baseRate = rateAdjustBean.getRateValue();
		    		String executeRate = BigNumberUtil.Multiply(baseRate, lnRateFloat);
		    		executeRate = BigNumberUtil.Divide(executeRate, "12", 10, "1");
		    		executeRate = BigNumberUtil.Divide(executeRate, "100", 10, "1");
		    		
		    		// ��������
		    		String overRate = BigNumberUtil.Multiply(baseRate, overRateFloat);
		    		overRate = BigNumberUtil.Divide(overRate, "12", 10, "1");
		    		overRate = BigNumberUtil.Divide(overRate, "100", 10, "1");
		    		
		    		// ��������
		    		String  cmpRate = BigNumberUtil.Multiply(baseRate, cmpRateFloat);
		    		cmpRate = BigNumberUtil.Divide(cmpRate, "12", 10, "1");
		    		cmpRate = BigNumberUtil.Divide(cmpRate, "100", 10, "1");
		    		
		    	}
		    	
		    	if(StringUtil.equals(SystemParm.RATE_DAY,rateAdjustBean.getRateType())){
		    		String baseRate = rateAdjustBean.getRateValue();
		    		String executeRate = BigNumberUtil.Multiply(baseRate, "30");
		    		executeRate = BigNumberUtil.Multiply(executeRate,lnRateFloat);
		    		executeRate = BigNumberUtil.Divide(executeRate,"100",10,"1");
		    		
		    		// ��������
		    		String overRate = BigNumberUtil.Multiply(baseRate, "30");
		    		overRate = BigNumberUtil.Multiply(overRate,overRateFloat);
		    		overRate = BigNumberUtil.Divide(overRate,"100",10,"1");
		    		
		    		// ��������
		    		String cmpRate = BigNumberUtil.Multiply(baseRate, "30");
		    		cmpRate = BigNumberUtil.Multiply(cmpRate,cmpRateFloat);
		    		cmpRate = BigNumberUtil.Divide(cmpRate,"100",10,"1");
		    		
		    	}
		    	effectDates.add(partParmBean);
		    	effectDate = null;
		    	partParmBean = null;
		    }
		    return effectDates;
	}  
	
	
	/**
	 * 
	 * ���������� �������ʵ������ںͼ�Ϣ��ʽ�����Ч����
	 * @param adjust
	 * @param rateWay
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����11:06:28
	 */
	private String getEffectDate(String adjustDate,String rateWay){
		String effectDate =  "";
		// ����������Ч����
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_IMMEDIATELY)){
			effectDate = immediatelyEffectDate(adjustDate);
		}
		//���µ���
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_MONTH)){
			effectDate = monthEffectDate(adjustDate);
		}
		//���¶��յ���
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_MONTH_DAY)){
			effectDate = monthAndDayEffectDate(adjustDate);
		}
		// ��������
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_SEASON)){
			effectDate =  monthAndDayEffectDate(adjustDate);
		}
		//�������¶��յ���
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_SEASON_DAY)){
			effectDate =  monthAndDayEffectDate(adjustDate);
		}
		// �������
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_YEAR)){
			effectDate =  yearEffectDate(adjustDate);
		}
		// ������¶��յ���
		if(StringUtil.equals(rateWay, SystemParm.RATE_WAY_YEAR_MONTH_DAY)){
			effectDate =  yearAndDayEffectDate(adjustDate);
		}
		return effectDate ;
	}
	
	/**
	 * 
	 * ���������� ������������Ч���� 
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����11:09:35
	 */
	private String  immediatelyEffectDate(String adjustDate){
		// ������Ч
		if(SystemParm.EffectiveType==1){
			return DateUtil.addByDay(adjustDate, 1, DateUtil.DATE_FORMAT_);
		}else{// ������Ч
			return adjustDate;
		}
	}
	
	
	/**
	 * 
	 * ���������� ��ð��µ�������Ч��
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����11:25:31
	 */
	private String monthEffectDate(String adjustDate){
		String date =  DateUtil.addByMonDay(adjustDate,1, 0, DateUtil.DATE_FORMAT_);
		return date.substring(0, 8)+"01";
	}
	/**
	 * 
	 * ����������   ��ð��¶��յ�������Ч��
	 * @param adjustDate
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����11:41:55
	 */
	private String monthAndDayEffectDate(String adjustDate){
		String date =  DateUtil.addByMonDay(adjustDate,1, 0, DateUtil.DATE_FORMAT_);
		return date;
	}
	
	/**
	 * 
	 * ����������  ��ð�����������Ч��
	 * @param adjustDate
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����03:17:52
	 */
	private String seasonEffectDate(String adjustDate){
		String date = ""; 
		int month = DateUtil.getMonth(adjustDate);
		
		// 1 2 3
		if(month<4){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-04-01"; 
		}
		
		//4 5 6
		if(3<month && month<7){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-07-01"; 
		}
		
		// 7 8 9
		if(6<month && month<10){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-10-01"; 
		}
		
		//10 11 12
		if(9<month && month<13){
			int year = DateUtil.getYear(adjustDate);
			date = year+1+"-01-01"; 
		}
		
		return date;
	}
	/**
	 * 
	 * ���������� 
	 * @param adjustDate
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����03:36:08
	 */
	private String seasonAndDayEffectDate(String adjustDate){
		
		String date = ""; 
		int month = DateUtil.getMonth(adjustDate);
		int day = DateUtil.getMonth(adjustDate);
		String s_day = String.valueOf(day);
		if(day<10){
			s_day = "0"+day;
		}
		
		// 1 2 3
		if(month<4){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-04-"+s_day; 
		}
		
		//4 5 6
		if(3<month && month<7){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-07-"+s_day; 
		}
		
		// 7 8 9
		if(6<month && month<10){
			int year = DateUtil.getYear(adjustDate);
			date = year+"-10-"+s_day; 
		}
		
		//10 11 12
		if(9<month && month<13){
			int year = DateUtil.getYear(adjustDate);
			date = year+1+"-01-"+s_day; 
		}
		
		return date;
	}
	
	
	/**
	 * 
	 * ����������  ��ð����������Ч��
	 * @param adjustDate
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����03:53:23
	 */
	private  String yearEffectDate(String adjustDate){
		int year = DateUtil.getYear(adjustDate);
		year =  year+1;
 		int month = DateUtil.getMonth(adjustDate);
		String s_month  = String.valueOf(month);
		if(month<10){
			s_month = "0"+month;
		}
		return year+"-"+s_month+"-01";
	}
	
	
	/**
	 * 
	 * ���������� ��ð�����¶��յ�������Ч��
	 * @param adjustDate
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-19 ����04:02:35
	 */
	private String yearAndDayEffectDate(String adjustDate){
		int year = DateUtil.getYear(adjustDate);
		year =  year+1;
 		int month = DateUtil.getMonth(adjustDate);
		String s_month  = String.valueOf(month);
		if(month<10){
			s_month = "0"+month;
		}
		int day = DateUtil.getDay(adjustDate);
		String s_day  = String.valueOf(day); 
		if(day<10){
			s_day = "0"+day;
		}
		return year+"-"+s_month+"-"+s_day;
	}
	
	
	
	
	/*************************************************�°����******************************************************/
	
	/**
	 * ��ü���������Ϣʱʹ�õĻ���
	 */
private  String getOverBase(PlanBean planBean,AcLnMstBean acLnMstBean){
	String overBase = "0.00";
	//�жϻ��ʽ
	String returnMethod = acLnMstBean.getReturnMethod();
	// ���汾��
	if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR, returnMethod)){
		
		//  ���ʣ�౾��
		String dueBal  = acLnMstBean.getDueBal();
		//  �����ձ�����Ϣ ������Ϣ  ������Ϣ�ĺ�
		
		// ������շ�����
		DayRateServices   dayRateServices = SpringFactory.getBean("dayRateServices");
		RatedayBean  tempRatedayBean = new RatedayBean();
		tempRatedayBean.setDueNo(acLnMstBean.getDueNo());
		RatedayBean  ratedayBean = dayRateServices.getRateDay(tempRatedayBean);
		// ���Ƿ�������
		DebtServices debtServices = SpringFactory.getBean("debtServices");
		DebtBean  debtBean =  new DebtBean();
		debtBean.setDueNo(acLnMstBean.getDueNo());
		debtBean.setTermNo("1");
		debtServices.getDebtBean(debtBean);
	    // ʣ�౾�� 
		overBase  = BigNumberUtil.add2String(acLnMstBean.getDueBal(), overBase);
		// ʣ�౾�����Ϣ
		overBase  = BigNumberUtil.add2String(overBase, ratedayBean.getInterest());
		// ʣ�౾�� ����Ϣ ��������Ϣ
		overBase  = BigNumberUtil.add2String(overBase, ratedayBean.getOverInterest());
		// ʣ�౾�� ����Ϣ ��������Ϣ �Ӹ�����Ϣ
		overBase  = BigNumberUtil.add2String(overBase, ratedayBean.getOverInterest());
		// ʣ�౾�� ����Ϣ ��������Ϣ �Ӹ�����Ϣ ��Ƿ����Ϣ
		overBase  = BigNumberUtil.add2String(overBase,debtBean.getDebIntst());
		// ʣ�౾�� ����Ϣ ��������Ϣ �Ӹ�����Ϣ ��Ƿ����Ϣ ��Ƿ��������Ϣ
		overBase  = BigNumberUtil.add2String(overBase,debtBean.getDebOverintst());
		// ʣ�౾�� ����Ϣ ��������Ϣ �Ӹ�����Ϣ ��Ƿ����Ϣ ��Ƿ��������Ϣ �Ӹ�����Ϣ
		overBase  = BigNumberUtil.add2String(overBase,debtBean.getDebCmpdintst());
	}else{
		// ʣ�౾�� 
		overBase  = BigNumberUtil.add2String(acLnMstBean.getDueBal(), overBase);
		// ����ǵڼ��ڻ���ƻ�
		Integer termNo = Integer.parseInt(planBean.getTermNo());
		// ���ݽ�ݺź��ں�(termNo)����ں�С�� termNo ���һ�û�л����ļƻ�,ȡ������Щ�ƻ����Ӧ�����պ�Ƿ����Ϣ
		// ������շ�����
		DayRateServices   dayRateServices = SpringFactory.getBean("dayRateServices");
		// ����ʵ��
		RatedayBean   parmRateDayBean = new RatedayBean();
		parmRateDayBean.setDueNo(planBean.getDueNo());
		parmRateDayBean.setTermNo(planBean.getTermNo());
		// ����ں�С�굱ǰ
		List<RatedayBean> ratedayBeans = dayRateServices.getRateDayBeanList(parmRateDayBean);
		for(RatedayBean ratedayBean :ratedayBeans){
			overBase = BigNumberUtil.Add(overBase,ratedayBean.getInterest(),ratedayBean.getOverInterest(),ratedayBean.getCmpdInterest(),ratedayBean.getAccFee());
		}
		// ���Ƿ�������
		DebtServices debtServices = SpringFactory.getBean("debtServices");
		DebtBean  parmDebtBean = new DebtBean();
		parmDebtBean.setDueNo(planBean.getDueNo());
		parmDebtBean.setTermNo(planBean.getTermNo());
		
		List<DebtBean> debtBeanList =  debtServices.getLoanDebtList(parmDebtBean);
		
		for(DebtBean debtBean:debtBeanList){
			overBase = BigNumberUtil.Add(overBase,debtBean.getDebIntst(),debtBean.getDebOverintst(),debtBean.getDebCmpdintst(),debtBean.getDebAccFee());
		}
	}
	return overBase;
}




private RatedayBean getLastRatedayBean(List<RatedayBean> ratedayBeans,String termNo){
	List<RatedayBean>  result = null;
	
	for (RatedayBean ratedayBean : ratedayBeans) {
		if(StringUtil.equals(termNo, ratedayBean.getTermNo())){
			if (result==null) {
				result = new ArrayList<RatedayBean>();
			}
			result.add(ratedayBean);
		}
	}
	
	if(result!=null){
		Collections.sort(result);
	}
	
	return result.get(result.size()-1);
}



}
