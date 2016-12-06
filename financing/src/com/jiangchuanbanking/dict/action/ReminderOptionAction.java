package com.jiangchuanbanking.dict.action;

import com.jiangchuanbanking.dict.domain.ReminderOption;

/**
 * Manages the Reminder Option dropdown list
 * 
 */
public class ReminderOptionAction extends OptionAction<ReminderOption> {

    private static final long serialVersionUID = -2404576552417042445L;

    @Override
    protected Class<ReminderOption> getEntityClass() {
        return ReminderOption.class;
    }

}
