package com.jiangchuanbanking.contract.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.impl.BaseService;
import com.jiangchuanbanking.contract.dao.TemplateDAO;
import com.jiangchuanbanking.contract.domain.Template;
import com.jiangchuanbanking.contract.service.TemplateBO;

public class TemplateBOImpl extends BaseService<Template> implements TemplateBO{

	private TemplateDAO templateDao;
	private IBaseDao baseDao;
	public TemplateDAO getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDAO templateDao) {
		this.templateDao = templateDao;
	}

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void upload(File file, String url) throws IOException   {
		String path = createUrlFolder(url.substring(0,
				url.lastIndexOf(File.separatorChar) + 1));
		InputStream in = new FileInputStream(file);
		OutputStream out = new FileOutputStream(path
				+ url.substring(url.lastIndexOf(File.separatorChar) + 1));
		
		byte[] buff = new byte[1024];
		int length = 0;
		
		while ((length = in.read(buff)) > 0) {
			out.write(buff, 0, length);
		}
		in.close();
		out.close();
	}
	public String createUrlFolder(String url) {
		File folder = new File(url);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return url;
	}



}
