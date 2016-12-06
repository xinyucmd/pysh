package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.AdminUserDao;
import com.sp2p.dao.admin.CompanyLoanDao;

/**
 * 小贷公司持久化类
 * 
 * @author ChenglongZhao
 */
public class CompanyLoanService extends BaseService {
	public static Log log = LogFactory.getLog(CompanyLoanService.class);
	private CompanyLoanDao companyLoanDao;
	private AdminUserDao adminUserDao;
	
	
	/***
	 * 根据用户名和密码查询用户
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> findUser(String userName,String password)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String, String>();
				
		try {
		  map = adminUserDao.findUser(conn, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
		return  map;
	}
	
	 
	/**
	 * 是否为小贷公司用户
	 * @param uerId
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> findLoanCompanyAdmnUser(long uerId)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map =  new HashMap<String, String>();;
				
		try {
		  map = adminUserDao.findLoanCompanyAdmnUser(conn, uerId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
		return  map;
	}
	
	public long addCompanyAdmnUser(long adminId,long userId,int isFlag)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = null;
		long m = -1;		
		try {
			m = adminUserDao.addAdminUser(conn, adminId, userId, isFlag);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
		return  m;
	}
	
	public Map<String,String> findLoanCompanyAdminUserById(long id)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String,String>();
				
		try {
		  map = adminUserDao.findLoanCompanyAdminUserById(conn, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
		return   map;
	}
	
	/***
	 * 根据id查询小贷公司
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> findLoanCompanyById(long id)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = companyLoanDao.findLoanCompanyById(conn, id);
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
		return map;
	}
	
	public List<Map<String, Object>> loadMortgPic(long id)throws Exception{
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		try {
			list = companyLoanDao.loadMortgPic(conn, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			conn.close();
		}
		return list;
	}
	
	
	
	/***
	 * 修改小贷公司数据
	 * @param admin_user_id
	 * @param adminId
	 * @param isFlag
	 * @return
	 * @throws Exception
	 */
	public long updateCompnayLoanData(long id,String name,double total_amount,double available_total_amount,
			double has_total_amount,double insolvency_reserves_scale,Date start_time,Date end_time,
			String service_letter_path,int bonding_required,int status,String desc,int level,double channel_cost,
			String mortg_path, String mortg_desc, String mortg_update_path, String mortg_update_desc, String idsMortg,
			String data_path, String data_desc, String data_update_path, String data_update_desc, String ids,String loanLogo)throws Exception{
		Connection conn = MySQL.getConnection();
		long m = -1;
		try {
			m = companyLoanDao.updateCompnayLoanData(conn, id, name, total_amount, available_total_amount, 
					has_total_amount, insolvency_reserves_scale, start_time, end_time, 
					service_letter_path, bonding_required, status, desc,level,channel_cost,loanLogo);
			if(bonding_required==0){
				//whb 添加抵押物资料
				if(!"".equals(mortg_path)){
					String[] mortgPath = mortg_path.split(",");
					String[] mortgDesc = mortg_desc.split(",");
					for(int i=0;i<mortgPath.length;i++){
						if(!"".equals(mortgPath[i].trim())){
							long mortg_id = companyLoanDao.addCompnayLoanMortg(conn, id, mortgPath[i], mortgDesc[i], 1);
							if(m>0 && mortg_id>0){
								conn.commit();
							}
						}
					}
				}
				//whb 修改抵押物资料
				if(!"".equals(mortg_update_path)){
					String[] mortgUpdatePath = mortg_update_path.split(",");
					String[] mortgUpdateDesc = mortg_update_desc.split(",");
					String[] idu = idsMortg.split(",");
					for(int i=0;i<mortgUpdatePath.length;i++){
						if(!"".equals(mortgUpdatePath[i].trim()) && !"".equals(mortgUpdateDesc[i].trim())){
							long mortg_id = companyLoanDao.updateCompnayMortgpic(conn, Long.parseLong(idu[i]), mortgUpdatePath[i], mortgUpdateDesc[i]);
							if(m>0 && mortg_id>0){
								conn.commit();
							}
						}
					}
				}
			}
			//whb 添加图片资料
			if(!"".equals(data_path)){
				String[] dataPath = data_path.split(",");
				String[] dataDesc = data_desc.split(",");
				for(int i=0;i<dataPath.length;i++){
					if(!"".equals(dataPath[i].trim())){
						long dataid = companyLoanDao.addCompnayLoanMortg(conn, id, dataPath[i], dataDesc[i], 2);
						if(m>0 && dataid>0){
							conn.commit();
						}
					}
				}
			}
			//whb 修改图片资料
			if(!"".equals(data_update_path)){
				String[] dataUpdatePath = data_update_path.split(",");
				String[] dataUpdateDesc = data_update_desc.split(",");
				String[] idu = ids.split(",");
				for(int i=0;i<dataUpdatePath.length;i++){
					if(!"".equals(dataUpdatePath[i].trim()) && !"".equals(dataUpdateDesc[i].trim())){
						long dataid = companyLoanDao.updateCompnayMortgpic(conn, Long.parseLong(idu[i]), dataUpdatePath[i], dataUpdateDesc[i]);
						if(m>0 && dataid>0){
							conn.commit();
						}
					}
				}
			}
			else{
				if(m>0){
					conn.commit();
				}
			}
			
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	    return m;
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
	public void queryCompanyLoanByConditions(PageBean<Map<String, Object>> pageBean,String loanCompanyName) throws Exception{
		//StringBuffer condition = new StringBuffer();
		 
//		if(loanCompanyName!=null && !"".equals(loanCompanyName)){
//			condition.append("  and  name  like '%" + loanCompanyName + "%'");
//		}
//		 
		Connection conn = MySQL.getConnection();
		try {
			//dataPage(conn, pageBean, "t_company_loan", "*", "", condition.toString());
			companyLoanDao.queryCompanyLoanByConditions(conn, pageBean, loanCompanyName);
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
	 * 添加小贷公司
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
	public long addCompnayLoan(String name, double total_amount,
			double available_total_amount, double has_total_amount,
			double insolvency_reserves_scale, Date start_time, Date end_time,
			String service_letter_path, int bonding_required, int status,
			String desc,int level,double channel_cost,String mortg_path, String mortg_desc,String data_path,String data_desc,String loanLogo) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			long id = companyLoanDao.addCompnayLoan(conn, name, total_amount,
					available_total_amount, has_total_amount,
					insolvency_reserves_scale, start_time, end_time,
					service_letter_path, bonding_required, status, desc,
					level, channel_cost,loanLogo);
			//不选三方担保 所有要上传抵押物哦
			if(bonding_required==0 && !"".equals(mortg_path)){
			  	String[] mortgPath = mortg_path.split(",");
				String[] mortgDesc = mortg_desc.split(",");
				for(int i=0;i<mortgPath.length;i++){
					if(!"".equals(mortgPath[i].trim())){
						long mortgid = companyLoanDao.addCompnayLoanMortg(conn, id, mortgPath[i], mortgDesc[i], 1);
						if(id>0 && mortgid>0){
							conn.commit();
						}
					}
				}
			}
			//whb 添加图片资料
			if(!"".equals(data_path)){
				String[] dataPath = data_path.split(",");
				String[] dataDesc = data_desc.split(",");
				for(int i=0;i<dataPath.length;i++){
					if(!"".equals(dataPath[i].trim())){
						long dataid = companyLoanDao.addCompnayLoanMortg(conn, id, dataPath[i], dataDesc[i], 2);
						if(id>0 && dataid>0){
							conn.commit();
						}
					}
				}
			}
			else {//有三方担保
				conn.commit();
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
	
	/***
	 * 小贷公司管理员查询旗下所有贷款账户
	 * @param pageBean
	 * @param cellphone
	 * @param userName
	 * @throws Exception
	 */
	public void queryAdminUserPage(PageBean<Map<String, Object>> pageBean,long adminId,String cellphone,String userName) throws Exception{
	
	StringBuffer condition = new StringBuffer();
	condition.append("  AND  admin_id  = " + adminId);
	
	if(userName!=null && !"".equals(userName)){
		condition.append("  AND  username  like '%" + userName+"%'");
	}
	if(cellphone!=null && !"".equals(cellphone)){
		condition.append(" AND  mobilePhone  = '" + cellphone+"'");
	}
	
	Connection conn = MySQL.getConnection();
	try {
		dataPage(conn, pageBean, "v_t_admin_user_list", "*", "", condition.toString());
		conn.commit();
	} catch (Exception e) {
		log.error(e);
		e.printStackTrace();
		throw e;
	} finally {
		conn.close();
	}

 }
	
	/***
	 * 查询营销账户列表
	 * @param pageBean
	 * @param cellphone
	 * @param userName
	 * @throws Exception
	 */
	public void queryUserYxPage(PageBean<Map<String, Object>> pageBean,String cellphone,String userName) throws Exception{
		
		StringBuffer condition = new StringBuffer();
		 
		if(userName!=null && !"".equals(userName)){
			condition.append("  AND  username  like '%" + userName+"%'");
		}
		if(cellphone!=null && !"".equals(cellphone)){
			condition.append(" AND  tell  = '" + cellphone+"'");
		}
		
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_user_yx", "*", "", condition.toString());
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
	 * 更改小贷公司投资用户-是否为前台超级用户
	 * @param admin_user_id
	 * @param isFlag
	 * @throws Exception
	 */
	public long updateAdminUser(long admin_user_id,long adminId,int  isFlag)throws Exception{
		Connection conn = MySQL.getConnection();
		long m = -1;
		try {
			 
				List<Map<String,Object>> lists = adminUserDao.queryAdminUserByConnections(conn, adminId);
				if(lists!=null && lists.size()>0){
					for(int i=0;i<lists.size();i++){
						Map<String,Object> map = lists.get(i);
						long id = (Long) map.get("id");
						adminUserDao.updateAdminUserBatch(conn, id);
					}
				}
		 
		    m = adminUserDao.updateAdminUser(conn, admin_user_id, isFlag);
			conn.commit();
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	    return m;
	}
	
    /***
     * 更改营销账户手机号
     * @param id
     * @param tell
     * @return
     * @throws Exception
     */
	public long updateUserYxTell(long userId,long id,String tell)throws Exception{
		Connection conn = MySQL.getConnection();
		int aa = (int) (Math.random()*9000+1000);//四位随机数
		String tt = String.valueOf(aa);
		
		long m = -1;
		try {
			m = adminUserDao.updateT_User(conn, userId, tell+tt);
			if(m<0){
				return m;	
			}
			m = -1;
			
			m = adminUserDao.updateT_Person(conn, userId, tell+tt);
			if(m<0){
				return m;	
			}
			m = -1;
					
			m = adminUserDao.updateT_Phone_Binding_Info(conn, userId, tell+tt);
			if(m<0){
				return m;	
			}
			m = -1;
			
		    m = adminUserDao.updateUserYxTell(conn, id, tell);
		    if(m>0){
		      conn.commit();
		    }
		}catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	    return m;
	}
	
	/**
	 * 添加小贷公司时：判断小贷公司是否已经有超级管理员-超级管理员要唯一
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> findLoanCompanyAdminUserByLoanId(long id) throws Exception{
		Connection conn = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
		  conn = MySQL.getConnection();
		  map = companyLoanDao.findLoanCompanyAdminUserByLoanId(conn, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			conn.close();
		}
		 
		return map;
	}
	
	public Map<String,String> findAdminUserByAdminId(long id) throws Exception{
		Connection conn = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
		  conn = MySQL.getConnection();
		  map = companyLoanDao.findAdminUserByAdminId(conn, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			conn.close();
		}
		 
		return map;
	}
	
	/***
	 * 删除合作机构
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long deleteCompanyLoan(long id,int temp)throws Exception{
		Connection conn = MySQL.getConnection(); 
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
    long z = -1; 
    try { 
        z = companyLoanDao.deleteCompanyLoan(conn, id,temp);//删除小贷公司数据--禁用 
        Map<String,String> map = companyLoanDao.findLoanCompanyAdminUserByLoanId(conn, id);//判断小贷公司是否有超级管理员 
        if(map!=null && map.size()>0){//有后台超级管理员 
           long adminId = Long.parseLong(map.get("user_id"));//后台超级管理员ID 
           //将该管理员禁用 
           companyLoanDao.stopLoanCompanyAdmin(conn, adminId,temp); 
           
           list = companyLoanDao.findAdminUser(conn, adminId);//检测小贷公司前台用户 
           if(list!=null && list.size()>0){ 
              for(int i = 0 ; i< list.size();i++){ 
                Map<String,Object> obj =  list.get(i); 
                long userId =  (Long) obj.get("userId");//前台账户id; 
                 //将该小贷公司下的所有前台用户禁用 
                companyLoanDao.stopLoanCompanyUser(conn, userId,temp); 
              } 
           } 
           
        } 
       conn.commit(); 
        
    } catch (Exception e) { 
       conn.rollback(); 
       e.printStackTrace(); 
    }finally{ 
       conn.close(); 
    } 
    return z; 
 }
	
	/***
	 * 删除小贷公司图片数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long deleteCompanyImg(long id)throws Exception{
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = companyLoanDao.deleteCompanyImg(conn, id);
			if(result > 0){
				conn.commit();
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return result;
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
	public void findChannelCostRecodeByContions(PageBean<Map<String, Object>> pageBean) throws Exception{
		 
		Connection conn = MySQL.getConnection();
		try {
			companyLoanDao.findChannelCostRecodeByContions(conn, pageBean);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	

	public CompanyLoanDao getCompanyLoanDao() {
		return companyLoanDao;
	}

	public void setCompanyLoanDao(CompanyLoanDao companyLoanDao) {
		this.companyLoanDao = companyLoanDao;
	}

	public AdminUserDao getAdminUserDao() {
		return adminUserDao;
	}

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}
	
	

}
