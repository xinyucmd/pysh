
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
	// ����ʵ��
	private RepayBean repayBean;
	public RepayAction(){};
	private static final long serialVersionUID = -3338541874815629602L;
	private IRepayService repayServiceImpl;
	private DayRateServices  dayRateServices ;
	// Ƿ�������
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
     * ������������������ҳ�� 
     * void
     * @author Ǭ֮��-0
     * @date 2012-6-12 ����05:42:09
     */
	public String repayDetial(){
		String dueNo = repayBean.getDueNo();
		// �жϸý���Ƿ���Ƿ����Ϣ
		String debtMoney = debtServices.getDebtByDueNo(dueNo);	//getDebtByDueNo
		RatedayBean tempRatedayBean = new RatedayBean();
		tempRatedayBean.setDueNo(dueNo);
		tempRatedayBean.setOccureDate( SystemParm.SystemDate);
		RatedayBean ratedayBean = dayRateServices.getRateDay(tempRatedayBean);
		if(ratedayBean==null){
			throw  new NullPointerException("���������Ϣ����,���ܸ��ڻ���ƻ���û�н������մ���,���ʵ��ǰϵͳ����");
		}
		// ��Ϣ ������Ϣ ������Ϣ�ĺ�
		String totalInterest = repayServiceImpl.getTotalInterest(ratedayBean);
		//�������ļ�
		AcLnMstBean tempAcLnMstBean = new AcLnMstBean();
		tempAcLnMstBean.setDueNo(dueNo);
		AcLnMstBean acLnMstBean = repayServiceImpl.getAcLnMstBean(tempAcLnMstBean);
		
		context = ActionContext.getContext();
		context.put("ratedayBean", ratedayBean);
		// �ж��ܲ����Ż� 0 �������Ż� 1�����Ż�
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
		// ��Լ��֤��
		context.put("perfAmount", acLnMstBean.getPerfAmount());
		// �ж��ǲ��Ǹ��ڵ����һ�컹��
		if(StringUtil.equals(repayBean.getEndDate(), SystemParm.SystemDate)){
			context.put("isLastDay", "1");
		}else{
			context.put("isLastDay", "0");
		}
		context.put("acLnMstBean", acLnMstBean);
		// �˻������
		context.put("otherFee", repayBean.getOtherFee());
		// Ƿ����Ϣ
		context.put("debtMoney", debtMoney);
	    
		return "repayDetail";
	}
	

	
	
	/**
	 * 
	 * ���������� ����� 
	 * void
	 * @author Ǭ֮��
	 * @throws IOException 
	 * @date 2012-6-14 ����09:57:35
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
	 * ���������� ��ǰ���� 
	 * void
	 * @author Ǭ֮��
	 * @date 2012-6-18 ����09:36:58
	 */
	public   void   advaceRepayTrading(){
		repayServiceImpl.saveAdvaceRepay(repayBean);
	}
	
	/**
	 * �������,�������ɹ���ת��������Ϣչʾҳ��
	 */
	public  String  toRepayFish(){
		RepayBean tempRepayBean =  repayServiceImpl.getRepayBean(repayBean);
		
		context = ActionContext.getContext();
		context.put("repayBean", tempRepayBean);
		return "repayFish";   
	}
	
	
	
	

	
	
	


	
	
	
	
	

}
