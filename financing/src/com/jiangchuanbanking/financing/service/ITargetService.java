package com.jiangchuanbanking.financing.service;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.Target;

/**
 * Target service Interface
 */
public interface ITargetService extends IBaseService<Target> {
    /**
     * Converts Target into lead
     * 
     * @param id
     *            target instance id
     */
    public void convert(Integer id) throws Exception;

}
