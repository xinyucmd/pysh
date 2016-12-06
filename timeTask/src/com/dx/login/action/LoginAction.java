package com.dx.login.action;



import java.io.IOException;

import com.dx.common.action.BaseAction;
import com.dx.common.bean.PageBean;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.DueBean;
import com.dx.login.services.ILoginService;

public class LoginAction extends BaseAction {

	
	private static final long serialVersionUID = 1L;
	
	private  ILoginService loginServer ;
	
	
	

	




	public String userLogin()  {
		System.out.println(loginServer);
		loginServer.getLoginBean("121000");
	
		 System.out.println("ok");
		return "success";
	 }


	public  String  testLanjie(){
		PactBean pactBean = new PactBean();
		pactBean.setApplyerBankName("工商银行");
		pactBean.setApplyerBankNo("6005");
		pactBean.setAuthId("111111");
		pactBean.setBal("300000");
		pactBean.setBegDate("2012-05-28");
		pactBean.setCifNo("130183");
		pactBean.setCmpdRate("6.468");
		pactBean.setCmpdRateFloat("160");
		pactBean.setConsultAmt("200");
		pactBean.setCurNo("1");
		pactBean.setEndDate("2030-05-28");
		pactBean.setExpRate("7.468");
		pactBean.setExpRateFloat("150");
		pactBean.setFineRate("8.468");
		pactBean.setFineRateFloat("150");
		pactBean.setFixrateInterest("0");
		pactBean.setGuaranType("1");
		pactBean.setIdType("1");
		pactBean.setIfYt("0");
		pactBean.setInfoOpType("A");
		pactBean.setIspreFixrate("0");
		pactBean.setLnRate("5.468");
		pactBean.setLnTradeNo("1");
		pactBean.setLnType("2");
		pactBean.setLoanKind("5");
		pactBean.setLoanType("2");
		pactBean.setMainGuaran("1");
		pactBean.setOccDate("2012-05-28");
		pactBean.setOccurType("1");
		pactBean.setOverRate("8.468");
		pactBean.setOverRateFloat("170");
		pactBean.setPactAmt("300000");
		pactBean.setPactKind("1");
		pactBean.setPactNature("1");
		pactBean.setPactNo("123456");
		pactBean.setPactSts("0");
		pactBean.setPactUse("置业");
		pactBean.setPactUseDesc("置业置业置业置业");
		pactBean.setRateFloat("100");
		pactBean.setRateNo("123");
		pactBean.setRateWay("2");
		pactBean.setReceiverBankName("工商银行");
		pactBean.setReceiverBankNo("600005");
		pactBean.setReturnMethod("2");
		pactBean.setTelNo("121000");
		pactBean.setTermDay("0");
		pactBean.setTermMon("240");
		pactBean.setTermYear("0");
		pactBean.setVouType("1");
		
		
		DueBean dueBean = new DueBean();
		dueBean.setBal("100000");
		dueBean.setBegDate("2012-05-28");
		dueBean.setConsFee("100");
		dueBean.setConsFeeType("0");
		dueBean.setDueAmt("100000");
		dueBean.setDueNo("222222");
		dueBean.setDueState("0");
		dueBean.setEndDate("2030-05-28");
		dueBean.setExpFlag("0");
		dueBean.setExtDays("0");
		dueBean.setFiveClass("1");
		dueBean.setFixDate(null);
		dueBean.setFourClass("1");
		dueBean.setInnerIntst("0");
		dueBean.setIsFix("0");
		dueBean.setIsForce("0");
		dueBean.setOuterIntst("0");
		// putOutService.savePactBean(pactBean);
		
		AcLnMstBean acLnMstBean = new AcLnMstBean();
		 acLnMstBean.copyVal(dueBean);
		acLnMstBean.copyVal(pactBean);
		acLnMstBean.setId("090909");
		// repayServiceImpl.saveAcLnMstBean(acLnMstBean);
		//RatedayBean ratedayBean = SpringFactory.getBean("rz");
		//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$"+ratedayBean.getDueNo());
		
		RatedayBean rateDayBean = new RatedayBean();
		rateDayBean.setCapital("10000");
		rateDayBean.setCmpdInterest("1000");
		rateDayBean.setDueNo("due111");
		rateDayBean.setId("id");
		rateDayBean.setInterest("120");
		rateDayBean.setOccureDate("2012-05-06");
		rateDayBean.setOverInterest("300");
		rateDayBean.setPactNo("111");
		rateDayBean.setState("6");
		rateDayBean.setTermNo("1");
		//repayServiceImpl.saveRateDayBean(rateDayBean);
		//RatedayBean rateDayBean = repayServiceImpl.getRateDay("111111", "3");
		//repayServiceImpl.updateRateDay(rateDayBean);
		//System.out.println(rateDayBean==null);
		System.out.println("#########################="+rateDayBean.getState());
		return  "ok";
		
	}



	public void setLoginServer(ILoginService loginServer) {
		this.loginServer = loginServer;
	}
	
	public String getTlrctList() throws IOException {
		
		PageBean pageBean=getPageBean();
		String json = loginServer.getTlrctlList(pageBean);
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	
	
	
}
