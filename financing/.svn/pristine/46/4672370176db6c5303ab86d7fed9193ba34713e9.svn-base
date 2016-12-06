package com.jiangchuanbanking.alert.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jiangchuanbanking.SMS.cn.SingletonClient;
import com.jiangchuanbanking.SMS.cn.TestSDKClient;
import com.jiangchuanbanking.alert.dao.IAlertItemDao;
import com.jiangchuanbanking.alert.dao.ISmsDataDao;
import com.jiangchuanbanking.alert.dao.impl.SmsDataDao;
import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.alert.domain.SmsData;
import com.jiangchuanbanking.alert.service.IAlertItemService;
import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.impl.BaseService;

/**
 * Call service
 */
public class AlertItemService extends BaseService<AlertItem> implements
		IAlertItemService {

	private IAlertItemDao alertDao;
	private IBaseDao<AlertItem> baseDao;
	private AlertItem alertItem;
	private SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ISmsDataDao smsDataDao ;
	public List<AlertItem> findScheduleEntitys() throws Exception {
		return alertDao.findScheduleEntitys();
	}

	public IBaseDao<AlertItem> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao<AlertItem> baseDao) {
		this.baseDao = baseDao;
	}

	public IAlertItemDao getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(IAlertItemDao alertDao) {
		this.alertDao = alertDao;
	}

	public List<AlertItem> findItem(AlertItem alert) throws Exception {
		// TODO Auto-generated method stub
		return alertDao.findItem(alert);
	}

	public void alarm(AlertItem alertItem, String[] mobile) throws Exception {
		alertItem = this.getAlertItem();
		TestSDKClient.testSendSMS();
		for (String no : mobile) {
			alertItem.getAlert_content().replace("$cif_name$", "");// ....参数待定
			alertItem.getAlert_content().replace("$year1$", "");
			alertItem.getAlert_content().replace("$month1$", "");
			alertItem.getAlert_content().replace("$day1$", "");
			alertItem.getAlert_content().replace("$year2$", "");
			alertItem.getAlert_content().replace("$month2$", "");
			alertItem.getAlert_content().replace("$day2$", "");
			alertItem.getAlert_content().replace("$sum_amt$", "");
			int i = SingletonClient.getClient().sendSMS(new String[] { no },
					alertItem.getAlert_content(), "", 5);// 带扩展码
			System.out.println(i);
			//发送短信记录
			SmsData data = new SmsData();
			data.setAlert_content(alertItem.getAlert_content());
			data.setAlert_method(alertItem.getAlert_method());
			data.setAlert_type(alertItem.getAlert_type());
			data.setMobile(no);
			String result = String.valueOf(i);
			String rflag = "0";
			if (!result.startsWith("-") && !result.equals(""))// 发送短信，如果是以负号开头就是发送失败。
			{
				rflag = "1";
			}
			data.setSend_sts(rflag);
			data.setCreate_date(time.format(new Date()));
			data.setCreate_op(alertItem.getCreate_op());
			data.setCmt(alertItem.getCmt());
			data=smsDataDao.makePersistent(data);
		}

	}

	public AlertItem getAlertItem() {
		return alertItem;
	}

	public void setAlertItem(AlertItem alertItem) {
		this.alertItem = alertItem;
	}

	public AlertItem getItem(AlertItem alert) throws Exception {
		return alertDao.getItem(alert);
	}

	public ISmsDataDao getSmsDataDao() {
		return smsDataDao;
	}

	public void setSmsDataDao(ISmsDataDao smsDataDao) {
		this.smsDataDao = smsDataDao;
	}

}
