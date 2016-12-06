/**
 * 
 */
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
 * @author David‎-RYE
 *
 */
public class RewardDao {
	
	/**
	 * 统计奖励记录总额
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> queryRewardRecordTotal(Connection conn, long userId)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT 9 as reward_type, ROUND(SUM(reward_amount),2) as sum_amount from t_reward_record where reward_user = " + userId
				+ " UNION ALL "
				+ " SELECT reward_type as reward_type,ROUND(SUM(reward_amount),2) as sum_amount from t_reward_record where reward_user = " + userId
				+ " GROUP BY reward_type");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql= null;
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> queryRewardByRangeTime(Connection conn,String startTime,String endTime, long userId)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * from t_reward_record where ");
		sql.append(" DATE_FORMAT(t_reward_record.reward_time,'%Y-%m-%d')>=DATE_FORMAT('"+startTime+"','%Y-%m-%d')");
		sql.append(" and DATE_FORMAT(t_reward_record.reward_time,'%Y-%m-%d')<=DATE_FORMAT('"+endTime+"','%Y-%m-%d')");
		sql.append(" AND t_reward_record.reward_user = "+userId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql= null;
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 奖励记录列表
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> rewardRecordInfoApp(Connection conn, long userId)
			throws SQLException, DataException {
		Dao.Tables.t_reward_record t_reward_record = new Dao().new Tables().new t_reward_record();
		DataSet dataSet = t_reward_record.open(conn, " DATE_FORMAT(reward_time,'%Y-%m-%d') as rewardTime, t_reward_record.* ", " reward_user = " + userId, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 添加奖励记录
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long addRewardRecord(Connection conn, int reward_type, double reward_amount, int reward_user, 
			int reward_link, String reward_time, String reward_remark,long invest_repayment_id)throws SQLException {
		
		Dao.Tables.t_reward_record t_reward_record = new Dao().new Tables().new t_reward_record();
		t_reward_record.reward_type.setValue(reward_type);
		t_reward_record.reward_amount.setValue(reward_amount);
		t_reward_record.reward_user.setValue(reward_user);
		t_reward_record.reward_link.setValue(reward_link);
		t_reward_record.reward_time.setValue(reward_time);
		t_reward_record.reward_remark.setValue(reward_remark);
		t_reward_record.create_time.setValue(new Date());
		t_reward_record.repayment_id.setValue(invest_repayment_id);

		return t_reward_record.insert(conn);
	}
	
	
	/**
	 *  根据id查询规则信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_reward_setting t_reward_setting = new Dao().new Tables().new t_reward_setting();
		DataSet dataSet = t_reward_setting.open(conn, " * ", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 *  根据id查询规则信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryByRewardTime(Connection conn, String rewardType,String rewardTime)
			throws SQLException, DataException {
		Dao.Tables.t_reward_record t_reward_record = new Dao().new Tables().new t_reward_record();
		StringBuffer condition = new StringBuffer();
		condition.append(" DATE_FORMAT(reward_time,'%Y-%m-%d')='" + rewardTime+"'");
		condition.append(" AND reward_type = '"+rewardType+"'");
		DataSet dataSet = t_reward_record.open(conn, " * ", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 *  根据id查询规则信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryByRepaymentId(Connection conn, String type,String repaymentId)
			throws SQLException, DataException {
		Dao.Tables.t_reward_record t_reward_record = new Dao().new Tables().new t_reward_record();
		StringBuffer condition = new StringBuffer();
		condition.append(" reward_type = "+type);
		condition.append(" and repayment_id = "+repaymentId);
		DataSet dataSet = t_reward_record.open(conn, " * ", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询加息的奖励（还款）列表
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> queryRewardInvestPlusInterest(Connection conn,String rewardTime)
			throws SQLException, DataException {
		Dao.Views.v_reward_invest_plus_interest v_reward_invest_plus_interest = new Dao().new Views().new v_reward_invest_plus_interest();
		
		StringBuffer condition = new StringBuffer();
		condition.append(" DATE_FORMAT(realRepayDate,'%Y-%m-%d')='" + rewardTime+"'");
		DataSet dataSet = v_reward_invest_plus_interest.open(conn, " * ", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询加息的奖励（还款）列表
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String,Object>> queryRewardInvestPlusInterest(Connection conn,String rewardTime,String borrowId)
			throws SQLException, DataException {
		Dao.Views.v_reward_invest_plus_interest v_reward_invest_plus_interest = new Dao().new Views().new v_reward_invest_plus_interest();
		
		StringBuffer condition = new StringBuffer();
		condition.append(" DATE_FORMAT(realRepayDate,'%Y-%m-%d')='" + rewardTime+"'");
		condition.append(" and borrow_id = "+borrowId);
		DataSet dataSet = v_reward_invest_plus_interest.open(conn, " * ", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	/**
	 *  根据id删除规则信息
	 * @return
	 * @throws Exception
	 */
	public Long deleteById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_reward_setting t_reward_setting = new Dao().new Tables().new t_reward_setting();
		return t_reward_setting.delete(conn, " id=" + id);
	}

	/**
	 * 添加配置
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long addRewardSetting(Connection conn, int reward_src, int user_type, double min_invest_amount, double max_invest_amount, int deadline_start, 
			int deadline_end, int deadline_unit, String borrow_type, String reward_level,int return_type,	double brokerage_one, double up_limit, 
			double down_limit, double brokerage_two, String start_time, String end_time, int give_way, int reward_valid,
			int reward_valid_unit,double amount_invest_sum, int reg_sum, int isopne)throws SQLException {
		
		Dao.Tables.t_reward_setting t_reward_setting = new Dao().new Tables().new t_reward_setting();
		t_reward_setting.reward_src.setValue(reward_src);
		t_reward_setting.user_type.setValue(user_type);
		t_reward_setting.min_invest_amount.setValue(min_invest_amount);
		t_reward_setting.max_invest_amount.setValue(max_invest_amount);
		t_reward_setting.deadline_start.setValue(deadline_start);
		t_reward_setting.deadline_end.setValue(deadline_end);
		t_reward_setting.deadline_unit.setValue(deadline_unit);
		t_reward_setting.borrow_type.setValue(borrow_type);
		t_reward_setting.reward_level.setValue(reward_level);
		t_reward_setting.return_type.setValue(return_type);
		t_reward_setting.brokerage_one.setValue(brokerage_one);
		t_reward_setting.up_limit.setValue(up_limit);
		t_reward_setting.down_limit.setValue(down_limit);
		t_reward_setting.brokerage_two.setValue(brokerage_two);
		t_reward_setting.start_time.setValue(start_time);
		t_reward_setting.end_time.setValue(end_time);
		t_reward_setting.give_way.setValue(give_way);
		t_reward_setting.reward_valid.setValue(reward_valid);
		t_reward_setting.reward_valid_unit.setValue(reward_valid_unit);
		t_reward_setting.amount_invest_sum.setValue(amount_invest_sum);
		t_reward_setting.reg_sum.setValue(reg_sum);
		t_reward_setting.isopne.setValue(isopne);
		t_reward_setting.create_time.setValue(new Date());

		return t_reward_setting.insert(conn);
	}
	
	/**
	 * 更新配置
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Long updateRewardSetting(Connection conn, int id ,int reward_src, int user_type, double min_invest_amount, double max_invest_amount, int deadline_start, 
			int deadline_end, int deadline_unit, String borrow_type, String reward_level,int return_type,	double brokerage_one, double up_limit, 
			double down_limit, double brokerage_two, String start_time, String end_time, int give_way, int reward_valid,
			int reward_valid_unit,double amount_invest_sum, int reg_sum, int isopne)throws SQLException {
		Dao.Tables.t_reward_setting t_reward_setting = new Dao().new Tables().new t_reward_setting();
		t_reward_setting.reward_src.setValue(reward_src);
		t_reward_setting.user_type.setValue(user_type);
		t_reward_setting.min_invest_amount.setValue(min_invest_amount);
		t_reward_setting.max_invest_amount.setValue(max_invest_amount);
		t_reward_setting.deadline_start.setValue(deadline_start);
		t_reward_setting.deadline_end.setValue(deadline_end);
		t_reward_setting.deadline_unit.setValue(deadline_unit);
		t_reward_setting.borrow_type.setValue(borrow_type);
		t_reward_setting.reward_level.setValue(reward_level);
		t_reward_setting.return_type.setValue(return_type);
		t_reward_setting.brokerage_one.setValue(brokerage_one);
		t_reward_setting.up_limit.setValue(up_limit);
		t_reward_setting.down_limit.setValue(down_limit);
		t_reward_setting.brokerage_two.setValue(brokerage_two);
		t_reward_setting.start_time.setValue(start_time);
		t_reward_setting.end_time.setValue(end_time);
		t_reward_setting.give_way.setValue(give_way);
		t_reward_setting.reward_valid.setValue(reward_valid);
		t_reward_setting.reward_valid_unit.setValue(reward_valid_unit);
		t_reward_setting.amount_invest_sum.setValue(amount_invest_sum);
		t_reward_setting.reg_sum.setValue(reg_sum);
		t_reward_setting.isopne.setValue(isopne);
		t_reward_setting.create_time.setValue(new Date());
		
		return t_reward_setting.update(conn, " id = " + id);
	}
	
	/**
	 *  根据id查询规则信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryByRewardSrc(Connection conn, int reward_src,String investTime)
			throws SQLException, DataException {
		Dao.Tables.t_reward_setting t_reward_setting = new Dao().new Tables().new t_reward_setting();
		StringBuffer condition = new StringBuffer();
		condition.append(" reward_src=" + reward_src);
		condition.append(" AND DATE_FORMAT('"+investTime+"','%Y-%m-%d')>=DATE_FORMAT(t_reward_setting.start_time,'%Y-%m-%d') ");
		condition.append(" AND DATE_FORMAT('"+investTime+"','%Y-%m-%d')<=DATE_FORMAT(t_reward_setting.end_time,'%Y-%m-%d') ");
		
		DataSet dataSet = t_reward_setting.open(conn, " * ", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> queryRecommendInvestBrokerage(Connection conn,String dateString) throws SQLException, DataException{
		Dao.Views.v_recommend_invest_brokerage v_recommend_invest_brokerage = new Dao().new Views().new v_recommend_invest_brokerage();
		StringBuffer condition = new StringBuffer();
		condition.append(" DATE_FORMAT(v_recommend_invest_brokerage.realRepayDate,'%Y-%m-%d')=DATE_FORMAT('"+dateString+"','%Y-%m-%d') ");
		condition.append(" and recommendUserId is not null ");
		DataSet dataSet = v_recommend_invest_brokerage.open(conn, " * ", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> queryRecommendInvestBrokerage(Connection conn,String dateString,String borrowId) throws SQLException, DataException{
		Dao.Views.v_recommend_invest_brokerage v_recommend_invest_brokerage = new Dao().new Views().new v_recommend_invest_brokerage();
		StringBuffer condition = new StringBuffer();
		condition.append(" DATE_FORMAT(v_recommend_invest_brokerage.realRepayDate,'%Y-%m-%d')=DATE_FORMAT('"+dateString+"','%Y-%m-%d') ");
		condition.append(" and recommendUserId is not null ");
		condition.append(" and borrow_id= "+borrowId);
		DataSet dataSet = v_recommend_invest_brokerage.open(conn, " * ", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
}
