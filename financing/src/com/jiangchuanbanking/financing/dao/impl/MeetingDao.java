package com.jiangchuanbanking.financing.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;

import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.financing.dao.IMeetingDao;
import com.jiangchuanbanking.financing.domain.Meeting;

/**
 * Meeting DAO
 */
public class MeetingDao extends BaseDao<Meeting> implements IMeetingDao {

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.dao.IMeetingDao#findScheduleMeetings(java.util.Date)
     */
    @Override
    public List<Meeting> findScheduleMeetings(Date startDate) throws Exception {
        List<Meeting> meetings = this.findByParam(
                "from Meeting where reminder_email = true and start_date > ? ",
                startDate);
        for (Meeting meeting : meetings) {
            Hibernate.initialize(meeting.getContacts());
            Hibernate.initialize(meeting.getLeads());
            Hibernate.initialize(meeting.getUsers());
        }

        return meetings;
    }

}
