package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.dao.RptTradeDAO;
import app.batch.dao.RptxydjDAO;
import app.util.exc.DataSourceUtils;

/**
 * 
 * ���������������ȼ�
 * @author dhcc wangshanfang
 * @date Oct 23, 2012
 * @see
 * @�޸���־��
 *
 */
public class RptxydjBO {
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
		RptxydjDAO dao = new RptxydjDAO(conn);
		String[] execstep = BatchPublic.isExecSussess("S", conn);

		if (execstep != null && "1".equals(execstep[1]) && "S01".equals(execstep[0])) {// �Ѿ���ȫִ�гɹ�
			System.out.println("==RPT_XYDJ �����Ѿ�ִ�гɹ��������ٴ�ִ��==");
			return "0";
		}
		dao.Leiji();
		
	DataSourceUtils.closeConnection(conn);
		return "1";
	}
}