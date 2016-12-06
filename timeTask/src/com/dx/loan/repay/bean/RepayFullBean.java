/**
 * Copyright (C) DXHM 版权所有
 * 文件名： RepayFull.java
 * 包名： com.dx.loan.repay.bean
 * 说明：
 * @author sll
 * @date May 15, 2012 2:55:14 PM
 * @version V1.0
 */ 
package com.dx.loan.repay.bean;

import java.util.List;

/**
 * 类名： RepayFull
 * 描述： 还款详细类
 * @author sll
 * @date May 15, 2012 2:55:14 PM
 *
 *
 */
public class RepayFullBean {
	//客户名称
	private String cifName;
	//申请利率
	private String rateValue;
	//借据类
	private  DueBean dueBean;
	//已还信息集合:List[0] 存放已还本金,List[1] 存放已还利息,List[2] 存放实收利息,	List[3] 存放累计优惠
	private List<String> list;
	//欠款类
	private DebBean  debBean;
	//当期信息
	//private RatedayBean ratedayBean;
	//贷款主文件
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
