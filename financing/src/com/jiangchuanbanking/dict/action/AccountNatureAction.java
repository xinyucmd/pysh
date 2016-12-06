package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.AccountNature;

/**
 * Manages the Account Nature dropdown list
 * 
 */
public class AccountNatureAction extends OptionAction<AccountNature> {

	private static final long serialVersionUID = -2404576552417042445L;

	@Override
	protected Class<AccountNature> getEntityClass() {
		return AccountNature.class;
	}

}
