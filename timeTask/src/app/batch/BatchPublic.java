package app.batch;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import app.util.exc.DataSourceUtils;

public class BatchPublic {

/**
 *某天的批量日志数 
 **/
public static String getBatchLog(Connection con,String date){
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

/**
 * 取得昨天的日期
 * @param con
 * @return
 */
public static String ztGlobal(Connection con) {
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
 * 取得今天的日期
 * @param con
 * @return
 */
public static String jtGlobal(Connection con) {
	PreparedStatement ps = null;
	ResultSet rs = null;
	String riqi = "";
	String sql = "SELECT SYS_DATE FROM SYS_GLOBAL";
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
 * 
 * @param step
 * @param con
 * @return
 */
public static String[] isExecSussess( String step, Connection con) {
	String[] is = new String[2];
	String date = ztGlobal(con);
	String sql = "SELECT EXECSTEP,STATUS FROM BATCHLOG WHERE BATCHDATE=? AND STEP=? AND STATUS='1'";
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
		ps = con.prepareStatement(sql);
		ps.setString(1, date);
		ps.setString(2, step);
		rs = ps.executeQuery();
		if (rs.next()) {
			is[0] = rs.getString(1);
			is[1] = rs.getString(2);
		}
	} catch (Exception e) {
	} finally {
		DataSourceUtils.closeStatement(ps);
	}
	return is;
}
/**
 * 
 * @param step
 * @param con
 * @return
 */
public static String[] isExecSussess2( String step, Connection con) {
	String[] is = new String[2];
	String date = ztGlobal(con);
	String sql = "SELECT EXECSTEP,STATUS FROM BATCHLOG WHERE BATCHDATE=? AND STEP=? AND STATUS='1'";
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
		ps = con.prepareStatement(sql);
		ps.setString(1, date.substring(0,6));
		ps.setString(2, step);
		rs = ps.executeQuery();
		if (rs.next()) {
			is[0] = rs.getString(1);
			is[1] = rs.getString(2);
		}
	} catch (Exception e) {
	} finally {
		DataSourceUtils.closeStatement(ps);
	}
	return is;
}

public static void inBatchlog(String step,String execstep,String status, Connection con) {
	PreparedStatement ps = null;
	String date = ztGlobal(con);
	String sql = "UPDATE BATCHLOG SET STATUS=?,EXECSTEP=? WHERE BATCHDATE=? AND STEP=? ";
	try {
		ps = con.prepareStatement(sql);
		ps.setString(1, status);
		ps.setString(2, execstep);
		ps.setString(3, date);
		ps.setString(4, step);
		ps.executeUpdate();
		con.commit();
	} catch (Exception e) {
	} finally {
		DataSourceUtils.closeStatement(ps);
	}
}
public static void inBatchlog2(String step,String execstep,String status, Connection con) {
	PreparedStatement ps = null;
	String date = ztGlobal(con);
	String sql = "UPDATE BATCHLOG SET STATUS=?,EXECSTEP=? WHERE BATCHDATE=? AND STEP=? ";
	try {
		ps = con.prepareStatement(sql);
		ps.setString(1, status);
		ps.setString(2, execstep);
		ps.setString(3, date.substring(0, 6));
		ps.setString(4, step);
		ps.executeUpdate();
		con.commit();
	} catch (Exception e) {
	} finally {
		DataSourceUtils.closeStatement(ps);
	}
}
}
