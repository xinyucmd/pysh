/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� RepayFull.java
 * ������ com.dx.loan.repay.bean
 * ˵����
 * @author sll
 * @date May 15, 2012 2:55:14 PM
 * @version V1.0
 */ 
package com.dx.loan.repay.bean;

import java.util.List;

/**
 * ������ RepayFull
 * ������ ������ϸ��
 * @author sll
 * @date May 15, 2012 2:55:14 PM
 *
 *
 */
public class RepayFullBean {
	//�ͻ�����
	private String cifName;
	//��������
	private String rateValue;
	//�����
	private  DueBean dueBean;
	//�ѻ���Ϣ����:List[0] ����ѻ�����,List[1] ����ѻ���Ϣ,List[2] ���ʵ����Ϣ,	List[3] ����ۼ��Ż�
	private List<String> list;
	//Ƿ����
	private DebBean  debBean;
	//������Ϣ
	//private RatedayBean ratedayBean;
	//�������ļ�
	private AcLnMstBean acLnMstBean;
	
	public RepayFullBean(){};
	
	public String getCifName() {
		return cifName;
	}
	public void setCifName(String cifName) {
		this.cifName = cifName;
	}
	public String getRateValue() {
		return rateValue;
	}
	public void setRateValue(String rateValue) {
		this.rateValue = rateValue;
	}
	public DueBean getDueBean() {
		return dueBean;
	}
	public void setDueBean(DueBean dueBean) {
		this.dueBean = dueBean;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public DebBean getDebBean() {
		return debBean;
	}
	public void setDebBean(DebBean debBean) {
		this.debBean = debBean;
	}

	public AcLnMstBean getAcLnMstBean() {
		return acLnMstBean;
	}
	public void setAcLnMstBean(AcLnMstBean acLnMstBean) {
		this.acLnMstBean = acLnMstBean;
	}
	
	
	
}
