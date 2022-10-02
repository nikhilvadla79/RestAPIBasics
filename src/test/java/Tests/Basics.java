package Tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;


import File.PayLoad;
import File.ReUsableMethods;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JsonPath js;
		
		//Validate if Addplace API is working
		//given -- All input details
		//when -- Submit the API --resource, httpmethod
		//then -- validate the response 
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String postresponse=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\nikhi\\eclipse-workspace\\RestAPIbyRahul\\resources\\AddPlaceJson.txt")))).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.41 (Ubuntu)")
		.extract().response().asString();
		System.out.println(postresponse);
		js=ReUsableMethods.rawToJson(postresponse);
		String place_id=js.getString("place_id");
		System.out.println(place_id);
		
		
		//Update Place
		String newAdress="70 Summer walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+newAdress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String getresponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().asString();
		
		js=ReUsableMethods.rawToJson(getresponse);
		String ActualAddress=js.getString("address");
		System.out.println(ActualAddress);
		Assert.assertEquals(ActualAddress,newAdress);
		
		
		
	}

}
