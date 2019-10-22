package com.Shopnow.entity;

/**
 * @author Ajay 
 *
 */
public class Product_detailsevent {
	
	
	
	private int id;
    private String name;
    private String description;
    private float regular_price;
    private String Category_name;
    private float sale_price;
    private String stock;
    private String image;
    private String Product_URL;
    private String similar_product_id;
    private String recommended_product_id;
    private String vendor_id;
    private String[] attributes;
 

    
    
    
    
    
	public Product_detailsevent(int id,String name,String description,String Category_name,float regular_price,float sale_price,String stock,String image,String Product_URL,String[] attributes,String similar_product_id,String recommended_product_id,String vendor_id)
	 {
		this.id=id;
		this.name=name;
		this.description=description;
		this.Category_name=Category_name;
		this.regular_price=regular_price;
		this.sale_price=sale_price;
		this.stock=stock;
		this.Product_URL=Product_URL;
		this.image=image;
	   this.similar_product_id=similar_product_id;
	   this.recommended_product_id=recommended_product_id;
	   this.vendor_id=vendor_id;
	   this.attributes=attributes;
	
	

}

	public Product_detailsevent() {
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

	public String getCategory_name() {
		return Category_name;
	}
	public void setCategory_name(String category_name) {
		Category_name = category_name;
	}


	public float getSale_price() {
		return sale_price;
	}

	public void setSale_price(float sale_price) {
		this.sale_price = sale_price;
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

	public String getProduct_URL() {
		return Product_URL;
	}


	public void setProduct_URL(String product_URL) {
		Product_URL = product_URL;
	}


	public String getSimilar_product_id() {
		return similar_product_id;
	}


	public void setSimilar_product_id(String similar_product_id) {
		this.similar_product_id = similar_product_id;
	}

	public String getRecommended_product_id() {
		return recommended_product_id;
	}



	public void setRecommended_product_id(String recommended_product_id) {
		this.recommended_product_id = recommended_product_id;
	}



	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String[] getAttribute_value() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}



	}
