package com.dx.timetask;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * 功能:修复还款错误的bug
 * @author zqc
 *
 */
public class RepaiRepay extends QuartzJobBean{
	private Log log = LogFactory.getLog(getClass());
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		    repayRepair();
	}
	
	private void repayRepair(){
		IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		AcLnMstBean parmAcLnMstBean = new AcLnMstBean();
		parmAcLnMstBean.setDueState("1");
		//parmAcLnMstBean.setDueNo("JC1073707012013082112424001");
		// 查询出有那些借据需要进行批量处理
		List<AcLnMstBean> acLnMstBeans = repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
		// 还款计划服务类
		PlanServices planServices = SpringFactory.getBean("planServices");
		 
    	for(AcLnMstBean acLnMstBean:acLnMstBeans){
    		 // 统计已经偿还的本金和利息
			 String dueNo = acLnMstBean.getDueNo();
			 if(StringUtil.isEmpty(dueNo)){
				 continue;
			 }
			 RepayBean  parmRepayBean = new RepayBean();
			 parmRepayBean.setDueNo(dueNo);
			 PlanBean parmPlanBean = new PlanBean();
			 parmPlanBean.setDueNo(dueNo);
			 List <RepayBean> repayBeans = repayServiceImpl.getRepayBeans(parmRepayBean);
			 List <PlanBean> planBeans = planServices.getPlanBeanList(parmPlanBean);
			 Collections.sort(planBeans);
			 if(repayBeans==null || repayBeans.size()==0){
				 continue;
			 }
			 int firstTermNo = 0;
			 int secondTermNo = 0;
			 RepayBean firstRepayBean  = new RepayBean();
			 String firstCapital = "0.00";
			 String firstInterest = "0.00";
			 for(RepayBean repayBean:repayBeans){
				 if(firstTermNo==0){
					 firstTermNo = Integer.parseInt(repayBean.getTermNo()); 
					 firstRepayBean = repayBean; 
					 firstCapital = repayBean.getReturnCapital();
					 firstInterest = repayBean.getReturnInterest();
				 }else{
					 secondTermNo = Integer.parseInt(repayBean.getTermNo());
					 break;
				 }
			 }
			 
			if(firstTermNo-secondTermNo>1){
				Double d = Double.parseDouble(BigNumberUtil.Subtract(StringUtil.KillNull(firstRepayBean.getBjbalance(), "0.00"), "0.00"));		
				Double d1 = Double.parseDouble(BigNumberUtil.Subtract(StringUtil.KillNull(firstRepayBean.getReturnCapital(), "0.00"), "0.00"));		
				
				if((d==0 || d<0) && (d1>0) ){
					log.error("############"+firstRepayBean.getDueNo());
					System.out.println(firstRepayBean.getDueNo());
				}
			}
    	}
	}
}
