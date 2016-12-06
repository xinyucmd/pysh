/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PlanServicesImpl.java
 * ������ com.dx.loan.plan.services.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-31 ����10:07:12
 * @version V1.0
 */ 
package com.dx.loan.plan.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ctc.wstx.util.DataUtil;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.StringUtil;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanParmBean;
import com.dx.loan.plan.dao.IPlanDao;
import com.dx.loan.plan.services.IPlanServices;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.services.impl.RepayService;

/**
 * ������ PlanServicesImpl
 * ������ ����ƻ�������
 * @author Ǭ֮��
 * @date 2012-5-31 ����10:07:12
 *
 *
 */
public class PlanServicesImpl implements IPlanServices {
	private IPlanDao planDao; 
	private RepayService repayService; 
	public void setPlanDao(IPlanDao planDao) {
		this.planDao = planDao;
	}
	
	public void setRepayService(RepayService repayService) {
		this.repayService = repayService;
	}
	/**
	 * 
	 * ���������� ���ɻ���ƻ� 
	 * @param dueNo
	 * @return
	 * @author Ǭ֮��
	 * @date 2012-5-31 ����10:07:34
	 */
	public List<PlanBean> createPlan(String dueNo,PlanParmBean planParmBean) {
		List<PlanBean> planBeans = null;
		// ��������ƻ�
		if(StringUtil.equals("0.00", planParmBean.getAdvaceAmt())){
			AcLnMstBean acLnMstBean = repayService.getAcLnMstBeanByDueNo(dueNo);
			PlanServices planServices = new PlanServices();
			planBeans = planServices.genePlan(acLnMstBean, planParmBean);
		}else{
		// ��ǰ����������ɻ���ƻ�
			AcLnMstBean acLnMstBean = repayService.getAcLnMstBeanByDueNo(dueNo);
			acLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(), planParmBean.getAdvaceAmt()));
			planBeans = advacePlan(dueNo,planParmBean,acLnMstBean);
		}
		return planBeans;
	}
	/**
	 * �༭����ƻ�
	 */
	public List<PlanBean> createPlan(List<PlanBean> planList,PlanParmBean planParmBean) {
		String dueNo = planList.get(0).getDueNo();
		AcLnMstBean acLnMstBean = repayService.getAcLnMstBeanByDueNo(dueNo);
		int size = planList.size();
		// ����С�ڻ���ڱ���������ƻ������Ļ���ƻ��ı����
		String dueBal = "0.00";
		// �����������֮ǰ�Ļ���ƻ�
		List<PlanBean> oldPlanList = new ArrayList<PlanBean>();
		// �жϻ���ƻ��ĵ�����ʽ 0 �������� 1 ��������ƻ��Ľ�������
		String adjustType = planParmBean.getAdjustType();
		// �ӵڼ��ڿ�ʼ����
		Integer adjustTerm =  Integer.parseInt(planParmBean.getTermNo());
		// �����ڻ���ƻ�
		PlanBean adjustPlanBean = planList.get(adjustTerm-1); 
		for(int i=0;i<adjustTerm;++i){
			PlanBean planBean  = planList.get(i);
			planBean.setTotal(BigNumberUtil.Add(planBean.getReturnCapital(),planBean.getReturnInterest()));
			oldPlanList.add(planBean);
			dueBal = BigNumberUtil.Add(dueBal,planBean.getReturnCapital());
		}
		// �������û���ƻ�����������
		planParmBean.setBeginDate(adjustPlanBean.getEndDate());
		// ���ô������ɻ���ƻ��Ŀ�ʼ����
		planParmBean.setBeginTerm(String.valueOf(adjustTerm+1));
		// ���û���Ҫ���ɶ����ڻ���ƻ�
		int terms = planList.size()-adjustTerm;
		planParmBean.setTerms(String.valueOf(terms));
		// ʣ�౾��
		String  remaDueBal = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), dueBal);
		// ����ÿ�ڵı���
		String returnCapital =  BigNumberUtil.Divide(remaDueBal, String.valueOf(terms), 2, "1");
		
		for(int i=adjustTerm+1;i<=size;++i){
			PlanBean planBean = planList.get(i-1);
			int[] monthAndDays = DateUtil.getMonthsAndDays(planBean.getBegDate(),planBean.getEndDate());
			// ��������,�������ʼ���(�����ڽ�������)
			if(monthAndDays[1]>0){
				// ����ƻ�����
				int days = monthAndDays[0]*30+monthAndDays[1];
				// ��������ת��Ϊ������
				String dayRate = BigNumberUtil.Divide(acLnMstBean.getLnRate(), "30", 10, "1");
				dayRate = BigNumberUtil.Divide(dayRate, "1000", 10, "1");
				// ʣ�౾�����������
				String interest = BigNumberUtil.Multiply(remaDueBal, String.valueOf(days));
				// ʣ�౾��������������������
				interest = BigNumberUtil.Multiply(interest, dayRate);
				interest = BigNumberUtil.Divide(interest, "1", 2, "1");
				planBean.setReturnCapital(returnCapital);
				planBean.setReturnInterest(interest);
				planBean.setTermNo(String.valueOf(i));
				planBean.setTotal(BigNumberUtil.Add(interest, returnCapital));
				oldPlanList.add(planBean);	
				
				remaDueBal = BigNumberUtil.Subtract(remaDueBal, returnCapital);
			}else{
				// ����ʹ��������	
				// ʣ�౾�����������
				String interest = BigNumberUtil.Multiply(remaDueBal, String.valueOf(monthAndDays[0]));
				// ʣ�౾����������³���������
				interest = BigNumberUtil.Multiply(interest,acLnMstBean.getLnRate());
				interest  = BigNumberUtil.Divide(interest , "1000",2, "1");
				planBean.setReturnCapital(returnCapital);
				planBean.setReturnInterest(interest);
				planBean.setTermNo(String.valueOf(i));
				planBean.setTotal(BigNumberUtil.Add(interest, returnCapital));
				oldPlanList.add(planBean);	
				remaDueBal = BigNumberUtil.Subtract(remaDueBal, returnCapital);
			}
		}
		Collections.sort(oldPlanList);
		return oldPlanList;
	}
	
	/**
	 * �������ڽ��л���ƻ��ĵ���
	 * @param planList
	 * @param planParmBean
	 * @return
	 */
	public List<PlanBean> createPlan1(List<PlanBean> planList,PlanParmBean planParmBean) {
		String dueNo = planList.get(0).getDueNo();
		AcLnMstBean acLnMstBean = repayService.getAcLnMstBeanByDueNo(dueNo);
		int size = planList.size();
		// ����С�ڻ���ڱ���������ƻ������Ļ���ƻ��ı����
		String dueBal = "0.00";
		// �����������֮ǰ�Ļ���ƻ�
		List<PlanBean> oldPlanList = new ArrayList<PlanBean>();
		// �жϻ���ƻ��ĵ�����ʽ 0 �������� 1 ��������ƻ��Ľ�������
		String adjustType = planParmBean.getAdjustType();
		// �ӵڼ��ڿ�ʼ����
		Integer adjustTerm =  Integer.parseInt(planParmBean.getTermNo());
		// �����ڻ���ƻ�
		PlanBean adjustPlanBean = planList.get(adjustTerm-1); 
		
		// �������ڵĽ������� 
		String adjustEndDate = "";
		String tempDueBal = acLnMstBean.getDueBal();
		for(int i=0;i<adjustTerm;++i){
			PlanBean planBean  = planList.get(i);
			int[] monthAndDays = DateUtil.getMonthsAndDays(planBean.getBegDate(), planBean.getEndDate());
			if(monthAndDays[1]>0){
				// ��������ʹ�������ʼ���
				int days = monthAndDays[1]*30+monthAndDays[1];
				// ��������ת��Ϊ������
				String dayRate = BigNumberUtil.Divide(acLnMstBean.getLnRate(), "30", 10, "1");
				dayRate = BigNumberUtil.Divide(dayRate, "1000", 10, "1");
				// ʣ�౾�����������
				String interest = BigNumberUtil.Multiply(tempDueBal, String.valueOf(days));
				// ʣ�౾��������������������
				interest = BigNumberUtil.Multiply(interest, dayRate);
				interest = BigNumberUtil.Divide(interest, "1", 2, "1");
				planBean.setReturnInterest(interest);
				planBean.setTermNo(String.valueOf(i));
				planBean.setTotal(BigNumberUtil.Add(interest, planBean.getReturnCapital()));
				oldPlanList.add(planBean);	
				tempDueBal = BigNumberUtil.Subtract(tempDueBal, planBean.getReturnCapital());
				adjustEndDate = planBean.getEndDate();
				dueBal = BigNumberUtil.Add(dueBal,planBean.getReturnCapital());
			}else{
				// ����ʹ�������ʼ���
				// ʣ�౾�����������
				String interest = BigNumberUtil.Multiply(tempDueBal, String.valueOf(monthAndDays[0]));
				// ʣ�౾����������³���������
				interest = BigNumberUtil.Multiply(interest,acLnMstBean.getLnRate());
				interest  = BigNumberUtil.Divide(interest , "1000",2, "1");
				planBean.setReturnInterest(interest);
				planBean.setTermNo(String.valueOf(i));
				planBean.setTotal(BigNumberUtil.Add(interest, planBean.getReturnCapital()));
				oldPlanList.add(planBean);	
				tempDueBal = BigNumberUtil.Subtract(tempDueBal, planBean.getReturnCapital());
				adjustEndDate = planBean.getEndDate();
				dueBal = BigNumberUtil.Add(dueBal,planBean.getReturnCapital());
			}
		}
		// �ӵ����ڵĽ������ڵ���ݽ������ڻ�ʣ�����ڻ���ƻ�
		int[] monthAndDays = DateUtil.getMonthsAndDays(adjustEndDate, acLnMstBean.getDueEndDate());
		// ʣ�������
		int terms = monthAndDays[0];
		if(monthAndDays[1]>0){
			terms = terms+1;
		}
		// ʣ�౾��
		String  remaDueBal = BigNumberUtil.Subtract(acLnMstBean.getDueBal(), dueBal);
		int termNo = adjustTerm+1;
		for(int i=0;i<terms;++i){
			PlanBean planBean = planList.get(termNo);
			planBean.setTermNo(String.valueOf(termNo));
			planBean.setBegDate(adjustEndDate);
			String endDate = DateUtil.addByMonDay(adjustEndDate,1, 0, DateUtil.DATE_FORMAT_);
			planBean.setEndDate(endDate);
			String interest =  BigNumberUtil.Multiply(remaDueBal, acLnMstBean.getLnRate());
			interest =  BigNumberUtil.Divide(interest , "1000", 2, "1");
			planBean.setTotal(BigNumberUtil.Add(interest,planBean.getReturnCapital())); 
			oldPlanList.add(planBean);
			adjustEndDate = endDate;
			termNo = termNo+1;
		}
		
		return oldPlanList;
	}
	
	
	/**
	 * 
	 * ���������� ���滹��ƻ� 
	 * @param planBeans
	 * @author Ǭ֮��
	 * @date 2012-6-5 ����07:49:57
	 */
	public void savePlanList(List<PlanBean> planBeans) {
		planDao.savePlanList(planBeans);
	}
	
	/**
	 * 
	 * ���������� ��ǰ����������ɻ���ƻ�
	 * @param dueNo
	 * @param planParmBean
	 * @param acLnMstBean
	 * @return
	 * List<PlanBean>
	 * @author Ǭ֮��
	 * @date 2012-6-18 ����10:39:43
	 */
	private List<PlanBean> advacePlan(String dueNo,PlanParmBean planParmBean,AcLnMstBean acLnMstBean){
		// ɾ��δ����Ļ���ƻ�
		PlanBean planBean = new PlanBean();
		planBean.setDueNo(dueNo);
		planBean.setState("0");
		// �������µĻ���ƻ�
		planDao.delPlan(planBean);
		// ���������µĻ���ƻ�
		PlanServices planServices = new PlanServices();
		return planServices.genePlan(acLnMstBean, planParmBean);
	}
}
