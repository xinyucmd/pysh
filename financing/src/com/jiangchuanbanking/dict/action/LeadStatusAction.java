package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.LeadStatus;

/**
 * Manages the Lead Status dropdown list
 * 
 */
public class LeadStatusAction extends OptionAction<LeadStatus> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<LeadStatus> getEntityClass() {
        return LeadStatus.class;
    }

}
