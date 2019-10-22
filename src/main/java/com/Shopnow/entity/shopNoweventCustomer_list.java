package com.Shopnow.entity;

/**
 * @author Ajay 
 *
 */
public class shopNoweventCustomer_list {
		//THIS IS GETTER SETTER METHOD FOR COLOR_CODE_MAIN TABLE
	

	
	private int id;
	private String f_name;
    private String l_name;
    private String email;
    private String phone_number;
    private String image;
    private String password;
	private String newpsw;
    
    
   // THIS CONSTRUTOR FOR ADDRESS COLOR_CODE_MAIN TABLE
    
    public shopNoweventCustomer_list() {
        
    }
    
    
    
	public shopNoweventCustomer_list(int id, String f_name,String l_name,String email,String phone_number,String image) {
    this.id=id;
    this.f_name=f_name;
    this.l_name=l_name;
    this.email=email;
    this.phone_number=phone_number;
    this.image=image;
  
    
    }



	public String getpassword() {
		return password;
	}



	public void setpassword(String password) {
		this.password = password;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getF_name() {
		return f_name;
	}



	public void setF_name(String f_name) {
		this.f_name = f_name;
	}



	public String getL_name() {
		return l_name;
	}



	public void setL_name(String l_name) {
		this.l_name = l_name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPhone_number() {
		return phone_number;
	}



	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public String getnewpsw() {
	
		// TODO Auto-generated method stub
		return newpsw;
		
	}



	public String getEmailid() {
		// TODO Auto-generated method stub
		return email;
	}



	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}



	
	
		  
}
