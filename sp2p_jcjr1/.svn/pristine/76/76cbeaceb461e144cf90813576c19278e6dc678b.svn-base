package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.RecommendBrokerageListDao;
import com.sp2p.dao.RecommendUserCountDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.admin.SMSInterfaceService;

public class RecommendBrokerageListService extends BaseService {

	private RecommendBrokerageListDao recommendBrokerageListDao;
	
	private SMSInterfaceService sMSInterfaceService;
	public SMSInterfaceService getsMSInterfaceService() {
		return sMSInterfaceService;
	}

	public void setsMSInterfaceService(SMSInterfaceService sMSInterfaceService) {
		this.sMSInterfaceService = sMSInterfaceService;
	}

	public RecommendUserCountDao getRecommendUserCountDao() {
		return recommendUserCountDao;
	}

	public void setRecommendUserCountDao(RecommendUserCountDao recommendUserCountDao) {
		this.recommendUserCountDao = recommendUserCountDao;
	}

	private RecommendUserCountDao recommendUserCountDao;

	public static Log log = LogFactory.getLog(RecommendBrokerageListService.class);
	/**
	 * 
	 * @param amount
	 * @param type
	 * @param recommned_user_id
	 * @param user_id
	 * @param bak
	 * @param super_id
	 * @param _type 返还奖励类型 1 推荐奖励 2 投资奖励
	 * @return
	 * @throws Exception
	 */
	public Long addBrokerage(double amount,int type,long recommned_user_id,long user_id,String bak,long super_id,int _type) throws Exception{
		Connection conn = MySQL.getConnection();
		Long bid = -1L;
		try {
			// 转账操作,从超级账户中转钱到推广人账户
			Map<String,Object> result = recommendBrokerageListDao.updateRecommendAccount(conn,recommned_user_id, super_id, amount,_type);
			if((Long)result.get("status")==1){				
				bid = addBrokerage(conn,amount, type, recommned_user_id, user_id,1,bak);
			}else{
				log.error("更新推广奖励失败:"+result.get("desc"));
				bid = addBrokerage(conn,amount, type, recommned_user_id, user_id, 0,String.valueOf(result.get("desc")));
			}
			conn.commit();
		} catch (Exception e) {
			log.error("更新返现错误："+e.getMessage());
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		
		return bid;
	}
	
	public Map<String,Object> addBrokerageForInvst(long repayment_id,double annual_rate,String bak) throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,Object> result = null;
		try {
			result = recommendBrokerageListDao.updateRecommendForInvest(conn,repayment_id, annual_rate,bak);
			if(result.get("status").equals("1")){
				conn.commit();
			}else{
				log.error("更新投资佣金失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public List<Map<String, Object>> queryBrokerage(String type,long recommnedUserId,String code) throws Exception{
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = recommendBrokerageListDao.queryBrokerage(conn,type, recommnedUserId,code);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public Map<String, String> queryBrokerage(String type,long recommnedUserId,long userId,String code) throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = recommendBrokerageListDao.queryBrokerage(conn,type, recommnedUserId,userId,code);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public List<Map<String,Object>> queryRecommendUserInvstTotalByConditions(Long recommendUserId,Integer type) throws Exception, DataException{
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			Map<String,String> mapConds = new HashMap<String,String>();
			if(recommendUserId != null && recommendUserId>0){
				mapConds.put("recommendUserId", String.valueOf(recommendUserId));
			}
			if(type != null && type>0){
				mapConds.put("type", String.valueOf(type));
			}
			result = recommendBrokerageListDao.queryRecommendUserInvstTotalByConditions(conn, mapConds);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public List<Map<String,Object>> queryRecommendUserCount(Connection conn,Long recommendUserId,Long userId) throws SQLException, DataException{
		DataSet dateset = MySQL.executeQuery(conn, "select * from t_recommend_brokerage_list where recommend_user_id = "+recommendUserId+
				" and user_id = "+ userId+" and type !=2 and code = 1001");
		dateset.tables.get(0).rows.genRowsMap();
		List<Map<String, Object>> awardMaplist = dateset.tables.get(0).rows.rowsMap;
		return awardMaplist;
	}
	
	public Map<String,String> queryInvestAmountAtActivity(Connection conn,Long userId) throws SQLException, DataException{
		DataSet dataSet = MySQL.executeQuery(conn, "SELECT SUM(t_invest.investAmount) as amount from t_invest WHERE investor = "+userId+" "
				+ " AND ((unix_timestamp(`t_invest`.`investTime`) >= (select unix_timestamp(`t_setting_activity`.`start_time`) from `t_setting_activity` where (`t_setting_activity`.`code` = '1001'))) "
				+ " and (unix_timestamp(`t_invest`.`investTime`) <= (select unix_timestamp(`t_setting_activity`.`end_time`) from `t_setting_activity` where (`t_setting_activity`.`code` = '1001'))))");
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	public Map<String,String> queryRecommendSummary(Long recommendUserId) throws Exception, DataException{
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = recommendBrokerageListDao.queryRecommendSummary(conn, recommendUserId);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public List<Map<String,Object>> queryAllRecommendSummary(String userName,String realName,String recommendUserId) throws Exception, DataException{
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			result = recommendBrokerageListDao.queryAllRecommendSummary(conn,userName,realName,recommendUserId);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public List<Map<String,Object>> queryRecommendTicketList() throws Exception, DataException{
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> result = null;
		try {
			result = recommendBrokerageListDao.queryRecommendTicketList(conn);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public long senedSms(long userId){
		Connection conn = null;
		long res= 0;
		try {
			conn = MySQL.getConnection();
			
			DataSet dateset = MySQL.executeQuery(conn, "SELECT user_id,cinema_ticket from t_recommend_cinema_ticket where user_id="+userId+" and cinema_ticket is not null");
			dateset.tables.get(0).rows.genRowsMap();
			List<Map<String, Object>> awardMaplist = dateset.tables.get(0).rows.rowsMap;
			if(awardMaplist!=null && awardMaplist.size()>0){
				return -1;
			}else{
				Map<String,String> cinemaTicketMap = recommendUserCountDao.queryRecommendCinemaTicketMin(conn);
				if(cinemaTicketMap!=null && cinemaTicketMap.get("cinema_ticket")!=null && StringUtils.isNotBlank(cinemaTicketMap.get("cinema_ticket"))){
					res = recommendUserCountDao.updateRecommendCinemaTicket(conn, (int)userId, Convert.strToInt(cinemaTicketMap.get("r_id"),0),1);
					
					StringBuffer content = new StringBuffer();
					content.append("您的电影票兑换码为:");
					content.append(cinemaTicketMap.get("cinema_ticket"));
					content.append("，请登陆微信贷合作伙伴“蜘蛛网”进行在线选座");
					if(IConstants.ISDEMO.equals("2")){
						res = sMSInterfaceService.sendSMSByConditions(userId, content.toString());
			            if (res>0) {
			            	log.info("发送电影票成功！");
			            }
					}else{
						log.info("测试环境："+content.toString());
					}
				}else{
//					res= recommendUserCountDao.insertRecommendCinemaTicket(conn, Integer.parseInt(String.valueOf(userId)),1);
					log.info("电影票已经发送完毕！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				
				if(res>0){
					conn.commit();
				}else{
					conn.rollback();
				}
				
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 1;
	}
	
	public Map<String,String> querySmsRecommendSummary() throws Exception, DataException{
		Connection conn = MySQL.getConnection();
		Map<String,String> result = null;
		try {
			result = recommendBrokerageListDao.querySmsRecommendSummary(conn);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	public void queryAllRecommendSummaryPage(PageBean<Map<String, Object>> pageBean,String userName,String realName,String recommendUserId) throws Exception{
		
		StringBuffer condition = new StringBuffer();
		 
		if(StringUtils.isNotBlank(userName)){
			condition.append(" and username='"+userName+"'");
		}
		
		if(StringUtils.isNotBlank(realName)){
			condition.append(" and realName='"+realName+"'");
		}
		if(StringUtils.isNotBlank(recommendUserId)){
			condition.append(" and recommendUserId="+recommendUserId+"");
		}
		
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_recommend_summary", "*", "", condition.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	
	}
	
	
	
	public Map<String,String> queryRecommendBase(Long userId) throws Exception, DataException{
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = recommendBrokerageListDao.queryRecommendBase(conn, userId);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	
	public Map<String,Object> updateRecommendUserForEndYear(Connection conn ,double investAmount,int process,int userId,String investId) throws Exception{
		
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_recommend_user_end_year(conn, ds, outParameterValues, investAmount,process,userId,investId,-1, "",-3,"","");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", Convert.strToLong(outParameterValues.get(0) + "", -1));
		result.put("desc",outParameterValues.get(1) + "");
		result.put("status_ticket",Convert.strToLong(outParameterValues.get(2) + "", -3));
		result.put("ticket",outParameterValues.get(3) + "");
		result.put("out_notice_content",outParameterValues.get(4) + "");
		return result;
	}
	
	
	private Long addBrokerage(Connection conn,double amount,int type,long recommned_user_id,long user_id,int status,String bak) throws Exception{
		
		return recommendBrokerageListDao.addBrokerage(conn, amount, type, recommned_user_id, user_id, status, bak);
	}
	
	public RecommendBrokerageListDao getRecommendBrokerageListDao() {
		return recommendBrokerageListDao;
	}

	public void setRecommendBrokerageListDao(
			RecommendBrokerageListDao recommendBrokerageListDao) {
		this.recommendBrokerageListDao = recommendBrokerageListDao;
	}

}
