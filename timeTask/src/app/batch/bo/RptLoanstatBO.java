package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.dao.RptLoanstatDAO;
import app.util.exc.DataSourceUtils;

public class RptLoanstatBO {
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

	public String ExecTrade() {
		Connection conn = null;
		conn = this.getConnection();
		RptLoanstatDAO dao = new RptLoanstatDAO(conn);
		String[] execstep = BatchPublic.isExecSussess("N", conn);

		/*if (execstep != null && "1".equals(execstep[1]) && "N9".equals(execstep[0])) {// 已经完全执行成功
			log.info("==RPT_LOAN 批量已经执行成功，无需再次执行==");
			return "0";
		}*/
		int i = getStep(execstep[0]);
		int flag;
		switch (i) {
		case 0:
		case 1:
			flag=dao.deleteData1();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 2:
			flag=dao.deleteData2();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 3:
			flag=dao.deleteData3();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 4:
			flag=dao.insertLoanData();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 5:
			flag=dao.insertPutData();	
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 6:
			flag=dao.insertOverData();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 7:
			flag=dao.insertTotalLoanData();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 8:
			flag=dao.insertTotalPutData();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 9:
			flag=dao.insertTotalOverData();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		}
		DataSourceUtils.closeConnection(conn);
		return "1";
	}
}
