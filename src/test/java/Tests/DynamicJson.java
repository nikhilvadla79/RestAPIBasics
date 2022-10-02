package Tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import File.PayLoad;
import File.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;

public class DynamicJson {

	ArrayList<String> BookID=new ArrayList();	
	@Test(dataProvider = "BookDetails")
	public void addBook(String isbn,String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String addBookResponse=given().header("Content-Type","application/json")
		.body(PayLoad.addBook(isbn,aisle)).when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).extract().asString();
		
		JsonPath js=ReUsableMethods.rawToJson(addBookResponse);
		String id=js.getString("ID");
		BookID.add(id);
		System.out.println(id);
	}
	
	@DataProvider(name = "BookDetails")
	public Object[][] getData() {
		return new Object[][] {{"abca","1234"},{"accb","1235"},{"abcc","1236"}};
	}
	
}
