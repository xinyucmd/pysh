package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.CaseType;

/**
 * Manages the Case Type dropdown list
 * 
 */
public class CaseTypeAction extends OptionAction<CaseType> {

	private static final long serialVersionUID = -2404576552417042445L;

	@Override
	protected Class<CaseType> getEntityClass() {
		return CaseType.class;
	}

}
