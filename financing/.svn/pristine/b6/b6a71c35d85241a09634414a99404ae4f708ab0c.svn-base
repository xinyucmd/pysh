package com.jiangchuanbanking.subscription.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.jiangchuanbanking.base.dao.impl.BaseDao;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.subscription.dao.ISubscripDao;
import com.jiangchuanbanking.subscription.domain.ListSubscrip;

public class SubscripDao extends BaseDao implements ISubscripDao {

	
	@SuppressWarnings("unchecked")
	public SearchResult<ListSubscrip> getSubscripObjects(final String clazz,
            final String columns, final SearchCondition searchCondition) {

        List<ListSubscrip> objects = null;

        final String condition = searchCondition.getCondition();

        objects = getHibernateTemplate().executeFind(
                new HibernateCallback<List<ListSubscrip>>() {

                    public List<ListSubscrip> doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        StringBuilder hqlBuilder = new StringBuilder("");
                        hqlBuilder.append("select  new com.jiangchuanbanking.subscription.domain.ListSubscrip(p.prdt_name,p.customer.cifName,p.customer.idNo,p.customer.sex)" +
                        		"from com.jiangchuanbanking.subscription.domain.PactInfo p ");

                        if (condition != null && condition.length() > 0) {
                            hqlBuilder.append(" where ");
                            hqlBuilder.append(condition);
                        }
                        hqlBuilder.append(" order by ")
                                .append(searchCondition.getSidx()).append(" ")
                                .append(searchCondition.getSord());
                        int pageSize = searchCondition.getPageSize();
                        int pageNo = searchCondition.getPageNo();

                        Query query = session.createQuery(hqlBuilder.toString());

                        if (pageNo != 0 && pageSize != 0) {
                            int rowNumber = (pageNo - 1) * pageSize;
                            query.setFirstResult(rowNumber);
                            query.setMaxResults(pageSize);
                        }
                        List<ListSubscrip> list = query.list();

                        return list;
                    }
                });

        long count = 0;
        String countHql = "select count(*) from " + clazz;
        if (condition != null && condition.length() > 0) {
            countHql += " where ";
            countHql += condition;
        }

        count = (Long) getHibernateTemplate().find(countHql).get(0);
        SearchResult<ListSubscrip> result = new SearchResult<ListSubscrip>(count, objects);

        return result;
    }

	@Override
	public String getPactNo() {
		String sql="select pactno_sequence.nextval from dual";
		SQLQuery query = this.getSession().createSQLQuery(sql);  	
		return query.uniqueResult().toString();
	}
	
	
	
	

}
