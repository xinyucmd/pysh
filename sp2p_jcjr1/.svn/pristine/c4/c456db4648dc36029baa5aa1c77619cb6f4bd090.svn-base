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
import com.sp2p.database.Dao;

/**
 * 小贷公司和担保公司关系持久化类
 * @author ChenglongZhao
 */
public class CompanyLoanBondingDao {

	/**
	 * 查询小贷公司和担保公司关系列表
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryCompanyLoanBondingByConditions(Connection conn,long id ,String name) throws SQLException, DataException{
		Dao.Tables.t_company_loan_bonding t_company_loan_bonding = new Dao().new Tables().new t_company_loan_bonding();
		StringBuffer condition = new StringBuffer();
		condition.append("1 = 1");
		if(id>0){
			condition.append(" and id='"+id+"'");
		}
		
		DataSet dataSet = t_company_loan_bonding.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public long addCompnayLoanBonding(Connection conn,long loan_comp_id,long bonding_comp_id,Date start_time,Date end_time,double credit_limit,
			int model_type,Date create_time,String desc,String bonding_letter_path) throws SQLException{
		Dao.Tables.t_company_loan_bonding t_company_loan_bonding = new Dao().new Tables().new t_company_loan_bonding();
		t_company_loan_bonding.loan_comp_id.setValue(loan_comp_id);
		t_company_loan_bonding.bonding_comp_id.setValue(bonding_comp_id);
		t_company_loan_bonding.start_time.setValue(start_time);
		t_company_loan_bonding.end_time.setValue(end_time);
		t_company_loan_bonding.credit_limit.setValue(credit_limit);
		t_company_loan_bonding.model_type.setValue(model_type);
		t_company_loan_bonding.create_time.setValue(create_time);
		t_company_loan_bonding.desc.setValue(desc);
		t_company_loan_bonding.bonding_letter_path.setValue(bonding_letter_path);
		
		return t_company_loan_bonding.insert(conn);
	}
	
	public long updateCompnayLoanBonding(Connection conn,long id,Date start_time,Date end_time,double credit_limit,
			int model_type,String desc,String bonding_letter_path) throws SQLException{
		Dao.Tables.t_company_loan_bonding t_company_loan_bonding = new Dao().new Tables().new t_company_loan_bonding();
		t_company_loan_bonding.start_time.setValue(start_time);
		t_company_loan_bonding.end_time.setValue(end_time);
		t_company_loan_bonding.credit_limit.setValue(credit_limit);
		t_company_loan_bonding.model_type.setValue(model_type);
		t_company_loan_bonding.desc.setValue(desc);
		t_company_loan_bonding.bonding_letter_path.setValue(bonding_letter_path);
		
		return t_company_loan_bonding.update(conn, " id = "+id);
	}
	
	/***
	 * 查询合作模式
	 * @param conn
	 * @param loanId
	 * @param bondingId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryModel(Connection conn,long loanId,long bondingId) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_company_loan_bonding where loan_comp_id = "+loanId+" and bonding_comp_id = "+bondingId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
}
