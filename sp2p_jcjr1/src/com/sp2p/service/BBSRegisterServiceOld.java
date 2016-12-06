package com.sp2p.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.shove.security.Encrypt;

/**
 * @Description: 同步论坛
 * @author 杨程
 * @date Dec 25, 2010 12:00:37 PM
 */
public class BBSRegisterServiceOld {
	private static Log log = LogFactory.getLog(BBSRegisterServiceOld.class);
	private TaskExecutor taskExecutor;

	/**
	 * 
	 * @see com.zhangjihao.service.MailService#sendMailByAsynchronousMode(com.zhangjihao.bean.Email)
	 */
	public void doRegisterByAsynchronousMode(final User user) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					doRegister(user);
				} catch (Exception e) {
					log.error(e);
				}
			}
		});
	}
	
	

	public void doUpdatePwdByAsynchronousMode(final String userName,
			final String newPwd, final String oldPwd, final int type) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					doUpdatePwd(userName, newPwd, oldPwd, type);
				} catch (Exception e) {
					log.error(e);
				}
			}
		});
	}
	
	public void doExitByAsynchronousMode() {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					doExit();
				} catch (Exception e) {
					log.error(e);
				}
			}
		});
	}
	
	private void doExit() throws Exception {
		String bbsuUrl = IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL : IConstants.BBS_URL+"/";
		log.info("doExit bbs URL=" + bbsuUrl);
		connect(bbsuUrl + "otherweb.do?action=logout", "k="+Encrypt.encryptSES(IConstants.BBS_KEY, IConstants.BBS_SES_KEY));
	}


	private void doUpdatePwd(String username, String newPwd, String oldPwd,
			int type) throws Exception {
		String bbsuUrl = IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL : IConstants.BBS_URL+"/";
		log.info("doUpdatePwd bbs URL=" + bbsuUrl);
		connect(bbsuUrl + "otherweb.do?action=updatePwd", "userName="
				+ URLEncoder.encode(username+"","UTF-8")
				+ "&password="
				+ URLEncoder.encode(oldPwd.trim()+"","UTF-8")
				+ "&newPassword="
				+ URLEncoder.encode(Encrypt.MD5(newPwd.trim()+IConstants.PASS_KEY)+"","UTF-8")
				+ "&k="
				+ Encrypt.encryptSES(IConstants.BBS_KEY, IConstants.BBS_SES_KEY)
				+ "&type=" + type);
	}

	private void doRegister(User user) throws Exception {
		String password = user.getPassword();
		String bbsuUrl = IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL : IConstants.BBS_URL+"/";
		log.info("register bbs URL=" + bbsuUrl);
		String emailConidtion = "";
		if(user.getEmail().equals("")){
			emailConidtion ="admin@admin.com";
		}else{
			emailConidtion = user.getEmail();
		}
		connect(
				bbsuUrl + "register.jsp",
				"regsubmit=yes&alipay=&answer=&bday=0000-00-00&bio=&dateformat=0&email="
				+URLEncoder.encode(emailConidtion+"","UTF-8")+"&formHash=6a36c78f&gender=0&icq=&location=&msn=&newsletter=1&password="
				+URLEncoder.encode(Encrypt.MD5(password.trim()+IConstants.PASS_KEY)+"","UTF-8")+"&password2="+URLEncoder.encode(Encrypt.MD5(password.trim()+IConstants.PASS_KEY)+"","UTF-8")+"&pmsound=1&ppp=0&qq=&questionid=0&showemail=1&signature=&site=&styleid=0&taobao=&timeformat=0&timeoffset=9999&tpp=0&username="+URLEncoder.encode(user.getUserName()+"","UTF-8")+"&yahoo=&k="+Encrypt.encryptSES(IConstants.BBS_KEY, IConstants.BBS_SES_KEY));
		}

	private String connect(String _url, String parameters) throws IOException {
		URL url = new URL(_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setAllowUserInteraction(false);
		conn.setRequestProperty("User-Agent", "Internet Explorer");
		BufferedOutputStream buf = new BufferedOutputStream(conn
				.getOutputStream());
		buf.write(parameters.getBytes(), 0, parameters.length());
		buf.flush();
		buf.close();
		String cookie = conn.getHeaderField("Set-Cookie");
		String sessionId = cookie.substring(0, cookie.indexOf(";"));
		conn.disconnect();

		return sessionId;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String strURL=IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL : IConstants.BBS_URL+"/";
		URL url = new URL(strURL + "other.do?action=updatePwd");
		String parameters = "userName=TEST3&password=123456&newPassword=1234567";
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setAllowUserInteraction(false);
		conn.setRequestProperty("User-Agent", "Internet Explorer");
		BufferedOutputStream buf = new BufferedOutputStream(conn
				.getOutputStream());

		buf.write(parameters.getBytes(), 0, parameters.length());
		buf.flush();
		buf.close();
		String cookie = conn.getHeaderField("Set-Cookie");
		String sessionId = cookie.substring(0, cookie.indexOf(";"));
		System.out.println("sessionId=" + sessionId);
		conn.disconnect();

	}
	/**
	 * 文件更改
	 * @param ips
	 * @param fileName
	 * @throws IOException
	 */
	private static void challage(InputStream ips, String fileName)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] contents = new byte[1024];
		int len = 0;
		while ((len = ips.read(contents)) != -1) {
			fos.write(contents, 0, len);
		}
		fos.flush();
		fos.close();
		ips.close();
	}
	/**
	 * 更改邮箱
	 * @param userName
	 * @param email
	 */
	public void doUpdateEmailByAsynchronousMode(final String userName,final String email){
		taskExecutor.execute(new Runnable() {
			public void run() {
			try {
				doUpdateEmail(userName, email);
			} catch (Exception e) {
			log.error(e);
			}
			}
			});
		}
		private void doUpdateEmail(String username,String email) throws Exception{
			String bbsuUrl = IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL : IConstants.BBS_URL+"/";
			log.info("doUpdateEmail bbs URL=" + bbsuUrl);
			connect(bbsuUrl + "otherweb.do?action=updateEmail", "userName="
			+ URLEncoder.encode(username+"","UTF-8")
			+ "&email="+URLEncoder.encode(email+"","UTF-8")
			+ "&k="
			+ Encrypt.encryptSES(IConstants.BBS_KEY, IConstants.BBS_SES_KEY));
		}
}
