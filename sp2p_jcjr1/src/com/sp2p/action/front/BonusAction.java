/**
 * 
 */
package com.sp2p.action.front;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BonusService;
import com.sp2p.service.admin.RewardService;
import com.sp2p.util.DateUtil;

/**
 * @author David‎-RYE
 *
 */
public class BonusAction extends BaseFrontAction{

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(BonusAction.class);
	private BonusService bonusService;
	private RewardService rewardService;
	
	
	/**
	 * 我的奖励—现金奖励列表
	 * @return
	 * @throws Exception
	 */
	public String toRewardInfo() throws Exception {
		long userId = this.getUserId();
		//奖励总额
		List<Map<String,Object>> list = rewardService.queryRewardRecordTotal(userId);
		Map<String, String> rewardStatisMap = new HashMap<String, String>();
		if(!list.isEmpty()){
			for(Map<String,Object> map:list){
				//1、注册奖励 2、投资奖励 3 推荐注册奖励 4 累计推荐注册奖励 5 推荐单次投资奖励 6 累计推荐单次投资奖励
				if("1".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("regReward", String.valueOf(map.get("sum_amount")));
				}else if("2".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("investReward", String.valueOf(map.get("sum_amount")));
				}else if("3".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("recomReward", String.valueOf(map.get("sum_amount")));
				}else if("4".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("sumRecomReward", String.valueOf(map.get("sum_amount")));
				}else if("5".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("onceInvestReward", String.valueOf(map.get("sum_amount")));
				}
//				else if("6".equals(String.valueOf(map.get("reward_type")))){
//					rewardStatisMap.put("sumOnceInvestReward", String.valueOf(map.get("sum_amount")));
//				}
				else if("9".equals(String.valueOf(map.get("reward_type")))){
					rewardStatisMap.put("sumReward", String.valueOf(map.get("sum_amount")));
				}else {
					rewardStatisMap.put("error", "未知错误");
				}
			}
		}
		request().setAttribute("rewardStatisMap", rewardStatisMap);
		//奖励列表
		String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		int rewardType = Convert.strToInt(paramMap.get("rewardType"),-1);
		int rewardLink = Convert.strToInt(paramMap.get("rewardLink"),-1);
		//奖励时间 -1：区间  -7：最近7天  -30：最近1个月
		int reward_time = Convert.strToInt(paramMap.get("reward_time"),1);
		String timeStart = "";
		String timeEnd = "";
		if(-1 == reward_time){
			timeStart = paramMap.get("timeStart") == null ? "" : paramMap.get("timeStart");
			timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap.get("timeEnd");
		}else if(-7 == reward_time){
			timeStart = DateUtil.getCurrDateLateDay(-7);
			timeEnd = DateUtil.dateToString(new Date());
		}else if(-30 == reward_time){
			timeStart = DateUtil.getCurrDateLateDay(-30);
			timeEnd = DateUtil.dateToString(new Date());
		}
		rewardService.rewardRecordInfo(userId, rewardType, rewardLink, timeStart, timeEnd, pageBean);

		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		request().setAttribute("reward_time", reward_time);
		return "success";
	}
	
	public String rewardRules(){
		request().setAttribute("userName", getUser().getUserName());		return SUCCESS;
	}
	
	/**
	 * 红包
	 * @return
	 */
	public String myBonus(){
		long userId = this.getUserId();
		Map<String, String> bonusRewardMap = new HashMap<String, String>();
		try {
			//查询红包总额等统计信息
			bonusRewardMap = bonusService.queryBonusSumCount(userId,1);
			request().setAttribute("flag", "1");
			request().setAttribute("bonusRewardMap", bonusRewardMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 红包列表
	 * @return
	 */
	public String getBonusInfo(){
		String pageNum = request().getParameter("pageNum"); 
		String pageSize = request().getParameter("pageSize");
		int flag = Convert.strToInt(request().getParameter("flag"), 2);
		pageBean.setPageNum(Integer.parseInt(pageNum)); 
		pageBean.setPageSize(Integer.parseInt(pageSize));
		
		JSONObject jo = new JSONObject();
		try {
			long userId = getUser().getId();
			
			long count = bonusService.queryBonusListCount(userId,flag);
			List<Map<String, Object>> bonusList = bonusService.queryBonusList(userId,pageBean.getStartOfPage(),pageBean.getPageSize(),flag);
			
			pageBean.setTotalNum(count);
			jo.put("totalPageNum", pageBean.getTotalPageNum());
			jo.put("bonusList", bonusList);
			jo.put("flag", flag);
		} catch (DataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		printJson(jo.toString());
		
		return null;
	}
	
	/**
	 * 6.24红包
	 * @return
	 */
	public String mySixBonus(){
		long userId = this.getUserId();
		Map<String, String> bonusRewardMap = new HashMap<String, String>();
		try {
			//查询红包总额等统计信息
			bonusRewardMap = bonusService.queryBonusSumCount(userId,2);
			request().setAttribute("bonusRewardMap", bonusRewardMap);
			request().setAttribute("flag", "2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 我的体验金
	 * @return
	 */
	public String myTyj(){
		long userId = this.getUserId();
		pageBean.setPageNum(request().getParameter("curPage"));
		pageBean.setPageSize(10);
		try {
			bonusService.queryMyTyj(pageBean, userId);
			request().setAttribute("pageBean", pageBean);
			int pageNums = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNums);
			request().setAttribute("flag", 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	
	public String activityCi(){
		long userId = this.getUserId();
		try {
			List<Map<String,Object>>  list = bonusService.queryActivityCi(userId);
			request().setAttribute("activityCiList", list);
			request().setAttribute("flag", 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 红包规则初始化
	 * @return
	 * @throws Exception
	 */
	public String bonusConfigInit() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 修改红包规则初始化
	 * @return
	 * @throws Exception
	 */
	public String updateBonusConfigInit() throws Exception {
		Map<String,String> bonusMap = null;
		//红包金额区间
		Map<String,String> map = null;
		List<Map<String, Object>> list = null;
		try {
			map = bonusService.queryBonusAmountId();
			bonusMap = bonusService.queryBonusConfig();
			list = bonusService.queryBonusAmount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request().setAttribute("map", map);
		request().setAttribute("bonusMap", bonusMap);
		request().setAttribute("list", list);
		return SUCCESS;
	}
	
	/**
	 * 删除金额区间数据
	 * @author 
	 * @return
	 */
	public String deleteBonusAmount(){
        JSONObject jo = new JSONObject();
        //图片id
        long id= Convert.strToLong(request().getParameter("id"),0);
		try {
			long m = bonusService.deleteById(id);
			if(m>0){
				jo.put("state", "1");
			}else{
				jo.put("state", "0");
			}
		} catch (Exception e) {
			jo.put("status", "删除失败:"+e.getMessage());
			e.printStackTrace();
		}
		printJson(jo.toString());
		return null;
	}
	
	/**
	 * 修改红包配置
	 * @return
	 * @throws Exception
	 */
	public String updateBonusConfig(){
		
		int super_id =  Convert.strToInt(paramMap.get("super_id"), -1);//红包使用期限
		int use_deadline =  Convert.strToInt(paramMap.get("use_deadline"), -1);//红包使用期限
		int reg_deadline =  Convert.strToInt(paramMap.get("reg_deadline"), -1);//注册红包投资期限
		int recom_deadline =  Convert.strToInt(paramMap.get("recom_deadline"), -1);//推荐红包投资期限
		int invest_deadline =  Convert.strToInt(paramMap.get("invest_deadline"), -1);//投资红包投资期限
		double reg_recommended_red = Convert.strToDouble(paramMap.get("reg_recommended_red"), 0.00);//推荐注册红包
		double reg_registration_red = Convert.strToDouble(paramMap.get("reg_registration_red"), 0.00);//注册红包
		double reg_registration_cash = Convert.strToDouble(paramMap.get("reg_registration_cash"), 0.00);//注册奖励
		double invest_proportion = Convert.strToDouble(paramMap.get("invest_proportion"), 0.00);//投资比例
		double low_amount = Convert.strToDouble(paramMap.get("low_amount"), 0.00);//最低投资金额
		double red_limit = Convert.strToDouble(paramMap.get("red_limit"), 0.00);//红包上限
		String start_time = paramMap.get("start_time");//有效日期
		String end_time = paramMap.get("end_time");
		String min_invest_amount = paramMap.get("min_invest_amount");//最小投资金额
		String max_invest_amount = paramMap.get("max_invest_amount");//最大投资金额
		String red_value = paramMap.get("red_value");//红包面值
		String update_min_invest_amount = paramMap.get("update_min_invest_amount");//最小投资金额
		String update_max_invest_amount = paramMap.get("update_max_invest_amount");//最大投资金额
		String update_red_value = paramMap.get("update_red_value");//红包面值
		String ids = paramMap.get("ids");//金额区间修改id
		int bonusId =  Convert.strToInt(request.getString("bonusId"), -1);//红包配置修改id
		
		Long result = -1L;//配置修改结果
		Long amount_result = 1L;//金额区间修改结果
		try {
			//红包使用期限不能为空
			if(super_id <= 0){
				JSONUtils.printStr("12");
				return null;
			}
			//红包使用期限不能为空
			if(use_deadline <= 0){
				JSONUtils.printStr("1");
				return null;
			}
			//投资比例不能大于1
			if(invest_proportion > 1){
				JSONUtils.printStr("4");
				return null;
			}
			//最低投资金额不能为空
			if(low_amount <= 0){
				JSONUtils.printStr("5");
				return null;
			}
			//红包上限不能为空
			if(red_limit <= 0){
				JSONUtils.printStr("6");
				return null;
			}
			//注册红包投资期限不能为空
			if(reg_deadline <= 0){
				JSONUtils.printStr("7");
				return null;
			}
			//推荐红包投资期限不能为空
			if(recom_deadline <= 0){
				JSONUtils.printStr("11");
				return null;
			}
			//投资红包投资期限不能为空
			if(invest_deadline <= 0){
				JSONUtils.printStr("8");
				return null;
			}
			//有效日期不能为空
			if(StringUtils.isBlank(start_time) || StringUtils.isBlank(end_time)){
				JSONUtils.printStr("10");
				return null;
			}
			
			result = bonusService.updateBonusConfig(bonusId, super_id, use_deadline, reg_deadline, recom_deadline, invest_deadline, reg_recommended_red, 
					reg_registration_red, reg_registration_cash, invest_proportion, low_amount, red_limit, start_time, end_time);
			// 添加金额区间
			if(StringUtils.isNotBlank(min_invest_amount) && StringUtils.isNotBlank(max_invest_amount) && StringUtils.isNotBlank(red_value)){
				String[] minAmount = min_invest_amount.split(",");
				String[] maxAmount = max_invest_amount.split(",");
				String[] redValue = red_value.split(",");
				for(int i=0;i<minAmount.length;i++){
					if(StringUtils.isNotBlank(minAmount[i].trim()) && StringUtils.isNotBlank(maxAmount[i].trim()) && StringUtils.isNotBlank(redValue[i].trim())){
						long dataid = bonusService.addBonusAmount(Double.parseDouble(minAmount[i]), Double.parseDouble(maxAmount[i]), Double.parseDouble(redValue[i]));
						if(dataid < 0){
							amount_result = -1L;
						}
					}
				}
			}
			// 修改金额区间
			if(StringUtils.isNotBlank(update_min_invest_amount) && StringUtils.isNotBlank(update_max_invest_amount) && StringUtils.isNotBlank(update_red_value)){
				String[] minAmount = update_min_invest_amount.split(",");
				String[] maxAmount = update_max_invest_amount.split(",");
				String[] redValue = update_red_value.split(",");
				String[] idu = ids.split(",");
				for(int i=0;i<minAmount.length;i++){
					if(StringUtils.isNotBlank(minAmount[i].trim()) && StringUtils.isNotBlank(maxAmount[i].trim()) && StringUtils.isNotBlank(redValue[i].trim())){
						long dataid = bonusService.updateBonusAmount(Integer.parseInt(idu[i]),Double.parseDouble(minAmount[i]), Double.parseDouble(maxAmount[i]), Double.parseDouble(redValue[i]));
						if(dataid < 0){
							amount_result = -1L;
						}
					}
				}
			}
			//添加成功
			if(result > 0 && amount_result > 0){
				JSONUtils.printStr("-1");
			}else if(result > 0 && amount_result < 0){
				JSONUtils.printStr("-2");
			}else if(result < 0 && amount_result > 0){
				JSONUtils.printStr("-3");
			}else {
				//添加失败
				JSONUtils.printStr("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String saveBouns(){
		try {
			String bonus_able = request().getParameter("bonus_able");
			session().setAttribute("bonus_able", bonus_able);
			log.info("摇奖红包金额是："+bonus_able);
			JSONUtils.printStr("0");
		} catch (Exception e) {
			try {
				JSONUtils.printStr("-1");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}
	
	public String getBouns(){
		try {
			String bonus_able = (String)session().getAttribute("bonus_able");
			System.out.println(bonus_able);
			JSONUtils.printStr("0");
		} catch (Exception e) {
			try {
				JSONUtils.printStr("-1");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}
	
	public BonusService getBonusService() {
		return bonusService;
	}
	public void setBonusService(BonusService bonusService) {
		this.bonusService = bonusService;
	}

	public RewardService getRewardService() {
		return rewardService;
	}

	public void setRewardService(RewardService rewardService) {
		this.rewardService = rewardService;
	}
	
}
