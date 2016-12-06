package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.batch.BatchPublic;
import app.util.exc.DataSourceUtils;

/**
 * 
 * 功能描述：借款按投向汇总
 * 
 * @author dhcc wangshanfang
 * @date Oct 23, 2012
 * @see
 * @修改日志：
 * 
 */
public class RptTradeDAO {
	Log log = LogFactory.getLog(this.getClass());
	private Connection conn;
	private String mon_date;

	public RptTradeDAO(Connection conn) {
		this.conn = conn;
		initDate();
	}

	// 获取日期
	public void initDate() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SUBSTR(SYS_DATE,1,6)||'01' FROM SYS_GLOBAL");
		try {
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				this.mon_date = rs.getString(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
	}

	// 删除本期数据
	public int deleteData() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("DELETE FROM RPT_TRADE WHERE MON_DATE=?");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("M", "M01", "1", conn);
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("M", "M01", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	// 插入基层社数据
	public int insertData() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO RPT_TRADE(MON_DATE,BR_NO,BR_LEV,TRADE_NO,USE_NO,VOU_TYPE,BAL,AMT,APP_OP_NO) ");
		sb.append("SELECT ?,BR_NO,'0',LN_TRADE_NO,LN_USE,VOU_TYPE,SUM(BAL),");
		sb.append("SUM(CASE SUBSTR(BEG_DATE,1,6) WHEN ? THEN DUE_AMT ELSE 0.00 END) ");
		sb.append("FROM RPT_XD GROUP BY BR_NO,LN_TRADE_NO,LN_USE,VOU_TYPE");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			ps.setString(2, mon_date.substring(0, 6));
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("M", "M02", "1", conn);
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("M", "M02", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	// 判断是否有汇总机构
	public boolean hasNextLev(int lev) {
		boolean result = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(*) FROM RPT_TRADE WHERE BR_LEV=? AND BR_NO IN (");
		sb.append("SELECT BR_NO FROM TBL_ORG_DEPARTMENTS WHERE BR_NO<>'10000' ");
		sb.append("CONNECT BY UP_ONE =PRIOR BR_NO START WITH BR_NO ='10000')");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, String.valueOf(lev));
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1) >= 1;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("=======hasNextLev========" + result);
		return result;
	}

	// 汇总数据
	public void insertTotalData() {
		int lev = 0;
		while (true) {
			if (hasNextLev(lev)) {
				insertDataByLev(lev);
			} else {
				break;
			}
			lev++;
		}
		BatchPublic.inBatchlog("M", "M03", "1", conn);
		log.info("=======insertTotalData END========");
	}

	// 按级别汇总数据
	public int insertDataByLev(int lev) {
		log.info("=======insertDataByLev lev========" + lev);
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO RPT_TRADE(MON_DATE,BR_NO,BR_LEV,TRADE_NO,USE_NO,VOU_TYPE,BAL,AMT) ");
		sb.append("SELECT A.MON_DATE,B.UP_ONE,?,A.TRADE_NO,A.USE_NO,A.VOU_TYPE,SUM(A.BAL),SUM(A.AMT) ");
		sb.append("FROM RPT_TRADE A,TBL_ORG_DEPARTMENTS B WHERE A.BR_NO=B.BR_NO AND A.MON_DATE=? AND A.BR_LEV=? ");
		sb.append("GROUP BY A.MON_DATE,B.UP_ONE,A.TRADE_NO,A.USE_NO,A.VOU_TYPE");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, String.valueOf(lev + 1));
			ps.setString(2, mon_date);
			ps.setString(3, String.valueOf(lev));
			count = ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("M", "M03", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("=======insertDataByLev========" + count);
		return count;
	}
}
