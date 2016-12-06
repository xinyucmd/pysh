package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.batch.BatchPublic;
import app.util.exc.DataSourceUtils;

public class UpdateMonth {
	Log log = LogFactory.getLog(this.getClass());
	private Connection con;
	private String date_jr;

	public UpdateMonth(Connection con) {
		this.con = con;
		date_jr = BatchPublic.jtGlobal(con);
	}

	/**
	 * 功能描述： 生成报表rpt_xdls表的月表
	 * 
	 * @return 更新记录数
	 * @author zhangwei
	 * @date 20110821
	 */
	public int updateRpt_xdls() {

		PreparedStatement ps = null;
		int count = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into rpt_xdls ");
		sb.append("select * from rpt_xd");
		log.info(sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("L", "L01", "1", con);
			log.info("步骤 -L  -L01 生成rpt_xdls月表 操作结束 更新记录:" + count
					+ "-->步骤 -L.2  :::");
		} catch (Exception e) {
			log.error("步骤 -L  -L01 生成rpt_xdls月表 操作失败 -->步骤 -L.2  :::"
					+ e.getMessage());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("L", "L01", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * 功能描述： 生成risk_five_mon月表
	 * 
	 * @return 更新记录数
	 * @author zhangwei
	 * @date 20110821
	 */
	public int updateRisk_five_mon() {

		PreparedStatement ps = null;
		int count = 0;
		// String adate = Log.jtGlobal(con);
		// String date = Log.ztGlobal(con).substring(0, 6) + "__";
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO RISK_FIVE_MON ");
		sb.append("SELECT * FROM RISK_FIVE");

		log.info(sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			// ps.setString(1, adate);
			// ps.setString(2, date);
			ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("L", "L02", "1", con);
			log.info("步骤 -L  -L.2 生成risk_five_mon月表 操作结束 更新记录:" + count
					+ "-->步骤 -L.3  :::");
		} catch (Exception e) {
			log.error("步骤 -L  end.2 生成risk_five_mon月表 操作失败 -->步骤 -L.2  :::"
					+ e.getMessage());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("L", "L02", "2", con);
			e.printStackTrace();
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * 功能描述： 更新RPT_XD表的MON_DATE
	 * 
	 * @return 更新记录数
	 * @author zhangwei
	 * @date 20110821
	 */
	public int updateRptXd_MonDate() {
		PreparedStatement ps = null;
		// ResultSet rs = null;
		int count = 0;
		String mon_date = date_jr.substring(0, 6) + "50";
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE RPT_XD SET MON_DATE =?");
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("L", "L03", "1", con);
			log.info("步骤 -L  -L03 更新RPT_XD表的MON_DATE 操作结束 更新记录:" + count
					+ "-->步骤 -L03  :::");
		} catch (Exception e) {
			e.printStackTrace();
			// Log.err("步骤 -L  -L03 更新RPT_XD表的MON_DATE 操作失败 -->步骤 -L02  :::" +
			// e.getMessage());
			// Log.inBatchlog("L", "L03", "2", con);
			// new PublicBOExHandler().handle(con, e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("L", "L03", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * 
	 * 功能描述：更新risk_five表的MON_DATE、FIVE_STS_LAST、CLASS_FLAG、
	 * 
	 * @return
	 * @author dhcc zhangwei
	 * @date 20110821
	 * @修改日志：
	 * 
	 */
	public int updateRisk_five() {
		PreparedStatement ps = null;
		int count = 0;
		String mon_date = date_jr.substring(0, 6) + "50";
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE RISK_FIVE SET MON_DATE =?");
		sb.append(",FIVE_STS_LAST = FIVE_STS");
		sb.append(",CLASS_FLAG = '0'");

		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("L", "L99", "1", con);
			log.info("步骤 更新risk_five表的MON_DATE、FIVE_STS_LAST、CLASS_FLAG 操作结束 更新记录:"
					+ count + "-->步骤  :::");
		} catch (Exception e) {
			log.error("步骤 更新risk_five表的MON_DATE、FIVE_STS_LAST、CLASS_FLAG 操作失败 -->步骤  :::"
					+ e.getMessage());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("L", "L99", "2", con);
			// new PublicBOExHandler().handle(con, e);
			e.printStackTrace();
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * 
	 * 功能描述：汇总评审和审批的审批信息OP_NO、COUNTS、AMT_TOTAL,APP_MONTH
	 * 
	 * @return
	 * @author dhcc zhangwei
	 * @date 20110821
	 * @修改日志：
	 * 
	 */
	public int updateApp_gather() {
		PreparedStatement ps = null;
		int count = 0;
		String mon_date = date_jr.substring(0, 6);
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO APP_WORK_GATHER(OP_NO,COUNTS,AMT_TOTAL,APP_MONTH)  ");
		sb.append("select  B.OP_NO,count(1) as total,sum(a.app_amt) as amt ,");
		sb.append(" '" + mon_date
				+ "' from app_base a, app_idea b where a.trace_no = b.trace_no");
		sb.append(" and a.app_state = '3' and a.put_date like '" + mon_date
				+ "%'  and b.op_no in (");
		sb.append(" select username from tbl_org_user where role_no ='R006' )");
		sb.append(" group by b.op_no ");

		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog2("O", "O01", "1", con);

			log.info("步骤 更新APP_WORK_GATHER表的OP_NO、COUNTS、AMT_TOTAL,APP_MONTH 操作结束 更新记录:"
							+ count + "-->步骤  :::");
		} catch (Exception e) {
			log.error("步骤 更新risk_five表的MON_DATE、FIVE_STS_LAST、CLASS_FLAG 操作失败 -->步骤  :::"
			 + e.getMessage());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog2("O", "O01", "2", con);
			e.printStackTrace();
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * 
	 * 功能描述：汇总评审和审批的审批信息counts_drop
	 * 
	 * @return
	 * @author dhcc zhangwei
	 * @date 20110821
	 * @修改日志：
	 * 
	 */
	public int updateApp_gather2() {
		PreparedStatement ps = null;
		Statement ps2 = null;
		String mon_date = date_jr.substring(0, 6);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT B.OP_NO, COUNT(1) FROM APP_BASE A, APP_IDEA B WHERE A.TRACE_NO = B.TRACE_NO ");
		sb.append("AND A.APP_STATE = '3'  AND B.APP_IDEA='2' and a.put_date like '"
				+ mon_date + "%' AND B.OP_NO IN ");
		sb.append("(select username from tbl_org_user where role_no='R006' ) group by b.op_no  ");
		int count = 0;
		try {
			ps = con.prepareStatement(sb.toString());
			ps2 = con.createStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ps2.addBatch("UPDATE APP_WORK_GATHER SET counts_drop="
						+ rs.getInt(2) + " WHERE OP_NO='" + rs.getString(1)
						+ "' AND APP_MONTH='" + mon_date + "' ");
			}

			int counts[] = ps2.executeBatch();
			count = counts.length;
			BatchPublic.inBatchlog2("O", "O02", "1", con);

			log.info("步骤 更新APP_WORK_GATHER表的counts_drop 操作结束 更新记录:" + count
					+ "-->步骤  :::");
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog2("O", "O02", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps2);
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * 
	 * 功能描述：汇总评审和审批的审批信息counts_pass
	 * 
	 * @return
	 * @author dhcc zhangwei
	 * @date 20110821
	 * @修改日志：
	 * 
	 */
	public int updateApp_gather3() {
		PreparedStatement ps = null;
		Statement ps2 = null;
		int count = 0;
		String mon_date = date_jr.substring(0, 6);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT B.OP_NO, COUNT(1) FROM APP_BASE A, APP_IDEA B WHERE A.TRACE_NO = B.TRACE_NO ");
		sb.append("AND A.APP_STATE = '3'  AND B.APP_IDEA='1' and a.put_date like '"
				+ mon_date + "%' AND B.OP_NO IN ");
		sb.append("(select username from tbl_org_user where role_no='R006' ) group by b.op_no  ");

		try {
			ps = con.prepareStatement(sb.toString());
			ps2 = con.createStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ps2.addBatch("UPDATE APP_WORK_GATHER SET COUNTS_PASS="
						+ rs.getInt(2) + " WHERE OP_NO='" + rs.getString(1)
						+ "' AND APP_MONTH='" + mon_date + "'");
			}

			int[] counts = ps2.executeBatch();
			count = counts.length;
			BatchPublic.inBatchlog2("O", "O03", "1", con);

			log.info("步骤 更新APP_WORK_GATHER表的counts_pass 操作结束 更新记录:" + count
					+ "-->步骤  :::");
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog2("O", "O03", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps2);
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * 
	 * 功能描述：汇总评审和审批的审批信息PS_DELAY_COUNTS
	 * 
	 * @return
	 * @author dhcc zhangwei
	 * @date 20110821
	 * @修改日志：
	 * 
	 */
	public int updateApp_gather4() {
		PreparedStatement ps = null;
		Statement ps2 = null;
		int count = 0;
		String mon_date = date_jr.substring(0, 6);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.PS_NO, COUNT(1) FROM APP_BASE b, APP_PS_DELAY a WHERE A.TRACE_NO = B.TRACE_NO ");
		sb.append("AND b.APP_STATE = '3'  and b.put_date like '" + mon_date
				+ "%'  ");
		sb.append(" group by a.PS_NO  ");

		try {
			ps = con.prepareStatement(sb.toString());
			ps2 = con.createStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ps2.addBatch("UPDATE APP_WORK_GATHER SET PS_DELAY_COUNTS="
						+ rs.getInt(2) + " WHERE OP_NO='" + rs.getString(1)
						+ "' AND APP_MONTH='" + mon_date + "' ");
			}

			int[] counts = ps2.executeBatch();
			count = counts.length;
			BatchPublic.inBatchlog2("O", "O04", "1", con);

			System.out
					.println("步骤 更新APP_WORK_GATHER表的PS_DELAY_COUNTS 操作结束 更新记录:"
							+ count + "-->步骤  :::");
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog2("O", "O04", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps2);
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * 
	 * 功能描述：汇总评审和审批的审批信息PS_OVER_COUNTS
	 * 
	 * @return
	 * @author dhcc zhangwei
	 * @date 20110821
	 * @修改日志：
	 * 
	 */
	public int updateApp_gather5() {
		PreparedStatement ps = null;
		Statement ps2 = null;
		int count = 0;
		String mon_date = date_jr.substring(0, 6);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.op_NO, COUNT(1) FROM APP_idea a, rpt_xd b WHERE A.app_NO = B.app_NO ");
		sb.append(" AND B.OVER_DAY>0 and B.mon_date like '" + mon_date
				+ "%' AND A.OP_NO IN ");
		sb.append("(select username from tbl_org_user where role_no='R006' ) group by A.op_no  ");

		try {
			ps = con.prepareStatement(sb.toString());
			ps2 = con.createStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ps2.addBatch("UPDATE APP_WORK_GATHER SET PS_OVER_COUNTS="
						+ rs.getInt(2) + " WHERE OP_NO='" + rs.getString(1)
						+ "' AND APP_MONTH='" + mon_date + "' ");
			}

			int[] counts = ps2.executeBatch();
			count = counts.length;
			BatchPublic.inBatchlog2("O", "O99", "1", con);

			log.info("步骤 更新APP_WORK_GATHER表的PS_OVER_COUNTS 操作结束 更新记录:"
							+ count + "-->步骤  :::");
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog2("O", "O99", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps2);
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	public int getStep(String step) {
		if (step == null || "".equals(step)) {
			return 0;
		}
		int i = 0;
		i = Integer.valueOf(step.substring(1));
		return i;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// UpdateMonth m = new UpdateMonth();
		// int i = m.getStep(m.execstep[0]);
		// if (m.execstep != null && "1".equals(m.execstep[1]) &&
		// "L99".equals(m.execstep[0])) {// 已经完全执行成功
		// m.closeConn();
		// return;
		// }
		//
		// if (m.date_jr.endsWith("01")) { // 机构五级分类的处理 月末
		// if (m.execstep != null && "1".equals(m.execstep[1]) &&
		// "L99".equals(m.execstep[0])) {// 已经完全执行成功
		// m.closeConn();
		// return;
		// }
		// //月末批量
		// switch (i) {
		// case 0:
		// case 1: //月末插入下月数据(rpt_xdls)
		// m.updateRpt_xdls();
		// case 2: //月末插入下月数据(risk_five_mon)
		// m.updateRisk_five_mon();
		// case 3: //更新RPT_XD表的MON_DATE
		// m.updateRptXd_MonDate();
		// case 4: //更新表risk_five的相关操作
		// m.updateRisk_five();
		// }
		// m.closeConn();
		// } else {
		// m.closeConn();
		// log.info("不是月末,月末批量不用更新.............."+"\n");
		// }
	}
}
