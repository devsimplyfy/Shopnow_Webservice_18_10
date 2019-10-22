package com.Shopnow.dao;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository("mysql")
public class MySqlshopNowDaoImpl implements shopNowDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private Object getTransaction_id;

	// ------------------------------------------------------------THIS
	// SQLIMPLIMANTETION FOR ADDRESS
	// TABLE--------------------------------------------------------------------------------------------------------

	private static class addressRowMapper implements RowMapper<JSONObject> {

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONArray ja_customerAddress = new JSONArray();
			JSONObject jo__customerAddress_Result = new JSONObject();

			do {
				JSONObject jo_customerAddress = new JSONObject();
				jo_customerAddress.put("id", rs.getString("id"));
				jo_customerAddress.put("firstName", rs.getString("firstName"));
				jo_customerAddress.put("lastName", rs.getString("lastName"));
				jo_customerAddress.put("address1", rs.getString("address1"));
				jo_customerAddress.put("address2", rs.getString("address2"));
				jo_customerAddress.put("address3", rs.getString("address3"));
				jo_customerAddress.put("city", rs.getString("city"));
				jo_customerAddress.put("state", rs.getString("state"));
				jo_customerAddress.put("country", rs.getString("country"));
				jo_customerAddress.put("phoneNumber", rs.getString("phoneNumber"));
				ja_customerAddress.put(jo_customerAddress);

			} while (rs.next());

			jo__customerAddress_Result.put("Address", ja_customerAddress);
			return jo__customerAddress_Result;

		}
	}

	@Override
	public String getAllCustomer_address() throws SQLException {

		final String sql = "select * from address LIMIT 10";
		List<JSONObject> cust_address = jdbcTemplate.query(sql, new addressRowMapper());
		if (cust_address.isEmpty()) {

			return "Address not Found!";
		} else {
			String Str_ALL_Customer_address = cust_address.toString().substring(1,
					cust_address.toString().length() - 1);
			return Str_ALL_Customer_address;
		}
	}

	@Override
	public String getCustomer_addressById(long cust_id) throws SQLException, ClassNotFoundException {

		String sql = "select id,customerId,type_billing_shipping,firstName,lastName,address1,address2,address3,city,state,country,phoneNumber,IsPrimary,createdDatetime from address where customerId=?";
		List<JSONObject> cust_address = jdbcTemplate.query(sql, new addressRowMapper(), cust_id);
		if (cust_address.isEmpty()) {

			return "Address not Found!";
		} else {
			String Str_Customer_address_by_id = cust_address.toString().substring(1,
					cust_address.toString().length() - 1);
			return Str_Customer_address_by_id;
		}
	}

	@Override
	public String removeCustomer_addressById(shopNoweventaddress c1) {
		long customerId = c1.getCustomerId();
		long id = c1.getId();
		JSONObject jo_customerAddress_Delete = new JSONObject();
		String Str_msg = null;

		final String sql = "DELETE FROM address WHERE customerId ='" + customerId + "' and id='" + id + "'";
		int i = jdbcTemplate.update(sql);

		if (i > 0) {
			Str_msg = "Record deleted successfully";
			jo_customerAddress_Delete.put("status", "1");
			jo_customerAddress_Delete.put("message", Str_msg);

		}

		else {
			Str_msg = "Record not Found";
			jo_customerAddress_Delete.put("status", "0");
			jo_customerAddress_Delete.put("message", Str_msg);

		}
		return jo_customerAddress_Delete.toString();

	}

	@Override
	public void updateCustomer_addressById(shopNoweventaddress cust_add) {

		final String sql_update_address = "UPDATE address SET customerId=?,type_billing_shipping=?,firstName=?,lastName=?,address1=?,address2=?,address3=?,city=?,state=?,country=?,phoneNumber=?,isPrimary=?,createdDatetime=? WHERE id=?";
		System.out.println(sql_update_address);
		final long id = cust_add.getId();
		final long customerId = cust_add.getCustomerId();
		final String type_billing_shipping = cust_add.getType_billing_shipping();
		final String lastName = cust_add.getLastName();
		final String firstName = cust_add.getFirstName();
		final String address1 = cust_add.getAddress1();
		final String address2 = cust_add.getAddress2();
		final String address3 = cust_add.getAddress3();
		final String city = cust_add.getCity();
		final String state = cust_add.getState();
		final String country = cust_add.getCountry();
		final String phoneNumber = cust_add.getPhoneNumber();
		final long isPrimary = cust_add.getIsPrimary();
		final Date createdDatetime = cust_add.getCreatedDatetime();

		jdbcTemplate.update(sql_update_address, new Object[] { customerId, type_billing_shipping, firstName, lastName,
				address1, address2, address3, city, state, country, phoneNumber, isPrimary, createdDatetime, id });
	}

	@Override
	public String insertCustomer_addressToDb(shopNoweventaddress cust_add) {

		final String sql_insert_address = "INSERT INTO address (customerId,type_billing_shipping,firstName,lastName,address1,address2,address3,city,state,country,phoneNumber)VALUES(?,?,?,?,?,?,?,?,?,?,?)";

		final long customerId = cust_add.getCustomerId();
		final String type_billing_shipping = cust_add.getType_billing_shipping();
		final String lastName = cust_add.getLastName();
		final String firstName = cust_add.getFirstName();
		final String address1 = cust_add.getAddress1();
		final String address2 = cust_add.getAddress2();
		final String address3 = cust_add.getAddress3();
		final String city = cust_add.getCity();
		final String state = cust_add.getState();
		final String country = cust_add.getCountry();
		final String phoneNumber = cust_add.getPhoneNumber();

		String Str_msg = null;
		JSONObject jo_AddressInsert = new JSONObject();

		int i = jdbcTemplate.update(sql_insert_address, new Object[] { customerId, type_billing_shipping, firstName,
				lastName, address1, address2, address3, city, state, country, phoneNumber });

		if (i > 0) {
			Str_msg = "Record inserted successfully";
			jo_AddressInsert.put("status", "1");
			jo_AddressInsert.put("message", Str_msg);

		}

		else {
			Str_msg = "Record not Inserted";
			jo_AddressInsert.put("status", "0");
			jo_AddressInsert.put("message", Str_msg);

		}
		return jo_AddressInsert.toString();

	}

	// ---------------------------------------------------------- shop now-category
	// -------------------------------------------------------------
	private static class CategorynewRowMapper implements RowMapper<JSONObject> {

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONArray ja_category = new JSONArray();
			JSONObject jo_category_result = new JSONObject();

			do {
				JSONObject jo_category = new JSONObject();
				jo_category.put("id", rs.getLong("id"));
				jo_category.put("name", rs.getString("name"));
				jo_category.put("image", rs.getString("image"));
				ja_category.put(jo_category);

			} while (rs.next());

			jo_category_result.put("categories", ja_category);

			return jo_category_result;

		}
	}

	@Override
	public String getAllCategorynew() {
		final String sql_Category_All = "SELECT * FROM categories";
		List<JSONObject> cat1 = jdbcTemplate.query(sql_Category_All, new CategorynewRowMapper());
		String str_Category_All = cat1.toString().substring(1, cat1.toString().length() - 1);
		return str_Category_All;
	}

	@Override
	public String getCategorynewById(long id) {

		String sql_Category_ById = "SELECT * FROM categories WHERE parent_id=?";
		String Str_CategoryByid;
		String sql_Category_ById1 = "CALL shop_category(" + id + ")";

		List<JSONObject> cat1 = jdbcTemplate.query(sql_Category_ById, new CategorynewRowMapper(), id);

		if (cat1.isEmpty()) {

			Str_CategoryByid = "Category not found!";

		} else {
			Str_CategoryByid = cat1.toString().substring(1, cat1.toString().length() - 1);
		}

		return Str_CategoryByid;
	}

	// --------------------------------------------------------------------
	// Product_LIS
	// Table----------------------------------------------------------------------------

	private static class Products_listRowMapper implements RowMapper<JSONObject> {

		JSONArray ja_product_list = new JSONArray();
		JSONObject jo_product_list_result = new JSONObject();

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			do {
				JSONObject jo_product_list = new JSONObject();
				jo_product_list.put("id", rs.getString("id"));

				jo_product_list.put("wishlist", rs.getString("wishlist"));
				jo_product_list.put("name", rs.getString("name"));
				jo_product_list.put("description", rs.getString("description"));
				jo_product_list.put("regular_price", rs.getFloat("regular_price"));
				jo_product_list.put("sale_price", rs.getFloat("sale_price"));
				jo_product_list.put("stock", rs.getString("stock"));
				jo_product_list.put("image", rs.getString("image"));
				jo_product_list.put("total", rs.getInt("total"));
				String image_url = rs.getString("image1");

				JSONObject joimage = new JSONObject();
				JSONArray jaimage = new JSONArray();

				String[] image_url1 = null;
				if (image_url == null) {

					joimage.put("image", "NA");
					jaimage.put(joimage);
				} else {
					image_url1 = image_url.split(",");

					for (int k = 0; k < image_url1.length; k++) {
						JSONObject joimage1 = new JSONObject();
						joimage1.put("image", image_url1[k]);
						jaimage.put(joimage1);
					}

				}
				jo_product_list.put("image_extra", jaimage);
				ja_product_list.put(jo_product_list);

			} while (rs.next());

			jo_product_list_result.put("products", ja_product_list);

			return jo_product_list_result;

		}
	}

	@Override
	public String getProducts_list() throws SQLException {

		final String sql = "SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
				+ "GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id limit 10";

		List<JSONObject> product_list = jdbcTemplate.query(sql, new Products_listRowMapper());
		String Str_product_list = product_list.toString().substring(1, product_list.toString().length() - 1);
		return Str_product_list;
	}

	@Override
	public String getProducts_listById(Products_list_event product_list) throws SQLException, ClassNotFoundException {

		final long id = product_list.getId();

		int page_number = product_list.getPage_number();
		int page_size = 50;
		if (page_number == 0) {
			page_number = 1;

		}

		int page_offset = (page_number - 1) * page_size;

		final long customerId = product_list.getCustomerId();
		float min_price = product_list.getMin_price();
		float max_price = product_list.getMax_price();
		String sql4 = "", sql5 = "";

		String search = product_list.getSearch();
		if (search == null) {
			search = "";
		}

		if (min_price == 0) {
			min_price = 0;
		}

		String orderBy = product_list.getOrderBy();
		int flagChange = 0;
		String order = "ASC";

		if (orderBy == null) {
			order = "ASC";
			flagChange = 0;
		} else if (orderBy.equalsIgnoreCase("priceup")) {
			order = "ASC";
			flagChange = 1;
		} else if (orderBy.equalsIgnoreCase("pricedown")) {
			order = "DESC";
			flagChange = 1;
		} else {
			order = "ASC";
			flagChange = 0;

		}

		// =====================================================================================================================================

		if (flagChange == 0) {
			if (id == 0) {
				if (max_price == 0) {
					if (customerId == 0) {
						System.out.println("flagChange=0,id=0 max = 0 customer_id=0");

						sql4 = "SELECT table1.*,t1.c AS total,0 AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  NAME LIKE '%" + search
								+ "%' AND sale_price >" + min_price + " GROUP BY id " + order + " limit " + page_offset
								+ "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "SELECT * FROM(\n"
								+ "SELECT t1.*,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM(\n"
								+ "SELECT p1.*,p2.total FROM products AS p1  LEFT JOIN (SELECT category_id,COUNT(6) AS total FROM products GROUP BY category_id) AS p2 ON p1.category_id=p2.category_id WHERE p1.name LIKE '%"
								+ search + "%' AND p1.sale_price>" + min_price + ") AS t1\n"
								+ "LEFT JOIN (SELECT * FROM wish_list WHERE customer_id=113)AS wish_list1 ON t1.id=wish_list1.product_id)AS table2\n"
								+ "LEFT JOIN\n"
								+ "(SELECT product_id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY product_id)AS table3 ON table2.id=table3.product_id  LIMIT 0,100";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";

						

					} else {
						System.out.println("flagChange=0,id=0 max = 0 customer_id=1");

						sql4 = "SELECT table1.*,t1.c AS total,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END) AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  NAME LIKE '%" + search
								+ "%' AND sale_price >" + min_price + " GROUP BY id " + order + " limit " + page_offset
								+ "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT product_id FROM wish_list WHERE customer_id=" + customerId
								+ ") AS wish_list1 ON wish_list1.product_id=table1.id\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";
						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";

						
					}
				} else {
					if (customerId == 0) {
						System.out.println("flagChange=0,id=0 max = 1 customer_id=0");

						sql4 = "SELECT table1.*,t1.c AS total,0 AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE NAME LIKE '%" + search
								+ "%' AND sale_price >" + min_price + " and sale_price<=" + max_price + " GROUP BY id "
								+ order + " limit " + page_offset + "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);

					} else {
						System.out.println("flagChange=0,id=0 max = 1 customer_id=1");

						sql4 = "SELECT table1.*,t1.c AS total,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END) AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  NAME LIKE '%" + search
								+ "%' AND sale_price >" + min_price + " and sale_price<=" + max_price + " GROUP BY id "
								+ order + " limit " + page_offset + "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT product_id FROM wish_list WHERE customer_id=" + customerId
								+ ") AS wish_list1 ON wish_list1.product_id=table1.id\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);

					}

				}
			} else {

				if (max_price == 0) {
					if (customerId == 0) {
						System.out.println("flagChange=0,id=1 max = 0 customer_id=0");

						sql4 = "SELECT table1.*,t1.c AS total,0 AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  category_id=" + id
								+ " AND NAME LIKE '%" + search + "%' AND sale_price >" + min_price + " GROUP BY id "
								+ order + " limit " + page_offset + "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);

					} else {
						System.out.println("flagChange=0,id=0 max = 0 customer_id=1");

						sql4 = "SELECT table1.*,t1.c AS total,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END) AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id Having  category_id=" + id
								+ " AND NAME LIKE '%" + search + "%' AND sale_price >" + min_price + " GROUP BY id "
								+ order + " limit " + page_offset + "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT product_id FROM wish_list WHERE customer_id=" + customerId
								+ ") AS wish_list1 ON wish_list1.product_id=table1.id\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);
					}
				} else {
					if (customerId == 0) {
						System.out.println("flagChange=0,id=0 max = 1 customer_id=0");

						sql4 = "SELECT table1.*,t1.c AS total,0 AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  category_id=" + id
								+ " AND NAME LIKE '%" + search + "%' AND sale_price >" + min_price + " and sale_price<="
								+ max_price + " GROUP BY id " + order + " limit " + page_offset + "," + page_size
								+ ")AS table1\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);

					} else {
						System.out.println("flagChange=0,id=1 max = 1 customer_id=1");

						sql4 = "SELECT table1.*,t1.c AS total,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END) AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  category_id=" + id
								+ " AND NAME LIKE '%" + search + "%' AND sale_price >" + min_price + " and sale_price<="
								+ max_price + " GROUP BY id " + order + " limit " + page_offset + "," + page_size
								+ ")AS table1\n" + "LEFT JOIN (SELECT product_id FROM wish_list WHERE customer_id="
								+ customerId + ") AS wish_list1 ON wish_list1.product_id=table1.id\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);

					}

				}

			}
		}

		else {

			if (id == 0) {
				if (max_price == 0) {
					if (customerId == 0) {
						System.out.println("flagChange=1,id=0 max = 0 customer_id=0");

						sql4 = "SELECT table1.*,t1.c AS total,0 AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  NAME LIKE '%" + search
								+ "%' AND sale_price >" + min_price + " GROUP BY id order by sale_price " + order
								+ " limit " + page_offset + "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "SELECT * FROM(\n"
								+ "SELECT t1.*,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM(\n"
								+ "SELECT p1.*,p2.total FROM products AS p1  LEFT JOIN (SELECT category_id,COUNT(6) AS total FROM products GROUP BY category_id) AS p2 ON p1.category_id=p2.category_id WHERE p1.name LIKE '%"
								+ search + "%' AND p1.sale_price>" + min_price + ") AS t1\n"
								+ "LEFT JOIN (SELECT * FROM wish_list WHERE customer_id=113)AS wish_list1 ON t1.id=wish_list1.product_id)AS table2\n"
								+ "LEFT JOIN\n"
								+ "(SELECT product_id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY product_id)AS table3 ON table2.id=table3.product_id  LIMIT 0,100";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";

						System.out.println(sql4);

					} else {
						System.out.println("flagChange=1,id=0 max = 0 customer_id=1");

						sql4 = "SELECT table1.*,t1.c AS total,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END) AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  NAME LIKE '%" + search
								+ "%' AND sale_price >" + min_price + " GROUP BY id order by sale_price " + order
								+ " limit " + page_offset + "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT product_id FROM wish_list WHERE customer_id=" + customerId
								+ ") AS wish_list1 ON wish_list1.product_id=table1.id\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);
					}
				} else {
					if (customerId == 0) {
						System.out.println("flagChange=1,id=0 max = 1 customer_id=0");

						sql4 = "SELECT table1.*,t1.c AS total,0 AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE NAME LIKE '%" + search
								+ "%' AND sale_price >" + min_price + " and sale_price<=" + max_price
								+ " GROUP BY id order by sale_price " + order + " limit " + page_offset + ","
								+ page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);

					} else {
						System.out.println("flagChange=1,id=0 max = 1 customer_id=1");

						sql4 = "SELECT table1.*,t1.c AS total,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END) AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  NAME LIKE '%" + search
								+ "%' AND sale_price >" + min_price + " and sale_price<=" + max_price
								+ " GROUP BY id order by sale_price " + order + " limit " + page_offset + ","
								+ page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT product_id FROM wish_list WHERE customer_id=" + customerId
								+ ") AS wish_list1 ON wish_list1.product_id=table1.id\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);

					}

				}
			} else {

				if (max_price == 0) {
					if (customerId == 0) {
						System.out.println("flagChange=1,id=1 max = 0 customer_id=0");

						sql4 = "SELECT table1.*,t1.c AS total,0 AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  category_id=" + id
								+ " AND NAME LIKE '%" + search + "%' AND sale_price >" + min_price
								+ " GROUP BY id order by sale_price " + order + " limit " + page_offset + ","
								+ page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";

						System.out.println(sql5);

						System.out.println(sql4);

					} else {
						System.out.println("flagChange=1,id=1 max = 0 customer_id=1");

						sql4 = "SELECT table1.*,t1.c AS total,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END) AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  category_id=" + id
								+ " AND NAME LIKE '%" + search + "%' AND sale_price >" + min_price
								+ " GROUP BY id order by sale_price " + order + " limit " + page_offset + ","
								+ page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT product_id FROM wish_list WHERE customer_id=" + customerId
								+ ") AS wish_list1 ON wish_list1.product_id=table1.id\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);
					}
				} else {
					if (customerId == 0) {
						System.out.println("flagChange=1,id=1 max = 1 customer_id=0");

						sql4 = "SELECT table1.*,t1.c AS total,0 AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  category_id=" + id
								+ " AND NAME LIKE '%" + search + "%' AND sale_price >" + min_price
								+ "  and sale_price<=" + max_price + " GROUP BY id order by sale_price " + order
								+ " limit " + page_offset + "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);

					} else {
						System.out.println("flagChange=1,id=1 max = 1 customer_id=1");

						sql4 = "SELECT table1.*,t1.c AS total,(CASE WHEN wish_list1.product_id IS NOT NULL THEN 1 ELSE 0 END) AS wishlist FROM\n"
								+ "(SELECT p1.*,GROUP_CONCAT(im.image) AS image1 FROM products AS p1\n"
								+ "LEFT JOIN product_image AS im ON p1.id=im.product_id WHERE  category_id=" + id
								+ " AND NAME LIKE '%" + search + "%' AND sale_price >" + min_price + " and sale_price<="
								+ max_price + " GROUP BY id order by sale_price " + order + " limit " + page_offset
								+ "," + page_size + ")AS table1\n"
								+ "LEFT JOIN (SELECT product_id FROM wish_list WHERE customer_id=" + customerId
								+ ") AS wish_list1 ON wish_list1.product_id=table1.id\n"
								+ "LEFT JOIN (SELECT category_id,COUNT(6) AS c FROM products GROUP BY category_id) AS t1 ON t1.category_id=table1.category_id";

						sql5 = "CALL product_list(" + flagChange + "," + customerId + "," + id + ",'DESC',"
								+ page_offset + "," + min_price + "," + max_price + ")";
						System.out.println(sql4);

					}

				}

			}

		}

		// ========================================================================================================================================

		List<JSONObject> product_list_ob = jdbcTemplate.query(sql4, new Products_listRowMapper());

		if (product_list_ob.isEmpty()) {

			return "products not found!";

		} else {

			String product_list_str = product_list_ob.toString().substring(1, product_list_ob.toString().length() - 1);

			return product_list_str;
		}
	}

	// ---------------------------------------- THIS SQLIMPLIMANTETION FOr
	// PRODUCT_Detail TABLE---------------------------------------------------

	private static class Product_detailRowMapper implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int i) throws SQLException {

			JSONArray ja = new JSONArray();

			JSONObject final_product = new JSONObject();
			JSONObject joimage = new JSONObject();

			do {
				JSONObject jo_product_detail = new JSONObject();

				jo_product_detail.put("id", rs.getString("id"));
				jo_product_detail.put("name", rs.getString("name"));
				jo_product_detail.put("description", rs.getString("description"));
				jo_product_detail.put("regular_price", rs.getFloat("regular_price"));
				jo_product_detail.put("sale_price", rs.getFloat("sale_price"));
				jo_product_detail.put("stock", rs.getString("stock"));
				jo_product_detail.put("product_url", rs.getString("product_url"));
				joimage.put("image", rs.getString("image"));

				JSONArray jaimage = new JSONArray();

				jaimage.put(joimage);
				jo_product_detail.put("Image", jaimage);

				jo_product_detail.put("category", rs.getString("category"));

				ja.put(jo_product_detail);

				String similar_products_id = rs.getString("similar_product_id");
				String[] similar_product_array = {};
				if (similar_products_id != null) {
					similar_product_array = similar_products_id.split(",");
				}

				String recommended_product_id = rs.getString("recommended_product_id");

				String[] recommended_product_id_array = {};
				if (recommended_product_id != null) {
					recommended_product_id_array = recommended_product_id.split(",");
				}

				String vendor_id = rs.getString("vendor_id");

				String[] vendor_id_array = {};
				if (vendor_id != null) {
					vendor_id_array = vendor_id.split(",");
				}

				final_product.put("product_detail", ja);
				final_product.put("Similar Products", similar_product_array);
				final_product.put("Recommended Products", recommended_product_id_array);
				final_product.put("vendors", vendor_id_array);

			} while (rs.next());

			return final_product.toString();

		}
	}

	private static class Product_detailRowMapper_att implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject final_product = new JSONObject();

			String attribute_value = rs.getString("attribute_value");
			String[] attribute_value1 = {};
			if (attribute_value != null) {
				attribute_value1 = attribute_value.split(",");
			}

			final_product.put("attribute", attribute_value1);

			return final_product.toString();

		}
	}

	private static class Product_detailRowMapper_option implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject final_product = new JSONObject();

			String product_option = rs.getString("product_option");
			String[] product_option_array = {};
			if (product_option != null) {
				product_option_array = product_option.split(",");
			}

			final_product.put("option", product_option_array);

			return final_product.toString();

		}
	}

	@Override
	public String getProduct_detailById(long id) throws SQLException {

		final String sql_product = "SELECT p.id,p.name,p.description,p.regular_price,p.sale_price,p.stock,categories.name AS category,p.image,p.product_url,p.similar_product_id,p.recommended_product_id,p.vendor_id FROM products AS p INNER JOIN  categories ON p.category_id=categories.id WHERE p.id=?";

		String sql_product_att = "SELECT pa.product_id,GROUP_CONCAT(att_group_name,'\":\"',av.att_value) AS attribute_value,pa.price_change AS price,pa.product_url FROM product_attributes pa  \n"
				+ "INNER JOIN\n"
				+ "attributes_value av ON av.id=pa.att_group_val_id INNER JOIN attributes a ON a.id=pa.att_group_id  where pa.product_id=?";

		String sql_product_option = "SELECT table1.product_id,GROUP_CONCAT(table1.option_name,'\":\"',product_option_value.value) AS product_option FROM(\n"
				+ "SELECT product_options.id,product_options.product_id,product_option_group.option_name FROM product_options INNER JOIN  product_option_group ON product_options.id=product_option_group.id WHERE product_options.product_id=16) AS table1\n"
				+ "INNER JOIN\n"
				+ "product_option_value ON table1.id=product_option_value.id WHERE table1.product_id=?";

		String sql_product1 = "CALL product_detail(" + id + ")";
		String sql_product_att1 = "CALL product_detail2(" + id + ")";
		String sql_product_option1 = "CALL product_detail_3(" + id + ")";

		List<String> pd1 = jdbcTemplate.query(sql_product, new Product_detailRowMapper(), id);

		List<String> patt = jdbcTemplate.query(sql_product_att, new Product_detailRowMapper_att(), id);

		List<String> poption = jdbcTemplate.query(sql_product_option, new Product_detailRowMapper_option(), id);

		String Str_product_detail = null;
		String Str_product_option = null;
		String Str_product_att = null;
		String sf;

		if (pd1.isEmpty()) {

			sf = "Product not found!";

		} else {

			Str_product_detail = pd1.toString().substring(1, pd1.toString().length() - 2);

			if (patt != null) {

				Str_product_att = patt.toString().substring(2, patt.toString().length() - 2);
			}

			if (poption != null) {
				Str_product_option = poption.toString().substring(2, poption.toString().length() - 1);
			}

			sf = Str_product_detail + "," + Str_product_att + "," + Str_product_option;

		}

		return sf;

	}

	// ---------------------------------------------------------------customer_list-----------------------------------------------------------------------------

	private static class CustomerRowMapper implements RowMapper<JSONObject> {

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject jo_customer = new JSONObject();

			jo_customer.put("first_name", rs.getString("first_name"));
			jo_customer.put("last_name", rs.getString("last_name"));
			jo_customer.put("email", rs.getString("email"));
			jo_customer.put("phone_number", rs.getInt("phone_number"));
			jo_customer.put("photo", rs.getString("image"));

			return jo_customer;

		}

	}

	@Override
	public String getAllCustomer() {

		final String sql1 = "SELECT id,first_name,last_name,email,phone_number,image FROM customers LIMIT 10";
		List<JSONObject> customer = jdbcTemplate.query(sql1, new CustomerRowMapper());
		String s = customer.toString().substring(1, customer.toString().length() - 1);
		return s;

	}

	@Override
	public String getCustomerById(long id) {

		String sql = " SELECT id, first_name,last_name,email,phone_number,image FROM customers where id=?";

		String sql1 = "call customer_list(" + id + ")";
		List<JSONObject> customer_list = jdbcTemplate.query(sql, new CustomerRowMapper(), id);
		// List<JSONObject> customer_list = jdbcTemplate.query(sql1, new
		// CustomerRowMapper());

		if (customer_list.isEmpty()) {
			return "customer not found";
		} else {
			String str_customer = customer_list.toString().substring(1, customer_list.toString().length() - 1);

			return str_customer;
		}
	}

	// ----------------------------------------------------
	// shop_now-Deal_Detail-----------------------------------------------------------------------------------
	private static class Deal_detailRowMapper implements RowMapper<JSONObject> {

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject jo_customer_dealDetail_result = new JSONObject();
			JSONArray json_array_deal = new JSONArray();
			do {
				JSONObject jo_customer_dealDetail = new JSONObject();

				jo_customer_dealDetail.put("id", rs.getString("id"));
				jo_customer_dealDetail.put("name", rs.getString("name"));
				jo_customer_dealDetail.put("regular_price", rs.getFloat("regular_price"));
				jo_customer_dealDetail.put("sale_price", rs.getFloat("sale_price"));
				jo_customer_dealDetail.put("stock", rs.getString("stock"));
				jo_customer_dealDetail.put("image", rs.getString("image"));

				json_array_deal.put(jo_customer_dealDetail);

				// ---------------------------code for time
				// remaining---------------------------------------------------------------------------------

				SimpleDateFormat f1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				String s1 = null;
				Date d2, d3 = null;

				String dateStop = rs.getString("end_date");
				String dateStop1 = dateStop.substring(0, dateStop.length() - 3);
				long dateStop2 = Long.parseLong(dateStop1);
				String edate = f1.format(new java.util.Date(dateStop2 * 1000));

				String current = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

				try {
					d2 = f1.parse(edate);
					d3 = f1.parse(current);

					// in milliseconds
					long diff = d2.getTime() - d3.getTime();

					if (diff > 0) {

						long diffSeconds = diff / 1000 % 60;
						long diffMinutes = diff / (60 * 1000) % 60;
						long diffHours = diff / (60 * 60 * 1000) % 24;
						long diffDays = diff / (24 * 60 * 60 * 1000);

						s1 = Long.toString(diffDays) + " days " + Long.toString(diffHours) + " hours "
								+ Long.toString(diffMinutes) + " minutes " + Long.toString(diffSeconds) + " seconds ";

						jo_customer_dealDetail.put("time_remaining", s1);
					} else {
						// System.out.println("deal is over");
						jo_customer_dealDetail.put("time_remaining", "deal is over");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				// --------------------------------code end of time
				// remaining------------------------------------------------------------------

			} while (rs.next());

			jo_customer_dealDetail_result.put("products", json_array_deal);

			return jo_customer_dealDetail_result;

		}
	}

	@Override
	public String getAllDeal_detail() {

		final String sql1 = "SELECT p.id,p.name,p.regular_price,p.sale_price,p.stock,p.image,deals.start_date,deals.end_date FROM products AS p INNER JOIN deal_detail AS d ON p.id=d.product_id  INNER JOIN deals ON d.deal_id=deals.id";

		List<JSONObject> product_Deal = jdbcTemplate.query(sql1, new Deal_detailRowMapper());
		if (product_Deal.isEmpty()) {
			return "Deal Not Found!";
		} else
			return product_Deal.toString();

	}

	@Override
	public String getDeal_detailById(long id) {
		String sql_Deal = "SELECT p.id,p.name,p.regular_price,p.sale_price,p.stock,p.image,deals.start_date,deals.end_date FROM products AS p INNER JOIN deal_detail AS d ON p.id=d.product_id  INNER JOIN deals ON d.deal_id=deals.id WHERE deals.id=?";
		List<JSONObject> product_deal = jdbcTemplate.query(sql_Deal, new Deal_detailRowMapper(), id);
		if (product_deal.isEmpty()) {
			return "Deal Not Found!";
		} else {
			String str_deal = product_deal.toString().substring(1, product_deal.toString().length() - 1);
			return str_deal;
		}
	}

	// ------------------------------------------------------------------------------------WISH_LIST--------------------------------------------------------------------------------------
	@PropertySource(value = { "classpath:application.properties" })
	private static class shopNoweventWish_list_RowMapper implements RowMapper<JSONObject> {

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject jo_wishList_result = new JSONObject();
			JSONArray json_array_wishlist = new JSONArray();
			do {

				JSONObject jo_wishList = new JSONObject();

				jo_wishList.put("product_id", rs.getString("id"));
				jo_wishList.put("product_name", rs.getString("name"));
				jo_wishList.put("regular_price", rs.getFloat("regular_price"));
				jo_wishList.put("sale_price", rs.getFloat("sale_price"));
				// jo.put("customer_id",rs.getInt("customer_id"));
				jo_wishList.put("image", rs.getString("image"));
				json_array_wishlist.put(jo_wishList);
			} while (rs.next());

			jo_wishList_result.put("wishlist", json_array_wishlist);

			return jo_wishList_result;

		}
	}
	// ------------------------------------------------------------------------ WISH
	// LIST TABLE VIEW
	// DATA--------------------------------------------------------------------------------------------------

	@Override
	public String getwish_listByCust_Id(long id) {
		// SELECT column_name(s) FROM table_name where column = value

		String sql = "SELECT pa.id,pa.name,pa.regular_price,pa.sale_price,pa.image,wish_list.customer_id FROM products AS pa INNER JOIN wish_list ON wish_list.product_id=pa.id WHERE wish_list.customer_id=?";

		List<JSONObject> wish_list = jdbcTemplate.query(sql, new shopNoweventWish_list_RowMapper(), id);

		if (wish_list.isEmpty()) {
			return "wishlist for this customer id not found!";

		} else {

			String str_WishListView = wish_list.toString().substring(1, wish_list.toString().length() - 1);
			return str_WishListView;
		}
	}

	// ---------------------------------------------------------------INSERT RECORD
	// INTO WISHLIST
	// TABLE-----------------------------------------------------------------------------------------------------

	@Override
	public String insert_wish_listToDb(shopNoweventWish_list wl1) throws SQLException, ClassNotFoundException {

		final String sql = "INSERT INTO wish_list (customer_id,product_id)VALUES(?,?)";

		final long customerId = wl1.getCustid();
		final long product_id = wl1.getId();

		String strMsg = null;
		JSONObject jo_InsertWishList_Result = new JSONObject();

		SqlRowSet srs_customer = jdbcTemplate.queryForRowSet("SELECT id FROM customers where id='" + customerId + "'");
		SqlRowSet srs_products = jdbcTemplate.queryForRowSet("SELECT id FROM products where id='" + product_id + "'");

		if (srs_customer.next() && srs_products.next()) {

			SqlRowSet srs_wishlist = jdbcTemplate
					.queryForRowSet("SELECT customer_id,product_id FROM wish_list where customer_id='" + customerId
							+ "' and product_id='" + product_id + "'");

			if (srs_wishlist.next()) {

				strMsg = "customerId and productId Allredy presant in wishlist";
				jo_InsertWishList_Result.put("status", "0");
				jo_InsertWishList_Result.put("message", strMsg);

			}

			else {
				jdbcTemplate.update(sql, new Object[] { customerId, product_id });

				strMsg = "insert product in wish_list sussesfully";
				jo_InsertWishList_Result.put("status", "1");
				jo_InsertWishList_Result.put("message", strMsg);
			}
		} else {

			return "customer OR product not registed !";
		}
		return jo_InsertWishList_Result.toString();
	}

	// --------------------------------------------------------------DELETE RECORD
	// FROM WISH
	// LIST-------------------------------------------------------------------------------------------
	@Override

	public String delete_wish_listToDb(shopNoweventWish_list wl1) throws SQLException, ClassNotFoundException {

		final String sql = "delete from wish_list where customer_id=? and product_id=?";
		final long customerId = wl1.getCustid();
		final long product_id = wl1.getId();
		String str_Msg = null;

		SqlRowSet srs_wishlist_customer = jdbcTemplate.queryForRowSet(
				"SELECT id FROM wish_list where customer_id='" + customerId + "' and product_id='" + product_id + "'");

		JSONObject jo_wishListDelete_Result = new JSONObject();

		if (srs_wishlist_customer.next()) {
			jdbcTemplate.update(sql, new Object[] { customerId, product_id });
			str_Msg = "delete product in wish_list sussesfully";
			jo_wishListDelete_Result.put("status", "1");
			jo_wishListDelete_Result.put("message", str_Msg);
		} else {

			str_Msg = "enter valid customerId and productId";
			jo_wishListDelete_Result.put("status", "0");
			jo_wishListDelete_Result.put("message", str_Msg);

		}
		return jo_wishListDelete_Result.toString();
	}

	// -----------------------------------------------------------------------------Login
	// methods----------------------------------------------------------------

	@Override
	public String getcustomer_By_emailAnd_psw(shopNoweventCustomer_list a1)
			throws ClassNotFoundException, SQLException {
		// SELECT column_name(s) FROM table_name
		final String email = a1.getEmail();
		final String psw = a1.getPassword();
		JSONObject jo_login_result = new JSONObject();
		String strMsg;

		if (email == "" || email == null) {
			if (psw == "" || psw == null) {

				strMsg = "Email-Id and Password cannot be empty.";
				jo_login_result.put("status", "0");
				jo_login_result.put("message", strMsg);

			} else {

				strMsg = "Email-Id cannot be empty";
				jo_login_result.put("status", "0");
				jo_login_result.put("message", strMsg);

			}

		} else if (psw == "" || psw == null) {

			strMsg = "Password cannot be empty";
			jo_login_result.put("status", "0");
			jo_login_result.put("message", strMsg);

		} else {

			SqlRowSet srs_customer_login = jdbcTemplate
					.queryForRowSet("SELECT id FROM customers where email='" + email + "'and password='" + psw + "'");

			if (srs_customer_login.next()) {

				strMsg = "Login Sucessfull";
				jo_login_result.put("status", "1");
				jo_login_result.put("message", strMsg);
				jo_login_result.put("id", srs_customer_login.getLong("id"));
			} else {

				strMsg = "Incorrect Email-Id or Password";
				jo_login_result.put("status", "0");
				jo_login_result.put("message", strMsg);
			}
		}
		return jo_login_result.toString();

	}

	// -------------------------------------------------------------- ------CHANGE
	// PASSWORD------------------------------------------------------------------------------------------

	@Override
	public String updateCustomer_list_pass(shopNoweventCustomer_list c1) throws ClassNotFoundException, SQLException {

		final String email = c1.getEmail();
		final String cust_psw = c1.getpassword();
		String newpsw = c1.getnewpsw();
		int lentgth = newpsw.length();
		String str_msg = null;
		JSONObject jo_change_psw = new JSONObject();
		int i;

		final String sql = "update customers set password='" + newpsw + "' where email='" + email + "' and password='"
				+ cust_psw + "'";

		i = jdbcTemplate.update(sql);

		if (i > 0 && lentgth > 1) {
			str_msg = "password update sussesfully";
			jo_change_psw.put("status", "1");
			jo_change_psw.put("message", str_msg);

		}

		else {
			str_msg = "password  not update fail";
			jo_change_psw.put("status", "0");
			jo_change_psw.put("message", str_msg);

		}

		return jo_change_psw.toString();

	}

	// -------------------------------------------------------------- insert into
	// register Table
	// ---------------------------------------------------------------------------------

	@Override
	public String insertCustomer_listDao(shopNoweventCustomer_list c1) throws ClassNotFoundException, SQLException {

		final String sql = "INSERT INTO customers(email,password)VALUES(?,?)";
		final String customermail = c1.getEmail();
		final String cust_psw = c1.getpassword();
		JSONObject jo_Register_customer_result = new JSONObject();
		System.out.println(customermail);
		String strMsg;

		if (customermail == "" || customermail == null) {
			if (cust_psw == "" || cust_psw == null) {

				strMsg = "Email-id and Passowrd cannot be empty";
				jo_Register_customer_result.put("status", "0");
				jo_Register_customer_result.put("message", strMsg);
				System.out.println("null");
			} else {
				strMsg = "Email-Id cannot be empty";
				jo_Register_customer_result.put("status", "0");
				jo_Register_customer_result.put("message", strMsg);
				System.out.println(strMsg);

			}
		} else if (cust_psw == "" || cust_psw == null) {

			strMsg = "Password cannot be empty";
			jo_Register_customer_result.put("status", "0");
			jo_Register_customer_result.put("message", strMsg);
			System.out.println(strMsg);

		}

		else {

			SqlRowSet srs_customer_register = jdbcTemplate
					.queryForRowSet("SELECT email FROM customers where email='" + customermail + "'");

			if (srs_customer_register.next()) {
				strMsg = "User already exists with this email-id";
				jo_Register_customer_result.put("status", "0");
				jo_Register_customer_result.put("message", strMsg);

			} else {
				jdbcTemplate.update(sql, new Object[] { customermail, cust_psw });
				SqlRowSet srs_customer_register1 = jdbcTemplate
						.queryForRowSet("SELECT id FROM customers order by id DESC LIMIT 1");

				int id1 = 0;
				if (srs_customer_register1.next()) {
					id1 = srs_customer_register1.getInt("id");

				}

				strMsg = "Registered Customer Successfully";
				jo_Register_customer_result.put("status", "1");
				jo_Register_customer_result.put("message", strMsg);
				jo_Register_customer_result.put("id", id1);

			}

		}

		return jo_Register_customer_result.toString();

	}

	// --------------------------========================Home
	// Web_service==========================------------------------------------------

	private static class shopNowHome_RowMapper implements RowMapper<JSONObject> {

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject jo_Home_Result = new JSONObject();
			JSONArray json_array_home_top_picks = new JSONArray();
			do {

				JSONObject jo_Home_topPiks = new JSONObject();

				jo_Home_topPiks.put("id", rs.getString("id"));
				jo_Home_topPiks.put("name", rs.getString("name"));
				jo_Home_topPiks.put("regular_price", rs.getFloat("regular_price"));
				jo_Home_topPiks.put("sale_price", rs.getFloat("sale_price"));
				jo_Home_topPiks.put("wish_list", rs.getInt("wishlist"));
				jo_Home_topPiks.put("image", rs.getString("image"));
				json_array_home_top_picks.put(jo_Home_topPiks);
			} while (rs.next());

			jo_Home_Result.put("top_picks", json_array_home_top_picks);

			return jo_Home_Result;

		}

	}

	private static class shopNowHome_best_product_RowMapper1 implements RowMapper<JSONObject> {

		@Override

		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject jo_Home_bestProduct_Result = new JSONObject();
			JSONArray json_array_home_top_picks = new JSONArray();
			do {

				JSONObject jo_Home_bestProduct = new JSONObject();

				jo_Home_bestProduct.put("id", rs.getString("id"));
				jo_Home_bestProduct.put("name", rs.getString("name"));
				jo_Home_bestProduct.put("regular_price", rs.getFloat("regular_price"));
				jo_Home_bestProduct.put("sale_price", rs.getFloat("sale_price"));
				jo_Home_bestProduct.put("wish_list", rs.getInt("wishlist"));
				jo_Home_bestProduct.put("image", rs.getString("image"));
				json_array_home_top_picks.put(jo_Home_bestProduct);
			} while (rs.next());

			jo_Home_bestProduct_Result.put("best_product", json_array_home_top_picks);

			return jo_Home_bestProduct_Result;

		}

	}

	private static class shopNowHome_RowMapper_banner implements RowMapper<JSONObject> {

		@Override

		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject jo_banner_Result = new JSONObject();
			JSONArray json_array_home_banner = new JSONArray();
			do {

				JSONObject jo_banner = new JSONObject();

				jo_banner.put("id", rs.getString("id"));
				jo_banner.put("name", rs.getString("name"));
				jo_banner.put("image", rs.getString("image"));
				json_array_home_banner.put(jo_banner);
			} while (rs.next());

			jo_banner_Result.put("banner", json_array_home_banner);

			return jo_banner_Result;

		}

	}

	private static class Home_Deal_detailRowMapper implements RowMapper<JSONObject> {

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject jo_Home_Deal_Result = new JSONObject();
			JSONArray json_array_deal = new JSONArray();
			do {
				JSONObject jo_Home_deal = new JSONObject();

				jo_Home_deal.put("id", rs.getString("id"));
				jo_Home_deal.put("name", rs.getString("name"));
				jo_Home_deal.put("regular_price", rs.getFloat("regular_price"));
				jo_Home_deal.put("sale_price", rs.getFloat("sale_price"));
				jo_Home_deal.put("stock", rs.getString("stock"));
				jo_Home_deal.put("image", rs.getString("image"));

				json_array_deal.put(jo_Home_deal);

				// ---------------------------code for time
				// remaining---------------------------------------------------------------------------------

				SimpleDateFormat f1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				String s1 = null;
				Date d2, d3 = null;

				String dateStop = rs.getString("end_date");
				String dateStop1 = dateStop.substring(0, dateStop.length() - 3);
				long dateStop2 = Long.parseLong(dateStop1);
				String edate = f1.format(new java.util.Date(dateStop2 * 1000));

				String current = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

				try {
					d2 = f1.parse(edate);
					d3 = f1.parse(current);

					// in milliseconds
					long diff = d2.getTime() - d3.getTime();

					if (diff > 0) {

						long diffSeconds = diff / 1000 % 60;
						long diffMinutes = diff / (60 * 1000) % 60;
						long diffHours = diff / (60 * 60 * 1000) % 24;
						long diffDays = diff / (24 * 60 * 60 * 1000);

						s1 = Long.toString(diffDays) + " days " + Long.toString(diffHours) + " hours "
								+ Long.toString(diffMinutes) + " minutes " + Long.toString(diffSeconds) + " seconds ";

						jo_Home_deal.put("time_remaining", s1);
					} else {
						System.out.println("deal is over");
						jo_Home_deal.put("time_remaining", "deal is over");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				// --------------------------------code end of time
				// remaining------------------------------------------------------------------

			} while (rs.next());

			jo_Home_Deal_Result.put("deals", json_array_deal);

			return jo_Home_Deal_Result;

		}
	}

	// ------------------------------------------------------------Home
	// --------------------------------------------------------------------------------------------------

	@Override
	public String getHome() {
		String sql_top_picks = "SELECT table3.* FROM(SELECT products.id,products.name,products.regular_price,products.sale_price,products.image,\n"
				+ " (CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM products\n"
				+ " LEFT JOIN (SELECT * FROM wish_list) AS table2 ON products.id=table2.product_id and customer_id=0) AS table3 INNER JOIN top_picks ON table3.id=top_picks.product_id";

		String sql_top_picks1 = "CALL shopnowHomeTopPicks0()";

		String sql_best = "SELECT table3.* FROM(SELECT products.id,products.name,products.regular_price,products.sale_price,products.image,\n"
				+ " (CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM products\n"
				+ " LEFT JOIN (SELECT * FROM wish_list) AS table2 ON products.id=table2.product_id and customer_id=0) AS table3 INNER JOIN best_product ON table3.id=best_product.product_id";

		String sql_best1 = "CALL shopnowHomebestProduct0()";

		String sql_deal = "SELECT table1.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM(\n"
				+ "SELECT p.id,p.name,p.regular_price,p.sale_price,p.stock,p.image,deals.start_date,deals.end_date FROM products AS p INNER JOIN deal_detail AS d ON p.id=d.product_id  INNER JOIN deals ON d.deal_id=deals.id WHERE (deals.end_date/1000) > UNIX_TIMESTAMP())AS table1 \n"
				+ "INNER JOIN wish_list ON table1.id=wish_list.product_id";

		String sql_deal1 = "call shopnowHomeDeal()";

		String banner_sql = "SELECT id,NAME,image FROM banners WHERE STATUS=1";
		String banner_sql1 = "CALL shopnowHomeBanner()";

		List<JSONObject> home_banners = jdbcTemplate.query(banner_sql, new shopNowHome_RowMapper_banner());

		String banner, Str_home_top, Str_best_product, S_deal;

		if (home_banners.isEmpty()) {
			banner = "{\"banner\":" + home_banners.toString();
		} else {

			banner = home_banners.toString().substring(1, home_banners.toString().length() - 2);
		}

		List<JSONObject> home_top_picks = jdbcTemplate.query(sql_top_picks, new shopNowHome_RowMapper());
		if (home_top_picks.isEmpty()) {
			Str_home_top = "\"top_picks\":" + home_top_picks.toString();
		} else {
			Str_home_top = home_top_picks.toString().substring(2, home_top_picks.toString().length() - 2);
		}

		List<JSONObject> home_best_product = jdbcTemplate.query(sql_best, new shopNowHome_best_product_RowMapper1());
		if (home_best_product.isEmpty()) {
			Str_best_product = "\"best_product\":" + home_best_product.toString();
		} else {
			Str_best_product = home_best_product.toString().substring(2, home_best_product.toString().length() - 1);
		}

		List<JSONObject> product_deal = jdbcTemplate.query(sql_deal, new Home_Deal_detailRowMapper());
		if (product_deal.isEmpty()) {
			S_deal = "\"deal\":" + product_deal.toString();

		} else {

			S_deal = product_deal.toString().substring(2, product_deal.toString().length() - 2);
		}

		StringBuilder str = new StringBuilder();
		str.append(banner).append(',').append(S_deal).append(',').append(Str_home_top).append(',')
				.append(Str_best_product);

		return str.toString();

	}

	@Override
	public String getHomeByCust_Id(long id) {
		// SELECT column_name(s) FROM table_name where column = value

		String sql_top_picks = "SELECT table3.* FROM(SELECT products.id,products.name,products.regular_price,products.sale_price,products.image,\n"
				+ " (CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM products\n"
				+ " LEFT JOIN (SELECT * FROM wish_list WHERE customer_id=?) AS table2 ON products.id=table2.product_id) AS table3 INNER JOIN top_picks ON table3.id=top_picks.product_id";

		String sql_top_picks1 = "CALL shopnowHomeTopPicks(" + id + ")";

		String sql_best = "SELECT table3.* FROM(SELECT products.id,products.name,products.regular_price,products.sale_price,products.image,\n"
				+ " (CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM products\n"
				+ " LEFT JOIN (SELECT * FROM wish_list WHERE customer_id=?) AS table2 ON products.id=table2.product_id) AS table3 INNER JOIN best_product ON table3.id=best_product.product_id ";

		String sql_best1 = "CALL shopnowHomebestProduct(" + id + ")";

		String sql_deal = "SELECT table1.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM(\n"
				+ "SELECT p.id,p.name,p.regular_price,p.sale_price,p.stock,p.image,deals.start_date,deals.end_date FROM products AS p INNER JOIN deal_detail AS d ON p.id=d.product_id  INNER JOIN deals ON d.deal_id=deals.id WHERE (deals.end_date/1000) > UNIX_TIMESTAMP())AS table1 \n"
				+ "INNER JOIN wish_list ON table1.id=wish_list.product_id";

		String sql_deal1 = "call shopnowHomeDeal()";

		String banner_sql = "SELECT id,NAME,image FROM banners";
		String banner_sql1 = "CALL shopnowHomeBanner()";

		List<JSONObject> home_banners = jdbcTemplate.query(banner_sql1, new shopNowHome_RowMapper_banner());

		String banner, Str_home_top, Str_best_product, S_deal;

		if (home_banners.isEmpty()) {
			banner = "{\"banner\":" + home_banners.toString();
		} else {
			banner = home_banners.toString().substring(1, home_banners.toString().length() - 2);
		}

		List<JSONObject> home_top_picks = jdbcTemplate.query(sql_top_picks1, new shopNowHome_RowMapper());
		if (home_top_picks.isEmpty()) {
			Str_home_top = "\"top_picks\":" + home_top_picks.toString();
		} else {
			Str_home_top = home_top_picks.toString().substring(2, home_top_picks.toString().length() - 2);
		}

		List<JSONObject> home_best_product = jdbcTemplate.query(sql_best1, new shopNowHome_best_product_RowMapper1());
		if (home_best_product.isEmpty()) {
			Str_best_product = "\"best_product\":" + home_best_product.toString() + "}";
		} else {
			Str_best_product = home_best_product.toString().substring(2, home_best_product.toString().length() - 1);
		}

		List<JSONObject> product_deal = jdbcTemplate.query(sql_deal1, new Home_Deal_detailRowMapper());
		if (product_deal.isEmpty()) {
			S_deal = "\"deal\":" + product_deal.toString();

		} else {

			S_deal = product_deal.toString().substring(2, product_deal.toString().length() - 2);
		}

		StringBuilder str = new StringBuilder();
		str.append(banner).append(',').append(S_deal).append(',').append(Str_home_top).append(',')
				.append(Str_best_product);

		return str.toString();
	}

	// ==========================================================================================================================================================================

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++filter_api+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// ------------------------------------------------------------------------------------Filter--------------------------------------------------------------------------------------

	private static class shopNowevent_filterRowMapper implements RowMapper<JSONObject> {

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject jo_filter_Result = new JSONObject();
			JSONArray json_array_filter = new JSONArray();
			do {

				JSONObject jo_filter = new JSONObject();

				jo_filter.put("id", rs.getString("id"));
				jo_filter.put("name", rs.getString("name"));
				jo_filter.put("parent_id", rs.getInt("parent_id"));
				jo_filter.put("image", rs.getString("image"));
				json_array_filter.put(jo_filter);
			} while (rs.next());

			jo_filter_Result.put("Categories", json_array_filter);

			return jo_filter_Result;

		}
	}

	@Override
	public String getfilterByCategory_Id(long id) {
		// SELECT column_name(s) FROM table_name where column = value

		String sql = "SELECT p.id,p.name,p.image,categories.parent_id FROM products  AS p INNER JOIN categories ON p.category_id=categories.id WHERE p.category_id=? LIMIT 10";

		String sql_product_att = "SELECT pa.product_id,GROUP_CONCAT(att_group_name,'\":\"',av.att_value) AS attribute_value,pa.price_change AS price,pa.product_url FROM product_attributes pa+ \n"
				+ "INNER JOIN attributes_value av ON av.id=pa.att_group_val_id INNER JOIN attributes a ON a.id=pa.att_group_id  WHERE pa.product_id IN (SELECT product_id FROM wish_list WHERE customer_id=113) GROUP BY product_id";

		List<JSONObject> filter_category = jdbcTemplate.query(sql, new shopNowevent_filterRowMapper(), id);

		if (filter_category.isEmpty()) {

			return "Category not found !";
		} else {
			String Str_filter_category = filter_category.toString().substring(1,
					filter_category.toString().length() - 1);
			return Str_filter_category;
		}

	}

	// ----------------------------------------------------------------------------------------------------

	
	
	// ---------------------Cart ItemAdd-----------------------------------------------------
	@Override
	public String getCartItemByAdd(CartItemByAdd a1) {

		final long userid = a1.getUserId();
		final long product_id = a1.getProduct_id();
		int quantity = a1.getQuantity();
		final int vendor_id = a1.getVendor_id();
		String device_id = null;
		String Str_msg;
		JSONObject jo_cartInsert = new JSONObject();
		
		if (quantity == 0) {
			quantity = 1;

		}
		

		// -------get mac address--------------------------------

		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			device_id = sb.toString();

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		}


		SqlRowSet srs_product_id = jdbcTemplate
				.queryForRowSet("SELECT id FROM products where id='" + product_id + "'AND stock='true'");

		if (srs_product_id.next() == false) {

			Str_msg = "Either product id is not valid or the product is currently not available";
			jo_cartInsert.put("status", "0");
			jo_cartInsert.put("message", Str_msg);

			return Str_msg;

		}

		if (userid == 0) {

			SqlRowSet Cart_product_id = jdbcTemplate.queryForRowSet("SELECT UserId FROM cart_items where productId='"
					+ product_id + "' and device_id='" + device_id + "'");

			if (Cart_product_id.next()) {
				long uid = Cart_product_id.getLong("UserId");
				if (uid > 0) {
					String sql = "INSERT  INTO cart_items (device_id,UserId,ProductId,VendorId,Quantity) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE  Quantity =("
							+ quantity + ")";

					int i = jdbcTemplate.update(sql,
							new Object[] { device_id, userid, product_id, vendor_id, quantity });

					if (i > 0) {
						Str_msg = "CartItem inserted successfully";
						jo_cartInsert.put("status", "1");
						jo_cartInsert.put("message", Str_msg);

					}

					else {
						Str_msg = "Cart_Item not Inserted";
						jo_cartInsert.put("status", "0");
						jo_cartInsert.put("message", Str_msg);

					}

					return jo_cartInsert.toString();

				}

				Str_msg = "product is present in cart please call Cart/update web service";
				jo_cartInsert.put("status", "0");
				jo_cartInsert.put("message", Str_msg);

				return Str_msg;

			} else {

				String sql = "INSERT  INTO cart_items (device_id,UserId,ProductId,VendorId,Quantity) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE  Quantity =("
						+ quantity + ")";

				int i = jdbcTemplate.update(sql, new Object[] { device_id, userid, product_id, vendor_id, quantity });

				if (i > 0) {
					Str_msg = "CartItem inserted successfully";
					jo_cartInsert.put("status", "1");
					jo_cartInsert.put("message", Str_msg);

				}

				else {
					Str_msg = "Cart_Item not Inserted";
					jo_cartInsert.put("status", "0");
					jo_cartInsert.put("message", Str_msg);

				}

				return jo_cartInsert.toString();

			}

		} else {

			SqlRowSet srs_customer_id = jdbcTemplate
					.queryForRowSet("SELECT id FROM customers where id='" + userid + "'");

			SqlRowSet user_item_cart = jdbcTemplate.queryForRowSet(
					"SELECT * FROM cart_items where UserId='" + userid + "' and productId=" + product_id);

			SqlRowSet user_item_cart1 = jdbcTemplate
					.queryForRowSet("SELECT * FROM cart_items where UserId=0  and productId='" + product_id
							+ "' and device_id='" + device_id + "'");

			if (srs_customer_id.next() == false) {

				Str_msg = "user is not valid";
				jo_cartInsert.put("status", "0");
				jo_cartInsert.put("message", Str_msg);

				return jo_cartInsert.toString();

			}

			// ------------------------------------------

			else if (user_item_cart.next()) {

				Str_msg = "product is present in cart please call Cart/update web service";
				jo_cartInsert.put("status", "0");
				jo_cartInsert.put("message", Str_msg);

				return jo_cartInsert.toString();

			}

			// --------------------------------------------
			else if (user_item_cart1.next()) {

				String sql1 = "UPDATE cart_items SET UserId=" + userid + " WHERE ProductId=" + product_id
						+ " AND device_id='" + device_id + "' and UserId = 0";

				jdbcTemplate.update(sql1);
				//jdbcTemplate.execute(sql1);

				Str_msg = "CartItem update successfully";
				jo_cartInsert.put("status", "1");
				jo_cartInsert.put("message", Str_msg);
				System.out.println("About to return");
				return jo_cartInsert.toString();

			}

			// ---------------------------------------------------

			else {
							

				String sql = "INSERT  INTO cart_items (device_id,UserId,ProductId,VendorId,Quantity) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE  Quantity =("
						+ quantity + ")";

				int i = jdbcTemplate.update(sql, new Object[] { device_id, userid, product_id, vendor_id, quantity });

				if (i > 0) {
					Str_msg = "CartItem inserted successfully";
					jo_cartInsert.put("status", "1");
					jo_cartInsert.put("message", Str_msg);

				}

				else {
					Str_msg = "Cart_Item not Inserted";
					jo_cartInsert.put("status", "0");
					jo_cartInsert.put("message", Str_msg);

				}

			}

			return jo_cartInsert.toString();

		}

	}
	
	
	// -------------------------------------Cart Quantity_update------------------------------------------------------

		@Override
		public String getCartItemByDelete(CartItemByDelete a1) {

			final long userid = a1.getUserId();
			final long product_id = a1.getProduct_id();
			int quantity = a1.getQuantity();
			final int vendor_id = a1.getVendor_id();
			String device_id = null;

			String Str_msg;
			JSONObject jo_cartUpdate = new JSONObject();

			// -------get mac address--------------------------------

			InetAddress ip;
			try {

				ip = InetAddress.getLocalHost();
				System.out.println("Current IP address : " + ip.getHostAddress());
				NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				byte[] mac = network.getHardwareAddress();
				System.out.print("Current MAC address : ");

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}
				System.out.println(sb.toString());
				device_id = sb.toString();

			} catch (UnknownHostException e) {

				e.printStackTrace();

			} catch (SocketException e) {

				e.printStackTrace();

			}

			if (userid == 0) {
				SqlRowSet srs_product_id = jdbcTemplate.queryForRowSet("SELECT * FROM cart_items where ProductId='"
						+ product_id + "'AND device_id='" + device_id + "'and userid='" + userid + "'");

				if (srs_product_id.next() == false) {

					Str_msg = "product is not Added in cart please call Cart/ADD web service";
					jo_cartUpdate.put("status", "0");
					jo_cartUpdate.put("message", Str_msg);

					return Str_msg;

				} else if (quantity == 0) {

					jdbcTemplate.update("Delete from cart_items where device_id='" + device_id + "' and productId='"
							+ product_id + "'");

					Str_msg = "product is remove from  cart ";
					jo_cartUpdate.put("status", "0");
					jo_cartUpdate.put("message", Str_msg);

					return jo_cartUpdate.toString();

				} else {

					String sql1 = "UPDATE cart_items SET Quantity=" + quantity + " WHERE ProductId=" + product_id
							+ " AND device_id='" + device_id + "'";

					jdbcTemplate.update(sql1);

					Str_msg = "CartItem update successfully";
					jo_cartUpdate.put("status", "1");
					jo_cartUpdate.put("message", Str_msg);

				}
				return jo_cartUpdate.toString();

			}

			else {

				SqlRowSet srs_product_id = jdbcTemplate.queryForRowSet(
						"SELECT * FROM cart_items where ProductId='" + product_id + "'AND UserId='" + userid + "'");

				SqlRowSet user_item_cart1 = jdbcTemplate
						.queryForRowSet("SELECT * FROM cart_items where UserId=0  and productId='" + product_id
								+ "' and device_id='" + device_id + "'");

				if (user_item_cart1.next()) {

					String sql1 = "UPDATE cart_items SET userid=" + userid + " WHERE ProductId=" + product_id
							+ " AND device_id='" + device_id + "'";

					jdbcTemplate.update(sql1);

					Str_msg = "CartItem update successfully";
					jo_cartUpdate.put("status", "1");
					jo_cartUpdate.put("message", Str_msg);
					return jo_cartUpdate.toString();

				}

				else if (srs_product_id.next() == false) {

					Str_msg = "product is not Added in cart please call Cart/ADD web service";
					jo_cartUpdate.put("status", "0");
					jo_cartUpdate.put("message", Str_msg);

					return jo_cartUpdate.toString();

				} else if (quantity == 0) {

					jdbcTemplate.update(
							"Delete from cart_items where  UserId='" + userid + "' and productId='" + product_id + "'");

					Str_msg = "product is remove from  cart ";
					jo_cartUpdate.put("status", "0");
					jo_cartUpdate.put("message", Str_msg);

					return jo_cartUpdate.toString();

				}

				else {

					String sql1 = "UPDATE cart_items SET Quantity=" + quantity + " WHERE ProductId='" + product_id
							+ "' AND UserId='" + userid + "'";

					jdbcTemplate.update(sql1);

					Str_msg = "CartItem update successfully";
					jo_cartUpdate.put("status", "1");
					jo_cartUpdate.put("message", Str_msg);

				}
				return jo_cartUpdate.toString();
			}

		}

		// -------------------Remove Web service-----------------------------------------
		@Override
		public String getCartItemByRemove(CartItemByRemove a1) {

			final long userid = a1.getUserId();
			final long product_id = a1.getProduct_id();
			String device_id = null;
			final int vendor_id = a1.getVendor_id();

			String Str_msg;
			JSONObject jo_cartRemove = new JSONObject();

			
			
			
			
			// -------get mac address--------------------------------

			InetAddress ip;
			try {

				ip = InetAddress.getLocalHost();
				NetworkInterface network = NetworkInterface.getByInetAddress(ip);
				byte[] mac = network.getHardwareAddress();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}

				device_id = sb.toString();

			} catch (UnknownHostException e) {

				e.printStackTrace();

			} catch (SocketException e) {

				e.printStackTrace();

			}

			if (userid == 0) {

				int i = jdbcTemplate.update(
						"Delete from cart_items where productId='" + product_id + "' and device_id='" + device_id + "'");
				if (i > 0) {
					Str_msg = "product is remove from  cart ";
					jo_cartRemove.put("status", "0");
					jo_cartRemove.put("message", Str_msg);
				} else {

					Str_msg = "product is not remove from  cart ";
					jo_cartRemove.put("status", "0");
					jo_cartRemove.put("message", Str_msg);

				}
				return jo_cartRemove.toString();
			} else {

				int i = jdbcTemplate
						.update("Delete from cart_items where  UserId='" + userid + "' and productId='" + product_id + "'");
				if (i > 0) {
					Str_msg = "product is remove from  cart ";
					jo_cartRemove.put("status", "0");
					jo_cartRemove.put("message", Str_msg);
				} else {

					Str_msg = "product is not remove from  cart ";
					jo_cartRemove.put("status", "0");
					jo_cartRemove.put("message", Str_msg);

				}
				return jo_cartRemove.toString();
			}

		}

	
	
	

	// -------------------------------get CartItem Display------------------------------------------------

	@Override
	public String getCartItemByCustomer_Id(CartItemByCustomer_Id a1) {

		long userid = a1.getUserId();
		String device_id = null;
		String Str_msg;
		JSONObject jo_cartDisplay = new JSONObject();
		
		SqlRowSet srs_customer_id = jdbcTemplate
				.queryForRowSet("SELECT id FROM customers where id='" + userid + "'");
		
		if(device_id==null && srs_customer_id.next() == false && userid==0) {
			
			Str_msg = "device_id or userid not valid";
			jo_cartDisplay.put("status", "0");
			jo_cartDisplay.put("message", Str_msg);
			
			
		}
		String search = a1.getSearch();
		if(search==null){
			search="";
			
		}
		String sql = null;
		
		//--------------get mac Address---------------------------------------------------
		
		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}		
			device_id = sb.toString();

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		}

		if(userid!=0) {

		
		 sql = "SELECT Products.*,table1.attribute_value,cart_items.UserId,cart_items.Quantity,SUM(cart_items.Quantity *Products.sale_price) AS total,product_offers.offer_name ,(SELECT GROUP_CONCAT(firstName,\" \",lastName,\", phoneNumber- \",phoneNumber ,\" , Address - \",address1,\" \",address2,\" \",address3,\" \",city,\" \",state) AS addresscustomer  FROM(SELECT * FROM  address WHERE customerId="
				+ userid + " LIMIT 1)AS table1 )AS delavery  FROM Products LEFT JOIN \n"
				+ " (SELECT pa.product_id,GROUP_CONCAT(att_group_name,':',av.att_value) AS attribute_value FROM product_attributes pa INNER JOIN attributes_value av ON av.id=pa.att_group_val_id INNER JOIN attributes a ON a.id=pa.att_group_id GROUP BY pa.product_id) AS table1 ON Products.id=table1.product_id \n"
				+ " LEFT JOIN cart_items ON cart_items.ProductId=Products.id  LEFT JOIN product_offers ON product_offers.product_id=Products.id WHERE cart_items.UserId="
				+ userid + " AND Products.name LIKE '%" + search + "%' GROUP BY Products.id";
		

		}
		else {
			 sql = "SELECT Products.*,table1.attribute_value,cart_items.UserId,cart_items.Quantity,SUM(cart_items.Quantity *Products.sale_price) AS total,product_offers.offer_name  FROM Products LEFT JOIN \n"
						+ " (SELECT pa.product_id,GROUP_CONCAT(att_group_name,'\":\"',av.att_value) AS attribute_value FROM product_attributes pa INNER JOIN attributes_value av ON av.id=pa.att_group_val_id INNER JOIN attributes a ON a.id=pa.att_group_id GROUP BY pa.product_id) AS table1 ON Products.id=table1.product_id \n"
						+ " LEFT JOIN cart_items ON cart_items.ProductId=Products.id  LEFT JOIN product_offers ON product_offers.product_id=Products.id WHERE cart_items.device_id='"
						+ device_id + "'  AND Products.name LIKE '%" + search + "%' GROUP BY Products.id";
			

			
		}
		List<JSONObject> CartItem = jdbcTemplate.query(sql, new shopNowevent_CartItemByCustomer_IdRowMapper());

		if (CartItem.isEmpty()) {

			Str_msg = "CartItem not found !";
			jo_cartDisplay.put("status", "0");
			jo_cartDisplay.put("message", Str_msg);
		
			return jo_cartDisplay.toString();
		} else {

			String Str_filter_category = CartItem.toString().substring(1, CartItem.toString().length() - 1);
			jo_cartDisplay.put("Cart_items", Str_filter_category);
			
			return Str_filter_category;
		}

	}
	
	
	
	private static class shopNowevent_CartItemByCustomer_IdRowMapper implements RowMapper<JSONObject> {

		@Override
		public JSONObject mapRow(ResultSet rs, int i) throws SQLException {

			JSONObject jo_CartItem_Result = new JSONObject();
			JSONArray json_array_CartItem = new JSONArray();

			float total = 0;

		
			do {

				JSONObject jo_cartItem = new JSONObject();
				JSONArray json_array_CartItem1 = new JSONArray();
				jo_cartItem.put("Product_id", rs.getString("id"));
				jo_cartItem.put("vendor_product_id", rs.getString("vendor_product_id"));
				jo_cartItem.put("quantity", rs.getString("Quantity"));
				jo_cartItem.put("Sale_price", rs.getFloat("sale_price"));
				jo_cartItem.put("Regular_price", rs.getFloat("regular_price"));
				jo_cartItem.put("name", rs.getString("name"));

				if (rs.getString("offer_name") == null) {

					jo_cartItem.put("offer", "");
				} else {

					jo_cartItem.put("offer", rs.getString("offer_name"));
				}

				jo_cartItem.put("description", rs.getString("description"));
				jo_cartItem.put("total", rs.getInt("total"));
				jo_cartItem.put("image", rs.getString("image"));

			

				if (rs.getString("attribute_value") == null) {

				} else {
					json_array_CartItem1.put(rs.getString("attribute_value"));
					jo_cartItem.put("product_attribute", json_array_CartItem1);

				}

				jo_cartItem.put("product_attribute", json_array_CartItem1);
				total = total + rs.getFloat("total");

				json_array_CartItem.put(jo_cartItem);

			} while (rs.next());

			jo_CartItem_Result.put("Products", json_array_CartItem);
			jo_CartItem_Result.put("Grant Total", total);
			if (total > 1000) {
				jo_CartItem_Result.put("Delivery", "FREE");

			}

			jo_CartItem_Result.put("tax", "0");	
			jo_CartItem_Result.put("curancy", "INR");

			return jo_CartItem_Result;

		}
	}


	// ------------------------------------------------PlaceOrder---------------------------------------------------------

	@Override
	public String getPlaceOrder(PlaceOrder a1) {

		final long userid = a1.getUserId();
		final String paymentMode = a1.getPayment_mode();	
		String payment_gateway = a1.getPayment_gateway();
		String delivery_address = a1.getDelivery_address();
		String billing_address = a1.getBilling_address();
		String transaction_id = a1.getTransaction_id();
		int flag = 0;
		int quantity = 0;
		long productId;
		String orderId;

		String name;
		float sale_price;
		String attribute_value;
		String delavery;
		int i = 0;
		String Str_msg = null;
		JSONObject jo_cartPlaceOrder = new JSONObject();
		JSONObject jsonObject_placeorder_result = new JSONObject();

		long current = System.currentTimeMillis();
		orderId = Long.toString(current) + "_" + Long.toString(userid);

		
		
		if(paymentMode.equalsIgnoreCase("COD"))
		{
			flag = 1;
			transaction_id="COD";
			payment_gateway="COD";
			
		}
		
		
		SqlRowSet srs_customer_id = jdbcTemplate.queryForRowSet("SELECT CartItemId FROM cart_items where UserId='" + userid + "'");
		
		
		if (srs_customer_id.next()==false || userid==0) {

			Str_msg = "Order not placed successfully because userID is null or not valid user Or user has not any cart items";
			jsonObject_placeorder_result.put("status", "0");
			jsonObject_placeorder_result.put("message", Str_msg);
			return jsonObject_placeorder_result.toString();

		}
		if(flag==0){
			
		
		if (transaction_id == null || transaction_id == "") {

			Str_msg = "Order not placed successfully because transaction_id is null";
			jsonObject_placeorder_result.put("status", "0");
			jsonObject_placeorder_result.put("message", Str_msg);
			return jsonObject_placeorder_result.toString();

		}
		if (payment_gateway == null || payment_gateway == "") {

			Str_msg = "Order not placed successfully because payment_gateway is null";
			jsonObject_placeorder_result.put("status", "0");
			jsonObject_placeorder_result.put("message", Str_msg);
			return jsonObject_placeorder_result.toString();

		}
		}
				

		String sql = "SELECT Products.*,table1.attribute_value,cart_items.UserId,cart_items.Quantity,SUM(cart_items.Quantity *Products.sale_price) AS total ,(SELECT GROUP_CONCAT(firstName,\" \",lastName,\", phoneNumber- \",phoneNumber ,\" , Address\",address1,\" \",address2,\" \",address3,\" \",city,\" \",state) AS addresscustomer  FROM(SELECT * FROM  address WHERE customerId='110' LIMIT 1)AS table1 )AS delavery  FROM Products LEFT JOIN \n"
				+ "(SELECT pa.product_id,GROUP_CONCAT(att_group_name,':',av.att_value) AS attribute_value FROM product_attributes pa INNER JOIN attributes_value av ON av.id=pa.att_group_val_id INNER JOIN attributes a ON a.id=pa.att_group_id GROUP BY pa.product_id) AS table1 ON Products.id=table1.product_id \n"
				+ " LEFT JOIN cart_items ON cart_items.ProductId=Products.id  WHERE  cart_items.UserId=" + userid
				+ " GROUP BY Products.id";

		System.out.println(sql);
		SqlRowSet srs_product_id = jdbcTemplate.queryForRowSet(sql);

		while (srs_product_id.next()) {

			productId = srs_product_id.getLong("id");
			quantity = srs_product_id.getInt("Quantity");
			name = srs_product_id.getString("name");
			sale_price = srs_product_id.getFloat("sale_price");
			attribute_value = srs_product_id.getString("attribute_value");

			if (attribute_value == null) {

				name = "Name : " + name;
			} else {

				name = "Name : " + name + " , " + attribute_value;
			}

			delavery = srs_product_id.getString("delavery");
			String vendorId = srs_product_id.getString("vendor_id");
			String vendor_product_id = srs_product_id.getString("vendor_product_id");
			Date now = new Date();
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
			simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
			String day = simpleDateformat.format(now);

			if (quantity > 0) {

				if (day == "Sunday") {

					String sql_insert_address = "INSERT INTO order_details_delivery(order_id,user_id,product_description, productId,payment_status,delivery_status_code,delivery_address,vendorId,vendor_product_id,quantity,mode_of_payment,transaction_id,discounts,delivery_charges,price,expected_date_of_delivery,order_status,payment_gateway) VALUES\n"
							+ "(?,?,?,?,'payment_status','delivery_status_code',?,?,?,?,?,?,100,200,?,DATE_ADD(Now(),INTERVAL 8 DAY,Order placed,?))";
					i = jdbcTemplate.update(sql_insert_address, new Object[] { orderId, userid, name, productId,
							delavery, vendorId, vendor_product_id, quantity,paymentMode,transaction_id,sale_price,payment_gateway});
                   
				} else {

					String sql_insert_address = "INSERT INTO order_details(order_id,user_id,product_description, productId,payment_status,delivery_status_code,delivery_address,vendorId,vendor_product_id,quantity,mode_of_payment,transaction_id,discounts,delivery_charges,price,expected_date_of_delivery,order_status,payment_gateway) VALUES\n"
							+ "(?,?,?,?,'payment_status','delivery_status_code',?,?,?,?,?,?,100,200,?,DATE_ADD(Now(), INTERVAL 7 DAY),'Order placed',?)";
					
					i = jdbcTemplate.update(sql_insert_address, new Object[] { orderId, userid, name, productId,
							delavery, vendorId, vendor_product_id, quantity,paymentMode,transaction_id,sale_price,payment_gateway});

				}

			}

			Str_msg = "Order Place succesfully ";
			jo_cartPlaceOrder.put("status", "0");
			jo_cartPlaceOrder.put("message", Str_msg);

		}
		//String sql_Remove_Cart ="DELETE FROM cart_items where UserId='" + userid +"'";
		//jdbcTemplate.update(sql_Remove_Cart);
		
		return jo_cartPlaceOrder.toString();
	}

	
	
	
	// -----------------------------Display Order---------------------------------------

	@Override
	public String getProductOrder(ProductOrder a1) {

		long userId = a1.getUserId();
		String orderBy = a1.getOrderBy();
		String search = a1.getSearch();
		String order_status=a1.getOrder_status();
		String Vendor_id = null;
		
		if(order_status==null) {
			
			order_status="Order paced";
		}
		
		String order1;
		
		int flagChange;

		if (orderBy == null) {
			order1 = "DESC";
			flagChange = 0;
		} else if (orderBy.equalsIgnoreCase("priceup")) {
			order1 = "ASC";
			flagChange = 1;
		} else if (orderBy.equalsIgnoreCase("pricedown")) {
			order1 = "DESC";
			flagChange = 1;
		} else {
			order1 = "ASC";
			flagChange = 0;

		}

		if (userId > 0) {

			SqlRowSet str_order_ids = jdbcTemplate.queryForRowSet(
					"select DISTINCT (order_id),delivery_status_code,vendorId,mode_of_payment,transaction_id,delivery_address,order_Date_Time,expected_date_of_delivery,payment_status from order_details where user_id ='"
							+ userId + "' and order_status='"+order_status+"'");

			JSONObject jo_CartItem_Result_final = new JSONObject();
			JSONArray json_array_orderItem = new JSONArray();
			int i = 1;

			while (str_order_ids.next()) {
				JSONArray json_array_orderItem1 = new JSONArray();

				JSONObject jo_CartItem_Result = new JSONObject();

				String sql = "SELECT * FROM order_details  INNER JOIN products ON products.id=order_details.productId WHERE order_details.order_id ='"
						+ str_order_ids.getString("order_id") + "' and order_status='"+order_status+"'" ;

				SqlRowSet str_order_product = jdbcTemplate.queryForRowSet(sql);

				float total = 0;
				while (str_order_product.next()) {
					JSONObject jo_OrderItem1 = new JSONObject();
					jo_OrderItem1.put("id", str_order_product.getLong("id"));
					jo_OrderItem1.put("product_id", str_order_product.getLong("productId"));
					jo_OrderItem1.put("product_name", str_order_product.getString("product_description"));
					Vendor_id = str_order_ids.getString("vendorId");
					jo_OrderItem1.put("Image", str_order_product.getString("image"));
					float price = str_order_product.getFloat("price");
					jo_OrderItem1.put("price", price);
					total = total + (price * str_order_product.getInt("quantity"));
					jo_OrderItem1.put("total_price", price * str_order_product.getInt("quantity"));
					json_array_orderItem1.put(jo_OrderItem1);
				}

				jo_CartItem_Result.put("Order_Id", str_order_ids.getString("order_id"));
				jo_CartItem_Result.put("total_price", total);
				jo_CartItem_Result.put("Vendor_id", Vendor_id);
				jo_CartItem_Result.put("payment_mode", str_order_ids.getString("mode_of_payment"));
				jo_CartItem_Result.put("Order Date", str_order_ids.getDate("order_Date_Time"));

				if (str_order_ids.getString("mode_of_payment") != "COD") {
					jo_CartItem_Result.put("transaction_id", str_order_ids.getString("transaction_id"));
				} else {
					jo_CartItem_Result.put("transaction_id", "COD");
				}

				json_array_orderItem.put(jo_CartItem_Result);

			}

			jo_CartItem_Result_final.put("orders", json_array_orderItem);

			return jo_CartItem_Result_final.toString();
		} else
			return "Enter Valid User Id";
	}

	// ---------------------------------------------Order/Display--------------------------------------------------------

	@Override
	public String getOrderDisplay(OrderDisplay a1) {

		long userId = a1.getUserId();
		String orderBy = a1.getOrderBy();
		String search = a1.getSearch();
		if(search==null) {
			search="";
		}
		int page_number=a1.getPage_number();
		
		if(page_number==0) {
			page_number=1;
			
		}
		int  page_size=10;

		float Product_total = 0;
		String vendor_id = null;
		String order1;
		int flagChange;
		String Str_msg;
		JSONObject jo_CartItem_Result_final = new JSONObject();
		JSONArray json_array_orderItem = new JSONArray();
		int i = 1;

		if (orderBy == null) {
			order1 = "DESC";
			flagChange = 0;
		} else if (orderBy.equalsIgnoreCase("ASC")) {
			order1 = "ASC";
			flagChange = 1;
		} else if (orderBy.equalsIgnoreCase("DESC")) {
			order1 = "DESC";
			flagChange = 1;
		} else {
			order1 = "DESC";
			flagChange = 0;

		}

		SqlRowSet srs_customer_id = jdbcTemplate.queryForRowSet("SELECT id FROM customers where id='" + userId + "'");

		if (srs_customer_id.next() == false) {

			Str_msg = "user is not valid";
			jo_CartItem_Result_final.put("status", "0");
			jo_CartItem_Result_final.put("message", Str_msg);

			return jo_CartItem_Result_final.toString();

		}

		SqlRowSet str_order_ids = jdbcTemplate.queryForRowSet(
				"select DISTINCT (order_id),product_description,delivery_status_code,vendorId,mode_of_payment,transaction_id,delivery_address,order_Date_Time,expected_date_of_delivery,payment_status,order_status from order_details where user_id ='"
						+ userId + "' AND product_description LIKE '%"+search+"%'  order by order_Date_Time "+order1+" limit "+(page_number-1)*10 +","+ page_size);
		
		System.out.println();

		while (str_order_ids.next()) {

			JSONArray json_array_orderItem1 = new JSONArray();
			JSONObject jo_CartItem_Result = new JSONObject();

			String sql = "SELECT * FROM order_details  INNER JOIN products ON products.id=order_details.productId WHERE order_details.order_id ='"
					+ str_order_ids.getString("order_id") + "'";

			SqlRowSet str_order_product = jdbcTemplate.queryForRowSet(sql);

			while (str_order_product.next()) {

				JSONObject jo_OrderItem1 = new JSONObject();
				jo_OrderItem1.put("product_id", str_order_product.getLong("productId"));
				jo_OrderItem1.put("product_name", str_order_product.getString("product_description"));
				jo_OrderItem1.put("Vendor Id",str_order_product.getString("vendorId"));
				float price = str_order_product.getFloat("price");
				Product_total = Product_total + (price * str_order_product.getInt("quantity"));
				jo_OrderItem1.put("Product_total_price", price * str_order_product.getInt("quantity"));
				json_array_orderItem1.put(jo_OrderItem1);

			}
			jo_CartItem_Result.put("Order_product", json_array_orderItem1);
			jo_CartItem_Result.put("Order_Id", str_order_ids.getString("order_id"));
			jo_CartItem_Result.put("Order_total_price", Product_total);
			jo_CartItem_Result.put("payment_mode", str_order_ids.getString("mode_of_payment"));

			if (str_order_ids.getString("mode_of_payment") != "COD") {
				jo_CartItem_Result.put("transaction_id", str_order_ids.getString("transaction_id"));
			}

			jo_CartItem_Result.put("Order Date", str_order_ids.getDate("order_Date_Time"));
			jo_CartItem_Result.put("Order_Status", str_order_ids.getString("order_status"));
			jo_CartItem_Result.put("expected_delivery_Date", str_order_ids.getDate("expected_date_of_delivery"));
			jo_CartItem_Result.put("transaction_id", "COD");
			json_array_orderItem.put(jo_CartItem_Result);

		}

		jo_CartItem_Result_final.put("orders", json_array_orderItem);

		return jo_CartItem_Result_final.toString();

	}

	// ---------------------------------------------------------------Order/Detail--------------------------------------------------------

	@Override
	public String getOrderDetail(OrderDetail a1) {

		long userId = a1.getUserId();
		String orderBy = a1.getOrderBy();
		String search = a1.getSearch();
		if(search==null) {
			search="";
		}
		long product_id = a1.getProduct_id();
		String order_id = a1.getOrder_id();
		String order1;
		int flagChange;
		int page_number = a1.getPage_number();
		
		int page_size = 50;
		if (page_number == 0) {
			page_number = 1;

		}

		if (orderBy == null) {
			order1 = "ASC";
			flagChange = 0;
		} else if (orderBy.equalsIgnoreCase("priceup")) {
			order1 = "ASC";
			flagChange = 1;
		} else if (orderBy.equalsIgnoreCase("pricedown")) {
			order1 = "DESC";
			flagChange = 1;
		} else {
			order1 = "ASC";
			flagChange = 0;

		}

		String Str_msg;
		JSONObject jo_CartItem_Result_final = new JSONObject();
		JSONObject jo_Order_detail = new JSONObject();
		JSONArray json_array_orderItem = new JSONArray();
		
		float total = 0;
		String Order_Id = null;
		String order_status = null;
		String delivery_status_code = null;
		
		String payment_mode = null;
		String payment_status = null;
	
		java.sql.Date expected_delivery_Date=null;
		String transaction_id = null;
		String billing_address = null ;
		String shipping_address = null;
		java.sql.Date orderDate = null;
        String Order_Status = null;
		SqlRowSet srs_customer_id = jdbcTemplate.queryForRowSet("SELECT id FROM order_details where user_id='" + userId + "'");

		if (srs_customer_id.next() == false) {

			Str_msg = "user have not any order";
			jo_Order_detail.put("status", "0");
			jo_Order_detail.put("message", Str_msg);

			return jo_Order_detail.toString();

		}
		String sql_order="Select * from order_details where order_id ='"+order_id+"'";
		SqlRowSet srs_order = jdbcTemplate.queryForRowSet(sql_order);
		
		
		if (srs_order.next() == false) {

			Str_msg = "order not faund";
			jo_Order_detail.put("status", "0");
			jo_Order_detail.put("message", Str_msg);
			return jo_Order_detail.toString();

		}
		

		String sql11;
		if (product_id == 0) {
			sql11="SELECT * FROM order_details  INNER JOIN products ON products.id=order_details.productId WHERE order_details.order_id ='"+ order_id + "' and order_details.user_id ='" + userId + "' AND order_details.product_description LIKE '%"+search+"%' limit "+(page_number-1)*10 +","+ page_size;
			System.out.println(sql11);		
		}else if(order_id==null || order_id == ""){
			
			sql11="SELECT * FROM order_details  INNER JOIN products ON products.id=order_details.productId WHERE  order_details.user_id ='" + userId + "' and order_details.productId='" + product_id + "'";
			
	
			
			SqlRowSet str_order_product = jdbcTemplate.queryForRowSet(sql11);
			
			while (str_order_product.next()) {

				JSONObject jo_OrderItem1 = new JSONObject();

				//jo_OrderItem1.put("id", str_order_product.getInt("id"));
				jo_OrderItem1.put("product_id", str_order_product.getLong("productId"));
				
				jo_OrderItem1.put("product_name", str_order_product.getString("product_description"));
				jo_OrderItem1.put("vendor_id", str_order_product.getString("vendorId"));
				
				jo_OrderItem1.put("Image", str_order_product.getString("image"));
				float price = str_order_product.getFloat("price");
				jo_OrderItem1.put("price", price);
				jo_OrderItem1.put("quantity", str_order_product.getInt("quantity"));

				total = total + (price * str_order_product.getInt("quantity"));
				jo_OrderItem1.put("Product_total_price", price * str_order_product.getInt("quantity"));
				
				
				 Order_Id= str_order_product.getString("order_id");
				 order_status= str_order_product.getString("order_status");
				 payment_mode =str_order_product.getString("mode_of_payment");
				 payment_status= str_order_product.getString("payment_status");
			
				 transaction_id=str_order_product.getString("transaction_id");
				 billing_address =str_order_product.getString("delivery_address");
				 shipping_address= str_order_product.getString("delivery_address");
				 orderDate= str_order_product.getDate("order_Date_Time");
				 expected_delivery_Date=str_order_product.getDate("expected_date_of_delivery");
				    
				 jo_OrderItem1.put("Order_Id", Order_Id);
				 jo_OrderItem1.put("Order_Date", orderDate);
				 jo_OrderItem1.put("expected_delivery_Date", expected_delivery_Date);
				 //jo_OrderItem1.put("Order_total_price", total);
				 jo_OrderItem1.put("Order_status",order_status);
				 jo_OrderItem1.put("payment_mode", payment_mode);
				 jo_OrderItem1.put("payment_status",payment_status);
				 jo_OrderItem1.put("transaction_id", transaction_id);
				 jo_OrderItem1.put("billing address",billing_address);
				 jo_OrderItem1.put("shipping_address", shipping_address);
				 json_array_orderItem.put(jo_OrderItem1);
	
			}
			jo_Order_detail.put("Product_Detais", json_array_orderItem);
   		   return jo_Order_detail.toString();
		}
		
		
		else{
			
			sql11="SELECT * FROM order_details  INNER JOIN products ON products.id=order_details.productId WHERE order_details.order_id ='"+ order_id + "' and order_details.user_id ='" + userId + "' and order_details.productId='" + product_id + "'";
			System.out.println(sql11);
		}

		

			SqlRowSet str_order_product = jdbcTemplate.queryForRowSet(sql11);
	
			while (str_order_product.next()) {

				JSONObject jo_OrderItem1 = new JSONObject();

				jo_OrderItem1.put("id", str_order_product.getLong("id"));
				jo_OrderItem1.put("product_id", str_order_product.getLong("productId"));
				
				jo_OrderItem1.put("product_name", str_order_product.getString("product_description"));
				jo_OrderItem1.put("vendor_id", str_order_product.getString("vendorId"));
				
				jo_OrderItem1.put("Image", str_order_product.getString("image"));
				float price = str_order_product.getFloat("price");
				jo_OrderItem1.put("price", price);
				jo_OrderItem1.put("quantity", str_order_product.getInt("quantity"));

				total = total + (price * str_order_product.getInt("quantity"));
				jo_OrderItem1.put("Product_total_price", price * str_order_product.getInt("quantity"));
			  	json_array_orderItem.put(jo_OrderItem1);
				
				 Order_Id= str_order_product.getString("order_id");
				 delivery_status_code= str_order_product.getString("delivery_status_code");
				 payment_mode =str_order_product.getString("mode_of_payment");
				 payment_status= str_order_product.getString("payment_status");
			
				 transaction_id=str_order_product.getString("transaction_id");
				 billing_address =str_order_product.getString("delivery_address");
				 shipping_address= str_order_product.getString("delivery_address");
				 orderDate= str_order_product.getDate("order_Date_Time");
				 expected_delivery_Date=str_order_product.getDate("expected_date_of_delivery");
				 Order_Status =str_order_product.getString("order_status");
				
				
				
			}

			jo_Order_detail.put("Order_Id", Order_Id);
			jo_Order_detail.put("Order_Date", orderDate);
			jo_Order_detail.put("Order_Status", Order_Status);
			jo_Order_detail.put("expected_delivery_Date", expected_delivery_Date);
			jo_Order_detail.put("Order_total_price", total);
			jo_Order_detail.put("delivery_status_code",delivery_status_code);
			jo_Order_detail.put("payment_mode", payment_mode);
			jo_Order_detail.put("payment_status",payment_status);
			jo_Order_detail.put("transaction_id",transaction_id);
			jo_Order_detail.put("billing address",billing_address);
			jo_Order_detail.put("shipping_address", shipping_address);

			jo_Order_detail.put("Product_Detais", json_array_orderItem);

		return jo_Order_detail.toString();

	}

	// ---------------------------------------------------------------------------------------------------------------------------

	@Override
	public String getPaymentByCard(Payment pay1) {

		long userId = pay1.getUserId();
		String sql = "SELECT card_number,name_on_card,card_number,expiry_month,expiry_year FROM customer_payment_methods WHERE customer_id ='"
				+ userId + "'";

		SqlRowSet str_order_ids = jdbcTemplate.queryForRowSet(sql);

		JSONObject jo_CartItem_Result_final = new JSONObject();
		JSONArray json_array_orderItem = new JSONArray();
	

		while (str_order_ids.next()) {

		}

		jo_CartItem_Result_final.put("orders", json_array_orderItem);

		return jo_CartItem_Result_final.toString();

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ==========================Extra Work==================================================


	
	// --------------------------------------------------------------------------------------------------

	
	

	@Override
	public String getAllCartItem() {
		// SELECT column_name(s) FROM table_name where column = value

		String sql = "SELECT t1.total,cart_items.* FROM (SELECT CartId,SUM(Price * Quantity) AS total FROM cart_items) AS t1 INNER JOIN cart_items ON t1.CartId=cart_items.CartId LIMIT 10";

		List<JSONObject> AllCartItem = jdbcTemplate.query(sql, new shopNowevent_CartItemByCustomer_IdRowMapper());

		if (AllCartItem.isEmpty()) {

			return "CartItem not found !";
		} else {
			String Str_filter_category = AllCartItem.toString().substring(1, AllCartItem.toString().length() - 1);
			return Str_filter_category;
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ----------THIS SQLIMPLIMANTETION FOR PRODUCT_ATTRIBUTE TABLE----------------------------------------------

	private static class Product_attRowMapper implements RowMapper<shopNoweventProduct_att> {

		@Override
		public shopNoweventProduct_att mapRow(ResultSet resultSet2, int i) throws SQLException {
			shopNoweventProduct_att product_att = new shopNoweventProduct_att();

			product_att.setProduct_id(resultSet2.getInt("product_id"));
			String att_value = resultSet2.getString("attribute_value");

			String[] att_value1 = att_value.split(",");
			product_att.setAtt_value(att_value1);
			product_att.setPrice(resultSet2.getString("price"));
			product_att.setProduct_url(resultSet2.getString("product_url"));

			return product_att;
		}

	}

	@Override
	public Collection<shopNoweventProduct_att> getAllProduct_atts() throws SQLException {

		final String sql1 = "SELECT pa.product_id,GROUP_CONCAT(att_group_name,':',av.att_value) AS attribute_value,pa.price_change AS price,pa.product_url FROM product_attributes pa  \n"
				+ " INNER JOIN attributes_value av ON av.id=pa.att_group_val_id INNER JOIN attributes a ON a.id=pa.att_group_id GROUP BY product_id limit 10";

		List<shopNoweventProduct_att> product_att = jdbcTemplate.query(sql1, new Product_attRowMapper());

		return product_att;
	}

	@Override
	public Collection<shopNoweventProduct_att> getProduct_attById(int product_id) throws SQLException {

		final String sql1 = "SELECT pa.product_id,GROUP_CONCAT(att_group_name,' : ',av.att_value) AS attribute_value,pa.price_change AS price,pa.product_url FROM product_attributes pa  \n"
				+ " INNER JOIN attributes_value av ON av.id=pa.att_group_val_id INNER JOIN attributes a ON a.id=pa.att_group_id where product_id=?";

		List<shopNoweventProduct_att> product_att = jdbcTemplate.query(sql1, new Product_attRowMapper(), product_id);

		return product_att;
	}

	// ----------------------------------------- -------------------------------THIS
	// SQLIMPLIMANTETION FOR OFFER
	// TABLE--------------------------------------------------

	private static class OfferRowMapper implements RowMapper<shopNoweventOffer> {

		@Override
		public shopNoweventOffer mapRow(ResultSet resultSet, int i) throws SQLException {

			shopNoweventOffer shopoffer = new shopNoweventOffer();

			shopoffer.setProduct_id(resultSet.getInt("product_id"));
			String offer = resultSet.getString("offer_name");
			String[] offer_name = offer.split(",");
			shopoffer.setOffer_name(offer_name);

			return shopoffer;
		}
	}

	@Override
	public Collection<shopNoweventOffer> getAllOffer() throws SQLException {

		final String sql = "SELECT product_id,GROUP_CONCAT(offer_name) AS offer_name FROM product_offers Group by product_id LIMIT 20";
		List<shopNoweventOffer> offer = jdbcTemplate.query(sql, new OfferRowMapper());

		return offer;
	}

	@Override
	public Collection<shopNoweventOffer> getOfferById(int product_id) throws SQLException, ClassNotFoundException {

		final String sql = "SELECT product_id,GROUP_CONCAT(offer_name) AS offer_name FROM product_offers WHERE product_id=?";
		List<shopNoweventOffer> offer = jdbcTemplate.query(sql, new OfferRowMapper(), product_id);

		return offer;

	}

	// --------------------------------------------------------------------THIS
	// SQLIMPLIMANTETION FOR PRODUCT_SIMPLE TABLE---------------------------------

	private static class ProductsRowMapper implements RowMapper<Productsevent> {

		@Override
		public Productsevent mapRow(ResultSet resultSet, int i) throws SQLException {
			Productsevent pe = new Productsevent();

			pe.setId(resultSet.getInt("id"));
			pe.setName(resultSet.getString("name"));
			pe.setDescription(resultSet.getString("description"));
			pe.setRegular_price(resultSet.getFloat("regular_price"));
			pe.setSale_price(resultSet.getFloat("sale_price"));
			pe.setStock(resultSet.getString("stock"));
			pe.setImage(resultSet.getString("image"));

			String image_url = resultSet.getString("image1");
			String[] image_url1 = image_url.split(",");
			pe.setImage_extra(image_url1);

			return pe;

		}
	}

	@Override
	public Collection<Productsevent> getAllProducts() throws SQLException {

		final String sql = "SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,\n"
				+ "GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id GROUP BY products.id LIMIT 3";

		List<Productsevent> pe = jdbcTemplate.query(sql, new ProductsRowMapper());
		return pe;
	}

	@Override
	public Collection<Productsevent> getProductsById(int id) throws SQLException {

		final String sql1 = "Select products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.id=?";
		List<Productsevent> pe = jdbcTemplate.query(sql1, new ProductsRowMapper(), id);

		return pe;
	}

	// --------------------------------------------------------THIS
	// SQLIMPLIMANTETION FOR PRODUCT_SIMPLE_ALL
	// TABLE---------------------------------------------------

	private static class ProductsAllRowMapper implements RowMapper<ProductsAllevent> {

		@Override
		public ProductsAllevent mapRow(ResultSet resultSet, int i) throws SQLException {
			ProductsAllevent pe1 = new ProductsAllevent();

			pe1.setId(resultSet.getInt("id"));
			pe1.setName(resultSet.getString("name"));
			pe1.setDescription(resultSet.getString("description"));
			pe1.setCategory_name(resultSet.getString("Category_name"));
			pe1.setParent_category(resultSet.getString("parent_category"));
			pe1.setRegular_price(resultSet.getFloat("regular_price"));
			pe1.setSale_price(resultSet.getFloat("sale_price"));
			pe1.setStock(resultSet.getString("stock"));
			pe1.setProduct_URL(resultSet.getString("product_url"));
			pe1.setImage(resultSet.getString("image"));

			String image_url = resultSet.getString("extra_image");
			String[] image_url1 = image_url.split(",");
			pe1.setImage_extra(image_url1);

			return pe1;

		}
	}

	@Override
	public Collection<ProductsAllevent> getAllProductsAll() throws SQLException {

		final String sql = "SELECT t2.id,t2.description,t2.name,t1.Category_name,t1.parent_category,t2.regular_price,t2.sale_price,t2.stock,t2.product_url,t2.image,t2.extra_image FROM(\n"
				+ "SELECT result.name AS Category_name,result.id,cat.parent_name AS parent_category FROM(SELECT pa.id,ca.name,ca.parent_id FROM products pa INNER JOIN categories ca ON pa.category_id=ca.id\n"
				+ ") AS result INNER JOIN (SELECT id,parent_id,NAME AS parent_name FROM categories  ) AS cat ON result.parent_id=cat.id\n"
				+ ") AS t1 INNER JOIN\n"
				+ "(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.product_url,products.image,GROUP_CONCAT(product_image.image) AS extra_image FROM products,product_image WHERE products.id=product_image.product_id GROUP BY products.id\n"
				+ ") t2 ON t1.id=t2.id limit 3";

		List<ProductsAllevent> pe1 = jdbcTemplate.query(sql, new ProductsAllRowMapper());
		return pe1;
	}

	@Override
	public Collection<ProductsAllevent> getProductsAllById(int id) throws SQLException {

		final String sql1 = "SELECT t2.id,t2.description,t2.name,t1.Category_name,t1.parent_category,t2.regular_price,t2.sale_price,t2.stock,t2.product_url,t2.image,t2.extra_image FROM(\n"
				+ "SELECT result.name AS Category_name,result.id,cat.parent_name AS parent_category FROM(SELECT pa.id,ca.name,ca.parent_id FROM products pa INNER JOIN categories ca ON pa.category_id=ca.id\n"
				+ ") AS result INNER JOIN (SELECT id,parent_id,NAME AS parent_name FROM categories  ) AS cat ON result.parent_id=cat.id\n"
				+ ") AS t1 INNER JOIN\n"
				+ "(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.product_url,products.image,GROUP_CONCAT(product_image.image) AS extra_image FROM products,product_image WHERE products.id=product_image.product_id GROUP BY products.id\n"
				+ ") t2 ON t1.id=t2.id WHERE t1.id=?";

		List<ProductsAllevent> pe1 = jdbcTemplate.query(sql1, new ProductsAllRowMapper(), id);

		return pe1;
	}

	// ----------------------------------- --------------------------THIS
	// SQLIMPLIMANTETION FOR CATEGORY
	// TABLE-------------------------------------------------

	private static class CategoryRowMapper implements RowMapper<shopNoweventCategory> {

		@Override
		public shopNoweventCategory mapRow(ResultSet resultSet1, int i) throws SQLException {
			shopNoweventCategory cat = new shopNoweventCategory();

			// cat.setId(resultSet1.getInt("id"));
			// cat.setName(resultSet1.getString("name"));
			// cat.setParent_name(resultSet1.getString("parent_name"));

			return cat;

		}
	}

	@Override
	public Collection<shopNoweventCategory> getAllCategory() {
		// SELECT column_name(s) FROM table_name
		final String sql1 = "SELECT result.id,result.name,cat.parent_name FROM(SELECT pa.id,ca.name,ca.parent_id FROM products pa INNER JOIN categories ca ON pa.category_id=ca.id\n"
				+ ") AS result INNER JOIN (SELECT id,parent_id,NAME AS parent_name FROM categories  ) AS cat ON result.parent_id=cat.id";

		List<shopNoweventCategory> cat = jdbcTemplate.query(sql1, new CategoryRowMapper());
		return cat;

	}

	@Override
	public Collection<shopNoweventCategory> getCategoryById(int id) {
		// SELECT column_name(s) FROM table_name where column = value
		String sql = "SELECT result.id,result.name,cat.parent_name FROM(SELECT pa.id,ca.name,ca.parent_id FROM products pa INNER JOIN categories ca ON pa.category_id=ca.id AND pa.category_id=? LIMIT 1 \n"
				+ ") AS result INNER JOIN (SELECT id,parent_id,NAME AS parent_name FROM categories  ) AS cat ON result.parent_id=cat.id";

		List<shopNoweventCategory> category = jdbcTemplate.query(sql, new CategoryRowMapper(), id);

		return category;
	}

	// -----------------------------------------------------------PRODUCTS_FAMILY
	// TABLE------------------------------------------------------------------------------------------------

	private static class Products_familyRowMapper implements RowMapper<shopNoweventProducts_family> {

		@Override
		public shopNoweventProducts_family mapRow(ResultSet resultSet, int i) throws SQLException {
			shopNoweventProducts_family product_fami = new shopNoweventProducts_family();

			product_fami.setProduct_family_id(resultSet.getString("product_family_id"));
			product_fami.setProducts_family(resultSet.getString("products_family"));

			return product_fami;

		}
	}

	@Override
	public Collection<shopNoweventProducts_family> getAllProducts_family() {

		final String sql1 = "SELECT product_family_id,GROUP_CONCAT(product_family) AS products_family FROM products_family Group By product_family_id Limit 10";
		List<shopNoweventProducts_family> product_fami = jdbcTemplate.query(sql1, new Products_familyRowMapper());
		return product_fami;

	}

	@Override
	public Collection<shopNoweventProducts_family> getProducts_familyById(int id) {
		// SELECT column_name(s) FROM table_name where column = value
		String sql = "SELECT product_family_id,GROUP_CONCAT(product_family) AS products_family FROM products_family WHERE product_family_id=?";
		List<shopNoweventProducts_family> product_fami = jdbcTemplate.query(sql, new Products_familyRowMapper(), id);

		return product_fami;
	}

	// -----------------------------------------------------------PRODUCTS_OPTIONS
	// TABLE------------------------------------------------------------------------------------------------

	private static class Product_optionsRowMapper implements RowMapper<shopNoweventProduct_options> {

		@Override
		public shopNoweventProduct_options mapRow(ResultSet resultSet, int i) throws SQLException {
			shopNoweventProduct_options product_ops = new shopNoweventProduct_options();

			product_ops.setId(resultSet.getInt("id"));
			product_ops.setOption_name(resultSet.getString("option_name"));
			product_ops.setValue(resultSet.getString("value"));

			return product_ops;

		}
	}

	@Override
	public Collection<shopNoweventProduct_options> getAllProduct_options() {

		final String sql1 = " SELECT product_options.id,product_option_group.option_name,product_option_value.value FROM product_options INNER JOIN  product_option_group ON product_options.id=product_option_group.id INNER JOIN\n"
				+ " product_option_value ON product_options.id=product_option_value.id";

		List<shopNoweventProduct_options> product_fami1 = jdbcTemplate.query(sql1, new Product_optionsRowMapper());
		return product_fami1;

	}

	@Override
	public Collection<shopNoweventProduct_options> getProduct_optionsById(int id) {
		// SELECT column_name(s) FROM table_name where column = value
		String sql = " SELECT product_options.id,product_option_group.option_name,product_option_value.value FROM product_options INNER JOIN  product_option_group ON product_options.id=product_option_group.id INNER JOIN\n"
				+ " product_option_value ON product_options.id=product_option_value.id where product_options.id=?";

		List<shopNoweventProduct_options> product_fami1 = jdbcTemplate.query(sql, new Product_optionsRowMapper(), id);

		return product_fami1;
	}

	/*
	 * 
	 * if (flagChange == 0) { if (id == 0) { if (customerId == 0) { if (max_price ==
	 * 0) { System.out.println(
	 * "inside id=0 customer id=0 and max=0  all perameter null give output base on some defolt parameter"
	 * );
	 * 
	 * sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * " SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1  FROM(SELECT category_id,COUNT(category_id) AS total FROM products) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * " GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " GROUP BY products.id) AS t2 ON t1.category_id=t2.category_id)AS table1 \n"
	 * + "  LEFT JOIN \n" +
	 * " (SELECT customer_id,product_id FROM wish_list)AS table2 ON table1.id=table2.product_id and customer_id=0 ORDER BY table1.id "
	 * + order + " limit " + page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * 
	 * sql5="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id \n"
	 * + ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.id \n" + order + " limit " + page_offset + ","
	 * + page_size;
	 * 
	 * 
	 * System.out.println(sql4);
	 * 
	 * } else { System.out.println(
	 * "inside id=0 customer id=0 and max is not 0   give min_price and max_price or only max_price"
	 * );
	 * 
	 * sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * "SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * "GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " AND products.sale_price<" + max_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id)AS table1 LEFT JOIN \n"
	 * +
	 * "(SELECT customer_id,product_id FROM wish_list)AS table2 ON table1.id=table2.product_id customer_id=0 ORDER BY table1.id "
	 * + order + " LIMIT " + page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE products.NAME LIKE '%"
	 * +search+"%' AND products.sale_price >"+ min_price+"AND products.sale_proce<"
	 * + max_price +"\n" + ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.id \n" + order + " limit " + page_offset + ","
	 * + page_size; System.out.println(sql4);
	 * 
	 * 
	 * } } else {
	 * System.out.println("inside id=0 customer id not 0 and max is not 0"); sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * "SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id, COUNT(category_id) AS total FROM products) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * "GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id)AS table1 \n"
	 * +
	 * "LEFT JOIN  (SELECT customer_id,product_id FROM wish_list WHERE customer_id='"
	 * + customerId +
	 * "')AS table2 ON table1.id=table2.product_id ORDER BY table1.id " + order +
	 * " LIMIT " + page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE products.NAME LIKE '%"
	 * +search+"%' AND products.sale_price >"+ min_price+"\n" +
	 * ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.id \n" + order + " limit " + page_offset + ","
	 * + page_size;
	 * 
	 * System.out.println(sql4); }
	 * 
	 * } else {
	 * 
	 * if (customerId == 0) {
	 * 
	 * if (max_price == 0) {
	 * System.out.println("inside id not 0 customerid=0 and max_price=0 ");
	 * 
	 * sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * " SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * " GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search +
	 * "%' AND products.sale_price > 0.0 GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id WHERE t1.category_id="
	 * + id + ")AS table1  LEFT JOIN \n" +
	 * "(SELECT customer_id,product_id FROM wish_list)AS table2 ON table1.id=table2.product_id and customer_id =0 ORDER BY table1.id ASC LIMIT "
	 * + page_offset + "," + page_size;
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+"\n" + ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.id \n" + order + " limit " + page_offset + ","
	 * + page_size;
	 * 
	 * 
	 * 
	 * System.out.println(sql4);
	 * 
	 * } else {
	 * System.out.println("inside id not 0 customer id=0 and max is not 0");
	 * 
	 * sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * " SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * " GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " AND products.sale_price<"+ max_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id WHERE t1.category_id="
	 * + id + ")AS table1 LEFT JOIN \n" +
	 * "(SELECT customer_id,product_id FROM wish_list)AS table2 ON table1.id=table2.product_id and customer_id =0 ORDER BY table1.id "
	 * + order + " LIMIT  " + page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+" AND products.sale_price<"+ max_price+"\n" +
	 * ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.id \n" + order + " limit " + page_offset + ","
	 * + page_size; System.out.println(sql4);
	 * 
	 * } }
	 * 
	 * else if (max_price == 0) { System.out.
	 * println("inside else if id not 0 customer id not 0 but max price ==0");
	 * System.out.println(order); sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * "    			    SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * "    			    GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id WHERE t1.category_id="
	 * + id + ")AS table1 \n" + "    			   LEFT JOIN \n" +
	 * "    			   (SELECT customer_id,product_id FROM wish_list WHERE customer_id='"
	 * + customerId +
	 * "')AS table2 ON table1.id=table2.product_id ORDER BY table1.id " + order +
	 * " LIMIT " + page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+"\n" + ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.id \n" + order + " limit " + page_offset + ","
	 * + page_size;
	 * 
	 * 
	 * System.out.println(sql4);
	 * 
	 * } else { System.out.
	 * println("output when give  all input parameter in correct validation"); sql4
	 * =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * "    			    SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * "    			    GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " AND products.sale_price<" + max_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id WHERE t1.category_id="
	 * + id + ")AS table1 \n" + "    			   LEFT JOIN \n" +
	 * "    			   (SELECT customer_id,product_id FROM wish_list WHERE customer_id='"
	 * + customerId +
	 * "')AS table2 ON table1.id=table2.product_id ORDER BY table1.id " + order +
	 * " LIMIT " + page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+" AND products.sale_price<"+ max_price+"\n" +
	 * ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.id \n" + order + " limit " + page_offset + ","
	 * + page_size;
	 * 
	 * System.out.println(sql4);
	 * 
	 * 
	 * }
	 * 
	 * }
	 * 
	 * } else {
	 * 
	 * if (id == 0) { if (customerId == 0) { if (max_price == 0) {
	 * System.out.println(
	 * "inside id=0 customer id=0 and max=0  all perameter null give output base on some defolt parameter"
	 * );
	 * 
	 * sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * " SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * " GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " GROUP BY products.id) AS t2 ON t1.category_id=t2.category_id)AS table1 \n"
	 * + "  LEFT JOIN \n" +
	 * " (SELECT customer_id,product_id FROM wish_list)AS table2 ON table1.id=table2.product_id ORDER BY table1.sale_price "
	 * + order + " limit " + page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+"\n" + ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.sale_price \n" + order + " limit " +
	 * page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * System.out.println(sql4); } else { System.out.println(
	 * "inside id=0 customer id=0 and max is not 0   give min_price and max_price or only max_price"
	 * );
	 * 
	 * sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * "SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * "GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " AND products.sale_price<" + max_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id)AS table1 LEFT JOIN \n"
	 * +
	 * "(SELECT customer_id,product_id FROM wish_list)AS table2 ON table1.id=table2.product_id ORDER BY table1.sale_price "
	 * + order + " LIMIT 10 ";
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+ " AND products.sale_price<"+ max_price+ "\n" +
	 * ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.sale_price \n" + order + " limit " +
	 * page_offset + "," + page_size;
	 * 
	 * System.out.println(sql4);
	 * 
	 * 
	 * } } else {
	 * System.out.println("inside id=0 customer id not 0 and max is not 0"); sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * "SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * "GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id)AS table1 \n"
	 * +
	 * "LEFT JOIN  (SELECT customer_id,product_id FROM wish_list WHERE customer_id='"
	 * + customerId +
	 * "')AS table2 ON table1.id=table2.product_id ORDER BY table1.sale_price " +
	 * order + " LIMIT " + page_offset + "," + page_size;
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+"\n" + ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.sale_price \n" + order + " limit " +
	 * page_offset + "," + page_size;
	 * 
	 * System.out.println(sql4);
	 * 
	 * 
	 * }
	 * 
	 * } else {
	 * 
	 * if (customerId == 0) {
	 * 
	 * if (max_price == 0) {
	 * System.out.println("inside id not 0 customerid=0 and max_price=0 ");
	 * 
	 * sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * " SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * " GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search +
	 * "%' AND products.sale_price > 0.0 GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id WHERE t1.category_id="
	 * + id + ")AS table1  LEFT JOIN \n" +
	 * "(SELECT customer_id,product_id FROM wish_list)AS table2 ON table1.id=table2.product_id ORDER BY table1.sale_price "
	 * + order + " LIMIT " + page_offset + "," + page_size;
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+ " AND products.sale_price<"+ max_price+"\n" +
	 * ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.sale_price \n" + order + " limit " +
	 * page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * System.out.println(sql4);
	 * 
	 * } else {
	 * System.out.println("inside id not 0 customer id=0 and max is not 0");
	 * 
	 * sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * " SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * " GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " AND products.sale_price<" + max_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id WHERE t1.category_id="
	 * + id + ")AS table1 LEFT JOIN \n" +
	 * "(SELECT customer_id,product_id FROM wish_list)AS table2 ON table1.id=table2.product_id ORDER BY table1.sale_price "
	 * + order + " LIMIT  " + page_offset + "," + page_size;
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+ " AND products.sale_price<"+ max_price+"\n" +
	 * ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.sale_price \n" + order + " limit " +
	 * page_offset + "," + page_size;
	 * 
	 * System.out.println(sql4);
	 * 
	 * } }
	 * 
	 * else if (max_price == 0) { System.out.
	 * println("inside else if id not 0 customer id not 0 but max price ==0");
	 * System.out.println(order); sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * "    			    SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * "    			    GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id WHERE t1.category_id="
	 * + id + ")AS table1 \n" + "    			   LEFT JOIN \n" +
	 * "    			   (SELECT customer_id,product_id FROM wish_list WHERE customer_id='"
	 * + customerId +
	 * "')AS table2 ON table1.id=table2.product_id ORDER BY table1.sale_price " +
	 * order + " LIMIT  " + page_offset + "," + page_size;
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+"\n" + ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.sale_price \n" + order + " limit " +
	 * page_offset + "," + page_size;
	 * 
	 * System.out.println(sql4);
	 * 
	 * } else { System.out.
	 * println("output when give  all input parameter in correct validation");
	 * 
	 * sql4 =
	 * "SELECT table1.id,(CASE WHEN table2.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist,table1.name,table1.description,table1.regular_price,table1.sale_price,table1.total,table1.stock,table1.image, table1.image1   FROM (\n"
	 * +
	 * " SELECT t2.id,t2.name,t2.description,t2.regular_price,t2.sale_price,t1.total,t2.stock,t2.image,t2.image1 FROM(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS t1 INNER JOIN(SELECT products.id,products.description,products.name,products.regular_price,products.sale_price,products.stock,products.image,products.category_id, \n"
	 * +
	 * " GROUP_CONCAT(product_image.image) AS image1 FROM products,product_image WHERE products.id=product_image.product_id AND products.NAME LIKE '%"
	 * + search + "%' AND products.sale_price > " + min_price +
	 * " AND products.sale_price<" + max_price +
	 * " GROUP BY products.id ) AS t2 ON t1.category_id=t2.category_id WHERE t1.category_id="
	 * + id + ")AS table1 \n" + "    			   LEFT JOIN \n" +
	 * "    			   (SELECT customer_id,product_id FROM wish_list WHERE customer_id='"
	 * + customerId +
	 * "')AS table2 ON table1.id=table2.product_id ORDER BY table1.sale_price " +
	 * order + " LIMIT  " + page_offset + "," + page_size;
	 * 
	 * 
	 * 
	 * sql4="SELECT table3.*,(CASE WHEN wish_list.product_id IS NOT NULL THEN 1 ELSE 0 END)AS wishlist FROM\n"
	 * +
	 * "(SELECT table2.*,(CASE WHEN pit.image1 IS NOT NULL THEN pit.image1 ELSE 'NA' END)AS image1 FROM\n"
	 * + "(SELECT products.*,table1.total FROM products INNER JOIN \n" +
	 * "(SELECT category_id,COUNT(category_id) AS total FROM products GROUP BY category_id) AS table1 ON products.category_id=table1.category_id WHERE table1.category_id="
	 * + id +" AND products.NAME LIKE '%"+search+"%' AND products.sale_price >"+
	 * min_price+" AND products.sale_price<"+ max_price+"\n" +
	 * ")AS table2 LEFT JOIN\n" +
	 * "(SELECT id,GROUP_CONCAT(product_image.image) AS image1 FROM product_image GROUP BY id) AS pit ON table2.id=pit.id)AS table3\n"
	 * + "LEFT JOIN wish_list ON table3.id=wish_list.product_id AND customer_id ="
	 * +customerId+" ORDER BY table3.sale_price \n" + order + " limit " +
	 * page_offset + "," + page_size; System.out.println(sql4);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 */

}