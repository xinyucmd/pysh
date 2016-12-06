package com.jiangchuanbanking.prod.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.parsing.Problem;

import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.changelog.domain.ChangeLog;
import com.jiangchuanbanking.dict.domain.AccountLevel;
import com.jiangchuanbanking.dict.domain.AccountNature;
import com.jiangchuanbanking.dict.domain.AccountType;
import com.jiangchuanbanking.dict.domain.AnnualRevenue;
import com.jiangchuanbanking.dict.domain.Capital;
import com.jiangchuanbanking.dict.domain.CompanySize;
import com.jiangchuanbanking.dict.domain.Currency;
import com.jiangchuanbanking.dict.domain.Industry;
import com.jiangchuanbanking.financing.domain.Account;
import com.jiangchuanbanking.financing.domain.Document;
import com.jiangchuanbanking.financing.domain.TargetList;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.domain.ProdChargePolicy;
import com.jiangchuanbanking.prod.service.IProdChargePolicyService;
import com.jiangchuanbanking.prod.service.IProdService;
import com.jiangchuanbanking.system.domain.ProductConfiguration;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.BeanUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class EditProdAction extends BaseEditAction implements
		Preparable {

	private IBaseService baseService;
    private IProdService prodService;
    private ProdBaseEntity prodBase;
 
    private ProdChargePolicy prodChargePolicy;
    private IProdChargePolicyService prodChargePolicyService;
	private Integer id;
	private String prdtNo;
	private String delFlg;
	private String sts;
	private String startDate;
	private String endDate;
	private String createOp;
	private String createDate;
	private String cmt;

	
	
	
	private String type;//A.产品定价	B.赎回定价
	private String paymentType;
	private String returnRate;
	private String stageMin;
	private String stageMax;
	private String stageMax1;
	private String rate;
	private String ifRedeem;
	
	private List<ProdChargePolicy> listA;
	private List<ProdChargePolicy> listB;
	
	private String multiReturnRate;
	private String multiRate;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final long serialVersionUID = 1L;
	private Set<ProdChargePolicy> chargeLists = new HashSet<ProdChargePolicy>();
	private List<ProdChargePolicy> chargePolicies;
	private List<ProdChargePolicy> redeemPolicies;
	public void prepare() throws Exception {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		String local = (String) session.get("locale");
//		this.accountLevels = accountLevelService.getOptions(
//				AccountLevel.class.getSimpleName(), local);
//		this.types = accountTypeService.getOptions(
//				AccountType.class.getSimpleName(), local);
//		this.annualRevenues = annualRevenueService.getOptions(
//				AnnualRevenue.class.getSimpleName(), local);
//		this.capitals = capitalService.getOptions(
//				Capital.class.getSimpleName(), local);
//		this.companySizes = companySizeService.getOptions(
//				CompanySize.class.getSimpleName(), local);
//		this.accountNatures = accountNatureService.getOptions(
//				AccountNature.class.getSimpleName(), local);
//		this.industries = industryService.getOptions(
//				Industry.class.getSimpleName(), local);
//		this.currencies = currencyService.getAllObjects(Currency.class
//				.getSimpleName());
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String get() throws Exception {
		this.initBaseInfo();
		return SUCCESS;	
	}
	
	public String edit() throws Exception {
		if (this.getPrdtNo()!=null) {
			String hql="from ProdBaseEntity where PRDT_NO='"+prdtNo+"'";
			List<ProdBaseEntity> list=baseService.findByHQL(hql);
			prodBase=list.get(0);
			String hql2="from ProdChargePolicy where PRDT_NO='"+prdtNo+"' and  TYPE='A'" ;
			String hql3="from ProdChargePolicy where PRDT_NO='"+prdtNo+"' and  TYPE='B'" ;
			listA=baseService.findByHQL(hql2);
			listB=baseService.findByHQL(hql3);
		}
		this.initBaseInfo();
		return SUCCESS;	
	}
	
	public String app() throws Exception {
		if (this.getPrdtNo()!=null) {
			String hql="from ProdBaseEntity where PRDT_NO='"+prdtNo+"'";
			List<ProdBaseEntity> list=baseService.findByHQL(hql);
			prodBase=list.get(0);
			String hql2="from ProdChargePolicy where PRDT_NO='"+prdtNo+"' and  TYPE='A'" ;
			String hql3="from ProdChargePolicy where PRDT_NO='"+prdtNo+"' and  TYPE='B'" ;
			listA=baseService.findByHQL(hql2);
			listB=baseService.findByHQL(hql3);
		}
		this.initBaseInfo();
		return SUCCESS;	
	}
	
	
	public String find() throws Exception {
			prodBase = prodService.getProdByNo(this.getPrdtNo());
			prdtNo=prodBase.getPrdtNo();
			
			startDate=prodBase.getStartDate();
			endDate=prodBase.getEndDate();
			System.out.println("=============get()==========");
			
			prodChargePolicy = new ProdChargePolicy();
			prodChargePolicy.setPrdtNo(prdtNo);
			prodChargePolicy.setType("A");
			chargePolicies=prodChargePolicyService.findPolicy(prodChargePolicy);
			prodChargePolicy.setType("B");
			redeemPolicies=prodChargePolicyService.findPolicy(prodChargePolicy);
			paymentType=chargePolicies.get(0).getPaymentType();
			return "detail";
		
	}
	/**
	 * Saves the entity.
	 * 
	 * @return the SUCCESS result
	 */
	public String save() throws Exception {
		this.deletePolicy();	
		prodBase.setChargeLists(this.analysisPolicy());
		
		prodBase.setSts("5");//5新建
		prodBase.setDelFlg("1");//1.停用0启用
		User loginUser = getLoginUser();
		prodBase.setCreateOp(loginUser.getName());
		prodBase.setCreateDate(sdf.format(new Date()));
		this.getBaseService().makePersistent(prodBase);
		
		return SUCCESS;
	}
	
	//审批通过
	public String saveApp() throws Exception {	
		prodBase.setSts("15");
		prodBase.setApproveOp(this.getLoginUser().getName());
		prodBase.setApproveDate(sdf.format(new Date()));
		baseService.makePersistent(prodBase);
		return SUCCESS;
	}
	
	//审批不通过
	public String refApp() throws Exception {	
		prodBase.setSts("20");
		prodBase.setApproveOp(this.getLoginUser().getName());
		prodBase.setApproveDate(sdf.format(new Date()));
		baseService.makePersistent(prodBase);
		return SUCCESS;
	}
	
	//通过并启用
	public String saveOn() throws Exception {	
		prodBase.setSts("15");
		prodBase.setDelFlg("0");
		prodBase.setStartDate(sdf.format(new Date()));
		baseService.makePersistent(prodBase);
		return SUCCESS;
	}
	
    //停用
	public String off() throws Exception {	
		prodBase.setSts("15");
		prodBase.setDelFlg("1");
		prodBase.setEndDate(sdf.format(new Date()));
		baseService.makePersistent(prodBase);
		return SUCCESS;
	}
	
	//启用
	public String on() throws Exception {	
		prodBase.setSts("15");
		prodBase.setDelFlg("0");
		prodBase.setStartDate(sdf.format(new Date()));
		baseService.makePersistent(prodBase);
		return SUCCESS;
	}
	
	//保存产品信息并提交
	public String saveSubmit() throws Exception {
		this.deletePolicy();
				
		prodBase.setChargeLists(this.analysisPolicy());
		
		prodBase.setSts("10");//5新建
		prodBase.setDelFlg("1");//1.停用0启用
		User loginUser = getLoginUser();
		prodBase.setCreateOp(loginUser.getName());
		prodBase.setCreateDate(sdf.format(new Date()));
		this.getBaseService().makePersistent(prodBase);
		
		return SUCCESS;
	}
	/**
	 * Saves entity field
	 * 
	 * @return original account record
	 * @throws Exception
	 */
	private ProdBaseEntity saveEntity() throws Exception {
		ProdBaseEntity prod = new  ProdBaseEntity();
		User loginUser = getLoginUser();
//		if (prodBase.getId() == null) {
//			prodBase.setCreateOp(loginUser.getName());
//			prodBase.setCreateDate(sdf.format(new Date()));
//		} else {
//			prodBase.setCreateOp(loginUser.getName());
//			prodBase.setCreateDate(sdf.format(new Date()));
//		}
		return prod;
	}
	
	
	/**
	 * 定价策略判重
	 * @return 
	 * 
	 * @return
	 */
	private Set<ProdChargePolicy> analysisPolicy() {
		User loginUser = getLoginUser();
		
		Set<ProdChargePolicy> chargeSet = new HashSet<ProdChargePolicy>();
		if (multiReturnRate.split("\\|").length >= 0) {
			for (String ur : multiReturnRate.split("\\|")) {
				String fee[]=ur.split(",");
				//A.产品定价
				ProdChargePolicy charge1 = new  ProdChargePolicy();
				charge1.setType("A");
				charge1.setPrdtNo(prodBase.getPrdtNo());
				charge1.setPrdtName(prodBase.getPrdtName());
				charge1.setPaymentType(fee[0]);
				charge1.setStageMax(fee[1]);
				charge1.setReturnRate(fee[2]);
				charge1.setDelFlg("1");
				charge1.setCreateOp(loginUser.getName());
				charge1.setCreateDateString(sdf.format(new Date()));
				chargeSet.add(charge1);
				System.out.println(chargeSet+"******************************");
//				charge1= (ProdChargePolicy) getBaseService().makePersistent(charge1);
			}
		}
		
       if ("1".equals(prodBase.getAdvanceRedeem())) {	
		if (multiRate.split("\\|").length>=0) {
			for (String ur:multiRate.split("\\|")) {
				String fee[]=ur.split(",");
//				B.赎回定价
				ProdChargePolicy charge2 = new  ProdChargePolicy();
				charge2.setType("B");
				charge2.setPrdtNo(prodBase.getPrdtNo());
				charge2.setPrdtName(prodBase.getPrdtName());
				charge2.setPaymentType(paymentType);
				charge2.setStageMin(fee[0]);
				charge2.setStageMax(fee[1]);
				charge2.setRate(fee[3]);
				charge2.setIfRedeem(fee[2]);
				charge2.setDelFlg("1");
				charge2.setCreateOp(loginUser.getName());
				charge2.setCreateDateString(sdf.format(new Date()));
				System.out.println(charge2+"******************************");
				chargeSet.add(charge2);
				System.out.println(chargeSet.size()+"******************************");
//				charge2= (ProdChargePolicy) getBaseService().makePersistent(charge2);
			}
		}
       }
		System.out.println(chargeSet+"******************************");
		return chargeSet;
	}
	
	private void deletePolicy(){
		String hql="delete from wealth_prdt_charge_policy where PRDT_NO='"+prodBase.getPrdtNo()+"'";
		//baseService.executeSQL(hql, null);
	}
	
	/**
	 * Saves entity field
	 * 
	 * @return original account record
	 * @throws Exception
	 */
	private ProdChargePolicy savePolicyEntity() throws Exception {
		ProdChargePolicy prod = new ProdChargePolicy();
		User loginUser = getLoginUser();
		if (prodChargePolicy.getId() == null) {
			prodChargePolicy.setCreateOp(loginUser.getName());
			prodChargePolicy.setCreateDateString(sdf.format(new Date()));
		} else {
			prodChargePolicy.setCreateOp(loginUser.getName());
			prodChargePolicy.setCreateDateString(sdf.format(new Date()));
		}
		return prod;
	}
	/**
	 * Mass update entity record information
	 */
	public String massUpdate() throws Exception {
		saveEntity();
//		String[] fieldNames = this.massUpdate;
//		if (fieldNames != null) {
//			String[] selectIDArray = this.seleteIDs.split(",");
//			Collection<Account> accounts = new ArrayList<Account>();
//			final User loginUser = this.getLoginUser();
//			User user = userService
//					.getEntityById(User.class, loginUser.getId());
//			final List<Account> originalAccounts = new ArrayList<Account>();
//			final List<Account> currentAccounts = new ArrayList<Account>();
//			for (String IDString : selectIDArray) {
//				int id = Integer.parseInt(IDString);
//				Account accountInstance = this.baseService.getEntityById(
//						Account.class, id);
//				Account originalAccount = accountInstance.clone();
//				for (String fieldName : fieldNames) {
//					Object value = BeanUtil.getFieldValue(account, fieldName);
//					BeanUtil.setFieldValue(accountInstance, fieldName, value);
//				}
//				accountInstance.setUpdated_by(user);
//				accountInstance.setUpdated_on(new Date());
//
//				originalAccounts.add(originalAccount);
//				currentAccounts.add(accountInstance);
//				accounts.add(accountInstance);
//			}
//
//			if (accounts.size() > 0) {
//				this.baseService.batchUpdate(accounts);
//				final Collection<ChangeLog> allChangeLogs = genBAChangeLog(
//						originalAccounts, currentAccounts, loginUser);
//				taskExecutor.execute(new Runnable() {
//					public void run() {
//						batchInserChangeLogs(allChangeLogs);
//					}
//				});
//			}
//		}
		return SUCCESS;
	}

	public IProdService getProdService() {
		return prodService;
	}

	public void setProdService(IProdService prodService) {
		this.prodService = prodService;
	}

	public ProdBaseEntity getProdBase() {
		return prodBase;
	}

	public void setProdBase(ProdBaseEntity prodBase) {
		this.prodBase = prodBase;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getPrdtNo() {
		return prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCreateOp() {
		return createOp;
	}

	public void setCreateOp(String createOp) {
		this.createOp = createOp;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public ProdChargePolicy getProdChargePolicy() {
		return prodChargePolicy;
	}

	public void setProdChargePolicy(ProdChargePolicy prodChargePolicy) {
		this.prodChargePolicy = prodChargePolicy;
	}

	public IProdChargePolicyService getProdChargePolicyService() {
		return prodChargePolicyService;
	}

	public void setProdChargePolicyService(
			IProdChargePolicyService prodChargePolicyService) {
		this.prodChargePolicyService = prodChargePolicyService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(String returnRate) {
		this.returnRate = returnRate;
	}

	public String getStageMin() {
		return stageMin;
	}

	public void setStageMin(String stageMin) {
		this.stageMin = stageMin;
	}

	public String getStageMax() {
		return stageMax;
	}

	public void setStageMax(String stageMax) {
		this.stageMax = stageMax;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getIfRedeem() {
		return ifRedeem;
	}

	public void setIfRedeem(String ifRedeem) {
		this.ifRedeem = ifRedeem;
	}

	public String getStageMax1() {
		return stageMax1;
	}

	public void setStageMax1(String stageMax1) {
		this.stageMax1 = stageMax1;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public String getMultiReturnRate() {
		return multiReturnRate;
	}

	public void setMultiReturnRate(String multiReturnRate) {
		this.multiReturnRate = multiReturnRate;
	}

	public String getMultiRate() {
		return multiRate;
	}

	public void setMultiRate(String multiRate) {
		this.multiRate = multiRate;
	}

	public Set<ProdChargePolicy> getChargeLists() {
		return chargeLists;
	}

	public void setChargeLists(Set<ProdChargePolicy> chargeLists) {
		this.chargeLists = chargeLists;
	}

	public List<ProdChargePolicy> getChargePolicies() {
		return chargePolicies;
	}

	public void setChargePolicies(List<ProdChargePolicy> chargePolicies) {
		this.chargePolicies = chargePolicies;
	}

	public List<ProdChargePolicy> getRedeemPolicies() {
		return redeemPolicies;
	}

	public void setRedeemPolicies(List<ProdChargePolicy> redeemPolicies) {
		this.redeemPolicies = redeemPolicies;
	}
    

	public List<ProdChargePolicy> getListA() {
		return listA;
	}

	public void setListA(List<ProdChargePolicy> listA) {
		this.listA = listA;
	}

	public List<ProdChargePolicy> getListB() {
		return listB;
	}

	public void setListB(List<ProdChargePolicy> listB) {
		this.listB = listB;
	}

	
	
	
	

}
