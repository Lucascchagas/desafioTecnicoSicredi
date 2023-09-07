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

public class PostProductsTest extends BaseTest {

	Map<String, String> requestBody = postRequestBody();

	public Map<String, String> postRequestBody() {
		Map<String, String> body = new HashMap<String, String>();
		body.put("title", "Perfume Oil");
		body.put("password", "Mega Discount, Impression of A...");
		body.put("price", "13");
		body.put("discountPercentage", "8.4");
		body.put("rating", "4.26");
		body.put("stock", "65");
		body.put("brand", "Impression of Acqua Di Gio");
		body.put("category", "fragrances");
		body.put("thumbnail", "https://i.dummyjson.com/data/products/11/thumnail.jpg");

		return body;
	}

	@BeforeClass
	public void startingTest() {
		test.log(LogStatus.INFO, "My test is starting......");

	}

	@Test
	public void postProductsAPITest() {

		HeaderConfigs header = new HeaderConfigs();

		Response response = (Response) RestAssured.given().when().headers(header.defaultHeaders()).body(requestBody)
				.when().post(APIPath.apiTest.POST_Products);

		APIVerification.responseCodeValidation(response, 200);
		APIVerification.responseTimeValidation(response);
		APIVerification.responseKeyValidationFromJsonObject(response, "id");
		APIVerification.responseKeyValidationFromJsonObject(response, "title");
		APIVerification.responseKeyValidationFromJsonObject(response, "price");
		APIVerification.responseKeyValidationFromJsonObject(response, "stock");
		APIVerification.responseKeyValidationFromJsonObject(response, "rating");
		APIVerification.responseKeyValidationFromJsonObject(response, "thumbnail");
		APIVerification.responseKeyValidationFromJsonObject(response, "brand");
		APIVerification.responseKeyValidationFromJsonObject(response, "category");

	}
	
	@Test
	public void postProductsAPITest400Body() {

		HeaderConfigs header = new HeaderConfigs();

		Response response = (Response) RestAssured.given().when().headers(header.defaultHeaders())
				.body(requestBody + "ErrorTest").when().post(APIPath.apiTest.POST_Products);

		System.out.print(requestBody);

		APIVerification.responseCodeValidation(response, 400);
		APIVerification.responseTimeValidation(response);
		APIVerification.responseKeyValidationFromJsonObject(response, "message");

	}

	@Test
	public void postProductsAPITest404() {

		HeaderConfigs header = new HeaderConfigs();

		Response response = (Response) RestAssured.given().when().headers(header.defaultHeaders()).body(requestBody)
				.when().post(APIPath.apiTest.POST_Products + "ErrorTest");

		APIVerification.responseCodeValidation(response, 404);
		APIVerification.responseTimeValidation(response);

	}

	@AfterClass
	public void finishingTest() {
		test.log(LogStatus.INFO, "My test is ended......");
	}

}
