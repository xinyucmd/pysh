package com.dx.collect.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dx.collect.bean.*;

/**
 * 贷后检查报告DAO接口
 * 
 * @author liuxiao
 * @date 2010-11-17
 * @see
 * @修改日志：
 */
public interface CollectTaskDAO {

	public void insert(CollectTask ct) ;

	public List<CollectTask> findFirstReport(HashMap<String, Object> map);

	public Integer getCount(CollectTask ct);

	public Integer getFollowCount(CollectTask ct);

	public Object findFollowReport(HashMap<String, Object> map);

	public void setStatus(CollectTask ct);

	public List<CollectTask> findAllReport(CollectTask ct);

	public List<CollectTask> findByPact(CollectTask ctTemp);

	public CollectTask getById(String id);

	public Integer findCountByTermNo(CollectTask ct);

	public void updateTask(CollectTask ct);

	public void UpdateNotDone(CollectTask ct);
	
}