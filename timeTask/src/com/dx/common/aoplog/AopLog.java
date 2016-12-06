/**
 * Copyright (C) DXHM ��Ȩ����
 * @�ļ��� AopLog.java
 * @���� com.dx.common.aoplog
 * @˵�� springAop��־
 * @����  Ǭ֮��
 * @ʱ�� 2012-4-19 ����03:42:56
 * @�汾 V1.0
 */
package com.dx.common.aoplog;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;



/**
 * @���� AopLog
 * @���� springAop��־
 * @���� Ǭ֮��
 * @���� 2012-4-19 ����03:42:56 ========�޸���־=======
 * 
 */
public class AopLog {

	public AopLog() {
	}

	/**
	 * 
	 * @���� beforeAdvice
	 * @���� ǰ��ִ֪ͨ�з���ǰ����
	 * @����
	 * @����ֵ void
	 * @���� Ǭ֮��
	 * @ʱ�� 2012-4-20 ����09:39:31
	 */
	public void beforeAdvice(JoinPoint jp) {
		Logger logger = Logger.getLogger(jp.getTarget().getClass());
		if (logger.isDebugEnabled()) {

			logger.debug("��ʼ����" + jp.getTarget().getClass().getName() + "����"
					+ jp.getSignature().getName());
		}
	}

	/**
	 * 
	 * @���� afterAdvice
	 * @���� ����֪ͨ
	 * @���� @param jp
	 * @����ֵ void
	 * @���� Ǭ֮��
	 * @ʱ�� 2012-4-20 ����09:40:27
	 */
	public void afterAdvice(JoinPoint jp) {
		Logger logger = Logger.getLogger(jp.getTarget().getClass());
		if (logger.isDebugEnabled()) {
			logger.debug("��������" + jp.getTarget().getClass().getName() + "����"
					+ jp.getSignature().getName());
		}
	}

	/**
	 * 
	 * @���� exceptionAdvice
	 * @���� �쳣֪ͨ��Ϣ
	 * @���� @param jp
	 * @���� @param ex
	 * @����ֵ void
	 * @���� Ǭ֮��
	 * @ʱ�� 2012-4-20 ����11:27:42
	 */
	public void exceptionAdvice(JoinPoint jp, Exception ex) {//[Method method, Object[] args, Object target,] <Exception/Throwable> ex
		Logger logger = Logger.getLogger(jp.getTarget().getClass());
		logger.error("����ʱ�쳣���� "+jp.getTarget().getClass().getName() + "���� "
				+ jp.getSignature().getName()+"�쳣 "+ex.getClass().getSimpleName());
	}

	/**
	 * 
	 * @���� aroudAdvice
	 * @���� ����֪ͨ
	 * @���� @param pjp
	 * @���� @return
	 * @���� @throws Throwable
	 * @����ֵ Object
	 * @���� Ǭ֮��
	 * @ʱ�� 2012-4-20 ����09:40:44
	 */
	public Object aroudAdvice(ProceedingJoinPoint pjp) throws Throwable {
		Object[] objects = pjp.getArgs();
		for (Object object : objects) {
			System.out.println(object);
		}
		return pjp.proceed();
	}

	@SuppressWarnings("unused")
	private String getDate() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

}
