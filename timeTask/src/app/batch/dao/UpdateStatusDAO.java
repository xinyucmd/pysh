package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateStatusDAO {
	private  Connection con;
	public  UpdateStatusDAO(Connection con){
		this.con = con;
	}
	
	/**
	 * �������������ݺ��ı�����Ŵ���ݱ�״̬ D01
	 * @return ���¼�¼��
	 * @author DHCC HUOCHUANXI
	 * @date   2011-12-28
	 * @�޸���־���޸�������״̬��Ϊ���֣��ѽ���0������1
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
//		System.out.println("���� D01 ���ݺ��ı�����Ŵ���ݱ�״̬ >>>>>> " + sql);
//		try {
//			ps = con.prepareStatement(sql);
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D01", "1", con);
//			System.out.println("���� D01 ���ݺ��ı�����Ŵ���ݱ�״̬ >>>>>> " + count + "D01 ���� <<<<<<");
//		} catch (Exception e) {
////			BatchPublic.err("���� D01 ���ݺ��ı�����Ŵ���ݱ�״̬ >>>>>> " + e.getMessage() + "D01 ʧ�� <<<<<<");
//			BatchPublic.inBatchlog("D", "D01", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
		return count;

	}

	/**
	 * ���������� ���ݽ�ݱ���±�������
	 * @return  ���¼�¼�� 
	 * @author  DHCC HUOCHUANXI
	 * @date    2011-08-21
	 * @�޸���־ 
	 */
	public int updatePrtXd() {

//		PreparedStatement ps = null;
		int count = 0;
//		StringBuffer sb = new StringBuffer();
//		sb.append("UPDATE RPT_XD SET DUE_STS = (SELECT DUE_STS FROM LN_DUE WHERE DUE_NO=RPT_XD.DUE_NO)");
//		System.out.println("���� D02 ���ݽ�ݱ���±������� >>>>>> " + sb.toString());
//		try {
//			ps = con.prepareStatement(sb.toString());
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D02", "1", con);
//			System.out.println("���� D02 ���ݽ�ݱ���±������� >>>>>> " + count + "<<<<<< ���� D02 ���� ");
//		} catch (Exception e) {
////			BatchPublic.err("���� D02 ���ݽ�ݱ���±������� >>>>>> " + e.getMessage() + "<<<<<< ���� D02 ʧ�� ");
//			BatchPublic.inBatchlog("D", "D02", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
		return count;
	}
	/**
	 * ���������� ����ѭ���߶��ͬ�ĺ�ͬ״̬
	 * @return  ���¼�¼�� 
	 * @author  DHCC HUOCHUANXI
	 * @date    2011-08-21
	 * @�޸���־ 
	 */
//	public int updatePactSts() {
//
//		PreparedStatement ps = null;
//		int count = 0;
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT PACT_AMT,PUT_OUT_AMT,BAL,END_DATE FROM LN_PACT WHERE PUT_OUT_AMT<PACT_AMT AND ");
//		sb.append("TO_DATE(END_DATE,'YYYYMMDD')>TO_DATE((SELECT SYS_DATE FROM SYS_GLOBAL),'YYYYMMDD')");
//		System.out.println("���� D03 ����ѭ���߶��ͬ�ĺ�ͬ״̬ >>>>>> " + sb.toString());
//		try {
//			ps = con.prepareStatement(sb.toString());
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D03", "1", con);
//			System.out.println("���� D03 ����ѭ���߶��ͬ�ĺ�ͬ״̬ >>>>>> " + count + "<<<<<< ���� D03 ���� ");
//		} catch (Exception e) {
////			BatchPublic.err("���� D03 ����ѭ���߶��ͬ�ĺ�ͬ״̬ >>>>>> " + e.getMessage() + "<<<<<< ���� D03 ʧ�� ");
//			BatchPublic.inBatchlog("D", "D03", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
//		return count;
//	}
	/**
	 * ���������� ����ln_due���ac_no�ֶ�
	 * @return  ���¼�¼�� 
	 * @author  DHCC HUOCHUANXI
	 * @date    2011-12-28
	 * @�޸���־ 
	 */
	public int updateLndueAcno() {
		
//		PreparedStatement ps = null;
		int count = 0;
//		StringBuffer sb = new StringBuffer();
//		sb.append("UPDATE LN_DUE SET AC_NO = (SELECT AC_NO FROM RPT_XD WHERE LN_DUE.DUE_NO=RPT_XD.DUE_NO) WHERE AC_NO IS NULL");
//		System.out.println("���� D03 ����ln_due���ac_no�ֶ� >>>>>> " + sb.toString());
//		try {
//			ps = con.prepareStatement(sb.toString());
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D03", "1", con);
//			System.out.println("���� D03 ����ln_due���ac_no�ֶ� >>>>>> " + count + "<<<<<< ���� D03 ���� ");
//		} catch (Exception e) {
////			BatchPublic.err("���� D03 ����ln_due���ac_no�ֶ� >>>>>> " + e.getMessage() + "<<<<<< ���� D03 ʧ�� ");
//			BatchPublic.inBatchlog("D", "D03", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
		return count;
	}
	/**
	 * ���������� ����ln_due������ֵ�ac_no�ֶ�
	 * @return  ���¼�¼�� 
	 * @author  DHCC HUOCHUANXI
	 * @date    2011-12-28
	 * @�޸���־ 
	 */
	public int updateLndueTXAcno() {
		
//		PreparedStatement ps = null;
		int count = 0;
//		StringBuffer sb = new StringBuffer();
//		sb.append("UPDATE LN_DUE SET AC_NO = (SELECT trim(ZH) FROM FHDKFHZ WHERE LN_DUE.PACT_NO=TRIM(DKHTH) AND TO_NUMBER(SUBSTR(LN_DUE.DUE_NO,-3))=HTXH ");
//		sb.append("AND SUBSTR(ZHZT,2,1)='1') WHERE PRDT_NO IN ('H101','H103') AND AC_NO IS NULL AND SUBSTR(DUE_NO,-1)<>'X'");
//		System.out.println("���� D04 ����ln_due������ֵ�ac_no�ֶ� >>>>>> " + sb.toString());
//		try {
//			ps = con.prepareStatement(sb.toString());
//			count = ps.executeUpdate();
//			con.commit();
//			BatchPublic.inBatchlog("D", "D04", "1", con);
//			System.out.println("���� D04 ����ln_due���ac_no�ֶ� >>>>>> " + count + "<<<<<< ���� D04 ���� ");
//		} catch (Exception e) {
////			BatchPublic.err("���� D04 ����ln_due���ac_no�ֶ� >>>>>> " + e.getMessage() + "<<<<<< ���� D04 ʧ�� ");
//			BatchPublic.inBatchlog("D", "D04", "2", con);
//			new PublicDAOExHandler().handle(con, e);
//		} finally {
//			DataSourceUtils.closeStatement(ps);
//		}
		return count;
	}
	
}
