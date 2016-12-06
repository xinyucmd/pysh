package com.jiangchuanbanking.system.security;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.jiangchuanbanking.base.domain.BaseEntity;
import com.jiangchuanbanking.system.domain.Role;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.service.IUserService;
import com.jiangchuanbanking.util.BeanUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.spring.SpringContextUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * User util
 */
public class UserUtil {
    /**
     * Gets current login user name
     * 
     * @return curretn login user name
     */
    public static String getUserName() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            UserDetails userDetails = (UserDetails) authentication
                    .getPrincipal();
            return userDetails.getUsername();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets user by user name
     * 
     * @param userName
     *            user name
     * @return user instance
     */
    public static User getUser(String userName) {
        IUserService userService = (IUserService) SpringContextUtil
                .getBean("userService");
        StringBuilder hqlBuilder = new StringBuilder(
                "select new User(name,password) from User");
        hqlBuilder.append(" where name = ?");
        try {
            List<User> result = userService.findByParam(hqlBuilder.toString(),
                    userName);
            if (result == null) {
                return null;
            } else {
                return result.get(0);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static void setAccessValue(Role role, User user) throws Exception {

        Field[] fields = role.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.startsWith("scope_") || fieldName.startsWith("view_")
                    || fieldName.startsWith("create_")
                    || fieldName.startsWith("update_")
                    || fieldName.startsWith("delete_")) {
                Integer valueOfRole = (Integer) BeanUtil.getFieldValue(role,
                        fieldName);
                if (valueOfRole == null) {
                    continue;
                }
                Integer valueOfUser = (Integer) BeanUtil.getFieldValue(user,
                        fieldName);
                switch (valueOfRole) {
                case Role.NOT_SET:
                    if (valueOfUser == null) {
                        BeanUtil.setFieldValue(user, fieldName, Role.NOT_SET);
                    }
                    break;
                case Role.ALL_OR_ENABLED:
                    if (valueOfUser == null
                            || valueOfUser != Role.OWNER_OR_DISABLED) {
                        BeanUtil.setFieldValue(user, fieldName,
                                Role.ALL_OR_ENABLED);
                    }
                    break;
                case Role.OWNER_OR_DISABLED:
                    BeanUtil.setFieldValue(user, fieldName,
                            Role.OWNER_OR_DISABLED);
                    break;
                }
            }
        }

    }

    public static void permissionCheck(String fieldName) throws Exception {
        User loginUser = UserUtil.getLoginUser();
        Integer value = (Integer) BeanUtil.getFieldValue(loginUser, fieldName);
        if (value != Role.ALL_OR_ENABLED) {
            ResourceBundle rb = CommonUtil.getResourceBundle();
            String errorMessage = rb.getString("access.nopermission");
            throw new AccessDeniedException(errorMessage);
        }
    }

    public static void scopeCheck(BaseEntity entity, String fieldName)
            throws Exception {
        User loginUser = UserUtil.getLoginUser();
        Integer value = (Integer) BeanUtil.getFieldValue(loginUser, fieldName);
        if (value == Role.OWNER_OR_DISABLED) {
            if (loginUser.getId().intValue() != entity.getOwner().getId()
                    .intValue()) {
                ResourceBundle rb = CommonUtil.getResourceBundle();
                String errorMessage = rb
                        .getString("access.nopermission.record");
                throw new AccessDeniedException(errorMessage);
            }
        }
    }

    public static User getLoginUser() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        User loginUser = (User) session
                .get(AuthenticationSuccessListener.LOGIN_USER);
        return loginUser;
    }

}
