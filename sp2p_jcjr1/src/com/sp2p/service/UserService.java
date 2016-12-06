package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataRow;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.BeVipDao;
import com.sp2p.dao.MyHomeInfoSettingDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.PersonDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.VidateDao;
import com.sp2p.dao.admin.RelationDao;
import com.sp2p.dao.admin.ShoveBorrowAmountTypeDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.entity.LoginVerify;
import com.sp2p.entity.User;
import com.sp2p.service.admin.SendmsgService;
import com.sp2p.util.DateUtil;

@SuppressWarnings("unused")
public class UserService extends BaseService {

	public static Log log = LogFactory.getLog(UserService.class);

	private UserDao userDao;
	private RelationDao relationDao;
	private SendmsgService sendmsgService;
	private BeVipDao beVipDao;
	private OperationLogDao operationLogDao;
	private MyHomeInfoSettingDao myHomeInfoSettingDao;
	private ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao;
	private PersonDao personDao;
	private VidateDao vidateDao;

	
	public VidateDao getVidateDao() {
		return vidateDao;
	}

	public void setVidateDao(VidateDao vidateDao) {
		this.vidateDao = vidateDao;
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	
	public MyHomeInfoSettingDao getMyHomeInfoSettingDao() {
		return myHomeInfoSettingDao;
	}

	public void setMyHomeInfoSettingDao(
			MyHomeInfoSettingDao myHomeInfoSettingDao) {
		this.myHomeInfoSettingDao = myHomeInfoSettingDao;
	}

	public void setBeVipDao(BeVipDao beVipDao) {
		this.beVipDao = beVipDao;
	}

	public void setSendmsgService(SendmsgService sendmsgService) {
		this.sendmsgService = sendmsgService;
	}

	/**
	 * 添加用户
	 * 
	 * @param email
	 * @param userName
	 * @param password
	 * @param refferee
	 *            推荐人
	 * @param lastDate
	 * @param lastIP
	 * @param dealpwd
	 *            交易密码
	 * @param mobilePhone
	 * @param rating
	 *            网站积分
	 * @param creditrating
	 * @param vipstatus
	 * @param vipcreatetime
	 * @param creditlimit
	 * @param authstep
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addUser(String email, String userName, String password,
			String refferee, String lastDate, String lastIP, String dealpwd,
			String mobilePhone, Integer rating, Integer creditrating,
			Integer vipstatus, String vipcreatetime, Integer creditlimit,
			Integer authstep, String headImg, Integer enable,
			Long servicePersonId) throws Exception {

		Connection conn = MySQL.getConnection();
		long userId = -1L;
		try {
			// 得到信息额度类型
			Map<String, String> map = shoveBorrowAmountTypeDao
					.queryBorrowAmountByNid(conn, "credit");
			double creditLimit = Convert.strToDouble(map.get("creditLimit"), 0);
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit);

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
	 * 添加认证图片
	 * 
	 * @param materAuthTypeId
	 * @param imgPath
	 * @param auditStatus
	 * @param userId
	 * @param authTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addImage(long materAuthTypeId, String imgPath, long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long ImageId = -1L;
		try {
			ImageId = userDao.addImage(conn, materAuthTypeId, imgPath, userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return ImageId;
	}

	/**
	 * 用户注册(存储过程处理)
	 * 
	 * @param email
	 * @param username
	 * @param password
	 * @param refferee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
	public Long userRegister1(String email, String userName, String password,
			String refferee, Map<String, Object> userMap, int typeLen,
			String mobilePhone) throws Exception {
		Connection conn = MySQL.getConnection();
		int demo = Convert.strToInt(IConstants.ISDEMO, 2);

		Long ret = -1l;
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_user_register(conn, ds, outParameterValues, email,
					userName, password, refferee, demo, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);

			// userId = userDao.addUser(conn, email, userName, password,
			// refferee, lastDate, lastIP, dealpwd, mobilePhone, rating,
			// creditrating, vipstatus, vipcreatetime, authstep, headImg,
			// enable, servicePersonId, creditLimit);
			if (ret <= 0) {
				return ret;
			}
			if (userMap != null) {
				relationDao.addRelation(conn, ret, (Long) userMap
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

		return ret;
	}

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
	 * @throws DataException
	 */
	public Long userRegister(String email, String userName, String password,
			String refferee, Map<String, Object> userMap, int typeLen,
			String mobilePhone) throws Exception {
		Connection conn = MySQL.getConnection();
		String dealpwd = null; // 交易密码
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		String vipcreatetime = null;// VIP创建时间
		double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		// 系统给予默认头型
		Integer enable = 2; // 是否禁用 1、启用 2、禁用
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

		try {

			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit);
			if (userId <= 0) {
				return -1L;
			}
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				userDao.addMaterialsauth1(conn, userId, i);
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
	 * 手机用户注册
	 * 
	 * @param email
	 * @param userName
	 * @param password
	 * @param refferee
	 * @param userMap
	 * @param typeLen
	 * @param mobilePhone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long userAppRegister(String email, String userName, String password,
			String refferee,String src,String activity, Map<String, Object> userMap, int typeLen,
			String mobilePhone,long recommendUserId) throws Exception {
		Connection conn = MySQL.getConnection();
		String dealpwd = null; // 交易密码
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
		// 系统给予默认头型
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

		try {
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit);
			if (userId <= 0) {
				return -1L;
			}
			personDao.addPerson(conn, null, mobilePhone, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, userId, null, null, null,
					null,src,activity);
			myHomeInfoSettingDao.addBindingMobile(conn,
					mobilePhone, userId, IConstants.PHONE_BINDING_ON,
					"手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				userDao.addMaterialsauth1(conn, userId, i);
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
					userDao.addSendTyj(conn, userId, amount);//注册人发送体验金
					 
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
	/**
	 * 手机用户注册
	 * 
	 * @param email
	 * @param userName
	 * @param password
	 * @param refferee
	 * @param userMap
	 * @param typeLen
	 * @param mobilePhone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long userAppRegisters(String email, String userName, String password,
			String refferee,String src,String activity, Map<String, Object> userMap, int typeLen,
			String mobilePhone,long recommendUserId,int regSrc) throws Exception {
		Connection conn = MySQL.getConnection();
		String dealpwd = null; // 交易密码
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
		// 系统给予默认头型
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

		try {
			userId = userDao.addUser2(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit,regSrc);
			if (userId <= 0) {
				return -1L;
			}
			personDao.addPerson(conn, null, mobilePhone, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, userId, null, null, null,
					null,src,activity);
			myHomeInfoSettingDao.addBindingMobile(conn,
					mobilePhone, userId, IConstants.PHONE_BINDING_ON,
					"手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				userDao.addMaterialsauth1(conn, userId, i);
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
					userDao.addSendTyj(conn, userId, amount);//注册人发送体验金
					 
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

	/**
	 * 投资人填写个人信息
	 * 
	 * @param realName
	 * @param cellPhone
	 * @param sex
	 * @param birthday
	 * @param highestEdu
	 * @param eduStartDay
	 * @param school
	 * @param maritalStatus
	 * @param hasChild
	 * @param hasHourse
	 * @param hasHousrseLoan
	 * @param hasCar
	 * @param hasCarLoan
	 * @param nativePlacePro
	 * @param nativePlaceCity
	 * @param registedPlacePro
	 * @param registedPlaceCity
	 * @param address
	 * @param telephone
	 * @param personalHead
	 * @param userId
	 * @param idNo
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long updateUserBaseTT(String realName, String cellPhone, String sex,
			String birthday, String highestEdu, String eduStartDay,
			String school, String maritalStatus, String hasChild,
			String hasHourse, String hasHousrseLoan, String hasCar,
			String hasCarLoan, Long nativePlacePro, Long nativePlaceCity,
			Long registedPlacePro, Long registedPlaceCity, String address,
			String telephone, String personalHead, Long userId, String idNo)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userDao.updateUserBaseWWc(conn, realName, cellPhone,
					sex, birthday, highestEdu, eduStartDay, school,
					maritalStatus, hasChild, hasHourse, hasHousrseLoan, hasCar,
					hasCarLoan, nativePlacePro, nativePlaceCity,
					registedPlacePro, registedPlaceCity, address, telephone,
					personalHead, userId, idNo);
			// add by houli
			// add by houli 如果个人信息填写成功，将填写的手机号码同步到T_PHONE_BINDING_INFO表中
			Map<String, String> p_map = myHomeInfoSettingDao
					.queryBindingInfoByUserId(conn, userId, -1, -1);
			Long result1 = -1L;
			if (p_map == null || p_map.size() <= 0) {// 如果没有记录则插入手机绑定数据，否则进行更新
				result1 = myHomeInfoSettingDao.addBindingMobile(conn,
						cellPhone, userId, IConstants.PHONE_BINDING_ON,
						"个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
			} else {
				result1 = myHomeInfoSettingDao.updateBindingMobile(conn,
						cellPhone, userId, IConstants.PHONE_BINDING_ON,
						"个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
			}
			if (result1 <= 0) {
				conn.rollback();
				return -1L;
			}
			// end
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}

	/**
	 * 存储过程添加或更新用户基本资料
	 * 
	 * @param realName
	 * @param cellPhone
	 * @param sex
	 * @param birthday
	 * @param highestEdu
	 * @param eduStartDay
	 * @param school
	 * @param maritalStatus
	 * @param hasChild
	 * @param hasHourse
	 * @param hasHousrseLoan
	 * @param hasCar
	 * @param hasCarLoan
	 * @param nativePlacePro
	 * @param nativePlaceCity
	 * @param registedPlacePro
	 * @param registedPlaceCity
	 * @param address
	 * @param telephone
	 * @param personalHead
	 * @param userId
	 * @param idNo
	 * @param num
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> updateUserBaseData1(String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone,
			String personalHead, Long userId, String idNo, String num)
			throws Exception {
		Connection conn = MySQL.getConnection();
		conn.setAutoCommit(false);// 后面添加这么一句即可,设置无法自动提交,手动提交
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		Map<String, String> user = new HashMap<String, String>();
		user = userDao.queryUserById(conn, userId);
		String userName = Convert.strToStr(user.get("username"), "");
		String lastip = Convert.strToStr(user.get("lastIP"), "");
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_userInfo_update(conn, ds, outParameterValues,
					realName, cellPhone, sex, birthday, highestEdu,
					eduStartDay, school, maritalStatus, hasChild, hasHourse,
					hasHousrseLoan, hasCar, hasCarLoan, nativePlacePro,
					nativePlaceCity, registedPlacePro, registedPlaceCity,
					address, telephone, personalHead, userId, idNo, userName,
					lastip, num, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret < 0) {
				conn.rollback();
				return map;
			}
			
			//判断该用户是否为小贷公司的贷款用户 , 如果是，则不用审核-默认通过--认证信息
			List list= userDao.queryAminUserdata(conn, userId);
			long m = -1L;
			long n = -1L;
			if(list!=null && list.size()>0){
				//更新用户认证状态
				m = userDao.updateUserBaseDataCheck(conn, userId,3);// 更新用户的工作信息认证审核状态
				// 更新用户绑定手机状态
				n = beVipDao.updatePhoneBanding(conn, userId,1);
				
				if(m<0 || n<0){
					map.put("ret",-1+"");
					conn.rollback();
					return map;
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

		return map;
	}
	
	public Long addLianlianOrderRz(long order_id,Date date,long user_id,String content ) throws Exception {
		
		long m = 0;
		Connection conn = MySQL.getConnection();
		try {
			
			m = userDao.addLianlianOrderRz(conn, order_id, date,user_id,content);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			conn.close();
		}
		return m;
	}
	
	public List<Map<String,Object>> searchLianlianOrderRz(String user_id) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> listMap = null;
		try {
			listMap = userDao.searchLianlianOrderRz(conn, user_id);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally{
			conn.close();
		}
		return listMap;
	}
	
	/**
	 * 后台发标更新用户资料
	 * updateUserBaseData2
	 * @param realName
	 * @param cellPhone
	 * @param sex
	 * @param birthday
	 * @param highestEdu
	 * @param eduStartDay
	 * @param school
	 * @param maritalStatus
	 * @param hasChild
	 * @param hasHourse
	 * @param hasHousrseLoan
	 * @param hasCar
	 * @param hasCarLoan
	 * @param nativePlacePro
	 * @param nativePlaceCity
	 * @param registedPlacePro
	 * @param companyLine
	 * @param address
	 * @param telephone
	 * @param personalHead
	 * @param userId
	 * @param idNo
	 * @param num
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-6 下午03:29:02
	 */
	public Map<String, String> updateUserBaseData2(String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long workPro,
			Long workCity, String companyType,
			String companyLine, String companyScale, String job, Long userId, String idNo, String num,long applyId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		conn.setAutoCommit(false);// 后面添加这么一句即可,设置无法自动提交,手动提交
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		Map<String, String> user = new HashMap<String, String>();
		//user = userDao.queryUserById(conn, userId);
		String userName ="";
		String lastip = "";
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_userInfo_update_admin(conn, ds, outParameterValues,
					realName, cellPhone, sex, birthday, highestEdu,
					eduStartDay, school, maritalStatus, hasChild, hasHourse,
					hasHousrseLoan, hasCar, hasCarLoan, workPro,
					workCity, companyType, companyLine,
					companyScale, job, userId, idNo, userName,
					lastip, num, applyId,-1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret < 0) {
				conn.rollback();
				return map;
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

		return map;
	}
	
	/**
	 * 更新企业基本信息
	 * updateEnterpriseUserBaseData
	 * @param userId
	 * @param companyName
	 * @param legalPerson
	 * @param registeredTime
	 * @param registeredCapital
	 * @param businessCode
	 * @param companyAddress
	 * @param companyPhone
	 * @param borrowCause
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-9 下午02:21:49
	 */
	public Map<String, String> updateEnterpriseUserBaseData(long userId,String companyName,String legalPerson,String registeredTime,
			 double registeredCapital, String businessCode, String companyAddress,
			String companyPhone, String borrowCause,long applyId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		conn.setAutoCommit(false);// 后面添加这么一句即可,设置无法自动提交,手动提交
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		Map<String, String> user = new HashMap<String, String>();
		//user = userDao.queryUserById(conn, userId);
		String userName ="";
		String lastip = "";
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_enterprise_userInfo_update(conn, ds, outParameterValues,
					userId,companyName, legalPerson, registeredTime, registeredCapital, businessCode,
					companyAddress, companyPhone, borrowCause,applyId,-1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret < 0) {
				conn.rollback();
				return map;
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

		return map;
	}
	//更新某一项基本资料（姓名和身份证）
	public Long updateUserBaseDataOne(String reallyName,String idNo,Long userId) throws Exception{
		long ret = -1;
		Connection conn = MySQL.getConnection();
		try {
			ret = userDao.updateUserBaseDataOne(conn,reallyName,idNo,userId);
			List list= userDao.queryAminUserdata(conn, userId);
			long m = -1L;
			long n = -1L;
			if(list!=null && list.size()>0){
				//更新用户认证状态
				m = userDao.updateUserBaseDataCheck(conn, userId,3);
				// 更新用户绑定手机状态
				n = beVipDao.updatePhoneBanding(conn, userId,1);
				
				if(m>0 && n>0 && ret>0){
					conn.commit();
				}else{
					conn.rollback();
				}
			}else if(ret >0){
				conn.commit();
			}else{
				conn.rollback();
			}
		}catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		
		return ret;
	}
	/**
	 * 更新用户基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserBaseData(String realName, String cellPhone,
			String sex, String birthday, String highestEdu, String eduStartDay,
			String school, String maritalStatus, String hasChild,
			String hasHourse, String hasHousrseLoan, String hasCar,
			String hasCarLoan, Long nativePlacePro, Long nativePlaceCity,
			Long registedPlacePro, Long registedPlaceCity, String address,
			String telephone, String personalHead, Long userId, String idNo,int lian_state)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> user = new HashMap<String, String>();
		long personId = -1L;
		try {
			user = userDao.queryUserById(conn, userId);
			personId = userDao.updateUserBaseData(conn, lian_state,realName, cellPhone,
					sex, birthday, highestEdu, eduStartDay, school,
					maritalStatus, hasChild, hasHourse, hasHousrseLoan, hasCar,
					hasCarLoan, nativePlacePro, nativePlaceCity,
					registedPlacePro, registedPlaceCity, address, telephone,
					personalHead, userId, idNo, Convert.strToStr(user
							.get("username"), ""), Convert.strToStr(user
							.get("lastIP"), ""));
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			}

			// add by houli
			// add by houli 如果个人信息填写成功，将填写的手机号码同步到T_PHONE_BINDING_INFO表中
			Map<String, String> p_map = myHomeInfoSettingDao
					.queryBindingInfoByUserId(conn, userId, -1, -1);
			Long result1 = -1L;
			if (p_map == null || p_map.size() <= 0) {// 如果没有记录则插入手机绑定数据，否则进行更新
				result1 = myHomeInfoSettingDao.addBindingMobile(conn,
						cellPhone, userId, IConstants.PHONE_BINDING_ON,
						"个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
			} else {
				result1 = myHomeInfoSettingDao.updateBindingMobile(conn,
						cellPhone, userId, IConstants.PHONE_BINDING_ON,
						"个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE,null);
			}
			if (result1 <= 0) {
				conn.rollback();
				return -1L;
			}
			// end
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;

	}

	/**
	 * 审核用户基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserBaseDataCheck(long userId, int auditStatus,
			Long adminId) throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer msg = new StringBuffer();
		long personId = -1L;
		try {
			personId = userDao.updateUserBaseDataCheck(conn, userId,
					auditStatus);// 更新用户的工作信息认证审核状态

			if (personId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				int phoneStatus = 2;// 默认审核中
				if (auditStatus == 2) {// 失败
					phoneStatus = 4;// bangding 失败
				} else if (auditStatus == 3) {
					phoneStatus = 1;// 审核成功
				}
				beVipDao.updatePhoneBanding(conn, userId, phoneStatus);
				// 更新用户绑定手机状态
				personId = beVipDao.updatePhoneBanding(conn, userId,
						phoneStatus);
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				} else {

					// 发站内信
					String m = "";
					if (auditStatus == 2) {
						m = "不通过";
					} else if (auditStatus == 3) {
						m = "通过";
					}
					msg.append("您的基本信息审核状况:");
					msg.append(m);
					// 发站内信
					personId = sendmsgService.sendCheckMail(conn,userId,
							" 基本信息审核通知", msg.toString(), 2, adminId);// 2管理员信件
					if (personId <= 0) {
						conn.rollback();
						return -1L;
					}
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

		return personId;
	}

	/**
	 * 该用户上传资料的类型的审核状态
	 * 
	 * @param id
	 * @param materAuthTypeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserPicturStatus(Long id, Long materAuthTypeId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userDao
					.updateUserPicturStatus(conn, id, materAuthTypeId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}

	/**
	 * 增加用户的图片
	 * 
	 * @param auditStatus
	 * @param uploadingTime
	 * @param imagePath
	 * @param materialsauthid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addUserImage(Integer auditStatus, String uploadingTime,
			List<Long> lists, List<String> imgListsy, List<String> imgListsn,
			Long materialsauthid, Long id, Long materAuthTypeId, Long tmid,
			int addCount) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long personId = -1L;
		Long userId = -1L;
		Long userIdauthd = -1L;
		try {
			// 将用户选择可见的设置为可见
			// if(lists.size()>0&&lists!=null){
			personId = userDao.updatematerialImagedetalvisiable(conn,
					materialsauthid);// 将重置图片的可见性 设置为不可见
			for (Long list : lists) {
				personId = userDao.updatevisiable(conn, list);// 根据传来的id集合重新设置哪个可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			
			/***************** 判断用户是否为      小贷公司的 前台  贷款账户   ***************************/
			List list= userDao.queryAminUserdata(conn, id);
			if(list!=null && list.size()>0){
				// 插于图片为可见的
				for (String vimg : imgListsy) {// 遍历集合
					personId = userDao.addUserImage(conn, 3,uploadingTime, vimg, materialsauthid, 1);// t_materialImagedetal添加图片
					if (personId <= 0) {
						conn.rollback();
						return -1L;
					}

				}
				// 插于图片为不可见的
				for (String vimg : imgListsn) {// 遍历集合
					personId = userDao.addUserImage(conn, 3,uploadingTime, vimg, materialsauthid, 2);// t_materialImagedetal添加图片
					if (personId <= 0) {
						conn.rollback();
						return -1L;
					}
				}
				 
				Map<String,Object> map = (Map<String, Object>) list.get(0);
				long adminId = (Long) map.get("adminId");//小贷公司超级管理员ID
				//直接总审核
				long resultId = vidateDao.updateSatues(conn, id, materAuthTypeId);
				if(resultId>0){
					vidateDao.addCheckRecord(conn, 0, adminId, id, Integer.parseInt(String.valueOf(materAuthTypeId)), 0);
				}
				
				// 更新user authod表中的状态
				userIdauthd = userDao.updateUserauthodLoanUser(conn, id);// 当5大基本资料上传完成后更新用户的认证步骤
				if (userIdauthd <= 0) {
					conn.rollback();
					return -1L;
				}
			
			}else{
			// 插于图片为可见的
			for (String vimg : imgListsy) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus,
						uploadingTime, vimg, materialsauthid, 1);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}

			}
			// 插于图片为不可见的
			for (String vimg : imgListsn) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus,
						uploadingTime, vimg, materialsauthid, 2);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			// --------modify by houli 如果未新添加上传图片，那么不修改总审核状态
			if (addCount > 0) {
				userId = userDao.updateUserPicturStatus(conn, id,
						materAuthTypeId);// 更新用户总证件状态
				if (userId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			
			// 更新user authod表中的状态
			userIdauthd = userDao.updateUserauthod(conn, id);// 当5大基本资料上传完成后更新用户的认证步骤
			if (userIdauthd <= 0) {
				conn.rollback();
			    return -1L;
			}
			}		
			userMap = userDao.queryUserById(conn, id);
			operationLogDao.addOperationLog(conn, "t_materialimagedetal",
					Convert.strToStr(userMap.get("username"), ""),
					IConstants.UPDATE, Convert.strToStr(userMap.get("lastIP"),
							""), 0, "上传图片", 1);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}
	
	/**
	 * 后台发标增加用户的图片
	 * addUserImageAdmin
	 * @param auditStatus
	 * @param uploadingTime
	 * @param lists
	 * @param imgListsy
	 * @param imgListsn
	 * @param materialsauthid
	 * @param id
	 * @param materAuthTypeId
	 * @param tmid
	 * @param addCount
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-7 上午10:40:40
	 */
	public Long addUserImageAdmin(Integer auditStatus, String uploadingTime,
			List<Long> lists, List<String> imgListsy, List<String> imgListsn,
			Long materialsauthid, Long id, Long materAuthTypeId, Long tmid,
			int addCount,int type) throws Exception {
		Connection conn = MySQL.getConnection();
		//Map<String, String> userMap = new HashMap<String, String>();
		long personId = -1L;
		Long userId = -1L;
		Long userIdauthd = -1L;
		try {
			// 将用户选择可见的设置为可见
			// if(lists.size()>0&&lists!=null){
			personId = userDao.updatematerialImagedetalvisiable(conn,
					materialsauthid);// 将重置图片的可见性 设置为不可见
			for (Long list : lists) {
				personId = userDao.updatevisiable(conn, list);// 根据传来的id集合重新设置哪个可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			// 插于图片为可见的
			for (String vimg : imgListsy) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus,
						uploadingTime, vimg, materialsauthid, 1);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}

			}
			// 插于图片为不可见的
			for (String vimg : imgListsn) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus,
						uploadingTime, vimg, materialsauthid, 1);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}// --------modify by houli 如果未新添加上传图片，那么不修改总审核状态
			if (addCount > 0) {
				userId = userDao.updateUserPicturStatus(conn, id,
						materAuthTypeId);// 更新用户总证件状态
				if (userId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			userId = userDao.updateUserPicturStatusAdmin(conn, id, materAuthTypeId);// 更新用户总证件状态
			if (userId <= 0) {
				conn.rollback();
				return -1L;
			}
			// 更新user authod表中的状态
			userIdauthd = userDao.updateUserauthodAdmin(conn, id,type);// 当5大基本资料上传完成后更新用户的认证步骤
			if (userIdauthd <= 0) {
				conn.rollback();
				return -1L;
			}
			//userMap = userDao.queryUserById(conn, id);
			operationLogDao.addOperationLog(conn, "t_materialimagedetal",
					Convert.strToStr("admin", ""),
					IConstants.UPDATE, Convert.strToStr("",
							""), 0, "上传图片", 1);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}

	/**
	 * 更新用户上传图片的可见性
	 * 
	 * @param tmdid
	 *            //主图片类型下的图片明细id
	 * @param tmid
	 *            //主图片类型id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updatevisiable(Long tmid, List<Long> lists) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			long personId = -1L;
			Long resetvisable = -1L;

			resetvisable = userDao.updatematerialImagedetalvisiable(conn, tmid);// 将重置图片的可见性
			// 重置为不可见
			if (resetvisable <= 0) {
				conn.rollback();
				return -1L;
			}
			for (Long list : lists) {
				personId = userDao.updatevisiable(conn, list);// 根据传来的id集合重新设置哪个可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
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

		return -1L;
	}

	/**
	 * 添加用户的基础资料
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Long userBaseData(String realName, String cellPhone, String sex,
			String birthday, String highestEdu, String eduStartDay,
			String school, String maritalStatus, String hasChild,
			String hasHourse, String hasHousrseLoan, String hasCar,
			String hasCarLoan, Long nativePlacePro, Long nativePlaceCity,
			Long registedPlacePro, Long registedPlaceCity, String address,
			String telephone, String personalHead, Long userId, String idNo)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		long uId = -1L;
		try {
			personId = userDao.addUserBaseData(conn, realName, cellPhone, sex,
					birthday, highestEdu, eduStartDay, school, maritalStatus,
					hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
					nativePlacePro, nativePlaceCity, registedPlacePro,
					registedPlaceCity, address, telephone, personalHead,
					userId, idNo);

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}

	/**
	 * 添加用户工作认证信息
	 * 
	 * @throws SQLException
	 */
	public Long addUserWorkData(String orgName, String occStatus, Long workPro,
			Long workCity, String companyType, String companyLine,
			String companyScale, String job, String monthlyIncome,
			String workYear, String companyTel, String workEmail,
			String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long workDataId = -1L;
		try {
			workDataId = userDao.addUserWorkData(conn, orgName, occStatus,
					workPro, workCity, companyType, companyLine, companyScale,
					job, monthlyIncome, workYear, companyTel, workEmail,
					companyAddress, directedName, directedRelation,
					directedTel, otherName, otherRelation, otherTel, moredName,
					moredRelation, moredTel, userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return workDataId;
	}

	/**
	 * 修改用户工作认证信息
	 * 
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> updateUserWorkData1(String orgName,
			String occStatus, Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId,
			Integer vipStatus, Integer newutostept) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
//		long workDataId = -1L;
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		try {
			userMap = userDao.queryUserById(conn, userId);
			String lastip = Convert.strToStr(userMap.get("lastIP"), "");

			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_userWorkInfo_update(conn, ds, outParameterValues,
					orgName, occStatus, workPro, workCity, companyType,
					companyLine, companyScale, job, monthlyIncome, workYear,
					companyTel, workEmail, companyAddress, directedName,
					directedRelation, directedTel, otherName, otherRelation,
					otherTel, moredName, moredRelation, moredTel, userId,
					vipStatus, newutostept, lastip, -1, "");

			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");

			if (ret <= 0) {
				conn.rollback();
				return map;
			}
			
			//小贷公司的  贷款前台账户 的工作认证信息和联系人认证信息   不需要后台审核
			List list= userDao.queryAminUserdata(conn, userId);
			long state = -1L;
			 
			if(list!=null && list.size()>0){
				long workauthId = (Long) userDao.queryWorkId(conn, userId).get(0).get("id");
				state = vidateDao.updateworkStatus(conn, userId, workauthId,3, 3, 3, 3);
				if(state<0){
					map.put("ret",-1l+"");
					conn.rollback();
					return map;
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

		return map;
	}

	public Long updateUserWorkData(String orgName, String occStatus,
			Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId,
			Integer vipStatus, Integer newutostept) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long workDataId = -1L;
		try {
			workDataId = userDao.updateUserWorkData(conn, orgName, occStatus,
					workPro, workCity, companyType, companyLine, companyScale,
					job, monthlyIncome, workYear, companyTel, workEmail,
					companyAddress, directedName, directedRelation,
					directedTel, otherName, otherRelation, otherTel, moredName,
					moredRelation, moredTel, userId);

			userMap = userDao.queryUserById(conn, userId);
			workDataId = operationLogDao.addOperationLog(conn, "t_workauth",
					Convert.strToStr(userMap.get("username"), ""),
					IConstants.INSERT, Convert.strToStr(userMap.get("lastIP"),
							""), 0.00, "填写工作认证信息", 1);
			if (workDataId <= 0) {
				conn.rollback();
				return -1L;
			}

			// 跟新用户的认证步骤
			if (newutostept == 2) {
				workDataId = beVipDao.updateUserAustep(conn, userId, 3);// 3为填写完了工作信息的认证步骤
				if (workDataId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			if (vipStatus != 1) {// 如果此时用户的vip状态为会员 那么要更新user的认证步骤
				if (newutostept <= 3) {
					workDataId = beVipDao.updateUserAustep(conn, userId, 4);// 4为填写完了vip的认证步骤
					if (workDataId <= 0) {
						conn.rollback();
						return -1L;
					}
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

		return workDataId;
	}

	/**
	 * 判断重复登录
	 * 
	 * @param email
	 * @param userName
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long isExistEmailORUserName(String email, String userName)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUserEmaiORUseName(conn, email, userName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
	}

	// =====================================
	/**
	 * 用户登录时候验证邮箱和用户名是否激活
	 */
	public Long isUEjihuo(String email, String userName) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUEjihuo(conn, email, userName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
	}

	/**
	 * 用户登录时候验证邮箱和用户名是否激活
	 */
	public Long isUEjihuo_(String email, String userName) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUEjihuo_(conn, email, userName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
	}

	// =========================================

	/**
	 * 用户登录处理
	 * 
	 * 
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @param password
	 *            用户密码
	 * @param lastIP
	 *            最后登录ip
	 * @param loginType
	 *            登录类型，1用户名或邮箱登录，
	 * @return User
	 * @throws SQLException
	 * @throws DataException
	 */
	public User userLogin1(String userName, String password, String lastIP,
			String lastTime) throws Exception {
		Connection conn = MySQL.getConnection();
		password = StringEscapeUtils.escapeSql(password.trim());
		if ("1".equals(IConstants.ENABLED_PASS)) {
			password = com.shove.security.Encrypt.MD5(password.trim());
		} else {
			password = com.shove.security.Encrypt.MD5(password.trim()
					+ IConstants.PASS_KEY);
		}

		User user = null;
		Long userid = -1L;
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			List<Map<String, Object>> pmap = new ArrayList<Map<String, Object>>();
			Map<String, String> umap = new HashMap<String, String>();
			umap = userDao.queryUserByUserName(conn, userName);
			if (null == umap) {
				//如果是用手机号登录的情况时，umap为空
				pmap = userDao.isPhoneExist(conn,userName);
				
				if(pmap != null && pmap.size() > 0){
					umap = userDao.queryUserById(conn, Convert.strToLong(pmap.get(0).get("userId").toString(), -1) );
				}else{
					return null;
				}
			}
			int id = Convert.strToInt(umap.get("id"), -1);
			String lastLoginTime = Convert.strToStr(umap.get("lastDate"), null);
			SimpleDateFormat simple = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			if (null != lastLoginTime) {
				Date loginTime = simple.parse(lastLoginTime);
				if (new Date().getTime() - loginTime.getTime() > 3 * 60 * 60 * 1000) {
					// 取消用户限制登录将isLoginLimit设置为1和loginErrorCount设置为0
					userDao.resetUserState(conn, 0, 1, id);
				}
			}
			umap = userDao.queryUserById(conn, id);
			int isLoginLimit = Convert.strToInt(umap.get("isLoginLimit"), 1);
			int loginErrorCount = Convert.strToInt(umap.get("loginErrorCount"),
					0);
			if (loginErrorCount == 3 || isLoginLimit == 2) {
				// 设置用户限制登录，设置loginErrorCount为0
				userDao.resetUserState(conn, 0, 2, id);
				conn.commit();
				user = new User();
				user.setIsLoginLimit(2);
				return user;
			}
			Procedures.p_user_login(conn, ds, outParameterValues, userName,
					password, lastIP, -1, "");
			userid = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (userid <= 0) {
				if (userid == -4) {
					int count = loginErrorCount + 1;
					// 更新用户错误登录次数和登陆时间
					userDao.updateUserState(conn, count, lastTime, id);
					conn.commit();
				}
				if (userid == -5) {
					user = new User();
					user.setEnable(2);
					return user;
				} 				
				return null;
			}
			userDao.resetUserState(conn, 0, 1, userid);
			Map<String, String> usermap = new HashMap<String, String>();
			usermap = userDao.queryUserById(conn, userid);

			Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();
			DataSet dataSet = sessionuser.open(conn, "", " id=" + userid, "",
					-1, -1);
			Map<String, String> sessionmap = new HashMap<String, String>();
			sessionmap = BeanMapUtils.dataSetToMap(dataSet);
			if (sessionmap != null && sessionmap.size() > 0) {
				user = new User();
				user.setAuthStep(Convert.strToInt(sessionmap.get("authStep"),
						-1));
				user.setEmail(Convert.strToStr(sessionmap.get("email"), null));
				user.setPassword(Convert.strToStr(sessionmap.get("password"),
						null));
				user.setDealpwd(Convert.strToStr(sessionmap.get("dealpwd"),
						null));
				user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
				user.setRealName(Convert.strToStr(sessionmap.get("realName"),
						null));
				user.setKefuname(Convert.strToStr(sessionmap.get("kefuname"),
						null));
				user.setUserName(Convert.strToStr(sessionmap.get("username"),
						null));
				user.setCreateTime(Convert.strToStr(sessionmap.get("createTime"),
						null));
				user.setVipStatus(Convert.strToInt(sessionmap.get("vipStatus"),
						-1));
				user.setHeadImage(Convert
						.strToStr(usermap.get("headImg"), null));
				user.setEnable(Convert.strToInt(usermap.get("enable"), -1));
				user.setPersonalHead(Convert.strToStr(sessionmap
						.get("personalHead"), null));
				user.setKefuid(Convert.strToInt(sessionmap.get("tukid"), -1));
				user.setCreditLimit(Convert.strToStr(sessionmap
						.get("usableCreditLimit"), null));
				user.setUsableSum(Convert.strToStr(usermap.get("usableSum"), "0.00"));
				user.setPhoneStatus(Convert.strToInt(sessionmap.get("phoneStatus"), 2));

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
		return user;
	}

	/**
	 * 用户登录处理
	 * 
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @param password
	 *            用户密码
	 * @param lastIP
	 *            最后登录ip
	 * @param lastTime
	 *            最后登录时间
	 * @param loginType
	 *            登录类型，1用户名或邮箱登录，
	 * @return User
	 * @throws SQLException
	 * @throws DataException
	 */
	public User userLogin(String userName, String password, String lastIP,
			String lastTime) throws Exception {
		Connection conn = MySQL.getConnection();
		User user = null;
		try {
			password = StringEscapeUtils.escapeSql(password.trim());
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}
			Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
			DataSet ds = t_user
					.open(
							conn,
							"id,username,headImg,enable,vipStatus,email,authStep,lastIP ",
							"(email ='"
									+ StringEscapeUtils.escapeSql(userName
											.trim())
									+ "' OR username='"
									+ StringEscapeUtils.escapeSql(userName
											.trim())
									+ "' OR mobilePhone='"
									+ StringEscapeUtils.escapeSql(userName
											.trim()) + "') AND password = '"
									+ password + "' AND enable != 2", "", -1,
							-1);
			int returnId = ds.tables.get(0).rows.getCount();
			if (returnId <= 0) {
				return null;
			} else {
				user = new User();
				DataRow dr = ds.tables.get(0).rows.get(0);

				// ======
				Map<String, String> sessionmap = new HashMap<String, String>();

				Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

				DataSet dataSet = sessionuser.open(conn, "", " id="
						+ (Long) (dr.get("id") == null ? -1l : dr.get("id")),
						"", -1, -1);
				sessionmap = BeanMapUtils.dataSetToMap(dataSet);

				if (sessionmap != null && sessionmap.size() > 0) {
					user = new User();
					user.setAuthStep(Convert.strToInt(sessionmap
							.get("authStep"), -1));
					user.setEmail(Convert.strToStr(sessionmap.get("email"),
							null));
					user.setPassword(Convert.strToStr(sessionmap
							.get("password"), null));
					user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
					user.setRealName(Convert.strToStr(sessionmap
							.get("realName"), null));
					user.setKefuname(Convert.strToStr(sessionmap
							.get("kefuname"), null));
					user.setUserName(Convert.strToStr(sessionmap
							.get("username"), null));
					user.setVipStatus(Convert.strToInt(sessionmap
							.get("vipStatus"), -1));
					user.setHeadImage((String) (dr.get("headImg") == null ? ""
							: dr.get("headImg")));
					user.setEnable((Integer) (dr.get("enable") == null ? -1
							: dr.get("enable")));
					user.setPersonalHead(Convert.strToStr(sessionmap
							.get("personalHead"), null));
					user.setKefuid(Convert
							.strToInt(sessionmap.get("tukid"), -1));
					user.setCreditLimit(Convert.strToStr(sessionmap
							.get("usableCreditLimit"), null));
					user.setUsableSum(Convert.strToStr(sessionmap.get("usableSum"), "0.00"));
					user.setPhoneStatus(Convert.strToInt(sessionmap.get("phoneStatus"), 2));
				}
				if (StringUtils.isNotBlank(lastIP)) {
					t_user.lastDate.setValue(lastTime);
					t_user.lastIP.setValue(lastIP);
					t_user.update(conn, " id=" + user.getId());
				}

			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return user;
	}

	/**
	 * 虚拟用户登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public User userVirtualLogin(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		User user = null;

		try {
			Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
			DataSet ds = t_user
					.open(
							conn,
							"id  ,username ,headImg  ,enable  ,vipStatus  ,email ,authStep  ",
							" id=" + id, "", -1, -1);
			int returnId = ds.tables.get(0).rows.getCount();
			if (returnId <= 0) {
				return null;
			} else {
				user = new User();
				DataRow dr = ds.tables.get(0).rows.get(0);

				// ======
				Map<String, String> sessionmap = new HashMap<String, String>();

				Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

				DataSet dataSet = sessionuser.open(conn, "", " id ="
						+ (dr.get("id") == null ? -1l : dr.get("id")), "", -1,
						-1);
				sessionmap = BeanMapUtils.dataSetToMap(dataSet);

				if (sessionmap != null && sessionmap.size() > 0) {
					user = new User();
					user.setAuthStep(Convert.strToInt(sessionmap
							.get("authStep"), -1));
					user.setEmail(Convert.strToStr(sessionmap.get("email"),
							null));
					user.setPassword(Convert.strToStr(sessionmap
							.get("password"), null));
					user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
					user.setRealName(Convert.strToStr(sessionmap
							.get("realName"), null));
					user.setKefuname(Convert.strToStr(sessionmap
							.get("kefuname"), null));
					user.setUserName(Convert.strToStr(sessionmap
							.get("username"), null));
					user.setVipStatus(Convert.strToInt(sessionmap
							.get("vipStatus"), -1));
					user.setHeadImage((String) (dr.get("headImg") == null ? ""
							: dr.get("headImg")));
					user.setEnable(Convert.strToInt(dr.get("enable") + "", -1));
					user.setPersonalHead(Convert.strToStr(sessionmap
							.get("personalHead"), null));
					user.setKefuid(Convert
							.strToInt(sessionmap.get("tukid"), -1));
					user.setCreditLimit(Convert.strToStr(sessionmap
							.get("usableCreditLimit"), null));
					user.setUsableSum(Convert.strToStr(sessionmap.get("usableSum"), "0.00"));
					user.setPhoneStatus(Convert.strToInt(sessionmap.get("phoneStatus"), 2));
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return user;
	}

	/**
	 * @MethodName: loginCountReFresh
	 * @Param: UserService
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午10:34:45
	 * @Return:
	 * @Descb: 刷新登录计数
	 * @Throws:
	 */
	public void loginCountReFresh(long userId) throws Exception {
		if (userId > -1) {
			Connection conn = MySQL.getConnection();
			try {
				MySQL.executeNonQuery(conn,
						" update t_user set loginCount = loginCount + 1 where id="
								+ userId);
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

	// =================登陆中初始化LoginVerify
	public LoginVerify getLoginVerify(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		LoginVerify loginVerify = null;
		Map<String, String> spmap = new HashMap<String, String>();
		Map<String, String> vpmap = new HashMap<String, String>();
		try {
			Dao.Views.v_t_user_loginsession_user sessond = new Dao().new Views().new v_t_user_loginsession_user();
			Dao.Views.v_t_login_session_verify verify = new Dao().new Views().new v_t_login_session_verify();
			DataSet sdataSet = sessond.open(conn, "", " id=" + userId, "", -1,
					-1);
			spmap = BeanMapUtils.dataSetToMap(sdataSet);
			DataSet vdataSet = verify.open(conn, "", " id=" + userId, "", -1,
					-1);
			vpmap = BeanMapUtils.dataSetToMap(vdataSet);
			if (spmap != null && spmap.size() > 0 && vpmap != null
					&& vpmap.size() > 0) {
				loginVerify = new LoginVerify();
				loginVerify.setJbStatus(Convert.strToInt(spmap
						.get("tpauditStatus"), -1));
				loginVerify.setAllworkjbStatus(Convert.strToInt(spmap
						.get("twsum"), -1));
				loginVerify.setIdentyVerifyStatus(Convert.strToInt(vpmap
						.get("identyauditStatus"), -1));
				loginVerify.setWorkVerifyStatus(Convert.strToInt(vpmap
						.get("workauditStatus"), -1));
				loginVerify.setAddressVerifyStatus(Convert.strToInt(vpmap
						.get("addryauditStatus"), -1));
				loginVerify.setResponseVerifyStatus(Convert.strToInt(vpmap
						.get("responsauditStatus"), -1));
				loginVerify.setIncomeVerifyStatus(Convert.strToInt(vpmap
						.get("incomeauditStatus"), -1));
				loginVerify.setFangchanVerifyStatus(Convert.strToInt(vpmap
						.get("fcyauditStatus"), -1));
				loginVerify.setGcVerifyStatus(Convert.strToInt(vpmap
						.get("gcauditStatus"), -1));
				loginVerify.setJhVerifyStatus(Convert.strToInt(vpmap
						.get("jhauditStatus"), -1));
				loginVerify.setXlVerifyStatus(Convert.strToInt(vpmap
						.get("xlauditStatus"), -1));
				loginVerify.setJsVerifyStatus(Convert.strToInt(vpmap
						.get("jsauditStatus"), -1));
				loginVerify.setSjVerifyStatus(Convert.strToInt(vpmap
						.get("sjauditStatus"), -1));
				loginVerify.setWbVerifyStatus(Convert.strToInt(vpmap
						.get("wbauditStatus"), -1));
				loginVerify.setSpVerifyStatus(Convert.strToInt(vpmap
						.get("spauditStatus"), -1));
				loginVerify.setXcVerifyStatus(Convert.strToInt(vpmap
						.get("xcauditStatus"), -1));
				loginVerify.setDbVerifyStatus(Convert.strToInt(vpmap
						.get("dbauditStatus"), -1));
				loginVerify.setDyVerifyStatus(Convert.strToInt(vpmap
						.get("dyauditStatus"), -1));
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return loginVerify;
	}

	public User jumpToWorkData(Long userId) throws Exception {
		// 更新user表中的认证状态
		User user = null;
		Connection conn = MySQL.getConnection();
		Map<String, String> sessionmap = new HashMap<String, String>();
		try {
			Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

			DataSet dataSet = sessionuser.open(conn, "", " id=" + userId, "",
					-1, -1);
			sessionmap = BeanMapUtils.dataSetToMap(dataSet);

			if (sessionmap != null && sessionmap.size() > 0) {
				user = new User();
				user.setAuthStep(Convert.strToInt(sessionmap.get("authStep"),
						-1));
				user.setEmail(Convert.strToStr(sessionmap.get("email"), null));
				user.setPassword(Convert.strToStr(sessionmap.get("password"),
						null));
				user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
				user.setRealName(Convert.strToStr(sessionmap.get("realName"),
						null));
				user.setKefuname(Convert.strToStr(sessionmap.get("kefuname"),
						null));
				user.setUserName(Convert.strToStr(sessionmap.get("username"),
						null));
				user.setVipStatus(Convert.strToInt(sessionmap.get("vipStatus"),
						-1));
				user.setKefuid(Convert.strToInt(sessionmap.get("tukid"), -1));

			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return user;

		// ===============================================================================

	}

	// 查询用户的最新状态
	public Map<String, String> querynewStatus(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> sessionmap = null;
		try {
			Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

			DataSet dataSet = sessionuser.open(conn, "", " id=" + userId, "",
					-1, -1);
			sessionmap = BeanMapUtils.dataSetToMap(dataSet);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return sessionmap;

		// ===============================================================================
	}

	/**
	 * 更新申请vip时候的步骤和状态
	 * 
	 * @param userId
	 * @param authStep
	 *            认证步骤
	 * @param vipStatus
	 *            会员状态
	 * @return User实体
	 * @throws Exception
	 */
	public Long updataUserVipStatus(Long userId, int authStep, int vipStatus,
			int servicePersonId, String content, String vipFee, String username)
			throws Exception {
		StringBuffer msg = new StringBuffer();
		long uId = -1L;
		Connection conn = MySQL.getConnection();
		try {
			uId = userDao.updateUser(conn, userId, authStep, vipStatus,
					servicePersonId, content, vipFee);
			if (uId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				// 发送站内信
				msg.append("尊敬的" + username + ",你申请vip成功");
				// 发站内信
				uId = sendmsgService.sendCheckMail(conn,userId, " 申请vip审核通知", msg
						.toString(), 2, -1);// 2管理员信件 -1 后台管理员
				if (uId <= 0) {
					conn.rollback();
					return -1L;
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
		return uId;
	}

	public Long frontVerificationEmial(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Long retut = -1L;

		user.enable.setValue(1);
		try {
			retut = user.update(conn, " id=" + userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return retut;
	}

	/**
	 * 处理前台用户修改头像
	 * 
	 * @param userId
	 *            用户id
	 * @param headImg
	 *            图片地址
	 * @return Long
	 * @throws SQLException
	 */
	public Long frontUpdateUserHeadImg(Long userId, String headImg)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Long retut = -1L;
		try {
			user.headImg.setValue(headImg);
			retut = user.update(conn, " id=" + userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return retut;
	}

	/**
	 * 处理前台用户修改密码
	 * 
	 * @param userId
	 *            用户id
	 * @param password
	 *            新密码
	 * @return Long
	 * @throws SQLException
	 */
	public Long frontUpdatePassword(Long userId, String password)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Long retut = -1L;
		try {
			user.password.setValue(password);
			retut = user.update(conn, " id=" + userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return retut;
	}

	/**
	 * 修改用户邮箱验证状态
	 * 
	 * @Title: updateUserEmailStatus
	 * @param id
	 *            用户ID
	 * @param status
	 *            标志邮箱是否验证通过 (0:未通过1:通过)
	 * @throws SQLException
	 * @return Long
	 */
	public Long updateUserEmailStatus(Long id, Integer status) throws Exception {
		Connection conn = MySQL.getConnection();
		long userId = -1;

		try {
			userId = userDao.updateUserEmailStatus(conn, id, status);
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
	 * 修改用户密码
	 * 
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserPassword(Long id, String password) throws Exception {
		Connection conn = MySQL.getConnection();
		long userId = -1;

		try {
			userId = userDao.updateUserPassword(conn, id, password);
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
	 * 根据用户id查询用户信息(连接死锁，修改完成)
	 * 
	 * @param id
	 * @throws DataException
	 * @throws SQLException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserById(Connection conn,long id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
			map = userDao.queryUserById(conn, id);
		return map;
	}
	
	public Long updateUniqueBankCardMark(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;

		try {
			result = userDao.updateUniqueBankCardMark(conn, id);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}


	/**
	 * 根据用户id查询用户信息(重载方法)
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryUserById(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map =queryUserById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	/**
	 * 查询用户的五大基本资料的计数
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryPicturStatuCount(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPicturStatuCount(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询前台上传图片的图片状态
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryUserPictureStatus(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserPictureStatus(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询用户前台五大基本资料信息和显示详细图片的第一张
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryBasePicture(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryBasePicture(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	/**
	 * 查询用户后台五大基本资料信息和显示详细图片的第一张
	 * queryBasePictureAdmin
	 * @param id
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-6 下午08:47:56
	 */
	public List<Map<String, Object>> queryBasePictureAdmin(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryBasePictureAdmin(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	/**
	 * 查询企业五项认证
	 * queryEnterprisePicture
	 * @param id
	 * @return
	 * @throws Exception
	 * @autthor linww
	 * 2014-6-9 下午03:07:40
	 */
	public List<Map<String, Object>> queryEnterprisePicture(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryEnterprisePicture(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	/**
	 * 查询用户前台可选大基本资料信息和显示详细图片的第一张
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> querySelectPicture(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.querySelectPicture(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	/**
	 * 查询用户后台台可选大基本资料信息和显示详细图片的第一张
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> querySelectPictureAdmin(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.querySelectPictureAdmin(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询每一个证件的上传类型的图片数据显示
	 * 
	 * @param tmid
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryPerTyhpePicture(Long tmid)
			throws Exception {
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryPerTyhpePicture(conn, tmid);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询图片类型
	 * 
	 * @param userId
	 * @param tmid
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */

	public Map<String, String> queryPitcturTyep(Long userId, long tmid)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPitcturTyep(conn, tmid, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询图片id
	 * 
	 * @param userId
	 * @param tmid
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */

	public Map<String, String> queryPitcturId(Long userId, long tmid)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPitcturId(conn, tmid, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryIdByUser(String userName) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryIdByUser(conn, userName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryPassword(String email) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPassword(conn, email);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询客服列表
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> querykefylist() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.querykefylist(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 所有密码安全提问的内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAllQuestionList() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.queryAllQuestionList(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 用户基本信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryPersonById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPersonById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	public Map<String,String> queryUserYxById(long id) throws Exception{
		Connection conn = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
		  conn = MySQL.getConnection();
		  map = userDao.queryUserYxById(conn, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			conn.close();
		}
		 
		return map;
	}
	
	/**
	 * 用户基本信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryPersonByIdNo(String idNo) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPersonByIdNo(conn, idNo);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
    /**
     * 企业基本信息
     * queryEnterpriseById
     * @param id
     * @return
     * @throws Exception
     * @autthor linww
     * 2014-6-9 上午11:34:45
     */
	public Map<String, String> queryEnterpriseById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryEnterpriseById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	/**
	 * 查询用户所有密保答案
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAnswerByUserId(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryAnswerByUserId(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询vip页面状态参数
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryVipParamList(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryVipParamList(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * add by houli 查找用户资金管理信息
	 * 
	 * @param userDao
	 */
	public void queryUserFundInfo(PageBean<Map<String, Object>> pageBean,
			String userName) throws Exception {
		userName = Utility.filteSqlInfusion(userName);

		Connection conn = MySQL.getConnection();
		// 手机变更状态为空
		String command = " ";
		if (userName != null) {
			command += " and username like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		try {
			dataPage(conn, pageBean, "v_t_user_fund_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询黑名单用户
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryBlacklistUser(PageBean<Map<String, Object>> pageBean,
			String userName) throws Exception {
		userName = Utility.filteSqlInfusion(userName);

		Connection conn = MySQL.getConnection();
		// 手机变更状态为空
		StringBuffer command = new StringBuffer();
		command.append("and enable=3");
		if (StringUtils.isNotBlank(userName) && !userName.equals("")) {
			command.append(" and username like '%");
			command.append(StringEscapeUtils.escapeSql(userName));
			command.append("%'");
		}

		try {
			dataPage(conn, pageBean, "v_blacklist_list", "*", "", command
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 更新黑名单用户
	 * 
	 * @param conn
	 * @param id
	 * @param enable
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateEnable(Long id, Integer enable) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = userDao.updateEnable(conn, id, enable);
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
	//更新用户步骤状态
	public Long updateAuthStep(Long id, Integer authStep) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = userDao.updateAuthStep(conn, id, authStep);
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

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 查询锁定用户或未锁定用户
	 * 
	 * @param userName
	 *            用户名
	 * @param realName
	 *            真实名字
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param lockType
	 *            判断是否锁定，1为未锁定，2为锁定
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryLockUsers(String userName, String realName,
			String startTime, String endTime, int lockType, PageBean pageBean)
			throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username like '%");
			condition.append(StringEscapeUtils.escapeSql(userName));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName like '%");
			condition.append(StringEscapeUtils.escapeSql(realName));
			condition.append("%' ");
		}
		if (lockType == 1) {// 启用
			if (StringUtils.isNotBlank(startTime)) {
				condition.append(" and createTime >= '");
				condition.append(StringEscapeUtils.escapeSql(startTime));
				condition.append("' ");
			}
			if (StringUtils.isNotBlank(endTime)) {
				condition.append(" and createTime <= '");
				condition.append(StringEscapeUtils.escapeSql(endTime));
				condition.append("' ");
			}
			condition.append(" and enable=1 ");
		} else if (lockType == 2) { // 锁定
			if (StringUtils.isNotBlank(startTime)) {
				condition.append(" and lockTime >= '");
				condition.append(StringEscapeUtils.escapeSql(startTime));
				condition.append("' ");
			}
			if (StringUtils.isNotBlank(endTime)) {
				condition.append(" and lockTime <= '");
				condition.append(StringEscapeUtils.escapeSql(endTime));
				condition.append("' ");
			}
			condition.append(" and enable=2 ");
		}

		try {
			dataPage(conn, pageBean, "v_t_user_lock", "*", "", condition
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> querymaterialsauthtypeCount() throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.querymaterialsauthtypeCount(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 更新锁定用户状态
	 * 
	 * @param ids
	 * @param enable
	 * @return
	 * @throws SQLException
	 */
	public long updateLockedStatus(String ids, int enable) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = userDao.updateLockedStatus(conn, ids, enable);

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
	 * add by houli 查看用户是否已经绑定了手机号码
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBindingMobleUserInfo(Long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryBindingMobleUserInfo(conn, userId,
					-1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询所有用户
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryUserAll() throws Exception {

		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = userDao.queryUserAll(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;

	}

	public long queryUserIdByPhone(String cellPhone) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = userDao.queryUserIdByPhone(conn, cellPhone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public long updateLoginPwd(Long userId, String password) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = userDao.updatePwd(conn, userId, password, 1);
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

	public Long updateDealPwd(long userId, String password) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = userDao.updatePwd(conn, userId, password, 2);
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
	 * 查询用户信息
	 * 
	 * @param userName
	 *            邮箱号，手机号，用户名
	 * @param pwd
	 *            密码
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserByUserAndPwd(String userName, String pwd)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastTime = simple.format(new Date());
		Map<String, String> umap = new HashMap<String, String>();
		umap = userDao.queryUserByUserName(conn, userName);
		try {
			if (null == umap) {
				return null;
			}
			int id = Convert.strToInt(umap.get("id"), -1);
			String lastLoginTime = Convert.strToStr(umap.get("lastDate"), null);
			if (null != lastLoginTime) {
				Date loginTime = simple.parse(lastLoginTime);
				if (new Date().getTime() - loginTime.getTime() > 3 * 60 * 60 * 1000) {
					// 取消用户限制登录将isLoginLimit设置为1和loginErrorCount设置为0
					userDao.resetUserState(conn, 0, 1, id);
				}
			}
			umap = userDao.queryUserByUserName(conn, userName);
			int isLoginLimit = Convert.strToInt(umap.get("isLoginLimit"), 1);
			int loginErrorCount = Convert.strToInt(umap.get("loginErrorCount"),
					0);
			if (isLoginLimit == 2 || loginErrorCount == 3) {
				// 设置用户限制登录，设置loginErrorCount为0
				userDao.resetUserState(conn, 0, 2, id);
				conn.commit();
				result = new HashMap<String, String>();
				result.put("isLoginLimit", "2");
				return result;
			}
			result = userDao.queryUserByUserAndPwd(conn, userName, pwd);
			if (null == result) {
				int count = loginErrorCount + 1;
				// 更新用户错误登录次数和最后登陆时间
				if (count == 3) {
					// 设置用户限制登录，重置loginErrorCount为0
					userDao.updateUserState1(conn, 0,2, lastTime, id);
				} else {
					userDao.updateUserState1(conn, count,1, lastTime, id);
				}

			} else {
				userDao.updateUserState1(conn, 0,1, lastTime, id);
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public Map<String, String> queryUserAmount(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return userDao.queryUserAmount(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 手机查询
	 * 
	 * @param cellphone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long isPhoneExist(String cellphone) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isPhoneExist(conn, cellphone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
	}

	public long updateEmalByid(long id, String email) throws Exception {
		long result = -1;
		Connection conn = MySQL.getConnection();
		try {
			result = userDao.updateEmalByid(conn, id, email);
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
	 * 更新用户的申请密码保护状态 默认是1，表示还没有申请，2表示已经申请
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long updatePwdProState(Long userId) throws Exception {
		long result = -1;
		Connection conn = MySQL.getConnection();
		try {
			result = userDao.updatePwdProState(conn, userId);
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

	public Map<String, String> queEmailUser(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queEmailUser(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 激活账户
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long updateUserActivate(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = userDao.updateUserActivate(conn, userId);
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
	 * 更换校验码1：通过Action调用
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long updateSign(Connection conn,long userId) throws Exception {
		Map<String, String> userMap = new HashMap<String, String>();
		Map<String, String> fundMap = new HashMap<String, String>();
			userMap = userDao.queryUserById(conn, userId);
			if (userMap == null) {
				return -1;
			} else {
				String id = Convert.strToStr(userMap.get("id"), "0");
				String usableSum = Convert.strToStr(userMap.get("usableSum"),
						"0");
				String freezeSum = Convert.strToStr(userMap.get("freezeSum"),
						"0");
				String dueinSum = Convert
						.strToStr(userMap.get("dueinSum"), "0");
				String dueoutSum = Convert.strToStr(userMap.get("dueoutSum"),
						"0");
				fundMap = userDao.queryMaxIdFundById(conn, userId);
				if (fundMap == null) {
					if ("1".equals(IConstants.ENABLED_PASS)) {
						String sign = com.shove.security.Encrypt.MD5(id
								+ usableSum + freezeSum + dueinSum + dueoutSum);
						String sign2 = com.shove.security.Encrypt
								.MD5("0" + id + "0" + "0" + "0" + "0" + "0"
										+ "0" + "0" + "0");
						userDao.setSign(conn, userId, sign, sign2);
					} else {
						String sign = com.shove.security.Encrypt.MD5(id
								+ usableSum + freezeSum + dueinSum + dueoutSum
								+ IConstants.PASS_KEY);
						String sign2 = com.shove.security.Encrypt.MD5("0" + id
								+ "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0"
								+ IConstants.PASS_KEY);
						userDao.setSign(conn, userId, sign, sign2);
					}
				} else {
					String f_id = Convert.strToStr(fundMap.get("id"), "0");
					String f_userId = Convert.strToStr(fundMap.get("userId"),
							"0");
					String f_handleSum = Convert.strToStr(fundMap
							.get("handleSum"), "0");
					String f_usableSum = Convert.strToStr(fundMap
							.get("usableSum"), "0");
					String f_freezeSum = Convert.strToStr(fundMap
							.get("freezeSum"), "0");
					String f_dueinSum = Convert.strToStr(fundMap
							.get("dueinSum"), "0");
					String f_dueoutSum = Convert.strToStr(fundMap
							.get("dueoutSum"), "0");
					String f_cost = Convert.strToStr(fundMap.get("cost"), "0");
					String f_income = Convert.strToStr(fundMap.get("income"),
							"0");
					String f_spending = Convert.strToStr(fundMap
							.get("spending"), "0");
					if ("1".equals(IConstants.ENABLED_PASS)) {
						String sign = com.shove.security.Encrypt.MD5(f_userId
								+ usableSum + freezeSum + dueinSum + dueoutSum);
						String sign2 = com.shove.security.Encrypt.MD5(f_id
								+ f_userId + f_handleSum + f_usableSum
								+ f_freezeSum + f_dueinSum + f_dueoutSum
								+ f_cost + f_income + f_spending);
						userDao.setSign(conn, userId, sign, sign2);
					} else {
						String sign = com.shove.security.Encrypt.MD5(f_userId
								+ usableSum + freezeSum + dueinSum + dueoutSum
								+ IConstants.PASS_KEY);
						String sign2 = com.shove.security.Encrypt.MD5(f_id
								+ f_userId + f_handleSum + f_usableSum
								+ f_freezeSum + f_dueinSum + f_dueoutSum
								+ f_cost + f_income + f_spending
								+ IConstants.PASS_KEY);
						userDao.setSign(conn, userId, sign, sign2);
					}
				}
			}
			//whb 去掉打印更换校验码
			//log.info("-------------" + userMap.get("username")	+ "：更换校验码-----------");
		
		return userId;
	}
	/**
	 * 更换校验码1：通过Action调用(方法重载)
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long updateSignOld(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
		 updateSign(conn, userId);
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
	 * 更换校验码whb
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long updateSign(long userId) {
		log.info("更换校验码"+userId);
		return 1L;
	}
	

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	public void setShoveBorrowAmountTypeDao(
			ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao) {
		this.shoveBorrowAmountTypeDao = shoveBorrowAmountTypeDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public boolean checkSign(Connection conn,Long userId) throws Exception {
		Map<String, String> userMap = new HashMap<String, String>();
		Map<String, String> fundMap = new HashMap<String, String>();
		String sign = "";
		String sign2 = "";
			userMap = userDao.queryUserById(conn, userId);
			if (userMap == null) {
				return false;
			} else {
				String id = Convert.strToStr(userMap.get("id"), "0");
				String usableSum = Convert.strToStr(userMap.get("usableSum"),
						"0");
				String freezeSum = Convert.strToStr(userMap.get("freezeSum"),
						"0");
				String dueinSum = Convert
						.strToStr(userMap.get("dueinSum"), "0");
				String dueoutSum = Convert.strToStr(userMap.get("dueoutSum"),
						"0");
				fundMap = userDao.queryMaxIdFundById(conn, userId);
				if (fundMap == null) {
					if ("1".equals(IConstants.ENABLED_PASS)) {
						sign = com.shove.security.Encrypt.MD5(id + usableSum
								+ freezeSum + dueinSum + dueoutSum);
						sign2 = com.shove.security.Encrypt.MD5("0" + id + "0"
								+ "0" + "0" + "0" + "0" + "0" + "0" + "0");
					} else {
						sign = com.shove.security.Encrypt.MD5(id + usableSum
								+ freezeSum + dueinSum + dueoutSum
								+ IConstants.PASS_KEY);
						sign2 = com.shove.security.Encrypt.MD5("0" + id + "0"
								+ "0" + "0" + "0" + "0" + "0" + "0" + "0"
								+ IConstants.PASS_KEY);
					}
				} else {
					String f_id = Convert.strToStr(fundMap.get("id"), "0");
					String f_userId = Convert.strToStr(fundMap.get("userId"),
							"0");
					String f_handleSum = Convert.strToStr(fundMap
							.get("handleSum"), "0");
					String f_usableSum = Convert.strToStr(fundMap
							.get("usableSum"), "0");
					String f_freezeSum = Convert.strToStr(fundMap
							.get("freezeSum"), "0");
					String f_dueinSum = Convert.strToStr(fundMap
							.get("dueinSum"), "0");
					String f_dueoutSum = Convert.strToStr(fundMap
							.get("dueoutSum"), "0");
					String f_cost = Convert.strToStr(fundMap.get("cost"), "0");
					String f_income = Convert.strToStr(fundMap.get("income"),
							"0");
					String f_spending = Convert.strToStr(fundMap
							.get("spending"), "0");
					if ("1".equals(IConstants.ENABLED_PASS)) {
						sign = com.shove.security.Encrypt.MD5(f_userId
								+ usableSum + freezeSum + dueinSum + dueoutSum);
						sign2 = com.shove.security.Encrypt.MD5(f_id + f_userId
								+ f_handleSum + f_usableSum + f_freezeSum
								+ f_dueinSum + f_dueoutSum + f_cost + f_income
								+ f_spending);
					} else {
						sign = com.shove.security.Encrypt.MD5(f_userId
								+ usableSum + freezeSum + dueinSum + dueoutSum
								+ IConstants.PASS_KEY);
						sign2 = com.shove.security.Encrypt.MD5(f_id + f_userId
								+ f_handleSum + f_usableSum + f_freezeSum
								+ f_dueinSum + f_dueoutSum + f_cost + f_income
								+ f_spending + IConstants.PASS_KEY);
					}
				}
				if (userMap.get("sign").equals(sign)
						&& userMap.get("sign2").equals(sign2)) {
					return true;
				}
			}
			conn.commit();
		return false;
	}
	/**
	 * 验证签名(重载方法)
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean checkSign(Long userId) throws Exception {
		//	TODO:后续考虑签名规则
//		Connection conn = MySQL.getConnection();
//		boolean b = false;
//		try {
//			b = checkSign(conn, userId);
//			conn.commit();
//		} catch (Exception e) {
//			log.error(e);
//			e.printStackTrace();
//			conn.rollback();
//			throw e;
//		} finally {
//			conn.close();
//		}
//		return b;
		return true;
	}

	public Map<String, String> queryUserBindphone(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> result = userDao.queryUserBindphone(conn, userId);
			return result;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
	}

	public Map<String, String> queryUserByName(String email) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = userDao.queryUserByName(conn, email);
			return map;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	public Map<String,String> queryUserByNameAndPhone(String name,String phone)
		throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserByNameAndPhone(conn, name, phone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	public List<Map<String, Object>> queryUserByNameAndPhone2(String name)
			throws Exception{
			Connection conn = MySQL.getConnection();
			List<Map<String, Object>> list = null;
			try {
				list = userDao.queryUserByNameAndPhone2(conn, name);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
			return list;
		}
	public void queryUserByNameAndPhone3(String name,PageBean pageBean) throws Exception{
		Connection conn = MySQL.getConnection();
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotBlank(name)){
			sb.append(" and  username  like '%"
					+ StringEscapeUtils.escapeSql(name.trim()) + "%' ");
    	}
		try {
			dataPage(conn, pageBean, "v_t_borrow_backstage_info", "*", "", sb.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public Map<String,String> queryUserName(String name)
			throws Exception{
			Connection conn = MySQL.getConnection();
			Map<String,String> map = new HashMap<String, String>();
			try {
				map = userDao.queryUserNme(conn, name);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
			return map;
		}
	
	public void updatePersonLianSate(long id,long user_id,long order_id,Date date,String content)
			throws Exception{
			Connection conn = MySQL.getConnection();
			 
			try {
				userDao.updatePersonLianSate(conn, id);
				userDao.addLianlianOrderRz(conn, order_id, date, user_id,content);
			    conn.commit();
			} catch (Exception e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
			 
		}
	
	public Map<String,String> queryGroupById(long userId)
			throws Exception{
			Connection conn = MySQL.getConnection();
			Map<String,String> map = new HashMap<String, String>();
			try {
				map = userDao.queryGroupById(conn, userId);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
			return map;
		}
	
	
	public long updateUserByCell(String cell1,String cell2)
			throws Exception{
			Connection conn = MySQL.getConnection();
			long m = -1; 
			try {
				 userDao.updatet_user(conn, cell1, cell2);
				 userDao.updatet_person(conn, cell1, cell2);
				 userDao.updatet_phone_binding_info(conn, cell1, cell2);
			     conn.commit();
			     m = 1;
			} catch (Exception e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
			 
			return m;
		}
	
	
	public void queryBorrowTyjList(PageBean<Map<String, Object>> pageBean)
			throws Exception{
			Connection conn = MySQL.getConnection();
			try {
				userDao.queryBorrowTyjList(conn, pageBean);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
		 
		}
	
	public long addBorrowNew(String name,double rate,int day, double amount_sum)
			throws Exception{
			Connection conn = MySQL.getConnection();
			long m = -1;
			try {
				userDao.addBorrowNew(conn, name, rate, day, amount_sum);
				conn.commit();
				m=1;
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
		 
			return m;
		}
	
	public long addEmployeeBorrow(String name,double rate,int day, double amount_sum)
			throws Exception{
			Connection conn = MySQL.getConnection();
			long m = -1;
			try {
				userDao.addEmployeeBorrow(conn, name, rate, day, amount_sum);
				conn.commit();
				m=1;
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
		 
			return m;
		}
	
	public Map<String,String>  queryGroupUser(long userId)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String>  resMap = null;
		try {
			resMap = userDao.queryGroupUser(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		
		return resMap;
	}
	
	/**
	 * 查询员工体验金配置
	 * @param pageBean
	 * @throws Exception
	 */
	public void queryEmployeeConfigList(PageBean<Map<String, Object>> pageBean)
			throws Exception{
			Connection conn = MySQL.getConnection();
			StringBuffer condition = new StringBuffer();
			try {
				dataPage(conn, pageBean, "v_t_employee_config_list", "*", "", condition.toString());
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
		 
		}
	
  
	public long updateEmployeeConfig(long id, int amount,long userId)
			throws Exception{
			Connection conn = MySQL.getConnection();
			long m = -1; 
			try {
				 Map<String,String> map = userDao.queryEmployeeConfigByUserId(conn, userId);
				 if(map!=null && map.size()>0){
					 userDao.updateEmployeeConfig(conn, id, amount);
				 }else{
					 userDao.addEmployeeConfig(conn, userId, amount);
				 }
				
			     conn.commit();
			     m = 1;
			} catch (Exception e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
			 
			return m;
		}
	
	public void queryEmployeeBorrowList(PageBean<Map<String, Object>> pageBean)
			throws Exception{
			Connection conn = MySQL.getConnection();
			try {
				userDao.queryEmployeeBorrowList(conn, pageBean);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
		 
		}
	
	public Map<String, String> queryIsPhoneonUser(String phone)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = beVipDao.queryIsPhoneonUser(conn, phone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	
	public Map<String,String> searchInvestSumByUserId(long userId)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String, String>();
		try {
			//查询投资总额
			map = userDao.searchUserInvestSum(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
/**
 * 查询抽奖并修改使用状态
 * @return
 * @throws Exception
 */
	public Map<String,String> searchActivity20160801()throws Exception{
			Connection conn = MySQL.getConnection();
			Map<String,String> map = new HashMap<String, String>();
			try {
				map = userDao.searchActivity20160801(conn);
				//修改抽奖使用状态
				if(map!=null){
					userDao.updateActivity20160801(conn, Long.parseLong(map.get("id")) );	
				}
				  conn.commit();
			} catch (Exception e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
			return map;
		}
	
	public long updateActivity20160801(long Id)throws Exception{
			Connection conn = MySQL.getConnection();
			long m = -1; 
			try {
				 m = userDao.updateActivity20160801(conn, Id);			
			     conn.commit();
			     m = 1;
			} catch (Exception e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
			 
			return m;
		}
	public void addActivity20160801(double money_box)throws Exception{
		Connection conn = MySQL.getConnection();
		try {
			 userDao.addActivity20160801(conn, money_box);			
		     conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		 
	}
	
	public void addActivity20160801ByUser1(long user_id,int already_number,int total_number)throws Exception{
		Connection conn = MySQL.getConnection();
		try {
			System.out.println("1eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			 userDao.addActivityByUser(conn, user_id,already_number,total_number);	
			 System.out.println("2eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		     conn.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage()+"3eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		 
	}
	public Map<String,String> addActivity20160801ByUser(long user_id,int already_number,int total_number)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String, String>();
		try {
			 userDao.add(conn, user_id,already_number,total_number);
			 conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	public Map<String,String> searchActivity20160801ByUser(long userId)throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String> map = new HashMap<String, String>();
		try {
			map = userDao.searchActivity20160801ByUser(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	public long updateActivity20160801ByUser(long userId,int totalNumber,int alreadyNumber)throws Exception{
		Connection conn = MySQL.getConnection();
		long m = -1; 
		try {
			 m = userDao.updateActivity20160801ByUser(conn, userId,totalNumber,alreadyNumber);			
		     conn.commit();
		     m = 1;
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		 
		return m;
	}
	public long updateUserUsableAmount(double total_number,long userId)throws Exception{
		Connection conn = MySQL.getConnection();
		long m = -1; 
		try {
			 m = userDao.addUserUsableAmount(conn, total_number,userId);			
		     conn.commit();
		     m = 1;
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		 
		return m;
	}
}
