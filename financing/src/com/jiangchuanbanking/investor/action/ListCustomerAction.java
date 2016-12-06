package com.jiangchuanbanking.investor.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;

import com.jiangchuanbanking.investor.domain.Customer;

import com.jiangchuanbanking.select.service.ISelectService;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;
import com.opensymphony.xwork2.ActionContext;

public class ListCustomerAction extends BaseListAction{
	
	private static final long serialVersionUID = 1L;
	private String delFlg;//1.停用0启用
	private ISelectService selectService;
	private IBaseService<Customer> baseService;
	private String id_no;
	private static final String CLAZZ = Customer.class .getSimpleName();
	@Override
	public String list() throws Exception {
		 //	SearchCondition searchCondition = getSearchCondition();
		 	SearchCondition searchCondition = getSearchCondition("cif_name",UserUtil.getLoginUser(),"OPEN_ID","");
	   
		 	SearchResult<Customer> result = baseService.getPaginationObjects(CLAZZ,
	                searchCondition);
   
		 	Iterator<Customer> customers = result.getResult().iterator();
	        long totalRecords = result.getTotalRecords();
	        getListJson(customers, totalRecords, null, false);
	        return null;
	}
	
	public String listFull() throws Exception{
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","");
//		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
//				loginUser.getScope_system(), loginUser);

		SearchResult<Customer> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<Customer> custs = result.getResult().iterator();
		
		long totalRecords = result.getTotalRecords();
		getListJson(custs, totalRecords, searchCondition, true);	
		return null;
	}
	public String chactIdNo() throws Exception{
		String data = "Yes";
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
		
		User loginUser = UserUtil.getLoginUser();
		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				 loginUser,"OPEN_ID","");
		SearchResult<Customer> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		Iterator<Customer> custs = result.getResult().iterator();
		while(custs.hasNext()){
			Customer instance = custs.next();
			String idNo = CommonUtil.fromNullToEmpty(instance
					.getId_no());
			if(idNo.equals(id_no)){
				data = "No";
			continue;
			}
		}
		String jsonData = "[{\"data\":\"" + data + "\"}]";
		String json = JSONArray.fromObject(jsonData).toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		return null;
	}	
	private void getListJson(Iterator<Customer> custs, long totalRecords,
			SearchCondition searchCondition, boolean isList) throws Exception {
		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		while (custs.hasNext()) {
			Customer instance = custs.next();
			
			Integer id = instance.getId();
			String cif_no = CommonUtil
					.fromNullToEmpty(instance.getCif_no());
			String cif_name = CommonUtil.fromNullToEmpty(instance
					.getCif_name());
			String sex = CommonUtil.fromNullToEmpty(instance
					.getSex());
			sex=selectService.getOpCnName("sex", sex);
			String birth = CommonUtil.fromNullToEmpty(instance
					.getBirth());
			String cif_type = CommonUtil.fromNullToEmpty(instance
					.getCif_type());
			cif_type  =  selectService.getOpCnName("CUS_TYPE", cif_type);
		
			String id_type = CommonUtil.fromNullToEmpty(instance
					.getId_type());
			id_type	  =	  selectService.getOpCnName("ID_TYPE", id_type);
			
			String id_no = CommonUtil.fromNullToEmpty(instance
					.getId_no());
			String contact = CommonUtil.fromNullToEmpty(instance
					.getContact());
			String contact_tel = CommonUtil.fromNullToEmpty(instance
					.getContact_tel());
			String contact_phone = CommonUtil.fromNullToEmpty(instance
					.getContact_phone());
			String mail = CommonUtil.fromNullToEmpty(instance
					.getMail());
			/*String other_contact = CommonUtil.fromNullToEmpty(instance
					.getOther_contact());*/
			String addr = CommonUtil.fromNullToEmpty(instance
					.getAddr());
			String postcode = CommonUtil.fromNullToEmpty(instance
					.getPostcode());
			String open_date = CommonUtil.fromNullToEmpty(instance
					.getOpen_date());
			String open_op = CommonUtil.fromNullToEmpty(instance
					.getOpen_op());
			String cmt = CommonUtil.fromNullToEmpty(instance
					.getCmt());
			
			if (isList) {

				jsonBuilder
				.append("{\"cell\":[\"").append(id)
		/*	.append("\",\"").append(cif_no)*/
				.append("\",\"").append(cif_name)
				.append("\",\"").append(sex)
				.append("\",\"").append(birth)
				.append("\",\"").append(cif_type)
				.append("\",\"").append(id_type)
				.append("\",\"").append(id_no)
				.append("\",\"").append(contact)
				.append("\",\"").append(contact_tel)
				.append("\",\"").append(contact_phone)
				.append("\",\"").append(mail)
				/*.append("\",\"").append(other_contact)*/
				.append("\",\"").append(addr)
				.append("\",\"").append(postcode)
				.append("\",\"").append(open_date)
				.append("\",\"").append(open_op)
				.append("\",\"").append(cmt)		
				.append("\"]}");

			} else {
				jsonBuilder.append("{\"id\":\"").append(id)
						.append("\",\"cif_name\":\"").append(cif_name)
						.append("\",\"sex\":\"").append(sex)
						.append("\",\"birth\":\"").append(birth)
						.append("\",\"cif_type\":\"").append(cif_type)
						.append("\",\"id_type\":\"").append(id_type)
						.append("\",\"id_no\":\"").append(id_no)
						.append("\",\"contact\":\"").append(contact)
						.append("\",\"contact_tel\":\"").append(contact_tel)
						.append("\",\"contact_phone\":\"").append(contact_phone)
						.append("\",\"mail\":\"").append(mail)
						/*.append("\",\"other_contact\":\"").append(other_contact)*/
						.append("\",\"addr\":\"").append(addr)
						.append("\",\"postcode\":\"").append(postcode)
						.append("\",\"open_date\":\"").append(open_date)
						.append("\",\"open_op\":\"").append(open_op)
						.append("\",\"cmt\":\"").append(cmt)
						.append("\"}");
			}
			if (custs.hasNext()) {
				jsonBuilder.append(",");
			}
		}
		jsonBuilder.append("]}");

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
       
		response.getWriter().write(jsonBuilder.toString());
	}

	/**
	 * delete customer information
	 * @return
	 * @throws Exception
	 */
	 public String delete() throws Exception {
	        UserUtil.permissionCheck("delete_system");
	        baseService.batchDeleteEntity(Customer.class, this.getSeleteIDs());
	        return SUCCESS;
	    }
	 
	 /**
	     * Copies the selected entities
	     * 
	     * @return the SUCCESS result
	     */
	    public String copy() throws Exception {
	        UserUtil.permissionCheck("create_system");
	        if (this.getSeleteIDs() != null) {
	            String[] ids = seleteIDs.split(",");
	            for (int i = 0; i < ids.length; i++) {
	                String copyid = ids[i];
	                Customer oriRecord = baseService.getEntityById(Customer.class,
	                        Integer.valueOf(copyid));
	                Customer targetRecord = oriRecord.clone();
	                targetRecord.setId(null);
	                this.baseService.makePersistent(targetRecord);
	            }
	        }
	        return SUCCESS;
	    }
	    /**
	     * Exports the entities
	     * 
	     * @return the exported entities inputStream
	     */
	    public InputStream getInputStream() throws Exception {
	        return getDownloadContent(false);
	    }
	    private InputStream getDownloadContent(boolean isTemplate) throws Exception {
	        UserUtil.permissionCheck("view_system");
	        String fileName = getText("customer.title") + ".csv";
	        fileName = new String(fileName.getBytes(), "ISO8859-1");
	        File file = new File(fileName);
	        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
	                CsvPreference.EXCEL_PREFERENCE);
	        try {
	            final String[] header = new String[] { 
	            	
	            		getText("customer.id.label"),
	            		getText("customer.cifNo.label"),
	            		getText("customer.cifName.label"),
	                    getText("customer.sex.label"),
	                    getText("customer.birth.label"),
	                    getText("customer.cifType.label"),
	                    getText("customer.idType.label"),
	                    getText("customer.idNo.label"),
	                    getText("customer.contact.label"),                    
	                    getText("customer.contactTel.label"),
	                    getText("customer.contactPhone.label"),
	                    getText("customer.mail.label"),                    
	                    getText("customer.otherContact.label"),
	                    getText("customer.address.label"),
	                    getText("customer.postcode.label"),                    
	                    getText("customer.openDate.label"),
	                    getText("customer.openOp.label"),  
	                    getText("customer.cmt.label") 
	                    
	                   };
	            writer.writeHeader(header);
	            if (!isTemplate) {
	                String[] ids = seleteIDs.split(",");
	                for (int i = 0; i < ids.length; i++) {
	                    String id = ids[i];
	                    Customer cust = baseService.getEntityById(Customer.class,Integer.parseInt(id));
	                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
	                    data1.put(header[0], cust.getId());                  
	                    data1.put(header[1], CommonUtil.fromNullToEmpty(cust.getCif_no()));
	                    data1.put(header[2], CommonUtil.fromNullToEmpty(cust.getCif_name()));
	                    data1.put(header[3], CommonUtil.fromNullToEmpty(cust.getSex()));
	                    data1.put(header[4], CommonUtil.fromNullToEmpty(cust.getBirth()));
	                    data1.put(header[5], CommonUtil.fromNullToEmpty(cust.getCif_type()));
	                    data1.put(header[6], CommonUtil.fromNullToEmpty(cust.getId_type()));
	                    data1.put(header[7], CommonUtil.fromNullToEmpty(cust.getId_no()));
	                    data1.put(header[8], CommonUtil.fromNullToEmpty(cust.getContact()));
	                    data1.put(header[9], CommonUtil.fromNullToEmpty(cust.getContact_tel()));
	                    data1.put(header[10], CommonUtil.fromNullToEmpty(cust.getContact_phone()));
	                    data1.put(header[11], CommonUtil.fromNullToEmpty(cust.getMail()));
	                    data1.put(header[12], CommonUtil.fromNullToEmpty(cust.getOther_contact()));
	                    data1.put(header[13], CommonUtil.fromNullToEmpty(cust.getAddr()));
	                    data1.put(header[14], CommonUtil.fromNullToEmpty(cust.getPostcode()));
	                    data1.put(header[15], CommonUtil.fromNullToEmpty(cust.getOpen_date()));
	                    data1.put(header[16], CommonUtil.fromNullToEmpty(cust.getOpen_op()));
	                    data1.put(header[17], CommonUtil.fromNullToEmpty(cust.getCmt()));
	                   
	                    writer.write(data1, header);
   
	                }
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            writer.close();
	        }

	        InputStream in = new FileInputStream(file);
	        this.setFileName(fileName);
	        return in;
	    }

	public IBaseService<Customer> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<Customer> baseService) {
		this.baseService = baseService;
	}
	public ISelectService getSelectService() {
		return selectService;
	}

	public void setSelectService(ISelectService selectService) {
		this.selectService = selectService;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}
}
