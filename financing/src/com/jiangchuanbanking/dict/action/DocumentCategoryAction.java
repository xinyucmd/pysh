package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.DocumentCategory;

/**
 * Manages the Document Category dropdown list
 * 
 */
public class DocumentCategoryAction extends OptionAction<DocumentCategory> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<DocumentCategory> getEntityClass() {
        return DocumentCategory.class;
    }

}
