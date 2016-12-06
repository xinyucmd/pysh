/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PartParmBean.java
 * 包名： com.dx.loan.normrate.bean
 * 说明：
 * @author 乾之轩
 * @date 2012-5-21 下午02:35:40
 * @version V1.0
 */ 
package com.dx.loan.normrate.bean;

import hirondelle.date4j.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 类名： PartParmBean
 * 描述： 利息计算分段参数实体
 * @author 乾之轩
 * @date 2012-5-21 下午02:35:40
 *
 *
 */
public class PartParmBean implements Serializable ,Comparable<PartParmBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7600410943320728233L;
	// 利率调整后的生效点
	private String effectDate;
	// 调整后的执行利率(月利率)
	private String executeRate;
	// 调整后的逾期利率
	private String overRate;
	// 调整后的复利利率
	private String cmpRate;
	// 调整后的罚息利率
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
 * 方法描述：  根据生效日期进程排序 
 * @param partParmBean
 * @return
 * @author 乾之轩
 * @date 2012-5-21 下午03:18:59
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
