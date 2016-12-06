package app.batch;

import java.util.HashMap;
import java.util.Map;

import sun.rmi.log.LogHandler;

/**
 * 批量控制类
 * 
 * @author chenzhoujin
 * 
 */
public class BatchServices {

	/**
	 * 同步rpt_putstat
	 * @throws Exception
	 */
	public void dobatch() throws Exception {
			/* Map<String, Object> conditionMap=new HashMap<String, Object>();
			 try{
			 String querysql="insert into RPT_PUTSTAT "
		 		+" select * from LOAN_AC_LN_MST jn where  NOT EXISTS (  "
		 		+" select * from RPT_PUTSTAT gn "
		 		+"  where  concat(gn.prdt_no ,gn.pact_amt) = "
		 		+" concat(jn.pact_kind,jn.amt)) ";
			 super.getGiantBaseDao().runUpdateSql(querysql, conditionMap);
			 LogHandler.systems(LogEnums.COMMANDS,LogEnums.COMMANDS.getRemark()+"定时任务成功", null, super.getGiantBaseDao());
			 }catch (Exception e) {
				 LogHandler.systems(LogEnums.COMMANDS,LogEnums.COMMANDS.getRemark()+"定时任务失败", null, super.getGiantBaseDao());
				// TODO: handle exception
				 e.printStackTrace();
			}*/
			 System.out.println("nagios_commands");
			 
		
		
		
		
		
/*
		// ApplicationContext ac = new ClassPathXmlApplicationContext(
		// "applicationContext_batch.xml");

		// ac = SpringFactory;

		Log log = LogFactory.getLog(getClass());
		UpdateGlobalBO bo = (UpdateGlobalBO) SpringFactory.getBean("updateGlobalBO");
		bo.UpdateGlobalP(); // 初始化批量步骤
		log.info("初始化批量日志步骤完成");

		// saveRiskFive
		log.info("更新借据信息~~~");
		UpdateSelectDueBO due = (UpdateSelectDueBO) SpringFactory.getBean("updateSelectDueBO");
		due.UpdateSelectDueP();

		log.info("更新借据合同余额信息~~~");
		UpdateDueBalBO duebal = (UpdateDueBalBO) SpringFactory.getBean("updateDueBalBO");
		duebal.UpdateDueBalP();

		UpdateCrXdBO xd = (UpdateCrXdBO) SpringFactory.getBean("updateCrXdBO");
		xd.UpdateCrXdP();
		log.info("CR_XD表更新完毕！");

		log.info("更新五级分类");
		FiveClassBo five = (FiveClassBo) SpringFactory.getBean("fiveClassBo");
		five.updateFiveSts();

		log.info("更新月报表month.UpdateMonthP()");
		UpdateMonthBO month = (UpdateMonthBO) SpringFactory.getBean("updateMonthBO");
		month.UpdateMonthP();

		// 更新月报表
		log.info("更新月报表month2.UpdateAppMonth()");
		UpdateMonthBO month2 = (UpdateMonthBO) SpringFactory.getBean("updateMonthBO");

		// 借款信息汇总表
		log.info("借款信息汇总表~~~");
		RptLoanstatBO stat = (RptLoanstatBO) SpringFactory.getBean("rptLoanBO");
		stat.ExecTrade();

		// 借款信息汇总表
		log.info("贷款投向汇总表~~~");
		RptTradeBO trade = (RptTradeBO) SpringFactory.getBean("rptTradeBO");
		trade.ExecTrade();

		// 短信通知--贷款到期通知和逾期通知
		// System.out.println("短信通知");
		// SendMessageBO sendMessage = (SendMessageBO)
		// ac.getBean("sendMessageBO");
		// sendMessage.loanNotice();

		log.info("资金投向明细表");
		RptzjtxBO rptzjtx = (RptzjtxBO) SpringFactory.getBean("rptzjtxBO");
		rptzjtx.ExecTrade();

		// 信誉等级明细表
		// System.out.println("信用等级情况统计表");
		// RptxydjBO rptxydj = (RptxydjBO) ac.getBean("rptxydjBO");
		// rptxydj.ExecTrade();

		// 五级分类汇总表
		log.info("五级分类汇总表");
		RptwjflBO rptwjfl = (RptwjflBO) SpringFactory.getBean("rptwjflBO");
		rptwjfl.ExecTrade();

		CollectBO collect = (CollectBO) SpringFactory.getBean("collectBO");
		// 催收转阶段
		log.info("催收转阶段");

		collect.ExecShare();
		// 催收任务分配
		log.info("催收任务分配");
		collect.ExecTask();

//		// 贷后检查报告提醒表
//		log.info("贷后检查报告提醒表");
//		collect.ExecTrade();
*/
	}

	public static void main(String[] args) throws Exception {
		System.out.println("start-------------------");
		BatchServices batch = new BatchServices();
		batch.dobatch();
		System.out.println("end-------------------");
		System.exit(0);
	}
}
