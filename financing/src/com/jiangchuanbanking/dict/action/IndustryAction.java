package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.Industry;

/**
 * Manages the Industry dropdown list
 * 
 */
public class IndustryAction extends OptionAction<Industry> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<Industry> getEntityClass() {
        return Industry.class;
    }

}
