package com.sp2p.dao.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_user;
import com.sp2p.util.DateUtil;

/**
 * 发送短信
 * 
 * @author Administrator
 * 
 */
public class SendSMSDao {

	/**
	 * 添加短信内容
	 * 
	 * @param conn
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMessageSMS(Connection conn,String message)
			throws SQLException, DataException {
		Dao.Tables.t_sendsms sms=new Dao().new Tables().new t_sendsms();
	    sms.content.setValue(message);

		return sms.insert(conn);

	}

	/**
	 * 删除短信发送
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteMessageSMS(Connection conn, Long id) throws SQLException,
			DataException {
		Dao.Tables.t_sendsms sms=new Dao().new Tables().new t_sendsms();
		return sms.delete(conn,"id="+id);
	}


	/**
	 * 更新网短信内容
	 * 
	 * @param conn
	 * @param id
	 * @param sort
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param publisher
	 * @param visits
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateMessageSMS(Connection conn, Long id,String content) throws SQLException, DataException {
		Dao.Tables.t_sendsms sms=new Dao().new Tables().new t_sendsms();
		
		
		if (StringUtils.isNotBlank(content)) {
			sms.content.setValue(content);
		}
		
	

		return sms.update(conn, "id=" + id);

	}
	/**
	 * 发送短信，保存短信
	 * @param conn
	 * @param id
	 * @param content
	 * @param cellPhones
	 * @param sendUrl
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws DocumentException 
	 */
	public Long SendSMSs(Connection conn,String content,String splitID,String cellPhones)
	throws SQLException,DataException,IOException, DocumentException{
		
	
	
		
			//保存数据
			Dao.Tables.t_sendsms sms=new Dao().new Tables().new t_sendsms();
			sms.content.setValue(content);
			sms.splitId.setValue(splitID);
			sms.splitPhone.setValue(cellPhones);
			sms.status.setValue(2);
			sms.sendTime.setValue(new Date());
			
			return sms.insert(conn);
			
		
		
		
		
		
	}
	/**
	 * 获取发送短信详情
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getSendSMSByDetail(Connection conn,Long id)throws SQLException,DataException{
		Dao.Tables.t_sendsms sms=new Dao().new Tables().new t_sendsms();
		DataSet dataSet = sms.open(conn, "*", " id=" + id,
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 发送短信，保存短信
	 * @param conn
	 * @param id
	 * @param content
	 * @param cellPhones
	 * @param sendUrl
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws DocumentException 
	 */
	public Long insertSmsRecord(Connection conn,String phone,String content,String methods, String ipAddr)
			throws SQLException,DataException,IOException, DocumentException{
			//保存数据
			Dao.Tables.t_sms_record t_sms_record=new Dao().new Tables().new t_sms_record();
			t_sms_record.phone.setValue(phone);
			t_sms_record.create_time.setValue(new Date());
			t_sms_record.content.setValue(content);
			t_sms_record.methods.setValue(methods);
			t_sms_record.ipAddr.setValue(ipAddr);
			
			if("13718853545".equals(phone) || "13321152594".equals(phone)){
				return 0L;
			}
			return t_sms_record.insert(conn);
	}
	
	public List<Map<String,Object>> getNowDaySmsRecordList(Connection conn,String phone) throws SQLException, DataException{
		StringBuffer condition = new StringBuffer();
		condition.append(" 1=1");
		Dao.Tables.t_sms_record t_sms_record = new Dao().new Tables().new t_sms_record();
		if (StringUtils.isNotBlank(phone)){
			condition.append(" and phone='"+phone+"'");
		}
		String nowStr = DateUtil.dateToStringYYMMDD(new Date());
		condition.append(" and DATE_FORMAT(create_time,'%Y-%m-%d') = '"+nowStr+"'");
		DataSet dataSet = t_sms_record
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
}
