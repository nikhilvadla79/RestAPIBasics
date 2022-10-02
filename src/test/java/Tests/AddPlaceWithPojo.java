package Tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import PojoClass.Serialization.AddPlace;
import PojoClass.Serialization.Location;

public class AddPlaceWithPojo {

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

		

		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().queryParam("key", "qaclick123").body(p)
		.when().post("/maps/api/place/add/json")
		.then().log().all().extract().asString();
		
		
		
	}

}
