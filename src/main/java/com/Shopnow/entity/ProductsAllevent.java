package com.Shopnow.entity;

/**
 * @author Ajay 
 *
 */
public class ProductsAllevent {
	
	
	
	private int id;
    private String name;
    private String description;
    private float regular_price;
    private String Category_name;
    private String parent_category;
    private float sale_price;
    private String stock;
    private String image;
    private String Product_URL;
    private String[] image_extra;
 

    
    
    
    
    
	public ProductsAllevent(int id,String name,String description,String Category_name,String parent_category,float regular_price,float sale_price,String stock,String image,String Product_URL,String[] image_extra)
	 {
		this.id=id;
		this.name=name;
		this.description=description;
		this.Category_name=Category_name;
		this.parent_category=parent_category;
		this.regular_price=regular_price;
		this.sale_price=sale_price;
		this.stock=stock;
		this.Product_URL=Product_URL;
		this.image=image;
		
		this.image_extra=image_extra;
	

}



	public float getSale_price() {
		return sale_price;
	}



	public void setSale_price(float sale_price) {
		this.sale_price = sale_price;
	}



	public ProductsAllevent() {
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



	public String getCategory_name() {
		return Category_name;
	}



	public void setCategory_name(String category_name) {
		Category_name = category_name;
	}



	public String getParent_category() {
		return parent_category;
	}



	public void setParent_category(String parent_category) {
		this.parent_category = parent_category;
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



	



	public String getProduct_URL() {
		return Product_URL;
	}



	public void setProduct_URL(String product_URL) {
		Product_URL = product_URL;
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
