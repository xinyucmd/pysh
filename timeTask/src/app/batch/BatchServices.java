package app.batch;

import java.util.HashMap;
import java.util.Map;

import sun.rmi.log.LogHandler;

/**
 * ����������
 * 
 * @author chenzhoujin
 * 
 */
public class BatchServices {

	/**
	 * ͬ��rpt_putstat
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
			 LogHandler.systems(LogEnums.COMMANDS,LogEnums.COMMANDS.getRemark()+"��ʱ����ɹ�", null, super.getGiantBaseDao());
			 }catch (Exception e) {
				 LogHandler.systems(LogEnums.COMMANDS,LogEnums.COMMANDS.getRemark()+"��ʱ����ʧ��", null, super.getGiantBaseDao());
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
		bo.UpdateGlobalP(); // ��ʼ����������
		log.info("��ʼ��������־�������");

		// saveRiskFive
		log.info("���½����Ϣ~~~");
		UpdateSelectDueBO due = (UpdateSelectDueBO) SpringFactory.getBean("updateSelectDueBO");
		due.UpdateSelectDueP();

		log.info("���½�ݺ�ͬ�����Ϣ~~~");
		UpdateDueBalBO duebal = (UpdateDueBalBO) SpringFactory.getBean("updateDueBalBO");
		duebal.UpdateDueBalP();

		UpdateCrXdBO xd = (UpdateCrXdBO) SpringFactory.getBean("updateCrXdBO");
		xd.UpdateCrXdP();
		log.info("CR_XD�������ϣ�");

		log.info("�����弶����");
		FiveClassBo five = (FiveClassBo) SpringFactory.getBean("fiveClassBo");
		five.updateFiveSts();

		log.info("�����±���month.UpdateMonthP()");
		UpdateMonthBO month = (UpdateMonthBO) SpringFactory.getBean("updateMonthBO");
		month.UpdateMonthP();

		// �����±���
		log.info("�����±���month2.UpdateAppMonth()");
		UpdateMonthBO month2 = (UpdateMonthBO) SpringFactory.getBean("updateMonthBO");

		// �����Ϣ���ܱ�
		log.info("�����Ϣ���ܱ�~~~");
		RptLoanstatBO stat = (RptLoanstatBO) SpringFactory.getBean("rptLoanBO");
		stat.ExecTrade();

		// �����Ϣ���ܱ�
		log.info("����Ͷ����ܱ�~~~");
		RptTradeBO trade = (RptTradeBO) SpringFactory.getBean("rptTradeBO");
		trade.ExecTrade();

		// ����֪ͨ--�����֪ͨ������֪ͨ
		// System.out.println("����֪ͨ");
		// SendMessageBO sendMessage = (SendMessageBO)
		// ac.getBean("sendMessageBO");
		// sendMessage.loanNotice();

		log.info("�ʽ�Ͷ����ϸ��");
		RptzjtxBO rptzjtx = (RptzjtxBO) SpringFactory.getBean("rptzjtxBO");
		rptzjtx.ExecTrade();

		// �����ȼ���ϸ��
		// System.out.println("���õȼ����ͳ�Ʊ�");
		// RptxydjBO rptxydj = (RptxydjBO) ac.getBean("rptxydjBO");
		// rptxydj.ExecTrade();

		// �弶������ܱ�
		log.info("�弶������ܱ�");
		RptwjflBO rptwjfl = (RptwjflBO) SpringFactory.getBean("rptwjflBO");
		rptwjfl.ExecTrade();

		CollectBO collect = (CollectBO) SpringFactory.getBean("collectBO");
		// ����ת�׶�
		log.info("����ת�׶�");

		collect.ExecShare();
		// �����������
		log.info("�����������");
		collect.ExecTask();

//		// �����鱨�����ѱ�
//		log.info("�����鱨�����ѱ�");
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
