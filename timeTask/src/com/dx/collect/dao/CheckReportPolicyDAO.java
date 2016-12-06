package com.dx.collect.dao;

import java.util.List;
import java.util.Map;
import com.dx.collect.bean.*;

public interface CheckReportPolicyDAO {

	public CheckReportPolicy getById(String id) ;

	public List<CheckReportPolicy> findByPage(Map<String, Object> map)
			;

	public void del(String id) ;

	public void insert(CheckReportPolicy checkReportPolicy) ;

	public void update(CheckReportPolicy checkReportPolicy) ;

	public Integer getCount(CheckReportPolicy checkReportPolicy) ;

	public List<CheckReportPolicy> getReportPolicy(CheckReportPolicy crp) ;
}
