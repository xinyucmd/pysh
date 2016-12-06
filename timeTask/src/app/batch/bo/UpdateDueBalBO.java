package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.dao.UpdateDueBalDAO;
import app.util.exc.DataSourceUtils;

public class UpdateDueBalBO {
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

	/**
	 * Ö÷º¯Êý
	 * 
	 * @return
	 */
	public String UpdateDueBalP() {
		Log log = LogFactory.getLog(this.getClass());
		log.info("UpdateDueBal");
		Connection conn = null;
		conn = this.getConnection();
		String[] execstep = BatchPublic.isExecSussess("C", conn);
		UpdateDueBalDAO dao = new UpdateDueBalDAO(conn);
		if (execstep != null && "1".equals(execstep[1])
				&& "C02".equals(execstep[0])) {
			return "0";
		}
		int i = getStep(execstep[0]);
		int count;
		switch (i) {
		case 0:
		case 1:
			count = dao.updateDueBal();
			if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 2:
			count = dao.updatePactBal();
			if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		}
		DataSourceUtils.closeConnection(conn);
		return "1";
	}
}
