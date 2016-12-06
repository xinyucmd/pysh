/**
 * 
 */
package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.util.DBReflectUtil;

/**
 * 债权转让
 * 
 * @author Administrator
 * 
 */
public class AssignmentDebtDao {
	
	/**
	 * 系统自动撤销查询转让中债权
	 * @author whb
	 * @param conn，borrowId:借款id，userId:用户id，investId:投标id
	 * @param flag:sql区分标识，1：查询债转已过期，2：查询债转未过期
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> getDebtStatus(Connection conn, int flag) throws SQLException,
			DataException {

		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		condition.append(" and debtStatus = 1");
		if (1 == flag ) {
			condition.append(" and DATE_ADD(publishTime,INTERVAL debtLimit DAY) < NOW()");
		}
		if (2 == flag ) {
			condition.append(" and DATE_ADD(publishTime,INTERVAL debtLimit DAY) > NOW()");
		}
		
		DataSet ds = t_assignment_debt.open(conn, "*", condition.toString(), "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		condition=null;
		return ds.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 系统自动撤销债权转让--还款计划表
	 * @author whb
	 * @param conn，borrowId:借款id，userId:用户id，investId:投标id
	 * @param flag:sql区分标识，1：查询是否逾期，2：查询是否全部还款
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getDebtInvest(Connection conn, long borrowId, long investId, int flag)
			throws SQLException, DataException {
		
		Dao.Tables.t_invest_repayment t_invest_repayment = new Dao().new Tables().new t_invest_repayment();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		if (borrowId > 0) {
			condition.append(" and t_invest_repayment.borrow_id =");
			condition.append(borrowId);
		}
		if (investId > 0) {
			condition.append(" and t_invest_repayment.invest_id =");
			condition.append(investId);
		}
		if (1 == flag ) {
			condition.append(" and t_invest_repayment.isLate = 2 and t_invest_repayment.repayDate < NOW() and date_add(t_invest_repayment.repayDate, interval 1 month) > NOW()");
		}
		if (2 == flag ) {
			condition.append(" and t_invest_repayment.repayStatus = 1");
		}
		
		DataSet ds = t_invest_repayment.open(conn, " COUNT(id) AS count ", condition.toString(), "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 获取借款信息
	 * 
	 * @param conn，borrowId:借款id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getOnePay(Connection conn,long borrowId) throws SQLException, DataException{
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		StringBuffer condition = new StringBuffer(" 1=1 ");
		if(borrowId>0){
			condition.append(" and id ="+borrowId );
		}
		DataSet ds = t_borrow.open(conn, "*", condition.toString(), "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 查询剩余待还期数
	 * @param conn
	 * @param borrowId
	 * @param investId
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getPeriodsBanlanceCount(Connection conn, long borrowId, long investId)
			throws SQLException, DataException {
		StringBuilder command = new StringBuilder("");
		command.append("SELECT COUNT(id) as periodsBanlanceCount from t_invest_repayment ");
		
		command.append(" where t_invest_repayment.borrow_id ="+borrowId);
		command.append(" and t_invest_repayment.invest_id ="+investId);
//		command.append(" AND t_invest_repayment.`owner` ="+userId);
		command.append(" AND t_invest_repayment.realRepayDate is null");
		
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		command=null;
		return  BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 查询剩余待还本金
	 * @param conn
	 * @param borrowId
	 * @param investId
	 * @param userId
	 * @param nextRepayDate
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getPrincipalBalance(Connection conn, long borrowId, long investId,String nextRepayDate)
			throws SQLException, DataException {
		StringBuilder command = new StringBuilder("");
		command.append(" SELECT MIN(t_invest_repayment.principalBalance) AS principalBalance ");
		command.append(" from t_invest_repayment  LEFT JOIN t_invest ON t_invest_repayment.invest_id = t_invest.id ");
		
		command.append(" where t_invest_repayment.repayStatus = 2 ");
		command.append(" and t_invest_repayment.repayDate = date_sub('"+nextRepayDate+"', interval 1 month)");
		command.append(" and t_invest_repayment.borrow_id ="+borrowId);
		command.append(" and t_invest_repayment.invest_id ="+investId);
//		command.append(" AND t_invest_repayment.`owner` ="+userId);
		
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		command=null;
		return  BeanMapUtils.dataSetToMap(ds);
	}
	
	public Map<String,String> getDebtByConditions(Connection conn,long borrowId,long investId) throws SQLException, DataException{
		Dao.Views.v_t_can_assignment_borrow v_t_can_assignment_borrow = new Dao().new Views().new v_t_can_assignment_borrow();
		StringBuffer condition = new StringBuffer(" 1=1 ");
		if(borrowId>0){
			condition.append(" and borrowId ="+borrowId );
		}
		
		if(investId>0){
			condition.append(" and investId ="+investId );
		}
		
		DataSet ds = v_t_can_assignment_borrow.open(conn, "*", condition.toString(), "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 添加债权转让
	 * 
	 * @param conn
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long addAssignmentDebt(Connection conn, Map<String, String> paramMap)
			throws SQLException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DBReflectUtil.mapToTableValue(t_assignment_debt, paramMap);
		return t_assignment_debt.insert(conn);

	}
	
	/**
	 * 查询本月最大的序列值+1
	 * @param conn
	 * @param dateTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryMMaxDebtIndex(Connection conn,String dateTime) throws SQLException, DataException {
		
		StringBuilder command = new StringBuilder("");
		command.append(" SELECT maxDebtIndex from ");
		command.append(" ("
				+ "SELECT MAX(debtIndex)+1 as maxDebtIndex,DATE_FORMAT(publishTime,'%Y-%m'),t_assignment_debt.* from t_assignment_debt "
				+ " GROUP BY  DATE_FORMAT(publishTime,'%Y-%m')) as a  "
				+ " where  DATE_FORMAT(a.publishTime,'%Y-%m') = DATE_FORMAT('"+dateTime+"','%Y-%m');");
		
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		command=null;
		return  BeanMapUtils.dataSetToMap(ds);
	}
	
	

	/**
	 * 修改债权转让
	 * 
	 * @param conn
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long updateAssignmentDebt(Connection conn, long id,String debtStatus,
			Map<String, String> paramMap) throws SQLException {
		String idStr=com.shove.web.Utility.filteSqlInfusion(debtStatus);
		 idStr = StringEscapeUtils.escapeSql("'"+debtStatus+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DBReflectUtil.mapToTableValue(t_assignment_debt, paramMap);
		StringBuilder condition = new StringBuilder( " 1=1 ");
		condition.append(" and id=");
		condition.append(id);
		if(StringUtils.isNotBlank(debtStatus)){
			condition.append(" and debtStatus in(");
			condition.append(idSQL); 
			condition.append(")");
		}
		long result=t_assignment_debt.update(conn, condition.toString());
		condition=null;
		return result;

	}

	/**
	 * 删除债权转让，可删除多个
	 * 
	 * @param conn
	 * @param ids
	 *            id字符串，用,隔开
	 * @return
	 * @throws SQLException
	 */
	public long deleteAssignmentDebt(Connection conn, String ids)
			throws SQLException {
		String idStr=com.shove.web.Utility.filteSqlInfusion(ids);
		 idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		return t_assignment_debt.delete(conn, " id in("
				+ idSQL + ")");
	}

	/**
	 * 根据ID获债权转让信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getAssignmentDebt(Connection conn, long id)
			throws SQLException, DataException {
		
		StringBuilder command = new StringBuilder("");
		command.append("SELECT t_assignment_debt.*,t_borrow.paymentMode as paymentMode,");
		command.append("if((`t_borrow`.`borrowWay` = 4),concat('交易宝-',date_format(`t_assignment_debt`.`publishTime`,'%y%m'),`t_assignment_debt`.`debtIndexLable`),`t_borrow`.`borrowTitle`) ");
		command.append(" as debtBorrowTile from t_assignment_debt LEFT JOIN t_borrow on t_assignment_debt.borrowId = t_borrow.id");
		command.append(" WHERE t_assignment_debt.id ="+id);
		
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		command=null;
		return  BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 查询债转后的价值
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryDebtCoseInvestedList(Connection conn) throws SQLException,
			DataException {

		Dao.Views.v_debt_cose_invested v_debt_cose_invested = new Dao().new Views().new v_debt_cose_invested();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		DataSet ds = v_debt_cose_invested.open(conn, "*", condition.toString(), "",
				-1, -1);
		ds.tables.get(0).rows.genRowsMap();
		condition=null;
		return ds.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询债转后的价值
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryDebtCoseInvestingList(Connection conn) throws SQLException,
			DataException {

		Dao.Views.v_debt_cose_investing v_debt_cose_investing = new Dao().new Views().new v_debt_cose_investing();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		DataSet ds = v_debt_cose_investing.open(conn, "*", condition.toString(), "",
				-1, -1);
		ds.tables.get(0).rows.genRowsMap();
		condition=null;
		return ds.tables.get(0).rows.rowsMap;
	}
	
	
	/**
	 * 查询债转后的价值
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryDebtCoseInvestedMap(Connection conn,long investId) throws SQLException,
			DataException {

		Dao.Views.v_debt_cose_invested v_debt_cose_invested = new Dao().new Views().new v_debt_cose_invested();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		condition.append(" and v_debt_cose_invested.investId = "+investId);
		DataSet dataSet = v_debt_cose_invested.open(conn, "*", condition.toString(), "",
				-1, -1);
		condition=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询债转后的价值
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryDebtCoseInvestingMap(Connection conn,long investId) throws SQLException,
			DataException {

		Dao.Views.v_debt_cose_investing v_debt_cose_investing = new Dao().new Views().new v_debt_cose_investing();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		condition.append(" and v_debt_cose_investing.investId = "+investId);
		DataSet dataSet = v_debt_cose_investing.open(conn, "*", condition.toString(), "",
				-1, -1);
		condition=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	

	/**
	 * 跳转到审核页面
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryDebtBacking(Connection conn,
			long borrowId, long userId, long investId) throws SQLException,
			DataException {

		Dao.Views.v_debt_invest v_debt_invest = new Dao().new Views().new v_debt_invest();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		if (borrowId > 0) {
			condition.append(" and borrowId =");
			condition.append(borrowId);
		}
		if (userId > 0) {
			condition.append(" and investor =");
			condition.append(userId);
		}

		if (investId > 0) {
			condition.append(" and investId =");
			condition.append(investId);
		}
		DataSet ds = v_debt_invest.open(conn, "*", condition.toString(), "",
				-1, -1);
		ds.tables.get(0).rows.genRowsMap();
		condition=null;
		return ds.tables.get(0).rows.rowsMap;
	}

	public long addDebtMsg(Connection conn, long id, Long userId,
			String msgContent) throws SQLException {
		Dao.Tables.t_msgboard t_msgboard = new Dao().new Tables().new t_msgboard();
		t_msgboard.msgContent.setValue(msgContent);
		t_msgboard.modeId.setValue(id);
		// 债权转让留言类型
		t_msgboard.msgboardType.setValue(2);
		t_msgboard.msger.setValue(userId);
		t_msgboard.msgTime.setValue(new Date());
		return t_msgboard.insert(conn);
	}

	/**
	 * 根据借款Id查询借款
	 * 
	 * @param conn
	 * @param borrowId
	 * @param debtStatus
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAssignmentDebtIds(Connection conn,
			long borrowId, String debtStatus) throws SQLException,
			DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		if (borrowId > 0) {
			condition.append(" and borrowId=");
			condition.append(borrowId);
		}

		if (StringUtils.isNotBlank(debtStatus)) {
			String idStr=com.shove.web.Utility.filteSqlInfusion(debtStatus);
			 idStr = StringEscapeUtils.escapeSql("'"+debtStatus+"'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String [] array = idStr.split(",");
			for(int n=0;n<=array.length-1;n++){
			   idSQL += ","+array[n];
			}
			condition.append(" and debtStatus in (");
			condition.append(idSQL);
			condition.append(") ");
		}

		DataSet ds = t_assignment_debt.open(conn, "id", condition.toString(),
				"", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		condition=null;
		return ds.tables.get(0).rows.rowsMap;
	}

	public List<Map<String, Object>> queryDueDebt(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DataSet ds = t_assignment_debt.open(conn,"id,debtStatus"," publishTime <= now() and debtStatus=2","", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}

	/**
	 * 获取借款标题
	 * 
	 * @param strToLong
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String getBorrowTitle(Connection conn, long debtId)
			throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		DataSet ds = t_borrow.open(conn, "borrowTitle",
				" id = (select borrowId from t_assignment_debt t where t.id="
						+ debtId + ")", "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		String borrowTitle = null;
		if (map != null) {
			borrowTitle = map.get("borrowTitle");
		}
		map=null;
		return borrowTitle;
	}

	/**
	 * 获取用户名称
	 * 
	 * @param userId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryUserNameById(Connection conn, long userId)
			throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet ds = t_user
				.open(conn, "username", " id =" + userId, "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		String username = null;
		if (map != null) {
			username = map.get("username");
		}
		map=null;
		return username;
	}
	/**
	 * 判断是否可以债权转让
	 * @param strToLong
	 * @param strToLong2
	 * @return 返回true则不可以转让，否则可以
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public boolean isHaveAssignmentDebt(Connection conn, long investId, long userId) throws SQLException, DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		StringBuilder condition = new StringBuilder( " 1=1 ");
		condition.append(" and investId = ");
		condition.append(investId);
		condition.append(" and alienatorId = ");
		condition.append(userId);
		condition.append(" and debtStatus in (1,2,3)");
		DataSet ds = t_assignment_debt.open(conn,"count(1) as counts",condition.toString(),"", -1, -1);
		long count = Convert.strToLong(BeanMapUtils.dataSetToMap(ds).get("counts"),-1);
		condition=null;
		return count > 0;
	}
	/**
	 * 判断债权转让是否在某一状态
	 * @param strToLong
	 * @param strToLong2
	 * @return 返回true则在，否则不在
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public boolean isDebtInStatus(Connection conn,long id,String debtStatus) throws SQLException, DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		String idStr=com.shove.web.Utility.filteSqlInfusion(debtStatus);
		 idStr = StringEscapeUtils.escapeSql("'"+debtStatus+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		StringBuilder condition = new StringBuilder( " 1=1 ");
		condition.append(" and id = ");
		condition.append(id);
		condition.append(" and debtStatus in (");
		condition.append(idSQL);
		condition.append(")");
		DataSet ds = t_assignment_debt.open(conn,"count(1) as counts ",condition.toString(),"", -1, -1);
		long count = Convert.strToLong(BeanMapUtils.dataSetToMap(ds).get("counts"),-1);
		condition=null;
		return count > 0;
	}
	
	
	/**
	 * 通过
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public long goManageDebt(Connection conn,long id) throws SQLException{
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		t_assignment_debt.applyState.setValue("2");
		return t_assignment_debt.update(conn, "id="+id);
	}
	
	
		/**
	 * 查询债权转让人和竞拍成功人
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String>  queryDebtUserName(Connection  conn,long aid) throws DataException, SQLException{
		Dao.Views.v_t_assignment_debt_username  v_t_assignment_debt_username = new Dao().new Views().new v_t_assignment_debt_username();
		DataSet  ds = v_t_assignment_debt_username.open(conn, " *,date_format(createTime,'%Y年%m月%d日')  as DateTime ", " id = " + aid, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	
	public Map<String, String> queryApplyDebtDetail(Connection conn) throws SQLException, DataException{
		Dao.Views.v_t_assignment_debt_audit t_assignment_debt_audit = new Dao().new Views().new v_t_assignment_debt_audit();
		DataSet dataSet = t_assignment_debt_audit.open(conn, "sum(debtSum) as applydebtSum", "debtStatus=1", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryApplySuccessDebtDetail(Connection conn) throws SQLException, DataException{
		Dao.Views.v_admin_assignment_debt_borrow t_assignment_debt_audit = new Dao().new Views().new v_admin_assignment_debt_borrow();
		DataSet dataSet = t_assignment_debt_audit.open(conn, "sum(debtSum) as successapplydebtSum", "debtStatus=2", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryApplyFailDebtDetail(Connection conn) throws SQLException, DataException{
		Dao.Views.v_admin_assignment_debt_borrow admin_assignment_debt_borrow = new Dao().new Views().new v_admin_assignment_debt_borrow();
		DataSet dataSet = admin_assignment_debt_borrow.open(conn, "sum(debtSum) as failapplydebtSum", "debtStatus in (3,4)", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public List<Map<String, Object>> queryAuctioner(Connection conn, long debtId) throws Exception, DataException {
		Dao.Tables.t_invest_debt t_invest_debt = new Dao().new Tables().new t_invest_debt();
		DataSet ds = t_invest_debt.open(conn,"distinct userId","debtId = "+debtId,"", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 获取用户组每月可转让金额
	 * whb
	 * @param strToLong
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	
	public String getTransGroupMoney(Connection conn, long userId)
			throws SQLException, DataException {
		
		StringBuffer command = new StringBuffer();
		command.append(" SELECT transMoney FROM t_group WHERE id = IF((SELECT groupId FROM t_group_user WHERE userId = " + userId + " limit 1),");
		command.append("(SELECT groupId FROM t_group_user WHERE userId = " + userId + " limit 1),5) limit 0,1" );
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		String transMoney = String.valueOf(dataSet.tables.get(0).rows.rowsMap.get(0).get("transMoney"));
		
		return transMoney;
	}
	
	/**
	 * 获取用户每月累计债转金额
	 * whb
	 * @param strToLong
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	
	public String getTransUserMoney(Connection conn, long userId)
			throws SQLException, DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DataSet ds = t_assignment_debt.open(conn, " SUM(debtSum) AS debtSum ",
				" YEAR(publishTime) = YEAR(NOW()) and MONTH(publishTime) = MONTH(NOW()) and debtStatus = 2 and alienatorId = "+userId, "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		String debtSum = null;
		if (map != null) {
			debtSum = map.get("debtSum");
		}
		map=null;
		
		return debtSum;
	}
	
	public long addInvestDebtApply(Connection conn, long debtId,long userId,String pwd,double auctionPrice,String basePath,String debtValue)
			throws SQLException {
		Dao.Tables.t_invest_debt_apply t_invest_debt_apply = new Dao().new Tables().new t_invest_debt_apply();
		t_invest_debt_apply.debtId.setValue(debtId);
		t_invest_debt_apply.userId.setValue(userId);
		t_invest_debt_apply.pwd.setValue(pwd);
		t_invest_debt_apply.auctionPrice.setValue(auctionPrice);
		t_invest_debt_apply.basePath.setValue(basePath);
		t_invest_debt_apply.debtValue.setValue(debtValue);
		return t_invest_debt_apply.insert(conn);
	}
	
	public long updateInvestDebtApplyStatus(Connection conn,long id ,int stauts)throws SQLException {
		
		Dao.Tables.t_invest_debt_apply t_invest_debt_apply = new Dao().new Tables().new t_invest_debt_apply();
		t_invest_debt_apply.status.setValue(stauts);
		long result=t_invest_debt_apply.update(conn," id = " + id);
		
		return result;
	}
	
	public long updateDebtEnd(Connection conn,long id ,double debtValue,double debtPrice,int remainingDays,double annualRateDebtBDDouble)throws SQLException {
		
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		t_assignment_debt.debtValue.setValue(debtValue);
		t_assignment_debt.debtPrice.setValue(debtPrice);
		t_assignment_debt.remainingDays.setValue(remainingDays);
		t_assignment_debt.annualRateDebtBDDouble.setValue(annualRateDebtBDDouble);
		t_assignment_debt.endTime.setValue(new Date());
		long result=t_assignment_debt.update(conn," id = " + id);
		
		return result;
	}

	public List<Map<String, Object>> queryInvestDebtApply(Connection conn)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  *  from   t_invest_debt_apply t where t.status = 0");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Map<String,String> queryInvestDebtApplyById(Connection conn,long id)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  *  from   t_invest_debt_apply t where t.status = 0 and id = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/***
	 * 判断债转人是不是线下理财人 或者机构用户
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> findUserById(Connection conn,long userId)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  *  from   t_group_user t where t.groupId in(6,7) and t.userId =  "+userId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public List<Map<String, Object>> queryCanAssignmentDebt(Connection conn,String userNames, String debtAnnualRate,String debtDeadline) throws SQLException,DataException {

		Dao.Views.v_t_can_assignment_borrow v_t_can_assignment_borrow = new Dao().new Views().new v_t_can_assignment_borrow();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		
		if (StringUtils.isNotBlank(userNames)) {
			
			String[] userNameArr = userNames.split(",");
			userNames = "";
			for(String userName:userNameArr){
				userNames += "'"+userName+"',";
			}
			// 去掉最后的分隔符
			userNames = userNames.substring(0, userNames.length()-1);
			condition.append(" and  investor in (");
			condition.append("SELECT id from t_user where username in("+userNames+")");
			condition.append(") ");
		}
		
		condition.append(" and  borrowWay  in (3,4) ");
		
		condition.append(" and  debtId is null");
		
		DataSet ds = v_t_can_assignment_borrow.open(conn, "*", condition.toString(), " investorName desc ",
				-1, -1);
		ds.tables.get(0).rows.genRowsMap();
		condition=null;
		return ds.tables.get(0).rows.rowsMap;
	}
	
	public Map<String,String> queryDebtMap(Connection conn,long aliUserId,long borrowId)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  DATE_FORMAT(publishTime,'%Y-%m-%d') as  publishTime_f  from  t_assignment_debt where  alienatorId="+aliUserId+" and borrowId="+borrowId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public List<Map<String, Object>> queryRepayDebt(Connection conn,long borrowId)throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  a.stillInterest+a.stillPrincipal as repayValue ,a.repayDate ,b.paymentMode from  t_repayment a ,t_borrow b  where a.borrowId = b.id  and a.repayStatus =1 and a.borrowId="+borrowId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
}
