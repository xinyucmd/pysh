package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.dao.RptTradeDAO;
import app.util.exc.DataSourceUtils;

/**
 * 
 * 功能描述：借款按投向汇总
 * @author dhcc wangshanfang
 * @date Oct 23, 2012
 * @see
 * @修改日志：
 *
 */
public class RptTradeBO {
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
		RptTradeDAO dao = new RptTradeDAO(conn);
		//差执行内容，此步骤为判断是否成功
		
		
		String[] execstep = BatchPublic.isExecSussess("M", conn);

		if (execstep != null && "1".equals(execstep[1]) && "M03".equals(execstep[0])) {// 已经完全执行成功
			log.info("==RPT_TRADE 批量已经执行成功，无需再次执行==");
			return "0";
		}
		int i = getStep(execstep[0]);
		int flag;
		switch (i) {
		case 0:
		case 1:
			flag=dao.deleteData();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 2:
			flag=dao.insertData();
			if(flag==-1){
				DataSourceUtils.closeConnection(conn);
				return "0";
			}
		case 3:
			dao.insertTotalData();
		}
		DataSourceUtils.closeConnection(conn);
		return "1";
	}
}
