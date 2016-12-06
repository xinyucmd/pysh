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
	 * ������������ѯ��¼��������Ϊ1����δ��¼��ͬ����
	 * 
	 * @param br_no
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @�޸���־��
	 */
	public int getCount1(ZcLnPact lnpact) ;

	/**
	 * 
	 * ������������ѯ��¼��������Ϊ2����δ��¼��ͬ����
	 * 
	 * @param br_no
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @�޸���־��
	 */

	public int getCount2(String br_no) ;

	/**
	 * 
	 * ������������ѯ��¼��������Ϊ3����δ��¼��ͬ����
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
	 * ������������ѯ��¼��������Ϊ4����δ��¼��ͬ����
	 * 
	 * @param br_no
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @�޸���־��
	 */

	public int getCount4() ;

	/**
	 * ����������ҵ���ƽ�ʱ����ƽ���ϸʱ��ѯҪ�ƽ������ͬ
	 * 
	 * @param ipg
	 * @param zclnPact
	 * @return
	 * @throws ServiceException
	 */
	/** @�޸���־�� ����Ʒ��ѯ������ �鿴��ͬ��Ϣר��
	 * @return
	 * @throws Exception
	 */
	public List<ZcLnPact> listByPage(Map map) ;

	public List<ZcLnPact> findByPage1(Map map) ;

	public List<ZcLnPact> findByPage(Map map) ;

	/**
	 * 
	 * ������������ҳ��ѯ��¼��������Ϊ1��δ��¼��ͬ
	 * 
	 * @param map
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @�޸���־��
	 */
	
	public List<ZcLnPact> listUnPactByPage1(Map map) ;
	/**
	 * 
	* @Description: ������0�Ĵ����ͬ 
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
	 * ������������ҳ��ѯ��¼��������Ϊ2��δ��¼��ͬ
	 * 
	 * @param map
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @�޸���־��
	 */

	public List<ZcLnPact> listUnPactByPage2(Map map) ;

	/**
	 * 
	 * ������������ҳ��ѯ��¼��������Ϊ3��δ��¼��ͬ
	 * 
	 * @param map
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @�޸���־��
	 */

	public List<ZcLnPact> listUnPactByPage3(Map map) ;

	/**
	 * 
	 * ������������ҳ��ѯ��¼��������Ϊ4��δ��¼��ͬ
	 * 
	 * @param map
	 * @return
	 * @
	 * @author luke
	 * @date Dec 29, 2010
	 * @�޸���־��
	 */

	public List<ZcLnPact> listUnPactByPage4(Map map) ;

	public List<ZcLnPact> lnPact(String cif_no) ;



	/**
	 * ����������������Ŀ�鿴���� ln_pact��apply_Base�����
	 * @param map
	 * @return
	 */
	public List<ZcLnPact> findByPage_applyBase(HashMap<String, Object> map)  ;

	/**
	 * ͨ����ͬ�Ų�ѯ�����Ʒ����
	 * @param pact_no
	 * @return
	 */
	public String findLnTypebyPactNo(String pact_no) ;

	/**
	 * ���¡���ͬ������Ϣ��LN_PACT��
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