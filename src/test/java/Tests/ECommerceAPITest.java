package Tests;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.RequestBuilder;

import PojoClass.ECommerce.CreateOrderRequest;
import PojoClass.ECommerce.CreateOrderResponse;
import PojoClass.ECommerce.LoginRequest;
import PojoClass.ECommerce.LoginResponse;
import PojoClass.ECommerce.Orders;
import PojoClass.ECommerce.ViewOrderResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSenderOptions;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ECommerceAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/**
		 * @Login - Send login request and get token and userId
		 */
		System.out.println("---------------Login----------------\n");
		
		LoginRequest l=new LoginRequest();
		l.setuserEmail("postmanpractice@gmail.com");
		l.setuserPassword("Password@1");		
		
		RequestSpecification reqSpec =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		Response res=given().spec(reqSpec).body(l).when().post("/api/ecom/auth/login");
		LoginResponse loginresponse=res.then().log().all().extract().response().as(LoginResponse.class);
		
		String token=loginresponse.getToken();
		String userId=loginresponse.getUserId();
		
		/**
		 * @CreateProduct - Create a product with form data
		 */
		
		System.out.println("\n------------------Create Product-------------\n");
		reqSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",token).build();
		
		String addProductResponse=given().spec(reqSpec).param("productName", "Laptop")
		.param("productAddedBy", userId)
		.param("productCategory", "Accessories")
		.param("productSubCategory", "Laptops")
		.param("productPrice", "12000")
		.param("productDescription", "Lenovo ideapad 3")
		.param("productFor", "Students").multiPart("productImage", new File("C:\\Users\\nikhi\\OneDrive\\Pictures\\Saved Pictures\\earth.jpg"))
		.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath js=new JsonPath(addProductResponse);
		String productId=js.getString("productId");
		
		/**
		 * @CreateOrder - Create order
		 */
		System.out.println("\n-----------------Create Order------------------\n");
		
		Orders order=new Orders();
		order.setCountry("India");
		order.setProductOrderedId(productId);
		
		List<Orders> orderlist=new ArrayList<Orders>();
		orderlist.add(order);		
		
		CreateOrderRequest createorder=new CreateOrderRequest();
		createorder.setOrders(orderlist);
		
		reqSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",token)
				.setContentType(ContentType.JSON).build();
		
		CreateOrderResponse createorderresponse=given().spec(reqSpec).body(createorder).when().post("api/ecom/order/create-order")
		.then().log().all().extract().response().as(CreateOrderResponse.class);
		
		/**
		 * @ViewOrderDeatils - Get the order details
		 */
		List<String> orders=createorderresponse.getOrders();
		System.out.println("Number of orders : "+orders.size());
		for(String orderid: orders) {
		reqSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",token)
				.addQueryParam("id",orderid).build();
		ViewOrderResponse vieworderresponse=given().spec(reqSpec).when().get("api/ecom/order/get-orders-details")
		.then().log().all().extract().response().as(ViewOrderResponse.class);
		
		System.out.println("\n-------------Order Details------------\n");
		System.out.println("Order id : "+vieworderresponse.getData().get_id());
		System.out.println("Order by : "+vieworderresponse.getData().getOrderBy());
		System.out.println("ProductName : "+vieworderresponse.getData().getProductName());
		System.out.println("Order Price : "+vieworderresponse.getData().getOrderPrice());
		}
		
		/**
		 * @DeleteOrder - Delete product
		 */
		System.out.println("\n-------------Delete Product-----------------\n");
		reqSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",token)
				.addPathParam("productId",productId).build();
		String deleteresponse=given().spec(reqSpec).when().delete("api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().response().asString();
		
		js=new JsonPath(deleteresponse);
		System.out.println(js.getString("message"));
	}

}
