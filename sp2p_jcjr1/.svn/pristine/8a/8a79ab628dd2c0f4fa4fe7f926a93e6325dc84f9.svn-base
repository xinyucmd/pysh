package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.CompanyBondingDao;
import com.sp2p.dao.admin.CompanyLoanBondingDao;

/**
 * 担保公司持久化类
 * 
 * @author ChenglongZhao
 */
public class CompanyBondingService extends BaseService {
	public static Log log = LogFactory.getLog(CompanyBondingService.class);
	private CompanyBondingDao companyBondingDao;
	private CompanyLoanBondingDao companyLoanBondingDao;

	
	/**
	 * 根据小贷公司id 查询所有担保公司信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAllBondingCompanyByLoadId(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		try {
			list = companyBondingDao.findAllBondingCompanyByLoadId(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return list;
	}
	
	/**
	 * 更新担保公司和中间表信息
	 * @return
	 */
	public long updateBondingCompanyData(long id ,String name,Date in_time,int status,String desc,
			long loan_bonding_id ,Date start_time,Date end_time,double credit_limit,
			int model_type,String loan_bonding_desc,String bonding_letter_path,int level) throws Exception{
		Connection conn = MySQL.getConnection();
		long state = -1;
		try {
		long m = companyBondingDao.updateCompnayBonding(conn, id, name, in_time, status, desc,level);
		long n = companyLoanBondingDao.updateCompnayLoanBonding(conn, loan_bonding_id, start_time, end_time, credit_limit, model_type, loan_bonding_desc, bonding_letter_path);
		  if(m>0 && n>0){
			conn.commit();
			state = 1;
		  }else{
			conn.rollback();
		  }
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return state;
	}
	/**
	 * 查询小贷公司列表
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryCompanyBondingByConditions(PageBean<Map<String, Object>> pageBean,String bondindCompanyName) throws Exception {
		StringBuffer condition = new StringBuffer();
		 
//		if(bondindCompanyName!=null && !"".equals(bondindCompanyName)){
//			condition.append("  and  name  like '%" + bondindCompanyName + "%'");
//		}
		 
		Connection conn = MySQL.getConnection();
		try {
			//dataPage(conn, pageBean, "t_company_bonding", "*", "", condition.toString());
			companyBondingDao.queryCompanyBondingByConditions(conn, pageBean, bondindCompanyName);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 查询所有担保公司下拉框显示
	 */
	public List<Map<String, Object>> queryCompanyBondingAll(Long id) throws SQLException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = companyBondingDao.queryCompanyBondingAll(conn,id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return result;
	}
	
	
	/**
	 * 添加担保公司
	 */
	public long addCompnayBonding(String name,Date in_time,Date create_time,int status,String desc,
			long loan_comp_id ,Date start_time,Date end_time,double credit_limit,
			int model_type,String loan_bonding_desc,String bonding_letter_path,int level,
			String bondingid) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			
			//选择已经存在担保机构
			if(bondingid!=null && !"".equals(bondingid)){
				long state = companyLoanBondingDao.addCompnayLoanBonding(conn, loan_comp_id, Long.parseLong(bondingid), start_time, end_time, credit_limit, model_type, create_time, loan_bonding_desc, bonding_letter_path);
				if(state>0){
					conn.commit();
				}
				return Long.parseLong(bondingid);
			}
			
			//未选择已经存在担保机构
			long id = companyBondingDao.addCompnayBonding(conn, name, in_time,create_time, status,desc,level);
			if(id>0){
				long state = companyLoanBondingDao.addCompnayLoanBonding(conn, loan_comp_id, id, start_time, end_time, credit_limit, model_type, create_time, loan_bonding_desc, bonding_letter_path);
				if(state>0){
					conn.commit();
				}
			}
			return id;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return 0;
	}
	


	public CompanyBondingDao getCompanyBondingDao() {
		return companyBondingDao;
	}

	public void setCompanyBondingDao(CompanyBondingDao companyBondingDao) {
		this.companyBondingDao = companyBondingDao;
	}

	public CompanyLoanBondingDao getCompanyLoanBondingDao() {
		return companyLoanBondingDao;
	}

	public void setCompanyLoanBondingDao(CompanyLoanBondingDao companyLoanBondingDao) {
		this.companyLoanBondingDao = companyLoanBondingDao;
	}
	
	
}
