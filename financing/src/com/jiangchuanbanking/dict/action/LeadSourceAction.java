package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.LeadSource;

/**
 * Manages the Lead Source dropdown list
 * 
 */
public class LeadSourceAction extends OptionAction<LeadSource> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<LeadSource> getEntityClass() {
        return LeadSource.class;
    }

}
