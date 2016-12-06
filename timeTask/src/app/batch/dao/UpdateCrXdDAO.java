package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.batch.BatchPublic;
import app.util.exc.DataSourceUtils;

public class UpdateCrXdDAO {
	Log log = LogFactory.getLog(this.getClass());
	private Connection con;
	private String riqi;

	public UpdateCrXdDAO(Connection con) {
		this.con = con;
		riqi = BatchPublic.ztGlobal(con);

	}

	/**
	 * ���������� �ر�Connection �����������Ϣ
	 * 
	 * @return
	 * @author chensanhua
	 * @date 2011.11.12
	 * @�޸���־��
	 */
	public int updateAppInfo() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("update rpt_xd set app_no = (select app_no from ln_pact where rpt_xd.pact_no = ln_pact.pact_no) where app_no is null ");
		log.info("�����������Ϣ\n\t\t" + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J01", "1", con);
		} catch (Exception e) {
			log.error(
					"���� -J01 rpt_xd �����������Ϣ ����ʧ�� -->���� -J02  :::"
							+ e.getMessage(), e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J01", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("����  -J01 rpt_xd�����������Ϣ �������� ���¼�¼:" + count
				+ "-->���� -J02  :::");
		return count;
	}

	/**
	 * ���������� �ر�Connection ���º�ͬ��Ϣ���� ���±䶯�˵ĺ�ͬ��Ϣ���绹�����Ϣ��
	 * 
	 * @return
	 * @author xueruipeng
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updatePactInfo() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET(PRDT_NO,PACT_TYPE,LN_USE)= ");
		sb.append("(SELECT a.PRDT_NO,a.PACT_TYPE,a.LN_USE FROM LN_PACT a  WHERE ");
		sb.append(" a.PACT_NO = RPT_XD.PACT_NO AND a.PACT_NO IS NOT NULL)");
		log.info("���º�ͬ��Ϣ\n\t\t" + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J02", "1", con);
		} catch (Exception e) {
			log.error(
					"���� -J02 rpt_xd ���º�ͬ��Ϣ ����ʧ�� -->���� -J03  :::"
							+ e.getMessage(), e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J02", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("����  -J02 rpt_xd���º�ͬ��Ϣ �������� ���¼�¼:" + count + "-->���� -J03  :::");
		return count;
	}

	/**
	 * ���������� �ر�Connection ����������Ϣ
	 * 
	 * @return
	 * @author xueruipeng
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updateApplyInfo() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET(IF_AUTH,ITEM_NO,LN_TRADE_NO)= (SELECT a.AUTH_TYPE,a.ITEM_NO,a.LN_TRADE_NO ");
		sb.append(" FROM APPLY_BASE a  WHERE a.APP_NO = RPT_XD.APP_NO AND a.APP_NO IS NOT NULL)");
		log.info("����������Ϣ\n\t\t" + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J03", "1", con);
		} catch (Exception e) {
			log.error(
					"���� -J03 rpt_xd����������Ϣ ����ʧ�� -->���� -J04  :::"
							+ e.getMessage(), e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J03", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("����  -J03 rpt_xd����������Ϣ �������� ���¼�¼:" + count + "-->���� -J04  :::");
		return count;
	}

	/**
	 * ���������� �ر�Connection ���¶Թ��ͻ���Ϣ
	 * 
	 * @return
	 * @author xueruipeng
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updateCorpInfo() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET(CIF_TYPE,MANG_BRNO,WAY_NO,PROJ_SIZE,ID_NO,ADDR,PHONE)=  ");
		sb.append(" (SELECT '1',a.MANG_BR_NO,a.WAY_NO,a.PROJ_SIZE,a.ID_NO,a.COM_ADDR,a.CIF_TEL ");
		sb.append(" FROM CIF_CORP_INF a  WHERE a.CIF_NO = RPT_XD.CIF_NO AND a.CIF_NO IS NOT NULL)  WHERE CIF_TYPE IS NULL ");
		log.info("���¶Թ��ͻ���Ϣ\n\t\t" + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J04", "1", con);
		} catch (Exception e) {
			log.error(
					"���� -J04 rpt_xd���¶Թ��ͻ���Ϣ ����ʧ�� -->���� -J05  :::"
							+ e.getMessage(), e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J04", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("����  -J04 rpt_xd����������Ϣ �������� ���¼�¼:" + count + "-->���� -J05  :::");
		return count;
	}

	/**
	 * ���������� �ر�Connection ���¸��˿ͻ���Ϣ
	 * 
	 * @return
	 * @author xueruipeng
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updatePersonInfo() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET(CIF_TYPE,MANG_BRNO,ID_NO,ADDR,PHONE)= (SELECT '2',a.MANG_BR_NO,a.ID_NO,a.COMM_ADDR,a.CELL ");
		sb.append("  FROM CIF_PERS_INF a  WHERE a.CIF_NO = RPT_XD.CIF_NO AND a.CIF_NO IS NOT NULL)  WHERE CIF_TYPE IS NULL ");
		log.info("���¸��˿ͻ���Ϣ\n\t\t" + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J05", "1", con);
		} catch (Exception e) {
			log.error(
					"���� -J05 rpt_xd���¸��˿ͻ���Ϣ ����ʧ�� -->���� -J06  :::"
							+ e.getMessage(), e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J05", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("����  -J05 rpt_xd���¸��˿ͻ���Ϣ �������� ���¼�¼:" + count
				+ "-->���� -J06  :::");
		return count;
	}

	/**
	 * ���������� �ر�Connection ���½����Ϣ
	 * 
	 * @return
	 * @author xueruipeng
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updateDueInfo() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET(BR_NO,AC_NO,ACC_NO,CUR_NO,DUE_AMT,BAL,TERM_MON,OCCUR_TYPE,LN_RATE ");
		sb.append(",VOU_TYPE,IC_TYPE,PAY_TYPE,DUE_STS,MANG_NO,OLD_END_DATE)= (SELECT a.BR_NO,a.AC_NO,a.ACC_HRT,a.CUR_NO,a.DUE_AMT,");
		sb.append("a.bal,a.TERM_MON,a.OCCUR_TYPE,a.LN_RATE");
		sb.append(",a.VOU_TYPE,a.IC_TYPE,a.PAY_TYPE,a.DUE_STS,a.MANG_NO,a.OLD_END_DATE");
		sb.append(" FROM LN_DUE a  WHERE a.DUE_NO = RPT_XD.DUE_NO AND a.DUE_NO IS NOT NULL ) WHERE EXISTS (SELECT 1 FROM ln_due WHERE ln_due.due_no=rpt_xd.due_no )");
		log.info("���½����Ϣ\n\t\t" + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J06", "1", con);
		} catch (Exception e) {
			log.error(
					"���� -J06 rpt_xd���½����Ϣ ����ʧ�� -->���� -J07  :::"
							+ e.getMessage(), e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J06", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("����  -J06 rpt_xd���½����Ϣ �������� ���¼�¼:" + count + "-->���� -J07  :::");
		return count;
	}

	/**
	 * ���������� �ر�Connection �������õȼ�
	 * 
	 * @return
	 * @author xueruipeng
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updateCLevel() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET GRADE = (SELECT a.GRADE FROM CIF_BASE a  WHERE a.CIF_NO = RPT_XD.CIF_NO AND a.CIF_NO IS NOT NULL)");
		log.info("�������õȼ���Ϣ\n\t\t" + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J07", "1", con);
		} catch (Exception e) {
			log.error(
					"���� -J07 rpt_xd�������õȼ���Ϣ ����ʧ�� -->���� -J08  :::"
							+ e.getMessage(), e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J07", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("����  -J07 rpt_xd�������õȼ���Ϣ �������� ���¼�¼:" + count
				+ "-->���� -J08  :::");
		return count;
	}

	/**
	 * ���������� �ر�Connection ���º�����Ŀ��Ϣ
	 * 
	 * @return
	 * @author chensanhua
	 * @date 2011.11.12
	 * @�޸���־��
	 */
	public int updateTrust() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("update rpt_xd set (item_no,coop_flag) = ((select item_no,'1' from apply_base where apply_base.app_no=rpt_xd.app_no)) where app_no in (");
		sb.append("select app_no from apply_base where item_no is not null) and item_no is not null");
		log.info("���º�����Ŀ��Ϣ\n\t\t" + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J08", "1", con);
		} catch (Exception e) {
			log.error(
					"���� -J08 rpt_xd���º�����Ŀ��Ϣ ����ʧ�� -->���� -J08  :::"
							+ e.getMessage(), e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J08", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		log.info("����  -J08 rpt_xd���º�����Ŀ��Ϣ �������� ���¼�¼:" + count
				+ "-->���� -J09  :::");
		return count;
	}

	// /**
	// * ���������������ļ�����״̬
	// * @return
	// * @author DHCC HUOCHUANXI
	// * @date 2011.08.21
	// * @�޸���־��
	// */
	// public int updateRiskFour(){
	// PreparedStatement ps = null;
	// StringBuffer sb = new StringBuffer();
	// int count = 0;
	// sb.append("UPDATE RPT_XD SET FOUR_STS = CASE ");
	// sb.append("WHEN AC_NO IN (SELECT TRIM(ZH) FROM FHDKFHZ WHERE SUBSTR(ZHZT,1,2) ='11' AND YE > 0 ) THEN '1' ");
	// sb.append("WHEN AC_NO IN (SELECT TRIM(ZH) FROM FHDKFHZ WHERE SUBSTR(ZHZT,1,2) ='21' AND YE > 0 ) THEN '2' ");
	// sb.append("WHEN AC_NO IN (SELECT TRIM(ZH) FROM FHDKFHZ WHERE SUBSTR(ZHZT,1,2) ='41' AND YE > 0 ) THEN '3' ");
	// sb.append("WHEN AC_NO IN (SELECT TRIM(ZH) FROM FHDKFHZ WHERE SUBSTR(ZHZT,1,2) ='51' AND YE > 0 ) THEN '4' END");
	// log.info("rpt_xd�����ļ�����״̬ ���� -J10 ----��ʼ  " + sb.toString());
	// try{
	// ps = con.prepareStatement(sb.toString());
	// count = ps.executeUpdate();
	// con.commit();
	// BatchPublic.inBatchlog("J", "J10", "1", con);
	// log.info("���� -J10 rpt_xd�����ļ�����״̬ >>>>>> " + count +
	// " ���� -J10 ���� <<<<<< ");
	// } catch (Exception e) {
	// log.error("���� -J10 rpt_xd�����ļ�����״̬ >>>>>> " + e.getMessage() +
	// " ���� -J10 ʧ�� <<<<<< ");
	// BatchPublic.inBatchlog("J", "J10", "2", con);
	// new Exception(e.getMessage());
	// } finally {
	// DataSourceUtils.closeStatement(ps);
	// }
	// return count;
	// }
	/**
	 * �������������º��Ļ�����
	 * 
	 * @return
	 * @author DHCC HUOCHUANXI
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	// public int updateHxBrno(){
	// PreparedStatement ps = null;
	// StringBuffer sb = new StringBuffer();
	// int count = 0;
	// sb.append("update rpt_xd set br_no = (select trim(fhdkfhz.jgbm) from fhdkfhz where trim(fhdkfhz.zh) = rpt_xd.ac_no) where (br_no not in (select br_no from tbl_org_departments) or br_no is null)");
	// log.info("rpt_xd���º��Ļ����� ���� -J11 ----��ʼ  " + sb.toString());
	// try{
	// ps = con.prepareStatement(sb.toString());
	// count = ps.executeUpdate();
	// con.commit();
	// BatchPublic.inBatchlog("J", "J11", "1", con);
	// log.info("���� -J11 rpt_xd���º��Ļ����� >>>>>> " + count +
	// " ���� -J11 ���� <<<<<< ");
	// } catch (Exception e) {
	// log.error("���� -J11 rpt_xd���º��Ļ����� >>>>>> " + e.getMessage() +
	// " ���� -J11 ʧ�� <<<<<< ");
	// BatchPublic.inBatchlog("J", "J11", "2", con);
	// new Exception(e.getMessage());
	// } finally {
	// DataSourceUtils.closeStatement(ps);
	// }
	// return count;
	// }
	/**
	 * ���������������Ŵ����˻�����
	 * 
	 * @return
	 * @author DHCC HUOCHUANXI
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	// public int updateXdBrno(){
	// PreparedStatement ps = null;
	// StringBuffer sb = new StringBuffer();
	// int count = 0;
	// sb.append("update rpt_xd set br_no = (select hx_br_rel.xd_br_no from hx_br_rel where rpt_xd.br_no=hx_br_rel.hx_br_no) where br_no not in (select br_no from tbl_org_departments)");
	// log.info("rpt_xd�����Ŵ����˻����� ���� -J12 ----��ʼ  " + sb.toString());
	// try{
	// ps = con.prepareStatement(sb.toString());
	// count = ps.executeUpdate();
	// con.commit();
	// BatchPublic.inBatchlog("J", "J12", "1", con);
	// log.info("���� -J12 rpt_xd�����Ŵ����˻����� >>>>>> " + count +
	// " ���� -J12 ���� <<<<<< ");
	// } catch (Exception e) {
	// log.error("���� -J12 rpt_xd�����Ŵ����˻����� >>>>>> " + e.getMessage() +
	// " ���� -J12 ʧ�� <<<<<< ");
	// BatchPublic.inBatchlog("J", "J12", "2", con);
	// new Exception(e.getMessage());
	// } finally {
	// DataSourceUtils.closeStatement(ps);
	// }
	// return count;
	// }
	/**
	 * ���������������Ŵ�����ϵͳ�ܻ��������ܻ�����
	 * 
	 * @return
	 * @author DHCC HUOCHUANXI
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updateMangBrNo() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("update rpt_xd set (mang_brno,mang_no) = (select br_no,op_no from apply_base where rpt_xd.app_no=apply_base.app_no)");
		log.info("rpt_xd�����Ŵ�����ϵͳ�ܻ��������ܻ����� ���� -J13 ----��ʼ  " + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J13", "1", con);
			log.info("���� -J13 rpt_xd�����Ŵ�����ϵͳ�ܻ��������ܻ����� >>>>>> " + count
					+ " ���� -J13 ���� <<<<<< ");
		} catch (Exception e) {
			log.error("���� -J13 rpt_xd�����Ŵ�����ϵͳ�ܻ��������ܻ����� >>>>>> "
					+ e.getMessage() + " ���� -J13 ʧ�� <<<<<< ");
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J13", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * �������������������������
	 * 
	 * @return
	 * @author DHCC HUOCHUANXI
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updateTXBal() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET BAL =(SELECT YE FROM FHDKFHZ WHERE RPT_XD.AC_NO=TRIM(FHDKFHZ.ZH)) WHERE RPT_XD.PRDT_NO IN ('H101','H102')");
		log.info("rpt_xd����������� ���� -J14 ----��ʼ  " + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J14", "1", con);
			log.info("���� -J14 rpt_xd����������� >>>>>> " + count
					+ " ���� -J14 ���� <<<<<< ");
		} catch (Exception e) {
			log.error("���� -J14 rpt_xd����������� >>>>>> " + e.getMessage()
					+ " ���� -J14 ʧ�� <<<<<< ");
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J14", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * ������������������xd�������¡������ա���������
	 * 
	 * @return
	 * @author DHCC HUOCHUANXI
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updateTimeType() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET TIME_TYPE = CASE ");
		sb.append("WHEN (SELECT TERM_MON FROM LN_DUE WHERE RPT_XD.DUE_NO=LN_DUE.DUE_NO AND SUBSTR(RPT_XD.DUE_NO,-1)<>'X' AND LN_DUE.BAL>0)<=12 THEN '1' ");
		sb.append("WHEN ((SELECT TERM_MON FROM LN_DUE WHERE RPT_XD.DUE_NO=LN_DUE.DUE_NO AND SUBSTR(RPT_XD.DUE_NO,-1)<>'X' AND LN_DUE.BAL>0)>12 and ");
		sb.append("(SELECT TERM_MON FROM LN_DUE WHERE RPT_XD.DUE_NO=LN_DUE.DUE_NO AND SUBSTR(RPT_XD.DUE_NO,-1)<>'X' AND LN_DUE.BAL>0)<=60) THEN '2' ");
		sb.append("WHEN (SELECT TERM_MON FROM LN_DUE WHERE RPT_XD.DUE_NO=LN_DUE.DUE_NO AND SUBSTR(RPT_XD.DUE_NO,-1)<>'X' AND LN_DUE.BAL>0)>60 THEN '3' ");
		sb.append("END");
		log.info("rpt_xd����������� ���� -J15 ----��ʼ  " + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J15", "1", con);
			log.info("���� -J15 rpt_xd����������� >>>>>> " + count
					+ " ���� -J15 ���� <<<<<< ");
		} catch (Exception e) {
			log.error("���� -J15 rpt_xd����������� >>>>>> " + e.getMessage()
					+ " ���� -J15 ʧ�� <<<<<< ");
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J15", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * ������������������H101���ֵ�time_type
	 * 
	 * @return
	 * @author DHCC HUOCHUANXI
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updateTXTimeType() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET TIME_TYPE = '1' WHERE PRDT_NO='H101' AND SUBSTR(DUE_NO,-1)<>'X' AND BAL>0 AND TIME_TYPE IS NULL");
		log.info("rpt_xd����������� ���� -J16 ----��ʼ  " + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J16", "1", con);
			log.info("���� -J16 rpt_xd����������� >>>>>> " + count
					+ " ���� -J16 ���� <<<<<< ");
		} catch (Exception e) {
			log.error("���� -J16 rpt_xd����������� >>>>>> " + e.getMessage()
					+ " ���� -J16 ʧ�� <<<<<< ");
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J16", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * ��������������LN_TYPE
	 * 
	 * @return
	 * @author DHCC HUOCHUANXI
	 * @date 2011.08.21
	 * @�޸���־��
	 */
	public int updateLnType() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		sb.append("UPDATE RPT_XD SET LN_TYPE = (SELECT LN_TYPE FROM PROD_BASE WHERE RPT_XD.PRDT_NO=PROD_BASE.PRDT_NO) WHERE ");
		sb.append("SUBSTR(DUE_NO,-1)<>'X' AND BAL>0 AND LN_TYPE IS NULL");// 20111214
		log.info("rpt_xd����������� ���� -J17 ----��ʼ  " + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J17", "1", con);
			log.info("���� -J17 ����LN_TYPE >>>>>> " + count
					+ " ���� -J17 ���� <<<<<< ");
		} catch (Exception e) {
			log.error("���� -J17 ����LN_TYPE >>>>>> " + e.getMessage()
					+ " ���� -J17 ʧ�� <<<<<< ");
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J17", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * ��������������xd����������
	 * 
	 * @return
	 * @author DHCC HUOCHUANXI
	 * @date 2011-12-28
	 * @�޸���־��
	 */
	public int updateVouCifName() {
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		String sql = "";
		String sql1 = "";
		String sql2 = "";
		int count = 0;
		String result = "";
		List list = new ArrayList();
		List list1 = null;
		Map map = new HashMap();
		try {
			sql = "SELECT PACT_NO FROM RPT_XD WHERE BAL>0 AND SUBSTR(DUE_NO,-1)<>'X' GROUP BY PACT_NO";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				result = rs.getString(1);
				list.add(result);
			}
			rs.close();
			result = "";
			sql1 = "SELECT DISTINCT VOU_CIF_NAME FROM APPLY_ASSURE_INF WHERE PACT_PLUS_NO IN (SELECT PACT_PLUS_NO FROM "
					+ "LN_PACT_PLUS WHERE PACT_NO = ? AND PACT_VOU_TYPE='3')";
			ps1 = con.prepareStatement(sql1);
			for (int i = 0; i < list.size(); i++) {
				list1 = new ArrayList();
				ps1.setString(1, (String) list.get(i));
				rs = ps1.executeQuery();
				while (rs.next()) {
					result = rs.getString(1);
					list1.add(result);
				}
				rs.close();
				result = "";
				for (int j = 0; j < list1.size(); j++) {
					if (j == (list1.size() - 1)) {
						result += (String) list1.get(j);
					} else {
						result += (String) list1.get(j) + ",";
					}
					map.put(list.get(i), result);
				}
				result = "";
				list1 = null;
			}
			Set<String> mapSet = map.keySet();
			Iterator<String> itor = mapSet.iterator();
			sql2 = "UPDATE RPT_XD SET STR1 = ? WHERE PACT_NO = ?";
			ps2 = con.prepareStatement(sql2);
			while (itor.hasNext()) {
				String key = itor.next();
				ps2.setString(1, (String) map.get(key));
				ps2.setString(2, key);
				count += ps2.executeUpdate();
			}
			con.commit();
			BatchPublic.inBatchlog("J", "J18", "1", con);
			log.info("���� -J18 rpt_xd���µ��������� >>>>>> " + count
					+ " ���� -J18 ���� <<<<<< ");
		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			log.error("���� -J18 rpt_xd���µ��������� >>>>>> " + e.getMessage()
					+ " ���� -J18 ʧ�� <<<<<< ", e);
			BatchPublic.inBatchlog("J", "J18", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
			DataSourceUtils.closeStatement(ps1);
			DataSourceUtils.closeStatement(ps2);
		}
		return count;
	}

	// �������ڵ�����
	public int update_overDay() {
		PreparedStatement ps = null;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		// sb.append("UPDATE RPT_XD set OVER_DAY = floor( to_date(" + riqi
		// + ",'yyyymmdd') - to_date(end_date,'yyyymmdd')) where  bal>0 ");

		sb.append(
				"UPDATE RPT_XD t SET t.OVER_DAY = (SELECT NVL(a.OVER_DAYS,0) FROM LN_DUE a ")
				.append(" WHERE a.DUE_NO = t.DUE_NO AND a.DUE_NO IS NOT NULL ) ")
				.append("WHERE EXISTS (SELECT 1 FROM ln_due WHERE ln_due.due_no=t.due_no )");
		log.info("rpt_xd������������ ----��ʼ  " + sb.toString());
		try {
			ps = con.prepareStatement(sb.toString());
			count = ps.executeUpdate();
			con.commit();
			BatchPublic.inBatchlog("J", "J19", "1", con);
			log.info("���� -J19 ����rpt_xd������������ - >>>>>> " + count
					+ " ���� -J19 ���� <<<<<< ");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("���� -J19 ����rpt_xd������������ >>>>>> " + e.getMessage()
					+ " ���� -J19 ʧ�� <<<<<< ");
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			BatchPublic.inBatchlog("J", "J19", "2", con);
			count = -1;
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

}
