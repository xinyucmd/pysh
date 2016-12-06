/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� BaseAction.java
 * @���� com.dx.common.action
 * @˵�� TODO(�����ļ���ʲô)
 * @����   Ǭ֮��
 * @ʱ�� 2012-5-7 ����12:39:51
 * @�汾 V1.0
 */ 
package com.jiangchuanbanking.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * ���� StringUtil
 *
 * ������ϵͳ�����String�����಻�������������ӷ���,����Ҫʹ��ĳ������ʱ���Ȳ鿴����(org.apache.commons.lang3.StringUtils)�Ƿ��Ѿ��ṩ,��Ҫ���׵���ӷ���.
 * @author Ǭ֮��

 * @date 2012-5-12 ����08:45:26
 *
 *
 */
public class StringUtil  extends StringUtils{
	
	
	public static String PadLeft(String s, String pad, int n) {
		int l = 0;
		if (s != null && !s.equals("")) {
			l = s.length();
		}
		for (int i = l;i<=n;i++){
			s = pad + s;
		}
		return s;
	}
	
	public static String PadRight(String s, String pad, int n) {
		int l = 0;
		if (s != null && !s.equals("")) {
			l = s.length();
		}
		for (int i = l;i<n;i++){
			s = s + pad;
		}
		return s;
	}
	

	/**
	 * ���������� ������ת��ΪUnicode
	 *
	 * @param str  ��Ҫת�����ַ�

	 * @return
	 * String
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:46:04
	 */
	public static String Chines2Unicode(String str) {
		if (isEmpty(str)) {
			throw new NullPointerException("Ҫת�����ַ���Ϊ��");
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
	 * ���������� ��Unicodeת��Ϊ����

	 * @param theString  Ҫת�����ַ�

	 * @return
	 * String
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:46:37
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
	 * ���������� ������ת��Ϊ���Ĵ�д��ʽ�� 4 ת��Ϊ ��

	 *
	 * @param v
	 * @return String
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:47:16
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
	 * ���������� ��ʵ��ת��Ϊxml
	 *
	 * @param obj
	 * @return String
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:47:42
	 */
	public String toXML(Object obj) {
		// ע������Ҳ����д��new XStream()��ʵ��--->XML��OK;XML--->ʵ��OK
		XStream xstream = new XStream(new DomDriver());
		xstream.alias(obj.getClass().getSimpleName(), obj.getClass());
		String xml = xstream.toXML(obj);
		return xml;
	}

	/**
	 * ���������� �� xml�ļ�ת��Ϊʵ��

	 *
	 * @param <T>
	 * @param xml
	 * @param classType
	 * @return T
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:47:58
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
	 * ���������� ����ʽ��,��ȡ��С��λ��������������
	 * @param s           ��Ҫ��ʽ���Ĳ���

	 * @param precision   С��㱣����λ��
	 * @return
	 * String
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:48:46
	 */
	public static String numberFormat(String s, int precision) {
		Double d = Double.parseDouble(s);
		// 1$����Ǵӵڼ�λ��ʼ��ʽ��,","ÿ3λ����֮����"��"�ָ�,".2"���С������ȡ2λ."f"����Ǹ�������

		return numberFormat(d, precision);
	}
	/**
	 * ���������� ����ʽ��,��ȡ��С��λ��������������
	 *
	 * @param d           ��Ҫ��ʽ���Ĳ���

	 * @param precision   С��㱣����λ��
	 * @return
	 * String
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:49:48
	 */
	public static String numberFormat(Double d, int precision) {
		// %1$,.2f
		// 1$����Ǵӵڼ�λ��ʼ��ʽ��,","ÿ3λ����֮����"��"�ָ�,".2"���С������ȡ2λ."f"����Ǹ�������

		String format = "%1$,." + precision + "f";
		return (String.format(format, d));
	}

	/**
	 * ����������  ������ַ�
	 *
	 * @param s
	 * @return
	 * String
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:50:36
	 */
	public static String KillNull(String s) {
		return StringUtils.trimToEmpty(s);
	}


	/**
	 * ���������� ������ַ�
	 *
	 * @param s   ������ַ�
	 * @param d   ���Ϊ�վ��滻Ϊ���ַ�
	 * @return
	 * String
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:50:52
	 */
	public static String KillNull(String s, String d) {
		if (isEmpty(s)) {
			return d;
		} else {
			return s;
		}
	}
	
	/**
	 * �����������������
	 *
	 * @param _prefix   ������ַ�
	 * @return
	 * String
	 * @author Ǭ֮��

	 * @date 2012-5-12 ����08:50:52
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
	


	public static void main(String[] args) {
		System.out.println(StringUtil.contains("2012-02-30","-02-30"));
		System.out.println("2012-02-30".substring(0, 8));
	}
}
