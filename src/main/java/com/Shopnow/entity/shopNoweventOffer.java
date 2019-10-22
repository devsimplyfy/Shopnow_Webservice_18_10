package com.Shopnow.entity;

/**
 * @author Ajay 
 *
 */
public class shopNoweventOffer {
		//THIS IS GETTER SETTER METHOD FOR COLOR_CODE_MAIN TABLE
	
	 
	    private int product_id;
	    private String[] offer_name;
	   
	   
	    public shopNoweventOffer() {
	        
	    }
	   

		public String[] getOffer_name() {
			return offer_name;
		}





		public void setOffer_name(String[] offer_name) {
			this.offer_name = offer_name;
		}





		public int getProduct_id() {
			return product_id;
		}





		public void setProduct_id(int product_id) {
			this.product_id = product_id;
		}





		public shopNoweventOffer(int product_id, String[] offer_name) {
	       // this.id = id;
			this.product_id = product_id;
	        this.offer_name = offer_name;
	        
	       // this.vendor_id=vendor_id;
	    }

	}
