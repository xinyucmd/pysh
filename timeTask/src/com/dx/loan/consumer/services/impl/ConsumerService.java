/**
 * Copyright (C) DXHM 版权所有
 * 文件名： ConsumerService.java
 * 包名： com.dx.loan.consumer.services.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-5-28 下午04:44:18
 * @version V1.0
 */ 
package com.dx.loan.consumer.services.impl;

import com.dx.loan.consumer.bean.ConsBean;

/**
 * 类名： ConsumerService
 * 描述： 客户模块服务类
 * @author 乾之轩
 * @date 2012-5-28 下午04:44:18
 *
 *
 */
public class ConsumerService {
	
	
	public ConsBean getConsumer(String cifNo){
		// 
		ConsBean consBean = new ConsBean();
		consBean.setCardNo("121212121212");
		consBean.setCardType("1");
		consBean.setCifName("张三");
		consBean.setCifNo(cifNo);
		
		return consBean;
	}
	
	
	
	

}
