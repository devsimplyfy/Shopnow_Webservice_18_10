package com.Shopnow.entity;

/**
 * @author Ajay 
 *
 */
public class shopNoweventProduct_options {
		//THIS IS GETTER SETTER METHOD FOR COLOR_CODE_MAIN TABLE
	
	
	
	private int id;
	private String option_name;
    private String value;
    
    
   // THIS CONSTRUTOR FOR ADDRESS COLOR_CODE_MAIN TABLE
    
    public shopNoweventProduct_options() {
        
    }
    
    
    
	public shopNoweventProduct_options(int id, String option_name,String value) {
        this.id = id;
        this.option_name = option_name;
        this.value=value;
    }



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getOption_name() {
		return option_name;
	}



	public void setOption_name(String option_name) {
		this.option_name = option_name;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}
	
	
	  
}
