package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.database.Dao;
import com.sp2p.util.DateUtil;

/**
 * 红包dao
 * 
 */
public class BonusDao extends RecommendBrokerageBase{
	
	public long queryBonusListCount(Connection conn,long userId,int flag)throws SQLException,DataException{
		long count = 0;
		StringBuffer sql = new StringBuffer();
		if(1 == flag){
			sql.append("select count(user_id) as count from t_bonus_list");
			sql.append(" where user_id="+userId);
			sql.append(" and bonus_money>0");
		}else {
			sql.append("select count(user_id) as count from t_6_24_bouns");
			sql.append(" where user_id="+userId);
		}
		DataSet ds = MySQL.executeQuery(conn, sql.toString());
		Map<String,String> map = BeanMapUtils.dataSetToMap(ds);
		if(map.containsKey("count") &&
				StringUtils.isNotBlank(map.get("count"))){
			count = Convert.strToLong(map.get("count"), 0);
		}
		
		return count;
	}
	
	public List<Map<String,Object>> queryBonusList(Connection conn,long userId,long start,int end)throws SQLException,DataException{
		Dao.Tables.t_bonus_list t_bonus_list = new Dao().new Tables().new t_bonus_list();
		DataSet dataSet = t_bonus_list.open(conn, " t_bonus_list.*,DATE_FORMAT(t_bonus_list.create_time,'%Y-%m-%d') as createTime,DATE_FORMAT(t_bonus_list.last_time,'%Y-%m-%d') as lastTime,DATE_FORMAT(t_bonus_list.end_time,'%Y-%m-%d') as endTime  ", " user_id= "+userId+" and bonus_money>0", "status,createTime", start, end);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 6.24红包
	 * @return
	 */
	public List<Map<String,Object>> querySixBonusList(Connection conn,long userId,long start,int end)throws SQLException,DataException{
		Dao.Tables.t_6_24_bouns t_6_24_bouns = new Dao().new Tables().new t_6_24_bouns();
		DataSet dataSet = t_6_24_bouns.open(conn, " t_6_24_bouns.*,DATE_FORMAT(t_6_24_bouns.start_time,'%Y-%m-%d') as startTime,DATE_FORMAT(t_6_24_bouns.end_time,'%Y-%m-%d') as endTime  ", " user_id= "+userId, "state,startTime", start, end);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
     * 查询红包金额区间
     * @param conn
     * @return
     * @throws SQLException
     * @throws DataException
     */
	public List<Map<String, Object>> queryBonusAmount(Connection conn)throws SQLException, DataException{
		Dao.Tables.t_bonus_amount t_bonus_amount = new Dao().new Tables().new t_bonus_amount();
		DataSet dataSet = t_bonus_amount.open(conn, " * ", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询红包金额区间最大、最小id
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryBonusAmountId(Connection conn)throws SQLException, DataException{
		Dao.Tables.t_bonus_amount t_bonus_amount = new Dao().new Tables().new t_bonus_amount();
		DataSet dataSet = t_bonus_amount.open(conn, " MIN(id) as min, MAX(id) as max ", "", "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 添加红包配置
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long addBonusConfig(Connection conn, int use_deadline, int reg_deadline, int invest_deadline,double reg_recommended_red, double reg_registration_red, double reg_registration_cash,
			double invest_proportion, double low_amount, double red_limit, String start_time, String end_time)throws SQLException {
		
		Dao.Tables.t_bonus_config t_bonus_config = new Dao().new Tables().new t_bonus_config();
		t_bonus_config.use_deadline.setValue(use_deadline);
		t_bonus_config.reg_deadline.setValue(reg_deadline);
		t_bonus_config.invest_deadline.setValue(invest_deadline);
		t_bonus_config.reg_recommended_red.setValue(reg_recommended_red);
		t_bonus_config.reg_registration_red.setValue(reg_registration_red);
		t_bonus_config.reg_registration_cash.setValue(reg_registration_cash);
		t_bonus_config.invest_proportion.setValue(invest_proportion);
		t_bonus_config.low_amount.setValue(low_amount);
		t_bonus_config.red_limit.setValue(red_limit);
		t_bonus_config.start_time.setValue(start_time);
		t_bonus_config.end_time.setValue(end_time);
		t_bonus_config.create_time.setValue(new Date());

		return t_bonus_config.insert(conn);
	}

	/**
	 * 添加红包金额区间
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long addBonusAmount(Connection conn, double min_invest_amount, double max_invest_amount, double red_value)throws SQLException {
		
		Dao.Tables.t_bonus_amount t_bonus_amount = new Dao().new Tables().new t_bonus_amount();
		t_bonus_amount.min_invest_amount.setValue(min_invest_amount);
		t_bonus_amount.max_invest_amount.setValue(max_invest_amount);
		t_bonus_amount.red_value.setValue(red_value);
		t_bonus_amount.create_time.setValue(new Date());

		return t_bonus_amount.insert(conn);
	}
	
	/**
	 *  根据id删除红包金额区间
	 * @return
	 * @throws Exception
	 */
	public Long deleteById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_bonus_amount t_bonus_amount = new Dao().new Tables().new t_bonus_amount();
		return t_bonus_amount.delete(conn, " id=" + id);
	}
	
	/**
	 * 更新红包配置
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long updateBonusConfig(Connection conn, int id, int super_id, int use_deadline, int reg_deadline, int recom_deadline, int invest_deadline,double reg_recommended_red, double reg_registration_red, double reg_registration_cash,
			double invest_proportion, double low_amount, double red_limit, String start_time, String end_time)throws SQLException {
		
		Dao.Tables.t_bonus_config t_bonus_config = new Dao().new Tables().new t_bonus_config();
		t_bonus_config.super_id.setValue(super_id);
		t_bonus_config.use_deadline.setValue(use_deadline);
		t_bonus_config.reg_deadline.setValue(reg_deadline);
		t_bonus_config.recom_deadline.setValue(recom_deadline);
		t_bonus_config.invest_deadline.setValue(invest_deadline);
		t_bonus_config.reg_recommended_red.setValue(reg_recommended_red);
		t_bonus_config.reg_registration_red.setValue(reg_registration_red);
		t_bonus_config.reg_registration_cash.setValue(reg_registration_cash);
		t_bonus_config.invest_proportion.setValue(invest_proportion);
		t_bonus_config.low_amount.setValue(low_amount);
		t_bonus_config.red_limit.setValue(red_limit);
		t_bonus_config.start_time.setValue(start_time);
		t_bonus_config.end_time.setValue(end_time);
		t_bonus_config.create_time.setValue(new Date());
		
		return t_bonus_config.update(conn, " id = " + id);
	}
	
	
	
	/**
	 * 更新红包金额区间
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long updateBonusAmount(Connection conn, int id, double min_invest_amount, double max_invest_amount, double red_value)throws SQLException {
		
		Dao.Tables.t_bonus_amount t_bonus_amount = new Dao().new Tables().new t_bonus_amount();
		t_bonus_amount.min_invest_amount.setValue(min_invest_amount);
		t_bonus_amount.max_invest_amount.setValue(max_invest_amount);
		t_bonus_amount.red_value.setValue(red_value);
		t_bonus_amount.create_time.setValue(new Date());
		
		return t_bonus_amount.update(conn, " id = " + id);
	}
	
	/**
	 * 查询红包的配置
	 * @return
	 */
	public Map<String,String> queryBonusConfig(Connection conn)throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " SELECT t_bonus_config.*,DATE_FORMAT(t_bonus_config.start_time,'%Y-%m-%d') as startTime from t_bonus_config");
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> queryBonusListCount(Connection conn)throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " SELECT SUM(t_bonus_list.bonus_money) as bonusMoneySum from t_bonus_list ");
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询红包总额等统计信息
	 * @return
	 */
	public Map<String,String> queryBonusSumCount(Connection conn,long userId)throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " SELECT ROUND(SUM(bonus_money),2) as bonus_money, ROUND(SUM(bonus_used),2) as bonus_used, "
				+ "ROUND((SELECT SUM(bonus_avaliable) from t_bonus_list where `status` = 1 and user_id = "+userId+"),2) as bonus_avaliable,"
				+ "ROUND((SELECT SUM(bonus_avaliable) from t_bonus_list where NOW() > end_time and user_id = "+userId+"),2) as bonus_expired "
				+ "from t_bonus_list where user_id = " + userId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询6.24红包总额等统计信息
	 * @return
	 */
	public Map<String,String> querySixBonusSumCount(Connection conn,long userId)throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append( " SELECT ROUND(SUM(bonus_sum),2) as bonus_money, ROUND(SUM(bonus_already),2) as bonus_used, "
				+ " ROUND((SELECT SUM(bonus_able) from t_6_24_bouns where state = 1 and user_id = " + userId + "),2) as bonus_avaliable,"
				+ " ROUND((SELECT SUM(bonus_able) from t_6_24_bouns where NOW() > end_time and user_id = " + userId + "),2) as bonus_expired "
				+ " from t_6_24_bouns where user_id = " + userId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询有效期内的红包的配置，如果红包过期，则查询不出结果
	 * @return
	 */
	public Map<String,String> queryBounsConfigAvaliable(Connection conn)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append( " SELECT t_bonus_config.*,DATE_FORMAT(t_bonus_config.start_time,'%Y-%m-%d') as startTime from t_bonus_config ");
		sql.append(" where unix_timestamp(DATE_FORMAT(t_bonus_config.end_time,'%Y-%m-%d'))>=unix_timestamp(DATE_FORMAT(NOW(),'%Y-%m-%d')) ");
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
		
	}
	
	/**
	 * 查询用户红包
	 * @author 郭井超------------非本人不许呦
	 * @param conn
	 * @param userId
	 * @param bonus_type
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryUserBonusY(Connection conn,long userId,int bonus_type)throws SQLException, DataException {
		  //String currDate = DateUtil.dateToString(new Date());
		  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		  String currDate =  df.format(new Date())+" 00:00:00";
		  StringBuffer sql = new StringBuffer();
			sql.append( " select * from t_bonus_list a where a.user_id ="+userId+" and a.status = 1 and a.end_time>='"+currDate+"'");
			if(bonus_type==1){
				sql.append( " and a.bonus_type = 1 ");
			}
			if(bonus_type==2){
				sql.append( " and a.bonus_type = 2 ");
			}
			if(bonus_type==3){
				sql.append( " and a.bonus_type = 3 ");
			}
			if(bonus_type==12){
				sql.append( " and a.bonus_type in (1,2) ");
			}
			if(bonus_type==13){
				sql.append( " and a.bonus_type in (1,3) ");
			}
			if(bonus_type==23){
				sql.append( " and a.bonus_type in (2,3) ");
			}
			
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> queryUserBonusN(Connection conn,long userId,int bonus_type)throws SQLException, DataException {
		//String currDate = DateUtil.dateToString(new Date());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currDate =  df.format(new Date())+" 00:00:00";
		StringBuffer sql = new StringBuffer();
		sql.append( " select * from t_bonus_list a where a.user_id ="+userId+" and a.status = 1 and a.end_time>='"+currDate+"'");
		
		if(bonus_type==1){
			sql.append( " and a.bonus_type = 1 ");
		}
		if(bonus_type==2){
			sql.append( " and a.bonus_type = 2 ");
		}
		
		if(bonus_type==12){
			sql.append( " and a.bonus_type in (1,2) ");
		}
		if(bonus_type==13){
			sql.append( " and a.bonus_type in (1,3) ");
		}
		if(bonus_type==23){
			sql.append( " and a.bonus_type in (2,3) ");
		}
		
		sql.append( " ORDER BY a.end_time ASC ");
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
    }
	
	
  /***
   * 更新用户红包
  * @author 郭井超------------非本人不许改呦
   * @param conn
   * @param id
   * @param min_invest_amount
   * @param max_invest_amount
   * @param red_value
   * @return
   * @throws SQLException
   */
  public Long updateUserBonus(Connection conn, long id,double bonus_money,double bonus_avaliable,double bouns_used,int status)throws SQLException {
		
		Dao.Tables.t_bonus_list t_bonus_list = new Dao().new Tables().new t_bonus_list();
		t_bonus_list.bonus_money.setValue(bonus_money);
		t_bonus_list.bonus_avaliable.setValue(bonus_avaliable);
		t_bonus_list.bonus_used.setValue(bouns_used);
		t_bonus_list.status.setValue(status);
		t_bonus_list.last_time.setValue(new Date());
		return t_bonus_list.update(conn, " id = " + id);
		
	}
  
  public Long updateUserBonus6_24(Connection conn, long id,double bonus_able,double bonus_already,int status)throws SQLException {
		
		Dao.Tables.t_6_24_bouns t_6_24_bouns = new Dao().new Tables().new t_6_24_bouns();
		t_6_24_bouns.bonus_able.setValue(bonus_able);
		t_6_24_bouns.bonus_already.setValue(bonus_already);
		t_6_24_bouns.state.setValue(status);
		return t_6_24_bouns.update(conn, " id = " + id);
		
	}
  
  /***
   * 跑P处理已过期的红包
   * @param conn
   * @param id
   * @return
   * @throws SQLException
   */
  public Long updateUserBonus(Connection conn, long id)throws SQLException {
		Dao.Tables.t_bonus_list t_bonus_list = new Dao().new Tables().new t_bonus_list();
		t_bonus_list.status.setValue(2);
		t_bonus_list.last_time.setValue(new Date());
		return t_bonus_list.update(conn, " id = " + id);
	}
  
  /**
   * 查询到期红包，将状
   * @param conn
   * @return
   * @throws SQLException
   * @throws DataException
   */
  public List<Map<String,Object>> queryBonus(Connection conn)throws SQLException, DataException {
	  String currDate = DateUtil.dateToString(new Date());	
	  StringBuffer sql = new StringBuffer();
		sql.append( " select * from  t_bonus_list a where a.status = 1 and a.end_time<'"+currDate+"'" );
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
}
	
  /**
   * 添加用户使用红包记录
   * @param conn
   * @param user_id
   * @param bonus_money
   * @param bonus_avaliable
   * @param bonus_used
   * @param bonus_type
   * @param status
   * @param endTime
   * @return
   * @throws SQLException
   */
  public Long addBonusHis(Connection conn, long user_id,double do_bonus_money, long borrow_id)throws SQLException {
		Dao.Tables.t_bonus_his t_bonus_his = new Dao().new Tables().new t_bonus_his();
		t_bonus_his.user_id.setValue(user_id);
		t_bonus_his.amount.setValue(do_bonus_money);
		t_bonus_his.borrow_id.setValue(borrow_id);
		t_bonus_his.create_time.setValue(new Date());
		return t_bonus_his.insert(conn);
	}
	
	
	/**
	 * 查询注册未收到红包的用户
	 * @return
	 */
	public List<Map<String,Object>> queryUserTop(Connection conn)throws SQLException, DataException {
		Map<String,String> map = queryBonusConfig(conn);
		if(map!=null){
			String startTime = map.get("startTime");
			StringBuffer sql = new StringBuffer();
			sql.append( " SELECT t_user.id as user_id,");
			sql.append( " t_user.username as username,");
			sql.append( " t_recommend_user.recommendUserId as recommendUserId");
			sql.append( " from t_user ");
			sql.append( " LEFT JOIN t_recommend_user on t_user.id = t_recommend_user.userId");
			sql.append( " LEFT JOIN (select * FROM t_bonus_list  where t_bonus_list.bonus_type = 1) as tbl ");
			sql.append( " on t_user.id = tbl.user_id ");
			sql.append( " where DATE_FORMAT(t_user.createTime,'%Y-%m-%d')>='"+startTime+"'");
			sql.append( " AND tbl.user_id IS NULL");
			
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		}
		return null;
	}
	
	/**
	 * 查询投资但未收到红包的投资记录
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryInvestTop(Connection conn)throws SQLException, DataException{
		Map<String,String> map = queryBonusConfig(conn);
		if(map!=null){
			String startTime = map.get("startTime");
			StringBuffer sql = new StringBuffer();
			sql.append( " SELECT t_invest.investor as investor,");
			sql.append(" min(t_invest.id) as investId,t_invest.investAmount as amount, ");
			sql.append( " sum(t_invest.investAmount) as investAmount,");
			sql.append( " t_invest.investTime as investTime");
			sql.append( " from t_invest ");
			sql.append( " LEFT JOIN (select * FROM t_bonus_list  where t_bonus_list.bonus_type = 2) as tbl");
			sql.append( " on t_invest.investor = tbl.user_id ");
			sql.append( " where DATE_FORMAT(t_invest.investTime,'%Y-%m-%d')>='"+startTime+"'");
			sql.append( " AND tbl.user_id IS NULL");
			sql.append(" GROUP BY t_invest.investor");
			
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		}
		return null;
	}
	
	/**
	 * 查询注册未发送10元奖励的用户
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryBonusListForNothingCash(Connection conn)throws SQLException, DataException{
		Map<String,String> map = queryBonusConfig(conn);
		if(map!=null){
			StringBuffer sql = new StringBuffer();
			sql.append( " SELECT t_bonus_list.id,t_recharge_detail.userId,t_bonus_list.user_id from t_bonus_list ");
			sql.append(" LEFT JOIN t_recharge_detail on t_bonus_list.user_id = t_recharge_detail.userId ");
			sql.append(" where t_bonus_list.bonus_type = 1 and cash_type = 1 "
					+ " AND (t_recharge_detail.result = 1 or t_recharge_detail.result = 6) AND t_recharge_detail.userId is not NULL"
					+ " GROUP BY t_recharge_detail.userId ORDER BY create_time ");
			
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		}
		return null;
	}
	
	public Long updateBonusList(Connection conn,long id,int cash_type)throws SQLException {
		Dao.Tables.t_bonus_list t_bonus_list = new Dao().new Tables().new t_bonus_list();
		if(cash_type>=0){
			t_bonus_list.cash_type.setValue(cash_type);
		}
		
		return t_bonus_list.update(conn,  " id ="+id);
	}
	
	/**
	 * 添加红包配置
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long addBonusList(Connection conn, long user_id, long from_user_id,
			double bonus_money, double bonus_avaliable, double bonus_used,int bonus_type,int status,String endTime)throws SQLException {
		Dao.Tables.t_bonus_list t_bonus_list = new Dao().new Tables().new t_bonus_list();
		t_bonus_list.user_id.setValue(user_id);
		t_bonus_list.from_user_id.setValue(from_user_id);
		t_bonus_list.bonus_money.setValue(bonus_money);
		t_bonus_list.bonus_avaliable.setValue(bonus_avaliable);
		t_bonus_list.bonus_used.setValue(bonus_used);
		t_bonus_list.bonus_type.setValue(bonus_type);
		t_bonus_list.last_time.setValue(new Date());
		t_bonus_list.create_time.setValue(new Date());
		t_bonus_list.status.setValue(status);
		t_bonus_list.end_time.setValue(endTime);

		return t_bonus_list.insert(conn);
	}
	
	
	 public List<Map<String,Object>> queryBonus_6_24(Connection conn,long user_id)throws SQLException, DataException {
		  String currDate = DateUtil.dateToString(new Date());	
		  StringBuffer sql = new StringBuffer();
			sql.append( " select * from  t_6_24_bouns a where  a.user_id="+user_id+" and a.end_time>='"+currDate+"'"+" and a.state=1 order by a.end_time asc " );
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	public Long addBonus_6_24(Connection conn, long user_id,double bonus_able)throws Exception {
		Dao.Tables.t_6_24_bouns t_6_24_bouns = new Dao().new Tables().new t_6_24_bouns();
		Date date = new Date();
		t_6_24_bouns.bonus_able.setValue(bonus_able);
		t_6_24_bouns.bonus_already.setValue(0);
		t_6_24_bouns.bonus_sum.setValue(bonus_able);
		t_6_24_bouns.start_time.setValue(date);
		t_6_24_bouns.end_time.setValue(DateUtil.getCurrDateAgoParmsDay(30));
		t_6_24_bouns.user_id.setValue(user_id);
		t_6_24_bouns.state.setValue(1);
		return t_6_24_bouns.insert(conn);
	}
	
	public Long addBonus_6_24(Connection conn, long user_id,double bonus_able,int type)throws Exception {
		Dao.Tables.t_6_24_bouns t_6_24_bouns = new Dao().new Tables().new t_6_24_bouns();
		Date date = new Date();
		t_6_24_bouns.bonus_able.setValue(bonus_able);
		t_6_24_bouns.bonus_already.setValue(0);
		t_6_24_bouns.bonus_sum.setValue(bonus_able);
		t_6_24_bouns.start_time.setValue(date);
		t_6_24_bouns.end_time.setValue(DateUtil.getCurrDateAgoParmsDay(30));
		t_6_24_bouns.user_id.setValue(user_id);
		t_6_24_bouns.state.setValue(1);
		t_6_24_bouns.type.setValue(type);
		return t_6_24_bouns.insert(conn);
	}
		
	 public Map<String,String> queryBonus_6_24_sum(Connection conn,long user_id)throws SQLException, DataException {
		  String currDate = DateUtil.dateToString(new Date());	
		  StringBuffer sql = new StringBuffer();
			sql.append( " select sum(a.bonus_able) as bonus_able from  t_6_24_bouns a where  a.user_id="+user_id+" and a.end_time>='"+currDate+"'"+" and a.state=1 order by a.end_time desc " );
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
	}
	 
	 public void queryMyTyj(Connection conn,PageBean<Map<String, Object>> pageBean,long userId)throws SQLException, DataException {
			Dao.Tables.t_send_tyj t_send_tyj = new Dao().new Tables().new t_send_tyj();
			StringBuffer condition = new StringBuffer();
			condition.append(" userId  = "+userId);
			long c=t_send_tyj.getCount(conn, condition.toString()); 
			boolean  result=pageBean.setTotalNum(c); 
			if(result)
			{
				DataSet ds= t_send_tyj.open(conn, " DATE_FORMAT(createTime,'%Y-%m-%d') as createTime_f,DATE_FORMAT(endTime,'%Y-%m-%d')as endTime_f,t_send_tyj.*  ", condition.toString(), "", pageBean.getStartOfPage(), pageBean.getPageSize());
				ds.tables.get(0).rows.genRowsMap();
				pageBean.setPage(ds.tables.get(0).rows.rowsMap); 
			}
			
		}
	 
	 
	 public List<Map<String,Object>> queryActivityCi(Connection conn,long user_id)throws SQLException, DataException {
		    StringBuffer sql = new StringBuffer();
			sql.append( " SELECT  a.amount,a.status ,"
					+ " date_format(a.create_time,'%Y-%m-%d %H:%i:%s') as create_time ,date_format(a.end_time,'%Y-%m-%d') as end_time ,"
					+ " b.borrowTitle,b.deadline  "
					+ " FROM    t_activity_ci a ,t_borrow  b where  a.borrow_id = b.id and a.user_id= "+user_id );
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
	}
	
}
