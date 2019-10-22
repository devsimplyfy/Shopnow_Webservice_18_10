package com.Shopnow.entity;

/**
 * @author Ajay 
 *
 */
public class shopNoweventProducts_family {
		//THIS IS GETTER SETTER METHOD FOR COLOR_CODE_MAIN TABLE
	
	
	
	
	private String product_family_id;
    private String products_family;
    
    
   // THIS CONSTRUTOR FOR ADDRESS COLOR_CODE_MAIN TABLE
    
    public shopNoweventProducts_family() {
        
    }
    
    
    
	public shopNoweventProducts_family(int color_id, String product_family_id,String products_family) {
        
        this.product_family_id = product_family_id;
        this.products_family=products_family;
    }



	public String getProduct_family_id() {
		return product_family_id;
	}



	public void setProduct_family_id(String product_family_id) {
		this.product_family_id = product_family_id;
	}



	public String getProducts_family() {
		return products_family;
	}



	public void setProducts_family(String products_family) {
		this.products_family = products_family;
	}
	
		  
}
