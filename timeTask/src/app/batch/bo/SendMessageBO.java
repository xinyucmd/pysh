package app.batch.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import app.base.ServiceException;
import app.batch.BatchPublic;
import app.batch.dao.SendMessageDAO;
import app.util.exc.DataSourceUtils;

public class SendMessageBO {
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

    public int getStep(String execstep) {
		if (execstep == null || "".equals(execstep)) {
			return 0;
		}
		int i = 0; 
		i = Integer.valueOf(execstep.substring(1));
		return i;
	}
		
	/**贷款到期提醒  
	 * 1，到期前三天上午十点提醒
	 * 2，到期当天上午十点提醒
	 * @return
	 */
	public String loanNotice(){
		Connection conn;
		conn = null;
		try {
			conn = this.getConnection();
			SendMessageDAO dao = new SendMessageDAO(conn);
			String[] execstep = BatchPublic.isExecSussess("Q", conn);

			if (execstep != null && "1".equals(execstep[1]) && "Q08".equals(execstep[0])) {// 已经完全执行成功
				System.out.println("短信通知 批量已经执行成功，无需再次执行==");
				return "0";
			}
			int i = getStep(execstep[0]);
			int flag;
			//get all SMS parameters from DB
			PreparedStatement ps = null;
			ps = conn.prepareStatement("select NOTIFY_CIF_DATE_BEFORE,NOTIFY_MANG_DATE_BEFORE,NOTIFY_CIF_DATE_AFTER,NOTIFY_CIF_MANG_DATE_AFTER,CIF_MANG_LEADER_DATE_AFTER from SMS_PARAM");
			ResultSet rs=ps.executeQuery();
			Integer notifyCifDateBefore=0;
			Integer notifyMangDateBefore=0;
			Integer notifyCifDateAfter=0;
			Integer notifyCifMangDateAfter=0;
			Integer notifyCifMangLeaderDateAfter=0;
			while(rs.next()){
				notifyCifDateBefore=rs.getInt("NOTIFY_CIF_DATE_BEFORE");
				notifyMangDateBefore=rs.getInt("NOTIFY_MANG_DATE_BEFORE");
				notifyCifDateAfter=rs.getInt("NOTIFY_CIF_DATE_AFTER");
				notifyCifMangDateAfter=rs.getInt("NOTIFY_CIF_MANG_DATE_AFTER");
				notifyCifMangLeaderDateAfter=rs.getInt("CIF_MANG_LEADER_DATE_AFTER");
			}
			switch (i) {
			case 0:
			case 1:// 贷款到期前三天-提醒客户
				flag=dao.insertLoanMessageBef3(notifyCifDateBefore);
				if(flag==-1){
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 2:// 贷款到期前三天-提醒客户经理
				flag=dao.insertLoanMessageBef3ForTlr(notifyMangDateBefore);
				if(flag==-1){
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 3://贷款到期前三天-提醒客户经理提醒客户经理主管
//			flag=dao.insertOverLoanMessageBef3ForMang();
//			if(flag==-1){
//				DataSourceUtils.closeConnection(conn);
//				return "0";
//			}
			case 4://贷款到期当天-提醒客户
				flag=dao.insertLoanMessageBef1();
				if(flag==-1){
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 5://贷款到期当天-提醒客户经理
				flag=dao.insertLoanMessageBef1ForTlr();	
				if(flag==-1){
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 6://逾期第三天早上10点--客户
				flag=dao.insertOverLoanMessageBef3(notifyCifDateAfter);	
				if(flag==-1){
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 7://逾期第三天早上10点--客户经理
				flag=dao.insertOverLoanMessageBef3ForTlr(notifyCifMangDateAfter);	
				if(flag==-1){
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			case 8://逾期第三天早上10点--客户经理主管
				flag=dao.insertOverLoanMessageBef3ForMang(notifyCifMangLeaderDateAfter);	
				if(flag==-1){
					DataSourceUtils.closeConnection(conn);
					return "0";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			 DataSourceUtils.closeConnection(conn);	
		}
		return "1";
	}
}
