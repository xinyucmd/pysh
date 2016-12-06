package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;

public class RecommendUserCountDao extends RecommendBrokerageBase{

	public Long updateRecomendUserCount(Connection conn,Integer recommend_user_id,Integer has_count,Integer available_count) throws Exception{
		Dao.Tables.t_recommend_user_count t_recommend_user_count = new Dao().new Tables().new t_recommend_user_count();
		if(has_count != null){
			t_recommend_user_count.has_count.setValue(has_count);
		}
		if(available_count != null){
			t_recommend_user_count.available_count.setValue(available_count);
		}
		
		return t_recommend_user_count.update(conn, " recommend_user_id="+recommend_user_id);
	}
	
	public Map<String,Object> updateRecommendUserCount(Connection conn ,double investAmount,int process,int userId) throws Exception{
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_recommend_user_count_update(conn, ds, outParameterValues, investAmount,process,userId,-1, "",-3,"");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", Convert.strToLong(outParameterValues.get(0) + "", -1));
		result.put("desc",outParameterValues.get(1) + "");
		result.put("status_cinema",Convert.strToLong(outParameterValues.get(2) + "", -3));
		result.put("cinema_ticket",outParameterValues.get(3) + "");
		return result;
	}
	
	public Long insertRecommendCinemaTicket(Connection conn,Integer user_id,Integer type) throws SQLException{
		Dao.Tables.t_recommend_cinema_ticket t_recommend_cinema_ticket = new Dao().new Tables().new t_recommend_cinema_ticket();
		if(user_id !=null && user_id>0){
			t_recommend_cinema_ticket.user_id.setValue(user_id);
		}
		if(type !=null && type>0){
			t_recommend_cinema_ticket.type.setValue(type);
		}
		return t_recommend_cinema_ticket.insert(conn);
	}
	
	public Long updateRecommendCinemaTicket(Connection conn,Integer user_id,Integer id,Integer type) throws SQLException{
		Dao.Tables.t_recommend_cinema_ticket t_recommend_cinema_ticket = new Dao().new Tables().new t_recommend_cinema_ticket();
		if(user_id !=null && user_id>0){
			t_recommend_cinema_ticket.user_id.setValue(user_id);
		}
		if(type !=null && type>0){
			t_recommend_cinema_ticket.type.setValue(type);
		}
//		try {
//			t_recommend_cinema_ticket.last_modify_date.setValue(new Date().getTime());
//		} catch (Exception e) {
//			log.error("最后更新时间出现异常！");;
//		}
		
		return t_recommend_cinema_ticket.update(conn, " id = "+id);
	}
	
	public Map<String,String> queryRecommendCinemaTicketMin(Connection conn) throws SQLException, DataException{
		Dao.Tables.t_recommend_cinema_ticket t_recommend_cinema_ticket = new Dao().new Tables().new t_recommend_cinema_ticket();
		StringBuffer condition = new StringBuffer();
		condition.append(" 1=1 ");
		condition.append(" and user_id is null and cinema_ticket is not null ");
		DataSet ds = t_recommend_cinema_ticket.open(conn, " MIN(id) as r_id,t_recommend_cinema_ticket.* ", condition.toString(), "", -1, -1);
		
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public Map<String,String> queryRecommendUserCountInfoByUserId(Connection conn,long userId) throws SQLException, DataException{
		Dao.Tables.t_recommend_user_count t_recommend_user_count = new Dao().new Tables().new t_recommend_user_count();
		if(userId>0){
			StringBuffer condition = new StringBuffer();
			condition.append(" 1=1 ");
			condition.append(" and recommend_user_id = "+userId);
			DataSet ds = t_recommend_user_count.open(conn, "*", condition.toString(), "", -1, -1);
			
			return BeanMapUtils.dataSetToMap(ds);
		}
		return null;
	}
	
}
