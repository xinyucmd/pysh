package com.jiangchuanbanking.subscription.action;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.jiangchuanbanking.account.domain.SubAccount;
import com.jiangchuanbanking.account.service.IMainAccountService;
import com.jiangchuanbanking.account.service.ISubAccountService;
import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.financing.domain.BankAccount;
import com.jiangchuanbanking.flowdetails.domain.FinancingDetails;
import com.jiangchuanbanking.flowdetails.service.IFinancingDetailsService;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.plan.domain.PlanBean;
import com.jiangchuanbanking.plan.service.IPlanServices;
import com.jiangchuanbanking.process.service.IProcessService;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.domain.ProdChargePolicy;
import com.jiangchuanbanking.redeem.domain.RedeemEntity;
import com.jiangchuanbanking.subscription.domain.Claims;
import com.jiangchuanbanking.subscription.domain.ListSubscrip;
import com.jiangchuanbanking.subscription.domain.PactInfo;
import com.jiangchuanbanking.subscription.service.ISubscripService;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.BigNumberUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.DateTimeUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.DateTimeUtil;
import com.jiangchuanbanking.util.DateUtil;
import com.jiangchuanbanking.util.Message;
import com.opensymphony.xwork2.Preparable;

public class EditSubscripAction extends BaseEditAction implements Preparable {

	private ListSubscrip listSubscrip;

	private Integer cusID;

	private String cusName;

	private String prodNo;

	private String prdtNo;

	private String prdtName;

	private String qishu;

	private String cifName;

	private Double bxhj;

	private String if_continue;

	private PactInfo pactInfo;

	private String pact_no;

	private String redem_Date;
	
	private String hbPactNo;

	private Customer customer;
	private Integer id;
	private String redem_date;
	private IBaseService baseService;

	private IProcessService processService;

	private ISubscripService subscripService;

	private ISubAccountService subAccountService;

	private IFinancingDetailsService financingDetailsService;

	private IMainAccountService mainAccountService;

	private IPlanServices planServicesImpl;

	private List<ProdChargePolicy> listProdCP;

	private String cmt;

	private String checkCmt;

	private FinancingDetails financingDetails;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	// 到账日期
	private String arrivaldate;

	private String pactNos;

	private String message;

	private String claims_no;
	private String claims_name;
	private String claims_amt;
	private String multiClaims;
	private List<Claims> claimsList;
	public IFinancingDetailsService getFinancingDetailsService() {
		return financingDetailsService;
	}

	public void setFinancingDetailsService(
			IFinancingDetailsService financingDetailsService) {
		this.financingDetailsService = financingDetailsService;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void prepare() throws Exception {

	}

	public String get() throws Exception {
		if (this.getId() != null) {
			pactInfo = (PactInfo) baseService.getEntityById(PactInfo.class,
					getId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (pactInfo.getStart_date() != null
					&& pactInfo.getEnd_date() != null) {
				String startTime = dateFormat.format(pactInfo.getStart_date());
				String endTime = dateFormat.format(pactInfo.getEnd_date());

				pactInfo.setStart_date(sdf.parse(startTime));
				pactInfo.setEnd_date(sdf.parse(endTime));
			}

			customer = pactInfo.getCustomer();
			this.setCusID(customer.getId());
			this.setCusName(customer.getCif_name());
			this.setPrdtNo(pactInfo.getPrdt_no());
			this.setPrdtName(pactInfo.getPrdt_name());
			this.setIf_continue(pactInfo.getIf_continue());
			if (pactInfo.getStart_date() != null) {
				arrivaldate = subscripService.getLastDay(pactInfo
						.getStart_date());
			}
			cmt = processService.getCmt(pactInfo);
			financingDetails = financingDetailsService.getCashDetail(pactInfo);
			checkCmt = processService.getCheckCmt(pactInfo);
			
			claimsList  = baseService.findByHQL(" from Claims where pact_no='"+pactInfo.getPact_no()+"'");
		}
		this.initBaseInfo();
		return SUCCESS;
	}

	// 保存
	public String save() {
		// 更新客户合同信息
		try {
			this.creatorUpdateCP("1", "1", false);
			message = Message.SAVESECC;
			return SUCCESS;
		} catch (Exception e) {
			message = Message.SAVEFAIL;
			return INPUT;
		}
	}

	// 更新客户合同信息
	public void creatorUpdateCP(String sts, String type, boolean isSubmit)
			throws Exception {
		
		

		if (isSubmit&&!"HB".equals(pactInfo.getPrdt_no().substring(0, 2))) {
			String pactNo = subscripService.getPactNo(pactInfo.getPrdt_no());
			pactInfo.setPact_no(pactNo);
		}else{
			pactInfo.setPact_no(hbPactNo);
		}
		
		pactInfo.setSts(sts);
		pactInfo.setPact_type(type);
		if (pactInfo.getCash_amt() == null) {
			pactInfo.setCash_amt(0d);
		}
		if (pactInfo.getIncome_amt() == null) {
			pactInfo.setIncome_amt(0d);
		}
		pactInfo.setPact_amt(pactInfo.getCash_amt() + pactInfo.getIncome_amt());
		pactInfo.setCif_name(customer.getCif_name());
		if (customer.getId() != null) {
			customer = (Customer) baseService.makePersistent(customer);
			pactInfo.setCustomer(customer);
		} else {
			customer.setCif_name(this.getCifName());
			customer.setOpen_date(DateTimeUtil.getNowDateString());
			customer.setOpen_op(CommonUtil.getLoginUserName());
			customer = (Customer) baseService.makePersistent(customer);
			pactInfo.setCif_name(this.getCifName());
			pactInfo.setCustomer(customer);
		}
		pactInfo.setOpen_date(DateTimeUtil.getNowDateString());
		pactInfo.setOpen_op(CommonUtil.getLoginUserName());
		pactInfo.setOpen_id(CommonUtil.getLoginUserId());
		pactInfo.setContinueflg("0");
		pactInfo.setIf_already_conti("2");
		pactInfo.setIf_export_ht("1");
		pactInfo.setIf_export_sj("1");
		pactInfo = (PactInfo) baseService.makePersistent(pactInfo);

	}

	public String getPrdtName() {
		return prdtName;
	}

	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
	}

	// 认购提交
	public String submit() throws Exception {
		// 更新客户与合同信息
		this.creatorUpdateCP("2", "1", true);
		// 创建主账户
		mainAccountService.creatMainAcc(customer);
		// 创建子账户
		subAccountService.creatSubAccount(pactInfo);
		// 创建/更新银行账户
		subscripService.createBankAcc(pactInfo);
		// 创建流程
		processService.createPro(pactInfo, "11");
		return SUCCESS;
	}

	// 债权匹配
	public String claims() throws Exception {
		// 更新合同表
		pactInfo = subscripService.updatePact1(pactInfo, "3", null,"");
		// 更新流程
		processService.updatePro(pactInfo, "11", "10", "10", "1", cmt);
		// 债券信息保存
		this.analyzeClaims();
		return SUCCESS;
	}

	// 解析债权匹配信息串
	private void analyzeClaims() {
		String claims[]=multiClaims.split("\\|");
		for (String str : claims) {
			String c[]=str.split("#");
			Claims claim = new Claims();
			claim.setPact_no(this.getPactInfo().getPact_no());
			claim.setCif_no(this.getPactInfo().getCustomer().getId());
			claim.setCif_name(this.getPactInfo().getCif_name());
			claim.setClaims_no(c[0]);
			claim.setClaims_name(c[1]);
			claim.setClaims_amt(c[2]);
			//claim.setCmt(c[3]);
			claim.setCreate_op(this.getLoginUser().getName());
			claim.setCreate_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			baseService.makePersistent(claim);
		}
	}

	// 债权匹配失败
	public String failClaims() throws Exception {
		// 更新合同表
		pactInfo = subscripService.updatePact1(pactInfo, "9", null,"");
		// 如果为续购更新续购合同状态位6
		if (!"1".equals(pactInfo.getPact_type())) {
			subscripService.updateContinuePacts(pactInfo.getPact_no());
		}
		// 更新流程
		processService.updatePro(pactInfo, "11", "99", "10", "2", cmt);
		return SUCCESS;
	}

	// 确认到账
	public String accConfirm() throws Exception {
		// 更新合同
		if ("1".equals(pactInfo.getPact_type())) {
			pactInfo = subscripService.updatePact1(pactInfo, "4",
					financingDetails.getFlow_date(),pactInfo.getCmt());
		} else {
			pactInfo = subscripService.updatePact(pactInfo, "4", arrivaldate,
					pactInfo.getPact_no());
		}
		// 更新流程
		processService.updatePro(pactInfo, "11", "20", "20", "1", cmt);
		// 更新子账户
		SubAccount sub = subAccountService.updateSubAccount(pactInfo, "11");
		// 回退时删除流水
		financingDetailsService.deleteDetail(pactInfo);
		// 创建流水
		if ("1".equals(pactInfo.getPact_type())) {

			financingDetailsService.creatDetail(sub, "21", pactInfo
					.getCash_amt().toString(), financingDetails);
		} else {
			if (pactInfo.getCash_amt() != null && pactInfo.getCash_amt() != 0d) {
				financingDetailsService.creatDetail(sub, "21", pactInfo
						.getCash_amt().toString(), financingDetails);
			}
			financingDetailsService.creatDetail(sub, "22", pactInfo
					.getIncome_amt().toString(), null);
			financingDetailsService.creatDetails(sub);
		}
		return SUCCESS;
	}

	public String saveRenewConfirm() {
		User loginUser = UserUtil.getLoginUser();
		// // 更新子账户
		// SubAccount subAccount = new SubAccount();
		// subAccount = (SubAccount) baseService.findByHQL(
		// " from SubAccount where pact_no='" + pactInfo.getPact_no()
		// + "'").get(0);
		// subAccount.setRedeem_amt(bxhj == null
		// || pactInfo.getContinueAmt() == null ? "0.00" :
		// BigNumberUtil.Divide2(String
		// .valueOf(bxhj - pactInfo.getContinueAmt()), "1"));
		// baseService.makePersistent(subAccount);
		// 更新合同
		Double continueAmt = pactInfo.getContinueAmt() == null ? 0.00
				: pactInfo.getContinueAmt();
		bxhj = bxhj == null ? 0.00 : bxhj;
		pactInfo = (PactInfo) baseService.findByHQL(
				" from PactInfo where pact_no='" + pactInfo.getPact_no() + "'")
				.get(0);
		pactInfo.setIf_continue(if_continue);
		pactInfo.setContinue_amt(String.valueOf(continueAmt));
		pactInfo.setContinueflg("1");
		// pactInfo.setSts("86");//续购确认
		baseService.makePersistent(pactInfo);

		// 赎回插入
		if ("1".equals(if_continue)) {// 是
			if (bxhj == continueAmt && bxhj != 0.00 && continueAmt != 0.00) {// 全部续购--无赎回

			} else {// 部分续购--部分赎回
				RedeemEntity redeemEntity = new RedeemEntity();
				redeemEntity.setPact_no(pactInfo.getPact_no());
				redeemEntity.setCif_no(pactInfo.getCif_no());
				redeemEntity.setCif_name(pactInfo.getCif_name());
				redeemEntity.setSts("11");// 赎回申请
				redeemEntity.setRedem_type("2");// 部分赎回
				redeemEntity.setRedem_amount(BigNumberUtil.Divide2(
						String.valueOf(bxhj - continueAmt), "1"));// 赎回金额
				redeemEntity.setPay_amt(BigNumberUtil.Divide2(
						String.valueOf(bxhj), "1"));
				redeemEntity.setCreate_date(dateFormat.format(new Date()));
				redeemEntity.setCreate_op(this.getLoginUser().getName());
				redeemEntity.setRedem_Date(dateFormat.format(pactInfo
						.getEnd_date()));
				redeemEntity.setOpen_id(loginUser.getId());
				this.getBaseService().makePersistent(redeemEntity);
			}
		} else {// 否-- 全部赎回
			SubAccount subAccount = new SubAccount();
			subAccount = (SubAccount) baseService.findByHQL(
					" from SubAccount where pact_no='" + pactInfo.getPact_no()
							+ "'").get(0);
			bxhj = Double.parseDouble(BigNumberUtil.Add(
					subAccount.getCash_amt(), subAccount.getRenew_amt(),
					subAccount.getIncome_amt()));
			bxhj = Double.parseDouble(BigNumberUtil.Subtract(
					String.valueOf(bxhj), subAccount.getRedeem_amt()));
			RedeemEntity redeemEntity = new RedeemEntity();
			redeemEntity.setPact_no(pactInfo.getPact_no());
			redeemEntity.setCif_no(pactInfo.getCif_no());
			redeemEntity.setCif_name(pactInfo.getCif_name());
			redeemEntity.setSts("11");// 赎回申请
			redeemEntity.setRedem_type("1");// 到期赎回(全部)
			redeemEntity.setRedem_amount(BigNumberUtil.Divide2(
					String.valueOf(bxhj), "1"));// 赎回金额
			redeemEntity.setPay_amt(BigNumberUtil.Divide2(String.valueOf(bxhj),
					"1"));
			redeemEntity.setCreate_date(dateFormat.format(new Date()));
			redeemEntity.setCreate_op(this.getLoginUser().getName());
			redeemEntity.setRedem_capital(BigNumberUtil.Add(
					subAccount.getCash_amt(), subAccount.getRenew_amt()));
			redeemEntity.setRedem_interest(subAccount.getIncome_amt());
			redeemEntity
					.setRedem_Date(dateFormat.format(pactInfo.getEnd_date()));
			redeemEntity.setOpen_id(loginUser.getId());
			this.getBaseService().makePersistent(redeemEntity);
		}
		return SUCCESS;
	}

	public String saveAdvanceRedemConfirm() throws ParseException {
		pactInfo = (PactInfo) this
				.getBaseService()
				.findByHQL(
						" from PactInfo where pact_no='"
								+ pactInfo.getPact_no() + "'").get(0);
		RedeemEntity redeemEntity = new RedeemEntity();
		redeemEntity.setCif_name(pactInfo.getCif_name());
		redeemEntity.setCif_no(pactInfo.getCif_no());
		redeemEntity.setPact_no(pactInfo.getPact_no());
		redeemEntity.setSts("11");
		redeemEntity.setRedem_type("3");
		redeemEntity.setRedem_amount(BigNumberUtil.Divide2(
				this.calculBXH(pactInfo), "1"));
		redeemEntity.setPay_amt(BigNumberUtil.Divide2(this.calculBXH(pactInfo),
				"1"));
		redeemEntity.setRedem_capital(BigNumberUtil.Divide2(
				String.valueOf(pactInfo.getPact_amt()), "1"));
		redeemEntity.setRedem_interest(BigNumberUtil.Subtract(BigNumberUtil
				.Divide2(this.calculBXH(pactInfo), "1"), BigNumberUtil.Divide2(
				String.valueOf(pactInfo.getPact_amt()), "1")));
		redeemEntity.setRedem_Date(DateTimeUtil.getJDateToODate(redem_Date));
		redeemEntity.setOpen_id(CommonUtil.getLoginUserId());
		redeemEntity.setCreate_date(DateTimeUtil.getDateString(new Date()));
		redeemEntity.setCreate_op(this.getLoginUser().getName());
		this.baseService.makePersistent(redeemEntity);

		pactInfo.setSts("81");
		pactInfo.setContinueflg("1");
		this.baseService.makePersistent(pactInfo);
		return SUCCESS;
	}

	private String calculBXH(PactInfo pactInfo) throws ParseException {
		String bxhj = "0.00";
		// 按照提前赎回利率算
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date d = sdf.parse(redem_Date);
		sdf.applyPattern("yyyy-MM-dd");
		int[] monthAndDay = DateUtil.getMonthsAndDays(
				dateFormat.format(pactInfo.getStart_date()),
				dateFormat.format(dateFormat.parse(sdf.format(d))));
		String stage = "";
		if (monthAndDay[1] > 0) {
			stage = String.valueOf(monthAndDay[0] + 1);
		} else {
			stage = String.valueOf(monthAndDay[0]);
		}
		//增加2016年4.30号之前的费率通道
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
		String s_date=simpleDateFormat.format(pactInfo.getStart_date());
		String hql="";
		if(Integer.parseInt(s_date.toString())>=Integer.parseInt("20150901".toString())&&Integer.parseInt(s_date.toString())<=Integer.parseInt("20160430".toString())){
			 hql = " from ProdChargePolicy where  type='C' and prdt_no='"
					+ pactInfo.getPrdt_no() + "' and stage_min <=" + stage
					+ " and stage_max>" + stage + "";
		}else{
			hql = " from ProdChargePolicy where  type='B' and prdt_no='"
				+ pactInfo.getPrdt_no() + "' and stage_min <=" + stage
				+ " and stage_max>" + stage + "";
		}
		List<ProdChargePolicy> chargePolicy = baseService.findByHQL(hql);

		String rateString = chargePolicy.get(0).getRate();
		int days = DateUtil.getBetweenDays(
				DateTimeUtil.getDateString(pactInfo.getStart_date()),
				DateTimeUtil.getDateString(dateFormat.parse(sdf.format(d))));
		String lx = BigNumberUtil.Multiply(String.valueOf(pactInfo.getPact_amt()), BigNumberUtil.Divide2(rateString, String.valueOf(100)));
		lx=BigNumberUtil.Divide5(lx, String.valueOf(360));
		lx = BigNumberUtil.Multiply(lx, String.valueOf(days));
		bxhj = BigNumberUtil.Add(lx, String.valueOf(pactInfo.getPact_amt()));
		return bxhj;
	}

	// 到账复核回退

	public String undoAccCheck() throws Exception {

		// 更新合同
		pactInfo = subscripService.updatePact1(pactInfo, "3", null,pactInfo.getCmt());
		// 更新流程
		processService.updatePro(pactInfo, "11", "20", "30", "2", checkCmt);
		// 删除流水
		// financingDetailsService.deleteDetail(pactInfo);

		return SUCCESS;
	}

	// 到账复核

	public String accCheck() throws Exception {

		// 更新合同
		pactInfo = subscripService.updatePact1(pactInfo, "5", null,pactInfo.getCmt());
		// 更新流程
		processService.updatePro(pactInfo, "11", "30", "30", "1", checkCmt);
		// 生成还款计划
		List<PlanBean> list = planServicesImpl.createPlan(pactInfo);

		planServicesImpl.savePlanList(list);
		return SUCCESS;
	}

	public String undo() throws Exception {
		// 1.关闭合同
		pactInfo = subscripService.updatePact1(pactInfo, "7", null,"");
		// 2.更新流程
		processService.updatePro(pactInfo, "13", "99", "99", "1", "");
		// 3.关闭子账户
		subAccountService.updateSubAccount(pactInfo, "22");
		// 4.如果是续购，更新原合同状态为‘6’
		if (!"1".equals(pactInfo.getPact_type())) {
			subscripService.updateContinuePacts(pactInfo.getPact_no());
		}
		return SUCCESS;
	}

	public String submitUndoApply() {
		User loginUser = UserUtil.getLoginUser();
		// 插入撤销申请，与赎回通用一张表
		if (pactInfo.getId() != null) {
			pactInfo = (PactInfo) baseService.findByHQL(
					" from PactInfo where id=" + pactInfo.getId() + "").get(0);
			RedeemEntity redeemEntity = new RedeemEntity();
			redeemEntity.setPact_no(pactInfo.getPact_no());
			redeemEntity.setCif_name(pactInfo.getCif_name());
			redeemEntity.setCif_no(pactInfo.getCif_no());
			redeemEntity.setSts("11");
			redeemEntity.setRedem_type("4");
			redeemEntity
					.setRedem_amount(String.valueOf(pactInfo.getPact_amt()));
			redeemEntity.setPay_amt(String.valueOf(pactInfo.getPact_amt()));
			redeemEntity.setCreate_date(dateFormat.format(new Date()));
			redeemEntity.setCreate_op(loginUser.getName());
			redeemEntity.setRedem_Date(dateFormat.format(new Date()));
			redeemEntity.setOpen_id(loginUser.getId());
			redeemEntity
					.setRedem_capital(String.valueOf(pactInfo.getPact_amt()));
			redeemEntity.setRedem_interest("0.00");

			baseService.makePersistent(redeemEntity);
			// 更新合同状态
			pactInfo.setSts("65");
			baseService.makePersistent(pactInfo);
		}
		return SUCCESS;
	}

	// 续购提交
	public String submitContinue() throws Exception {
		// 更新新合同
		this.creatorUpdateCP("2", "2", true);
		// 创建新子账号
		SubAccount subAccount = subAccountService.creatSubAccount(pactInfo);
		// 创建合并\续购账号信息
		subscripService.createMergerSub(pactNos, subAccount);
		// 更新原合同状态
		subscripService.updatePacts(pactNos);
		// 创建/更新银行账户
		subscripService.createBankAcc(pactInfo);
		// 创建流程
		processService.createPro(pactInfo, "11");
		return SUCCESS;
	}

	public String renewConfirm() {

		return SUCCESS;
	}

	// 得到期数
	public String getInstallments() throws IOException {

		String hql = "from ProdChargePolicy  where type='A' and prdt_no='"
				+ this.getProdNo() + "'";
		List<ProdChargePolicy> queryList = baseService.findByHQL(hql);

		if (queryList != null && queryList.size() != 0) {
			JsonConfig config = new JsonConfig();
			// 过滤掉prodBaseEntity属性
			config.setExcludes(new String[] { "prodBaseEntity" });
			String json = JSONArray.fromObject(queryList, config).toString();

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(json);
		}
		return null;
	}

	// 得到年化收益率
	public String getRate() throws IOException {
		String hql = "from ProdChargePolicy where  type='A' and prdt_no='"
				+ this.getProdNo() + "' and stage_Max='" + this.getQishu()
				+ "'";
		List<ProdChargePolicy> queryList = baseService.findByHQL(hql);

		if (queryList != null && queryList.size() == 1) {

			String rate = queryList.get(0).getReturnRate();
			String type = queryList.get(0).getPaymentType();

			String jsonRate = "[{\"rate\":\"" + rate + "\",\"type\":\"" + type
					+ "\"}]";
			String json = JSONArray.fromObject(jsonRate).toString();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(json);
		}
		return null;
	}

	public String getJsBxhj() throws IOException {
		String bxhj = "";
		pactInfo = (PactInfo) baseService.findByHQL(
				" from PactInfo where pact_no='" + pact_no + "'").get(0);
		SubAccount subAccount = (SubAccount) baseService.findByHQL(
				" from SubAccount where pact_no='" + pact_no + "'").get(0);
		bxhj = BigNumberUtil.Add(subAccount.getCash_amt(),
				subAccount.getRenew_amt(), subAccount.getIncome_amt());
		bxhj = BigNumberUtil.Subtract(bxhj, subAccount.getRedeem_amt());

		String jsonRate = "[{\"bxhj\":\"" + bxhj + "\"}]";
		String json = JSONArray.fromObject(jsonRate).toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		return null;
	}

	// 是否可提前赎回
	public String checkAdvRedem() throws IOException, ParseException {
		String data = "no";
		pactInfo = (PactInfo) baseService.findByHQL(
				" from PactInfo where pact_no='" + pact_no + "'").get(0);
		ProdBaseEntity prodBaseEntity = (ProdBaseEntity) baseService.findByHQL(
				" from ProdBaseEntity where prdtNo='" + pactInfo.getPrdt_no()
						+ "'").get(0);
		if (prodBaseEntity != null) {
			if ("1".equals(prodBaseEntity.getAdvanceRedeem())) {// 是
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date d = sdf.parse(redem_date);
				sdf.applyPattern("yyyy-MM-dd");
				int[] monthAndDay = DateUtil.getMonthsAndDays(
						dateFormat.format(pactInfo.getStart_date()),
						dateFormat.format(dateFormat.parse(sdf.format(d))));
				String stage = "";
				if (monthAndDay[1] > 0) {
					stage = String.valueOf(monthAndDay[0] + 1);
				} else {
					stage = String.valueOf(monthAndDay[0]);
				}
				String hql = " from ProdChargePolicy where  type='B' and prdt_no='"
						+ pactInfo.getPrdt_no()
						+ "' and stage_min <="
						+ stage
						+ " and stage_max>" + stage + "";
				List<ProdChargePolicy> chargePolicy = baseService
						.findByHQL(hql);
				if (chargePolicy.size() > 0) {
					if ("1".equals(chargePolicy.get(0).getIfRedeem())) {
						data = "yes";
					}
				}

			}
			String jsonRate = "[{\"data\":\"" + data + "\"}]";
			String json = JSONArray.fromObject(jsonRate).toString();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(json);
		}

		return null;
	}

	// 得到续购金额
	// public String getIncomeAmt() throws IOException{
	// System.out.println(pactNos+"===========================");
	// String[] pactS=pactNos.split(",");
	// Double incomeAmt= 0d;
	// if (pactS!=null&&pactS.length>0) {
	// for (String s : pactS) {
	// if (!CommonUtil.isNullOrEmpty(s)) {
	// incomeAmt+=subscripService.getIncomeAmt(s);
	// }
	// }
	// }
	//
	// String jsonRate="[{\"incomeAmt\":\""+incomeAmt+"\"}]";
	// String json = JSONArray.fromObject(jsonRate).toString();
	// HttpServletResponse response = ServletActionContext.getResponse();
	// response.setContentType("text/html;charset=UTF-8");
	// response.getWriter().write(json);
	// return null;
	// }

	// 得到银行账户
	public String getBankAcc() throws IOException {
		String hql = "from BankAccount  where sts='0' and ID='"
				+ this.getCusID() + "'";
		List<BankAccount> queryList = baseService.findByHQL(hql);

		if (queryList != null && queryList.size() == 1) {
			JsonConfig config = new JsonConfig();
			// 过滤掉customer属性
			config.setExcludes(new String[] { "customer" });
			String json = JSONArray.fromObject(queryList, config).toString();

			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(json);
		}

		return null;
	}

	public String getPrdtNo() {
		return prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public String getQishu() {
		return qishu;
	}

	public void setQishu(String qishu) {
		this.qishu = qishu;
	}

	public ListSubscrip getListSubscrip() {
		return listSubscrip;
	}

	public void setListSubscrip(ListSubscrip listSubscrip) {
		this.listSubscrip = listSubscrip;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public PactInfo getPactInfo() {
		return pactInfo;
	}

	public void setPactInfo(PactInfo pactInfo) {
		this.pactInfo = pactInfo;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public IProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(IProcessService processService) {
		this.processService = processService;
	}

	public ISubscripService getSubscripService() {
		return subscripService;
	}

	public void setSubscripService(ISubscripService subscripService) {
		this.subscripService = subscripService;
	}

	public Integer getCusID() {
		return cusID;
	}

	public void setCusID(Integer cusID) {
		this.cusID = cusID;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getProdNo() {
		return prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public List<ProdChargePolicy> getListProdCP() {
		return listProdCP;
	}

	public void setListProdCP(List<ProdChargePolicy> listProdCP) {
		this.listProdCP = listProdCP;
	}

	public String getCifName() {
		return cifName;
	}

	public void setCifName(String cifName) {
		this.cifName = cifName;
	}

	public ISubAccountService getSubAccountService() {
		return subAccountService;
	}

	public void setSubAccountService(ISubAccountService subAccountService) {
		this.subAccountService = subAccountService;
	}

	public Double getBxhj() {
		return bxhj;
	}

	public void setBxhj(Double bxhj) {
		this.bxhj = bxhj;
	}

	public String getIf_continue() {
		return if_continue;
	}

	public void setIf_continue(String if_continue) {
		this.if_continue = if_continue;
	}

	public String getRedem_date() {
		return redem_date;
	}

	public void setRedem_date(String redem_date) {
		this.redem_date = redem_date;
	}

	public String getPact_no() {
		return pact_no;
	}

	public void setPact_no(String pact_no) {
		this.pact_no = pact_no;
	}

	public String getRedem_Date() {
		return redem_Date;
	}

	public void setRedem_Date(String redem_Date) {
		this.redem_Date = redem_Date;
	}

	public IMainAccountService getMainAccountService() {
		return mainAccountService;
	}

	public void setMainAccountService(IMainAccountService mainAccountService) {
		this.mainAccountService = mainAccountService;
	}

	public String getArrivaldate() {
		return arrivaldate;
	}

	public void setArrivaldate(String arrivaldate) {
		this.arrivaldate = arrivaldate;
	}

	public String getPactNos() {
		return pactNos;
	}

	public void setPactNos(String pactNos) {
		this.pactNos = pactNos;
	}

	public IPlanServices getPlanServicesImpl() {
		return planServicesImpl;
	}

	public void setPlanServicesImpl(IPlanServices planServicesImpl) {
		this.planServicesImpl = planServicesImpl;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public FinancingDetails getFinancingDetails() {
		return financingDetails;
	}

	public void setFinancingDetails(FinancingDetails financingDetails) {
		this.financingDetails = financingDetails;
	}

	public String getCheckCmt() {
		return checkCmt;
	}

	public void setCheckCmt(String checkCmt) {
		this.checkCmt = checkCmt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getClaims_no() {
		return claims_no;
	}

	public void setClaims_no(String claims_no) {
		this.claims_no = claims_no;
	}

	public String getClaims_name() {
		return claims_name;
	}

	public void setClaims_name(String claims_name) {
		this.claims_name = claims_name;
	}

	public String getClaims_amt() {
		return claims_amt;
	}

	public void setClaims_amt(String claims_amt) {
		this.claims_amt = claims_amt;
	}

	public String getMultiClaims() {
		return multiClaims;
	}

	public void setMultiClaims(String multiClaims) {
		this.multiClaims = multiClaims;
	}

	public List<Claims> getClaimsList() {
		return claimsList;
	}

	public void setClaimsList(List<Claims> claimsList) {
		this.claimsList = claimsList;
	}

	public String getHbPactNo() {
		return hbPactNo;
	}

	public void setHbPactNo(String hbPactNo) {
		this.hbPactNo = hbPactNo;
	}
	
	

}
