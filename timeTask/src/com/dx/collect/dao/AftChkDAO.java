package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import com.dx.collect.bean.AftChk;


/**
 * 贷后检查报告DAO接口
 * 
 * @author liuxiao
 * @date 2010-11-17
 * @see
 * @修改日志：
 */
public interface AftChkDAO {
	/**
	 * 根据贷后检查报告的唯一主键查询详细内容
	 * 
	 * @param id
	 *            唯一主键
	 * @return 贷款检查报告的字段信息
	 * @
	 */
	public AftChk getById(String id) ;

	/**
	 * 根据贷后检查报告的唯一主键删除报告信息
	 * 
	 * @param id
	 *            唯一主键
	 * @
	 */
	public void del(String id) ;

	/**
	 * 新增贷后检查报告信息
	 * 
	 * @param aftchk
	 *            贷后检查报告字段信息
	 * @
	 * 
	 */
	public void insert(AftChk aftchk) ;

	/**
	 * 修改贷后检查报告信息
	 * 
	 * @param aftchk
	 *            贷后检查报告字段信息
	 * @
	 */
	public void update(AftChk aftchk) ;
	public void update1(AftChk aftchk) ;

	/**
	 * 查询符合条件的贷后检查报告的总条数
	 * 
	 * @param aftchk
	 *            查询的条件
	 * @return 总条数
	 * @
	 */
	public int getCount(AftChk aftchk) ;

	/**
	 * 查询符合条件的的贷后检查报告列表
	 * 
	 * @param map
	 *            裝贷后检查报告的容器
	 * @return 贷后检查报告列表
	 * @
	 */
	public List<AftChk> findByPage(Map map) ;

	/**
	 * 根据根据合同号查询合同详细内容
	 * 
	 * @param pactNo
	 *            合同号
	 * @return 合同信息
	 * @
	 */
	public AftChk getAftChkByPactNo(String pactNo) ;

	/**
	 * 根据合同号查询贷后调查报告表是否存在该合同的首次调查报告
	 * @param pactNo
	 *               合同号
	 * @return 存在的条数
	 * @
	 */
	public int IsExistFirstAftChk(String pactNo) ;
	
	/**
	 * 根据产品号查询对应的业务品种类型
	 * @param prdtNo
	 *        产品号      
	 * @
	 */
	public String getLntype(String prdtNo) ;
	
	/**
	 * 根据机构号,查询机构级别，用于初始化
	 * @date    Aug 22, 2011
	 * @param   @param brno
	 * @param   @
	 */
	public String setBrnoLevDefault(String brno);

	public AftChk findByPact(AftChk aftchkBean);
}