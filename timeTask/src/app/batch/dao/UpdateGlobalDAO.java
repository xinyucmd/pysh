package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import app.util.exc.DataSourceUtils;

public class UpdateGlobalDAO {

	private Connection con;

	public UpdateGlobalDAO(Connection con) {
		this.con = con;
	}

	public int initBatchLog() {
		String date = ztGlobal();
		String flag = getBatchLog(date);
		if (!flag.equals("0")) { // ������¼�Ѿ���ʼ����
			return 0;
		} else {
			ArrayList<String> sqllist = new ArrayList<String>();
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','B','UpdateSelectDue','һ������','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','C','UpdateBal','���½�ݱ���ͬ���弶��������','D','0','') ");
			/*
			 * ��������Ŀǰ����û���õ���modified by suozhiqi on 2014-6-10.
			 */
			// sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('"+date+"','D','UpdateStatus','���½��״̬','D','0','') ");
			// sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('"+date+"','E','UpdatetieXian','����ҵ����','D','0','') ");
			// sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('"+date+"','H','UpdateZqCl','չ�ڴ���','D','0','') ");
			// sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('"+date+"','I','UpdatePatch','��������','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','J','UpdateCrXd','����rpt_xd��','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','L','UpdateMonth','�����±���','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','M','RptTrade','���Ͷ����ܱ�','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','N','RptLoan','�����Ϣ���ܱ�','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date.substring(0, 6) + "','O','UpdateMonth','���������������ܱ�(�±�)','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','P','FiveClass','�弶����״̬','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','Q','SendMessage','����֪ͨ','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','R','Rptzjtx','�ʽ�Ͷ�����','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','S','Rptxydj','�����ȼ�','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','T','Rptwjfl','�弶����','D','0','') ");

			Statement st = null;
			try {
				st = con.createStatement();

				int len = sqllist.size();
				for (int i = 0; i < len; i++) {
					st.addBatch(sqllist.get(i));
				}
				st.executeBatch();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DataSourceUtils.closeStatement(st);
			}
		}
		return 1;
	}

	/*
	 * ��������
	 */
	public String ztGlobal() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String riqi = "";
		String sql = "SELECT LST_DATE FROM SYS_GLOBAL";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				riqi = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return riqi;
	}

	/**
	 * ĳ���������־��
	 **/
	public String getBatchLog(String date) {
		String flag = "0";
		String sql = "SELECT NVL(COUNT(*),0) FROM BATCHLOG WHERE BATCHDATE=? AND ROWNUM =1";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, date);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return flag;
	}
}
