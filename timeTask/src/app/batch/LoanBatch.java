package app.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
/**
 * 
 * @author zqc
 * 功能:将柔和在日终的信贷批量抽离出来
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
