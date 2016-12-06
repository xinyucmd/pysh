package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.database.Dao;
import com.sp2p.util.DateUtil;

import freemarker.template.SimpleDate;
import javassist.runtime.Inner;

/**
 * @ClassName: AfterCreditManageDao.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:16:48
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 统计管理数据处理层
 */
public class StatisManageDao {
	
	/**
	 * 发送体检卡后修改t_activty_any状态
	 * @author whb
	 * @param flag 1:处理过期 2:正常发送后修改
	 * @return
	 * @throws SQLException
	 */
	public long updateActivtyAnyState(Connection conn,long id,String end_time,long user_id,int flag)throws SQLException{
		Dao.Tables.t_activty_any t_activty_any = new Dao().new Tables().new t_activty_any();
		if(1 == flag){
			t_activty_any.state.setValue(4);
		}else {
			t_activty_any.end_time.setValue(end_time);
			t_activty_any.state.setValue(3);
			t_activty_any.user_id.setValue(user_id);
		}
		return t_activty_any.update(conn, " id = "+id);
	}
	
	/**
	 * 补发体检卡后修改t_examination状态
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public long updateExamination(Connection conn,long id,int cardNo,int status,String remark)throws SQLException{
		Dao.Tables.t_examination t_examination = new Dao().new Tables().new t_examination();
		t_examination.card_no.setValue(cardNo);
		t_examination.status.setValue(status);
		t_examination.remark.setValue(remark);
		t_examination.createTime.setValue(new Date());
		return t_examination.update(conn, " id = "+id);
	}
	
	/**
	 * 补发之前未发送的用户
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryReissueUser(Connection conn)throws SQLException, DataException{
		Dao.Tables.t_examination  t_examination  = new Dao().new Tables().new t_examination();
		DataSet dataSet = t_examination.open(conn, " * ", " status != 1 ", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询过期的体检卡
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryExpiredExamination(Connection conn)throws SQLException, DataException{
		Dao.Tables.t_activty_any  t_activty_any  = new Dao().new Tables().new t_activty_any();
		DataSet dataSet = t_activty_any.open(conn, " * ", " state = 3 and end_time < NOW()", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 根据面值查询未发送的体检卡(只查询一条)
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> unsendExamination(Connection conn, double amount)throws SQLException, DataException{
		Dao.Tables.t_activty_any  t_activty_any  = new Dao().new Tables().new t_activty_any();
		DataSet dataSet = t_activty_any.open(conn, " * ", " state = 1 and t_activty_any.values = " + amount, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询奖励总额
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryRewardListSum(Connection conn, String userName, String recommendUser, double rewardAmountDown, 
			double rewardAmountUp, int typeInt)throws SQLException, DataException{
		Dao.Views.v_t_invest_reward_list  v_t_invest_reward_list  = new Dao().new Views().new v_t_invest_reward_list();
		userName = Utility.filteSqlInfusion(userName);
		recommendUser = Utility.filteSqlInfusion(recommendUser);
		
		StringBuffer condition = new StringBuffer(" 1=1 ");
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(recommendUser)) {
			condition.append(" and recommendUser like '%"
					+ StringEscapeUtils.escapeSql(recommendUser.trim()) + "%'");
		}
		if (rewardAmountUp > 0) {
			condition.append(" and amount >= " + rewardAmountDown);
			condition.append(" and amount <= " + rewardAmountUp);
		}
		if (typeInt > 0) {
			condition.append(" and type = " + typeInt);
		}
		DataSet dataSet = v_t_invest_reward_list.open(conn, " SUM(amount) as sumAmount ", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 投标统计sum
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestSum(Connection conn, String investor, String realName, 	int borrowWayInt,
			double sumAmountDown, double sumAmountUp, int groupInt,String timeStart,String timeEnd)throws SQLException, DataException{
		Dao.Views.v_t_invest_statis  v_t_invest_statis  = new Dao().new Views().new v_t_invest_statis();
		
		realName = Utility.filteSqlInfusion(realName);
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);

		StringBuffer condition = new StringBuffer(" 1=1 ");
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and  realName  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (borrowWayInt > 0) {
			if(7 == borrowWayInt){
				condition.append(" and isDebt = 2 ");
			}else {
				condition.append(" and isDebt = 1 ");
				condition.append(" and borrowWay = " + borrowWayInt);
			}
		}
		if (sumAmountUp > 0) {
			condition.append(" and realAmount <= " + sumAmountUp);
			condition.append(" and realAmount >= " + sumAmountDown);
		}
		if (groupInt > 0) {
			condition.append(" and groupid = " + groupInt);
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + " 23:59:59'");
		}
		DataSet dataSet = v_t_invest_statis.open(conn, " SUM(realAmount) as sumAmount ", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 总投资列表
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestSums(Connection conn, String investor, String realName, 	int borrowWayInt,
			double sumAmountDown, double sumAmountUp, String timeStart,String timeEnd)throws SQLException, DataException{
		Dao.Views.v_t_invest_statis  v_t_invest_statis  = new Dao().new Views().new v_t_invest_statis();
		
		realName = Utility.filteSqlInfusion(realName);
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);

		StringBuffer condition = new StringBuffer(" 1=1 ");
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and  realName  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (borrowWayInt > 0) {
			if(7 == borrowWayInt){
				condition.append(" and isDebt = 2 ");
			}else {
				condition.append(" and isDebt = 1 ");
				condition.append(" and borrowWay = " + borrowWayInt);
			}
		}
		if (sumAmountUp > 0) {
			condition.append(" and realAmount <= " + sumAmountUp);
			condition.append(" and realAmount >= " + sumAmountDown);
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + " 23:59:59'");
		}
		DataSet dataSet = v_t_invest_statis.open(conn, " SUM(realAmount) as sumAmount ", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 投标排名sum
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestSum(Connection conn, String investor,
			String timeStart, String timeEnd, int groupInt)throws SQLException, DataException{
		Dao.Views.v_t_invest_rank  v_t_invest_rank  = new Dao().new Views().new v_t_invest_rank();
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		StringBuffer condition = new StringBuffer(" 1=1 ");
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (groupInt > 0) {
			condition.append(" and groupId = " + groupInt);
		}
		DataSet dataSet = v_t_invest_rank.open(conn, " SUM(investAmount) as sumInvestAmount,SUM(realAmount) as sumRealAmount,SUM(yearAmount) as sumYearAmount ", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 投标记录sum
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestSum(Connection conn, String flag, String bTitle, String investor,
			String timeStart, String timeEnd, int borrowWayInt,
			int isAutoBidInt, int deadlineInt, double sumAmountDown, double sumAmountUp, int groupInt)throws SQLException, DataException{
		Dao.Views.v_t_invest_statis_detail  v_t_invest_statis_detail  = new Dao().new Views().new v_t_invest_statis_detail();
		
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		StringBuffer condition = new StringBuffer(" 1=1 ");
		if (StringUtils.isNotBlank(bTitle)) {
			condition.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(bTitle.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(investor)) {
			if("record".equals(flag)){
				condition.append(" and investor like '%"
						+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
			}else {
				condition.append(" and investor = '"
						+ StringEscapeUtils.escapeSql(investor.trim()) + "'");
			}
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + " 23:59:59'");
		}
		if (borrowWayInt > 0) {
			if(7 == borrowWayInt){
				condition.append(" and isDebt = 2 ");
			}else {
				condition.append(" and isDebt = 1 ");
				condition.append(" and borrowWay = " + borrowWayInt);
			}
		}
		if (isAutoBidInt > 0) {
			condition.append(" and isAutoBid = " + isAutoBidInt);
		}
		if (deadlineInt > 0) {
			condition.append(" and deadline = " + deadlineInt);
		}
		if (groupInt > 0) {
			condition.append(" and groupId = " + groupInt);
		}
		if (sumAmountUp > 0) {
			condition.append(" and realAmount <= " + sumAmountUp);
			condition.append(" and realAmount >= " + sumAmountDown);
		}
		DataSet dataSet = v_t_invest_statis_detail.open(conn, " SUM(realAmount) as sumAmount ", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 总投资列表统计+推荐人
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestSums(Connection conn, 
			String timeStart, String timeEnd)throws SQLException, DataException{
		Dao.Views.v_t_invest2  v_t_invest2  = new Dao().new Views().new v_t_invest2();
		
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		StringBuffer condition = new StringBuffer(" 1=1 ");
		
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + " 23:59:59'");
		}
		DataSet dataSet = v_t_invest2.open(conn, " SUM(investAmount) as sumAmount ", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 短信使用量统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryMs(Connection conn, 
			String timeStart, String timeEnd,String status)throws Exception{
		Dao.Views.v_t_smsrecord  v_t_smsrecord  = new Dao().new Views().new v_t_smsrecord();
		String currDateAgo = DateUtil.getCurrDateAgo().substring(0, 11);//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd().substring(0, 11);//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay().substring(0, 11);//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay().substring(0, 11);//本月第一天
		timeStart = Utility.filteSqlInfusion(timeStart);
	    timeEnd = Utility.filteSqlInfusion(timeEnd);
		StringBuffer condition = new StringBuffer(" 1=1 ");
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			condition.append(" and createTime >= '"+currDateAgo+"'"+" AND createTime <= '"+currDateAgoEnd+"'" );
			}	
		if("2".equals(status)){
			condition.append(" and  createTime >= '"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			condition.append(" and  createTime >= '"+currMonthFirstDay+"'" );
		}
		if("4".equals(status)){
			condition.append(" and createTime >= '"+timeStart+"'"+" and createTime < '"+timeEnd.substring(0, 10)+"'");
		}
		DataSet dataSet = v_t_smsrecord.open(conn, "*", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 注册来源统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryRegSrc(Connection conn, 
			String timeStart, String timeEnd,String status,int regSrc)throws Exception{
		Dao.Views.v_t_regsrc  v_t_regsrc  = new Dao().new Views().new v_t_regsrc();
		String currDateAgo = DateUtil.getCurrDateAgo().substring(0, 11);//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd().substring(0, 11);//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay().substring(0, 11);//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay().substring(0, 11);//本月第一天
		timeStart = Utility.filteSqlInfusion(timeStart);
	    timeEnd = Utility.filteSqlInfusion(timeEnd);
		StringBuffer condition = new StringBuffer(" 1=1 ");
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			condition.append(" and createTime >= '"+currDateAgo+"'"+" AND createTime <= '"+currDateAgoEnd+"'" );
			}	
		if("2".equals(status)){
			condition.append(" and  createTime >= '"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			condition.append(" and  createTime >= '"+currMonthFirstDay+"'" );
		}
		if("4".equals(status)){
			condition.append(" and createTime >= '"+timeStart+"'"+" and createTime < '"+timeEnd.substring(0, 10)+"'");
		}
		if(regSrc>=0){
			//0:'安卓',1:'苹果',2:'电脑'
			condition.append("  AND  regSrc='" + regSrc + "'");
		}
		DataSet dataSet = v_t_regsrc.open(conn, "*", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 实名认证统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryIdentify(Connection conn, 
			String timeStart, String timeEnd,String status)throws Exception{
		Dao.Views.v_t_identify  v_t_identify  = new Dao().new Views().new v_t_identify();
		String currDateAgo = DateUtil.getCurrDateAgo().substring(0, 11);//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd().substring(0, 11);//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay().substring(0, 11);//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay().substring(0, 11);//本月第一天
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		StringBuffer condition = new StringBuffer(" 1=1 ");

		if(status==null || "".equals(status)){
			status="1";
		}
		
		if("1".equals(status)){
			condition.append(" and createTime >= '"+currDateAgo+"'"+" AND createTime <= '"+currDateAgoEnd+"'" );
			}	
		if("2".equals(status)){
			condition.append(" and  createTime >= '"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			condition.append(" and  createTime >= '"+currMonthFirstDay+"'" );
		}
		if("4".equals(status)){
			condition.append(" and createTime >= '"+timeStart+"'"+" and createTime < '"+timeEnd.substring(0, 10)+"'");
		}
		DataSet dataSet = v_t_identify.open(conn, " * ", condition.toString(), "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 总发标统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryGiveBiao(Connection conn, String status, int borrowWay, String startTime,
			String endTime,  int deadlineInt)throws Exception{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT IFNULL(truncate(sum(a.borrowAmount),2),0) AS borrowAmount FROM t_borrow a where 1=1 ");
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" and a.applyTime >= '"+currDateAgo+"'"+" AND a.applyTime <= '"+currDateAgoEnd+"'" );
			}	
		if("2".equals(status)){
			sql.append(" and  a.applyTime >= '"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" and  a.applyTime >= '"+currMonthFirstDay+"'" );
		}
		if("4".equals(status)){
			sql.append(" and a.applyTime >= '"+startTime+"'"+" AND a.applyTime <= '"+endTime+"'" );
		}
		
		if(borrowWay>0){
			//3:'优选宝',4:'定息宝',6:'活利宝',7:'交易宝'
				sql.append("  AND  a.borrowWay='" + borrowWay + "'");
		}
		
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and  a.applyTime  >= '" + startTime + "'");
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and  a.applyTime  <= '" + endTime + "'");
		}
		if(deadlineInt>0){
				sql.append("  AND  a.deadline  = '" + deadlineInt + "'");
		} 
		 
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 总投标
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> querytouBiao(Connection conn, String status, int borrowWay, String startTime,
			String endTime,  int deadlineInt,int groupInt)throws Exception{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(a.investAmount),2),0) as touAmount from t_invest a,t_borrow b where a.borrowId=b.id   ");
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" and a.investTime >= '"+currDateAgo+"'"+" AND a.investTime <= '"+currDateAgoEnd+"'" );
			}	
		if("2".equals(status)){
			sql.append(" and  a.investTime >= '"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" and  a.investTime >= '"+currMonthFirstDay+"'" );
		}
		if("4".equals(status)){
			sql.append(" and a.investTime >= '"+startTime+"'"+" AND a.investTime <= '"+endTime+"'" );
		}
		
		if("1".equals(groupInt)||groupInt==1){
			sql.append(" and  a.investor not in (select userId from t_group_user where groupId=7 or groupId=6)" );
		}
		if("2".equals(groupInt)||groupInt==2){
			sql.append(" and  a.investor  in (select userId from t_group_user where groupId=7)" );
		}
		
		if(borrowWay>0){
			//3:'优选宝',4:'定息宝',6:'活利宝',7:'交易宝'
				sql.append("  AND  b.borrowWay='" + borrowWay + "'");
		}
		
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and  a.investTime  >= '" + startTime + "'");
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and  a.investTime  <= '" + endTime + "'");
		}
		if(deadlineInt>0){
				sql.append("  AND  b.deadline  = '" + deadlineInt + "'");
		} 
		 
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 推荐明细查询推荐人数
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryRecommendDetailSum(Connection conn, long recommendUserId)throws SQLException, DataException{
//		Dao.Views.v_t_recommend_sum  v_t_recommend_sum  = new Dao().new Views().new v_t_recommend_sum();
//		DataSet dataSet = v_t_recommend_sum.open(conn, " * ", " recommendUserId = " + recommendUserId, "", 0, 1);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t_recommend_user.recommendUserId as `recommendUserId`, IFNULL(COUNT(t_recommend_user.userId),0) as recomendSum, "
				+ "IFNULL(COUNT(IF(t_person.idNo!='',1,NULL)),0) as recomendRzSum, IFNULL(COUNT(a.investSum),0) as recomendTzSum, "
				+ "IFNULL(round(SUM(a.investAmountSum),2),0) as recomendInvestAmount from t_recommend_user LEFT JOIN t_person on t_recommend_user.userId = t_person.userId "
				+ "LEFT JOIN v_t_recommend_sum_invest as a on t_recommend_user.userId = a.investor where recommendUserId =" + recommendUserId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 待发送体检的用户
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryTobeSendUser(Connection conn)throws SQLException, DataException{
		Dao.Views.v_t_invest_examination  v_t_invest_examination  = new Dao().new Views().new v_t_invest_examination();
		DataSet dataSet = v_t_invest_examination.open(conn, " * ", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 7.6-8.6活动
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryInvestRank(Connection conn)throws SQLException, DataException{
		Dao.Views.v_t_invest_rank_activity  v_t_invest_rank_activity  = new Dao().new Views().new v_t_invest_rank_activity();
		DataSet dataSet = v_t_invest_rank_activity.open(conn, " * ", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 添加九华体检卡发送日志
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public long addExamination(Connection conn, long user_id, double amount, int card_no, int status, String remark)throws SQLException{
		Dao.Tables.t_examination t_examination = new Dao().new Tables().new t_examination();
		t_examination.user_id.setValue(user_id);
		t_examination.amount.setValue(amount);
		t_examination.card_no.setValue(card_no);
		t_examination.status.setValue(status);
		t_examination.remark.setValue(remark);
		t_examination.createTime.setValue(new Date());
		return t_examination.insert(conn);
	}
	
	public Map<String, String> queryBorrowStatisAmount(Connection conn,
			String borrowTitle, String borrower, String timeStart,
			String timeEnd, int borrowWayInt) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		borrowTitle=com.shove.web.Utility.filteSqlInfusion(borrowTitle);
		borrower=com.shove.web.Utility.filteSqlInfusion(borrower);
		timeEnd=com.shove.web.Utility.filteSqlInfusion(timeEnd);
		timeStart=com.shove.web.Utility.filteSqlInfusion(timeStart);
		command.append("SELECT SUM(manageFee) amount FROM v_t_borrow_statis where 1=1");
		if (StringUtils.isNotBlank(borrowTitle)) {
			command.append(" and  borrowTitle  like '%"+StringEscapeUtils.escapeSql(borrowTitle.trim())+"%' ");
		}
		if (StringUtils.isNotBlank(borrower)) {
			command.append(" and borrower  like '%"+ StringEscapeUtils.escapeSql(borrower.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			command.append(" and auditTime >= '" + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			command.append(" and auditTime <= '" + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (borrowWayInt != -1) {
			command.append(" and borrowWay = " + borrowWayInt);
		}
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 累计用户注册
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllRegiestUser(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as allUuerSum from t_user; ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 累计安卓用户注册
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllARegiestUser(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as allUuerSum from t_user where regSrc=0; ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}/**
	 * 累计苹果用户注册
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllIRegiestUser(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as allUuerSum from t_user where regSrc=1; ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 累计PC用户注册
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllPRegiestUser(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as allUuerSum from t_user where regSrc=2; ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 累计用户投资
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllInvestUser(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(m.investUserCount) as  investUserCount from  "
				+ " ( select a.username,b.investor,COUNT(*) as investUserCount from t_user a,t_invest b where a.id = b.investor and b.investor not in(select userId from t_group_user where groupId=6 or groupId=7) GROUP BY b.investor) as m ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 累计充值金额
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllFundUser(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(a.handleSum),2),0) as  handleSum  from t_fundrecord a where a.fundMode in ('手工充值','线下充值成功') ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 累计提现金额
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllWithdrawUser(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  withdrawSum  from t_withdraw a where a.status='2' ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 真实用户提现统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryAllWithdrawUser1(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  withdrawSum1  from t_withdraw a where a.status='2' AND a.userId NOT in (SELECT userId from t_group_user where groupId = 7 or groupId=6)");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 机构提现
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryAllWithdrawUser2(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  withdrawSum2  from t_withdraw a where a.status='2' AND a.userId in (SELECT userId from t_group_user where groupId = 7) ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 线下理财人提现统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryAllWithdrawUser3(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  withdrawSum3  from t_withdraw a where a.status='2' AND a.userId  in (SELECT userId from t_group_user where  groupId=6) ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 总还款
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryAllRepay(Connection conn,int ri) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		if(ri==1){
			sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d1 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where a.repayStatus=1");
		}else if(ri==2){
			sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d1 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where a.repayStatus=2");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 真实用户还款统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryAllRepay1(Connection conn,int ri) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		if(ri==1){
			sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d3 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where a.repayStatus=1 AND c.id NOT in (SELECT userId from t_group_user where groupId = 7 or groupId=6)");
		}else if(ri==2){
			sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d3 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where a.repayStatus=2 AND c.id NOT in (SELECT userId from t_group_user where groupId = 7 or groupId=6)");
		}
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 机构用户还款统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryAllRepay2(Connection conn,int ri) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		if(ri==1){
			sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d5 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where a.repayStatus=1 AND c.id  in (SELECT userId from t_group_user where groupId = 7 ) ");
		}else if (ri==2) {
			sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d5 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where a.repayStatus=2 AND c.id  in (SELECT userId from t_group_user where groupId = 7 ) ");
		}		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 线下理财还款统计
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryAllRepay3(Connection conn,int ri) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		if(ri==1){
			sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d7 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where a.repayStatus=1 AND c.id  in (SELECT userId from t_group_user where  groupId=6) ");
		}else if(ri==2){
			sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d7 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where a.repayStatus=2 AND c.id  in (SELECT userId from t_group_user where  groupId=6) ");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 新增充值金额
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllFundNewUser(Connection conn) throws Exception{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(a.handleSum),2),0) as  handleSum  from t_fundrecord a where a.fundMode in ('手工充值','线下充值成功')  and a.recordTime>='"+currDateAgo+"'");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 累计成交金额
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllUserUplineAndDownlineSum(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select IFNULL(truncate(SUM(c.investAmount),2),0) as investAmount,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as  investAmountF from t_invest c");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 总投资+推荐人
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryAllTouSum(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select IFNULL(truncate(SUM(a.investAmount),2),0) as touAmounts from t_invest a,t_borrow b where a.borrowId=b.id ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 总发标
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryAllGiveSum(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT IFNULL(truncate(sum(a.borrowAmount),2),0) AS borrowAmounts FROM t_borrow a ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 累计线下理财
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllUserDownlineSum(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(c.investAmount),2),0) as 'investX' ,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as 'investXF' from t_invest c where  c.investor  in (SELECT userId from t_group_user where groupId = 6)");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 累计线上投资
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllUserUplineSum(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select IFNULL(truncate(SUM(c.investAmount),2),0) as 'investS' ,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as 'investSF' from t_invest c where  c.investor  not in (SELECT userId from t_group_user where groupId = 6 or groupId=7)");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 图表
	 */
	public List chart(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(investTime, '%Y/%m') as cmonth,TRUNCATE(sum(investAmount),2) as allinvest,truncate(SUM(investAmount*(deadline/12)),2) as yearinvest from t_invest where  investor not in(select userId from t_group_user where groupId in (6,7)) group by DATE_FORMAT(investTime, '%Y/%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	/**
	 * 图表1
	 */
	public List borrowChart(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(a.investTime, '%Y/%m') as cmonth,TRUNCATE(sum(case when a.investor not in(select userId from t_group_user where groupId<9) then a.investAmount end),2) as realInvest ,TRUNCATE(sum(case when a.investor in(select userId from t_group_user where groupId=7) then a.investAmount end ),2) as groupInvest from t_invest a group by  DATE_FORMAT(a.investTime, '%Y/%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	/**
	 * 图表2
	 */
	public List borrowChart1(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(b.investTime, '%Y%m') as cmonth,truncate(sum(b.investAmount),2) as allinvest from t_borrow a,t_invest b where a.id=b.borrowId   and b.investor not in(select userId from t_group_user where groupId<9) GROUP BY DATE_FORMAT(b.investTime, '%Y%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	//活力宝
	public List borrowChart11(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(b.investTime, '%Y%m') as cmonth,truncate(sum(b.investAmount),2) as allinvest1 from t_borrow a,t_invest b where a.id=b.borrowId  and a.borrowWay=6 and b.investor not in(select userId from t_group_user where groupId<9) GROUP BY DATE_FORMAT(b.investTime, '%Y%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	public List borrowChart2(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(b.investTime, '%Y%m') as cmonth,truncate(sum(b.investAmount),2) as allinvest2 from t_borrow a,t_invest b where a.id=b.borrowId  and a.borrowWay=4  and b.investor not in(select userId from t_group_user where groupId<9) GROUP BY DATE_FORMAT(b.investTime, '%Y%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	public List borrowChart3(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(b.investTime, '%Y%m') as cmonth,truncate(sum(b.investAmount),2) as allinvest3 from t_borrow a,t_invest b where a.id=b.borrowId  and a.borrowWay=4 and a.deadline=2 and b.investor not in(select userId from t_group_user where groupId<9) GROUP BY DATE_FORMAT(b.investTime, '%Y%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	public List borrowChart4(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(b.investTime, '%Y%m') as cmonth,truncate(sum(b.investAmount),2) as allinvest4 from t_borrow a,t_invest b where a.id=b.borrowId  and a.borrowWay=4 and a.deadline=3 and b.investor not in(select userId from t_group_user where groupId<9) GROUP BY DATE_FORMAT(b.investTime, '%Y%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	public List borrowChart5(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(b.investTime, '%Y%m') as cmonth,truncate(sum(b.investAmount),2) as allinvest5 from t_borrow a,t_invest b where a.id=b.borrowId  and a.borrowWay=4 and a.deadline=6 and b.investor not in(select userId from t_group_user where groupId<9) GROUP BY DATE_FORMAT(b.investTime, '%Y%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	public List borrowChart6(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(b.investTime, '%Y%m') as cmonth,truncate(sum(b.investAmount),2) as allinvest6 from t_borrow a,t_invest b where a.id=b.borrowId  and a.borrowWay=4 and a.deadline=12 and b.investor not in(select userId from t_group_user where groupId<9) GROUP BY DATE_FORMAT(b.investTime, '%Y%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	/**
	 * 图表1
	 */
	public List investChart(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(investTime, '%d') as cmonth,TRUNCATE(sum(investAmount),2) as allinvest from t_invest where  DATE_FORMAT(investTime, '%Y/%m')=DATE_FORMAT(NOW(), '%Y/%m')   and investor not in(select userId from t_group_user where groupId in (6,7)) group by DATE_FORMAT(investTime, '%d')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	/**
	 * 图表1
	 */
	public List regChart(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(createTime, '%Y/%m') as cmonth,count(id) as alluser from t_user where  id not in(select userId from t_group_user where groupId in (6,7)) group by DATE_FORMAT(createTime, '%Y/%m')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	/**
	 * 图表1
	 */
	public List regChart1(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select DATE_FORMAT(createTime, '%d') as cmonth,count(id) as alluser from t_user where  DATE_FORMAT(createTime, '%Y/%m')=DATE_FORMAT(NOW(), '%Y/%m')   and id not in(select userId from t_group_user where groupId in (6,7)) group by DATE_FORMAT(createTime, '%d')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	/**
	 * 图表1
	 */
	public List regSourceChart(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("select regSrc as regSrc,count(id) as alluser from t_user where  regSrc <> 0 or regSrc='' and DATE_FORMAT(createTime, '%Y/%m')=DATE_FORMAT(NOW(), '%Y/%m')  and id not in(select userId from t_group_user where groupId in (6,7)) group by regSrc");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;	
		}
	/**
	 * 累计机构投资
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllUserOrgSum(Connection conn) throws SQLException,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(c.investAmount),2),0) as 'investJ' ,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as 'investJF' from t_invest c where  c.investor   in (SELECT userId from t_group_user where groupId = 7)");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 用户可用余额
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllUserAbleSum(Connection conn) throws Exception,DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select SUM(t.usableSum) as usableSum from t_user t  ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 新增注册用户
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryNewRegUser(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.createTime>='"+currDateAgo+"'"+" and a.createTime<='"+currDateAgoEnd+"'" );
		}
		if("2".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.createTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.createTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.createTime>='"+startTime+"'"+" and a.createTime<='"+endTime+"'");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增安卓注册用户
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryNewARegUser(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=0 and a.createTime>='"+currDateAgo+"'"+" and a.createTime<='"+currDateAgoEnd+"'" );
		}
		if("2".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=0 and a.createTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=0 and a.createTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=0 and a.createTime>='"+startTime+"'"+" and a.createTime<='"+endTime+"'");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增苹果注册用户
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryNewIRegUser(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=1 and a.createTime>='"+currDateAgo+"'"+" and a.createTime<='"+currDateAgoEnd+"'" );
		}
		if("2".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=1 and a.createTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=1 and a.createTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=1 and a.createTime>='"+startTime+"'"+" and a.createTime<='"+endTime+"'");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增PC注册用户
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryNewPRegUser(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=2 and a.createTime>='"+currDateAgo+"'"+" and a.createTime<='"+currDateAgoEnd+"'" );
		}
		if("2".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=2 and a.createTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=2 and a.createTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" select count(*) as newRegUsers   from t_user a where a.regSrc=2 and a.createTime>='"+startTime+"'"+" and a.createTime<='"+endTime+"'");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增提现金额
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryNewWithdraw(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw  from t_withdraw a where a.status='2' and a.checkTime>='"+currDateAgo+"'"+" and a.checkTime<='"+currDateAgoEnd+"'" );
		}
		if("2".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw  from t_withdraw a where a.status='2' and a.checkTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw  from t_withdraw a where a.status='2' and a.checkTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw  from t_withdraw a where a.status='2' and a.checkTime>='"+startTime+"'"+" and a.checkTime<='"+endTime+"'");
		}

		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增真实用户提现
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryNewWithdraw1(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw1  from t_withdraw a where a.status='2' and  a.userId NOT in (SELECT userId from t_group_user where groupId = 7 or groupId=6) and a.checkTime>='"+currDateAgo+"'"+" and a.checkTime<='"+currDateAgoEnd+"'" );
		}
		if("2".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw1  from t_withdraw a where a.status='2' AND a.userId NOT in (SELECT userId from t_group_user where groupId = 7 or groupId=6) and a.checkTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw1  from t_withdraw a where a.status='2' AND a.userId NOT in (SELECT userId from t_group_user where groupId = 7 or groupId=6) and a.checkTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw1  from t_withdraw a where a.status='2' AND a.userId NOT in (SELECT userId from t_group_user where groupId = 7 or groupId=6) and a.checkTime>='"+startTime+"'"+" and a.checkTime<='"+endTime+"'");
		}

		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增机构用户提现
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryNewWithdraw2(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw2  from t_withdraw a where a.status='2' AND a.userId  in (SELECT userId from t_group_user where groupId = 7) and a.checkTime>='"+currDateAgo+"'"+" and a.checkTime<='"+currDateAgoEnd+"'" );
		}
		if("2".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw2  from t_withdraw a where a.status='2' AND a.userId  in (SELECT userId from t_group_user where groupId = 7 ) and a.checkTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw2  from t_withdraw a where a.status='2' AND a.userId  in (SELECT userId from t_group_user where groupId = 7 ) and a.checkTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw2  from t_withdraw a where a.status='2' AND a.userId  in (SELECT userId from t_group_user where groupId = 7 ) and a.checkTime>='"+startTime+"'"+" and a.checkTime<='"+endTime+"'");
		}

		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增总还款
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryNewRepay(Connection conn,String status,String startTime,String endTime,int ri) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		sql.append("SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d2 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where 1=1");
		if("1".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currDateAgo+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currDateAgo+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}
		}
		if("2".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1  AND a.repayDate>='"+currWeekFirstDay+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currWeekFirstDay+"'");
			}
			
		}
		if("3".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currMonthFirstDay+"'");
			}else if(ri==2){
			sql.append("  and a.repayStatus=2 AND a.repayDate>='"+currMonthFirstDay+"'");}
		}
		if("4".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+startTime+"'"+" and   a.repayDate<'"+endTime+"'");
			}else if(ri==2){
			sql.append(" and a.repayStatus=2 AND a.repayDate>='"+startTime+"'"+" and   a.repayDate<'"+endTime+"'");
			}
		}

		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增真实用户待还款
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryNewRepay1(Connection conn,String status,String startTime,String endTime,int ri) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d4 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where c.id not in(SELECT userId from t_group_user where groupId = 7 or groupId=6)");
		if("1".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currDateAgo+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currDateAgo+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}
		}
		if("2".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currWeekFirstDay+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currWeekFirstDay+"'");
			}
		}
		if("3".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currMonthFirstDay+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currMonthFirstDay+"'");
			}		}
		if("4".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+startTime+"'"+" and   a.repayDate<'"+endTime+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+startTime+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}		}

		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增机构用户待还款
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryNewRepay2(Connection conn,String status,String startTime,String endTime,int ri) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d6 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where c.id  in(SELECT userId from t_group_user where groupId = 7)");
		if("1".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currDateAgo+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currDateAgo+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}
		}
		if("2".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currWeekFirstDay+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currWeekFirstDay+"'");
			}
		}
		if("3".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currMonthFirstDay+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currMonthFirstDay+"'");
			}		}
		if("4".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+startTime+"'"+" and   a.repayDate<'"+endTime+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+startTime+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}		}


		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增线下理财待还款
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryNewRepay3(Connection conn,String status,String startTime,String endTime,int ri) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		sql.append(" SELECT IFNULL(truncate(sum(a.stillPrincipal + a.stillInterest),2),0) as d8 FROM (t_repayment a LEFT JOIN t_borrow b ON ((a.borrowId = b.id)) LEFT JOIN t_user c ON ((b.publisher = c.id)) LEFT JOIN t_person d ON ((d.userId = c.id))) where c.id  in(SELECT userId from t_group_user where groupId = 6)");
		if("1".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currDateAgo+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currDateAgo+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}
		}
		if("2".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currWeekFirstDay+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currWeekFirstDay+"'");
			}
		}
		if("3".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+currMonthFirstDay+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+currMonthFirstDay+"'");
			}		}
		if("4".equals(status)){
			if(ri==1){
				sql.append(" and a.repayStatus=1 AND a.repayDate>='"+startTime+"'"+" and   a.repayDate<'"+endTime+"'");
			}else if(ri==2){
				sql.append(" and a.repayStatus=2 AND a.repayDate>='"+startTime+"'"+" and a.repayDate<'"+currDateAgoEnd+"'");
			}		}

		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增线下理财人提现
	 * @author gxy
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Map<String,String> queryNewWithdraw3(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天开始
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw3  from t_withdraw a where a.status='2' AND a.userId  in (SELECT userId from t_group_user where  groupId=6) and a.checkTime>='"+currDateAgo+"'"+" and a.checkTime<='"+currDateAgoEnd+"'" );
		}
		if("2".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw3  from t_withdraw a where a.status='2' AND a.userId  in (SELECT userId from t_group_user where  groupId=6) and a.checkTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw3  from t_withdraw a where a.status='2' AND a.userId  in (SELECT userId from t_group_user where groupId=6) and a.checkTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(a.sum),2),0) as  newWithdraw3  from t_withdraw a where a.status='2' AND a.userId  in (SELECT userId from t_group_user where groupId=6) and a.checkTime>='"+startTime+"'"+" and a.checkTime<='"+endTime+"'");
		}

		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 新增投资用户
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryNewInvestUser(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append("  select count(m.investUserCount) as  investUserCount from  "
					+ " ( select b.investTime, a.username,b.investor,COUNT(*) as investUserCount from t_user a,t_invest b where a.id = b.investor "
					+ " and b.investTime>='"+currDateAgo+"'"+" and b.investTime<'"+currDateAgoEnd+"'"
					+ " and b.investor not in(select userId from t_group_user where groupId=6 or groupId=7) GROUP BY b.investor) as m ");
		}
		if("2".equals(status)){
			sql.append("  select count(m.investUserCount) as  investUserCount from  "
					+ " ( select b.investTime, a.username,b.investor,COUNT(*) as investUserCount from t_user a,t_invest b where a.id = b.investor "
					+ " and b.investTime>='"+currWeekFirstDay+"'"
					+ " and b.investor not in(select userId from t_group_user where groupId=6 or groupId=7) GROUP BY b.investor) as m ");
		}
		if("3".equals(status)){
			sql.append("  select count(m.investUserCount) as  investUserCount from  "
					+ " ( select b.investTime, a.username,b.investor,COUNT(*) as investUserCount from t_user a,t_invest b where a.id = b.investor "
					+ " and b.investTime>='"+currMonthFirstDay+"'"
					+ " and b.investor not in(select userId from t_group_user where groupId=6 or groupId=7) GROUP BY b.investor) as m ");
		}
		if("4".equals(status)){
			sql.append("  select count(m.investUserCount) as  investUserCount from  "
					+ " ( select b.investTime, a.username,b.investor,COUNT(*) as investUserCount from t_user a,t_invest b where a.id = b.investor "
					+ " and b.investTime>='"+startTime+"'"+" and b.investTime<'"+endTime+"'"
					+ " and b.investor not in(select userId from t_group_user where groupId=6 or groupId=7) GROUP BY b.investor) as m ");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * 新增投资金额
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryNewInvestMoney(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append("  select IFNULL(truncate(SUM(c.investAmount),2),0) as investNewAmount,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as  investNewAmountF from t_invest c where c.investTime>='"+currDateAgo+"'"+" and c.investTime<'"+currDateAgoEnd+"' ");
		}
		if("2".equals(status)){
			sql.append("  select IFNULL(truncate(SUM(c.investAmount),2),0) as investNewAmount,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as  investNewAmountF from t_invest c where c.investTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append("   select IFNULL(truncate(SUM(c.investAmount),2),0) as investNewAmount,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as  investNewAmountF from t_invest c where c.investTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append("  select IFNULL(truncate(SUM(c.investAmount),2),0) as investNewAmount,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as  investNewAmountF from t_invest c where c.investTime>='"+startTime+"'"+" and c.investTime<'"+endTime+"'");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询提现金额
	 * @param conn
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String,String> queryTxMoney(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer sql = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(handleSum),2),0) as  handleSum from t_fundrecord a where a.fundMode = '提现成功'"
					+ "  and a.recordTime>='"+currDateAgo+"'"+" and a.recordTime <'"+currDateAgoEnd+"'");
		}
		if("2".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(handleSum),2),0) as  handleSum from t_fundrecord a where a.fundMode = '提现成功'"
					+ "  and a.recordTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(handleSum),2),0) as  handleSum from t_fundrecord a where a.fundMode = '提现成功'"
					+ "  and a.recordTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" select IFNULL(truncate(SUM(handleSum),2),0) as  handleSum from t_fundrecord a where a.fundMode = '提现成功'"
					+ "  and a.recordTime>='"+startTime+"'"+" and a.recordTime< '"+endTime+"'");
		}
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 新增机构投资
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllUserNewOrgSum(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		if(status==null || "".equals(status)){
			status="1";
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(c.investAmount),2),0) as investNewAmountJ,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as  investNewAmountJF from t_invest c where c.investor in (select userId from t_group_user where groupId=7)");
		
		if("1".equals(status)){
			sql.append(" AND c.investTime>='"+currDateAgo+"'"+" and c.investTime<'"+currDateAgoEnd+"'");
		}
		if("2".equals(status)){
			sql.append(" AND c.investTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" AND c.investTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" AND c.investTime>='"+startTime+"'"+" and   c.investTime<'"+endTime+"'");
		}
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 新增线上投资
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllUserNewUpLineSum(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		if(status==null || "".equals(status)){
			status="1";
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(c.investAmount),2),0) as investNewAmountS,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as  investNewAmountSF from t_invest c where c.investor not in (select userId from t_group_user where groupId=6 or groupId=7) ");
		
		if("1".equals(status)){
			sql.append(" AND c.investTime>='"+currDateAgo+"'"+" and c.investTime<'"+currDateAgoEnd+"'");
		}
		if("2".equals(status)){
			sql.append(" AND c.investTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" AND c.investTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" AND c.investTime>='"+startTime+"'"+" and   c.investTime<'"+endTime+"'");
		}
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 新增线下理财
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryAllUserNewDownLineSum(Connection conn,String status,String startTime,String endTime) throws Exception,DataException{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		if(status==null || "".equals(status)){
			status="1";
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select IFNULL(truncate(SUM(c.investAmount),2),0) as investNewAmountX,IFNULL(truncate(SUM(c.investAmount*(deadline/12)),2),0) as  investNewAmountXF from t_invest c where c.investor in (select userId from t_group_user where groupId=6)");
		if("1".equals(status)){
			sql.append(" AND c.investTime>='"+currDateAgo+"'"+" and c.investTime<'"+currDateAgoEnd+"'");
		}
		if("2".equals(status)){
			sql.append(" AND c.investTime>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			sql.append(" AND c.investTime>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			sql.append(" AND c.investTime>='"+startTime+"'"+" and  c.investTime<'"+endTime+"'");
		}
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	 /**
	  * 查询统计详情
	  * @param conn
	  * @param pageBean
	  * @param status
	  * @param startTime
	  * @param endTime
	  * @throws SQLException
	  * @throws DataException
	  */
	public void sysBaseStatisDetail(Connection conn,PageBean<Map<String, Object>> pageBean,String status,String startTime,String endTime) throws Exception{
		String currDateAgo = DateUtil.getCurrDateAgo();//昨天
		String currDateAgoEnd = DateUtil.getCurrDateAgoEnd();//昨天结束
		String currWeekFirstDay = DateUtil.getCurrWeekFirstDay();//本周第一天
		String currMonthFirstDay = DateUtil.getCurrMonthFirstDay();//本月第一天
		StringBuffer condition = new StringBuffer();
		if(status==null || "".equals(status)){
			status="1";
		}
		if("1".equals(status)){
			condition.append("  s_date>='"+currDateAgo+"'"+" and s_date<'"+currDateAgoEnd+"'");
		}
		if("2".equals(status)){
			condition.append("  s_date>='"+currWeekFirstDay+"'");
		}
		if("3".equals(status)){
			condition.append("  s_date>='"+currMonthFirstDay+"'");
		}
		if("4".equals(status)){
			condition.append(" s_date>='"+startTime+"'"+" and s_date<'"+endTime+"'");
		}
		Dao.Tables.t_sysbase_statis t_sysbase_statis = new Dao().new Tables().new t_sysbase_statis();
		long c=t_sysbase_statis.getCount(conn, condition.toString()); 
		boolean  result=pageBean.setTotalNum(c); 
		if(result){
			DataSet ds= t_sysbase_statis.open(conn, " DATE_FORMAT(s_date,'%Y-%m-%d') as s_date_f,t_sysbase_statis.*  ", condition.toString(), " ", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();
			pageBean.setPage(ds.tables.get(0).rows.rowsMap); 
		}
	}
	
	/**
	 * 每天系统跑P处理要统计的数据
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public long addSysBaseStatis(Connection conn,Date s_date,long s_reg_user_sum,long s_invest_user_sum,
			double s_cz_money,double s_tx_money,double s_tz_money,
			double s_org_moey,double s_upline_moeny,double s_downline_money)throws SQLException{
		Dao.Tables.t_sysbase_statis t_sysbase_statis = new Dao().new Tables().new t_sysbase_statis();
		t_sysbase_statis.s_date.setValue(s_date);
		t_sysbase_statis.s_reg_user_sum.setValue(s_reg_user_sum);
		t_sysbase_statis.s_invest_user_sum.setValue(s_invest_user_sum);
		t_sysbase_statis.s_cz_money.setValue(s_cz_money);
		t_sysbase_statis.s_tx_money.setValue(s_tx_money);
		t_sysbase_statis.s_tz_money.setValue(s_tz_money);
		t_sysbase_statis.s_org_moey.setValue(s_org_moey);
		t_sysbase_statis.s_upline_moeny.setValue(s_upline_moeny);
		t_sysbase_statis.s_downline_money.setValue(s_downline_money);
		return t_sysbase_statis.insert(conn);
	}
	
	
	public Map<String,String> queryRecomendRationHelp(Connection conn,String userName,String reallyName,String mobilePhone) throws Exception,DataException{
		 
		StringBuffer sql = new StringBuffer();
		sql.append(" select COUNT(*) as totalNum from (select p.id,p.username,p.mobilePhone,p.realName,p.recomendSum,p.recomendRzSum, count(DISTINCT(u.investor)) as recomendTzSum,  "
				+ " sum(u.investAmount) as recomendInvestAmount from  (select m.id,m.username,m.mobilePhone,m.realName,m.recomendSum  ,COUNT(*) AS recomendRzSum from (select a.id,a.username,  "
				+ " a.mobilePhone, c.realName,COUNT(*) AS recomendSum from t_user a ,t_recommend_user b ,t_person c where a.id = b.recommendUserId and a.id = c.userId GROUP BY a.username  "
				+ " ORDER BY a.id DESC ) m,t_recommend_user n,t_person z where m.id = n.recommendUserId and n.userId = z.userId  GROUP BY m.id) p LEFT JOIN  "
				+ " t_recommend_user o ON p.id = o.recommendUserId  LEFT JOIN  t_invest u ON  o.userId = u.investor GROUP BY p.id) g where 1=1 ");
		if(userName!=null && !"".equals(userName)){
			sql.append("  and  g.username  like '%" + userName + "%'");
		}
		
		if(reallyName!=null && !"".equals(reallyName)){
			sql.append("  AND  g.realName  like '%" + reallyName + "%'");
		}
		
		if(mobilePhone!=null && !"".equals(mobilePhone)){
			sql.append(" and  g.mobilePhone  = '" + mobilePhone + "'");
		}
		  
		 
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	public List<Map<String, Object>> queryMoneySendAll(Connection conn)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.reward_type as reward_type, SUM(a.reward_amount) as reward_amount from t_reward_record a GROUP BY a.reward_type ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public long addActivtyAny(Connection conn,double values,String cheeck_code,int day)throws SQLException{
		Dao.Tables.t_activty_any t_activty_any = new Dao().new Tables().new t_activty_any();
		t_activty_any.values.setValue(values);
		t_activty_any.check_code.setValue(cheeck_code);
		t_activty_any.start_time.setValue(new Date());
		t_activty_any.state.setValue(1);
		t_activty_any.day.setValue(day);
		return t_activty_any.insert(conn);
	}
	
	public long updateActivtyAny(Connection conn,long id)throws SQLException{
		Dao.Tables.t_activty_any t_activty_any = new Dao().new Tables().new t_activty_any();
		t_activty_any.state.setValue(2);
		return t_activty_any.update(conn, " id = "+id);
	}
	
	public List<Map<String, Object>> queryActivtyAnyExcel(Connection conn,String check_code, String state,String start_time,String end_time)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,date_format(a.start_time,'%Y-%m-%d') AS start_time_f,date_format(a.end_time,'%Y-%m-%d') AS end_time_f,"
			 
				+ "  if(b.username is null ,'',b.username) as username , if(b.mobilePhone is null ,'',b.mobilePhone) as mobilePhone , "
				+ "  CASE(a.state) when 1 then '未发送' when 2 then '已使用'  when 3 then '已发送'  when 4 then '已过期'  else '异常数据' end  as states "
				+ " from t_activty_any a LEFT JOIN t_user b on a.user_id = b.id where 1=1 ");
		if(check_code!=null && !"".equals(check_code)){
			sql.append("  AND  check_code  = '" + check_code+"'");
		}
		  
		if(state!=null && !"".equals(state)){
			sql.append(" AND  state  = '" + state+"'");
		}
		if(start_time!=null && !"".equals(start_time)){
			sql.append(" AND  start_time  >= '" + start_time+"'");
		}
		if(end_time!=null && !"".equals(end_time)){
			sql.append(" AND  end_time  <= '" + end_time+"'");
		}
		
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

}
