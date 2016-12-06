package com.jiangchuanbanking.SMS.vo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;





import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jiangchuanbanking.SMS.action.FileDownloadAction;
import com.jiangchuanbanking.SMS.cn.SingletonClient;
import com.jiangchuanbanking.alert.domain.AlertItem;
import com.jiangchuanbanking.alert.domain.SmsData;
import com.jiangchuanbanking.alert.service.ISmsDataService;
import com.jiangchuanbanking.alert.service.impl.SmsDataService;


public class SmsDeal {
//	private static ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext( new String[] { "classpath:applicationContext-alertItem.xml" });
	private DataSource dataSource;
	private ISmsDataService smsDataService   ;

	public void expireRemind() throws Exception {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		AlertItem smsParm = null;
		SmsData smsData = null;
		String overstr = "";
		String result = "";
		String rflag = "0";
		int i=-1;
		ArrayList<AlertItem> smslist = null;
		ArrayList<SmsData> datalist = new ArrayList<SmsData>();
		try {
			InputStream is = FileDownloadAction.class.getClassLoader().getResourceAsStream("config.properties");
			Properties prop = new Properties();
			prop.load(is);
			String  disjunctor = prop.getProperty("sms.disjunctor");
			smslist = this.getAllSms("1");//短信
			if (smslist != null && smslist.size() > 0) {
				for (AlertItem smsParmBean : smslist) {
					if (StringUtils.equals("expire", smsParmBean.getType())) {
						smsParm = smsParmBean;
					} 
				}
			}
			overstr = smsParm.getAdv_days();//prop.getProperty("sms.repay1");
			datalist = this.getCustomerInfs("expire", overstr);

			if (datalist != null && datalist.size() > 0) {
				for (SmsData bean : datalist) {
					String overParm="";
					overParm=smsParm.getAlert_content();
					if (overParm != null) {
//						尊敬的$cif_name$： 
//						 你于$year$年$month$月$day$日购买的江川理财（合同编号）将于$yearOver$年$monthOver$月$dayOver$日到期，
//						届时预计本息收益总计$bxhj$元。如您需办理赎回或续存业务，请您自收到本信息之日起7日内联系您的客户经理。如已联系，请忽略此信息。【江川财富】
						overParm=overParm.replaceAll("\\$cif_name\\$", String.valueOf(bean.getCif_name()));
						String begdate[]=dateFormat.format(bean.getBeg_date()).split("-");
						overParm=overParm.replaceAll("\\$year\\$", String.valueOf(begdate[0]));
						overParm=overParm.replaceAll("\\$month\\$", String.valueOf(begdate[1]));
						overParm=overParm.replaceAll("\\$day\\$", String.valueOf(begdate[2]));
						String enddate[]=dateFormat.format(bean.getEnd_date()).split("-");
						overParm=overParm.replaceAll("\\$yearOver\\$", String.valueOf(enddate[0]));
						overParm=overParm.replaceAll("\\$monthOver\\$", String.valueOf(enddate[1]));
						overParm=overParm.replaceAll("\\$dayOver\\$", String.valueOf(enddate[2]));
						
						overParm=overParm.replaceAll("\\$bxhj\\$","123");
						System.out.println("-------------------------" + bean.getMobile()+ "--" + overParm  + "-------------");
						i = SingletonClient.getClient().sendSMS(new String[] { bean.getMobile() }, overParm , "", 5);
						result = String.valueOf(i);
						System.out.println(result);
						if (!result.startsWith("-") && !result.equals("")) 
						{
							rflag = "1";
						}
						SmsData	smsDataBean = new SmsData();
						smsDataBean.setAlert_content(overParm);
						smsDataBean.setMobile(StringUtils.isBlank(bean.getMobile()) ? "无" : bean.getMobile());
						smsDataBean.setSend_sts(result);
						smsDataBean.setCif_name(bean.getCif_name());
						smsDataBean.setCif_no(bean.getCif_no());
						smsDataBean.setCreate_date(time.format(new Date()));
						smsDataBean.setCreate_op("admin");
						smsDataBean.setPact_no(bean.getPact_no());
						smsDataBean.setAlert_method(smsParm.getAlert_method());
						smsDataBean.setAlert_type(smsParm.getAlert_type());
						//this.insertSmsData(smsDataBean);
						smsDataService.insert(smsDataBean);
						smsDataBean = null;
					}

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String getBxhj(SmsData bean) {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<AlertItem> getAllSms(String flg) {
		ArrayList<AlertItem> list = new ArrayList<AlertItem>();
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT ID,	ALERT_METHOD,ALERT_TYPE,ADV_DAYS,ALERT_CONTENT,DEL_FLG,CREATE_OP,CREATE_DATE,"
					+ "CMT ,TYPE FROM WEALTH_ALERTS_ITEM WHERE DEL_FLG='0' AND ALERT_METHOD='"+flg+"'");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				AlertItem smsParm = new AlertItem();
				smsParm.setId(rs.getInt("ID")); 
				smsParm.setAlert_method(rs.getString("ALERT_METHOD"));
				smsParm.setAlert_type(rs.getString("ALERT_TYPE"));
				smsParm.setAdv_days(rs.getString("ADV_DAYS"));
				smsParm.setAlert_content(rs.getString("ALERT_CONTENT"));
				smsParm.setDel_flg(rs.getString("DEL_FLG"));
				smsParm.setCreate_date(rs.getString("CREATE_DATE"));
				smsParm.setCreate_op(rs.getString("CREATE_OP"));
				smsParm.setCmt(rs.getString("CMT"));
				smsParm.setType(rs.getString("TYPE"));
				list.add(smsParm);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// 获取客户信息
	public ArrayList<SmsData> getCustomerInfs(String flag, String str) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<SmsData> list = new ArrayList<SmsData>();
		String datas[] = null;
		String dataStr = "";
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (StringUtils.equals("expire", flag)) {//到期提醒
				if (StringUtils.isNotBlank(str)) {
					datas = str.split("@");
				}
				if (datas != null && datas.length > 0) {
					for (int i = 0; i < datas.length; i++) {
						if (StringUtils.equals("", dataStr)) {
							dataStr += "('" + datas[i];
						} else {
							dataStr += "','" + datas[i];
						}
					}
					if (!StringUtils.equals("", dataStr)) {
						dataStr += "')";
					}
					map.put("rdate", dateFormat.format(new Date()));
					map.put("datas", dataStr);
					list = this.getExpireCus(map);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return (ArrayList<SmsData>) list;
	}
	public ArrayList<SmsData> getExpireCus(Map<String, String> map) throws SQLException {
		Connection con = dataSource.getConnection();
		ArrayList<SmsData> list = new ArrayList<SmsData>();
		try {

//select a.cif_no CIF_NO,a.cif_name CIF_NAME,a.pact_no PACT_NO,a.rate RATE,a.pact_amt PACT_AMT,a.payment_type PAYMENT_TYPE
// from wealth_pact_info a ,wealth_cif_info b where a.cif_no=b.id and a.sts='6' and
//TO_CHAR(to_date(TO_CHAR(a.end_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE('20150520','yyyy-mm-dd') ) in ('15')
			String sql = "select a.cif_no CIF_NO,a.cif_name CIF_NAME,a.pact_no PACT_NO,a.rate RATE,a.pact_amt PACT_AMT,a.payment_type PAYMENT_TYPE "
					+ " ,b.contact_tel CELL ,a.start_date BEGIN_DATE,a.end_date END_DATE ,a.term_range TERM from wealth_pact_info a "
					+ ",wealth_cif_info b where a.cif_no=b.id and a.sts in ( '6','83','84') and  "
					+ "TO_CHAR(to_date(TO_CHAR(a.end_date, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE('" 
					+ map.get("rdate") + "','yyyy-mm-dd') ) in " + map.get("datas");
			System.out.println(sql);
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SmsData smsDataBean = new SmsData();
				smsDataBean.setCif_no(rs.getString("CIF_NO"));
				smsDataBean.setCif_name(rs.getString("CIF_NAME"));
				smsDataBean.setPact_no(rs.getString("PACT_NO"));;
				smsDataBean.setRate(rs.getString("RATE"));
				smsDataBean.setPact_amt(rs.getString("PACT_AMT"));
				smsDataBean.setPayment_type(rs.getString("PAYMENT_TYPE"));
				smsDataBean.setMobile(rs.getString("CELL"));
				smsDataBean.setBeg_date(rs.getDate("BEGIN_DATE"));
				smsDataBean.setEnd_date(rs.getDate("END_DATE"));
				smsDataBean.setTerm(rs.getString("TERM"));
				list.add(smsDataBean);
			}
			rs.close();
			ps.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			con.close();
		}
		return list;
	}
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public ISmsDataService getSmsDataService() {
		return smsDataService;
	}
	public void setSmsDataService(ISmsDataService smsDataService) {
		this.smsDataService = smsDataService;
	}
	

}
