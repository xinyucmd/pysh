
package com.dx.loan.repay.action;

import java.io.IOException;

import com.dx.common.SystemParm;
import com.dx.common.action.BaseAction;
import com.dx.common.bean.PageBean;
import com.dx.common.util.StringUtil;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.dayrate.services.impl.DayRateServices;
import com.dx.loan.debt.services.impl.DebtServices;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.services.IRepayService;
import com.opensymphony.xwork2.ActionContext;


public class RepayAction  extends  BaseAction {
	// 还款实体
	private RepayBean repayBean;
	public RepayAction(){};
	private static final long serialVersionUID = -3338541874815629602L;
	private IRepayService repayServiceImpl;
	private DayRateServices  dayRateServices ;
	// 欠款服务类
	private DebtServices debtServices;
	
	public void setDebtServices(DebtServices debtServices) {
		this.debtServices = debtServices;
	}

	public void setDayRateServices(DayRateServices dayRateServices) {
		this.dayRateServices = dayRateServices;
	}

	public void setRepayServiceImpl(IRepayService repayServiceImpl) {
		this.repayServiceImpl = repayServiceImpl;
	}
	public void setRepayBean(RepayBean repayBean) {
		this.repayBean = repayBean;
	}

	

	public String  repayList() throws IOException{
		
		PageBean pageBean = getPageBean();
		String json = repayServiceImpl.getRepayList(pageBean);
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	public RepayBean getRepayBean() {
		return repayBean;
	}

	public String  toRepayList() {
		return "repayList";
	}
	
	
    /**
     * 
     * 方法描述：还款详情页面 
     * void
     * @author 乾之轩-0
     * @date 2012-6-12 下午05:42:09
     */
	public String repayDetial(){
		String dueNo = repayBean.getDueNo();
		// 判断该借据是否有欠款信息
		String debtMoney = debtServices.getDebtByDueNo(dueNo);	//getDebtByDueNo
		RatedayBean tempRatedayBean = new RatedayBean();
		tempRatedayBean.setDueNo(dueNo);
		tempRatedayBean.setOccureDate( SystemParm.SystemDate);
		RatedayBean ratedayBean = dayRateServices.getRateDay(tempRatedayBean);
		if(ratedayBean==null){
			throw  new NullPointerException("获得日终信息出错,可能该期还款计划还没有进行日终处理,请核实当前系统日期");
		}
		// 利息 逾期利息 复利利息的和
		String totalInterest = repayServiceImpl.getTotalInterest(ratedayBean);
		//贷款主文件
		AcLnMstBean tempAcLnMstBean = new AcLnMstBean();
		tempAcLnMstBean.setDueNo(dueNo);
		AcLnMstBean acLnMstBean = repayServiceImpl.getAcLnMstBean(tempAcLnMstBean);
		
		context = ActionContext.getContext();
		context.put("ratedayBean", ratedayBean);
		// 判断能不能优惠 0 不允许优惠 1允许优惠
		context.put("is_privilege", SystemParm.IS_PRIVILEGE);
		context.put("totalInterest", totalInterest);
		context.put("account_fee", SystemParm.ACCOUNT_FEE);
		context.put("begDate",repayBean.getBegDate());
		context.put("endDate",repayBean.getEndDate());
		context.put("cifNo", repayBean.getCifNo());
		context.put("termNo", repayBean.getTermNo());
		context.put("dueAmt", repayBean.getDueAmt());
		context.put("advaceAmt", repayBean.getAdvaceAmt());
		repayBean.setCifName(StringUtil.Unicode2Chines(repayBean.getCifName()));
		context.put("cifName", repayBean.getCifName());
		context.put("systemDate", SystemParm.SystemDate);
		// 履约保证金
		context.put("perfAmount", acLnMstBean.getPerfAmount());
		// 判断是不是该期的最后一天还款
		if(StringUtil.equals(repayBean.getEndDate(), SystemParm.SystemDate)){
			context.put("isLastDay", "1");
		}else{
			context.put("isLastDay", "0");
		}
		context.put("acLnMstBean", acLnMstBean);
		// 账户管理费
		context.put("otherFee", repayBean.getOtherFee());
		// 欠款信息
		context.put("debtMoney", debtMoney);
	    
		return "repayDetail";
	}
	

	
	
	/**
	 * 
	 * 方法描述： 还款交易 
	 * void
	 * @author 乾之轩
	 * @throws IOException 
	 * @date 2012-6-14 上午09:57:35
	 */
	public void repayTrading() throws IOException{
		String flag = "1";
		repayServiceImpl.saveRepay(repayBean);
		response.getWriter().write(flag);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 
	 * 方法描述： 提前还款 
	 * void
	 * @author 乾之轩
	 * @date 2012-6-18 上午09:36:58
	 */
	public   void   advaceRepayTrading(){
		repayServiceImpl.saveAdvaceRepay(repayBean);
	}
	
	/**
	 * 点击还款,如果还款成功跳转到还款信息展示页面
	 */
	public  String  toRepayFish(){
		RepayBean tempRepayBean =  repayServiceImpl.getRepayBean(repayBean);
		
		context = ActionContext.getContext();
		context.put("repayBean", tempRepayBean);
		return "repayFish";   
	}
	
	
	
	

	
	
	


	
	
	
	
	

}
