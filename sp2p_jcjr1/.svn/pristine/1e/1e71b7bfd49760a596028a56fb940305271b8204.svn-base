package com.sp2p.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class RecommendBrokerageListDao extends RecommendBrokerageBase{

	public Long addBrokerage(Connection conn,double amount,int type,long recommned_user_id,long user_id,int status,String bak) throws Exception{
		Dao.Tables.t_recommend_brokerage_list t_recommend_brokerage_list = new Dao().new Tables().new t_recommend_brokerage_list();
		t_recommend_brokerage_list.amount.setValue(amount);
		t_recommend_brokerage_list.type.setValue(type);
		t_recommend_brokerage_list.recommend_user_id.setValue(recommned_user_id);
		t_recommend_brokerage_list.user_id.setValue(user_id);
		t_recommend_brokerage_list.stauts.setValue(status);
		t_recommend_brokerage_list.bak.setValue(bak);
		t_recommend_brokerage_list.create_time.setValue(new Date());
		
		return t_recommend_brokerage_list.insert(conn);
	}
	
	
	
	public List<Map<String, Object>> queryBrokerage(Connection conn,String type,long recommnedUserId,String code) throws Exception{
		Dao.Tables.t_recommend_brokerage_list t_recommend_brokerage_list = new Dao().new Tables().new t_recommend_brokerage_list();
		StringBuffer condition = new StringBuffer();
		condition.append("1 = 1");
		if(StringUtils.isNotBlank(type)){
			condition.append(" and type="+type);
		}
		
		if(recommnedUserId>0){
			condition.append(" and recommend_user_id="+recommnedUserId);
		}
		
		if(StringUtils.isNotBlank(code)){
			condition.append(" and code = "+code);
		}
		
		DataSet ds = t_recommend_brokerage_list.open(conn, "*", condition.toString(),"",-1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	public Map<String, String> queryBrokerage(Connection conn,String type,long recommnedUserId,long userId,String code) throws Exception{
		Dao.Tables.t_recommend_brokerage_list t_recommend_brokerage_list = new Dao().new Tables().new t_recommend_brokerage_list();
		StringBuffer condition = new StringBuffer();
		condition.append("1 = 1");
		if(StringUtils.isNotBlank(type)){
			condition.append(" and type="+type);
		}
		
		if(recommnedUserId>0){
			condition.append(" and recommend_user_id="+recommnedUserId);
		}
		
		if(userId>0){
			condition.append(" and user_id="+userId);
		}
		
		if(StringUtils.isNotBlank(code)){
			condition.append(" and code = "+code);
		}
		
		DataSet ds = t_recommend_brokerage_list.open(conn, "*", condition.toString(),"",-1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public List<Map<String,Object>> queryRecommendUserInvstTotalByConditions(Connection conn,Map<String,String> mapConds) throws Exception, DataException{
		Dao.Views.v_recommend_user_invst_total v_recommend_user_invst_total = new Dao().new Views().new v_recommend_user_invst_total();
		StringBuffer condition = new StringBuffer();
		condition.append("1 = 1");
		if(mapConds != null && !mapConds.isEmpty()){
			for(Entry<String, String> item:mapConds.entrySet()){
				condition.append(" AND "+item.getKey()+"="+item.getValue());
			}
		}
		DataSet ds = v_recommend_user_invst_total.open(conn, "*", condition.toString(), "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	public Map<String,String> queryRecommendSummary(Connection conn,long recommendUserId) throws Exception, DataException{
		Dao.Views.v_recommend_summary v_recommend_summary = new Dao().new Views().new v_recommend_summary();
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(String.valueOf(recommendUserId))){
			condition.append("recommendUserId="+recommendUserId);
		}
		DataSet ds = v_recommend_summary.open(conn, "*", condition.toString(), "", -1, -1);
		
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public List<Map<String,Object>> queryAllRecommendSummary(Connection conn,String userName,String realName,String recommendUserId) throws Exception, DataException{
		Dao.Views.v_recommend_summary v_recommend_summary = new Dao().new Views().new v_recommend_summary();
		StringBuffer condition = new StringBuffer();
		condition.append("1 = 1");
		if(StringUtils.isNotBlank(userName)){
			condition.append(" and username='"+userName+"'");
		}
		
		if(StringUtils.isNotBlank(realName)){
			condition.append(" and realName='"+realName+"'");
		}
		if(StringUtils.isNotBlank(recommendUserId)){
			condition.append(" and recommendUserId="+recommendUserId+"");
		}
		
		
		DataSet ds = v_recommend_summary.open(conn, "*", condition.toString(), "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> queryRecommendTicketList(Connection conn) throws Exception, DataException{
		Dao.Views.v_recommend_cinema_ticket_list v_recommend_cinema_ticket_list = new Dao().new Views().new v_recommend_cinema_ticket_list();
		StringBuffer condition = new StringBuffer();
		DataSet ds = v_recommend_cinema_ticket_list.open(conn, "*", condition.toString(), "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	public Map<String,String> querySmsRecommendSummary(Connection conn) throws Exception, DataException{
		Dao.Views.v_recommend_cinema_ticket_summary v_recommend_cinema_ticket_summary = new Dao().new Views().new v_recommend_cinema_ticket_summary();
		StringBuffer condition = new StringBuffer();
		condition.append("1 = 1");
		DataSet ds = v_recommend_cinema_ticket_summary.open(conn, "*", condition.toString(), "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	
	public Map<String,String> queryRecommendBase(Connection conn,long userId) throws Exception, DataException{
		Dao.Views.v_recommend_base v_recommend_base = new Dao().new Views().new v_recommend_base();
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(String.valueOf(userId))){
			condition.append("id="+userId);
		}
		DataSet ds = v_recommend_base.open(conn, "*", condition.toString(), "", -1, -1);
		
		return BeanMapUtils.dataSetToMap(ds);
	}
	
}
