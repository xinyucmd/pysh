package com.sp2p.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.SMSUtil;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.BonusDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.FrontMyPaymentDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.RepayMentDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.BorrowManageDao;
import com.sp2p.dao.admin.StatisManageDao;
import com.sp2p.database.Dao.Functions;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SendMessageService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.service.admin.StatisManageService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;
import com.sp2p.util.WebUtil;

/**
 * @ClassName: JobTaskService.java
 * @Author: gang.lv
 * @Date: 2013-4-11 上午11:14:41
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 定时任务处理服务
 */
public class JobTaskService extends BaseService {
	public static Log log = LogFactory.getLog(JobTaskService.class);

	private JobTaskDao jobTaskDao;
	private SelectedService selectedService;
	private UserService userService;
	private UserIntegralService userIntegralService;
	private FinanceDao financeDao;
	private AccountUsersDao   accountUsersDao;
	private BorrowManageDao  borrowManageDao;
	private InvestDao investDao;
	private BorrowDao  borrowDao;
	private RepayMentDao repayMentDao;
	private FrontMyPaymentDao frontpayDao;
	private FundRecordDao fundRecordDao;
	private UserDao userDao;
	private AssignmentDebtService assignmentDebtService;
	private StatisManageDao statisManageDao;
	private BonusDao bonusDao;
	private StatisManageService statisManageService;
	private SMSInterfaceService sMSInterfaceService;
	private SendMessageService sendMessageService;

	
	public SendMessageService getSendMessageService() {
		return sendMessageService;
	}

	public void setSendMessageService(SendMessageService sendMessageService) {
		this.sendMessageService = sendMessageService;
	}

	public SMSInterfaceService getsMSInterfaceService() {
		return sMSInterfaceService;
	}

	public void setsMSInterfaceService(SMSInterfaceService sMSInterfaceService) {
		this.sMSInterfaceService = sMSInterfaceService;
	}

	public StatisManageService getStatisManageService() {
		return statisManageService;
	}

	public void setStatisManageService(StatisManageService statisManageService) {
		this.statisManageService = statisManageService;
	}

	public BonusDao getBonusDao() {
		return bonusDao;
	}

	public void setBonusDao(BonusDao bonusDao) {
		this.bonusDao = bonusDao;
	}

	public StatisManageDao getStatisManageDao() {
		return statisManageDao;
	}

	public void setStatisManageDao(StatisManageDao statisManageDao) {
		this.statisManageDao = statisManageDao;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public BorrowManageDao getBorrowManageDao() {
		return borrowManageDao;
	}

	public InvestDao getInvestDao() {
		return investDao;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public RepayMentDao getRepayMentDao() {
		return repayMentDao;
	}

	public FrontMyPaymentDao getFrontpayDao() {
		return frontpayDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

	public void setRepayMentDao(RepayMentDao repayMentDao) {
		this.repayMentDao = repayMentDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAssignmentDebtService(AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setFrontpayDao(FrontMyPaymentDao frontpayDao) {
		this.frontpayDao = frontpayDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}
	
	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public void setBorrowManageDao(BorrowManageDao borrowManageDao) {
		this.borrowManageDao = borrowManageDao;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public JobTaskDao getJobTaskDao() {
		return jobTaskDao;
	}

	public void setJobTaskDao(JobTaskDao jobTaskDao) {
		this.jobTaskDao = jobTaskDao;
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserIntegralService getUserIntegralService() {
		return userIntegralService;
	}

	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	}

	/**
	 * 处理过期体检卡
	 * @author whb
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public void expiredExamination() throws Exception{
		Connection  conn = MySQL.getConnection();
		List<Map<String, Object>> expiredList = null;//过期体检卡
		
		try {
			expiredList = statisManageService.queryExpiredExamination();
			if(null != expiredList && !expiredList.isEmpty()){
				for(Map<String, Object> map:expiredList){
					statisManageService.updateActivtyAnyState(Long.parseLong(String.valueOf((map.get("id")))), String.valueOf(map.get("end_time")), 
							Long.parseLong(String.valueOf(map.get("user_id"))),1);
				}
			}
		} finally{
			conn.close();
		}
	}
	
	/**
	 * 发送体检卡
	 * @author whb
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public void sendExamination() throws Exception{
		Connection  conn = MySQL.getConnection();
		List<Map<String, Object>> reissueList = null;//补发之前未发送的用户
		List<Map<String, Object>> list = null;//待发送体检的用户
		Map<String, String> unsendMap = null;//未发送的体检卡
		long result = -1L;
		int status = -1;
		String contentBef = "您的九华体检套餐兑换码是：";//短信内容前缀
		String contentAft = "，兑换预约咨询：400—001—6007";//短信内容后缀
		
		try {
			/****************补          发 *****************/
			reissueList = statisManageService.queryReissueUser();
			if(null != reissueList && !reissueList.isEmpty()){
				for(int i=0;i<reissueList.size();i++){
					//根据面值查询未发送的体检卡
					unsendMap = statisManageService.unsendExamination(Double.parseDouble(String.valueOf(reissueList.get(i).get("amount"))));
					if(null != unsendMap && !unsendMap.isEmpty()){
						//发送短信
						result = sMSInterfaceService.sendSMSByConditions(Long.parseLong(String.valueOf(reissueList.get(i).get("user_id"))), contentBef + unsendMap.get("check_code") + contentAft);
						if(1 == result){
							status = 1;
						}else {
							status = 2;
						}
						sendMessageService.mailSend("体检卡发送", "尊敬的用户您好，您的体检卡号是"+unsendMap.get("check_code"), Long.parseLong(String.valueOf(reissueList.get(i).get("user_id"))));
						statisManageService.updateExamination(Long.parseLong(String.valueOf(reissueList.get(i).get("id"))), Integer.parseInt(unsendMap.get("id")), status, "已补发");
						statisManageService.updateActivtyAnyState(Long.parseLong(unsendMap.get("id")), DateUtil.getCurrDateLateDay(Integer.parseInt(unsendMap.get("day"))), Long.parseLong(String.valueOf(reissueList.get(i).get("user_id"))),0);
					}else {
						log.info("还是没有此面值体检卡，需补发");
					}
				}
			}
			/**************** 正常发送 *****************/
			list = statisManageService.queryTobeSendUser();
			if(null != list && !list.isEmpty()){
				for(int i=0;i<list.size();i++){
					//根据面值查询未发送的体检卡
					unsendMap = statisManageService.unsendExamination(Double.parseDouble(String.valueOf(list.get(i).get("amount"))));
					if(null != unsendMap && !unsendMap.isEmpty()){
						//发送短信
						result = sMSInterfaceService.sendSMSByConditions(Long.parseLong(String.valueOf(list.get(i).get("investor"))), contentBef + unsendMap.get("check_code") + contentAft);
						if(1 == result){
							status = 1;
						}else {
							status = 2;
						}
						sendMessageService.mailSend("体检卡发送", "尊敬的用户您好，您的体检卡号是"+unsendMap.get("check_code"), Long.parseLong(String.valueOf(list.get(i).get("investor"))));
						statisManageService.addExamination(Long.parseLong(String.valueOf(list.get(i).get("investor"))), Double.parseDouble(String.valueOf(list.get(i).get("amount"))), Integer.parseInt(unsendMap.get("id")), status, "");
						statisManageService.updateActivtyAnyState(Long.parseLong(unsendMap.get("id")), DateUtil.getCurrDateLateDay(Integer.parseInt(unsendMap.get("day"))), Long.parseLong(String.valueOf(list.get(i).get("investor"))),0);
					}else {
						statisManageService.addExamination(Long.parseLong(String.valueOf(list.get(i).get("investor"))), Double.parseDouble(String.valueOf(list.get(i).get("amount"))), -1, 3, "没有此面值体检卡，需补发");
					}
				}
			}
			
		}  catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 撤销债权转让whb
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public void cancelDebt() throws Exception{
		Connection  conn = MySQL.getConnection();
		List<Map<String, Object>> debtEndList = null;
		List<Map<String, Object>> debtList = null;
		Map<String, String> lateMap = null;
		Map<String, String> repayMap = null;
		
		//到达转让截止日期时
		try {
			debtEndList = assignmentDebtService.getDebtStatus(1);
			if(debtEndList != null && debtEndList.size() > 0){
				for(Map<String, Object> map: debtEndList){
					assignmentDebtService.auctingDebtSuccess(Long.parseLong(String.valueOf(map.get("id"))), 1, 2, "");
				}
			}
			//查询转让未过期的债权
			debtList = assignmentDebtService.getDebtStatus(2);
			if(debtList != null && debtList.size() > 0){
				for(Map<String, Object> map: debtList){
					//借款产生逾期时
					lateMap = assignmentDebtService.getDebtInvest(Long.parseLong(String.valueOf(map.get("borrowId"))), 
							Long.parseLong(String.valueOf(map.get("investId"))), 1);
					if(lateMap != null && lateMap.size() > 0 && Integer.parseInt(String.valueOf(lateMap.get("count"))) > 0){
						assignmentDebtService.auctingDebtSuccess(Long.parseLong(String.valueOf(map.get("id"))), 1, 2, "");
					}else if(lateMap == null || lateMap.size() == 0 || Integer.parseInt(String.valueOf(lateMap.get("count"))) == 0){
						//借款人提前全部结清时
						repayMap = assignmentDebtService.getDebtInvest(Long.parseLong(String.valueOf(map.get("borrowId"))), 
								Long.parseLong(String.valueOf(map.get("investId"))), 2);
						if(repayMap != null && repayMap.size() > 0 && Integer.parseInt(String.valueOf(repayMap.get("count"))) == 0){
							assignmentDebtService.auctingDebtSuccess(Long.parseLong(String.valueOf(map.get("id"))), 1, 2, "");
						}
					}
				}
			}
		}  catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * VIP会费处理
	 * @throws Exception 
	 */
	public void autoDeductedVIPFee() throws Exception{
		Connection  conn = MySQL.getConnection();
		DataSet  ds = new DataSet();
		List<Object>  outParameterValues = new ArrayList<Object>();
		List<Map<String, Object>> VipUserList = new ArrayList<Map<String, Object>>();
		try { 
			Procedures.p_auto_pastvipfee(conn, ds, outParameterValues, 0, "");
			//查询出没有扣会员费的会员
			VipUserList = userDao.queryVipUser(conn);
			Procedures.p_auto_firstvip(conn, ds, outParameterValues, 0, "");
			if(VipUserList != null){
				for(Map<String, Object> map : VipUserList){
					long userId = Convert.strToLong(map.get("id")+"", -1);
					userService.updateSign(conn, userId);//更换校验码
					map = null;
				}
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		
		
	}
	/**
	 * add by houli 当用户首次成为vip并且有会费余额，则立即进行会费扣除（用户申请会员时引用）
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Long beToVip(Long userId,Map<String,Object> platformCostMap) throws SQLException{
		Connection conn = Database.getConnection();
		Map<String,String> noticeMap = new HashMap<String, String>();
		Map<String,String> map = null;
		Map<String,String> vipFeeMap = null;
		Map<String,String> userAmountMap = null;
		Map<String,String> userSumMap = null;
		String username = "";
		//模板
		Map<String,Object> informTemplateMap = (Map<String, Object>)
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		try {
			map = jobTaskDao.queryFirstVipById(conn,userId);
			if(map == null || map.size() <=0){//用户不是首次成为vip
				return -1L;
			}
	    	double vipFee = Convert.strToDouble(platformCostMap
    				.get(IAmountConstants.VIP_FEE_RATE)+ "", 0);
        	//如果代扣的VIP用户账户有钱就进行VIP会费扣除
        	userAmountMap = jobTaskDao.queryUserHasVipFee(conn,userId,vipFee);
        	if(userAmountMap != null && userAmountMap.size() > 0 && vipFee > 0){
        		username = userAmountMap.get("userAmountMap")+"";
        		//扣除VIP会费
        		jobTaskDao.deductedUserVipFee(conn,userId,vipFee); 
        		//添加VIP会费扣除记录
        		jobTaskDao.addVipFeeRecord(conn,userId,vipFee);
        		//查询投资后的账户金额
        		userSumMap = financeDao.queryUserAmountAfterHander(conn,userId);
				if(userSumMap == null){userSumMap = new HashMap<String,String>();}
				double usableSum = Convert.strToDouble(userSumMap.get("usableSum")+"", 0);
				double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum")+"", 0);
				double forPI = Convert.strToDouble(userSumMap.get("forPI")+"", 0);
				//添加资金流动记录
				fundRecordDao.addFundRecord(conn, userId,"VIP会员续费",vipFee,usableSum,freezeSum,forPI,-1,"扣除VIP会员费",0.0,vipFee,-1,-1,804,0.0);
				
				//站内信
				String informTemplate = informTemplateMap.get(IInformTemplateConstants.VIP_SUCCESS_XU)+"";
				informTemplate = informTemplate.replace("vipFee", vipFee+"");
				//短信
				String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_VIP_SUCCESS_XU)+"";
				s_informTemplate = s_informTemplate.replace("username", username);
				s_informTemplate = s_informTemplate.replace("vipFee", vipFee+"");
				//邮件
				String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_VIP_SUCCESS_XU)+"";
				e_informTemplate = e_informTemplate.replace("vipFee", vipFee+"");
				 noticeMap.put("mail",informTemplate);//站内信
				 noticeMap.put("note", s_informTemplate);//短信
				 noticeMap.put("email",e_informTemplate);//邮件
        		//发送通知
            	selectedService.sendNoticeMSG(conn, userId, "VIP会员成功续费", noticeMap, IConstants.NOTICE_MODE_5);
            	userService.updateSign(conn, userId);//更换校验码
        	}
        	conn.commit();
        	return 1L;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
		    conn = null;
			noticeMap = null;
			map = null;
			vipFeeMap = null;
			userAmountMap = null;
			userSumMap = null;
			System.gc();
		}
		return -1L;
	}

	/**
	 * @MethodName: pr_inviteFriendsReward
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午01:29:55
	 * @Return:
	 * @Descb: 好友奖励发放
	 * @Throws:
	 */
	public void inviteFriendsReward() throws SQLException, DataException {
	Connection conn = Database.getConnection();
		Map<String,String> userSumMap = null;
		Map<String, String> riskMap = null;
		Map<String,String> noticeMap = new HashMap<String, String>();
		List<Map<String,Object>> friendsRewardList = null;
		Map<String,String> fmap = null;
		long id = -1;
		//奖励金额
		double money = 0;
		//用户Id
		long userId = -1;
		String username = "";
		//模板
		Map<String,Object> informTemplateMap = (Map<String, Object>)
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		
		try {
			//清除异常数据(奖励的用户为小于1的用户)
			jobTaskDao.clearExceptionDate(conn);
			//处理好友奖励
			friendsRewardList = jobTaskDao.queryFriendsReward(conn);
			for(Map<String,Object> vipMap:friendsRewardList){
				id = Convert.strToLong(vipMap.get("id")+"", -1);
				money = Convert.strToLong(vipMap.get("money")+"", -1);
				userId = Convert.strToLong(vipMap.get("userId")+"", -1);
				username = vipMap.get("username")+"";
				
				//查询该条奖励对应的用户是否交了VIP会费
	         	  List<Map<String,Object>> map = jobTaskDao.queryFriendsvip2(conn,id);
	         	  //若交了会费推荐人进行奖励
	         	  if(map!=null&&map.size()>0){
	         		  
	         	// 查询风险保障金余额
         	   riskMap = jobTaskDao.queryRiskBalance(conn);
         	   if(riskMap == null){riskMap = new HashMap<String, String>();}
         	   double riskBalance = Convert.strToDouble(riskMap
						.get("riskBalance")+ "", 0);
         	   //扣除风险保障金
         	   jobTaskDao.spendingRiskAmount(conn, riskBalance, money, userId, -1, "好友邀请奖励");
         	   //更新已奖励状态为已奖励
         	   jobTaskDao.updateRewardStatus(conn,id,userId);
         	   //邀请奖励发给邀请人
         	   jobTaskDao.addUserAmount(conn,userId,money);
			   //查询投资后的账户金额
				userSumMap = financeDao.queryUserAmountAfterHander(conn,userId);
				if(userSumMap == null){userSumMap = new HashMap<String,String>();}
				double usableSum = Convert.strToDouble(userSumMap.get("usableSum")+"", 0);
				double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum")+"", 0);
				double forPI = Convert.strToDouble(userSumMap.get("forPI")+"", 0);
				//发送通知
				//--------------add by houli
				fmap = jobTaskDao.queryFriendInfo(conn,id,userId);
				String friendName = "";
				if(fmap != null){
					friendName = fmap.get("username");
				}
				//添加资金流动记录
				fundRecordDao.addFundRecord(conn, userId,"好友邀请奖励",money,usableSum,freezeSum,forPI,-1,"您邀请的用户<a href='userMeg.do?id="+userId+"' target='_blank'>【"+friendName+"】</a>已成为VIP会员,在此奖励￥"+money+"元,再接再厉!",money,0.0,-1,-1,251,0.0);
        		
				//------------------/
				//模板通知
				//站内信
				 String	informTemplate = informTemplateMap.get(IInformTemplateConstants.GOOD_INVITATION)+"";
				 informTemplate  = informTemplate.replace("friendName", friendName);
				 informTemplate  = informTemplate.replace("money", money+"");
				 //短信
				 String	s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_GOOD_INVITATION)+"";
				 s_informTemplate  = s_informTemplate.replace("friendName", friendName);
				 s_informTemplate  = s_informTemplate.replace("username", username);
				 s_informTemplate  = s_informTemplate.replace("money", money+"");
				 //邮件
				 String	e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_GOOD_INVITATION)+"";
				 e_informTemplate  = e_informTemplate.replace("friendName", friendName);
				 e_informTemplate  = e_informTemplate.replace("money", money+"");
				 noticeMap.put("mail",informTemplate); //站内信
			     noticeMap.put("email",e_informTemplate);//邮件
				 noticeMap.put("note", s_informTemplate);//短信
            	 selectedService.sendNoticeMSG(conn, userId, "好友邀请奖励", noticeMap, IConstants.NOTICE_MODE_5);
            	 
         		 }//回收对象
	         	 userService.updateSign(conn,userId);//更换校验码
				 vipMap = null;
         	  
			}
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		}  finally {
			conn.close();
		    conn = null;
			userSumMap = null;
			riskMap = null;
			noticeMap = null;
			friendsRewardList = null;
			fmap = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: updateOverDueRepayment
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午02:42:50
	 * @Return:
	 * @Descb: 更新逾期的还款
	 * @Throws:
	 */
	public void updateOverDueRepayment() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		DecimalFormat df= new DecimalFormat("#0.00");
		List<Map<String,Object>> overDueRepaymentList = null;
		String date = sf.format(new Date());
		long borrowId = -1L;
		long id = -1;
		//应还本金
		double stillPrincipal = 0;
		//应还利息
		double stillInterest = 0;
		// 逾期罚息
		double lateFee = 0;
		//执行天数
		int executeDay = 0;
		//逾期天数
		int overdueDay = 0;
		//借款方式
		int borrowWay = 0;
		//执行时间
		String executeTime ="";
		//-- 7 - 9
		//查询借款信息得到借款时插入的平台收费标准
		Map<String,String> map = new HashMap<String, String>();
		String feelog = "";
		Map<String,Double> feeMap = new HashMap<String, Double>();
		//得到收费标准的说明信息
		String feestate = "";
		Map<String,String> feestateMap = new HashMap<String, String>();
		//--end
		double overdueFeeRate = 0;
		try {
			overDueRepaymentList = jobTaskDao.queryOverDueRepayment(conn,date);
			for(Map<String,Object> overDueMap:overDueRepaymentList){
				id = Convert.strToLong(overDueMap.get("id")+"", -1);
				borrowId = Convert.strToLong(overDueMap.get("borrowId")+"", 0);
				stillPrincipal = Convert.strToDouble(overDueMap.get("stillPrincipal")+"", 0);
				stillInterest = Convert.strToDouble(overDueMap.get("stillInterest")+"", 0);
				executeTime = overDueMap.get("executeTime")+"";
				
				overdueDay = Convert.strToInt(overDueMap.get("overdueDay")+"", 0);
				map = borrowManageDao.queBorrowInfo(conn, borrowId);
				if(map!=null){
					//得到收费标准的json代码
					feelog = Convert.strToStr(map.get("feelog"), "");
					feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
			
					overdueFeeRate =Convert.strToDouble(  feeMap.get(IAmountConstants.OVERDUE_FEE_RATE)+"",0);
					borrowWay = Convert.strToInt(map.get("borrowWay"), 0);
				}else{
					overdueFeeRate = 0;
				}
				//不符合条件的情况，将逾期天数重置为0
				if(overdueDay < 0){
					overdueDay = 0;
				}
				if (borrowWay ==6) {//流转标不计算罚息
					overdueDay = 0;
				}
				// 计算罚息=本息
				lateFee = (stillPrincipal + stillInterest)*overdueDay* overdueFeeRate;
				lateFee = Double.valueOf(df.format(lateFee));
				// 更新逾期还款
				if(overdueDay >0){
					jobTaskDao.updateOverDueRepayment(conn, id, lateFee,overdueDay,date);
				}
				// 回收对象
				overDueMap = null;
				map = null;
				feeMap = null;
				feestateMap = null;
				//提交任务
				conn.commit();
			}
		}catch(Exception e){
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
		    sf = null;
			df= null;
			overDueRepaymentList = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: autoDeductedXLFee
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-12 下午05:05:24
	 * @Return:
	 * @Descb: 自动扣除学历认证费用
	 * @Throws:
	 */
	public void autoDeductedXLFee() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		Map<String,String> userSumMap = null;
		Map<String,String> noticeMap = new HashMap<String, String>();
		List<Map<String,Object>> xlFeeList = null;
		//扣费id
		long costId = -1;
		//用户Id
		long userId = -1;
		//学历认证费用
		double freeEducation = 0;
		//用户可用金额
		double useableAmount = 0;
		String username = "";
		Map<String,Object> informTemplateMap = (Map<String, Object>)
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		try {
			xlFeeList = jobTaskDao.queryDeductedXLFee(conn);
			for(Map<String,Object> xlFeeMap:xlFeeList){
				costId = Convert.strToLong(xlFeeMap.get("id")+"", -1);
				userId = Convert.strToLong(xlFeeMap.get("userId")+"", 0);
				freeEducation = Convert.strToDouble(xlFeeMap.get("freeEducation")+"", 0);
				username = xlFeeMap.get("username")+"";
				useableAmount = Convert.strToDouble(xlFeeMap.get("usableSum")+"", 0);
				//如果扣费费用和用户有钱就进行扣费处理
				if(freeEducation > 0 && useableAmount > freeEducation){
			        //更新费用扣除状态
				    jobTaskDao.updateXLFeeStatus(conn,costId);	
				    //扣除学历认证费用				
				    jobTaskDao.deductedXLFee(conn,userId,freeEducation);
				    //查询投资后的账户金额
				    userSumMap = financeDao.queryUserAmountAfterHander(conn,userId);
				    if(userSumMap == null){userSumMap = new HashMap<String,String>();}
				    double usableSum = Convert.strToDouble(userSumMap.get("usableSum")+"", 0);
				    double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum")+"", 0);
				    double forPI = Convert.strToDouble(userSumMap.get("forPI")+"", 0);
				    //添加资金流动记录
				    fundRecordDao.addFundRecord(conn, userId,"学历认证费",freeEducation,usableSum,freezeSum,forPI,-1,"管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation+"元",0.0,freeEducation,-1,-1,802,0.0);
				    
				    //消息模版  学历认证成功 
				    //站内信
				    String	informTemplate = informTemplateMap.get(IInformTemplateConstants.APPROVE_EDU_SUCCESS)+"";
					 informTemplate  = informTemplate.replace("freeEducation", freeEducation+"");
					 //短信
					 String	s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_APPROVE_EDU_SUCCESS)+"";
					 s_informTemplate  = s_informTemplate.replace("username", username);
					 s_informTemplate  = s_informTemplate.replace("freeEducation", freeEducation+"");
					//邮件
					 String	e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_APPROVE_EDU_SUCCESS)+"";
					 e_informTemplate  = e_informTemplate.replace("freeEducation", freeEducation+"");
					 //站内信
					 noticeMap.put("mail",informTemplate);
					 //邮件
					 noticeMap.put("email",e_informTemplate);
					 //短信
					 noticeMap.put("note",s_informTemplate);
					/* //站内信
				    noticeMap.put("mail", "管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation+"元");
				    //邮件
				    noticeMap.put("email","管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation+"元");
				    //短信
				    noticeMap.put("note",  "尊敬的"+username+":\n    管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation+"元");
*/
				    //发送通知
				    selectedService.sendNoticeMSG(conn, userId, "学历认证费", noticeMap, IConstants.NOTICE_MODE_5);
				    userService.updateSign(conn, userId);//更换校验码
				    //回收对象
				    xlFeeMap = null;
				}
			}
			
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			userSumMap = null;
			noticeMap = null;
			xlFeeList = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: autoBid
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-14 下午11:50:51
	 * @Return:
	 * @Descb: 自动投标
	 * @Throws:
	 */
	public void autoBid() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		List<Map<String, Object>> biderList = jobTaskDao.queryAllBider(conn);
		List<Map<String, Object>> userList = null;
		Map<String, String> userParam = null;
		Map<String, String> bider = null;
		Map<String, String> userAmount = null;
		Map<String, String> borrowOwer = null;
		List<Map<String, Object>> userBiderList = null;
		Map<String,String> borrowInfoMap = null;
		Map<String,String> userAmountMap = null;
		Map<String,String> Usermap = null;
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		Map<String,String> noticeMap = new HashMap<String, String>();
		Map<String,String> hasInvestMap = null;
		Map<String, String> borrowMap = null;
		// 利率开始
		double rateStart = 0;
		// 利率结束
		double rateEnd = 0;
		// 借款期限开始
		int deadLineStart = 0;
		// 借款期限结束
		int deadLineEnd = 0;
		// 信用等级开始
		int creditStart = 0;
		// 信用等级结束
		int creditEnd = 0;
		// 用户Id
		long userId = -1;
		// 借款id
		int borrowId = -1;
		// 投标金额
		double bidAmount = 0;
		// 保留金额
		double remandAmount = 0;
		// 借款金额
		double borrowAmount = 0;
		// 已投资金额
		double hasAmount = 0;
		// 可用金额
		double usableAmount = 0;
		//借款方式数组字符串
		String borrowWay = "";
		//借款方式
		String way= "";
		//是否为天标
		int isDayThe = 1;
		//月利率
		double monthRate =0;
		//发布者
		long publisher = -1;
		//借款期限
		int deadline = 0;
		//借款标题
		String borrowTitle = "";
		int score = 0;
		Integer preScore = null;
		long returnId = -1;
		String username = "";
		String basePath = WebUtil.getWebPath();
		try {
			// 遍历所有的符合条件进度低于95%的招标中的借款
			for (Map<String, Object> biderMap : biderList) {
				borrowId = Convert.strToInt(biderMap.get("id") + "", -1);
				borrowAmount = Convert.strToDouble(biderMap.get("borrowAmount")+ "", 0);
				hasAmount = Convert.strToDouble(biderMap.get("hasInvestAmount")+ "", 0);
				way = biderMap.get("borrowWay")+"";
				isDayThe = Convert.strToInt(biderMap.get("isDayThe") + "", -1);
				// 查询符合自动投标的用户
				userList = jobTaskDao.queryAutoBidUser(conn);
				// 所有符合条件的用户排队对每条借款进行自动投标
				for (Map<String, Object> userMap : userList) {
					userId = Convert.strToInt(userMap.get("userId") + "", -1);
					// 如果该借款是发布者的标,则发布者不能投标,用户自动排队到后面
					borrowOwer = jobTaskDao.queryBorrowOwer(conn, borrowId,userId);
					if (borrowOwer != null) {
						// 是发布者的标,退出本次投标,用户自动排队到后面
						jobTaskDao.updateUserAutoBidTime(conn, userId);
					} else {
						userParam = jobTaskDao.queryUserBidParam(conn, userId);
						// 当用户设置了自动投标参数
						if (userParam != null) {
							userBiderList = jobTaskDao.queryUserBider(conn,borrowId, userId);
							// 用户已经投标的标的不能再自动投标
							if (userBiderList != null && userBiderList.size() > 0) {
								// 已经投标,用户自动排队到后面
								jobTaskDao.updateUserAutoBidTime(conn, userId);
							} else {
								rateStart = Convert.strToDouble(userParam.get("rateStart")+ "", 0);
								rateEnd = Convert.strToDouble(userParam.get("rateEnd")+ "", 0);
								deadLineStart = Convert.strToInt(userParam.get("deadlineStart")+ "", 0);
								deadLineEnd = Convert.strToInt(userParam.get("deadlineEnd")+ "", 0);
								creditStart = Convert.strToInt(userParam.get("creditStart")+ "", 0);
								creditEnd = Convert.strToInt(userParam.get("creditEnd")+ "", 0);
								bidAmount = Convert.strToDouble(userParam.get("bidAmount")+ "", 0);
								remandAmount = Convert.strToDouble(userParam.get("remandAmount")+ "", 0);
								borrowWay = userParam.get("borrowWay");
								//用户设置的借款类型
								if(borrowWay.contains(way)){
									// 根据用户投标参数获取投标的标的
									bider = jobTaskDao.queryBiderByParam(conn,
											rateStart, rateEnd, deadLineStart,
											deadLineEnd, creditStart, creditEnd,
											borrowId,isDayThe);
									// 找到了符合自动投标的标的
									if (bider != null) {
										// 计算投标金额
										bidAmount = calculateBidAmount(bidAmount,borrowAmount, hasAmount);
										if (bidAmount > 0) {
											// 查询用户可用余额是否足够
											userAmount = jobTaskDao.queryUserAmount(conn,remandAmount, userId);
											if (userAmount != null) {
												// 获取用户减掉预留金额后的可用金额
												usableAmount = Convert.strToDouble(userAmount.get("usableSum")+ "", 0);
												if (usableAmount >= bidAmount&& usableAmount > 0) {
													// 查询借款的状态,借款未达到95%且处于招标中
													borrowMap = jobTaskDao.queryBorrow(conn,borrowId);
													if (borrowMap != null) {
														borrowAmount = Convert.strToDouble(borrowMap.get("borrowAmount")+ "",0);
														hasAmount = Convert.strToDouble(borrowMap.get("hasInvestAmount")+ "",0);
														bidAmount = calculateBidAmount(bidAmount,borrowAmount,hasAmount);
														// 满足投标条件,进行扣费处理
														//#查询借款的基础信息
														borrowInfoMap = jobTaskDao.queryBorrowInfo(conn,borrowId);
														if(borrowInfoMap !=null){
															monthRate = Convert.strToDouble(borrowInfoMap.get("monthRate")+"", 0);
															deadline = Convert.strToInt(borrowInfoMap.get("deadline")+"", 0);
															borrowTitle = borrowInfoMap.get("borrowTitle")+"";
															publisher = Convert.strToLong(borrowInfoMap.get("publisher")+"", 0);
															int version = Convert.strToInt(borrowInfoMap.get("version") + "",0);
//															notice.append("您于["+sf.format(new Date())+"]自动投标了借款[["+borrowTitle+"]],投资金额为：￥"+bidAmount);
//															   notice.append("元");
															Map<String,Object> informTemplateMap = (Map<String, Object>)
															ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
												            //站内信
															String informTemplate = informTemplateMap.get(IInformTemplateConstants.TENDER)+"";
															informTemplate = informTemplate.replace("title", borrowTitle+"");
															informTemplate = informTemplate.replace("[voluntarily]", "自动");
															informTemplate = informTemplate.replace("date",sf.format(new Date()));
															informTemplate = informTemplate.replace("investAmount", bidAmount+"");
															//短信
															String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_TENDER)+"";
															s_informTemplate = s_informTemplate.replace("username", username);
															s_informTemplate = s_informTemplate.replace("title", borrowTitle+"");
															s_informTemplate = s_informTemplate.replace("[voluntarily]", "自动");
															s_informTemplate = s_informTemplate.replace("date",sf.format(new Date()));
															s_informTemplate = s_informTemplate.replace("investAmount", bidAmount+"");
															//邮件
															String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_TENDER)+"";
															e_informTemplate = e_informTemplate.replace("title", borrowTitle+"");
															e_informTemplate = e_informTemplate.replace("[voluntarily]", "自动");
															e_informTemplate = e_informTemplate.replace("date",sf.format(new Date()));
															e_informTemplate = e_informTemplate.replace("investAmount", bidAmount+"");
															
															 noticeMap.put("mail", informTemplate);//站内信
															 noticeMap.put("email",e_informTemplate);//邮件
															 noticeMap.put("note", s_informTemplate);//短信
															//消息模版voluntarily
													         /*  //站内信
													           noticeMap.put("mail", "["+sf.format(new Date())+"] 您自动投标了借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+" target=_blank>"+borrowTitle+"</a>],冻结投标金额：￥"+bidAmount+"元");
													           //邮件
													           noticeMap.put("email", "["+sf.format(new Date())+"] 您自动投标了借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>],冻结投标金额：￥"+bidAmount+"元");
													           //短信
													           noticeMap.put("note",  "尊敬的"+username+":\n    ["+sf.format(new Date())+"] 您自动投标了借款["+borrowTitle+"],冻结投标金额：￥"+bidAmount+"元");
*/
													           // 更新借款信息中的已投资总额和数量
																returnId = financeDao.updateBorrowStatus(conn,
																		bidAmount,0, borrowId,version);
																if(returnId > 0){
													               //查询已投资总额是否小于等于借款总额，否则是无效投标
															       hasInvestMap = financeDao.queryHasInvest(conn,borrowId);
															       if(hasInvestMap !=null && hasInvestMap.size() > 0){
																      if(deadline!=0){
																	     score = Integer.parseInt(new java.text.DecimalFormat("#0").format((bidAmount/100)*deadline));//小数部分四舍五入
																      }
																      Usermap = userService.queryUserById(conn,userId);
																      preScore = Convert.strToInt(Usermap.get("rating"),-1);//查找用户的之前的信用积分
																      if(preScore!=-1&&deadline!=0&&score!=0){
																	     //添加投标所得积分
																	     userIntegralService.UpdateFinnceRating(conn,userId, score, preScore);
																      }
																      //添加投资记录
																      returnId = financeDao.addInvest(conn,bidAmount,bidAmount,monthRate,userId,userId,borrowId,deadline,2);
																      //投资人投资成功资金冻结
																      returnId = financeDao.freezeUserAmount(conn,bidAmount,userId);
																      //投标状态已经达到满标条件,更新为满标
																      returnId = financeDao.updateFullBorrow(conn, borrowId);
																      //添加用户动态
																      String cotent = "自动投标了借款<a href='"+basePath+"/financeDetail.do?id="+borrowId+"' target='_blank'>"
																           +borrowTitle+"</a>";
																      returnId = financeDao.addUserDynamic(conn,userId,cotent);
																      //查询投资后的账户金额
																      userAmountMap = financeDao.queryUserAmountAfterHander(conn,userId);
																      if(userAmountMap == null){userAmountMap = new HashMap<String,String>();}
																      double usableSum = Convert.strToDouble(userAmountMap.get("usableSum")+"", 0);
																      double freezeSum = Convert.strToDouble(userAmountMap.get("freezeSum")+"", 0);
																      double forPI = Convert.strToDouble(userAmountMap.get("forPI")+"", 0);
																      //添加资金流动记录
																      returnId = fundRecordDao.addFundRecord(conn, userId,"投标金额冻结",bidAmount,usableSum,freezeSum,forPI,publisher,"投标"+"<a href='"+basePath+"/financeDetail.do?id="+borrowId+"' target='_blank'>",
																    		  0.0,bidAmount,borrowId,-1,654,0.0);
																       
																        //更新用户自动投标的标的记录
																      returnId = financeDao.addAutoBidRecord(conn,userId,borrowId);
																      if(returnId > 0){
																	     //发送通知
																		selectedService.sendNoticeMSG(conn,userId,"理财投资报告",noticeMap,IConstants.NOTICE_MODE_5);
																      }
															     }else{
																   returnId = -1;
															     }
															}else{
																returnId = -1;
															}
														}
														// 投标完成,用户自动排队到后面
														jobTaskDao.updateUserAutoBidTime(conn,userId);
													} else {
														// 借款状态已改变,投标失败,用户自动排队到后面
														jobTaskDao.updateUserAutoBidTime(
																		conn,
																		userId);
													}
												} else {
													// 用户的可用余额不够投标，投标失败,用户自动排队到后面
													jobTaskDao.updateUserAutoBidTime(
																	conn, userId);
												}
											} else {
												// 用户的可用余额不足，投标失败,用户自动排队到后面
												jobTaskDao.updateUserAutoBidTime(
														conn, userId);
											}
										} else {
											// 投标金额为0,投标失败,用户自动排队到后面
											jobTaskDao.updateUserAutoBidTime(conn,
													userId);
										}
									} else {
										// 没有找到符合用的标的,用户自动排队到后面
										jobTaskDao.updateUserAutoBidTime(conn,
												userId);
									}
								}else{
									// 不符合设置的借款类型,用户自动排队到后面
									jobTaskDao.updateUserAutoBidTime(
													conn,
													userId);
								}
								
							}
						}
					}
					//回收对象
					userMap = null;
					if (returnId < 0) {
						conn.rollback();
					}
					userService.updateSign(conn, userId);//更换校验码
				}
				//回收对象
				biderMap = null;
			}
			if (returnId < 0) {
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			biderList = null;
			userList = null;
			userParam = null;
			bider = null;
			userAmount = null;
			borrowOwer = null;
			userBiderList = null;
			borrowInfoMap = null;
			userAmountMap = null;
			Usermap = null;
			sf = null;
			noticeMap = null;
			hasInvestMap = null;
			borrowMap = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: calculateBidAmount
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-15 上午10:34:39
	 * @Return:
	 * @Descb: 计算最后投标金额,(扣除保留金额)
	 * @Throws:
	 */
	private double calculateBidAmount(double bidAmount, double borrowAmount,
			double hasAmount) {
		double maxBidAmount = borrowAmount * 0.2;
		double schedule = hasAmount / borrowAmount;
		double invesAmount = 0;
		if (schedule < 0.9500) {
			while (bidAmount > maxBidAmount) {
				bidAmount = bidAmount - 50;
			}

			do {
				invesAmount = hasAmount + bidAmount;
				schedule = invesAmount / borrowAmount;
				if (schedule > 0.9500) {
					bidAmount = bidAmount - 50;
				}
			} while (schedule > 0.9500);
		}
		return bidAmount;
	}
	/**   
	 * @MethodName: updateOverDueInvestRepayment  
	 * @Param: JobTaskService  
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午10:25:20
	 * @Return:    
	 * @Descb: 更新逾期投资还款记录
	 * @Throws:
	*/
	public void updateOverDueInvestRepayment() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		DecimalFormat df= new DecimalFormat("#0.00");
		List<Map<String,Object>> overDueRepaymentList = null;
		String date = sf.format(UtilDate.getYesterDay());
		long id = -1;
		//应还本金
		double stillPrincipal = 0;
		//应还利息
		double stillInterest = 0;
		//逾期罚息
		double lateFee = 0;
		long repayId = 0;
		int lateDay= 0;
		int isLate= 0 ;
		int borrowWay= 0;
		//-- 7 - 9
		//查询借款信息得到借款时插入的平台收费标准
		Map<String,String> map = null;
		String feelog = "";
		Map<String,Double> feeMap = null;
		//得到收费标准的说明信息
		String feestate = "";
		Map<String,String> feestateMap = null;
		//--end
		double overdueFeeRate =0;
		try {
			overDueRepaymentList = jobTaskDao.queryOverDueInvestRepayment(conn,date);
			for(Map<String,Object> overDueMap:overDueRepaymentList){
				long borrowId = Convert.strToLong(overDueMap.get("borrowId")+"", 0);
				map = borrowManageDao.queryBorrowInfo(conn, borrowId);
			 	//得到收费标准的json代码
				feelog = Convert.strToStr(map.get("feelog"), "");
				feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
				feestate = Convert.strToStr(map.get("feestate"), "");
				feestateMap = (Map<String,String>)JSONObject.toBean(JSONObject.fromObject(feestate),HashMap.class);
				overdueFeeRate = Convert.strToDouble( feeMap.get(IAmountConstants.OVERDUE_FEE_RATE)+"",0);
					
			    //计算罚息
				id = Convert.strToLong(overDueMap.get("id")+"", -1);
				repayId = Convert.strToLong(overDueMap.get("repayId")+"", -1);
				lateDay = Convert.strToInt(overDueMap.get("lateDay")+"", 0);
				isLate = Convert.strToInt(overDueMap.get("isLate")+"", 1);
				stillPrincipal = Convert.strToDouble(overDueMap.get("recivedPrincipal")+"", 0);
				stillInterest = Convert.strToDouble(overDueMap.get("recivedInterest")+"", 0);
				lateFee = (stillPrincipal+stillInterest)*overdueFeeRate*lateDay*0.5;
			    lateFee = Double.valueOf(df.format(lateFee));
			    borrowWay = Convert.strToInt(overDueMap.get("borrowWay")+"", 0);
			    //更新逾期还款
			    // 6 为流转标 不处理
			    if ( borrowWay !=6) { 
			    	jobTaskDao.updateOverDueInvestRepayment(conn,id,repayId,lateFee,lateDay,isLate);
				}
			    //回收对象
			    overDueMap = null;
				map = null;
				feeMap = null;
				feestateMap = null;
			}
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
		    sf = null;
			df= null;
			overDueRepaymentList = null;
			System.gc();
		}
	}
	
	/**
	 * 待发布借款状态更新
	 * updateBorrowStatus
	 * @throws SQLException
	 * @throws DataException
	 * @autthor linww
	 * 2014-6-10 下午05:08:06
	 */
	public void WaitPublishBorrow() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		List<Map<String,Object>> waitPublishBorrowList = null;
		
		try {
			Date nowTime = new Date();
			waitPublishBorrowList = jobTaskDao.queryWaitPublishBorrow(conn,nowTime);
			for(Map<String,Object> map:waitPublishBorrowList){
				long borrowId = Convert.strToLong(map.get("id")+"", 0);
			    jobTaskDao.updateBorrowStatus(conn,borrowId);
			}
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
		    sf = null;
		    waitPublishBorrowList = null;
			System.gc();
		}
	}
	
	/**   
	 * @throws SQLException 
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: AutomaticPayment  
	 * @Param: JobTaskService  
	 * @Author: gang.lv
	 * @Date: 2013-5-21 下午11:36:05
	 * @Return:    
	 * @Descb: 自动还款
	 * @Throws:
	*/
	@SuppressWarnings("unchecked")
	public void automaticPayment() throws SQLException{
		double investFeeRate = 0;
		//处理流转标状态为认购中或回购中的借款
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> circulationList = null;
		List<Map<String,Object>> investList=null;
		Map<String,String> noticeMap = new HashMap<String, String>();
		DecimalFormat df = new DecimalFormat("#0.00");
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		Map<String,String> userSumMap = null;
		//借款人
		long borrower = -1;
		//投资人
		long investor = -1;
		//借款id
		long borrowId = -1;
		//投资id
		long investId = -1;
		//返回值
		long returnId = -1;
		//应收本金
		double recivedPrincipal =0;
		//应收利息
		double recievedInterest = 0;
		//应收本息
		double recivedPI = 0;
		//借款标题
		String borrowTitle = "";
		//实得还款金额
		double hasSum = 0;
		//投资管理费
		double mFee = 0;
		//投资人名称
		String username = "";
		//回购期数
		int hasRepoNumber = 0;
		//最小流转单位
		double smallestFlowUnit =0;
		//实际投资金额
		double realAmount = 0;
		int repayId = -1;
		long oriInvestor =  -1;  //原始投资人
		String basePath = WebUtil.getWebPath();
		//查询借款信息得到借款时插入的平台收费标准
		String feelog = "";
		Map<String,Double> feeMap = null;
		Map<String,String> flowMap = null;
		//模板
		Map<String,Object> informTemplateMap = (Map<String, Object>)
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		try{
			//查询状态为认购中或回购中的借款或者借款人还完的借款
			circulationList = borrowDao.queryCirculationBorrow(conn);
			for(Map<String,Object> circulationMap:circulationList){
								borrowId = Convert.strToLong(circulationMap.get("id")+"", -1);
								borrowTitle = circulationMap.get("borrowTitle")+"";
								borrower = Convert.strToLong(circulationMap.get("publisher")+"", -1);
								smallestFlowUnit = Convert.strToDouble(circulationMap.get("smallestFlowUnit")+"", 0);
								repayId = Convert.strToInt(circulationMap.get("repayId")+"", -1);
								//得到收费标准的json代码
								feelog= circulationMap.get("feelog")+"";
								feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
								investFeeRate= Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE)+"",0);
								//查询流转标借款待收款投资人
								investList = investDao.queryInvestorByBorrowId(conn, borrowId);
								for(Map<String,Object> investMap:investList){
									investId = Convert.strToLong(investMap.get("id")+"", -1);
									investor = Convert.strToLong(investMap.get("investor")+"", -1);
									oriInvestor = Convert.strToLong(investMap.get("oriInvestor")+"", -1);
									username = investMap.get("username")+"";
									realAmount = Convert.strToDouble(investMap.get("realAmount")+"", 0);
									recivedPrincipal = Convert.strToDouble(investMap.get("recivedPrincipal")+"", 0);
									recievedInterest = Convert.strToDouble(investMap.get("recievedInterest")+"", 0);
									recivedPI = recivedPrincipal+recievedInterest;
									mFee = recievedInterest*investFeeRate;
						            mFee = Double.valueOf(df.format(mFee));
						            hasSum = recivedPI - mFee;
						            //查询流转标还款记录ID
						            flowMap = investDao.queryFlowMap(conn, investId);
						            if(flowMap==null){  //为空则该条投资没有还
						            	//更新流转标还款状态为已还款
										returnId = investDao.updateInvestRepayStatus(conn,investId);
										//更新t_invest_repayment的状态为已还款
										returnId =  investDao.updateInvestRepaymentStatus(conn, investId);
										//更新流转标投资 辅助表
										returnId = investDao.addFlowRepayment(conn, investId);
										//更新t_invest借款人已收本息
										returnId = investDao.updateInvesthasPrincipalAndhasInterest(conn, investId, recivedPrincipal, recievedInterest);
										//更新invest
										investDao.updateInvestRepayment(conn, repayId, investId, investor, 0, oriInvestor, 2);
										if(returnId >0){
											//更新借款已回购份数
											hasRepoNumber = (int) (realAmount/smallestFlowUnit);
											borrowDao.updateHasRepoNumber(conn,borrowId,hasRepoNumber);
											String informTemplate = informTemplateMap.get(IInformTemplateConstants.RECOVER_ADVANCE_SUCCESS)+"";
											informTemplate = informTemplate.replace("title", borrowTitle+"");
											informTemplate = informTemplate.replace("[repayPeriod]", "1/1");
											informTemplate = informTemplate.replace("[paymentModeStr]", "");
											informTemplate = informTemplate.replace("[recivedSum]", recivedPI+"");
											informTemplate = informTemplate.replace("[hasP]", recivedPrincipal+"");
											informTemplate = informTemplate.replace("[hasI]", recievedInterest+"");
											informTemplate = informTemplate.replace("[mFee]", mFee+"");
											informTemplate = informTemplate.replace("[msFee]",df.format(hasSum)+"");
											informTemplate = informTemplate.replace("[hasLFI]", "0");
											informTemplate = informTemplate.replace("[paymentModeStr]","还款方式：[一次性还款]<br/>");
											//短信
											String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_RECOVER_ADVANCE_SUCCESS)+"";
											s_informTemplate = s_informTemplate.replace("username", username);
											s_informTemplate = s_informTemplate.replace("title", borrowTitle+"");
											s_informTemplate = s_informTemplate.replace("[repayPeriod]", "1/1");
											s_informTemplate = s_informTemplate.replace("[paymentModeStr]", "");
											s_informTemplate = s_informTemplate.replace("[recivedSum]", recivedPI+"");
											s_informTemplate = s_informTemplate.replace("[hasP]", recivedPrincipal+"");
											s_informTemplate = s_informTemplate.replace("[hasI]", recievedInterest+"");
											s_informTemplate = s_informTemplate.replace("[mFee]", mFee+"");
											s_informTemplate = s_informTemplate.replace("[msFee]", df.format(hasSum)+"");
											s_informTemplate = s_informTemplate.replace("[paymentModeStr]","还款方式：[一次性还款]<br/>");
											s_informTemplate = s_informTemplate.replace("[hasLFI]", "0");
											//邮件
											String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_RECOVER_ADVANCE_SUCCESS)+"";
											e_informTemplate = e_informTemplate.replace("title", borrowTitle+"");
											e_informTemplate = e_informTemplate.replace("[repayPeriod]", "1/1");
											e_informTemplate = e_informTemplate.replace("[paymentModeStr]", "");
											e_informTemplate = e_informTemplate.replace("[recivedSum]", recivedPI+"");
											e_informTemplate = e_informTemplate.replace("[hasP]", recivedPrincipal+"");
											e_informTemplate = e_informTemplate.replace("[hasI]", recievedInterest+"");
											e_informTemplate = e_informTemplate.replace("[mFee]", mFee+"");
											e_informTemplate = e_informTemplate.replace("[msFee]", df.format(hasSum)+"");
											e_informTemplate = e_informTemplate.replace("[paymentModeStr]","还款方式：[一次性还款]<br/>");
											e_informTemplate = e_informTemplate.replace("[hasLFI]","0");
											
											 noticeMap.put("mail", informTemplate);//站内信
											 noticeMap.put("email",e_informTemplate);//邮件
											 noticeMap.put("note", s_informTemplate);//短信
											//消息模版
								            //站内信
								          /*  noticeMap.put("mail", "您投资的借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>],已经完成.<br/>"+
								                  "本期应得总额：￥"+recivedPI+",其中本金部分为："+recivedPrincipal+"元,利息部分："+recievedInterest+"元<br/>扣除投资管理费：￥"+mFee+"元"+"<br/>实得总额：￥"+hasSum+"元");
								            //邮件
								            noticeMap.put("email","您投资的借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>],已经完成.<br/>"+
								                    "本期应得总额：￥"+recivedPI+",其中本金部分为："+recivedPrincipal+"元,利息部分："+recievedInterest+"元<br/>扣除投资管理费：￥"+mFee+"元"+"<br/>实得总额：￥"+hasSum+"元");
								            //短信
								            noticeMap.put("note",  "尊敬的"+username+":\n    ["+sf.format(new Date())+"] 您投资的借款["+borrowTitle+"]已经完成.\n"+
								            		"本期应得总额：￥"+recivedPI+",其中本金部分为："+recivedPrincipal+"元,利息部分："+recievedInterest+"元\n扣除投资管理费：￥"+mFee+"元"+"<br/>实得总额：￥"+hasSum+"元");
								            	*/
								             // 查询风险保障金余额
											 Map<String, String> riskMap = frontpayDao.queryRiskBalance(conn);
											 double riskBalance = Convert.strToDouble(riskMap.get("riskBalance")+ "", 0);
											 //投资手续费累加到风险保障金
											 returnId=frontpayDao.addRiskAmount(conn, riskBalance,mFee, investor, -1,"投资管理费累加风险保障金");
											 //关闭自动投标
											 returnId=frontpayDao.closeAutoBid(conn,investor);
											 //投资人帐号资金收入
											 returnId=userDao.addUserUsableAmount(conn,recivedPI,investor);
											 // 查询投资后的账户金额
											 userSumMap = userDao.queryUserAmountAfterHander(conn, investor);
											if(userSumMap == null){userSumMap = new HashMap<String,String>();}
											double usableSum = Convert.strToDouble(userSumMap.get("usableSum") + "", 0);
											double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum") + "", 0);
											double forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
											// 添加资金流动记录
											returnId = fundRecordDao.addFundRecord(conn, investor, "用户还款资金收入",
													recivedPI, usableSum, freezeSum, forPI, borrower,"您投资的借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>]",recivedPI,0.0,borrowId,repayId,151,0.0);
										
											// 投资人扣除投资管理费
											returnId = userDao.deducteUserUsableAmount(conn, mFee,investor);
											// 查询投资后的账户金额
											userSumMap = userDao.queryUserAmountAfterHander(conn, investor);
											usableSum = Convert.strToDouble(userSumMap.get("usableSum")
															+ "", 0);
											freezeSum = Convert.strToDouble(userSumMap.get("freezeSum")
															+ "", 0);
											forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
												// 添加资金流动记录
											returnId = fundRecordDao.addFundRecord(conn, investor,"投资收款扣除管理费", mFee, usableSum, freezeSum, forPI,-1,"您投资的借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>]",0.0,mFee,borrowId,repayId,651,0.0);
											if(returnId > 0){
												 //给投资人发送消息
												 selectedService.sendNoticeMSG(conn, investor, "用户还款资金收入报告", noticeMap, IConstants.NOTICE_MODE_1);
												 //自动结束提前还款正在竞拍中的债权
												 assignmentDebtService.preRepayment(conn,repayId);
											}
											
									}else{
										returnId = -1;
									}
									//回收对象
									investMap = null;
									if(returnId <=0){
										conn.rollback();
									}
									
								}
								userService.updateSign(conn, investId);//更换校验码
							}
				userService.updateSign(conn, borrower);//更换校验码
				//回收对象
				circulationMap = null;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		}finally{
			conn.close();
			circulationList = null;
			investList=null;
			noticeMap = null;
			df = null;
			sf = null;
			userSumMap = null;
			df = null;
            System.gc();
		}
	}
	
	/**
	 * 发送短信 模板
	 * @throws Exception 
	 */
	public void sendtoTemple() throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String>  noticeMap = new HashMap<String, String>();
		List<Map<String,Object>> tmplList = new ArrayList<Map<String,Object>>();
		PageBean<Map<String,Object>>  pagebean = new PageBean<Map<String,Object>>();
		long investor = -1;
		String title = "";
		int id = 0;
		try {
			pagebean.setPageSize(150);
			jobTaskDao.queryTmpleAll(conn,pagebean);
			tmplList = pagebean.getPage();
			if (tmplList!=null) {
				for (Map<String, Object> map : pagebean.getPage()) {
					investor = Convert.strToLong(map.get("user_id")+"",-1L);
					title = Convert.strToStr(map.get("sendtitle")+"", "");
					id = Convert.strToInt(map.get("id")+"", -1);
					noticeMap.put("email", Convert.strToStr(map.get("email_info")+"",""));
					noticeMap.put("mail", Convert.strToStr(map.get("mail_info")+"",""));
					noticeMap.put("note", Convert.strToStr(map.get("sms_info")+"",""));
					noticeMap.put("operate_id", Convert.strToStr(map.get("operate_id")+"",""));
					selectedService.sendNoticeMSG(conn, investor,
							title, noticeMap, Convert.strToStr(map.get("s_nid")+"",""));
					
					Functions.f_send_temple(conn,id);
					map = null;
				}
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
			noticeMap = null;
			pagebean = null;
			tmplList = null;
			System.gc();
		}
	}

	public void autoBidProTask() throws Exception {
		checkSign();
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_autobid_task(conn, ds, outParameterValues, IConstants.WEB_URL, -1, "");
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public void autoBidPro() throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_autobid(conn, ds, outParameterValues, IConstants.WEB_URL, -1, "");
			changeSign(conn);//更换校验码方法
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	private void checkSign() throws Exception {
		Connection conn = MySQL.getConnection();
		long userId = -1;
		try {
			List<Map<String,Object>> autoBidTaskList = new ArrayList<Map<String,Object>>();
			autoBidTaskList = jobTaskDao.queryAutoBidTask(conn);
			if (autoBidTaskList!=null) {
				for (Map<String, Object> map : autoBidTaskList) {
					userId = Convert.strToLong(map.get("userId")+"",-1L);
//					boolean re = userService.checkSign(conn,userId);//验证校验码
//					if(!re){
//						JobTaskDao.updateAuto(conn, userId);
//					}
					map = null;
				}
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 自动投标时修改校验码
	 * @throws Exception
	 */
	private void changeSign(Connection conn) throws Exception{
		long userId = -1;
		List<Map<String,Object>> autoBidTaskList = new ArrayList<Map<String,Object>>();
		autoBidTaskList = jobTaskDao.queryAutoBidTask(conn);
		if (autoBidTaskList!=null) {
			for (Map<String, Object> map : autoBidTaskList) {
				userId = Convert.strToLong(map.get("userId")+"",-1L);
				userService.updateSign(conn, userId);//更换校验码
				map = null;
			}
		}
	}
	
	/*public void autoRelaseLimit() throws Exception{
		Connection conn = Database.getConnection();
		try{
			
			List<Map<String, String>> limitList = jobTaskDao.queryAllLimitUser(conn);
			for (Map<String, String> userMap : limitList){
				String lastDate= userMap.get("lastDate");
				int id=Convert.strToInt(userMap.get("id"), -1);
				SimpleDateFormat simple =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date  d=simple.parse(lastDate);
				if(new Date().getTime()-d.getTime()>3*60*60*1000){
					//取消用户限制登录将isLoginLimit设置为1和loginErrorCount设置为0
					//resetUserState(Connection conn,int loginErrorCount,int isLoginLimit,long userId)
					userDao.resetUserState( conn,0,1, id);
					 conn.commit();
				}
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}*/
	
	public long addSysBaseStatis() throws Exception{
		long m = -1;
		Connection conn = MySQL.getConnection();
		try {
		  
			Map<String,String> queryNewRegUserMap = statisManageDao.queryNewRegUser(conn, "1", "", "");//注册用户
			long s_reg_user_sum = 0;
			if(queryNewRegUserMap!=null){
				s_reg_user_sum = Convert.strToLong(queryNewRegUserMap.get("newRegUsers"), 0);
			}
			
			
			
			Map<String,String> queryNewInvestUserMap = statisManageDao.queryNewInvestUser(conn, "1", "", "");//投资用户
			long s_invest_user_sum = 0;
			if(queryNewInvestUserMap!=null){
				s_invest_user_sum = Convert.strToLong(queryNewInvestUserMap.get("investUserCount"), 0);
			}
			
			
			Map<String,String> queryAllFundNewUserMap = statisManageDao.queryAllFundNewUser(conn);//充值金额
			double s_cz_money = 0;
			if(queryAllFundNewUserMap!=null){
				s_cz_money = Convert.strToDouble(queryAllFundNewUserMap.get("handleSum"), 0);
			}
			
			
			
			Map<String,String> queryTxMoneyMap = statisManageDao.queryTxMoney(conn, "1","","");//提现金额
			double s_tx_money = 0;
			if(queryTxMoneyMap!=null){
				s_tx_money = Convert.strToDouble(queryTxMoneyMap.get("handleSum"), 0);
			}
			
			
			
			Map<String,String> queryInvestMoneyMap = statisManageDao.queryNewInvestMoney(conn, "1", "", "");//投资金额
			double s_tz_money = 0;
			if(queryInvestMoneyMap!=null){
				s_tz_money = Convert.strToDouble(queryInvestMoneyMap.get("investAmountSum"), 0);
			}
			
			Map<String,String> queryAllUserNewOrgSumMap = statisManageDao.queryAllUserNewOrgSum(conn, "1", "", "");//机构投资金额
			double s_org_moey = 0;
			if(queryAllUserNewOrgSumMap!=null){
				s_org_moey = Convert.strToDouble(queryAllUserNewOrgSumMap.get("investAmountNewOrg"), 0);
			}
			
			
			Map<String,String> queryAllUserNewUpLineSumMap = statisManageDao.queryAllUserNewUpLineSum(conn, "1", "", "");//线上投资金额
			double s_upline_moeny = 0;
			if(queryAllUserNewUpLineSumMap!=null){
				s_upline_moeny = Convert.strToDouble(queryAllUserNewUpLineSumMap.get("investAmountNewUpLine"), 0);
			}
			
			
			Map<String,String> queryAllUserNewDownLineSumMap = statisManageDao.queryAllUserNewDownLineSum(conn, "1", "", "");//线下理财金额
			double s_downline_money = 0;
			if(queryAllUserNewDownLineSumMap!=null){
				s_downline_money = Convert.strToDouble(queryAllUserNewDownLineSumMap.get("investAmountNewDownLine"), 0);
			} 
			 
			String currDateAgo = DateUtil.getCurrDateAgo();//昨天
			Date s_date = DateUtil.strToDate(currDateAgo);
			m  = statisManageDao.addSysBaseStatis(conn, s_date, s_reg_user_sum, s_invest_user_sum, s_cz_money, s_tx_money, s_tz_money, s_org_moey, s_upline_moeny, s_downline_money);//整理好的数据添加
			if(m>0){
				m = 1;
			}else{
				m = -1;
			}
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return m;
	}
		
	public long updateUserBonus() throws Exception{
		long m = -1;
		Connection conn = MySQL.getConnection();
		
		try {
			
			List<Map<String,Object>> list = bonusDao.queryBonus(conn);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					long id = (Long)list.get(i).get("id");
					bonusDao.updateUserBonus(conn, id);
				}
				conn.commit();
				m=1;
			}else{
			  m = 0;//不存在数据
			}
			
		} catch (Exception e) {
			m = -1;
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return m;
	}
	
	/***
	 * p处理体验金投资到期还款的利息给用户
	 * @return
	 * @throws Exception
	 */
	public long doBatchTyjRepay() throws Exception{
		
		Connection conn = MySQL.getConnection();
		String currentDate = DateUtil.dateToStringYYMMDD(new Date());
		long m = -1;
		try {
			
			List<Map<String,Object>> list = financeDao.queryTyjRepay(conn,currentDate);
			if(list!=null && list.size()>0){
				Map<String,String> SMSInface = borrowDao.getSMSById(conn, 1);
				for(int i=0;i<list.size();i++){
					Map<String,Object> map = list.get(i);
					long id = Convert.strToLong(String.valueOf(map.get("id")), 0);
					long userId = Convert.strToLong(String.valueOf(map.get("userId")), 0);
					double getLx = Convert.strToDouble(String.valueOf(map.get("getLx")), 0);
					String username = String.valueOf(map.get("username"));
					String mobilePhone = String.valueOf(map.get("mobilePhone"));
					 
					Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
					double usableSum = Double.parseDouble(useMap.get("usableSum"));//投资可用金额
					double fall = usableSum + getLx;
					
					Map<String,String>  activtySetcomfig = financeDao.query51ActivtyComfig(conn, "1005");
					long super_id = Long.parseLong(activtySetcomfig.get("user_id")); 
					Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
					double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
					double superFall = superUsableSum - getLx;
					
					borrowDao.updateUserMoney(conn, userId, fall);//更新用户可用金
					borrowDao.updateUserMoney(conn, super_id, superFall);//更新超级用户可用金
					
					financeDao.updateTyjRepay(conn, id);//已还款
					financeDao.add51Recode(conn,userId, super_id,"体验金活动利息", getLx, fall, getLx, "体验金活动利息",2,0);
					financeDao.add51Recode(conn,super_id, userId,"体验金活动利息", getLx, superFall, getLx, "体验金活动利息",1,0);
				
					if("2".equals(IConstants.ISDEMO)){
						StringBuffer buffer = new StringBuffer();
						buffer.append("【微信贷】尊敬的["+username+"]:您投资的活动体验标,已还款完成,");
						buffer.append("其中体验金部分：8888.00元已被系统收回,利息部分："+getLx+"元已发送到您的账户,请查收.");
						SMSUtil.sendMSM(SMSInface.get("Account"), SMSInface.get("Password"), buffer.toString(), mobilePhone); 
					}
				}
			}
			
			String end_time = DateUtil.dateToStringYYMMDD(new Date());
			financeDao.updateUserTyj(conn, end_time);
			
			conn.commit();
			m = 1;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return m;
	}
	
	
	
	
    public long doBatchEmployeeRepayment() throws Exception{
	    Connection conn = MySQL.getConnection();
		long m = -1;
		try {
			
			List<Map<String,Object>> list = financeDao.queryEmployeeRepayment(conn);
			if(list!=null && list.size()>0){
				Map<String,String> SMSInface = borrowDao.getSMSById(conn, 1);
				for(int i=0;i<list.size();i++){
					Map<String,Object> map = list.get(i);
					long userId = Convert.strToLong(String.valueOf(map.get("userId")), 0);
					double getLx = Convert.strToDouble(String.valueOf(map.get("repayAmount")), 0);
					String username = String.valueOf(map.get("username"));
					String mobilePhone = String.valueOf(map.get("mobilePhone"));
					 
					Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
					double usableSum = Double.parseDouble(useMap.get("usableSum"));//用户可用金额
					double fall = usableSum + getLx;
					
					Map<String,String>  activtySetcomfig = financeDao.query51ActivtyComfig(conn, "1005");
					long super_id = Long.parseLong(activtySetcomfig.get("user_id")); 
					Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
					double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
					double superFall = superUsableSum - getLx;
					
					borrowDao.updateUserMoney(conn, userId, fall);//更新用户可用金
					borrowDao.updateUserMoney(conn, super_id, superFall);//更新超级用户可用金
					
					financeDao.add51Recode(conn,userId, super_id,"员工还款", getLx, fall, getLx, "员工还款",2,0);
					financeDao.add51Recode(conn,super_id, userId,"员工还款", getLx, superFall, getLx, "员工还款",1,0);
				
					if("2".equals(IConstants.ISDEMO)){
						StringBuffer buffer = new StringBuffer();
						buffer.append("【微信贷】尊敬的["+username+"]:您投资已还款,");
						buffer.append("金额："+getLx+"元已发送到您的账户,请查收");
						SMSUtil.sendMSM(SMSInface.get("Account"), SMSInface.get("Password"), buffer.toString(), mobilePhone); 
					}
				}
			}
			
			conn.commit();
			m = 1;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return m;
	}
    
    
    
    /**
     * 处理10月份    活动时间范围内年化投资额达到10+返现0.5%
     * @return
     * @throws Exception
     */
    public long doBatchOctoberInvestAmount() throws Exception{
	    Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			List<Map<String,Object>> checkList = financeDao.checkctoberInvestAmount(conn);
			if(checkList!=null && checkList.size()>0){
				return result;
			}
			
			Map<String,String> config = financeDao.query51ActivtyComfig(conn, "1007");
			if(config!=null && config.size()>0){
				String start_time =  config.get("start_time_f");
				String end_time = config.get("end_time_f");
				long super_id = Long.parseLong(config.get("user_id"));
				List<Map<String,Object>> list = financeDao.queryOctoberInvestAmount(conn, start_time, end_time);
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						Map<String,Object> map = list.get(i);
						long userId = Convert.strToLong(String.valueOf(map.get("userId")), 0);
						double investAmountSum = Convert.strToDouble(String.valueOf(map.get("investAmountSum")), 0);
						 
						double getAmount = investAmountSum*0.5/100;
						Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
						double usableSum = Double.parseDouble(useMap.get("usableSum"));//用户可用金额
						double fall = usableSum + getAmount;
						 
						Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
						double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
						double superFall = superUsableSum - getAmount;
						
						borrowDao.updateUserMoney(conn, userId, fall);//更新用户可用金
						borrowDao.updateUserMoney(conn, super_id, superFall);//更新超级用户可用金
						
						financeDao.add51Recode(conn,userId, super_id,"年化投资额满10W返现千分之五", getAmount, fall, getAmount, "年化投资额满10W返现千分之五",2,0);
						financeDao.add51Recode(conn,super_id, userId,"年化投资额满10W返现千分之五", getAmount, superFall, getAmount, "年化投资额满10W返现千分之五",1,0);
					 
					}
				 }
			}
			conn.commit();
			result = 1;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
    
    
    /***
     * 2015-11累计年化投资一定金额返红包
     * 
     * 
     * 
     * 活动日期 2015-11-13至2015-11-30。
     * 
     * 活动结束后 每月1日发送满足条件的用户红包，每月发红包总额的1/4，共发送四个月
     * 既：（2015-12-1、2016-1-1、2016-2-1、2016-3-1、）发送红包
     * 
     * 作者：郭井超
     * 时间：2015-11-17日
     * @return
     * @throws Exception
     */
    public long doBatchNovemberInvestAmount() throws Exception{
	    Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			    //计算当前日期是否可发送红包
				String currDate = DateUtil.dateToStringYYMMDD(new Date()); 
                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd"); 
                Date date2 = sdformat.parse(currDate); 
                Date date3 = sdformat.parse("2016-03-02"); 
                long dates = date2.getTime()-date3.getTime(); 
                if(dates<=0){//在可发送红包日期内
                	
				//查询活动时间内用户年华投资额
				List<Map<String,Object>> list = financeDao.queryNovemberInvestAmount(conn);
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						Map<String,Object> map = list.get(i);
						long userId = Convert.strToLong(String.valueOf(map.get("userId")), 0);
						double investAmountSum = Convert.strToDouble(String.valueOf(map.get("investAmountSum")), 0);
						double bonus_able = 0;
						if(investAmountSum>30000 && investAmountSum<=100000){
							bonus_able = 80/4;
						}
						if(investAmountSum>100000 && investAmountSum<=200000){
							bonus_able = 800/4;
						}
						if(investAmountSum>200000 && investAmountSum<=300000){
							bonus_able = 1800/4;
						}
						if(investAmountSum>300000 && investAmountSum<=500000){
							bonus_able = 2800/4;
						}
						if(investAmountSum>500000 && investAmountSum<=800000){
							bonus_able = 3800/4;
						}
						if(investAmountSum>800000 && investAmountSum<=1000000){
							bonus_able = 6800/4;
						} 
						if(investAmountSum>1000000){
							bonus_able = 6800/4;
						}
						
						if(bonus_able>0){
							bonusDao.addBonus_6_24(conn, userId, bonus_able);
						}
					}
				  }
                } 
			conn.commit();
			result = 1;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
    
    
    
    
    
    /***
     * 12月份累计投资一定金额返现金
     * 
     * 
     * 活动日期   2015-12-07至2015-12-31。
     * 
     * 当月投资累计满5万以上， 奖励加息1%。
                  当月投资累计满8万以上， 奖励加息1.5%。
                   当月投资累计满10万以上，奖励加息2%。
     * 加息奖励将在投资人投标到期后一次性发放到个人账户。
     * 
     * 
     * 作者：郭井超
     * 时间：2015-12-08日、
     * 
     * @return
     * @throws Exception
     */
    public long doBatchDecemberInvestAmount() throws Exception{
	    Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			 
			/** 获取扣款大账户*/
			Map<String,String> config = financeDao.query51ActivtyComfig(conn, "1007");
			if(config!=null && config.size()>0){
				long super_id = Long.parseLong(config.get("user_id"));
		
				/**查询活动月累计投资金额至少达到5W的用户*/
				List<Map<String,Object>> list = financeDao.queryDecemberInvestAmount(conn);
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						Map<String,Object> map = list.get(i);
						long userId = Convert.strToLong(String.valueOf(map.get("userId")), 0);
						double investAmountSum = Convert.strToDouble(String.valueOf(map.get("investAmountSum")), 0);
						double parm = 0;//返现年华收益率
						if(investAmountSum>=50000 && investAmountSum<80000){
							parm = 0.01;
						}
						if(investAmountSum>=80000 && investAmountSum<100000){
							parm = 0.015;
						}
						if(investAmountSum>=100000){
							parm = 0.02;
						}
						
					    /**查询该用户所投的标+已经还款完毕+非债转+系统当前时间*/
						String curSysDate = DateUtil.dateToStringYYMMDD(new Date()); 
						List<Map<String,Object>> investList = financeDao.queryDecemberInvestRepayOver(conn, userId, curSysDate);
						if(investList!=null && investList.size()>0){
							for(int j=0;j<investList.size();j++){
								Map<String,Object> investMap = investList.get(j);
								double investAmount = Convert.strToDouble(String.valueOf(investMap.get("investAmount")), 0);
								int deadline = Convert.strToInt(String.valueOf(investMap.get("deadline")), 0);
								double flee = investAmount*deadline/12*parm;//返现金额
								
								/**投资用户*/
								Map<String,String> useMap = borrowDao.queryUserAmount(conn,userId);
								double usableSum = Double.parseDouble(useMap.get("usableSum"));//用户可用金额
								/**大账户*/ 
								Map<String,String> superUseMap = borrowDao.queryUserAmount(conn,super_id);
								double superUsableSum = Double.parseDouble(superUseMap.get("usableSum"));//超级账户可用金额
								/**更新可用余额*/
								borrowDao.updateUserMoney(conn, userId, usableSum+flee);//更新用户可用金
								borrowDao.updateUserMoney(conn, super_id, superUsableSum-flee);//更新超级用户可用金
								/**保存资金记录*/
								financeDao.add51Recode(conn,userId, super_id,"12月份活动投资返现", flee, usableSum+flee, flee, "12月份活动投资返现",2,0);
								financeDao.add51Recode(conn,super_id, userId,"12月份活动投资返现", flee, superUsableSum-flee, flee, "12月份活动投资返现",1,0);
							 
							}
						}
					 }
				  }
			} 
			
			conn.commit();
			result = 1;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
		return result;
	}
    
}
