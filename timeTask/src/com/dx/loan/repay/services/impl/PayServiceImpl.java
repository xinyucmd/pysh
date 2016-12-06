
package com.dx.loan.repay.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import app.base.ServiceException;
import app.batch.entity.CustRepayDetails;

import com.dx.common.SystemParm;
import com.dx.common.bean.PageBean;
import com.dx.common.util.BigNumberUtil;
import com.dx.common.util.DateUtil;
import com.dx.common.util.SpringFactory;
import com.dx.common.util.StringUtil;
import com.dx.loan.dayrate.bean.RatedayBean;
import com.dx.loan.debt.bean.DebtBean;
import com.dx.loan.debt.services.impl.DebtServices;
import com.dx.loan.plan.bean.PlanBean;
import com.dx.loan.plan.bean.PlanParmBean;
import com.dx.loan.plan.services.impl.PlanServices;
import com.dx.loan.plan.services.impl.PlanServicesImpl;
import com.dx.loan.repay.bean.AcLnMstBean;
import com.dx.loan.repay.bean.DueBean;
import com.dx.loan.repay.bean.LnDue;
import com.dx.loan.repay.bean.RepayBean;
import com.dx.loan.repay.dao.IRepayDao;
import com.dx.loan.repay.services.IRepayService;
import com.google.gson.Gson;
/**
 * 
 * ������ PayServiceImpl
 * ������
 * @author Ǭ֮��
 * @date 2012-5-17 ����07:13:23
 * 
 *
 */
@WebService(endpointInterface = "com.dx.loan.repay.services.IRepayService")
public class PayServiceImpl implements IRepayService  {
	public  PayServiceImpl(){}
	
	private  IRepayDao repayDao;
	
	
	public void setRepayDao(IRepayDao repayDao) {
		this.repayDao = repayDao;
	}

	
/***
 * ��û���ʵ��
 * @param ratedayBean
 * @return
 */
	public RepayBean getRepayBean(RepayBean  repayBean) {
	  return	repayDao.getRepayBean(repayBean);
	}
/**
 * 
 * ���������� 
 * @param pageBean
 * 
 * @return
 * @author Ǭ֮��
 * @date 2012-5-17 ����07:23:51
 */
	public  String getRepayList(PageBean pageBean) {
		return toJSON(repayDao.getRepayList(pageBean),pageBean);
	}
	
	/**
	 * 
	 * ���������� ����������ļ�
	 * @param acLnMstBean
	 * @author Ǭ֮��
	 * @date 2012-5-30 ����07:03:06
	 */
	public void saveAcLnMstBean(AcLnMstBean acLnMstBean) {
			repayDao.saveAcLnMstBean(acLnMstBean);
	}
		/**
		 * 
		 * ����������  ��ô������ļ�
		 * @param acLnMstBean
		 * @return
		 * @author Ǭ֮��
		 * @date 2012-6-19 ����05:10:32
		 */
	public AcLnMstBean getAcLnMstBean(AcLnMstBean acLnMstBean) {
		return repayDao.getAcLnMstBean(acLnMstBean);
	} 	
		
		
	@SuppressWarnings("unchecked")
	private String toJSON(List<RepayBean> list, PageBean pageBean) {
		Map map = new HashMap();
		map.put("page", pageBean.getCurPage());
		map.put("total", pageBean.getTotalSize());
		List mapList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map cellMap = new HashMap();
			cellMap.put("dueNo", list.get(i).getDueNo());
			cellMap.put("cell", new Object[] {
					list.get(i).getDueNo(),
					list.get(i).getCifName(),
					list.get(i).getDueAmt(),
					list.get(i).getReturnCapital(),
					list.get(i).getReturnInterest(),
					list.get(i).getBegDate(),
					list.get(i).getEndDate(),
					list.get(i).getCifNo(),
					list.get(i).getTermNo(),
					list.get(i).getOtherFee()
					});
			mapList.add(cellMap);
		}
		map.put("rows", mapList);
		Gson gs=new Gson();
		String ja =gs.toJson(map);
		System.out.println(ja);
		return ja.toString();
	}

	
	
	/**
	 * 
	 * ���������� ���ĳ�ڻ���ƻ���ʣ�౾��  
	 * @param dueNo
	 * @param termNo
	 * @return
	 * @author Ǭ֮��
	 * @date 2012-5-23 ����06:02:53
	 */
	 public  String  remaCapital(PlanBean planBean){
		 String dueNo = planBean.getDueNo();
		 String termNo = planBean.getTermNo();
		 String capital = planBean.getReturnCapital();
		 String tempCapital = "0.00";
		 RepayBean repayBean = new RepayBean();
		 repayBean.setDueNo(dueNo);
		 repayBean.setTermNo(termNo);
		 List<RepayBean> repayHisBeans = repayDao.getRepayBeans(repayBean);
		 for(RepayBean repayHisBean:repayHisBeans){
			 tempCapital = BigNumberUtil.Add(tempCapital,repayHisBean.getReturnCapital());
		 }
		 String remaCapital = BigNumberUtil.Subtract(capital, tempCapital);
		 return remaCapital;
	 }
	 /**
	  * 
	  * ���������� �����Ϣ��(������Ϣ,������Ϣ,������Ϣ) 
	  * void
	  * @author Ǭ֮��
	  * @date 2012-6-13 ����04:30:07
	  */
	 public String getTotalInterest(RatedayBean ratedayBean){
		 return BigNumberUtil.Add(ratedayBean.getInterest(),ratedayBean.getOverInterest(),ratedayBean.getCmpdInterest());
	 }
	 
/**
 * 
 * ���������� ��û�����ʷ
 * @param repayBean
 * @return
 * List<RepayBean>
 * @author Ǭ֮��
 * @date 2012-6-15 ����11:16:12
 */
	 public List<RepayBean> getRepayBeans(RepayBean repayBean){
		return  repayDao.getRepayBeans(repayBean);
	 }
	 public List<RepayBean> getRepayBeanss(RepayBean repayBean){
		return  repayDao.getRepayBeanss(repayBean);
	 }
	 /**
	  * 
	  * ���������� ���滹�� 
	  * @param repayBean
	  * void
	  * @author Ǭ֮��
	  * @date 2012-6-15 ����02:58:44
	  */
	 // Ƿ���б�
	 List<DebtBean> myDebtBeans = new ArrayList<DebtBean>();
	 
	 // ���ú�Ļ���ʵ��
	 RepayBean  reRepayBean = null; 
	 // ����Ƿ���б�
	 List<RepayBean> repayDabts = null;
	 
	 // ���ڱ����Ѿ�����Ƿ��Ļ���ƻ�
	 List<PlanBean> planBeans = new ArrayList<PlanBean>();
	 
	 
	 
	 
	 /**
	  * ���滹��
	  */
	 public void  saveRepay(RepayBean repayBean){
		 	myDebtBeans.clear();
			// ��ø��ڻ����ǵڼ��λ���
			int counts = 0;
			int termNo = Integer.parseInt(repayBean.getTermNo());
			RepayBean  tempRepayBean = new RepayBean();
			tempRepayBean.setDueNo(repayBean.getDueNo());
			tempRepayBean.setTermNo(repayBean.getTermNo());
			List<RepayBean> repayBeans = getRepayBeans(repayBean);
			if(repayBeans!=null && repayBeans.size()>0){
				counts = repayBeans.size()+1;
			}else{
				counts = 1;
			}
			// �����ǵڼ��λ���
			repayBean.setCounts(String.valueOf(counts));
			// ���ü��˱�־
			repayBean.setAccFlag("0");  
			
			if(StringUtil.equals("0", repayBean.getPayType())){
				repayBean.setBankNo("");
			}
			
			if(StringUtil.equals("1", repayBean.getPayType()) && StringUtil.isEmpty(repayBean.getBankNo())){
				throw new IllegalArgumentException("ѡ������ת��ʱ,���д��벻��Ϊ��!");
			}
			
		
			// ͨ���÷�������ʱ������ֵ
			reSetRepay(repayBean);
			
			// �жϱ��ڻ����Ƿ����Ƿ��(���ڻ����ܶ�С��ʵ��Ӧ�����ܶ�ʱ�Ͳ���Ƿ��)
			DebtBean debtBean = getDebtBean(repayBean, reRepayBean);
			
			// ˵�����ڻ���ƻ���Ƿ��
			if(debtBean!=null){
				 // Ƿ�������
				 DebtServices debtServices = SpringFactory.getBean("debtServices");
				 // ������Ӧ����δ���Ĳ��ֱ��浽Ƿ�����
				 debtServices.saveDebtBean(debtBean);
				 // ��û���ƻ�������
				 PlanServices planServices = SpringFactory.getBean("planServices");
				 PlanBean planBean3 =  new PlanBean();
				 planBean3.setDueNo(repayBean.getDueNo());
				 planBean3.setTermNo(repayBean.getTermNo());
				 planBean3.setIsDebt("1");
				 // ���ݽ�ݺź��ںŸ��¸��ڻ���ƻ���Ƿ���־λǷ��
				 planServices.updatePlanBean(planBean3);
			}else{
				// ���¸��ڻ���ƻ���״̬Ϊ 3 Ƿ��״̬Ϊ0
				 // ��û���ƻ�������
				 PlanServices planServices = SpringFactory.getBean("planServices");
				 PlanBean planBean3 =  new PlanBean();
				 planBean3.setDueNo(repayBean.getDueNo());
				 planBean3.setTermNo(repayBean.getTermNo());
				 planBean3.setIsDebt("0");
				 planBean3.setState("3");
				 // ���ݽ�ݺź��ںŸ��¸��ڻ���ƻ���Ƿ���־λǷ��ͻ���ƻ�״̬
				 
				 planServices.updatePlanBean(planBean3);
			}
			if(reRepayBean!=null){
				repayBean.setReturnCapital(reRepayBean.getReturnCapital());
				repayBean.setReturnInterest(reRepayBean.getReturnInterest());
				repayBean.setCmpdInterest(reRepayBean.getCmpdInterest());
				repayBean.setOverInterest(reRepayBean.getOverInterest());
				repayBean.setOtherFee(reRepayBean.getOtherFee());
				// ����Լ��֤������Ϊ0
				repayBean.setPerfAmount("0.00");
			}

			 // ������Ƿ����Ƿ���Ļ���ƻ���״̬��Ƿ���־
			if(planBeans!=null && planBeans.size()>0){
				 PlanServices planServices = SpringFactory.getBean("planServices");
				 planServices.updatePlanList("1", "0", repayBean.getDueNo(), planBeans);
			}
			// ����Ƿ���
			if(myDebtBeans!=null  &&  myDebtBeans.size()>0){
				DebtServices debtServices = SpringFactory.getBean("debtServices");
				debtServices.updateLoanDebtList(myDebtBeans);
			}
			  
			
			if(repayDabts!=null && repayDabts.size()>0){
				// ���泥����Ƿ��
				repayDao.saveRepay(repayDabts);
			}
			// �ж��ǲ����ڸ��ڽ�����֮ǰ���л����.true ��false  ��ʾ�ڸ��ڽ�����֮����л���
			boolean  returnflag = DateUtil.gt( repayBean.getOccDate(),repayBean.getEndDate());
			
			// ������Լ��֤��������Ϊ,��û��Ԥ�ڵ�ǰ���»��걾��Ӧ�����,�˻���һ�ڵ���Լ��֤��
			// ���û��Ԥ��,����Լ��֤�����������ļ�,���������ڳ䵱������
			if(returnflag && debtBean==null){
				AcLnMstBean acLnMstBean = repayDao.getAcLnMstBeanByDueNo(repayBean.getDueNo());
				
				// ������Լ��֤���
				AcLnMstBean tempAcLnMstBean = new AcLnMstBean();
				tempAcLnMstBean.setDueNo(repayBean.getDueNo());
				// ������Լ��֤���
				if(termNo<13){
					// ԭ����Լ��֤����ȥ���λ����ֵ����Լ��֤�����ϱ��η�������Լ��֤���
					String perfAmount = acLnMstBean.getPerfAmount();
					perfAmount = BigNumberUtil.Subtract(perfAmount, repayBean.getPerfAmount());
					perfAmount = BigNumberUtil.Add(perfAmount, getPerfAmount(acLnMstBean));
					tempAcLnMstBean.setPerfAmount(perfAmount);
				}
				// ���½�����
				tempAcLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(),repayBean.getReturnCapital()));
				repayDao.updateAcLnMstBean(tempAcLnMstBean);
			}else{
				AcLnMstBean acLnMstBean = repayDao.getAcLnMstBeanByDueNo(repayBean.getDueNo());
				String perfAmount =  getPerfAmount(acLnMstBean);
				// ������Լ��֤���
				AcLnMstBean tempAcLnMstBean = new AcLnMstBean();
				tempAcLnMstBean.setDueNo(repayBean.getDueNo());
				// ���½�����
				tempAcLnMstBean.setDueBal(BigNumberUtil.Subtract(acLnMstBean.getDueBal(),repayBean.getReturnCapital()));
				repayDao.updateAcLnMstBean(tempAcLnMstBean);
			}
			
			// ���浱�ڵĻ���
			repayDao.saveRepay(repayBean);
		
			
			/****����ҵ��ʼ****/
			/**
			ReayService repayService = new ReayServiceImpl();
			app.creditapp.lnp.advrepay.entity.RepayBean parmRepayBean = new app.creditapp.lnp.advrepay.entity.RepayBean();
			parmRepayBean.setBal(Double.parseDouble(debtBean.getDebCapital()));
			parmRepayBean.setCif_name(repayBean.getCifName());
			parmRepayBean.setComp_intst(Double.parseDouble(debtBean.getDebCmpdintst()));
			// ��ݽ��
			parmRepayBean.setDue_amt(Double.parseDouble(repayBean.getDueAmt()));
			parmRepayBean.setDue_no(repayBean.getDueNo());
			// ������Ϣ��ȥ�ѻ���Ϣ
			parmRepayBean.setInner_intst(Double.parseDouble(debtBean.getDebIntst()));
			// ����ǷϢ����ΪԤ����Ϣ
			parmRepayBean.setOuter_intst(0.00);
			// Ԥ����Ϣ
			parmRepayBean.setOver_intst(Double.parseDouble(debtBean.getDebOverintst()));
			// �������ڲ��ø�������ֻ��������һ��ֵ
			parmRepayBean.setTx_date("20120612");
			repayService.rapayService(parmRepayBean);
			**/
			/****����ҵ�����****/
			
			
			
			
			
	 }
	 
	 /**
	  * 
	  * ����������  ����˳��  	���ڷ�Ϣ  �����˻������  ���ڷ�Ϣ  �����˻������  ʣ�౾��
	  * @param repayBean
	  * void 
	  * @author Ǭ֮��
	  * @date 2012-6-20 ����10:02:14
	  */
	 
	 private  void reSetRepay(RepayBean repayBean){
		 List<RepayBean> repayBeans = new ArrayList<RepayBean>();
		 // ��û����ܶ�(������+�������)
		 String returnTotal = BigNumberUtil.Add(repayBean.getPerfAmount(),repayBean.getReturnAmt());
		 String tempReturnTotal = returnTotal;
		 // Ƿ�������
		 DebtServices debtServices = SpringFactory.getBean("debtServices");
		 // ���Ƿ��ʵ��
		 DebtBean tempDebtBean = new DebtBean();
		 tempDebtBean.setDueNo(repayBean.getDueNo());
		 
		 List<DebtBean> debtBeans = debtServices.getDebtBeans(tempDebtBean);
		 Collections.sort(debtBeans);
		
		 
		 /********************** Ƿ�����ʼ********************************/
		 // ��Ƿ��
		 if(debtBeans!=null && debtBeans.size()>0){
			 for (Iterator iterator = debtBeans.iterator(); iterator.hasNext();) {
				 DebtBean debtBean = (DebtBean) iterator.next();
				 //   ���泥��Ƿ����Ƿ������
				 DebtBean myDebtBean =  new DebtBean();
				 
				 
				 myDebtBean.setDueNo(debtBean.getDueNo());
				 myDebtBean.setTermNo(debtBean.getTermNo());
				 
				 RepayBean tempRepayBean = new RepayBean();
				 // ���ý�ݺ�
				 tempRepayBean.setDueNo(debtBean.getDueNo());
				 // �����ǵڼ��ڻ���ƻ�
				 tempRepayBean.setTermNo(debtBean.getTermNo());
				 List<RepayBean> tempRepayBeans = getRepayBeans(tempRepayBean);
				 if(tempRepayBeans==null ||tempRepayBeans.size()==0){
					// �����Ǹ��ڻ���ƻ��ĵڼ��λ���
					 tempRepayBean.setCounts("1");
				  }else{
					 // �����Ǹ��ڻ���ƻ��ĵڼ��λ���
					 tempRepayBean.setCounts(String.valueOf(tempRepayBeans.size()+1));
				  }
				 // ���˱�־����Ϊδ����
				 tempRepayBean.setAccFlag("0");
				 // ��ǰ���������Ϊ0
				 tempRepayBean.setAdvaceAmt("0.00");
				 // ����ת�����д���
				 tempRepayBean.setBankNo(repayBean.getBankNo());
				 // ���ñ��ڻ���ƻ���ʼ����
				 tempRepayBean.setBegDate(debtBean.getBegDate());
				 // ���ÿͻ�����
				 tempRepayBean.setCifName(repayBean.getCifName());
				 // ���ÿͻ���
				 tempRepayBean.setCifNo(debtBean.getCifNo());
				 // ���ý�ݽ��
				 tempRepayBean.setDueAmt(repayBean.getDueAmt());
				
				 // ���ø��ڻ���ƻ��Ľ�������
				 tempRepayBean.setEndDate(debtBean.getEndDate());
				 // ���û�������
				 tempRepayBean.setOccDate(SystemParm.SystemDate);
				 // ���û������� ת�� �ֽ�
				 tempRepayBean.setPayType(repayBean.getPayType());
				
				// Ƿ��������Ϣ
				String debtOverInterest = debtBean.getDebOverintst();
				// �����ܶ��ȥǷ��������Ϣ
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, debtOverInterest);
				if(returnTotal.startsWith("-")){
					// �����ܶ�С��Ƿ��������Ϣ,��ʱ�����ܶ�ȫ�����ڳ���Ƿ��������Ϣ
					tempRepayBean.setOverInterest(tempReturnTotal);
					returnTotal = "0.00";
					repayBeans.add(tempRepayBean);
					// ��������Ƿ��������Ϣ�Ľ��ʱ
					myDebtBean.setDebOverintst(BigNumberUtil.Subtract(debtOverInterest, tempReturnTotal));
					myDebtBeans.add(myDebtBean);
				    break;
				}else{
					myDebtBean.setDebOverintst("0.00");
					// �����ܶ����Ƿ��������Ϣ
					tempRepayBean.setOverInterest(debtOverInterest);
					tempReturnTotal = returnTotal;
				}
				
				
				// Ƿ�����Ϣ
				String debCmpdintst = debtBean.getDebCmpdintst();
				// �����ܶ��ȥǷ��������Ϣ ��ȥ������Ϣ���ʣ����
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, debCmpdintst);
				
				if (returnTotal.startsWith("-")) {
					tempRepayBean.setCmpdInterest(tempReturnTotal);
					returnTotal = "0.00";
					repayBeans.add(tempRepayBean);
					// ��ʱǷ�ĸ���������ʣ��Ļ����ܶ�
					myDebtBean.setDebCmpdintst(BigNumberUtil.Subtract(debCmpdintst, tempReturnTotal));
					myDebtBeans.add(myDebtBean);
					break;
				} else {
					// ������Ƿ�ĸ�����Ϣ
					myDebtBean.setDebCmpdintst("0.00");
					tempRepayBean.setCmpdInterest(debCmpdintst);
					tempReturnTotal = returnTotal;
				}
				
				// Ƿ���˻������
				String debAccFee =  debtBean.getDebAccFee();
				// �����ܶ��ȥǷ��������Ϣ ��ȥ������Ϣ  ��ȥ�����˻�����Ѻ��ʣ����
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, debAccFee);
				if (returnTotal.startsWith("-")) {
					tempRepayBean.setOtherFee(tempReturnTotal);
					returnTotal = "0.00";
					myDebtBean.setDebAccFee(BigNumberUtil.Subtract(debAccFee ,tempReturnTotal));
					myDebtBeans.add(myDebtBean);
					repayBeans.add(tempRepayBean);
					break;
				} else {
					myDebtBean.setDebAccFee("0.00");
					tempRepayBean.setOtherFee(debAccFee);
					tempReturnTotal = returnTotal;
				}
				
				// Ƿ����Ϣ
				String debIntst = debtBean.getDebIntst();
				// �����ܶ��ȥǷ��������Ϣ ��ȥ������Ϣ  ��ȥ�����˻������  ��ȥ��Ϣ���ʣ����
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, debIntst);
				if (returnTotal.startsWith("-")) {
					tempRepayBean.setReturnInterest(tempReturnTotal);
					returnTotal = "0.00";
					myDebtBean.setDebIntst(BigNumberUtil.Subtract(debIntst ,tempReturnTotal));
					myDebtBeans.add(myDebtBean);
					repayBeans.add(tempRepayBean);
					break;
				} else {
					myDebtBean.setDebIntst("0.00");
					tempRepayBean.setReturnInterest(debIntst);
					tempReturnTotal = returnTotal;
				}
				
				// Ƿ���
				String capital = debtBean.getDebCapital();
				
				// �����ܶ��ȥǷ��������Ϣ ��ȥ������Ϣ  ��ȥ�����˻������  ��ȥ��Ϣ  ��ȥǷ�����ʣ����
				returnTotal = BigNumberUtil.Subtract(tempReturnTotal, capital);
				if (returnTotal.startsWith("-")) {
					tempRepayBean.setReturnCapital(tempReturnTotal);
					returnTotal = "0.00";
					myDebtBean.setDebCapital(BigNumberUtil.Subtract( capital,tempReturnTotal));
					myDebtBeans.add(myDebtBean);
					repayBeans.add(tempRepayBean);
					break;
				} else {
					myDebtBean.setDebCapital("0.00");
					tempRepayBean.setReturnCapital(tempReturnTotal);
					tempReturnTotal = returnTotal;
				}
				
				PlanBean planBean = new PlanBean();
				planBean.setDueNo(debtBean.getDueNo());
				planBean.setTermNo(debtBean.getTermNo());
				// �����ź�Ƿ�����Ӧ�Ļ���ƻ�,���Ҹ�Ƿ���Ѿ��������.��Ҫ���»���ƻ���״̬Ϊ3 Ƿ���־��Ҫ����Ϊ0
				planBeans.add(planBean);
				
				myDebtBeans.add(myDebtBean);
				repayBeans.add(tempRepayBean);
			}
				tempReturnTotal = returnTotal;
				
		 }
		 // ��������Ƿ�������ʱ����
		 repayDabts = repayBeans;
		 /********************** Ƿ�������********************************/
		 
		 /**********************�������ڿ�ʼ********************************/
		 // 
		 RepayBean repayBean2  = new RepayBean();  
		 // ���ڵ�������Ϣ
		 String overInterest =  repayBean.getOverInterest();
		 
		 // �����ܶ��ڻ���Ƿ���ǰ����,��ȥ����������Ϣ��ʣ��Ľ��
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, overInterest);
		if(returnTotal.startsWith("-")){
			repayBean2.setOverInterest(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		}else{
			repayBean2.setOverInterest(overInterest);
			tempReturnTotal = returnTotal;
		}
		
		 // ���ڸ�����Ϣ
		String cmpdInterest =  repayBean.getCmpdInterest();
		 // �����ܶ��ڻ���Ƿ���ǰ����,��ȥ����������Ϣ  ��ȥ������Ϣ��ʣ��Ľ��
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, cmpdInterest);
		if (returnTotal.startsWith("-")) {
			repayBean2.setOverInterest(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		} else {
			repayBean2.setOverInterest(cmpdInterest);
			tempReturnTotal = returnTotal;
		}  
		
		
		// �����˻������
		String otherFee = repayBean.getOtherFee();
		// �����ܶ��ڻ���Ƿ���ǰ����,��ȥ����������Ϣ  ��ȥ������Ϣ  ��ȥ�˻�����Ѻ�ʣ��Ľ��
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, otherFee);
		if (returnTotal.startsWith("-")) {
			repayBean2.setOtherFee(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		} else {
			repayBean2.setOtherFee(otherFee);
			tempReturnTotal = returnTotal;
		}
		
		
		// ������Ϣ
		String returnInterest =  repayBean.getReturnInterest();
		// �����ܶ��ڻ���Ƿ���ǰ����,��ȥ����������Ϣ  ��ȥ������Ϣ  ��ȥ�˻������ ��ȥ������Ϣ��ʣ��Ľ��
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, returnInterest);
		if (returnTotal.startsWith("-")) {
			repayBean2.setReturnInterest(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		} else {
			repayBean2.setReturnInterest(returnInterest);
			tempReturnTotal = returnTotal;
		}
	
		// ���ڱ���
		String returnCapital = repayBean.getReturnCapital();
		// �����ܶ��ڻ���Ƿ���ǰ����,��ȥ����������Ϣ  ��ȥ������Ϣ  ��ȥ�˻������ ��ȥ������Ϣ  ��ȥ���ڱ����ʣ��Ľ��
		returnTotal = BigNumberUtil.Subtract(tempReturnTotal, returnCapital);
		
		if (returnTotal.startsWith("-")) {
			repayBean2.setReturnCapital(tempReturnTotal);
			returnTotal = "0.00";
			tempReturnTotal = "0.00";
		} else {
			repayBean2.setReturnCapital(returnCapital);
			tempReturnTotal = returnTotal;
		}
		reRepayBean = repayBean2;
		
		 /**********************�������ڽ���********************************/
	 } 
	 
	 /**
	  * 
	  * ����������  ��ԭ����ʵ��ı����ȥ���ú�Ļ���ʵ�����0����Ƿ��(�����ֶ�����Ϣһ���Ƶ�)
	  * @param repayBean   ԭ����ʵ��    
	  * @param repayBean2     ���ú�Ļ���ʵ��
	  * @return
	  * DebtBean
	  * @author Ǭ֮��
	  * @date 2012-6-21 ����09:10:45
	  */
	 private DebtBean getDebtBean(RepayBean repayBean,RepayBean repayBean2){
		 DebtBean debtBean = null;
		 // ԭ����ʵ��������Ϣ ��ȥ���ú�������Ϣ
		 Double overInterest =  Double.parseDouble(BigNumberUtil.Subtract(repayBean.getOverInterest(), repayBean2.getOverInterest()));
		 if (overInterest>0) {
			 debtBean = new DebtBean();
			 debtBean.setDebOverintst(overInterest.toString());
		 }
		 // ԭ����ʵ�帴����Ϣ��ȥ���ú�ĸ�����Ϣ
		 Double cmpdInterest = Double.parseDouble(BigNumberUtil.Subtract(repayBean.getCmpdInterest(),repayBean2.getCmpdInterest()));
		 if(cmpdInterest>0){
			 if(debtBean==null){
				 debtBean = new DebtBean();
			 }
			 debtBean.setDebCmpdintst(cmpdInterest.toString());
		 }
		 // ԭ�˻�����Ѽ�ȥ���ú���˻������
		 Double otherFee = Double.parseDouble(BigNumberUtil.Subtract(repayBean.getOtherFee(), repayBean2.getOtherFee()));
		 if(otherFee>0){
			 if(debtBean==null){
				 debtBean = new DebtBean();
			 }
			 debtBean.setDebAccFee(otherFee.toString());
		 }
		 //  ԭ����ʵ����Ϣ��ȥ���ú����Ϣ
		 Double returnInterest = Double.parseDouble(BigNumberUtil.Subtract(repayBean.getReturnInterest(),repayBean2.getReturnInterest()));
		 if(returnInterest>0){
			 if(debtBean==null){
				 debtBean = new DebtBean();
			 }
			 debtBean.setDebIntst(returnInterest.toString()); 
		 }
		 
		 // ԭ����ʵ�屾���ȥ���ú�ı���
		 Double  returnCapital= Double.parseDouble(BigNumberUtil.Subtract(repayBean.getReturnCapital(),repayBean2.getReturnCapital()));
		 if(returnCapital>0){
			 if(debtBean==null){
				 debtBean = new DebtBean();
			 }
			 debtBean.setDebCapital(returnCapital.toString());
		 }
		// �����Ƿ�� 
		 if (debtBean!=null) {
			debtBean.setDueNo(repayBean.getDueNo());
			debtBean.setTermNo(repayBean.getTermNo());
		}
		 return debtBean;
	 }
	 
	 
	 
	 /**
	  * 
	  * ���������� ��ǰ����ҵ���� 
	  * void
	  * @author Ǭ֮��
	  * @date 2012-6-18 ����11:03:33
	  */
	 public void saveAdvaceRepay(RepayBean repayBean){
		 PlanServicesImpl planServicesImpl = SpringFactory.getBean("planServicesImpl");
		 AcLnMstBean acLnMstBean = repayDao.getAcLnMstBeanByDueNo(repayBean.getDueNo());
		 PlanParmBean planParmBean = new PlanParmBean();
		 // ������ǰ�����
		 planParmBean.setAdvaceAmt(repayBean.getAdvaceAmt());
		 // ���û���ƻ�(��һ��)�Ŀ�ʼ����
		 planParmBean.setBeginDate(repayBean.getBegDate());
		 // �̶���������
		 planParmBean.setFixDate(acLnMstBean.getFixDate());
		 // 0 ��Ԥ��֧�� 1 Ԥ��֧�� 3 ����һ��
		 planParmBean.setIsAdvance(acLnMstBean.getIsAdvance());
		 // �Ƿ�����  0 ������   1����
		 planParmBean.setIsDelay(acLnMstBean.getIsDelay());
		 // �Ƿ�ǿ�Ƽƻ�
		 planParmBean.setIsForce(acLnMstBean.getIsForce());
		 // �Ƿ�̶��ջ��� 0 ���� 1��
		 planParmBean.setIsFixDate(acLnMstBean.getIsFixDate());
		 // ���ʽ
		 planParmBean.setReturnMentod(acLnMstBean.getReturnMethod());
		 
		 planServicesImpl.createPlan(repayBean.getDueNo(), planParmBean);
		 
	 }
	 
	 /**
	  * 
	  * ���������������Լ��֤��� 
	  * @return
	  * String
	  * @author Ǭ֮��
	  * @date 2012-6-19 ����04:35:04
	  */
	 private String  getPerfAmount(AcLnMstBean acLnMstBean){
		 // ��ݽ��
		 String dueAmt = acLnMstBean.getDueAmt();
		 // ÿ����ȡ����������
		 String perfAmountPerc =  acLnMstBean.getPerfAmountPerc();
		 String perfAmount =  BigNumberUtil.Multiply(dueAmt, perfAmountPerc);
		 perfAmount = BigNumberUtil.Divide(perfAmount, "100", 2, "1");
		 return perfAmount;
	 }
	 
	
	 

		public List<AcLnMstBean> getAcLnMstBeans(AcLnMstBean parmAcLnMstBean) {
			return repayDao.getAcLnMstList(parmAcLnMstBean);
		}


		public List<RepayBean> getDistinctRepayBeans(RepayBean repayBean) {
			return  repayDao.getDistinctRepayBeans(repayBean);
		}


		public LnDue getDueBean(LnDue dueBean) {
			return  repayDao.getDueBean(dueBean);
		}


		public List<PlanBean> getPlanBeans(String currDate) {
			return  repayDao.getPlanBeans(currDate);
		}


		public List<RepayBean> getRepayBeansOnSchedule(String dueNo) {
			return  repayDao.getRepayBeansOnSchedule(dueNo);
		}


		public List<PlanBean> getPlanBeanList(PlanBean planBean) {
			return  repayDao.getPlanBeanList(planBean);
		}


		public void updateREpayDetails(CustRepayDetails custRepayDetails) throws ServiceException {
			try {
				  repayDao.updateREpayDetails(custRepayDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}


		public void insertREpayDetails(CustRepayDetails custRepayDetails) throws ServiceException {
			try {
				  repayDao.insertREpayDetails(custRepayDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}


		public List<CustRepayDetails> findCustRepayDetailsList(CustRepayDetails custRepayDetails) {
			return  repayDao.findCustRepayDetailsList(custRepayDetails);
			
		}
		 
		public List<RepayBean>  getLastRepayList(){
			return repayDao.getLastRepayList();
		}
	
		 
		
		public void upLnDue(Map<String,String> parmMap){
			repayDao.upLnDue(parmMap);
		}
	 

		public List<AcLnMstBean> getAcLnMstBeansByReportPolicy(AcLnMstBean parmAcLnMstBean) {
			return repayDao.getAcLnMstBeansByReportPolicy(parmAcLnMstBean);
		}

	
	 
}
