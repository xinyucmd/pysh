package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.CacheManager;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.admin.BorrowfaService;

public class BorrowfabiaoAction extends BaseFrontAction {
	/**
	 * 企业融资申请表
	 */
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(BorrowfabiaoAction.class);
	private BorrowfaService BorrowfaService;
	protected  OperationLogService operationLogService;	
	private Map<String,String> map;
	public OperationLogService getOperationLogService() {
		return operationLogService;
	}
	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}
	public BorrowfaService getBorrowfaService() {
		return BorrowfaService;
	}
	public void setBorrowfaService(BorrowfaService borrowfaService) {
		BorrowfaService = borrowfaService;
	}
	/**
	 * 跳转到企业融资申请用户初始化
	 * @return
	 * @throws Exception 
	 */
	public String  borrowfabiao() throws Exception{
		String state = paramMap.get("state");
		paramMap.put("state", state);
		return SUCCESS;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	/**
	 *  跳转到企业融资申请用户详细信息
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String queryborrowfabiao() throws Exception{
		int state = Convert.strToInt(paramMap.get("state"), -1) ;
		String tname = paramMap.get("tname");
		String telphone = paramMap.get("telphone");
		BorrowfaService.queryborrowfabiao(pageBean,tname,telphone,state);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	/**
	 * 企业融资申请用户初始化
	 * @return
	 */
	public String  queryUserManageInfoIndex(){
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryborrowfabiaoInfo() throws Exception{
		
		String tname = paramMap.get("tname");
		String telphone = paramMap.get("telphone");
		BorrowfaService.queryborrowfabiaoInfo(pageBean,tname,telphone);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	//企业融资申请用户中 查看个人信息
	@SuppressWarnings("unchecked")
	public String queryfabiaoInfo() throws Exception {
		long  id = request.getLong("id",-1);	
		map = BorrowfaService.queryfabiaoInfo(id); 
		request().setAttribute("map",map);
		paramMap.put("state", map.get("state"));
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String updatestate() throws Exception{
		JSONObject obj = new JSONObject();
		String state = paramMap.get("state");
		long id=Convert.strToInt(paramMap.get("id"), -1);
		long result = -1L;
		try {
		result = BorrowfaService.updateApplystate(id,state);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			if(result>=1){
				operationLogService.addOperationLog("t_apply", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新用户基本信息（QQ）", 2);
			obj.put("msg","1");
			JSONUtils.printObject(obj);
			return null;
			}else{
				obj.put("msg","操作失败");
				JSONUtils.printObject(obj);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 导出企业融资申请用户信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportinborrowfa() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		try {
			//姓名
			String tname = paramMap.get("tname");
			String telphone = paramMap.get("telphone");
			BorrowfaService.queryborrowfabiaoInfo(pageBean,tname,telphone);
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
			HSSFWorkbook wb = ExcelUtils.exportExcel("申请列表", pageBean
					.getPage(), new String[] { "企业名称", "注册号", "联系人", "联系电话",
					"城市所在地","借款金额","借款期限","状态"}, new String[] { "companyname",
					"registnumber", "tname", "telephone", "cityaddress","borrowAmount","deadline","state"
					});
			this.export(wb,new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_apply", admin.getUserName(),IConstants.EXCEL, admin.getLastIP(), 0, "导出用户列表基本信息", 2);
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
	/**
	 * 查询未企业融资申请用户信息初始化
	 * @return
	 */
	public String queryborrowfabiaohu(){
		return SUCCESS;
	}
	
	/**
	 * 查询未企业融资申请用户信息初始化
	 * @return
	 */
	public String queryfabiaohuInfo(){
		String tname = paramMap.get("tname");
		String telphone = paramMap.get("telphone");
		try {
			BorrowfaService.queryborrowfabiaoInfo(pageBean,tname,telphone);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} 
		return SUCCESS;
	}
		/**
		 * 删除企业融资申请表记录
		 * @return
		 * @throws SQLException
		 * @throws DataException
		 */
		public String deleteBorrowfaId() throws Exception{
			long id=Convert.strToInt(paramMap.get("id"), -1);
			BorrowfaService.deleteBorrowfaId(id);
			return SUCCESS;
	}
		
		/**
		 * 删除企业融资申请表记录
		 * 
		 * @return
		 * @throws Exception
		 */
		public String deleteBorrowfabiao() throws Exception {
			
			String ids = request.getString("id");
			String[] id = ids.split(",");
			int length = id.length;
			if (length <= 0) {
				return SUCCESS;
			}
			long[] teacherid = new long[length];
			for (int i = 0; i < id.length; i++) {
				teacherid[i] = Convert.strToLong(id[i], -1);
				if (teacherid[i] == -1) {
					return SUCCESS;
				}
			}
			try {
				BorrowfaService.deleteBorrowfabiao(ids);
				Admin admin = (Admin) session().getAttribute(
						IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_apply", admin.getUserName(),
						IConstants.DELETE, admin.getLastIP(), 0, "删除id为" + ids
								+ "的企业融资申请表记录", 2);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}
			return SUCCESS;
		}

		
}
