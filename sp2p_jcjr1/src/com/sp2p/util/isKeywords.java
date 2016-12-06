package com.sp2p.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
 


import com.shove.data.DataSet;
import com.shove.data.dao.MySQL; 
import com.shove.util.BeanMapUtils;
import com.shove.util.SqlInfusion;
import com.sp2p.database.Dao;

public class isKeywords {

	 
	
	/**
	 * 判断是否存在关键字
	 * @param 输入值
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean isKeywordsOnDB(String str) throws SQLException
	{
		str = str.replaceAll(" ", "");
		Connection conn = MySQL.getConnection();
		boolean isKeyword = false;//是否是关键字
		try {
			Dao.Tables.t_keywords list =  new Dao().new Tables().new t_keywords();
			DataSet dataSet = list.open(conn, "sum(INSTR('"+SqlInfusion.FilteSqlInfusion(str)+"',keyword)) as keywordscount", "isuse = 1", "", -1, -1);
			
			if (dataSet == null)
			{
				return false;
			}
			
			if (dataSet.tables.get(0).rows.getCount() < 1)
			{
				isKeyword = false;
			}
			
			
			Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
			Long keywordscount = com.shove.Convert.strToLong(map.get("keywordscount"), 0);
			if (keywordscount > 0 )
			{
				isKeyword = true;
			}
			else
			{
				isKeyword = false;
			}
 
			
		} catch (Exception e) {
			conn.rollback();
			return false;
		}finally{
			conn.close();
		}
		
		 
		return isKeyword;
	}
	
	/**
	 * Unicode转汉字
	 * @param 输入值
	 * @throws SQLException
	 */
	public static String decodeUnicode(String theString) {    
		 char aChar;    
	     int len = theString.length();    
	     StringBuffer outBuffer = new StringBuffer(len);    
	     for (int x = 0; x < len;) {    
	    	 aChar = theString.charAt(x++);    
	    	 if (aChar == '\\') {    
	    		 aChar = theString.charAt(x++);    
	    		 if (aChar == 'u') {    
	    			 // Read the xxxx    
	    			 int value = 0;    
	    			 for (int i = 0; i < 4; i++) {    
	    				 aChar = theString.charAt(x++);    
					     switch (aChar) {    
					          case '0':    
					          case '1':    
					          case '2':    
					          case '3':    
					          case '4':    
					          case '5':    
					          case '6':    
					          case '7':    
					          case '8':    
					          case '9':    
					          value = (value << 4) + aChar - '0';    
					          break;    
					          case 'a':    
					          case 'b':    
					          case 'c':    
					          case 'd':    
					          case 'e':    
					          case 'f':    
					          value = (value << 4) + 10 + aChar - 'a';    
					          break;    
					          case 'A':    
					          case 'B':    
					          case 'C':    
					          case 'D':    
					          case 'E':    
					          case 'F':    
					          value = (value << 4) + 10 + aChar - 'A';    
					          break;    
					          default:    
					          throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");    
					     }    
	    			 }    
		          outBuffer.append((char) value);    
		         } else {    
		          if (aChar == 't')    
		           aChar = '\t';    
		          else if (aChar == 'r')    
		           aChar = '\r';    
		          else if (aChar == 'n')    
		           aChar = '\n';    
		          else if (aChar == 'f')    
		           aChar = '\f';    
		          outBuffer.append(aChar);    
		         }    
		        } else { 
		        outBuffer.append(aChar);   
		        } 
		       }    
		       return outBuffer.toString();    
		      }    
}
