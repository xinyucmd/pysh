/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PlanServices.java
 * ������ com.dx.loan.plan.services.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-26 ����10:20:15
 * @version V1.0
 */ 
package com.dx.loan.plan.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dx.common.SystemParm;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.fee.bean.FeeTypeBean;
import com.dx.loan.fee.services.FeeServices;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanParmBean;
import com.dx.loan.plan.dao.IPlanDao;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.interest.BactInterest;

/**
 * ������ PlanServices
 * ������
 * @author Ǭ֮��
 * @date 2012-5-26 ����10:20:15
 *
 *
 */
public class PlanServices {
	private IPlanDao  planDao ;
	public void setPlanDao(IPlanDao planDao) {
		this.planDao = planDao;
	}

	/**
	 * 
	 * ���������� ���ݽ����Ϣ���ɻ���ƻ�
	 * @param dueBean
	 * @param begDate(���ɻ���ƻ�������,Ĭ��Ϊ��ݵĿ�ʼ����)
	 * @author Ǭ֮��
	 * @date 2012-5-24 ����09:23:52
	 */
	public List<PlanBean> genePlan(AcLnMstBean acLnMstBean,PlanParmBean planParmBean) {
		List<PlanBean> planBeans = null;
		String returnMethod = acLnMstBean.getReturnMethod();
		
		// ���汾�廹��ƻ�
		if(StringUtil.equals(SystemParm.RETURNMETHOD_PROFITSCLEAR, returnMethod)){
			planBeans = genePlan_1(acLnMstBean);
		}
		
		// ���ƻ��Ļ���ƻ�
		if(StringUtil.equals(SystemParm.RETURNMETHOD_PLAN, returnMethod)){
			planBeans = genePlan_2(acLnMstBean,planParmBean);
		}
		
		// һ�γ��������½�Ϣ
		if(StringUtil.equals(SystemParm.RETURNMETHOD_MONTH, returnMethod)){
			planBeans = genePlan_3(acLnMstBean,planParmBean);
		}
		
		// һ�γ������𰴼���Ϣ
		if(StringUtil.equals(SystemParm.RETURNMETHOD_SEASON, returnMethod)){
			planBeans = genePlan_4(acLnMstBean,planParmBean);
		}
		
		// �ȶ�𻹿�ƻ�
		if(StringUtil.equals(SystemParm.RETURNMETHOD_PRINCIPAL, returnMethod)){
			planBeans = genePlan_5(acLnMstBean,planParmBean);
		}
		
		// �ȶϢ����ƻ�
		if(StringUtil.equals(SystemParm.RETURNMETHOD_INTEREST, returnMethod)){
			planBeans = genePlan_6(acLnMstBean,planParmBean);
		}
		return planBeans;
	} 
	
	/**************************���ɻ���ƻ����ֿ�ʼ***********************/
	/**
	 * 
	 * ���������� ���汾�廹��ƻ�
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-24 ����09:48:46
	 */
	private List<PlanBean> genePlan_1(AcLnMstBean acLnMstBean){
		List<PlanBean> planList = new ArrayList<PlanBean>();
		BactInterest interest = new BactInterest();
		PlanBean planBean = new PlanBean();
		planBean.setBegDate(acLnMstBean.getDueBegDate());
		
		planBean.setCifNo(acLnMstBean.getCifNo());
		planBean.setDueAmt(acLnMstBean.getDueAmt());
		planBean.setDueBal(acLnMstBean.getDueBal());
		planBean.setDueNo(acLnMstBean.getDueNo());
		// ʹ�ñ�����
		if(SystemParm.PLAN_END==1){
			String planEndDate = DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1");
			planBean.setEndDate(planEndDate);
		}
		// ʹ�ð뿪�������
		if(SystemParm.PLAN_END==0){
			planBean.setEndDate(acLnMstBean.getDueEndDate());
		}
		planBean.setPactNo(acLnMstBean.getPactNo());
		planBean.setReturnCapital(acLnMstBean.getDueAmt());
		planBean.setState("0");
		planBean.setTermNo("1");
		planBean.setReturnInterest(interest.getInterest(planBean,acLnMstBean));
		// �˻������
		planBean.setAccFee(getFee(acLnMstBean));
		// ��Լ��֤��
		planBean.setPerfAmount(getPerfAmount(acLnMstBean));
		planList.add(planBean);
		return planList;
	}
	
	/**
	 * 
	 * ���������� ������ɵĻ���ƻ�(ÿ�ڵ��������㷽��)
	 * @param dueBean 
	 * @param begDate
	 * void   
	 * @author Ǭ֮��
	 * @date 2012-5-24 ����11:02:23
	 */
	private List<PlanBean> genePlan_2(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// ������Ϣ������
		BactInterest interest = new BactInterest();
		// ��ȡ��������Ϣ
		String advaceInterest = "0.00";
		int plan_day_type = SystemParm.PLAN_DAY_TYPE;
		// �̶�������
		String fixDate = planParmBean.getFixDate();
		// ��ȡ�����ĵ�һ�ڵĽ��������ڱ��»�������һ����   0 ���� 1 ��һ�� 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// ��ȡ������һ�ڵĴ���ʽ 0 �ڵ�һ����ȡ 1 ��������һ���б���  2 ������Ϊһ���ޱ���
		int dealAdvanceType =  SystemParm.DEAL_ADVANCE_TYPE;
		// ���ڲ����Ƿ���ȡ��Ϣ 0 ����ȡ 1��ȡ
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// ��ʾ���һ�ڵ���Ϣ�Ƿ���Ҫ���¼���
		boolean rComLastTerm = false;
		//�Ƿ����� 0 ������ 1 ����
		int isDelay = SystemParm.IS_DELAY;
		// ���ں�Ľ�������
		String  delayDate = "";
		// ��ȡ�����Ľ�������
		String firEndDate = "";
		// ����̶��ջ���
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// ���������ڷſ���������(����һ�ڲ���û�б�����������һ����)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			// ���������ڷſ��������µ���һ��
			if(advanceEndType==1){
				// �ſ����ڼ�һ����
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			
			
			// ��ȡ������һ�ڵ���Ϣ�ڵ�һ����ȡ
			if(dealAdvanceType==0){
				 PlanBean planBean = new PlanBean();
				 planBean.setBegDate(acLnMstBean.getDueBegDate());
				 planBean.setEndDate(firEndDate);
				 planBean.setDueBal(acLnMstBean.getDueBal());
				 planBean.setAccFee(getFee(acLnMstBean));
				 planBean.setPerfAmount(getPerfAmount(acLnMstBean));
				 advaceInterest= interest.getInterest(planBean,acLnMstBean);
				 
			}
			
			// ��ȡ��һ�ڵ�����Ϊһ�����б���
			if(dealAdvanceType==1){
				PlanBean planBean = new PlanBean();
				int firstDay = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), firEndDate);
				// ��ý��������
				int days = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
				String everyDayMoney = BigNumberUtil.Divide(acLnMstBean.getDueBal(), String.valueOf(days), 2, "1");
				String firstCapital = BigNumberUtil.Multiply(everyDayMoney, String.valueOf(firstDay));
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital(firstCapital);
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				 planBean.setAccFee(getFee(acLnMstBean));
				 planBean.setPerfAmount(getPerfAmount(acLnMstBean));
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				// �������ý�ݵ����
				acLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(), firstCapital));
				
			}
			// ������Ϊһ�ڲ���û�б���
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				 planBean.setAccFee(getFee(acLnMstBean));
				 planBean.setPerfAmount(getPerfAmount(acLnMstBean));
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
			}
			
			// ��������������ں�Ľ�������
			if(isDelay==1){
				// ��ý�ݵĽ�����
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// ���ں�Ľ������ڻ��ڵ�ǰ��
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+"0"+tempFixDate;
					}
				}else{
					// ���ں�Ľ�������������һ����
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			// ����ȡ������һ�ڵĽ���������Ϊ��ݵĵĿ�ʼ����
			acLnMstBean.setDueBegDate(firEndDate);
		}
		
		
		
		
		
		
		// ��ý������(����)
		int days = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
		// Ĭ������
		int terms = Integer.parseInt(defaultTerm(acLnMstBean));
		// ���޳�������ȡ��
		int termDays = days/terms;
		// ���޳�����������
		int remaDays = days%terms;
		// ��ݽ���������ȡ��
		String termMoney = BigNumberUtil.Divide(acLnMstBean.getDueBal(),String.valueOf(terms) , 0, "3");
		// ��ݽ����������������
		String tempMoney = BigNumberUtil.Multiply(termMoney, String.valueOf(terms)); 
		String remaMoney = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), tempMoney);
		
		String planBegDate = acLnMstBean.getDueBegDate();
		if(plan_day_type==1){
			for(int i=0;i<terms;++i){
				PlanBean planBean = new PlanBean(); 
				planBean.setTermNo(String.valueOf(i+1));
				planBean.setBegDate(planBegDate);
				String planEndDate = DateUtil.addByDay(planBegDate, termDays, DateUtil.DATE_FORMAT_);
				
				// ����ƻ�Ϊ������(��2012-05-25--2012-05-24)
				if(SystemParm.PLAN_END==1){
					planBegDate = planEndDate;
					planEndDate = DateUtil.subtDays(planEndDate, "1");
				}
				// ����ƻ�Ϊ�뿪�������(��2012-05-25--2012-05-25)
				if(SystemParm.PLAN_END==0){
					planBegDate = planEndDate;
				}
				
				// ��������
				if(i==terms-1){
					// �������һ�ڵĽ�������
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}else{
					// ������ڵĽ�������
					planBean.setEndDate(planEndDate);
				}
				
			
				
	 			if(remaDays>0){
	 				planEndDate  =  DateUtil.addByDay(planEndDate, 1, DateUtil.DATE_FORMAT_);
	 				remaDays = remaDays-1;
	 			}
	 			
	 			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
	 			if(i<13){
	 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
	 			}
	 			planBeans.add(planBean);
	 			
	 			planBean = null;
			}
		}
		
		if(plan_day_type==0){
			String tempBal = "0.00";
			for(int i=0;i<terms;++i){
				PlanBean planBean = new PlanBean();
				planBean.setTermNo(String.valueOf(i+1));
				planBean.setBegDate(planBegDate);
				String planEndDate = DateUtil.addByMonDay(planBegDate, 1, 0, DateUtil.DATE_FORMAT_);
				planBean.setBegDate(planBegDate);
				// ����ƻ�Ϊ������(��2012-05-25--2012-05-24)
				if(SystemParm.PLAN_END==1){
					planBegDate = planEndDate;
					planEndDate = DateUtil.subtDays(planEndDate, "1");
				}
				// ����ƻ�Ϊ�뿪�������(��2012-05-25--2012-05-25)
				if(SystemParm.PLAN_END==0){
					planBegDate = planEndDate;
				}
				
				
				if(i==terms-1){
					// ���һ��ʹ�ñ�����
					if(SystemParm.LAST_TERM_OPEN==0){
						planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
					}
					// ���һ��ʹ�ÿ�����
					if(SystemParm.LAST_TERM_OPEN==1){
						planBean.setEndDate(acLnMstBean.getDueEndDate());
					}
					planBean.setReturnCapital(BigNumberUtil.Add(termMoney,remaMoney));
					// ���ں��������һ��
					if(StringUtil.isNotEmpty(delayDate)){
						planBean.setEndDate(delayDate);
					}
					
				}else{
					planBean.setReturnCapital(termMoney);
					planBean.setEndDate(planEndDate);
				}
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueAmt());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setPactNo(acLnMstBean.getPactNo());
				String planBal = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), tempBal);
				planBal = BigNumberUtil.Divide(planBal, "1",2,"1");
				planBean.setDueBal(planBal);
				
				tempBal = BigNumberUtil.Add(tempBal, planBean.getReturnCapital());
				planBean.setState("0");
				planBean.setReturnInterest(BigNumberUtil.Divide(interest.getInterest(planBean,acLnMstBean), "1", 2, "1"));
				
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				// �˻������
				planBean.setAccFee(getFee(acLnMstBean));
				// ��Լ��֤���
				if(i<13){
					planBean.setPerfAmount(getPerfAmount(acLnMstBean));
				}
				planBeans.add(planBean);
				planBean = null;
			}
		}
		// ���б��ں�����
		Collections.sort(planBeans);
		// ��ȡ��������Ϣ�ڵ�һ����ȡʱʹ��
		if(Double.parseDouble(advaceInterest)>0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(planBeans.get(0).getReturnInterest(),advaceInterest));
		}
		// �޸��ں�
		if(StringUtil.equals("0", planBeans.get(0).getTermNo())){
			int i=1;
			for(PlanBean planBean:planBeans){
				planBean.setTermNo(String.valueOf(i));
				i=i+1;
			}
		}
		// ���ڲ��ֲ�������Ϣʱ,���¼������һ�ڵ���Ϣ
		if(rComLastTerm){
			int planBeanSize = planBeans.size();
			PlanBean planBean = planBeans.get(planBeanSize-1);
			String endDate = planBean.getEndDate();
			planBean.setEndDate(acLnMstBean.getDueEndDate());
			String  interestMoney = interest.getInterest(planBean,acLnMstBean);
			planBean.setReturnInterest(interestMoney);
			planBean.setEndDate(endDate);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
		}
		
		return planBeans;
	}
	
	
	/**
	 * 
	 * ���������� һ�γ��������½�Ϣ
	 * @param dueBean
	 * @param planParmBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-24 ����06:43:37
	 */
	private List<PlanBean> genePlan_3(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		BactInterest interest = new BactInterest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// �̶�������ʱԤ�Ƚ�ȡ������(����)�Ľ�������ʱ�ڱ��»�����һ���� 0 ���� 1 ��һ�� 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// �̶������ս�ȡ������һ�ڵĴ���ʽ 0 �ڵ�һ����ȡ 1 ����һ���б��� 2 ����һ���ޱ���
		int dealAdvanceType = SystemParm.DEAL_ADVANCE_TYPE;
		// ���ڲ����Ƿ���ȡ��Ϣ 0 ����ȡ 1��ȡ
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// ��ʾ���һ�ڵ���Ϣ�Ƿ���Ҫ���¼���
		boolean rComLastTerm = false;
		// �̶�������ʱԤ�Ƚ�ȡ������(����)�Ľ�������
		String firEndDate="";
		// �Ƿ�����  ֻ��ѡ��̶������պ�����ò���������  0 ������  1 ����
		int isDelay = SystemParm.IS_DELAY;
		// ��ȡ������һ�ڵ���Ϣ
		String advaceInterest="0.00";
		// �̶�������
		String fixDate = planParmBean.getFixDate();
		// ���ں�Ľ������� 
		String delayDate="";
		// ����̶��ջ���
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// ���������ڷſ���������(����һ�ڲ���û�б�����������һ����)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			// ���������ڷſ��������µ���һ��
			if(advanceEndType==1){
				// �ſ����ڼ�һ����
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			
			
			// ��ȡ������һ�ڵ���Ϣ�ڵ�һ����ȡ
			if(dealAdvanceType==0){
				 PlanBean planBean = new PlanBean();
				 planBean.setBegDate(acLnMstBean.getDueBegDate());
				 planBean.setEndDate(firEndDate);
				 planBean.setDueBal(acLnMstBean.getDueBal());
				 advaceInterest= interest.getInterest(planBean,acLnMstBean);
				 
			}
			
			// ��ȡ��һ�ڵ�����Ϊһ�����б���(һ�γ�������Ƚ��������͵���һ���ޱ�����ͬ)
			if(dealAdvanceType==1){
				/**
				PlanBean planBean = new PlanBean();
				int firstDay = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), firEndDate);
				// ��ý��������
				int days = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
				String everyDayMoney = BigNumberUtil.Divide(acLnMstBean.getDueBal(), String.valueOf(days), 2, "1");
				String firstCapital = BigNumberUtil.Multiply(everyDayMoney, String.valueOf(firstDay));
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital(firstCapital);
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				// �������ý�ݵ����
				acLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(), firstCapital));
				**/
				
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				
			}
			// ������Ϊһ�ڲ���û�б���
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBeans.add(planBean);
			}
			
			// ��������������ں�Ľ�������
			if(isDelay==1){
				// ��ý�ݵĽ�����
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// ���ں�Ľ������ڻ��ڵ�ǰ��
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+"0"+tempFixDate;
					}
				}else{
					// ���ں�Ľ�������������һ����
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			// ����ȡ������һ�ڵĽ���������Ϊ��ݵĵĿ�ʼ����
			acLnMstBean.setDueBegDate(firEndDate);
		}
		
		// ����ƻ���һ�ڿ�ʼ����
		String planBegDate = acLnMstBean.getDueBegDate();
		// Ĭ������
		int terms = Integer.parseInt(defaultTerm(acLnMstBean));
		for(int i=0;i<terms;++i){
			PlanBean planBean = new PlanBean();
			planBean.setBegDate(planBegDate);
			String planEndDate = DateUtil.addByMonDay(planBegDate, 1, 0, DateUtil.DATE_FORMAT_);
			// ����ƻ�Ϊ������(��2012-05-25--2012-05-24)
			if(SystemParm.PLAN_END==1){
				planBegDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// ����ƻ�Ϊ�뿪�������(��2012-05-25--2012-05-25)
			if(SystemParm.PLAN_END==0){
				planBegDate = planEndDate;
			}
			planBean.setTermNo(String.valueOf(i+1));
			if(i==terms-1){
				planBean.setReturnCapital(acLnMstBean.getDueBal());
				// ���һ��ʹ�ñ�����
				if(SystemParm.LAST_TERM_OPEN==0){
					planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
				}
				// ���һ��ʹ�ÿ�����
				if(SystemParm.LAST_TERM_OPEN==1){
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}
				// ���ں��������һ��
				if(StringUtil.isNotEmpty(delayDate)){
					planBean.setEndDate(delayDate);
				}
			}else {
				planBean.setReturnCapital("0.00");
				planBean.setEndDate(planEndDate);
			}
			planBean.setCifNo(acLnMstBean.getCifNo());
			planBean.setDueAmt(acLnMstBean.getDueAmt());
			planBean.setDueBal(acLnMstBean.getDueBal());
			planBean.setDueNo(acLnMstBean.getDueNo());
			planBean.setPactNo(acLnMstBean.getPactNo());
			planBean.setState("0");
			planBean.setReturnInterest(interest.getInterest(planBean,acLnMstBean));
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
			// �˻������
			planBean.setAccFee(getFee(acLnMstBean));
				if(i<13){
	 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
	 			}
			planBeans.add(planBean);
		
		}
		
		// ���б��ں�����
		Collections.sort(planBeans);
		// ��ȡ��������Ϣ�ڵ�һ����ȡʱʹ��
		if(Double.parseDouble(advaceInterest)>0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(planBeans.get(0).getReturnInterest(),advaceInterest));
		}
		// �޸��ں�
		if(StringUtil.equals("0", planBeans.get(0).getTermNo())){
			int i=1;
			for(PlanBean planBean:planBeans){
				planBean.setTermNo(String.valueOf(i));
				i=i+1;
			}
		}
		if(rComLastTerm){
			int planBeanSize = planBeans.size();
			PlanBean planBean = planBeans.get(planBeanSize-1);
			String endDate = planBean.getEndDate();
			planBean.setEndDate(acLnMstBean.getDueEndDate());
			String interestMoney = interest.getInterest(planBean, acLnMstBean);
			planBean.setReturnInterest(interestMoney);
			planBean.setEndDate(endDate);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
		}
	
		return planBeans;
	}
	
	/**
	 * 
	 * ����������  һ�γ������𰴼���Ϣ
	 * @param dueBean
	 * @param planParmBean
	 * @return
	 * List<PlanBean>
	 * @author Ǭ֮��
	 * @date 2012-5-25 ����09:11:32
	 */
	private List<PlanBean> genePlan_4(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		BactInterest interest = new BactInterest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// �̶�������ʱԤ�Ƚ�ȡ������(����)�Ľ�������ʱ�ڱ��»�����һ���� 0 ���� 1 ��һ�� 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// �̶������ս�ȡ������һ�ڵĴ���ʽ 0 �ڵ�һ����ȡ 1 ����һ���б��� 2 ����һ���ޱ���
		int dealAdvanceType = SystemParm.DEAL_ADVANCE_TYPE;
		// ���ڲ����Ƿ���ȡ��Ϣ 0 ����ȡ 1��ȡ
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// ��ʾ���һ�ڵ���Ϣ�Ƿ���Ҫ���¼���
		boolean rComLastTerm = false;
		// �̶�������ʱԤ�Ƚ�ȡ������(����)�Ľ�������
		String firEndDate="";
		// �Ƿ�����  ֻ��ѡ��̶������պ�����ò���������  0 ������  1 ����
		int isDelay = SystemParm.IS_DELAY;
		// ��ȡ������һ�ڵ���Ϣ
		String advaceInterest="0.00";
		// �̶�������
		String fixDate = planParmBean.getFixDate();
		// ���ں�Ľ������� 
		String delayDate="";
		// ����̶��ջ���
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// ���������ڷſ���������(����һ�ڲ���û�б�����������һ����)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			// ���������ڷſ��������µ���һ��
			if(advanceEndType==1){
				// �ſ����ڼ�һ����
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			
			
			// ��ȡ������һ�ڵ���Ϣ�ڵ�һ����ȡ
			if(dealAdvanceType==0){
				 PlanBean planBean = new PlanBean();
				 planBean.setBegDate(acLnMstBean.getDueBegDate());
				 planBean.setEndDate(firEndDate);
				 planBean.setDueBal(acLnMstBean.getDueBal());
				 advaceInterest= interest.getInterest(planBean,acLnMstBean);
				 
			}
			
			// ��ȡ��һ�ڵ�����Ϊһ�����б���(һ�γ�������Ƚ��������͵���һ���ޱ�����ͬ)
			if(dealAdvanceType==1){
				/**
				PlanBean planBean = new PlanBean();
				int firstDay = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), firEndDate);
				// ��ý��������
				int days = DateUtil.getBetweenDays(acLnMstBean.getDueBegDate(), acLnMstBean.getDueEndDate());
				String everyDayMoney = BigNumberUtil.Divide(acLnMstBean.getDueBal(), String.valueOf(days), 2, "1");
				String firstCapital = BigNumberUtil.Multiply(everyDayMoney, String.valueOf(firstDay));
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital(firstCapital);
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				// �������ý�ݵ����
				acLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(), firstCapital));
				**/
				
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				
			}
			// ������Ϊһ�ڲ���û�б���
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setState("0");
				planBean.setTermNo("0");
				planBeans.add(planBean);
			}
			
			// ��������������ں�Ľ�������
			if(isDelay==1){
				// ��ý�ݵĽ�����
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// ���ں�Ľ������ڻ��ڵ�ǰ��
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+"0"+tempFixDate;
					}
				}else{
					// ���ں�Ľ�������������һ����
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				// 
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			// ����ȡ������һ�ڵĽ���������Ϊ��ݵĵĿ�ʼ����
			acLnMstBean.setDueBegDate(firEndDate);
		}
		
		// ����ƻ���һ�ڿ�ʼ����
		String planBegDate = acLnMstBean.getDueBegDate();
		// Ĭ������
		int terms = Integer.parseInt(defaultTerm(acLnMstBean));
		for(int i=0;i<terms;++i){
			PlanBean planBean = new PlanBean();
			planBean.setBegDate(planBegDate);
			String planEndDate = DateUtil.addByMonDay(planBegDate, 3, 0, DateUtil.DATE_FORMAT_);
			// ����ƻ�Ϊ������(��2012-05-25--2012-05-24)
			if(SystemParm.PLAN_END==1){
				planBegDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// ����ƻ�Ϊ�뿪�������(��2012-05-25--2012-05-25)
			if(SystemParm.PLAN_END==0){
				planBegDate = planEndDate;
			}
			planBean.setTermNo(String.valueOf(i+1));
			if(i==terms-1){
				planBean.setReturnCapital(acLnMstBean.getDueBal());
				// ���һ��ʹ�ñ�����
				if(SystemParm.LAST_TERM_OPEN==0){
					planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
				}
				// ���һ��ʹ�ÿ�����
				if(SystemParm.LAST_TERM_OPEN==1){
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}
				// ���ں��������һ��
				if(StringUtil.isNotEmpty(delayDate)){
					planBean.setEndDate(delayDate);
				}
			}else {
				planBean.setReturnCapital("0.00");
				planBean.setEndDate(planEndDate);
			}
			planBean.setCifNo(acLnMstBean.getCifNo());
			planBean.setDueAmt(acLnMstBean.getDueAmt());
			planBean.setDueBal(acLnMstBean.getDueBal());
			planBean.setDueNo(acLnMstBean.getDueNo());
			planBean.setPactNo(acLnMstBean.getPactNo());
			planBean.setState("0");
			planBean.setReturnInterest(interest.getInterest(planBean,acLnMstBean));
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
			// �˻������
			planBean.setAccFee(getFee(acLnMstBean));
			if(i<13){
 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
 			}
			planBeans.add(planBean);
		
		}
		
		// ���б��ں�����
		Collections.sort(planBeans);
		// ��ȡ��������Ϣ�ڵ�һ����ȡʱʹ��
		if(Double.parseDouble(advaceInterest)>0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(planBeans.get(0).getReturnInterest(),advaceInterest));
		}
		// �޸��ں�
		if(StringUtil.equals("0", planBeans.get(0).getTermNo())){
			int i=1;
			for(PlanBean planBean:planBeans){
				planBean.setTermNo(String.valueOf(i));
				i=i+1;
			}
		}
		
		if(rComLastTerm){
			int planBeanSize = planBeans.size();
			PlanBean planBean = planBeans.get(planBeanSize-1);
			String endDate = planBean.getEndDate();
			String interestMoney = interest.getInterest(planBean, acLnMstBean);
			planBean.setReturnInterest(interestMoney);
			planBean.setEndDate(endDate);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
		}
	
		return planBeans;
	}
	
	/**
	 * 
	 * ���������� �ȶ�𻹿�ƻ�
	 * ÿ�»�����Ϣ���=������/����������+�������ۼ��ѻ����𣩡������� 
	 * ÿ�±���=�ܱ���/�������� 
     * ÿ����Ϣ=������-�ۼ��ѻ����𣩡������� 
     * ����ԭ��ÿ�¹黹�ı����ʼ�ղ��䣬��Ϣ��ʣ�౾��ļ��ٶ����� 
	 * @param dueBean
	 * @param planParmBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-25 ����10:39:38
	 */
	private List<PlanBean>  genePlan_5(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		BactInterest interest = new BactInterest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// �̶�������ʱԤ�Ƚ�ȡ������(����)�Ľ�������ʱ�ڱ��»�����һ���� 0 ���� 1 ��һ�� 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// �̶������ս�ȡ������һ�ڵĴ���ʽ 0 �ڵ�һ����ȡ 1 ����һ���б��� 2 ����һ���ޱ���
		int dealAdvanceType = SystemParm.DEAL_ADVANCE_TYPE;
		// ���ڲ����Ƿ���ȡ��Ϣ 0 ����ȡ 1��ȡ
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// ��ʾ���һ�ڵ���Ϣ�Ƿ���Ҫ���¼���
		boolean rComLastTerm = false;
		// �Ƿ����� 0 ������ 1 ����
		int isDelay = SystemParm.IS_DELAY;
		// ���ں�Ľ�������
		String delayDate="";
		// �̶������ս�ȡ������һ�ڵ���Ϣ
		String advaceInterest="0.00";
		// �̶�������ʱԤ�Ƚ�ȡ������(����)�Ľ�������
		String firEndDate="";
		// �̶�������
		String fixDate = planParmBean.getFixDate();
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// ���������ڷſ���������(����һ�ڲ���û�б�����������һ����)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			// ���������ڷſ��������µ���һ��
			if(advanceEndType==1){
				// �ſ����ڼ�һ����
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			
			// ��ȡ������һ�ڵ���Ϣ�ڵ�һ����ȡ
			if(dealAdvanceType==0){
				 PlanBean planBean = new PlanBean();
				 planBean.setBegDate(acLnMstBean.getDueBegDate());
				 planBean.setEndDate(firEndDate);
				 planBean.setDueBal(acLnMstBean.getDueBal());
				 planBean.setTermNo("0");
				 advaceInterest= interest.getInterest(planBean,acLnMstBean);
			}
			
			// ��ȡ��һ�ڵ�����Ϊһ�����б���
			if(dealAdvanceType==1){
				PlanBean planBean = new PlanBean();
				int terms = Integer.parseInt(defaultTerm(acLnMstBean));
				terms =  terms+1;
				String  firstCapital = BigNumberUtil.Divide(acLnMstBean.getDueAmt(), String.valueOf(terms), 2, "1");
				String remaBal =  BigNumberUtil.Subtract(acLnMstBean.getDueBal(), firstCapital);
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(remaBal);
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital(firstCapital);
				planBean.setState("0");
				planBean.setTermNo("0");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
			
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				// �������ý�ݵ����
				acLnMstBean.setDueBal(remaBal);
			}
			
			// ������Ϊһ�ڲ���û�б���
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setTermNo("0");
				planBean.setState("0");
				planBean.setReturnInterest(interest.getInterest(planBean, acLnMstBean));
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
			}
			
			// ��������������ں�Ľ�������
			if(isDelay==1){
				// ��ý�ݵĽ�����
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// ���ں�Ľ������ڻ��ڵ�ǰ��
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}else{
					// ���ں�Ľ�������������һ����
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				// ���ڲ��ֲ���ȡ��Ϣ
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			// ����ȡ������һ�ڵĽ���������Ϊ��ݵĵĿ�ʼ����
			acLnMstBean.setDueBegDate(firEndDate);
		}
		
		// Ĭ������
		int terms = Integer.parseInt(defaultTerm(acLnMstBean));
		// ÿ�³�������
		String  capital = BigNumberUtil.Divide(acLnMstBean.getDueBal(), String.valueOf(terms), 2, "1");
        // ģ��ʣ�౾��(������Ϣʹ��)
        String remaCapital = "0.00";
        String planBenDate = acLnMstBean.getDueBegDate();
		for(int i=0;i<terms;++i){
        	PlanBean planBean = new PlanBean();
        	planBean.setTermNo(String.valueOf(i+1));
        	planBean.setBegDate(planBenDate);
        	String planEndDate = DateUtil.addByMonDay(planBenDate, 1, 0, DateUtil.DATE_FORMAT_);
    		// ����ƻ�Ϊ������(��2012-05-25--2012-05-24)
			if(SystemParm.PLAN_END==1){
				planBenDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// ����ƻ�Ϊ�뿪�������(��2012-05-25--2012-05-25)
			if(SystemParm.PLAN_END==0){
				planBenDate = planEndDate;
			}
			
			// ��������
			if(i==terms-1){
				// �������һ�ڵĽ�������
				// ���һ��ʹ�ñ�����
				if(SystemParm.LAST_TERM_OPEN==0){
					planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
				}
				// ���һ��ʹ�ÿ�����
				if(SystemParm.LAST_TERM_OPEN==1){
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}
				// ���ں��������һ��
				if(StringUtil.isNotEmpty(delayDate)){
					planBean.setEndDate(delayDate);
				}
			}else{
				// ������ڵĽ�������
				planBean.setEndDate(planEndDate);
			}
        	planBean.setCifNo(acLnMstBean.getCifNo());
        	planBean.setDueAmt(acLnMstBean.getDueAmt());
        	planBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueAmt(), remaCapital));
        	remaCapital =BigNumberUtil.add2String(remaCapital, capital);
        	planBean.setDueNo(acLnMstBean.getDueNo());
        	planBean.setPactNo(acLnMstBean.getPactNo());
        	planBean.setState("0");
        	planBean.setReturnCapital(capital);
        	String termInterest = interest.getInterest(planBean,acLnMstBean);
        	planBean.setReturnInterest(termInterest);
        	planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
        	// �˻������
			planBean.setAccFee(getFee(acLnMstBean));
			if(i<13){
 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
 			}
        	planBeans.add(planBean);
        }		
		
		// ���б��ں�����
		Collections.sort(planBeans);
		// ��ȡ��������Ϣ�ڵ�һ����ȡʱʹ��
		if(Double.parseDouble(advaceInterest)>0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(planBeans.get(0).getReturnInterest(),advaceInterest));
		}
		// �޸��ں�
		if(StringUtil.equals("0", planBeans.get(0).getTermNo())){
			int i=1;
			for(PlanBean planBean:planBeans){
				planBean.setTermNo(String.valueOf(i));
				i=i+1;
			}
		}
		// ���ڲ��ֲ���ȡ��Ϣ
		if(rComLastTerm){
			int planBeanSize =  planBeans.size();
			PlanBean planBean = planBeans.get(planBeanSize-1);
			String endDate = planBean.getEndDate();
			planBean.setEndDate(acLnMstBean.getDueEndDate());
			String  interestMoney =  interest.getInterest(planBean,acLnMstBean);
			planBean.setReturnInterest(interestMoney);
			planBean.setEndDate(endDate);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
		}
		
		return planBeans;
	}
	
	
	/**
	 * 
	 * ����������  �ȶϢ����ƻ�
	 * @param dueBean
	 * @param planParmBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-5-25 ����06:44:52
	 */
	private List<PlanBean> genePlan_6(AcLnMstBean acLnMstBean,PlanParmBean planParmBean){
		BactInterest interest = new BactInterest();
		List<PlanBean> planBeans = new ArrayList<PlanBean>();
		// �̶�������ʱԤ�Ƚ�ȡ������(����)�Ľ�������ʱ�ڱ��»�����һ���� 0 ���� 1 ��һ�� 
		int advanceEndType = SystemParm.ADVANCE_END_TYPE;
		// �̶������ս�ȡ������һ�ڵĴ���ʽ 0 �ڵ�һ����ȡ 1 ����һ���б��� 2 ����һ���ޱ���
		int dealAdvanceType = SystemParm.DEAL_ADVANCE_TYPE;
		// ���ڲ����Ƿ���ȡ��Ϣ 0 ����ȡ 1��ȡ
		int gatherDelayRate = SystemParm.GATHER_DELAY_RATE;
		// ��ʾ���һ�ڵ���Ϣ�Ƿ���Ҫ���¼���
		boolean rComLastTerm = false;
		 dealAdvanceType = 0;
		//�ж��Ƿ����� 0 ������ 1 ����
		int isDelay = SystemParm.IS_DELAY;
		// ���ں�Ľ�ݽ�����
		String delayDate = "";
		// ��ȡ��������һ�ڵĽ������� 
		String firEndDate = "";
		// Ĭ������
		int terms = 0;
		// Ԥ�Ƚ�ȡ��������Ϣ
		String advaceInterest="0.00";
		// Ĭ�Ͻ�ݿ�ʼ����
		String dueBegDate = acLnMstBean.getDueBegDate();
		
		// �̶�������
		String fixDate = planParmBean.getFixDate();
		if(StringUtil.isNotEmpty(fixDate)){
			int tempFixDate = Integer.parseInt(fixDate);
			int putoutDay = DateUtil.getDay(acLnMstBean.getDueBegDate());
			// ���������ڷſ���������(����һ�ڲ���û�б�����������һ����)
			if(advanceEndType==0  || dealAdvanceType==2){
				if(putoutDay< tempFixDate){
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}else{
					String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT_);
					if(tempFixDate<10){
						firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
					}else{
						firEndDate = date.substring(0, date.length()-2)+fixDate; 
					}
				}
			}
			
			
			// ���������ڷſ��������µ���һ��
			if(advanceEndType==1){
				// �ſ����ڼ�һ����
				String date = DateUtil.addByMonDay(acLnMstBean.getDueBegDate(), 1, 0, DateUtil.DATE_FORMAT);
				if(tempFixDate<10){
					firEndDate = date.substring(0, date.length()-2)+"0"+fixDate; 
				}else{
					firEndDate = date.substring(0, date.length()-2)+fixDate; 
				}
			}
			
			// ��ȡ��������Ϣ�ڵ�һ����ȡ
			if(dealAdvanceType==0){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setEndDate(firEndDate);
				planBean.setDueBal(acLnMstBean.getDueBal());
				advaceInterest = interest.getInterestByDay(planBean, acLnMstBean.getDueBal(), acLnMstBean);
				acLnMstBean.setDueBegDate(firEndDate);
				terms = Integer.parseInt(defaultTerm(acLnMstBean));
			}
			
			
			
			// ����һ���б���
			if(dealAdvanceType==1){
				// ����ݵĿ�ʼ��������Ϊ��ȡ������һ�ڵĽ�������
			    acLnMstBean.setDueBegDate(firEndDate);
				// ����ӽ�ȡ������һ�ڵĽ������ڵ���ݽ������ڵ�����
				terms = Integer.parseInt(defaultTerm(acLnMstBean));
				//���������������������ȡ��������һ�������ټ���1
				terms = terms+1;
				// �ָ���ݵ�Ĭ�Ͽ�ʼ����
				acLnMstBean.setDueBegDate(dueBegDate);
			}
			
			// ����һ���ޱ���
			if(dealAdvanceType==2){
				PlanBean planBean = new PlanBean();
				planBean.setBegDate(acLnMstBean.getDueBegDate());
				planBean.setCifNo(acLnMstBean.getCifNo());
				planBean.setDueAmt(acLnMstBean.getDueBal());
				planBean.setDueBal(acLnMstBean.getDueBal());
				planBean.setDueNo(acLnMstBean.getDueNo());
				planBean.setEndDate(firEndDate);
				planBean.setPactNo(acLnMstBean.getPactNo());
				planBean.setReturnCapital("0.00");
				planBean.setTermNo("0");
				planBean.setState("0");
				planBean.setReturnInterest(interest.getInterestByDay(planBean, acLnMstBean.getDueBal(), acLnMstBean));
				planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
				planBeans.add(planBean);
				
				// ����ݵĿ�ʼ��������Ϊ��ȡ������һ�ڵĽ�������
			    acLnMstBean.setDueBegDate(firEndDate);
				// ����ӽ�ȡ������һ�ڵĽ������ڵ���ݽ������ڵ�����
				terms = Integer.parseInt(defaultTerm(acLnMstBean));
				//���������������������ȡ��������һ�������ټ���1
			}
			// ��������������ں�Ľ�������
			if(isDelay==1){
				// ��ý�ݵĽ�����
				int endDay = DateUtil.getDay(acLnMstBean.getDueEndDate());
				// ���ں�Ľ������ڻ��ڵ�ǰ��
				if(endDay<tempFixDate){
					String date = acLnMstBean.getDueEndDate().substring(0,acLnMstBean.getDueEndDate().length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+"0"+tempFixDate;
					}
				}else{
					// ���ں�Ľ�������������һ����
					String date = DateUtil.addByMonDay(acLnMstBean.getDueEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
					date = date.substring(0,date.length()-2);
					if(tempFixDate<10){
						delayDate = date+"0"+tempFixDate;
					}else{
						delayDate = date+tempFixDate;
					}
				}
				// ���ڲ��ֲ���ȡ��Ϣ
				if (gatherDelayRate==0) {
					rComLastTerm = true;
				}
			}
			
			
		}else{
			terms = Integer.parseInt(defaultTerm(acLnMstBean));
		}
		
		// ��һ�ڵĿ�ʼ����
		String planBenDate = acLnMstBean.getDueBegDate();
		//ģ��ʣ�౾��,������Ϣ����
		String remaMoney = "0.00";
		
		for(int i=0;i<terms;++i){
			PlanBean  planBean = new PlanBean(); 
			// ����
			planBean.setTermNo(String.valueOf(i+1));
			// ��ʼ����
			planBean.setBegDate(planBenDate);
			String planEndDate ="";
			if(i==0 && StringUtil.isNotEmpty(firEndDate) ){
				 planEndDate = firEndDate;
				 planEndDate = DateUtil.addByMonDay(planBenDate, 1, 0, DateUtil.DATE_FORMAT_);
			}else{
				
				 planEndDate = DateUtil.addByMonDay(planBenDate, 1, 0, DateUtil.DATE_FORMAT_);
			}
			// ����ƻ�Ϊ������(��2012-05-25--2012-05-24)
			if(SystemParm.PLAN_END==1){
				planBenDate = planEndDate;
				planEndDate = DateUtil.subtDays(planEndDate, "1");
			}
			// ����ƻ�Ϊ�뿪�������(��2012-05-25--2012-05-25)
			if(SystemParm.PLAN_END==0){
				planBenDate = planEndDate;
			}
			
			// ��������
			if(i==terms-1){
				// �������һ�ڵĽ�������
				// ���һ��ʹ�ñ�����
				if(SystemParm.LAST_TERM_OPEN==0){
					planBean.setEndDate(DateUtil.subtDays(acLnMstBean.getDueEndDate(), "1"));
				}
				// ���һ��ʹ�ÿ�����
				if(SystemParm.LAST_TERM_OPEN==1){
					planBean.setEndDate(acLnMstBean.getDueEndDate());
				}
				// ����������ý�ݵ����һ�ڵĽ�������
				if(StringUtil.isNotEmpty(delayDate)){
					planBean.setEndDate(delayDate);
				}
			}else{
				// ������ڵĽ�������
				planBean.setEndDate(planEndDate);
			}
		
			// �ͻ���
			planBean.setCifNo(acLnMstBean.getCifNo());
			planBean.setDueNo(acLnMstBean.getDueNo());
			// ��ݽ��
			planBean.setDueAmt(acLnMstBean.getDueBal());
			// ��Ϣ�ϼ�
			String TotalMoney = interest.getInterest(planBean,terms, BigNumberUtil.Subtract(acLnMstBean.getDueBal(),remaMoney),acLnMstBean);
			// ������Ϣ
			String interestMoney =   interest.getInterestByMonth(planBean,BigNumberUtil.Subtract(acLnMstBean.getDueBal(),remaMoney),acLnMstBean);
			// Ӧ����Ϣ
			planBean.setReturnInterest(interestMoney);
			String capital = BigNumberUtil.Subtract(TotalMoney, interestMoney);
			capital = BigNumberUtil.Divide(capital, "1", 2, "1");
			// Ӧ������
			planBean.setReturnCapital(capital);
			remaMoney = BigNumberUtil.Add(remaMoney,capital);
			// ������
			planBean.setDueBal(remaMoney);
			// ��ͬ��
			planBean.setPactNo(acLnMstBean.getPactNo());
			// ����ƻ�״̬
			planBean.setState("0");
			// ��Ϣ�ϼ�
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
			// �˻������
			planBean.setAccFee(getFee(acLnMstBean));
			if(i<13){
 				planBean.setPerfAmount(getPerfAmount(acLnMstBean));
 			}
			
			planBeans.add(planBean);
		}
		Collections.sort(planBeans);
		// ���������һ��
		if(dealAdvanceType==0){
			planBeans.get(0).setReturnInterest(BigNumberUtil.Add(advaceInterest,planBeans.get(0).getReturnInterest()));
			planBeans.get(0).setTotal(BigNumberUtil.Add(planBeans.get(0).getReturnCapital(), planBeans.get(0).getReturnInterest()));
		}
		// ���������һ�ڵ���Ϣ
		if(dealAdvanceType==1){
			String interestMoney = interest.getInterestByDay(planBeans.get(0), acLnMstBean.getDueBal(), acLnMstBean);
			planBeans.get(0).setReturnInterest(interestMoney);
			planBeans.get(0).setTotal(BigNumberUtil.Add(planBeans.get(0).getReturnCapital(), planBeans.get(0).getReturnInterest()));
		}
		// ���������һ��
		if(dealAdvanceType==2){
			String interestMoney = interest.getInterestByDay(planBeans.get(0), acLnMstBean.getDueBal(), acLnMstBean);
			planBeans.get(0).setReturnInterest(interestMoney);
			planBeans.get(0).setTotal(BigNumberUtil.Add(planBeans.get(0).getReturnCapital(), planBeans.get(0).getReturnInterest()));
		}
		// ���ڲ��ֲ���ȡ��Ϣ����
		if(rComLastTerm){
			int planBeansSize = planBeans.size();
			PlanBean planBean = planBeans.get(planBeansSize-1);
			String endDate = planBean.getEndDate();
			planBean.setEndDate(acLnMstBean.getDueEndDate());
			// ���¼������һ�ڵ���Ϣ
			String interestMoney = interest.getInterestByDay(planBean, planBean.getDueBal(), acLnMstBean);
			planBeans.get(planBeansSize-1).setReturnInterest(interestMoney);
			planBeans.get(planBeansSize-1).setTotal(BigNumberUtil.Add(planBeans.get(planBeansSize-1).getReturnInterest(),planBeans.get(planBeansSize-1).getReturnCapital()));
			planBean.setEndDate(endDate);
		}
		
		
		return planBeans;
	}
	
	
	/**
	 * 
	 * ���������� ���Ĭ������ 
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-24 ����12:02:57
	 */
	private String defaultTerm(AcLnMstBean acLnMstBean){
		// ����ƻ���һ�ڵĿ�ʼ����(Ĭ�Ϻͽ�ݵĿ�ʼ������ͬ)
		String begDate = acLnMstBean.getDueBegDate();
		// ��ݵĿ�ʼ����
		String endDate = acLnMstBean.getDueEndDate();
		int[]  monthAndDays = DateUtil.getMonthsAndDays(begDate, endDate);
		if(monthAndDays[1]>0){
			return String.valueOf(monthAndDays[0]+1);
		}else{
			return String.valueOf(monthAndDays[0]);
		}
	} 
	
	
	/**
	 * 
	 * ����������  �����Ҫ������б�
	 * @return
	 * List<PlanBean>
	 * @author Ǭ֮��
	 * @date 2012-5-26 ����10:52:35
	 */
	public List<PlanBean>   getNormalPlanList(){
		return planDao.getNormalPlanList(SystemParm.SystemDate);
	}
	
	
	
	
	/**
	 * 
	 * ���������� ���ݷ��ü��㹫ʽ���������
	 * @param feeTypeBean
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-6-19 ����10:01:20
	 */
	private String getFee(AcLnMstBean acLnMstBean){
		String  feeValue = "0.00"; 
		
		feeValue = BigNumberUtil.Multiply(acLnMstBean.getDueAmt(), StringUtil.KillNull(acLnMstBean.getAccFee(), "0.00"));
		if(!StringUtil.equals(feeValue, "0.00")){
			feeValue = BigNumberUtil.Divide(feeValue, "100", 2, "1");
		}
		
		
		
		/**
		// ���ü��������
		FeeServices feeServices = SpringFactory.getBean("feeServices");
		// ���ݼ���ʵ��id��ü���ʵ��
		FeeTypeBean tempFeeTypeBean = new FeeTypeBean();
		tempFeeTypeBean.setFeeId(feeId);
		FeeTypeBean feeTypeBean = feeServices.getFeeTypeBean(tempFeeTypeBean);
		// �ù�ʽ����
		if(StringUtil.isNotEmpty(feeTypeBean.getFeeExpression()) ){
			
		}else{
		// �̶�ֵ 
			feeValue = feeTypeBean.getFeeValue();
		}
		**/
		return feeValue;
	}
	/**
	 * ��Լ��֤���
	 */
   private String  getPerfAmount(AcLnMstBean acLnMstBean){
	   
	   String  perfAmount = "0.00"; 
		
	   perfAmount = BigNumberUtil.Multiply(acLnMstBean.getDueAmt(), StringUtil.KillNull(acLnMstBean.getPerfAmountPerc(), "0.00"));
		if(!StringUtil.equals(perfAmount, "0.00")){
			perfAmount = BigNumberUtil.Divide(perfAmount, "100", 2, "1");
		}
		
		return perfAmount;
	}
	
	
	/**
	 * 
	 * ���������� �������»���ƻ���״̬��Ƿ���־ 
	 * @param state
	 * @param isDebt
	 * @param dueNo
	 * @param planBeans
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-22 ����09:55:37
	 */
	public void updatePlanList(String state, String isDebt,String dueNo, List<PlanBean> planBeans){
		planDao.updatePlanList(state, isDebt, dueNo, planBeans);
	}  
	
	/**
	 * 
	 * ���������� ���»���ƻ� 
	 * @param planBean
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-22 ����11:33:32
	 */
	public void updatePlanBean(PlanBean planBean){
		planDao.updatePlan(planBean);
	}
	
	
	public  PlanBean   getPlanBean(PlanBean parmPlanBean){
		return 	planDao.getPlanBean(parmPlanBean);
	}
	
	
	public List<PlanBean>  getPlanBeanList(PlanBean parmPlanBean) {
		return planDao.getPlanBeanList(parmPlanBean);
	}
	
	
	

}
