package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.AccountType;

/**
 * Manages the Account Type dropdown list
 * 
 */
public class AccountTypeAction extends OptionAction<AccountType> {

	private static final long serialVersionUID = -2404576552417042445L;

	@Override
	protected Class<AccountType> getEntityClass() {
		return AccountType.class;
	}

}
