package app.batch.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import app.batch.BatchPublic;

public class DataBakDAO {
	private Connection con;
	private String riqi;
	public DataBakDAO(Connection con) {
		this.con = con;
		riqi = BatchPublic.ztGlobal(con);
	}
	
	private String dataBackup(String databasename, String databasepw,
			   String netname, String filepath, String filename)
			   throws IOException {
			  Runtime rt = Runtime.getRuntime();
			  Process processexp = null;
			  checkCreatDir(filepath);
			  String exp = "expdp " + databasename + "/" + databasepw+"@"+netname+ " directory = dir_test dumpfile="+filename+"dmp"+" logfile="+filename+".log" ; 
			  System.out.println("exp"+exp);
			  String success = "0";
			  try {
			   processexp = rt.exec(exp);
			  } catch (IOException e) {
			   success = "-1";
			   e.printStackTrace();
			  }
			return success;
			 }
	
	
	public void checkCreatDir(String dirPath) {
		 File file = new File(dirPath);
		  if (!file.exists()) {
		   file.mkdirs();
		  }
		
	}
	
	



}
