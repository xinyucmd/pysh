package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.database.Dao;

/**
 * 小贷公司持久化类
 * @author ChenglongZhao
 */
public class CompanyLoanDao {
	
	
	/**
	 * 通过ID查找小贷公司
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> findLoanCompanyById(Connection conn,long id)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_company_loan where id = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
    /**
     * 根据小贷公司id查询抵押图片
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     * @throws DataException
     */
	public List<Map<String, Object>> loadMortgPic(Connection conn,long id)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_company_data where loan_id = "+id);
		sql.append(" ORDER BY id ASC ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 添加小贷公司时：判断小贷公司是否已经有超级管理员-超级管理员要唯一
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> findLoanCompanyAdminUserByLoanId(Connection conn,long id)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.user_id  from t_company_loan a , t_admin b where a.user_id = b.id and a.id = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/***
	 * 判断小贷公司是否存在高级用户
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> findAdminUserByAdminId(Connection conn,long id)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select  *  from t_admin_user a where a.isFlag='1' and a.adminId = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	

	/**
	 * 查询小贷公司列表
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryCompanyLoanByConditions(Connection conn,PageBean<Map<String, Object>> pageBean,String loanCompanyName) throws SQLException, DataException{
		Dao.Tables.t_company_loan t_company_loan = new Dao().new Tables().new t_company_loan();
		StringBuffer condition = new StringBuffer();
		 
		if(loanCompanyName!=null && !"".equals(loanCompanyName)){
			condition.append(" name  like '%" + loanCompanyName + "%'");
		}
		
		long c=t_company_loan.getCount(conn, condition.toString()); 
		boolean  result=pageBean.setTotalNum(c); 
		if(result)
		{
			DataSet ds= t_company_loan.open(conn, " DATE_FORMAT(start_time,'%Y-%m-%d %H:%i:%s') as start_time_f,DATE_FORMAT(end_time,'%Y-%m-%d %H:%i:%s')as end_time_f,t_company_loan.*  ", condition.toString(), " ", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();
			pageBean.setPage(ds.tables.get(0).rows.rowsMap); 
		}
		
		 
	}
	
	public long addCompnayLoan(Connection conn,String name,double total_amount,double available_total_amount,
			double has_total_amount,double insolvency_reserves_scale,Date start_time,Date end_time,
			String service_letter_path,int bonding_required,int status,String desc,int level,double channel_cost,String loanLogo) throws SQLException{
		Dao.Tables.t_company_loan t_company_loan = new Dao().new Tables().new t_company_loan();
		t_company_loan.loan_name.setValue(name);
		t_company_loan.total_amount.setValue(total_amount);
		t_company_loan.available_total_amount.setValue(available_total_amount);
		t_company_loan.has_total_amount.setValue(has_total_amount);
		t_company_loan.insolvency_reserves_scale.setValue(insolvency_reserves_scale);
		t_company_loan.start_time.setValue(start_time);
		t_company_loan.end_time.setValue(end_time);
		t_company_loan.service_letter_path.setValue(service_letter_path);
		t_company_loan.bonding_required.setValue(bonding_required);
		t_company_loan.status.setValue(1);
		t_company_loan.desc.setValue(desc);
		t_company_loan.loan_logo_path.setValue(loanLogo);
		t_company_loan.level.setValue(level);
		t_company_loan.channel_cost.setValue(channel_cost);
		
		return t_company_loan.insert(conn);
	}
	
	public long addCompnayLoanMortg(Connection conn, long loan_id, String data_path,String data_desc, long type)throws SQLException{
		Dao.Tables.t_company_data t_company_data =  new Dao().new Tables().new t_company_data();
		t_company_data.loan_id.setValue(loan_id);
		t_company_data.data_path.setValue(data_path);
		t_company_data.data_desc.setValue(data_desc);
		t_company_data.type.setValue(type);
		return t_company_data.insert(conn);
	}
	
	/***
	 * 修改小贷公司数据
	 * @param conn
	 * @param id
	 * @param name
	 * @param total_amount
	 * @param available_total_amount
	 * @param has_total_amount
	 * @param insolvency_reserves_scale
	 * @param start_time
	 * @param end_time
	 * @param service_letter_path
	 * @param bonding_required
	 * @param status
	 * @param desc
	 * @return
	 * @throws SQLException
	 */
	public long updateCompnayLoanData(Connection conn,long id,String name,double total_amount,double available_total_amount,
			double has_total_amount,double insolvency_reserves_scale,Date start_time,Date end_time,
			String service_letter_path,int bonding_required,int status,String desc,int level,double channel_cost,String loanLogo) throws SQLException{
		Dao.Tables.t_company_loan t_company_loan = new Dao().new Tables().new t_company_loan();
		t_company_loan.loan_name.setValue(name);
		t_company_loan.total_amount.setValue(total_amount);
		t_company_loan.available_total_amount.setValue(available_total_amount);
		t_company_loan.has_total_amount.setValue(total_amount-available_total_amount);
		t_company_loan.insolvency_reserves_scale.setValue(insolvency_reserves_scale);
		t_company_loan.start_time.setValue(start_time);
		t_company_loan.end_time.setValue(end_time);
		t_company_loan.service_letter_path.setValue(service_letter_path);
		t_company_loan.bonding_required.setValue(bonding_required);
		//t_company_loan.status.setValue(status);
		t_company_loan.desc.setValue(desc);
		t_company_loan.level.setValue(level);
		t_company_loan.channel_cost.setValue(channel_cost);
		t_company_loan.loan_logo_path.setValue(loanLogo);
		return t_company_loan.update(conn, "id="+id);
	}
	
	/***
	 * 修改抵押图片
	 * @param conn
	 * @param id
	 * @param data_path
	 * @param data_desc
	 * @return
	 * @throws SQLException
	 */
	public long updateCompnayMortgpic(Connection conn,long id,String data_path,String data_desc)throws SQLException{
		Dao.Tables.t_company_data t_company_data = new Dao().new Tables().new t_company_data();
		t_company_data.data_path.setValue(data_path);
		t_company_data.data_desc.setValue(data_desc);
		return t_company_data.update(conn, "id="+id);
	}
	
	
	/**
	 * 添加小贷公司管理员时 user_id更新到 小贷公司表——user_id字段
	 * @param conn
	 * @param id
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long   updateCompnayLoan(Connection conn,long id,long userId) throws SQLException{
		Dao.Tables.t_company_loan t_company_loan = new Dao().new Tables().new t_company_loan();
		t_company_loan.user_id.setValue(userId);
		return t_company_loan.update(conn, "id="+id);
	}
	
	/***
	 * 删除小贷公司数据
	 * @param conn
	 * @param id  小贷公司id
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long deleteCompanyLoan(Connection conn,long id,int temp) throws SQLException{
		Dao.Tables.t_company_loan t_company_loan = new Dao().new Tables().new t_company_loan();
		if(temp==0){
		   t_company_loan.status.setValue(0);//禁用
		}
		if(temp==1){
		   t_company_loan.status.setValue(1);//启用
		}
		return t_company_loan.update(conn,  " id="+id );
	}
	
	/***
	 * 删除小贷公司图片数据
	 * @param conn
	 * @param id  小贷公司id
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long deleteCompanyImg(Connection conn,long id) throws SQLException{
		Dao.Tables.t_company_data t_company_data = new Dao().new Tables().new t_company_data();
		return t_company_data.delete(conn,  " id="+id );
	}
	
	/***
	 * 删除小贷公司关联图片数据
	 * @param conn
	 * @param id  小贷公司id
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long deleteCompanyImgData(Connection conn,long id) throws SQLException{
		Dao.Tables.t_company_data t_company_data = new Dao().new Tables().new t_company_data();
		return t_company_data.delete(conn,  " loan_id = " + id );
	}
	
	/**
	 * 禁用小贷公司后台超级管理员
	 * @param conn
	 * @param adminId
	 * @return
	 * @throws SQLException
	 */
	public long stopLoanCompanyAdmin(Connection conn,long adminId,int temp) throws SQLException{
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		if(temp==0){
			t_admin.enable.setValue(2);//禁用
		}
		if(temp==1){
			t_admin.enable.setValue(1);//启用
		}
		return t_admin.update(conn,  " id="+adminId );
	}
	
	/**
	 * 根据小贷公后台管理员user_id查询t_ admin_user表数据中的前台用户id
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAdminUser(Connection conn,long adminId)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_admin_user where adminId = "+adminId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 禁用小贷公司前台所有用户
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long stopLoanCompanyUser(Connection conn,long userId,int temp) throws SQLException{
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		if(temp==0){
			t_user.enable.setValue(2);//禁用
		}
		if(temp==1){
			t_user.enable.setValue(1);//启用
		}
		return t_user.update(conn,  " id="+userId );
	}
	
	/**
	 * 查询渠道费用金额记录
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void findChannelCostRecodeByContions(Connection conn,PageBean<Map<String, Object>> pageBean) throws SQLException, DataException{
		Dao.Tables.t_channel_cost_recond t_channel_cost_recond = new Dao().new Tables().new t_channel_cost_recond();
		StringBuffer condition = new StringBuffer();
		 
//		if(loanCompanyName!=null && !"".equals(loanCompanyName)){
//			condition.append(" name  like '%" + loanCompanyName + "%'");
//		}
		
		long c=t_channel_cost_recond.getCount(conn, condition.toString()); 
		boolean  result=pageBean.setTotalNum(c); 
		if(result)
		{
			DataSet ds= t_channel_cost_recond.open(conn, " DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') as createTime, t_channel_cost_recond.*  ", condition.toString(), " ", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();
			pageBean.setPage(ds.tables.get(0).rows.rowsMap); 
		}
		
		 
	}
	
	 
}
