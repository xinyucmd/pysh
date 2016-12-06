/**
 * Copyright (C) DXHM 版权所有
 * @文件名 BaseAction.java
 * @包名 com.dx.common.action
 * @说明 TODO(描述文件做什么)
 * @作者   乾之轩
 * @时间 2012-5-7 下午12:39:51
 * @版本 V1.0
 */ 
package com.dx.common.action;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.dx.common.bean.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @类名 BaseAction
 * @描述 公共action的基类
 * @作者 乾之轩
 * @日期 2012-5-7 下午12:39:51
 * ========修改日志=======
 *
 */
public class BaseAction extends  ActionSupport implements ServletRequestAware,
		ServletResponseAware, SessionAware {
	private static final long serialVersionUID = 7620009925942346126L;

	protected ActionContext context ;// 类及其子类所有,备用
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected SessionMap<String, Object> session;
	private PageBean pageBean;

	public void setServletRequest(HttpServletRequest request) {
		
		this.request = request;
		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		this.response.setContentType("text/html");
		this.response.setCharacterEncoding("utf-8");
		this.response.setHeader("Pragma", "no-cache");
		this.response.setHeader("Cache-Control", "no-cache, must-revalidate");
		this.response.setHeader("Pragma", "no-cache");
	}

	public void setSession(Map<String, Object> map) {
		this.session = (SessionMap<String, Object>) map;
	}
	
	
	public   BaseAction() {
		
	}
	
	
	
	
	protected PageBean getPageBean(){
		pageBean=new PageBean();
		//当前页
		String curPage = request.getParameter("page");
		// 页容量
		String pageSize = request.getParameter("rp");
		// 查询条件字段
		String searchByCol = request.getParameter("qtype");
		// 查询条件
		String searchByVal="";
		try {
			searchByVal = new String(request.getParameter("query").getBytes("UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//排序字段
		String orderByCol = request.getParameter("sortname");
		// desc or asc
		String orderByVal = request.getParameter("sortorder");
		//查询操作符
		@SuppressWarnings("unused")
		String searchOperator = request.getParameter("qop");
		pageBean=new PageBean();
		pageBean.setCurPage(curPage);
		pageBean.setPageSize(pageSize);
		pageBean.setSearchByCol(searchByCol);
		pageBean.setSearchByVal(searchByVal);
		pageBean.setOrderByCol(orderByCol);
		pageBean.setOrderByVal(orderByVal);
		pageBean.setSearchOperator("like");
		pageBean.setPageSizeStart(String.valueOf((Integer.parseInt(curPage) - 1) * Integer.parseInt(pageSize)));
		return pageBean;
	}
}

