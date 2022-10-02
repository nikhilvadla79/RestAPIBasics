package Tests;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import File.ReUsableMethods;

public class JIRATest {

		public static void main(String args[]) {
			
			RestAssured.baseURI="http://localhost:8081/";
			SessionFilter session=new SessionFilter();
			//Login
			String loginresponse=given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{ \"username\": \"nikhilvadla790\", \"password\": \"Nikhil@479\" }")
			.filter(session).when().post("rest/auth/1/session").then().extract().asString();
				
			//Add Comment
			String comment="added comment";
			String AddCommentResponse =given().pathParam("key","10002").header("Content-Type","application/json").body("{\r\n"
					+ "    \"body\":\""+comment+"\",\r\n"
					+ "    \"visibillity\" : {\r\n"
					+ "        \"type\" : \"role\",\r\n"
					+ "        \"value\" : \"Administrators\"\r\n"
					+ "    }\r\n"
					+ "}").filter(session).when().post("rest/api/2/issue/{key}/comment")
			.then().assertThat().statusCode(201).extract().asString();
			
			JsonPath js=new JsonPath(AddCommentResponse);
			String commentid=js.getString("id");
			
			//Add attachment
			
			given().header("X-Atlassian-Token","no-check").pathParam("key","10002").filter(session).
			multiPart("file",new File("C:\\Users\\nikhi\\eclipse-workspace\\RestAPIbyRahul\\resources\\AddPlaceJson.txt")).header("Content-Type","multipart/form-data")
			.when().post("/rest/api/2/issue/{key}/attachments")
			.then().assertThat().statusCode(200);
			
			
			//Get Issue details
			String issueDetails=given().filter(session).pathParam("key", "10002").queryParam("fields", "comment")
			.when().get("/rest/api/2/issue/{key}")
			.then().extract().asString();
			
			js=new JsonPath(issueDetails);
			int commentcount=js.get("fields.comment.comments.size()");
			for(int i=0;i<commentcount;i++) {
				String ActualCommentID=js.get("fields.comment.comments["+i+"].id").toString();
				if(ActualCommentID.equalsIgnoreCase(commentid)) {
					String Message=js.get("fields.comment.comments["+i+"].body").toString();
					System.out.println(Message);
					Assert.assertEquals(Message,comment);
				}
			}
		}
}
