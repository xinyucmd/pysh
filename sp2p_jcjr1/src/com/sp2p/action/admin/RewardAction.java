package com.sp2p.action.admin;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.admin.RewardService;

/**
 * @author whb
 *
 */
public class RewardAction extends BasePageAction{

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(RewardAction.class);
	
	private RewardService rewardService;
	
	
	public String executeReward()  throws Exception {
		try {
			String rewardTime = paramMap.get("rewardTime");
			String borrowId = paramMap.get("borrowId");
			// 查询是否已经发送过
//			Map<String, String>  rewardMap = rewardService.queryByRewardTime("5", rewardTime);
//			if(rewardMap!= null && !rewardMap.isEmpty()){
//				JSONUtils.printStr("1");
//			}else{
				rewardService.provideRecommendInvestBrokerage(rewardTime,borrowId);
//				JSONUtils.printStr("0");
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONUtils.printStr("0");
		
		return null;
	}
	
	public String executePlusInterest()  throws Exception {
		try {
			String rewardTime = paramMap.get("rewardTime");
			String borrowId = paramMap.get("borrowId");
			// 查询是否已经发送过
//			Map<String, String>  rewardMap = rewardService.queryByRewardTime("6", rewardTime);
//			if(rewardMap!= null && !rewardMap.isEmpty()){
//				JSONUtils.printStr("1");
//			}else{
				rewardService.provideRewardInvestPlusInterest(rewardTime,borrowId);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONUtils.printStr("0");
		
		return null;
	}
	
	
	/**
	 * 推广奖励初始化
	 * @return
	 * @throws Exception
	 */
	public String rewardSettingInit() throws Exception {
		return SUCCESS;
	}
	
	/**
	 *  根据id删除规则信息
	 * @return
	 * @throws Exception
	 */
	public String deleteById() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		long result = -1;
		try {
			result = rewardService.deleteById(idLong);
			//删除成功
			if(result > 0){
				JSONUtils.printStr("-1");
			}else{
				JSONUtils.printStr("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改奖励规则初始化
	 * @return
	 * @throws Exception
	 */
	public String updateRewardSettingInit() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> map = null;
		try {
			map = rewardService.queryById(idLong);
			JSONUtils.printObject(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  推广奖励列表
	 * @return
	 * @throws Exception
	 */
	public String rewardSettingInfo() throws Exception {
		String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		int rewardSrc = Convert.strToInt(paramMap.get("rewardSrc"),-1);
		int userType = Convert.strToInt(paramMap.get("userType"),-1);
		int rewardLevel = Convert.strToInt(paramMap.get("rewardLevel"),-1);
		int giveWay = Convert.strToInt(paramMap.get("giveWay"),-1);

		rewardService.rewardSettingInfo(rewardSrc, userType, rewardLevel, giveWay, pageBean);

		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}
	
	/**
	 *  推广奖励添加
	 * @return
	 * @throws Exception
	 */
	public String addRewardSetting(){
		
		int reward_src =  Convert.strToInt(paramMap.get("reward_src"), -1);//推荐类型
		int user_type =  Convert.strToInt(paramMap.get("user_type"), -1);//用户类型
		double min_invest_amount = Convert.strToDouble(paramMap.get("min_invest_amount"), 0.00);//最小投资金额
		double max_invest_amount = Convert.strToDouble(paramMap.get("max_invest_amount"), 0.00);//最大投资金额
		int deadline_start = Convert.strToInt(paramMap.get("deadline_start"), -1);//投资期限开始
		int deadline_end = Convert.strToInt(paramMap.get("deadline_end"), -1);//投资期限结束
		int deadline_unit = Convert.strToInt(paramMap.get("deadline_unit"), -1);//投资期限单位
		String reward_level = paramMap.get("reward_level");//奖励层级
		String borrow_type = paramMap.get("borrow_type");//标种
		int return_type = Convert.strToInt(paramMap.get("return_type"), -1);//奖励计算方式
		double brokerage_one = Convert.strToDouble(paramMap.get("brokerage_one"), 0.00);//第一层返现比例
		double up_limit = Convert.strToDouble(paramMap.get("up_limit"), 0.00);//返现上限
		double down_limit = Convert.strToDouble(paramMap.get("down_limit"), 0.00);//返现下限
		double brokerage_two = Convert.strToDouble(paramMap.get("brokerage_two"), 0.00);//第二层返现比例
		String start_time = paramMap.get("start_time");//有效日期
		String end_time = paramMap.get("end_time");
		int reward_valid = Convert.strToInt(paramMap.get("reward_valid"), -1);//推荐投资奖励有效期
		int reward_valid_unit = Convert.strToInt(paramMap.get("reward_valid_unit"), -1);//推荐有效奖励单位
		double amount_invest_sum = Convert.strToDouble(paramMap.get("amount_invest_sum"), 0.00);//累计投资额度
		int give_way = Convert.strToInt(paramMap.get("give_way"), -1);//奖励发放方式
		int reg_sum = Convert.strToInt(paramMap.get("reg_sum"), -1);//累计推荐注册个数
		int isopne = Convert.strToInt(paramMap.get("isopne"), -1);//是否启用
		
		Long result = -1L;
		try {
			//请选择推荐类型
			if(reward_src < 0){
				JSONUtils.printStr("1");
				return null;
			}
			//用户类型不能为空
			if(user_type < 0){
				JSONUtils.printStr("12");
				return null;
			}
			//累计推荐注册个数不能为空
			if(2 == reward_src && reg_sum < 0){
				JSONUtils.printStr("13");
				return null;
			}
			//推荐单次投资下的校验
			if(3 == reward_src){
				//最小投资金额不能大于最大投资金额
				if(max_invest_amount <= 0 || min_invest_amount > max_invest_amount){
					JSONUtils.printStr("2");
					return null;
				}
				//投资期限不正确
				if(deadline_start < 0 || deadline_end < 0){
					JSONUtils.printStr("3");
					return null;
				}
				//请选择标种
				if(StringUtils.isBlank(borrow_type)){
					JSONUtils.printStr("4");
					return null;
				}
			}
			//累计投资额度不能为空
			if(4 == reward_src && amount_invest_sum <= 0){
				JSONUtils.printStr("11");
				return null;
			}
			//推荐投资奖励有效期
			if(3 == reward_src || 4 == reward_src){
				if(reward_valid < 0){
					JSONUtils.printStr("10");
					return null;
				}
			}
			//请选择奖励计算方式
			if(return_type < 0){
				JSONUtils.printStr("5");
				return null;
			}
			//开始日期不能大于结束日期
//			if(end_time.before(start_time)){
//				JSONUtils.printStr("6");
//				return null;
//			}
			//第一层返现比例不能为空
			if(brokerage_one <= 0){
				JSONUtils.printStr("7");
				return null;
			}
			//第二层返现比例不能大于第一层返现比例
			if(brokerage_two > brokerage_one){
				JSONUtils.printStr("9");
				return null;
			}
			//下限值不能大于上限值
			if(down_limit > 0 && down_limit > up_limit){
				JSONUtils.printStr("6");
				return null;
			}
			//有效日期不能为空
			if(StringUtils.isBlank(start_time) || StringUtils.isBlank(end_time)){
				JSONUtils.printStr("8");
				return null;
			}
			//奖励发放方式不能为空
			if(give_way < 0){
				JSONUtils.printStr("14");
				return null;
			}
			//是否启用不能为空
			if(isopne < 0){
				JSONUtils.printStr("15");
				return null;
			}
			//奖励层级不能为空
			if(StringUtils.isBlank(reward_level)){
				JSONUtils.printStr("16");
				return null;
			}
			
			result = rewardService.addRewardSetting(reward_src, user_type, min_invest_amount, max_invest_amount, deadline_start, 
					deadline_end, deadline_unit, borrow_type, reward_level,return_type, brokerage_one, up_limit, down_limit, brokerage_two, 
					start_time, end_time, give_way, reward_valid, reward_valid_unit, amount_invest_sum, reg_sum, isopne);
			//添加成功
			if(result > 0){
				JSONUtils.printStr("-1");
			}else{
				JSONUtils.printStr("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  推广奖励修改
	 * @return
	 * @throws Exception
	 */
	public String updateRewardSetting(){
		//修改id
		int id =  Convert.strToInt(paramMap.get("id"), -1);
		int reward_src =  Convert.strToInt(paramMap.get("reward_src"), -1);//推荐类型
		int user_type =  Convert.strToInt(paramMap.get("user_type"), -1);//用户类型
		double min_invest_amount = Convert.strToDouble(paramMap.get("min_invest_amount"), 0.00);//最小投资金额
		double max_invest_amount = Convert.strToDouble(paramMap.get("max_invest_amount"), 0.00);//最大投资金额
		int deadline_start = Convert.strToInt(paramMap.get("deadline_start"), -1);//投资期限开始
		int deadline_end = Convert.strToInt(paramMap.get("deadline_end"), -1);//投资期限结束
		int deadline_unit = Convert.strToInt(paramMap.get("deadline_unit"), -1);//投资期限单位
		String reward_level = paramMap.get("reward_level");//奖励层级
		String borrow_type = paramMap.get("borrow_type");//标种
		int return_type = Convert.strToInt(paramMap.get("return_type"), -1);//奖励计算方式
		double brokerage_one = Convert.strToDouble(paramMap.get("brokerage_one"), 0.00);//第一层返现比例
		double up_limit = Convert.strToDouble(paramMap.get("up_limit"), 0.00);//返现上限
		double down_limit = Convert.strToDouble(paramMap.get("down_limit"), 0.00);//返现下限
		double brokerage_two = Convert.strToDouble(paramMap.get("brokerage_two"), 0.00);//第二层返现比例
		String start_time = paramMap.get("start_time");//有效日期
		String end_time = paramMap.get("end_time");
		int reward_valid = Convert.strToInt(paramMap.get("reward_valid"), -1);//推荐投资奖励有效期
		int reward_valid_unit = Convert.strToInt(paramMap.get("reward_valid_unit"), -1);//推荐有效奖励单位
		double amount_invest_sum = Convert.strToDouble(paramMap.get("amount_invest_sum"), 0.00);//累计投资额度
		int give_way = Convert.strToInt(paramMap.get("give_way"), -1);//奖励发放方式
		int reg_sum = Convert.strToInt(paramMap.get("reg_sum"), -1);//累计推荐注册个数
		int isopne = Convert.strToInt(paramMap.get("isopne"), -1);//是否启用
		
		Long result = -1L;
		try {
			//请选择推荐类型
			if(reward_src < 0){
				JSONUtils.printStr("1");
				return null;
			}
			//用户类型不能为空
			if(user_type < 0){
				JSONUtils.printStr("12");
				return null;
			}
			//累计推荐注册个数不能为空
			if(2 == reward_src && reg_sum < 0){
				JSONUtils.printStr("13");
				return null;
			}
			//推荐单次投资下的校验
			if(3 == reward_src){
				//最小投资金额不能大于最大投资金额
				if(max_invest_amount <= 0 || min_invest_amount > max_invest_amount){
					JSONUtils.printStr("2");
					return null;
				}
				//投资期限不正确
				if(deadline_start < 0 || deadline_end < 0){
					JSONUtils.printStr("3");
					return null;
				}
				//请选择标种
				if(StringUtils.isBlank(borrow_type)){
					JSONUtils.printStr("4");
					return null;
				}
			}
			//累计投资额度不能为空
			if(4 == reward_src && amount_invest_sum <= 0){
				JSONUtils.printStr("11");
				return null;
			}
			//推荐投资奖励有效期
			if(3 == reward_src || 4 == reward_src){
				if(reward_valid < 0){
					JSONUtils.printStr("10");
					return null;
				}
			}
			//请选择奖励计算方式
			if(return_type < 0){
				JSONUtils.printStr("5");
				return null;
			}
			//开始日期不能大于结束日期
//			if(end_time.before(start_time)){
//				JSONUtils.printStr("6");
//				return null;
//			}
			//第一层返现比例不能为空
			if(brokerage_one <= 0){
				JSONUtils.printStr("7");
				return null;
			}
			//第二层返现比例不能大于第一层返现比例
			if(brokerage_two > brokerage_one){
				JSONUtils.printStr("9");
				return null;
			}
			//下限值不能大于上限值
			if(down_limit > 0 && down_limit > up_limit){
				JSONUtils.printStr("6");
				return null;
			}
			//有效日期不能为空
			if(StringUtils.isBlank(start_time) || StringUtils.isBlank(end_time)){
				JSONUtils.printStr("8");
				return null;
			}
			//奖励发放方式不能为空
			if(give_way < 0){
				JSONUtils.printStr("14");
				return null;
			}
			//是否启用不能为空
			if(isopne < 0){
				JSONUtils.printStr("15");
				return null;
			}
			//奖励层级不能为空
			if(StringUtils.isBlank(reward_level)){
				JSONUtils.printStr("16");
				return null;
			}
			
			result = rewardService.updateRewardSetting(id,reward_src, user_type, min_invest_amount, max_invest_amount, deadline_start, 
					deadline_end, deadline_unit, borrow_type, reward_level,return_type, brokerage_one, up_limit, down_limit, brokerage_two, 
					start_time, end_time, give_way, reward_valid, reward_valid_unit, amount_invest_sum, reg_sum, isopne);
			//修改成功
			if(result > 0){
				JSONUtils.printStr("-1");
			}else{
				JSONUtils.printStr("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	public RewardService getRewardService() {
		return rewardService;
	}

	public void setRewardService(RewardService rewardService) {
		this.rewardService = rewardService;
	}
	
}
