package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.Users;
import com.qa.gorest.utils.ExcelUtils;
import com.qa.gorest.utils.StringUtils;
import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {

	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}

	@DataProvider
	public Object[][] getUserTestData() {

		return new Object[][] { { "Suriya", "male", "active" }, { "Suryah", "male", "inactive" },
				{ "Rashmi", "female", "active" },

		};

	}

	@DataProvider
	public Object[][] getUserTestSheetData() {

		return ExcelUtils.getTestData(APIConstants.GOREST_USER_SHEET_NAME);

	}

	@DataProvider
	public Object[][] getUserTestData1() {

		Object obj[][] = new Object[3][3];
		obj[0][0] = "Suriya";
		obj[0][1] = "male";
		obj[0][2] = "active";
		obj[1][0] = "Suryah";
		obj[1][1] = "male";
		obj[1][2] = "inactive";
		obj[2][0] = "Rashmi";
		obj[2][1] = "female";
		obj[2][2] = "active";
		return obj;

	}

	@Test(dataProvider = "getUserTestSheetData")
	public void getAllUsers(String name, String gender, String status) {

		// 1. Post
		Users users = new Users(name, StringUtils.getRandomEmailId(), gender, status);
		Integer userId = restClient.post(GOREST_ENDPOINT, "JSON", users, true, true).then().log().all().assertThat()
				.statusCode(APIHttpStatus.CREATED_201.getCode()).extract().path("id");
		System.out.println("User ID is " + userId);

		// 2. Get
		RestClient restClientGet = new RestClient(prop, baseURI);
		restClientGet.get(GOREST_ENDPOINT + "/" + userId, true, true).then().log().all().assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode()).and().assertThat().body("id", equalTo(userId));
	}
}
