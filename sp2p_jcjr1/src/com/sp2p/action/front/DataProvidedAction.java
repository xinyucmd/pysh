package com.sp2p.action.front;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.util.HttpClient;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.FinanceService;
import com.sp2p.service.PartenersService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.util.DateUtil;
import com.sp2p.util.RSAUtil;
import com.sp2p.util.isKeywords;


public class DataProvidedAction extends BaseFrontAction{
	
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(DataProvidedAction.class);
	
	private PartenersService partenersService;
	private FinanceService financeService;
	private List<Map<String, Object>> list;
	private BorrowManageService borrowManageService;
	
	
	/**
	 * 融360查看标的信息
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String getborrowDataListTo360() throws Exception{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String t = request().getParameter("t");
			String token = request().getParameter("token");
			//1：查看标的列表     2：查看单个项目信息
			int flag = Convert.strToInt(request().getParameter("flag"),0);
			
			String reToken = com.shove.security.Encrypt.MD5(com.shove.security.Encrypt.MD5(t)+"bc4564d2ee267cfcf8d7252cba0e34ac");
			if(StringUtils.isNotBlank(token)){
				if(reToken.equals(token)){
					if(1 == flag){
						Map<String, Object> dataMap = new HashMap<String, Object>();
						pageBean.setPageNum(Convert.strToInt(request().getParameter("page"), 1)); 
						pageBean.setPageSize(Convert.strToInt(request().getParameter("count"), 20));
						partenersService.getborrowDataListTo360(pageBean);
						list = pageBean.getPage();
						dataMap.put("list", list);
						dataMap.put("total_number", list.size());
						jsonMap.put("data", dataMap);
						partenersService.addParenersMessage(6, "融360查看标的列表信息成功", "", -1L, 7, 0, 0, "", new Date());
					}else if(2 == flag){
						long product_id = Convert.strToLong(request().getParameter("product_id"),-1L);
						List<Map<String, Object>> list = partenersService.getborrowDataTo360(product_id);
						if(!list.isEmpty()){
							jsonMap.put("data", list.get(0));
							partenersService.addParenersMessage(6, "融360查看单个项目信息成功", "", -1L, 7, 0, 0, "", new Date());
						}else {
							jsonMap.put("status", 100);
							jsonMap.put("msg", "项目id错误，没有此项目信息");
							jsonMap.put("data", null);
							JSONUtils.printObject(jsonMap);
							partenersService.addParenersMessage(4, "项目id错误，没有此项目信息", "", -1L, 7, 0, 0, "", new Date());
							return null;
						}
					}else {
						jsonMap.put("status", 100);
						jsonMap.put("msg", "广告商参数flag有误，1：查看项目列表     2：查看单个项目信息");
						jsonMap.put("data", null);
						JSONUtils.printObject(jsonMap);
						partenersService.addParenersMessage(4, "广告商参数flag有误，1：查看项目列表     2：查看单个项目信息", "", -1L, 7, 0, 0, "", new Date());
						return null;
					}
					jsonMap.put("version", 2);
					jsonMap.put("status", 0);
					jsonMap.put("msg", "");
					JSONUtils.printObject(jsonMap);
				}else{
					jsonMap.put("status", 100);
					jsonMap.put("msg", "广告商参数token有误");
					jsonMap.put("data", null);
					JSONUtils.printObject(jsonMap);
					partenersService.addParenersMessage(4, "广告商参数token有误", "", -1L, 7, 0, 0, "", new Date());
					return null;
				}
			}else{
				jsonMap.put("status", 100);
				jsonMap.put("msg", "广告商参数token为空");
				jsonMap.put("data", null);
				JSONUtils.printObject(jsonMap);
				partenersService.addParenersMessage(4, "广告商参数token为空", "", -1L, 7, 0, 0, "", new Date());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			getOut().print("<script>alert('广告商调用异常! ');window.location.href='index.jsp';</script>");
		}
		return null;
	}
	
	/**
	 * 查询希财平台用户统计
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryUserXc() throws Exception{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			//获取接口参数 73eee07152ae74727f1d85d4b22c0be4
			String t = request().getParameter("t");
			String token = request().getParameter("token");
			String startdate = request().getParameter("startdate");
			String enddate = request().getParameter("enddate");
			pageBean.setPageNum(Convert.strToInt(request().getParameter("page"), 1)); 
			pageBean.setPageSize(Convert.strToInt(request().getParameter("pagesize"), 10));
			
			String client_secret = "8fe63b1471ae4f1fb7080e2ac131a7fb";
			String reToken = com.shove.security.Encrypt.MD5(com.shove.security.Encrypt.MD5(t)+client_secret);
			if(StringUtils.isNotBlank(token)){
				if(reToken.equals(token)){
						partenersService.queryUserXc(pageBean, startdate, enddate);
						list = pageBean.getPage();
						jsonMap.put("list", list);
						JSONUtils.printObject(jsonMap);
						partenersService.addParenersMessage(6, "查询希财平台用户统计信息成功", "", -1L, 5, 0, 0, "", new Date());
				}else{
					partenersService.addParenersMessage(4, "广告商参数token有误", "", -1L, 5, 0, 0, "", new Date());
					getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
					return null;
				}
			}else{
				partenersService.addParenersMessage(4, "广告商参数token为空", "", -1L, 5, 0, 0, "", new Date());
				getOut().print("<script>alert('未获取广告商参数! ');window.location.href='index.jsp';</script>");
				return null;
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			getOut().print("<script>alert('广告商调用异常! ');window.location.href='index.jsp';</script>");
		}
		return null;
	}
	
	/**
	 * 查询希财平台投资统计
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryInvestXc() throws Exception{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			//获取接口参数 73eee07152ae74727f1d85d4b22c0be4
			String t = request().getParameter("t");
			String token = request().getParameter("token");
			String startdate = request().getParameter("startdate");
			String enddate = request().getParameter("enddate");
			pageBean.setPageNum(Convert.strToInt(request().getParameter("page"), 1)); 
			pageBean.setPageSize(Convert.strToInt(request().getParameter("pagesize"), 10));
			
			String client_secret = "8fe63b1471ae4f1fb7080e2ac131a7fb";
			String reToken = com.shove.security.Encrypt.MD5(com.shove.security.Encrypt.MD5(t)+client_secret);
			if(StringUtils.isNotBlank(token)){
				if(reToken.equals(token)){
						partenersService.queryInvestXc(pageBean, startdate, enddate);
						list = pageBean.getPage();
						jsonMap.put("list", list);
						JSONUtils.printObject(jsonMap);
						partenersService.addParenersMessage(6, "查询希财平台投资统计信息成功", "", -1L, 5, 0, 0, "", new Date());
				}else{
					partenersService.addParenersMessage(4, "广告商参数token有误", "", -1L, 5, 0, 0, "", new Date());
					getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
					return null;
				}
			}else{
				partenersService.addParenersMessage(4, "广告商参数token为空", "", -1L, 5, 0, 0, "", new Date());
				getOut().print("<script>alert('未获取广告商参数! ');window.location.href='index.jsp';</script>");
				return null;
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			getOut().print("<script>alert('广告商调用异常! ');window.location.href='index.jsp';</script>");
		}
		return null;
	}
	
	/**
	 * 广告商用户投资统计
	 * @return
	 * @throws Exception
	 */
	public String queryPartenersInvestSystatic() throws Exception {
		long id = Convert.strToLong(request().getParameter("id"), 0);//广告商id
		String time = request().getParameter("invest_time");//投资时间
		partenersService.queryPartenersInvestSystatic(pageBean,id, time);
		return SUCCESS;
	}
	
	/**
	 * 广告商用户注册统计
	 * @return
	 * @throws Exception
	 */
	public String queryPartenersRegSystatic() throws Exception {
		long id = Convert.strToLong(request().getParameter("id"), 0);//广告商id
		String time = request().getParameter("reg_time");//注册时间
		partenersService.queryPartenersRegSystatic(pageBean,id, time);
		return SUCCESS;
	}
	
	
	
	/**
	 * 广告商到达方法
	 * @author whb
	 * @return
	 * @throws Exception 
	 */
	public String dataProvided() throws Exception{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String username = request().getParameter("username");
			String password = request().getParameter("password");
			//类型 success：成交标的列表；new：新增标的列表
			String type = request().getParameter("type");
			String dateString = request().getParameter("date");
			
			if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
				//3为米袋360
				Map<String,String> parteners = partenersService.queryParteners(3);
				String key = com.shove.security.Encrypt.MD5(password + IConstants.PASS_KEY);
				//验证用户名密码是否正确
				if(username.equals(String.valueOf(parteners.get("alias"))) && key.equals(String.valueOf(parteners.get("key")))){
					Date date = DateUtil.strToDateYYMMDD(dateString);
					String dateNext = DateUtil.getParamDateNext(date);
					list = financeService.queryBorrowList(type, DateUtil.dateToStringYYMMDD(date), dateNext);
					jsonMap.put("date", dateString);
					jsonMap.put("loans", list);
					jsonMap.put("total", list.size());
					JSONUtils.printObject(jsonMap);
					partenersService.addParenersMessage(6, "米袋360统计信息成功", "", -1L, 3, 0, 0, "", new Date());
				}else{
					partenersService.addParenersMessage(4, "广告商参数验证有误", "", -1L, 3, 0, 0, "", new Date());
					getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
					return null;
				}
			}else{
				partenersService.addParenersMessage(4, "广告商参数验证为空", "", -1L, 3, 0, 0, "", new Date());
				getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			getOut().print("<script>alert('广告商调用异常! ');window.location.href='index.jsp';</script>");
		}
		return null;
	}
	
	/**
	 * 黄金投资网查看统计数据
	 * @author whb
	 * @return
	 * @throws Exception 
	 */
	public String dataProvidedToJintou() throws Exception{
		String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		
		long pid = Convert.strToLong(request.getString("pid"), -1L);
		String starTime = request.getString("starTime");
		String endTime = request.getString("endTime");
		request().setAttribute("starTime", starTime);
		request().setAttribute("endTime", endTime);
		partenersService.dataProvidedToJintou(pid , starTime, endTime, pageBean);
		
		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		partenersService.addParenersMessage(6, "黄金投资网查看统计信息成功", "", -1L, 4, 0, 0, "", new Date());
		return "success";
	}
	
	/**
	 * crLinshi
	 * @author whb
	 * @return
	 * @throws Exception 
	 */
	public void crLinshi() throws Exception{
		List<Map<String, Object>> list = partenersService.crLinshi();
		if(!list.isEmpty()){
			String realName = "";
			String id = "";
			for(Map<String, Object> map:list){
				realName = String.valueOf(map.get("realName")).toLowerCase();
				id = String.valueOf(map.get("id"));
				partenersService.updateLinshi(Integer.parseInt(id), isKeywords.decodeUnicode(realName.replace("u", "\\u")));
			}
		}
	}
	
	/**
	 * 寻金会查看标的数据
	 * @author whb
	 * @return
	 * @throws Exception 
	 */
	public String dataProvidedToXunjinhui(){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String pid = request().getParameter("jc_pid");
			String key = request().getParameter("jc_key");
			//类型 success：成交标的列表；new：新增标的列表
			String type = request().getParameter("type");
			String dateString = Convert.strToStr(request().getParameter("date"), "");
			
			if(StringUtils.isNotBlank(pid) && StringUtils.isNotBlank(key)){
				String newKey = com.shove.security.Encrypt.MD5(pid + IConstants.PASS_KEY);
				if(StringUtils.isNotBlank(key) && key.equals(newKey)){
					String time = "";
					if(StringUtils.isNotBlank(dateString)){
						Date date = DateUtil.strToDateYYMMDD(dateString);
						time = DateUtil.dateToStringYYMMDD(date);
					}
					list = partenersService.getborrowData(Integer.parseInt(type), time);
					if(!list.isEmpty()){
						String borrowId = "";//如果有流转标 projectId就是 项目主键_日期
						for(Map<String,Object> map:list){
							map.put("subscribes", partenersService.getInvestList(Long.parseLong(String.valueOf(map.get("projectId")))));
							if("6".equals(String.valueOf(map.get("borrowWay")))){
								borrowId = String.valueOf(map.get("projectId")) + "_" + String.valueOf(map.get("applyTime"));
								map.put("projectId", borrowId);
							}
						}
					}
					jsonMap.put("list", list);
					JSONUtils.printObject(jsonMap);
					partenersService.addParenersMessage(6, "寻金会查看标的数据信息成功", "", -1L, 6, 0, 0, "", new Date());
				}else{
					partenersService.addParenersMessage(4, "广告商参数验证有误", "", -1L, 6, 0, 0, "", new Date());
					getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
					return null;
				}
			}else{
				partenersService.addParenersMessage(4, "广告商参数为空", "", -1L, 6, 0, 0, "", new Date());
				getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				getOut().print("<script>alert('广告商调用异常! ');window.location.href='index.jsp';</script>");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 未来网贷查看标的数据
	 * @param flag 1:进行中的标的 2:满标
	 * @param date 满标日期
	 * @return
	 * @throws Exception 
	 */
	public String dataProvidedToWeilaiwd(){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<Map<String, Object>> list;
		try {
			String pid = request().getParameter("jc_pid");
			String key = request().getParameter("jc_key");
			//类型 2：成交标的列表；1：未满标
			int flag = Convert.strToInt(request().getParameter("flag"), -1);
			
			if(StringUtils.isNotBlank(pid) && StringUtils.isNotBlank(key)){
				String newKey = com.shove.security.Encrypt.MD5(pid + IConstants.PASS_KEY);
				if(StringUtils.isNotBlank(key) && key.equals(newKey)){
					//RSA解密
					String dataUrl = request.getString("data");
					//dataUrl = URLDecoder.decode(dataUrl,"UTF-8");
					String data = RSAUtil.decodeByPrivateKey(dataUrl, IConstants.PRIVATE_KEY);
					Map<String, Object> dataMap = HttpClient.parseJSON2Map(data);
					
					list = partenersService.getborrowToWeilaiwd(flag, String.valueOf(dataMap.get("getdate")));
					if(!list.isEmpty() && 2 == flag){
						for(Map<String,Object> map:list){
							map.put("investor", partenersService.queryInvestInfoById(Long.parseLong(String.valueOf(map.get("bid")))));
						}
					}
					JSONUtils.printArray(list);
					partenersService.addParenersMessage(6, "未来网贷查看标的数据信息成功", "", -1L, 6, 0, 0, "", new Date());
				}else{
					partenersService.addParenersMessage(4, "广告商参数验证有误", "", -1L, 6, 0, 0, "", new Date());
					getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
					return null;
				}
			}else{
				partenersService.addParenersMessage(4, "广告商参数为空", "", -1L, 6, 0, 0, "", new Date());
				getOut().print("<script>alert('广告商参数有误! ');window.location.href='index.jsp';</script>");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				getOut().print("<script>alert('广告商调用异常! ');window.location.href='index.jsp';</script>");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 九盟互动-注册投资的用户
	 *  
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String querySbydate() throws Exception{
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			 
			pageBean.setPageNum(Convert.strToInt(request().getParameter("pageNum"), 1)); 
			pageBean.setPageSize(20);
			log.info("【九盟互动】****************pageNum="+pageBean.getPageNum());
			partenersService.querySbydate(pageBean);
			jsonMap.put("pageBean", pageBean);
			JSONUtils.printObject(jsonMap);
			 
		} catch (Exception e) {
			e.printStackTrace();
			getOut().print("<script>alert('广告商调用异常! ');window.location.href='index.jsp';</script>");
		}
		return null;
	}
	

	public PartenersService getPartenersService() {
		return partenersService;
	}

	public void setPartenersService(PartenersService partenersService) {
		this.partenersService = partenersService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public BorrowManageService getBorrowManageService() {
		return borrowManageService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	
}
