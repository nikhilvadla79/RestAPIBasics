package Tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;import java.util.Map;

import org.junit.Test;
import File.ReUsableMethods;
import File.ReadExcelData;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DataDriven {

	
	@Test
	public void excelDriven() throws IOException {
		
		ReadExcelData readexcel=new ReadExcelData();
		ArrayList<String> data=readexcel.getData("AddBookAPI");
		HashMap<String, Object> AddBookpayload=new HashMap<>();
		AddBookpayload.put("name", data.get(1));
		AddBookpayload.put("isbn", data.get(2));
		AddBookpayload.put("aisle", data.get(3));
		AddBookpayload.put("author", data.get(4));
		RestAssured.baseURI="http://216.10.245.166";
/*		String addBookResponse=given().log().all().header("Content-Type","application/json")
		.body(AddBookpayload)
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().asString();   */
		
		Map<String,Object> response=given().log().all().header("Content-Type","application/json")
				.body(AddBookpayload)
				.when().post("Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().as(Map.class);
		System.out.println(response);
	//	JsonPath js=ReUsableMethods.rawToJson(addBookResponse);
		System.out.println(response.get("ID"));
	//	String id=js.getString("ID");
	//	System.out.println(id);
	}
	}
