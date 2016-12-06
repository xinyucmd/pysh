package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.DocumentStatus;

/**
 * Manages the Document Status dropdown list
 * 
 */
public class DocumentStatusAction extends OptionAction<DocumentStatus> {

	private static final long serialVersionUID = -2404576552417042445L;

	@Override
	protected Class<DocumentStatus> getEntityClass() {
		return DocumentStatus.class;
	}

}
