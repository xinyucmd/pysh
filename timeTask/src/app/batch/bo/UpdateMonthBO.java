package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.dao.UpdateMonth;
import app.util.exc.DataSourceUtils;

public class UpdateMonthBO {
	Log log = LogFactory.getLog(this.getClass());
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Connection getConnection() {

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		return conn;
	}

	public int getStep(String execstep) {
		if (execstep == null || "".equals(execstep)) {
			return 0;
		}
		int i = 0;
		i = Integer.valueOf(execstep.substring(1));
		return i;
	}

	public String UpdateMonthP() {
		Connection conn = null;
		conn = this.getConnection();

		UpdateMonth m = new UpdateMonth(conn);

		String[] execstep = BatchPublic.isExecSussess("L", conn);
		int i = getStep(execstep[0]);
		if (execstep != null && "1".equals(execstep[1])
				&& "L99".equals(execstep[0])) {// 已经完全执行成功
			return "0";
		}
		int flag;
		String date_jr = BatchPublic.jtGlobal(conn);
		if (date_jr.endsWith("01")) { // 机构五级分类的处理 月末
			if (execstep != null && "1".equals(execstep[1])
					&& "L99".equals(execstep[0])) {// 已经完全执行成功
				return "0";
			}
			// 月末批量
			switch (i) {
			case 0:
			case 1: // 月末插入下月数据(rpt_xdls)
				flag = m.updateRpt_xdls();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 2: // 月末插入下月数据(risk_five_mon)
				flag = m.updateRisk_five_mon();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 3: // 更新RPT_XD表的MON_DATE
				flag = m.updateRptXd_MonDate();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 4: // 更新表risk_five的相关操作
				flag = m.updateRisk_five();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			}

		} else {

			log.info("不是月末,月末批量不用更新.............." + "\n");
		}
		return "1";
	}

	public String UpdateAppMonth() {
		Connection conn = null;
		conn = this.getConnection();

		UpdateMonth m = new UpdateMonth(conn);

		String[] execstep = BatchPublic.isExecSussess2("O", conn);
		int i = getStep(execstep[0]);
		if (execstep != null && "1".equals(execstep[1])
				&& "O99".equals(execstep[0])) {// 已经完全执行成功
			return "0";
		}
		int flag;
		String date_jr = BatchPublic.jtGlobal(conn);
		if (date_jr.endsWith("01")) { // 机构五级分类的处理 月末
			if (execstep != null && "1".equals(execstep[1])
					&& "O99".equals(execstep[0])) {// 已经完全执行成功
				return "0";
			}
			i++;
			// 月末批量
			switch (i) {
			case 0:
			case 1:
				flag = m.updateApp_gather(); //
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 2: //
				flag = m.updateApp_gather2();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 3: //
				flag = m.updateApp_gather3();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 4:
				flag = m.updateApp_gather4(); // //月末插入当月数据(app_work_gather)
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 5: //
				flag = m.updateApp_gather5();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			}

		} else {

			log.info("不是月末,月末批量不用更新.............." + "\n");
		}
		DataSourceUtils.closeConnection(conn);
		return "1";
	}

}
