package app.batch.bo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import app.base.ServiceException;
import app.batch.dao.DataBakDAO;
import app.util.exc.DataSourceUtils;

public class DataBakBO {
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Connection getConnection() {

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		return conn;
	}
  
	
	public String expdp() {
		System.out.println("���ݿⱸ����.....................");
		Connection conn = null;
		conn = this.getConnection();
		DataBakDAO exp = new DataBakDAO(conn);
//		String flag = exp.expdp();
//		if(flag.equals("1")){
//			System.out.println("���ݳɹ�");
//		}else{
//			System.out.println("����ʧ��");
//		}
		DataSourceUtils.closeConnection(conn);
		return "1";
	}
	



}
