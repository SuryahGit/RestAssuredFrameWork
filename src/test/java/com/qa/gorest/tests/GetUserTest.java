package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class GetUserTest extends BaseTest {

	@BeforeMethod
	public void getUserSetup()
	{
		restClient = new RestClient(prop, baseURI); 
	}
	
	@Test(enabled=false,priority=3,description="This test is in progress")
	public void getGoRestAllUsersTest() {
		restClient.get(GOREST_ENDPOINT, true, true).then().log().all().assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test(priority=2)
	public void getSpecificUser() {

		restClient.get(GOREST_ENDPOINT+"/5804080", true, true).then().log().all().assertThat().statusCode(APIHttpStatus.OK_200.getCode()).and()
				.body("id", equalTo(5804080));
	}

	@Test(priority=1)
	public void getUserWithQueryParam() {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", "Devak Kocchar");
		queryParams.put("status", "active");
		restClient.get(GOREST_ENDPOINT, null, queryParams, true, true).then().log().all().assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}

}
