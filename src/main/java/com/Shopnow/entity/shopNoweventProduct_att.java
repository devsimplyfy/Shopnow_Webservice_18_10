package com.Shopnow.entity;

/**
 * @author Ajay 
 *
 */
public class shopNoweventProduct_att {
		// THIS GETER SETTER METHOD FOR PRODUCT_ATTRIBUTE TABLE
	
	    //private int Pro_id;
	    private int product_id;
	    private String[] att_value;
	    private String price;
	    private String product_url;
	    
	    
	    
	    
	    	public shopNoweventProduct_att() {
	        
			
	        
	    }
	    
	    
	    
		public shopNoweventProduct_att(int product_id,String[] att_value,String price,String product_url) {
	        
			
			//this.Pro_id = Pro_id;
	        this.product_id =product_id;
	        this.product_url=product_url;
	        this.att_value=att_value;
	        this.price=price;
	        
	    }

	

		public int getProduct_id() {
			return product_id;
		}

		public void setProduct_id(int product_id) {
			this.product_id = product_id;
		}

		public String[] getAtt_value() {
			return att_value;
		}

		public void setAtt_value(String[] att_value) {
			this.att_value = att_value;
		}



		public String getPrice() {
			return price;
		}



		public void setPrice(String price) {
			this.price = price;
		}



		public String getProduct_url() {
			return product_url;
		}



		public void setProduct_url(String product_url) {
			this.product_url = product_url;
		}

		
	
  
}
