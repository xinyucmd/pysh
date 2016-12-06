package app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;

import org.apache.commons.lang.StringUtils;

import com.ibatis.sqlmap.engine.mapping.result.ResultMap;

import app.batch.BatchPublic;
import app.batch.entity.Rptxydjqktjb;
import app.batch.entity.Rptzjtxmxb;
import app.util.exc.DataSourceUtils;

/**
 * 资金投向明细表
 * 
 * @author Administrator
 * 
 */
public class RptxydjDAO {
	private Connection conn;
	private String date;
	Rptxydjqktjb dj; //信用等级情况统计表
	private List<Rptxydjqktjb>list1=new ArrayList<Rptxydjqktjb>();
	public RptxydjDAO(Connection conn) {
		this.conn = conn;
		//Leiji();
	}

	//统计
	public void Leiji() {
		PreparedStatement ps1 = null;	//时间
		PreparedStatement ps2 = null; 	//年初
		PreparedStatement ps3 = null;	//本期
		PreparedStatement ps4 = null; 	//从年初到目前为止的笔数
		PreparedStatement ps5 = null;	//去年本期的笔数
		
		PreparedStatement ps6 = null; 	//年初
		PreparedStatement ps7 = null;	//本期
		PreparedStatement ps8 = null; 	//从年初到目前为止的笔数
		PreparedStatement ps9 = null;	//去年本期的笔数
		
		PreparedStatement ps10 = null; 	//各个等级的总数总金额
		PreparedStatement ps11 = null;	//总数、总金额
		
		ResultSet rs1 = null;			//时间
		ResultSet rs2 = null;			//年初
		ResultSet rs3 = null;			//本期
		ResultSet rs4 = null;			//从年初到目前为止的笔数
		ResultSet rs5 = null;			//去年本期的笔数

		ResultSet rs6 = null;			//年初
		ResultSet rs7 = null;			//本期
		ResultSet rs8 = null;			//从年初到目前为止的笔数
		ResultSet rs9 = null;			//去年本期的笔数
		
		ResultSet rs10 = null;			//各个等级的总数总金额
		ResultSet rs11 = null;			//总数、总金额
		String sql="SELECT LST_DATE FROM SYS_GLOBAL";
		String sql1="select grade,count(*) a from rpt_xd where  " +
				"to_date(beg_date,'yyyy/mm/dd')>= add_months(trunc(to_date(?,'yyyy/mm/dd'),'yyyy'),-12)" +
				" and to_date(beg_date,'yyyy/mm/dd')<= trunc(to_date(?,'yyyy/mm/dd'),'yyyy')-1  and grade is not null  " +
				" group by grade ";
		String sql2="select grade,count(*) b " +
				"from rpt_xd where  substr(beg_date,5,2)=substr(?,5,2) " +
				"and substr(beg_date,1,4)=substr(?,1,4)  and grade is not null   group by grade";
		String sql3="select grade,count(*) c from rpt_xd where  " +
				"to_date(beg_date,'yyyy/mm/dd')>=trunc(to_date(?,'yyyy/mm/dd'),'yyyy') " +
				"and to_date(beg_date,'yyyy/mm/dd')<=to_date(?,'yyyy/mm/dd')  and grade is not null " +
				"group by grade";
		String sql4="select grade,count(*) d from rpt_xd  where  " +
				"substr(beg_date,1,4)=substr(?,1,4)-1 " +
				"and substr(beg_date,5,2)=substr(?,5,2)  and grade is not null  group by grade";
		//========================================================================================
		String sql5="select grade ,sum(due_amt) a from rpt_xd  where to_" +
				"date(beg_date,'yyyy/mm/dd')>= add_months(trunc(to_date(?,'yyyy/mm/dd'),'yyyy'),-12) " +
				"and to_date(beg_date,'yyyy/mm/dd')<= trunc(to_date(?,'yyyy/mm/dd'),'yyyy')-1  and grade is not null  group by grade";
		
		String sql6="select grade ,sum(due_amt) b from rpt_xd where substr(beg_date,5,2)=substr(?,5,2) " +
				"and substr(beg_date,1,4)=substr(?,1,4)  and grade is not null  group by grade";
		
		String sql7="select grade,sum(due_amt) c from rpt_xd  where  " +
				"to_date(beg_date,'yyyy/mm/dd')>=trunc(to_date(?,'yyyy/mm/dd'),'yyyy')" +
				" and to_date(beg_date,'yyyy/mm/dd')<=to_date(?,'yyyy/mm/dd')  and grade is not null  group by grade";
		
		String sql8="select grade,sum(due_amt) d from rpt_xd  where  " +
				"substr(beg_date,1,4)=substr(?,1,4)-1 and substr(beg_date,5,2)=substr(?,5,2)  and grade is not null " +
				" group by grade";
		//============================================================================================
		String sql9="select grade, count(*)  coun,sum(due_amt) sum from rpt_xd where grade=? group by grade";
		String sql10="select count(*) coun ,sum(due_amt) sum from rpt_xd ";
		try {
			ps1 = conn.prepareStatement(sql.toString());
			// 执行查询，ResultSet保存查询结果--系统时间
			rs1 = ps1.executeQuery();
			String cur_date="";
			while (rs1.next()) {
				cur_date=rs1.getString(1);
			}
			

			// 执行查询，ResultSet保存查询结果--年初余额
			ps2 = conn.prepareStatement(sql1);
			ps2.setString(1, cur_date);
			ps2.setString(2, cur_date);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				dj=new Rptxydjqktjb();
				dj.setGRADE(rs2.getString("grade"));
				dj.setBEGYEARBAL_ACCOUNT(rs2.getString("a"));
				list1.add(dj);
				
			}
			
			// 执行查询，ResultSet保存查询结果--本期
			ps3 = conn.prepareStatement(sql2);
			ps3.setString(1, cur_date);
			ps3.setString(2, cur_date);
			String grade;
			rs3 = ps3.executeQuery();
			boolean flag=false;
			while (rs3.next()) {
				for(Rptxydjqktjb rpt:list1){
					grade=rpt.getGRADE();
					if (StringUtils.equals(grade, rs3.getString("grade"))&&StringUtils.isNotEmpty(rs3.getString("grade")) ) {
					
						rpt.setTHISMONTH_ACCOUNT(rs3.getString("b"));
						//list1.add(dj);
						flag=true;
						break;
					}
					
				}
				if (flag!=true&&StringUtils.isNotEmpty(rs3.getString("grade"))) {
					dj=new Rptxydjqktjb();
					dj.setGRADE(rs3.getString("grade"));
					dj.setTHISMONTH_ACCOUNT(rs3.getString("b"));;
					list1.add(dj);
					flag=false; 
				}
			}

			// 执行查询，ResultSet保存查询结果--从年初到目前为止
						ps4 = conn.prepareStatement(sql3);
						ps4.setString(1, cur_date);
						ps4.setString(2, cur_date);
						rs4 = ps4.executeQuery();
						while (rs4.next()) {
							for(Rptxydjqktjb rpt:list1){
								grade=rpt.getGRADE();
								if (StringUtils.equals(grade, rs4.getString("grade"))&&StringUtils.isNotEmpty(rs4.getString("grade")) ) {
								
									rpt.setINCRBEGYEAR_ACCOUNT(rs4.getString("c"));
									//list1.add(dj);
									flag=true;
									break;
								
							  }
							}
							if (flag!=true&&StringUtils.isNotEmpty(rs4.getString("grade"))) {
								dj=new Rptxydjqktjb();
								dj.setGRADE(rs4.getString("grade"));
								dj.setINCRBEGYEAR_ACCOUNT(rs4.getString("c"));
								list1.add(dj);
								flag=false; 
							} 
						}
			// 执行查询，ResultSet保存查询结果--去年本期
						ps5 = conn.prepareStatement(sql4);
						ps5.setString(1, cur_date);
						ps5.setString(2, cur_date);
						rs5 = ps5.executeQuery();
						while (rs5.next()) {
							for(Rptxydjqktjb rpt:list1){
								grade=rpt.getGRADE();
								if (StringUtils.equals(grade, rs5.getString("grade"))&&StringUtils.isNotEmpty(rs5.getString("grade"))) {
									rpt.setINCRLASTYEAR_ACCOUNT(rs5.getString("d"));
									//list1.add(dj);
									flag=true;
									break;
								}
							}
							if (flag!=true&&StringUtils.isNotEmpty(rs5.getString("grade"))) {
								dj=new Rptxydjqktjb();
								dj.setGRADE(rs5.getString("grade"));
								dj.setINCRLASTYEAR_ACCOUNT(rs5.getString("d"));
								list1.add(dj);
								flag=false; 
							} 
						}
			//==================================================================
				// 执行查询，ResultSet保存查询结果--年初余额
						ps6 = conn.prepareStatement(sql5);
						ps6.setString(1, cur_date);
						ps6.setString(2, cur_date);
						rs6 = ps6.executeQuery();
						while (rs6.next()) {
							for(Rptxydjqktjb rpt:list1){
								grade=rpt.getGRADE();
								if (StringUtils.equals(grade, rs6.getString("grade"))&&StringUtils.isNotEmpty(rs6.getString("grade"))) {
									rpt.setBEGYEARBAL_AMT(rs6.getString("a"));
									//list1.add(dj);
									flag=true;
									break;
								}
							}
							if (flag!=true&&StringUtils.isNotEmpty(rs6.getString("grade"))) {
								dj=new Rptxydjqktjb();
								dj.setGRADE(rs6.getString("grade"));
								dj.setBEGYEARBAL_AMT(rs6.getString("a"));
								list1.add(dj);
								flag=false; 
							} 
						}
				// 执行查询，ResultSet保存查询结果--本期
						ps7 = conn.prepareStatement(sql6);
						ps7.setString(1, cur_date);
						ps7.setString(2, cur_date);
						rs7 = ps7.executeQuery();
						while (rs7.next()) {
							for(Rptxydjqktjb rpt:list1){
								grade=rpt.getGRADE();
								if (StringUtils.equals(grade, rs7.getString("grade"))&&StringUtils.isNotEmpty(rs7.getString("grade"))) {
									rpt.setTHISMONTH_AMT(rs7.getString("b"));
									//list1.add(dj);
									flag=true;
									break;
								}
							}
							if (flag!=true&&StringUtils.isNotEmpty(rs7.getString("grade"))) {
								dj=new Rptxydjqktjb();
								dj.setGRADE(rs7.getString("grade"));
								dj.setTHISMONTH_AMT(rs7.getString("b"));
								list1.add(dj);
								flag=false; 
							} 
						}
						
				// 执行查询，ResultSet保存查询结果--从年初到目前为止
						ps8 = conn.prepareStatement(sql7);
						ps8.setString(1, cur_date);
						ps8.setString(2, cur_date);
						rs8 = ps8.executeQuery();
						while (rs8.next()) {
							for(Rptxydjqktjb rpt:list1){
								grade=rpt.getGRADE();
								if (StringUtils.equals(grade, rs8.getString("grade"))&&StringUtils.isNotEmpty(rs8.getString("grade"))) {
									rpt.setINCRBEGYEAR_AMT(rs8.getString("c"));
									//list1.add(dj);
									flag=true;
									break;
								}
							}
							if (flag!=true&&StringUtils.isNotEmpty(rs8.getString("grade"))) {
								dj=new Rptxydjqktjb();
								dj.setGRADE(rs8.getString("grade"));
								dj.setINCRBEGYEAR_AMT(rs8.getString("c"));
								list1.add(dj);
								flag=false; 
							} 
						}
						
				// 执行查询，ResultSet保存查询结果--去年本期
								ps9 = conn.prepareStatement(sql8);
								ps9.setString(1, cur_date);
								ps9.setString(2, cur_date);
								rs9 = ps9.executeQuery();
								while (rs9.next()) {
									for(Rptxydjqktjb rpt:list1){
										grade=rpt.getGRADE();
										if (StringUtils.equals(grade, rs9.getString("grade"))&&StringUtils.isNotEmpty(rs9.getString("grade"))) {
											rpt.setINCRLASTYEAR_AMT(rs9.getString("d"));
											//list1.add(dj);
											flag=true;
											break;
										}
									}
									if (flag!=true&&StringUtils.isNotEmpty(rs9.getString("grade"))) {
										dj=new Rptxydjqktjb();
										dj.setGRADE(rs9.getString("grade"));
										dj.setINCRLASTYEAR_AMT(rs9.getString("d"));
										list1.add(dj);
										flag=false; 
									} 
								}				
								ps11 = conn.prepareStatement(sql10);
								rs11 = ps11.executeQuery();
								String zCount = null;
								String zSum = null;
								while (rs11.next()) {
									 zCount=rs11.getString("coun");
									 zSum=rs11.getString("sum");
								}
			// 执行查询，ResultSet保存查询结果--去年本期 
								ps10 = conn.prepareStatement(sql9);
								for(Rptxydjqktjb rpt0:list1){
								ps10.setString(1, rpt0.getGRADE()==null?"":rpt0.getGRADE());
								rs10 = ps10.executeQuery();
								while (rs10.next()) {
								for(Rptxydjqktjb rpt:list1){
									grade=rpt.getGRADE();
									if (StringUtils.equals(grade, rs10.getString("grade"))&&StringUtils.isNotEmpty(rs10.getString("grade"))) {
										if (Integer.parseInt(rs10.getString("coun"))==0) {
											rpt.setPERCENT_ACCOUN("0");
											rpt.setPERCENT_AMT("0");
											//list1.add(dj);
										}else{
											rpt.setPERCENT_ACCOUN(Double.parseDouble(rs10.getString("coun"))/Integer.parseInt(zCount)+"");
											rpt.setPERCENT_AMT(Double.parseDouble(rs10.getString("sum"))/Integer.parseInt(zSum)+"");
											//list1.add(dj);
										}
										
										flag=true;
										break;
									}
								
								if (flag!=true&&StringUtils.isNotEmpty(rs10.getString("grade"))) {
									dj=new Rptxydjqktjb();
									dj.setGRADE(rs10.getString("grade"));
									if (Integer.parseInt(rs11.getString("coun"))==0) {
										dj.setPERCENT_ACCOUN("0");
										dj.setPERCENT_AMT("0");
										//list1.add(dj);
									}else{
										dj.setPERCENT_ACCOUN(Integer.parseInt(rs10.getString("coun"))/Integer.parseInt(rs11.getString("coun"))+"");
										dj.setPERCENT_AMT(Integer.parseInt(rs10.getString("sum"))/Integer.parseInt(rs11.getString("sum"))+"");
										//list1.add(dj);
									}
									list1.add(dj);
									flag=false; 
								} 
					
							}}
								}	
								
								if (list1!=null) {
									for (int i = 0; i < list1.size(); i++) {
										Rptxydjqktjb xydj=list1.get(i);
									PreparedStatement ps = null;
									StringBuffer sb1 = new StringBuffer();
									int count = 0;
									sb1.append("insert into xydjqkb  (grade,   begyearbal_account,   thismonth_account,   incrbegyear_account," +
											"   incrlastyear_account,   percent_account,   begyearbal_amt,   thismonth_amt,   incrbegyear_amt," +
											"   incrlastyear_amt,   percent_amt)");
									sb1.append("values   (?,   ?,   ?,   ?,  ?,   ?,   ?,   ?, ?,   ?,   ?)");

								
									try {
										ps = conn.prepareStatement(sb1.toString());
										ps.setString(1, xydj.getGRADE());
										ps.setString(2, xydj.getBEGYEARBAL_ACCOUNT());
										ps.setString(3, xydj.getTHISMONTH_ACCOUNT());
										ps.setString(4, xydj.getINCRBEGYEAR_ACCOUNT());
										ps.setString(5, xydj.getINCRLASTYEAR_ACCOUNT());
										ps.setString(6, xydj.getPERCENT_ACCOUN());
										ps.setString(7, xydj.getBEGYEARBAL_AMT());
										ps.setString(8, xydj.getTHISMONTH_AMT());
										ps.setString(9, xydj.getINCRBEGYEAR_AMT());
										ps.setString(10, xydj.getINCRLASTYEAR_AMT());
										ps.setString(11, xydj.getPERCENT_AMT());

										count = ps.executeUpdate();
										conn.commit();
										BatchPublic.inBatchlog("S", "S01", "1", conn);
									} catch (Exception e) {
										count=-1;
										BatchPublic.inBatchlog("S", "S01", "2", conn);
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
		}
	

}
}
