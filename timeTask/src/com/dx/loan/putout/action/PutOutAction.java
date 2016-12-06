/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PutOutAction.java
 * ������ com.dx.loan.putout.action
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-28 ����03:58:48
 * @version V1.0
 */ 
package com.dx.loan.putout.action;

import java.io.IOException;

import com.dx.common.action.BaseAction;
import com.dx.common.bean.PageBean;
import com.dx.loan.consumer.bean.ConsBean;
import com.dx.loan.consumer.services.impl.ConsumerService;
import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.putout.services.IPutOutServices;
import com.opensymphony.xwork2.ActionContext;

/**
 * ������ PutOutAction
 * ������ �ſ�ģ��action
 * @author Ǭ֮��
 * @date 2012-5-28 ����03:58:48
 *
 *
 */
public class PutOutAction extends BaseAction {
	private String  pactNo;
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}

	private static final long serialVersionUID = 292888336753135839L;
	
	private IPutOutServices  putOutServiceImpl;
	/**
	 * 
	 * ���������� ��ͬ��¼�б�
	 * @return
	 * @throws IOException
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-28 ����07:10:12
	 */
	public String  additiList() throws IOException{
		PageBean pageBean = getPageBean();
		String json = putOutServiceImpl.getAdditiList(pageBean);
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	public void setPutOutServiceImpl(IPutOutServices putOutServiceImpl) {
		this.putOutServiceImpl = putOutServiceImpl;
	}
/**
 * 
 * ���������� ��ת����ͬ��¼�б�
 * @return
 * String
 * @author Ǭ֮��
 * @date 2012-5-29 ����02:41:24
 */
	public String toAdditiList(){
		PactBean pactBean = new PactBean();
		pactBean.setCifNo("lp");
		pactBean.setTelNo("121001");
		pactBean.setPactNo("123456");
		pactBean.setPactAmt("300000");
		putOutServiceImpl.updatePactBean(pactBean);
		return "additiList";
	}
	
	/**
	 * 
	 * ���������� ��ת����ͬ��¼ҳ��
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-5-29 ����02:42:19
	 */
	public String toPactAddit(){
		PactBean pactBean = putOutServiceImpl.getPactById(pactNo);
		System.out.println("pactBean.getCifNo()="+pactBean.getCifNo());
		// ģ��ͻ���,�ͻ�ģ����ɺ���Ҫע�����
		ConsumerService consumerService = new ConsumerService();
		ConsBean consBean = consumerService.getConsumer(pactBean.getCifNo());
		context = ActionContext.getContext();
		context.put("pactBean", pactBean);
		context.put("cifName", consBean.getCifName());
		
		return "pactAddit";
	}

	
	
	
}
