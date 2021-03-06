package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.Capital;

/**
 * Manages the Capital dropdown list
 * 
 */
public class CapitalAction extends OptionAction<Capital> {

	private static final long serialVersionUID = -2404576552417042445L;

	@Override
	protected Class<Capital> getEntityClass() {
		return Capital.class;
	}

}
