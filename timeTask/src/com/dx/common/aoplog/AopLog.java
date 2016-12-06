/**
 * Copyright (C) DXHM 版权所有
 * @文件名 AopLog.java
 * @包名 com.dx.common.aoplog
 * @说明 springAop日志
 * @作者  乾之轩
 * @时间 2012-4-19 下午03:42:56
 * @版本 V1.0
 */
package com.dx.common.aoplog;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;



/**
 * @类名 AopLog
 * @描述 springAop日志
 * @作者 乾之轩
 * @日期 2012-4-19 下午03:42:56 ========修改日志=======
 * 
 */
public class AopLog {

	public AopLog() {
	}

	/**
	 * 
	 * @名称 beforeAdvice
	 * @描述 前置通知执行方法前调用
	 * @参数
	 * @返回值 void
	 * @作者 乾之轩
	 * @时间 2012-4-20 上午09:39:31
	 */
	public void beforeAdvice(JoinPoint jp) {
		Logger logger = Logger.getLogger(jp.getTarget().getClass());
		if (logger.isDebugEnabled()) {

			logger.debug("开始调用" + jp.getTarget().getClass().getName() + "方法"
					+ jp.getSignature().getName());
		}
	}

	/**
	 * 
	 * @名称 afterAdvice
	 * @描述 后置通知
	 * @参数 @param jp
	 * @返回值 void
	 * @作者 乾之轩
	 * @时间 2012-4-20 上午09:40:27
	 */
	public void afterAdvice(JoinPoint jp) {
		Logger logger = Logger.getLogger(jp.getTarget().getClass());
		if (logger.isDebugEnabled()) {
			logger.debug("结束调用" + jp.getTarget().getClass().getName() + "方法"
					+ jp.getSignature().getName());
		}
	}

	/**
	 * 
	 * @名称 exceptionAdvice
	 * @描述 异常通知信息
	 * @参数 @param jp
	 * @参数 @param ex
	 * @返回值 void
	 * @作者 乾之轩
	 * @时间 2012-4-20 上午11:27:42
	 */
	public void exceptionAdvice(JoinPoint jp, Exception ex) {//[Method method, Object[] args, Object target,] <Exception/Throwable> ex
		Logger logger = Logger.getLogger(jp.getTarget().getClass());
		logger.error("运行时异常调用 "+jp.getTarget().getClass().getName() + "方法 "
				+ jp.getSignature().getName()+"异常 "+ex.getClass().getSimpleName());
	}

	/**
	 * 
	 * @名称 aroudAdvice
	 * @描述 环绕通知
	 * @参数 @param pjp
	 * @参数 @return
	 * @参数 @throws Throwable
	 * @返回值 Object
	 * @作者 乾之轩
	 * @时间 2012-4-20 上午09:40:44
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
