package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.database.Dao;

/**
 * 担保公司持久化类
 * @author ChenglongZhao
 */
public class CompanyBondingDao {

	/**
	 * 查询担保公司列表分页
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryCompanyBondingByConditions(Connection conn,PageBean<Map<String, Object>> pageBean,String bondindCompanyName) throws SQLException, DataException{
		Dao.Tables.t_company_bonding t_company_bonding = new Dao().new Tables().new t_company_bonding();
		StringBuffer condition = new StringBuffer();
		if(bondindCompanyName!=null && !"".equals(bondindCompanyName)){
			condition.append(" name  like '%" + bondindCompanyName + "%'");
		}
		long c=t_company_bonding.getCount(conn, condition.toString()); 
		boolean  result=pageBean.setTotalNum(c); 
		if(result)
		{
			DataSet ds= t_company_bonding.open(conn, " DATE_FORMAT(in_time,'%Y-%m-%d %H:%i:%s') as in_time_f,t_company_bonding.*  ", condition.toString(), " ", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();
			pageBean.setPage(ds.tables.get(0).rows.rowsMap); 
		}
		 
	}
	
	/**
	 * 查询所有担保公司显示下拉列表
	 */
	public List<Map<String,Object>> queryCompanyBondingAll(Connection conn,Long id) throws SQLException, DataException{
		Dao.Tables.t_company_bonding t_company_bonding = new Dao().new Tables().new t_company_bonding();
		 
		DataSet dataSet = null;
		if(id>0){
			 dataSet = t_company_bonding.open(conn, "DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%s') as create_time_f,t_company_bonding.* ", "id="+id, "", -1, -1);
		}else{
			 dataSet = t_company_bonding.open(conn, "*", "", "", -1, -1);
		}
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public long addCompnayBonding(Connection conn,String name,Date in_time,Date create_time,int status,String desc,int level) throws SQLException{
		Dao.Tables.t_company_bonding t_company_bonding = new Dao().new Tables().new t_company_bonding();
		t_company_bonding.bonding_name.setValue(name);
		t_company_bonding.in_time.setValue(in_time);
		t_company_bonding.create_time.setValue(create_time);
		t_company_bonding.status.setValue(status);
		t_company_bonding.desc.setValue(desc);
		t_company_bonding.bonding_level.setValue(level);
		return t_company_bonding.insert(conn);
	}
	
	public long updateCompnayBonding(Connection conn,long id,String name,Date in_time,int status,String desc,int level) throws SQLException{
		Dao.Tables.t_company_bonding t_company_bonding = new Dao().new Tables().new t_company_bonding();
		t_company_bonding.bonding_name.setValue(name);
		t_company_bonding.in_time.setValue(in_time);
		t_company_bonding.status.setValue(status);
		t_company_bonding.desc.setValue(desc);
		t_company_bonding.bonding_level.setValue(level);
		return t_company_bonding.update(conn, " id = "+id);
	}
	
	/***
	 * 根据小贷公司id查询所有担保公司信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAllBondingCompanyByLoadId(Connection conn,long id) throws Exception {
		StringBuffer buffer = new StringBuffer();
		String sql = " select a.*,DATE_FORMAT(a.in_time,'%Y-%m-%d %H:%i:%s') as in_time_f,b.id as loan_bonding_id,b.bonding_letter_path AS bonding_letter_paths, DATE_FORMAT(b.start_time,'%Y-%m-%d %H:%i:%s') as start_time,DATE_FORMAT(b.end_time,'%Y-%m-%d %H:%i:%s') as end_time,b.credit_limit,b.create_time as loan_bonding_create_time,b.model_type,b.`desc` AS loan_bonding_desc from   t_company_bonding a, t_company_loan_bonding b where a.id = b.bonding_comp_id  and b.loan_comp_id = "+id;
		buffer.append(sql);
		DataSet dataSet = MySQL.executeQuery(conn, buffer.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		 
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
}
