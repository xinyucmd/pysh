package app.batch.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import app.batch.entity.CustRepayDetails;

import com.dx.back.systemdate.bean.SystemDateBean;
import com.dx.back.systemdate.services.impl.SystemDateServices;
import com.dx.common.SystemParm;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanBeans;
import com.dx.loan.putout.bean.PactBean;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.DueBean;
import com.dx.loan.repay.bean.LnDue;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.services.IRepayService;

public class CustRepayDetailBO {

	Log log = LogFactory.getLog(getClass());

	public void executeInternal() {
		IRepayService repayServiceImpl = SpringFactory
				.getBean("repayServiceImpl");

		RepayBean repayBeanTemp = new RepayBean();
		// 获取有还款记录的借据号list
		List<RepayBean> repayBeansDueNo = repayServiceImpl
				.getDistinctRepayBeans(repayBeanTemp);
		for (RepayBean repayBean : repayBeansDueNo) {
			CustRepayDetails custRepayDetails = new CustRepayDetails();
			String dueNo = repayBean.getDueNo();
			String yhkze = "0.00";// 应还款总额
			String sjhkze = "0.00";// 实际还款总额
			// 获取还款计划list
			PlanBean planBean = new PlanBean();
			planBean.setDueNo(dueNo);
			List<PlanBean> planBeanList = repayServiceImpl
					.getPlanBeanList(planBean);
			// 获取某一单子的还款记录list
			RepayBean repayBeanOne = new RepayBean();
			repayBeanOne.setDueNo(dueNo);
			List<RepayBean> repayBeanOneList = repayServiceImpl
					.getRepayBeanss(repayBeanOne);
			int termNo_jh = 0;// 还款计划运算期数
			int termNo_jl = 0;// 还款记录运算期数
			int sumCount_hk = 0;// 实际还款总次数
			double qigong_ye = 0.00;// 期供余额
			double sjhk = 0.00;// 实际还款
			long over_days = 0;// 逾期天数
			String jq_days = "0.00";//加权天数
			String eachYhze = "0.00";// 还款计划中每期的期供
			String eachShze = "0.00";// 还款记录中每期的实还总额
			String eachCe="0.00";//每次还款与计划差额
			int ysterm_no=0;//中间运算期数
			custRepayDetails.setDue_no(dueNo);
//			if (dueNo=="鲁淄个130400006001"||dueNo=="C1173701022013062511646001") {
				
			
			for (int i = 0; i < planBeanList.size(); i++) {
				eachYhze = BigNumberUtil.Add(planBeanList.get(i)
						.getReturnCapital(), planBeanList.get(i)
						.getReturnInterest());//2000
				custRepayDetails.setTerm_no((i+1)+"");//期数
				for (int j = ysterm_no; j < repayBeanOneList.size(); j++) {
					System.out.println(repayBeanOneList.get(j)+repayBeanOneList.get(j).getReturnCapital()+ "==="+
							repayBeanOneList.get(j).getReturnInterest()+"==="+
							repayBeanOneList.get(j).getRatecomebalance()+"==="+
							repayBeanOneList.get(j).getBjbalance());
					eachShze = BigNumberUtil.Add(repayBeanOneList.get(j)
							.getReturnCapital(), repayBeanOneList.get(j)
							.getReturnInterest(), repayBeanOneList.get(j)
							.getRatecomebalance(), repayBeanOneList.get(j)
							.getBjbalance(),eachShze);//1500+300+500
					if (Double.parseDouble(eachShze)>=Double.parseDouble(eachYhze)) {
						//实还>=期供
						eachCe=BigNumberUtil.Subtract(eachShze, eachYhze);//300
						custRepayDetails.setTerm_no((i+1)+"");//赋值期数
						custRepayDetails.setTerm_amt(eachYhze);//赋值金额
						if (getQuot(repayBeanOneList.get(j).getOccDate(),planBeanList.get(i).getEndDate())>0) {
							//产生逾期：实际还款日大于还款计划日
							over_days=getQuot(repayBeanOneList.get(j).getOccDate(),planBeanList.get(i).getEndDate());
							jq_days = BigNumberUtil.add2String(jq_days, BigNumberUtil.Divide2(BigNumberUtil.Multiply(over_days+"", eachShze),eachYhze));
							custRepayDetails.setOver_days(jq_days);//赋值逾期天数
						}else {
							custRepayDetails.setOver_days("0");//未产生逾期
						}
						eachShze=eachCe;//将多还的金额放到下一次循环里面使用
						over_days=0;//将中间运算参数归零
						jq_days="0";//将中间运算参数归零
						ysterm_no=j+1;
						custRepayDetails.setTerm_date(repayBeanOneList.get(j).getOccDate());
						if (repayServiceImpl.findCustRepayDetailsList(custRepayDetails).size()!=0) {
							//repayServiceImpl.updateREpayDetails(custRepayDetails);
							System.out.println("借据号："+dueNo+"期数："+custRepayDetails.getTerm_no()+"==已存在！！");
							break;
						}else {
							repayServiceImpl.insertREpayDetails(custRepayDetails);
							System.out.println("借据号："+dueNo+"期数："+custRepayDetails.getTerm_no()+"==新增成功！！");
						}
						
						break;
					}else {
						//实还<期供
						eachCe=BigNumberUtil.Subtract(eachYhze, eachShze);//500,200
							//累加后还是不够
							if (getQuot(repayBeanOneList.get(j).getOccDate(),planBeanList.get(i).getEndDate())>0) {
								//产生逾期：实际还款日大于还款计划日
								over_days=getQuot(repayBeanOneList.get(j).getOccDate(),planBeanList.get(i).getEndDate());
								jq_days = BigNumberUtil.add2String(jq_days, BigNumberUtil.Divide2(BigNumberUtil.Multiply(over_days+"", eachShze),eachYhze));
								custRepayDetails.setOver_days(jq_days);//赋值逾期天数
							}else {
								custRepayDetails.setOver_days("0");//未产生逾期
							}
						continue;
					}

				}
				
				
			}
		}

	}

	public long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
}
