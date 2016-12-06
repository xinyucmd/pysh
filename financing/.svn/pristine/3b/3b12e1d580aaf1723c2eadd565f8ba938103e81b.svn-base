package com.jiangchuanbanking.contract.service.impl;

import java.io.File;

import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.contract.dao.TemplatePackageDAO;
import com.jiangchuanbanking.contract.domain.TemplatePackage;
import com.jiangchuanbanking.contract.service.TemplatePackageBO;




public class TemplatePackageBOImpl extends BaseService<TemplatePackage> implements TemplatePackageBO{

	private TemplatePackageDAO templatePackageDao;
	private IBaseDao baseDao;
	public TemplatePackageDAO getTemplatePackageDao() {
		return templatePackageDao;
	}
	public void setTemplatePackageDao(TemplatePackageDAO templatePackageDao) {
		this.templatePackageDao = templatePackageDao;
	}
	public IBaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public void createUrlFolder(String url) {
		File folder = new File(url);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
}
