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
				&& "L99".equals(execstep[0])) {// �Ѿ���ȫִ�гɹ�
			return "0";
		}
		int flag;
		String date_jr = BatchPublic.jtGlobal(conn);
		if (date_jr.endsWith("01")) { // �����弶����Ĵ��� ��ĩ
			if (execstep != null && "1".equals(execstep[1])
					&& "L99".equals(execstep[0])) {// �Ѿ���ȫִ�гɹ�
				return "0";
			}
			// ��ĩ����
			switch (i) {
			case 0:
			case 1: // ��ĩ������������(rpt_xdls)
				flag = m.updateRpt_xdls();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 2: // ��ĩ������������(risk_five_mon)
				flag = m.updateRisk_five_mon();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 3: // ����RPT_XD���MON_DATE
				flag = m.updateRptXd_MonDate();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 4: // ���±�risk_five����ز���
				flag = m.updateRisk_five();
				if (flag == -1) {
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			}

		} else {

			log.info("������ĩ,��ĩ�������ø���.............." + "\n");
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
				&& "O99".equals(execstep[0])) {// �Ѿ���ȫִ�гɹ�
			return "0";
		}
		int flag;
		String date_jr = BatchPublic.jtGlobal(conn);
		if (date_jr.endsWith("01")) { // �����弶����Ĵ��� ��ĩ
			if (execstep != null && "1".equals(execstep[1])
					&& "O99".equals(execstep[0])) {// �Ѿ���ȫִ�гɹ�
				return "0";
			}
			i++;
			// ��ĩ����
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
				flag = m.updateApp_gather4(); // //��ĩ���뵱������(app_work_gather)
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

			log.info("������ĩ,��ĩ�������ø���.............." + "\n");
		}
		DataSourceUtils.closeConnection(conn);
		return "1";
	}

}
