package com.jiangchuanbanking.plan.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.plan.dao.IPlanDao;
import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.plan.service.IPlanServices;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.util.DateUtil;

/**
 * 类名： PlanServicesImpl 描述： 还款计划服务类
 */
public class PlanServicesImpl extends BaseService<PlanBean> implements
		IPlanServices {
	private IPlanDao planDao;
	private BaseDao baseDao;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 
	 * 方法描述： 生成还款计划
	 * 
	 * @param dueNo
	 * @return
	 * @author 乾之轩
	 * @throws UnsupportedEncodingException
	 * @date 2012-5-31 上午10:07:34
	 */
	public List<PlanBean> createPlan(PactInfo pactInfo) {
		List<PlanBean> planBeans = null;
		// 计息日为放款日
		String begin =dateFormat.format(pactInfo.getStart_date());
		String end =dateFormat.format(pactInfo.getEnd_date());
		pactInfo.setBeginDate(begin);
//		pactInfo.setEndDate(DateUtil.addByMonDay(begin,
//				Integer.valueOf(pactInfo.getTerm_range()), 0,
//				DateUtil.DATE_FORMAT_));
		
		pactInfo.setEndDate(end);
		pactInfo.setDue_bal(String.valueOf(pactInfo.getPact_amt()));
		PlanServices planServices = new PlanServices();
		planBeans = planServices.genePlan(pactInfo);
		return planBeans;
	}
	/**
	 * 
	 * 方法描述： 保存还款计划
	 * 
	 * @param planBeans
	 * @author 乾之轩
	 * @throws SQLException 
	 * @date 2012-6-5 下午07:49:57
	 */
	public void savePlanList(List<PlanBean> planBeans) throws SQLException {
		planDao.savePlanList(planBeans);
	}
	public IPlanDao getPlanDao() {
		return planDao;
	}

	public void setPlanDao(IPlanDao planDao) {
		this.planDao = planDao;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
