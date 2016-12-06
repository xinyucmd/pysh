package app.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
/**
 * 
 * @author zqc
 * ����:����������յ��Ŵ������������
 */
public class LoanBatch extends	QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		BatchServices batchServices =  new BatchServices();
		try {
			batchServices.dobatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
