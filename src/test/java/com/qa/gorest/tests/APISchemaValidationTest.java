package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.Users;
import com.qa.gorest.utils.StringUtils;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class APISchemaValidationTest extends BaseTest{

	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@Test()
	public void createUserAPISchemaTest() {
		
		//1. post:
		Users user = new Users("Tom", StringUtils.getRandomEmailId(), "male", "active");		
		
		 restClient.post(GOREST_ENDPOINT, "json", user, true, true)
					.then().log().all()
						.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
							.body(matchesJsonSchemaInClasspath("createuserschema.json"));
	}

}
