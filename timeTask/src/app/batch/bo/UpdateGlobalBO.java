package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.base.ServiceException;
import app.batch.dao.UpdateGlobalDAO;
import app.util.exc.DataSourceUtils;

public class UpdateGlobalBO {
	Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	public String[] execstep = null;

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

	public String UpdateGlobalP() {

		Connection conn = this.getConnection();
		UpdateGlobalDAO dao = new UpdateGlobalDAO(conn);
		dao.initBatchLog();
		DataSourceUtils.closeConnection(conn);
		log.info("初始化完成.");
		return "1";
	}

}
