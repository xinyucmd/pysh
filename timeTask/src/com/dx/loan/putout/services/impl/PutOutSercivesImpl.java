/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PutOutSercivesImpl.java
 * ������ com.dx.loan.putout.services.impl
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-16 ����10:22:06
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
 * ������ PutOutSercivesImpl
 * ������
 * @author Ǭ֮��
 * @date 2012-5-16 ����10:22:06
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
 * ���������� ��ú�ͬ��¼�б�
 * @return
 * @author Ǭ֮��
 * @date 2012-5-28 ����04:42:02
 */
	public String  getAdditiList(PageBean pageBean) {
		List<PactAdditiBean> pactAdditiBeanList = new ArrayList<PactAdditiBean>();
		// �ͻ�������,�Ժ����ƺ���Ҫע�����,����ֻ��ģ������
		ConsumerService consumerService = new ConsumerService();
		List<PactBean>  pactBeanList = putOutDao.getAdditiList(pageBean);
		for(PactBean pactBean:pactBeanList){
			ConsBean consBean = consumerService.getConsumer(pactBean.getCifNo());
			PactAdditiBean pactAdditiBean = new PactAdditiBean();
			pactAdditiBean.setCardNo(consBean.getCardNo());
			pactAdditiBean.setCifName(consBean.getCifName());
			pactAdditiBean.setCifNo(pactBean.getCifNo());
			pactAdditiBean.setKindName("���˴���");
			pactAdditiBean.setPactAmt(StringUtil.numberFormat(pactBean.getPactAmt(), 2));
			pactAdditiBean.setPactNo(pactBean.getPactNo());
			pactAdditiBean.setPactSts(dealPactState(pactBean.getPactSts()));
			pactAdditiBeanList.add(pactAdditiBean);
		}
		return toJSON(pactAdditiBeanList,pageBean);
	}
	

	/**
	 * 
	 * �������������ݺ�ͬ�Ż�ú�ͬʵ�� 
	 * @param pactNo
	 * @return
	 * PactBean
	 * @author Ǭ֮��
	 * @date 2012-5-29 ����03:59:31
	 */
	public PactBean getPactById(String pactNo) {
		return putOutDao.getPactById(pactNo);
	}
	/**
	 * 
	 * �������������º�ͬʵ��
	 * @param pactBean
	 * @author Ǭ֮��
	 * @date 2012-5-29 ����07:54:24
	 */
	public void updatePactBean(PactBean pactBean) {
		putOutDao.updatePactBean(pactBean);
	}

	
	/*******************************����Ϊ�ڲ�ʹ�õ�˽�з���*****************************************/
	
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
	 * ���������� �����ͬ״̬ 
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-29 ����09:23:54
	 */
	private String dealPactState(String pactSts){
		String pactStsName="";
		if(StringUtil.equals("0", SystemParm.PACT_STS_ADDIT)){
			pactStsName = "δ��¼";
		}
		if(StringUtil.equals("2", SystemParm.PACT_STS_PUT)){
			pactStsName = "δ�ſ�";
		}
		if(StringUtil.equals("3", SystemParm.PACT_STS_PLAN)){
			pactStsName = "δ���ɻ���ƻ�";
		}
		return pactStsName;
	}
	
	
}
