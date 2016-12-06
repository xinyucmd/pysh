package com.jiangchuanbanking.dict.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.struts2.ServletActionContext;
import org.hibernate.SQLQuery;

import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.dao.IBaseDao;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;

import com.jiangchuanbanking.dict.service.impl.OptionService;
import com.jiangchuanbanking.select.domain.WealthParnDic;
import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

public class DictionaryAction extends BaseListAction
implements Preparable {
	
	private static final long serialVersionUID = -2404576552417042445L;
	private WealthParnDic entity;
	private IBaseService<WealthParnDic> baseService;
	private ISelectService selectService;
	private String parentId;
	private String opCode;
    
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void prepare() throws Exception {
		Map request = (Map) ActionContext.getContext().get("request");
	    String entityName = getEntityName();
	    request.put("entityName", entityName);
	    String title = getText("title.grid."
	                + CommonUtil.lowerCaseString(entityName));
	    request.put("title", title);
		
	}

	@Override
	public String list() throws Exception {
        UserUtil.permissionCheck("view_system");
        SearchCondition searchCondition = getSearchCondition();
        
        if (searchCondition.getCondition()==null||"".endsWith(searchCondition.getCondition())) {
			searchCondition.setCondition("PARENTID ='0'");
		}else{
			searchCondition.setCondition(searchCondition.getCondition()+"and PARENTID ='0'");
		}
        
        SearchResult<WealthParnDic> result = getBaseService().getPaginationObjects(
                getEntityClass().getSimpleName(), searchCondition);
        List<WealthParnDic> optons = result.getResult();

        long totalRecords = result.getTotalRecords();

        // Constructs the JSON data
        String json = "{\"total\": " + totalRecords + ",\"rows\": [";
        int size = optons.size();
        for (int i = 0; i < size; i++) {
        	WealthParnDic instance = optons.get(i);
            Integer id = instance.getId();
            String keyCode = instance.getKeyCode();
            String KeyName = instance.getKeyName();
            String opCode = instance.getOpCode();
            String opCnName = instance.getOpCnName();
            String opEnName = instance.getOpEnName();
            String parentId = instance.getParentId();
            
            json += "{\"entity.id\":\"" + id + "\",\"id\":\"" + id
                    + "\",\"keyCode\":\"" + keyCode
                    + "\",\"KeyName\":\"" + KeyName
                    + "\",\"opCode\":\"" + opCode
                    + "\",\"entity.opCnName\":\""
                    + CommonUtil.fromNullToEmpty(opCnName)
                    + "\",\"entity.opEnName\":\""
                    + CommonUtil.fromNullToEmpty(opEnName)
                    + "\",\"parentId\":\"" + parentId + "\"}";
            if (i < size - 1) {
                json += ",";
            }
        }
        json += "]}";

        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
        return null;
    }
	
	
	public String listSubDict() throws Exception {
        UserUtil.permissionCheck("view_system");
        SearchCondition searchCondition = getSearchCondition();
        
        if (searchCondition.getCondition()==null||"".endsWith(searchCondition.getCondition())) {
			searchCondition.setCondition("PARENTID ='"+parentId+"'");
		}else{
			searchCondition.setCondition(searchCondition.getCondition()+"and PARENTID ='"+parentId+"'");
		}
        
        SearchResult<WealthParnDic> result = getBaseService().getPaginationObjects(
                getEntityClass().getSimpleName(), searchCondition);
        List<WealthParnDic> optons = result.getResult();

        long totalRecords = result.getTotalRecords();

        // Constructs the JSON data
        String json = "{\"total\": " + totalRecords + ",\"rows\": [";
        int size = optons.size();
        for (int i = 0; i < size; i++) {
        	WealthParnDic instance = optons.get(i);
            Integer id = instance.getId();
            String keyCode = instance.getKeyCode();
            String KeyName = instance.getKeyName();
            String opCode = instance.getOpCode();
            String opCnName = instance.getOpCnName();
            String opEnName = instance.getOpEnName();
            String parentId = instance.getParentId();
            
            json += "{\"entity.id\":\"" + id + "\",\"id\":\"" + id
                    + "\",\"keyCode\":\"" + keyCode
                    + "\",\"KeyName\":\"" + KeyName
                    + "\",\"entity.opCode\":\"" + opCode
                    + "\",\"entity.opCnName\":\""
                    + CommonUtil.fromNullToEmpty(opCnName)
                    + "\",\"entity.opEnName\":\""
                    + CommonUtil.fromNullToEmpty(opEnName)
                    + "\",\"parentId\":\"" + parentId + "\"}";
            if (i < size - 1) {
                json += ",";
            }
        }
        json += "]}";

        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
        return null;
    }

	@Override
	protected String getEntityName() {
		return getEntityClass().getSimpleName();
	}
	
	/**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
    	WealthParnDic optionBase = this.getEntity();
        if (optionBase.getId() == null) {
            UserUtil.permissionCheck("create_system");
        } else {
            UserUtil.permissionCheck("update_system");
        }
        WealthParnDic instance = this.getEntityClass().newInstance();
        
        
        if (optionBase.getId() == null){
        	instance.setOpCode(selectService.getSubParentId());
        }else{
        	instance.setId(optionBase.getId());
        	instance.setOpCode(opCode);
        }
        instance.setOpCnName(optionBase.getOpCnName());
        instance.setOpEnName(optionBase.getOpEnName());
        instance.setParentId("0");
        
        getBaseService().makePersistent(instance);
        OptionService.optionMap.remove(this.getEntityClass().getSimpleName());
        return SUCCESS;
    }
    
    public String saveSubDict() throws Exception {
    	WealthParnDic optionBase = this.getEntity();
        if (optionBase.getId() == null) {
            UserUtil.permissionCheck("create_system");
        } else {
            UserUtil.permissionCheck("update_system");
        }
        WealthParnDic instance = this.getEntityClass().newInstance();
        //instance.setId(optionBase.getId());
        instance.setParentId(parentId);
        WealthParnDic wp=baseService.findByHQL("from WealthParnDic where PARENTID='0' and OP_CODE='"+parentId+"'").get(0);
        instance.setKeyCode(wp.getOpEnName());
        instance.setKeyName(wp.getOpCnName());
        instance.setOpCode(optionBase.getOpCode());
        instance.setOpCnName(optionBase.getOpCnName());
        instance.setOpEnName(optionBase.getOpEnName());
        instance.setParentId(parentId);
        
        getBaseService().makePersistent(instance);
        OptionService.optionMap.remove(this.getEntityClass().getSimpleName());
        return SUCCESS;
    }
    
    public String updateSubDict() throws Exception {
    	WealthParnDic optionBase = this.getEntity();
        if (optionBase.getId() == null) {
            UserUtil.permissionCheck("create_system");
        } else {
            UserUtil.permissionCheck("update_system");
        }
        WealthParnDic wp=baseService.getEntityById(WealthParnDic.class, optionBase.getId());
        wp.setOpCode(optionBase.getOpCode());
        wp.setOpCnName(optionBase.getOpCnName());
        wp.setOpEnName(optionBase.getOpEnName());
        wp.setParentId(parentId);        
        getBaseService().makePersistent(wp);
        return SUCCESS;
    }

    /**
     * Deletes the selected entity.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_system");
        getBaseService().batchDeleteEntity(getEntityClass(),
                this.getSeleteIDs());
      //  OptionService.optionMap.remove(this.getEntityClass().getSimpleName());
        return null;
    }
    
    public String deleteAll() throws Exception {
    	List<WealthParnDic> list= getBaseService().findByHQL("from WealthParnDic where id in ("+ this.getSeleteIDs()+")");
    	if (list!=null&&list.size()>0) {
    		for (WealthParnDic wealthParnDic : list) {
        		getBaseService().executeSQL("delete from WEALTH_PARM_DIC where PARENTID='"+wealthParnDic.getOpCode()+"'", null);
    		} 
		}
    		
        UserUtil.permissionCheck("delete_system");
        getBaseService().batchDeleteEntity(getEntityClass(),
                this.getSeleteIDs()); 
      //  OptionService.optionMap.remove(this.getEntityClass().getSimpleName());
        return SUCCESS;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String init() throws Exception {
        // Sets navigation history
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        ArrayList navigationList = (ArrayList) session
                .getAttribute(Constant.NAVIGATION_HISTORY);
        if (navigationList == null) {
            navigationList = new ArrayList();
        }
        String entityLabel = getText("menu."
                + CommonUtil.lowerCaseString(this.getEntityName()) + ".title");
        String navigatoin = "<a href='" + Constant.APP_PATH
                + Constant.SYSTEM_NAMESPACE + "list" + this.getEntityName()
                + "Page.action'>" + entityLabel + "</a>";
        if (navigationList.contains(navigatoin)) {
            navigationList.remove(navigatoin);
        }
        navigationList.add(navigatoin);
        if (navigationList.size() > Constant.NAVIGATION_HISTORY_COUNT) {
            navigationList.remove(0);
        }
        session.setAttribute(Constant.NAVIGATION_HISTORY, navigationList);

        return SUCCESS;
    }
  
    
    protected Class<WealthParnDic> getEntityClass() {
		return WealthParnDic.class;
	}

	public WealthParnDic getEntity() {
		return entity;
	}

	public void setEntity(WealthParnDic entity) {
		this.entity = entity;
	}

	public IBaseService getBaseService() {
		return baseService;
	}

	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public void setBaseService(IBaseService<WealthParnDic> baseService) {
		this.baseService = baseService;
	}

	public ISelectService getSelectService() {
		return selectService;
	}

	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	
	

	
    

}
