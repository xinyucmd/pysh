package com.sp2p.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataSet;
import com.sp2p.database.Dao.Procedures;

public class RecommendBrokerageBase {
	public static Log log = LogFactory.getLog(RecommendBrokerageBase.class);
	public Map<String,Object> updateRecommendAccount(Connection conn ,long user_id,long supper_user_id,double sumAmount,int type) throws Exception{
		
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			
			Procedures.p_recommend_brokerage(conn, ds, outParameterValues, user_id, supper_user_id, sumAmount, type,-1, "");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", Convert.strToLong(outParameterValues.get(0) + "", -1));
		result.put("desc",outParameterValues.get(1) + "");
		return result;
	}
	
	public Map<String,Object> updateRecommendForInvest(Connection conn ,long repayment_id,double annual_rate,String bak) throws Exception{
		
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_recommend_brokerage_invst(conn, ds, outParameterValues, repayment_id, annual_rate,bak,-1, "");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", Convert.strToLong(outParameterValues.get(0) + "", -1));
		result.put("desc",outParameterValues.get(1) + "");
		return result;
	}
}
