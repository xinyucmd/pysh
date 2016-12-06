/**
 * Copyright (C) DXHM 版权所有
 * @文件名 BaseAction.java
 * @包名 com.dx.common.action
 * @说明 TODO(描述文件做什么)
 * @作者   乾之轩
 * @时间 2012-5-7 下午12:39:51
 * @版本 V1.0
 */ 
package com.dx.common.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 类名： StringUtil
 *
 * 描述：系统级别的String工具类不允许个人随意添加方法,当需要使用某个功能时首先查看父类(org.apache.commons.lang3.StringUtils)是否已经提供,不要轻易地添加方法.
 * @author 乾之轩

 * @date 2012-5-12 上午08:45:26
 *
 *
 */
public class StringUtil  extends StringUtils{

	/**
	 * 方法描述： 将中文转化为Unicode
	 *
	 * @param str  需要转化的字符串

	 * @return
	 * String
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:46:04
	 */
	public static String Chines2Unicode(String str) {
		if (isEmpty(str)) {
			throw new NullPointerException("要转化的字符串不能为空");
		}
		char[] arChar = str.toCharArray();
		int iValue = 0;
		String uStr = "";
		for (int i = 0; i < arChar.length; i++) {
			iValue = (int) str.charAt(i);
			if (iValue <= 256) {
				uStr += "\\u00" + Integer.toHexString(iValue);
			} else {
				uStr += "\\u" + Integer.toHexString(iValue);
			}
		}
		return uStr;
	}



	/**
	 * 方法描述： 将Unicode转化为中文

	 * @param theString  要转化的字符串

	 * @return
	 * String
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:46:37
	 */

	public static String Unicode2Chines(String theString) {

		char aChar;

		int len = theString.length();

		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {

			aChar = theString.charAt(x++);

			if (aChar == '\\') {

				aChar = theString.charAt(x++);

				if (aChar == 'u') {

					int value = 0;

					for (int i = 0; i < 4; i++) {

						aChar = theString.charAt(x++);

						switch (aChar) {

						case '0':

						case '1':

						case '2':

						case '3':

						case '4':

						case '5':

						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';

					else if (aChar == 'n')

						aChar = '\n';

					else if (aChar == 'f')

						aChar = '\f';

					outBuffer.append(aChar);

				}

			} else

				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}


	/**
	 * 方法描述： 将数字转化为中文大写格式日 4 转化为 肆

	 *
	 * @param v
	 * @return String
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:47:16
	 */
	public static String numberToChinese(String v) {
		Double d = Double.parseDouble(v);
		DecimalFormat decimalformat = new DecimalFormat("############0.00");
		String s = decimalformat.format(d);
		int i = s.indexOf(".");
		if (s.substring(i).compareTo(".00") == 0)
			s = s.substring(0, i);
		String s1 = "";
		String as[] = new String[4];
		String as1[] = new String[4];
		String as2[] = new String[2];
		String s6 = "";
		int j = 0;
		int k = 0;
		as[0] = "";
		as[1] = "\312\260";
		as[2] = "\260\333";
		as[3] = "\307\252";
		as1[0] = "";
		as1[1] = "\315\362";
		as1[2] = "\322\332";
		as1[3] = "\315\362";
		as2[0] = "\267\326";
		as2[1] = "\275\307";
		if (s.compareTo("0") == 0 || s.compareTo("0.0") == 0 || s.compareTo("0.00") == 0) {
			s6 = "\301\343\324\252\325\373";
			return s6;
		}
		if (s.indexOf(".") > 0)
			s1 = s.substring(0, s.indexOf("."));
		else
			s1 = s;
		j = s1.length() % 4 == 0 ? s1.length() / 4 : s1.length() / 4 + 1;
		for (int i1 = j; i1 >= 1; i1--) {
			int l;
			if (i1 == j && s1.length() % 4 != 0)
				l = s1.length() % 4;
			else
				l = 4;
			String s3 = s1.substring(k, k + l);
			for (int j1 = 0; j1 < s3.length(); j1++)
				if (Integer.parseInt(s3.substring(j1, j1 + 1)) != 0)
					switch (Integer.parseInt(s3.substring(j1, j1 + 1))) {
					case 1: // '\001'
						s6 = s6 + "\322\274" + as[s3.length() - j1 - 1];
						break;

					case 2: // '\002'
						s6 = s6 + "\267\241" + as[s3.length() - j1 - 1];
						break;

					case 3: // '\003'
						s6 = s6 + "\310\376" + as[s3.length() - j1 - 1];
						break;

					case 4: // '\004'
						s6 = s6 + "\313\301" + as[s3.length() - j1 - 1];
						break;

					case 5: // '\005'
						s6 = s6 + "\316\351" + as[s3.length() - j1 - 1];
						break;

					case 6: // '\006'
						s6 = s6 + "\302\275" + as[s3.length() - j1 - 1];
						break;

					case 7: // '\007'
						s6 = s6 + "\306\342" + as[s3.length() - j1 - 1];
						break;

					case 8: // '\b'
						s6 = s6 + "\260\306" + as[s3.length() - j1 - 1];
						break;

					case 9: // '\t'
						s6 = s6 + "\276\301" + as[s3.length() - j1 - 1];
						break;
					}
				else if (j1 + 1 < s3.length() && s3.charAt(j1 + 1) != '0')
					s6 = s6 + "\301\343";

			k += l;
			if (i1 < j) {
				if (Integer.parseInt(s3.substring(s3.length() - 1, s3.length())) != 0 || Integer.parseInt(s3.substring(s3.length() - 2, s3.length() - 1)) != 0
						|| Integer.parseInt(s3.substring(s3.length() - 3, s3.length() - 2)) != 0 || Integer.parseInt(s3.substring(s3.length() - 4, s3.length() - 3)) != 0)
					s6 = s6 + as1[i1 - 1];
			} else {
				s6 = s6 + as1[i1 - 1];
			}
		}

		if (s6.length() > 0)
			s6 = s6 + "\324\252";
		if (s.indexOf(".") > 0) {
			String s5 = s.substring(s.indexOf(".") + 1);
			for (int k1 = 0; k1 < 2; k1++)
				if (Integer.parseInt(s5.substring(k1, k1 + 1)) != 0)
					switch (Integer.parseInt(s5.substring(k1, k1 + 1))) {
					case 1: // '\001'
						s6 = s6 + "\322\274" + as2[1 - k1];
						break;

					case 2: // '\002'
						s6 = s6 + "\267\241" + as2[1 - k1];
						break;

					case 3: // '\003'
						s6 = s6 + "\310\376" + as2[1 - k1];
						break;

					case 4: // '\004'
						s6 = s6 + "\313\301" + as2[1 - k1];
						break;

					case 5: // '\005'
						s6 = s6 + "\316\351" + as2[1 - k1];
						break;

					case 6: // '\006'
						s6 = s6 + "\302\275" + as2[1 - k1];
						break;

					case 7: // '\007'
						s6 = s6 + "\306\342" + as2[1 - k1];
						break;

					case 8: // '\b'
						s6 = s6 + "\260\306" + as2[1 - k1];
						break;

					case 9: // '\t'
						s6 = s6 + "\276\301" + as2[1 - k1];
						break;
					}
				else if (s6.length() > 0)
					s6 = s6 + "\301\343";

		} else {
			s6 = s6 + "\325\373";
		}
		if (s6.substring(s6.length() - 1).compareTo("\301\343") == 0)
			s6 = s6.substring(0, s6.length() - 1);
		return s6;
	}


	/**
	 * 方法描述： 将实体转化为xml
	 *
	 * @param obj
	 * @return String
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:47:42
	 */
	public String toXML(Object obj) {
		// 注意这里也可以写成new XStream()，实例--->XML，OK;XML--->实例，不OK
		XStream xstream = new XStream(new DomDriver());
		xstream.alias(obj.getClass().getSimpleName(), obj.getClass());
		String xml = xstream.toXML(obj);
		return xml;
	}

	/**
	 * 方法描述： 将 xml文件转化为实体

	 *
	 * @param <T>
	 * @param xml
	 * @param classType
	 * @return T
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:47:58
	 */
	@SuppressWarnings("unchecked")
	public <T> T toObject(String xml, Class<T> classType) {
		T object = null;
		XStream xstream = new XStream(new DomDriver());
		xstream.alias(classType.getSimpleName(), classType);
		object = (T) xstream.fromXML(xml);
		return object;
	}



	/**
	 * 方法描述： 金额格式化,截取的小数位数会进行四舍五入
	 * @param s           需要格式化的参数

	 * @param precision   小数点保留的位数
	 * @return
	 * String
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:48:46
	 */
	public static String numberFormat(String s, int precision) {
		Double d = Double.parseDouble(s);
		// 1$代表是从第几位开始格式化,","每3位数字之间用"，"分隔,".2"代表小数点后面取2位."f"代表是浮点类型

		return numberFormat(d, precision);
	}
	/**
	 * 方法描述： 金额格式化,截取的小数位数会进行四舍五入
	 *
	 * @param d           需要格式化的参数

	 * @param precision   小数点保留的位数
	 * @return
	 * String
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:49:48
	 */
	public static String numberFormat(Double d, int precision) {
		// %1$,.2f
		// 1$代表是从第几位开始格式化,","每3位数字之间用"，"分隔,".2"代表小数点后面取2位."f"代表是浮点类型

		String format = "%1$,." + precision + "f";
		return (String.format(format, d));
	}

	/**
	 * 方法描述：  处理空字符串
	 *
	 * @param s
	 * @return
	 * String
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:50:36
	 */
	public static String KillNull(String s) {
		return StringUtils.trimToEmpty(s);
	}


	/**
	 * 方法描述： 处理空字符串
	 *
	 * @param s   传入的字符串
	 * @param d   如果为空就替换为该字符串
	 * @return
	 * String
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:50:52
	 */
	public static String KillNull(String s, String d) {
		if (isEmpty(s)) {
			return d;
		} else {
			return s;
		}
	}
	
	/**
	 * 方法描述：获得主键
	 *
	 * @param _prefix   传入的字符串
	 * @return
	 * String
	 * @author 乾之轩

	 * @date 2012-5-12 上午08:50:52
	 */
	private static int _suffix = 0;
	public static synchronized String getPK(String _prefix) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date now = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strTmp = f.format(now);
		if (_suffix > 9){
			_suffix = 0;
		}
		strTmp = _prefix + strTmp + _suffix;
		_suffix++;
		return strTmp;
	}


}
