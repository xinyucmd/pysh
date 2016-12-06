package com.jiangchuanbanking.subscription.domain;

import java.io.Serializable;

public class SubscripId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ListSubscrip id;
	
	public SubscripId(){
		super (); 
	}

	public ListSubscrip getId() {
		return id;
	}

	public void setId(ListSubscrip id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
