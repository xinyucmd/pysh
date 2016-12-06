package com.jiangchuanbanking.prod.action;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.prod.domain.ProdChargePolicy;
import com.jiangchuanbanking.prod.service.IProdService;
import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;


@SuppressWarnings("serial")
public class ListProdAction extends BaseListAction {

    private IBaseService<ProdBaseEntity> baseService;
    private IProdService prodService;
    private ISelectService selectService;
    private ProdBaseEntity prodBase;
    private String prdtName;
	private String prdtNo;
	private String standardAmt;
	private String advanceRedeem;
	private String delFlg;//1.停用0启用
	
	private String sts;//5新建 8补充 10待审核 15审核通过 20审核否决 
	private String startDate;
	private String endDate;
	private String createOp;
	private String createDate;
	private String cmt;
	private Set<ProdChargePolicy> chargeLists = new HashSet<ProdChargePolicy>();
    private static final String CLAZZ = ProdBaseEntity.class.getSimpleName();
	/**
	 * Gets the list data.
	 * 
	 * @return null
	 */
	public String listFull() throws Exception {
		//UserUtil.permissionCheck("view_account");

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","sts='5' or sts='20'");
		
		SearchResult<ProdBaseEntity> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<ProdBaseEntity> prods = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(prods, totalRecords, searchCondition, true);
		return null;
	}
	
	/**
	 *产品审批页面
	 * 
	 * @return list JSON data
	 */
	public String listApp() throws Exception {
				
		//UserUtil.permissionCheck("view_account");

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","sts='10' or sts='15'");
		//查询新建状态和被退回的
		
		SearchResult<ProdBaseEntity> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<ProdBaseEntity> prods = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(prods, totalRecords, searchCondition, true);
		return null;
	}
	/**
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public  void getListJson(Iterator<ProdBaseEntity> prods,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		String assignedTo = null;
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (prods.hasNext()) {
			ProdBaseEntity instance = prods.next();
			String name = CommonUtil.fromNullToEmpty(instance.getPrdtName());
			String no = CommonUtil.fromNullToEmpty(instance.getPrdtNo());
			String standardamt = CommonUtil.fromNullToEmpty(instance.getStandardAmt());

			String advanceredeem = CommonUtil.fromNullToEmpty(instance.getAdvanceRedeem());
			advanceredeem=selectService.getOpCnName("YES_NO", advanceredeem);
			String delflg = CommonUtil.fromNullToEmpty(instance .getDelFlg());
			delflg=selectService.getOpCnName("ENABLE", delflg);
			String sts = CommonUtil.fromNullToEmpty(instance .getSts());
			sts=selectService.getOpCnName("PROT_STS", sts);
			String startdate = CommonUtil.fromNullToEmpty(instance .getStartDate());
			String enddate = CommonUtil.fromNullToEmpty(instance .getEndDate());
			String createopString=CommonUtil.fromNullToEmpty(instance .getCreateOp());
			String createdateString=CommonUtil.fromNullToEmpty(instance .getCreateDate());
			String cmt=CommonUtil.fromNullToEmpty(instance .getCmt());
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"")
						.append(no).append("\",\"").append(name)
						.append("\",\"").append(standardamt).append("\"") .append(",\"").append(advanceredeem).append("\",\"")
						.append(delflg).append("\",\"").append(sts)
						.append("\",\"").append(startdate).append("\",\"")
						.append(enddate).append("\",\"")
						.append(createopString).append("\",\"").append(createdateString)
						.append("\",\"").append(cmt) .append("\"]}");
			} else {
				jsonBuilder
						.append("\",\"prdtNo\":\"").append(no)
						.append("\",\"prdtName\":\"").append(name)
						.append("\",\"standardAmt\":\"").append(standardamt)
						.append("\",\"advanceRedeem\":\"").append(advanceredeem)
						.append("\",\"delFlg\":\"").append(delflg)
						.append("\",\"sts\":\"").append(sts)
						.append("\",\"startDate\":\"").append(startdate)
						.append("\",\"endDate\":\"")
						.append(enddate)
						.append("\",\"createOp\":\"")
						.append(createopString).append("\",\"createDate\":\"")
						.append(createdateString).append("\",\"cmt\":\"")
						.append(cmt).append("\"}");
			}
			if (prods.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");
		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	 /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
//        UserUtil.permissionCheck("delete_system");
//        baseService.batchDeleteEntity(ProdBaseEntity.class, this.getSeleteIDs());
    //	prodService.deleteEntity(prdtNo);
    	prodBase = prodService.getProdByNo(prdtNo);
    	prodBase.setDelFlg("1");
    	baseService.makePersistent(prodBase);
        return "list";
    }

	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition("prdt_name",UserUtil.getLoginUser(),"","DEL_FLG='0'");
		SearchResult<ProdBaseEntity> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		
		Iterator<ProdBaseEntity> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, null, false);
		return null;
	}

	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

	public IBaseService<ProdBaseEntity> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<ProdBaseEntity> baseService) {
		this.baseService = baseService;
	}

	public ProdBaseEntity getProdBase() {
		return prodBase;
	}

	public void setProdBase(ProdBaseEntity prodBase) {
		this.prodBase = prodBase;
	}

	public IProdService getProdService() {
		return prodService;
	}

	public void setProdService(IProdService prodService) {
		this.prodService = prodService;
	}
	public String getPrdtName() {
		return prdtName;
	}
	public void setPrdtName(String prdtName) {
		this.prdtName = prdtName;
	}
	public String getPrdtNo() {
		return prdtNo;
	}
	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}
	public String getStandardAmt() {
		return standardAmt;
	}
	public void setStandardAmt(String standardAmt) {
		this.standardAmt = standardAmt;
	}
	public String getAdvanceRedeem() {
		return advanceRedeem;
	}
	public void setAdvanceRedeem(String advanceRedeem) {
		this.advanceRedeem = advanceRedeem;
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
	public Set<ProdChargePolicy> getChargeLists() {
		return chargeLists;
	}
	public void setChargeLists(Set<ProdChargePolicy> chargeLists) {
		this.chargeLists = chargeLists;
	}


	public ISelectService getSelectService() {
		return selectService;
	}

	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}
	
	
}
