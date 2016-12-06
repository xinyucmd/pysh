/**
 * Copyright (C) DXHM 版权所有
 * 文件名： IPutOutDao.java
 * 包名： com.dx.loan.putout.dao
 * 说明：
 * @author 乾之轩
 * @date 2012-5-15 下午04:54:50
 * @version V1.0
 */ 
package com.dx.loan.putout.dao;


import java.util.List;

import com.dx.common.bean.PageBean;
import com.dx.loan.putout.bean.PactBean;


/**
 * 类名： IPutOutDao
 * 描述： 放款接口
 * @author 乾之轩
 * @date 2012-5-15 下午04:54:50
 *
 *
 */
public interface IPutOutDao {
	/**
	 * 
	 * 方法描述： 保存合同实体
	 * @param pactBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-28 下午02:59:50
	 */
	public  void savePactBean(PactBean pactBean);
	
	/**
	 * 
	 * 方法描述  根据合同号获得合同实体
	 * @param pactNo
	 * @return
	 * PactBean
	 * @author sll
	 * @date 2012-5-10 11:26:33
	 */
	public PactBean getPactById(String pactNo);
	/**
	 * 
	 * 方法描述： 获得需要补录的合同列表 
	 * @return
	 * List<PactBean>
	 * @author 乾之轩
	 * @date 2012-5-28 上午10:20:13
	 */
	public List<PactBean> getAdditiList(PageBean PageBean);
	
	
	/**
	 * 
	 * 方法描述： 获得待确认合同列表
	 * @return
	 * List<PactBean>
	 * @author 乾之轩
	 * @date 2012-5-28 上午10:05:48
	 */
	public List<PactBean> getConfList();
	
	/**
	 * 
	 * 方法描述： 更新合同实体
	 * @param pactBean
	 * void
	 * @author 乾之轩
	 * @date 2012-5-29 下午07:32:16
	 */
	public void updatePactBean(PactBean pactBean);
	
	

}
