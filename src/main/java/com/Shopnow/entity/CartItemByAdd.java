package com.Shopnow.entity;

public class CartItemByAdd {
	
	int CartId = 0;
	int UserId = 0;
	int product_id=0;
	int Quantity=0;
	int vendor_id=0;
	
	
	
	
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
		return Quantity;
	}

	public void setQuantity(int Quantity) {
		this.Quantity = Quantity;
	}

	public int getQuantity() {
		return Quantity;
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

	

	

}
