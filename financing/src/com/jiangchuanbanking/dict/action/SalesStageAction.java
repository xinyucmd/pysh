package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.SalesStage;

/**
 * Manages the Sales Stage dropdown list
 * 
 */
public class SalesStageAction extends OptionAction<SalesStage> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<SalesStage> getEntityClass() {
        return SalesStage.class;
    }

}
