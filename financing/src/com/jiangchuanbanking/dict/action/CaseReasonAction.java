
package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.CaseReason;

/**
 * Manages the Case Reason dropdown list
 * 
 */
public class CaseReasonAction extends OptionAction<CaseReason> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<CaseReason> getEntityClass() {
        return CaseReason.class;
    }

}
