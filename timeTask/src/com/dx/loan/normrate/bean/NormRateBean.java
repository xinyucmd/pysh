/**
 * Copyright (C) DXHM 版权所有
 * 文件名： NormRateBean.java
 * 包名： com.dx.loan.normrate.bean
 * 说明：
 * @author 乾之轩
 * @date 2012-5-17 下午04:34:47
 * @version V1.0
 */ 
package com.dx.loan.normrate.bean;
/**
 * 类名： NormRateBean
 * 描述：
 * @author 乾之轩
 * @date 2012-5-17 下午04:34:47
 *
 *
 */
public class NormRateBean {
	//利率编号
    private String rateNo;
    //利率类型
    private String rateType;
    //利率值
    private String rateValue;
    //利率生效日
    private String begDate;
    //利率失效日
    private String endDate;
    //调整日期
    private String adjustDate;
    //最小期数
    private String minTerm;
    //最大期数
    private String maxTerm;
    //利率最小值
    private String minValue;
    //利率最大值
    private String maxValue;

    public NormRateBean(){};
    
    public String getRateNo() {
        return rateNo;
    }

    public void setRateNo(String rateNo) {
        this.rateNo = rateNo;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAdjustDate() {
        return adjustDate;
    }

    public void setAdjustDate(String adjustDate) {
        this.adjustDate = adjustDate;
    }

    public String getMinTerm() {
        return minTerm;
    }

    public void setMinTerm(String minTerm) {
        this.minTerm = minTerm;
    }

    public String getMaxTerm() {
        return maxTerm;
    }

    public void setMaxTerm(String maxTerm) {
        this.maxTerm = maxTerm;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

}
