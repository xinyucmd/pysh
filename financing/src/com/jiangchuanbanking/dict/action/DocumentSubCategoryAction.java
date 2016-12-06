package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.DocumentSubCategory;

/**
 * Manages the Document SubCategory dropdown list
 * 
 */
public class DocumentSubCategoryAction extends
		OptionAction<DocumentSubCategory> {

	private static final long serialVersionUID = -2404576552417042445L;

	@Override
	protected Class<DocumentSubCategory> getEntityClass() {
		return DocumentSubCategory.class;
	}

}
