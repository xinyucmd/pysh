package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.CompanySize;

/**
 * Manages the Company Size dropdown list
 * 
 */
public class CompanySizeAction extends OptionAction<CompanySize> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<CompanySize> getEntityClass() {
        return CompanySize.class;
    }

}
