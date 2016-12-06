package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dx.collect.bean.AftChk;

public class AftChkDAOImpl extends SqlMapClientDaoSupport implements AftChkDAO {
	public void del(String id) {

		getSqlMapClientTemplate().delete("AftChk.del", id);
	}

	public List<AftChk> findByPage(Map map) {
		List<AftChk> aftchklist = null;

		aftchklist = getSqlMapClientTemplate().queryForList("AftChk.findByPage", map);
		return aftchklist;
	}

	public AftChk getById(String id) {
		AftChk aftchk = null;

		aftchk = (AftChk) getSqlMapClientTemplate().queryForObject("AftChk.getById", id);
		return aftchk;
	}

	public int getCount(AftChk aftchk) {

		int count = 0;

		count = (Integer) getSqlMapClientTemplate().queryForObject("AftChk.findPage.count", aftchk);
		return count;
	}

	public void insert(AftChk aftchk) {

		getSqlMapClientTemplate().insert("AftChk.insert", aftchk);

	}

	public void update(AftChk aftchk) {

		getSqlMapClientTemplate().insert("AftChk.update", aftchk);
	}

	public AftChk getAftChkByPactNo(String pactNo) {
		AftChk aftchk = null;

		aftchk = (AftChk) getSqlMapClientTemplate().queryForObject("LnPact.getLnPactByPactNoForAftChk", pactNo);
		return aftchk;
	}

	public int IsExistFirstAftChk(String pactNo) {
		int count = 0;

		count = (Integer) getSqlMapClientTemplate().queryForObject("AftChk.IsExistFirstAftChk", pactNo);
		return count;
	}

	public String getLntype(String parmprdtNo) {
		String lntype = "";

		lntype = (String) getSqlMapClientTemplate().queryForObject("AftChk.getLntypeWithPrdtNo", parmprdtNo);
		return lntype;
	}

	public void update1(AftChk aftChk) {

		getSqlMapClientTemplate().insert("AftChk.updateForChkId", aftChk);
	}

	public String setBrnoLevDefault(String brno) {
		String brno_lev = "";
		brno_lev = (String) getSqlMapClientTemplate().queryForObject("AftChk.getBrnoLev", brno);
		return brno_lev;
	}

	public AftChk findByPact(AftChk aftchkBean) {
		AftChk aftchk = null;

		aftchk = (AftChk) getSqlMapClientTemplate().queryForObject("AftChk.findByPact", aftchkBean);
		return aftchk;
	}
}