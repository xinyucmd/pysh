package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ibatis.sqlmap.engine.mapping.result.ResultMap;

import app.batch.BatchPublic;
import app.batch.entity.Rptzjtxmxb;
import app.util.exc.DataSourceUtils;

/**
 * 资金投向明细表
 * 
 * @author Administrator
 * 
 */
public class RptzjtxDAO {
	private Connection conn;
	private String date;
	Rptzjtxmxb tx; // 资金投向明细表实体类
	private List<Rptzjtxmxb> list1 = new ArrayList<Rptzjtxmxb>();
	private List<Rptzjtxmxb> list2 = new ArrayList<Rptzjtxmxb>();

	public RptzjtxDAO(Connection conn) {
		this.conn = conn;
//		Leiji();
	}

	// 企业累计
	public void Leiji() {
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null; // 企业本月
		PreparedStatement ps3 = null; // 企业本季
		PreparedStatement ps4 = null; // 企业本年

		PreparedStatement ps5 = null; // 个人本月
		PreparedStatement ps6 = null; // 个人本季
		PreparedStatement ps7 = null; // 个人本年

		PreparedStatement ps8 = null; // 企业各项累计
		PreparedStatement ps9 = null; // 个人各项累计

		ResultSet rs1 = null; // 时间
		ResultSet rs2 = null; // 企业本月
		ResultSet rs3 = null; // 企业本季
		ResultSet rs4 = null; // 企业本年

		ResultSet rs5 = null; // 个人本月
		ResultSet rs6 = null; // 个人本季
		ResultSet rs7 = null; // 个人本年

		ResultSet rs8 = null; // 企业各项累计
		ResultSet rs9 = null; // 个人各项累计
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT LST_DATE FROM SYS_GLOBAL");
		String sql1 = "select prdt_no,mang_brno ,sum(due_amt) a1,sum(bal) b1,count(distinct cif_no) c1,"
				+ "count( cif_no) d1,app_op_no from rpt_xd where cif_type='1' and  "
				+ "to_date(beg_date,'yyyy/mm/dd')> trunc(to_date(?,'yyyy/mm/dd'),'mm') and "
				+ "to_date(beg_date,'yyyy/mm/dd')< last_day(trunc(to_date(?,'yyyy/mm/dd')))"
				+ " group by prdt_no,mang_brno, app_op_no";// 企业本月新增
		String sql2 = "select prdt_no,mang_brno,sum(due_amt)  a2,sum(bal) b2,count(distinct cif_no) c2,"
				+ "count( cif_no) d2,app_op_no from rpt_xd where cif_type='1' and "
				+ "to_date(beg_date,'yyyy/mm/dd')> trunc(to_date(?,'yyyy/mm/dd'),'Q') and "
				+ "to_date(beg_date,'yyyy/mm/dd')< add_months(trunc(to_date(?,'yyyy/mm/dd'),'Q'),3)-1"
				+ " group by prdt_no,mang_brno, app_op_no"; // 企业本季新增
		String sql3 = "select prdt_no,mang_brno ,sum(due_amt) a3,sum(bal) b3,count(distinct cif_no) c3"
				+ ",count( cif_no) d3,app_op_no from rpt_xd where cif_type='1' and "
				+ "to_date(beg_date,'yyyy/mm/dd')> trunc(to_date(?,'yyyy/mm/dd'),'yyyy') "
				+ "and to_date(beg_date,'yyyy/mm/dd')< add_months(trunc(to_date(?,'yyyy/mm/dd'),'yyyy'),12)-1"
				+ "  group by prdt_no,mang_brno, app_op_no";// 本年企业新增
		// ===================================================================================================
		String sql4 = "select prdt_no,mang_brno ,sum(due_amt) a4,sum(bal) b4,count(distinct cif_no) c4,"
				+ "count( cif_no) d4,app_op_no from rpt_xd where cif_type='2' and  "
				+ "to_date(beg_date,'yyyy/mm/dd')> trunc(to_date(?,'yyyy/mm/dd'),'mm') and "
				+ "to_date(beg_date,'yyyy/mm/dd')< last_day(trunc(to_date(?,'yyyy/mm/dd')))"
				+ " group by prdt_no,mang_brno, app_op_no";// 个人本月新增
		String sql5 = "select prdt_no,mang_brno,sum(due_amt)  a5,sum(bal) b5,count(distinct cif_no) c5,"
				+ "count( cif_no) d5,app_op_no from rpt_xd where cif_type='2' and "
				+ "to_date(beg_date,'yyyy/mm/dd')> trunc(to_date(?,'yyyy/mm/dd'),'Q') and "
				+ "to_date(beg_date,'yyyy/mm/dd')< add_months(trunc(to_date(?,'yyyy/mm/dd'),'Q'),3)-1"
				+ " group by prdt_no,mang_brno, app_op_no"; // 个人本季新增
		String sql6 = "select prdt_no,mang_brno ,sum(due_amt) a6,sum(bal) b6,count(distinct cif_no) c6"
				+ ",count( cif_no) d6,app_op_no from rpt_xd where cif_type='2' and "
				+ "to_date(beg_date,'yyyy/mm/dd')> trunc(to_date(?,'yyyy/mm/dd'),'yyyy') "
				+ "and to_date(beg_date,'yyyy/mm/dd')< add_months(trunc(to_date(?,'yyyy/mm/dd'),'yyyy'),12)-1"
				+ "  group by prdt_no,mang_brno, app_op_no";// 本年个人新增
		// ====================================================================================================
		String sql7 = "select prdt_no,mang_brno,sum(bal) a7 ,sum(due_amt) b7,count(distinct cif_no) c7,count( cif_no) d7,app_op_no "
				+ " from rpt_xd where to_date(beg_date,'yyyy/mm/dd')<=to_date(?,'yyyy/mm/dd')"
				+ " and cif_type='1' group by prdt_no ,mang_brno, app_op_no";// 企业各项累计
		String sql8 = "select prdt_no,mang_brno,sum(bal) a8 ,sum(due_amt) b8,count(distinct cif_no) c8,count( cif_no) d8,app_op_no"
				+ " from rpt_xd where to_date(beg_date,'yyyy/mm/dd')<=to_date(?,'yyyy/mm/dd') "
				+ "and cif_type='2'group by prdt_no ,mang_brno, app_op_no";// 个人各项累计

		try {
			ps1 = conn.prepareStatement(sb.toString());
			ps2 = conn.prepareStatement(sql1);

			// 执行查询，ResultSet保存查询结果--系统时间
			rs1 = ps1.executeQuery();
			String cur_date = "";
			while (rs1.next()) {
				cur_date = rs1.getString(1);

			}

			// 执行查询，ResultSet保存查询结果--企业月新增
			ps2.setString(1, cur_date);
			ps2.setString(2, cur_date);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				tx = new Rptzjtxmxb();
				tx.setPrdt_no(rs2.getString("prdt_no"));
				tx.setCif_type("1");
				tx.setMon_addusers(rs2.getString("c1"));
				tx.setMon_addcounts(rs2.getString("d1"));
				tx.setMon_addamt(rs2.getString("a1"));
				tx.setClassdate(cur_date);
				tx.setBr_no(rs2.getString("mang_brno"));
				tx.setApp_op_no(rs2.getString("app_op_no"));
				list1.add(tx);

			}

			// 执行查询，ResultSet保存查询结果--企业季新增
			ps3 = conn.prepareStatement(sql2);
			ps3.setString(1, cur_date);
			ps3.setString(2, cur_date);
			String prdt_no;
			rs3 = ps3.executeQuery();
			boolean flag = false;
			while (rs3.next()) {
				for (Rptzjtxmxb rpt : list1) {
					prdt_no = rpt.getPrdt_no();
					String br_no = rpt.getBr_no();
					if (StringUtils.equals(prdt_no, rs3.getString("prdt_no")) 	&& StringUtils.equals(br_no,rs3.getString("mang_brno"))&&StringUtils.isNotEmpty(prdt_no)&&StringUtils.isNotEmpty(rs3.getString("prdt_no")) ) {
							rpt.setPrdt_no(rs3.getString("prdt_no"));
							rpt.setCif_type("1");
							rpt.setSea_addusers(rs3.getString("c2"));
							rpt.setSea_addcounts(rs3.getString("d2"));
							rpt.setSea_addamt(rs3.getString("a2"));
							rpt.setClassdate(cur_date);
							rpt.setBr_no(rs3.getString("mang_brno"));
							rpt.setApp_op_no(rs3.getString("app_op_no"));
							// list1.add(tx);
							flag = true;
							break;
					}

				}
				if (flag != true&&StringUtils.isNotEmpty(rs3.getString("prdt_no"))) {
					tx = new Rptzjtxmxb();
					tx.setPrdt_no(rs3.getString("prdt_no"));
					tx.setCif_type("1");
					tx.setSea_addusers(rs3.getString("c2"));
					tx.setSea_addcounts(rs3.getString("d2"));
					tx.setSea_addamt(rs3.getString("a2"));
					tx.setClassdate(cur_date);
					tx.setBr_no(rs3.getString("mang_brno"));
					tx.setApp_op_no(rs3.getString("app_op_no"));
					list1.add(tx);
					flag = false;
				}
			}

			// 执行查询，ResultSet保存查询结果--企业年新增
			ps4 = conn.prepareStatement(sql3);
			ps4.setString(1, cur_date);
			ps4.setString(2, cur_date);
			rs4 = ps4.executeQuery();
			while (rs4.next()) {
				for (Rptzjtxmxb rpt : list1) {
					prdt_no = rpt.getPrdt_no();
					String br_no = rpt.getBr_no();
					if (StringUtils.equals(prdt_no, rs4.getString("prdt_no")) 	&& StringUtils.equals(br_no,rs4.getString("mang_brno"))&&StringUtils.isNotEmpty(prdt_no)&&StringUtils.isNotEmpty(rs4.getString("prdt_no")) ) {

							rpt.setPrdt_no(rs4.getString("prdt_no"));
							rpt.setCif_type("1");
							rpt.setYear_addusers(rs4.getString("c3"));
							rpt.setYear_addcounts(rs4.getString("d3"));
							rpt.setYear_addamt(rs4.getString("a3"));
							rpt.setClassdate(cur_date);
							rpt.setBr_no(rs4.getString("mang_brno"));
							rpt.setApp_op_no(rs4.getString("app_op_no"));
							// list1.add(tx);
							flag = true;
							break;
					}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs4.getString("prdt_no"))) {
					tx = new Rptzjtxmxb();
					tx.setPrdt_no(rs4.getString("prdt_no"));
					tx.setCif_type("1");
					tx.setYear_addusers(rs4.getString("c3"));
					tx.setYear_addcounts(rs4.getString("d3"));
					tx.setYear_addamt(rs4.getString("a3"));
					tx.setClassdate(cur_date);
					tx.setBr_no(rs4.getString("mang_brno"));
					tx.setApp_op_no(rs4.getString("app_op_no"));
					list1.add(tx);
					flag = false;
				}
			}
			// 执行查询，ResultSet保存查询结果--企业各项累计
			ps8 = conn.prepareStatement(sql7);
			ps8.setString(1, cur_date);
			rs8 = ps8.executeQuery();
			while (rs8.next()) {
				for (Rptzjtxmxb rpt : list1) {
					prdt_no = rpt.getPrdt_no();
					String br_no = rpt.getBr_no();
					if (StringUtils.equals(prdt_no, rs8.getString("prdt_no")) 	&& StringUtils.equals(br_no,rs8.getString("mang_brno"))&&StringUtils.isNotEmpty(prdt_no)&&StringUtils.isNotEmpty(rs8.getString("prdt_no")) ) {

							rpt.setPrdt_no(rs8.getString("prdt_no"));
							rpt.setCif_type("1");
							rpt.setBal(rs8.getString("a7"));
							rpt.setTotal_amt(rs8.getString("b7"));
							rpt.setTotal_counts(rs8.getString("d7"));
							rpt.setTotal_users(rs8.getString("c7"));
							rpt.setClassdate(cur_date);
							rpt.setBr_no(rs8.getString("mang_brno"));
							rpt.setApp_op_no(rs8.getString("app_op_no"));
							// list1.add(tx);
							flag = true;
							break;
					}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs8.getString("prdt_no"))) {
					tx = new Rptzjtxmxb();
					tx.setPrdt_no(rs8.getString("prdt_no"));
					tx.setCif_type("1");
					tx.setTotal_amt(rs8.getString("b7"));
					tx.setTotal_counts(rs8.getString("d7"));
					tx.setTotal_users(rs8.getString("c7"));
					tx.setBal(rs8.getString("a7"));
					tx.setClassdate(cur_date);
					tx.setBr_no(rs8.getString("mang_brno"));
					tx.setApp_op_no(rs8.getString("app_op_no"));
					list1.add(tx);
					flag = false;
				}
			}
			if (list1 != null) {
				for (int i = 0; i < list1.size(); i++) {
					Rptzjtxmxb zjtx = list1.get(i);
					PreparedStatement ps = null;
					StringBuffer sb1 = new StringBuffer();
					int count = 0;
					sb1.append("insert into loan_amt_trade   (prdt_no,   cif_type,   total_users,   mon_addusers,"
							+ "   sea_addusers,   year_addusers,   total_counts,   mon_addcounts,   sea_addcounts,"
							+ "   year_addcounts,   total_amt,   mon_addamt,   sea_addamt,   year_addamt,   bal,"
							+ "   percent,   br_no,   classdate, app_op_no) ");
					sb1.append("values  (?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,  ?, ?)");

					try {
						ps = conn.prepareStatement(sb1.toString());
						ps.setString(1, zjtx.getPrdt_no());
						ps.setString(2, zjtx.getCif_type());
						ps.setString(3, zjtx.getTotal_users());
						ps.setString(4, zjtx.getMon_addusers());
						ps.setString(5, zjtx.getSea_addusers());
						ps.setString(6, zjtx.getYear_addusers());
						ps.setString(7, zjtx.getTotal_counts());
						ps.setString(8, zjtx.getMon_addcounts());
						ps.setString(9, zjtx.getSea_addcounts());
						ps.setString(10, zjtx.getYear_addcounts());
						ps.setString(11, zjtx.getTotal_amt());
						ps.setString(12, zjtx.getMon_addamt());
						ps.setString(13, zjtx.getSea_addamt());
						ps.setString(14, zjtx.getYear_addamt());
						ps.setString(15, zjtx.getBal());
						ps.setString(16, zjtx.getPercent());			
						ps.setString(17, zjtx.getBr_no());
						ps.setString(18, zjtx.getClassdate());
						ps.setString(19, zjtx.getApp_op_no());
						count = ps.executeUpdate();
						conn.commit();
						BatchPublic.inBatchlog("R", "R01", "1", conn);
					} catch (Exception e) {
						count = -1;
						BatchPublic.inBatchlog("R", "R01", "2", conn);
					} finally {
						DataSourceUtils.closeStatement(ps);
					}
				}

			}
			// ==================================================================
			ps5 = conn.prepareStatement(sql4);
			// 执行查询，ResultSet保存查询结果--个人月新增
			ps5.setString(1, cur_date);
			ps5.setString(2, cur_date);
			rs5 = ps5.executeQuery();
			while (rs5.next()) {
				tx = new Rptzjtxmxb();
				tx.setPrdt_no(rs5.getString("prdt_no"));
				tx.setCif_type("2");
				tx.setMon_addusers(rs5.getString("c4"));
				tx.setMon_addcounts(rs5.getString("d4"));
				tx.setMon_addamt(rs5.getString("a4"));
				tx.setClassdate(cur_date);
				tx.setBr_no(rs5.getString("mang_brno"));
				tx.setApp_op_no(rs5.getString("app_op_no"));
				list2.add(tx);

			}

			// 执行查询，ResultSet保存查询结果--个人季新增
			ps6 = conn.prepareStatement(sql5);
			ps6.setString(1, cur_date);
			ps6.setString(2, cur_date);
			rs6 = ps6.executeQuery();
			while (rs6.next()) {
				for (Rptzjtxmxb rpt : list2) {
					prdt_no = rpt.getPrdt_no();
					String br_no = rpt.getBr_no();
					if (StringUtils.equals(prdt_no, rs6.getString("prdt_no")) 	&& StringUtils.equals(br_no,rs6.getString("mang_brno"))&&StringUtils.isNotEmpty(prdt_no)&&StringUtils.isNotEmpty(rs6.getString("prdt_no")) ) {

							rpt.setPrdt_no(rs6.getString("prdt_no"));
							rpt.setCif_type("2");
							rpt.setSea_addusers(rs6.getString("c5"));
							rpt.setSea_addcounts(rs6.getString("d5"));
							rpt.setSea_addamt(rs6.getString("a5"));
							rpt.setClassdate(cur_date);
							rpt.setBr_no(rs6.getString("mang_brno"));
							rpt.setApp_op_no(rs6.getString("app_op_no"));
							// list2.add(tx);
							flag = true;
							break;
					}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs6.getString("prdt_no"))) {
					tx = new Rptzjtxmxb();
					tx.setPrdt_no(rs6.getString("prdt_no"));
					tx.setCif_type("2");
					tx.setSea_addusers(rs6.getString("c5"));
					tx.setSea_addcounts(rs6.getString("d5"));
					tx.setSea_addamt(rs6.getString("a5"));
					tx.setClassdate(cur_date);
					tx.setBr_no(rs6.getString("mang_brno"));
					tx.setApp_op_no(rs6.getString("app_op_no"));
					list2.add(tx);
					flag = false;
				}
			}

			// 执行查询，ResultSet保存查询结果--个人年新增
			ps7 = conn.prepareStatement(sql6);
			ps7.setString(1, cur_date);
			ps7.setString(2, cur_date);
			rs7 = ps7.executeQuery();
			while (rs7.next()) {
				for (Rptzjtxmxb rpt : list2) {
					prdt_no = rpt.getPrdt_no();
					String br_no = rpt.getBr_no();
					if (StringUtils.equals(prdt_no, rs7.getString("prdt_no")) 	&& StringUtils.equals(br_no,rs7.getString("mang_brno"))&&StringUtils.isNotEmpty(prdt_no)&&StringUtils.isNotEmpty(rs7.getString("prdt_no")) ) {

							rpt.setPrdt_no(rs7.getString("prdt_no"));
							rpt.setCif_type("2");
							rpt.setYear_addusers(rs7.getString("c6"));
							rpt.setYear_addcounts(rs7.getString("d6"));
							rpt.setYear_addamt(rs7.getString("a6"));

							rpt.setClassdate(cur_date);
							rpt.setBr_no(rs7.getString("mang_brno"));
							rpt.setApp_op_no(rs7.getString("app_op_no"));
							// list2.add(tx);
							flag = true;
							break;
						}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs7.getString("prdt_no"))) {
					tx = new Rptzjtxmxb();
					tx.setPrdt_no(rs7.getString("prdt_no"));
					tx.setCif_type("2");
					tx.setYear_addusers(rs7.getString("c6"));
					tx.setYear_addcounts(rs7.getString("d6"));
					tx.setYear_addamt(rs7.getString("a6"));

					tx.setClassdate(cur_date);
					tx.setBr_no(rs7.getString("mang_brno"));
					tx.setApp_op_no(rs7.getString("app_op_no"));
					list2.add(tx);
					flag = false;
				}
			}
			// 执行查询，ResultSet保存查询结果--个人各项累计
			ps9 = conn.prepareStatement(sql8);
			ps9.setString(1, cur_date);
			rs9 = ps9.executeQuery();
			while (rs9.next()) {
				for (Rptzjtxmxb rpt : list2) {
					prdt_no = rpt.getPrdt_no();
					String br_no = rpt.getBr_no();
					if (StringUtils.equals(prdt_no, rs9.getString("prdt_no")) 	&& StringUtils.equals(br_no,rs9.getString("mang_brno"))&&StringUtils.isNotEmpty(prdt_no)&&StringUtils.isNotEmpty(rs9.getString("prdt_no")) ) {

							rpt.setPrdt_no(rs9.getString("prdt_no"));
							rpt.setCif_type("2");
							rpt.setBal(rs9.getString("a8"));
							rpt.setTotal_amt(rs9.getString("b8"));
							rpt.setTotal_counts(rs9.getString("d8"));
							rpt.setTotal_users(rs9.getString("c8"));
							rpt.setClassdate(cur_date);
							rpt.setBr_no(rs9.getString("mang_brno"));
							rpt.setApp_op_no(rs9.getString("app_op_no"));
							// list2.add(tx);
							flag = true;
							break;
						}
				}
				if (flag != true&&StringUtils.isNotEmpty(rs9.getString("prdt_no"))) {
					tx = new Rptzjtxmxb();
					tx.setPrdt_no(rs9.getString("prdt_no"));
					tx.setCif_type("2");
					tx.setTotal_amt(rs9.getString("b8"));
					tx.setTotal_counts(rs9.getString("d8"));
					tx.setTotal_users(rs9.getString("c8"));
					tx.setBal(rs9.getString("a8"));
					tx.setClassdate(cur_date);
					tx.setBr_no(rs9.getString("mang_brno"));
					tx.setApp_op_no(rs9.getString("app_op_no"));
					list2.add(tx);
					flag = false;
				}
			}
			// ==================================================
			if (list2 != null) {
				for (int i = 0; i < list2.size(); i++) {
					Rptzjtxmxb zjtx = list2.get(i);
					PreparedStatement ps = null;
					StringBuffer sb2 = new StringBuffer();
					int count = 0;
					sb2.append("insert into loan_amt_trade   (prdt_no,   cif_type,   total_users,   mon_addusers,"
							+ "   sea_addusers,   year_addusers,   total_counts,   mon_addcounts,   sea_addcounts,"
							+ "   year_addcounts,   total_amt,   mon_addamt,   sea_addamt,   year_addamt,   bal, app_op_no"
							+ "   percent,   br_no,   classdate) ");
					sb2.append("values  (?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,  ?,  ?)");
					try {
						ps = conn.prepareStatement(sb2.toString());
						ps.setString(1, zjtx.getPrdt_no());
						ps.setString(2, zjtx.getCif_type());
						ps.setString(3, zjtx.getTotal_users());
						ps.setString(4, zjtx.getMon_addusers());
						ps.setString(5, zjtx.getSea_addusers());
						ps.setString(6, zjtx.getYear_addusers());
						ps.setString(7, zjtx.getTotal_counts());
						ps.setString(8, zjtx.getMon_addcounts());
						ps.setString(9, zjtx.getSea_addcounts());
						ps.setString(10, zjtx.getYear_addcounts());
						ps.setString(11, zjtx.getTotal_amt());
						ps.setString(12, zjtx.getMon_addamt());
						ps.setString(13, zjtx.getSea_addamt());
						ps.setString(14, zjtx.getYear_addamt());
						ps.setString(15, zjtx.getBal());
						ps.setString(16, zjtx.getPercent());
						ps.setString(17, zjtx.getBr_no());
						ps.setString(18, zjtx.getClassdate());
						ps.setString(19, zjtx.getApp_op_no());
						count = ps.executeUpdate();
						conn.commit();
						BatchPublic.inBatchlog("R", "R01", "1", conn);
					} catch (Exception e) {
						count = -1;
						BatchPublic.inBatchlog("R", "R01", "2", conn);
					} finally {
						DataSourceUtils.closeStatement(ps);
					}
				}

			}
			else{
				BatchPublic.inBatchlog("R", "R01", "1", conn);
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
		}
	}

}
