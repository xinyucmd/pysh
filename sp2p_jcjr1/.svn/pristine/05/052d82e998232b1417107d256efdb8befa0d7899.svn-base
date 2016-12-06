package com.sp2p.action.admin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.util.DesSecurityUtil;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.service.RechargeService;

/**
 * 后台线下充值
 * 
 * @author Administrator
 * 
 */
public class RechargeAdminAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(RechargeAdminAction.class);
	private static final long serialVersionUID = 1L;
	private RechargeService rechargeService;
	
	//whb充值状态
	private List<Map<String, Object>> results;
		
		
	public List<Map<String, Object>> getResults() {
		if (results == null) {
			results = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("resultId", -100);
			mp.put("resultValue", "---请选择---");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 1);
			mp.put("resultValue", "充值成功");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 0);
			mp.put("resultValue", "充值失败");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 3);
			mp.put("resultValue", "审核中");
			results.add(mp);
		}
		return results;
	}

	public void setResults(List<Map<String, Object>> results) {
		this.results = results;
	}

	public String queryxxRechargeInit() {
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String queryxxRechargeInfo() throws Exception {
		String userName = paramMap.get("userName");
		String realName = paramMap.get("realName");
		//whb添加审核状态
		String result = paramMap.get("result");

		rechargeService.queryRechareDetil(pageBean, userName, realName, result);
		
		List<Map<String,Object>> list = pageBean.getPage();
		if(list!=null){
			Iterator<Map<String,Object>> iter = list.iterator();
			int i = 0;
			while(iter.hasNext()){
				Map<String,Object> map = iter.next();
				list.get(i).put("ids", new DesSecurityUtil().encrypt(map.get("id").toString()));
				i++;
			}
			pageBean.setPage(list);
		}
		return SUCCESS;
	}

	public String queryrechargeAdminInit() throws Exception {
		long userid = Convert.strToLong(new DesSecurityUtil().decrypt(request("id")), -1);
		paramMap = rechargeService.queryupdateRechargeDetailById(userid);
		
		Map<String,String> map = paramMap;
		if(map!=null){
			map.put("ids", new DesSecurityUtil().encrypt(map.get("id").toString()));
			map.put("userIds", new DesSecurityUtil().encrypt(map.get("userId").toString()));
			paramMap = map;
		}
		
		request().setAttribute(
				"awardmoney",
				Convert.strToDouble(paramMap.get("award"), 0)
						* Convert.strToDouble(paramMap.get("rechargeMoney"), 0)
						* 0.01);

		return SUCCESS;
	}

	public String queryrechargeAdminInfo() {
		return SUCCESS;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}
}
