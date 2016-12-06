package com.jiangchuanbanking.system.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.AuthenticationFilter;
import com.jiangchuanbanking.system.security.AuthenticationSuccessListener;
import com.jiangchuanbanking.system.service.IUserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Change Password Action
 */
public class ChangePasswordAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private IUserService baseService;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

	@Override
	public String execute() throws Exception {
/*		if (!newPassword.equals(confirmPassword)) {
			this.addActionError(this.getText("changePassword.password.noequal"));
			return INPUT;
		}*/
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		User loginUser = (User) session
				.get(AuthenticationSuccessListener.LOGIN_USER);
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String encodePassword = encoder.encodePassword(oldPassword,
				AuthenticationFilter.SALT);
		/*if (!encodePassword.equals(loginUser.getPassword())) {
			this.addActionError(this
					.getText("changePassword.wrong.oldPassword"));
			return INPUT;
		}*/
		encodePassword = encoder.encodePassword(newPassword,
				AuthenticationFilter.SALT);
		loginUser.setPassword(encodePassword);
		baseService.makePersistent(loginUser);
//		this.addActionError(this.getText("changePassword.password.success"));
		return SUCCESS;
	}

	public String checkPasswordChange() throws IOException {
		String data = "yes";
		if (!newPassword.equals(confirmPassword)) {
			data="a";//确认！=新设置
		}
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		User loginUser = (User) session
				.get(AuthenticationSuccessListener.LOGIN_USER);
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String encodePassword = encoder.encodePassword(oldPassword,
				AuthenticationFilter.SALT);
		if (!encodePassword.equals(loginUser.getPassword())) {
			data="b";//旧密码不对
		}
		if ("yes".equals(data)) {
			encodePassword = encoder.encodePassword(newPassword,
					AuthenticationFilter.SALT);
			loginUser.setPassword(encodePassword);
			baseService.makePersistent(loginUser);
		}
		String jsonData = "[{\"data\":\"" + data + "\"}]";
		String json = JSONArray.fromObject(jsonData).toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		return null;
	}

	/**
	 * @return the baseService
	 */
	public IUserService getBaseService() {
		return baseService;
	}

	/**
	 * @param baseService
	 *            the baseService to set
	 */
	public void setBaseService(IUserService baseService) {
		this.baseService = baseService;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}