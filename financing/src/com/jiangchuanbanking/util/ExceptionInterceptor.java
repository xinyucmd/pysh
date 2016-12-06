package com.jiangchuanbanking.util;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Exception interceptor
 */
public class ExceptionInterceptor implements Interceptor {

	private static final long serialVersionUID = -8471937545040722947L;

	private final Logger logger = Logger.getLogger(this.getClass());

	public void destroy() {
		// no logic
	}

	public void init() {
		// no logic
	}

	/**
	 * Intercepts exception
	 * 
	 * @param action
	 *            target action
	 * @return Action result
	 */
	public String intercept(ActionInvocation action) throws Exception {
		String result = Action.SUCCESS;

		try {
			result = action.invoke();
		} catch (Exception e) {
			result = Action.INPUT;
			logger.error(e.getMessage(), e);
			ActionSupport actionSupport = (ActionSupport) action.getAction();
			String errorMessage = e.getMessage();
			ResourceBundle rb = CommonUtil.getResourceBundle();
			if (e instanceof DataIntegrityViolationException) {
				errorMessage = rb.getString("error.message.head") + ":"
						+ rb.getString("error.message.violationException");
			} else if (e instanceof AccessDeniedException) {
				result = "accessDenied";
			}
			actionSupport.addActionError(errorMessage);
		}

		return result;
	}
}
