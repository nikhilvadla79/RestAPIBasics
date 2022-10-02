package Tests;

import static io.restassured.RestAssured.*;

import java.sql.DriverManager;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import PojoClass.Deserialization.API;
import PojoClass.Deserialization.InstitueDetails;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		/**
		 * We have to hit Below URL manually as it can not be automated, and get url after the authentication
		 * Automation is restricted by Google
		 */
		/*System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikhi\\eclipse-workspace\\RestAPIbyRahul\\resources\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=Verifyw");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("srinath19830");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("password");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000); */
		String URL="https://rahulshettyacademy.com/getCourse.php?state=Verifyw&code=4%2F0ARtbsJqWGS9QQBrxXB-7dc3igGjpJaUBuLp8u7j4WUHi3gQH3TwcnWC9G61f3cvYWcD0VA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		
		String code=URL.split("code=")[1].split("&")[0];
		System.out.println(code);
		
		String AccessTokenResponse=given().log().all().urlEncodingEnabled(false).queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		System.out.println(AccessTokenResponse);
		JsonPath js=new JsonPath(AccessTokenResponse);
		String accesstoken=js.getString("access_token");
		System.out.println(accesstoken);
		
		//Pojo class
		InstitueDetails id=given().queryParam("access_token",accesstoken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php").as(InstitueDetails.class);
		
		System.out.println(id.getServices());
	
		//Print List of API courses and its prices
		
		List<API> api=id.getCourses().getApi();
		for(int i=0;i<api.size();i++) {
			System.out.print("Course title is : "+api.get(i).getCourseTitle());
			System.out.println(" Price of course is : "+api.get(i).getPrice());
			
		}
		
	}

}
