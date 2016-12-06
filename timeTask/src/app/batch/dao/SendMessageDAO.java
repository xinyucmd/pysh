package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import app.batch.BatchPublic;
import app.util.exc.DataSourceUtils;

public class SendMessageDAO {
	private Connection conn;
	private String mon_date;
	private String date_stamp;

	public SendMessageDAO(Connection conn) {
		this.conn = conn;
		initDate();
	}
	// 获取日期
	public void initDate() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SYS_DATE FROM SYS_GLOBAL");
		try {
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				String _date=rs.getString(1);
				this.date_stamp=_date.substring(0,4)+"-"+_date.substring(4,6)+"-"+_date.substring(6)+" 10:00:01.10";
				this.mon_date = _date.substring(0,4)+"-"+_date.substring(4,6)+"-"+_date.substring(6);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
	}
	
	// 插入到期前三天提醒信息
	public int insertLoanMessageBef3(Integer notifyCifDateBefore) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  b.phone, '尊敬的客户，您本月还款日为' || a.end_date || ',应还金额 ' || (a.return_capital + a.return_interest) || '元，请您按时偿还至指定账户内。祝您愉快！',");
		sb.append("sysdate, 'U', to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff'), 'U', 'modem1','0', '0','-1','-1','0' ");
		sb.append("FROM LOAN_PLAN A, CIF_INFO B WHERE A.CIF_NO = B.CIF_NO AND B.PHONE IS NOT NULL ");
		sb.append("AND B.PHONE !='null'  AND A.STATE = '0' ");
		sb.append("AND to_date(end_date, 'yyyy-MM-dd') -to_date(?, 'yyyy-MM-dd') =");
		sb.append(notifyCifDateBefore);
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, date_stamp);
			ps.setString(2, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("Q", "Q01", "1", conn);
		} catch (Exception e) {
			count = -1;
			BatchPublic.inBatchlog("Q", "Q01", "2", conn);
			e.printStackTrace();
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	
	
	// 插入到期前三天提醒信息(客户经理)
	public int insertLoanMessageBef3ForTlr(Integer notifyMangDateBefore) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.tlrmobile, '客户经理 '||d.tlrname||' 您好，您的客户 '||c.cif_name||' 本月还款日为' || a.end_date || ',应还金额 ' || (a.return_capital + a.return_interest) || '元，请您做好提示收回工作。【江川金融总部】',");
		sb.append("sysdate, 'U', to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff'), 'U', 'modem1','0', '0','-1','-1','0' ");
		sb.append("FROM LOAN_PLAN A, CIF_INFO B,LN_DUE C,TLR_MANGTLR_INFO D WHERE A.CIF_NO = B.CIF_NO AND d.tlrmobile IS NOT NULL ");
		sb.append("AND d.tlrmobile !='null'  AND A.STATE = '0' ");
		sb.append("AND A.DUE_NO=C.DUE_NO  AND C.MANG_NO=D.OP_NO ");
		sb.append(" AND to_date(a.end_date, 'yyyy-MM-dd') -to_date(?, 'yyyy-MM-dd') =");
		sb.append(notifyMangDateBefore);
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, date_stamp);
			ps.setString(2, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("Q", "Q02", "1", conn);
		} catch (Exception e) {
			count = -1;
			BatchPublic.inBatchlog("Q", "Q02", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	
	// 插入到期前三天提醒信息(客户经理主管) 暂时不启用
	public int insertLoanMessageBef3ForMang() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.mangmobile, '客户经理 '||d.managename||' 您好，您的客户 '||c.cif_name||' 本月还款日为' || a.end_date || ',应还金额 ' || (a.return_capital + a.return_interest) || '元，请您做好提示收回工作。【江川金融总部】',");
		sb.append("sysdate, 'U', to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff'), 'U', 'modem1','0', '0','-1','-1','0' ");
		sb.append("FROM LOAN_PLAN A, CIF_INFO B,LN_DUE C,TLR_MANGTLR_INFO D WHERE A.CIF_NO = B.CIF_NO AND d.mangmobile IS NOT NULL ");
		sb.append("AND d.mangmobile !='null'  AND A.STATE = '0' ");
		sb.append("AND A.DUE_NO=C.DUE_NO  AND C.MANG_NO=D.OP_NO ");
		sb.append(" AND to_date(a.end_date, 'yyyy-MM-dd') -to_date(?, 'yyyy-MM-dd') = 3");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, date_stamp);
			ps.setString(2, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("Q", "Q03", "1", conn);
		} catch (Exception e) {
			count = -1;
			BatchPublic.inBatchlog("Q", "Q03", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	
	// 插入到期当天提醒信息
	public int insertLoanMessageBef1() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  b.phone, '尊敬的客户，您本月还款日为' || a.end_date || ',应还金额 ' || (a.return_capital + a.return_interest) || '元，请您按时偿还至指定账户内。祝您愉快！',");
		sb.append("sysdate, 'U', to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff'), 'U', 'modem1','0', '0','-1','-1','0' ");
		sb.append("FROM LOAN_PLAN A, CIF_INFO B WHERE A.CIF_NO = B.CIF_NO AND B.PHONE IS NOT NULL ");
		sb.append("AND B.PHONE !='null'  AND A.STATE = '0' ");
		sb.append("AND to_date(a.end_date, 'yyyy-MM-dd') -to_date(?, 'yyyy-MM-dd') = 0");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, date_stamp);
			ps.setString(2, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("Q", "Q04", "1", conn);
		} catch (Exception e) {
			count = -1;
			BatchPublic.inBatchlog("Q", "Q04", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	// 插入到期当天提醒信息(客户经理)
	public int insertLoanMessageBef1ForTlr() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.tlrmobile, '客户经理 '||d.tlrname||' 您好，您的客户 '||c.cif_name||' 本月还款日为' || a.end_date || ',应还金额 ' || (a.return_capital + a.return_interest) || '元，请您做好提示收回工作。【江川金融总部】',");
		sb.append("sysdate, 'U', to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff'), 'U', 'modem1','0', '0','-1','-1','0' ");
		sb.append("FROM LOAN_PLAN A, CIF_INFO B,LN_DUE C,TLR_MANGTLR_INFO D WHERE A.CIF_NO = B.CIF_NO AND d.tlrmobile IS NOT NULL ");
		sb.append("AND d.tlrmobile !='null'  AND A.STATE = '0' ");
		sb.append("AND A.DUE_NO=C.DUE_NO  AND C.MANG_NO=D.OP_NO ");
		sb.append("AND to_date(a.end_date, 'yyyy-MM-dd') -to_date(?, 'yyyy-MM-dd') = 0");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, date_stamp);
			ps.setString(2, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("Q", "Q05", "1", conn);
		} catch (Exception e) {
			count = -1;
			BatchPublic.inBatchlog("Q", "Q05", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	
	// 发生逾期第三天提醒
	public int insertOverLoanMessageBef3(Integer notifyCifDateAfter) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  b.phone, '尊敬的客户，您在我公司借据号为 ' || a.due_no || '的借款已于 ' ||(to_date(a.end_date,'yyyy-mm-dd')+1)||' 日发生逾期，截止今日应还金额 '||(c.capital + c.interest+c.over_interest) || '，请您尽快偿还至指定账户内。祝您愉快！【江川金融】',");
		sb.append("sysdate, 'U', to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff'), 'U', 'modem1','0', '0','-1','-1','0' ");
		sb.append(" FROM LOAN_PLAN A, CIF_INFO B,LOAN_RATEDAY C WHERE A.CIF_NO = B.CIF_NO AND B.PHONE IS NOT NULL ");
		sb.append("AND B.PHONE !='null'  AND A.STATE = '1'");
		sb.append("AND A.DUE_NO=C.DUE_NO AND A.TERM_NO=C.TERM_NO ");
		sb.append("AND C.OCCURE_DATE=? AND to_date(?, 'yyyy-MM-dd') -to_date(a.end_date, 'yyyy-MM-dd') >=");
		sb.append(notifyCifDateAfter);
		sb.append(" AND MOD(to_date(?, 'yyyy-MM-dd') -to_date(a.end_date, 'yyyy-MM-dd'),3) = 0");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, date_stamp);
			ps.setString(2, mon_date);
			ps.setString(3, mon_date);
			ps.setString(4, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("Q", "Q06", "1", conn);
		} catch (Exception e) {
			count = -1;
			BatchPublic.inBatchlog("Q", "Q06", "2", conn);
			e.printStackTrace();
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	
	// 发生逾期第三天提醒(客户经理)
	public int insertOverLoanMessageBef3ForTlr(Integer notifyCifMangDateAfter) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.tlrmobile, '客户经理 '||d.tlrname||' 您好，您的客户 '||c.cif_name||' 的借款已于' || (to_date(a.end_date,'yyyy-mm-dd')+1) || '日发生逾期,截止今日应还金额 ' || (f.capital + f.interest+f.over_interest) || '元，请您做好收回工作。若发现客户有异动，需及时反馈总部。【江川金融总部】',");
		sb.append("sysdate, 'U', to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff'), 'U', 'modem1','0', '0','-1','-1','0' ");
		sb.append(" FROM LOAN_PLAN A, CIF_INFO B,LN_DUE C,TLR_MANGTLR_INFO D,LOAN_RATEDAY F WHERE A.CIF_NO = B.CIF_NO AND B.PHONE IS NOT NULL ");
		sb.append("AND A.DUE_NO=C.DUE_NO  AND C.MANG_NO=D.OP_NO  AND A.STATE = '1' ");
		sb.append("AND A.DUE_NO=F.DUE_NO AND A.TERM_NO=F.TERM_NO ");
		sb.append("AND D.TLRMOBILE IS NOT NULL AND D.TLRMOBILE!='null' ");
		sb.append("AND F.OCCURE_DATE=? AND to_date(?, 'yyyy-MM-dd') -to_date(a.end_date, 'yyyy-MM-dd') >=");
		sb.append(notifyCifMangDateAfter);
		sb.append(" AND MOD(to_date(?, 'yyyy-MM-dd') -to_date(a.end_date, 'yyyy-MM-dd'),3) = 0");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, date_stamp);
			ps.setString(2, mon_date);
			ps.setString(3, mon_date);
			ps.setString(4, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("Q", "Q07", "1", conn);
		} catch (Exception e) {
			count = -1;
			BatchPublic.inBatchlog("Q", "Q07", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	
	// 发生逾期第三天提醒(客户经理主管)
	public int insertOverLoanMessageBef3ForMang(Integer notifyCifMangLeaderDateAfter) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.mangmobile,'客户经理主管 '||d.managename||' 您好，客户经理'||d.tlrname||'的客户 '||c.cif_name||' 的借款已于' || (to_date(a.end_date,'yyyy-mm-dd')+1) || '日发生逾期,截止今日应还金额 ' || (f.capital + f.interest+f.over_interest) || '元，请督促有关催收工作，若发现客户有异动，需及时反馈总部。【江川金融总部】',");
		sb.append("sysdate, 'U', to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss.ff'), 'U', 'modem1','0', '0','-1','-1','0' ");
		sb.append(" FROM LOAN_PLAN A, CIF_INFO B,LN_DUE C,TLR_MANGTLR_INFO D,LOAN_RATEDAY F WHERE A.CIF_NO = B.CIF_NO AND B.PHONE IS NOT NULL ");
		sb.append("AND A.DUE_NO=C.DUE_NO  AND C.MANG_NO=D.OP_NO  AND A.STATE = '1' ");
		sb.append("AND A.DUE_NO=F.DUE_NO AND A.TERM_NO=F.TERM_NO ");
		sb.append("AND D.TLRMOBILE IS NOT NULL AND D.TLRMOBILE!='null' ");
		sb.append("AND F.OCCURE_DATE=? AND to_date(?, 'yyyy-MM-dd') -to_date(a.end_date, 'yyyy-MM-dd') >=" );
		sb.append(notifyCifMangLeaderDateAfter);
		sb.append(" AND MOD(to_date(?, 'yyyy-MM-dd') -to_date(a.end_date, 'yyyy-MM-dd'),3) = 0");
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, date_stamp);
			ps.setString(2, mon_date);
			ps.setString(3, mon_date);
			ps.setString(4, mon_date);
			count = ps.executeUpdate();
			conn.commit();
			BatchPublic.inBatchlog("Q", "Q08", "1", conn);
		} catch (Exception e) {
			count = -1;
			BatchPublic.inBatchlog("Q", "Q08", "2", conn);
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}
	public String getMon_date() {
		return mon_date;
	}
	public void setMon_date(String mon_date) {
		this.mon_date = mon_date;
	}
	public String getDate_stamp() {
		return date_stamp;
	}
	public void setDate_stamp(String date_stamp) {
		this.date_stamp = date_stamp;
	}

}
