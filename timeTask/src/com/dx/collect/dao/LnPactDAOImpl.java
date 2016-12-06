package com.dx.collect.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dx.collect.bean.*;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import app.base.ServiceException;

public class LnPactDAOImpl extends SqlMapClientDaoSupport implements LnPactDAO {
	public void del(String pact_no)  {
			getSqlMapClientTemplate().delete("LnPact.del", pact_no);
	}

	public List<ZcLnPact> lnPact(String cif_no)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.findBylist", cif_no);
		return lnpactlist;
	}

	public List<ZcLnPact> findByPage(Map map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.findByPage", map);
		return lnpactlist;
	}
	/**
	 * 功能描述：业务移交时添加移交明细时查询要移交贷款合同
	 * @param ipg
	 * @param zclnPact
	 * @return
	 * @throws ServiceException
	 */
	public List<ZcLnPact> findByPage1(Map map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"LnPact.yeMove_findPage", map);
		return lnpactlist;
	}
	public ZcLnPact getById(String pact_no)  {
		ZcLnPact lnpact = null;
			lnpact = (ZcLnPact) getSqlMapClientTemplate().queryForObject(
					"LnPact.getById", pact_no);
		return lnpact;
	}


	public String findLnTypebyPactNo(String pact_no)  {
		String ln_type = "";
			ln_type =  (String) getSqlMapClientTemplate().queryForObject(
					"LnPact.findLnTypebyPactNo", pact_no);
		return ln_type;
	}
	
	/* (non-Javadoc)
	 * 功能描述：合作项目查看贷款 ln_pact与apply_Base表关联
	 * @see app.creditapp.dao.LnPactDAO#findByPage_applyBase(java.util.HashMap)
	 */
	public List<ZcLnPact> findByPage_applyBase(HashMap<String, Object> map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactconapplyBase.findByPage", map);
		return lnpactlist;
	}
	public int getCountfindByPage_applyBase(String lnitem_no)  {
		
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.findByPage_applyBase.count", lnitem_no);
		return count;
	}
	public int getCount(ZcLnPact lnpact)  {

		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.findPage.count", lnpact);
		return count;
	}
	/**
	 * 担保品查询功能中 查看合同信息专用
	 * @return
	 * @throws Exception
	 */
	public List<ZcLnPact> listByPage(Map map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.listBylist", map);
		return lnpactlist;
	}
	public int getCountList(ZcLnPact lnpact)  {

		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.findPage.count", lnpact);
		return count;
	}

	public void insert(ZcLnPact lnpact)  {
			getSqlMapClientTemplate().insert("LnPact.insert", lnpact);

	}

	public void update(ZcLnPact lnpact)  {
			getSqlMapClientTemplate().insert("LnPact.update", lnpact);
	}

	public int getCount1(ZcLnPact lnpact)  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.findPage.count1", lnpact);
		return count;
	}

	public List<ZcLnPact> listUnPactByPage1(Map map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.listUnPactByPage1", map);
		return lnpactlist;
	}

	public int getCount2(String br_no)  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.findPage.count2", br_no);
		return count;
	}

	public List<ZcLnPact> listUnPactByPage2(Map map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.listUnPactByPage2", map);
		return lnpactlist;
	}

	public int getCount3(String br_no)  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.findPage.count3", br_no);
		return count;
	}

	public List<ZcLnPact> listUnPactByPage3(Map map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.listUnPactByPage3", map);
		return lnpactlist;
	}


	public int getCount4()  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.findPage.count4");
		return count;
	}

	public List<ZcLnPact> listUnPactByPage4(Map map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.listUnPactByPage4", map);
		return lnpactlist;
		
	}


	@SuppressWarnings("unchecked")
	public List<ZcLnPact>  findByPageBal(Map map)  {
		// TODO Auto-generated method stub
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.findByPageBal", map);
		return lnpactlist;
	}

	public int getCountBal(ZcLnPact lnpact)  {
		// TODO Auto-generated method stub
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.findPage.countBal", lnpact);
		return count;
	}

	public int updateForBulu(ZcLnPact zclnPact) {
		int successCount=1;
			getSqlMapClientTemplate().insert("LnPact.update", zclnPact);
		return successCount;
	}





	public Integer getCountBySts(ZcLnPact lnpact)  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.getCountBySts.count", lnpact);
		return count;
	}

	public Object findBySts(HashMap<String, Object> map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.findBySts", map);
		return lnpactlist;
	}

	public void updateReturnSts(ZcLnPact lnpact)  {
			getSqlMapClientTemplate().update("LnPact.updateReturnSts", lnpact);
	}

	public Integer getCountByStsIn(ZcLnPact lnpact)  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.getCountByStsIn.count", lnpact);
		return count;
	}
	public Integer getCountByStsInWwd(ZcLnPact lnpact)  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.getCountByStsInWwd.count", lnpact);
		return count;
	}
	public Object findByStsIn(HashMap<String, Object> map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.findByStsIn", map);
		return lnpactlist;
	}

	public Object findByStsInWwd(HashMap<String, Object> map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.findByStsInWwd", map);
		return lnpactlist;
	}

	public Integer getCountByStsWwd(ZcLnPact lnpact)  {
		int count = 0;
			count = (Integer) getSqlMapClientTemplate().queryForObject(
					"LnPact.getCountByStsWwd.count", lnpact);
		return count;
	}

	public Object findByStsWwd(HashMap<String, Object> map)  {
		List lnpactlist = null;
			lnpactlist = getSqlMapClientTemplate().queryForList(
					"lnpactlist.findByStsWwd", map);
		return lnpactlist;
	}


}