package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.dao.UpdateCrXdDAO;
import app.util.exc.DataSourceUtils;

public class UpdateCrXdBO {
	Log log = LogFactory.getLog(this.getClass());
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * ������ݿ�����
	 * 
	 * @return
	 */
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

	public String UpdateCrXdP() {

		Connection conn = null;
		conn = this.getConnection();

		UpdateCrXdDAO xd = new UpdateCrXdDAO(conn);
		String[] execstep = BatchPublic.isExecSussess("J", conn);

		if (execstep != null && "1".equals(execstep[1])
				&& "J19".equals(execstep[0])) {// �Ѿ���ȫִ�гɹ�
			log.info("==RPT_XD �����Ѿ�ִ�гɹ��������ٴ�ִ��==");
			return "0";
		}
		int i = getStep(execstep[0]);
		int count;
		switch (i) {
		case 0:
		case 1:
			count = xd.updateAppInfo();// ���������J01
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 2:
			count = xd.updatePactInfo();// ���º�ͬ��ϢJ02
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 3:
			count = xd.updateApplyInfo();// ����������ϢJ03
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 4:
			count = xd.updateCorpInfo();// ���¶Թ��ͻ���ϢJ04
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 5:
			count = xd.updatePersonInfo();// ���¸��˿ͻ���ϢJ05
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 6:
			count = xd.updateDueInfo();// ���½����ϢJ06
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 7:
			count = xd.updateCLevel();// �������õȼ�J07
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 8:
			count = xd.updateTrust();// ���º�����Ŀ��ϢJ08
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 10:
			// xd.updateRiskFour();//�����ļ�����J10
		case 11:
			// xd.updateHxBrno();//���º��Ļ�����J11
		case 12:
			// xd.updateXdBrno();//�����Ŵ����˻�����J12
		case 13:
			count = xd.updateMangBrNo();// �����Ŵ�����ϵͳ�ܻ��������ܻ�����J13
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 14:
			count = xd.updateTXBal();// �����������J14
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 15:
			count = xd.updateTimeType();// ���������¡������ա���������J15
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 16:
			count = xd.updateTXTimeType();// ����TX��������J16
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 17:
			count = xd.updateLnType();// ����LN_TYPE J17
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 18:
			count = xd.updateVouCifName();// ���µ��������� J18
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 19:
			count = xd.update_overDay();// ������������
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		default:

		}
		log.info("-----����J ����rpt_xd�� ����-----");
		DataSourceUtils.closeConnection(conn);
		return "1";

	}
}
