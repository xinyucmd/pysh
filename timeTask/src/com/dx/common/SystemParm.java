/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� SystemParm.java
 * ������ com.dx.common
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-16 ����09:34:36
 * @version V1.0
 */ 
package com.dx.common;

import com.dx.back.systemdate.services.impl.SystemDateServices;
import com.dx.common.util.SpringFactory;

/**
 * ������ SystemParm
 * ������ ϵͳ����
 * @author Ǭ֮��
 * @date 2012-5-16 ����09:34:36
 *
 *
 */
public class SystemParm {
	// ϵͳ����
	 public static String SystemDate="";
	// ��ǰ����ʱ��Ϣ���㵽��ǰ���ڻ��Ǽ��㵽����,0 ���㵽��ǰ����,1���㵽��ǰ��������
	public static String RateEndDay="0";
	// һ�������
	public static int DaysOfYear = 365;
	// ���ʵ�����(������Ч)�Ĳ��� 1 ������Ч 2 ������Ч
	public static int EffectiveType = 1;
	
	/**�ſ�֮ǰ�����ʵ����Ĵ���ʽ**/
	/** 0  ���ݼ�Ϣ��ʽ,���ȼ������Ч��.Ȼ����зֶμ���.
	*	��:5��5��Ϊ��ͬ��ʼ���� 5��7�Ž������ʵ������Ҽ�Ϣ��ʽΪ����,5��10 �Ž��зſ�,��ô5.7---6.7 ʹ��������6.7����ݽ�����ʹ��������	
	*	1 ʹ��������
	*	�ӷſ��տ�ʼֱ��ʹ��������
	**/
	public static int BeforeRateAdjust = 1;
	/**
	 * ���ƻ�ÿ�ڵ������������ַ�ʽ����  0 (Ĭ��)��1 
	 */
	public static int  PLAN_DAY_TYPE=0;  
	/**
	 * 0 ����ƻ��Ǳ����� ��:2012-05-25 --2012-06-25   1 ���ǰ�հ뿪2012-05-25 --2012-06-24 
	 */
	public static int PLAN_END=0;
	/**
	 *  ���һ���������һ���Ƿ������ڼ��� 0 ���� 1��
	 */
	public static int LAST_TERM_FULL=1;
	/**
	 * ���һ���Ƿ����� 
	 * 0 ������(��ʼ����Ϊ2012-05-20  ��������Ϊ2013-05-19)
	 * 1�ǿ�����(��ʼ����Ϊ2012-05-20  ��������Ϊ2013-05-20)           
	 */
	public static int  LAST_TERM_OPEN=1;
	/**
	 * ������Ϣʱʹ�õ��������� 
	 * 0 ������
	 * 1 ������
	 */
	public static int  USE_RATE_TYPE=1;
	/**
	 * ��ѡ������,���Ҳ������µĴ���
	 * 0  ���²���ʹ�������ʼ���,�����²���ʹ�������ʼ���
	 * 1 ʹ�������ʼ���
	 */
	public static int  RATE_NOT_FULL=0;
	
	
	/**
	 * ���������ʼ���ʱһ���µ�����
	 * 0 ʵ������
	 * 1 ���°�30�����
	 */
	public static int MONTH_OF_DAYS=0;
	
	/**
	 * �Ƿ��й̶���������
	 * 0 û�� 
	 * 1 ��
	 */
	public static int IS_FIX_DAY=1;
	
	/**
	 * �̶��������ڵ�����
	 * 0 ��������
	 * 1 �趨����
	 */
	public static int FIX_DAY_TYPE;
	/**
	 * �Ƿ�����  ֻ��ѡ��̶������պ�����ò���������
	 * 0 ������
	 * 1 ����
	 */
	public static int IS_DELAY=1;
	/**
	 * �̶������ջ���ʱ��ȡ���ֵĴ���ʽ
	 * 0  ��һ����ȡ
	 * 1 ��������һ���б���
	 * 2 ������Ϊһ���ޱ���
	 */
	public static int  DEAL_ADVANCE_TYPE=2;
	/**
	 * �̶�������ʱԤ�Ƚ�ȡ������(����)�Ľ�������ʱ�ڱ��»�����һ����
	 * 0 ����
	 * 1 ��һ�� 
	 */
	public static int ADVANCE_END_TYPE=0;
	
	/**
	 * �ӽ����յ����������Ƿ���ȡ��Ϣ
	 * 0 ����ȡ
	 * 1 ��ȡ
	 */
	public static int GATHER_DELAY_RATE = 0;
	
	/**
	 * �ж��Ƿ�ǿ�Ƽƻ�
	 * 0  ����
	 * 1 ��
	 */
	public static int IS_FORCE = 1 ;
	/**
	 * �Ƿ���ȡ�˻������
	 * 0  ����ȡ
	 * 1  ��ȡ
	 */
	public static int ACCOUNT_FEE = 1; 
	
	
	
    /***************���������ʼ***************/    
	/**
	 * �Ƿ������Ż�
	 * 0 �����Ż�
	 * 1 ���Ż�
	 */
	public static int  IS_PRIVILEGE=1;
	
	
	/***************�����������***************/    
	
	
	
	
	
	
	
	/*********************ϵͳ������ʼ*****************/
	
	/**
	 *  ��Ϣ��ʽ����˵��
	 */

	/** �̶����� */
	public static final String RATE_WAY_FIX = "1";

	/** ���µ��� */
	public static final String RATE_WAY_MONTH = "2";

	/** ���¶��յ��� */
	public static final String RATE_WAY_MONTH_DAY = "3";

	/** �������� */
	public static final String RATE_WAY_SEASON = "4";

	/** �������յ��� */
	public static final String RATE_WAY_SEASON_DAY = "5";

	/** ������� */
	public static final String RATE_WAY_YEAR = "6";

	/** ������¶��յ��� */
	public static final String RATE_WAY_YEAR_MONTH_DAY = "7";

	/** �������� */
	public static final String RATE_WAY_IMMEDIATELY = "8";

	/** �̶���Ϣ */
	public static final String RATE_WAY_FIX_INTEREST = "9";
	
	
	
	
	/**��������**/
	// ����������
	public static final String RATE_DAY="1";
	// ����������
	public static final String RATE_MONTH="2";
	// ����������
	public static final String RATE_YEAR="3";
	
	
	
	/**
	 *  ���ʽ����˵��
	 */

	/** �ȶϢ */
	public static final String RETURNMETHOD_INTEREST= "4";

	/** �ȶ�� */
	public static final String RETURNMETHOD_PRINCIPAL = "1";

	/** ���汾�� */
	public static final String RETURNMETHOD_PROFITSCLEAR = "3";

	/** ���ڻ������½�Ϣ */
	public static final String RETURNMETHOD_MONTH = "2";

	/** ���ڻ����𰴼���Ϣ */
	public static final String RETURNMETHOD_SEASON = "5";

	/** ���ƻ� */
	public static final String RETURNMETHOD_PLAN = "B";
	
	
	/**
	 * ��ͬ״̬
	 */
	// ����¼״̬
	public static final String PACT_STS_ADDIT="0";
	// ��¼��ɻ�û�зſ�
	public static final String PACT_STS_PUT="1";
	// ��ɷſû�����ɻ���ƻ�
	public static final String PACT_STS_PLAN="2";
	
	
	
	/*********************ϵͳ��������*****************/
	
	

	

}
