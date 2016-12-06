package com.jiangchuanbanking.system.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;

import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.base.exception.DaoException;
import com.jiangchuanbanking.system.dao.IUserDao;
import com.jiangchuanbanking.system.domain.User;

/**
 * User DAO
 */
public class UserDao extends BaseDao<User> implements IUserDao {

	public User findByName(String userName) throws DaoException {
		User user;

		List<User> users = this.findByParam("from User where name =  ? ",
				userName);

		if (users == null) {
			user = null;
		} else {
			user = users.get(0);
			Hibernate.initialize(user.getRoles());
		}
		return user;

	}

}
