
package com.jiangchuanbanking.financing.action;

import java.util.Map;

import com.jiangchuanbanking.base.exception.ServiceException;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.AuthenticationSuccessListener;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Home Action
 * 
 */
public class HomeAction extends ActionSupport {

	private static final long serialVersionUID = -2404576552417042445L;

	private Integer userID = null;
	
	/**
	 * Selects the entities
	 * 
	 * @return the SUCCESS result
	 */
	public String load() throws ServiceException {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		User loginUser = (User) session
				.get(AuthenticationSuccessListener.LOGIN_USER);
		this.userID = loginUser.getId();
		return SUCCESS;
	}

	/**
	 * @return the userID
	 */
	public Integer getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(Integer userID) {
		this.userID = userID;
	}

}
