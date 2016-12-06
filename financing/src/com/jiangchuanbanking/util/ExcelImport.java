package com.jiangchuanbanking.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.support.StaticApplicationContext;


public class ExcelImport {

	private static String url = "jdbc:oracle:thin:@192.168.2.85:1521:orcl";
	private static String user = "jclc";
	private static String password = "jclc";
	public static Connection conn;

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);
			
			insertData();// other_rights，为要插入的数据表名
//			String a=StringUtil.PadRight("JC301", "X", 5);
//			System.out.println(a);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void insertData() {
		InputStream is = null;
		ResultSet rs = null;
		PreparedStatement ps = null;// wealth_cif_info
		PreparedStatement ps1 = null;// wealth_pact_info
		PreparedStatement ps2 = null;// wealth_bank_account
		PreparedStatement id =null;
		try {

			File target = new File("C:\\Users\\Administrator\\Desktop\\jieya.xls");
			// 开始读Excel
			is = new FileInputStream(target);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet sh = rwb.getSheet(0);
			int Rows = sh.getRows();
			int Cols = sh.getColumns();
			int startRow = 2;// 从第几行开始读
			int startCol = 1;
			
			// 开始建立插入的sql语句,每一次插入的开头都是不变的,都是字段名
			StringBuffer sql = new StringBuffer("update  wealth_cif_info set ");
			sql.append("ID_NO=?,CONTACT_PHONE=? where cif_name =?");
			
			StringBuffer sql1 = new StringBuffer("update  wealth_pact_info set ");
			sql.append(" ACCOUNT_NO=?,ACCOUNT_NAME=?,ACCOUNT_BANK=? where PACT_NO=?");
			
			StringBuffer sql2 = new StringBuffer("  insert into wealth_bank_account (");
			sql.append("(id, bank_account_id, bank_account_name, bank_account_no, bank_account_addr, sts, updata_time, op_no, open_id)  values  ");
			sql.append("( ?, hibernate_sequence.nextval, ?, ?, ?, ?, ?, ?, ?)");
			
			
			String excel_value = "";
			for (int i = startRow; i < Rows; i++) {
				ps = conn.prepareStatement(sql.toString());
				
				for (int j = startCol - 1; j < Cols; j++) {
					excel_value = sh.getCell(j, i).getContents();
					
					switch (j) {
					case 0://2GAGE_NO
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(2, excel_value.trim());
						break;
					case 1://3GAGE_ID
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(3, excel_value.trim());
						break;
					case 2://4STATUS
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(4, excel_value.trim());
						break;
					case 3://15OWN_NAME
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(15, excel_value.trim());
						break;
					case 4://9CIF_NAME
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(9, excel_value.trim());
						break;
					case 5://12APPLY_NO
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(12, excel_value.trim());
//						ApplyBase applybase = applybasebo.getByAppNO(excel_value.trim());
//						if (applybase!=null) {
//							CifBase cifbase= cifBaseBo.getCifBaseByCifNo(applybase.getCif_no());
//							ps.setString(13, applybase.getCif_no());//13CIF_NO
//							ps.setString(9, applybase.getCif_name());//9CIF_NAME
//							ps.setString(10, cifbase.getIdNo());//10CIF_IDNO
//						}else{
//							ps.setString(13, "");//13CIF_NO
//							ps.setString(10, "");//10CIF_IDNO
//						}
						break;
					case 6://8BR_NO
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(8, excel_value.trim());
						break;
					case 8://16LIFT_TIME
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(16, excel_value.trim());
						break;
					case 10://17CMT
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(17, excel_value.trim());
						break;
					}
					ps.setString(5, "20150407");//5CREATE_DATE	
					ps.setString(6, "");//6UPDATE_DATE
					ps.setString(7, "999999");//7OP_NO	
					ps.setString(11, "");//11PAWN_NO
					ps.setString(14, "");//14BRANCH

				}
				
				System.out.println(sql);
				ps.executeUpdate();
				ps.close();
			}
			
			
			conn.commit();
			is.close();
			System.out.println("他项证信息导入完毕");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public static void updateData(String tbName) {
		InputStream is = null;
		ResultSet rs = null;
		PreparedStatement ps = null;// other_rights
		try {

			File target = new File("C:\\Users\\Administrator\\Desktop\\updateData.xls");
			// 开始读Excel
			is = new FileInputStream(target);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet sh = rwb.getSheet(0);
			int Rows = sh.getRows();
			int Cols = sh.getColumns();
			int startRow = 1;// 从第几行开始读
			int startCol = 1;
			
			// 开始建立插入的sql语句,每一次插入的开头都是不变的,都是字段名
			StringBuffer sql = new StringBuffer("update " + tbName + " set ");
			sql.append(" APPLY_NO = ? where branch = ?");
			
			String excel_value = "";
			for (int i = startRow; i < Rows; i++) {
				ps = conn.prepareStatement(sql.toString());
				for (int j = startCol - 1; j < Cols; j++) {
					excel_value = sh.getCell(j, i).getContents();
					switch (j) {
					case 4://APPLY_NO
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(1, excel_value.trim());
						break;
					case 2://branch
						System.out.print(i + "<=======>" + excel_value);
						ps.setString(2, excel_value.trim());
						break;
					}

				}
				
				System.out.println(sql);
				ps.executeUpdate();
				ps.close();
			}
			
			
			conn.commit();
			is.close();
			System.out.println("他项证信息导入完毕");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
