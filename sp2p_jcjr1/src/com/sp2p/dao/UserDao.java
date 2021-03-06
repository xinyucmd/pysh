package com.sp2p.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.DesSecurityUtil;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.util.DBReflectUtil;
import com.sp2p.util.DateUtil;

public class UserDao {

	/**
	 * 添加用户
	 * 
	 * @param conn
	 * @param email
	 * @param userName
	 * @param password
	 * @param refferee
	 * @param lastDate
	 * @param lastIP
	 * @param dealpwd
	 * @param mobilePhone
	 * @param rating
	 * @param creditrating
	 * @param status
	 * @param vipcreatetime
	 * @param creditlimit
	 * @param authstep
	 * @param headImg
	 * @return
	 * @throws SQLException
	 */
	public Long addUser(Connection conn, String email, String userName,
			String password, String refferee, String lastDate, String lastIP,
			String dealpwd, String mobilePhone, Integer rating,
			Integer creditrating, Integer vipstatus, String vipcreatetime,
			Integer authstep, String headImg, Integer enable,
			Long servicePersonId,double creditLimit) throws SQLException {

		Dao.Tables.t_user user = new Dao().new Tables().new t_user();

		user.email.setValue(email);
		user.username.setValue(userName);
		user.password.setValue(password);
		user.lastDate.setValue(lastDate);
		user.refferee.setValue(refferee);
		user.dealpwd.setValue(password);
		if (StringUtils.isNotBlank(lastIP)) {
			user.lastIP.setValue(lastIP);
		}
		
		user.creditLimit.setValue(creditLimit);
		user.usableCreditLimit.setValue(creditLimit);
		user.authStep.setValue(authstep);
		user.mobilePhone.setValue(mobilePhone);
		user.rating.setValue(rating);
		user.creditrating.setValue(creditrating);
		user.vipStatus.setValue(vipstatus);
		user.vipCreateTime.setValue(vipcreatetime);
		user.headImg.setValue(headImg);
		user.enable.setValue(enable);
		user.createTime.setValue(new Date());
		return user.insert(conn);
	}
	/**
	 * 添加用户
	 * 
	 * @param conn
	 * @param email
	 * @param userName
	 * @param password
	 * @param refferee
	 * @param lastDate
	 * @param lastIP
	 * @param dealpwd
	 * @param mobilePhone
	 * @param rating
	 * @param creditrating
	 * @param status
	 * @param vipcreatetime
	 * @param creditlimit
	 * @param authstep
	 * @param headImg
	 * @return
	 * @throws SQLException
	 */
	public Long addUser2(Connection conn, String email, String userName,
			String password, String refferee, String lastDate, String lastIP,
			String dealpwd, String mobilePhone, Integer rating,
			Integer creditrating, Integer vipstatus, String vipcreatetime,
			Integer authstep, String headImg, Integer enable,
			Long servicePersonId,double creditLimit,int regSrc) throws SQLException {

		Dao.Tables.t_user user = new Dao().new Tables().new t_user();

		user.email.setValue(email);
		user.username.setValue(userName);
		user.password.setValue(password);
		user.lastDate.setValue(lastDate);
		user.refferee.setValue(refferee);
		user.dealpwd.setValue(password);
		if (StringUtils.isNotBlank(lastIP)) {
			user.lastIP.setValue(lastIP);
		}
		
		user.creditLimit.setValue(creditLimit);
		user.usableCreditLimit.setValue(creditLimit);
		user.authStep.setValue(authstep);
		user.mobilePhone.setValue(mobilePhone);
		user.rating.setValue(rating);
		user.creditrating.setValue(creditrating);
		user.vipStatus.setValue(vipstatus);
		user.vipCreateTime.setValue(vipcreatetime);
		user.headImg.setValue(headImg);
		user.enable.setValue(enable);
		user.createTime.setValue(new Date());
		user.regSrc.setValue(regSrc);
		return user.insert(conn);
	}
	public Long addUsers(Connection conn, String email, String userName,
			String password, String refferee, String lastDate, String lastIP,
			String dealpwd, String mobilePhone, Integer rating,
			Integer creditrating, Integer vipstatus, String vipcreatetime,
			Integer authstep, String headImg, Integer enable,
			Long servicePersonId,double creditLimit,int regSrc) throws SQLException {

		Dao.Tables.t_user user = new Dao().new Tables().new t_user();

		user.email.setValue(email);
		user.username.setValue(userName);
		user.password.setValue(password);
		user.lastDate.setValue(lastDate);
		user.refferee.setValue(refferee);
		user.dealpwd.setValue(password);
		if (StringUtils.isNotBlank(lastIP)) {
			user.lastIP.setValue(lastIP);
		}
		
		user.creditLimit.setValue(creditLimit);
		user.usableCreditLimit.setValue(creditLimit);
		user.authStep.setValue(authstep);
		user.mobilePhone.setValue(mobilePhone);
		user.rating.setValue(rating);
		user.creditrating.setValue(creditrating);
		user.vipStatus.setValue(vipstatus);
		user.vipCreateTime.setValue(vipcreatetime);
		user.headImg.setValue(headImg);
		user.enable.setValue(enable);
		user.createTime.setValue(new Date());
		user.regSrc.setValue(regSrc);
		return user.insert(conn);
	}
	/**
	 * 初始化资料认证
	 * 
	 * @param conn
	 * @param userId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public synchronized Long addMaterialsauth1(Connection conn, Long userId, long type)
			throws Exception {
		// 初始化资料认证
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
		materialsauth.materAuthTypeId.setValue(type);// 默认为16种类型
		materialsauth.userId.setValue(userId);
		return materialsauth.insert(conn);
	}

	/**
	 * 统计有多少图片类型
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> querymaterialsauthtypeCount(Connection conn)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select COUNT(*) as cccc from t_materialsauthtype where id >= 1 and id <= 16");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql= null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	 
	public Long addYxUser(Connection conn, long user_id,String cellPhone) throws SQLException {
		Dao.Tables.t_user_yx t_user_yx = new Dao().new Tables().new t_user_yx();
		t_user_yx.user_id.setValue(user_id);
		t_user_yx.tell.setValue(cellPhone);
		return t_user_yx.insert(conn);
	}

	/**
	 * 添加用户的基本资料
	 * 
	 * @return
	 */
	public Long addUserBaseData(Connection conn, String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone,
			String personalHead, Long userId, String idNo) throws SQLException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(realName);
		person.cellPhone.setValue(cellPhone);
		person.sex.setValue(sex);
		person.birthday.setValue(birthday);
		person.highestEdu.setValue(highestEdu);
		person.eduStartDay.setValue(eduStartDay);
		person.school.setValue(school);
		person.maritalStatus.setValue(maritalStatus);
		person.hasChild.setValue(hasChild);
		person.hasHourse.setValue(hasHourse);
		person.hasHousrseLoan.setValue(hasHousrseLoan);
		person.hasCar.setValue(hasCar);
		person.hasCarLoan.setValue(hasCarLoan);
		person.nativePlacePro.setValue(nativePlacePro);
		person.nativePlaceCity.setValue(nativePlaceCity);
		person.registedPlacePro.setValue(registedPlacePro);
		person.registedPlaceCity.setValue(registedPlaceCity);
		person.address.setValue(address);
		person.telephone.setValue(telephone);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.personalHead.setValue(personalHead);
		return person.insert(conn);
	}

	/**
	 * 添加图片
	 * 
	 * @param conn
	 * @param materAuthTypeId
	 * @param imgPath
	 * @param auditStatus
	 * @param userId
	 * @param authTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addImage(Connection conn, long materAuthTypeId, String imgPath,
			long userId) throws SQLException, DataException {
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
		materialsauth.materAuthTypeId.setValue(materAuthTypeId);
		materialsauth.imgPath.setValue(imgPath);

		Map<String, String> map = null;
		Map<String, String> Accmap = null;
		if (userId > 0) {
			DataSet matondataSet = materialsauth.open(conn, "",
					" 1 = 1 AND materAuthTypeId = " + materAuthTypeId
							+ " AND userId =" + userId
							+ " AND imgPath is not null", "", -1, -1);
			map = BeanMapUtils.dataSetToMap(matondataSet);
		}
		if (map != null && map.size() > 0) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format.format(new Date());
			materialsauth.passTime.setValue(date);
			materialsauth.auditStatus.setValue(1);
			// 审核之后不可以修改
			return materialsauth.update(conn, "materAuthTypeId = "
					+ materAuthTypeId + " AND userId = " + userId);
		} else {
			// 如果更新的那么更新user表中的步骤状态值
			materialsauth.userId.setValue(userId);
			Dao.Tables.t_user user = new Dao().new Tables().new t_user();

			Accmap = queryPicturStatuCount(conn, userId);
			Integer alli = 0;
			if (Accmap != null && Accmap.size() > 0) {

				alli = Convert.strToInt(Accmap.get("ccc"), 0);
				if (alli >= 4) {
					user.authStep.setValue(5);
					user.update(conn, " id = " + userId);
				}
			}

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format.format(new Date());
			materialsauth.passTime.setValue(date);
			materialsauth.auditStatus.setValue(1);
			return materialsauth.update(conn, "materAuthTypeId = "
					+ materAuthTypeId + " AND userId = " + userId);
		}

	}

	public Long updateUserauthod(Connection conn, Long userId) {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Map<String, String> Accmap = null;// 统计t_materialsauth用户类型
		try {
			Accmap = queryPicturStatuCount(conn, userId);
			Integer alli = 0;
			if (Accmap != null && Accmap.size() > 0) {
				alli = Convert.strToInt(Accmap.get("ccc"), 0);
				if (alli >= 5) {
					user.authStep.setValue(5);
					return user.update(conn, " id = " + userId);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
		return 1L;
	}
	
	/**
	 * 
	 * updateUserauthodAdmin
	 * @param conn
	 * @param userId
	 * @return
	 * @autthor linww
	 * 2014-6-7 上午11:38:55
	 */
	public Long updateUserauthodAdmin(Connection conn, Long userId,int type) {
		Dao.Tables.t_borrow_role t_borrow_role = new Dao().new Tables().new t_borrow_role();
		Map<String, String> Accmap = null;// 统计t_materialsauth用户类型
		try {
			if(type==1){ //个人
			    Accmap = queryPicturStatuCountAdmin(conn, userId);
			}else if (type==2){ //企业
				Accmap = queryEnterprisePicturStatuCountAdmin(conn, userId);
			}
			Integer alli = 0;
			if (Accmap != null && Accmap.size() > 0) {
				alli = Convert.strToInt(Accmap.get("ccc"), 0);
				if (alli >= 15) {
					t_borrow_role.authStep.setValue(2);
					return t_borrow_role.update(conn, " userId = " + userId);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
		return 1L;
	}
	/**
	 * 增加系统日志 (只用于这个类)
	 * @param conn
	 * @param operation_table
	 * @param operation_user
	 * @param operation_type
	 * @param operation_ip
	 * @param operation_money
	 * @param operation_remarks
	 * @param operation_around
	 * @return
	 * @throws SQLException
	 */
	 public  long  addOperationLog(Connection  conn,String operation_table,String operation_user,int  operation_type,String operation_ip,
			  double operation_money,String operation_remarks ,int operation_around) throws SQLException{
			Dao.Tables.t_operation_log t_opration_log  = new Dao().new Tables().new t_operation_log();
			t_opration_log.operation_table.setValue(operation_table);
			t_opration_log.operation_user.setValue(operation_user);
			t_opration_log.operation_type.setValue(operation_type);
			t_opration_log.operation_ip.setValue(operation_ip);
			t_opration_log.operation_money.setValue(operation_money);
			t_opration_log.operation_remarks.setValue(operation_remarks);
			t_opration_log.operation_around.setValue(operation_around);
			t_opration_log.operation_time.setValue(new Date());
			
			return  t_opration_log.insert(conn);
		}
	 public Long updateUserBaseDataOne(Connection conn,String reallyName,String idNo,Long userId) throws SQLException{
		 Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		 Long ret = -1L;
		 
			 if(StringUtils.isNotBlank(reallyName)){
				person.realName.setValue(reallyName.trim());
		     } 
			 if(StringUtils.isNotBlank(idNo)){
				 person.idNo.setValue(idNo.trim());
		     }
			 person.lian_state.setValue(1);
		 ret =  person.update(conn, "userId = " + userId);
		 ret = addOperationLog(conn, "t_person", idNo, IConstants.INSERT, "", 0, "填写个人基本资料", 1);
		 return ret;
	 }
	 
	/**
	 * 更新用户的基本信息
	 * 
	 * @param conn
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
	public Long updateUserBaseData(Connection conn, int lian_state,String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone,
			String personalHead, Long userId, String idNo,String username,String lastIP) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		if(StringUtils.isNotBlank(realName)){
		person.realName.setValue(realName);
		}
        person.cellPhone.setValue(cellPhone);
        if(StringUtils.isNotBlank(sex)){	
		person.sex.setValue(sex);
        }
        if(StringUtils.isNotBlank(birthday)){
		person.birthday.setValue(birthday);
        }
		person.highestEdu.setValue(highestEdu);
        
        if(StringUtils.isNotBlank(eduStartDay)){
		person.eduStartDay.setValue(eduStartDay);
        }
		person.school.setValue(school);
		person.maritalStatus.setValue(maritalStatus);
		person.hasChild.setValue(hasChild);
		person.hasHourse.setValue(hasHourse);
		person.hasHousrseLoan.setValue(hasHousrseLoan);
		person.hasCar.setValue(hasCar);
		person.hasCarLoan.setValue(hasCarLoan);
		person.nativePlacePro.setValue(nativePlacePro);
		person.nativePlaceCity.setValue(nativePlaceCity);
		person.registedPlacePro.setValue(registedPlacePro);
		person.registedPlaceCity.setValue(registedPlaceCity);
		person.address.setValue(address);
		person.telephone.setValue(telephone);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.personalHead.setValue(personalHead);
		if(lian_state==1){
			person.lian_state.setValue(lian_state);
		}
		Long result = -1L;
		Map<String, String> map = null;
		try{
		DataSet PersondataSet = person.open(conn, "", "userId = " + userId, "",
				-1, -1);
		map = BeanMapUtils.dataSetToMap(PersondataSet);
		}catch (Exception e) {
		  e.printStackTrace();
		  return -1L;
		}
		if (map != null && map.size() > 0) {
			
		    String 	realNamestr = map.get("realName");	
            if(realNamestr!=null&&!realNamestr.equals("")){
            	result =  person.update(conn, "userId = " + userId);	
            	//添加操作日志
				result = addOperationLog(conn, "t_person", username, IConstants.UPDATE, lastIP, 0, "更新个人详细资料", 1);
				return  result;
            }else{
				// 如果更新的那么更新user表中的步骤状态值
		    	Dao.Tables.t_user  user = new Dao().new Tables().new t_user();
		    	
		    		user.authStep.setValue(2);// 2表示填写完基本认证
		    	
				result = user.update(conn, " id = " + userId);
				if (result > 0) {
					result =  person.update(conn, "userId = " + userId);// 如果user表更新成功
					 //添加操作日志
					result = addOperationLog(conn, "t_person", username, IConstants.INSERT, lastIP, 0, "填写个人详细资料", 1);
					return result;
				}
				return result;
		    }
			//return person.update(conn, "userId = " + userId);
		} else {
			// 如果更新的那么更新user表中的步骤状态值
			Dao.Tables.t_user user = new Dao().new Tables().new t_user();
			user.authStep.setValue(2);// 2表示填写完基本认证
			result = user.update(conn, " id = " + userId);
			if (result > 0) {
				result =  person.insert(conn);// 如果user表更新成功
				//添加操作日志
				result = addOperationLog(conn, "t_person", username, IConstants.UPDATE, lastIP, 0, "更新个人详细资料", 1);
				return result;
			}
			return result;
		}
	}
	/** 
	 *    投资人填写个人个信息，后属性状态
	 * @param conn
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
	public Long updateUserBaseWWc(Connection conn, String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone,
			String personalHead, Long userId, String idNo) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(realName);
		person.cellPhone.setValue(cellPhone);
		person.sex.setValue(sex);
		person.birthday.setValue(birthday);
		person.highestEdu.setValue(highestEdu);
		person.eduStartDay.setValue(eduStartDay);
		person.school.setValue(school);
		person.maritalStatus.setValue(maritalStatus);
		person.hasChild.setValue(hasChild);
		person.hasHourse.setValue(hasHourse);
		person.hasHousrseLoan.setValue(hasHousrseLoan);
		person.hasCar.setValue(hasCar);
		person.hasCarLoan.setValue(hasCarLoan);
		person.nativePlacePro.setValue(nativePlacePro);
		person.nativePlaceCity.setValue(nativePlaceCity);
		person.registedPlacePro.setValue(registedPlacePro);
		person.registedPlaceCity.setValue(registedPlaceCity);
		person.address.setValue(address);
		person.telephone.setValue(telephone);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.personalHead.setValue(personalHead);
		Long result = -1L;
		Map<String, String> map = null;
		try{
		DataSet PersondataSet = person.open(conn, "", "userId = " + userId, "",
				-1, -1);
		map = BeanMapUtils.dataSetToMap(PersondataSet);
		}catch (Exception e) {
		  e.printStackTrace();
		  return -1L;
		}
		if (map != null && map.size() > 0) {
			
		    String 	realNamestr = map.get("realName");	
            if(realNamestr!=null&&!realNamestr.equals("")){
            	return person.update(conn, "userId = " + userId);	
		    }else{
				// 如果更新的那么更新user表中的步骤状态值
		    	Dao.Tables.t_user  user = new Dao().new Tables().new t_user();
		    	user.authStep.setValue(1);// 2表示填写完基本认证
				result = user.update(conn, " id = " + userId);
				if (result > 0) {
					return person.update(conn, "userId = " + userId);// 如果user表更新成功
				}
				return result;
		    }
		} else {
			// 如果更新的那么更新user表中的步骤状态值
			Dao.Tables.t_user user = new Dao().new Tables().new t_user();
			user.authStep.setValue(1);// 2表示填写完基本认证
			result = user.update(conn, " id = " + userId);
			if (result > 0) {
				return person.insert(conn);// 如果user表更新成功
			}
			return result;
		}
	}
	/**
	 * 
	 * 后台用户基本资料审核
	 * @param conn  
	 * @param userId  用户id
	 * @param auditStatus 审核状态
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserBaseDataCheck(Connection conn, Long userId,
			int auditStatus) throws SQLException, DataException {
		Long result = -1L;
		Integer personauditStatus = -1;
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		Map<String, String> Personmap = new HashMap<String, String>();
		try{
		DataSet personds = person.open(conn, "auditStatus", " userId = "
				+ userId, "", -1, -1);
		Personmap = BeanMapUtils.dataSetToMap(personds);
		}catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		Integer precreditrating = -1;// 原来的信用积分
		Map<String, String> map = new HashMap<String, String>();
		DataSet ds = null;
		Map<String, String> wormap = null;
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		try{
		ds = user.open(conn, "creditrating", " id = " + userId, "", -1,
				-1);
		map = BeanMapUtils.dataSetToMap(ds);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		if (map != null && map.size() > 0) {
			precreditrating = Convert.strToInt(map.get("creditrating"), -1);
		}
	
		
		if (auditStatus == 3){
			user.creditrating.setValue(10 + precreditrating);
			result = user.update(conn, " id = " + userId);// 更新用户的信用积分
			result = addserintegraldetail(conn, userId,10, 
					"用户基本资料审核",1, "用户基本资料审核", "增加");
			if(result<=0){
				return  -1L;
			}
		}else if (auditStatus == 2){
			user.creditrating.setValue(precreditrating - 10);
			result = user.update(conn, " id = " + userId);// 更新用户的信用积分
			result = addserintegraldetail(conn, userId, 10,
					"用户基本资料审核",1, "用户基本资料审核", "减少");
			if(result<=0){
				return  -1L;
			}
		}
			
		person.auditStatus.setValue(auditStatus);
		return person.update(conn, "userId = " + userId);
	}
	
	
	
	/**
	 * 向积分记录表添加记录
	 * @param conn
	 * @param userId
	 * @param score
	 * @param type
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addserintegraldetail(Connection conn,Long userId,Integer score,String typeStr,Integer type,String remark,String changetype) throws SQLException, DataException{
		Dao.Tables.t_userintegraldetail  integraldetail = new Dao().new Tables().new t_userintegraldetail();
		integraldetail.changerecore.setValue(score);
		integraldetail.intergraltype.setValue(typeStr);
		integraldetail.remark.setValue(remark);
		integraldetail.changetype.setValue(changetype);//先设置成增加
		integraldetail.time.setValue(new Date());
		integraldetail.userid.setValue(userId);
		if(type==1){//信用积分
			integraldetail.type.setValue(1);
		}
		if(type==2){//vip积分
			integraldetail.type.setValue(2);
		}
		
		return  integraldetail.insert(conn);
	}
	
	public Long addLianlianOrderRz(Connection conn,long order_id,Date date,long user_id,String content) throws SQLException, DataException{
		Dao.Tables.t_lianlian_order_rz  t_lianlian_order_rz = new Dao().new Tables().new t_lianlian_order_rz();
		t_lianlian_order_rz.order_id.setValue(order_id);
		t_lianlian_order_rz.createTime.setValue(date);
		t_lianlian_order_rz.user_id.setValue(user_id);
		t_lianlian_order_rz.remarks.setValue(content);
		return  t_lianlian_order_rz.insert(conn);
	}
	
	
	public List<Map<String,Object>> searchLianlianOrderRz(Connection conn,String user_id) throws SQLException, DataException{
		StringBuffer condition = new StringBuffer();
		condition.append(" 1=1");
		Dao.Tables.t_lianlian_order_rz t_lianlian_order_rz = new Dao().new Tables().new t_lianlian_order_rz();
		if (StringUtils.isNotBlank(user_id)){
			condition.append(" and user_id='"+user_id+"'");
		}
		String nowStr = DateUtil.dateToStringYYMMDD(new Date());
		condition.append(" and DATE_FORMAT(creat_time,'%Y-%m-%d') = '"+nowStr+"'");
		DataSet dataSet = t_lianlian_order_rz
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 
	 * 添加用户的工作认证资料
	 * 
	 * @param conn
	 * @param orgName
	 * @param occStatus
	 * @param workPro
	 * @param workCity
	 * @param companyType
	 * @param companyLine
	 * @param companyScale
	 * @param job
	 * @param monthlyIncome
	 * @param workYear
	 * @param companyTel
	 * @param workEmail
	 * @param companyAddress
	 * @param directedName
	 * @param directedRelation
	 * @param directedTel
	 * @param otherName
	 * @param otherRelation
	 * @param otherTel
	 * @param moredName
	 * @param moredRelation
	 * @param moredTel
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Long addUserWorkData(Connection conn, String orgName,
			String occStatus, Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId)
			throws SQLException {
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		workauth.orgName.setValue(orgName);
		workauth.occStatus.setValue(occStatus);
		workauth.workPro.setValue(workPro);
		workauth.workCity.setValue(workCity);
		workauth.companyType.setValue(companyType);
		workauth.companyLine.setValue(companyLine);
		workauth.companyScale.setValue(companyScale);
		workauth.job.setValue(job);
		workauth.monthlyIncome.setValue(monthlyIncome);
		workauth.workYear.setValue(workYear);
		workauth.companyTel.setValue(companyTel);
		workauth.workEmail.setValue(workEmail);
		workauth.companyAddress.setValue(companyAddress);
		workauth.directedName.setValue(directedName);
		workauth.directedRelation.setValue(directedRelation);
		workauth.directedTel.setValue(directedTel);
		workauth.otherName.setValue(otherName);
		workauth.otherRelation.setValue(otherName);
		workauth.otherTel.setValue(otherTel);
		workauth.moredName.setValue(moredName);
		workauth.moredRelation.setValue(moredRelation);
		workauth.moredTel.setValue(moredTel);
		workauth.userId.setValue(userId);
		return workauth.insert(conn);
	}

	/**
	 * 修改用户工作认证资料
	 * 
	 * @param conn
	 * @param orgName
	 * @param occStatus
	 * @param workPro
	 * @param workCity
	 * @param companyType
	 * @param companyLine
	 * @param companyScale
	 * @param job
	 * @param monthlyIncome
	 * @param workYear
	 * @param companyTel
	 * @param workEmail
	 * @param companyAddress
	 * @param directedName
	 * @param directedRelation
	 * @param directedTel
	 * @param otherName
	 * @param otherRelation
	 * @param otherTel
	 * @param moredName
	 * @param moredRelation
	 * @param moredTel
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserWorkData(Connection conn, String orgName,
			String occStatus, Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId)
			throws SQLException, DataException {
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		workauth.orgName.setValue(orgName);
		workauth.occStatus.setValue(occStatus);
		workauth.workPro.setValue(workPro);
		workauth.workCity.setValue(workCity);
		workauth.companyType.setValue(companyType);
		workauth.companyLine.setValue(companyLine);
		workauth.companyScale.setValue(companyScale);
		workauth.job.setValue(job);
		workauth.monthlyIncome.setValue(monthlyIncome);
		workauth.workYear.setValue(workYear);
		workauth.companyTel.setValue(companyTel);
		workauth.workEmail.setValue(workEmail);
		workauth.companyAddress.setValue(companyAddress);
		workauth.directedName.setValue(directedName);
		workauth.directedRelation.setValue(directedRelation);
		workauth.directedTel.setValue(directedTel);
		workauth.otherName.setValue(otherName);
		workauth.otherRelation.setValue(otherRelation);
		workauth.otherTel.setValue(otherTel);
		workauth.moredName.setValue(moredName);
		workauth.moredRelation.setValue(moredRelation);
		workauth.moredTel.setValue(moredTel);
		DataSet PersondataSet = workauth.open(conn, "", "userId = " + userId,
				"", -1, -1);
		Map<String, String> map = new HashMap<String, String>();
		try{
		map = BeanMapUtils.dataSetToMap(PersondataSet);}
		catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		if (map != null && map.size() > 0) {
			return workauth.update(conn, "userId = " + userId);
		} else {
			workauth.userId.setValue(userId);
			return workauth.insert(conn);

		}

	}

	/**
	 * 修改用户
	 * 
	 * @param conn
	 * @param id
	 *            用户编号
	 * @param email
	 *            电子邮箱
	 * @param userName
	 *            用户名
	 * @param password
	 *            用户密码
	 * @param name
	 *            真实姓名
	 * @param gender
	 *            性别
	 * @param mobilePhone
	 *            手机号码
	 * @param qq
	 * @param provinceId
	 *            省Id
	 * @param cityId
	 *            城市id
	 * @param areaId
	 *            区/镇/县id
	 * @param postalcode
	 *            邮政编码
	 * @param headImg
	 *            头像
	 * @param status
	 *            邮箱是否验证通过 (0:未通过1:通过)
	 * @param balances
	 *            E币账户余额
	 * @param enable
	 *            是否禁用 1、启用 2、禁用
	 * @param rating
	 *            会员等级(1:普通会员2:铜牌会员3:银牌会员4:金牌会员)
	 * @param lastDate
	 *            最后登录时间
	 * @param lastIP
	 *            最后登录ip
	 * @throws SQLException
	 * @return Long
	 */
	public Long updateUser(Connection conn, Long id, String email,
			String userName, String password, String lastDate, String lastIP)
			throws SQLException {

		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if (email != null) {
			user.email.setValue(email);
		}
		if (password != null) {
			password = com.shove.security.Encrypt.MD5(password.trim());
			user.password.setValue(password);
		}
		if (userName != null) {
			user.username.setValue(userName);
		}

		return user.update(conn, " id=" + id);
	}

	// 更新用户的认证状态
	public long updateUser(Connection conn, long userId,
			Map<String, String> userMap) throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DBReflectUtil.mapToTableValue(user, userMap);

		return user.update(conn, "id=" + userId);
	}
	// 更新用户的申请密码保护状态 默认是1，表示还没有申请，2表示已经申请
	public Long updatePwdProState(Connection conn, Long userId)
			throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.isApplyPro.setValue(2);
		return user.update(conn, "id = " + userId);

	}

	// 更新用户的vip状态
	public Long updateUser(Connection conn, Long uerId, int authStep,
			int vipStatus, int servicePersonId, String content, String vipFee)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.authStep.setValue(authStep);
		user.kefuId.setValue(servicePersonId);
		user.content.setValue(content);
		user.vipCreateTime.setValue(new Date());
		user.vipStatus.setValue(2);// 修改vip状态 2为vip状态
		BigDecimal vipFeedecimal = new BigDecimal(vipFee);
		user.vipFee.setValue(vipFeedecimal);
		//----modify by houli 申请vip时，feeStatus=1 1为代扣  2为已扣
		//user.feeStatus.setValue(2);//为扣费用
		user.feeStatus.setValue(1);
		//---------------
		return user.update(conn, "id = " + uerId);
	}

	public int deleteUser(Connection conn, String userIds, String delimiter)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.delete(conn, "id=" + userIds);
		return 0;
	}

	/**
	 * 判断是否用户或者email重复
	 * 
	 * @param conn
	 * @param email
	 * @param userName
	 * @throws SQLException
	 * @throws DataException
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> isUserEmaiORUseName(Connection conn,
			String email, String userName) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		email=com.shove.web.Utility.filteSqlInfusion(email);
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		condition.append(" 1=1");
		if (StringUtils.isNotBlank(email)) {
			condition.append(" AND email= '" + StringEscapeUtils.escapeSql(email) + "'");
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName= '" + StringEscapeUtils.escapeSql(userName) + "'");
		}

		DataSet dataSet = user
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	// ===============================================================
	/**
	 * 用户登录时候验证邮箱和用户名是否激活==========================
	 */
	public List<Map<String, Object>> isUEjihuo(Connection conn, String email,
			String userName) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		email=com.shove.web.Utility.filteSqlInfusion(email);
		condition.append(" 1=1");
		// 邮箱不空 用户名空
		if (StringUtils.isNotBlank(email) && !StringUtils.isNotBlank(userName)) {
			condition.append(" AND email= '" + StringEscapeUtils.escapeSql(email) + "' AND enable = 2");
			// 邮箱和用户名都不空
		} else if (StringUtils.isNotBlank(email)
				&& StringUtils.isNotBlank(userName)) {
			condition.append(" AND email= '" + StringEscapeUtils.escapeSql(email) + "'");
		}
		// 邮箱空 用户名不空
		if (!StringUtils.isNotBlank(email) && StringUtils.isNotBlank(userName)) {
			condition
					.append(" AND userName= '" + StringEscapeUtils.escapeSql(userName) + "' AND enable = 2");
		}

		DataSet dataSet = user
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	// 没有检测enable状态
	public List<Map<String, Object>> isUEjihuo_(Connection conn, String email,
			String userName) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		email=com.shove.web.Utility.filteSqlInfusion(email);
		condition.append(" 1=1");
		if (StringUtils.isNotBlank(email)) {
			condition.append(" AND email= '" + StringEscapeUtils.escapeSql(email) + "'");
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName= '" + StringEscapeUtils.escapeSql(userName) + "'");
		}

		DataSet dataSet = user
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 用户登录时候验证邮箱和用户名是否激活==========================
	 */
	// ======================================================
	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param conn
	 * @param id
	 *            用户编号
	 * @throws SQLException
	 * @throws DataException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet dataSet = user.open(conn, "*", " id=" + id, " id desc ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/***
	 * 判断发标人是否为小贷公司高级用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAdminUserPublisher(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_admin_user where userId = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	 /**
	  * 查询合作机构
	  * @param conn
	  * @param adminId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	public Map<String, String> queryLoanCompany(Connection conn, long adminId)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_company_loan where user_id = "+adminId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	 /**
	  * 查询合作机构
	  * @param conn
	  * @param adminId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	public Map<String, String> queryBondingCompany(Connection conn, long loan_comp_id,long bonding_comp_id )
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.id as loan_bonding_id, b.model_type as model_type,b.desc as loan_bonding_desc,b.bonding_letter_path as bonding_company_letter_path, "
				 + " c.id as bonding_id,c.name as bonding_name ,c.desc as bonding_desc ,c.bonding_level as bonding_level "
				 + " from t_company_loan a,t_company_loan_bonding b,t_company_bonding c "
				 + " where a.id = b.loan_comp_id and b.bonding_comp_id = c.id and b.loan_comp_id = "+ loan_comp_id +" and b.bonding_comp_id ="+bonding_comp_id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	 /**
	  * 查询受让人基本信息
	  * @param conn
	  * @param adminId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	public Map<String, String> findUserBase(Connection conn, long userId)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " select  a.email,a.mobilePhone,b.realName,b.idNo from t_user a , t_person b where a.id = b.userId and a.id= "+userId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	 /**
	  * 查询用户可用余额
	  * @param conn
	  * @param adminId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	public Map<String, String> findUserAbleMoney(Connection conn, long userId)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " select a.usableSum from t_user a where a.id= "+userId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	  * 更新用户可用余额
	  * @param conn
	  * @param adminId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	public long  updateUserAbleMoney(Connection conn, long userId,double userFinallyAbleMoney)
			throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		t_user.usableSum.setValue(userFinallyAbleMoney);
		return t_user.update(conn, "id="+userId);
	}
	
	
	public long addChannelCostRecond(Connection conn,long borrowId,String borrowName,long loanId,String loanName,double channelCost,Date createTime)throws SQLException, DataException{
		Dao.Tables.t_channel_cost_recond t_channel_cost_recond = new Dao().new Tables().new t_channel_cost_recond();
		t_channel_cost_recond.borrowId.setValue(borrowId);
		t_channel_cost_recond.borrowName.setValue(borrowName);
		t_channel_cost_recond.loandId.setValue(loanId);
		t_channel_cost_recond.loanName.setValue(loanName);
		t_channel_cost_recond.channelCost.setValue(channelCost);
		t_channel_cost_recond.createTime.setValue(createTime);
		return t_channel_cost_recond.insert(conn);
	}
	
	/**
	 * 添加偿债总金额
	 * @param conn
	 * @param pay_debt_money
	 * @param user_id
	 * @param debt_id
	 * @param creat_time
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addPayDebt(Connection conn,double pay_debt_money_sum,long loan_id,Date creat_time)throws SQLException, DataException{
		Dao.Tables.t_pay_debt t_pay_debt = new Dao().new Tables().new t_pay_debt();
		t_pay_debt.pay_debt_money_sum.setValue(pay_debt_money_sum);
		t_pay_debt.creat_time.setValue(creat_time);
		t_pay_debt.loan_id.setValue(loan_id);
		return t_pay_debt.insert(conn);
	}
	
	/**
	 * 查询合作机构 偿债总金额
	 * @param conn
	 * @param loan_id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> findPayDebt(Connection conn,long loan_id)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append( " select * from t_pay_debt a where a.loan_id='"+loan_id+"'");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/***
	 * 更新合作机构 偿债总金额
	 * @param conn
	 * @param id
	 * @param userFinallyAbleMoney
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long  updatePayDebt(Connection conn,double pay_debt_money_sum, long id)
			throws SQLException, DataException {
		Dao.Tables.t_pay_debt t_pay_debt = new Dao().new Tables().new t_pay_debt();
		t_pay_debt.pay_debt_money_sum.setValue(pay_debt_money_sum);
		return t_pay_debt.update(conn, "id="+id);
	}
	
	/**
	 * 添加偿债金额记录
	 * @param conn
	 * @param pay_debt_money
	 * @param user_id
	 * @param debt_id
	 * @param creat_time
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addPayDebtRecode(Connection conn,double pay_debt_money,long borrow_id,long debt_id,Date creat_time)throws SQLException, DataException{
		Dao.Tables.t_pay_debt_recode t_pay_debt_recode = new Dao().new Tables().new t_pay_debt_recode();
		t_pay_debt_recode.pay_debt_money.setValue(pay_debt_money);
		t_pay_debt_recode.creat_time.setValue(creat_time);
		t_pay_debt_recode.borrow_id.setValue(borrow_id);
		t_pay_debt_recode.debt_id.setValue(debt_id);
		return t_pay_debt_recode.insert(conn);
	}
	
	/**
	 * 添加借款人可查询的资金记录
	 * @param conn
	 * @param userId
	 * @param fundMode
	 * @param handleSum
	 * @param usableSum
	 * @param remarks
	 * @param oprateType
	 * @param speeding
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long saveChanncelCostFundrecode(Connection conn,long userId,String fundMode,double handleSum,double usableSum,
			String remarks,double speeding)throws SQLException, DataException{
		Dao.Tables.t_fundrecord t_fundrecord = new Dao().new Tables().new t_fundrecord();
		t_fundrecord.userId.setValue(userId);
		t_fundrecord.fundMode.setValue(fundMode);
		t_fundrecord.handleSum.setValue(handleSum);
		t_fundrecord.usableSum.setValue(usableSum);
		t_fundrecord.freezeSum.setValue(0);
		t_fundrecord.dueinSum.setValue(0);
		t_fundrecord.trader.setValue(-1);
		t_fundrecord.recordTime.setValue(new Date());
		t_fundrecord.dueoutSum.setValue(0);
		t_fundrecord.remarks.setValue(remarks);
		t_fundrecord.operateType.setValue(601);
		t_fundrecord.oninvest.setValue(0);
		t_fundrecord.cost.setValue(0);
		t_fundrecord.income.setValue(0);
		t_fundrecord.spending.setValue(handleSum);
		t_fundrecord.repayment_id.setValue(-1);
		return t_fundrecord.insert(conn);
	}
	
	public long saveChanncelCostFundrecode1(Connection conn,long userId,String fundMode,double handleSum,double usableSum,
			String remarks,double income)throws SQLException, DataException{
		Dao.Tables.t_fundrecord t_fundrecord = new Dao().new Tables().new t_fundrecord();
		t_fundrecord.userId.setValue(userId);
		t_fundrecord.fundMode.setValue(fundMode);
		t_fundrecord.handleSum.setValue(handleSum);
		t_fundrecord.usableSum.setValue(usableSum);
		t_fundrecord.freezeSum.setValue(0);
		t_fundrecord.dueinSum.setValue(0);
		t_fundrecord.trader.setValue(-1);
		t_fundrecord.recordTime.setValue(new Date());
		t_fundrecord.dueoutSum.setValue(0);
		t_fundrecord.remarks.setValue(remarks);
		t_fundrecord.operateType.setValue(601);
		t_fundrecord.oninvest.setValue(0);
		t_fundrecord.cost.setValue(0);
		t_fundrecord.income.setValue(income);
		t_fundrecord.spending.setValue(0);
		t_fundrecord.repayment_id.setValue(-1);
		return t_fundrecord.insert(conn);
	}
	/***
	 * 根据用户名查询配置好的的存放渠道费金额的用户
	 * @param conn
	 * @param uName
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> findUserByuName(Connection conn,String uName)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append( " select * from t_user a where a.username='"+uName+"'");
		System.out.println(sql.toString());
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/***
	 * 更合作机构户的授信可用金额和授信已用金额
	 * @param conn
	 * @param id
	 * @param availableTotalAmountSum
	 * @param hasTotalAmountSum
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long  updateLoanCompanyMoney(Connection conn,long id,double availableTotalAmountSum,double hasTotalAmountSum)
			throws SQLException, DataException {
		Dao.Tables.t_company_loan t_company_loan = new Dao().new Tables().new t_company_loan();
		t_company_loan.available_total_amount.setValue(availableTotalAmountSum);
		t_company_loan.has_total_amount.setValue(hasTotalAmountSum);
		return t_company_loan.update(conn, "id="+id);
	}
	/**
	  * 查询逐笔审批的担保函
	  * @param conn
	  * @param adminId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	public Map<String, String> queryFinanceById(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " select a.bonding_letter_path as bonding_letter_path,a.bonding_id as bonding_id from  t_borrow a where a.id ="+ id );
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询合作机构的抵押信息type=1
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryLoanCompanyMortgPic(Connection conn, Long id) throws SQLException, DataException {
		Dao.Tables.t_company_data t_company_data = new Dao().new Tables().new t_company_data();
		StringBuffer condition = new StringBuffer();
		condition.append("  1=1 and  loan_id = " + id + " and type= 1 ");
		DataSet dataSet = t_company_data.open(conn, "*",condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询合作机构的基本资料type=2
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryLoanCompanyBasePic(Connection conn, Long id) throws SQLException, DataException {
		Dao.Tables.t_company_data t_company_data = new Dao().new Tables().new t_company_data();
		StringBuffer condition = new StringBuffer();
		condition.append("  1=1 and  loan_id = " + id + " and type= 2 ");
		DataSet dataSet = t_company_data.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 根据用户name查询用户信息
	 * 
	 * @param conn
	 * @param userName
	 *            用户姓名
	 * @throws SQLException
	 * @throws DataException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserByUserName(Connection conn, String userName)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet dataSet = user.open(conn, "*", " username = '" + StringEscapeUtils.escapeSql(userName) + "'" +
				" or email = '" + StringEscapeUtils.escapeSql(userName) + "'" +
				" or mobilePhone = '" + StringEscapeUtils.escapeSql(userName) + "'", " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 查询用户前台的图片信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserPictureStatus(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_user_frontpictur frontMeg = new Dao().new Views().new v_t_user_frontpictur();
		DataSet dataSet = frontMeg.open(conn, "", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户前台五大基本资料信息和显示详细图片的第一张
	 * 
	 * @param conn
	 * @param id
	 *            用户的id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryBasePicture(Connection conn, Long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.userId as id, ");
		sql.append(" tuser.username username, ");
		sql.append(" tmy.`name` as tmyname, ");
		sql
				.append(" tm.auditStatus as auditStatus,tm.id as tmid,tmy.id as tmyid,vts.imagePath as imagePath ");
		sql.append(" from t_materialsauth tm  ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId  = tmy.id   ");
		sql.append(" left join t_user tuser on tuser.id = tm.userId ");
		sql
				.append(" left join v_t_user_showfirstpicture vts on vts.tmid = tm.id ");
		sql.append(" where tmy.id <= 5 AND  tuser.id =  " + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询用户后台五大基本资料信息和显示详细图片的第一张
	 * queryBasePictureAdmin
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-6 下午08:45:59
	 */
	public List<Map<String, Object>> queryBasePictureAdmin(Connection conn, Long id)
	throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.userId as id, ");
		sql.append(" tmy.`name` as tmyname, ");
		sql
				.append(" tm.auditStatus as auditStatus,tm.id as tmid,tmy.id as tmyid,vts.imagePath as imagePath ");
		sql.append(" from t_materialsauth tm  ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId  = tmy.id   ");
		sql
				.append(" left join v_t_user_showfirstpicture vts on vts.tmid = tm.id ");
		sql.append(" where tmy.id <= 5 AND  tm.userId =  " + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 查询企业五项认证
	 * queryEnterprisePicture
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-9 下午03:06:37
	 */
	public List<Map<String, Object>> queryEnterprisePicture(Connection conn, Long id)
	throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.userId as id, ");
		sql.append(" tmy.`name` as tmyname, ");
		sql
				.append(" tm.auditStatus as auditStatus,tm.id as tmid,tmy.id as tmyid,vts.imagePath as imagePath ");
		sql.append(" from t_materialsauth tm  ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId  = tmy.id   ");
		sql
				.append(" left join v_t_user_showfirstpicture vts on vts.tmid = tm.id ");
		sql.append(" where tmy.id > 16 AND  tm.userId =  " + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * 查询用户前台可选大基本资料信息和显示详细图片的第一张
	 * 
	 * @param conn
	 * @param id
	 *            用户的id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> querySelectPicture(Connection conn, Long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.userId as id, ");
		sql.append(" tuser.username username, ");
		sql.append(" tmy.`name` as tmyname, ");
		sql
				.append(" tm.auditStatus as auditStatus,tm.id as tmid,tmy.id as tmyid,vts.imagePath as imagePath ");
		sql.append(" from t_materialsauth tm  ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId  = tmy.id   ");
		sql.append(" left join t_user tuser on tuser.id = tm.userId ");
		sql
				.append(" left join v_t_user_showfirstpicture vts on vts.tmid = tm.id ");
		sql.append(" where tmy.id > 5 AND  tuser.id =  " + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询用户后台可选大基本资料信息和显示详细图片的第一张
	 * 
	 * @param conn
	 * @param id
	 *            用户的id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> querySelectPictureAdmin(Connection conn, Long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.userId as id, ");
		sql.append(" tmy.`name` as tmyname, ");
		sql
				.append(" tm.auditStatus as auditStatus,tm.id as tmid,tmy.id as tmyid,vts.imagePath as imagePath ");
		sql.append(" from t_materialsauth tm  ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId  = tmy.id   ");
		sql
				.append(" left join v_t_user_showfirstpicture vts on vts.tmid = tm.id ");
		sql.append(" where tmy.id > 5 AND  tm.userId =  " + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}


	/**
	 * 查询每一个证件类型下的明细
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryPerTyhpePicture(Connection conn,
			Long tmid) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tmd.id as id,tmd.imagePath, ");
		sql
				.append(" tmd.auditStatus as auditStatus ,tmy.`name` as tmyname,tm.materAuthTypeId as materAuthTypeId,tmd.visiable as visiable  ");
		sql.append(" from t_materialimagedetal tmd ");
		sql
				.append(" left join t_materialsauth tm on  tmd.materialsauthid = tm.id ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId = tmy.id ");
		sql.append(" where tmd.materialsauthid =  " + tmid);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 查询图片类型
	 * 
	 * @param conn
	 * @param tmId
	 *            证件主表类型的id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPitcturTyep(Connection conn, Long tmId,
			Long userId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select tm.materAuthTypeId  from t_materialsauth tm  where tm.userId = "
						+ userId + " and tm.id = " + tmId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 计算用户的图片上传个数基本资料的上传个数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPicturStatuCount(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_user_verypictur verypictur = new Dao().new Views().new v_t_user_verypictur();
		DataSet dataSet = verypictur.open(conn, "", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 后台发标计算个人用户的图片上传个数基本资料的上传个数
	 * queryPicturStatuCountAdmin
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-7 下午01:30:45
	 */
	public Map<String, String> queryPicturStatuCountAdmin(Connection conn, long id)
	throws SQLException, DataException {
		Dao.Views.v_t_user_verypictur_admin verypictur = new Dao().new Views().new v_t_user_verypictur_admin();
		DataSet dataSet = verypictur.open(conn, "", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 后台发标计算企业用户的图片上传个数基本资料的上传个数
	 * queryEnterprisePicturStatuCountAdmin
	 * @param conn
	 * @param id
	 * @param type
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-9 下午03:50:11
	 */
	public Map<String, String> queryEnterprisePicturStatuCountAdmin(Connection conn, long id)
	throws SQLException, DataException {
		Dao.Views.v_t_enterprise_user_verypictur verypictur = new Dao().new Views().new v_t_enterprise_user_verypictur();
		DataSet dataSet = verypictur.open(conn, "", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 根据用户名查用户id
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryIdByUser(Connection conn, String userName)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		DataSet dataSet = user.open(conn, "id", " username = '" + StringEscapeUtils.escapeSql(userName) + "'" +
				" or email = '" + StringEscapeUtils.escapeSql(userName) + "'" +
				" or mobilePhone = '" + StringEscapeUtils.escapeSql(userName) + "'",
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询图片id
	 * 
	 * @param conn
	 * @param tmId
	 *            证件主表类型的id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPitcturId(Connection conn, Long tmId,
			Long userId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select tm.id as id from t_materialsauth tm  where tm.userId = "
						+ userId + " and tm.materAuthTypeId = " + tmId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 忘记密码
	 * 
	 * @param conn
	 * @param username
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPassword(Connection conn, String email)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		email=com.shove.web.Utility.filteSqlInfusion(email);
		DataSet dataSet = user.open(conn, "", " email = '" + StringEscapeUtils.escapeSql(email) + "'", "",
				-1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 客服列表的内容
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> querykefylist(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_user_kefu kefu = new Dao().new Tables().new t_user_kefu();
		DataSet dataSet = kefu.open(conn, "", "  ", " ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 所有密码安全提问的内容
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAllQuestionList(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_pwd_requestion question = new Dao().new Tables().new t_pwd_requestion();
		DataSet dataSet = question.open(conn, "", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	
	
	/**
	 * 用户基本信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPersonById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		DataSet dataSet = person.open(conn, "", "userId = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryUserYxById(Connection conn, Long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select b.tell from t_user a ,t_user_yx b where a.id = b.user_id and a.id = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询用户信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPersonByIdNo(Connection conn, String idNo)
			throws SQLException, DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		DataSet dataSet = person.open(conn, "", "idNo = " + idNo, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 企业用户基本信息
	 * queryEnterpriseById
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-9 上午11:31:15
	 */
	public Map<String, String> queryEnterpriseById(Connection conn, long id)
	throws SQLException, DataException {
		Dao.Tables.t_enterprise t_enterprise = new Dao().new Tables().new t_enterprise();
		DataSet dataSet = t_enterprise.open(conn, "", "userId = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询用户所有密保答案
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAnswerByUserId(Connection conn, long userId)
			throws SQLException, DataException {
		Dao.Tables.t_pwd_answer answer = new Dao().new Tables().new t_pwd_answer();
		DataSet dataSet = answer.open(conn, "", "userId = " + userId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 查询用户的工作状态值
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryWorkState(Connection conn, long id)
			throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"select COUNT(*) as 'total'  from t_materialsauth tm where tm.userId = "
								+ id
								+ " AND tm.auditStatus = 3  AND tm.materAuthTypeId > 5");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询vip页面状态参数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryVipParamList(Connection conn, long id)
			throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"SELECT tuk.id as kfid, tuser.id as id ,tuser.username as username,tuser.email as email,tuser.vipStatus as vipStatus,tp.realName as realName,tuk.`name` as kefuname from t_user tuser LEFT join t_person tp on tuser.id = tp.userId left join t_user_kefu tuk on tuser.kefuId = tuk.id where tuser.id = "
								+ id);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 该用户上传资料的类型的审核状态
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public Long updateUserPicturStatus(Connection conn, Long id,
			Long materAuthTypeId) throws SQLException, DataException {
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
			materialsauth.auditStatus.setValue(1);// 设置主表的状态值1 为等待审核
			materialsauth.passTime.setValue(new Date());
			return materialsauth.update(conn, " userId = " + id
					+ " AND materAuthTypeId = " + materAuthTypeId);
	}

	/**
	 * 后台发标更新该用户上传资料的类型的审核状态
	 * updateUserPicturStatusAdmin
	 * @param conn
	 * @param id
	 * @param materAuthTypeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-7 上午10:40:02
	 */
	public Long updateUserPicturStatusAdmin(Connection conn, Long id,
			Long materAuthTypeId) throws SQLException, DataException {
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
			materialsauth.auditStatus.setValue(3);// 设置主表的状态值1 为等待审核
			materialsauth.passTime.setValue(new Date());
			materialsauth.authTime.setValue(new Date());
			return materialsauth.update(conn, " userId = " + id
					+ " AND materAuthTypeId = " + materAuthTypeId);
	}
	
	/**
	 * 增加用户的图片
	 * 
	 * @param conn
	 * @param id
	 * @param pasttime
	 * @param materAuthTypeId
	 * @param auditStatus
	 * @param uploadingTime
	 * @param imagePath
	 * @param materialsauthid
	 * @return
	 * @throws SQLException
	 */
	public synchronized Long  addUserImage(Connection conn, Integer auditStatus,
			String uploadingTime, String imagePath, Long materialsauthid,Integer visable)
			throws SQLException {
		Dao.Tables.t_materialimagedetal materialImagedetal = new Dao().new Tables().new t_materialimagedetal();
		materialImagedetal.auditStatus.setValue(auditStatus);
		materialImagedetal.imagePath.setValue(imagePath);
		materialImagedetal.visiable.setValue(visable);
		materialImagedetal.uploadingTime.setValue(new Date());
		materialImagedetal.materialsauthid.setValue(materialsauthid);
		return materialImagedetal.insert(conn);
	}

	/**
	 * 重置图片的可见性 重置为不可见
	 * 
	 * @param conn
	 * @param tmid
	 * @return
	 * @throws SQLException
	 */
	public Long updatematerialImagedetalvisiable(Connection conn, Long tmid)
			throws SQLException {
		Dao.Tables.t_materialimagedetal materialImagedetal = new Dao().new Tables().new t_materialimagedetal();
		materialImagedetal.visiable.setValue(1);
		return materialImagedetal.update(conn, " materialsauthid = " + tmid);
	}

	/**
	 * 更新用户上传图片的可见性
	 * 
	 * @param conn
	 * @param tmdid
	 * @return
	 * @throws SQLException
	 */
	public Long updatevisiable(Connection conn, Long tmdid) throws SQLException {
		Dao.Tables.t_materialimagedetal Imagedetal = new Dao().new Tables().new t_materialimagedetal();
		Imagedetal.visiable.setValue(1);// 1为可见 2 为不可见
		return Imagedetal.update(conn, " id = " + tmdid);
	}

	/**
	 * 修改用户邮箱验证状态
	 * 
	 * @param conn
	 * @param id
	 *            用户编号
	 * @param status
	 *            邮箱是否验证通过 (0:未通过1:通过)
	 * @throws SQLException
	 * @return Long
	 */
	public Long updateUserEmailStatus(Connection conn, Long id, Integer status)
			throws SQLException {

		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if (status != null) {
			// user.status.setValue(status);
		}
		return user.update(conn, " id=" + id);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param conn
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserPassword(Connection conn, Long id, String password)
			throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if ("1".equals(IConstants.ENABLED_PASS)){
			password = com.shove.security.Encrypt.MD5(password.trim());
		}else{
			password = com.shove.security.Encrypt.MD5(password.trim()+IConstants.PASS_KEY);
		}
		user.password.setValue(password);
		return user.update(conn, " id=" + id);
	}
	
	public Long updateUniqueBankCardMark(Connection conn, Long id)
			throws SQLException {
		Dao.Tables.t_bankcard t_bankcard = new Dao().new Tables().new t_bankcard();
		t_bankcard.unique_mark.setValue(1);
		return t_bankcard.update(conn, " id=" + id);
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
	public Long updateEnable(Connection conn, Long id, Integer enable)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if (enable != null) {
			user.enable.setValue(enable);
		}
		return user.update(conn, " id=" + id);

	}
	
	public Long updateAuthStep(Connection conn, Long id, Integer authStep)
	throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if (authStep != null) {
			user.authStep.setValue(authStep);
		}
		return user.update(conn, " id=" + id);
		
	}

	/**
	 * 更新锁定用户状态
	 * 
	 * @param conn
	 * @param ids
	 * @param i
	 * @return
	 * @throws Exception 
	 */
	public Long updateLockedStatus(Connection conn, String ids, int enable)
			throws Exception {
		ids=com.shove.web.Utility.filteSqlInfusion(ids);
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+ new DesSecurityUtil().decrypt(array[n]);
		}
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.enable.setValue(enable);
		if (enable == 1) {
			user.lockTime.setValue(null);
		} else {
			user.lockTime.setValue(new Date());
		}
		return user.update(conn, " id in (" +idSQL
				+ ")");
	}

	/**
	 * 更新锁定提现状态
	 * 
	 * @param conn
	 * @param ids
	 * @param cashStatus
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserCashStatus(Connection conn, String ids,
			String cashStatus) throws SQLException {
		ids=com.shove.web.Utility.filteSqlInfusion(ids);
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.cashStatus.setValue(StringEscapeUtils.escapeSql(cashStatus));
		return user.update(conn, " id in (" + idSQL
				+ ")");
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param conn
	 * @param userNames
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryUserIdByUserName(Connection conn,
			String userNames) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		userNames = userNames.replaceAll("'", "");
		String[] userName = userNames.split(",");
		StringBuilder newUserName = new StringBuilder();
		for (String name : userName) {
			newUserName.append("'");
			newUserName.append(name);
			newUserName.append("',");
		}
		int length = newUserName.length();
		if (length > 0) {
			newUserName.delete(length - 1, length);
		}
		if (StringUtils.isNotBlank(userNames)) {
			DataSet ds = user.open(conn, "id,username", " username in ("
					+ newUserName.toString() + ")", "", -1, -1); 
			ds.tables.get(0).rows.genRowsMap();
			return ds.tables.get(0).rows.rowsMap;
		}
		return new ArrayList<Map<String, Object>>();
	}

	public List<Map<String, Object>> queryUserAll(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet ds = user.open(conn, "userName,email ,password ", "", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	/**
	 * 判断用户是否为VIP
	 * @param conn
	 * @param userId
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public boolean isVip(Connection conn, long userId) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet ds = user.open(conn, "  vipStatus  ", " id="+userId, "", -1, -1);
		Map<String,String> map = BeanMapUtils.dataSetToMap(ds);
		boolean isVip = false;
		if(map != null){
			long vipStatus = Convert.strToLong(map.get("vipStatus"), -1);
			if(vipStatus == 2){
				isVip = true;
			}
		}
		return isVip;
	}
	
	/**
	 * @MethodName: updateInvestorSum
	 * @Param: FrontMyPaymentDao
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午05:01:09
	 * @Return:
	 * @Descb: 给用户添加可用资金
	 * @Throws:
	 */
	public long addUserUsableAmount(Connection conn, double amount, long userId)
			throws SQLException {
		long returnId = -1;
		String command = "update t_user set usableSum = usableSum + " + amount
				+ " where id =" + userId;
		returnId = MySQL.executeNonQuery(conn, command.toString());
		command = null;
		return returnId;
	}
	
	
	public Map<String, String> queryUserByUserAndPwd(Connection conn,
			String userName, String pwd) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		pwd=com.shove.web.Utility.filteSqlInfusion(pwd);
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		DataSet ds = t_user.open(conn,
				"id,username,headImg,enable,vipStatus,email,authStep,usableSum,freezeSum,isLoginLimit ",
				" (email ='" + StringEscapeUtils.escapeSql(userName.trim())
						+ "' OR username='"
						+ StringEscapeUtils.escapeSql(userName.trim())
						+ "' or mobilePhone='"+ StringEscapeUtils.escapeSql(userName.trim())
						+"') AND password = '" + StringEscapeUtils.escapeSql(pwd)
						+ "' AND enable != 2", "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 更改登录密码
	 * @param conn
	 * @param id
	 * @param password
	 * @param type 1为登录密码，2为交易密码
	 * @return
	 * @throws SQLException
	 */
	public long updatePwd(Connection conn, Long id, String password,long type) throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if(type == 1){
			user.password.setValue(password);
		}else if(type == 2){
			user.dealpwd.setValue(password);
		}else{
			return 0;
		}
		
		return user.update(conn, " id=" + id);
	}
	
	public long queryUserIdByPhone(Connection conn, String cellPhone) throws SQLException, DataException {
		StringBuilder command = new StringBuilder();
		cellPhone=com.shove.web.Utility.filteSqlInfusion(cellPhone);
		command.append("select u.id from t_user u left JOIN t_person p on p.userId=u.id ");
		command.append(" LEFT JOIN t_phone_binding_info pb on pb.userId=u.id where u.mobilePhone = '");
		command.append(StringEscapeUtils.escapeSql(cellPhone));
		command.append("' or pb.mobilePhone='");
		command.append(StringEscapeUtils.escapeSql(cellPhone));
		command.append("' or p.cellPhone='");
		command.append(StringEscapeUtils.escapeSql(cellPhone));
		command.append("' ");
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		Map<String,String> map = BeanMapUtils.dataSetToMap(ds);
		long userId = -1;
		if(map != null){
			userId = Convert.strToLong(map.get("id"), -1);
		}
		return userId;
	}
	public Map<String,String> queryUserBindphone(Connection conn,long userId) throws DataException, SQLException{
		StringBuilder command = new StringBuilder();
		command.append("select u.id as userId ,u.mobilePhone as mobilePhone,p.cellPhone as cellPhone,pb.status as status,pb.mobilePhone as bindingPhone from t_user u left JOIN t_person p on p.userId=u.id ");
		command.append(" LEFT JOIN t_phone_binding_info pb on pb.userId=u.id where u.id = "+userId);
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		Map<String,String> map = BeanMapUtils.dataSetToMap(ds);
		return map;
	}
	
//	public List<Map<String, Object>> isPhoneExist(Connection conn,
//			String cellphone) throws SQLException, DataException {
//		
//		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
//		StringBuffer condition = new StringBuffer();
//		cellphone=com.shove.web.Utility.filteSqlInfusion(cellphone);
//		condition.append(" 1=1 ");
//		if (StringUtils.isNotBlank(cellphone)) {
//			condition.append("AND mobilePhone= '"+ StringEscapeUtils.escapeSql(cellphone.trim())+ "' or username= '"+ StringEscapeUtils.escapeSql(cellphone.trim())+ "' AND enable = 1 ");
//		}
//		DataSet dataSet = user
//				.open(conn, "*", condition.toString(), "", -1, -1);
//		dataSet.tables.get(0).rows.genRowsMap();
//		return dataSet.tables.get(0).rows.rowsMap;
//	}
	
    public List<Map<String, Object>> isPhoneExist(Connection conn, String cellphone) throws SQLException, DataException {
        Dao.Tables.t_person person = new Dao().new Tables().new t_person();
        DataSet dataSet = person.open(conn, "*", " cellPhone = '"+ StringEscapeUtils.escapeSql(cellphone.trim())+ "' ", "", -1, -1);
        dataSet.tables.get(0).rows.genRowsMap();
        return dataSet.tables.get(0).rows.rowsMap;
    }
	
//    public List<Map<String, Object>> isPhoneExist(Connection conn, 
//    String cellphone) throws SQLException, DataException { 
//    DataSet dataSet = MySQL.executeQuery(conn," select u.id,u.username from t_user u left join t_person p on u.id = p.userId where p.cellPhone = "  + StringEscapeUtils.escapeSql(cellphone.trim()) + "  and u.enable = 1"); 
//    dataSet.tables.get(0).rows.genRowsMap(); 
//    return dataSet.tables.get(0).rows.rowsMap; 
//    }
	
	// 通过手机更改用户手机号码
	public Long updatepasswordBycellphone(Connection conn, String cellphone,
			String password) throws SQLException {
		cellphone=com.shove.web.Utility.filteSqlInfusion(cellphone);
		password=com.shove.web.Utility.filteSqlInfusion(password);
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.password.setValue(com.shove.security.Encrypt.MD5(password.trim()+IConstants.PASS_KEY));
		return user.update(conn, " mobilePhone = " + StringEscapeUtils.escapeSql(cellphone.trim()));
	}
	
	public Map<String, String> queryUserAmount(Connection conn, Long userId)
	throws DataException, SQLException {
	DataSet dataSet = MySQL.executeQuery(conn," select usableSum from t_user where id = " + userId);
	return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 更改邮箱byid
	 * @throws SQLException 
	 */
	public long updateEmalByid(Connection conn,long id,String email) throws SQLException{
		email=com.shove.web.Utility.filteSqlInfusion(email);
		return MySQL.executeNonQuery(conn, " update t_user set t_user.email = '"+StringEscapeUtils.escapeSql(email)+"' where id = "+id);
	}
	/**
	 * @MethodName: deducteUserUsableAmount
	 * @Param: UserDao
	 * @Author: gang.lv
	 * @Date: 2013-5-22 上午11:57:35
	 * @Return:
	 * @Descb: 扣除用户可用资金
	 * @Throws:
	 */
	public long deducteUserUsableAmount(Connection conn, double amount,
			long userId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_user set usableSum = usableSum - " + amount
				+ " where id =" + userId);
		return MySQL.executeNonQuery(conn, command.toString());
	}
	/**
	 * @MethodName: queryUserAmountAfterHander
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午09:11:22
	 * @Return:
	 * @Descb: 查询用户 的资金
	 * @Throws:
	 */
	public Map<String, String> queryUserAmountAfterHander(Connection conn,
			long userId) throws SQLException, DataException {
		String command ="select a.usableSum,a.freezeSum,sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest) forPI "
		+" from t_user a left join t_invest b on a.id = b.investor where a.id="
		+ userId + " group by a.id";
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * 激活账户
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public long updateUserActivate(Connection conn, long userId) throws SQLException{
		 Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		 t_user.enable.setValue(1);
		 return t_user.update(conn, " id = "+ userId);
	}


	public long setSign(Connection conn, long userId, String sign, String sign2) throws SQLException {
		Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		t_user.sign.setValue(sign);
		t_user.sign2.setValue(sign2);
		return t_user.update(conn, " id = "+ userId);
	}

	public Map<String, String> queryMaxIdFundById(Connection conn, long userId)  throws DataException, SQLException  {
		DataSet dataSet = MySQL.executeQuery(conn," select * from t_fundrecord where id = (select max(id) from t_fundrecord where userId = "+ userId+")" );
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public List<Map<String, Object>> queryVipUser(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet ds = t_user.open(conn, "id", "(vipStatus = 2 or vipStatus = 3) and feeStatus =1 and usableSum>vipFee", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	/**
	 * 重置用户状态，即设置用户是否限制登录和设置用户错误登录次数
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long resetUserState(Connection conn,int loginErrorCount,int isLoginLimit,long userId) throws SQLException {
		Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		t_user.loginErrorCount.setValue(loginErrorCount);
		t_user.isLoginLimit.setValue(isLoginLimit);
		return t_user.update(conn, " id = "+ userId);
	}
	/**
	 * 设置用户登录错误次数和登陆时间
	 * @param conn
	 * @param loginErrorCount
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long updateUserState(Connection conn,int loginErrorCount,String lastDate,long userId) throws SQLException {
		Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		t_user.loginErrorCount.setValue(loginErrorCount);
		t_user.lastDate.setValue(lastDate);
		return t_user.update(conn, " id = "+ userId);
	}
	public long updateUserState1(Connection conn,int loginErrorCount,int isLoginLimit,String lastDate,long userId) throws SQLException {
		Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		t_user.loginErrorCount.setValue(loginErrorCount);
		t_user.lastDate.setValue(lastDate);
		t_user.isLoginLimit.setValue(isLoginLimit);
		return t_user.update(conn, " id = "+ userId);
	}
	
	public Map<String, String> queryUserByName(Connection conn, String name) throws DataException, SQLException {
		StringBuilder command = new StringBuilder();
		command.append("select * from t_user where username = '");
		command.append(StringEscapeUtils.escapeSql(name));
		command.append("' or email = '");
		command.append(StringEscapeUtils.escapeSql(name));
		command.append("' or mobilePhone='");
		command.append(StringEscapeUtils.escapeSql(name));
		command.append("' ");
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(ds);
	}
	
    public Map<String, String> queryUsernameById(Connection conn, Long userId)
    throws DataException, SQLException {
    DataSet dataSet = MySQL.executeQuery(conn," select username from t_user where id = " + userId);
    return BeanMapUtils.dataSetToMap(dataSet);
    }
    
    public Map<String,String> queryUserByNameAndPhone(Connection conn,String name,String phone) throws DataException, SQLException{
    	StringBuffer sb = new StringBuffer();
    	sb.append("select tu.*,tp.cellPhone from t_user tu left join t_person tp on tp.userId = tu.id ");
    	if(name != null){
    		sb.append(" where tu.username = '"+name+"'");
    	}
    	if(phone != null){
    		sb.append(" where tp.cellPhone = '"+phone+"'");
    	}
    	if(name !=null || phone !=null){
    		DataSet ds = MySQL.executeQuery(conn, sb.toString());
    		return BeanMapUtils.dataSetToMap(ds);
    	}else{
    		return null;
    	}
    }
    
    public List<Map<String,Object>> queryUserByNameAndPhone2(Connection conn,String name) throws DataException, SQLException{
    	StringBuffer sb = new StringBuffer();
    	sb.append("select tu.*,tp.cellPhone,tp.realName,tp.idNo from t_user tu left join t_person tp on tp.userId = tu.id ");
    	sb.append(" where 1 = 1 ");
    	if(StringUtils.isNotBlank(name)){
    		sb.append(" and  tu.username  like '%"
					+ StringEscapeUtils.escapeSql(name.trim()) + "%' ");
    	}
    	sb.append(" and tu.id > 1 ");
    	DataSet ds = MySQL.executeQuery(conn, sb.toString());
    	ds.tables.get(0).rows.genRowsMap();
    	return ds.tables.get(0).rows.rowsMap;
    }
    
    
    
    
    /***
     * 判断当前用户是否为  小贷公司开户的前台用户
     * @param conn
     * @param userId
     * @return
     * @throws DataException
     * @throws SQLException
     */
    public List<Map<String,Object>> queryAminUserdata(Connection conn,Long userId)throws DataException, SQLException{
    	Dao.Tables.t_admin_user   t_admin_user = new Dao().new Tables().new t_admin_user();
    	StringBuffer condition = new StringBuffer();
		condition.append("1 = 1");
		condition.append(" and userId='"+userId+"'");
		DataSet dataSet = t_admin_user.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
    }
    
    /**
     * 根据用户userId 查询  workId 
     * @param conn
     * @param userId
     * @return
     * @throws DataException
     * @throws SQLException
     */
    public List<Map<String,Object>> queryWorkId(Connection conn,Long userId)throws DataException, SQLException{
    	Dao.Tables.t_workauth   t_workauth = new Dao().new Tables().new t_workauth();
    	StringBuffer condition = new StringBuffer();
		condition.append("1 = 1");
		condition.append(" and userId='"+userId+"'");
		DataSet dataSet = t_workauth.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
    }
    
    /**
     * 五项必须认证判断
     * @param conn
     * @param userId
     * @return
     */
    public Long updateUserauthodLoanUser(Connection conn, Long userId) {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Map<String, String> Accmap = null;// 统计t_materialsauth用户类型
		try {
			Accmap = queryPicturStatuCount(conn, userId);
			Integer alli = 0;
			if (Accmap != null && Accmap.size() > 0) {
				alli = Convert.strToInt(Accmap.get("ccc"), 0);
				if (alli >= 15) {
					user.authStep.setValue(5);
					return user.update(conn, " id = " + userId);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
		return 1L;
	}
    
    /**
	  * 查询小贷公司
	  * @param conn
	  * @param adminId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	public Map<String, String> queryLoan(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " select * from  t_company_loan a where a.user_id ="+ id );
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	  * 查询待还本金
	  * @param conn
	  * @param adminId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	public Map<String, String> queryRepayment(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " select * from  t_repayment a where a.id ="+ id );
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	 public Map<String, String> queryUserNme(Connection conn, String name)
			    throws DataException, SQLException {
			    DataSet dataSet = MySQL.executeQuery(conn," select * from t_user where username = '" + name+"'");
			    return BeanMapUtils.dataSetToMap(dataSet);
	}
	 
	 public long updatePersonLianSate(Connection conn, long id)
			    throws DataException, SQLException {
		 Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		 t_person.lian_state.setValue(1);
		 return  t_person.update(conn, "id="+id);
	}
	 
	 public Map<String, String> queryGroupById(Connection conn, long userId)
				throws SQLException, DataException {
			StringBuffer sql = new StringBuffer();
			sql.append( " select a.groupName,b.userId  from t_group a ,t_group_user b WHERE a.id = b.groupId and a.groupName in ('线下理财人','机构客户','线下借款人','其他') and b.userId ="+userId);
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			return BeanMapUtils.dataSetToMap(dataSet); 
		}
	
	 /***
	  * 查询推荐人年化投资总额
	  * @param conn
	  * @param investor
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	 public Map<String, String> queryUserInvestSum(Connection conn, long investor)
				throws SQLException, DataException {
			StringBuffer sql = new StringBuffer();
			sql.append( " SELECT SUM(t.investAmount*t.deadline/12) as investAmountSum  FROM  t_invest t where t.investor ="+investor);
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			return BeanMapUtils.dataSetToMap(dataSet); 
	 }
	 
	 public Map<String, String> searchUserInvestSum(Connection conn, long investor)
				throws SQLException, DataException {
			StringBuffer sql = new StringBuffer();
			sql.append( " SELECT SUM(t.investAmount) as investAmountSum  FROM  t_invest t where t.investor ="+investor);
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			return BeanMapUtils.dataSetToMap(dataSet); 
	 }
	 
	 /***
	  * 获取推荐人体验金个数
	  * @param conn
	  * @param recommendUserId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	 public Map<String, String> queryUserTyjCounts(Connection conn, long recommendUserId)
				throws SQLException, DataException {
			StringBuffer sql = new StringBuffer();
			sql.append( " SELECT  COUNT(*) as counts  from  t_send_tyj t  where t.userId not in (select b.userId from t_group a,t_group_user b where  a.id = b.groupId and a.id  in (5,6,7,8))  and t.userId = "+recommendUserId);
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			return BeanMapUtils.dataSetToMap(dataSet); 
		}
	 
	 public long updatet_person(Connection conn, String cell1,String cell2) throws SQLException, DataException {
			 Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
			 t_person.cellPhone.setValue(cell2);
			 return  t_person.update(conn, "cellPhone='"+cell1+"'");
		}
	 
	 public long updatet_user(Connection conn, String cell1,String cell2) throws SQLException, DataException {
		 Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		 t_user.mobilePhone.setValue(cell2);
		 return  t_user.update(conn, "mobilePhone='"+cell1+"'");
	}
	 
	 public long updatet_phone_binding_info(Connection conn, String cell1,String cell2) throws SQLException, DataException {
		 Dao.Tables.t_phone_binding_info t_phone_binding_info = new Dao().new Tables().new t_phone_binding_info();
		 t_phone_binding_info.mobilePhone.setValue(cell2);
		 return  t_phone_binding_info.update(conn, "mobilePhone='"+cell1+"'");
	}
	 
	 public Map<String, String> queryActivtySetComfig(Connection conn, String code) throws SQLException, DataException {
			StringBuffer command = new StringBuffer();
			command.append(" select a.*,date_format(a.start_time,'%Y-%m-%d') as start_time_f ,date_format(a.end_time,'%Y-%m-%d') as  end_time_f  from t_setting_activity a where a.code ='"+code+"'");
			DataSet dataSet = Database.executeQuery(conn, command.toString());
			command = null;
			return BeanMapUtils.dataSetToMap(dataSet);
		}
	 
	 public Long addSendTyj(Connection conn, long userId,double amount) throws Exception{
			Dao.Tables.t_send_tyj t_send_tyj = new Dao().new Tables().new t_send_tyj();
			t_send_tyj.userId.setValue(userId);
			t_send_tyj.amount.setValue(amount);
			t_send_tyj.createTime.setValue(new Date());
			t_send_tyj.endTime.setValue(DateUtil.getCurrDateLateDay(30));
			return t_send_tyj.insert(conn);
	 }
	 
	 public void queryBorrowTyjList(Connection conn,PageBean<Map<String, Object>> pageBean) throws SQLException, DataException{
			Dao.Tables.t_borrow_new t_borrow_new = new Dao().new Tables().new t_borrow_new();
			StringBuffer condition = new StringBuffer();
		 
			long c=t_borrow_new.getCount(conn, condition.toString()); 
			boolean  result=pageBean.setTotalNum(c); 
			if(result)
			{
				DataSet ds= t_borrow_new.open(conn, " DATE_FORMAT(createTime,'%Y-%m-%d %H:%i:%s') as createTime_f,t_borrow_new.* ", "", " ", pageBean.getStartOfPage(), pageBean.getPageSize());
				ds.tables.get(0).rows.genRowsMap();
				pageBean.setPage(ds.tables.get(0).rows.rowsMap); 
				
			}
			 
		}
	 
	 public Long addBorrowNew(Connection conn, String name,double rate,int day, double amount_sum) throws Exception{
			Dao.Tables.t_borrow_new t_borrow_new = new Dao().new Tables().new t_borrow_new();
			t_borrow_new.borrowName.setValue(name);
			t_borrow_new.amount_sum.setValue(amount_sum);
			t_borrow_new.rate.setValue(rate);
			t_borrow_new.day.setValue(day);
			t_borrow_new.createTime.setValue(new Date());
			t_borrow_new.amount_able.setValue(0);
			return t_borrow_new.insert(conn);
	 }
	 
	 public Long addEmployeeBorrow(Connection conn, String name,double rate,int day, double amount_sum) throws Exception{
			Dao.Tables.t_employee_borrow t_employee_borrow = new Dao().new Tables().new t_employee_borrow();
			t_employee_borrow.title_name.setValue(name);
			t_employee_borrow.amount_sum.setValue(amount_sum);
			t_employee_borrow.rate.setValue(rate);
			t_employee_borrow.day.setValue(day);
			t_employee_borrow.createTime.setValue(new Date());
			t_employee_borrow.amount_able.setValue(0);
			return t_employee_borrow.insert(conn);
	 }
	 
	 /**
	  * 判断用户是否为机构用户
	  * @param conn
	  * @param userId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	 public Map<String, String> queryGroupUser(Connection conn, long userId) throws SQLException, DataException {
			StringBuffer command = new StringBuffer();
			command.append(" SELECT *  from  t_group_user where groupId = 7 and userId = "+userId);
			DataSet dataSet = Database.executeQuery(conn, command.toString());
			command = null;
			return BeanMapUtils.dataSetToMap(dataSet);
		}
	 
	 /**
	  * 更新用户体验金份数
	  * @param conn
	  * @param cell1
	  * @param cell2
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	 public long updateEmployeeConfig(Connection conn,long id, int amount) throws SQLException, DataException {
		 Dao.Tables.t_employee_config  t_employee_config  = new Dao().new Tables().new t_employee_config();
		 t_employee_config.amount.setValue(amount);
		 t_employee_config.createTime.setValue(new Date());
		 return  t_employee_config.update(conn, "id="+id);
	}
	 
	 public long addEmployeeConfig(Connection conn,long userId,int amount) throws SQLException, DataException {
		 Dao.Tables.t_employee_config  t_employee_config  = new Dao().new Tables().new t_employee_config();
		 t_employee_config.userId.setValue(userId);
		 t_employee_config.amount.setValue(amount);
		 t_employee_config.createTime.setValue(new Date());
		 return  t_employee_config.insert(conn);
	}
	 
	 public Map<String, String> queryEmployeeConfigByUserId(Connection conn, long userId) throws SQLException, DataException {
			StringBuffer command = new StringBuffer();
			command.append(" SELECT  *  from  t_employee_config where userId = "+userId);
			DataSet dataSet = Database.executeQuery(conn, command.toString());
			command = null;
			return BeanMapUtils.dataSetToMap(dataSet);
		}
	 
	 
	 
	 public void queryEmployeeBorrowList(Connection conn,PageBean<Map<String, Object>> pageBean) throws SQLException, DataException{
			Dao.Tables.t_employee_borrow t_employee_borrow = new Dao().new Tables().new t_employee_borrow();
			StringBuffer condition = new StringBuffer();
		 
			long c=t_employee_borrow.getCount(conn, condition.toString()); 
			boolean  result=pageBean.setTotalNum(c); 
			if(result)
			{
				DataSet ds= t_employee_borrow.open(conn, " DATE_FORMAT(createTime,'%Y-%m-%d %H:%i:%s') as createTime_f,t_employee_borrow.* ", "", " ", pageBean.getStartOfPage(), pageBean.getPageSize());
				ds.tables.get(0).rows.genRowsMap();
				pageBean.setPage(ds.tables.get(0).rows.rowsMap); 
				
			}
			 
		}
	 /**
	  * 活动操作
	  * @param conn
	  * @param userId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	 public Map<String, String> searchActivity20160801(Connection conn) throws SQLException, DataException {
			StringBuffer command = new StringBuffer();
			command.append(" select id, money_box  from  t_activity_20160801 where state=1 order by rand() limit 1 ");
			DataSet dataSet = Database.executeQuery(conn, command.toString());
			command = null;
			return BeanMapUtils.dataSetToMap(dataSet);
		}
	 public long updateActivity20160801(Connection conn,long id) throws SQLException, DataException {
		 Dao.Tables.t_activity_20160801  t_activity_20160801  = new Dao().new Tables().new t_activity_20160801();
		 t_activity_20160801.state.setValue(2);
		 return  t_activity_20160801.update(conn, "id="+id);
	}
	 
	 public long addActivity20160801(Connection conn,double money_box) throws SQLException, DataException, ParseException {
		 Dao.Tables.t_activity_20160801  t_activity_20160801  = new Dao().new Tables().new t_activity_20160801();
		 String dateString = "2016-09-01";  
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		 Date date = sdf.parse(dateString);  
		 t_activity_20160801.money_box.setValue(money_box);
		 t_activity_20160801.start_time.setValue(new Date());
		 t_activity_20160801.end_time.setValue(date);
		 t_activity_20160801.state.setValue(1);
		 return  t_activity_20160801.insert(conn);
	}
	 /**
	  * 活动关系操作
	  * @param conn
	  * @param userId
	  * @return
	  * @throws SQLException
	  * @throws DataException
	  */
	 public Map<String, String> searchActivity20160801ByUser(Connection conn, long userId) throws SQLException, DataException {
			StringBuffer command = new StringBuffer();
			command.append(" SELECT  *  from  t_activity_user_20160801 where user_id = "+userId);
			DataSet dataSet = Database.executeQuery(conn, command.toString());
			command = null;
			return BeanMapUtils.dataSetToMap(dataSet);
		}
	 
	 public long add(Connection conn,long user_id,int already_number,int total_number) throws SQLException, DataException {
		 Dao.Tables.t_activity_user_20160801  t_activity_user_20160801  = new Dao().new Tables().new t_activity_user_20160801();
			System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		 String dateString = "2016-09-01";  
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
//		 Date date = sdf.parse(dateString);  
		 t_activity_user_20160801.user_id.setValue(user_id);
		 t_activity_user_20160801.already_number.setValue(already_number);
		 t_activity_user_20160801.total_number.setValue(total_number);
		 t_activity_user_20160801.start_time.setValue(new Date());
//		 t_activity_user_20160801.end_time.setValue(date);
		 t_activity_user_20160801.state.setValue(1);
		 System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		 return  t_activity_user_20160801.insert(conn);
		}
	 public long updateActivity20160801ByUser(Connection conn,long userId,int total_number,int already_number) throws SQLException, DataException {
		 Dao.Tables.t_activity_user_20160801  t_activity_user_20160801  = new Dao().new Tables().new t_activity_user_20160801();
		 t_activity_user_20160801.total_number.setValue(total_number);
		 t_activity_user_20160801.already_number.setValue(already_number);
		 return  t_activity_user_20160801.update(conn, "user_id="+userId);
	}
	 public long addActivityByUser(Connection conn,long user_id,int already_number,int total_number) throws SQLException, DataException, ParseException {
		 Dao.Tables.t_activity_user_20160801  t_activity_user_20160801  = new Dao().new Tables().new t_activity_user_20160801();
			System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		 String dateString = "2016-09-01";  
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		 Date date = sdf.parse(dateString);  
		 t_activity_user_20160801.user_id.setValue(user_id);
		 t_activity_user_20160801.already_number.setValue(already_number);
		 t_activity_user_20160801.total_number.setValue(total_number);
		 t_activity_user_20160801.start_time.setValue(new Date());
		 t_activity_user_20160801.end_time.setValue(date);
		 t_activity_user_20160801.state.setValue(1);
		 System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		 return  t_activity_user_20160801.insert(conn);
	}
	 
	 
}
