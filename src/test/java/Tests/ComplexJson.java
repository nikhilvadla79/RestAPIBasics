package Tests;

import org.testng.Assert;

import com.google.gson.JsonParser;

import File.PayLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJson {

	public static void main(String[] args) {
	
	JsonPath js=new JsonPath(PayLoad.Courses());
	
	//1. Print No of courses returned by API
	System.out.println("Count of courses is : "+js.getString("courses.size()"));
	int coursecount=js.getInt("courses.size()");
	//2.Print Purchase Amount
	System.out.println("Purchased Amount is : "+js.getString("dashboard.purchaseAmount"));
	
	//3.Print Title of the first course
	System.out.println("First course title is : "+js.getString("courses.title[0]"));
	int RPAcopies=0;
	int coursesSum=0;
	for(int i=0;i<coursecount;i++) {
		
		System.out.println("Couse is "+js.getString("courses["+i+"].title")+" and respective price is : "+js.getInt("courses.price["+i+"]"));
		String course=js.getString("courses.title["+i+"]");
		int price=js.getInt("courses.price["+i+"]");
		int copies=js.getInt("courses.copies["+i+"]");
		if(course.equals("RPA"))
			RPAcopies=js.getInt("courses.copies["+i+"]");
		coursesSum+=copies*price;
	}
	// Print no of copies sold by RPA Course
	System.out.println("No of copies sold by RPA course are : "+RPAcopies);
	//Verify if Sum of all Course prices matches with Purchase Amount
	int actualpurchaseamount=js.getInt("dashboard.purchaseAmount");
	System.out.println("Total purchase amount is : "+coursesSum);
	Assert.assertEquals(actualpurchaseamount, coursesSum);
	
	
	
	}
}
