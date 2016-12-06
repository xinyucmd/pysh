package com.jiangchuanbanking.system.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.jiangchuanbanking.base.action.BaseListAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.base.vo.SearchCondition;
import com.jiangchuanbanking.base.vo.SearchResult;
import com.jiangchuanbanking.system.domain.ProductConfiguration;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.security.UserUtil;
import com.jiangchuanbanking.util.CommonUtil;
import com.jiangchuanbanking.util.Constant;

public class ListProductConfigurationAction extends BaseListAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IBaseService<ProductConfiguration> baseService;

	private static final String CLAZZ = ProductConfiguration.class
			.getSimpleName();

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the list data.
	 * 
	 * @return null
	 */
	public String listFull() throws Exception {

//		UserUtil.permissionCheck("view_system");

		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
		fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

		User loginUser = UserUtil.getLoginUser();

		SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
				loginUser.getScope_system(), loginUser);

		SearchResult<ProductConfiguration> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		
		Iterator<ProductConfiguration> prdts = result.getResult().iterator();
		
		   System.out.println(prdts.hasNext());
		   
		long totalRecords = result.getTotalRecords();
		getListJson(prdts, totalRecords, searchCondition, true);
		return null;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IBaseService<ProductConfiguration> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<ProductConfiguration> baseService) {
		this.baseService = baseService;
	}

	/**
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public static void getListJson(Iterator<ProductConfiguration> prdts,
			long totalRecords, SearchCondition searchCondition, boolean isList)
			throws Exception {

		StringBuilder jsonBuilder = new StringBuilder("");
		jsonBuilder
				.append(getJsonHeader(totalRecords, searchCondition, isList));
		
		while (prdts.hasNext()) {
			ProductConfiguration instance = prdts.next();
			Integer id = instance.getId();
			String padt_no = CommonUtil
					.fromNullToEmpty(instance.getProductNo());
			String padt_name = CommonUtil.fromNullToEmpty(instance
					.getProductName());
			Integer standard_amt = instance.getStandardAMT();
			String advance_redeem = CommonUtil.fromNullToEmpty(instance
					.getAdvanceRedeem());
			String pryment_type = CommonUtil.fromNullToEmpty(instance
					.getPrymentType());
			String stage_days = CommonUtil.fromNullToEmpty(instance
					.getStageDays());
			String return_rate = CommonUtil.fromNullToEmpty(instance
					.getReturnRate());
			String redeem_days = CommonUtil.fromNullToEmpty(instance
					.getRedeemDays());
			String is_redeem_before = CommonUtil.fromNullToEmpty(instance
					.getIsRedeemBefore());
			String rates = CommonUtil.fromNullToEmpty(instance.getRates());

			if (isList) {

				jsonBuilder.append("{\"cell\":[\"").append(id)
				.append("\",\"").append(padt_no)
				.append("\",\"").append(padt_name)
				.append("\",\"").append(standard_amt)
				.append("\",\"").append(advance_redeem)
				.append("\",\"").append(pryment_type)
				.append("\",\"").append(advance_redeem)
				.append("\",\"").append(stage_days)
				.append("\",\"").append(return_rate)
				.append("\",\"").append(redeem_days)
				.append("\",\"").append(is_redeem_before)
				.append("\",\"").append(rates)
				.append("\"]}");

			} else {
				jsonBuilder.append("{\"id\":\"").append(id)
						.append("\",\"padt_no\":\"").append(padt_no)
						.append("\",\"padt_name\":\"").append(padt_name)
						.append("\",\"standard_amt\":\"").append(standard_amt)
						.append("\",\"advance_redeem\":\"")
						.append(advance_redeem)
						.append("\",\"pryment_type\":\"").append(pryment_type)
						.append("\",\"advance_redeem\":\"")
						.append(advance_redeem).append("\",\"stage_days\":\"")
						.append(stage_days).append("\",\"return_rate\":\"")
						.append(return_rate).append("\",\"redeem_days\":\"")
						.append(redeem_days)
						.append("\",\"is_redeem_before\":\"")
						.append(is_redeem_before).append("\",\"rates\":\"")
						.append(rates).append("\"}");
			}
			if (prdts.hasNext()) {
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
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_system");
        baseService.batchDeleteEntity(ProductConfiguration.class, this.getSeleteIDs());
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
                ProductConfiguration oriRecord = baseService.getEntityById(ProductConfiguration.class,
                        Integer.valueOf(copyid));
                ProductConfiguration targetRecord = oriRecord.clone();
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
        String fileName = getText("menu.ProductConfiguration") + ".csv";
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        File file = new File(fileName);
        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
                CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = new String[] { 
            	
            		getText("menu.ProductConfiguration.id"),
            		getText("menu.ProductConfiguration.PADT_NO"),
            		getText("menu.ProductConfiguration.PADT_NAME"),
                    getText("menu.ProductConfiguration.STANDARD_AMT"),
                    getText("menu.ProductConfiguration.ADVANCE_REDEEM"),
                    getText("menu.ProductConfiguration.PRYMENT_TYPE"),
                    getText("menu.ProductConfiguration.STAGE_DAYS"),
                    getText("menu.ProductConfiguration.RETURN_RATE"),
                    getText("menu.ProductConfiguration.REDEEM_DAYS"),                    
                    getText("menu.ProductConfiguration.IS_REDEEM_BEFORE"),
                    getText("menu.ProductConfiguration.RATES")   
                    
                   };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    ProductConfiguration prdt = baseService.getEntityById(ProductConfiguration.class,
                            Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                 
                    data1.put(header[0], prdt.getId());
                    data1.put(header[1], CommonUtil.fromNullToEmpty(prdt.getProductNo()));
                    data1.put(header[2],CommonUtil.fromNullToEmpty(prdt.getProductName()));
                    data1.put(header[3],prdt.getStandardAMT());
                    data1.put(header[4], CommonUtil.fromNullToEmpty(prdt.getAdvanceRedeem()));
                    data1.put(header[5], CommonUtil.fromNullToEmpty(prdt.getPrymentType()));
                    data1.put(header[6],CommonUtil.fromNullToEmpty(prdt.getStageDays()));
                    data1.put(header[7], CommonUtil.fromNullToEmpty(prdt.getReturnRate()));
                    data1.put(header[8], CommonUtil.fromNullToEmpty(prdt.getRedeemDays()));
                    data1.put(header[9], CommonUtil.fromNullToEmpty(prdt.getIsRedeemBefore()));
                    data1.put(header[10], CommonUtil.fromNullToEmpty(prdt.getRates()));
                   
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

    
    /**
     * Imports the entities
     * 
     * @return the SUCCESS result
     */
    public String importCSV() throws Exception {
        File file = this.getUpload();
        CsvListReader reader = new CsvListReader(new FileReader(file),
                CsvPreference.EXCEL_PREFERENCE);
        int failedNum = 0;
        int successfulNum = 0;
        try {
            final String[] header = reader.getCSVHeader(true);

            List<String> line = new ArrayList<String>();
            Map<String, String> failedMsg = new HashMap<String, String>();
            while ((line = reader.read()) != null) {

                Map<String, String> row = new HashMap<String, String>();
                for (int i = 0; i < line.size(); i++) {
                    row.put(header[i], line.get(i));
                }

                ProductConfiguration prdt = new ProductConfiguration();
                try {
                    
                    prdt.setId(Integer.getInteger(getText("menu.ProductConfiguration.id")));
                    prdt.setProductNo(getText("menu.ProductConfiguration.PADT_NO"));
                    prdt.setProductName(getText("menu.ProductConfiguration.PADT_NAME"));
                    prdt.setStandardAMT(Integer.getInteger(getText("menu.ProductConfiguration.STANDARD_AMT")));
                    prdt.setAdvanceRedeem(getText("menu.ProductConfiguration.ADVANCE_REDEEM"));                   
                    prdt.setPrymentType(getText("menu.ProductConfiguration.PRYMENT_TYPE"));
                    prdt.setStageDays(getText("menu.ProductConfiguration.STAGE_DAYS"));
                    prdt.setReturnRate(getText("menu.ProductConfiguration.RETURN_RATE"));
                    prdt.setRedeemDays(getText("menu.ProductConfiguration.REDEEM_DAYS"));
                    prdt.setIsRedeemBefore(getText("menu.ProductConfiguration.IS_REDEEM_BEFORE"));
                    prdt.setRates(getText("menu.ProductConfiguration.RATES"));
                    
                   
                    baseService.makePersistent(prdt);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    failedMsg.put(prdt.getName(), e.getMessage());
                }

            }

            this.setFailedMsg(failedMsg);
            this.setFailedNum(failedNum);
            this.setSuccessfulNum(successfulNum);
            this.setTotalNum(successfulNum + failedNum);
        } finally {
            reader.close();
        }
        return SUCCESS;
    }
	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

}
