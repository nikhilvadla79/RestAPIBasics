package Tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import PojoClass.Serialization.AddPlace;
import PojoClass.Serialization.Location;

public class SpecBuildTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Pojo Set
		AddPlace p=new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		List<String> types=new ArrayList<>();
		types.add("shoe park");
		types.add("shop");
		p.setTypes(types);
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);

		/**
		 * Building RequestSpecification
		 * @Parameters : BaseURI, QueryParm, PathParam, ContentType
		 */
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		/**
		 * Building ResponseSpecification
		 * @Parameters : statusCode, expectContentType
		 */
		ResponseSpecification resSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		/**
		 * Simplified API Post Request
		 */
		RequestSpecification res=given().spec(req)
		.body(p);		
		Response response=res.when().post("/maps/api/place/add/json").then().spec(resSpec).extract().response();
		
		String resString=response.asString();
		System.out.println(resString);
		
		
		
	}

}
