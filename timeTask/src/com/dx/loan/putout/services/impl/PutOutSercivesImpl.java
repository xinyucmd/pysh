/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PutOutSercivesImpl.java
 * 包名： com.dx.loan.putout.services.impl
 * 说明：
 * @author 乾之轩
 * @date 2012-5-16 上午10:22:06
 * @version V1.0
 */ 
package com.dx.loan.putout.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dx.common.SystemParm;
import com.dx.common.bean.PageBean;
import com.dx.common.util.StringUtil;
import com.dx.loan.consumer.bean.ConsBean;
import com.dx.loan.consumer.services.impl.ConsumerService;
import com.dx.loan.putout.bean.PactAdditiBean;
import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.putout.dao.IPutOutDao;
import com.dx.loan.putout.services.IPutOutServices;
import com.google.gson.Gson;


/**
 * 类名： PutOutSercivesImpl
 * 描述：
 * @author 乾之轩
 * @date 2012-5-16 上午10:22:06
 *
 *
 */
public class PutOutSercivesImpl implements IPutOutServices {
	private IPutOutDao putOutDao;

	public void setPutOutDao(IPutOutDao putOutDao) {
		this.putOutDao = putOutDao;
	}
/**
 * 
 * 方法描述： 获得合同补录列表
 * @return
 * @author 乾之轩
 * @date 2012-5-28 下午04:42:02
 */
	public String  getAdditiList(PageBean pageBean) {
		List<PactAdditiBean> pactAdditiBeanList = new ArrayList<PactAdditiBean>();
		// 客户服务类,以后完善后需要注入进来,这里只是模拟数据
		ConsumerService consumerService = new ConsumerService();
		List<PactBean>  pactBeanList = putOutDao.getAdditiList(pageBean);
		for(PactBean pactBean:pactBeanList){
			ConsBean consBean = consumerService.getConsumer(pactBean.getCifNo());
			PactAdditiBean pactAdditiBean = new PactAdditiBean();
			pactAdditiBean.setCardNo(consBean.getCardNo());
			pactAdditiBean.setCifName(consBean.getCifName());
			pactAdditiBean.setCifNo(pactBean.getCifNo());
			pactAdditiBean.setKindName("个人贷款");
			pactAdditiBean.setPactAmt(StringUtil.numberFormat(pactBean.getPactAmt(), 2));
			pactAdditiBean.setPactNo(pactBean.getPactNo());
			pactAdditiBean.setPactSts(dealPactState(pactBean.getPactSts()));
			pactAdditiBeanList.add(pactAdditiBean);
		}
		return toJSON(pactAdditiBeanList,pageBean);
	}
	

	/**
	 * 
	 * 方法描述：根据合同号获得合同实体 
	 * @param pactNo
	 * @return
	 * PactBean
	 * @author 乾之轩
	 * @date 2012-5-29 下午03:59:31
	 */
	public PactBean getPactById(String pactNo) {
		return putOutDao.getPactById(pactNo);
	}
	/**
	 * 
	 * 方法描述：更新合同实体
	 * @param pactBean
	 * @author 乾之轩
	 * @date 2012-5-29 下午07:54:24
	 */
	public void updatePactBean(PactBean pactBean) {
		putOutDao.updatePactBean(pactBean);
	}

	
	/*******************************以下为内部使用的私有方法*****************************************/
	
	@SuppressWarnings("unchecked")
	private String toJSON(List<PactAdditiBean> list, PageBean pageBean) {
		Map map = new HashMap();
		map.put("page", pageBean.getCurPage());
		map.put("total", pageBean.getTotalSize());
		List mapList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map cellMap = new HashMap();
			cellMap.put("pactNo", list.get(i).getPactNo());
			cellMap.put("cell", new Object[] {
					list.get(i).getPactNo(),
					list.get(i).getCifName(),
					list.get(i).getCardNo(),
					list.get(i).getPactAmt(),
					list.get(i).getKindName(),
					list.get(i).getPactSts()
					});
			mapList.add(cellMap);
		}
		map.put("rows", mapList);
		Gson gs=new Gson();
		String ja =gs.toJson(map);
		System.out.println(ja);
		return ja.toString();
	}
	
	/**
	 * 
	 * 方法描述： 处理合同状态 
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-5-29 上午09:23:54
	 */
	private String dealPactState(String pactSts){
		String pactStsName="";
		if(StringUtil.equals("0", SystemParm.PACT_STS_ADDIT)){
			pactStsName = "未补录";
		}
		if(StringUtil.equals("2", SystemParm.PACT_STS_PUT)){
			pactStsName = "未放款";
		}
		if(StringUtil.equals("3", SystemParm.PACT_STS_PLAN)){
			pactStsName = "未生成还款计划";
		}
		return pactStsName;
	}
	
	
}
