package com.dx.timetask;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.services.impl.PlanServices;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.services.IRepayService;
/**
 * 功能:修复还款计划的状态
 * @author zqc
 * 
 */
public class RepairPlan extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		 planRepair();
	}
	
	/**
	 *修复还款计划的状态 
	 */
	private void planRepair(){
		IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		AcLnMstBean parmAcLnMstBean = new AcLnMstBean();
		parmAcLnMstBean.setDueState("1");
		//parmAcLnMstBean.setDueNo("JC3041100012013060311322001");
		// 查询出有那些借据需要进行批量处理
		List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
		// 还款计划服务类
		PlanServices planServices = SpringFactory.getBean("planServices");
		 
    	for(AcLnMstBean acLnMstBean:acLnMstBeans){
			 // 统计已经偿还的本金和利息
			 String hasRepay = "0.00"; 
			 String dueNo = acLnMstBean.getDueNo();
			 if(StringUtil.isEmpty(dueNo)){
				 continue;
			 }
			 RepayBean  parmRepayBean = new RepayBean();
			 parmRepayBean.setDueNo(dueNo);
			 List <RepayBean> repayBeans = repayServiceImpl.getRepayBeans(parmRepayBean);
			
			 if(repayBeans==null || repayBeans.size()==0){
				 continue;
			 }
			 for(RepayBean repayBean:repayBeans){
				 hasRepay =  BigNumberUtil.Add(hasRepay,repayBean.getReturnCapital(),repayBean.getReturnInterest(),repayBean.getBjbalance(),repayBean.getRatecomebalance());
			 }
			 
			 PlanBean parmPlanBean = new PlanBean();
			 parmPlanBean.setDueNo(dueNo);
			 List<PlanBean> planBeans = planServices.getPlanBeanList(parmPlanBean);
			 if(planBeans==null || planBeans.size()==0){
				 continue;
			 }
			 // 统计有几期还款计划已经还完
			 for(PlanBean planBean:planBeans){
				 hasRepay = BigNumberUtil.Subtract(hasRepay,planBean.getReturnCapital());
				 hasRepay = BigNumberUtil.Subtract(hasRepay,planBean.getReturnInterest());
				 Double d = Double.parseDouble(hasRepay);
				 // 将已经偿还完毕的还款计划的状态更新成3
				 if(d>0 || d==0){
						PlanBean tempParmPlanBean = new PlanBean();
						tempParmPlanBean.setDueNo(planBean.getDueNo());
						tempParmPlanBean.setTermNo(planBean.getTermNo());
						tempParmPlanBean.setState("3");
						System.out.println("#####"+tempParmPlanBean.getTermNo()+"####");
						planServices.updatePlanBean(tempParmPlanBean);
				 }else{
					 PlanBean tempParmPlanBean = new PlanBean();
					 tempParmPlanBean.setDueNo(planBean.getDueNo());
					 tempParmPlanBean.setTermNo(planBean.getTermNo());
					 tempParmPlanBean.setState("0");
					 planServices.updatePlanBean(tempParmPlanBean);
				 }
			 }
		 }
	}
}
