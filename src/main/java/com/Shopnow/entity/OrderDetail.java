package com.Shopnow.entity;

public class OrderDetail {
	
	int CartId = 0;
	int UserId = 0;
	int product_id=0;
	int quntity=0;
	int vendor_id=0;
	
	String Payment_mode;
	String delivery_address;
	String billing_address;
	
	String cardtype;
	String name_on_card;
	int card_number=0;
	int expiryMonth=0;
	String date1; 
	String Search;
	String OrderBy;
	String Order_id;
	int page_number;
	
	

	public void setPage_number(int page_number) {
		this.page_number = page_number;
	}

	public void setOrder_id(String order_id) {
		Order_id = order_id;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public int getCard_number() {
		return card_number;
	}

	public void setCard_number(int card_number) {
		this.card_number = card_number;
	}
	public String getName_on_card() {
		return name_on_card;
	}

	public void setName_on_card(String name_on_card) {
		this.name_on_card = name_on_card;
	}

	public String getPayment_mode() {
		return Payment_mode;
	}

	public void setPayment_mode(String Payment_mode) {
		this.Payment_mode = Payment_mode;
	}

	public String getDelivery_address() {
		return delivery_address;
	}

	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}

	public String getBilling_address() {
		return billing_address;
	}

	public void setBilling_address(String billing_address) {
		this.billing_address = billing_address;
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

	public String getSearch() {
		return Search;
	}

	public void setSearch(String search) {
		Search = search;
	}

	public String getOrderBy() {
		return OrderBy;
	}

	public void setOrderBy(String orderBy) {
		OrderBy = orderBy;
	}

	public String getOrder_id() {
		// TODO Auto-generated method stub
		return Order_id;
	}

	public int getPage_number() {
		// TODO Auto-generated method stub
		return page_number;
	}



}
