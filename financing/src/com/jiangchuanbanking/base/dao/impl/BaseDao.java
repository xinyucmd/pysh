package com.jiangchuanbanking.base.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cc.dfsoft.ranqi.bo.HibernateSessionFactory;

import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;

/**
 * Base Dao
 */
public class BaseDao<T extends Serializable> extends HibernateDaoSupport
		implements IBaseDao<T> {

	private static String SELECT_HQL = "select ";
	private static String FROM_HQL = "from ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#getAllObjects(java.lang.String)
	 */
	public List<T> getAllObjects(String clazz) {
		return getAllObjects(clazz, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gcrm.dao.IBaseDao#getAllObjects(java.lang.String,java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAllObjects(String clazz, String columns) {
		StringBuilder hqlBuilder = new StringBuilder("");
		if (columns != null) {
			hqlBuilder.append(SELECT_HQL).append(columns).append(" ");
		}

		hqlBuilder.append(FROM_HQL).append(clazz);
		List<T> objects = null;

		objects = getHibernateTemplate().find(hqlBuilder.toString());
        
		return objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#getAllSortedObjects(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public List<T> getAllSortedObjects(String clazz, String sortColumn,
			String order) {
		return getAllSortedObjects(clazz, null, sortColumn, order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#getAllSortedObjects(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAllSortedObjects(String clazz, String columns,
			String sortColumn, String order) {
		StringBuilder hqlBuilder = new StringBuilder("");
		if (columns != null) {
			hqlBuilder.append(SELECT_HQL).append(columns).append(" ");
		}

		hqlBuilder.append(FROM_HQL).append(clazz).append(" order by ")
				.append(sortColumn).append(" ").append(order);
		List<T> objects = null;

		objects = getHibernateTemplate().find(hqlBuilder.toString());

		return objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#findByParam(java.lang.String,
	 * java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByParam(String hql, Object paramValue) {

		List<T> objects = null;

		objects = getHibernateTemplate().find(hql, paramValue);
		return objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#findByParams(java.lang.String,
	 * java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByParams(String hql, Object[] paramValues) {

		List<T> objects = null;

		objects = getHibernateTemplate().find(hql, paramValues);
		return objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#countsByParams(java.lang.String,
	 * java.lang.Object[])
	 */
	public long countsByParams(String hql, Object[] paramValues) {
		long count = 0;
		count = (Long) getHibernateTemplate().find(hql, paramValues).get(0);
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#findByHQL(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByHQL(String hql) {

		List<T> objects = null;

		objects = getHibernateTemplate().find(hql);
		return objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#findVOByHQL(java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes" })
	public List findVOByHQL(String hql) {

		List objects = null;

		objects = getHibernateTemplate().find(hql);
		return objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#findVOByParams(java.lang.String,
	 * java.lang.Object[])
	 */
	@SuppressWarnings({ "rawtypes" })
	public List findVOByParams(String hql, Object[] paramValues) {

		List objects = null;

		objects = getHibernateTemplate().find(hql, paramValues);
		return objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#findByName(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T findByName(String clazz, String name) {
		String hql = FROM_HQL + clazz + " where name = ?";
		T object = null;
		List result = null;

		result = findByParam(hql, name);
		if (result != null && result.size() > 0) {
			object = (T) result.get(0);
		}
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#makePersistent(java.io.Serializable)
	 */
	public T makePersistent(T entity) {
		T result = getHibernateTemplate().merge(entity);
		getHibernateTemplate().flush();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#batchUpdate(java.util.Collection)
	 */
	public void batchUpdate(Collection<T> entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
		getHibernateTemplate().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#deleteEntity(java.lang.Class,
	 * java.lang.Integer)
	 */
	public void deleteEntity(Class<T> entityClass, Integer id) {
		T entity = getHibernateTemplate().get(entityClass, id);
		getHibernateTemplate().delete(entity);
		getHibernateTemplate().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#getObjectsCount(java.lang.String)
	 */
	public long getObjectsCount(String clazz) {
		String hql = "select count(*) from " + clazz;

		long count = 0;

		count = (Long) getHibernateTemplate().find(hql).get(0);

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#getEntityById(java.lang.Class,
	 * java.lang.Integer)
	 */
	public T getEntityById(Class<T> entityClass, Integer id) {
		T entity = null;
		entity = getHibernateTemplate().load(entityClass, id);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#getPaginationObjects(java.lang.String,
	 * com.gcrm.vo.SearchCondition)
	 */
	public SearchResult<T> getPaginationObjects(String clazz,
			SearchCondition searchCondition) {
		return getPaginationObjects(clazz, null, searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#getPaginationObjects(java.lang.String,
	 * java.lang.String, com.gcrm.vo.SearchCondition)
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<T> getPaginationObjects(final String clazz,
			final String columns, final SearchCondition searchCondition) {

		List<T> objects = null;

		final String condition = searchCondition.getCondition();

		objects = getHibernateTemplate().executeFind(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuilder hqlBuilder = new StringBuilder("");
						if (columns != null) {
							hqlBuilder.append(SELECT_HQL).append(columns)
									.append(" ");
						}
						hqlBuilder.append(FROM_HQL).append(clazz);
						if (condition != null && condition.length() > 0) {
							hqlBuilder.append(" where ");
							hqlBuilder.append(condition);
						}
//						hqlBuilder.append(" order by ")
//								.append(searchCondition.getSidx()).append(" ")
//								.append(searchCondition.getSord());
						int pageSize = searchCondition.getPageSize();
						int pageNo = searchCondition.getPageNo();

						Query query = session.createQuery(hqlBuilder.toString());

						if (pageNo != 0 && pageSize != 0) {
							int rowNumber = (pageNo - 1) * pageSize;
							query.setFirstResult(rowNumber);
							query.setMaxResults(pageSize);
						}
						List<T> list = query.list();

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
		SearchResult<T> result = new SearchResult<T>(count, objects);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#getObjects(java.lang.String, java.lang.String)
	 */
	public List<T> getObjects(String clazz, String condition) {
		return getObjects(null, clazz, condition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gcrm.dao.IBaseDao#getObjects(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<T> getObjects(final String clazz, final String columns,
			final String condition) {

		List<T> objects = null;

		objects = getHibernateTemplate().executeFind(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						StringBuilder hqlBuilder = new StringBuilder("");
						if (columns != null) {
							hqlBuilder.append(SELECT_HQL).append(columns)
									.append(" ");
						}
						hqlBuilder.append(FROM_HQL).append(clazz);
						if (condition != null && condition.length() > 0) {
							hqlBuilder.append(" where ");
							hqlBuilder.append(condition);
						}
						Query query = session.createQuery(hqlBuilder.toString());

						List<T> list = query.list();

						return list;
					}
				});

		return objects;
	}

	@Override
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);

	}
	
	
	public int executeSQL(String sql,Map<String, Object> params) {  
		SQLQuery sqlquery = getSession().createSQLQuery(sql);  
	    if (params != null) {  
		           sqlquery.setProperties(params);//传值  
		  }  
		  return sqlquery.executeUpdate();  
    }
	
	public T findEntityByHql(String hql){
		T object = null;
		List<T> result=getHibernateTemplate().find(hql);
		if (result != null && result.size() > 0) {
            object = (T) result.get(0);
        }
		return object;
	}
	
	public T getById(Class<T> entityClass,Serializable id){	
		return getHibernateTemplate().get((Class<T>) entityClass, id);
	}


}
