package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.dao.FiveClass;
import app.util.exc.DataSourceUtils;

public class FiveClassBo {
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
	public String updateFiveSts() {
		System.out.println("FiveClass");
		Connection conn = null;
		conn = this.getConnection();
		String[] execstep = BatchPublic.isExecSussess("P", conn);
		FiveClass five = new FiveClass(conn);
		if (execstep != null && "1".equals(execstep[1])
				&& "P01".equals(execstep[0])) {
			return "0";
		}
		/*String date_jr = BatchPublic.ztGlobal(conn);
		if (date_jr.endsWith("01")) { // 机构五级分类的处理 月末
			if (execstep != null && "1".equals(execstep[1])
					&& "P01".equals(execstep[0])) {// 已经完全执行成功
				return "0";
			}
		}*/
		five.updateFiveSts();
		DataSourceUtils.closeConnection(conn);
		System.out.println("FiveClassOver");
		return "1";
	}
}
