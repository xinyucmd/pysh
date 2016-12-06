package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.Salutation;

/**
 * Manages the Salutation dropdown list
 * 
 */
public class SalutationAction extends OptionAction<Salutation> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<Salutation> getEntityClass() {
        return Salutation.class;
    }

}
