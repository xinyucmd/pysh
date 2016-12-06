package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.dao.FundRecordDao;
/**
 * 资金记录  
 * @author C_J
 *
 */
public class FundrecordService  extends  BaseService{
	
	public static Log log = LogFactory.getLog(FundrecordService.class);
	private FundRecordDao  fundRecordDao;
	
	/**
	 * 查询收/支
	 * @param pageBean
	 * @param buget
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public  void  queryfundrecord_buget (PageBean  pageBean,String userName) throws Exception{
		userName = Utility.filteSqlInfusion(userName);
		
		Connection  conn  = MySQL.getConnection();
		StringBuffer  condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append("  and username = '"+StringEscapeUtils.escapeSql(userName)+"' ");
		}
		try {
			dataPage(conn, pageBean, " v_fundrecord_buget  ", " * ", "", condition+"");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 查询收入/支出  操作金额
	 * @param buget
	 * @param userName
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String>  queryAmountSum(int buget,String  userName) throws Exception{
		Connection  conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String, String>();
		try {
			map = fundRecordDao.queryAmountSum(conn,userName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		
		return map;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}
	
}
