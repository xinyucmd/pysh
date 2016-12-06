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
	// ��ȡ����
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
	
	// ���뵽��ǰ����������Ϣ
	public int insertLoanMessageBef3(Integer notifyCifDateBefore) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  b.phone, '�𾴵Ŀͻ��������»�����Ϊ' || a.end_date || ',Ӧ����� ' || (a.return_capital + a.return_interest) || 'Ԫ��������ʱ������ָ���˻��ڡ�ף����죡',");
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
	
	
	// ���뵽��ǰ����������Ϣ(�ͻ�����)
	public int insertLoanMessageBef3ForTlr(Integer notifyMangDateBefore) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.tlrmobile, '�ͻ����� '||d.tlrname||' ���ã����Ŀͻ� '||c.cif_name||' ���»�����Ϊ' || a.end_date || ',Ӧ����� ' || (a.return_capital + a.return_interest) || 'Ԫ������������ʾ�ջع����������������ܲ���',");
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
	
	// ���뵽��ǰ����������Ϣ(�ͻ���������) ��ʱ������
	public int insertLoanMessageBef3ForMang() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.mangmobile, '�ͻ����� '||d.managename||' ���ã����Ŀͻ� '||c.cif_name||' ���»�����Ϊ' || a.end_date || ',Ӧ����� ' || (a.return_capital + a.return_interest) || 'Ԫ������������ʾ�ջع����������������ܲ���',");
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
	
	// ���뵽�ڵ���������Ϣ
	public int insertLoanMessageBef1() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  b.phone, '�𾴵Ŀͻ��������»�����Ϊ' || a.end_date || ',Ӧ����� ' || (a.return_capital + a.return_interest) || 'Ԫ��������ʱ������ָ���˻��ڡ�ף����죡',");
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

	// ���뵽�ڵ���������Ϣ(�ͻ�����)
	public int insertLoanMessageBef1ForTlr() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.tlrmobile, '�ͻ����� '||d.tlrname||' ���ã����Ŀͻ� '||c.cif_name||' ���»�����Ϊ' || a.end_date || ',Ӧ����� ' || (a.return_capital + a.return_interest) || 'Ԫ������������ʾ�ջع����������������ܲ���',");
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
	
	// �������ڵ���������
	public int insertOverLoanMessageBef3(Integer notifyCifDateAfter) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  b.phone, '�𾴵Ŀͻ��������ҹ�˾��ݺ�Ϊ ' || a.due_no || '�Ľ������ ' ||(to_date(a.end_date,'yyyy-mm-dd')+1)||' �շ������ڣ���ֹ����Ӧ����� '||(c.capital + c.interest+c.over_interest) || '���������쳥����ָ���˻��ڡ�ף����죡���������ڡ�',");
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
	
	// �������ڵ���������(�ͻ�����)
	public int insertOverLoanMessageBef3ForTlr(Integer notifyCifMangDateAfter) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.tlrmobile, '�ͻ����� '||d.tlrname||' ���ã����Ŀͻ� '||c.cif_name||' �Ľ������' || (to_date(a.end_date,'yyyy-mm-dd')+1) || '�շ�������,��ֹ����Ӧ����� ' || (f.capital + f.interest+f.over_interest) || 'Ԫ�����������ջع����������ֿͻ����춯���輰ʱ�����ܲ��������������ܲ���',");
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
	
	// �������ڵ���������(�ͻ���������)
	public int insertOverLoanMessageBef3ForMang(Integer notifyCifMangLeaderDateAfter) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("INSERT INTO SMSSERVER_OUT( TYPE,RECIPIENT,TEXT,CREATE_DATE,ENCODING,SENT_DATE,STATUS");
		sb.append(",GATEWAY_ID,STATUS_REPORT, FLASH_SMS, SRC_PORT, DST_PORT, PRIORITY)  SELECT ");
		sb.append("'O',  d.mangmobile,'�ͻ��������� '||d.managename||' ���ã��ͻ�����'||d.tlrname||'�Ŀͻ� '||c.cif_name||' �Ľ������' || (to_date(a.end_date,'yyyy-mm-dd')+1) || '�շ�������,��ֹ����Ӧ����� ' || (f.capital + f.interest+f.over_interest) || 'Ԫ���붽���йش��չ����������ֿͻ����춯���輰ʱ�����ܲ��������������ܲ���',");
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
