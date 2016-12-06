/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IPutOutServices.java
 * 包名： com.dx.loan.putout.services
 * 说明：
 * @author 乾之轩
 * @date 2012-5-16 上午10:21:28
 * @version V1.0
 */ 
package com.dx.loan.putout.services;


import com.dx.common.bean.PageBean;
import com.dx.loan.putout.bean.PactBean;



/**
 * 类名： IPutOutServices
 * 描述：  放款业务接口
 * @author 乾之轩
 * @date 2012-5-16 上午10:21:28
 *
 *
 */
public interface IPutOutServices {
	/**
	 * 
	 * 方法描述： 获得合同补录列表
	 * @return
	 * List<PactBean>
	 * @author 乾之轩
	 * @date 2012-5-28 下午04:40:59
	 */
	public String  getAdditiList(PageBean pageBean);
	
	/**
	 * 
	 * 方法描述： 根据合同号获得合同实体
	 * @return
	 * PactBean
	 * @author 乾之轩
	 * @date 2012-5-29 下午03:57:09
	 */
	public PactBean getPactById(String pactNo);
	
	/**
	 * 
	 * 方法描述： 更新合同实体
	 * @param pactBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-29 下午07:53:01
	 */
	public void updatePactBean(PactBean pactBean);
	
	

}
