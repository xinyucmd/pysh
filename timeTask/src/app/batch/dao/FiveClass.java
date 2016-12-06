package app.batch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.constant.BatchConstant;
import app.batch.entity.ParmBean;
import app.batch.entity.RiskFiveMatrix;
import app.util.exc.DataSourceUtils;

import com.dx.common.util.DateUtil;

public class FiveClass {
	Log log = LogFactory.getLog(this.getClass());
	private Connection con;
	private String riqi;
	private Statement updateFiveStsStmt = null;
	private List<String> updateFiveStsList = new ArrayList<String>();

	public FiveClass(Connection con) {
		this.con = con;
		riqi = BatchPublic.ztGlobal(con);
	}

	/**
	 * 更新五级分类主方法
	 * 
	 * @return
	 */
	public int updateFiveSts() {
		int flag = 0;
		List<ParmBean> list = this.getRptXdList();
		for (int i = 0; i < list.size(); i++) {
			ParmBean bean = list.get(i);

			if (BatchConstant.Client.CLIENT_TYPE_PERSON.equals(bean.getParm3())) {// 个人客户
				/*
				 * if (BatchConstant.Grade.GRADE_AAA.equals(bean.getParm4()))
				 * {// 优秀 List<RiskFiveMatrix> riskFiveMatrix = this
				 * .getFiveClassMatrix("1", bean.getParm6());
				 * this.updateFiveForPers(riskFiveMatrix, bean);// 更新五级分类状态 }
				 * else if
				 * (BatchConstant.Grade.GRADE_BBB.equals(bean.getParm4())) {//
				 * 优秀 List<RiskFiveMatrix> riskFiveMatrix = this
				 * .getFiveClassMatrix("2", bean.getParm6());
				 * this.updateFiveForPers(riskFiveMatrix, bean);// 更新五级分类状态 }
				 * else if
				 * (BatchConstant.Grade.GRADE_CCC.equals(bean.getParm4())) {//
				 * 优秀 List<RiskFiveMatrix> riskFiveMatrix = this
				 * .getFiveClassMatrix("3", bean.getParm6());
				 * this.updateFiveForPers(riskFiveMatrix, bean);// 更新五级分类状态 }
				 * else { List<RiskFiveMatrix> riskFiveMatrix = this
				 * .getFiveClassMatrix("2", bean.getParm6());
				 * this.updateFiveForPers(riskFiveMatrix, bean);// 更新五级分类状态 }
				 */

				List<RiskFiveMatrix> riskFiveMatrix = this.getFiveClassMatrix(
						BatchConstant.FiveClassMatrixType.MATRIX_TYPE_PERSON,
						bean.getParm6());
				this.updateFiveForPers(riskFiveMatrix, bean);// 更新五级分类状态
			} else {
				List<RiskFiveMatrix> riskFiveMatrix = this.getFiveClassMatrix(
						BatchConstant.FiveClassMatrixType.MATRIX_TYPE_CORP,
						bean.getParm6());
				this.updateFiveForCorps(riskFiveMatrix, bean);// 更新五级分类状态
			}
		}
		// 更新五级分类状态，批处理方式
		try {
			con.setAutoCommit(false);
			updateFiveStsStmt = con.createStatement();
			for (int j = 0; j < updateFiveStsList.size(); j++) {
				updateFiveStsStmt.addBatch(updateFiveStsList.get(j));
			}
			flag = updateFiveStsStmt.executeBatch().length;
			con.commit();
			BatchPublic.inBatchlog2("P", "P01", "1", con);
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog2("P", "P01", "2", con);
			e.printStackTrace();
		} finally {
			DataSourceUtils.closeStatement(updateFiveStsStmt);
		}
		return flag;
	}

	/**
	 * 更新五级分类状态(个人)
	 * 
	 * @param riskFiveMatrix
	 * @param bean
	 */
	public void updateFiveForPers(List<RiskFiveMatrix> riskFiveMatrix,
			ParmBean bean) {
//		System.out.println("updateFiveForPers");
		if (riskFiveMatrix == null || riskFiveMatrix.size() == 0) {
			log.info("五级分类矩阵不存在！");
		} else {
			RiskFiveMatrix matrix = riskFiveMatrix.get(0);
			List<ParmBean> list = getLnDue(bean.getParm1());
			if (list.size() > 0) {
				String date = list.get(0).getParm2().toString();
				date = date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6,8);
//				System.out.println(date);
				boolean flg = DateUtil.gteq(date, "2012-01-01");//是否有还款计划
				if (flg) {
					this.updateSqlList("9", bean.getParm1());
				} else {
					int days = 0;
					if (list.get(0).getParm1() != null && !list.get(0).getParm1().equals("")) {
						days = Integer.parseInt(list.get(0).getParm1());
					}
					if (days > 0) {
						if (days <= matrix.getFive_1_end()) {// 五级分类为正常
							this.updateSqlList("1", bean.getParm1());
						} else if (days <= matrix.getFive_2_end()) {// 五级分类为关注
							this.updateSqlList("2", bean.getParm1());
						} else if (days <= matrix.getFive_3_end()) {// 五级分类为次级
							this.updateSqlList("3", bean.getParm1());
						} else if (days <= matrix.getFive_4_end()) {// 五级分类为可疑
							this.updateSqlList("4", bean.getParm1());
						} else {
							this.updateSqlList("5", bean.getParm1());// 五级分类为损失
						}
					} else {
						this.updateSqlList("1", bean.getParm1());
					}
				}
			} else {
				log.info("是否逾期时间不能为空！");
			}
		}
	}

	/**
	 * 更新五级分类状态(对公)
	 * 
	 * @param riskFiveMatrix
	 * @param bean
	 */
	public void updateFiveForCorps(List<RiskFiveMatrix> riskFiveMatrix,
			ParmBean bean) {
//		System.out.println("updateFiveForCorps");
		if (riskFiveMatrix == null || riskFiveMatrix.size() == 0) {
			log.info("五级分类矩阵不存在！");
		} else {
			RiskFiveMatrix matrix = riskFiveMatrix.get(0);
			List<ParmBean> list = getLnDue(bean.getParm1());
			if (list.size() > 0) {
				String date = list.get(0).getParm2().toString();
//				System.out.println(date);
				date = date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6,8);
				boolean flg = DateUtil.gteq(date, "2012-01-01");//是否有还款计划
				if (flg) {
					this.updateSqlList("9", bean.getParm1());
				} else {
					int days = 0;
					if (list.get(0).getParm1() != null && !list.get(0).getParm1().equals("")) {
						days = Integer.parseInt(list.get(0).getParm1());
					}
					if (days > 0) {
						if (days <= matrix.getFive_1_end()) {// 五级分类为正常
							this.updateSqlList("1", bean.getParm1());
						} else if (days <= matrix.getFive_2_end()) {// 五级分类为关注
							this.updateSqlList("2", bean.getParm1());
						} else if (days <= matrix.getFive_3_end()) {// 五级分类为次级
							this.updateSqlList("3", bean.getParm1());
						} else if (days <= matrix.getFive_4_end()) {// 五级分类为可疑
							this.updateSqlList("4", bean.getParm1());
						} else {
							this.updateSqlList("5", bean.getParm1());// 五级分类为损失
						}
					} else {
							this.updateSqlList("1", bean.getParm1());
					}
				}
			} else {
				log.info("是否逾期时间不能为空！");
			}
		}
	}

	/**
	 * 将更新sql统一放到list里
	 * 
	 * @param five_sts
	 * @param due_no
	 */
	public void updateSqlList(String five_sts, String due_no) {
		String updateXd = "update rpt_xd set FIVE_STS='" + five_sts
				+ "',CLASSDATE='" + riqi + "' WHERE DUE_NO='" + due_no + "'";
		String updateDue = "update risk_five set FIVE_STS='" + five_sts
				+ "',CLASS_DATE='" + riqi + "' WHERE DUE_NO='" + due_no + "'";
		updateFiveStsList.add(updateXd);
		updateFiveStsList.add(updateDue);
	}

	/**
	 * 查询客户的五级分类矩阵
	 * 
	 * @param maxtrix_type
	 * @param vou_type
	 * @return
	 */
	public List<RiskFiveMatrix> getFiveClassMatrix(String maxtrix_type,
			String vou_type) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		log.info("步骤五级分类 开始 查询五级分类矩阵");
		sb.append("SELECT MATRIX_TYPE,VOU_TYPE, FIVE_1_BEG,FIVE_1_END,FIVE_2_BEG, FIVE_2_END,");
		sb.append("FIVE_3_BEG,FIVE_3_END,FIVE_4_BEG, FIVE_4_END,FIVE_5_BEG, PD_TYPE,GRADE_TYPE");
		sb.append(" FROM Risk_Five_Matrix WHERE MATRIX_TYPE=? AND VOU_TYPE=?");

		log.info("步骤 五级分类的SQL>>>>>> " + sb.toString());
		List<RiskFiveMatrix> list = new ArrayList<RiskFiveMatrix>();
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, maxtrix_type);
			ps.setString(2, vou_type);
			ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				RiskFiveMatrix result = new RiskFiveMatrix();
				result.setMatrix_type(rs.getString(1));
				result.setVou_type(rs.getString(2));
				result.setFive_1_beg(rs.getInt(3));
				result.setFive_1_end(rs.getInt(4));
				result.setFive_2_beg(rs.getInt(5));
				result.setFive_2_end(rs.getInt(6));
				result.setFive_3_beg(rs.getInt(7));
				result.setFive_3_end(rs.getInt(8));
				result.setFive_4_beg(rs.getInt(9));
				result.setFive_4_end(rs.getInt(10));
				result.setFive_5_beg(rs.getInt(11));
				result.setPd_type(rs.getString(12));
				result.setGrade_type(rs.getString(13));
				list.add(result);
			}
			log.info("步骤 五级分类 矩阵查询操作结束 ");
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return list;
	}

	/**
	 * 查询rpt_xd信息
	 * 
	 * @param maxtrix_type
	 * @param vou_type
	 * @return
	 */
	public List<ParmBean> getRptXdList() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		log.info("步骤五级分类 开始 查询rpt_xd信息");
		sb.append("SELECT DUE_NO,CIF_NO,CIF_TYPE,grade,to_date(?,'yyyyMMdd')-to_date(beg_date,'yyyyMMdd') as dates,vou_type FROM rpt_xd");

		log.info("步骤 五级分类 rpt_xd的SQL>>>>>> " + sb.toString());
		List<ParmBean> list = new ArrayList<ParmBean>();
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, riqi);
			ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				ParmBean result = new ParmBean();
				result.setParm1(rs.getString(1));// due_no
				result.setParm2(rs.getString(2));// CIF_NO
				result.setParm3(rs.getString(3));// CIF_TYPE
				result.setParm4(rs.getString(4));// grade
				result.setParm5(rs.getString(5));// dates
				result.setParm6(rs.getString(6));// vou_type
				list.add(result);
			}
			log.info("步骤 五级分类 查询rpt_xd操作结束 ");
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return list;
	}

	public List<ParmBean> getLnDue(String due_no) {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		sb.append("SELECT OVER_DAYS,beg_date FROM LN_DUE WHERE DUE_NO=?");

		List<ParmBean> list = new ArrayList<ParmBean>();
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, due_no);
			ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				ParmBean result = new ParmBean();
				result.setParm1(rs.getString(1));// overdays
				result.setParm2(rs.getString(2));// begindate
				list.add(result);
			}
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return list;
	}
	
	/**
	 * 获得客户等级
	 * 
	 * @param cif_no
	 * @return
	 */
	public String getCifGrade(String cif_no) {
		String grade = "";
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		ResultSet rs = null;
		log.info("步骤 获得客户等级 ");
		sb.append("SELECT NVL(GRADE,'X') AS GRADE");
		sb.append(" FROM CIF_BASE WHERE CIF_NO=? AND CIF_TYPE=2");

		log.info("步骤 获得客户等级的SQL>>>>>> " + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, cif_no);
			ps.executeQuery();
			rs = ps.executeQuery();
			while (rs.next()) {
				grade = rs.getString(1);
			}
			log.info("步骤 五级分类 矩阵查询操作结束 ");
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return grade;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FiveClass five = new FiveClass(getConnection());
		five.updateFiveSts();
	}

	/**
	 * 获得数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Connection getConnection() throws Exception {

		Connection conn = null;

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.2.19:1521:orcl", "cmsii",
					"1QAZXSW2");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		return conn;
	}
}
