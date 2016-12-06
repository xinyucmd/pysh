package com.jiangchuanbanking.contract.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.components.barbecue.BaseBarcodeProvider;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.contract.domain.Template;
import com.jiangchuanbanking.contract.domain.TemplatePackage;
import com.jiangchuanbanking.contract.service.TemplateBO;
import com.jiangchuanbanking.contract.service.TemplatePackageBO;
import com.jiangchuanbanking.dict.service.impl.OptionService;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.select.domain.WealthParnDic;
import com.jiangchuanbanking.system.security.UserUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class EditTemplateAction extends BaseEditAction implements Preparable {

	private static final long serialVersionUID = 6112789617185008761L;

	private IBaseService baseService;
	private TemplateBO templateBO;
	private TemplatePackageBO templatePackageBO;

	private Template template;
	private List<Template> templateList;
	private TemplatePackage entity;
	private List<TemplatePackage> templatePackageList;
	private Integer templateId;
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

	protected Class<TemplatePackage> getPackageEntityClass() {
		return TemplatePackage.class;
	}

	/**
	 * Saves the entity.
	 * 
	 * @return the SUCCESS result
	 */
	public String save() throws Exception {
		entity = new TemplatePackage();
		if (this.getId() == null) {
			entity.setPath(makePackagePath(name, prdtNo));
			entity.setDefaultFlg("1");
			entity.setStatus("0");
			entity.setCreateDate(new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date()));
		} else {
			entity.setId(this.getId());
		}
		entity.setPrdtNo(prdtNo);
		entity.setName(name);

		getBaseService().makePersistent(entity);
		return SUCCESS;
	}

	public String saveTemplate() {
		template = new Template();
		entity = (TemplatePackage) baseService.findByHQL(
				" from TemplatePackage where id=" + this.getId()).get(0);
		String path = entity.getPath() + File.separator + uploadFileName;
		template.setPackageId(this.getId());
		template.setName(uploadFileName);
		template.setType(type);
		template.setPackageName(entity.getName());
		template.setPath(path);
		template.setStatus("0");
		template.setOp(this.getLoginUser().getName());
		template.setCreateDate(new SimpleDateFormat("yyyyMMdd")
				.format(new Date()));
		baseService.makePersistent(template);
		try {
			templateBO.upload(upload, path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String updateTemplate() {
		template = new Template();
		entity = (TemplatePackage) baseService.findByHQL(
				" from TemplatePackage where id=" + this.getId()).get(0);
		String path = entity.getPath() + File.separator + uploadFileName;
		template.setId(this.getTemplateId());
		template.setPackageId(this.getId());
		template.setName(uploadFileName);
		template.setType(type);
		template.setPackageName(entity.getName());
		template.setPath(path);
		template.setStatus("0");
		template.setOp(this.getLoginUser().getName());
		template.setCreateDate(new SimpleDateFormat("yyyyMMdd")
				.format(new Date()));
		baseService.makePersistent(template);
		try {
			templateBO.upload(upload, path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String makePackagePath(String packageNage, String prdt) {
		ActionContext ac = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ac
				.get(ServletActionContext.HTTP_REQUEST);
		// request.getSession().getServletContext().getRealPath("") +
		String path = "D:"+ File.separator+"jclcContracts"
				+ File.separator + "contractTemplate" + File.separator + prdt
				+ File.separator + packageNage;
		templatePackageBO.createUrlFolder(path);
		return path.toString();
	}

	/**
	 * Deletes the selected entity.
	 * 
	 * @return the SUCCESS result
	 */
	public String deletePackage() throws Exception {
		getBaseService().batchDeleteEntity(getPackageEntityClass(),
				this.getSeleteIDs());
		List<Template> templates = new ArrayList<Template>();
		for (String a : this.getSeleteIDs().split(",")) {
			templates.addAll(baseService
					.findByHQL(" from Template where packageId ="
							+ Integer.parseInt(a)));
		}
		String b = "";
		for (Template template : templates) {
			b += template.getId() + ",";
		}
		if (!"".equals(b)) {
			getBaseService().batchDeleteEntity(Template.class, b);
		}
		
		return SUCCESS;
	}
	public String deleteTemplate() throws Exception {
		getBaseService().batchDeleteEntity(Template.class, this.getSeleteIDs());
		return SUCCESS;
	}

	public String modifyTemplate() {
		if (this.getId() != null) {
			entity = (TemplatePackage) baseService.findByHQL(
					" from TemplatePackage where id=" + this.getId()).get(0);
		}
		if (this.getTemplateId() != null) {
			template = (Template) baseService.findByHQL(
					" from Template where id=" + this.getTemplateId()).get(0);
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String get() throws Exception {
		if (this.getId() != null) {
			entity = (TemplatePackage) baseService.findByHQL(
					" from TemplatePackage where id=" + this.getId()).get(0);
		}
		this.initBaseInfo();
		return SUCCESS;
	}
public String addPackage(){
	this.setId(null);
	return SUCCESS;
}
	public String editPackage() throws Exception {
		if (this.getId() != null) {
			String hql = "from TemplatePackage where id='" + id + "'";
			List<TemplatePackage> list = baseService.findByHQL(hql);
			entity = list.get(0);
		}
		this.initBaseInfo();
		return SUCCESS;
	}

	public InputStream getTargetFile() throws Exception {
		template = (Template) baseService.getEntityById(Template.class, this.getTemplateId());
		fileName = template.getName();
		return new FileInputStream(new File(template.getPath()));
	}
	public String templateDetail() {
		return "templateDetail";
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
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

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

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

}
