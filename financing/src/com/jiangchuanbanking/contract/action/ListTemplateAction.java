package com.jiangchuanbanking.contract.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.contract.domain.PactFileInfo;
import com.jiangchuanbanking.contract.domain.Template;
import com.jiangchuanbanking.contract.domain.TemplatePackage;
import com.jiangchuanbanking.contract.service.TemplateBO;
import com.jiangchuanbanking.contract.service.TemplatePackageBO;
import com.jiangchuanbanking.redeem.domain.RedeemEntity;
import com.jiangchuanbanking.subscription.domain.ListSubscrip;
import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;

public class ListTemplateAction extends BaseListAction  {

	private static final long serialVersionUID = 6112789617185008761L;

	private IBaseService baseService;
	private TemplateBO templateBO;
	private TemplatePackageBO templatePackageBO;
	private ISelectService selectService;
	private Template template;
	private List<Template> templateList;
	private TemplatePackage entity;
	private List<TemplatePackage> templatePackageList;

	private Integer id;
	private String packageId;
	private String type;
	private String name;
	private String prdtNo;
	private String defaultFlg;

	private String query;
	private String view;

	private File upload;
	private String uploadFileName;
	private String uploadContextType;

	private String fileName;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static final String CLAZZ = TemplatePackage.class.getSimpleName();


    public String listFull() throws Exception {
		//UserUtil.permissionCheck("view_account");

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","");
		
		SearchResult<TemplatePackage> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<TemplatePackage> packages = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(packages, totalRecords, searchCondition, true);
		return null;
	}
	
	
	public String listContractGen() throws Exception{
		
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","STS='5'");
		
		SearchResult<ListSubscrip> result = baseService.getPaginationObjects(ListSubscrip.class.getSimpleName(),
				searchCondition);
		Iterator<ListSubscrip> packages = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getContractListJson(packages, totalRecords, searchCondition, true);
		return null;
	}
	
	public String listRedeemGen() throws Exception{
		
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","sts='21' and redem_type in ('1','2','3') ");

		SearchResult<RedeemEntity> result = baseService.getPaginationObjects(
				RedeemEntity.class.getSimpleName(), searchCondition);
		Iterator<RedeemEntity> redemiIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getRedeemListJson(redemiIterator, totalRecords, searchCondition, true);
		return null;
	}
	
	
	public String listPrintPact() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"CREATE_OP","sts <='6' and NAME='lcht' order by CREATE_TIME desc");
		
		SearchResult<PactFileInfo> result = baseService.getPaginationObjects(PactFileInfo.class.getSimpleName(),
				searchCondition);
		Iterator<PactFileInfo> packages = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getPrintPactListJson(packages, totalRecords, searchCondition, true);
		return null;
	}
	public String listPrintRedeem() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"CREATE_OP","sts <='22' and NAME='hg' order by CREATE_TIME desc");
		
		SearchResult<PactFileInfo> result = baseService.getPaginationObjects(PactFileInfo.class.getSimpleName(),
				searchCondition);
		Iterator<PactFileInfo> packages = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getPrintPactListJson(packages, totalRecords, searchCondition, true);
		return null;
	}
	
	public String listReceipt() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"CREATE_OP","sts <='6' and NAME='sj' order by CREATE_TIME desc");
		
		SearchResult<PactFileInfo> result = baseService.getPaginationObjects(PactFileInfo.class.getSimpleName(),
				searchCondition);
		Iterator<PactFileInfo> packages = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getPrintPactListJson(packages, totalRecords, searchCondition, true);
		return null;
	}
	
	
	
	
	private void getPrintPactListJson(Iterator<PactFileInfo> packages,
			long totalRecords, SearchCondition searchCondition, boolean isList) throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (packages.hasNext()) {
			PactFileInfo instance = packages.next();
	        int id=instance.getId();
	        String pactNo=CommonUtil.fromNullToEmpty(instance.getPact_no()); 
			//String name= CommonUtil.fromNullToEmpty(instance.getName()); 
			String path=CommonUtil.fromNullToEmpty(instance.getPath());	
			String cifName=CommonUtil.fromNullToEmpty(instance.getCif_name());
			path=path.substring(path.lastIndexOf(File.separator)+1, path.length());
            String if_export=selectService.getOpCnName("IF_EXPORT", instance.getIf_export());
			
			String createdate=CommonUtil.fromNullToEmpty(instance .getCreate_time());
			//String updatedate=CommonUtil.fromNullToEmpty(instance .getUpdate_time());
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"")
						.append(id).append("\",\"")
						.append(pactNo).append("\",\"")
						.append(cifName).append("\",\"")
						.append(path).append("\",\"")
						.append(createdate).append("\",\"")
						.append(if_export).append("\"]}");
			} else {
				
			}
			if (packages.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");
		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}


    public String listTemplate() throws Exception {
    	return SUCCESS;
    }
    public String listTemplateFull() throws Exception {
		//UserUtil.permissionCheck("view_account");
//    	entity=(TemplatePackage) baseService.getEntityById(TemplatePackage.class, this.getId());
//    	prdtNo=entity.getPrdtNo();
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","  packageId="+this.getId());
		
		SearchResult<Template> result = baseService.getPaginationObjects(Template.class.getSimpleName(),
				searchCondition);
		Iterator<Template> template = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListTemplateJson(template, totalRecords, searchCondition, true);
		return null;
	}
	public  void getListTemplateJson(Iterator<Template> template,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		String assignedTo = null;
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (template.hasNext()) {
			Template instance = template.next();
			Integer id= instance.getId();
			Integer packageId = instance.getPackageId() ;
			String name= CommonUtil.fromNullToEmpty(instance.getName()); 
			String path=CommonUtil.fromNullToEmpty(instance.getPath());
			String type = CommonUtil.fromNullToEmpty(instance .getType());
			defaultFlg=selectService.getOpCnName("CONTRACT_TYPE", defaultFlg);
			String sts = CommonUtil.fromNullToEmpty(instance .getStatus());
			sts=selectService.getOpCnName("ENABLE", sts);
			String op = CommonUtil.fromNullToEmpty(instance .getOp());
			String createdate=CommonUtil.fromNullToEmpty(instance .getCreateDate());
			String updatedate=CommonUtil.fromNullToEmpty(instance .getUpdateDate());
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"")
						.append(packageId).append("\",\"").append(id).append("\",\"").append(name)
						.append("\",\"").append(type).append("\"") .append(",\"")
						
						.append(sts).append("\",\"")
						.append(op).append("\",\"")
						.append("下载查看").append("\"]}");
			} else {
				jsonBuilder.append("{\"id\":\"").append(id)
						.append("\",\"prdtNo\":\"").append(prdtNo)
						.append("\",\"name\":\"").append(name)
						.append("\",\"path\":\"").append(path)
						.append("\",\"defaultFlg\":\"").append(defaultFlg)
						.append("\",\"status\":\"").append(sts)
						.append("\"}");
			}
			if (template.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");
		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}

	public  void getListJson(Iterator<TemplatePackage> packages,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		String assignedTo = null;
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (packages.hasNext()) {
			TemplatePackage instance = packages.next();
			Integer id= instance.getId();
			String prdtNo = CommonUtil.fromNullToEmpty(instance.getPrdtNo());
			String name= CommonUtil.fromNullToEmpty(instance.getName()); 
			String path=CommonUtil.fromNullToEmpty(instance.getPath());
			String defaultFlg = CommonUtil.fromNullToEmpty(instance .getDefaultFlg());
			defaultFlg=selectService.getOpCnName("YES_NO", defaultFlg);
			String sts = CommonUtil.fromNullToEmpty(instance .getStatus());
			sts=selectService.getOpCnName("ENABLE", sts);
			String createdate=CommonUtil.fromNullToEmpty(instance .getCreateDate());
			String updatedate=CommonUtil.fromNullToEmpty(instance .getUpdateDate());
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"")
						.append(id)
						.append("\",\"").append(prdtNo)
						.append("\",\"").append(name).append("\"") .append(",\"").
						append(defaultFlg).append("\",\"")
						.append(sts).append("\"]}");
			} else {
				jsonBuilder.append("{\"id\":\"").append(id)
						.append("\",\"prdtNo\":\"").append(prdtNo)
						.append("\",\"name\":\"").append(name)
						.append("\",\"path\":\"").append(path)
						.append("\",\"defaultFlg\":\"").append(defaultFlg)
						.append("\",\"status\":\"").append(sts)
						.append("\"}");
			}
			if (packages.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");
		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}

	
	
	
	public  void getContractListJson(Iterator<ListSubscrip> packages,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {
		StringBuilder jsonBuilder = new StringBuilder("");
		String assignedTo = null;
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (packages.hasNext()) {
			ListSubscrip instance = packages.next();
			String idNo=CommonUtil.fromNullToEmpty(instance.getId_no());
			String cifName=CommonUtil.fromNullToEmpty(instance.getCif_name());
			String prot=CommonUtil.fromNullToEmpty(instance.getPrdt_name());
			String sex=CommonUtil.fromNullToEmpty(instance.getSex());
			String contact_phone=CommonUtil.fromNullToEmpty(instance.getContact_phone());
			Double pact_amt=instance.getPact_amt();
			String term_range=CommonUtil.fromNullToEmpty(instance.getTerm_range());
			String rate=CommonUtil.fromNullToEmpty(instance.getRate());
			String pactNo=CommonUtil.fromNullToEmpty(instance.getPact_no());
			int id=instance.getId();
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"")
						.append(id).append("\",\"").append(pactNo)
						.append("\",\"").append(cifName).append("\"") .append(",\"")
						.append(idNo).append("\",\"")
						.append(contact_phone).append("\",\"")
						.append(prot).append("\",\"")
						.append(pact_amt).append("\",\"")
						.append(term_range).append("\",\"")
						.append(rate).append("\"]}");
			} else {
//				jsonBuilder
//						.append("\",\"prdtNo\":\"").append(prdtNo)
//						.append("\",\"prdtName\":\"").append(prdtName)
//						.append("\",\"name\":\"").append(name)
//						.append("\",\"path\":\"").append(path)
//						.append("\",\"defaultFlg\":\"").append(defaultFlg)
//						.append("\",\"sts\":\"").append(sts)
//						.append("\",\"createdate\":\"").append(createdate)
//						.append("\",\"updatedate\":\"").append(updatedate)
//						.append("\"}");
			}
			if (packages.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");
		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	
		private void getRedeemListJson(Iterator<RedeemEntity> redemiIterator,
				long totalRecords, SearchCondition searchCondition, boolean isList)
				throws IOException {

			StringBuilder jsonBuilder = new StringBuilder("");
			jsonBuilder
					.append(getJsonHeader(totalRecords, searchCondition, isList));
			while (redemiIterator.hasNext()) {
				RedeemEntity instance = redemiIterator.next();
				Integer cif_no  = instance.getCif_no();
				String pact_no = instance.getPact_no();
				String cif_name = instance.getCif_name();
				String sts = instance.getSts();
				String redem_type = instance.getRedem_type();
				
				redem_type=selectService.getOpCnName("REDEM_TYPE", redem_type);
				
				String redem_amount = instance.getRedem_amount();
				String pay_amt = instance.getPay_amt();
				String create_date = instance.getCreate_date();
				String create_op = instance.getCreate_op();
				String redem_Date = instance.getRedem_Date();
				String redem_capital=instance.getRedem_capital();
				String redem_interest=instance.getRedem_interest();
				int id = instance.getId();
				if (isList) {
					jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
							.append(cif_name).append("\",\"").append(pact_no)
							.append("\",\"").append(redem_type)
							.append("\",\"").append(redem_amount).append("\",\"")
							.append(redem_Date).append("\",\"")
							.append(pay_amt).append("\",\"")
							.append(redem_capital).append("\",\"")
							.append(redem_interest).append("\",\"")
							.append(create_date).append("\",\"")
							.append(create_op).append("\"]}");
				} else {
					jsonBuilder.append("{\"id\":\"").append(id)
							.append("\",\"cif_no\":\"").append(cif_no)
							.append("\",\"pact_no\":\"").append(pact_no)
							.append("\",\"cif_name\":\"")
							.append(cif_name).append("\",\"sts\":\"")
							.append(sts).append("\",\"redem_type\":\"")
							.append(redem_type).append("\",\"redem_amount\":\"")
							.append(redem_amount).append("\",\"redem_Date\":\"")
							.append(redem_Date).append("\",\"pay_amt\":\"")
							.append(pay_amt).append("\",\"redem_capital\":\"")
							.append(redem_capital).append("\",\"redem_interest\":\"")
							.append(redem_interest).append("\",\"create_date\":\"")
							.append(create_date).append("\",\"create_op\":\"")
							.append(create_op).append("\"}");
				}
				if (redemiIterator.hasNext()) {
					jsonBuilder.append(",");
				}
			}
			jsonBuilder.append("]}");

			// Returns JSON data back to page
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(jsonBuilder.toString());
		}
	

	public TemplateBO getTemplateBO() {
		return templateBO;
	}

	public void setTemplateBO(TemplateBO templateBO) {
		this.templateBO = templateBO;
	}

	public TemplatePackageBO getTemplatePackageBO() {
		return templatePackageBO;
	}

	public void setTemplatePackageBO(TemplatePackageBO templatePackageBO) {
		this.templatePackageBO = templatePackageBO;
	}


	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public List<Template> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<Template> templateList) {
		this.templateList = templateList;
	}


	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}


	public List<TemplatePackage> getTemplatePackageList() {
		return templatePackageList;
	}

	public void setTemplatePackageList(List<TemplatePackage> templatePackageList) {
		this.templatePackageList = templatePackageList;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPrdtNo() {
		return prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public String getDefaultFlg() {
		return defaultFlg;
	}

	public void setDefaultFlg(String defaultFlg) {
		this.defaultFlg = defaultFlg;
	}


	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContextType() {
		return uploadContextType;
	}

	public void setUploadContextType(String uploadContextType) {
		this.uploadContextType = uploadContextType;
	}



	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}


	@Override
	public String list() throws Exception {
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"","");
		SearchResult<TemplatePackage> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<TemplatePackage> subIterator = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(subIterator, totalRecords, searchCondition, false);
		return null;
	}
	@Override
	protected String getEntityName() {
		return  getPackageEntityClass().getSimpleName();
	}
	protected Class<TemplatePackage> getPackageEntityClass() {
		return TemplatePackage.class;
	}
	public TemplatePackage getEntity() {
		return entity;
	}
	public void setEntity(TemplatePackage entity) {
		this.entity = entity;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ISelectService getSelectService() {
		return selectService;
	}
	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}

}
