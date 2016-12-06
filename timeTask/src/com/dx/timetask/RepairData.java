package com.dx.timetask;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dx.common.SystemParm;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.dayrate.services.impl.DayRateServices;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.services.impl.PlanServices;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.services.IRepayService;
/**
 * �޸���������
 * @author liupei
 *
 */
public class RepairData extends QuartzJobBean{
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		dataRepair();
//		overInterestRepair();
	}
	
	/**
	 * �޸��������Ϣ
	 */
	private void    dataRepair(){
		System.out.println("��ʼ�޸�������Ϣ......");
		 String  occureDate = "2014-03-28";
		 // ����ƻ�������
		 PlanServices planServices = SpringFactory.getBean("planServices");
		 // ���մ��������
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		 RatedayBean  pramRateDayBean = new RatedayBean();
		 pramRateDayBean.setOccureDate(occureDate);
		 pramRateDayBean.setDueNo("JC1075106012014011114031001");
		 List<RatedayBean>  ratedayBeans = dayRateServices.getRateDayBeanList(pramRateDayBean);
		 int i = 0;
		 for(RatedayBean ratedayBean:ratedayBeans){
			
			 String  termNo = ratedayBean.getTermNo();
			 String  dueNo =  ratedayBean.getDueNo();
			 String  capital =  ratedayBean.getCapital();
			 String  interest =  ratedayBean.getInterest();
			 PlanBean  parmPlanBean = new PlanBean();
			 parmPlanBean.setDueNo(dueNo);
			 List<PlanBean>  planBeans = planServices.getPlanBeanList(parmPlanBean);
		     String[] returnInf = getReturnInf(planBeans,termNo)	; 
			 Double flag0 =  Double.parseDouble(BigNumberUtil.Subtract(capital, returnInf[0]));
			 Double flag1 =  Double.parseDouble(BigNumberUtil.Subtract(interest, returnInf[1]));
			 if(flag0<0 || flag1<0){
				 ratedayBean.setCapital(returnInf[0]);
				 ratedayBean.setInterest(returnInf[1]);
				 dayRateServices.updateRateDay(ratedayBean);
			 }
			 i++;
			 
			 System.out.println("��ݺţ�"+dueNo+"�޸���ɣ���");
			 if(i==ratedayBeans.size()){
				 System.out.println("������Ϣȫ���޸����");
			 }
		 }
	}
	/**
	 * 
	 */
	private  void  overInterestRepair(){
		System.out.println("��ʼ�޸���Ϣ......");
		 // ����ƻ�������
		 PlanServices planServices = SpringFactory.getBean("planServices");
		 IRepayService repayServiceImpl = SpringFactory.getBean("repayServiceImpl");
		 // ������ʷ�б�(��������)
		 List<RepayBean>  repayBeans  = repayServiceImpl.getLastRepayList();
		
		 AcLnMstBean parmAcLnMstBean = new AcLnMstBean();
		 parmAcLnMstBean.setDueState("1");
		 parmAcLnMstBean.setDueNo("JC1075106012014011114031001");
		 
		 List<AcLnMstBean> acLnMstBeans =  repayServiceImpl.getAcLnMstBeans(parmAcLnMstBean);
		 int i = 0;
		 for(AcLnMstBean acLnMstBean:acLnMstBeans){
	    	 // ���һ�εĳ������ڹ���Ϣ
	    	 String[] termRepay =  getRepayInf(repayBeans,acLnMstBean.getDueNo());
	    	 if(StringUtil.equals("0", termRepay[1])){
	    		 continue;
	    	 }
	    	 //  ��ö�Ӧ����ƻ��ĵ���Ӧ��
	    	 PlanBean parmPlanBean =  new PlanBean();
	    	 parmPlanBean.setDueNo(acLnMstBean.getDueNo());
	    	 parmPlanBean.setTermNo(termRepay[1]);
	    	 PlanBean  planBean = planServices.getPlanBean(parmPlanBean);
	    	 String d = BigNumberUtil.Add(planBean.getReturnCapital(), planBean.getReturnInterest());
	    	 String d1 = BigNumberUtil.Subtract(termRepay[0], d);
	    	 Double d2 = Double.parseDouble(d1);
	    	 if(d2>0  || d2==0 ){
	    		String tempDate= DateUtil.addByMonDay(planBean.getEndDate(), 1, 0, DateUtil.DATE_FORMAT_);
	    		 updateOverInterest(acLnMstBean,tempDate);
	    	 }else{
	    		 // ��ʼ�޸�������Ϣ
	    		 updateOverInterest(acLnMstBean,planBean.getEndDate());
	    	 }
	    	 i++;
	    	 System.out.println("��ݺţ�"+acLnMstBean.getDueNo()+"�޸���ɣ���");
	    	 if(i==acLnMstBeans.size()){
				 System.out.println("��Ϣȫ���޸����");
			 }
	     }
	}
	/**
	 * ����:���·�Ϣ
	 * @param acLnMstBean
	 * @param date
	 */
	private void updateOverInterest(AcLnMstBean acLnMstBean,String date){
		 DayRateServices dayRateServices = SpringFactory.getBean("dayRateServices");
		int days = DateUtil.getBetweenDays(date, SystemParm.SystemDate);
		int i;
		String lnRate = acLnMstBean.getLnRate();
		if(StringUtil.equals(acLnMstBean.getLnRate(), "18.7")){
			lnRate= "18.6666667";
		}
		for(i=1;i<=days;++i){
			String overins = BigNumberUtil.Multiply(acLnMstBean.getDueAmt(), String.valueOf(i));
			overins = BigNumberUtil.Multiply(overins, String.valueOf(lnRate));
			overins = BigNumberUtil.Divide2(overins, "30000");
			RatedayBean ratedayBean = new RatedayBean(); 
			ratedayBean.setDueNo(acLnMstBean.getDueNo());
			String tempDate = DateUtil.addByDay(date, i, DateUtil.DATE_FORMAT_); 
			ratedayBean.setOccureDate(tempDate);
			ratedayBean.setOverInterest(overins);
			dayRateServices.updateRateDay(ratedayBean);
		}
	}
	
	/**
	 * ����:��ȡ���һ�εĻ�����Ϣ(���� ���ڱ���  ������Ϣ)
	 * @param repayBeans
	 * @param dueNo
	 * @return
	 */
	private String[] getRepayInf(List<RepayBean> repayBeans,String dueNo){
		String[] repayInf = new String[2];  
		// ͳ���ڹ�
		String termRepay = "0.00";
		String termNo = "0"; 
		String date = "";
		boolean flag = false;
		for(RepayBean repayBean:repayBeans){
			if(StringUtils.equals(repayBean.getDueNo(), dueNo)){
				flag = true;
				termNo = repayBean.getTermNo();
				date = repayBean.getOccDate();
				termRepay = BigNumberUtil.Add(termRepay,repayBean.getReturnCapital(),repayBean.getReturnInterest()); 
			}
			if(flag && !StringUtils.equals(repayBean.getDueNo(), dueNo)){
				break;
			}
		}
		repayInf[0] = termRepay;
		repayInf[1] = termNo;
		return repayInf;	
	}
	
	
	private  String[]  getReturnInf(List<PlanBean> planBeans, String termNo){
		String[] returninf = new String[2];
		String  capital = "0.00";		
		String  interest = "0.00";
		int  termNo_0 =  Integer.parseInt(termNo);
		for(PlanBean  planBean:planBeans){
			int  termNo_1 =  Integer.parseInt(planBean.getTermNo());
			if(termNo_1<termNo_0 || termNo_1==termNo_0){
				capital = BigNumberUtil.Add(capital,planBean.getReturnCapital());
				interest = BigNumberUtil.Add(interest,planBean.getReturnInterest());
			}
		}
		returninf[0]= capital;
		returninf[1]= interest;
		return  returninf;
	}
	
	
	public static void  main(String...strings){
		 int days = DateUtil.getBetweenDays("2014-01-20", SystemParm.SystemDate);
		 System.out.println(days);
	}
	
	
	
	

}
