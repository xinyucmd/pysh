/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� NormRateBean.java
 * ������ com.dx.loan.normrate.bean
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-17 ����04:34:47
 * @version V1.0
 */ 
package com.dx.loan.normrate.bean;
/**
 * ������ NormRateBean
 * ������
 * @author Ǭ֮��
 * @date 2012-5-17 ����04:34:47
 *
 *
 */
public class NormRateBean {
	//���ʱ��
    private String rateNo;
    //��������
    private String rateType;
    //����ֵ
    private String rateValue;
    //������Ч��
    private String begDate;
    //����ʧЧ��
    private String endDate;
    //��������
    private String adjustDate;
    //��С����
    private String minTerm;
    //�������
    private String maxTerm;
    //������Сֵ
    private String minValue;
    //�������ֵ
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
