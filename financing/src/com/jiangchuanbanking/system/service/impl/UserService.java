
package com.jiangchuanbanking.system.service.impl;

import java.util.List;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.base.exception.DaoException;
import com.jiangchuanbanking.base.exception.ServiceException;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.mail.service.MailService;
import com.jiangchuanbanking.system.dao.IUserDao;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.AuthenticationFilter;
import com.jiangchuanbanking.system.service.IUserService;
import com.jiangchuanbanking.util.CommonUtil;

/**
 * User service
 */
public class UserService extends BaseService<User> implements IUserService {

    private IUserDao userDao;
    private MailService mailService;

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.IUserService#findByName(java.lang.String)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public User findByName(String username) throws ServiceException {
        User user;
        try {
            user = this.userDao.findByName(username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.IUserService#forgetPassword(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean forgetPassword(String username, String email,
            String subject, String content) throws Exception {
        List<User> users = this.userDao.findByParams(
                "from User where name =  ? and email = ?", new String[] {
                        username, email });

        boolean flag = false;
        if (users != null & users.size() == 1) {
            // Generates a random user password
            String newPassword = CommonUtil.randomString(6);

            // Saves the new password
            User user = users.get(0);
            Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            user.setPassword(encoder.encodePassword(newPassword,
                    AuthenticationFilter.SALT));
            this.makePersistent(user);

            // Sends the new password to user
            mailService.sendSystemSimpleMail(email, subject, content
                    + newPassword);
            flag = true;
        }
        return flag;
    }

    /**
     * @return the userDao
     */
    public IUserDao getUserDao() {
        return userDao;
    }

    /**
     * @param userDao
     *            the userDao to set
     */
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * @return the mailService
     */
    public MailService getMailService() {
        return mailService;
    }

    /**
     * @param mailService
     *            the mailService to set
     */
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

}
