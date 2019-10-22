package com.Shopnow.service;


import com.Shopnow.entity.CartItemByAdd;
import com.Shopnow.entity.CartItemByCustomer_Id;
import com.Shopnow.entity.CartItemByDelete;
import com.Shopnow.entity.CartItemByRemove;
import com.Shopnow.entity.OrderDetail;
import com.Shopnow.entity.OrderDisplay;
import com.Shopnow.entity.Payment;
import com.Shopnow.entity.PlaceOrder;
import com.Shopnow.entity.ProductOrder;
import com.Shopnow.entity.ProductsAllevent;
import com.Shopnow.entity.Products_list_event;
import com.Shopnow.entity.Productsevent;
import com.Shopnow.entity.shopNoweventCategory;
import com.Shopnow.dao.shopNowDao;
import com.Shopnow.entity.shopNoweventaddress;
import com.Shopnow.entity.shopNoweventCustomer_list;
import com.Shopnow.entity.shopNoweventOffer;
import com.Shopnow.entity.shopNoweventProduct_att;
import com.Shopnow.entity.shopNoweventProduct_options;
import com.Shopnow.entity.shopNoweventProducts_family;
import com.Shopnow.entity.shopNoweventWish_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class shopNowService {

 @Autowired
 @Qualifier("mysql")
 private shopNowDao shopnowDao;


 //-------------------------------------------------------------Service Method for Address web service------------------------------------------------------------

 public String getAllCustomer_address() throws SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getAllCustomer_address();
 }

 public String getCustomer_addressById(int customerId) throws SQLException, ClassNotFoundException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getCustomer_addressById(customerId);
 }

 public String removeCustomer_addressById(shopNoweventaddress emp) throws ClassNotFoundException, SQLException {
  return this.shopnowDao.removeCustomer_addressById(emp);

 }

 public String insertCustomer_adress(shopNoweventaddress emp) {
  return this.shopnowDao.insertCustomer_addressToDb(emp);

 }

 public void updateCustomer_addressById(shopNoweventaddress emp) {
  // TODO Auto-generated method stub
  this.shopnowDao.updateCustomer_addressById(emp);
 }


 //---------------------------------------------------------Service Method for Category API------------------------------------------------



 public Collection < shopNoweventCategory > getAllCategory() throws SQLException, ClassNotFoundException {
  return this.shopnowDao.getAllCategory();
 }

 public Collection < shopNoweventCategory > getCategoryById(int color_id) throws SQLException, ClassNotFoundException {
  return this.shopnowDao.getCategoryById(color_id);
 }



 //---------------------------------------------------Service Method for Product_list API-----------------------------------------------------

 public String getProducts_list() throws SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getProducts_list();
 }

 public String getProducts_listById(Products_list_event product_list) throws SQLException, ClassNotFoundException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getProducts_listById(product_list);
 }


 //---------------------------------------------------Service method for product Detail API-------------------------------------------------------


 public String getProduct_detailById(int id) throws SQLException {
  return this.shopnowDao.getProduct_detailById(id);
 }


 //------------------------------------------------------Service Method for DEal_Detail------------------------------------------------------------------------------
 public String getAllDeal_detail() {
  return this.shopnowDao.getAllDeal_detail();
 }

 public String getDeal_detailById(int id) {
  return this.shopnowDao.getDeal_detailById(id);
 }




 //----------------------------------------------------------Category_byId API---------------------------------------

 public String getAllCategorynew() {
  return this.shopnowDao.getAllCategorynew();
 }

 public String getCategorynewById(int id) {
  return this.shopnowDao.getCategorynewById(id);
 }



 //--------------------------------------------------------------Wish_list API--------------------------------------------------------------

 public String getwish_listByCust_Id(int id) {
  return this.shopnowDao.getwish_listByCust_Id(id);
 }

 public String insert_wish_listToDb(shopNoweventWish_list wl) throws ClassNotFoundException, SQLException {
  return this.shopnowDao.insert_wish_listToDb(wl);
 }

 public String delete_wish_listToDb(shopNoweventWish_list wl) throws ClassNotFoundException, SQLException {
  return this.shopnowDao.delete_wish_listToDb(wl);
 }
 //-----------------------------------------------------Change Password------------------------------------------------------------------

 public String updateCustomer_list_pass(com.Shopnow.entity.shopNoweventCustomer_list c1) throws ClassNotFoundException, SQLException {
  return this.shopnowDao.updateCustomer_list_pass(c1);
 }


 //------------------------------------------------------insert customer-----------------------------------------------------------------------
 public String shopNoweventCustomer_list(shopNoweventCustomer_list cust) throws ClassNotFoundException, SQLException {
  return this.shopnowDao.insertCustomer_listDao(cust);


 }

 //-----------------------------------------------Login----------------------------------------------

 public String getcustomer_By_emailAnd_psw(shopNoweventCustomer_list a1) throws ClassNotFoundException, SQLException {
  return this.shopnowDao.getcustomer_By_emailAnd_psw(a1);
 }

//-----------------------------------------------------------------------------customer------------------------------------
 public String getAllCustomer() {
	  return this.shopnowDao.getAllCustomer();
	 }

	 public String getCustomerById(int id) {
	  return this.shopnowDao.getCustomerById(id);
	 }
//------------------------------------------------------------Home--------------------------------------------------------------
	 public String getHomeByCust_Id(int id) {
	  return this.shopnowDao.getHomeByCust_Id(id);
	 }

//---------------------------------------------------------------filter-----------------------------------------------------------------
	 public String getfilterByCategory_Id(int id) {
	  return this.shopnowDao.getfilterByCategory_Id(id);
	 }

	 public String getHome() {
	  return this.shopnowDao.getHome();
	 }


































 //=================================================================================Extra Methods==================================================================



 //THIS SERVICE METHOD FOR PRODUCT_ATT TABLE

 public Collection < shopNoweventProduct_att > getAllProduct_atts() throws SQLException {

  return this.shopnowDao.getAllProduct_atts();
 }

 public Collection < shopNoweventProduct_att > getProduct_attById(int product_id) throws SQLException, ClassNotFoundException {

  return this.shopnowDao.getProduct_attById(product_id);
 }



 //THIS SERVICE METHOD FOR Offer TABLE

 public Collection < shopNoweventOffer > getAllOffer() throws SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getAllOffer();
 }

 public Collection < shopNoweventOffer > getOfferById(int product_id) throws ClassNotFoundException, SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getOfferById(product_id);
 }




 public Collection < Productsevent > getAllProducts() throws SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getAllProducts();
 }

 public Collection < Productsevent > getProductsById(int id) throws SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getProductsById(id);
 }






 public Collection < ProductsAllevent > getAllProductsAll() throws SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getAllProductsAll();
 }

 public Collection < ProductsAllevent > getProductsAllById(int id) throws SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getProductsAllById(id);
 }





 //-----------------------------------------------PRODUCT_FAMILY-----------------------------------------------------
 public Collection < shopNoweventProducts_family > getAllProducts_family() throws ClassNotFoundException, SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getAllProducts_family();
 }

 public Collection < shopNoweventProducts_family > getProducts_familyById(int id) throws ClassNotFoundException, SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getProducts_familyById(id);
 }


 //----------------------------------------------------------------------

 public Collection < shopNoweventProduct_options > getAllProduct_options() throws ClassNotFoundException, SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getAllProduct_options();
 }

 public Collection < shopNoweventProduct_options > getProduct_optionsById(int id) throws ClassNotFoundException, SQLException {
  // TODO Auto-generated method stub
  return this.shopnowDao.getProduct_optionsById(id);
 }

 
 
public String getCartItemByCustomer_Id(CartItemByCustomer_Id a1) {
	// TODO Auto-generated method stub
	return this.shopnowDao.getCartItemByCustomer_Id(a1);
}

public String getAllCartItems() {
	// TODO Auto-generated method stub
	return this.shopnowDao.getAllCartItem() ;
}

public String getCartItemByAdd(CartItemByAdd a1) {
	// TODO Auto-generated method stub
	return this.shopnowDao.getCartItemByAdd(a1);
}



public String getCartItemByDelete(CartItemByDelete a1) {
	// TODO Auto-generated method stub
	return this.shopnowDao.getCartItemByDelete(a1);
}

public String getProductOrder(ProductOrder a1) {
	// TODO Auto-generated method stub
	return this.shopnowDao.getProductOrder(a1);
}

public String getPlaceOrder(PlaceOrder a1) {
	// TODO Auto-generated method stub
	return this.shopnowDao.getPlaceOrder(a1);
}

public String getPaymentByCard(Payment pay1) {
	// TODO Auto-generated method stub
	return this.shopnowDao.getPaymentByCard(pay1);
}

public String getCartItemByRemove(CartItemByRemove a1) {
	// TODO Auto-generated method stub
	return this.shopnowDao.getCartItemByRemove(a1);
}

public String getOrderDetail(OrderDetail a1) {
	// TODO Auto-generated method stub
	return this.shopnowDao.getOrderDetail(a1);
}

public String getOrderDisplay(OrderDisplay a1) {
	// TODO Auto-generated method stub
	return this.shopnowDao.getOrderDisplay(a1);
}


 //-----------------------------------------------------------------------------------------------------------------------

 //----------------------------------------------------------------------------------------------------------------


 //-------------------------------------------------------------------------------------------------------------------------------------------------------




 //-------------------------------------------------------------------------------------------------------



}