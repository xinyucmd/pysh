package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.CompanyLoanBondingDao;

public class CompanyLoanBondingService extends BaseService {
	public static Log log = LogFactory.getLog(CompanyLoanBondingService.class);
	private  CompanyLoanBondingDao  companyLoanBondingDao;

	public CompanyLoanBondingDao getCompanyLoanBondingDao() {
		return companyLoanBondingDao;
	}

	public void setCompanyLoanBondingDao(CompanyLoanBondingDao companyLoanBondingDao) {
		this.companyLoanBondingDao = companyLoanBondingDao;
	}
	
	
	/**
	 * 添加小额公司和担保公司关系信息
	 * @param conn
	 * @param loan_comp_id
	 * @param bonding_comp_id
	 * @param start_time
	 * @param end_time
	 * @param credit_limit
	 * @param model_type
	 * @param create_time
	 * @param desc
	 * @return
	 * @throws SQLException
	 */
	public long addCompanyLoanBonding(long loan_comp_id,long bonding_comp_id,Date start_time,Date end_time,double credit_limit,
			int model_type,Date create_time,String desc,String bonding_letter_path) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			
			long id = companyLoanBondingDao.addCompnayLoanBonding(conn, loan_comp_id, bonding_comp_id, start_time, end_time, credit_limit, model_type, create_time, desc,bonding_letter_path);
			if(id>0){
				conn.commit();
			}
			return id;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		 
		return 0;
	}

   /**
    * 分页查询小贷公司和担保公司关系
    * @param pageBean
    * @throws Exception
    */
   public void queryCompnayLoanBondingPage(PageBean<Map<String, Object>> pageBean,String loanCompanyNames,String bondingCompanyNames) throws Exception{
		
		StringBuffer condition = new StringBuffer();
		if(loanCompanyNames!=null && !"".equals(loanCompanyNames)){
			condition.append("  and  loan_name  like '%" + loanCompanyNames + "%'");
		}
		if(bondingCompanyNames!=null && !"".equals(bondingCompanyNames)){
			condition.append("  and  bonding_name  like '%" + bondingCompanyNames + "%'");
		}
		 
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_company_loan_bonding_list", "*", "", condition.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	
	}
   
   /**8
    * 查询授信模式
    * @param loanId
    * @param bondingId
    * @return
    * @throws Exception
    */
   public Map<String,String> queryModel(long loanId,long bondingId)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String,String>();
				
		try {
		  map = companyLoanBondingDao.queryModel(conn, loanId,bondingId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
		return   map;
	}
}
