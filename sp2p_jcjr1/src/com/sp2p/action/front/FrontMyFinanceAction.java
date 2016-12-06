
package com.sp2p.action.front;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.entity.Admin;
import com.sp2p.entity.User;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AuctionDebtService;
import com.sp2p.service.BonusService;
import com.sp2p.service.BorrowService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.NewsAndMediaReportService;
import com.sp2p.service.PartenersService;
import com.sp2p.service.PublicModelService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.service.admin.StatisManageService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;
import com.sp2p.util.DebtUtil;
import com.sp2p.util.HttpClientHelp;
import com.sp2p.util.isKeywords;

import net.sf.json.JSONObject;

/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的理财控制层
 */
public class FrontMyFinanceAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;

	private FinanceService financeService;
	private SelectedService selectedService;
	private Map<String, String> investDetailMap;
	private NewsAndMediaReportService newsService;
	//-add by C_J -- 标种类型  历史记录
	private  ShoveBorrowTypeService  shoveBorrowTypeService;
	private PublicModelService publicModelService;
	private AssignmentDebtService assignmentDebtService;
	private AuctionDebtService auctionDebtService;
	private PartenersService partenersService;
	private BonusService bonusService;
	private StatisManageService statisManageService;

	public StatisManageService getStatisManageService() {
		return statisManageService;
	}

	public void setStatisManageService(StatisManageService statisManageService) {
		this.statisManageService = statisManageService;
	}

	public PartenersService getPartenersService() {
		return partenersService;
	}

	public void setPartenersService(PartenersService partenersService) {
		this.partenersService = partenersService;
	}

	public AuctionDebtService getAuctionDebtService() {
		return auctionDebtService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

	public void setAssignmentDebtService(AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	private UserService userService;
	//--
	//-add by houli
	private BorrowService borrowService;
	private BorrowManageService  borrowManageService;
	//--
	private RechargeService rechargeService;
	private List<Map<String, Object>> borrowPurposeList ;
    private List<Map<String, Object>> borrowDeadlineList ;
    private List<Map<String, Object>> borrowAmountList ;
    private List<Map<String, Object>> linksList;
    private List<Map<String, Object>> meikuList;
    private List<Map<String, Object>> meikuStick;
    private List<Map<String, Object>> listsGGList;
    private List<Map<String, Object>> bannerList;
    private List<Map<String, Object>> infomationList;
    private List<Map<String, Object>> licaiList;
    
    
    
    /***
     * 员工投资详情
     * @return
     */
 public String queryEmployeeBorrowDetail(){
	   User user = (User) session().getAttribute("user");
	   if(user==null){
		   return "noLogin";
	   }
	  
    	String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(10);
    	 
		try {
			
			Map<String,String> borrowMap = financeService.queryEmployeeBorrow();
			long borrowId = Convert.strToLong(borrowMap.get("id"), 0);
			financeService.queryEmployeeBorrowInvestDatail(pageBean,borrowId);
			int pageNums = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNums);
			request().setAttribute("borrowId", borrowId);
			request().setAttribute("borrowMap", borrowMap);
			
			Map<String,String> map = financeService.queryEmployeeConfig(user.getId());
			request().setAttribute("ableAmount", Convert.strToInt(map.get("ableAmount"), 0));
			 
		} catch (Exception e) {
			e.printStackTrace();
		}  
    	 
		return "success";
		 
	 }
    
    
    
    
    public String queryTyjInvestDetail(){
    	
    	String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
    	long borrowId = Convert.strToLong(request().getParameter("borrowId"), 0);
		try {
			financeService.queryTyjBorrowInvestDatail(pageBean,borrowId);
			int pageNums = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNums);
			request().setAttribute("borrowId", borrowId);
			
			Map<String,String> borrowMap = financeService.queryBorrowNew();
			request().setAttribute("borrowMap", borrowMap);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}  
    	 
		return "success";
		 
	 }
	/**
	 * 7.6-8.6活动
	 * @author whb
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public String queryInvestRank() throws Exception {
		//投标前10名
		List<Map<String, Object>> list = statisManageService.queryInvestRank();
		JSONObject jo = new JSONObject();
		jo.put("result", list);
		printJson(jo.toString());
		return null;
	}
    	
	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: financeInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-4 上午11:16:54
	 * @Return: String
	 * @Descb: 我的理财初始化(此方法不用了)
	 * @Throws:
	 */
    @Deprecated
	public String financeInit() throws SQLException, DataException {
		String mode = request.getString("m") == null ? "1" : request.getString("m");
		request().setAttribute("m", mode);
		String curPage = request.getString("curPage");
		if (StringUtils.isNotBlank(curPage)) {
			request().setAttribute("curPage", curPage);
		}
		
		// 初始化查询条件
		String title = request.getString("title");
		String paymentMode = request.getString("paymentMode");
		String purpose = request.getString("purpose");
		String raiseTerm = request.getString("raiseTerm");
		String reward = request.getString("reward");
		String arStart = request.getString("arStart");
		String arEnd = request.getString("arEnd");
		String type = request.getString("type");
		request().setAttribute("title", title);
		request().setAttribute("paymentMode", paymentMode);
		request().setAttribute("purpose", purpose);
		request().setAttribute("raiseTerm", raiseTerm);
		request().setAttribute("reward", reward);
		request().setAttribute("arStart", arStart);
		request().setAttribute("arEnd", arEnd);
		request().setAttribute("type", type);

		// 获取页面上需要的动态下拉列表

		request().setAttribute("borrowPurposeList", borrowPurposeList);
		request().setAttribute("borrowDeadlineList", borrowDeadlineList);
		request().setAttribute("borrowAmountList", borrowAmountList);
		return "success";
	}

	/**
	 * @MethodName: financeList
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-4 下午08:44:15
	 * @Return:
	 * @Descb: 我的理财列表
	 * @Throws:
	 */
	public String financeList() throws Exception {
		// 前台显示列表类型
		String mode = request.getString("m");
		String title = request.getString("title");
		String paymentMode = request.getString("paymentMode");
		String purpose = request.getString("purpose");
		String deadline = request.getString("deadline");
		String reward = request.getString("reward");
		String arStart = request.getString("arStart");
		String arEnd = request.getString("arEnd");
		String type = request.getString("type");
		
		pageBean.setPageNum(request.getString("curPage"));
		
		pageBean.setPageSize(10);
		String borrowWay = "";
		String borrowStatus = "";
		String borrowType = "";
		String borrow_t="";
		// 截取查询的类型，防止非常规操作
		if (StringUtils.isNotBlank(type)) {
			String[] types = type.split(",");
			if (types.length > 0) {
				for (int n = 0; n < types.length; n++) {
					// 是数字类型则添加到borrowType中
					if (StringUtils.isNumericSpace(types[n])) {
						borrowType += "," + types[n];
					}
				}
				if (StringUtils.isNotBlank(borrowType)) {
					borrowType = borrowType.substring(1, borrowType.length());
				}
			} else {
				if (StringUtils.isNumericSpace(type)) {
					borrowType = type;
				}
			}
		}
		if ("1".equals(mode)||"".equals(mode)) {
			// 全部借款列表,显示1 等待资料 2 正在招标中 3 已满标
			borrowStatus = "(2,3,4,5,6)";
			// 查询条件中的借款方式
			if (StringUtils.isNotBlank(borrowType)) {
				borrowWay = "(" + borrowType + ")";
			}
		} else if ("2".equals(mode)) {
			// 实地认证的借款
			borrowWay = "";
			borrowStatus = "(2,3,4,5,6)";
			borrow_t = "1";
		} else if ("3".equals(mode)) {
			// 信用认证的借款
			borrowWay = "";
			borrowStatus = "(2,3,4,5,6)";
			borrow_t = "2";
		} else if ("4".equals(mode)) {
			// 机构担保的借款
			borrowWay = "(" + IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE + ")";
			borrowStatus = "(2,3,4,5,6)";
		} else if ("5".equals(mode)) {
			// 最近成功的借款列表，显示4还款中 5 已还完
			borrowStatus = "(4,5)";
		}else if("6".equals(mode)){
			borrowWay = "(" + IConstants.BORROW_TYPE_INSTITUTION_FLOW + ")";
			borrowStatus = "(2,3,4,5,6)";
		}else{
			borrowStatus = "(2,3,4,5,6)";
		}
		if("7".equals(type)){
			assignmentDebtService.queryAllDebt("",-1, -1, -1, "1,2", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();
			Date nowDate = new Date();
			Map<String,Object> result = null;
			String borrowId = "";
			String investId = "";
//			String annualRate = "";
			log.info("债权列表个数为:["+list.size()+"]");
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					borrowId = String.valueOf(map.get("borrowId"));
					investId = String.valueOf(map.get("investId"));
//					annualRate = String.valueOf(map.get("annualRate"));
//					nextRepayDate = String.valueOf(map.get("nextRepayDate"));
					//修改已完成的债权信息
					if("2".equals(String.valueOf(map.get("debtStatus")))){
//						map.put("remainDays", "已完成");
//						map.put("debtValue", String.valueOf(map.get("debtSum"))); 
//						map.put("debtPrice", String.valueOf(map.get("debtPrice")));
						
						try {
							result = coseDebtPrice(Long.parseLong(investId),"");
							map.putAll(result);
							map.put("remainDays", "已完成");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						// 查询列表暂时不计算债权
						result = coseDebtPrice(Long.parseLong(borrowId), Long.parseLong(investId), "", "");
						map.putAll(result);
						Date date = (Date) map.get("actionTime");
						String remainDays = DateUtil.remainDateToString(nowDate,
								date);
						map.put("remainDays", remainDays);
					}
				}
			}
		}else{
			/**标的列表*/
			financeService.queryBorrowByCondition(borrow_t,borrowStatus, borrowWay, title,
					paymentMode, purpose, deadline, reward, arStart, arEnd,
					IConstants.SORT_TYPE_DESC, pageBean);
/**即将发售*/
			List<Map<String, Object>> borrowAllTime =  financeService.queryBorrowAllTime(borrowWay);
			request().setAttribute("borrowAllTime", borrowAllTime);		
}
		this.setRequestToParamMap();
		request().setAttribute("type", type);
		
		//long result = financeService.queryActivtySetComfig();//是否显示体验金投资的标哦
		Map<String, String> borrowNewMap = new HashMap<String,String>();
		//if(result>0){
			borrowNewMap = financeService.queryBorrowNew();
		//} 
		request().setAttribute("borrowNewMap", borrowNewMap);
		 
		return SUCCESS;
	}
	
	/**
	 * 债权转让
	 * 
	 * @param：borrowId借款id;investId还款拥有者;annualRate年利率;transRatio转让系数;nextRepayDate下一还款日期
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public Map<String,Object> coseDebtPrice(long borrowId, long investId, String  transRatio, String annualRate) throws Exception{
		
		Map<String, String> debtMap = null;
		Map<String,Object> result = null;
		//获取当前借款
		Map<String, String> map = assignmentDebtService.getOnePay(borrowId);
		
		if(null != map && map.size() >0){
			if(1 == Integer.parseInt(map.get("deadline"))){ // 活力宝
				log.info("===========暂不支持活力宝转让=============");
			}else{
				debtMap = assignmentDebtService.getDebt(borrowId,investId);
			}
		}else {
			log.info("===========查询标的信息错误【BORRROW_ID:"+borrowId+"不存在】=============");
		}
		
		result = DebtUtil.debtFormula(debtMap,annualRate);
		return result;
	}

	/**
	 * @MethodName: financeLastestList
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:29:33
	 * @Return:
	 * @Descb: 最新借款列表前10条记录
	 * @Throws:
	 */
	public String financeLastestList() throws Exception {
		try {
			List<Map<String, Object>> mapList = financeService
					.queryLastestBorrow();
			request().setAttribute("mapList", mapList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * @MethodName: investRank
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午11:24:23
	 * @Return:
	 * @Descb: 投资排名前20条记录
	 * @Throws:
	 */
	public String investRank() {
		List<Map<String, Object>>  rankList = new ArrayList<Map<String,Object>>(); 
		try {
			int number =Convert.strToInt( paramMap.get("number"),1);
				//当前年
			rankList = financeService.investRank(number,8);
			request().setAttribute("rankList", rankList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: index
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:46:12
	 * @Return:
	 * @Descb: 首页加载内容
	 * @Throws:
	 */
	public String index() throws Exception {
        /** 推荐展示的借款标 */
		Map<String,String> tyjMap = financeService.queryBorrowNew();//新手体检金
		
//		List<Map<String, Object>> mapList = financeService.queryLastestBorrow();
//		List <Map<String, Object>> mapListA = new ArrayList<Map<String, Object>>();//优选宝
//		List <Map<String, Object>> mapListB = new ArrayList<Map<String, Object>>();//定息宝
//		List <Map<String, Object>> mapListC = new ArrayList<Map<String, Object>>();//活利宝
//		for(int i = 0;i<mapList.size();i++){
//			String borrowWay = mapList.get(i).get("borrowWay").toString();
//			if("3".equals(borrowWay)){
//				mapListA.add(mapList.get(i));
//				break;
//			}
//		}
//		for(int i = 0;i<mapList.size();i++){
//			String borrowWay = mapList.get(i).get("borrowWay").toString();
//			if("4".equals(borrowWay)){
//				mapListB.add(mapList.get(i));
//				break;
//			}
//		}
//		for(int i = 0;i<mapList.size();i++){
//			String borrowWay = mapList.get(i).get("borrowWay").toString();
//			if("6".equals(borrowWay)){
//				mapListC.add(mapList.get(i));
//				break;
//			}
//		}
		
		
		List <Map<String, Object>> mapListA = financeService.queryBorrowIndex(3);//优选宝
		List <Map<String, Object>> mapListB = financeService.queryBorrowIndex(4);//定息宝
		List <Map<String, Object>> mapListC = financeService.queryBorrowIndex(6);//活力宝
		
		
		pageBean.setPageSize(1);
		assignmentDebtService.queryAllDebt("",-1, -1, -1, "1,2",pageBean);//交易宝
		List<Map<String, Object>> debtList = pageBean.getPage();
		Date nowDate = new Date();
	    if (debtList != null) {
			for (Map<String, Object> map : debtList) {
				Date date = (Date) map.get("actionTime");
				String remainDays = DateUtil.remainDateToString(nowDate,date);
				map.put("remainDays", remainDays);
			}
		}
	    
	    List<Map<String, Object>> listHlb = financeService.queryLastestBorrowWay(6);//活利宝
	    List<Map<String, Object>> listDxb = financeService.queryLastestBorrowWay(4);//定息宝
	    List<Map<String, Object>> listYxb = financeService.queryLastestBorrowWay(3);//优选宝
	    if(listHlb!=null && listHlb.size()>0){
	    	mapListC = listHlb;
		}
	    if(listDxb!=null && listDxb.size()>0){
			mapListB = listDxb;
		}
		if(listYxb!=null && listYxb.size()>0){
			mapListA = listYxb;
		}
		
		request().setAttribute("tyjMap", tyjMap);
		request().setAttribute("mapListA", mapListA);
		request().setAttribute("mapListB", mapListB);	
		request().setAttribute("mapListC", mapListC);
		request().setAttribute("debtList", debtList);
		
		//公告
		List<Map<String,Object>> newsList = new ArrayList<Map<String,Object>>();
		pageBean.setPageSize(5);
		newsService.frontQueryNewsPage(pageBean);
		newsList = pageBean.getPage();
		request().setAttribute("newsList", newsList);
		pageBean.setPage(null);
//		友情链接
//		pageBean.setPageSize(100);
//		publicModelService.queryLinksPage(pageBean);
//		linksList =pageBean.getPage();
//		session().setAttribute("linksList", linksList);
//		pageBean.setPage(null);
		
		
		//媒体报道 
		pageBean.setPageSize(5);
		newsService.queryMediaReportPage(pageBean,1);
		meikuList = pageBean.getPage();
		request().setAttribute("meikuStick", meikuList);
		pageBean.setPage(null);
		
		//行业资讯
		pageBean.setPageSize(5);
		newsService.queryInfomationPage(pageBean);
		infomationList = pageBean.getPage();
		request().setAttribute("infomationList", infomationList);
		pageBean.setPage(null);
		
		
		//理财知识
		pageBean.setPageSize(5);
		newsService.queryLicaiPage(pageBean);
		licaiList = pageBean.getPage();
		request().setAttribute("licaiList", licaiList);
		pageBean.setPage(null);
		
		//图片滚动
		pageBean.setPageSize(50);
		publicModelService.queryBannerListPage(pageBean,0);
		bannerList = pageBean.getPage();
		request().setAttribute("bannerList", bannerList);
		pageBean.setPage(null);
		
		//投资风云榜
		List<Map<String, Object>> investList = financeService.queryUserInvestRecode();
		request().setAttribute("investList", investList);
		
		//得到用户对象
		User user = (User) session().getAttribute("user");
		if (user!=null) {
			paramMap = userService.queryUserById(user.getId());
		}
		
		return "success";
	}

//		友情链接
	public String links() throws Exception{
		
		if(session().getAttribute("linksList")!=null){
			linksList = (List<Map<String, Object>>)session().getAttribute("linksList");
		}else{
			pageBean.setPageSize(100);
			publicModelService.queryLinksPage(pageBean);
			linksList =pageBean.getPage();
			pageBean.setPage(null);
			session().setAttribute("linksList", linksList);
		}
		JSONUtils.printArray(linksList);
		
		return null;
	}
	
	/*
	 * @MethodName: financeToolInit
	 * 
	 * @Param: FrontMyFinanceAction
	 * 
	 * @Author: gang.lv
	 * 
	 * @Date: 2013-3-4 下午01:30:25
	 * 
	 * @Return:理财工具箱
	 * 
	 * @Descb:
	 * 
	 * @Throws:
	 */
	public String financeToolInit() {
		return "success";
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: financeDetail
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:40:38
	 * @Return:
	 * @Descb: 理财中的借款明细
	 * @Throws:
	 */
	public String financeDetail()  throws Exception {
		session().setAttribute("DEMO", IConstants.ISDEMO);//得到是不是演示版本
		User user = (User) session().getAttribute("user");
		if(user!=null){
			Long userId = user.getId();
			Map<String, String> userMap = financeService.queryUserById(userId);
			if(userMap!=null){
				String usableSum = userMap.get("usableSum");
				request().setAttribute("usableSum", usableSum);
			}
			
		}else{
			request().setAttribute("isLogin", 0);
			String url = request().getScheme()+"://"; //请求协议 http 或 https
			url+=request().getHeader("host"); // 请求服务器
			url+=request().getRequestURI(); // 工程名
			if(request().getQueryString()!=null) //判断请求参数是否为空
			url+="?"+request().getQueryString(); // 参数 
			System.out.println(url);
			session().setAttribute("returnUrl", url);
			return "noLogin";
		}
		String idStr = request.getString("id") == null ? "" : request.getString("id");
		if (!"".equals(idStr) && StringUtils.isNumericSpace(idStr)) {
			Long id = Convert.strToLong(idStr, -1);
			
			long old_borrow_id = 0;
			Map<String,String> oldBorrowIdMap = borrowService.queryBorrow(id);
			if(oldBorrowIdMap!=null && oldBorrowIdMap.size()>0){
				old_borrow_id = Convert.strToLong(String.valueOf(oldBorrowIdMap.get("old_borrow_id")), 0);
				if(old_borrow_id>0){
					Map<String, String> borrow_Detail_Map = financeService.queryBorrowDetailById(old_borrow_id);
					request().setAttribute("old_borrow_id", old_borrow_id);
					request().setAttribute("borrow_Detail_Map", borrow_Detail_Map);
				}
			}
			
			
		
			// 借款详细
			Map<String, String> borrowDetailMap = financeService.queryBorrowDetailById(id);
			String borrowWays = borrowDetailMap.get("borrowWay");
			request().setAttribute("borrowWays", borrowWays);
			 
			String tempTime = "2015-08-31 23:59:59";
			String applyTime = borrowDetailMap.get("applyTime");
			int ret = applyTime.compareTo(tempTime);
			request().setAttribute("ret", String.valueOf(ret));
			 
			if(user!=null){
			//红包
			int borrowLimite =  Convert.strToInt(borrowDetailMap.get("deadline"), 0);//借款期限
			Map<String,String> hbConfigMap = bonusService.queryBonusConfig();//获取红包配置集合
			int reg_deadline =  Convert.strToInt(hbConfigMap.get("reg_deadline"), 0);//注册红包使用期限
			int invest_deadline =  Convert.strToInt(hbConfigMap.get("invest_deadline"), 0);//投资红包使用期限
			int recom_deadline =  Convert.strToInt(hbConfigMap.get("recom_deadline"), 0);//推荐红包
			
			//获取用户红包金额
			int reg = borrowLimite-reg_deadline;
			int invest = borrowLimite-invest_deadline;
			int recomm = borrowLimite-recom_deadline;
			Map<String,String> hbMap = new HashMap<String,String>();
			if(reg>=0){
				 hbMap = financeService.queryUserHbMoney(user.getId(),1);
				 request().setAttribute("bonus_type", 1);
			}
			if(invest>=0){
				 hbMap = financeService.queryUserHbMoney(user.getId(),2);
				 request().setAttribute("bonus_type", 2);
			}
			if(recomm>=0){
				 hbMap = financeService.queryUserHbMoney(user.getId(),3);
				 request().setAttribute("bonus_type", 3);
			}
			if(reg>=0 && invest>=0){
				 hbMap = financeService.queryUserHbMoney(user.getId(),12);
				 request().setAttribute("bonus_type", 12);
			}
			if(reg>=0 && recomm>=0){
				 hbMap = financeService.queryUserHbMoney(user.getId(),13);
				 request().setAttribute("bonus_type", 13);
			}
			if(invest>=0 && recomm>=0){
				 hbMap = financeService.queryUserHbMoney(user.getId(),23);
				 request().setAttribute("bonus_type", 23);
			}
			
			if(reg>=0 && invest>=0 && recomm>=0){
				 hbMap = financeService.queryUserHbMoney(user.getId(),123);
				 request().setAttribute("bonus_type", 123);//三 代表 所有红包之和
			}
			
			if(hbMap!=null && hbMap.size()>0){
				request().setAttribute("hbMap", hbMap);
			}else{
				hbMap.put("bonus_avaliable", "0");
				request().setAttribute("hbMap", hbMap);
			}
			
			String cuurSysDate = DateUtil.dateToString(new Date());//当前系统之间
			request().setAttribute("cuurSysDate", cuurSysDate);
			request().setAttribute("hbConfigMap", hbConfigMap);//红包配置集合
			
			
			Map<String,String> map_6_24 = financeService.queryBonus_6_24_sum(user.getId());
			if(map_6_24!=null && map_6_24.size()>0){
				String bonus_able_6_24 = map_6_24.get("bonus_able");
				request().setAttribute("bonus_able_6_24", bonus_able_6_24);
			}else{
				request().setAttribute("bonus_able_6_24", "0");
			}
			
			}
			//-- 7 - 9
			//查询借款信息得到借款时插入的平台收费标准
			Map<String,String> map = borrowManageService.queryBorrowInfo(id);
			if (map == null) {
				return "404";
			}
			//得到收费标准的json代码
			String feelog = Convert.strToStr(map.get("feelog"), "");
			Map<String,Double> feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
			//--end
			String nid_log = borrowDetailMap.get("nid_log");
			Map<String,String>  TypeLogMap = null;
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"),-1);
				request().setAttribute("subscribes",stauts );
			}
			if (borrowDetailMap != null && borrowDetailMap.size() > 0) {
				double borrowSum = Convert.strToDouble(borrowDetailMap.get("borrowSum")+"", 0);
				double annualRate = Convert.strToDouble(borrowDetailMap.get("annualRate")+"", 0);
				int deadline = Convert.strToInt(borrowDetailMap.get("deadline")+"", 0);
				int paymentMode = Convert.strToInt(borrowDetailMap.get("paymentMode")+"", -1);
				int isDayThe = Convert.strToInt(borrowDetailMap.get("isDayThe")+"", 1);
				double investAmount = 10000;
				String earnAmount = "";
				if(borrowSum < investAmount){
					investAmount = borrowSum;
				}
				AmountUtil au = new AmountUtil();
				Map<String,String> earnMap = null;
				double costFee = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE)+"",0);
				
				// 如果是约标，则不显示加息
				double addInterest = Convert.strToDouble(borrowDetailMap.get("add_interest"), 0);
				int hasPwd = Convert.strToInt(borrowDetailMap.get("hasPWD"), -9999);
				if(hasPwd ==1 && addInterest>0){
					addInterest = 0;
				}
				annualRate = annualRate + addInterest;
				
				if(paymentMode == 1){
					//按月等额还款
					earnMap = au.earnCalculateMonth(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2,costFee);
					earnAmount = earnMap.get("msg")+"";
				}else if(paymentMode == 2){
					//先息后本
					earnMap = au.earnCalculateSum(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2);
					earnAmount = earnMap.get("msg")+"";
				}else if(paymentMode == 3){
					//秒还
					earnMap = au.earnSecondsSum(investAmount, borrowSum, annualRate, deadline,0, 2);
					earnAmount = earnMap.get("msg")+"";
				} else if (paymentMode == 4) {
					// 一次性还款
					earnMap = au.earnCalculateOne(investAmount, borrowSum,
							annualRate, deadline, 0, isDayThe, 2, costFee);
					earnAmount = earnMap.get("msg") + "";
				}
				//----------add by houli 借款类型判断，前台借款详细信息中需要显示
				String borrowWay = borrowDetailMap.get("borrowWay");
				/*if(borrowWay.equals(IConstants.BORROW_TYPE_NET_VALUE)){
					request().setAttribute("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
				}else if(borrowWay.equals(IConstants.BORROW_TYPE_SECONDS)){
					request().setAttribute("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
				}else if(borrowWay.equals(IConstants.BORROW_TYPE_GENERAL)){
					request().setAttribute("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
				}else if(borrowWay.equals(IConstants.BORROW_TYPE_FIELD_VISIT)){
					request().setAttribute("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				}else if(borrowWay.equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)){
					request().setAttribute("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				}else if (borrowWay.equals(IConstants.BORROW_TYPE_INSTITUTION_FLOW)){//流转标
					request().setAttribute("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}*/
				request().setAttribute("borrowWay",borrowWay);
				//催收记录
				List<Map<String, Object>> collection = financeService.queryCollectionByid(id);
				if(collection != null && collection.size()>0)
					request().setAttribute("colSize", collection.size());
				
				request().setAttribute("earnAmount", earnAmount);
				request().setAttribute("earnMap", earnMap);
				// 每次点击借款详情时新增浏览量
				financeService.addBrowseCount(id);
				request().setAttribute("borrowDetailMap", borrowDetailMap);
				
				Map<String, String> borrowUserMap=new HashMap<String, String>();
				List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
				String publisherWay = borrowDetailMap.get("publisherWay"); //发布方式
				String borrowType = borrowDetailMap.get("borrowType"); //借款类型
				request().setAttribute("borrowType", borrowType);
				if("1".equals(publisherWay)){ //前台发布
					// 借款人资料
					 borrowUserMap = financeService
							.queryUserInfoById(id);
					// 借款人认证资料
					 list= financeService
							.queryUserIdentifiedByid(id);
				}else if("2".equals(publisherWay)){//后台发布
					if("1".equals(borrowType)){ //个人借款
						// 借款人资料
						 borrowUserMap = financeService
								.queryUserInfoByIdAdmin(id);
					}else{ //企业借款
						 borrowUserMap = financeService
							.queryEnterpriseUserInfoById(id);
					}
					// 借款人认证资料
					 list = financeService
							.queryUserIdentifiedByidAdmin(id);
				}
				DesSecurityUtil ds =new DesSecurityUtil();
				if(borrowUserMap!=null){
					String userId = borrowUserMap.get("userId");
					borrowUserMap.put("enUserId", ds.encrypt(userId));
				}
				
				//加密用户ID
				if(list.size()>0){
					Map<String, Object> listMap = 	list.get(0);
					String userId = listMap.get("userId")+"";
					String enUserId = ds.encrypt(userId);
					request().setAttribute("enUserId", enUserId);
				}
				
				if(old_borrow_id>0){
					Map<String, String> borrow_User_Map = financeService.queryUserInfoById(old_borrow_id);
					request().setAttribute("borrow_User_Map", borrow_User_Map);
				}
				
				request().setAttribute("borrowUserMap", borrowUserMap);
				request().setAttribute("list", list);
				
				// 投资记录
				List<Map<String, Object>> investList = financeService
						.queryInvestByid(id);
				
				if(investList!=null && investList.size()>0){
					for(int i=0;i<investList.size();i++){
						Map<String, Object> maps = investList.get(i);
					 	String investTime = String.valueOf(maps.get("investTime"));
					 	if(investTime.compareTo(applyTime)<0){
					 		maps.put("investTime", applyTime);
					 	}
					}
				}
				
				request().setAttribute("investList", investList);
				request().setAttribute("idStr", idStr);
				Map<String,String> borrowRecordMap = financeService.queryBorrowRecord(id);
				request().setAttribute("borrowRecordMap", borrowRecordMap);
				
				if(old_borrow_id>0){
					Map<String,String> borrow_Record_Map = financeService.queryBorrowRecord(old_borrow_id);
					request().setAttribute("borrow_Record_Map", borrow_Record_Map);
				}
				
				//-----------add by houli
				String wStatus = judgeStatus(Convert.strToInt(borrowWay, -1),
						Convert.strToLong(borrowDetailMap.get("publisher"), -100));
				if(wStatus == null){
					request().setAttribute("wStatus", "");
				}else{
					request().setAttribute("wStatus", wStatus);
				}
				// 借款显示类型，如果是流转标就跳转到流转标显示页面
				String cicuration = borrowDetailMap.get("borrowShow") + "";
				if (cicuration.equals("2")) {
					return "cicuration";
				}
				//郭井超----开始----信用借款或者实地考察
				long publisher = Long.parseLong(borrowDetailMap.get("publisher"));//发标人
				if(borrowWay.equals("3") || borrowWay.equals("4")){
					 /**判断发标人是否为前台高级用户*/
				     Map<String,String> userMap =	financeService.queryAdminUserPublisher(publisher);
				     if(userMap!=null && userMap.size()>0){
				    	 String isFlag = userMap.get("isFlag");
				    	 if("1".equals(isFlag)){//是小贷公司高级用户
				    		 long adminId = Long.parseLong(userMap.get("adminId"));//合作机构后台管理员id
				    		
				    		 Map<String,String> loanMap  = financeService.queryLoanCompany(adminId);//合作机构
				    		 Map<String,String> borrowMap = financeService.queryFinanceById(Convert.strToLong(idStr, 0));//合作模式为逐笔审批下的担保函
				    		 Map<String,String> bondingMap  = financeService.queryBondingCompany(Long.parseLong(loanMap.get("id")),Convert.strToLong((borrowMap.get("bonding_id")),0));//担保机构
				    		 List<Map<String, Object>> mortgPicList =  financeService.queryLoanCompanyMortgPic(Long.parseLong(loanMap.get("id")));//查询合作机构抵押物
				    		 List<Map<String, Object>> basePicList = financeService.queryLoanCompanyBasePic(Long.parseLong(loanMap.get("id")));//查询合作机基本资料
				    		
				    		 request().setAttribute("isFlag", isFlag);
				    		 request().setAttribute("loanMap", loanMap);
				    		 request().setAttribute("bondingMap", bondingMap);
				    		 request().setAttribute("borrowMap", borrowMap);
				    		 request().setAttribute("mortgPicList", mortgPicList);
				    		 request().setAttribute("basePicList", basePicList);
				    		 if(bondingMap!=null && bondingMap.size()>0){
				    			 request().setAttribute("isShowBondingCompany", 1);
				    		 }
				    	 }
				     }
				}
				//郭井超----结束----信用借款
			} else {
				return "404";
			}
		} else {
			return "404";
		}
		return "success";
	}
	/**
	 * 点击查看详情的时候，判断某标的的状态
	 * @param tInt
	 * @return
	 * @throws Exception 
	 */
	private String  judgeStatus(int tInt,Long userId) throws Exception{
		if(1 == userId){
			return null;
		}
		
		if(tInt < 3){//秒还、净值标的
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if(aa < 0){
				return "waitBorrow";
				
			}
		}else{//其它借款
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if(aa < 0){
				return "waitBorrow";
			}else{
				Long bb = borrowService.queryBaseFiveApprove(userId);
				if(bb < 0){
					return "waitBorrow";
				}
			}
		}
		return null;
	}
	
	/**
	 * 债权转让借款详情
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String queryDebtBorrowDetail()  throws Exception{
		
		Thread.sleep(100);
		
		Long debtId = Convert.strToLong(request.getString("debtId") == null ? "" : request.getString("debtId"), -1);
		//债权信息
		Map<String, String> map = assignmentDebtService
		.getAssignmentDebt(debtId);
		//债权购买信息
//		List<Map<String, Object>> list = auctionDebtService.queryAuctionDebtByDebtId(debtId);
		if (map != null) {
			long viewCount = Convert.strToLong(map.get("viewCount"), 0);
			viewCount++;
			paramMap.putAll(map);
			long borrowId = Convert.strToLong(map.get("borrowId"), -1);
			//whb添加公式查询
			long investId = Convert.strToLong(map.get("investId"), -1);
			long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
			Map<String, String> mapth = auctionDebtService.queryBorrowerImgpath(borrowId);
			String transRatio = map.get("transRatio");
			
			paramMap.put("investId", String.valueOf(investId));
			int debtStatus = Convert.strToInt(map.get("debtStatus"), 0);
			Map<String, Object> result = null;
			//修改已完成的债权信息
			if(2==debtStatus){
				try {
					result = coseDebtPrice(investId,"");
					map.put("remainDays", "已完成");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				result = coseDebtPrice(borrowId, investId, transRatio);
			}
			String debtValue = String.valueOf(result.get("debtValue"));
			String debtPrice = String.valueOf(result.get("debtPrice"));
			String deadline = String.valueOf(result.get("deadline"));
			String nextRepayDate = String.valueOf(result.get("nextRepayDate"));
			String remainPeriod = String.valueOf(result.get("remainPeriod"));
			paramMap.put("deadline", deadline);
			
			// 计算债权 开始
			for(String key : result.keySet()){
				paramMap.put(key, String.valueOf(result.get(key)));
			}
			// 计算债权结束
			
			//下一还款日
			if(StringUtils.isBlank(nextRepayDate)){
				//下一还款日为空
				paramMap.put("nextRepayDateNull", "0");
				request().setAttribute("nextRepayDate", "");
			}else{
				//下一还款日不为空
				paramMap.put("nextRepayDateNull", "1");
				if(nextRepayDate!=null && !nextRepayDate.toLowerCase().equals("null")){
					request().setAttribute("nextRepayDate", nextRepayDate);
				}else{
					request().setAttribute("nextRepayDate", "");
				}
			}
			
			//修改已完成的债权信息
//			if(2 == debtStatus){
//				debtValue = String.valueOf(map.get("debtSum"));
//				debtPrice = String.valueOf(list.get(0).get("debtPrice"));
//			}
			if("0".equals(remainPeriod)){
				paramMap.put("nextRepayDateValue", "已还完");
				paramMap.put("deadline", mapth.get("deadline"));
			}
			paramMap.put("debtValue", debtValue);
			paramMap.put("debtPrice", debtPrice);
			paramMap.put("remainPeriod", remainPeriod);
			paramMap.put("awaitPI", String.valueOf(result.get("awaitPI")));
			paramMap.put("transRatio", transRatio);
			//转让记录
//			Map<String, String> debtMap = financeService
//					.queryDebtById(borrowId, investId);
//			request().setAttribute("debtMap", debtMap);
			
			String imgPath = mapth.get("imgPath");
			paramMap.put("imgPath", imgPath+"");
			paramMap.put("borrowerId", borrowerId+"");
			paramMap.put("viewCount", viewCount + "");
			long deptStatus = Convert.strToLong(map.get("debtStatus"),-1);
			map = new HashMap<String, String>();
			map.put("viewCount", viewCount + "");
			assignmentDebtService.updateAssignmentDebt(debtId,deptStatus, map);
			String publishTime = paramMap.get("publishTime");
			//whb去掉竞拍
			long debtLimit = Convert.strToLong(paramMap
					.get("debtLimit"), 0);
			if(StringUtils.isNotBlank(publishTime)){
				String remainDays = DateUtil.remainDateToString(new Date(),
						DateUtil.dateAddDay(DateUtil.strToDate(publishTime),
								(int) debtLimit));
				paramMap.put("remainDays", remainDays);
			}
			
			debtId = Convert.strToLong(paramMap.get("id"), -1);
			paramMap.put("debtId", paramMap.get("id"));
			paramMap.putAll(auctionDebtService
					.queryAuctionMaxPriceAndCount(debtId));
			long alienatorId = Convert.strToLong(paramMap
					.get("alienatorId"), -1);
			Map<String, String> userMap = auctionDebtService
					.getUserAddressById(alienatorId);
			request().setAttribute("userMap", userMap);
		}
		
		
		// 获取红包金额
		User user = (User) session().getAttribute("user");
		Map<String,String> map_6_24 = financeService.queryBonus_6_24_sum(user.getId()); 
		if(map_6_24!=null && map_6_24.size()>0){ 
            String bonus_able_6_24 = map_6_24.get("bonus_able"); 
            paramMap.put("bonus_able_6_24", bonus_able_6_24); 
		}else{ 
			paramMap.put("bonus_able_6_24", "0"); 
		}
		
		// 获取红包金额
		
		return financeDebtDetail();
	}
	
	
	public String financeDebtDetail()  throws Exception {
		session().setAttribute("DEMO", IConstants.ISDEMO);//得到是不是演示版本
		User user = (User) session().getAttribute("user");
		if(user!=null){
			Long userId = user.getId();
			Map<String, String> userMap = financeService.queryUserById(userId);
			if(userMap!=null){
				String usableSum = userMap.get("usableSum");
				request().setAttribute("usableSum", usableSum);
			}
			
		}else{
			request().setAttribute("isLogin", 0);
			return "noLogin";
		}
		String idStr = request.getString("id") == null ? "" : request.getString("id");
		if (!"".equals(idStr) && StringUtils.isNumericSpace(idStr)) {
			Long id = Convert.strToLong(idStr, -1);
			// 借款详细
			Map<String, String> borrowDetailMap = financeService.queryBorrowDebtDetailById(id);
			String borrowWays = borrowDetailMap.get("borrowWay");
			request().setAttribute("borrowWays", borrowWays);
			 
			String tempTime = "2015-08-31 23:59:59";
			String applyTime = borrowDetailMap.get("applyTime");
			int ret = applyTime.compareTo(tempTime);
			request().setAttribute("ret", String.valueOf(ret));
			 
			//-- 7 - 9
			//查询借款信息得到借款时插入的平台收费标准
			Map<String,String> map = borrowManageService.queryBorrowInfo(id);
			if (map == null) {
				return "404";
			}
			//得到收费标准的json代码
			String feelog = Convert.strToStr(map.get("feelog"), "");
			Map<String,Double> feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
			//--end
			String nid_log = borrowDetailMap.get("nid_log");
			Map<String,String>  TypeLogMap = null;
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"),-1);
				request().setAttribute("subscribes",stauts );
			}
			if (borrowDetailMap != null && borrowDetailMap.size() > 0) {
				double borrowSum = Convert.strToDouble(borrowDetailMap.get("borrowSum")+"", 0);
				double annualRate = Convert.strToDouble(borrowDetailMap.get("annualRate")+"", 0);
				int deadline = Convert.strToInt(borrowDetailMap.get("deadline")+"", 0);
				int paymentMode = Convert.strToInt(borrowDetailMap.get("paymentMode")+"", -1);
				int isDayThe = Convert.strToInt(borrowDetailMap.get("isDayThe")+"", 1);
				double investAmount = 10000;
				String earnAmount = "";
				if(borrowSum < investAmount){
					investAmount = borrowSum;
				}
				AmountUtil au = new AmountUtil();
				Map<String,String> earnMap = null;
				double costFee = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE)+"",0);
				
				// 如果是约标，则不显示加息
				double addInterest = Convert.strToDouble(borrowDetailMap.get("add_interest"), 0);
				int hasPwd = Convert.strToInt(borrowDetailMap.get("hasPWD"), -9999);
				if(hasPwd ==1 && addInterest>0){
					addInterest = 0;
				}
				annualRate = annualRate + addInterest;
				
				if(paymentMode == 1){
					//按月等额还款
					earnMap = au.earnCalculateMonth(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2,costFee);
					earnAmount = earnMap.get("msg")+"";
				}else if(paymentMode == 2){
					//先息后本
					earnMap = au.earnCalculateSum(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2);
					earnAmount = earnMap.get("msg")+"";
				}else if(paymentMode == 3){
					//秒还
					earnMap = au.earnSecondsSum(investAmount, borrowSum, annualRate, deadline,0, 2);
					earnAmount = earnMap.get("msg")+"";
				} else if (paymentMode == 4) {
					// 一次性还款
					earnMap = au.earnCalculateOne(investAmount, borrowSum,
							annualRate, deadline, 0, isDayThe, 2, costFee);
					earnAmount = earnMap.get("msg") + "";
				}
				//----------add by houli 借款类型判断，前台借款详细信息中需要显示
				String borrowWay = borrowDetailMap.get("borrowWay");
				request().setAttribute("borrowWay",borrowWay);
				//催收记录
				List<Map<String, Object>> collection = financeService.queryCollectionByid(id);
				if(collection != null && collection.size()>0)
					request().setAttribute("colSize", collection.size());
				
				request().setAttribute("earnAmount", earnAmount);
				request().setAttribute("earnMap", earnMap);
				// 每次点击借款详情时新增浏览量
				financeService.addBrowseCount(id);
				request().setAttribute("borrowDetailMap", borrowDetailMap);
				
				Map<String, String> borrowUserMap=new HashMap<String, String>();
				List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
				String publisherWay = borrowDetailMap.get("publisherWay"); //发布方式
				String borrowType = borrowDetailMap.get("borrowType"); //借款类型
				request().setAttribute("borrowType", borrowType);
				if("1".equals(publisherWay)){ //前台发布
					// 借款人资料
					 borrowUserMap = financeService
							.queryUserInfoById(id);
					// 借款人认证资料
					 list= financeService
							.queryUserIdentifiedByid(id);
				}else if("2".equals(publisherWay)){//后台发布
					if("1".equals(borrowType)){ //个人借款
						// 借款人资料
						 borrowUserMap = financeService
								.queryUserInfoByIdAdmin(id);
					}else{ //企业借款
						 borrowUserMap = financeService
							.queryEnterpriseUserInfoById(id);
					}
					// 借款人认证资料
					 list = financeService
							.queryUserIdentifiedByidAdmin(id);
				}
				DesSecurityUtil ds =new DesSecurityUtil();
				if(borrowUserMap!=null){
					String userId = borrowUserMap.get("userId");
					borrowUserMap.put("enUserId", ds.encrypt(userId));
				}
				
				//加密用户ID
				if(list.size()>0){
					Map<String, Object> listMap = 	list.get(0);
					String userId = listMap.get("userId")+"";
					String enUserId = ds.encrypt(userId);
					request().setAttribute("enUserId", enUserId);
				}
				request().setAttribute("borrowUserMap", borrowUserMap);
				request().setAttribute("list", list);
				
				// 投资记录
				List<Map<String, Object>> investList = financeService
						.queryInvestByid(id);
				
				if(investList!=null && investList.size()>0){
					for(int i=0;i<investList.size();i++){
						Map<String, Object> maps = investList.get(i);
					 	String investTime = String.valueOf(maps.get("investTime"));
					 	if(investTime.compareTo(applyTime)<0){
					 		maps.put("investTime", applyTime);
					 	}
					}
				}
				
				request().setAttribute("investList", investList);
				request().setAttribute("idStr", idStr);
				Map<String,String> borrowRecordMap = financeService.queryBorrowRecord(id);
				request().setAttribute("borrowRecordMap", borrowRecordMap);
				//-----------add by houli
				String wStatus = judgeStatus(Convert.strToInt(borrowWay, -1),
						Convert.strToLong(borrowDetailMap.get("publisher"), -100));
				if(wStatus == null){
					request().setAttribute("wStatus", "");
				}else{
					request().setAttribute("wStatus", wStatus);
				}
				// 借款显示类型，如果是流转标就跳转到流转标显示页面
				String cicuration = borrowDetailMap.get("borrowShow") + "";
				if (cicuration.equals("2")) {
					return "cicuration";
				}
				//郭井超----开始----信用借款或者实地考察
				long publisher = Long.parseLong(borrowDetailMap.get("publisher"));//发标人
				if(borrowWay.equals("3") || borrowWay.equals("4")){
					 /**判断发标人是否为前台高级用户*/
				     Map<String,String> userMap =	financeService.queryAdminUserPublisher(publisher);
				     if(userMap!=null && userMap.size()>0){
				    	 String isFlag = userMap.get("isFlag");
				    	 if("1".equals(isFlag)){//是小贷公司高级用户
				    		 long adminId = Long.parseLong(userMap.get("adminId"));//合作机构后台管理员id
				    		
				    		 Map<String,String> loanMap  = financeService.queryLoanCompany(adminId);//合作机构
				    		 Map<String,String> borrowMap = financeService.queryFinanceById(Convert.strToLong(idStr, 0));//合作模式为逐笔审批下的担保函
				    		 Map<String,String> bondingMap  = financeService.queryBondingCompany(Long.parseLong(loanMap.get("id")),Convert.strToLong((borrowMap.get("bonding_id")),0));//担保机构
				    		 List<Map<String, Object>> mortgPicList =  financeService.queryLoanCompanyMortgPic(Long.parseLong(loanMap.get("id")));//查询合作机构抵押物
				    		 List<Map<String, Object>> basePicList = financeService.queryLoanCompanyBasePic(Long.parseLong(loanMap.get("id")));//查询合作机基本资料
				    		
				    		 request().setAttribute("isFlag", isFlag);
				    		 request().setAttribute("loanMap", loanMap);
				    		 request().setAttribute("bondingMap", bondingMap);
				    		 request().setAttribute("borrowMap", borrowMap);
				    		 request().setAttribute("mortgPicList", mortgPicList);
				    		 request().setAttribute("basePicList", basePicList);
				    		 if(bondingMap!=null && bondingMap.size()>0){
				    			 request().setAttribute("isShowBondingCompany", 1);
				    		 }
				    	 }
				     }
				}
				//郭井超----结束----信用借款
			} else {
				return "404";
			}
		} else {
			return "404";
		}
		return "success";
	}

	/**
	 * @MethodName: financeAudit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:26:02
	 * @Return:
	 * @Descb: 借款人认证资料
	 * @Throws:
	 */
	public String financeAudit() throws Exception {
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
		String publisherWay = paramMap.get("publisherWay")==null ? "" : paramMap.get("publisherWay"); //发布方式
		if("1".equals(publisherWay)){ //前台发布
			// 借款人认证资料
			 list= financeService
					.queryUserIdentifiedByid(idLong);
		}else if("2".equals(publisherWay)){//后台发布
			// 借款人认证资料
			 list = financeService
					.queryUserIdentifiedByidAdmin(idLong);
		}	
		request().setAttribute("auditList", list);
		//加密用户ID
		if(list.size()>0){
			Map<String, Object> listMap = 	list.get(0);
			String userId = listMap.get("userId")+"";
			DesSecurityUtil ds =new DesSecurityUtil();
			String enUserId = ds.encrypt(userId);
			request().setAttribute("enUserId", enUserId);
		}
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: financeRepay
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:27:02
	 * @Return:
	 * @Descb: 借款人还款记录
	 * @Throws:
	 */
	public String financeRepay() throws Exception {
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		// 借款人还款记录
		List<Map<String, Object>> list = financeService
				.queryRePayByid(idLong);
		request().setAttribute("repayList", list);
		return "success";
	}

	
	/**   
	 * @MethodName: financeCollection  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:29:12
	 * @Return:    
	 * @Descb: 借款人催款记录
	 * @Throws:
	*/
	public String financeCollection() throws Exception {
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		// 借款人催款记录
		List<Map<String, Object>> list = financeService
				.queryCollectionByid(idLong);
		request().setAttribute("collectionList", list);
		return "success";
	}

	/**
	 * @MethodName: financeInvestInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:20:57
	 * @Return:
	 * @Descb: 理财投标初始化
	 * @Throws:
	 */
	public String financeInvestInit() throws Exception {
		Thread.sleep(100);
		User user = (User) session().getAttribute("user");
		Map<String, String> m = new HashMap<String, String>();
		m = userService.queryUserById(user.getId());
		 
		JSONObject obj = new JSONObject();
		String id = request.getString("id") == null ? "" : request.getString("id");
		String amountMoney = request.getString("amountMoney") == null ? "" : request.getString("amountMoney");
		request().setAttribute("amountMoney", amountMoney);
		double bonus_avaliable = Convert.strToDouble(request().getParameter("bonus_avaliable"), 0);//可用红包金额
		int isUseHb = Convert.strToInt(request().getParameter("isUseHb"), 0);//可用红包金额   1使用0不使用
		int bonus_type = Convert.strToInt(request().getParameter("bonus_type"), 0);//红包类型 1-注册，2-投资，3-总和
		double low_amount = Convert.strToDouble(request().getParameter("low_amount"), 0);//使用红包时候最低投标金额
		int isUseHb_6_24 = Convert.strToInt(request().getParameter("isUseHb_6_24"), 0);//可用红包金额   1使用0不使用
		double invest_proportion = Convert.strToDouble(request().getParameter("invest_proportion"), 0);//红包使用比例
		
		request().setAttribute("invest_proportion", invest_proportion);
		request().setAttribute("bonus_avaliable", bonus_avaliable);
		request().setAttribute("isUseHb", isUseHb);
		request().setAttribute("bonus_type", bonus_type);
		request().setAttribute("low_amount", low_amount);
		request().setAttribute("isUseHb_6_24", isUseHb_6_24);
		
		if(!StringUtils.isNumericSpace(id)){
			return INPUT;
		}
		long idLong = Convert.strToLong(id, -1);
		if (idLong == -1) {
			// 非法操作直接返回
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String, String> investMaps = financeService.getInvestStatus(idLong);
		String nid_log= "";
		if(investMaps!=null && investMaps.size()>0){
			 nid_log = Convert.strToStr(investMaps.get("nid_log"),"");
			 Map<String,String>  typeLogMap = null;
				if (StringUtils.isNotBlank(nid_log)) {
					typeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
					int stauts = Convert.strToInt(typeLogMap.get("subscribe_status"),-1);
					request().setAttribute("subscribes",stauts );
					request().setAttribute("investMaps",investMaps );
				}
		}
		
		if (investMaps != null && investMaps.size() > 0) {
			String hasPWD = investMaps.get("hasPWD") == null?"-1":investMaps.get("hasPWD");
			investDetailMap = financeService.queryBorrowInvest(idLong);
			double residue =  Convert.strToDouble(investDetailMap.get("residue"), 0);
			double minTenderedSum =  Convert.strToDouble(investDetailMap.get("minTenderedSum"), 0);
			if(residue <minTenderedSum ){
				request().setAttribute("minTenderedSum",residue);
			}else{
				request().setAttribute("minTenderedSum",minTenderedSum);
			}
			String userId = investDetailMap.get("userId") == null ? ""
					: investDetailMap.get("userId");
			if (userId.equals(user.getId().toString())) {
				// 不满足投标条件,返回
				obj.put("msg", "不能投标自己发布的借款");
				JSONUtils.printObject(obj);
				return null;
			}
			session().setAttribute("investStatus","ok");
			Map<String, String> userMap = financeService.queryUserMonney(user
					.getId());
			request().setAttribute("userMap", userMap);
			session().setAttribute("hasPWD", hasPWD);
		} else {
			// 不满足投标条件,返回
			obj.put("msg", "该借款投标状态已失效");
			JSONUtils.printObject(obj);
			return null;
		}
		return "success";
	}

	
	/**   
	 * @throws Exception 
	 * @MethodName: financeInvestLoad  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:04:52
	 * @Return:    
	 * @Descb: 输入密码后的投标
	 * @Throws:
	*/
	public String financeInvestLoad() throws Exception{
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id")==null?"":paramMap.get("id");
		String investPWD = paramMap.get("investPWD")==null?"":paramMap.get("investPWD");
		long idLong = Convert.strToLong(id, -1);
    
		if (idLong == -1) {
			// 非法操作直接返回
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}
		if ("".equals(investPWD)) {
			this.addFieldError("paramMap['investPWD']", "请输入投标密码");
			return "input";						
		}
		Map<String, String> investPWDMap = financeService.getInvestPWD(idLong,investPWD);
		if (investPWDMap == null || investPWDMap.size() ==0) {
			this.addFieldError("paramMap['investPWD']", "投标密码错误");
			return "input";						
		}
		// 判断是否进行了资料审核
		Object object = session().getAttribute("investStatus");
		if (object == null) {
			return null;
		}
		Map<String, String> investMaps = financeService.getInvestStatus(idLong);
		if (investMaps != null && investMaps.size() > 0) {
			investDetailMap = financeService.queryBorrowInvest(idLong);

			String userId = investDetailMap.get("userId") == null ? ""
					: investDetailMap.get("userId");
			if (userId.equals(user.getId().toString())) {
				// 不满足投标条件,返回
				obj.put("msg", "不能投标自己发布的借款");
				JSONUtils.printObject(obj);
				return null;
			}
			Map<String, String> userMap = financeService.queryUserMonney(user
					.getId());
			request().setAttribute("userMap", userMap);
		} else {
			// 不满足投标条件,返回
			obj.put("msg", "该借款投标状态已失效");
			JSONUtils.printObject(obj);
			return null;
		}
		return "success";
	}
	/**   
	 * @MethodName: financeInvest  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-3-30 下午03:53:34
	 * @Return:    
	 * @Descb: 投标借款
	 * @Throws:
	*/
	public String financeInvest() throws Exception {
			JSONObject obj = new JSONObject();
			//金额校验
			String amount = paramMap.get("amount") == null?"":paramMap.get("amount");
			if("".equals(amount)){
				obj.put("msg", "请输入金额");
				JSONUtils.printObject(obj);
				return null;
			}else{
				if(!amount.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")){
					obj.put("msg", "金额格式不正确");
					JSONUtils.printObject(obj);
					return null;
				}
				if(StringUtils.isNumeric(amount)){
					if(amount.length()>=30){
						obj.put("msg", "金额小于30个字符");
						JSONUtils.printObject(obj);
						return null;
					}
				}else{
					obj.put("msg", "金额必须为整数");
					JSONUtils.printObject(obj);
					return null;
				}
				
			}
			int isUseHb_6_24 = Convert.strToInt(paramMap.get("isUseHb_6_24"), 0);//是否使用红包    0-不使用，1-使用
		    int isUseHb = Convert.strToInt(paramMap.get("isUseHb"), 0);//是否使用红包    0-不使用，1-使用
			double bonus_avaliable = Convert.strToDouble(paramMap.get("bonus_avaliable"), 0);//可用红包金额
			double low_amount = Convert.strToDouble(paramMap.get("low_amount"), 0);//使用红包时的最低输入投标金额
			int bonus_type = Convert.strToInt(paramMap.get("bonus_type"), 0);//使用红包时的最低输入投标金额
			//表示选择使用红包
			if(isUseHb==1){
				double amountTz = Convert.strToDouble(amount, 0);
				if(amountTz-low_amount<0){
					DecimalFormat df = new DecimalFormat("#.00");
					obj.put("msg", "您已选择使用红包，输入投资金额应至少为："+df.format(low_amount)+"元");
					JSONUtils.printObject(obj);
					return null;
				}
			}
			
		    User user = (User) session().getAttribute("user");
		    Map<String, String> m = new HashMap<String, String>();
			m = userService.queryUserById(user.getId());
			if (null != m) {
				if (m.get("password").equals(m.get("dealpwd"))) {
					getOut()
					.print(
							"<script>alert('*您还没有修改交易密码，为了您的帐号安全，请您先修改交易密码! ');parent.location.href='setPwd.do';</script>");
					return null;
				}
				int isApplyPro = Convert.strToInt(m.get("isApplyPro"), 1);
				/*if (isApplyPro == 1) {
					getOut()
							.print(
									"<script>alert('*您的账号还没有申请密保，为了您的帐号安全，请您申请密保！! ');window.location.href='queryQuestion.do';</script>");
					return null;

				}*/
			}

		    
		    boolean re = userService.checkSign(user.getId());
			if(!re){
				request().getSession().removeAttribute("user");
				request().getSession().invalidate();
				getOut().print(
				"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
				return null;
			}
			String id = paramMap.get("id") == null?"":paramMap.get("id");
			long idLong = Convert.strToLong(id, -1);
			//String amount = paramMap.get("amount") == null?"":paramMap.get("amount");
			double amountDouble = Convert.strToDouble(amount, 0);
			String hasPWD = ""+session().getAttribute("hasPWD");
		    String investPWD = paramMap.get("investPWD") == null?"":paramMap.get("investPWD");
		    int status =Convert.strToInt( paramMap.get("subscribes"),2);
		    if("1".equals(hasPWD)){
		    	Map<String, String> investPWDMap = financeService.getInvestPWD(idLong,investPWD);
				if (investPWDMap == null || investPWDMap.size() ==0) {
					/*if(status == 1){
						obj.put("msg", "投标密码错误");
						JSONUtils.printObject(obj);
						return null;
					}*/
					obj.put("msg", "投标密码错误");
					JSONUtils.printObject(obj);
					return null;
					//this.addFieldError("paramMap['investPWD']", "投标密码错误");
					//return "input";						
				}
		    }
		    
		    investDetailMap = financeService.queryBorrowInvest(idLong);
		    int num =0;
		    if(status ==1){
		    	double smallestFlowUnit = Convert.strToDouble(paramMap.get("smallestFlowUnit"), 0.0);
		    	if (smallestFlowUnit==0) {
		    		obj.put("msg", "操作失败");
					JSONUtils.printObject(obj);
					return null;
				}
		    	String result = Convert.strToStr(paramMap.get("result"),"");
		    	if(StringUtils.isBlank(result)){
		    		obj.put("msg", "请输入购买的份数");
					JSONUtils.printObject(obj);
					return null;
		    	}
		    	boolean b=result.matches("[0-9]*");
		    	if(!b){
		    		obj.put("msg", "请正确输入购买的份数");
					JSONUtils.printObject(obj);
					return null;
		    	} 
		    	
		    	 
		    	String userId = investDetailMap.get("userId") == null ? "": investDetailMap.get("userId");
		    	if (userId.equals(user.getId().toString())) {
			    	obj.put("msg", "不能投标自己发布的借款");
			    	JSONUtils.printObject(obj);
			    	return null;
		    	}
		    	 num = Integer.parseInt(result);
		    	if (num<1) {
		    		obj.put("msg", "请正确输入购买的份数");
					JSONUtils.printObject(obj);
					return null;
				}
		    	amountDouble = num * smallestFlowUnit;
		    }
		    
		    /**全投计算*/
		    double 	borrowAmount =  Convert.strToDouble(investDetailMap.get("borrowAmount"), 0);//借款金额
			double 	investAmountSum = 0;
			Map<String,String> investSumMap = financeService.queryInvestSumAmount(idLong);
			if(investSumMap!=null && investSumMap.size()>0){
			    	investAmountSum =  Convert.strToDouble(investSumMap.get("investAmountSum"), 0);//投标总额
			}
		    
		    if(investAmountSum+amountDouble>borrowAmount){
		    	amountDouble = borrowAmount - investAmountSum;
		    }
		    
		    //判断是否为首单
		    String mark = financeService.queryInvestId(-1, user.getId());
			//查询是否是首充
		    Map<String,String> flagMap = financeService.searchInvest(user.getId());	  
		    Map<String,String> borrowInstance= financeService.queryBorrowById(idLong);
		    Map<String,String> result = financeService.addBorrowInvest(idLong, user
					.getId(),"", Math.floor(amountDouble),getBasePath(),user.getUserName(),status,num,
					isUseHb,bonus_avaliable,bonus_type,isUseHb_6_24);
		    
		    String resultMSG = result.get("ret_desc");
		    userService.updateSign(user.getId());//更换校验码
		    financeService.addInvestMail(user.getId(), Double.parseDouble(amount), borrowInstance.get("borrowTitle"));
		    //whb 富爸爸接口调用
		    String investId = "";
		    if(!"".equals(result.get("ret_id"))){
		    	investId = result.get("ret_id");
		    }
		  //更新session中的可用余额
			Map<String,String> userMap = userService.queryUserById(user.getId());
			if(userMap!=null && userMap.size()>0){
				user.setUsableSum(userMap.get("usableSum"));
				session().setAttribute("user", user);
			}
			//判断客户是否是富爸爸推荐过来的
			Map<String,String> ps = partenersService.queryPartenersUser(user.getId());
		    if("".equals(resultMSG)){
		    	// TODO:后续改为分布式调用接口，实时的压力会越来越大，影响投标流程
			    if(amountDouble >= 200 && null != ps && ps.size() > 0 && "1".equals(ps.get("pid"))){
			    	String resultFbaba = fbaba(idLong, amountDouble, investId, mark,user.getId());
			    	log.info("广告商富爸爸调用返回结果为："+resultFbaba+"---------------"+amountDouble);
			    }
			    //添加超级用户显示名称变换
			    Map<String, String> map = financeService.queryGroupById(user.getId());
			    if(null != map && !map.isEmpty()){
			    	financeService.updateChangeUsername(Convert.strToLong(investId, 0),DateUtil.getChangeUserName());
			    }
		    	obj.put("msg", 1);
		    }else{
		    	obj.put("msg", result.get("ret_desc")+"");
		    }
		    JSONUtils.printObject(obj);
		    //七月份发红包活动
		    /*if(Integer.parseInt(obj.get("msg").toString())==1&&Integer.parseInt(amount)>=1000){
		    	addBonus(flagMap,user);
		    }*/
			return null;
	}
	
	/**
	 * 富爸爸接口调用
	 * @author whb
	 * @param id
	 * @return
	 */
	public String fbaba(long id, double amount, String investId, String mark,long userId){
		Map<String, String> investMaps;
		//Map<String, String> typeMap;
		//返回值
		String result = "";
		//借款标题
		String borrowTitle = "";
		//标的类型
		String type = "";
		int borrowWay = 0;
		//借款期限
		String deadline = "";
		//期限单位
		String termUnit = "";
		//计算佣金期限
		double line = 0.00;
		try {
			List<Map<String,Object>> ps = partenersService.queryPartenersUserByConditions(userId);
		    if(ps != null && ps.size()>0 && userId >0){
		    	
		    	//推荐的用户注册后30天内没有投资则不算为首单
		    	int day = 0;
		    	Map<String, String> map = financeService.getFbabaUser(userId);
		    	if(map != null && map.size() > 0){
		    		day = Convert.strToInt(map.get("day"), 0);
		    	}
		    	
				investMaps = financeService.getInvestToFbaba(id);
				if(investMaps != null && investMaps.size() > 0){
					borrowTitle = investMaps.get("borrowTitle");
					deadline = investMaps.get("deadline");
					//期限单位
					int isDayThe = Integer.parseInt(investMaps.get("isDayThe"));
					line = Double.parseDouble(deadline);
					if(1 == isDayThe){
						termUnit = "月";
					}else if(2 == isDayThe){
						termUnit = "天";
						line = line / 30;
					}
					//标的类型
					borrowWay = Integer.parseInt(investMaps.get("borrowWay"));
					if(3 == borrowWay){
						type = "优选宝";
					}else if(4 == borrowWay){
						type = "定息宝";
					}else if(6 == borrowWay){
						type = "活利宝";
					}else{
						type = "其他";
					}
					
				}
				/*
				typeMap = shoveBorrowTypeService.queryShoveBorrowTypeById(borrowWay);
				if(typeMap != null && typeMap.size() > 0){
					type = typeMap.get("name");
				}*/
				
				StringBuffer url = new StringBuffer("http://www.fbaba.net/track/cps.php");
				String sig = com.shove.security.Encrypt.MD5(investId+"jcbanking@123");
				//固定值create
				url.append("?action=create");
				//计划id值,在联盟中创建的广告计划ID
				url.append("&planid=28");
				//订单号,即投资id
				url.append("&order=" + investId);
				//1 代表首单,2 代表常规单
				DecimalFormat df_two = new DecimalFormat("#0.00");
				if((mark == null || "".equals(mark) || "null".equals(mark)) && day <= 30){
					url.append("&goodsmark=1");
					//订单金额,首单70;常规单投资金额
					url.append("&goodsprice=70");
					//项目详情,包括项目名称,项目类型,投资周期
					url.append("&goodsname=名称:" + borrowTitle	+ "-类型:" + type + "-周期:" + deadline	+ termUnit);
					//数据签名,将订单号码和Key进行加密   Md5(order+key) 
					url.append("&sig="+sig);
					//订单状态/备注,在首单中,需要将投资金额备注到字段中 
					url.append("&status=首单【" + df_two.format(amount)	+ "元：已付款】");
				}else{
					//goodsprice=出借金额*项目借款期限*1%/12(项目借款期限以月为单位)
					url.append("&goodsmark=2");
					url.append("&goodsprice=" + amount);
					//项目详情,包括项目名称,项目类型,投资周期
					url.append("&goodsname=名称:" + borrowTitle	+ "-类型:" + type + "-周期:" + deadline	+ termUnit);
					//数据签名,将订单号码和Key进行加密   Md5(order+key) 
					url.append("&sig="+sig);
					url.append("&status=复投【" + df_two.format(amount)	+ "元：已付款】");
				}
				//联盟会员id值 
				String uid = "";
				if(StringUtils.isNotBlank(String.valueOf(ps.get(0).get("uid")))){
					uid = String.valueOf(ps.get(0).get("uid"));
				}
				url.append("&uid="+uid);
				float goodsprice = Float.parseFloat(String.valueOf(amount * line * 0.01 / 12));
				HttpClientHelp httpClientHelp = new HttpClientHelp();
				result = httpClientHelp.byGetMethodToHttpEntity(url.toString());
				partenersService.addParenersMessage(6, "富爸爸参数回传结果："+result, url.toString(), userId, 1, amount, goodsprice, "", new Date());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @MethodName: borrowMSGInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:08:51
	 * @Return:
	 * @Descb: 借款留言初始化
	 * @Throws:
	 */
	public String borrowMSGInit() throws Exception {
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String pageNum = paramMap.get("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		if (idLong == -1) {
			return "404";
		}
		financeService.queryBorrowMSGBord(idLong, pageBean);
		request().setAttribute("id", id);
		return "success";
	}

	/**
	 * @throws IOException
	 * @throws SQLException
	 * @throws DataException 
	 * @MethodName: addBorrowMSG
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:09:06
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public String addBorrowMSG() throws Exception {
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("msg_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		//图形验证码不区分大小写
		if (!code.toLowerCase().equals(_code.toLowerCase())) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String msgContent = paramMap.get("msg") == null ? "" : paramMap
				.get("msg");
		if(isKeywords.isKeywordsOnDB(msgContent)){
			this.addFieldError("paramMap['code']", "留言内容含有关键字，不能留言");
			return "input";
		}
		long returnId = -1;
		returnId = financeService.addBorrowMSG(idLong, user.getId(), msgContent);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @throws IOException
	 * @throws DataException
	 * @MethodName: focusOnBorrow
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午09:06:16
	 * @Return:
	 * @Descb: 我关注的借款
	 * @Throws:
	 */
	public String focusOnBorrow() throws Exception,
			DataException {
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		long returnId = -1L;
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		if (idLong == -1) {
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}

		Map<String, String> map = financeService.hasFocusOn(idLong, user
				.getId(), IConstants.FOCUSON_BORROW);
		if (map != null && map.size() > 0) {
			obj.put("msg", "您已关注过该借款");
			JSONUtils.printObject(obj);
			return null;
		}

		returnId = financeService.addFocusOn(idLong, user.getId(),
				IConstants.FOCUSON_BORROW);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "关注成功!");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @throws IOException
	 * @throws DataException
	 * @MethodName: focusOnUser
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午09:07:20
	 * @Return:
	 * @Descb: 我关注的用户
	 * @Throws:
	 */
	public String focusOnUser() throws Exception {
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		long returnId = -1L;
		DesSecurityUtil ds =new DesSecurityUtil();
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		id = ds.decrypt(id);
		long idLong = Convert.strToLong(id, -1);
		if (idLong == -1) {
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}

		Map<String, String> map = financeService.hasFocusOn(idLong, user
				.getId(), IConstants.FOCUSON_USER);
		if (map != null && map.size() > 0) {
			obj.put("msg", "您已关注过该用户");
			JSONUtils.printObject(obj);
			return null;
		}
		returnId = financeService.addFocusOn(idLong, user.getId(),
				IConstants.FOCUSON_USER);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "关注成功!");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @MethodName: mailInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:31
	 * @Return:
	 * @Descb: 发送站内信初始化
	 * @Throws:
	 */
	public String mailInit() {
		String id = request.getString("id");
		String userName = String.valueOf(request.getString("username"));

		if ("null".equals(userName)) {
			userName = "";
		} else {
			try {
				userName = new String(userName.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		
		request().setAttribute("id", id);
		request().setAttribute("userName", userName);
		return "success";
	}

	/**
	 * @MethodName: reportInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:48
	 * @Return:
	 * @Descb: 举报用户初始化
	 * @Throws:
	 */
	public String reportInit() {
		String id = request.getString("id");
		String userName = String.valueOf(request.getString("username"));
		
//		String id = paramMap.get("id");
//		String userName = paramMap.get("username");
		
		if("null".equals(userName)){
			userName = "";
		}else{
			try {
				userName = new String(userName.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		request().setAttribute("id", id);
		request().setAttribute("userName", userName);
		return "success";
	}

	public String mailAdd() throws Exception {
		User user = (User) session().getAttribute("user");
		
		JSONObject obj = new JSONObject();
		

		String code = (String) session().getAttribute("code_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		//图形验证码不区分大小写
		if (!code.toLowerCase().equals(_code.toLowerCase())) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		DesSecurityUtil ds =new DesSecurityUtil();
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		id = ds.decrypt(id);
		long reciver = Convert.strToLong(id, -1);
		String title = paramMap.get("title") == null ? "" : paramMap
				.get("title");
		String content = paramMap.get("content") == null ? "" : paramMap
				.get("content");
		long returnId = -1;
		Integer enable=user.getEnable();
		if(enable==3){
			obj.put("msg", "8");
			JSONUtils.printObject(obj);
			return null;
		}
		returnId = financeService.addUserMail(reciver, user.getId(), title,
				content, IConstants.MALL_TYPE_COMMON);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @throws DataException 
	 * @MethodName: reportAdd
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:16:11
	 * @Return:
	 * @Descb: 添加用户举报
	 * @Throws:
	 */
	public String reportAdd() throws Exception {
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("code_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		//图形验证码不区分大小写
		if (!code.toLowerCase().equals(_code.toLowerCase())) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		DesSecurityUtil ds =new DesSecurityUtil();
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		id = ds.decrypt(id);
		long reporter = Convert.strToLong(id, -1);
		String title = paramMap.get("title") == null ? "" : paramMap
				.get("title");
		String content = paramMap.get("content") == null ? "" : paramMap
				.get("content");
		long returnId = -1;
		returnId = financeService.addUserReport(reporter, user.getId(), title,
				content);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	
	/**   
	 * @MethodName: showImg  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:24:03
	 * @Return:    
	 * @Descb: 查看图片
	 * @Throws:
	*/
	public String showImg() throws Exception{
		String typeId = request.getString("typeId") == null?"":request.getString("typeId");
		String userId = request.getString("userId") == null?"":request.getString("userId");
		DesSecurityUtil ds =new DesSecurityUtil();
		userId = ds.decrypt(userId);
		long typeIdLong = Convert.strToLong(typeId, -1);
		long userIdLong = Convert.strToLong(userId, -1);
		List<Map<String,Object>> imgList = financeService.queryUserImageByid(typeIdLong, userIdLong);
		request().setAttribute("imgList", divisionString(imgList));
		return "success";
	}
	
	/**
	 * 项目详情中查看抵押物
	 * @return
	 * @throws Exception
	 */
	public String showImgDyw() throws Exception{
		String typeId = request.getString("typeId") == null?"":request.getString("typeId");
		String userId = request.getString("userId") == null?"":request.getString("userId");
		DesSecurityUtil ds =new DesSecurityUtil();
		userId = ds.decrypt(userId);
		long typeIdLong = Convert.strToLong(typeId, -1);
		long userIdLong = Convert.strToLong(userId, -1);
		List<Map<String,Object>> imgList = financeService.queryUserImageByidDyw(typeIdLong, userIdLong);
		request().setAttribute("imgList", divisionString(imgList));
		return "success";
	}
	
	/**
	 * 出除../避免前台页面图片无法显示出来。
	 * @param imgList
	 * @return
	 */
	private List<Map<String,Object>> divisionString(List<Map<String,Object>> imgList){
		
		if(null != imgList && imgList.size()>0){
			for (Map<String, Object> map : imgList) {
				String imagePath = String.valueOf(map.get("imagePath"));
				if(imagePath.startsWith("../") && imagePath.length() > 3){//以../开头同时长度必须大于3，避免截字符串时报异常。
					map.put("imagePath", imagePath.substring(3,imagePath.length()));
				}
			}
			
		}
		
		return imgList;
	}

	/**
	 * 跳转流转标购买 页面
	 * @throws Exception 
	 */
	public String subscribeinit() throws Exception{
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###,##0.00");
		User user = (User) session().getAttribute("user");
		long borrowid = request.getLong("borrowid", -1);
		try {
			Map<String, String> borrowDetailMap = financeService.queryBorrowDetailById(borrowid);
			Map<String, String> investMap = financeService.queryInvest(user.getId(), borrowid);
			Map<String, String> borrowMap = financeService.queryBorrow(borrowid);
			double maxMoneyValue = Convert.strToDouble(borrowDetailMap.get("maxMoneyValue"), 0);
			double hasInvestAmount = Convert.strToDouble(investMap.get("investAmount"), 0);
			double smallestFlowUnit = Convert.strToDouble(borrowDetailMap.get("smallestFlowUnit"), 0);
			 
			double result  = maxMoneyValue - hasInvestAmount;//投资上限减去已经投资金额==可投金额
			if(result==0){
				borrowDetailMap.put("investAmount_t", String.valueOf(myformat.format(result)));
				borrowDetailMap.put("remainCirculationNumber_t", String.valueOf(0));
			}else{
				borrowDetailMap.put("investAmount_t", String.valueOf(myformat.format(result)));
				int m = (int) (result/smallestFlowUnit);
				borrowDetailMap.put("remainCirculationNumber_t", String.valueOf(m));
			}
			 
			//单次最大投标金额
			double maxTenderedSum = Convert.strToDouble(borrowMap.get("maxTenderedSum"), 0) ;
			int n = 0;
			if(maxTenderedSum>0){
				n = (int) (maxTenderedSum/smallestFlowUnit);
			}
			request().setAttribute("maxMoneyValue",myformat.format(maxMoneyValue));
			request().setAttribute("hasInvestAmount",myformat.format(hasInvestAmount));
			request().setAttribute("borrowDetailMap",borrowDetailMap);
			request().setAttribute("n",n);
			request().setAttribute("maxTenderedSum",String.valueOf(myformat.format(maxTenderedSum)));
			request().setAttribute("isUseHb_6_24",Convert.strToInt(request().getParameter("isUseHb_6_24"), 0));
			 
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} 
		return SUCCESS;
	}
	

	/**
	 * @throws SQLException
	 * @throws DataException 
	 * @MethodName: subscribe
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午08:22:15
	 * @Return:
	 * @Descb: 认购流转标
	 * @Throws:
	 */
	public String subscribe() throws Exception {
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String result = paramMap.get("result");
		int isUseHb_6_24 = Convert.strToInt(paramMap.get("isUseHb_6_24"), 0);
		//最小流转单位
		double amountUnit = Convert.strToDouble(paramMap.get("amountUnit"), 0.0);
		int resultLong = Convert.strToInt(result, -1);
		
		if (idLong == -1) {
			obj.put("msg", "无效认购标的");
			JSONUtils.printObject(obj);
			return null;
		}
		if (resultLong == -1) {
			obj.put("msg", "非法的认购份数");
			JSONUtils.printObject(obj);
			return null;
		} else if (resultLong <= 0) {
			obj.put("msg", "请输入正确的认购份数");
			JSONUtils.printObject(obj);
			return null;
		}
		
		//判断是否为首单
	    String mark = financeService.queryInvestId(-1, user.getId());
		//查询是否是首充
	    Map<String,String> flagMap = financeService.searchInvest(user.getId());	
//		String resultStr = financeService.subscribeSubmit(idLong, resultLong,
//				user.getId(), getBasePath(), user.getUserName(),getPlatformCost());
		Map<String,String> resultMap = financeService.subscribeSubmit(idLong, resultLong,
				user.getId(), getBasePath(), user.getUserName(),getPlatformCost(),isUseHb_6_24);
		Map<String, String> borrowMap = financeService.queryBorrowDetailById(idLong);
		long publisherId = Convert.strToLong(borrowMap.get("publisher"), -1L);
		userService.updateSign(user.getId());//更换校验码
		userService.updateSign(publisherId);//更换借款人的校验码
		
		String resultStr = "";
		String investId = "";
		if(resultMap != null && resultMap.size() > 0){
			resultStr = resultMap.get("msg");
			investId = resultMap.get("investId");
		}
		//判断客户是否是富爸爸推荐过来的
		Map<String,String> ps = partenersService.queryPartenersUser(user.getId());
		//whb 调用富爸爸接口
		double amount = amountUnit * resultLong;
		if(amount >= 200 && null != ps && !ps.isEmpty() && "1".equals(ps.get("pid"))){
			String resultFbaba = "";
			if("1".equals(resultStr)){
				resultFbaba = fbaba(idLong, amount, investId, mark,user.getId());
				log.info("广告商富爸爸调用返回结果为："+resultFbaba+"---------------"+amount);
			}
		}
		//添加超级用户显示名称变换
	    Map<String, String> map = financeService.queryGroupById(user.getId());
	    if(null != map && !map.isEmpty()){
	    	financeService.updateChangeUsername(Convert.strToLong(investId, 0),DateUtil.getChangeUserName());
	    }
		
		//更新session中的可用余额
		Map<String,String> userMap = userService.queryUserById(user.getId());
		if(userMap!=null && userMap.size()>0){
			user.setUsableSum(userMap.get("usableSum"));
			session().setAttribute("user", user);
		}
	    if(resultMap.get("msg").equals("1")&&amountUnit*10>=1000){
	    	addBonus(flagMap,user);
	    }
		
		obj.put("msg", resultStr);
		JSONUtils.printObject(obj);
		return null;
	}
    //活动时间：2016年7月1日-7月31日    
    //七月份红包
	public void addBonus(Map<String,String> flagMap,User user){
		String startTime = "2016-07-01";
		String andTime = "2016-07-31";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			Date d1 = sdf.parse(startTime);
			Date d2 = sdf.parse(andTime);		
			//判断是否在活动时间段内
		if (d1.getTime() <=date.getTime() && date.getTime() <=d2.getTime()) {
				//判断是否是首充
				if (sdf.parse(flagMap.get("time")).getTime()<d1.getTime()&&StringUtils.isBlank(flagMap.get("investAmount"))) {
					//分配200红包奖励
					bonusService.addBonusList(user.getId(), user.getId(), 200, 200, 0, 2, 1, "2016-07-31");
				}
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 债权转让
	 * 
	 * @param：borrowId借款id;investId还款拥有者;annualRate年利率;transRatio转让系数;nextRepayDate下一还款日期
	 * @author whb
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public Map<String,Object> coseDebtPrice(long borrowId, long investId, String  transRatio){
		Map<String,Object> result = null;
		try {
			Map<String,String> map = assignmentDebtService.getDebtByConditions(borrowId,investId);
			//剩余期数
//			String remainPeriod = "";
			String annualRate = "";
			//待收本息
			double awaitPI = 0;
			if(map != null && map.size() > 0){
				//待收本息
				awaitPI = Double.parseDouble(String.valueOf(map.get("recievedPI"))) - Double.parseDouble(String.valueOf(map.get("hasPI")));
			}else{
				log.info("该债权已经被投满！！！");
			}
			
			Map<String, String> debtMap = null;
			//获取当前借款
			Map<String, String> map1 = assignmentDebtService.getOnePay(borrowId);
			
			if(null != map1 && map1.size() >0){
				if(1 == Integer.parseInt(map1.get("deadline"))){ // 活力宝
					log.info("===========暂不支持活力宝转让=============");
				}else{
					debtMap = assignmentDebtService.getDebt(borrowId,investId);
				}
			}else {
				log.info("===========查询标的信息错误【BORRROW_ID:"+borrowId+"不存在】=============");
			}
			
			result = DebtUtil.debtFormula(debtMap,annualRate);
			
			result.put("awaitPI", awaitPI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 已经结束的 债权转让计算
	 */
	public Map<String,Object> coseDebtPrice(long investId, String annualRate) throws Exception{
		
		Map<String, String> debtMap = null;
		Map<String,Object> result = null;
		
		debtMap = assignmentDebtService.queryDebtCoseInvestedMap(investId);
		result = DebtUtil.debtFormula(debtMap,annualRate);
		result.put("awaitPI", 0.00);
		return result;
	}
	
	
	public Map<String, String> getInvestDetailMap() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		if (investDetailMap == null) {
			investDetailMap = financeService.queryBorrowInvest(idLong);
		}
		return investDetailMap;
	}

	public String infomationById() throws Exception {
		long id = Convert.strToLong(request().getParameter("id"), 0);
		Map<String,String> infomationMap = newsService.getInfomationById(id);
		request().setAttribute("infomationMap", infomationMap);
		
		Map<String,String> infomationMapNext = newsService.getInfomationById(id-1);
		if(infomationMapNext==null){
			infomationMapNext = newsService.getInfomationById(id-2);
		}
		request().setAttribute("infomationMapNext", infomationMapNext);
		 
		Map<String,String> infomationMapAgo = newsService.getInfomationById(id+1);
		if(infomationMapAgo==null){
			infomationMapAgo = newsService.getInfomationById(id+2);
		}
		request().setAttribute("infomationMapAgo", infomationMapAgo);
		return "success";
	}
	
	public String licaiById() throws Exception {
		long id = Convert.strToLong(request().getParameter("id"), 0);
		Map<String,String> licaiMap = newsService.getLicaiById(id);
		request().setAttribute("licaiMap", licaiMap);
		
		Map<String,String> licaiMapNext = newsService.getLicaiById(id-1);
		if(licaiMapNext==null){
			licaiMapNext = newsService.getLicaiById(id-2);
		}
		request().setAttribute("licaiMapNext", licaiMapNext);
		 
		Map<String,String> licaiMapAgo = newsService.getLicaiById(id+1);
		if(licaiMapAgo==null){
			licaiMapAgo = newsService.getLicaiById(id+2);
		}
		request().setAttribute("licaiMapAgo", licaiMapAgo);
		return "success";
		
	}
	
	/**
	 *20160801 八月份活动
	 * @return
	 */
	
	
	public String searchHtml() throws Exception {
 
		return "success";
		
	}
	public String searchActivity()  throws Exception{
		JSONObject obj = new JSONObject();
		try {
			User user = (User) session().getAttribute("user");
			if (user == null) {
				request().setAttribute("isLogin", 0);
				String url = request().getScheme() + "://"; // 请求协议 http 或 https
				url += request().getHeader("host"); // 请求服务器
				url += request().getRequestURI(); // 工程名
				if (request().getQueryString() != null) // 判断请求参数是否为空
					url += "?" + request().getQueryString(); // 参数
				System.out.println(url);
				session().setAttribute("returnUrl", url);
				obj.put("msg", "先登录");
				JSONUtils.printObject(obj);
				return "noLogin";
			} else {
				Map<String, String> map = userService.searchInvestSumByUserId(user.getId());
				//是否有活动记录
				Map<String, String> acMap = userService.searchActivity20160801ByUser(user.getId());
				//判断是否为空
				if(StringUtils.isNotBlank(map.get("investAmountSum"))){
				//活动投资金额
				double investAmountSum=Double.parseDouble(map.get("investAmountSum"));
				//抽奖次数
				int count=0;
				if (investAmountSum >=600 && investAmountSum < 1500) {
					count=1;
				}
				if (investAmountSum >=1500 && investAmountSum <3000) {
					count=2;
				}
				if (investAmountSum>=3000) {
					count=3;
				}
				//判断是否投资
//				if(investAmountSum==0){
//					obj.put("msg", "0");
//					JSONUtils.printObject(obj);
//					return "success";
//				}
				System.out.println(acMap+"================"+investAmountSum+"======================"+count);
				if(acMap==null&&count>0){
					System.out.println("1111111111111111111111111111111111111111");
					userService.addActivity20160801ByUser(user.getId(),0,count);
				}else{
					System.out.println("222222222222222222222222222222222222222222");
					userService.updateActivity20160801ByUser(user.getId(),count,Integer.parseInt(acMap.get("already_number")));
					}
				}else{
//					obj.put("msg","0");
//					JSONUtils.printObject(obj);
					System.out.println("3333333333333333333333333333333333333333333");
					return "success";
				}
				System.out.println("444444444444444444444444444444444444444");
				log.info("查询八月活动寻宝次数");
				return "success";
			}


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("55555555555555555555555555555555555");
			log.info("八月活动异常"+e.getMessage());
		}

		return null;
	}
	
	/**
	 * 查询宝箱金额
	 * @return
	 * @throws Exception
	 */
    public String searchBox() throws Exception {
		JSONObject obj = new JSONObject();
		try {
			User user = (User) session().getAttribute("user");
			if (user == null) {
				request().setAttribute("isLogin", 0);
				String url = request().getScheme() + "://"; // 请求协议 http 或 https
				url += request().getHeader("host"); // 请求服务器
				url += "/page/activity/20160801/bx.html"; // 工程名
				if (request().getQueryString() != null) // 判断请求参数是否为空
					url += "?" + request().getQueryString(); // 参数
				System.out.println(url);
				session().setAttribute("returnUrl", url);
				obj.put("msg", "先登录");
				JSONUtils.printObject(obj);
				System.err.println("11111111111111111111111111111111");
				return "noLogin";
			} else {
				//查询投资金额
				Map<String, String> map = userService.searchInvestSumByUserId(user.getId());
				//是否有活动记录
				Map<String, String> acMap = userService.searchActivity20160801ByUser(user.getId());
				//判断是否为空
				if(StringUtils.isNotBlank(map.get("investAmountSum"))){
				//活动投资金额
				double investAmountSum=Double.parseDouble(map.get("investAmountSum"));
				//抽奖次数
				int count=0;
				if (investAmountSum >=600 && investAmountSum < 1500) {
					count=1;
				}
				if (investAmountSum >=1500 && investAmountSum <3000) {
					count=2;
				}
				if (investAmountSum>=3000) {
					count=3;
				}
				if(count==0&&investAmountSum<600){
					System.err.println("222222222222222222222222");
					obj.put("msg","3");
					JSONUtils.printObject(obj);
					return null;
				}
				if(acMap==null&&count>0){
					userService.addActivity20160801ByUser(user.getId(),0,count);
				}else{
					userService.updateActivity20160801ByUser(user.getId(),count,Integer.parseInt(acMap.get("already_number")));
					}
				}else{
					obj.put("msg","0");
					System.err.println("33333333333333333333333");
					JSONUtils.printObject(obj);
					return null;
				}
			Map<String, String> userAcMap = userService.searchActivity20160801ByUser(user.getId());
			String startTime = "2016-08-01 00:00:00";
			String andTime = "2016-08-31 23:59:59";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sdf.parse(user.getCreateTime());	
			Date d1 = sdf.parse(startTime);
			Date d2 = sdf.parse(andTime);	
			//判断是否活动范围内
			if (d1.getTime() <=d.getTime() &&d.getTime() <=d2.getTime()) {
					if (userAcMap!=null) {
						int totalNumber = Integer.parseInt(userAcMap.get("total_number"));
						int alreadyNumber = Integer.parseInt(userAcMap.get("already_number"));
						if (totalNumber - alreadyNumber == 0) {
							obj.put("msg", "1");
							JSONUtils.printObject(obj);
							return "index";
						} else {
							// 随机获取宝箱金额
							Map<String, String> map1 = userService.searchActivity20160801();
							// 修改使用次数
							userService.updateActivity20160801ByUser(user.getId(), totalNumber, alreadyNumber + 1);
							// 修改用户余额
							userService.updateUserUsableAmount(Double.parseDouble(map1.get("money_box")),
									Long.parseLong(user.getId().toString()));
							obj.put("msg",
									"恭喜获得" + map1.get("money_box") + "元现金,还有" + (totalNumber - alreadyNumber - 1) + "次寻金机会.");
							JSONUtils.printObject(obj);
							log.info("获取宝箱金额:" + map1.get("money_box"));
							JSONUtils.printObject(obj);
						}
					}else{
						obj.put("msg","0");
						System.err.println("4444444444444444444444444444");
						JSONUtils.printObject(obj);
						return null;
					}
			   }else{
					obj.put("msg","2");
					System.err.println("555555555555555555555555");
					JSONUtils.printObject(obj);
					return null;
				
			}
		}
	}
		  catch (Exception e) {
			e.printStackTrace();
			log.info("获取宝箱金额失败：" + e.getMessage());
		}

    	return null;
    }
	
	public String wxdCreateById() throws Exception {
		long id = Convert.strToLong(request().getParameter("id"), 0);
		Map<String,String> wxdCreateMap = newsService.getWxdCreateById(id);
		request().setAttribute("wxdCreateMap", wxdCreateMap);
		
		Map<String,String> WxdCreateByIdMapNext = newsService.getWxdCreateById(id-1);
		if(WxdCreateByIdMapNext==null){
			WxdCreateByIdMapNext = newsService.getWxdCreateById(id-2);
		}
		request().setAttribute("WxdCreateByIdMapNext", WxdCreateByIdMapNext);
		 
		Map<String,String> WxdCreateMapAgo = newsService.getWxdCreateById(id+1);
		if(WxdCreateMapAgo==null){
			WxdCreateMapAgo = newsService.getLicaiById(id+2);
		}
		request().setAttribute("WxdCreateMapAgo", WxdCreateMapAgo);
		return "success";
		
	}
	
	
	public String infomations() throws Exception {
		try {
			pageBean.setPageSize(3);
			pageBean.setPageNum(request().getParameter("curPage"));
			newsService.queryInfomationPage(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("type", request().getParameter("type"));
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	public String licais() throws Exception {
		try {
			pageBean.setPageSize(3);
			pageBean.setPageNum(request().getParameter("curPage"));
			newsService.queryLicaiPage(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("type", request().getParameter("type"));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	public String wxdCreates() throws Exception {
		try {
			pageBean.setPageSize(3);
			pageBean.setPageNum(request().getParameter("curPage"));
			newsService.wxdCreates(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("type", request().getParameter("type"));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	public String newPerson() throws Exception{
		return "success";
	}
	
	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public NewsAndMediaReportService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsAndMediaReportService newsService) {
		this.newsService = newsService;
	}

	

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public List<Map<String, Object>> getBorrowPurposeList() throws Exception {
		 borrowPurposeList = selectedService
			.borrowPurpose();

		return borrowPurposeList;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws Exception {
		  borrowDeadlineList = selectedService
			.borrowDeadline();

		return borrowDeadlineList;
	}

	public List<Map<String, Object>> getBorrowAmountList() throws Exception {
		 borrowAmountList = selectedService.borrowAmountRange();
		return borrowAmountList;
	}

	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	public void setInvestDetailMap(Map<String, String> investDetailMap) {
		this.investDetailMap = investDetailMap;
	}


	public void setBorrowPurposeList(List<Map<String, Object>> borrowPurposeList) {
		this.borrowPurposeList = borrowPurposeList;
	}

	public void setBorrowDeadlineList(List<Map<String, Object>> borrowDeadlineList) {
		this.borrowDeadlineList = borrowDeadlineList;
	}

	public void setBorrowAmountList(List<Map<String, Object>> borrowAmountList) {
		this.borrowAmountList = borrowAmountList;
	}

//	public void setLinksService(LinksService linksService) {
//		this.linksService = linksService;
//	}
//	public LinksService getLinksService() {
//		return linksService;
//	}

	

	public List<Map<String, Object>> getLinksList() {
		return linksList;
	}

	public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}

	public void setLinksList(List<Map<String, Object>> linksList) {
		this.linksList = linksList;
	}

//	public void setMediaReportService(MediaReportService mediaReportService) {
//		this.mediaReportService = mediaReportService;
//	}

	public List<Map<String, Object>> getMeikuList() {
		return meikuList;
	}

	public void setMeikuList(List<Map<String, Object>> meikuList) {
		this.meikuList = meikuList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Map<String, Object>> getMeikuStick() {
		return meikuStick;
	}

	public void setMeikuStick(List<Map<String, Object>> meikuStick) {
		this.meikuStick = meikuStick;
	}

	public List<Map<String, Object>> getListsGGList() {
		return listsGGList;
	}

	public void setListsGGList(List<Map<String, Object>> listsGGList) {
		this.listsGGList = listsGGList;
	}

	public List<Map<String, Object>> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<Map<String, Object>> bannerList) {
		this.bannerList = bannerList;
	}

	public BonusService getBonusService() {
		return bonusService;
	}

	public void setBonusService(BonusService bonusService) {
		this.bonusService = bonusService;
	}
    
	
	
}
