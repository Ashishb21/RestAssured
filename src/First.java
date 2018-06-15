import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;


import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;


public class First {
	
	Properties prop = new Properties();
	
	
	@BeforeTest
	public void getData() throws IOException {
		
		FileInputStream fis= new FileInputStream("C:\\Users\\admin\\eclipse-workspace\\RestA\\src\\env.properties");
		prop.load(fis);
		}
	
	@Test
	public void getN() {
		// TODO Auto-generated method stub
		String Url=prop.getProperty("HOST");
		System.out.println(Url);
		String URI="/mtstatusapi/index/getactivitystatus/msgId/TestIDivo6x";
		System.out.println("in test");
		RestAssured.baseURI=prop.getProperty("HOST");
		Response res=(Response) given().when().get(Resource.placePostDate()).then().
				assertThat().statusCode(200).and().extract().response();
		// Task 2- Grab the Place ID from response
		//String responseString=res.asString();
		XmlPath xmlres=ReusableMethods.rawToXML(res);
		String resp=xmlres.get("statusResponse.msg[0].ID");
		System.out.println(resp);
		
	}
	@Test
	public void postData() throws IOException {
		String postdata=GenerateStringFromResource("C:\\Users\\admin\\eclipse-workspace\\RestA\\src\\postdata.xml");
		RestAssured.baseURI=prop.getProperty("HOST");
		Response res=(Response)given().body(postdata).when().post(Resource.getPostUrl()).then().assertThat().statusCode(200).and().body("mt-sms-intercept-response.status",equalTo("success")).and().extract().response();
		XmlPath xmlres=ReusableMethods.rawToXML(res);
		//XmlPath messageId =xmlres.get("messageId");
		String a=xmlres.get("mt-sms-intercept-response.messageId");
		//System.out.println(a);
	}
	
	public static String GenerateStringFromResource(String path) throws IOException {
	    return new String(Files.readAllBytes(Paths.get(path)));

	}
}




