package com.sp2p.service.admin;

import java.sql.Connection;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.dao.BorrowfaDao;
import com.sp2p.dao.OperationLogDao;


public class BorrowfaService extends BaseService{
	public static Log log = LogFactory.getLog(BorrowfaService.class);
	private BorrowfaDao borrowfaDao;
	private OperationLogDao operationLogDao;

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}
	public BorrowfaDao getBorrowfaDao() {
		return borrowfaDao;
	}

	public void setBorrowfaDao(BorrowfaDao borrowfaDao) {
		this.borrowfaDao = borrowfaDao;
	}

	public Long addApply(String companyname,String registnumber,
			String tname,String telphone,String cityaddress,
			String borrowAmount,String deadline,String borrowPurpose)
	throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
	result = borrowfaDao.addApply(conn,companyname,registnumber,tname,telphone,cityaddress,
			borrowAmount,deadline,borrowPurpose);
	if (result <= 0) {
		conn.rollback();
		return -1L;
	} 
		conn.commit();
	} catch (Exception e) {
		log.error(e);
		e.printStackTrace();
		conn.rollback();
			throw e;
	} finally {
		conn.close();
	}
	
	return result;
	}
	
	public Long addApplyAdmin(String companyname,String registnumber,
			String tname,String telphone,String cityaddress,
			String borrowAmount,String deadline,String borrowPurpose)
	throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
	result = borrowfaDao.addApplyAdmin(conn,companyname,registnumber,tname,telphone,cityaddress,
			borrowAmount,deadline,borrowPurpose);
	if (result <= 0) {
		conn.rollback();
		return -1L;
	} 
		conn.commit();
	} catch (Exception e) {
		log.error(e);
		e.printStackTrace();
		conn.rollback();
			throw e;
	} finally {
		conn.close();
	}
	
	return result;
	}

	public void queryApplyByCondition(long id, PageBean pageBean)
	throws Exception {
		String resultFeilds = " id,companyname,registnumber,tname,telphone,cityaddress,borrowAmount,deadline,state";
		StringBuffer condition = new StringBuffer();
			condition.append(" and id =" + id);
			Connection conn = MySQL.getConnection();
			try {
				dataPage(conn, pageBean, " t_apply", resultFeilds,
			" order by id desc", condition.toString());
				} catch (Exception e) {
					log.error(e);
					e.printStackTrace();
					throw e;
				} finally {
					conn.close();
				}
    }
	
	public void queryborrowfabiao(PageBean<Map<String, Object>> pageBean, 
			String tname,String telphone,int state)throws Exception {
	
			tname = Utility.filteSqlInfusion(tname);
			telphone = Utility.filteSqlInfusion(telphone);
			Connection conn = MySQL.getConnection();
			StringBuffer condition = new StringBuffer();
			try {
				
				if(state!=-1){
					
					condition.append(" and state = "+state);
				}
				
				if (StringUtils.isNotBlank(tname)) {
					condition.append(" and tname  like '%"
							+ StringEscapeUtils.escapeSql(tname.trim()) + "%' ");
				}
				
				if (StringUtils.isNotBlank(telphone)) {
					condition.append(" and telephone  like '%"
							+ StringEscapeUtils.escapeSql(telphone.trim()) + "%' ");
				}
				dataPage(conn, pageBean, "t_apply", "*",
						" order by id ", condition.toString());
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
		}

	public Map<String, String> queryfabiaoInfo(long id) throws Exception {
			
			Connection conn = MySQL.getConnection();
			try {
				return borrowfaDao.queryfabiaoInfo(conn, id);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();

				throw e;
			} finally {
				conn.close();
			}
		}

	public long updateApplystate(long id, String state) throws Exception {
		long result = -1L;
		Connection conn = MySQL.getConnection();
		try {
			result = borrowfaDao.updateApplystate(conn, id, state);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
		
	}

	public void queryborrowfabiaoInfo(PageBean pageBean, String tname,
			String telphone) throws Exception {
		tname = Utility.filteSqlInfusion(tname);
		telphone = Utility.filteSqlInfusion(telphone);
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(tname)) {
				condition.append(" and tname  like '%"
						+ StringEscapeUtils.escapeSql(tname.trim()) + "%' ");
			}
			

			dataPage(conn, pageBean, "t_apply", "companyname, registnumber, tname, telephone, cityaddress,	borrowAmount,	deadline,case state when 1 then '已处理' else '未处理' end state",
					" order by id ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

	}
	/**
	 * 删除分类
	 * 
	 * @param categoryId
	 * @return long
	 * @throws Exception
	 * @throws Exception
	 * @throws DataException
	 * @throws DataException
	 */
	public long deleteBorrowfaId(long id) throws Exception {
		Connection conn = MySQL.getConnection();
	long result = -1L;
	try {
		result = borrowfaDao.deleteBorrowfaId(conn, id);
		conn.commit();
	} catch (Exception e) {
		log.error(e);
		e.printStackTrace();
		conn.rollback();
		
		throw e;
	} finally {
		conn.close();
	}
	
	return result;
}
	/**
	 * 删除企业融资申请表记录
	 * 
	 * @param ids
	 *            企业融资申请表记录拼接成的字符串
	 * @throws Exception
	 */
	public void deleteBorrowfabiao(String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			borrowfaDao.deleteBorrowfabiao(conn, ids);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
	}
	}
	

	


