package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.MyHomeInfoSettingDao;
import com.sp2p.dao.PersonDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.AdminUserDao;
import com.sp2p.dao.admin.RelationDao;
import com.sp2p.dao.admin.ShoveBorrowAmountTypeDao;
import com.sp2p.util.DateUtil;

/**
 * 手机号码注册
 * 
 * @author lw
 * 
 */
public class CellPhoneService extends BaseService {
	private UserDao userDao;
	private RelationDao relationDao;
	private PersonDao personDao;
	private ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao;
	private MyHomeInfoSettingDao myHomeInfoSettingDao;
    private AdminUserDao adminUserDao;
	public MyHomeInfoSettingDao getMyHomeInfoSettingDao() {
		return myHomeInfoSettingDao;
	}

	public void setMyHomeInfoSettingDao(MyHomeInfoSettingDao myHomeInfoSettingDao) {
		this.myHomeInfoSettingDao = myHomeInfoSettingDao;
	}

	public static Log log = LogFactory.getLog(CellPhoneService.class);

	/**
	 * 用户注册
	 * 
	 * @param email
	 * @param username
	 * @param password
	 * @param refferee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long usercellRegister(String cellphone, String userName,
			String password, String refferee,String src,String activity, Map<String, Object> userMap,
			int typeLen,long recommendUserId,int regSrc) throws Exception {
		Connection conn = MySQL.getConnection();

		String email = null;
		String dealpwd = null; // 交易密码
		String mobilePhone = cellphone;
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		// DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String vipcreatetime = null;// VIP创建时间
		double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		Integer enable = 1; // 是否禁用 1、启用 2、禁用
		// 测试--跳过验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到信息额度类型
		Map<String, String> map = shoveBorrowAmountTypeDao
				.queryBorrowAmountByNid(conn, "credit");
		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
		if (IConstants.ISDEMO.equals("1")) {
			authstep = 1;
			enable = 1;
			vipstatus = 2;
			vipcreatetime = df.format(new Date());
			creditlimit = creditLimit;
		}
		Long servicePersonId = null;
		long userId = -1L;
		long result = -1L;
		try {
			userId = userDao.addUsers(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit,regSrc);

			// 将手机号码同步到t_person表中
			if(userId<0){
				return -1L;
			}
			result = personDao.addPerson(conn, null, cellphone, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, userId, null, null, null,
					null,src,activity);

			if (result <= 0) {
				return -1L;
			}
			//绑定手机
			myHomeInfoSettingDao.addBindingMobile(conn,
					cellphone, userId, IConstants.PHONE_BINDING_ON,
					"手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				try {
					result = userDao.addMaterialsauth1(conn, userId, i);
					if (result <= 0) {
						return -1L;
					}
				} catch (Exception e) {
					return -1L;
				}
			}
			if (userMap != null) {
				relationDao.addRelation(conn, userId, (Long) userMap
						.get("parentId"), (Integer) userMap.get("level"), 1);
			}

			
			//发送体验金
			Map<String,String> actSetMap = userDao.queryActivtySetComfig(conn, "1005");
			if(actSetMap!=null && actSetMap.size()>0){
				String start_time =  actSetMap.get("start_time_f");
				String end_time = actSetMap.get("end_time_f");
				String currDate = DateUtil.dateToStringYYMMDD(new Date());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d1 = sdf.parse(start_time);
				Date d2 = sdf.parse(currDate);
				Date d3 = sdf.parse(end_time);
				long m = d2.getTime()-d1.getTime();
				long n = d3.getTime()-d2.getTime();
				double amount = 8888;
				if(m>=0 && n>=0){//在活动时间范围内
					userDao.addSendTyj(conn, userId, amount);//注册用户发送体验金
					
//					Map<String,String> investMap = userDao.queryUserInvestSum(conn, recommendUserId);//查询推荐人投资总额
//					if(investMap!=null && investMap.size()>0){
//					   double investAmountSum = Convert.strToDouble(investMap.get("investAmountSum"), 0);
//					   if(investAmountSum>=10000){
//						   int counts = 0;
//						   Map<String,String> userTyjMap = userDao.queryUserTyjCounts(conn, recommendUserId);
//						   if(userTyjMap!=null && userTyjMap.size()>0){
//							   counts = Convert.strToInt(userTyjMap.get("counts"), 0);//推荐人获取体验金份数
//						   }
//						   Map<String,String> userMaps = userDao.queryUserById(conn, recommendUserId);
//						   if(userMaps!=null && userMaps.size()>=0){
//							  String createTime =  userMaps.get("createTime");//推荐人注册时间
//							  if(createTime.compareTo(start_time)<0){
//								  if(counts<50){//50
//									  userDao.addSendTyj(conn, recommendUserId, amount);//推荐人发送体验金
//								  }
//							  }
//							  if(createTime.compareTo(start_time)>=0){
//								  if(counts<51){//51
//									  userDao.addSendTyj(conn, recommendUserId, amount);//推荐人发送体验金
//								  }
//							  }
//						   }
//						   
//					   }
//					}
				}
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

		return userId;
	}
 
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	// 通过用户手机号码更改用户登录密码

	public Long updatepasswordBycellphone(String cellphone, String password)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;

		try {
			resultId = userDao.updatepasswordBycellphone(conn, cellphone,
					password);

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return resultId;
	}

	/**
	 * 根据手机号码查询
	 * 
	 * @param cellphone
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryCellPhone(String cellphone)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = personDao.querCellPhone(conn, cellphone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public void setShoveBorrowAmountTypeDao(
			ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao) {
		this.shoveBorrowAmountTypeDao = shoveBorrowAmountTypeDao;
	}
	
	
	/**
	 * 小贷公司的超级管理员 注册贷款账户.
	 * 
	 * @author 郭井超
	 * @author 2014-12-12
	 * @param adminId    小贷公司的超级管理员ID
	 * @param email
	 * @param username
	 * @param password
	 * @param refferee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long loanCompanyAdminUserCellRegister(long adminId,String cellphone, String userName,
			String password, String refferee,String src,String activity, Map<String, Object> userMap,
			int typeLen,int isFlag) throws Exception {
		Connection conn = MySQL.getConnection();

		String email = null;
		String dealpwd = null; // 交易密码
		String mobilePhone = cellphone;
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		// DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String vipcreatetime = null;// VIP创建时间
		double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		Integer enable = 1; // 是否禁用 1、启用 2、禁用
		// 测试--跳过验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到信息额度类型
		Map<String, String> map = shoveBorrowAmountTypeDao
				.queryBorrowAmountByNid(conn, "credit");
		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
		if (IConstants.ISDEMO.equals("1")) {
			authstep = 1;
			enable = 1;
			vipstatus = 2;
			vipcreatetime = df.format(new Date());
			creditlimit = creditLimit;
		}
		Long servicePersonId = null;
		long userId = -1L;
		long result = -1L;
		try {
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit);
			
			adminUserDao.addAdminUser(conn, adminId, userId,isFlag);

			// 将手机号码同步到t_person表中
			if(userId<0){
				return -1L;
			}
			result = personDao.addPerson(conn, null, cellphone, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, userId, null, null, null,
					null,src,activity);

			if (result <= 0) {
				return -1L;
			}
			//绑定手机
			myHomeInfoSettingDao.addBindingMobile(conn,
					cellphone, userId, IConstants.PHONE_BINDING_ON,
					"手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				try {
					result = userDao.addMaterialsauth1(conn, userId, i);
					if (result <= 0) {
						return -1L;
					}
				} catch (Exception e) {
					return -1L;
				}
			}
			if (userMap != null) {
				relationDao.addRelation(conn, userId, (Long) userMap
						.get("parentId"), (Integer) userMap.get("level"), 1);
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

		return userId;
	}
	
	/**
	 * 注册营销账户
	 * @param adminId
	 * @param cellphone
	 * @param userName
	 * @param password
	 * @param refferee
	 * @param src
	 * @param activity
	 * @param userMap
	 * @param typeLen
	 * @param isFlag
	 * @return
	 * @throws Exception
	 */
	public Long regYxUser(long adminId,String cellphone, String userName,
			String password, String refferee,String src,String activity, Map<String, Object> userMap,
			int typeLen,int isFlag) throws Exception {
		Connection conn = MySQL.getConnection();

		String email = null;
		String dealpwd = null; // 交易密码
		String mobilePhone = cellphone;
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		// DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String vipcreatetime = null;// VIP创建时间
		double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		Integer enable = 1; // 是否禁用 1、启用 2、禁用
		// 测试--跳过验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到信息额度类型
		Map<String, String> map = shoveBorrowAmountTypeDao
				.queryBorrowAmountByNid(conn, "credit");
		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
		if (IConstants.ISDEMO.equals("1")) {
			authstep = 1;
			enable = 1;
			vipstatus = 2;
			vipcreatetime = df.format(new Date());
			creditlimit = creditLimit;
		}
		Long servicePersonId = null;
		long userId = -1L;
		long result = -1L;
		
		int aa = (int) (Math.random()*9000+1000);//四位随机数
		String tt = String.valueOf(aa);
		try {
			try {
				userId = userDao.addUser(conn, email, userName, password, refferee,
						lastDate, lastIP, dealpwd, mobilePhone+tt, rating,
						creditrating, vipstatus, vipcreatetime, authstep, headImg,
						enable, servicePersonId, creditLimit);
			} catch (Exception e) {
				 e.printStackTrace();
			}

			// 将手机号码同步到t_person表中
			if(userId<0){
				return -1L;
			}
			result = userDao.addYxUser(conn, userId, mobilePhone);
			if(result<0){
				return -1L;
			}
			result = personDao.addPerson(conn, null, cellphone+tt, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, userId, null, null, null,
					null,src,activity);

			if (result <= 0) {
				return -1L;
			}
			//绑定手机
			myHomeInfoSettingDao.addBindingMobile(conn,
					cellphone+tt, userId, IConstants.PHONE_BINDING_ON,
					"手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				try {
					result = userDao.addMaterialsauth1(conn, userId, i);
					if (result <= 0) {
						return -1L;
					}
				} catch (Exception e) {
					return -1L;
				}
			}
			if (userMap != null) {
				relationDao.addRelation(conn, userId, (Long) userMap
						.get("parentId"), (Integer) userMap.get("level"), 1);
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

		return userId;
	}
	
	public Long userGroupRegUser(long adminId,String cellphone, String userName,
			String password, String refferee,String src,String activity, Map<String, Object> userMap,
			int typeLen,int groupId) throws Exception {
		Connection conn = MySQL.getConnection();

		String email = null;
		String dealpwd = null; // 交易密码
		String mobilePhone = cellphone;
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		// DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String vipcreatetime = null;// VIP创建时间
		double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		Integer enable = 1; // 是否禁用 1、启用 2、禁用
		// 测试--跳过验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到信息额度类型
		Map<String, String> map = shoveBorrowAmountTypeDao
				.queryBorrowAmountByNid(conn, "credit");
		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
		if (IConstants.ISDEMO.equals("1")) {
			authstep = 1;
			enable = 1;
			vipstatus = 2;
			vipcreatetime = df.format(new Date());
			creditlimit = creditLimit;
		}
		Long servicePersonId = null;
		long userId = -1L;
		long result = -1L;
		try {
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit);
			
			long m = adminUserDao.addUserGroup(conn, groupId, userId);

			if(m<0){
				return -1L;
			}
			if(userId<0){
				return -1L;
			}
			// 将手机号码同步到t_person表中
			result = personDao.addPerson(conn, null, cellphone, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, userId, null, null, null,
					null,src,activity);

			if (result <= 0) {
				return -1L;
			}
			//绑定手机
			myHomeInfoSettingDao.addBindingMobile(conn,
					cellphone, userId, IConstants.PHONE_BINDING_ON,
					"手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				try {
					result = userDao.addMaterialsauth1(conn, userId, i);
					if (result <= 0) {
						return -1L;
					}
				} catch (Exception e) {
					return -1L;
				}
			}
			if (userMap != null) {
				relationDao.addRelation(conn, userId, (Long) userMap
						.get("parentId"), (Integer) userMap.get("level"), 1);
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

		return userId;
	}

	public AdminUserDao getAdminUserDao() {
		return adminUserDao;
	}

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}
	
	

}
