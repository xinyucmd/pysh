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
	 * ���������� ���½�ݱ����
	 * 
	 * @return
	 * @author
	 * @date
	 * @�޸���־��
	 */
	public int updateDueBal() {

		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int flag = 0;
		log.info("���� C01 ��ʼ ���½�ݱ�   B01 >>>>>>");
		sb.append("UPDATE LN_DUE A  SET A.BAL=(SELECT B.DUE_BAL FROM LOAN_AC_LN_MST B WHERE B.DUE_NO=A.DUE_NO AND ROWNUM=1) ");

		log.info("���� C01 ��SQL>>>>>> " + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			flag = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("C", "C01", "1", con); // �ɹ���¼�ɹ�log
			log.info("���� C01 �������� ���½�� ����: " + flag);
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
			BatchPublic.inBatchlog("C", "C01", "2", con); // ʧ�ܼ�¼ʧ��log
			flag = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return flag;
	}

	/***
	 * 
	 * @����˵�������º�ͬ�����
	 * @author zhaorenxiang
	 * @date Aug 21, 2011
	 * @param @return
	 */
	public int updatePactBal() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int flag = 0;
		log.info("���� C02 ��ʼ ����ln_pact����   C02 >>>>>>");
		sb.append("UPDATE LN_PACT B SET B.BAL=(SELECT C.BAL FROM (SELECT SUM(B.BAL) AS BAL,B.PACT_NO FROM  LN_DUE B  GROUP BY B.PACT_NO) C  WHERE B.PACT_NO=C.PACT_NO ) ");
		log.info("���� C02 ��SQL>>>>>> " + sb.toString());

		try {
			ps = con.prepareStatement(sb.toString());
			flag = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("C", "C02", "1", con); // �ɹ���¼�ɹ�log
			log.info("���� C02 �������� ����ln_pact�� ����: " + flag);
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			log.error("���� B02 ��ʼ  �����������Rpt_xd�� ����   B02 :: :::", e);
			BatchPublic.inBatchlog("C", "C02", "2", con); // ʧ�ܼ�¼ʧ��log
			flag = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return flag;

	}
}
