/**
 * Copyright (C) DXHM ��Ȩ����

 * �ļ��� BigNumberUtil.java
 * ���� com.dx.common.util
 * ˵��
 * ����   Ǭ֮��

 * ʱ�� 2012-5-7 ����04:09:14
 * �汾 V1.0
 */
package com.dx.common.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���� BigNumberUtil
 * ���� ϵͳ��ʹ�õľ�ȷ����,�����������޸�.
 * ���� Ǭ֮��

 * ���� 2012-5-7 ����04:09:14 ========�޸���־=======
 *
 */
/**
 * ������ BigNumberUtil
 * ������

 * @author luanhaowei
 * @date 2012-5-12 ����08:39:08
 *
 *
 */
public class BigNumberUtil {
	/**
	 *
	 * ���� Add
	 * ���� ��ȷ����ӷ�
	 * @param num
	 * @param num1
	 * @return
	 * double
	 * @���� Ǭ֮��

	 * @ʱ�� 2012-5-7 ����04:14:58
	 */

	public static String add2String(String num, String num1) {
		Log log = LogFactory.getLog(BigNumberUtil.class);
		String s = "0.00";
		num = num.trim();//
		num1 = num1.trim();
	
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(num1);
	    num1 = m.replaceAll("");
	    num = StringUtil.KillNull(num, "0.00");
		num1 = StringUtil.KillNull(num1, "0.00");

		try{
			BigDecimal bigDecimal = new BigDecimal(num);
			BigDecimal bigDecimal1 = new BigDecimal(num1);
			s = bigDecimal.add(bigDecimal1).toString();
		}catch(Exception e){
			log.error(num+"+"+num1);
		}
		return s;
	}


	/**
	 * ���������� �ӷ�
	 * @param s      ����
	 * @return
	 * String
	 * @author luanhaowei
	 * @date 2012-5-12 ����08:40:51
	 */
	public static String Add(String... s) {
		String temp = "0.00";
		for (String str : s) {
			str = StringUtil.KillNull(str, "0.00");
			temp = add2String(temp, str);
		}
		return temp;
	}

	/**
	 * ���� Subtract
	 *
	 * ���� ��ȷ�������
	 * @param num
	 * @param num1
	 * @return
	 * @return String
	 * @author Ǭ֮��

	 *
	 */

	public static String Subtract(String num, String num1) {
		num = StringUtil.KillNull(num, "0.00");
		num1 = StringUtil.KillNull(num1, "0.00");
		BigDecimal bigDecimal = new BigDecimal(num);
		BigDecimal bigDecimal1 = new BigDecimal(num1);
		return bigDecimal.subtract(bigDecimal1).toString();
	}

	/**
	 *
	 * ���� Multiply
	 * ���� ��ȷ����˷�
	 * @param num
	 * @param num1
	 * @return
	 * double
	 * @author Ǭ֮��

	 *
	 */
	public static String Multiply(String num, String num1) {
		if ("".equals(num) || num == null || "0.00".equals(num)|| "0.0".equals(num)) {
			num = "0.00";
		}
		if ("".equals(num1) || num1 == null || "0.00".equals(num1)|| "0.0".equals(num1)) {
			num1 = "0.00";
		}
		BigDecimal bigDecimal = new BigDecimal(num);
		BigDecimal bigDecimal1 = new BigDecimal(num1);
		return bigDecimal.multiply(bigDecimal1).toString();
	}

	/**
	 *
	 * ���� Divide
	 * ���� ��ȷ�������Ĭ����������,Ĭ�ϱ���2λС��

	 * @param num
	 * @param num1
	 * @return
	 * String
	 * @author Ǭ֮��

	 * 2012-5-7 ����04:40:34
	 */
	public static String Divide2(String num, String num1) {
		if ("".equals(num) || num == null) {
			num = "0.00";
		}
		if ("".equals(num1) || num1 == null) {
			num1 = "1.00";
		}
		BigDecimal bigDecimal = new BigDecimal(num);
		BigDecimal bigDecimal1 = new BigDecimal(num1);
		return bigDecimal.divide(bigDecimal1, 2, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 *
	 * ���� Divide5 ���� ��ȷ�������Ĭ����������,Ĭ�ϱ���5λС��

	 *
	 * @param num
	 * @param num1
	 * @return String
	 * @author Ǭ֮�� 2012-5-7 ����04:57:27
	 */
	public static String Divide5(String num, String num1) {
		if ("".equals(num) || num == null) {
			num = "0.00";
		}
		if ("".equals(num1) || num1 == null) {
			num1 = "1.00";
		}
		BigDecimal bigDecimal = new BigDecimal(num);
		BigDecimal bigDecimal1 = new BigDecimal(num1);
		return bigDecimal.divide(bigDecimal1, 5, BigDecimal.ROUND_HALF_UP)
				.toString();

	}


	/**
	 * ���������� num ���� num1 ������ digits ����С��λ�� ,type ��λ����
	 * @param num
	 * @param num1
	 * @param digits
	 * @param type   1 �������� 2 ��һ�� 3 ��β��

	 * @return
	 * String
	 * @author luanhaowei
	 * @date 2012-5-12 ����08:39:11
	 */
	public static String Divide(String num, String num1, int digits, String type) {
		String value = "0";
		if ("".equals(num) || num == null) {
			num = "0.00";
		}
		if ("".equals(num1) || num1 == null) {
			num1 = "1.00";
		}
		BigDecimal bigDecimal = new BigDecimal(num);
		BigDecimal bigDecimal1 = new BigDecimal(num1);
		if (StringUtil.equals("1", type)) {
			value = bigDecimal.divide(bigDecimal1, digits, BigDecimal.ROUND_HALF_UP).toString();
		} else if (StringUtil.equals("2", type)) {
			value = bigDecimal.divide(bigDecimal1, digits, BigDecimal.ROUND_UP).toString();
		} else {
			value = bigDecimal.divide(bigDecimal1, digits, BigDecimal.ROUND_DOWN).toString();
		}
		return value;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(Divide("129","3.1",0,"3"));
		System.out.println(add2String("0.00", "1000.11"));
	}

}
