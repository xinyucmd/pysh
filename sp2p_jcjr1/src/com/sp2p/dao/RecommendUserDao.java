package com.sp2p.dao;import java.sql.Connection;import java.sql.SQLException;import java.util.List;import java.util.Map;import com.shove.data.DataException;import com.shove.data.DataSet;import com.shove.util.BeanMapUtils;import com.sp2p.database.Dao;public class RecommendUserDao {	/**	 * 添加用户和推荐人关系	 * @param conn	 * @param userId 	 * @param recommendUserId	 * @return	 * @throws SQLException	 */	public Long addRecommendUser(Connection conn,long userId,long recommendUserId,int recommendSrc,int point) throws SQLException{		Dao.Tables.t_recommend_user recommendUser = new Dao().new Tables().new t_recommend_user();		recommendUser.userId.setValue(userId);		recommendUser.recommendUserId.setValue(recommendUserId);		recommendUser.src.setValue(recommendSrc);		recommendUser.point.setValue(point);		return recommendUser.insert(conn);	}		/**	 * 修改用户和推荐人的奖励	 * 	 * @param conn	 * @param id	 * @param vouchersId	 * @return	 * @throws SQLException	 */	public Long updateRecommendUser(Connection conn,long id ,long moneyTypeId,int type) throws SQLException{		Dao.Tables.t_recommend_user recommendUser = new Dao().new Tables().new t_recommend_user();		recommendUser.moneyTypeId.setValue(moneyTypeId);		recommendUser.type.setValue(type);				return recommendUser.update(conn, " id="+id);	}		/**	 * 修改用户和推荐人的奖励	 * 	 * @param conn	 * @param id	 * @param vouchersId	 * @return	 * @throws SQLException	 */	public Long updateRecommendUser(Connection conn,long recommendUserId,long userId ,long moneyTypeId,int type) throws SQLException{		Dao.Tables.t_recommend_user recommendUser = new Dao().new Tables().new t_recommend_user();		recommendUser.moneyTypeId.setValue(moneyTypeId);		recommendUser.type.setValue(type);				return recommendUser.update(conn, " recommendUserId = "+recommendUserId+" and userId = "+userId);	}		/**	 * 根据条件查询数据	 * @param conn	 * @param id编号	 * @param userId用户编号	 * @param recommendUserId推荐人编号	 * @param vouchersId礼劵编号	 * @return	 * @throws DataException 	 * @throws SQLException 	 */	public List<Map<String,Object>> queryRecommendUser(Connection conn,Long id,Long userId,Long recommendUserId) throws SQLException, DataException{		StringBuffer condition = new StringBuffer();		condition.append(" 1=1 ");		if(id!=null&&id>0){			condition.append(" AND id= "+id);		} 		if(userId!=null&&userId>0){			condition.append(" AND userId= "+userId);		} 		if(recommendUserId!=null&&recommendUserId>0){			condition.append(" AND recommendUserId= "+recommendUserId);		} 		Dao.Tables.t_recommend_user recommendUser = new Dao().new Tables().new t_recommend_user();		DataSet dataSet = recommendUser.open(conn, "*", condition.toString(), "", -1, -1);		dataSet.tables.get(0).rows.genRowsMap();		condition = null;		return dataSet.tables.get(0).rows.rowsMap;	}			//根据邀请人Id查询所有被邀请人	public List<Map<String, Object>> findRecommendUserByRecommendId(Connection conn,Long recommendId)throws SQLException,DataException{		Dao.Views.v_t_recommendfriend_list friendList=new Dao().new Views().new v_t_recommendfriend_list();			DataSet dataSet=friendList.open(conn, " * ", "recommendUserId="+recommendId, "", -1, -1);			dataSet.tables.get(0).rows.genRowsMap();			return dataSet.tables.get(0).rows.rowsMap;	}		//根据用户Id查询邀请人Id	public Map<String, String> getRecommendUserByuserId(Connection conn,Long userId)throws SQLException,DataException{		Dao.Tables.t_recommend_user recommendUser = new Dao().new Tables().new t_recommend_user();		DataSet dataSet = recommendUser.open(conn, " * ", "userId=" + userId, "", -1, -1);		return BeanMapUtils.dataSetToMap(dataSet);	}		public Map<String,String> queryFriendCost(Connection  conn) throws SQLException, DataException	{		Dao.Tables.t_platform_cost  t_platform_cost = new Dao().new Tables().new t_platform_cost();		DataSet  ds=t_platform_cost.open(conn, " * ", " id = 2", "", -1, -1);		return BeanMapUtils.dataSetToMap(ds);	}		// 年终推广活动	public List<Map<String, Object>>  queryMyTop(Connection conn)throws SQLException, DataException{		Dao.Views.v_recommend_year_top  v_recommend_year_top = new Dao().new Views().new v_recommend_year_top();		DataSet  ds=v_recommend_year_top.open(conn, " * ", "", "", -1, -1);		ds.tables.get(0).rows.genRowsMap();		return ds.tables.get(0).rows.rowsMap;	}		// 年终推广活动	public Map<String,String> queryMyTop(Connection conn,long userId)throws SQLException, DataException{		Dao.Views.v_recommend_year_top  v_recommend_year_top = new Dao().new Views().new v_recommend_year_top();		DataSet  ds=v_recommend_year_top.open(conn, " * ", " userId = "+userId, "", -1, -1);		return BeanMapUtils.dataSetToMap(ds);	}		// 推荐用户记录	public Map<String,String> queryRecommendUserSummary(Connection conn,long recommendUserId)throws SQLException,DataException{		Dao.Views.v_recommend_user_summary  v_recommend_user_summary = new Dao().new Views().new v_recommend_user_summary();		DataSet  ds=v_recommend_user_summary.open(conn, " * ", " recommendUserId = "+recommendUserId, "", -1, -1);		return BeanMapUtils.dataSetToMap(ds);	}		// 推荐用户列表	public List<Map<String, Object>> queryRecommendUserInfo(Connection conn,long recommendUserId,long start,int end)throws SQLException,DataException{		Dao.Views.v_recommend_user_info  v_recommend_user_info = new Dao().new Views().new v_recommend_user_info();		DataSet  ds=v_recommend_user_info.open(conn, " * ", " recommendUserId = "+recommendUserId, "",start, end);		ds.tables.get(0).rows.genRowsMap();		return ds.tables.get(0).rows.rowsMap;	}	}