package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.batch.BatchPublic;
import app.util.exc.DataSourceUtils;

public class UpdateDueBalDAO {
	Log log = LogFactory.getLog(this.getClass());
	private Connection con;

	public UpdateDueBalDAO(Connection con) {
		this.con = con;
	}

	/**
	 * 功能描述： 更新借据表余额
	 * 
	 * @return
	 * @author
	 * @date
	 * @修改日志：
	 */
	public int updateDueBal() {

		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int flag = 0;
		log.info("步骤 C01 开始 更新借据表   B01 >>>>>>");
		sb.append("UPDATE LN_DUE A  SET A.BAL=(SELECT B.DUE_BAL FROM LOAN_AC_LN_MST B WHERE B.DUE_NO=A.DUE_NO AND ROWNUM=1) ");

		log.info("步骤 C01 的SQL>>>>>> " + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			flag = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("C", "C01", "1", con); // 成功记录成功log
			log.info("步骤 C01 操作结束 更新借据 条数: " + flag);
		} catch (Exception e) {
			log.error("步骤 B01 开始  新增贷款插入五级分类表 条数   B01 :: :::", e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			BatchPublic.inBatchlog("C", "C01", "2", con); // 失败记录失败log
			flag = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return flag;
	}

	/***
	 * 
	 * @方法说明：更新合同表的余额。
	 * @author zhaorenxiang
	 * @date Aug 21, 2011
	 * @param @return
	 */
	public int updatePactBal() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int flag = 0;
		log.info("步骤 C02 开始 更新ln_pact表中   C02 >>>>>>");
		sb.append("UPDATE LN_PACT B SET B.BAL=(SELECT C.BAL FROM (SELECT SUM(B.BAL) AS BAL,B.PACT_NO FROM  LN_DUE B  GROUP BY B.PACT_NO) C  WHERE B.PACT_NO=C.PACT_NO ) ");
		log.info("步骤 C02 的SQL>>>>>> " + sb.toString());

		try {
			ps = con.prepareStatement(sb.toString());
			flag = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("C", "C02", "1", con); // 成功记录成功log
			log.info("步骤 C02 操作结束 更新ln_pact表 条数: " + flag);
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			log.error("步骤 B02 开始  新增贷款插入Rpt_xd表 条数   B02 :: :::", e);
			BatchPublic.inBatchlog("C", "C02", "2", con); // 失败记录失败log
			flag = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return flag;

	}
}
