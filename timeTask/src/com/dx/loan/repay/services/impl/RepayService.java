/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ��� RepayService.java
 * ���� com.dx.loan.repay.services.impl
 * ˵����
 * @author sll
 * @date May 10, 2012 11:54:11 AM
 * @version V1.0
 */ 
package com.dx.loan.repay.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.dx.common.util.BigNumberUtil;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.dao.IRepayDao;

/**
 * 
 * ������ RepayService
 * ������ ���������
 * @author Ǭ֮��
 * @date 2012-5-31 ����10:11:10
 * 
 *
 */
public class RepayService {
	public RepayService(){};
	private IRepayDao repayDao;
	
	public IRepayDao getRepayDao() {
		return repayDao;
	}

	public void setRepayDao(IRepayDao repayDao) {
		this.repayDao = repayDao;
	}
	/**
	 * 
	 * ���������� ���ݽ�ݺŻ�ô������ļ� 
	 * @param dueNo
	 * @return
	 * AcLnMstBean
	 * @author Ǭ֮��
	 * @date 2012-5-31 ����10:12:53
	 */
	public AcLnMstBean getAcLnMstBeanByDueNo(String dueNo){
		return repayDao.getAcLnMstBeanByDueNo(dueNo);
	}
	
	
	/**
	 * 
	 * ���������� 
	 * @param dueNo
	 * @return
	 * List<String>
	 * @author Ǭ֮��
	 * @date 2012-5-31 ����10:11:45
	 */
	public List<String> getFinshInf(String dueNo) {
		List<String> list=new ArrayList<String>();
		RepayBean repayBean =  new RepayBean();
		repayBean.setDueNo(dueNo);
		List<RepayBean> hisList= repayDao.getRepayBeans(repayBean);
		double returnCapital=0.0; 
		double returnInterest=0.0;
		double trueInterest=0.0;  
		double discAmount=0.0;    		for (int i = 0; i < hisList.size(); i++) {
			RepayBean hisBean=hisList.get(i);
			if(hisBean.getReturnCapital()==null || "".equals(hisBean.getReturnCapital())){
				hisBean.setReturnCapital("0");
			}
			if(hisBean.getReturnInterest()==null || "".equals(hisBean.getReturnInterest())){
				hisBean.setReturnInterest("0");
			}
			if(hisBean.getPrivilege()==null || "".equals(hisBean.getPrivilege())){
				hisBean.setPrivilege("0");
			}
			returnCapital=returnCapital+Double.valueOf(hisBean.getReturnCapital());
			returnInterest=returnInterest+Double.valueOf(hisBean.getReturnInterest());
			discAmount=discAmount+Double.valueOf(hisBean.getPrivilege());
			trueInterest=trueInterest+(Double.valueOf(hisBean.getReturnInterest())-Double.valueOf(hisBean.getPrivilege()));
		}
		list.add(returnCapital+"");
		list.add(returnInterest+"");
		list.add(trueInterest+"");
		list.add(discAmount+"");
		return list;
	}
/**
 * ���ĳ�ڻ���ƻ����ۻ�������Ϣ
 * list[0] �ۼƻ����
 * list[1] �ۼƻ�����Ϣ
 * list[2] �ۼƻ���������Ϣ
 * list[3] �ۼƻ������Ϣ
 * list[4] �ۼƻ����˻������
 * @param dueNo
 * @param termNo
 * @return
 */
	public List<String>   getHasRepay(String dueNo,String termNo){
		List<String> hasRepayList = new ArrayList<String>();
		String capital = "0.00";
		String interest = "0.00";
		String overInterest = "0.00";
		String cmpdInterest = "0.00";
		String accFee = "0.00"; 
		RepayBean parmRepayBean = new RepayBean();
		parmRepayBean.setDueNo(dueNo);
		parmRepayBean.setTermNo(termNo);
		List<RepayBean> repayBeans = repayDao.getRepayBeans(parmRepayBean);
		for(RepayBean repayBean:repayBeans){
			capital = BigNumberUtil.Add(capital,repayBean.getReturnCapital());
			interest = BigNumberUtil.Add(interest,repayBean.getReturnInterest());
			cmpdInterest = BigNumberUtil.Add(cmpdInterest,repayBean.getCmpdInterest());
			overInterest = BigNumberUtil.Add(overInterest,repayBean.getOverInterest());
			accFee = BigNumberUtil.Add(accFee,repayBean.getOtherFee());
		}
		hasRepayList.add(capital);
		hasRepayList.add(interest);
		hasRepayList.add(overInterest);
		hasRepayList.add(cmpdInterest);
		hasRepayList.add(accFee);
		return hasRepayList;
	}
	
	
	
	
	public List<RepayBean> getRepayBeans(String dueNo){
		RepayBean parmRepayBean = new RepayBean();
		parmRepayBean.setDueNo(dueNo);
		return repayDao.getRepayBeans(parmRepayBean);
	}
	
	
	
}
