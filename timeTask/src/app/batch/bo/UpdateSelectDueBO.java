package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.dao.UpdateSelectDueDAO;
import app.util.exc.DataSourceUtils;

public class UpdateSelectDueBO {
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
	 * 主函数
	 * 
	 * @return
	 */
	public String UpdateSelectDueP() {
		Log log = LogFactory.getLog(this.getClass());
		log.info("UpdateDueBal");
		Connection conn = null;
		conn = this.getConnection();
		String[] execstep = BatchPublic.isExecSussess("B", conn);
		UpdateSelectDueDAO dao = new UpdateSelectDueDAO(conn);
		if (execstep != null && "1".equals(execstep[1])
				&& "B02".equals(execstep[0])) {//修改B03为B02，调整程序前后一致性。
			return "0";
		}
		int i = getStep(execstep[0]);
		int count;
		switch (i) {
		case 0:
		case 1:
			count = dao.saveRiskFive();
			if(count==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 2:
			count =dao.saveRptxd();
			if(count==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 3:
			// dao.saveRptxd_tiexian();
		}
		DataSourceUtils.closeConnection(conn);
		return "1";

	}
}
