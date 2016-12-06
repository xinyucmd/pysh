package com.jiangchuanbanking.prod.action;





import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.prod.domain.ProdBaseEntity;
import com.jiangchuanbanking.system.security.UserUtil;


@SuppressWarnings("serial")
public class ListProAction extends BaseListAction{
	
	
	private IBaseService<ProdBaseEntity> baseService;
	private static final String CLAZZ = ProdBaseEntity.class.getSimpleName();
	public IBaseService<ProdBaseEntity> getBaseService() {
		return baseService;
	}
	public void setBaseService(IBaseService<ProdBaseEntity> baseService) {
		this.baseService = baseService;
	}
	@Override
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition("prdt_name",UserUtil.getLoginUser(),"","DEL_FLG='0'");
		
		SearchResult<ProdBaseEntity> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		
		
		
		Iterator<ProdBaseEntity> accounts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(accounts, totalRecords, null, false);
		return null;
	}
	
	
	public static void getListJson(Iterator<ProdBaseEntity> accounts,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (accounts.hasNext()) {
			ProdBaseEntity instance = accounts.next();
			
			
			//String cifNo;
			String prdtName=instance.getPrdtName();
			String prdtNo=instance.getPrdtNo();
			String standardAmt=instance.getStandardAmt();
			String advanceRedeem=instance.getAdvanceRedeem();
			String delFlg=instance.getDelFlg();//1.停用0启用
			String sts=instance.getSts();//5新建 8补充 10待审核 15审核通过 20审核否决 
			String startDate=instance.getStartDate();
			String endDate=instance.getEndDate();
			String createOp=instance.getCreateOp();
			String createDate=instance.getCreateDate();
			String cmt=instance.getCmt();
			
			;
			if (isList) {
				jsonBuilder.append("{\"cell\":[\"").append(prdtNo).append("\",\"")
						.append(prdtName).append("\",\"").append(standardAmt)
						.append("\",\"").append(advanceRedeem).append("\"")
				       .append(",\"").append(delFlg).append("\",\"")
						.append(sts).append("\",\"").append(startDate)
						.append("\",\"").append(endDate).append("\",\"")
						.append(createOp).append("\",\"")
						.append(createDate).append("\",\"")					
						.append(cmt).append("\"]}");
			} else {
				jsonBuilder.append("{\"prdtNo\":\"").append(prdtNo)
						.append("\",\"prdtName\":\"").append(prdtName)
						.append("\",\"standardAmt\":\"").append(standardAmt)
						.append("\",\"advanceRedeem\":\"").append(advanceRedeem)
						.append("\",\"delFlg\":\"").append(delFlg)
						.append("\",\"sts\":\"").append(sts)
						.append("\",\"startDate\":\"").append(startDate)
						.append("\",\"endDate\":\"").append(endDate)
						.append("\",\"createOp\":\"").append(createOp)
						.append("\",\"createDate\":\"").append(createDate)
						.append("\",\"cmt\":\"")
						.append(cmt).append("\"}");
			}
			if (accounts.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(jsonBuilder.toString());
	}
	
	
	
	
	
	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	

}
