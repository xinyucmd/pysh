/**
 * Copyright (C) DXHM ��Ȩ����
 * �ļ����� PlanAction.java
 * ������ com.dx.loan.plan.action
 * ˵����
 * @author Ǭ֮��
 * @date 2012-5-31 ����10:02:40
 * @version V1.0
 */ 
package com.dx.loan.plan.action;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;

import com.dx.common.CacheParm;
import com.dx.common.SystemParm;
import com.dx.common.action.BaseAction;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanParmBean;
import com.dx.loan.plan.services.IPlanServices;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.services.impl.RepayService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionContext;

/**
 * ������ PlanAction
 * ������ ����ƻ�action
 * @author Ǭ֮��
 * @date 2012-5-31 ����10:02:40
 *
 *
 */
public class PlanAction extends BaseAction {
	private static final long serialVersionUID = 2533583062611029099L;
	private IPlanServices  planServicesImpl;
	private String dueNo;
	private String json;
	// �༭����ƻ���,�������ɻ���ƻ�ʱ�������
	private String planPram;
	public void setDueNo(String dueNo) {
		this.dueNo = dueNo;
	}



	public void setPlanServicesImpl(IPlanServices planServicesImpl) {
		this.planServicesImpl = planServicesImpl;
	}
	
	// �̶�������
	private String fixDate;
	
	


	public void setFixDate(String fixDate) {
		this.fixDate = fixDate;
	}
	
	
	/**
	 * ���ɻ���ƻ�
	 * @return
	 */
	public String creatPlan(){
		PlanParmBean planParmBean = new PlanParmBean();
		planParmBean.setFixDate(fixDate);
		context = ActionContext.getContext();
		List<PlanBean> planBeans = planServicesImpl.createPlan(dueNo,planParmBean);
		RepayService repayService = SpringFactory.getBean("repayService");
		AcLnMstBean acLnMstBean =  repayService.getAcLnMstBeanByDueNo(dueNo);
		context.put("planBeans", planBeans);
		// ���ù̶�������
		LinkedHashMap<String,String> fixdate = new LinkedHashMap<String,String>();
		fixdate.put("1","1");
		fixdate.put("5","5");
		fixdate.put("15","15");
		fixdate.put("20","20");
		context.put("fixdate", fixdate);
		// ����Ĭ��ѡ����
		context.put("defaultkey", "5");
		// �Ƿ���ʾ�̶�������ѡ��
		context.put("isFixDay", SystemParm.IS_FIX_DAY);
		// �̶�������������
		context.put("fixDayType", SystemParm.FIX_DAY_TYPE);
		// �Ƿ�����
		context.put("isDelay", SystemParm.IS_DELAY);
		// �����ɵĻ���ƻ����л��淽������ı���
		CacheParm.push("planBeans", planBeans);
		// �Ƿ���ȡ�˻������
		context.put("account_fee", SystemParm.ACCOUNT_FEE);
		// ��ݺ�
		context.put("dueNo", dueNo);
		// ��ͬ��
		context.put("pactNo", planBeans.get(0).getPactNo());
		context.put("cifNo", planBeans.get(0).getCifNo());
		// �ж��ǲ��ǰ��ƻ�
		context.put("isPlan",StringUtil.equals(acLnMstBean.getReturnMethod(), SystemParm.RETURNMETHOD_PLAN));
		return "createPlan";
	}
	/**
	 * 
	 * ���������� ���滹��ƻ� 
	 * @return
	 * String
	 * @author Ǭ֮��
	 * @date 2012-6-5 ����07:41:43
	 */
	public String savePlan(){
		List<PlanBean> planBeans = CacheParm.get("planBeans");
		planServicesImpl.savePlanList(planBeans);
		CacheParm.remove("planBeans");
		return "createPlan";
	}
	
	
	
	public String editePlan() throws IOException{
		Gson gson = new Gson();
		Type listType = new TypeToken<List<PlanBean>>(){}.getType(); 
		List<PlanBean> planList = gson.fromJson(json, listType);
		PlanParmBean planParmBean = gson.fromJson(planPram,PlanParmBean.class);
		// �������ɻ���ƻ�
		List<PlanBean> planBeans = planServicesImpl.createPlan(planList,planParmBean);
		System.out.println(gson.toJson(planBeans));
		String planString =  gson.toJson(planBeans);
		response.getWriter().write(planString);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}



	public void setJson(String json) {
		this.json = json;
	}



	public void setPlanPram(String planPram) {
		this.planPram = planPram;
	}
	

}
