package com.Shopnow.entity;

/**
 * @author Ajay 
 *
 */
public class Products_list_event {
	
	
	
	private int id;
 
    private int customerId;
    String orderBy;
    String search;
	private float min_price;
	private float max_price;
	int page_number;
	
	
 
	

	public int getId() {
		return id;
	}
	
	public int getCustomerId() {
		// TODO Auto-generated method stub
		return customerId;
	}



	public String getOrderBy() {
		
		// TODO Auto-generated method stub
		return orderBy;
	}



	public String getSearch() {
		// TODO Auto-generated method stub
		return search;
	}



	public float getMin_price() {
		// TODO Auto-generated method stub
		
		return min_price;
	}



	public float getMax_price() {
		// TODO Auto-generated method stub
	
		return max_price;
	}



	public int getPage_number() {
	
		// TODO Auto-generated method stub
		return page_number;
	}







/*    
    
    
    
    
	public Products_list_event(int id,String name,String description,float regular_price,float sale_price,String stock,String image,String[] image_extra, int customerId)
	 {
		this.id=id;
		this.name=name;
		this.description=description;
		this.customerId=customerId;
		//this.Category_name=Category_name;
		//this.parent_category=parent_category;
		//this.Product_URL=Product_URL;
		this.regular_price=regular_price;
		this.sale_price=sale_price;
		this.stock=stock;
		
		this.image=image;
		
		this.image_extra=image_extra;
	

}



	public float getSale_price() {
		return sale_price;
	}



	public void setSale_price(float sale_price) {
		this.sale_price = sale_price;
	}



	public Products_list_event() {
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	



	public String getStock() {
		return stock;
	}



	public void setStock(String stock) {
		this.stock = stock;
	}



	




	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public String[] getImage_extra() {
		return image_extra;
	}



	public void setImage_extra(String[] image_extra) {
		this.image_extra = image_extra;
	}



	public long getTotal() {
		return total;
	}



	public void setTotal(long total) {
		this.total = total;
	}

*/  
}
