package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.ExcelUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.UnactivatedService;
/**
 * 后台管理用户 - 未激活用户
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class UnactivatedAction extends BaseFrontAction{
	public static Log log = LogFactory.getLog(UnactivatedAction.class);
	private UnactivatedService unactivatedService;
	private UserService  userService;
	
	/**
	 * 查询未激活用户
	 * @return
	 */
	public String unactivatedindex(){
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String unactivatedinfo() throws SQLException, Exception{
		String userName = paramMap.get("userName");
		String createtimeStart = paramMap.get("createtimeStart");
		String createtimeEnd = paramMap.get("createtimeEnd");
		String email = paramMap.get("email");
		unactivatedService.queryUserUnactivated(pageBean,userName,createtimeStart,createtimeEnd,email);
		List<Map<String,Object>> list = pageBean.getPage();
		if(list!=null){
			Iterator<Map<String, Object>> iter = list.iterator();
			int i = 0;
			while(iter.hasNext()){
				Map<String, Object> map = iter.next();
				list.get(i).put("ids", new DesSecurityUtil().encrypt(map.get("id").toString()));
				i++;
			}
			pageBean.setPage(list);
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}
	/**
	 * 激活账户
	 * @return
	 * @throws Exception 
	 */
	public String updateUserActivate() throws Exception{
		long userId = Convert.strToLong(new DesSecurityUtil().decrypt(request("userId")), -1L);
		Map<String,String> userMap = new  HashMap<String, String>();
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			long result = userService.updateUserActivate(userId);
			if(result > 0 ){
				userMap = userService.queryUserById(userId);
				operationLogService.addOperationLog("t_user", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "管理员激活用户:"+Convert.strToStr(userMap.get("username"), ""), 2);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} 
		return SUCCESS;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public UnactivatedService getUnactivatedService() {
		return unactivatedService;
	}

	public void setUnactivatedService(UnactivatedService unactivatedService) {
		this.unactivatedService = unactivatedService;
	}
	
	/**
	 * 导出用户积分信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportuserenable() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			//用户名
			String userName = request.getString("userName")==null? "" : request.getString("userName");
			userName = URLDecoder.decode(userName,"UTF-8");
			//会员email
			String email=request.getString("email")==null? "" : request.getString("email");
			email = URLDecoder.decode(email,"UTF-8");
			//用户积分排序
			String createtimeStart = request.getString("createtimeStart") == null ? ""
					: request.getString("createtimeStart");
			String createtimeEnd = request.getString("createtimeEnd") == null ? ""
					: request.getString("createtimeEnd");
			
			createtimeStart = URLDecoder.decode(createtimeStart,"UTF-8");
			createtimeEnd = URLDecoder.decode(createtimeEnd,"UTF-8");
			
			// 待还款详情
			unactivatedService.queryUserUnactivated(pageBean,userName,createtimeStart,createtimeEnd,email);
			 if(pageBean.getPage()==null)
				{
					getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
					return  null;
				}
				if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
				{
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return  null;
				}
		
			
			HSSFWorkbook wb = ExcelUtils.exportExcel("未激活用户", pageBean
					.getPage(), new String[] { "用户名", "邮箱", "创建时间"}, new String[] { "username",
					"email", "createTime"
					});
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user", admin.getUserName(),IConstants.EXCEL, admin.getLastIP(), 0, "导出未激活用户列表", 2);
			
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
