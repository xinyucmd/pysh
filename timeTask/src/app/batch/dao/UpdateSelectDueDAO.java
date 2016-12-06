package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.batch.BatchPublic;
import app.util.exc.DataSourceUtils;

public class UpdateSelectDueDAO {
	private Connection con;
	private String riqi;

	public UpdateSelectDueDAO(Connection con) {
		this.con = con;
		riqi = BatchPublic.ztGlobal(con);
	}

	/**
	 * ���������� ����������ݲ��뵽�弶�������
	 * 
	 * @return �����弶����Ĵ���
	 * @author
	 * @date
	 * @�޸���־��
	 */
	public int saveRiskFive() {
		Log log = LogFactory.getLog(this.getClass());
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int flag = 0;

		log.info("���� B01 ��ʼ ������������弶�����   B01 >>>>>>");
		sb.append("INSERT INTO RISK_FIVE(CIF_NO,CIF_NAME,PACT_NO,DUE_NO,BR_NO,ACC_BRNO,MANG_BRNO,MANG_NO,BEG_DATE,");
		sb.append("END_DATE,CLASS_DATE,AC_NO,LN_TYPE,VOU_TYPE,DUE_AMT,BAL,IN_RATE,OUT_RATE,FIVE_STS,GRADE,CLASS_FLAG,APP_FLAG,MON_DATE)");
		sb.append("SELECT A.CIF_NO,A.CIF_NAME,A.PACT_NO,A.DUE_NO,A.BR_NO,A.ACC_BR_NO,A.MANG_BRNO,A.MANG_NO,A.DUE_BEG_DATE,A.DUE_END_DATE,");
		sb.append(riqi)
				.append(",A.DUE_NO,A.LN_TYPE,A.VOU_TYPE,A.DUE_AMT,A.DUE_BAL,A.INNER_INTST,A.OUTER_INTST,A.FIVE_STS,A.GRADE,A.CLASS_FLAG,A.APP_FLAG,")
				.append(riqi.substring(0, 6)).append("50");
		sb.append(" FROM VIEW_LOAN_NOT_IN_RISK_FIVE A");
		
		log.info("���� B01 ��SQL>>>>>>" + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
//			ps.setString(1, riqi);
			flag = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("B", "B01", "1", con); // �ɹ���¼�ɹ�log
			log.info("���� B01 �������� ������������弶����� ����: " + flag);
		} catch (Exception e) {
			log.error("���� B01 ��ʼ  ������������弶����� ����   B01 :: :::", e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			BatchPublic.inBatchlog("B", "B01", "2", con); // ʧ�ܼ�¼ʧ��log
			flag = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return flag;
	}

	/***
	 * 
	 * @����˵��������ݱ���弶������е���Ϣ���뵽Rpt_xd���С�
	 * @author zhaorenxiang
	 * @date Aug 21, 2011
	 * @param @return
	 */
	public int saveRptxd() {
		Log log = LogFactory.getLog(this.getClass());
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int flag = 0;
		String mon_date = riqi.substring(0, 6) + "50";
		log.info("���� B02 ��ʼ �����������Rpt_xd����   B02 >>>>>>");
		sb.append(" INSERT INTO RPT_XD(CIF_NO,CIF_NAME,PACT_NO,DUE_NO,BR_NO,MANG_BRNO,MANG_NO,");
		sb.append(" BEG_DATE,END_DATE,AC_NO,LN_TYPE,VOU_TYPE,DUE_AMT,BAL,FIVE_STS,GRADE,PRDT_NO,LN_RATE,APP_OP_NO,MON_DATE)");
		sb.append(" SELECT A.CIF_NO,A.CIF_NAME,A.PACT_NO,A.DUE_NO,A.BR_NO,A.MANG_BRNO,A.MANG_NO,");
		sb.append(" A.DUE_BEG_DATE,A.DUE_END_DATE,A.AC_NO,A.LN_TYPE,A.VOU_TYPE,A.DUE_AMT,A.DUE_BAL,A.FIVE_STS,");
		sb.append(" A.GRADE,A.PRDT_NO,A.LN_RATE,A.APP_OP_NO,? ");// ��һ������
		sb.append(" FROM VIEW_LOAN_NOT_IN_RPT A ");// ��һ������
		log.info("���� B02 ��SQL>>>>>> " + sb.toString());

		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, mon_date);
//			ps.setString(2, riqi);
			flag = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("B", "B02", "1", con); // �ɹ���¼�ɹ�log
			log.info("���� B02 �������� �����������Rpt_xd�� ����: " + flag);
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			log.error("���� B02 ��ʼ  �����������Rpt_xd�� ����   B02 :: :::", e);
			BatchPublic.inBatchlog("B", "B02", "2", con); // ʧ�ܼ�¼ʧ��log
			flag = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return flag;

	}
	/***
	 * 
	 * @����˵���������ֺ�ͬ����д�뵽�弶������У���Ϊ���ĵ�����ֻ�����ֺ�ͬ�����ڴ���ֻ������棬����ֱ�Ӱ����ֺ�ͬд�뵽rpt_xd��
	 * @author zhaorenxiang
	 * @date Aug 21, 2011
	 * @param @return
	 */
	/*
	 * public int saveRptxd_tiexian(){ PreparedStatement ps = null; StringBuffer
	 * sb = new StringBuffer(); int flag = 0; String mon_date =
	 * riqi.substring(0,6)+"50";
	 * System.out.println("���� B03 ��ʼ �����ֺ�ͬ����д�뵽�弶�������   B03 >>>>>>"); sb.append(
	 * " insert into rpt_xd(cif_no,cif_name,pact_no,due_no,prdt_no,occur_type,mon_date,bal,beg_date,end_date,ac_no,vou_type,five_sts)"
	 * ); sb.append(
	 * " select a.cif_no,a.cif_name,a.pact_no,a.pact_no,a.prdt_no,a.occur_type,?,b.ye,b.khrq,b.dqrq,trim(b.zh),'1','1'"
	 * );//��һ������ sb.append(
	 * " from ln_pact a,fhdkfhz b where a.prdt_no = 'H101' and a.pact_no = trim(b.dkhth) and b.khrq = ?"
	 * );//��һ������ System.out.println("���� B03 ��SQL>>>>>> " + sb.toString());
	 * 
	 * try { ps = con.prepareStatement(sb.toString()); ps.setString(1,
	 * mon_date); ps.setString(2, riqi); flag = ps.executeUpdate();
	 * con.commit(); BatchPublic.inBatchlog("B", "B03", "1", con); // �ɹ���¼�ɹ�log
	 * System.out.println("���� B03 �������� �����ֺ�ͬ����д�뵽�弶������� ����: " + flag); } catch
	 * (SQLException e) { e.printStackTrace(); //
	 * Log.err("���� B03 ��ʼ  �����ֺ�ͬ����д�뵽�弶������� ����   B03 :: :::",e);
	 * BatchPublic.inBatchlog("B", "B03", "2", con); // ʧ�ܼ�¼ʧ��log }finally {
	 * DataSourceUtils.closeStatement(ps); } return flag;
	 * 
	 * }
	 */
}
