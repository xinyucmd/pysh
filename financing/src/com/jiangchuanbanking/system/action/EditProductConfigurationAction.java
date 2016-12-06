package com.jiangchuanbanking.system.action;

import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.system.domain.ProductConfiguration;
import com.opensymphony.xwork2.Preparable;


public class EditProductConfigurationAction extends BaseEditAction implements
		Preparable {

	private ProductConfiguration prdt;
	private IBaseService<ProductConfiguration> baseService;

	public IBaseService<ProductConfiguration> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<ProductConfiguration> baseService) {
		this.baseService = baseService;
	}

	public ProductConfiguration getPrdt() {
		return prdt;
	}

	public void setPrdt(ProductConfiguration prdt) {
		this.prdt = prdt;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String get() throws Exception {
		
		return SUCCESS;
	}

	/**
    * Mass update entity record information
    */
  public String massUpdate() throws Exception {

      prdt = getBaseService().makePersistent(prdt);
    
      return SUCCESS;
  }


	 /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {

        prdt = getBaseService().makePersistent(prdt);
       
        return SUCCESS;
    }

	
//    private void saveEntity() throws Exception {
//			    
////	            UserUtil.permissionCheck("update_system");
//	            ProductConfiguration originalProduct = baseService.getEntityById(ProductConfiguration.class,
//	                    prdt.getId());
//	            System.out.println("888888");
//	            
//	            prdt.setId(originalProduct.getId());
//	            prdt.setProductNo(originalProduct.getProductNo());
//	            prdt.setProductName(originalProduct.getProductName());
//	            prdt.setStandardAMT(originalProduct.getStandardAMT());
//	            prdt.setAdvanceRedeem(originalProduct.getAdvanceRedeem());
//	            prdt.setPrymentType(originalProduct.getPrymentType());
//	            prdt.setStageDays(originalProduct.getStageDays());
//	            prdt.setIsRedeemBefore(originalProduct.getIsRedeemBefore());
//
//	      
//	        super.updateBaseInfo(prdt);
//	}
//	
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

}
