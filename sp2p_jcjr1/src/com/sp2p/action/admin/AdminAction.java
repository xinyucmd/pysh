package com.sp2p.action.admin;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.BeanMapUtils;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RoleRightsService;
import com.sp2p.service.admin.RoleService;

@SuppressWarnings( { "serial", "unchecked" })
public class AdminAction extends BasePageAction {

	public static Log log = LogFactory.getLog(AdminAction.class);

	private AdminService adminService;
	private RoleService roleService;
	private RoleRightsService roleRightsService;
	private UserService userService;
	private List<Map<String, Object>> roleList;

	/**
	 * 论坛后台登陆
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logging() throws Exception {
		getOut().print(
				"<script>parent.location.href='" + IConstants.BBS_URL
						+ "logging.do?action=toLogin&admin=admin';</script>");
		return null;
	}

	/**
	 * 查询管理员初始化
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String queryAdminInit() throws Exception {
		Map<String, String> uMaps = null;
		uMaps=adminService.queryUTool();
		request().setAttribute("uMaps", uMaps);
		return SUCCESS;
	}

	/**
	 * 查询管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAdminInfo() throws Exception {
		String userName = paramMap.get("userName");
		Integer enable = Convert.strToInt(paramMap.get("enable"), -1);
		Long roleId = Convert.strToLong(paramMap.get("roleId"), -1);
		adminService.queryAdminPage(userName, enable, roleId, pageBean);
		return SUCCESS;
	}

	/**
	 * 查询管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String isenableAdmin() throws Exception {
		long id = request.getLong("id", -1);
		int enable = request.getInt("enable", -1);
		long result = adminService.isenableAdmin(id, enable);
		if (result > 0) {
			return SUCCESS;
		}
		return INPUT;
	}

	/**
	 * 添加管理员初始化
	 * 
	 * @return
	 */
	public String addAdminInit() {
		paramMap.put("enable", 1 + "");
		return SUCCESS;
	}

	/**
	 * 添加管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAdmin() throws Exception {
		String userName = paramMap.get("userName");
		String password = paramMap.get("password");
		String realName = paramMap.get("realName");
		String telphone = paramMap.get("telphone");
		String qq = paramMap.get("qq");
		String email = paramMap.get("email");
		String img = paramMap.get("img");
		String isLeader = paramMap.get("isLeader");
		Integer enable = Integer.parseInt(paramMap.get("enable"));
		long roleId = Convert.strToLong(paramMap.get("roleId"), -1);
		Long returnId = -1L;
		try {
			returnId = adminService.addAdmin(userName, password, enable,
					roleId, realName, telphone, qq, email, img, isLeader);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		if (returnId == -2) {
			this.addFieldError("paramMap.userName", "用户名重复");
			return INPUT;
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_admin", admin.getUserName(),
				IConstants.INSERT, admin.getLastIP(), 0, "添加新管理员", 2);
		return SUCCESS;
	}
	
	
	/**
	 * 添加小贷公司超级管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public void addLoanAdmin() throws Exception {
		
		JSONObject json = new JSONObject();
		String userName =request().getParameter("userName");
		String password = request().getParameter("password");
		String realName = request().getParameter("realName");
		String telphone =request().getParameter("telphone");
		String qq = request().getParameter("qq");
		String email = request().getParameter("email");
		String img = request().getParameter("img");
		String isLeader = request().getParameter("isLeader");
		Integer enable = Integer.parseInt(request().getParameter("enable"));
		long roleId = Convert.strToLong(request().getParameter("roleId"),0);
		long loan_bonding_loanid = Convert.strToLong(request().getParameter("loan_bonding_loanid"),0);
		
		Long returnId = -1L;
		try {
			returnId = adminService.addLoanAdmin(userName, password, enable,
					roleId, realName, telphone, qq, email, img, isLeader,loan_bonding_loanid);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		if (returnId == -2) {
			this.addFieldError("paramMap.userName", "用户名重复");
			json.put("status", 0);
		}else if(returnId == -3){
			json.put("status", -3);
		}else{
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_admin", admin.getUserName(),
					IConstants.INSERT, admin.getLastIP(), 0, "添加新管理员", 2);
			json.put("status", 1);
		}
		printJson(json.toString());
	}

	/**
	 * 修改管理员初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateAdminInit() throws Exception {
		Long id = request.getLong("id", -1);
		paramMap = adminService.queryAdminById(id);
		String key = Encrypt.encryptSES(id+","+new Date().getTime()+"",IConstants.BBS_SES_KEY);
		String md5Id = Encrypt.MD5(key+IConstants.BBS_SES_KEY).substring(0,10)+key;
		paramMap.put("key",md5Id);
		return SUCCESS;
	}

	/**
	 * 修改管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateAdmin() throws Exception {
		JSONObject obj = new JSONObject();
		String key = paramMap.get("key");
		Long id = -1l;
		String mdKey = key.substring(0,10);
		String mdValue = key.substring(10,key.length());
		String mdCompare = Encrypt.MD5(mdValue+IConstants.BBS_SES_KEY).substring(0,10);
		String valAll = Encrypt.decryptSES(mdValue, IConstants.BBS_SES_KEY);
		if(!mdKey.equals(mdCompare)){
//		JSONUtils.printStr("签名错误");
//		return null;
			this.addFieldError("paramMap.allError", "签名错误");
	        return INPUT; 
		}
		
		String[] keys = valAll.split(",");
		String md5 = keys[0].toString();
		id = Convert.strToLong(md5, -1);
		String dateTime = keys[1].toString();
		long curTime = new Date().getTime();
		// 当用户点击时间大于于1分钟
		if (curTime - Long.valueOf(dateTime) >= 60 * 1000) {
//		obj.put("mailAddress", "已超时");
//		JSONUtils.printObject(obj);
//		return null;
			this.addFieldError("paramMap.allError", "已超时");
	        return INPUT; 
		}
		String password = paramMap.get("password");
		String realName = paramMap.get("realName");
		String telphone = paramMap.get("telphone");
		String qq = paramMap.get("qq");
		String email = paramMap.get("email");
		String img = paramMap.get("img");
		String isLeader = paramMap.get("isLeader");
		Integer enable = Integer.parseInt(paramMap.get("enable"));
		long roleId = Convert.strToLong(paramMap.get("roleId"), -1);
		try {
			if(roleId > 0){
				adminService.updateAdmin(id, password, enable, null, roleId,
				realName, telphone, qq, email, img, isLeader);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_admin", admin.getUserName(),
				IConstants.UPDATE, admin.getLastIP(), 0, "修改管理员信息", 2);
		return SUCCESS;
		
	}

	/**
	 * 删除管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteAdmin() throws Exception {
		String adminIds = request.getString("id");

		String[] adminids = adminIds.split(",");
		int length = adminids.length;
		if (length <= 0) {
			return SUCCESS;
		}
		long[] teacherid = new long[length];
		for (int i = 0; i < adminids.length; i++) {
			teacherid[i] = Convert.strToLong(adminids[i], -1);
			if (teacherid[i] == -1) {
				return SUCCESS;
			}
		}
		try {
			adminService.deleteAdmin(adminIds);
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_admin", admin.getUserName(),
					IConstants.DELETE, admin.getLastIP(), 0, "删除id为" + adminIds
							+ "的管理员", 2);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	
	/**
	 * 登陆前先获取设置U盾的状态值
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws Exception
	 * @throws AdminHelpMessageException
	 */
	public String UtoolLogin() throws Exception{
		Map<String, String> uMaps = null;
		uMaps=adminService.queryUTool();
		request().setAttribute("uMaps", uMaps);
		
		return SUCCESS;
	}
	
	/**
	 * 登陆前先读取U盾传过来的信息
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws Exception
	 * @throws AdminHelpMessageException
	 */
	public String checkUkey() throws Exception {
		PrintWriter pw =  response().getWriter();
		String error = com.shove.security.Encrypt.encrypt3DES( "false", IConstants.PASS_KEY);
		String adminId = null;
		//获取从控件传过来的值
		String userName = request.getString("userName");
		String password = request.getString("password");
		String sign = request.getString("sign");
		String time =Long.toString(getHours());
		String all = userName + password + time + IConstants.PASS_KEY;
		
		System.out.println("time="+time);
		
		//把穿过来的userName，password都3DES解密
		String userName2 = com.shove.security.Encrypt.decrypt3DES(userName, IConstants.PASS_KEY);
        String password2 = com.shove.security.Encrypt.decrypt3DES(password, IConstants.PASS_KEY);
        String MD5pass = com.shove.security.Encrypt.MD5(password2+IConstants.PASS_KEY);

		//把传过来的userName，password，time 用MD5加密验证签名
		String sign2 = com.shove.security.Encrypt.MD5(all.trim());
		
		if(!sign.equalsIgnoreCase(sign2))
		{
			pw.print(error);
		}else
		   {
			try {
				Map<String, String> map = adminService.queryAdminByInfo(userName2,MD5pass);
				if(map == null)
				{
					pw.print(error);
				}else
				{
					adminId = com.shove.security.Encrypt.encrypt3DES( map.get("id"), IConstants.PASS_KEY);//3DES加密用于传到控件
					
					pw.print(adminId);
				}
			    }catch(SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			    }catch(DataException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
		   }
		  }

		return null;
	}
	
	
	/**
	 * 登陆
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws Exception
	 * @throws AdminHelpMessageException
	 */
	public String adminLogin() throws Exception {
		Map<String, String> uMaps = null;
		uMaps=adminService.queryUTool();
		request().setAttribute("uMaps", uMaps);
		Map<String, String> uMap = null;
		uMap=adminService.queryUTool();
		String adminId = request.getString("adminId");
		String time =Long.toString(getHours()); 
		String pageId = request.getString("pageId");
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code").toString().trim();
		//图形验证码不区分大小写
		if (code == null || !_code.toLowerCase().trim().equals(code.toLowerCase())) {
			this.addFieldError("paramMap.code", "验证码错误！");
			return INPUT;
		}
		String userName = paramMap.get("userName").toString().trim();
		String password = paramMap.get("password").toString().trim();
		Admin admin = null;
		try {
			Thread.sleep(500);
			Map<String, String> map = adminService.queryAdminByName(userName);
			if (map == null) {
				this.addFieldError("paramMap.userName", "用户名或密码错误");
				return INPUT;
			}
			if(map != null && map.get("id").equals("-1")){
				List<Map<String, Object>> userList = userService.queryUserAll();
				long adminnum = adminService.queryAllAdmin();
				Map<String, String> amap = adminService.queryAdmin();
				long isFirstLogin = Convert.strToLong(amap.get("isFirstLogin"), -1);
				if(userList.size()==0&&adminnum==1&&isFirstLogin==1){
					return "setPass";
				}
			}
			String adminId2 =com.shove.security.Encrypt.MD5(map.get("id")+time);
			if(null != uMap.get("isOpen") && uMap.get("isOpen").equals("1") )
			{
				if(adminId == null || !adminId.equals(adminId2))
				{
					this.addFieldError("paramMap.userName", "用户名或密码错误");
					return INPUT;
				}
			}
			admin = adminService.adminLogin(userName, password, ServletUtils
					.getRemortIp());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		if (admin == null) {
			this.addFieldError("paramMap.userName", "用户名或密码错误");
			return INPUT;
		}
		if(admin.getIsLoginLimit()==2){
			this.addFieldError("paramMap.password", "你的帐号被暂时冻结，请于三小时以后登录！");
			return INPUT;
		}
		if (admin.getEnable() != 1) {
			this.addFieldError("paramMap.password", "你的帐号被停用请联系站点管理员");
			return INPUT;
		}
		long roleId = admin.getRoleId();

		// 后台登录初始页面
		// --审核管理
		Map<String, String> map = adminService.queryCheckCount(admin.getId());
		session().setAttribute("map", map);
		// 天添加后台操作日志
		operationLogService.addOperationLog("t_admin", admin.getUserName(),
				IConstants.UPDATE, admin.getLastIP(), 0, "后台管理员登陆", 2);

		List<Map<String, Object>> list = roleRightsService
				.queryAdminRoleRightMenu(roleId);
		session().setAttribute("index", -1);
		session().setAttribute("adminRoleMenuList", list);
		session().setAttribute(IConstants.SESSION_ADMIN, admin);
		
		return SUCCESS;
		}
	
	
	public String adminSetPass() throws Exception {
		String pageId = request.getString("pageId");
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code").toString().trim();
		if (code == null || !_code.equals(code)) {
			this.addFieldError("paramMap.code", "验证码错误！");
			return INPUT;
		}
		String password = paramMap.get("password").toString().trim();
		String confirmPass = paramMap.get("confirmPass").toString().trim();
		
		if (StringUtils.isBlank(password)) {
			this.addFieldError("paramMap.password", "登录密码不能为空");
			return INPUT;
		}
		
		if (StringUtils.isBlank(confirmPass)) {
			this.addFieldError("paramMap.confirmPass", "确认密码不能为空");
			return INPUT;
		}
		
		if (!password.equals(confirmPass)) {
			this.addFieldError("paramMap.password", "两次输入密码不一致");
			return INPUT;
		}
		try {
			adminService.updateAdminPass(password);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		// 添加后台操作日志
		operationLogService.addOperationLog("t_admin", "超级管理员",
				IConstants.UPDATE, ServletUtils.getRemortIp(), 0, "后台管理员初次登录修改密码", 2);

		return SUCCESS;
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String adminLoginOut() throws Exception {
		Map<String, String> uMaps = null;
		uMaps=adminService.queryUTool();
		request().setAttribute("uMaps", uMaps);
		session().removeAttribute(IConstants.SESSION_ADMIN);
		return SUCCESS;
	}

	/**
	 * 修改密码初始化
	 * 
	 * @return
	 */
	public String updatePasswordInit() {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		paramMap = BeanMapUtils.beanToMap(admin);
		paramMap.put("password", "");
		paramMap.put("oldPassword", "");
		
		return SUCCESS;
	}

	/**
	 * 同步用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String syncBBSUser() throws Exception {
		try {
			List<Map<String, Object>> list = userService.queryUserAll();
			if (list != null) {
				String strURL = IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL
						+ "otherweb.do?action=memberInitAdd"
						: IConstants.BBS_URL
								+ "/otherweb.do?action=memberInitAdd";
				URL url = new URL(strURL);
				for (Map<String, Object> map : list) {

					String parameters = "groupid=10&regsubmit=yes&alipay=&answer=&bday=0000-00-00&bio=&dateformat=0&email="
							+ URLEncoder.encode(map.get("email") + "", "UTF-8")
							+ "&formHash=6a36c78f&gender=0&icq=&location=&msn=&newsletter=1&password="
							+ URLEncoder.encode(map.get("password") + "",
									"UTF-8")
							+ "&password2="
							+ URLEncoder.encode(map.get("password") + "",
									"UTF-8")
							+ "&pmsound=1&ppp=0&qq=&questionid=0&showemail=1&signature=&site=&styleid=0&taobao=&timeformat=0&timeoffset=9999&tpp=0&username="
							+ URLEncoder.encode(map.get("userName") + "",
									"UTF-8")
							+ "&yahoo=&k="
							+ Encrypt.encryptSES(IConstants.BBS_KEY,
									IConstants.BBS_SES_KEY);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
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
				}

			}
		} catch (DataException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		OutputStream output = this.response().getOutputStream();
		PrintWriter pw = new PrintWriter(output);
		pw.write("同步成功！");
		pw.flush();
		pw.close();
		output.close();
		return null;
	}

	/**
	 * 修改当前用户密码
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updatePassword() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String oldPassword = paramMap.get("oldPassword").trim();
		String password = paramMap.get("password").trim();
		if ("1".equals(IConstants.ENABLED_PASS)) {
			oldPassword = Encrypt.MD5(oldPassword.trim());
		} else {
			oldPassword = Encrypt.MD5(oldPassword.trim() + IConstants.PASS_KEY);
		}

		String confirmPassword = paramMap.get("confirmPassword").trim();
		if (!admin.getPassword().equals(oldPassword)) {
			this.addFieldError("paramMap.oldPassword", "旧密码输入错误");
			return INPUT;
		} else if (!password.equals(confirmPassword)) {
			this.addFieldError("paraMap.oldPassword", "确认密码与新密码不一致");
			return INPUT;
		} else {
			try {
				long result = adminService.updateAdmin(admin.getId(), password, null, null,
						null, null, null, null, null, null, null);
				
				if(result > 0){
					// 后台操作日志
					operationLogService.addOperationLog("t_admin", admin
							.getUserName(), IConstants.UPDATE, admin.getLastIP(),
							0, "管理员修改密码", 2);
					
					getOut()
					.print(
							"<script>alert('修改成功! ');window.location.href='updatePasswordInit.do';</script>");
						return null;
				}
				
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}
		}

		return SUCCESS;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public List<Map<String, Object>> getRoleList() throws Exception {
		if (roleList != null) {
			return roleList;
		}
		roleList = roleService.queryRoleList();
		return roleList;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setRoleRightsService(RoleRightsService roleRightsService) {
		this.roleRightsService = roleRightsService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public RoleRightsService getRoleRightsService() {
		return roleRightsService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setRoleList(List<Map<String, Object>> roleList) {
		this.roleList = roleList;
	}

}
