package com.dx.loan.repay.bean;

public class DueBean {
	//��ݺ�
    private String dueNo;
    //��ͬ��
    private String pactNo;
    //��ݽ��
    private String dueAmt;
    //��ʼ����
    private String begDate;
    //��������
    private String endDate;
    //�ͻ���
    private String cifNo;
    //��������
    private String extDays;
    //�۳���ѯ��
    private String consFee;
    //��ѯ�ѿ۳���ʽ
    private String consFeeType;
    //���״̬
    private String dueState;
    //չ�ڱ�־
    private String expFlag;
    //������
    private String bal;
    //����ǷϢ(�ϱ�)
    private String innerIntst;
    //����ǷϢ(�ϱ�)
    private String outerIntst;
    //�弶����(�ϱ�)
    private String fiveClass;
    //�ļ�����(�ϱ�)
    private String fourClass;
    //�Ƿ�̶��ջ���
    private String isFix;
    //�̶��ջ�����
    private String fixDate;
    // �Ƿ�ǿ�Ƽƻ�
    private String isForce;
    
    
    public String getIsForce() {
		return isForce;
	}
	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}
	public DueBean(){};
    public String getDueNo() {
        return dueNo;
    }

    public void setDueNo(String dueNo) {
        this.dueNo = dueNo;
    }

    public String getPactNo() {
        return pactNo;
    }

    public void setPactNo(String pactNo) {
        this.pactNo = pactNo;
    }

    public String getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(String dueAmt) {
        this.dueAmt = dueAmt;
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

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getExtDays() {
        return extDays;
    }

    public void setExtDays(String extDays) {
        this.extDays = extDays;
    }

    public String getConsFee() {
        return consFee;
    }

    public void setConsFee(String consFee) {
        this.consFee = consFee;
    }

    public String getConsFeeType() {
        return consFeeType;
    }

    public void setConsFeeType(String consFeeType) {
        this.consFeeType = consFeeType;
    }

    public String getDueState() {
        return dueState;
    }

    public void setDueState(String dueState) {
        this.dueState = dueState;
    }

    public String getExpFlag() {
        return expFlag;
    }

    public void setExpFlag(String expFlag) {
        this.expFlag = expFlag;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }

   

    public String getInnerIntst() {
        return innerIntst;
    }

    public void setInnerIntst(String innerIntst) {
        this.innerIntst = innerIntst;
    }

    public String getOuterIntst() {
        return outerIntst;
    }

    public void setOuterIntst(String outerIntst) {
        this.outerIntst = outerIntst;
    }

    public String getFiveClass() {
        return fiveClass;
    }

    public void setFiveClass(String fiveClass) {
        this.fiveClass = fiveClass;
    }

    public String getFourClass() {
        return fourClass;
    }

    public void setFourClass(String fourClass) {
        this.fourClass = fourClass;
    }

    public String getIsFix() {
        return isFix;
    }

    public void setIsFix(String isFix) {
        this.isFix = isFix;
    }

    public String getFixDate() {
        return fixDate;
    }

    public void setFixDate(String fixDate) {
        this.fixDate = fixDate;
    }
}