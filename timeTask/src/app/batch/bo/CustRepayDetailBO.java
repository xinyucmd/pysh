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
		// ��ȡ�л����¼�Ľ�ݺ�list
		List<RepayBean> repayBeansDueNo = repayServiceImpl
				.getDistinctRepayBeans(repayBeanTemp);
		for (RepayBean repayBean : repayBeansDueNo) {
			CustRepayDetails custRepayDetails = new CustRepayDetails();
			String dueNo = repayBean.getDueNo();
			String yhkze = "0.00";// Ӧ�����ܶ�
			String sjhkze = "0.00";// ʵ�ʻ����ܶ�
			// ��ȡ����ƻ�list
			PlanBean planBean = new PlanBean();
			planBean.setDueNo(dueNo);
			List<PlanBean> planBeanList = repayServiceImpl
					.getPlanBeanList(planBean);
			// ��ȡĳһ���ӵĻ����¼list
			RepayBean repayBeanOne = new RepayBean();
			repayBeanOne.setDueNo(dueNo);
			List<RepayBean> repayBeanOneList = repayServiceImpl
					.getRepayBeanss(repayBeanOne);
			int termNo_jh = 0;// ����ƻ���������
			int termNo_jl = 0;// �����¼��������
			int sumCount_hk = 0;// ʵ�ʻ����ܴ���
			double qigong_ye = 0.00;// �ڹ����
			double sjhk = 0.00;// ʵ�ʻ���
			long over_days = 0;// ��������
			String jq_days = "0.00";//��Ȩ����
			String eachYhze = "0.00";// ����ƻ���ÿ�ڵ��ڹ�
			String eachShze = "0.00";// �����¼��ÿ�ڵ�ʵ���ܶ�
			String eachCe="0.00";//ÿ�λ�����ƻ����
			int ysterm_no=0;//�м���������
			custRepayDetails.setDue_no(dueNo);
//			if (dueNo=="³�͸�130400006001"||dueNo=="C1173701022013062511646001") {
				
			
			for (int i = 0; i < planBeanList.size(); i++) {
				eachYhze = BigNumberUtil.Add(planBeanList.get(i)
						.getReturnCapital(), planBeanList.get(i)
						.getReturnInterest());//2000
				custRepayDetails.setTerm_no((i+1)+"");//����
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
						//ʵ��>=�ڹ�
						eachCe=BigNumberUtil.Subtract(eachShze, eachYhze);//300
						custRepayDetails.setTerm_no((i+1)+"");//��ֵ����
						custRepayDetails.setTerm_amt(eachYhze);//��ֵ���
						if (getQuot(repayBeanOneList.get(j).getOccDate(),planBeanList.get(i).getEndDate())>0) {
							//�������ڣ�ʵ�ʻ����մ��ڻ���ƻ���
							over_days=getQuot(repayBeanOneList.get(j).getOccDate(),planBeanList.get(i).getEndDate());
							jq_days = BigNumberUtil.add2String(jq_days, BigNumberUtil.Divide2(BigNumberUtil.Multiply(over_days+"", eachShze),eachYhze));
							custRepayDetails.setOver_days(jq_days);//��ֵ��������
						}else {
							custRepayDetails.setOver_days("0");//δ��������
						}
						eachShze=eachCe;//���໹�Ľ��ŵ���һ��ѭ������ʹ��
						over_days=0;//���м������������
						jq_days="0";//���м������������
						ysterm_no=j+1;
						custRepayDetails.setTerm_date(repayBeanOneList.get(j).getOccDate());
						if (repayServiceImpl.findCustRepayDetailsList(custRepayDetails).size()!=0) {
							//repayServiceImpl.updateREpayDetails(custRepayDetails);
							System.out.println("��ݺţ�"+dueNo+"������"+custRepayDetails.getTerm_no()+"==�Ѵ��ڣ���");
							break;
						}else {
							repayServiceImpl.insertREpayDetails(custRepayDetails);
							System.out.println("��ݺţ�"+dueNo+"������"+custRepayDetails.getTerm_no()+"==�����ɹ�����");
						}
						
						break;
					}else {
						//ʵ��<�ڹ�
						eachCe=BigNumberUtil.Subtract(eachYhze, eachShze);//500,200
							//�ۼӺ��ǲ���
							if (getQuot(repayBeanOneList.get(j).getOccDate(),planBeanList.get(i).getEndDate())>0) {
								//�������ڣ�ʵ�ʻ����մ��ڻ���ƻ���
								over_days=getQuot(repayBeanOneList.get(j).getOccDate(),planBeanList.get(i).getEndDate());
								jq_days = BigNumberUtil.add2String(jq_days, BigNumberUtil.Divide2(BigNumberUtil.Multiply(over_days+"", eachShze),eachYhze));
								custRepayDetails.setOver_days(jq_days);//��ֵ��������
							}else {
								custRepayDetails.setOver_days("0");//δ��������
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
