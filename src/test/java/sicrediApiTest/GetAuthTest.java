package sicrediApiTest;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import apiConfigs.APIPath;
import apiConfigs.HeaderConfigs;
import apiVerifications.APIVerification;
import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetAuthTest extends BaseTest {

	private Map<String, String> postRequestBody() {
		Map<String, String> body = new HashMap<String, String>();
		body.put("username", "kminchelle");
		body.put("password", "0lelplR");

		return body;
	}

	private String token;

	@BeforeClass
	public void startingTest() {
		test.log(LogStatus.INFO, "My test is starting......");
	}

	@BeforeMethod
	public void setup() {
		HeaderConfigs header = new HeaderConfigs();
		Map<String, String> requestBody = postRequestBody();

		Response response = RestAssured.given().when().headers(header.defaultHeaders()).body(requestBody).when()
				.post(APIPath.apiTest.POST_Login);

		APIVerification.responseCodeValidation(response, 200);
		APIVerification.responseTimeValidation(response);
		APIVerification.responseKeyValidationFromJsonObject(response, "token");

		token = response.jsonPath().getString("token");
	}

	@Test
	public void getAuth() {
		Response responseToken = RestAssured.given().header("Authorization", "Bearer " + token).when()
				.get(APIPath.apiTest.GET_auth);

		APIVerification.responseCodeValidation(responseToken, 200);
		APIVerification.responseTimeValidation(responseToken);
	}


	@Test
	public void getAuth403() {
		Response responseToken = RestAssured.given().when().get(APIPath.apiTest.GET_auth);

		APIVerification.responseCodeValidation(responseToken, 403);
		APIVerification.responseTimeValidation(responseToken);
		APIVerification.responseKeyValidationFromJsonObject(responseToken, "message");
	}

	@Test
	public void getAuth404() {
		Response responseToken = RestAssured.given().header("Authorization", "Bearer " + token).when()
				.get(APIPath.apiTest.GET_auth + "Error Test");

		APIVerification.responseCodeValidation(responseToken, 404);
		APIVerification.responseTimeValidation(responseToken);
	}

	@Test
	public void getAuth500() {
		Response responseToken = RestAssured.given().header("Authorization", "Bearer Error Test " + token).when()
				.get(APIPath.apiTest.GET_auth);

		APIVerification.responseCodeValidation(responseToken, 500);
		APIVerification.responseTimeValidation(responseToken);
		APIVerification.responseKeyValidationFromJsonObject(responseToken, "message");
	}

	@AfterClass
	public void finishingTest() {
		test.log(LogStatus.INFO, "My test is ended......");
	}

}