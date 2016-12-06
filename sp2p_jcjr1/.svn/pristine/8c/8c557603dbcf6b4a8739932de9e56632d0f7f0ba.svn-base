package com.shove.web.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.shove.vo.HelpMessage;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;

/**
 * action的基类
 *
 */
public class BaseAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	//页面表单存放数据
	protected Map<String, String> paramMap = new HashMap<String, String>();
	protected HelpMessage helpMessage = new HelpMessage();
	protected com.shove.web.Request request = new com.shove.web.Request(ServletActionContext.getRequest()); 
	protected HttpServletRequest request(){
		return ServletActionContext.getRequest();
	}
	
	protected String request(String key){
		return request().getParameter(key);
	}
	
	protected void export(HSSFWorkbook wb, String fileName) throws IOException {
		HttpServletResponse response = response();
		// 设置response的编码方式
		response.setContentType("application/x-msdownload");

		// 写明要下载的文件的大小
		// response.setContentLength((int)fileName.length());

		// 设置附加文件名
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName);

		// 解决中文乱码
		// response.setHeader("Content-Disposition","attachment;filename="+new
		// String
		// (filename.getBytes("gbk"),"iso-8859-1"));
		OutputStream output = response().getOutputStream();
		wb.write(output);
		
		output.flush();
		output.close();

	}
	
	protected long getUserId(){
		User user =(User)session().getAttribute(IConstants.SESSION_USER);
		long userId = -1;
		if(user != null){
			userId = user.getId();
		}
		return userId;
	}
	/**
	 * 得到当前时间距2013-11-01 00:00:00的小时数
	 * @return
	 * @throws ParseException
	 */
	protected long getHours() throws ParseException{
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simple.parse("2013-11-01 00:00:00");
		long millisecond=System.currentTimeMillis()-date.getTime();
		long temp = 1000*60*60 ;
		return  millisecond/temp;
	}
	/**
	 * 生成令牌
	 * @param userId
	 * @return
	 * @throws ParseException
	 */
	protected String creatToken(String userId) throws ParseException{
		long hours=getHours();
		String DES3=com.shove.security.Encrypt.encrypt3DES(hours+","+userId, IConstants.PASS_KEY);
		String md5=com.shove.security.Encrypt.MD5(DES3+IConstants.PASS_KEY);
		String token=DES3+md5.substring(0, 8);
		System.out.println(DES3);
		return token;
	}
//	/**
//	 * 校验令牌是否正确
//	 * @param token
//	 * @param userId
//	 * @return
//	 */
//	protected boolean verifyToken(String token,String userId){
//		String des=token.substring(0, 32);
//		String md5=com.shove.security.Encrypt.MD5(des+IConstants.PASS_KEY);
//		if(token.substring(32).equals(md5.substring(0, 8))){
//			String temp=com.shove.security.Encrypt.decrypt3DES(token.substring(32), IConstants.PASS_KEY);
//			String [] array=temp.split(",");
//			if(array[1].equals(userId)){
//				return true;
//			}
//		}
//		return false;
//	}
//	
	
	protected HttpSession session(){
		return ServletActionContext.getRequest().getSession();
	}
	
	protected Object session(String key){
		return session().getAttribute(key);
	}
	
	protected ServletContext application(){
		return ServletActionContext.getServletContext();
	}
	
	protected HttpServletResponse response(){
		return ServletActionContext.getResponse();
	}
	
	protected void printJson(String content){
		try {
			request().setCharacterEncoding("utf-8");
			response().setCharacterEncoding("utf-8");
			if(!StringUtils.isNotBlank(content)){
				content = "no-data";
			}
			response().getWriter().print(content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}
	
	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	public HelpMessage getHelpMessage() {
		return helpMessage;
	}
	
	public void setHelpMessage(HelpMessage helpMessage) {
		this.helpMessage = helpMessage;
	}
}
