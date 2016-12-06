package com.sp2p.action.front;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.RecommendBrokerageListService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.UserService;
import com.sp2p.util.CacheRecommendUtils;
import com.sp2p.util.QCode;

public class RecommendAction extends BaseFrontAction{
	private static final long serialVersionUID = 1L;
	private RecommendBrokerageListService recommendBrokerageListService;
	private RecommendUserService recommendUserService;
	private UserService userService;
	public static Log log = LogFactory.getLog(RecommendAction.class);
	
	public String toActivityPage(){
		StringBuffer reconmendUrl = new StringBuffer();
//		reconmendUrl.append(IConstants.WEB_URL);
		reconmendUrl.append("http://www.jcbanking.com/");
		reconmendUrl.append("toActivityPage.do");
		request().setAttribute("reconmmend_url", reconmendUrl.toString());
		return SUCCESS;
	}
	
	public String toMobile(){
		return SUCCESS;
	}
	
	public String  toActivityReg(){
		return SUCCESS;
	}
	
	public String toActivityPageLogin(){
		return SUCCESS;
	}
	
	public String toReconmmendInfo(){
		long userId = this.getUserId();
		request().setAttribute("userId", userId);
		request().setAttribute("userName", getUser().getUserName());
		return SUCCESS;
	}
	
	public String getSessionLogin(){
		User user = (User) session().getAttribute("user");
		JSONObject jo = new JSONObject();
		if(user != null){
			jo.put("status", "1");
			jo.put("userId",user.getId());
			jo.put("userName", user.getUserName());
		}else{
			jo.put("status", "0");
			String retUrl = request().getHeader("Referer");
			session().setAttribute("forceAfterLoginUrl", retUrl);
		}
		
		printJson(jo.toString());
		return null;
	}
	
	public String queryReconmmendInfoForAdmin(){
		JSONObject jo = new JSONObject();
		String recommend_id = request().getParameter("recommend_id");
		
		if(StringUtils.isNotBlank(recommend_id) && !recommend_id.equals("")){
			// 查询推广活动期间注册用户和投资金额
			recommendSingleDetail(jo,Long.parseLong(recommend_id),-1);
		}else{
			jo.put("msg", "没有数据");
		}
		printJson(jo.toString());
		return null;
	}
	
	public String queryReconmmendInfo(){
		JSONObject jo = new JSONObject();
		String type = request().getParameter("type");
		
		// 1、生成邀请链接
		StringBuffer reconmendUrl = new StringBuffer();
//		reconmendUrl.append(IConstants.WEB_URL);
		reconmendUrl.append("http://m.jcbanking.com/");
//		reconmendUrl.append("http://192.168.2.150/");
		reconmendUrl.append("cellPhoneinit.do");
		reconmendUrl.append("?src=net");
		reconmendUrl.append("&refferee="+getUser().getUserName());
		reconmendUrl.append("&activity="+IConstants.PERSON_RECONMMEND);
		jo.put("reconmmend_url", reconmendUrl.toString());
		
		// 1、生成二维码链接
		StringBuffer reconmendUrlCode = new StringBuffer();
//		reconmendUrlCode.append(IConstants.WEB_URL);
		reconmendUrlCode.append("http://m.jcbanking.com/");
//		reconmendUrlCode.append("http://192.168.2.150/");
		reconmendUrlCode.append("page/m/home.html");
		reconmendUrlCode.append("?ref="+com.shove.security.Encrypt.encrypt3DES(getUser().getUserName(),IConstants.PASS_KEY));
		if(StringUtils.isNotBlank(getUser().getRealName())){
			reconmendUrlCode.append("&rn="+com.shove.security.Encrypt.encrypt3DES(getUser().getRealName(),IConstants.PASS_KEY));
		}
		jo.put("reconmmend_url_code", reconmendUrlCode.toString());
		log.info(reconmendUrlCode.toString());
		
		//2 、查询推广活动期间注册用户和投资金额
		if(StringUtils.isNotBlank(type) && type.length()>0){
			recommendSingleDetail(jo,getUserId(),Integer.parseInt(type));
		}else{
			recommendSingleDetail(jo,getUserId(),-1);
		}
		
		printJson(jo.toString());
		return null;
	}
	
	public String getDecryptUserInfo(){
		JSONObject jo = new JSONObject();
		String refferee = request().getParameter("refferee");
		String realName = request().getParameter("realName");
		
		if(StringUtils.isNotBlank(refferee)){
			jo.put("refferee", com.shove.security.Encrypt.decrypt3DES(refferee, IConstants.PASS_KEY));
		}
		if(StringUtils.isNotBlank(realName)){
			jo.put("realName", com.shove.security.Encrypt.decrypt3DES(realName, IConstants.PASS_KEY));
		}
		
		printJson(jo.toString());
		return null;
	}
	
	public String checkFriendsHasChance(){
		JSONObject jo = new JSONObject();
		String userName = request().getParameter("refferee");
		if(StringUtils.isNotBlank(userName)){
			Map<String, String> map;
			try {
				map = userService.queryUserByName(userName);
				Map<String,String> uCountMap = recommendUserService.queryRecommendUserCountInfoByUserId(Convert.strToLong(map.get("id"), 0));
				if(uCountMap != null){
					jo.put("ret", -1);
					jo.put("availableCount", uCountMap.get("available_count"));
				}else{
					jo.put("ret", -2);
					jo.put("availableCount", 0);
				}
			} catch (Exception e) {
				jo.put("ret", -1);
				jo.put("msg", "获取用户剩余推荐机会错误！");
				e.printStackTrace();
			}
		}
		
		printJson(jo.toString());
		
		return null;
	}
	
	public String getQcode(){
		String content = request().getParameter("content");
		QCode handler = new QCode();
		try {
			if(StringUtils.isNotBlank(content)){
				content = content.replaceAll("@@", "&");
			}
			log.info("二维码生成路径："+content);
			handler.encoderQRCode(content, response().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String toRecommendSummary(){
		return SUCCESS;
	}
	
	public String queryAllRecommendSummary(){
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(Integer.parseInt(pageNum)); 
		pageBean.setPageSize(Integer.parseInt(pageSize));
		
		JSONObject jo = new JSONObject();
		String userId = request().getParameter("userId");
		if(StringUtils.isNotBlank(userId)){
			recommendSingleDetail(jo,Long.parseLong(userId),-1);
		}else{
			// 查询推广活动期间注册用户和投资金额
			try {
				String userName = request().getParameter("username");
				String realName = request().getParameter("realName");
				String beUserName = request().getParameter("be_username");
				String recommendUserId = "";
				if(StringUtils.isNotBlank(beUserName)){
					Map<String,String> map= recommendUserService.getRecommendUserByUserName(beUserName);
					
					if(map != null && !map.isEmpty()){
						recommendUserId = map.get("recommendUserId");
					}else{
						recommendUserId = "0";// 查无数据
					}
				}
				//List<Map<String,Object>> recommendSummaryList = recommendBrokerageListService.queryAllRecommendSummary(userName,realName,recommendUserId);
				recommendBrokerageListService.queryAllRecommendSummaryPage(pageBean,userName,realName,recommendUserId);
				List<Map<String,Object>> recommendSummaryList = pageBean.getPage();
				jo.put("totalNum", pageBean.getTotalNum());
				jo.put("recommendSummaryList", recommendSummaryList);
			} catch (DataException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			printJson(jo.toString());
		}
		
		return null;
	}
	
	public String querySmsRecommendSummary(){
		JSONObject jo = new JSONObject();
		try {
			Map<String,String>  recommendSmsSummaryList = recommendBrokerageListService.querySmsRecommendSummary();
			jo.put("recommendSmsSummaryList", recommendSmsSummaryList);
			
			List<Map<String,Object>> ticketList = recommendBrokerageListService.queryRecommendTicketList();
			
			if(ticketList != null && ticketList.size()>0){
				jo.put("ticketListCount", ticketList.size());
				jo.put("ticketList", ticketList);
			}
			
			printJson(jo.toString());
		} catch (DataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String sendSmsTicket(){
		JSONObject jo = new JSONObject();
		try {
			List<Map<String,Object>> ticketList = recommendBrokerageListService.queryRecommendTicketList();
			if(ticketList != null && ticketList.size()>0){
				for(Map<String,Object> item :ticketList){
					Thread.sleep(100);
					long ret= recommendBrokerageListService.senedSms(Convert.strToLong(String.valueOf(item.get("userId")),0));
					if(ret<0){
						log.info("已经发送过电影票！");
						continue;
					}
				}
			}
			jo.put("res", "1");
			printJson(jo.toString());
		}catch (Exception e) {
			e.printStackTrace();
			jo.put("res", "-1");
			printJson(jo.toString());
		}
		return null;
	}
	
	/**
	 * 查询推广活动期间单个推荐人，注册用户和投资金额
	 * @param jo
	 * @param userId
	 */
	private void recommendSingleDetail(JSONObject jo,Long userId,Integer type){
		try {
			List<Map<String,Object>> result = recommendBrokerageListService.queryRecommendUserInvstTotalByConditions(userId,type);
			jo.put("result", result);
			
			Map<String,String> recommendBase = recommendBrokerageListService.queryRecommendBase(userId);
			jo.put("recommendBase", recommendBase);
			
			Map<String,String> recommendSummary = recommendBrokerageListService.queryRecommendSummary(userId);
			jo.put("recommnedSummary", recommendSummary);
			
			try {
				if(CacheRecommendUtils.getInstance().getOrderList() == null || CacheRecommendUtils.getInstance().getOrderList().size()<=0){
					List<Map<String,Object>> list = recommendUserService.queryMyTop();
					CacheRecommendUtils.getInstance().orderMap(list);
				}
				
				if(CacheRecommendUtils.getInstance().orderMap(null)!=null && CacheRecommendUtils.getInstance().orderMap(null).size()>0){
					Map<String,Object> map = CacheRecommendUtils.getInstance().getOrderMap(String.valueOf(userId));
					jo.put("recommendYearInfo", map);
				}
			} catch (Exception e) {
				e.printStackTrace();
				jo.put("error", e.getMessage());
			}
		} catch (DataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String toRecommendYearSummary(){
		return SUCCESS;
	}
	
	public String getMyInvestTop(){
		User user = (User) session().getAttribute("user");
		Map<String,Object> map = null;
		JSONObject jo = new JSONObject();
		try {
			if(CacheRecommendUtils.getInstance().getOrderList() == null || CacheRecommendUtils.getInstance().getOrderList().size()<=0){
				List<Map<String,Object>> list = recommendUserService.queryMyTop();
				CacheRecommendUtils.getInstance().orderMap(list);
			}
			
			if(CacheRecommendUtils.getInstance().orderMap(null)!=null && CacheRecommendUtils.getInstance().orderMap(null).size()>0){
				map = CacheRecommendUtils.getInstance().getOrderMap(String.valueOf(user.getId()));
				jo.put("data", map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("error", e.getMessage());
		}
		
		printJson(jo.toString());
		return null;
	}
	
	
	public String queryAllRecommendYearSummary(){
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(Integer.parseInt(pageNum)); 
		pageBean.setPageSize(Integer.parseInt(pageSize));
		
		JSONObject jo = new JSONObject();
		try {
			String userName = request().getParameter("username");
			String realName = request().getParameter("realName");
			recommendUserService.queryAllRecommendYearSummaryPage(pageBean,userName,realName);
			List<Map<String,Object>> recommendYearSummaryList = pageBean.getPage();
			
			// 排名
			for(int i=0;i<recommendYearSummaryList.size();i++){
				Map<String,Object> temMap = topOne(String.valueOf(recommendYearSummaryList.get(i).get("userId")));
				recommendYearSummaryList.set(i,temMap);
			}
			
			jo.put("totalNum", pageBean.getTotalNum());
			jo.put("recommendYearSummaryList", recommendYearSummaryList);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		
		return null;
	}
	
	public Map<String,Object> topOne(String userId) throws Exception{
		Map<String,Object> map = null;
		
		if(CacheRecommendUtils.getInstance().getOrderList() == null || CacheRecommendUtils.getInstance().getOrderList().size()<=0){
			List<Map<String,Object>> list = recommendUserService.queryMyTop();
			CacheRecommendUtils.getInstance().orderMap(list);
		}
		
		map = CacheRecommendUtils.getInstance().getOrderMap(userId);
		
		return map;
	}
	
	public String clearCache(){
		CacheRecommendUtils.getInstance().clearCache();
		try {
			getOut().print("<script type='text/javascript'>alert('清除缓存成功！');</script>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String queryRecommendUserInfo(){
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		pageBean.setPageNum(Integer.parseInt(pageNum)); 
		pageBean.setPageSize(Integer.parseInt(pageSize));
		
		JSONObject jo = new JSONObject();
		try {
			long userId = getUser().getId();
			
			Map<String,String> userMap = userService.queryUserById(userId);
			Map<String,String> recommendUserSummary = recommendUserService.queryRecommendUserSummary(userId);
			
			long uinfoCount = recommendUserService.queryRecommendUserInfoCount(userId);
			List<Map<String, Object>> recommendUserInfo = recommendUserService.queryRecommendUserInfo(userId,pageBean.getStartOfPage(),pageBean.getPageSize());
			
			pageBean.setTotalNum(uinfoCount);
			jo.put("uinfoCount", uinfoCount);
			jo.put("totalPageNum", pageBean.getTotalPageNum());
			jo.put("recommendUserSummary", recommendUserSummary);
			jo.put("recommendUserInfo", recommendUserInfo);
			jo.put("userMap", userMap);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		
		return null;
	}
	
	
	
	public RecommendBrokerageListService getRecommendBrokerageListService() {
		return recommendBrokerageListService;
	}
	public void setRecommendBrokerageListService(
			RecommendBrokerageListService recommendBrokerageListService) {
		this.recommendBrokerageListService = recommendBrokerageListService;
	}
	
	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
