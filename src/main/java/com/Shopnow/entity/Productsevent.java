package com.Shopnow.entity;

/**
 * @author Ajay 
 *
 */
public class Productsevent {
	
	
	
	private int id;
    private String name;
    private String description;
    private float regular_price;
    private float sale_price;
    private String stock;
    private String image;
    private String[] image_extra;
 

    
    
    
    
    
	public Productsevent(int id,String name,String description,float regular_price,float sale_price,String stock,String image,String[] image_extra)
	 {
		this.id=id;
		this.name=name;
		this.description=description;
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



	public Productsevent() {
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



	public float getRegular_price() {
		return regular_price;
	}



	public void setRegular_price(float regular_price) {
		this.regular_price = regular_price;
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





  
}
