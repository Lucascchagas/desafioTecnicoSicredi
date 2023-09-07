package sicrediApiTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import apiConfigs.APIPath;
import apiVerifications.APIVerification;
import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetUsersTest extends BaseTest {

	@BeforeClass
	public void startingTest() {
		test.log(LogStatus.INFO, "My test is starting......");
	}

	@Test
	public void getUsers() {

		Response response = RestAssured.given().when().get(APIPath.apiTest.GET_Users);

		APIVerification.responseCodeValidation(response, 200);
		APIVerification.responseTimeValidation(response);
		APIVerification.responseKeyValidationFromJsonObject(response, "users");

	}

	@Test
	public void getUsers404() {

		Response response = RestAssured.given().when().get(APIPath.apiTest.GET_Users + "ErrorTest");

		APIVerification.responseCodeValidation(response, 404);
		APIVerification.responseTimeValidation(response);
	}

	@AfterClass
	public void finishingTest() {
		test.log(LogStatus.INFO, "My test is ended......");
	}

}
