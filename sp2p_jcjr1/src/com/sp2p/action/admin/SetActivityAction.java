package com.sp2p.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.web.CacheManager;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.service.SettingActivityService;

/**
 * 活动管理
 * @author guojingchao
 *
 */
public class SetActivityAction extends BasePageAction{
	
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(NewsAction.class);
	
	
	private SettingActivityService settingActivityService;
	
	public SettingActivityService getSettingActivityService() {
		return settingActivityService;
	}

	public void setSettingActivityService(SettingActivityService settingActivityService) {
		this.settingActivityService = settingActivityService;
	}
	/**
	 * 查询活动设置初始化
	 * @return
	 */
	 
	public String querySetActivityInit(){
		return SUCCESS;
	}
	
	/**
	 * 分页查询活动设置信息集合
	 * 
	 * @author guojingchao
	 * @date 2014-12-09
	 * @return
	 */
	public String querySetActivityList() throws Exception{
		 try {
			 settingActivityService.querySetActivityPage(pageBean);
			 int pageNum = (int) (pageBean.getPageNum() - 1)
						* pageBean.getPageSize();
				request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		 
		 return SUCCESS;
	}
	
	/**
	 * 根据ID查询活动设置条记录
	 * 
	 * @author guojingchao
	 * @date 2014-12-09
	 * @return
	 * @throws Exception
	 */
	public String querySettingActivityById() throws Exception{
		Long id = request.getLong("id", -1);
		try {
			paramMap =	settingActivityService.querySettingActivityById(id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 更新活动设置条数据
	 * @return
	 * @throws Exception
	 */
	public String updateSettingActivity() throws Exception{
		Long id = Long.parseLong(paramMap.get("id"));
		String startTime = paramMap.get("start_time");
		String endTime = paramMap.get("end_time");
		String userId = paramMap.get("user_id");
		String process = paramMap.get("process");
		String message = "更新失败";
		try {
			Long result = settingActivityService.updateSettingActivity(id, startTime, endTime, userId,process);
			if (result > 0) {
				message = "更新成功";
				// 清空分页，列表数据，当前明细
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INDEX);
				CacheManager.clearByKey(IConstants.CACHE_WZGG_WZDT);
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INFO_ + id);
				CacheManager.clearStartsWithAll(IConstants.CACHE_WZGG_PAGE_);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
}
