package com.dx.collect.dao;

import java.util.List;
import java.util.Map;

import com.dx.collect.bean.AftChk;


/**
 * �����鱨��DAO�ӿ�
 * 
 * @author liuxiao
 * @date 2010-11-17
 * @see
 * @�޸���־��
 */
public interface AftChkDAO {
	/**
	 * ���ݴ����鱨���Ψһ������ѯ��ϸ����
	 * 
	 * @param id
	 *            Ψһ����
	 * @return �����鱨����ֶ���Ϣ
	 * @
	 */
	public AftChk getById(String id) ;

	/**
	 * ���ݴ����鱨���Ψһ����ɾ��������Ϣ
	 * 
	 * @param id
	 *            Ψһ����
	 * @
	 */
	public void del(String id) ;

	/**
	 * ���������鱨����Ϣ
	 * 
	 * @param aftchk
	 *            �����鱨���ֶ���Ϣ
	 * @
	 * 
	 */
	public void insert(AftChk aftchk) ;

	/**
	 * �޸Ĵ����鱨����Ϣ
	 * 
	 * @param aftchk
	 *            �����鱨���ֶ���Ϣ
	 * @
	 */
	public void update(AftChk aftchk) ;
	public void update1(AftChk aftchk) ;

	/**
	 * ��ѯ���������Ĵ����鱨���������
	 * 
	 * @param aftchk
	 *            ��ѯ������
	 * @return ������
	 * @
	 */
	public int getCount(AftChk aftchk) ;

	/**
	 * ��ѯ���������ĵĴ����鱨���б�
	 * 
	 * @param map
	 *            �b�����鱨�������
	 * @return �����鱨���б�
	 * @
	 */
	public List<AftChk> findByPage(Map map) ;

	/**
	 * ���ݸ��ݺ�ͬ�Ų�ѯ��ͬ��ϸ����
	 * 
	 * @param pactNo
	 *            ��ͬ��
	 * @return ��ͬ��Ϣ
	 * @
	 */
	public AftChk getAftChkByPactNo(String pactNo) ;

	/**
	 * ���ݺ�ͬ�Ų�ѯ������鱨����Ƿ���ڸú�ͬ���״ε��鱨��
	 * @param pactNo
	 *               ��ͬ��
	 * @return ���ڵ�����
	 * @
	 */
	public int IsExistFirstAftChk(String pactNo) ;
	
	/**
	 * ���ݲ�Ʒ�Ų�ѯ��Ӧ��ҵ��Ʒ������
	 * @param prdtNo
	 *        ��Ʒ��      
	 * @
	 */
	public String getLntype(String prdtNo) ;
	
	/**
	 * ���ݻ�����,��ѯ�����������ڳ�ʼ��
	 * @date    Aug 22, 2011
	 * @param   @param brno
	 * @param   @
	 */
	public String setBrnoLevDefault(String brno);

	public AftChk findByPact(AftChk aftchkBean);
}