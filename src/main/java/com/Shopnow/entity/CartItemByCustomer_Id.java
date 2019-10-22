package com.Shopnow.entity;

public class CartItemByCustomer_Id {
	

	int UserId = 0;
	String search;
	String device_id;
	


	public void setUserId(int UserId) {
		this.UserId = UserId;
	}


	public int getUserId() {
		return UserId;
	}


	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
		
		
	}


	public String getDevice_id() {
		return device_id;
	}


	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}


	

}
