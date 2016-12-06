package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateStatusDAO {
	private  Connection con;
	public  UpdateStatusDAO(Connection con){
		this.con = con;
	}
	
	/**
	 * 功能描述：根据核心表更新信贷借据表状态 D01
	 * @return 更新记录数
	 * @author DHCC HUOCHUANXI
	 * @date   2011-12-28
	 * @修改日志：修改批量，状态分为两种，已结清0，正常1
	 */
	public int updateLnDue() {

		PreparedStatement ps = null;
		int count = 0;
//		StringBuffer sb = new StringBuffer();
////		sb.append("UPDATE LN_DUE B SET B.DUE_STS='2' WHERE EXISTS (SELECT 1 FROM FHDKFHZ A ");
////		sb.append("WHERE TRIM(A.DKHTH)=B.PACT_NO AND A.HTXH=TO_NUMBER(SUBSTR(B.DUE_NO,-3)) AND ");
////		sb.append("SUBSTR(A.ZHZT,1,2)='*1' AND SUBSTR(JXBZ,1,1) NOT IN ('5','6'))");
//		sb.append("UPDATE LN_DUE SET DUE_STS = CASE ");
//		sb.append("WHEN BAL = 0 AND SUBSTR(DUE_NO,-1) <> 'X' THEN '0' ");
//		sb.append("ELSE '1' END");
//		String sql = sb.toString();
//		System.out.println("步骤 D01 根据核心表更新信贷借据表状态 >>>>>> " + sql);
//		try {
//			ps = con.prepareStatement(sql);
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D01", "1", con);
//			System.out.println("步骤 D01 根据核心表更新信贷借据表状态 >>>>>> " + count + "D01 结束 <<<<<<");
//		} catch (Exception e) {
////			BatchPublic.err("步骤 D01 根据核心表更新信贷借据表状态 >>>>>> " + e.getMessage() + "D01 失败 <<<<<<");
//			BatchPublic.inBatchlog("D", "D01", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
		return count;

	}

	/**
	 * 功能描述： 根据借据表更新报表主表
	 * @return  更新记录数 
	 * @author  DHCC HUOCHUANXI
	 * @date    2011-08-21
	 * @修改日志 
	 */
	public int updatePrtXd() {

//		PreparedStatement ps = null;
		int count = 0;
//		StringBuffer sb = new StringBuffer();
//		sb.append("UPDATE RPT_XD SET DUE_STS = (SELECT DUE_STS FROM LN_DUE WHERE DUE_NO=RPT_XD.DUE_NO)");
//		System.out.println("步骤 D02 根据借据表更新报表主表 >>>>>> " + sb.toString());
//		try {
//			ps = con.prepareStatement(sb.toString());
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D02", "1", con);
//			System.out.println("步骤 D02 根据借据表更新报表主表 >>>>>> " + count + "<<<<<< 步骤 D02 结束 ");
//		} catch (Exception e) {
////			BatchPublic.err("步骤 D02 根据借据表更新报表主表 >>>>>> " + e.getMessage() + "<<<<<< 步骤 D02 失败 ");
//			BatchPublic.inBatchlog("D", "D02", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
		return count;
	}
	/**
	 * 功能描述： 更新循环高额合同的合同状态
	 * @return  更新记录数 
	 * @author  DHCC HUOCHUANXI
	 * @date    2011-08-21
	 * @修改日志 
	 */
//	public int updatePactSts() {
//
//		PreparedStatement ps = null;
//		int count = 0;
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT PACT_AMT,PUT_OUT_AMT,BAL,END_DATE FROM LN_PACT WHERE PUT_OUT_AMT<PACT_AMT AND ");
//		sb.append("TO_DATE(END_DATE,'YYYYMMDD')>TO_DATE((SELECT SYS_DATE FROM SYS_GLOBAL),'YYYYMMDD')");
//		System.out.println("步骤 D03 更新循环高额合同的合同状态 >>>>>> " + sb.toString());
//		try {
//			ps = con.prepareStatement(sb.toString());
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D03", "1", con);
//			System.out.println("步骤 D03 更新循环高额合同的合同状态 >>>>>> " + count + "<<<<<< 步骤 D03 结束 ");
//		} catch (Exception e) {
////			BatchPublic.err("步骤 D03 更新循环高额合同的合同状态 >>>>>> " + e.getMessage() + "<<<<<< 步骤 D03 失败 ");
//			BatchPublic.inBatchlog("D", "D03", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
//		return count;
//	}
	/**
	 * 功能描述： 更新ln_due表的ac_no字段
	 * @return  更新记录数 
	 * @author  DHCC HUOCHUANXI
	 * @date    2011-12-28
	 * @修改日志 
	 */
	public int updateLndueAcno() {
		
//		PreparedStatement ps = null;
		int count = 0;
//		StringBuffer sb = new StringBuffer();
//		sb.append("UPDATE LN_DUE SET AC_NO = (SELECT AC_NO FROM RPT_XD WHERE LN_DUE.DUE_NO=RPT_XD.DUE_NO) WHERE AC_NO IS NULL");
//		System.out.println("步骤 D03 更新ln_due表的ac_no字段 >>>>>> " + sb.toString());
//		try {
//			ps = con.prepareStatement(sb.toString());
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D03", "1", con);
//			System.out.println("步骤 D03 更新ln_due表的ac_no字段 >>>>>> " + count + "<<<<<< 步骤 D03 结束 ");
//		} catch (Exception e) {
////			BatchPublic.err("步骤 D03 更新ln_due表的ac_no字段 >>>>>> " + e.getMessage() + "<<<<<< 步骤 D03 失败 ");
//			BatchPublic.inBatchlog("D", "D03", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
		return count;
	}
	/**
	 * 功能描述： 更新ln_due表的贴现的ac_no字段
	 * @return  更新记录数 
	 * @author  DHCC HUOCHUANXI
	 * @date    2011-12-28
	 * @修改日志 
	 */
	public int updateLndueTXAcno() {
		
//		PreparedStatement ps = null;
		int count = 0;
//		StringBuffer sb = new StringBuffer();
//		sb.append("UPDATE LN_DUE SET AC_NO = (SELECT trim(ZH) FROM FHDKFHZ WHERE LN_DUE.PACT_NO=TRIM(DKHTH) AND TO_NUMBER(SUBSTR(LN_DUE.DUE_NO,-3))=HTXH ");
//		sb.append("AND SUBSTR(ZHZT,2,1)='1') WHERE PRDT_NO IN ('H101','H103') AND AC_NO IS NULL AND SUBSTR(DUE_NO,-1)<>'X'");
//		System.out.println("步骤 D04 更新ln_due表的贴现的ac_no字段 >>>>>> " + sb.toString());
//		try {
//			ps = con.prepareStatement(sb.toString());
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D04", "1", con);
//			System.out.println("步骤 D04 更新ln_due表的ac_no字段 >>>>>> " + count + "<<<<<< 步骤 D04 结束 ");
//		} catch (Exception e) {
////			BatchPublic.err("步骤 D04 更新ln_due表的ac_no字段 >>>>>> " + e.getMessage() + "<<<<<< 步骤 D04 失败 ");
//			BatchPublic.inBatchlog("D", "D04", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
		return count;
	}
	
}
