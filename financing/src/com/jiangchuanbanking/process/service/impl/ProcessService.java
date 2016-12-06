package com.jiangchuanbanking.process.service.impl;



import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jiangchuanbanking.base.service.impl.BaseService;


import com.jiangchuanbanking.process.domain.FlowIdea;
import com.jiangchuanbanking.process.domain.WealthFlow;
import com.jiangchuanbanking.process.service.IProcessService;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.AuthenticationSuccessListener;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.DateTimeUtil;
import com.opensymphony.xwork2.ActionContext;


public class ProcessService  extends BaseService implements IProcessService{

	

	@Override
	public void saveOrUpdatePro(PactInfo pactInfo,String wfType,String sts) {
		
		//流程表
		WealthFlow wf=new WealthFlow();		
		wf.setCif_no(pactInfo.getCustomer().getId());
		wf.setClaims_pact_no(pactInfo.getClaims_pact_no());
		wf.setCif_name(pactInfo.getCustomer().getCif_name());
		wf.setPact_no(pactInfo.getPact_no());
		wf.setPrdt_name(pactInfo.getPrdt_name());	
		wf.setWorkflow_type(wfType);
		wf.setStatus(sts);
		User loginUser = this.getLoginUser();
		wf.setOp_no(loginUser.getId());
		wf.setOp_name(loginUser.getName());
		if (wf.getCreate_time()==null) {
			wf.setCreate_time(new Date());
		}
		wf.setUpdate_time(new Date());
		wf=(WealthFlow) this.makePersistent(wf);
		
		//流程意见表
		FlowIdea fi=new FlowIdea();
		fi.setWealthFlow(wf);
		fi.setFlow_type(wfType);
		fi.setApp_desc(sts);
		fi.setApp_idea("1");
		fi.setOp_no(loginUser.getId());
		fi.setCreate_date(new Date());
		fi.setCmt(pactInfo.getCmt());
		this.makePersistent(fi);
		
	}
	
	@Override
	public void updatePro(PactInfo pactInfo,String wfType,String sts,String ideaSts,String idea,String cmt) {
		
		//更新流程表
		String hql2="from WealthFlow where WORKFLOW_NO = '"+pactInfo.getPact_no()+"'";
		List<WealthFlow> list2=this.findByHQL(hql2);
		WealthFlow wf=list2.get(0);
		if (pactInfo.getClaims_pact_no()!=null) {
			wf.setClaims_pact_no(pactInfo.getClaims_pact_no());
		}
		wf.setStatus(sts);
		wf.setWorkflow_type(wfType);
		wf.setUpdate_time(new Date());
		wf=(WealthFlow) this.makePersistent(wf);
		
		
		//更新流程意见表
		FlowIdea fi=new FlowIdea();
		fi.setWealthFlow(wf);
		fi.setFlow_type(wfType);
		fi.setApp_desc(ideaSts);
		fi.setApp_idea(idea);
		fi.setOp_no(this.getLoginUser().getId());
		fi.setCreate_date(new Date());
		fi.setCmt(cmt);
		this.makePersistent(fi);
		
	}
	
	

	protected User getLoginUser() {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		User loginUser = (User) session
				.get(AuthenticationSuccessListener.LOGIN_USER);
		return loginUser;
	}


	@Override
	public WealthFlow createPro(PactInfo pactInfo, String wfType) {
		WealthFlow wf=new WealthFlow();
		wf.setCif_no(pactInfo.getCustomer().getId());
		wf.setClaims_pact_no(pactInfo.getClaims_pact_no());
		wf.setCif_name(pactInfo.getCustomer().getCif_name());
		wf.setPact_no(pactInfo.getPact_no());
		wf.setPrdt_no(pactInfo.getPrdt_no());
		wf.setPrdt_name(pactInfo.getPrdt_name());	
		wf.setWorkflow_type(wfType);
		wf.setStatus("5");
		wf.setOp_no(CommonUtil.getLoginUserId());
		wf.setOp_name(CommonUtil.getLoginUserName());
		wf.setCreate_time(new Date());
		return (WealthFlow) this.makePersistent(wf);
	}

	@Override
	public String getCmt(PactInfo pactInfo) {
		if ("4".equals(pactInfo.getSts())||"5".equals(pactInfo.getSts())) {
			FlowIdea fi=(FlowIdea) this.findByHQL("from FlowIdea where WORKFLOW_NO='"+pactInfo.getPact_no()+"' and APP_DESC='10' and APP_IDEA='1'").get(0);
			if (fi==null) {
				return "";
			}
			return  CommonUtil.fromNullToEmpty(fi.getCmt());
		}
		return "";
	}

	@Override
	public String getCheckCmt(PactInfo pactInfo) {
		if ("4".equals(pactInfo.getSts())) {
			List<FlowIdea> fi= this.findByHQL("from FlowIdea where WORKFLOW_NO='"+pactInfo.getPact_no()+"' and APP_DESC='30' and APP_IDEA='2' order by create_date desc");
			if (fi==null||fi.size()==0) {
				return "";
			}
			return  CommonUtil.fromNullToEmpty(fi.get(0).getCmt());
		}
		return "";
	}


	
	

	
	
	
	
	
	
	
	
	
	

	
	
}
