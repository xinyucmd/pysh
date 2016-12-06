package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ibatis.sqlmap.engine.mapping.result.ResultMap;

import app.batch.BatchPublic;
import app.batch.entity.Rptwjfl_fh;
import app.batch.entity.Rptwjfl_zh;
import app.batch.entity.Rptxydjqktjb;
import app.util.exc.DataSourceUtils;

/**
 * 资金投向明细表
 * 
 * @author Administrator
 * 
 */
public class RptwjflDAO {
	private Connection conn;
	private String date;
	Rptwjfl_zh fl; // 五级分类-----总行
	Rptwjfl_fh ffl;
	private List<Rptwjfl_zh> list1 = new ArrayList<Rptwjfl_zh>();
	private List<Rptwjfl_fh> list2 = new ArrayList<Rptwjfl_fh>();

	public RptwjflDAO(Connection conn) {
		this.conn = conn;
		// Leiji();
	}

	// 统计
	public void Leiji() {
		PreparedStatement ps1 = null; // 时间
		PreparedStatement ps2 = null; // 年初 ----------总行当月
		PreparedStatement ps3 = null; // 本期 ----------总行当月
		PreparedStatement ps4 = null; // 从年初到目前为止的笔数 ----------总行当月
		PreparedStatement ps5 = null; // 去年本期的笔数 ----------总行当月

		PreparedStatement ps6 = null; // 年初 --------总行非当月
		PreparedStatement ps7 = null; // 本期 --------总行非当月
		PreparedStatement ps8 = null; // 从年初到目前为止的笔数 --------总行非当月
		PreparedStatement ps9 = null; // 去年本期的笔数 --------总行非当月
		// ====================================================================
		PreparedStatement ps10 = null; // 年初 --------分行当月
		PreparedStatement ps11 = null; // 本期 --------分行当月
		PreparedStatement ps12 = null; // 从年初到目前为止的笔数 --------分行当月
		PreparedStatement ps13 = null; // 去年本期的笔数 --------分行当月

		PreparedStatement ps14 = null; // 年初 --------分行非当月
		PreparedStatement ps15 = null; // 本期 --------分行非当月
		PreparedStatement ps16 = null; // 从年初到目前为止的笔数 --------分行非当月
		PreparedStatement ps17 = null; // 去年本期的笔数 --------分行非当月

		// ===================================================================

		ResultSet rs1 = null; // 时间
		ResultSet rs2 = null; // 年初 ----------总行当月
		ResultSet rs3 = null; // 本期 ----------总行当月
		ResultSet rs4 = null; // 从年初到目前为止的笔数 ----------总行当月
		ResultSet rs5 = null; // 去年本期的笔数 　 ----------总行当月

		ResultSet rs6 = null; // 年初 --------总行非当月
		ResultSet rs7 = null; // 本期 --------总行非当月
		ResultSet rs8 = null; // 从年初到目前为止的笔数 --------总行非当月
		ResultSet rs9 = null; // 去年本期的笔数 --------总行非当月
		// ========================================================================
		ResultSet rs10 = null; // 年初 ----------分行当月
		ResultSet rs11 = null; // 本期 ----------分行当月
		ResultSet rs12 = null; // 从年初到目前为止的笔数 ----------分行当月
		ResultSet rs13 = null; // 去年本期的笔数 　 ----------分行当月

		ResultSet rs14 = null; // 年初 --------分行非当月
		ResultSet rs15 = null; // 本期 --------分行非当月
		ResultSet rs16 = null; // 从年初到目前为止的笔数 --------分行非当月
		ResultSet rs17 = null; // 去年本期的笔数 --------分行非当月
		// ===========================================================================
		String sql1 = "SELECT LST_DATE FROM SYS_GLOBAL";
		// ============================总行当月RPT_XD========================================================
		String sql2 = " select five_sts,MON_DATE,BR_NO, count(*) as coun,sum(bal) as bal  FROM RPT_XD  WHERE LN_TYPE IN ('1','3') AND BAL>0 and"
				+ " to_date(beg_date,'yyyy/mm/dd')>= add_months(trunc(to_date(?,'yyyy/mm/dd'),'yyyy'),-12) "
				+ "and to_date(beg_date,'yyyy/mm/dd')<= trunc(to_date(?,'yyyy/mm/dd'),'yyyy')-1  group by five_sts,MON_DATE, BR_NO";

		String sql3 = " select five_sts, MON_DATE,BR_NO, count(*) as coun,sum(bal) as bal FROM RPT_XD  WHERE LN_TYPE IN ('1','3') AND BAL>0 "
				+ " and substr(beg_date,5,2)=substr(?,5,2) and substr(beg_date,1,4)=substr(?,1,4) group by five_sts,MON_DATE, BR_NO";

		String sql4 = " select five_sts,MON_DATE, BR_NO, count(*) as coun,sum(bal) as bal FROM RPT_XD  WHERE LN_TYPE IN ('1','3') AND BAL>0  "
				+ " and to_date(beg_date,'yyyy/mm/dd')>=trunc(to_date(?,'yyyy/mm/dd'),'yyyy') and"
				+ " to_date(beg_date,'yyyy/mm/dd')<=to_date(?,'yyyy/mm/dd')  group by five_sts,MON_DATE, BR_NO";

		String sql5 = " select five_sts,MON_DATE,BR_NO,  count(*) as coun,sum(bal) as bal FROM RPT_XD  WHERE LN_TYPE IN ('1','3') AND BAL>0   "
				+ "and substr(beg_date,1,4)=substr(?,1,4)-1 and substr(beg_date,5,2)=substr(?,5,2)  group by five_sts,MON_DATE, BR_NO";
		// =============================总行非当月RPT_XDLS===========================================================
		String sql6 = " select five_sts, MON_DATE, BR_NO, count(*) as coun,sum(bal) as bal FROM RPT_XDLS  WHERE LN_TYPE IN ('1','3') AND BAL>0  "
				+ " and to_date(beg_date,'yyyy/mm/dd')>= add_months(trunc(to_date(?,'yyyy/mm/dd'),'yyyy'),-12)"
				+ " and to_date(beg_date,'yyyy/mm/dd')<= trunc(to_date(?,'yyyy/mm/dd'),'yyyy')-1 group by five_sts,MON_DATE, BR_NO";

		String sql7 = " select five_sts,MON_DATE, BR_NO,  count(*) as coun,sum(bal) as bal FROM RPT_XDLS  WHERE LN_TYPE IN ('1','3') AND BAL>0 "
				+ " and substr(beg_date,5,2)=substr(?,5,2) and substr(beg_date,1,4)=substr(?,1,4) group by five_sts,MON_DATE, BR_NO";

		String sql8 = " select five_sts,MON_DATE, BR_NO,  count(*) as coun,sum(bal) as bal FROM RPT_XDLS  WHERE LN_TYPE IN ('1','3') AND BAL>0  "
				+ " and to_date(beg_date,'yyyy/mm/dd')>=trunc(to_date(?,'yyyy/mm/dd'),'yyyy') "
				+ "and to_date(beg_date,'yyyy/mm/dd')<=to_date(?,'yyyy/mm/dd') group by five_sts,MON_DATE, BR_NO";

		String sql9 = " select five_sts,MON_DATE, BR_NO,  count(*) as coun,sum(bal) as bal FROM RPT_XDLS  WHERE LN_TYPE IN ('1','3') AND BAL>0"
				+ "   and substr(beg_date,1,4)=substr(?,1,4)-1 and substr(beg_date,5,2)=substr(?,5,2)  group by five_sts,MON_DATE, BR_NO";
		// ==============================分行当月RPT_XD=======================================================================
		String sql10 = " select five_sts,mang_brno,MON_DATE, BR_NO,count(*) as coun,sum(bal) as bal,app_op_no FROM RPT_XD  WHERE LN_TYPE IN ('1','3') AND BAL>0 and"
				+ " to_date(beg_date,'yyyy/mm/dd')>= add_months(trunc(to_date(?,'yyyy/mm/dd'),'yyyy'),-12) "
				+ "and to_date(beg_date,'yyyy/mm/dd')<= trunc(to_date(?,'yyyy/mm/dd'),'yyyy')-1  group by five_sts,MON_DATE,mang_brno, BR_NO,app_op_no";

		String sql11 = " select five_sts,mang_brno, MON_DATE, BR_NO, count(*) as coun,sum(bal) as bal,app_op_no FROM RPT_XD  WHERE LN_TYPE IN ('1','3') AND BAL>0 "
				+ " and substr(beg_date,5,2)=substr(?,5,2) and substr(beg_date,1,4)=substr(?,1,4) group by five_sts,MON_DATE,mang_brno, BR_NO,app_op_no";

		String sql12 = " select five_sts,mang_brno,MON_DATE, BR_NO,  count(*) as coun,sum(bal) as bal,app_op_no FROM RPT_XD  WHERE LN_TYPE IN ('1','3') AND BAL>0  "
				+ " and to_date(beg_date,'yyyy/mm/dd')>=trunc(to_date(?,'yyyy/mm/dd'),'yyyy') and"
				+ " to_date(beg_date,'yyyy/mm/dd')<=to_date(?,'yyyy/mm/dd')  group by five_sts,MON_DATE,mang_brno, BR_NO,app_op_no";

		String sql13 = " select five_sts,mang_brno,MON_DATE, BR_NO,  count(*) as coun,sum(bal) as bal,app_op_no FROM RPT_XD  WHERE LN_TYPE IN ('1','3') AND BAL>0   "
				+ "and substr(beg_date,1,4)=substr(?,1,4)-1 and substr(beg_date,5,2)=substr(?,5,2)  group by five_sts,MON_DATE,mang_brno, BR_NO,app_op_no";

		// ===============================分行非当月RPT_XDLS=================================================
		String sql14 = " select five_sts,mang_brno, MON_DATE, BR_NO, count(*) as coun,sum(bal) as bal,app_op_no FROM RPT_XDLS  WHERE LN_TYPE IN ('1','3') AND BAL>0  "
				+ " and to_date(beg_date,'yyyy/mm/dd')>= add_months(trunc(to_date(?,'yyyy/mm/dd'),'yyyy'),-12)"
				+ " and to_date(beg_date,'yyyy/mm/dd')<= trunc(to_date(?,'yyyy/mm/dd'),'yyyy')-1 group by five_sts,MON_DATE,mang_brno, BR_NO,app_op_no";

		String sql15 = " select five_sts,mang_brno,MON_DATE, BR_NO,  count(*) as coun,sum(bal) as bal,app_op_no FROM RPT_XDLS  WHERE LN_TYPE IN ('1','3') AND BAL>0 "
				+ " and substr(beg_date,5,2)=substr(?,5,2) and substr(beg_date,1,4)=substr(?,1,4) group by five_sts,MON_DATE,mang_brno, BR_NO,app_op_no";

		String sql16 = " select five_sts,mang_brno,MON_DATE, BR_NO,  count(*) as coun,sum(bal) as bal,app_op_no FROM RPT_XDLS  WHERE LN_TYPE IN ('1','3') AND BAL>0  "
				+ " and to_date(beg_date,'yyyy/mm/dd')>=trunc(to_date(?,'yyyy/mm/dd'),'yyyy') "
				+ "and to_date(beg_date,'yyyy/mm/dd')<=to_date(?,'yyyy/mm/dd') group by five_sts,MON_DATE,mang_brno, BR_NO,app_op_no";

		String sql17 = " select five_sts,mang_brno,MON_DATE, BR_NO,  count(*) as coun,sum(bal) as bal,app_op_no FROM RPT_XDLS  WHERE LN_TYPE IN ('1','3') AND BAL>0"
				+ "   and substr(beg_date,1,4)=substr(?,1,4)-1 and substr(beg_date,5,2)=substr(?,5,2)  group by five_sts,MON_DATE,mang_brno, BR_NO,app_op_no";

		// =================================================================================================

		try {
			ps1 = conn.prepareStatement(sql1.toString());
			// 执行查询，ResultSet保存查询结果--系统时间
			rs1 = ps1.executeQuery();
			String cur_date = "";
			while (rs1.next()) {
				cur_date = rs1.getString(1);
			}
			// =============================总行当月====================================================================

			// 执行查询，ResultSet保存查询结果--年初余额
			ps2 = conn.prepareStatement(sql2);
			ps2.setString(1, cur_date);
			ps2.setString(2, cur_date);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				fl = new Rptwjfl_zh();
				fl.setFIVE_STS(rs2.getString("five_sts"));
				fl.setMON_DATE(rs2.getString("MON_DATE"));
				fl.setBR_NO(rs2.getString("BR_NO"));
				fl.setNCYE_COUN_DY(rs2.getString("coun"));
				fl.setNCYE_AMT_DY(rs2.getString("bal"));
				list1.add(fl);

			}

			// 执行查询，ResultSet保存查询结果--本期
			ps3 = conn.prepareStatement(sql3);
			ps3.setString(1, cur_date);
			ps3.setString(2, cur_date);
			String five_sts;
			String mon_date;
			rs3 = ps3.executeQuery();
			boolean flag = false;
			while (rs3.next()) {
				for (Rptwjfl_zh rpt : list1) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();

					if (StringUtils.equals(five_sts, rs3.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs3.getString("MON_DATE"))&&StringUtils.isNotEmpty(rs3.getString("five_sts"))) {
							rpt.setFIVE_STS(rs3.getString("five_sts"));
							rpt.setMON_DATE(rs3.getString("MON_DATE"));
							rpt.setBR_NO(rs3.getString("BR_NO"));
							rpt.setBQ_COUN_DY(rs3.getString("coun"));
							rpt.setBQ_AMT_DY(rs3.getString("bal"));
							// list1.add(fl);
							flag = true;
							break;
						}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs3.getString("five_sts"))) {
					fl = new Rptwjfl_zh();
					fl.setFIVE_STS(rs3.getString("five_sts"));
					fl.setMON_DATE(rs3.getString("MON_DATE"));
					fl.setBR_NO(rs3.getString("BR_NO"));
					fl.setBQ_COUN_DY(rs3.getString("coun"));
					fl.setBQ_AMT_DY(rs3.getString("bal"));
					list1.add(fl);
					flag = false;
				}
			}

			// 执行查询，ResultSet保存查询结果--从年初到目前为止
			ps4 = conn.prepareStatement(sql4);
			ps4.setString(1, cur_date);
			ps4.setString(2, cur_date);
			rs4 = ps4.executeQuery();
			while (rs4.next()) {
				for (Rptwjfl_zh rpt : list1) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					if (StringUtils.equals(five_sts, rs4.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs4.getString("MON_DATE"))&&StringUtils.isNotEmpty(rs4.getString("five_sts"))) {
							rpt.setFIVE_STS(rs4.getString("five_sts"));
							rpt.setMON_DATE(rs4.getString("MON_DATE"));
							rpt.setBR_NO(rs4.getString("BR_NO"));
							rpt.setNC_MQ_COUN_DY(rs4.getString("coun"));
							rpt.setNC_MQ_AMT_DY(rs4.getString("bal"));
							// list1.add(fl);
							flag = true;
							break;
						}
					}
				if (flag != true&&StringUtils.isNotEmpty(rs4.getString("five_sts"))) {
					fl = new Rptwjfl_zh();
					fl.setFIVE_STS(rs4.getString("five_sts"));
					fl.setMON_DATE(rs4.getString("MON_DATE"));
					fl.setBR_NO(rs4.getString("BR_NO"));
					fl.setNC_MQ_COUN_DY(rs4.getString("coun"));
					fl.setNC_MQ_AMT_DY(rs4.getString("bal"));
					list1.add(fl);
					flag = false;
				}
			}
			// 执行查询，ResultSet保存查询结果--去年本期
			ps5 = conn.prepareStatement(sql5);
			ps5.setString(1, cur_date);
			ps5.setString(2, cur_date);
			rs5 = ps5.executeQuery();
			while (rs5.next()) {
				for (Rptwjfl_zh rpt : list1) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					if (StringUtils.equals(five_sts, rs5.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs5.getString("MON_DATE"))&&StringUtils.isNotEmpty(rs5.getString("five_sts"))) {
							rpt.setFIVE_STS(rs5.getString("five_sts"));
							rpt.setMON_DATE(rs5.getString("MON_DATE"));
							rpt.setBR_NO(rs5.getString("BR_NO"));
							rpt.setQN_BQ_COUN_DY(rs5.getString("coun"));
							rpt.setQN_BQ_AMT_DY(rs5.getString("bal"));
							// list1.add(fl);
							flag = true;
							break;
						}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs5.getString("five_sts"))) {
					fl = new Rptwjfl_zh();
					fl.setFIVE_STS(rs5.getString("five_sts"));
					fl.setMON_DATE(rs5.getString("MON_DATE"));
					fl.setBR_NO(rs5.getString("BR_NO"));
					fl.setQN_BQ_COUN_DY(rs5.getString("coun"));
					fl.setQN_BQ_AMT_DY(rs5.getString("bal"));
					list1.add(fl);
					flag = false;
				}
			}
			// ============================总行非当月======================================
			// 执行查询，ResultSet保存查询结果--年初余额
			ps6 = conn.prepareStatement(sql6);
			ps6.setString(1, cur_date);
			ps6.setString(2, cur_date);
			rs6 = ps6.executeQuery();
			flag = false;
			while (rs6.next()) {
				for (Rptwjfl_zh rpt : list1) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					if (StringUtils.equals(five_sts, rs6.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs6.getString("MON_DATE"))&&StringUtils.isNotEmpty(rs6.getString("five_sts"))) {
							rpt.setFIVE_STS(rs6.getString("five_sts"));
							rpt.setMON_DATE(rs6.getString("MON_DATE"));
							rpt.setBR_NO(rs6.getString("BR_NO"));
							rpt.setNCYE_COUN(rs6.getString("coun"));
							rpt.setNC_MQ_AMT(rs6.getString("bal"));
							// list1.add(fl);
							flag = true;
							break;
						}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs6.getString("five_sts"))) {
					fl = new Rptwjfl_zh();
					fl.setFIVE_STS(rs6.getString("five_sts"));
					fl.setMON_DATE(rs6.getString("MON_DATE"));
					fl.setBR_NO(rs6.getString("BR_NO"));
					fl.setNCYE_COUN(rs6.getString("coun"));
					fl.setNC_MQ_AMT(rs6.getString("bal"));
					list1.add(fl);
					flag = false;
				}
			}
			// 执行查询，ResultSet保存查询结果--本期
			ps7 = conn.prepareStatement(sql7);
			ps7.setString(1, cur_date);
			ps7.setString(2, cur_date);
			rs7 = ps7.executeQuery();
			while (rs7.next()) {
				for (Rptwjfl_zh rpt : list1) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					if (StringUtils.equals(five_sts, rs7.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs7.getString("MON_DATE"))&&StringUtils.isNotEmpty(rs7.getString("five_sts"))) {
							rpt.setFIVE_STS(rs7.getString("five_sts"));
							rpt.setMON_DATE(rs7.getString("MON_DATE"));
							rpt.setBR_NO(rs7.getString("BR_NO"));
							rpt.setBQ_COUN(rs7.getString("coun"));
							rpt.setBQ_AMT(rs7.getString("bal"));
							// list1.add(fl);
							flag = true;
							break;
					}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs7.getString("five_sts"))) {
					fl = new Rptwjfl_zh();
					fl.setFIVE_STS(rs7.getString("five_sts"));
					fl.setMON_DATE(rs7.getString("MON_DATE"));
					fl.setBR_NO(rs7.getString("BR_NO"));
					fl.setBQ_COUN(rs7.getString("coun"));
					fl.setBQ_AMT(rs7.getString("bal"));
					list1.add(fl);
					flag = false;
				}
			}

			// 执行查询，ResultSet保存查询结果--从年初到目前为止
			ps8 = conn.prepareStatement(sql8);
			ps8.setString(1, cur_date);
			ps8.setString(2, cur_date);
			rs8 = ps8.executeQuery();
			while (rs8.next()) {
				for (Rptwjfl_zh rpt : list1) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					if (StringUtils.equals(five_sts, rs8.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs8.getString("MON_DATE"))&&StringUtils.isNotEmpty(rs8.getString("five_sts"))) {
							rpt.setFIVE_STS(rs8.getString("five_sts"));
							rpt.setMON_DATE(rs8.getString("MON_DATE"));
							rpt.setBR_NO(rs8.getString("BR_NO"));
							rpt.setNC_MQ_COUN(rs8.getString("coun"));
							rpt.setNC_MQ_AMT(rs8.getString("bal"));
							// list1.add(fl);
							flag = true;
							break;
					}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs8.getString("five_sts"))) {
					fl = new Rptwjfl_zh();
					fl.setFIVE_STS(rs8.getString("five_sts"));
					fl.setMON_DATE(rs8.getString("MON_DATE"));
					fl.setBR_NO(rs8.getString("BR_NO"));
					fl.setNC_MQ_COUN(rs8.getString("coun"));
					fl.setNC_MQ_AMT(rs8.getString("bal"));
					list1.add(fl);
					flag = false;
				}
			}

			// 执行查询，ResultSet保存查询结果--去年本期
			ps9 = conn.prepareStatement(sql9);
			ps9.setString(1, cur_date);
			ps9.setString(2, cur_date);
			rs9 = ps9.executeQuery();
			while (rs9.next()) {
				for (Rptwjfl_zh rpt : list1) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					if (StringUtils.equals(five_sts, rs9.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs9.getString("MON_DATE"))&&StringUtils.isNotEmpty(rs9.getString("five_sts"))) {
							rpt.setFIVE_STS(rs9.getString("five_sts"));
							rpt.setMON_DATE(rs9.getString("MON_DATE"));
							rpt.setBR_NO(rs9.getString("BR_NO"));
							rpt.setQN_BQ_COUN(rs9.getString("coun"));
							rpt.setQN_BQ_AMT(rs9.getString("bal"));
							// list1.add(fl);
							flag = true;
							break;
					}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs9.getString("five_sts"))) {
					fl = new Rptwjfl_zh();
					fl.setFIVE_STS(rs9.getString("five_sts"));
					fl.setMON_DATE(rs9.getString("MON_DATE"));
					fl.setBR_NO(rs9.getString("BR_NO"));
					fl.setQN_BQ_COUN(rs9.getString("coun"));
					fl.setQN_BQ_AMT(rs9.getString("bal"));
					list1.add(fl);
					flag = false;
				}
			}
			// ==============================================================================
			if (list1 != null) {
				for (int i = 0; i < list1.size(); i++) {
					Rptwjfl_zh zh = list1.get(i);
					PreparedStatement ps = null;
					StringBuffer sb1 = new StringBuffer();
					int count = 0;
					sb1.append("insert into zhwjflhz  (five_sts, mon_date, br_no, ncye_coun_dy, ncye_amt_dy,"
							+ "bq_coun_dy, bq_amt_dy, nc_mq_coun_dy, nc_mq_amt_dy, qn_bq_coun_dy, qn_bq_amt_dy,"
							+ " ncye_coun, ncye_amt, bq_coun, bq_amt, nc_mq_coun, nc_mq_amt, qn_bq_coun, qn_bq_amt)");
					sb1.append("values  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

					try {
						ps = conn.prepareStatement(sb1.toString());
						ps.setString(1, zh.getFIVE_STS());
						ps.setString(2, zh.getMON_DATE());
						ps.setString(3, zh.getBR_NO());
						ps.setString(4, zh.getNCYE_COUN_DY());
						ps.setString(5, zh.getNC_MQ_AMT_DY());
						ps.setString(6, zh.getBQ_COUN_DY());
						ps.setString(7, zh.getBQ_AMT_DY());
						ps.setString(8, zh.getNC_MQ_COUN_DY());
						ps.setString(9, zh.getNC_MQ_AMT_DY());
						ps.setString(10, zh.getQN_BQ_COUN_DY());
						ps.setString(11, zh.getQN_BQ_AMT_DY());
						ps.setString(12, zh.getNCYE_COUN());
						ps.setString(13, zh.getNCYE_AMT());
						ps.setString(14, zh.getBQ_COUN());
						ps.setString(15, zh.getBQ_AMT());
						ps.setString(16, zh.getNC_MQ_COUN());
						ps.setString(17, zh.getNC_MQ_AMT());
						ps.setString(18, zh.getQN_BQ_COUN());
						ps.setString(19, zh.getQN_BQ_AMT());
						count = ps.executeUpdate();
						conn.commit();
						BatchPublic.inBatchlog("T", "T01", "1", conn);
					} catch (Exception e) {
						count = -1;
						BatchPublic.inBatchlog("T", "T01", "2", conn);
					} finally {
						DataSourceUtils.closeStatement(ps);
					}
				}

			// ===============================分行当月==========================================
			// 执行查询，ResultSet保存查询结果--年初余额
			ps10 = conn.prepareStatement(sql10);
			ps10.setString(1, cur_date);
			ps10.setString(2, cur_date);
			rs10 = ps10.executeQuery();
			while (rs10.next()) {
				ffl = new Rptwjfl_fh();
				ffl.setFIVE_STS(rs10.getString("five_sts"));
				ffl.setMON_DATE(rs10.getString("MON_DATE"));
				ffl.setMANG_BRNO(rs10.getString("mang_brno"));
				ffl.setBR_NO(rs10.getString("BR_NO"));
				ffl.setNCYE_COUN_DY(rs10.getString("coun"));
				ffl.setNCYE_AMT_DY(rs10.getString("bal"));
				ffl.setAPP_OP_NO(rs10.getString("app_op_no"));
				list2.add(ffl);

			}

			// 执行查询，ResultSet保存查询结果--本期
			ps11 = conn.prepareStatement(sql11);
			ps11.setString(1, cur_date);
			ps11.setString(2, cur_date);
			rs11 = ps11.executeQuery();
			String five_sts1;
			String mon_date1;
			String mang_brno;
			boolean flag1 = false;
			while (rs11.next()) {
				for (Rptwjfl_fh rpt : list2) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					mang_brno = rpt.getMANG_BRNO();
					if (StringUtils.equals(five_sts, rs11.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs11.getString("MON_DATE"))&& StringUtils.equals(mang_brno,	rs11.getString("mang_brno"))&&StringUtils.isNotEmpty(rs11.getString("five_sts"))) {
							rpt.setFIVE_STS(rs11.getString("five_sts"));
							rpt.setMON_DATE(rs11.getString("MON_DATE"));
							rpt.setMANG_BRNO(rs11.getString("mang_brno"));
							rpt.setBR_NO(rs11.getString("BR_NO"));
							rpt.setBQ_COUN_DY(rs11.getString("coun"));
							rpt.setBQ_AMT_DY(rs11.getString("bal"));
							// list1.add(fl);
							flag1 = true;
							break;
					}
				}
				if (flag1 != true&&StringUtils.isNotEmpty(rs11.getString("five_sts"))) {
					ffl = new Rptwjfl_fh();
					ffl.setFIVE_STS(rs11.getString("five_sts"));
					ffl.setMON_DATE(rs11.getString("MON_DATE"));
					ffl.setMANG_BRNO(rs11.getString("mang_brno"));
					ffl.setBR_NO(rs11.getString("BR_NO"));
					ffl.setBQ_COUN_DY(rs11.getString("coun"));
					ffl.setBQ_AMT_DY(rs11.getString("bal"));
					ffl.setAPP_OP_NO(rs11.getString("app_op_no"));
					list2.add(ffl);
					flag1 = false;
				}
			}

			// 执行查询，ResultSet保存查询结果--从年初到目前为止
			ps12 = conn.prepareStatement(sql12);
			ps12.setString(1, cur_date);
			ps12.setString(2, cur_date);
			rs12 = ps12.executeQuery();
			while (rs12.next()) {
				for (Rptwjfl_fh rpt : list2) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					mang_brno = rpt.getMANG_BRNO();
					if (StringUtils.equals(five_sts, rs12.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs12.getString("MON_DATE"))&& StringUtils.equals(mang_brno,	rs12.getString("mang_brno"))&&StringUtils.isNotEmpty(rs12.getString("five_sts"))) {
						
							rpt.setFIVE_STS(rs12.getString("five_sts"));
							rpt.setMON_DATE(rs12.getString("MON_DATE"));
							rpt.setMANG_BRNO(rs12.getString("mang_brno"));
							rpt.setBR_NO(rs12.getString("BR_NO"));
							rpt.setNC_MQ_COUN_DY(rs12.getString("coun"));
							rpt.setNC_MQ_AMT_DY(rs12.getString("bal"));
							// list1.add(fl);
							flag1 = true;
							break;
					}
				}
				if (flag1 != true&&StringUtils.isNotEmpty(rs12.getString("five_sts"))) {
					ffl = new Rptwjfl_fh();
					ffl.setFIVE_STS(rs12.getString("five_sts"));
					ffl.setMON_DATE(rs12.getString("MON_DATE"));
					ffl.setMANG_BRNO(rs12.getString("mang_brno"));
					ffl.setBR_NO(rs12.getString("BR_NO"));
					ffl.setNC_MQ_COUN_DY(rs12.getString("coun"));
					ffl.setNC_MQ_AMT_DY(rs12.getString("bal"));
					ffl.setAPP_OP_NO(rs12.getString("app_op_no"));
					list2.add(ffl);
					flag1 = false;
				}
			}
			// 执行查询，ResultSet保存查询结果--去年本期
			ps13 = conn.prepareStatement(sql13);
			ps13.setString(1, cur_date);
			ps13.setString(2, cur_date);
			rs13 = ps13.executeQuery();
			while (rs13.next()) {
				for (Rptwjfl_fh rpt : list2) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					mang_brno = rpt.getMANG_BRNO();
					if (StringUtils.equals(five_sts, rs13.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs13.getString("MON_DATE"))&& StringUtils.equals(mang_brno,	rs13.getString("mang_brno"))&&StringUtils.isNotEmpty(rs13.getString("five_sts"))) {
						
							rpt.setFIVE_STS(rs13.getString("five_sts"));
							rpt.setMON_DATE(rs13.getString("MON_DATE"));
							rpt.setMANG_BRNO(rs13.getString("mang_brno"));
							rpt.setBR_NO(rs13.getString("BR_NO"));
							rpt.setQN_BQ_COUN_DY(rs13.getString("coun"));
							rpt.setQN_BQ_AMT_DY(rs13.getString("bal"));
							// list1.add(fl);
							flag1 = true;
							break;
						}
				}
				if (flag1 != true&&StringUtils.isNotEmpty(rs13.getString("five_sts"))) {
					ffl = new Rptwjfl_fh();
					ffl.setFIVE_STS(rs13.getString("five_sts"));
					ffl.setMON_DATE(rs13.getString("MON_DATE"));
					ffl.setMANG_BRNO(rs13.getString("mang_brno"));
					ffl.setBR_NO(rs13.getString("BR_NO"));
					ffl.setQN_BQ_COUN_DY(rs13.getString("coun"));
					ffl.setQN_BQ_AMT_DY(rs13.getString("bal"));
					ffl.setAPP_OP_NO(rs13.getString("app_op_no"));
					list2.add(ffl);
					flag1 = false;
				}
			}
			// ===============================分行非当月====================================================
			// 执行查询，ResultSet保存查询结果--年初余额
			ps14 = conn.prepareStatement(sql14);
			ps14.setString(1, cur_date);
			ps14.setString(2, cur_date);
			rs14 = ps14.executeQuery();
			flag1 = false;
			while (rs14.next()) {
				for (Rptwjfl_fh rpt : list2) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					mang_brno = rpt.getMANG_BRNO();
					if (StringUtils.equals(five_sts, rs14.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs14.getString("MON_DATE"))&& StringUtils.equals(mang_brno,	rs14.getString("mang_brno"))&&StringUtils.isNotEmpty(rs14.getString("five_sts"))) {
						
							rpt.setFIVE_STS(rs14.getString("five_sts"));
							rpt.setMON_DATE(rs14.getString("MON_DATE"));
							rpt.setMANG_BRNO(rs14.getString("mang_brno"));
							rpt.setBR_NO(rs14.getString("BR_NO"));
							rpt.setNCYE_COUN(rs14.getString("coun"));
							rpt.setNC_MQ_AMT(rs14.getString("bal"));
							// list1.add(fl);
							flag1 = true;
							break;
					}
				}
				if (flag1 != true&&StringUtils.isNotEmpty(rs14.getString("five_sts"))) {
					ffl = new Rptwjfl_fh();
					ffl.setFIVE_STS(rs14.getString("five_sts"));
					ffl.setMON_DATE(rs14.getString("MON_DATE"));
					ffl.setMANG_BRNO(rs14.getString("mang_brno"));
					ffl.setBR_NO(rs14.getString("BR_NO"));
					ffl.setNCYE_COUN(rs14.getString("coun"));
					ffl.setNC_MQ_AMT(rs14.getString("bal"));
					ffl.setAPP_OP_NO(rs14.getString("app_op_no"));
					list2.add(ffl);
					flag1 = false;
				}
			}
			// 执行查询，ResultSet保存查询结果--本期
			ps15 = conn.prepareStatement(sql15);
			ps15.setString(1, cur_date);
			ps15.setString(2, cur_date);
			rs15 = ps15.executeQuery();
			while (rs15.next()) {
				for (Rptwjfl_fh rpt : list2) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					mang_brno = rpt.getMANG_BRNO();
					if (StringUtils.equals(five_sts, rs15.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs15.getString("MON_DATE"))&& StringUtils.equals(mang_brno,	rs15.getString("mang_brno"))&&StringUtils.isNotEmpty(rs15.getString("five_sts"))) {
						
							rpt.setFIVE_STS(rs15.getString("five_sts"));
							rpt.setMON_DATE(rs15.getString("MON_DATE"));
							rpt.setMANG_BRNO(rs15.getString("mang_brno"));
							rpt.setBR_NO(rs15.getString("BR_NO"));
							rpt.setBQ_COUN(rs15.getString("coun"));
							rpt.setBQ_AMT(rs15.getString("bal"));
							// list1.add(fl);
							flag1 = true;
							break;
					}
				}
				if (flag1 != true&&StringUtils.isNotEmpty(rs15.getString("five_sts"))) {
					ffl = new Rptwjfl_fh();
					ffl.setFIVE_STS(rs15.getString("five_sts"));
					ffl.setMON_DATE(rs15.getString("MON_DATE"));
					ffl.setMANG_BRNO(rs15.getString("mang_brno"));
					ffl.setBR_NO(rs15.getString("BR_NO"));
					ffl.setBQ_COUN(rs15.getString("coun"));
					ffl.setBQ_AMT(rs15.getString("bal"));
					ffl.setAPP_OP_NO(rs15.getString("app_op_no"));
					list2.add(ffl);
					flag1 = false;
				}
			}

			// 执行查询，ResultSet保存查询结果--从年初到目前为止
			ps16 = conn.prepareStatement(sql16);
			ps16.setString(1, cur_date);
			ps16.setString(2, cur_date);
			rs16 = ps16.executeQuery();
			while (rs16.next()) {
				for (Rptwjfl_fh rpt : list2) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					mang_brno = rpt.getMANG_BRNO();
					if (StringUtils.equals(five_sts, rs16.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs16.getString("MON_DATE"))&& StringUtils.equals(mang_brno,	rs16.getString("mang_brno"))&&StringUtils.isNotEmpty(rs16.getString("five_sts"))) {
						
							rpt.setFIVE_STS(rs16.getString("five_sts"));
							rpt.setMON_DATE(rs16.getString("MON_DATE"));
							rpt.setMANG_BRNO(rs16.getString("mang_brno"));
							rpt.setBR_NO(rs16.getString("BR_NO"));
							rpt.setNC_MQ_COUN(rs16.getString("coun"));
							rpt.setNC_MQ_AMT(rs16.getString("bal"));
							// list1.add(fl);
							flag1 = true;
							break;
						}
				}
				if (flag1 != true&&StringUtils.isNotEmpty(rs16.getString("five_sts"))) {
					ffl = new Rptwjfl_fh();
					ffl.setFIVE_STS(rs16.getString("five_sts"));
					ffl.setMON_DATE(rs16.getString("MON_DATE"));
					ffl.setMANG_BRNO(rs16.getString("mang_brno"));
					ffl.setBR_NO(rs16.getString("BR_NO"));
					ffl.setNC_MQ_COUN(rs16.getString("coun"));
					ffl.setNC_MQ_AMT(rs16.getString("bal"));
					ffl.setAPP_OP_NO(rs16.getString("app_op_no"));
					list2.add(ffl);
					flag1 = false;
				}
			}

			// 执行查询，ResultSet保存查询结果--去年本期
			ps17 = conn.prepareStatement(sql17);
			ps17.setString(1, cur_date);
			ps17.setString(2, cur_date);
			rs17 = ps17.executeQuery();
			while (rs17.next()) {
				for (Rptwjfl_fh rpt : list2) {
					five_sts = rpt.getFIVE_STS();
					mon_date = rpt.getMON_DATE();
					mang_brno = rpt.getMANG_BRNO();
					if (StringUtils.equals(five_sts, rs17.getString("five_sts"))
							&& StringUtils.equals(mon_date,	rs17.getString("MON_DATE"))&& StringUtils.equals(mang_brno,	rs17.getString("mang_brno"))&&StringUtils.isNotEmpty(rs17.getString("five_sts"))) {
						
							rpt.setFIVE_STS(rs17.getString("five_sts"));
							rpt.setMON_DATE(rs17.getString("MON_DATE"));
							rpt.setMANG_BRNO(rs17.getString("mang_brno"));
							rpt.setBR_NO(rs17.getString("BR_NO"));
							rpt.setQN_BQ_COUN(rs17.getString("coun"));
							rpt.setQN_BQ_AMT(rs17.getString("bal"));
							// list1.add(fl);
							flag1 = true;
							break;
					}
				}
				if (flag1 != true&&StringUtils.isNotEmpty(rs17.getString("five_sts"))) {
					ffl = new Rptwjfl_fh();
					ffl.setFIVE_STS(rs17.getString("five_sts"));
					ffl.setMON_DATE(rs17.getString("MON_DATE"));
					ffl.setMANG_BRNO(rs17.getString("mang_brno"));
					ffl.setBR_NO(rs17.getString("BR_NO"));
					ffl.setQN_BQ_COUN(rs17.getString("coun"));
					ffl.setQN_BQ_AMT(rs17.getString("bal"));
					ffl.setAPP_OP_NO(rs17.getString("app_op_no"));
					list2.add(ffl);
					flag1 = false;
				}
			}
			// ==================================================================================================================
			if (list2 != null) {
				for (int i = 0; i < list2.size(); i++) {
					Rptwjfl_fh fh = list2.get(i);
					PreparedStatement ps = null;
					StringBuffer sb1 = new StringBuffer();
					int count = 0;
					sb1.append("insert into fhwjflhz  (five_sts, mon_date, mang_brno, ncye_coun_dy, ncye_amt_dy"
							+ ", bq_coun_dy, bq_amt_dy, nc_mq_coun_dy, nc_mq_amt_dy, qn_bq_coun_dy, qn_bq_amt_dy, "
							+ "ncye_coun, ncye_amt, bq_coun, bq_amt, nc_mq_coun, nc_mq_amt, qn_bq_coun, qn_bq_amt, app_op_no)");
					sb1.append("values  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

					try {
						ps = conn.prepareStatement(sb1.toString());
						ps.setString(1, fh.getFIVE_STS());
						ps.setString(2, fh.getMON_DATE());
						ps.setString(3, fh.getMANG_BRNO());
						ps.setString(4, fh.getNCYE_COUN_DY());
						ps.setString(5, fh.getNC_MQ_AMT_DY());
						ps.setString(6, fh.getBQ_COUN_DY());
						ps.setString(7, fh.getBQ_AMT_DY());
						ps.setString(8, fh.getNC_MQ_COUN_DY());
						ps.setString(9, fh.getNC_MQ_AMT_DY());
						ps.setString(10, fh.getQN_BQ_COUN_DY());
						ps.setString(11, fh.getQN_BQ_AMT_DY());
						ps.setString(12, fh.getNCYE_COUN());
						ps.setString(13, fh.getNCYE_AMT());
						ps.setString(14, fh.getBQ_COUN());
						ps.setString(15, fh.getBQ_AMT());
						ps.setString(16, fh.getNC_MQ_COUN());
						ps.setString(17, fh.getNC_MQ_AMT());
						ps.setString(18, fh.getQN_BQ_COUN());
						ps.setString(19, fh.getQN_BQ_AMT());
						ps.setString(20, fh.getAPP_OP_NO());
						count = ps.executeUpdate();
						conn.commit();
						BatchPublic.inBatchlog("T", "T01", "1", conn);
					} catch (Exception e) {
						count = -1;
						BatchPublic.inBatchlog("T", "T01", "2", conn);
					} finally {
						DataSourceUtils.closeStatement(ps);
					}
				}

			}
			rs1.close();
			rs2.close();
			rs3.close();
			rs4.close();
			rs5.close();

			rs6.close();
			rs7.close();
			rs8.close();
			rs9.close();

			rs10.close();
			rs11.close();
			rs12.close();
			rs13.close();

			rs14.close();
			rs15.close();
			rs16.close();
			rs17.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceUtils.closeStatement(ps1);
			DataSourceUtils.closeStatement(ps2);
			DataSourceUtils.closeStatement(ps3);
			DataSourceUtils.closeStatement(ps4);
			DataSourceUtils.closeStatement(ps5);
			DataSourceUtils.closeStatement(ps6);
			DataSourceUtils.closeStatement(ps7);
			DataSourceUtils.closeStatement(ps8);
			DataSourceUtils.closeStatement(ps9);
			DataSourceUtils.closeStatement(ps10);
			DataSourceUtils.closeStatement(ps11);
			DataSourceUtils.closeStatement(ps12);
			DataSourceUtils.closeStatement(ps13);
			DataSourceUtils.closeStatement(ps14);
			DataSourceUtils.closeStatement(ps15);
			DataSourceUtils.closeStatement(ps16);
			DataSourceUtils.closeStatement(ps17);

		}

	}
}
