package sicrediApiTest;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import apiConfigs.APIPath;
import apiConfigs.HeaderConfigs;
import apiVerifications.APIVerification;
import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PostAuthTest extends BaseTest {

	Map<String, String> requestBody = postRequestBody();

	public Map<String, String> postRequestBody() {
		Map<String, String> body = new HashMap<String, String>();
		body.put("username", "kminchelle");
		body.put("password", "0lelplR");

		return body;

	}

	@BeforeClass
	public void startingTest() {
		test.log(LogStatus.INFO, "My test is starting......");
	}

	@Test
	public void PostAuthAPITest() {

		HeaderConfigs header = new HeaderConfigs();

		Response response = (Response) RestAssured.given().when().headers(header.defaultHeaders()).body(requestBody)
				.when().post(APIPath.apiTest.POST_Login);

		APIVerification.responseCodeValidation(response, 200);
		APIVerification.responseTimeValidation(response);
		APIVerification.responseKeyValidationFromJsonObject(response, "id");
		APIVerification.responseKeyValidationFromJsonObject(response, "username");
		APIVerification.responseKeyValidationFromJsonObject(response, "email");
		APIVerification.responseKeyValidationFromJsonObject(response, "firstName");
		APIVerification.responseKeyValidationFromJsonObject(response, "lastName");
		APIVerification.responseKeyValidationFromJsonObject(response, "gender");
		APIVerification.responseKeyValidationFromJsonObject(response, "image");
		APIVerification.responseKeyValidationFromJsonObject(response, "token");

	}

	@Test
	public void postAuthAPITest400Headers() {

		Response response = (Response) RestAssured.given().when().body(requestBody).when()
				.post(APIPath.apiTest.POST_Login);

		APIVerification.responseCodeValidation(response, 400);
		APIVerification.responseTimeValidation(response);
		APIVerification.responseKeyValidationFromJsonObject(response, "message");

	}

	@Test
	public void postAuthAPITest400Body() {

		HeaderConfigs header = new HeaderConfigs();

		Response response = (Response) RestAssured.given().when().headers(header.defaultHeaders())
				.body(requestBody + "ErrorTest").when().post(APIPath.apiTest.POST_Login);

		System.out.print(requestBody);

		APIVerification.responseCodeValidation(response, 400);
		APIVerification.responseTimeValidation(response);
		APIVerification.responseKeyValidationFromJsonObject(response, "message");

	}

	@Test
	public void postAuthAPITest403() {

		HeaderConfigs header = new HeaderConfigs();

		Response response = (Response) RestAssured.given().when().headers(header.defaultHeaders()).body(requestBody)
				.when().post(APIPath.apiTest.POST_Login + "ErrorTest");

		APIVerification.responseCodeValidation(response, 403);
		APIVerification.responseTimeValidation(response);
		APIVerification.responseKeyValidationFromJsonObject(response, "message");

	}

	@AfterClass
	public void finishingTest() {
		test.log(LogStatus.INFO, "My test is ended......");
	}

}
