package com.Shopnow.dao;

import java.sql.SQLException;
import java.util.Collection;
import com.Shopnow.entity.shopNoweventaddress;
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
import com.Shopnow.entity.shopNoweventCustomer_list;
import com.Shopnow.entity.shopNoweventOffer;
import com.Shopnow.entity.shopNoweventProduct_att;
import com.Shopnow.entity.shopNoweventProduct_options;
import com.Shopnow.entity.shopNoweventProducts_family;
import com.Shopnow.entity.shopNoweventWish_list;


public interface shopNowDao {

 //THIS INTERFACE METHOD OF ADDRESS CLASS	


//----------------------------------------------------CUSTOMER ADDRESS-------------------------------------------------------
 String getAllCustomer_address() throws SQLException;
 String getCustomer_addressById(long customerId) throws SQLException, ClassNotFoundException;

 String removeCustomer_addressById(shopNoweventaddress c1);

 String insertCustomer_addressToDb(shopNoweventaddress c1);
 void updateCustomer_addressById(shopNoweventaddress c1);


 //------------------------------------------------------------       Product_list Interface MEthods-------------------------------------------------------------------------

 String getProducts_list() throws SQLException;
 String getProducts_listById(Products_list_event product_list) throws SQLException, ClassNotFoundException;

 //--------------------------------------------------------           -Deal_Detail-----------------------------------------------------------------------------------
 String getAllDeal_detail();
 String getDeal_detailById(long id);
 //------------------------------------------------------------------------CategoryNEw--------------------------------------------------------------------

 String getAllCategorynew();
 String getCategorynewById(long id);

 //-------------------------------------------------------------------     Product_DEtail-------------------------------------------------------------------------

 String getProduct_detailById(long id) throws SQLException;

 //---------------------------------------------------------- Customer-----------------------------------------------------------------------------------------
 String getAllCustomer();
 String getCustomerById(long id);



 //---------------------------------------------------------------------------wish_list----------------------------------------------------------

 String getwish_listByCust_Id(long id);
 public String insert_wish_listToDb(shopNoweventWish_list wl1) throws SQLException, ClassNotFoundException;
 public String delete_wish_listToDb(shopNoweventWish_list wl1) throws SQLException, ClassNotFoundException;

 //---------------------------------------------------------------------customer Login----------------------------------------------------


 String insertCustomer_listDao(shopNoweventCustomer_list c1) throws ClassNotFoundException, SQLException;
 String updateCustomer_list_pass(shopNoweventCustomer_list c1) throws ClassNotFoundException, SQLException;
 String getcustomer_By_emailAnd_psw(shopNoweventCustomer_list a1) throws ClassNotFoundException, SQLException;



 //----------------------------------------------------home--------------------------------------------------------------------			
 String getHomeByCust_Id(long id);
 String getHome();
 //-----------------------------------------------------filter----------------------------------------------------------			
 String getfilterByCategory_Id(long id);

//---------------------------------------------------CartItem-----------------------------------------------------



//--------------------------------------------------------------------------------------------------------------
 String getAllCartItem();


























 //===================================================Extra==============================================================================================


 //--------------------------------THIS INTERFACE METHOD OF PRODUCT_ATT CLASS-------------------------------------------------------

 Collection < shopNoweventProduct_att > getAllProduct_atts() throws SQLException;
 Collection < shopNoweventProduct_att > getProduct_attById(int id) throws SQLException, ClassNotFoundException;




 //--------------------------------------------THIS INTERFACE METHOD OF Product_Offer CLASS----------------------------------------------

 Collection < shopNoweventOffer > getAllOffer() throws SQLException;
 Collection < shopNoweventOffer > getOfferById(int product_id) throws SQLException, ClassNotFoundException;


 //--------------------------------------------------THIS INTERFACE METHOD OF Products CLASS----------------------------------------------

 Collection < Productsevent > getAllProducts() throws SQLException;
 Collection < Productsevent > getProductsById(int id) throws SQLException;


 //---------------------------------------------------THIS INTERFACE METHOD OF ProductsAll CLASS--------------------------------------------

 Collection < ProductsAllevent > getAllProductsAll() throws SQLException;
 Collection < ProductsAllevent > getProductsAllById(int id) throws SQLException;





 //----------------------------------------THIS INTERFACE METHOD OF Category CLASS------------------------------------------------------------

 Collection < shopNoweventCategory > getAllCategory() throws SQLException, ClassNotFoundException;
 Collection < shopNoweventCategory > getCategoryById(int id) throws SQLException, ClassNotFoundException;



 //--------------------------------THIS INTERFACE METHOD OF PRODUCT_FAMILY CLASS-------------------------------------------------------------

 Collection < shopNoweventProducts_family > getAllProducts_family() throws SQLException, ClassNotFoundException;
 Collection < shopNoweventProducts_family > getProducts_familyById(int id) throws SQLException, ClassNotFoundException;


 //----------------------------THIS INTERFACE METHOD OF PRODUCT_OPTION CLASS----------------------------------------------------------------

 Collection < shopNoweventProduct_options > getAllProduct_options() throws SQLException, ClassNotFoundException;
 Collection < shopNoweventProduct_options > getProduct_optionsById(int id) throws SQLException, ClassNotFoundException;
 
 
 
String getCartItemByCustomer_Id(CartItemByCustomer_Id a1);

String getCartItemByAdd(CartItemByAdd a1);


String getCartItemByDelete(CartItemByDelete a1);

String getProductOrder(ProductOrder a1);

String getPlaceOrder(PlaceOrder a1);


String getPaymentByCard(Payment pay1);

String getCartItemByRemove(CartItemByRemove a1);

String getOrderDetail(OrderDetail a1);
String getOrderDisplay(OrderDisplay a1);

















}