package com.jiangchuanbanking.system.service;

import com.jiangchuanbanking.base.exception.ServiceException;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.system.domain.User;

/**
 * User service Interface
 */
public interface IUserService extends IBaseService<User> {

	/**
	 * Finds user by user name
	 * 
	 * @param userName
	 *            user name
	 * @return user entity
	 * @throws serviceException
	 */
	public User findByName(String username) throws ServiceException;

	/**
	 * Sends password to user when password is forgot
	 * 
	 * @param userName
	 *            user name
	 * @param email
	 *            email address
	 * @param subject
	 *            email subject
	 * @param content
	 *            email content
	 * @return success flag
	 * @throws Exception
	 * @throws serviceException
	 */
	public boolean forgetPassword(String username, String email,
			String subject, String content) throws ServiceException, Exception;

}
