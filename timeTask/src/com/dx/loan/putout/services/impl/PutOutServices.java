/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PutOutServices.java
 * 包名： com.dx.loan.putout.services.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-5-16 上午10:22:43
 * @version V1.0
 */ 
package com.dx.loan.putout.services.impl;

import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.putout.dao.IPutOutDao;


/**
 * 类名： PutOutServices
 * 描述： 
 * @author 乾之轩
 * @date 2012-5-16 上午10:22:43
 *
 *
 */
public class PutOutServices {
	
	private IPutOutDao putOutDao;

	public void setPutOutDao(IPutOutDao putOutDao) {
		this.putOutDao = putOutDao;
		
	}
	
	
	
	/**
	 * 
	 * 方法描述： 保存放款实体
	 * @param pactBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-28 下午03:10:12
	 */
	public void savePactBean(PactBean pactBean){
		putOutDao.savePactBean(pactBean);
	}
	
	
	

}
