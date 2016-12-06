/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PartParmBean.java
 * ������ com.dx.loan.normrate.bean
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-21 ����02:35:40
 * @version V1.0
 */ 
package com.dx.loan.normrate.bean;

import hirondelle.date4j.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * ������ PartParmBean
 * ������ ��Ϣ����ֶβ���ʵ��
 * @author Ǭ֮��
 * @date 2012-5-21 ����02:35:40
 *
 *
 */
public class PartParmBean implements Serializable ,Comparable<PartParmBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7600410943320728233L;
	// ���ʵ��������Ч��
	private String effectDate;
	// �������ִ������(������)
	private String executeRate;
	// ���������������
	private String overRate;
	// ������ĸ�������
	private String cmpRate;
	// ������ķ�Ϣ����
	private String finRate;
	
	
	public String getOverRate() {
		return overRate;
	}


	public void setOverRate(String overRate) {
		this.overRate = overRate;
	}


	public String getCmpRate() {
		return cmpRate;
	}


	public void setCmpRate(String cmpRate) {
		this.cmpRate = cmpRate;
	}


	public String getFinRate() {
		return finRate;
	}


	public void setFinRate(String finRate) {
		this.finRate = finRate;
	}


	public PartParmBean(){};
	
	
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	public String getExecuteRate() {
		return executeRate;
	}
	public void setExecuteRate(String executeRate) {
		this.executeRate = executeRate;
	}


/**
 * 
 * ����������  ������Ч���ڽ������� 
 * @param partParmBean
 * @return
 * @author Ǭ֮��
 * @date 2012-5-21 ����03:18:59
 */
public int compareTo(PartParmBean partParmBean) {
	DateTime dateTime1 = new DateTime(this.getEffectDate());
	DateTime dateTime2 = new DateTime(partParmBean.getEffectDate());
	if(dateTime1.gt(dateTime2)){
		return 1;
	}else{
		return 0;
	}
}


public static void main(String[] args) {
	List<PartParmBean> partParmBeans = new ArrayList<PartParmBean>();
	PartParmBean  partParmBean1 = new PartParmBean();
	partParmBean1.setEffectDate("2012-05-22");
	partParmBean1.setExecuteRate("11");
	PartParmBean  partParmBean2 = new PartParmBean();
	partParmBean2.setEffectDate("2012-05-27");
	partParmBean2.setExecuteRate("22");
	
	partParmBeans.add(partParmBean2);
	partParmBeans.add(partParmBean1);
	Collections.sort(partParmBeans);
	for(PartParmBean parmBean:partParmBeans){
		System.out.println(parmBean.getExecuteRate());
	}
	
	
}

	
	
	

}
