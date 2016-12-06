package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.UserStatus;

/**
 * Manages the User Status dropdown list
 * 
 */
public class UserStatusAction extends OptionAction<UserStatus> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<UserStatus> getEntityClass() {
        return UserStatus.class;
    }

}
