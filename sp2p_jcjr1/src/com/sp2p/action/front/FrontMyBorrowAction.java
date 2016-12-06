package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;







import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.ServletUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.BorrowType;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.BorrowService;
import com.sp2p.service.DataApproveService;
import com.sp2p.service.PublicModelService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.CompanyLoanBondingService;
import com.sp2p.service.admin.PlatformCostService;
import com.sp2p.service.admin.ShoveBorrowStyleService;
import com.sp2p.service.admin.ShoveBorrowTypeService;


/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的借款控制层
 */
public class FrontMyBorrowAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyBorrowAction.class);
	private static final long serialVersionUID = 1L;

	// ------------add by houli
	private String from;
	private String btype;
	// -----------

	private BorrowService borrowService;
	private SelectedService selectedService;
	private DataApproveService dataApproveService;
	private ShoveBorrowTypeService shoveBorrowTypeService;
	private ShoveBorrowStyleService shoveBorrowStyleService;
	private PlatformCostService platformCostService; // 平台收费参数

	// 下拉列表
	private List<Map<String, Object>> borrowPurposeList;
	private List<Map<String, Object>> borrowDeadlineMonthList;
	private List<Map<String, Object>> borrowDeadlineDayList;
	private List<Map<String, Object>> borrowMinAmountList;
	private List<Map<String, Object>> borrowMaxAmountList;
	private List<Map<String, Object>> borrowRaiseTermList;
	private List<Map<String, Object>> borrowRepayWayList;
	private List<Map<String, Object>> sysImageList;
	private Map<String, String> counterList;
	private Map<String, String> instiList;
	private UserService userService;
	private PublicModelService publicModelService;
	private CompanyLoanBondingService companyLoanBondingService;
	

	public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String doDefault() throws Exception {
		return super.doDefault();
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	public String borrowAppyInit() throws Exception {
		Map<String, String> map= publicModelService.getMessageByTypeId(16);
		String content = map.get("content");
		String[] citys = content.split(",");
		List list = Arrays.asList(citys);
		request().setAttribute("cityList", list);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowInit
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午07:05:56
	 * @Return:
	 * @Descb: 我要借款初始化
	 * @Throws:
	 */
	public String borrowInit() throws Exception {
		shoveBorrowTypeService.queryShoveBorrowTypePageAll(pageBean);
		List<Map<String, Object>> list = pageBean.getPage();
		for (Map<String, Object> map : list) {
			if (map.get("nid").equals("seconds")) {
				request().setAttribute("seconds", map.get("status"));
			} else if (map.get("nid").equals("worth")) {
				request().setAttribute("worth", map.get("status"));
			} else if (map.get("nid").equals("ordinary")) {
				request().setAttribute("ordinary", map.get("status"));
			} else if (map.get("nid").equals("field")) {
				request().setAttribute("field", map.get("status"));
			} else if (map.get("nid").equals("institutions")) {
				request().setAttribute("institutions", map.get("status"));
			} else if (map.get("nid").equals("flow")) {
				request().setAttribute("flow", map.get("status"));
			}
		}
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: creditingInit
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-7 下午05:55:26
	 * @Return:
	 * @Descb: 申请信用额度
	 * @Throws:
	 */
	public String creditingInit() throws Exception {
		Thread.sleep(100);
		User user = (User) session().getAttribute("user");
		Map<String, String> m = new HashMap<String, String>();
		m = userService.queryUserById(user.getId());
		
		String pageNum = request.getString("curPage") == null ? "" : request
				.getString("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		if (user != null) {

			// 设置为申请信用额度认证模式
			session().setAttribute("stepMode", "2");

			// 获取用户认证进行的步骤
			if (user.getAuthStep() == 1) {
				// 个人信息认证步骤
				return "querBaseData";
			} else if (user.getAuthStep() == 2) {
				// 工作信息认证步骤
				return "querWorkData";
			} else if (user.getAuthStep() == 3) {
				// VIP申请认证步骤
				return "quervipData";
			} else if (user.getAuthStep() == 4) {
				// 上传资料认证步骤
				return "userPassData";
			}
			// 查询当前可用信用额度
			Map<String, String> creditMap = borrowService
					.queryCurrentCreditLimet(user.getId());
			request().setAttribute("creditMap", creditMap);
			// 查询信用申请记录
			borrowService.queryCreditingByCondition(user.getId(), pageBean);
			// 清空paramMap
			paramMap = null;
		} else {
			returnParentUrl("", "index.jsp");
		}
		return "success";
	}

	public String addBorrowInit() throws Exception {
		Thread.sleep(100);
		// 清空paramMap
		paramMap = null;
		String t = request.getString("t") == null ? "-1" : request
				.getString("t");
		session().setAttribute("t", t);
		int tInt = Convert.strToInt(t, -1);
		User user = this.getUser();
		Map<String, String> map = new HashMap<String, String>();
		map = userService.queryUserById(user.getId());
		if (null != map) {
			if (map.get("password").equals(map.get("dealpwd"))) {
				getOut()
				.print(
						"<script>alert('*您还没有修改交易密码，为了您的帐号安全，请您先修改交易密码! ');window.location.href='setPwd.do';</script>");
				return null;
			}
			
		}
		
		Map<String, String> maps = userService.queryPersonById(user.getId());
		if(maps!=null && maps.size()>0){
			String lian_state = maps.get("lian_state");
			String idNo = maps.get("idNo");
			if("0".equals(lian_state)){
				if(idNo==null || "".equals(idNo)){
					getOut()
					.print(
							"<script>alert('*您还没有进行实名认证，不能发起借款! ');window.location.href='updateUserBaseDataJbxxrz.do';</script>");
					return null;
				}
			}
		}
		
		boolean re = userService.checkSign(user.getId());
		if (!re) {
			request().getSession().removeAttribute("user");
			request().getSession().invalidate();
			getOut()
					.print(
							"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
			return null;
		}
		btype = t;
		if (tInt < 0 || tInt > 6) {
			// 判断是否正常提交，并且范围在6种借款范围内
			return "borrowinit";
		}

		// ----这里设置borrowway的值 否则如果在这里进行资料填写跳转后，再点击发布借款无法获得值，不能进行正确跳转
		session().setAttribute("borrowWay", "0");
		Map<String, String> borrowTypeMap = null;
		if (IConstants.BORROW_TYPE_NET_VALUE.equals(t)) {
			session().setAttribute("borrowWay",
					IConstants.BORROW_TYPE_NET_VALUE);
		} else if (IConstants.BORROW_TYPE_SECONDS.equals(t)) {
			session().setAttribute("borrowWay", IConstants.BORROW_TYPE_SECONDS);
		}

		if (tInt != 0) {
			borrowTypeMap = getBorrowTypeMap(tInt + "");
			// 判断是否开启
			if (borrowTypeMap == null
					|| !"1".equals(borrowTypeMap.get("status"))) {
				return "borrowinit";
			}
			// 设置是否开启密码（1开启）

			request().setAttribute("password_status",
					borrowTypeMap.get("password_status"));

			// 设置是否开启奖励(1开启)
			request().setAttribute("award_status",
					borrowTypeMap.get("award_status"));
			String validate = borrowTypeMap.get("validate");
			if ("0".equals(validate)) {
				request().setAttribute("validateDay", "1");
			}
			// /---------cj____判断是否开启认购模式
			int subscribe_status = Convert.strToInt(borrowTypeMap
					.get("subscribe_status"), -1);
			request().setAttribute("subscribeStatus", subscribe_status);
		}

		// --
		// 设置为借款认证模式
		session().setAttribute("stepMode", "1");
		// 获取用户认证进行的步骤
		if (user.getAuthStep() == 1) {
			if (IConstants.BORROW_TYPE_NET_VALUE.equals(t)
					|| IConstants.BORROW_TYPE_SECONDS.equals(t))
				from = "1";
			// 个人信息认证步骤
			return "querBaseData";
		}

		// -------------modify by houli
		/* 净值借款，秒还借款步骤 */
		if (IConstants.BORROW_TYPE_NET_VALUE.equals(t)
				|| IConstants.BORROW_TYPE_SECONDS.equals(t)) {
			if (user.getVipStatus() == IConstants.UNVIP_STATUS) {// 没有成为VIP
				from = "1";
				return "quervipData";
			}
		} else {
			from = "";
			if (user.getAuthStep() == 2) {
				// 工作信息认证步骤
				return "querBaseData";//whb 修改都跳转到个人信息页
			} else if (user.getAuthStep() == 3) {
				// VIP申请认证步骤
				return "quervipData";
			} else if (user.getAuthStep() == 4) {
				// 上传资料认证步骤
				return "userPassData";
			}
		}
		// ---------------
//		if (user.getVipStatus() == IConstants.VIP_FAIL) {
//			getOut().print(
//					"<script>alert('您的VIP已过期,请及时续费!');parent.location.href='"
//							+ request().getContextPath()
//							+ "/borrowType.do';</script>");// +
//			return null;
//		}
		// -----------add houli 判断是否还有满标未审核通过的标的，如果存在，则不能继续发布借款
		Long result = borrowService.queryBorrowStatus(user.getId());

		if (result < 0) {
			if (tInt < 3) {// 秒还、净值标的
				Long aa = borrowService.queryBaseApprove(user.getId(), 3);
				if (aa < 0) {
					getOut()
							.print(
									"<script>alert('  您还有等待资料认证的标的，暂时还不能再次发布，如有疑问，请致电"
											+ IConstants.PRO_GLOBLE_NAME
											+ "！如果您想补充资料或修改填写的信息，请前往账户中心认证信息上传资料。');parent.location.href='"
											+ request().getContextPath()
											+ "/borrowType.do';</script>");
				} else {
					getOut()
							.print(
									"<script>alert('  您还有未审核通过的标，暂时还不能再次发布！如果您想补充资料或修改填写的信息，请前往账户中心认证信息上传资料。');parent.location.href='"
											+ request().getContextPath()
											+ "/borrowType.do';</script>");
				}
			} else {// 其它借款
				Long aa = borrowService.queryBaseApprove(user.getId(), 3);
				if (aa < 0) {
					getOut()
							.print(
									"<script>alert('  您还有等待资料认证的标，暂时还不能再次发布，如有疑问，请致电"
											+ IConstants.PRO_GLOBLE_NAME
											+ "！如果您想补充资料或修改填写的信息，请前往账户中心认证信息上传资料。');parent.location.href='"
											+ request().getContextPath()
											+ "/borrowType.do';</script>");
				} else {
					Long bb = borrowService.queryBaseFiveApprove(user.getId());
					if (bb < 0) {
						getOut()
								.print(
										"<script>alert('  您还有等待资料认证的标的，暂时还不能再次发布，如有疑问，请致电"
												+ IConstants.PRO_GLOBLE_NAME
												+ "！如果您想补充资料或修改填写的信息，请前往账户中心认证信息上传资料。');parent.location.href='"
												+ request().getContextPath()
												+ "/borrowType.do';</script>");
					} else {
						getOut()
								.print(
										"<script>alert('  您还有未审核通过的标的，暂时还不能再次发布！');parent.location.href='"
												+ request().getContextPath()
												+ "/borrowType.do';</script>");
					}

				}
			}
			return null;
		}

		if (IConstants.BORROW_OTHER.equals(t)) {
			// 其他借款
			return borrowOther();
		} else if (IConstants.BORROW_TYPE_NET_VALUE.equals(t)) {
			// 净值借款（可用+待收-待还）*0.7 > 1万
			return borrowTypeNet();
		} else if (IConstants.BORROW_TYPE_SECONDS.equals(t)) {
			// 秒还借款 需要可用金额大于（借款利息+借款手续费），其中借款利息=借款本金*借款年利率/12
			return borrowTypeSeconds();
		} else if (IConstants.BORROW_TYPE_GENERAL.equals(t)) {
			// 信用借款
			return borrowGeneral();
		} else if (IConstants.BORROW_TYPE_FIELD_VISIT.equals(t)) {
			// 实地考察借款
			return borrowFieldVisit();
		} else if (IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE.equals(t)) {
			// 机构担保借款
			return borrowGuarantee();
		} else if (IConstants.BORROW_TYPE_INSTITUTION_FLOW.equals(t)) {
			// 流转标
			return borrowFlow();
		} else {
			session().removeAttribute("typeName");
			session().removeAttribute("borrowWay");
			session().removeAttribute("borrowWay1");
			return "borrowinit";
		}
	}

	private Map<String, String> getBorrowTypeMap(String type) throws Exception {
		String nid = "";
		if (IConstants.BORROW_TYPE_NET_VALUE.equals(type)) {
			// 净值借款
			nid = BorrowType.WORTH.getValue();
		} else if (IConstants.BORROW_TYPE_SECONDS.equals(type)) {
			// 秒还借款
			nid = BorrowType.SECONDS.getValue();
		} else if (IConstants.BORROW_TYPE_FIELD_VISIT.equals(type)) {
			// 实地考察借款
			nid = BorrowType.FIELD.getValue();
		} else if (IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE.equals(type)) {
			// 机构担保借款
			nid = BorrowType.INSTITUTIONS.getValue();
		} else if (IConstants.BORROW_TYPE_GENERAL.equals(type)) {
			// 信用借款
			nid = BorrowType.ORDINARY.getValue();
		} else if (IConstants.BORROW_TYPE_INSTITUTION_FLOW.equals(type)) {
			nid = BorrowType.FLOW.getValue();// 流转标
		}
		session().setAttribute("nid", nid);
		return shoveBorrowTypeService.queryShoveBorrowTypeByNid(nid);
	}

	public String borrowOther() throws Exception {
		// 其他借款
		session().removeAttribute("typeName");
		// --------modify by houli
		// session().removeAttribute("borrowWay");
		Map<String, String> map;
		try {
			map = shoveBorrowTypeService
					.queryShoveBorrowTypeByNid(BorrowType.ORDINARY.getValue());
			if (map != null) {
				request().setAttribute("minRate", map.get("apr_first"));
				request().setAttribute("maxRate", map.get("apr_end"));
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		session().setAttribute("borrowWay", "0");
		session().removeAttribute("borrowWay1");
		// ----------------
		return "borrowtype";
	}

	public String borrowTypeNet() throws Exception {
		User user = (User) session().getAttribute("user");
		// 净值借款（可用+待收-待还）*0.7 > 1万
		session().setAttribute("typeName", "净值借款");
		session().setAttribute("borrowWay", IConstants.BORROW_TYPE_NET_VALUE);
		// ----add by houli 因为跳转发生变化，所以需要新增一个变量
		session().setAttribute("borrowWay1", IConstants.BORROW_TYPE_NET_VALUE);
		// ----
		Map<String, String> map = borrowService
				.queryBorrowTypeNetValueCondition(user.getId(), 0);
		String result = map.get("result") == null ? "" : map.get("result");
		if (!"1".equals(result)) {

			// 判定不通过也要移除borrowWay
			session().removeAttribute("borrowWay");
			getOut().print(
					"<script>alert('您的资产净值小于1万元,不能发布净值借款!');parent.location.href='"
							+ request().getContextPath()
							+ "/borrowType.do';</script>");
			return null;
		}
		return SUCCESS;
	}

	public String borrowTypeSeconds() {
		session().setAttribute("typeName", "秒还借款");
		session().setAttribute("borrowWay", IConstants.BORROW_TYPE_SECONDS);

		session().setAttribute("borrowWay1", IConstants.BORROW_TYPE_SECONDS);

		request().setAttribute("hasPWD", "1");
		// 清空paramMap
		paramMap = null;
		return "seconds";
	}

	public String borrowGeneral() throws Exception {
		User user = (User) session().getAttribute("user");
		session().setAttribute("typeName", "信用借款");
		session().setAttribute("borrowWay", "0"// modify by houli
		// IConstants.BORROW_TYPE_GENERAL
				);

		session().setAttribute("borrowWay1", IConstants.BORROW_TYPE_GENERAL);
		// add by houli 当用户发布借款的时候，给用户提示信息
		try {
			Map<String, String> map = borrowService
					.queryCurrentCreditLimet(user.getId());
			if (map != null && map.size() > 0) {
				request().setAttribute("usableCreditLimit",map.get("usableCreditLimit"));
				request().setAttribute("creditLimit", map.get("creditLimit"));
				request().setAttribute("msg", "现在您发布的为信用借款，需要通过五项基本认证");
			}
			/**判断该用户所在小贷公司是否有担保机构*/
			List<Map<String, Object>> modelList  = borrowService.queryModelType(user.getId());
			if(modelList!=null && modelList.size()>0){
				long loan_comp_id = (Long)modelList.get(0).get("loan_comp_id");
				request().setAttribute("loan_comp_id",loan_comp_id);
				request().setAttribute("modelType","1");
				request().setAttribute("modelList",modelList);
			}else{
				request().setAttribute("modelType","0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}
		// ---end
		return SUCCESS;
	}

	public String borrowFieldVisit() throws Exception {
		User user = (User) session().getAttribute("user");
		try {
			String result = queryDataApproveStatus(user.getId(),
					IConstants.FIELD_VISIT, "抵押物认证");
			if (result == null) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		session().setAttribute("typeName", "实地考察借款");
		session().setAttribute("borrowWay", "0"// modify by houli
		// 其它借款每次操作之后，都返回到信用借款主页面
				);

		session()
				.setAttribute("borrowWay1", IConstants.BORROW_TYPE_FIELD_VISIT);
		// add by houli 当用户发布借款的时候，给用户提示信息
		try {
			Map<String, String> map = borrowService
					.queryCurrentCreditLimet(user.getId());
			if (map != null && map.size() > 0) {
				request().setAttribute("usableCreditLimit",
						map.get("usableCreditLimit"));
				request().setAttribute("creditLimit", map.get("creditLimit"));
				request().setAttribute("msg", "现在您发布的为实地考察借款，需要通过五项基本认证跟抵押物认证");
				request().setAttribute("borrow_sdkc",1);
			}
			/**判断该用户所在小贷公司是否有担保机构*/
			List<Map<String, Object>> modelList  = borrowService.queryModelType(user.getId());
			if(modelList!=null && modelList.size()>0){
				long loan_comp_id = (Long)modelList.get(0).get("loan_comp_id");
				request().setAttribute("loan_comp_id",loan_comp_id);
				request().setAttribute("modelType","1");
				request().setAttribute("modelList",modelList);
			}else{
				request().setAttribute("modelType","0");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}
		// ---end

		return SUCCESS;
	}

	public String borrowGuarantee() throws Exception {
		User user = (User) session().getAttribute("user");
		try {
			String result = queryDataApproveStatus(user.getId(),
					IConstants.GUARANTEE, "机构担保");
			if (result == null) {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		session().setAttribute("typeName", "机构担保");
		session().setAttribute("borrowWay", "0"// modify by houli
		// 其它借款每次操作之后，都返回到信用借款主页面
				// IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE
				);

		session().setAttribute("borrowWay1",
				IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE);

		// add by houli 当用户发布借款的时候，给用户提示信息
		try {
			Map<String, String> map = borrowService
					.queryCurrentCreditLimet(user.getId());
			if (map != null && map.size() > 0) {
				request().setAttribute("usableCreditLimit",
						map.get("usableCreditLimit"));
				request().setAttribute("creditLimit", map.get("creditLimit"));
				request()
						.setAttribute("msg", "现在您发布的为机构担保借款，需要通过五项基本认证跟机构担保认证");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}
		// ---end

		return SUCCESS;
	}

	private String queryDataApproveStatus(Long userId, Long typeId,
			String typeStr) throws Exception {
		Map<String, String> map = dataApproveService.querySauthId(userId,
				typeId);
		if (map == null) {
			getOut().print(
					"<script>alert('请先上传" + typeStr
							+ "资料！');parent.location.href='"
							+ request().getContextPath()
							+ "/userPassData.do';</script>");
			return null;
		} else {
			Long sauthId = Convert.strToLong(map.get("id"), -1L);
			if (sauthId > 0) {
				if (map.get("auditStatus") == null
						|| map.get("auditStatus").equals("")) {
					getOut().print(
							"<script>alert('请先上传" + typeStr
									+ "资料审核！');parent.location.href='"
									+ request().getContextPath()
									+ "/userPassData.do';</script>");
					return null;
				} else {
					Long status = dataApproveService
							.queryApproveStatus(sauthId);
					if (status < 0) {
						getOut().print(
								"<script>alert('请等待" + typeStr
										+ "资料审核！');parent.location.href='"
										+ request().getContextPath()
										+ "/borrowType.do';</script>");
						return null;
					}
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 流转标 c_j
	 * 
	 * @return
	 * @throws Exception
	 */
	public String borrowFlow() throws Exception {
		User user = (User) session().getAttribute("user");
		try {
			String result = queryDataApproveStatus(user.getId(),
					IConstants.FLOW_PHONE, "手机认证");
			if (result == null) {
				return null;
			}
			//判断发流转标的用户是否属于小贷公司旗下的用户,如果是则不允许发标，否则可以哦
			Map<String,String> userMap = borrowService.queryUser(user.getId());
			if(userMap!=null && userMap.size()>0){
				getOut().print("<script>alert('您属于合作机构旗下的用户，目前不支持发流转标！');parent.location.href='"
										+ request().getContextPath()
										+ "/borrowType.do';</script>");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		session().setAttribute("typeName", "流转标");
		session().setAttribute("borrowWay", "0"// modify by houli
		// 其它借款每次操作之后，都返回到信用借款主页面
				// IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE
				);

		session().setAttribute("borrowWay1",
				IConstants.BORROW_TYPE_INSTITUTION_FLOW);

		// add by houli 当用户发布借款的时候，给用户提示信息
		try {
			Map<String, String> map = borrowService
					.queryCurrentCreditLimet(user.getId());

			session().setAttribute("borrowWay", "circulation");

			Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
			tempBorrwBidMessage = getBorrowTypeMap(IConstants.BORROW_TYPE_INSTITUTION_FLOW
					+ "");
			// 得到流转标
			// 取得按借款金额的比例进行奖励
			paramMap = new HashMap<String, String>();
			paramMap.put("scalefirst", Convert.strToStr(tempBorrwBidMessage
					.get("award_scale_first"), ""));
			paramMap
					.put("scaleend", tempBorrwBidMessage.get("award_scale_end"));
			// 如果选择金额的话，则按此奖励的金额范围
			paramMap.put("accountfirst", tempBorrwBidMessage
					.get("award_account_first"));
			paramMap.put("accountend", tempBorrwBidMessage
					.get("award_account_end"));
			// 借款额度
			paramMap.put("borrowMoneyfirst", tempBorrwBidMessage
					.get("amount_first"));
			paramMap.put("borrowMoneyend", tempBorrwBidMessage
					.get("amount_end"));
			// 借款额度倍数
			paramMap.put("accountmultiple", tempBorrwBidMessage
					.get("account_multiple"));
			// 年利率
			paramMap.put("aprfirst", tempBorrwBidMessage.get("apr_first"));
			paramMap.put("aprend", tempBorrwBidMessage.get("apr_end"));

			if (map != null && map.size() > 0) {
				request().setAttribute("usableCreditLimit",
						map.get("usableCreditLimit"));
				request().setAttribute("creditLimit", map.get("creditLimit"));
				request().setAttribute("msg", "现在您发布的为流转标借款，需要通过五项基本认证跟手机认证");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}
		// ---end
		return "flow";
	}

	public void validateAddBorrow() throws Exception {
		String borrowWay = session().getAttribute("borrowWay1") + "";
		if(borrowWay!=null && !"".equals(borrowWay) && "4".equals(borrowWay)){
			request().setAttribute("borrow_sdkc",1);
		}
		request().setAttribute("award_status",
				request.getString("award_status"));
		request().setAttribute("password_status",
				request.getString("password_status"));
		request().setAttribute("validateDay", request.getString("validateDay"));
		
		User user = (User) session().getAttribute("user");
		String 	bondingId =  request().getParameter("bondingId");
		if(bondingId!=null && !"".equals(bondingId) && "0".equals(bondingId)){
			this.addFieldError("paramMap['companyBondingMsg']", "请选择担保机构！");
		}
		request().setAttribute("bonding_ids",bondingId);
		
		/**判断该用户所在小贷公司是否有担保机构*/
		List<Map<String, Object>> modelList  = borrowService.queryModelType(user.getId());
		if(modelList!=null && modelList.size()>0){
			long loan_comp_id = (Long)modelList.get(0).get("loan_comp_id");
			request().setAttribute("loan_comp_id",loan_comp_id);
			request().setAttribute("modelType","1");
			request().setAttribute("modelList",modelList);
			
			String changeModelType = request().getParameter("changeModelType");
			if(changeModelType!=null && !"".equals(changeModelType)&& "2".equals(changeModelType)){
				String bonding_letter_path =  request().getParameter("bonding_letter_path");
				request().setAttribute("bonding_letter_path",bonding_letter_path);
				request().setAttribute("changeModelType",changeModelType);
				if(bonding_letter_path==null || "".equals(bonding_letter_path)){
					this.addFieldError("paramMap['bonding_letter_path']", "请上传担保函！");
				}
		   }
		} 
		 
		//借款金额  与 小贷公司合作总额范围的校验
		String borrow_money = paramMap.get("amount");//借款总额
		if(borrow_money!=null && !"".equals(borrow_money)){
			/**判断该发标的借款用户是否属于  合作机构下的用户，如果属于则进行校验，否则不校验**/ //即查询t_admin_user表是否有该用户记录
			Map<String,String> userMap = borrowService.queryUser(user.getId());
			if(userMap!=null && userMap.size()>0){//说明该用户是小贷公司旗下的用户
				long adminId = Long.parseLong(userMap.get("adminId"));
				
				/**根据合作机构管理员adminId查询合作机构信息*/
				Map<String,String> loanMap = borrowService.queryLoan(adminId);
				String  total_amount = loanMap.get("total_amount");//合作总额
				String  available_total_amount = loanMap.get("available_total_amount");//可用总额
				//String  has_total_amount = loanMap.get("has_total_amount");//已用总额
				String  start_time = loanMap.get("start_time");//与微信贷合作开始时间
				String  end_time = loanMap.get("end_time");//与微信贷合作结束时间
				double borrowMoney = Double.parseDouble(borrow_money);
				double totalAmount = Double.parseDouble(total_amount);
				double availableTotalAmount = Double.parseDouble(available_total_amount);
				//double hasTotalAmount = Double.parseDouble(has_total_amount);
				Date date = new Date();
				Date startTime= Convert.strToDate(start_time,new Date());
				Date endTime= Convert.strToDate(end_time,new Date());
				
				/**根据loanId查询合作机构信息*/
				String  loanId = loanMap.get("id");//合作机构loanId
				Map<String,String> bondingMap = borrowService.queryBonding(Long.parseLong(loanId),Convert.strToLong(bondingId, 0));
				if(borrowMoney > totalAmount){
					request().setAttribute("checkeds",1);
					request().setAttribute("values","您提交借款金额已大于与微信贷合作总额,合作总额"+totalAmount+"元");
					this.addFieldError("paramMap['checkDate']", "您提交借款金额已大于与微信贷合作总金额，不可借款，合作总金额  "+totalAmount+"元");
				}else if(borrowMoney > availableTotalAmount){
					request().setAttribute("checkeds",1);
					request().setAttribute("values","您提交借款金额已大于与微信贷合作授信余额,授信余额 "+availableTotalAmount+"元");
					this.addFieldError("paramMap['checkDate']", "您提交借款金额已大于与微信贷合作授信余额，不可借款，授信余额  "+availableTotalAmount+"元");
				}else if(date.getTime() <= startTime.getTime()){
					request().setAttribute("checkeds",1);
					request().setAttribute("values","与微信贷合作时间尚未开始,开始时间"+start_time);
					this.addFieldError("paramMap['checkDate']", "与微信贷合作时间尚未开始,开始时间"+start_time);
				}else if(date.getTime() >= endTime.getTime()){
					request().setAttribute("checkeds",1);
					request().setAttribute("values","与微信贷合作时间已经结束,结束时间"+end_time);
					this.addFieldError("paramMap['checkDate']", "与微信贷合作时间已经结束,结束时间"+end_time);
				}else if(bondingMap!=null && bondingMap.size()>0){
					String credit_limit = bondingMap.get("credit_limit");//担保公司对合作机构的授信额度
					String bonding_start_time = bondingMap.get("start_time");//担保公司与合作机构合作开始时间
					String bonding_end_time = bondingMap.get("end_time");//担保公司与合作机构合作结束时间
					double creditLimit = Double.parseDouble(credit_limit);
					Date bondingStartTime= Convert.strToDate(bonding_start_time,new Date());
					Date bondingEndTime= Convert.strToDate(bonding_end_time,new Date());
					double tempMoney = 0;
					Map<String,String> borrowAmountSumMap = borrowService.queryBorrowMoneySum(Convert.strToLong(bondingId, 0),Convert.strToLong(loanId, 0));//查询选择该担保公司用户的借款总额
					if(borrowAmountSumMap!=null && borrowAmountSumMap.size()>0){
						tempMoney = Convert.strToDouble(borrowAmountSumMap.get("borrowAmount"), 0)+borrowMoney;
					}else{
						tempMoney = borrowMoney;
					}
					double mm = Convert.strToDouble(borrowAmountSumMap.get("borrowAmount"), 0);
					if(tempMoney>creditLimit){
						request().setAttribute("checkeds",1);
						request().setAttribute("values","您提交借款金额已大于担保机构授信金额的剩余额,授信金额的剩余额"+(creditLimit-mm)+"元");
						this.addFieldError("paramMap['checkDate']", "您提交借款金额已大于担保机构授信金额的剩余额,授信金额的剩余额"+(creditLimit-mm)+"元");
					}else if(date.getTime()<=bondingStartTime.getTime()){
						request().setAttribute("checkeds",1);
						request().setAttribute("values","与担保机构合作开始时间尚未开始,开始时间"+bonding_start_time);
						this.addFieldError("paramMap['checkDate']", "与担保机构合作开始时间尚未开始,开始时间"+bonding_start_time);
					}else if(date.getTime()>=bondingEndTime.getTime()){
						request().setAttribute("checkeds",1);
						request().setAttribute("values","与担保机构合作结束时间已经结束,结束时间"+bonding_end_time);
						this.addFieldError("paramMap['checkDate']", "与担保机构合作结束时间已经结束,结束时间"+bonding_end_time);
					}
				}
			}
		}
		
		Map<String, String> borrowTypeMap = this.getBorrowTypeMap(borrowWay);
		if (borrowTypeMap == null) {
			this.addFieldError("paramMap['allError']", "该借款类型已关闭");
		}
		if (!"1".equals(borrowTypeMap.get("status"))) {
			this.addFieldError("paramMap['allError']", "该借款类型已关闭");
		}
		// 金额倍数
		long accountMultiple = Convert.strToLong(borrowTypeMap
				.get("account_multiple"), 0);
		// 最小年利率
		double minRate = Convert.strToDouble(borrowTypeMap.get("apr_first"),
				0.0);
		double maxRate = Convert.strToDouble(borrowTypeMap.get("apr_end"), 0.0);

		String title = paramMap.get("title").trim();
		if (StringUtils.isBlank(title)) {
			this.addFieldError("paramMap['title']", "借款标题不能为空");
		}
		if (StringUtils.isNotBlank(title) && title.length() >= 20) {
			this.addFieldError("paramMap['title']", "借款标题长度不得大于20个字符");
		}
		String imgPath = paramMap.get("imgPath");
		if (StringUtils.isBlank(imgPath)) {
			this.addFieldError("paramMap['imgPath']", "请上传借款图片");
		}
		String amount = paramMap.get("amount");
		if (StringUtils.isBlank(amount)) {
			this.addFieldError("paramMap['amount']", "请填写借款总额");
		}

		if (StringUtils.isNotBlank(amount)) {
			if (!amount
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['amount']", "借款总额格式不正确");
			} else {
				double aountD = Convert.strToDouble(amount, 0.0);

				double minAount = Convert.strToDouble(borrowTypeMap
						.get("amount_first"), 0.0);
				double maxAount = Convert.strToDouble(borrowTypeMap
						.get("amount_end"), 0.0);
				if (aountD < minAount || aountD > maxAount) {
					this.addFieldError("paramMap['amount']", "借款总额范围为"
							+ minAount + "元 ~ " + maxAount + "元");
				}

				if (accountMultiple != 0 && aountD % accountMultiple != 0) {
					this.addFieldError("paramMap['amount']", "借款总额应为"
							+ accountMultiple + "的整数倍");
				}
			}
		}

		String annualRate = paramMap.get("annualRate");
		if (StringUtils.isBlank(annualRate)) {
			this.addFieldError("paramMap['annualRate']", "请填写借款年利率");
		}
		log.info("-----请填写借款年利率----");
		if (StringUtils.isNotBlank(annualRate)) {
			if (!annualRate
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['annualRate']", "年利率格式不正确");
			} else {
				double annualRateD = Convert.strToDouble(annualRate, 0.0);
				if (annualRateD < minRate || annualRateD > maxRate) {
					this.addFieldError("paramMap['annualRate']", "年利率范围为"
							+ minRate + "%~" + maxRate + "%");
				}
			}
		}
		
		String add_interest = paramMap.get("add_interest");
		if (!StringUtils.isBlank(add_interest)) {
			if (!add_interest.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['add_interest']", "加息利率格式不正确");
			}
		}
		

		String detail = paramMap.get("detail");

		if (StringUtils.isNotBlank(detail) && detail.length() > 500) {
			this.addFieldError("paramMap['detail']", "借款详情不能超过500个字符");
		}
		String excitationType = paramMap.get("excitationType");
		int excitationTypeNum = -1;
		if (StringUtils.isNotBlank(excitationType)) {
			if (!excitationType.matches("^([0-9]\\d{0,9})$")) {
				this.addFieldError("paramMap['excitationType']", "非数字");
			}
			excitationTypeNum = Convert.strToInt(excitationType, -1);
		}

		String sum = paramMap.get("sum");
		if (StringUtils.isNotBlank(sum)) {
			if (!sum
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['sum']", "金额格式不正确");
			}
			// 投标奖励
			if ("1".equals(borrowTypeMap.get("award_status"))
					&& excitationTypeNum == 2) {
				double minSum = Convert.strToDouble(borrowTypeMap
						.get("award_account_first"), 0.0);
				double maxSum = Convert.strToDouble(borrowTypeMap
						.get("award_account_end"), 0.0);
				double sumD = Convert.strToDouble(sum, 0.0);
				if (sumD < minSum || sumD > maxSum) {
					this.addFieldError("paramMap['sum']", "奖励金额范围是" + minSum
							+ "元 ~ " + maxSum + "元");
				}
			}
		}

		String sumRate = paramMap.get("sumRate");
		if (StringUtils.isNotBlank(sumRate)) {
			if (!sumRate
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['sumRate']", "金额比例格式不正确");
			} else {
				double sumRateD = Convert.strToDouble(sumRate, 0.0);
				// 投标奖励
				if ("1".equals(borrowTypeMap.get("award_status"))
						&& excitationTypeNum == 3) {
					double minSumRate = Convert.strToDouble(borrowTypeMap
							.get("award_scale_first"), 0.0);
					double maxSumRate = Convert.strToDouble(borrowTypeMap
							.get("award_scale_end"), 0.0);

					if (sumRateD < minSumRate || sumRateD > maxSumRate) {
						this.addFieldError("paramMap['sumRate']", "奖励比率范围是"
								+ minSumRate + "% ~ " + maxSumRate + "%");
					}
				}
			}
		}

		String investPWD = paramMap.get("investPWD");
		if (StringUtils.isNotBlank(investPWD) && investPWD.length() > 20) {
			this.addFieldError("paramMap['investPWD']", "投标密码长度不得大于20个字符");
		}

		String code = paramMap.get("code");
		if (StringUtils.isBlank(code)) {
			this.addFieldError("paramMap['code']", "请填写验证码");
			code = "codss";
		}

		String raiseTerm = paramMap.get("raiseTerm");
		if (StringUtils.isBlank(raiseTerm)) {
			this.addFieldError("paramMap['raiseTerm']", "请选择投标期限");
		}

		String _code = (String) session().getAttribute("borrow_checkCode");
		if (_code == null || _code == "") {
			_code = "code";
		}
		//图形验证码不区分大小写
		if (!code.toLowerCase().equals(_code.toLowerCase())) {
			this.addFieldError("paramMap['code']", "验证码错误");
		}

		String daythe = paramMap.get("daythe");
		String deadLine = paramMap.get("deadLine");
		String deadDay = paramMap.get("deadDay");
		int deadLineInt = Convert.strToInt(deadLine, -1);
		if (!"2".equals(borrowWay)) {
			if (!"true".equals(daythe)) {
				if (deadLineInt < 0) {
					this.addFieldError("paramMap['deadLine']", "请选择期限");
				}
				String paymentMode = paramMap.get("paymentMode");
				int paymentModeInt = Convert.strToInt(paymentMode, -1);
				if (paymentModeInt < 0) {
					this.addFieldError("paramMap['paymentMode']", "请选择还款方式");
				}
			} else {
				int deadDayInt = Convert.strToInt(deadDay, -1);
				if (deadDayInt < 0) {
					this.addFieldError("paramMap['deadLine']", "请选择期限");
				}
			}
		}
		// 是否启用认购模式
		int subscribe_status = request.getInt("subscribeStatus", -1);
		String subscribe = Convert.strToStr(paramMap.get("subscribe"), "");

		if (subscribe_status != 1) {
			request().setAttribute("subscribeStatus", 2);
			String minTenderedSum = paramMap.get("minTenderedSum");
			if (StringUtils.isBlank(minTenderedSum)) {
				this.addFieldError("paramMap['minTenderedSum']", "请选择最低投标金额");
			}
		} else {
			request().setAttribute("subscribeStatus", 1);
			double subscribeMomey = Convert.strToDouble(subscribe, 0.0);
			if (subscribeMomey == 0) {
				this.addFieldError("paramMap['subscribe']", "请填写最小认购金额");
			}
			if (!subscribe
					.matches("^(([1-9][0-9]*([.][0-9]{1,2})?)|(0[.][0-9]{1,2})|(0))$")) {
				this.addFieldError("paramMap['subscribe']", "认购金额格式不正确");
			}
			// 借款总金额
			double aountDou = Convert.strToDouble(amount, 0.0);
			if (subscribeMomey != 0 && aountDou % subscribeMomey != 0) {
				this.addFieldError("paramMap['subscribe']", "认购金额应能被借款总额整除");
			}
		}
	}

	public String addBorrow() throws Exception {
		Thread.sleep(100);
		User user = (User) session().getAttribute("user");
		if (user != null) {
			
			/**判断该用户所在小贷公司是否有担保机构*/
			String bonding_letter_path = "";
			String 	bondingId =  request().getParameter("bondingId");
			request().setAttribute("bonding_ids",bondingId);
			List<Map<String, Object>> modelList  = borrowService.queryModelType(user.getId());
			if(modelList!=null && modelList.size()>0){
				long loan_comp_id = (Long)modelList.get(0).get("loan_comp_id");
				request().setAttribute("loan_comp_id",loan_comp_id);
				request().setAttribute("modelType","1");
				request().setAttribute("modelList",modelList);
				
				String changeModelType = request().getParameter("changeModelType");
				if(changeModelType!=null && !"".equals(changeModelType)&& "2".equals(changeModelType)){
					bonding_letter_path =  request().getParameter("bonding_letter_path");
					request().setAttribute("bonding_letter_path",bonding_letter_path);
					request().setAttribute("changeModelType",changeModelType);
					if(bonding_letter_path==null || "".equals(bonding_letter_path)){
						this.addFieldError("paramMap['bonding_letter_path']", "请上传担保函！");
					}
			   }
			} 

			String deadLine = paramMap.get("deadLine");
			String deadDay = paramMap.get("deadDay");
			String daythe = paramMap.get("daythe");
			String paymentMode = paramMap.get("paymentMode");
			
			//whb 添加是否专享标
			int isExclus = Convert.strToInt(paramMap.get("isExclus"), 0);
			int isLimitMaxMoney = 0;//是否限制最大投标金额  0-不限制，2-限制
			double maxMoneyValue = Convert.strToDouble(paramMap.get("maxMoneyValue"), 0);//最大投标金额
			
			int daytheInt = IConstants.DAY_THE_1;
			int deadLineInt = Convert.strToInt(deadLine, -1);
			int deadDayInt = Convert.strToInt(deadDay, -1);
			int paymentModeInt = Convert.strToInt(paymentMode, -1);
			// 天标
			if ("true".equals(daythe)) {
				deadLineInt = deadDayInt;
				daytheInt = IConstants.DAY_THE_2;
				// 为天标时默认就是按月分期还款
				paymentModeInt = 1;
			}
			// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
			Object object = session().getAttribute("borrowWay1");
			if (object == null) {
				returnParentUrl("借款发布权限不足", "borrow.do");
				return null;
			}
//			if (user.getVipStatus() != IConstants.VIP_STATUS) {
//				getOut().print(
//						"<script>alert('您的VIP已过期,请及时续费!');parent.location.href='"
//								+ request().getContextPath()
//								+ "/home.do';</script>");
//				return null;
//			}
			String borrowWay = (String) (object == null ? "" : object);
			if(borrowWay!=null && !"".equals(borrowWay) && "4".equals(borrowWay)){
				request().setAttribute("borrow_sdkc",1);
			}
			
			String title = paramMap.get("title").trim();
			String imgPath = paramMap.get("imgPath");
			String purpose = paramMap.get("purpose");
			int purposeInt = Convert.strToInt(purpose, -1);
			String amount = paramMap.get("amount");
			double amountDouble = Convert.strToDouble(amount, 0);
			String sum = paramMap.get("sum");
			double sumInt = Convert.strToDouble(sum, 0);
			if (sumInt > amountDouble) {
				this.addFieldError("enough", " *   奖励金额不能大于借款金额!");
				return "input";
			}
			boolean res = userService.checkSign(user.getId());
			if (!res) {
				request().getSession().removeAttribute("user");
				request().getSession().invalidate();
				getOut()
						.print(
								"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
				return null;
			}
			String sumRate = paramMap.get("sumRate");
			double sumRateDouble = Convert.strToDouble(sumRate, -1);
			String annualRate = paramMap.get("annualRate");
			double annualRateDouble = Convert.strToDouble(annualRate, 0);
			int minTenderedSumInt = 0;// 最小投标金额
			int maxTenderedSumInt = 0;// 最大投标金额
			// 是否启用认购模式
			int subscribe_status = request.getInt("subscribeStatus", 2);
			// 认购金额
			String subscribe = Convert.strToStr(paramMap.get("subscribe"), "");
			int circulationNumber = 0;
			if (subscribe_status != 1) {
				String minTenderedSum = paramMap.get("minTenderedSum");
				minTenderedSumInt = Convert.strToInt(minTenderedSum, 0);
				String maxTenderedSum = paramMap.get("maxTenderedSum");
				maxTenderedSumInt = Convert.strToInt(maxTenderedSum, -1);
			} else {
				// 得到认购总分份数
				circulationNumber = Integer.parseInt(amount)
						/ Integer.parseInt(subscribe);
			}
			String raiseTerm = paramMap.get("raiseTerm");
			int raiseTermInt = Convert.strToInt(raiseTerm, -1);
			String excitationType = paramMap.get("excitationType");
			if (StringUtils.isNotBlank(excitationType)) {
				// 按借款金额比例奖励
				if (StringUtils.isNumericSpace(excitationType)
						&& "3".equals(excitationType)) {
					sumInt = sumRateDouble;
				}
			}
			int excitationTypeInt = Convert.strToInt(excitationType, -1);
			String detail = paramMap.get("detail");
			//String borrowWay = (String) (object == null ? "" : object);
			String remoteIP = ServletUtils.getRemortIp();
			int borrowWayInt = Convert.strToInt(borrowWay, -1);

			if (borrowWayInt <= 0) {
				this.addFieldError("enough", "无效操作!");
				return "input";

			}
			// 查询标种类型
			Map<String, String> borrowTypeMap = getBorrowTypeMap(borrowWay);
			int number = Convert.strToInt(
					borrowTypeMap.get("subscribe_status"), -1);
			if (number != subscribe_status) {
				this.addFieldError("paramMap['allError']",
						"无效操作,该模式已关闭,请重新发布借款!");
				return "input";
			}
			// 冻结保证金
			double frozenMargin = 0;
			if (user.getVipStatus() == 2) {
				// vip冻结保证金
				frozenMargin = amountDouble
						* Double.parseDouble(borrowTypeMap
								.get("vip_frost_scale")) / 100;
			} else {
				// 普通会员冻结保证金
				frozenMargin = amountDouble
						* Double.parseDouble(borrowTypeMap
								.get("all_frost_scale")) / 100;
			}
			// 当借款为净值借款时，需要验证所输入的金额小于净值的70%
			if (IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)) {
				Map<String, String> map = borrowService
						.queryBorrowTypeNetValueCondition(user.getId(),
								amountDouble + frozenMargin);
				String result = map.get("result") == null ? "" : map
						.get("result");
				if (!"1".equals(result)) {
					this.addFieldError("enough", "您发布的借款金额超过净值+保障金的70%!");
					return "input";
				}
			}
			// 当借款类型为秒还借款时,需要进行验证是否满足条件
			if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)) {
				Map<String, String> map = borrowService
						.queryBorrowTypeSecondsCondition(amountDouble,
								annualRateDouble, user.getId(),
								getPlatformCost(), frozenMargin);
				if (map == null || map.size() == 0) {
					this.addFieldError("enough", "您的可用金额不满足秒还借款的发布条件!");
					return "input";
				}
			}
			Map<String, String> maps = borrowService.queryBorrowFinMoney(
					frozenMargin, user.getId());
			if (maps == null || maps.size() == 0) {
				this.addFieldError("enough", "您的可用金额不满足借款所需保障金的发布条件!");
				return "input";
			}

			Long result = -1L;
			// ----modify by houli 秒还借款调用的是另外一个方法，这里只需要判断净值借款即可(否则这个中间应该用&&)
			if (!IConstants.BORROW_TYPE_NET_VALUE.equals(borrowWay)) {
				// 除了秒还借款和净值借款之外，其他要验证可用信用额度是否大于发布借款金额，同时发布成功后要扣除可用信用额度
				Map<String, String> map = borrowService.queryCreditLimit(
						amountDouble, user.getId());
				if (map == null || map.size() == 0) {
					this.addFieldError("enough", "您的可用信用额度不足以发布["
							+ amountDouble + "]元的借款!");
					return "input";
				}
			}
			// 进行借款的判断，如果已经发布了借款未满标通过，就不能再次发送（解决电脑卡机，造成数据重复提交）
			Long re = borrowService.queryBorrowStatus(user.getId());
			if (re < 0) {
				getOut().print(
						"<script>alert('你还有未审核通过的标的，暂时还不能再次发布！');parent.location.href='"
								+ request().getContextPath()
								+ "/borrow.do';</script>");
				return null;
			}
			String investPWD = paramMap.get("investPWD");
			if (StringUtils.isNotBlank(investPWD)) {
				investPWD = Encrypt.MD5(investPWD);
			}
			String hasPWD = paramMap.get("hasPWD");
			if ("true".equals(hasPWD)) {
				hasPWD = "1";
			}
			int hasPWDInt = Convert.strToInt(hasPWD, -1);
			// 得到所有平台所有收费标准
			List<Map<String, Object>> mapList = platformCostService
					.queryAllPlatformCost();

			Map<String, Object> mapfee = new HashMap<String, Object>();
			Map<String, Object> mapFeestate = new HashMap<String, Object>();
			for (Map<String, Object> platformCostMap : mapList) {
				double costFee = Convert.strToDouble(platformCostMap
						.get("costFee")
						+ "", 0);
				int costMode = Convert.strToInt(platformCostMap.get("costMode")
						+ "", 0);
				String remark = Convert.strToStr(platformCostMap.get("remark")
						+ "", "");
				if (costMode == 1) {
					mapfee.put(platformCostMap.get("alias") + "",
							costFee * 0.01);
				} else {
					mapfee.put(platformCostMap.get("alias") + "", costFee);
				}
				mapFeestate.put(platformCostMap.get("alias") + "", remark); // 费用说明
				platformCostMap = null;
			}
			String json = JSONObject.fromObject(mapfee).toString();
			String jsonState = JSONObject.fromObject(mapFeestate).toString();
			
			/**判断用户是否属于合作机构旗下用户*/
			Map<String,String> userMap = borrowService.queryUser(user.getId());
			if(userMap!=null && userMap.size()>0){//说明该用户是小贷公司旗下的用户
				long adminId = Long.parseLong(userMap.get("adminId"));//得到合作机构管理员ID
				
				/**根据合作机构管理员adminId查询合作机构信息*/
				Map<String,String> loanMap = borrowService.queryLoan(adminId);
				long id = Convert.strToLong(loanMap.get("id"),0);//合作机构loanId
				double availableTotalAmount = Convert.strToDouble(loanMap.get("available_total_amount"),0);//授信余额
				double hasTotalAmount = Convert.strToDouble(loanMap.get("has_total_amount"),0);//已用金额
				double availableTotalAmountSum = availableTotalAmount - amountDouble;
				double hasTotalAmountSum = hasTotalAmount + amountDouble;
				borrowService.updateLoanCompanyMoney(id, availableTotalAmountSum, hasTotalAmountSum);
			}
			long bonding_id = Convert.strToLong(request().getParameter("bondingId"),0);
			int isShowDyw =  Convert.strToInt(request().getParameter("isShowDyw"), 0);
			double add_interest =  Convert.strToDouble(paramMap.get("add_interest"), 0);//加息利率
			Map<String, String> returnMap = null;
			returnMap = borrowService.addBorrow(title, imgPath, borrowWayInt,
					purposeInt, deadLineInt, paymentModeInt, amountDouble,
					annualRateDouble, minTenderedSumInt, maxTenderedSumInt,
					raiseTermInt, excitationTypeInt, sumInt, detail, 1,
					investPWD, hasPWDInt, remoteIP, user.getId(), frozenMargin,
					daytheInt, getBasePath(), user.getUserName(), Convert
							.strToDouble(subscribe, 0), circulationNumber, 0,
					subscribe_status, borrowTypeMap.get("identifier"),
					frozenMargin, json, jsonState,bonding_letter_path,bonding_id,isShowDyw,isExclus,
					isLimitMaxMoney,maxMoneyValue,add_interest);

			result = Convert.strToLong(returnMap.get("ret") + "", -1);
			if (result < 0) {
				this.addFieldError("enough", returnMap.get("ret_desc") + "");
				return "input";
			}

			userService.updateSign(user.getId());// 更换校验码
			if (result < 0)
				return "fail";
			// 清空paramMap
			paramMap = null;
			getOut().print(
					"<script>alert('您申请的借款已经提交管理人员进行审核，谢谢！');parent.location.href='"
							+ request().getContextPath()
							+ "/home.do';</script>");
		} else {
			return "nologin";
		}
		return null;
	}

	public void validateAddBorrowSeconds() throws Exception {
		this.validateAddBorrow();
	}

	// 校验提交借款参数
	@SuppressWarnings("unchecked")
	public boolean isValidateForTturnBid(double amountDouble,
			String excitationType, double sumRateDouble, double annualRateDouble)
			throws Exception {
		String t = (String) session().getAttribute("t");
		// 获取借款的范围
		Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
		tempBorrwBidMessage = shoveBorrowTypeService
				.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
		// 取得按借款金额的比例进行奖励
		double accountfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("award_account_first")
				+ "", 0);
		double accountend = Convert.strToDouble(tempBorrwBidMessage
				.get("award_account_end")
				+ "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "2".equals(excitationType)) {
				if (sumRateDouble < accountfirst || sumRateDouble > accountend) {
					this.addFieldError("paramMap['sum']", "固定总额奖励填写不正确");
					return false;
				}
			}
		}
		// 如果选择金额的话，则按此奖励的金额范围
		double scalefirst = Convert.strToDouble(tempBorrwBidMessage
				.get("award_scale_first")
				+ "", 0);
		double scaleend = Convert.strToDouble(tempBorrwBidMessage
				.get("award_scale_end")
				+ "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "3".equals(excitationType)) {
				if (sumRateDouble < scalefirst || sumRateDouble > scaleend) {
					this.addFieldError("paramMap['sumRate']", "奖励比例填写不正确");
					return false;
				}
			}
		}
		// 借款额度
		double borrowMoneyfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("amount_first")
				+ "", 0);
		double borrowMoneyend = Convert.strToDouble(tempBorrwBidMessage
				.get("amount_end")
				+ "", 0);
		if (borrowMoneyfirst > amountDouble || borrowMoneyend < amountDouble) {
			this.addFieldError("paramMap['amount']", "输入的借款总额不正确");
			return false;
		}
		// 借款额度倍数
		double accountmultiple = Convert.strToDouble(tempBorrwBidMessage
				.get("account_multiple")
				+ "", -1);
		if (accountmultiple != 0) {
			if (amountDouble % accountmultiple != 0) {
				this.addFieldError("paramMap['amount']", "输入的借款总额的倍数不正确");
				return false;
			}
		}
		// 年利率
		double aprfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("apr_first")
				+ "", 0);
		double aprend = Convert.strToDouble(tempBorrwBidMessage.get("apr_end")
				+ "", 0);
		if (aprfirst > annualRateDouble || aprend < annualRateDouble) {
			this.addFieldError("paramMap['annualRate']", "输入的年利率不正确");
			return false;
		}
		return true;
	}

	public String addBorrowSeconds() throws Exception {
		Thread.sleep(100);
		User user = (User) session().getAttribute("user");
		
		// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
		Object object = session().getAttribute("borrowWay");
		if (object == null) {
			returnParentUrl("", "borrow.do");
			return null;
		}
//		if (user.getVipStatus() != IConstants.VIP_STATUS) {
//			getOut().print(
//					"<script>alert('您的VIP已过期,请及时续费!');parent.location.href='"
//							+ request().getContextPath()
//							+ "/home.do';</script>");
//			return null;
//		}
		boolean res = userService.checkSign(user.getId());
		if (!res) {
			request().getSession().removeAttribute("user");
			request().getSession().invalidate();
			getOut()
					.print(
							"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
			return null;
		}
		String title = paramMap.get("title").trim();
		String imgPath = paramMap.get("imgPath");
		String purpose = paramMap.get("purpose");
		int purposeInt = Convert.strToInt(purpose, -1);
		String deadLine = paramMap.get("deadLine");
		int deadLineInt = Convert.strToInt(deadLine, 0);
		int paymentModeInt = IConstants.PAY_WAY_SECONDS;
		String amount = paramMap.get("amount");
		double amountDouble = Convert.strToDouble(amount, 0);
		String sum = paramMap.get("sum");
		double sumInt = Convert.strToDouble(sum, 0);
		String annualRate = paramMap.get("annualRate");
		double annualRateDouble = Convert.strToDouble(annualRate, 0);
		int minTenderedSumInt = 0;// 最小投标金额
		int maxTenderedSumInt = 0;// 最大投标金额
		// 是否启用认购模式
		int subscribe_status = request.getInt("subscribeStatus", -1);
		// 认购金额
		String subscribe = Convert.strToStr(paramMap.get("subscribe"), "");
		int circulationNumber = 0;
		if (subscribe_status != 1) {
			String minTenderedSum = paramMap.get("minTenderedSum");
			minTenderedSumInt = Convert.strToInt(minTenderedSum, 0);
			String maxTenderedSum = paramMap.get("maxTenderedSum");
			maxTenderedSumInt = Convert.strToInt(maxTenderedSum, -1);
		} else {
			// 得到认购总分份数
			circulationNumber = Integer.parseInt(amount)
					/ Integer.parseInt(subscribe);
		}
		String raiseTerm = paramMap.get("raiseTerm");
		int raiseTermInt = Convert.strToInt(raiseTerm, -1);
		String investPWD = paramMap.get("investPWD");
		if (StringUtils.isNotBlank(investPWD)) {
			investPWD = Encrypt.MD5(investPWD);
		}
		String hasPWD = paramMap.get("hasPWD");
		if ("true".equals(hasPWD)) {
			hasPWD = "1";
		}
		int hasPWDInt = Convert.strToInt(hasPWD, -1);
		String detail = paramMap.get("detail");
		String borrowWay = IConstants.BORROW_TYPE_SECONDS;
		String remoteIP = ServletUtils.getRemortIp();
		int borrowWayInt = Convert.strToInt(borrowWay, -1);

		// 查询标种详情
		Map<String, String> borrowTypeMap = this.getBorrowTypeMap(borrowWay);
		// 冻结保证金 ----------------
		double frozenMargin = 0;
		if (user.getVipStatus() == 2) {
			// vip冻结保证金
			frozenMargin = amountDouble
					* Double.parseDouble(borrowTypeMap.get("vip_frost_scale"))
					/ 100;
		} else {
			// 普通会员冻结保证金
			frozenMargin = amountDouble
					* Double.parseDouble(borrowTypeMap.get("all_frost_scale"))
					/ 100;
		}

		// 平台收费
		//Map<String, Object> platformCostMap = getPlatformCost();
		//double costFee = Convert.strToDouble(platformCostMap
				//.get(IAmountConstants.BORROW_FEE_RATE_1)
				//+ "", 0);
		// 秒还借款冻结借款+借款利息+借款手续费 + 冻结保证金
		//double fee = (amountDouble * (annualRateDouble * 0.01 / 12))
			//	+ (amountDouble * costFee) + frozenMargin;
		Map<String, Object> platformCostMap = borrowService.queryplatformCostLog(borrowTypeMap.get("identifier"));
		double costFee = Convert.strToDouble(platformCostMap
				.get("locan_fee")+"", 0);
		// 秒还借款冻结借款+借款利息+借款手续费 + 冻结保证金
		double fee = (amountDouble * (annualRateDouble * 0.01 / 12))
				+ (amountDouble * (costFee/100)) + frozenMargin;
		// 当借款类型为秒还借款时,需要进行验证是否满足条件
		if (IConstants.BORROW_TYPE_SECONDS.equals(borrowWay)) {
			Map<String, String> map = borrowService
					.queryBorrowTypeSecondsCondition(amountDouble,
							annualRateDouble, user.getId(), platformCostMap,
							frozenMargin);
			if (map == null || map.size() == 0) {
				this.addFieldError("enough", "您的可用金额不满足秒还借款的发布条件!");
				return "input";
			}
		}
		Long result = -1L;
		// ------add by houli 进行借款的判断，如果已经发布了借款未满标通过，就不能再次发送（解决电脑卡机，造成数据重复提交）
		Long re = borrowService.queryBorrowStatus(user.getId());
		if (re < 0) {
			getOut().print(
					"<script>alert('你还有未审核通过的标的，暂时还不能再次发布！');parent.location.href='"
							+ request().getContextPath()
							+ "/borrow.do';</script>");
			return null;
		}
		// 得到所有平台所有收费标准
		List<Map<String, Object>> mapList = platformCostService
				.queryAllPlatformCost();

		Map<String, Object> mapfee = new HashMap<String, Object>();
		Map<String, Object> mapFeestate = new HashMap<String, Object>();
		for (Map<String, Object> platformCostMaps : mapList) {
			double costFees = Convert.strToDouble(platformCostMaps
					.get("costFee")
					+ "", 0);
			int costMode = Convert.strToInt(platformCostMaps.get("costMode")
					+ "", 0);
			String remark = Convert.strToStr(platformCostMaps.get("remark")
					+ "", "");
			if (costMode == 1) {
				mapfee.put(platformCostMaps.get("alias") + "", costFees * 0.01);
			} else {
				mapfee.put(platformCostMaps.get("alias") + "", costFees);
			}
			mapFeestate.put(platformCostMaps.get("alias") + "", remark); // 费用说明
			platformCostMaps = null;
		}
		String json = JSONObject.fromObject(mapfee).toString();
		String jsonState = JSONObject.fromObject(mapFeestate).toString();
		Map<String, String> returnMap = null;
		
		//whb 添加是否专享标
		int isExclus = Convert.strToInt(paramMap.get("isExclus"), 0);
		int isLimitMaxMoney = 0;//是否限制最大投标金额  0-不限制，2-限制
		double maxMoneyValue = Convert.strToDouble(paramMap.get("maxMoneyValue"), 0);//最大投标金额
		double add_interest =  Convert.strToDouble(request().getParameter("add_interest"), 0);//加息利率
		returnMap = borrowService.addBorrow(title, imgPath, borrowWayInt,
				purposeInt, deadLineInt, paymentModeInt, amountDouble,
				annualRateDouble, minTenderedSumInt, maxTenderedSumInt,
				raiseTermInt, 1, sumInt, detail, 1, investPWD, hasPWDInt,
				remoteIP, user.getId(), fee, IConstants.DAY_THE_1,
				getBasePath(), user.getUserName(), Convert.strToDouble(
						subscribe, 0), circulationNumber, 0, subscribe_status,
				borrowTypeMap.get("identifier"), frozenMargin, json, jsonState,"",0,0,isExclus,
				isLimitMaxMoney,maxMoneyValue,add_interest);
		userService.updateSign(user.getId());// 更换校验码
		result = Convert.strToLong(returnMap.get("ret") + "", -1);
		if (result < 0) {
			this.addFieldError("enough", returnMap.get("ret_desc") + "");
			return "input";
		}
		if (result < 0)
			return "fail";
		// 清空paramMap
		paramMap = null;
		// modify by houli 客户要求发布借款之后要给出提示
		getOut()
				.print(
						"<script>alert('您申请的借款已经提交管理人员进行审核，谢谢！');parent.location.href='"
								+ request().getContextPath()
								+ "/home.do';</script>");
		return null;
	}

	/**
	 * 流转标
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addCirculationBorrow() throws Exception {
		User user = (User) session().getAttribute("user");
		
		String code = (String) session().getAttribute("borrow_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		//图形验证码不区分大小写
		if (!code.toLowerCase().equals(_code.toLowerCase())) {
			this.addFieldError("paramMap['code']", "验证码错误");
			return "input";
		}
		// 判断是否进行了借款发布资格验证,没有通过则返回到初始化
		Object object = session().getAttribute("borrowWay");
		if (object == null) {
			returnParentUrl("", "borrow.do");
			return null;
		}
//		if (user.getVipStatus() != IConstants.VIP_STATUS) {
//			getOut().print(
//					"<script>alert('您的VIP已过期,请及时续费!');parent.location.href='"
//							+ request().getContextPath()
//							+ "/home.do';</script>");
//			return null;
//		}
		boolean res = userService.checkSign(user.getId());
		if (!res) {
			request().getSession().removeAttribute("user");
			request().getSession().invalidate();
			getOut()
					.print(
							"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
			return null;
		}
		String remoteIP = ServletUtils.getRemortIp();
		String title = paramMap.get("title").trim();
		String imgPath = paramMap.get("imgPath");
		String purpose = paramMap.get("purpose");
		int purposeInt = Convert.strToInt(purpose, -1);
		String deadLine = paramMap.get("deadLine");
		int deadLineInt = Convert.strToInt(deadLine, 0);
		int paymentMode = 4;
		int borrowWay = Convert.strToInt(
				IConstants.BORROW_TYPE_INSTITUTION_FLOW, 6);
		String amount = paramMap.get("amount");
		double amountDouble = Convert.strToDouble(amount, 0);
		String annualRate = paramMap.get("annualRate");
		double annualRateDouble = Convert.strToDouble(annualRate, 0);
		String smallestFlowUnit = paramMap.get("smallestFlowUnit");
		double smallestFlowUnitDouble = Convert
				.strToDouble(smallestFlowUnit, 0);
		String businessDetail = paramMap.get("businessDetail");
		String assets = paramMap.get("assets");
		String moneyPurposes = paramMap.get("moneyPurposes");
		int circulationNumber = (int) (amountDouble / smallestFlowUnitDouble);
		double maxMoney = Convert.strToDouble(paramMap.get("maxMoneyValue"), 0);
		double bigestFlowUnit = Convert.strToDouble(paramMap.get("bigestFlowUnit"), 0);//最大流转单位
		
		Map<String, String> tempBorrwBidMessage = shoveBorrowTypeService
				.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
		if (smallestFlowUnitDouble > amountDouble) {
			this
					.addFieldError("paramMap['smallestFlowUnit']",
							"最小流转单位不能超过借款总额");
			return "input";
		}
		if (maxMoney > amountDouble) {
			this
					.addFieldError("paramMap['maxMoneyValue']",
							"累计投标金额不能超过借款总额");
			return "input";
		}
		if (maxMoney % smallestFlowUnitDouble != 0) {
			this
					.addFieldError("paramMap['maxMoneyValue']",
							"累计投标金额必须整除最小流转单位");
			return "input";
		}
		
		if(maxMoney-smallestFlowUnitDouble<0){
			this.addFieldError("paramMap['maxMoneyValue']",
					"累计投标金额必须大于或等于最小流转单位");
			return "input";
		}
		
		
		if (bigestFlowUnit % smallestFlowUnitDouble != 0) {
			this.addFieldError("paramMap['bigestFlowUnit']",
							"单次最大投标金额必须整除最小流转单位");
			return "input";
		}
		
		if(maxMoney-bigestFlowUnit<0){ 
			this.addFieldError("paramMap['bigestFlowUnit']",
					"单次最大投标金额必须小于或等于累计投标金额");
	        return "input";
		}
		
		if(bigestFlowUnit-smallestFlowUnitDouble<0){ 
			this.addFieldError("paramMap['bigestFlowUnit']",
					"单次最大投标金额必须大于或等于最小流转单位");
	        return "input";
		}
		
		if (amountDouble < Convert.strToDouble(tempBorrwBidMessage
				.get("amount_first"), 0)) {
			this.addFieldError("paramMap['amount']", "借款总额必须大于等于"
					+ Convert.strToDouble(tempBorrwBidMessage
							.get("amount_first"), 0));
			return "input";
		}
		if (amountDouble > Convert.strToDouble(tempBorrwBidMessage
				.get("amount_end"), 0)) {
			this.addFieldError("paramMap['amount']", "借款总额小于等于"
					+ Convert.strToDouble(
							tempBorrwBidMessage.get("amount_end"), 0));
			return "input";
		}
		if (paymentMode == -1) {
			this.addFieldError("paramMap['paymentMode']", "请选择还款方式");
			return "input";
		}
		if (smallestFlowUnitDouble < 1) {
			this.addFieldError("paramMap['smallestFlowUnit']", "最小流转单位必须大于等于1");
			return "input";
		}
		if (smallestFlowUnitDouble > amountDouble) {
			this
					.addFieldError("paramMap['smallestFlowUnit']",
							"最小流转单位不能超过借款总额");
			return "input";
		}
		if (amountDouble % smallestFlowUnitDouble != 0) {
			this
					.addFieldError("paramMap['smallestFlowUnit']",
							"借款总额必须整除最小流转单位");
			return "input";
		}
		String excitationType = paramMap.get("excitationType");
		String sum = paramMap.get("sum");
		double sumInt = Convert.strToDouble(sum, -1);
		String sumRate = paramMap.get("sumRate");
		double sumRateDouble = Convert.strToDouble(sumRate, -1);

		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "3".equals(excitationType)) {
				sumInt = sumRateDouble;
			}
		}

		if (excitationType.equals("2")) {
			if (!isValidateForTturnBid(amountDouble, excitationType, sumInt,
					annualRateDouble)) {
				return "input";
			}
		} else if (excitationType.equals("3")) {
			if (!isValidateForTturnBid(amountDouble, excitationType,
					sumRateDouble, annualRateDouble)) {
				return "input";
			}
		} else {
			if (!isValidateForTturnBid(amountDouble, excitationType,
					sumRateDouble, annualRateDouble)) {
				return "input";
			}
		}

		int excitationTypeInt = Convert.strToInt(excitationType, 1);
		// -------------
		// 查询标种详情
		Map<String, String> borrowTypeMap = this
				.getBorrowTypeMap(IConstants.BORROW_TYPE_INSTITUTION_FLOW);
		Map<String, String> counterList = shoveBorrowStyleService
				.querySlectStyleByTypeNid(IConstants.BORROW_TYPE_FLOW, 3);
		// /得到反担保方式
		String counterAgent = counterList.get("selectName");
		// 得到担保结构
		Map<String, String> instiList = shoveBorrowStyleService
				.querySlectStyleByTypeNid(IConstants.BORROW_TYPE_FLOW, 2);
		String agent = instiList.get("selectName");
		// 冻结保证金 ----------------
		double frozenMargin = 0;
		if (user.getVipStatus() == 2) {
			// vip冻结保证金
			frozenMargin = amountDouble
					* Double.parseDouble(borrowTypeMap.get("vip_frost_scale"))
					/ 100;
		} else {
			// 普通会员冻结保证金
			frozenMargin = amountDouble
					* Double.parseDouble(borrowTypeMap.get("all_frost_scale"))
					/ 100;
		}
		Map<String, String> maps = borrowService.queryBorrowFinMoney(
				frozenMargin, user.getId());
		if (maps == null || maps.size() == 0) {
			this.addFieldError("enough", "您的可用金额不满足借款所需保障金的发布条件!");
			return "input";
		}
		Long result = -1L;

		Long re = borrowService.queryBorrowStatus(user.getId());
		if (re < 0) {
			getOut().print(
					"<script>alert('你还有未审核通过的标的，暂时还不能再次发布！');parent.location.href='"
							+ request().getContextPath()
							+ "/borrow.do';</script>");
			return null;
		}
		// 得到所有平台所有收费标准
		List<Map<String, Object>> mapList = platformCostService
				.queryAllPlatformCost();

		Map<String, Object> mapfee = new HashMap<String, Object>();
		Map<String, Object> mapFeestate = new HashMap<String, Object>();
		for (Map<String, Object> platformMap : mapList) {
			double costFee = Convert.strToDouble(platformMap.get("costFee")
					+ "", 0);
			int costMode = Convert
					.strToInt(platformMap.get("costMode") + "", 0);
			String remark = Convert
					.strToStr(platformMap.get("remark") + "", "");
			if (costMode == 1) {
				mapfee.put(platformMap.get("alias") + "", costFee * 0.01);
			} else {
				mapfee.put(platformMap.get("alias") + "", costFee);
			}
			mapFeestate.put(platformMap.get("alias") + "", remark); // 费用说明
			platformMap = null;
		}

		String json = JSONObject.fromObject(mapfee).toString();
		String jsonState = JSONObject.fromObject(mapFeestate).toString();
		// --------------
		//whb 添加是否专享标
		int isExclus = Convert.strToInt(paramMap.get("isExclus"), 0);
		int isLimitMaxMoney = 1;//是否限制最大投标金额  0-不限制，2-限制
		double maxMoneyValue = Convert.strToDouble(paramMap.get("maxMoneyValue"), 0);//最大投标金额
		double add_interest =  Convert.strToDouble(request().getParameter("add_interest"), 0);//加息利率
		
		result = borrowService.addCirculationBorrow(bigestFlowUnit,title, imgPath, borrowWay,
				purposeInt, deadLineInt, paymentMode, amountDouble,
				annualRateDouble, remoteIP, circulationNumber,
				smallestFlowUnitDouble, user.getId(), businessDetail, assets,
				moneyPurposes, IConstants.DAY_THE_1, getBasePath(), user
						.getUserName(), excitationTypeInt, sumInt, json,
				jsonState, borrowTypeMap.get("identifier"), agent,
				counterAgent, frozenMargin,"" ,isExclus,isLimitMaxMoney,maxMoneyValue,add_interest,-1,-1);
		userService.updateSign(user.getId());// 更换校验码
		if (result < 0)
			return "fail";

		getOut()
				.print(
						"<script>alert('您申请的借款已经提交管理人员进行审核，谢谢！');parent.location.href='"
								+ request().getContextPath()
								+ "/home.do';</script>");
		return null;
	}

	/**
	 * @throws DataException
	 * @throws IOException
	 * @MethodName: addCrediting
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午04:39:43
	 * @Return:
	 * @Descb: 添加信用申请
	 * @Throws:
	 */
	public String addCrediting() throws Exception {
		Thread.sleep(100);
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String applyAmount = paramMap.get("applyAmount");
		String applyDetail = paramMap.get("applyDetail");
		double applyAmountDouble = Convert.strToDouble(applyAmount, 0);
		if (StringUtils.isBlank(applyAmount)) {
			obj.put("msg", "请填写申请资金");
			JSONUtils.printObject(obj);
			return null;
		} else if (applyAmountDouble <= 0) {
			obj.put("msg", "申请资金格式错误");
			JSONUtils.printObject(obj);
			return null;
		} else if (applyAmountDouble < 1 || applyAmountDouble > 10000000) {
			obj.put("msg", "申请资金范围1到1千万");
			JSONUtils.printObject(obj);
			return null;
		}
		if (applyDetail.length() > 500) {
			obj.put("msg", "申请详情不能超过500字符");
			JSONUtils.printObject(obj);
			return null;
		}
		Long result = -1L;
		// 验证是否能够进行申请信用额度操作,防止通过URL提交的方式
		int validate = validatedCreditingApply();
		if (validate == 1) {
			result = borrowService.addCrediting(applyAmountDouble, applyDetail,
					user.getId());
		} else {
			obj.put("msg", "您已经申请过信用额度,请等待一个月后再次申请.");
			JSONUtils.printObject(obj);
			return null;
		}
		if (result <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @MethodName: validatedCreditingApply
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-9 上午08:58:02
	 * @Return: 0 无法操作 1 可以操作
	 * @Descb: 验证是否能够进行申请信用额度操作
	 * @Throws:
	 */
	public int validatedCreditingApply() throws Exception {
		User user = (User) session().getAttribute("user");
		// 查询能够再次申请信用额度的记录
		Map<String, String> applyMap = borrowService.queryCreditingApply(user
				.getId());
		if (applyMap != null && applyMap.size() > 0) {
			String applyTime = applyMap.get("applyTime") == null ? ""
					: applyMap.get("applyTime");
			String targetTime = applyMap.get("targetTime") == null ? ""
					: applyMap.get("targetTime");
			String msg = "您已在[" + applyTime + "]申请过信用额度,<br/>请于[" + targetTime
					+ "] 以后再次申请.";
			request().setAttribute("apply", msg);
			return 0;
		} else {
			request().removeAttribute("apply");
			return 1;
		}
	}

	/**
	 * @MethodName: borrowConcernList
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午11:43:47
	 * @Return:
	 * @Descb: 关注的借款列表
	 * @Throws:
	 */
	public String borrowConcernList() throws Exception {
		User user = (User) session().getAttribute("user");

		pageBean.setPageNum(request.getInt("curPage", -1));

		String title = request.getString("title").trim();
		String publishTimeStart = request.getString("publishTimeStart");
		String publishTimeEnd = request.getString("publishTimeEnd");
		borrowService.queryBorrowConcernByCondition(title, publishTimeStart,
				publishTimeEnd, user.getId(), pageBean);
		this.setRequestToParamMap();

		return SUCCESS;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: delBorrowConcern
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午12:18:30
	 * @Return:
	 * @Descb: 删除我关注的借款
	 * @Throws:
	 */
	public String delBorrowConcern() throws Exception {
		User user = (User) session().getAttribute("user");
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1L);
		
		borrowService.delBorrowConcern(idLong, user.getId());
		return "success";
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getBorrowPurposeList() throws Exception {
		if (borrowPurposeList == null) {
			// 借款目的列表
			borrowPurposeList = selectedService.borrowPurpose();
		}
		return borrowPurposeList;
	}

	public List<Map<String, Object>> getBorrowDeadlineMonthList()
			throws Exception {
		if (borrowDeadlineMonthList == null) {
			borrowDeadlineMonthList = new ArrayList<Map<String, Object>>();
			String nid = session().getAttribute("nid") + "";
			List<String> list = shoveBorrowTypeService
					.queryDeadlineMonthListByNid(nid);

			String month = "个月";
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value + month);
				borrowDeadlineMonthList.add(map);
			}
		}
		return borrowDeadlineMonthList;
	}

	public List<Map<String, Object>> getBorrowDeadlineDayList()
			throws Exception {
		if (borrowDeadlineDayList == null) {
			borrowDeadlineDayList = new ArrayList<Map<String, Object>>();
			String nid = session().getAttribute("nid") + "";
			List<String> list = shoveBorrowTypeService
					.queryDeadlineDayListByNid(nid);

			String day = "天";
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value + day);
				borrowDeadlineDayList.add(map);
			}
		}
		return borrowDeadlineDayList;
	}

	public List<Map<String, Object>> getBorrowMinAmountList() throws Exception {
		if (borrowMinAmountList == null) {
			borrowMinAmountList = new ArrayList<Map<String, Object>>();
			String nid = session().getAttribute("nid") + "";
			List<String> list = shoveBorrowTypeService
					.queryMinAmountListByNid(nid);

			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value);
				borrowMinAmountList.add(map);
			}
		}

		return borrowMinAmountList;
	}

	public List<Map<String, Object>> getBorrowMaxAmountList() throws Exception {
		if (borrowMaxAmountList == null) {
			borrowMaxAmountList = new ArrayList<Map<String, Object>>();
			String nid = session().getAttribute("nid") + "";
			List<String> list = shoveBorrowTypeService
					.queryMaxAmountListByNid(nid);

			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value);
				borrowMaxAmountList.add(map);
			}
		}
		return borrowMaxAmountList;
	}

	public List<Map<String, Object>> getBorrowRaiseTermList() throws Exception {
		if (borrowRaiseTermList == null) {
			// 筹款期限列表
			String nid = session().getAttribute("nid") + "";
			borrowRaiseTermList = new ArrayList<Map<String, Object>>();
			List<String> list = shoveBorrowTypeService
					.queryRaiseTermLisByNid(nid);
			String day = "天";
			for (String value : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", value);
				map.put("value", value + day);
				borrowRaiseTermList.add(map);
			}
		}
		return borrowRaiseTermList;
	}

	public List<Map<String, Object>> getSysImageList() throws Exception {
		if (sysImageList == null) {
			// 系统列表
			sysImageList = selectedService.sysImageList();
		}
		return sysImageList;
	}

	public List<Map<String, Object>> getBorrowRepayWayList() throws Exception {
		if (borrowRepayWayList == null) {
			String nid = session().getAttribute("nid") + "";
			borrowRepayWayList = shoveBorrowStyleService
					.queryShoveBorrowStyleByTypeNid(nid);
		}
		return borrowRepayWayList;
	}

	public Map<String, String> getCounterList() throws Exception {
		if (counterList == null) {
			String nid = session().getAttribute("nid") + "";
			counterList = shoveBorrowStyleService.querySlectStyleByTypeNid(nid,
					3);
		}
		return counterList;
	}

	public void setCounterList(Map<String, String> counterList) {
		this.counterList = counterList;
	}

	public Map<String, String> getInstiList() throws Exception {
		if (instiList == null) {
			String nid = session().getAttribute("nid") + "";
			instiList = shoveBorrowStyleService
					.querySlectStyleByTypeNid(nid, 2);
		}
		return instiList;
	}
	
	
	/***
	 * 查询担保公司授信模式
	 * @return
	 */
	public String queryCompanyModelType(){
	
			long loanId = Convert.strToLong(request().getParameter("loanId"), 0);
		    long bondingId = Convert.strToLong(request().getParameter("bondingId"), 0);
			  
			JSONObject jo = new JSONObject();
			try {
					Map<String,String> map = companyLoanBondingService.queryModel(loanId, bondingId);
					jo.put("type", map.get("model_type"));
			} catch (Exception e) {
					e.printStackTrace();
			}
			
			printJson(jo.toString());
			return null;
	}

	public void setInstiList(Map<String, String> instiList) {
		this.instiList = instiList;
	}

	public int getCreditingApplyStatus() throws Exception {
		return validatedCreditingApply();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}

	public DataApproveService getDataApproveService() {
		return dataApproveService;
	}

	public void setDataApproveService(DataApproveService dataApproveService) {
		this.dataApproveService = dataApproveService;
	}

	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setShoveBorrowStyleService(
			ShoveBorrowStyleService shoveBorrowStyleService) {
		this.shoveBorrowStyleService = shoveBorrowStyleService;
	}

	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}

	public CompanyLoanBondingService getCompanyLoanBondingService() {
		return companyLoanBondingService;
	}

	public void setCompanyLoanBondingService(
			CompanyLoanBondingService companyLoanBondingService) {
		this.companyLoanBondingService = companyLoanBondingService;
	}
	
	

}