package com.jiangchuanbanking.financing.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.financing.dao.IMeetingDao;
import com.jiangchuanbanking.financing.domain.Meeting;
import com.jiangchuanbanking.financing.service.IMeetingService;

/**
 * Meeting service
 */
public class MeetingService extends BaseService<Meeting> implements
        IMeetingService {

    private IMeetingDao meetingDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.gcrm.service.IMeetingService#findScheduleMeetings(java.util.Date)
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Meeting> findScheduleMeetings(Date startDate) throws Exception {
        return meetingDao.findScheduleMeetings(startDate);
    }

    /**
     * @return the meetingDao
     */
    public IMeetingDao getMeetingDao() {
        return meetingDao;
    }

    /**
     * @param meetingDao
     *            the meetingDao to set
     */
    public void setMeetingDao(IMeetingDao meetingDao) {
        this.meetingDao = meetingDao;
    }

}
