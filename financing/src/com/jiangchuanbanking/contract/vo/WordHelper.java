/**
 * Copyright(c)2012
 * DHC Software Co., Ltd.
 *
 * All Rights Reserved
 *
 * Revision History:
 *                       Modification        Tracking
 * Author (Email ID)     Date                Number              Description
 * xiongxiaoming         2012-07-27          BugId
 *
 */
package com.jiangchuanbanking.contract.vo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Map;


import com.jiangchuanbanking.util.ExceptionHandler;
import com.jiangchuanbanking.util.IOStream;
import com.jiangchuanbanking.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class WordHelper {

	private static final String WORD_ENCODING = "UTF-8";
	private Configuration configuration = null;

	/** ftl filename **/
	private String templateFile;
	private String wordFile;
	private Map variables;

	public WordHelper() {
		configuration = new Configuration();
		configuration.setDefaultEncoding(WORD_ENCODING);
	}
	
	public WordHelper(String templateFile, String wordFile, Map variables) {
		this();
		this.templateFile = templateFile;
		this.wordFile = wordFile;
		this.variables = variables;
	}

	/**
	 * Create Word Document from templateFile.
	 */
	public String createWord() {
		if(StringUtil.isEmpty(templateFile)) {
			ExceptionHandler.rethrow(
					new RuntimeException("templateFile must specified while creating word document."));
		}
		if(StringUtil.isEmpty(wordFile)) {
			ExceptionHandler.rethrow(
					new RuntimeException("wordFile must specified while creating word document."));
		}

		Template template = null;
		try {
			
		
	         
//			URL url = ResourceUtil.getResource(templateFile, null);
			//File fltFile = new File(templateFile);
		//	configuration.setDirectoryForTemplateLoading(fltFile.getParentFile());

			String tempurl=templateFile;
			
			templateFile =   templateFile.replace("\\", "/"); 
	        templateFile = templateFile.substring(0, templateFile.lastIndexOf("/")); 
	        
	        File fltFile = new File(templateFile);
	        
	        configuration.setDirectoryForTemplateLoading(fltFile);
	         
	         
			configuration.setObjectWrapper(new DefaultObjectWrapper());
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			File fltFiletemp = new File(tempurl);
			
			template = configuration.getTemplate(fltFiletemp.getName());
			
//			tempurl=tempurl.replace("\\", "/");
//			String filename=tempurl.substring((tempurl.lastIndexOf("/"))+1, tempurl.length());
//			
//			template = configuration.getTemplate(filename);
			
			template.setEncoding(WORD_ENCODING);
			
		} catch (IOException e) {
			ExceptionHandler.rethrow(
					new RuntimeException("setting template or read template file failure while creating word document."+wordFile));
		}
		
		File file = new File(wordFile);
		Writer writer = null;  
		try {  
			writer = new BufferedWriter(new OutputStreamWriter(  
					new FileOutputStream(file), WORD_ENCODING));  
			template.process(variables, writer);  
			
		} catch (Exception e) {  
			ExceptionHandler.rethrow(e);
		} finally {
			IOStream.close(writer);
		}
		return file.getAbsolutePath();
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	
	public String getTemplateFile() {
		return templateFile;
	}

	public String getWordFile() {
		return wordFile;
	}

	public void setWordFile(String wordFile) {
		this.wordFile = wordFile;
	}	
	
	public void setVariables(Map variables) {
		this.variables = variables;
	}

	public Map getVariables() {
		return variables;
	}
}
