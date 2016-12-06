package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.batch.BatchPublic;
import app.util.exc.DataSourceUtils;

public class RptLoanstatDAO {
	Log log = LogFactory.getLog(this.getClass());
	private Connection conn;
	private String mon_date;

	public RptLoanstatDAO(Connection conn) {
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

	// 删除本期数据 -- 借款余额汇总数据
	public int deleteData1() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("DELETE FROM RPT_LOANSTAT WHERE MON_DATE=?");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("N", "N01", "1", conn);
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("N", "N01", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	// 删除本期数据 -- 放款汇总数据
	public int deleteData2() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("DELETE FROM RPT_PUTSTAT WHERE MON_DATE=?");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("N", "N02", "1", conn);
		} catch (Exception e) {
			BatchPublic.inBatchlog("N", "N02", "2", conn);
			count=-1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	
	// 删除本期数据 -- 逾期数据
	public int deleteData3() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("DELETE FROM RPT_OVERSTAT WHERE MON_DATE=?");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("N", "N03", "1", conn);
		} catch (Exception e) {
			BatchPublic.inBatchlog("N", "N03", "2", conn);
			count=-1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	
	// 插入借款余额数据
	public int insertLoanData() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO RPT_LOANSTAT(MON_DATE,BR_NO,BR_LEV,PRDT_NO,VOU_TYPE,BAL,DUE_AMT,REPAY_AMT,OTHER_AMT,APP_OP_NO) ");
		sb.append("SELECT ?,BR_NO,'0',PRDT_NO,VOU_TYPE,SUM(BAL),");
		sb.append("SUM(CASE SUBSTR(BEG_DATE,1,6) WHEN ? THEN DUE_AMT ELSE 0.00 END),SUM(PAY_BAL),0,APP_OP_NO");
		sb.append("FROM RPT_XD GROUP BY BR_NO,PRDT_NO,VOU_TYPE,APP_OP_NO");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			ps.setString(2, mon_date.substring(0, 6));
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("N", "N04", "1", conn);
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("N", "N04", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	// 插入本期放款数据
	public int insertPutData() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO RPT_PUTSTAT(MON_DATE,BR_NO,BR_LEV,PRDT_NO,VOU_TYPE,TERM_TYPE,AMT,CNT,APP_OP_NO) ");
		sb.append("SELECT ?,BR_NO,'0',PRDT_NO,VOU_TYPE,CASE WHEN TERM_MON<12 THEN 1 WHEN TERM_MON=12 THEN 2 WHEN TERM_MON>12 THEN 3 END,");
		sb.append("SUM(DUE_AMT),COUNT(*),APP_OP_NO ");
		sb.append("FROM RPT_XD WHERE BEG_DATE LIKE ? GROUP BY BR_NO,PRDT_NO,VOU_TYPE,APP_OP_NO,");
		sb.append("CASE WHEN TERM_MON<12 THEN 1 WHEN TERM_MON=12 THEN 2 WHEN TERM_MON>12 THEN 3 END");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			ps.setString(2, mon_date.substring(0, 6)+"%");
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("N", "N05", "1", conn);
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("N", "N05", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	
	// 插入逾期数据
	public int insertOverData() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO RPT_OVERSTAT(MON_DATE,BR_NO,BR_LEV,PRDT_NO,VOU_TYPE,TERM_TYPE,OVER_TYPE,BAL,CNT) ");
		sb.append("SELECT ?, BR_NO,'0',PRDT_NO,VOU_TYPE,");
		sb.append("CASE WHEN TERM_MON<12 THEN 1 WHEN TERM_MON=12 THEN 2 WHEN TERM_MON>12 THEN 3 END,");
		sb.append("CASE WHEN OVER_DAY<=5 THEN 1 WHEN OVER_DAY>30 THEN 3 ELSE 2 END,SUM(BAL),COUNT(*) ");
		sb.append("FROM RPT_XD WHERE BAL>0 AND OVER_DAY>=1 ");
		sb.append("GROUP BY BR_NO,PRDT_NO,VOU_TYPE,");
		sb.append("CASE WHEN TERM_MON<12 THEN 1 WHEN TERM_MON=12 THEN 2 WHEN TERM_MON>12 THEN 3 END,");
		sb.append("CASE WHEN OVER_DAY<=5 THEN 1 WHEN OVER_DAY>30 THEN 3 ELSE 2 END");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("N", "N06", "1", conn);
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("N", "N06", "2", conn);
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
		sb.append("CONNECT BY UP_ONE=PRIOR BR_NO START WITH BR_NO ='10000')");
		
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
	
	// 汇总余额数据
	public int insertTotalLoanData() {
		int lev = 0;
		int count=0;
		while (true) {
			if (hasNextLev(lev)) {
				count=insertLoanDataByLev(lev);
				if(count==-1){
					return count;
				}
			} else {
				break;
			}
			lev++;
		}
		BatchPublic.inBatchlog("N", "N07", "1", conn);
		log.info("=======insertTotalData END========");
		return count;
	}
	
	public int insertLoanDataByLev(int lev){
		log.info("=======insertDataByLev lev========" + lev);
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO RPT_LOANSTAT(MON_DATE,BR_NO,BR_LEV,PRDT_NO,VOU_TYPE,BAL,DUE_AMT,REPAY_AMT,OTHER_AMT) ");
		sb.append("SELECT A.MON_DATE,B.UP_ONE,?,A.PRDT_NO,A.VOU_TYPE,SUM(A.BAL),SUM(A.DUE_AMT),SUM(A.REPAY_AMT),SUM(A.OTHER_AMT) ");
		sb.append("FROM RPT_LOANSTAT A,TBL_ORG_DEPARTMENTS B WHERE A.BR_NO=B.BR_NO AND A.MON_DATE=? AND A.BR_LEV=? ");
		sb.append("GROUP BY A.MON_DATE,B.UP_ONE,A.PRDT_NO,A.VOU_TYPE");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, String.valueOf(lev + 1));
			ps.setString(2, mon_date);
			ps.setString(3, String.valueOf(lev));
			count = ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("N", "N07", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("=======insertDataByLev========" + count);
		return count;
	}
	
	// 汇总放款数据
	public int insertTotalPutData() {
		int lev = 0;
		int count=0;
		while (true) {
			if (hasNextLev(lev)) {
				count=insertPutDataByLev(lev);
				if(count==-1){
					return count;
				}
			} else {
				break;
			}
			lev++;
		}
		BatchPublic.inBatchlog("N", "N08", "1", conn);
		log.info("=======insertTotalData END========");
		return count;
	}
	
	public int insertPutDataByLev(int lev){
		log.info("=======insertDataByLev lev========" + lev);
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO RPT_PUTSTAT(MON_DATE,BR_NO,BR_LEV,PRDT_NO,VOU_TYPE,TERM_TYPE,AMT,CNT) ");
		sb.append("SELECT A.MON_DATE,B.UP_ONE,?,A.PRDT_NO,A.VOU_TYPE,A.TERM_TYPE,SUM(A.AMT),SUM(A.CNT) ");
		sb.append("FROM RPT_PUTSTAT A,TBL_ORG_DEPARTMENTS B WHERE A.BR_NO=B.BR_NO AND A.MON_DATE=? AND A.BR_LEV=? ");
		sb.append("GROUP BY A.MON_DATE,B.UP_ONE,A.PRDT_NO,A.VOU_TYPE,A.TERM_TYPE");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, String.valueOf(lev + 1));
			ps.setString(2, mon_date);
			ps.setString(3, String.valueOf(lev));
			count = ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("N", "N08", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("=======insertDataByLev========" + count);
		return count;
	}
	
	// 汇总逾期数据
	public int insertTotalOverData() {
		int lev = 0;
		int count = 0;
		while (true) {
			if (hasNextLev(lev)) {
				count=insertOverDataByLev(lev);
				if(count==-1){
					return count;
				}
			} else {
				break;
			}
			lev++;
		}
		BatchPublic.inBatchlog("N", "N09", "1", conn);
		log.info("=======insertTotalData END========");
		return count;
	}
	
	public int insertOverDataByLev(int lev){
		log.info("=======insertDataByLev lev========" + lev);
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO RPT_OVERSTAT(MON_DATE,BR_NO,BR_LEV,PRDT_NO,VOU_TYPE,TERM_TYPE,OVER_TYPE,BAL,CNT) ");
		sb.append("SELECT A.MON_DATE,B.UP_ONE,?,A.PRDT_NO,A.VOU_TYPE,A.TERM_TYPE,A.OVER_TYPE,SUM(A.BAL),SUM(A.CNT) ");
		sb.append("FROM RPT_OVERSTAT A,TBL_ORG_DEPARTMENTS B WHERE A.BR_NO=B.BR_NO AND A.MON_DATE=? AND A.BR_LEV=? ");
		sb.append("GROUP BY A.MON_DATE,B.UP_ONE,A.PRDT_NO,A.VOU_TYPE,A.TERM_TYPE,A.OVER_TYPE");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, String.valueOf(lev + 1));
			ps.setString(2, mon_date);
			ps.setString(3, String.valueOf(lev));
			count = ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			count=-1;
			BatchPublic.inBatchlog("N", "N09", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("=======insertDataByLev========" + count);
		return count;
	}
}
