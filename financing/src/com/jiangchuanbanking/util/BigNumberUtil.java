package com.jiangchuanbanking.util;

/**
 * Copyright (C) DXHM 版权所有

 * 文件名 BigNumberUtil.java
 * 包名 com.dx.common.util
 * 说明
 * 作者   乾之轩

 * 时间 2012-5-7 下午04:09:14
 * 版本 V1.0
 */

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类名 BigNumberUtil
 * 描述 系统中使用的精确计算,不允许随意修改.
 * 作者 乾之轩

 * 日期 2012-5-7 下午04:09:14 ========修改日志=======
 *
 */
/**
 * 类名： BigNumberUtil
 * 描述：

 * @author luanhaowei
 * @date 2012-5-12 上午08:39:08
 *
 *
 */
public class BigNumberUtil {
	/**
	 *
	 * 名称 Add
	 * 描述 精确计算加法
	 * @param num
	 * @param num1
	 * @return
	 * double
	 * @作者 乾之轩

	 * @时间 2012-5-7 下午04:14:58
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
	 * 方法描述： 加法
	 * @param s      数组
	 * @return
	 * String
	 * @author luanhaowei
	 * @date 2012-5-12 上午08:40:51
	 */
	public static String Add(String... s) {
		String temp = "0.00";
		for (String str : s) {
			str = StringUtil.KillNull(str, "0.00");
			str.trim();
			temp = add2String(temp, str);
		}
		return temp;
	}

	/**
	 * 名称 Subtract
	 *
	 * 描述 精确计算减法
	 * @param num
	 * @param num1
	 * @return
	 * @return String
	 * @author 乾之轩

	 *
	 */

	public static String Subtract(String num, String num1) {
		num = StringUtil.KillNull(num, "0.00");
		num1 = StringUtil.KillNull(num1, "0.00");
		num = num.trim();
		num1 = num1.trim();
		BigDecimal bigDecimal = new BigDecimal(num);
		BigDecimal bigDecimal1 = new BigDecimal(num1);
		return bigDecimal.subtract(bigDecimal1).toString();
	}

	/**
	 *
	 * 名称 Multiply
	 * 描述 精确计算乘法
	 * @param num
	 * @param num1
	 * @return
	 * double
	 * @author 乾之轩

	 *
	 */
	public static String Multiply(String num, String num1) {
		num = num.trim();
		num1 = num1.trim();
		if ("".equals(num) || num == null) {
			num = "0.00";
		}
		if ("".equals(num1) || num1 == null) {
			num1 = "0.00";
		}
		BigDecimal bigDecimal = new BigDecimal(num);
		BigDecimal bigDecimal1 = new BigDecimal(num1);
		return bigDecimal.multiply(bigDecimal1).toString();
	}

	/**
	 *
	 * 名称 Divide
	 * 描述 精确计算除法默认四射五入,默认保留2位小数

	 * @param num
	 * @param num1
	 * @return
	 * String
	 * @author 乾之轩

	 * 2012-5-7 下午04:40:34
	 */
	public static String Divide2(String num, String num1)  throws ArithmeticException{
		num = num.trim();
		num1 = num1.trim();
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
	public static String Divide3(String num, String num1)  throws ArithmeticException{
		num = num.trim();
		num1 = num1.trim();
		if ("".equals(num) || num == null) {
			num = "0.00";
		}
		if ("".equals(num1) || num1 == null) {
			num1 = "1.00";
		}

		BigDecimal bigDecimal = new BigDecimal(num);
		BigDecimal bigDecimal1 = new BigDecimal(num1);
		System.out.println(bigDecimal.divide(bigDecimal1, 3, BigDecimal.ROUND_HALF_UP).toString());
		return bigDecimal.divide(bigDecimal1, 3, BigDecimal.ROUND_HALF_UP).toString();
	}
                                                                          
	/**
	 *
	 * 名称 Divide5 描述 精确计算除法默认四舍五入,默认保留5位小数

	 *
	 * @param num
	 * @param num1
	 * @return String
	 * @author 乾之轩 2012-5-7 下午04:57:27
	 */
	public static String Divide5(String num, String num1) {
		num = num.trim();
		num1 = num1.trim();
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
	 * 方法描述： num 除数 num1 被除数 digits 保留小数位数 ,type 进位类型
	 * @param num
	 * @param num1
	 * @param digits
	 * @param type   1 四舍五入 2 进一法 3 舍尾法

	 * @return
	 * String
	 * @author luanhaowei
	 * @date 2012-5-12 上午08:39:11
	 */
	public static String Divide(String num, String num1, int digits, String type) {
		num = num.trim();
		num1 = num1.trim();
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
		System.out.println(Divide5("160000","360"));
	}

}
