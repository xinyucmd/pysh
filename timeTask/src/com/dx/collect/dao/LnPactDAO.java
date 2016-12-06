package com.dx.collect.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dx.collect.bean.*;

public interface LnPactDAO {
	public ZcLnPact getById(String pact_no) ;

	public void del(String pact_no) ;

	public void insert(ZcLnPact lnpact) ;

	public void update(ZcLnPact lnpact) ;

	public int getCount(ZcLnPact lnpact) ;
	
	public int getCountfindByPage_applyBase(String lnitem_no) ;
	

	/**
	 * 
	 * 功能描述：查询登录机构级别为1级的未补录合同总数
	 * 
	 * @param br_no
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @修改日志：
	 */
	public int getCount1(ZcLnPact lnpact) ;

	/**
	 * 
	 * 功能描述：查询登录机构级别为2级的未补录合同总数
	 * 
	 * @param br_no
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @修改日志：
	 */

	public int getCount2(String br_no) ;

	/**
	 * 
	 * 功能描述：查询登录机构级别为3级的未补录合同总数
	 * 
	 * @param br_no
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * 
	 **/


	public int getCount3(String br_no) ;

	/**
	 * 
	 * 功能描述：查询登录机构级别为4级的未补录合同总数
	 * 
	 * @param br_no
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @修改日志：
	 */

	public int getCount4() ;

	/**
	 * 功能描述：业务移交时添加移交明细时查询要移交贷款合同
	 * 
	 * @param ipg
	 * @param zclnPact
	 * @return
	 * @throws ServiceException
	 */
	/** @修改日志： 担保品查询功能中 查看合同信息专用
	 * @return
	 * @throws Exception
	 */
	public List<ZcLnPact> listByPage(Map map) ;

	public List<ZcLnPact> findByPage1(Map map) ;

	public List<ZcLnPact> findByPage(Map map) ;

	/**
	 * 
	 * 功能描述：分页查询登录机构级别为1的未补录合同
	 * 
	 * @param map
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @修改日志：
	 */
	
	public List<ZcLnPact> listUnPactByPage1(Map map) ;
	/**
	 * 
	* @Description: 余额大于0的贷款合同 
	* @package app.creditapp.dao
	* @author zhaojiangfei
	* @date Mar 31, 2011 11:22:44 AM
	* @throws 
	* @param Ipage
	 */
	public List<ZcLnPact>  findByPageBal(Map map) ;

	public int getCountBal(ZcLnPact lnpact) ;

	/**
	 * 
	 * 功能描述：分页查询登录机构级别为2的未补录合同
	 * 
	 * @param map
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @修改日志：
	 */

	public List<ZcLnPact> listUnPactByPage2(Map map) ;

	/**
	 * 
	 * 功能描述：分页查询登录机构级别为3的未补录合同
	 * 
	 * @param map
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @修改日志：
	 */

	public List<ZcLnPact> listUnPactByPage3(Map map) ;

	/**
	 * 
	 * 功能描述：分页查询登录机构级别为4的未补录合同
	 * 
	 * @param map
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @修改日志：
	 */

	public List<ZcLnPact> listUnPactByPage4(Map map) ;

	public List<ZcLnPact> lnPact(String cif_no) ;



	/**
	 * 功能描述：合作项目查看贷款 ln_pact与apply_Base表关联
	 * @param map
	 * @return
	 */
	public List<ZcLnPact> findByPage_applyBase(HashMap<String, Object> map)  ;

	/**
	 * 通过合同号查询贷款产品类型
	 * @param pact_no
	 * @return
	 */
	public String findLnTypebyPactNo(String pact_no) ;

	/**
	 * 更新【合同主表信息，LN_PACT】
	 * @param zclnPact
	 * @return
	 */
	public int updateForBulu(ZcLnPact zclnPact)  ;


	public Integer getCountBySts(ZcLnPact lnpact) ;

	public Object findBySts(HashMap<String, Object> map) ;

	public void updateReturnSts(ZcLnPact lnpact) ;

	public Integer getCountByStsIn(ZcLnPact lnpact);

	public Object findByStsIn(HashMap<String, Object> map);

	public Object findByStsInWwd(HashMap<String, Object> map);

	public Integer getCountByStsInWwd(ZcLnPact lnpact);

	public Integer getCountByStsWwd(ZcLnPact lnpact);

	public Object findByStsWwd(HashMap<String, Object> map);
	
}