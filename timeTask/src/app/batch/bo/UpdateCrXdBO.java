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
	 * 获得数据库连接
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
				&& "J19".equals(execstep[0])) {// 已经完全执行成功
			log.info("==RPT_XD 批量已经执行成功，无需再次执行==");
			return "0";
		}
		int i = getStep(execstep[0]);
		int count;
		switch (i) {
		case 0:
		case 1:
			count = xd.updateAppInfo();// 更新申请号J01
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 2:
			count = xd.updatePactInfo();// 更新合同信息J02
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 3:
			count = xd.updateApplyInfo();// 更新申请信息J03
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 4:
			count = xd.updateCorpInfo();// 更新对公客户信息J04
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 5:
			count = xd.updatePersonInfo();// 更新个人客户信息J05
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 6:
			count = xd.updateDueInfo();// 更新借据信息J06
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 7:
			count = xd.updateCLevel();// 更新信用等级J07
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 8:
			count = xd.updateTrust();// 更新合作项目信息J08
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 10:
			// xd.updateRiskFour();//更新四级分类J10
		case 11:
			// xd.updateHxBrno();//更新核心机构号J11
		case 12:
			// xd.updateXdBrno();//更新信贷入账机构号J12
		case 13:
			count = xd.updateMangBrNo();// 更新信贷报表系统管户机构，管户经理J13
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 14:
			count = xd.updateTXBal();// 更新贴现余额J14
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 15:
			count = xd.updateTimeType();// 更新期限月、期限日、期限种类J15
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 16:
			count = xd.updateTXTimeType();// 更新TX期限种类J16
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 17:
			count = xd.updateLnType();// 更新LN_TYPE J17
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 18:
			count = xd.updateVouCifName();// 更新担保人名称 J18
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		case 19:
			count = xd.update_overDay();// 更新日期天数
			/*if (count == -1) {
				DataSourceUtils.closeConnection(conn);
				return "0";
			}*/
		default:

		}
		log.info("-----步骤J 更新rpt_xd表 结束-----");
		DataSourceUtils.closeConnection(conn);
		return "1";

	}
}
