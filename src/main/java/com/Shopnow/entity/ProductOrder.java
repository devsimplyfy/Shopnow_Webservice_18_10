package com.Shopnow.entity;

public class ProductOrder {
	
	int CartId = 0;
	int UserId = 0;
	int product_id=0;
	int quntity=0;
	int vendor_id=0;
	
	String search;
	String orderBy;
	String order_status;
	
	
	
	
	
	
	
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
	
	public int getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(int vendor_id) {
		this.vendor_id = vendor_id;
	}

	public void setCartId(int CartId) {
		this.CartId = CartId;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getQuntity() {
		return quntity;
	}

	public void setQuntity(int quntity) {
		this.quntity = quntity;
	}

	public void setUserId(int UserId) {
		this.UserId = UserId;
	}

	public int getCartId() {
		
		// TODO Auto-generated method stub
		return CartId;
	}

	public int getUserId() {
		
		// TODO Auto-generated method stub
		return UserId;
	}

	
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getOrder_status() {
		// TODO Auto-generated method stub
		return order_status;
	}


}
