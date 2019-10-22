package com.Shopnow.entity;

import java.util.Date;
/*
/**
 * @author Ajay 10-04-19
 *
 */
public class shopNoweventaddress {
	private int id;
    private int customerId;
    private String type_billing_shipping;
   
    private String lastName;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String country;
    private String phoneNumber;
    private int isPrimary;
    private Date createdDatetime;
    private String firstName;
    
    
   
    //THIS CONSTRUTOR FOR ADDRESS THABEL
    
    public shopNoweventaddress(int id,int customerId,String type_billing_shipping,String firstName,String lastName,String address1,String address2,String address3,String city,
			String state,String country,String phoneNumber,int isPrimary,Date createdDatetime)
	 {
		this.id=id;
		this.customerId=customerId;
		this.type_billing_shipping=type_billing_shipping;
		this.firstName=firstName;
		this.lastName=lastName;
		this.address1=address1;
		this.address2=address2;
		this.address3=address3;
		this.city=city;
		this.state=state;
		this.country=country;
		this.phoneNumber=phoneNumber;
		this.isPrimary=isPrimary;
		this.createdDatetime=createdDatetime;

}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public synchronized String getAddress1() {
		return address1;
	}



	public synchronized void setAddress1(String address1) {
		this.address1 = address1;
	}



	public synchronized String getAddress2() {
		return address2;
	}



	public synchronized void setAddress2(String address2) {
		this.address2 = address2;
	}



	public synchronized String getAddress3() {
		return address3;
	}



	public synchronized void setAddress3(String address3) {
		this.address3 = address3;
	}



	public synchronized String getCity() {
		return city;
	}



	public synchronized void setCity(String city) {
		this.city = city;
	}



	public synchronized String getState() {
		return state;
	}



	public synchronized void setState(String state) {
		this.state = state;
	}



	public synchronized String getCountry() {
		return country;
	}



	public synchronized void setCountry(String country) {
		this.country = country;
	}



	public synchronized String getPhoneNumber() {
		return phoneNumber;
	}



	public synchronized void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public synchronized int getCustomerId() {
		return customerId;
	}



	public synchronized void setCustomerId(int customerId) {
		this.customerId = customerId;
	}



	public synchronized String getType_billing_shipping() {
		return type_billing_shipping;
	}



	public synchronized void setType_billing_shipping(String type_billing_shipping) {
		this.type_billing_shipping = type_billing_shipping;
	}



	public synchronized int getIsPrimary() {
		return isPrimary;
	}



	public synchronized void setIsPrimary(int isPrimary) {
		this.isPrimary = isPrimary;
	}



	public synchronized Date getCreatedDatetime() {
		return createdDatetime;
	}



	public synchronized void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	

	public shopNoweventaddress() {
		// TODO Auto-generated constructor stub
	}


/*
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getCustomerId() {
		return customerId;
	}



	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}



	public String getType_billing_shipping() {
		return type_billing_shipping;
	}



	public void setType_billing_shipping(String type_billing_shipping) {
		this.type_billing_shipping = type_billing_shipping;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getAddress1() {
		return address1;
	}



	public void setAddress1(String address1) {
		this.address1 = address1;
	}



	public String getAddress2() {
		return address2;
	}



	public void setAddress2(String address2) {
		this.address2 = address2;
	}



	public String getAddress3() {
		return address3;
	}



	public void setAddress3(String address3) {
		this.address3 = address3;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public int getIsPrimary() {
		return isPrimary;
	}



	public void setIsPrimary(int isPrimary) {
		this.isPrimary = isPrimary;
	}



	public Date getCreatedDatetime() {
		return createdDatetime;
	}



	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}


    
    
  */

}
