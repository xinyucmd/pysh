package com.jiangchuanbanking.investor.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jiangchuanbanking.base.action.BaseEditAction;
import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.investor.domain.Contacts;
import com.jiangchuanbanking.investor.domain.Customer;
import com.jiangchuanbanking.util.CommonUtil;
import com.opensymphony.xwork2.Preparable;

public class EditCustomerAction extends BaseEditAction implements Preparable {

	private static final long serialVersionUID = 1L;
	private Customer cust;
	private Contacts contacts;
	private IBaseService baseService;
	private String ownerName;

	@SuppressWarnings("unchecked")
	public String get() throws Exception {
		if (this.getId() != null) {
			cust = (Customer) baseService.getEntityById(Customer.class,
					this.getId());
			contacts = (Contacts) baseService.getEntityById(Contacts.class,
					this.getId());
			this.ownerName = cust.getOpen_op();
		}
		return SUCCESS;
	}

	public String save() throws Exception {
		Date date = new Date();
		String userName = CommonUtil.getLoginUserName();
		Integer userId = CommonUtil.getLoginUserId();
		String sysTime = new SimpleDateFormat("yy/MM/dd").format(date)
				.toString();
		if (cust.getId() == null) {
			contacts.setCif_name(cust.getCif_name());
			cust.setOpen_date(sysTime);

			cust.setOpen_op(userName);
			cust.setOpen_id(userId);

			cust = getBaseService().makePersistent(cust);
			contacts.setContact_id(cust.getId());
			baseService.makePersistent(contacts);
		} else {

			contacts.setCif_name(cust.getCif_name());
			contacts.setContact_id(cust.getId());
			Customer cust2 = (Customer) baseService.getEntityById(
					Customer.class, cust.getId());

			cust.setOpen_date(cust2.getOpen_date());
			cust.setOpen_op(cust2.getOpen_op());
			cust.setOpen_id(userId);
			cust = getBaseService().makePersistent(cust);

			contacts = (Contacts) baseService.makePersistent(contacts);

		}
		return SUCCESS;
	}

	public String massUpdate() throws Exception {

		String userName = CommonUtil.getLoginUserName();
		System.out.println(userName);
		cust = getBaseService().makePersistent(cust);
		return SUCCESS;
	}

	public Customer getCust() {
		return cust;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public IBaseService<Customer> getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService<Customer> baseService) {
		this.baseService = baseService;
	}

	public Contacts getContacts() {
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}
}
