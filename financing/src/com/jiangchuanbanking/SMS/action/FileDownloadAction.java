package com.jiangchuanbanking.SMS.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FileDownloadAction  {

	private static final long serialVersionUID = 2849597142557397461L;
	
	private String isView;
	private String filePath;
	private String contentDisposition;
	private final Log log = LogFactory.getLog(getClass());
	
	public InputStream getTargetFile() throws Exception{
		InputStream is=FileDownloadAction.class.getClassLoader().getResourceAsStream("path.properties");
		Properties prop=new Properties();
		String path="";
		try {
			prop.load(is);
			path=prop.getProperty("downloadPath");
		} catch (IOException e) {
			log.error(e);
			throw new  Exception();
		}
		filePath = path+filePath;
		File file=new File(filePath);
		if(!file.exists()){
			System.out.println("");
		}
		System.out.println("path:"+path);
		System.out.println("filePath:"+filePath);
		return new FileInputStream(filePath); 
	}
	
	public String execute() throws Exception{
		if(isView == null){
			contentDisposition = "attachment;filename=" + filePath.replaceAll(".*/", "");
		}else{
			contentDisposition = "filename=" + filePath.replaceAll(".*/", "");
		}
		return "success";
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getIsView() {
		return isView;
	}

	public void setIsView(String isView) {
		this.isView = isView;
	}

}
