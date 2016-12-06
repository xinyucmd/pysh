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
		if (!flag.equals("0")) { // 该条记录已经初始化了
			return 0;
		} else {
			ArrayList<String> sqllist = new ArrayList<String>();
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','B','UpdateSelectDue','一般贷款处理','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','C','UpdateBal','更新借据表、合同表、五级分类表余额','D','0','') ");
			/*
			 * 以下四行目前批量没有用到，modified by suozhiqi on 2014-6-10.
			 */
			// sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('"+date+"','D','UpdateStatus','更新借据状态','D','0','') ");
			// sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('"+date+"','E','UpdatetieXian','贴现业务处理','D','0','') ");
			// sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('"+date+"','H','UpdateZqCl','展期处理','D','0','') ");
			// sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('"+date+"','I','UpdatePatch','补丁程序','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','J','UpdateCrXd','更新rpt_xd表','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','L','UpdateMonth','更新月报表','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','M','RptTrade','借款投向汇总表','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','N','RptLoan','借款信息汇总表','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date.substring(0, 6) + "','O','UpdateMonth','更新审批工作汇总表(月报)','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','P','FiveClass','五级分类状态','M','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','Q','SendMessage','短信通知','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','R','Rptzjtx','资金投向汇总','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','S','Rptxydj','信誉等级','D','0','') ");
			sqllist.add("INSERT INTO BATCHLOG ( BATCHDATE,STEP,PROC,EXPRESS,RUNCYC,STATUS,EXECSTEP) VALUES ('" + date + "','T','Rptwjfl','五级分类','D','0','') ");

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
	 * 昨天日期
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
	 * 某天的批量日志数
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
