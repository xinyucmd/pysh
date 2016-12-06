/**
 * Copyright (C) DXHM 版权所有
 * 文件名： PlanAction.java
 * 包名： com.dx.loan.plan.action
 * 说明：
 * @author 乾之轩
 * @date 2012-5-31 上午10:02:40
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
 * 类名： PlanAction
 * 描述： 还款计划action
 * @author 乾之轩
 * @date 2012-5-31 上午10:02:40
 *
 *
 */
public class PlanAction extends BaseAction {
	private static final long serialVersionUID = 2533583062611029099L;
	private IPlanServices  planServicesImpl;
	private String dueNo;
	private String json;
	// 编辑还款计划后,从新生成还款计划时所需参数
	private String planPram;
	public void setDueNo(String dueNo) {
		this.dueNo = dueNo;
	}



	public void setPlanServicesImpl(IPlanServices planServicesImpl) {
		this.planServicesImpl = planServicesImpl;
	}
	
	// 固定还款日
	private String fixDate;
	
	


	public void setFixDate(String fixDate) {
		this.fixDate = fixDate;
	}
	
	
	/**
	 * 生成还款计划
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
		// 设置固定还款日
		LinkedHashMap<String,String> fixdate = new LinkedHashMap<String,String>();
		fixdate.put("1","1");
		fixdate.put("5","5");
		fixdate.put("15","15");
		fixdate.put("20","20");
		context.put("fixdate", fixdate);
		// 设置默认选中项
		context.put("defaultkey", "5");
		// 是否显示固定还款日选项
		context.put("isFixDay", SystemParm.IS_FIX_DAY);
		// 固定还款日期类型
		context.put("fixDayType", SystemParm.FIX_DAY_TYPE);
		// 是否延期
		context.put("isDelay", SystemParm.IS_DELAY);
		// 将生成的还款计划进行缓存方便后续的保存
		CacheParm.push("planBeans", planBeans);
		// 是否收取账户管理费
		context.put("account_fee", SystemParm.ACCOUNT_FEE);
		// 借据号
		context.put("dueNo", dueNo);
		// 合同号
		context.put("pactNo", planBeans.get(0).getPactNo());
		context.put("cifNo", planBeans.get(0).getCifNo());
		// 判断是不是按计划
		context.put("isPlan",StringUtil.equals(acLnMstBean.getReturnMethod(), SystemParm.RETURNMETHOD_PLAN));
		return "createPlan";
	}
	/**
	 * 
	 * 方法描述： 保存还款计划 
	 * @return
	 * String
	 * @author 乾之轩
	 * @date 2012-6-5 下午07:41:43
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
		// 从新生成还款计划
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
