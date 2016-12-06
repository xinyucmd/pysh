package com.jiangchuanbanking.plan.service;
import java.sql.SQLException;
import java.util.List;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Meeting;
import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.plan.domain.PlanParmBean;
import com.jiangchuanbanking.subscription.domain.PactInfo;

public interface IPlanServices extends IBaseService<PlanBean>{
	/**
	 * 
	 * 方法描述： 根据借据号和还款计划参数实体生成还款计划
	 * @param dueNo
	 * @param planParmBean
	 * @return
	 * List<PlanBean>
	 * @author 乾之轩
	 * @date 2012-6-5 下午07:42:39
	 */
	public List<PlanBean> createPlan(PactInfo pactInfo);
	public void savePlanList(List<PlanBean> planBeans) throws SQLException;

}
